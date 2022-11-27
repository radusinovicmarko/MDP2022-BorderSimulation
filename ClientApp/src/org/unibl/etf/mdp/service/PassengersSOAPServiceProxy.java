package org.unibl.etf.mdp.service;

public class PassengersSOAPServiceProxy implements org.unibl.etf.mdp.service.PassengersSOAPService {
  private String _endpoint = null;
  private org.unibl.etf.mdp.service.PassengersSOAPService passengersSOAPService = null;
  
  public PassengersSOAPServiceProxy() {
    _initPassengersSOAPServiceProxy();
  }
  
  public PassengersSOAPServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initPassengersSOAPServiceProxy();
  }
  
  private void _initPassengersSOAPServiceProxy() {
    try {
      passengersSOAPService = (new org.unibl.etf.mdp.service.PassengersSOAPServiceServiceLocator()).getPassengersSOAPService();
      if (passengersSOAPService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)passengersSOAPService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)passengersSOAPService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (passengersSOAPService != null)
      ((javax.xml.rpc.Stub)passengersSOAPService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.unibl.etf.mdp.service.PassengersSOAPService getPassengersSOAPService() {
    if (passengersSOAPService == null)
      _initPassengersSOAPServiceProxy();
    return passengersSOAPService;
  }
  
  public void addLog(java.lang.String personID) throws java.rmi.RemoteException{
    if (passengersSOAPService == null)
      _initPassengersSOAPServiceProxy();
    passengersSOAPService.addLog(personID);
  }
  
  public byte[] getLog() throws java.rmi.RemoteException{
    if (passengersSOAPService == null)
      _initPassengersSOAPServiceProxy();
    return passengersSOAPService.getLog();
  }
  
  public void addOnWarantLog(java.lang.String personID) throws java.rmi.RemoteException{
    if (passengersSOAPService == null)
      _initPassengersSOAPServiceProxy();
    passengersSOAPService.addOnWarantLog(personID);
  }
  
  
}