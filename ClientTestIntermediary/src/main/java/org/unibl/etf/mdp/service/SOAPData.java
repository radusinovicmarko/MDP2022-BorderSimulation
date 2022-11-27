package org.unibl.etf.mdp.service;

import java.util.HashMap;
import java.util.LinkedList;

import org.unibl.etf.mdp.model.Document;

public class SOAPData {

	private static HashMap<String, LinkedList<String>> policeControl = new HashMap<>();
	private static HashMap<String, LinkedList<Document>> borderControl = new HashMap<>();
	private static HashMap<String, Boolean> passedPassengers = new HashMap<>();

	public static HashMap<String, LinkedList<Document>> getBorderControl() {
		return borderControl;
	}

	public static HashMap<String, LinkedList<String>> getPoliceControl() {
		return policeControl;
	}

	public static HashMap<String, Boolean> getPassedPassengers() {
		return passedPassengers;
	}

}
