package org.unibl.etf.mdp.control;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.mdp.gui.Main;
import org.unibl.etf.mdp.gui.MainWindowController;
import org.unibl.etf.mdp.model.Document;
import org.unibl.etf.mdp.service.ClientAppSOAPService;
import org.unibl.etf.mdp.service.ClientAppSOAPServiceServiceLocator;
import org.unibl.etf.mdp.service.IFileTrasnferService;
import org.unibl.etf.mdp.service.PassengersSOAPService;
import org.unibl.etf.mdp.service.PassengersSOAPServiceServiceLocator;

public class BorderControlThread extends Thread {

	private String client;
	private long sleepTime = 5000;

	private Consumer<String> filesSentCallback;

	private Object lock;
	private MainWindowController controller;

	public BorderControlThread(String client, Consumer<String> filesSentCallback, Object lock,
			MainWindowController mainWindowController) {
		this.client = client;
		this.filesSentCallback = filesSentCallback;
		this.lock = lock;
		this.controller = mainWindowController;
		setDaemon(true);
	}

	@Override
	public void run() {
		while (true) {
			try {
				synchronized (lock) {
					if (controller.isClose())
						lock.wait();
				}
				ClientAppSOAPServiceServiceLocator locator = new ClientAppSOAPServiceServiceLocator();
				ClientAppSOAPService service = locator.getClientAppSOAPService();
				Document files = service.getNextDocument(client);
				if (files == null)
					sleep(sleepTime);
				else {
					Registry registry = LocateRegistry.getRegistry(Main.getFilesRMIPort());
					IFileTrasnferService rmi = (IFileTrasnferService) registry.lookup(Main.getFilesRMIName());
					if (rmi.send(files.getPersonID(), files.getDocument())) {
						logPassenger(files.getPersonID());
						filesSentCallback.accept("Fajlovi uspjesno poslati.");
						service.logPassenger(files.getPersonID(), true);
					} else {
						filesSentCallback.accept("Greska.");
						service.logPassenger(files.getPersonID(), true);
					}
				}
			} catch (Exception ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
			}
		}
	}

	private void logPassenger(String personID) {
		try {
			PassengersSOAPServiceServiceLocator locator = new PassengersSOAPServiceServiceLocator();
			PassengersSOAPService service = locator.getPassengersSOAPService();
			service.addLog(personID);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}
}
