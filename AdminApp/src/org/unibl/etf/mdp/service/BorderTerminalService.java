/**
 * BorderTerminalService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.mdp.service;

public interface BorderTerminalService extends java.rmi.Remote {
    public org.unibl.etf.mdp.model.BorderTerminal addBorderTerminal(org.unibl.etf.mdp.model.BorderTerminal terminal) throws java.rmi.RemoteException;
    public org.unibl.etf.mdp.model.BorderTerminal getBorderTerminal(int id) throws java.rmi.RemoteException;
    public boolean deleteBorderTerminal(org.unibl.etf.mdp.model.BorderTerminal terminal) throws java.rmi.RemoteException;
    public org.unibl.etf.mdp.model.BorderTerminal updateBorderTerminal(org.unibl.etf.mdp.model.BorderTerminal terminal) throws java.rmi.RemoteException;
}
