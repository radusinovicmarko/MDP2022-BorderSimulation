package org.unibl.etf.mdp.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IFileTrasnferService extends Remote {

	boolean send(String personId, byte[] input) throws RemoteException;
	
}
