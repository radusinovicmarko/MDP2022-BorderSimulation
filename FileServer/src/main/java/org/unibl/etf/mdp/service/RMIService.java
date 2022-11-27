package org.unibl.etf.mdp.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.AccessException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class RMIService implements ServletContextListener {
	
	public static final String CONFIG_PATH = FileTransferService.RESOURCES_PATH + "config.properties";
	
	private static IFileTrasnferService stub;
	private static FileTransferService service;
	
	private static Properties getProperties() {
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(CONFIG_PATH));
			return prop;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public void contextInitialized(ServletContextEvent e) {
		System.setProperty("java.security.policy", FileTransferService.RESOURCES_PATH + "server_policyfile.txt");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			Properties prop = getProperties();
			service = new FileTransferService();
			stub = (IFileTrasnferService) UnicastRemoteObject.exportObject(service, 0);
			int port = Integer.parseInt(prop.getProperty("PORT"));
			Registry registry = LocateRegistry.createRegistry(port);
			registry.rebind("FileTransferService", stub);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
