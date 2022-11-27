package org.unibl.etf.mdp.service;

import java.util.HashMap;
import java.util.LinkedList;

import org.unibl.etf.mdp.model.Document;

public class ClientAppSOAPService {
	
	public boolean checkIfActive(String client) {
		HashMap<String, LinkedList<String>> policeControl = SOAPData.getPoliceControl();
		HashMap<String, LinkedList<Document>> borderControl = SOAPData.getBorderControl();
		return policeControl.containsKey(client) || borderControl.containsKey(client);
 	}

	public void login(String clientName) {
		HashMap<String, LinkedList<String>> policeControl = SOAPData.getPoliceControl();
		HashMap<String, LinkedList<Document>> borderControl = SOAPData.getBorderControl();
		if (clientName.endsWith("p") && !policeControl.containsKey(clientName)) {
			synchronized (policeControl) {
				policeControl.put(clientName, new LinkedList<>());
			}
		} else if (clientName.endsWith("b") && !borderControl.containsKey(clientName)) {
			synchronized (borderControl) {
				borderControl.put(clientName, new LinkedList<>());
			}
		}
	}

	public void logout(String clientName) {
		HashMap<String, LinkedList<String>> policeControl = SOAPData.getPoliceControl();
		HashMap<String, LinkedList<Document>> borderControl = SOAPData.getBorderControl();
		if (policeControl.containsKey(clientName)) {
			synchronized (policeControl) {
				policeControl.remove(clientName);
			}
		} else if (borderControl.containsKey(clientName)) {
			synchronized (borderControl) {
				borderControl.remove(clientName);
			}
		}
	}

	public String getNextPerson(String client) {
		HashMap<String, LinkedList<String>> policeControl = SOAPData.getPoliceControl();
		synchronized (policeControl) {
			if (policeControl.containsKey(client))
				return policeControl.get(client).pollFirst();
			else
				return null;
		}
	}

	public Document getNextDocument(String client) {
		HashMap<String, LinkedList<Document>> borderControl = SOAPData.getBorderControl();
		synchronized (borderControl) {
			if (borderControl.containsKey(client))
				return borderControl.get(client).pollFirst();
			else
				return null;
		}
	}
	
	public void logPassenger(String personID, boolean passed) {
		HashMap<String, Boolean> passedPassengers = SOAPData.getPassedPassengers();
		synchronized (passedPassengers) {
			passedPassengers.put(personID, passed);
		}
	}

}
