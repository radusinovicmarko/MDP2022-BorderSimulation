package org.unibl.etf.mdp.control;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.mdp.gui.Main;
import org.unibl.etf.mdp.gui.MainWindowController;
import org.unibl.etf.mdp.service.ClientAppSOAPService;
import org.unibl.etf.mdp.service.ClientAppSOAPServiceServiceLocator;
import org.unibl.etf.mdp.service.IWarrantsService;
import org.unibl.etf.mdp.service.PassengersSOAPService;
import org.unibl.etf.mdp.service.PassengersSOAPServiceServiceLocator;

public class PoliceControlThread extends Thread {
	
	private String client;
	private long sleepTime = 5000;
	
	private Runnable sendNotificationCallback;
	
	private Object lock;
	private MainWindowController controller;
	
	public PoliceControlThread(String client, Runnable callback, Object lock, MainWindowController controller) {
		this.client = client;
		this.sendNotificationCallback = callback;
		this.lock = lock;
		this.controller = controller;
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
				String personID = service.getNextPerson(client);
				if (personID == null) 
					sleep(sleepTime);
				else {
					Registry registry = LocateRegistry.getRegistry(Main.getWarrantsRMIPort());
					IWarrantsService rmi = (IWarrantsService) registry.lookup(Main.getWarrantsRMIName());
					if (rmi.isPersonWanted(personID)) {
						service.logPassenger(personID, false);
						logWarrant(personID);
						sendNotificationCallback.run();
					} else
						service.logPassenger(personID, true);
				}
			} catch (Exception ex) {
				Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
			}
		} 
	}

	private void logWarrant(String personID) {
		try {
			PassengersSOAPServiceServiceLocator locator = new PassengersSOAPServiceServiceLocator();
			PassengersSOAPService service = locator.getPassengersSOAPService();
			service.addOnWarantLog(personID);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

}
