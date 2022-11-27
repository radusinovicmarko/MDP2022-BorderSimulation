/**
 * TestAppSOAPService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.mdp.service;

public interface TestAppSOAPService extends java.rmi.Remote {
    public int checkIfPassed(java.lang.String personID) throws java.rmi.RemoteException;
    public boolean checkIfActive(java.lang.String client) throws java.rmi.RemoteException;
    public void addPoliceControl(java.lang.String client, java.lang.String personID) throws java.rmi.RemoteException;
    public void addBorderControl(java.lang.String client, org.unibl.etf.mdp.model.Document document) throws java.rmi.RemoteException;
}
