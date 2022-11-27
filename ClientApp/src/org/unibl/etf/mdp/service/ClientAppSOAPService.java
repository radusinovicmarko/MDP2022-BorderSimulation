/**
 * ClientAppSOAPService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.mdp.service;

public interface ClientAppSOAPService extends java.rmi.Remote {
    public void login(java.lang.String clientName) throws java.rmi.RemoteException;
    public void logout(java.lang.String clientName) throws java.rmi.RemoteException;
    public boolean checkIfActive(java.lang.String client) throws java.rmi.RemoteException;
    public java.lang.String getNextPerson(java.lang.String client) throws java.rmi.RemoteException;
    public void logPassenger(java.lang.String personID, boolean passed) throws java.rmi.RemoteException;
    public org.unibl.etf.mdp.model.Document getNextDocument(java.lang.String client) throws java.rmi.RemoteException;
}
