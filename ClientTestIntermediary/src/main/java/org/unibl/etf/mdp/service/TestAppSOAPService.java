package org.unibl.etf.mdp.service;

import java.util.HashMap;
import java.util.LinkedList;

import org.unibl.etf.mdp.model.Document;

public class TestAppSOAPService {
	
	public boolean checkIfActive(String client) {
		HashMap<String, LinkedList<String>> policeControl = SOAPData.getPoliceControl();
		HashMap<String, LinkedList<Document>> borderControl = SOAPData.getBorderControl();
		return policeControl.containsKey(client + "-p") && borderControl.containsKey(client + "-b");
	}
	
	public void addPoliceControl(String client, String personID) {
		HashMap<String, LinkedList<String>> policeControl = SOAPData.getPoliceControl();
		if (checkIfActive(client)) {
			synchronized (policeControl) {
				policeControl.get(client + "-p").addLast(personID); 
			}
		}
	}
	
	public void addBorderControl(String client, Document document) {
		HashMap<String, LinkedList<Document>> borderControl = SOAPData.getBorderControl();
		if (checkIfActive(client)) {
			synchronized (borderControl) {
				borderControl.get(client + "-b").addLast(document); 
			}
		}
	}
	
	public int checkIfPassed(String personID) {
		HashMap<String, Boolean> passed = SOAPData.getPassedPassengers();
		if (passed.containsKey(personID)) {
			int retValue = passed.get(personID) ? 2 : 1;
			synchronized (passed) {
				passed.remove(personID);
			}
			return retValue;
		} else 
			return 0;
	}
}
