package org.unibl.etf.mdp.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.AccessException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RMIService {

	public static final String CONFIG_PATH = "./resources/config.properties";
	public static final String LOG_PATH = "./resources/warrants.log";
	
	private static String wantedPersonsPath, policyPath, serviceName;
	private static int port;
	private static FileHandler handler;

	static {
		try {
			handler = new FileHandler(LOG_PATH, true);
			Logger.getLogger(RMIService.class.getName()).setUseParentHandlers(false);
			Logger.getLogger(RMIService.class.getName()).addHandler(handler);
		} catch (IOException e) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, e.fillInStackTrace().toString());
		}
	}

	public static void main(String[] args) {
		try {
			loadProperties();
		} catch (Exception ex) {
			Logger.getLogger(RMIService.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
			return;
		}
		System.setProperty("java.security.policy", policyPath);
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new SecurityManager());
		try {
			WarrantsService service = new WarrantsService();
			IWarrantsService stub = (IWarrantsService) UnicastRemoteObject.exportObject(service, 0);
			Registry registry = LocateRegistry.createRegistry(port);
			registry.rebind(serviceName, stub);
		} catch (Exception ex) {
			Logger.getLogger(RMIService.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
			ex.printStackTrace();
		}
	}

	public static String getWantedPersonsPath() {
		return wantedPersonsPath;
	}

	public static String getPolicyPath() {
		return policyPath;
	}

	public static String getServiceName() {
		return serviceName;
	}

	public static int getPort() {
		return port;
	}

	private static void loadProperties() throws Exception {
		Properties prop = new Properties();
		prop.load(new FileInputStream(CONFIG_PATH));
		port = Integer.parseInt(prop.getProperty("PORT"));
		wantedPersonsPath = prop.getProperty("WANTED_PERSONS_FILE_PATH");
		policyPath = prop.getProperty("POLICY_PATH");
		serviceName = prop.getProperty("SERVICE_NAME");
	}

}
