package org.unibl.etf.mdp.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IWarrantsService extends Remote {
	
	boolean isPersonWanted(String personID) throws RemoteException;

}
