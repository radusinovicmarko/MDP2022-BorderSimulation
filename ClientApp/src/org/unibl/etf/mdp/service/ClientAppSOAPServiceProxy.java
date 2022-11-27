package org.unibl.etf.mdp.service;

public class ClientAppSOAPServiceProxy implements org.unibl.etf.mdp.service.ClientAppSOAPService {
  private String _endpoint = null;
  private org.unibl.etf.mdp.service.ClientAppSOAPService clientAppSOAPService = null;
  
  public ClientAppSOAPServiceProxy() {
    _initClientAppSOAPServiceProxy();
  }
  
  public ClientAppSOAPServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initClientAppSOAPServiceProxy();
  }
  
  private void _initClientAppSOAPServiceProxy() {
    try {
      clientAppSOAPService = (new org.unibl.etf.mdp.service.ClientAppSOAPServiceServiceLocator()).getClientAppSOAPService();
      if (clientAppSOAPService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)clientAppSOAPService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)clientAppSOAPService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (clientAppSOAPService != null)
      ((javax.xml.rpc.Stub)clientAppSOAPService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.unibl.etf.mdp.service.ClientAppSOAPService getClientAppSOAPService() {
    if (clientAppSOAPService == null)
      _initClientAppSOAPServiceProxy();
    return clientAppSOAPService;
  }
  
  public void login(java.lang.String clientName) throws java.rmi.RemoteException{
    if (clientAppSOAPService == null)
      _initClientAppSOAPServiceProxy();
    clientAppSOAPService.login(clientName);
  }
  
  public void logout(java.lang.String clientName) throws java.rmi.RemoteException{
    if (clientAppSOAPService == null)
      _initClientAppSOAPServiceProxy();
    clientAppSOAPService.logout(clientName);
  }
  
  public boolean checkIfActive(java.lang.String client) throws java.rmi.RemoteException{
    if (clientAppSOAPService == null)
      _initClientAppSOAPServiceProxy();
    return clientAppSOAPService.checkIfActive(client);
  }
  
  public java.lang.String getNextPerson(java.lang.String client) throws java.rmi.RemoteException{
    if (clientAppSOAPService == null)
      _initClientAppSOAPServiceProxy();
    return clientAppSOAPService.getNextPerson(client);
  }
  
  public void logPassenger(java.lang.String personID, boolean passed) throws java.rmi.RemoteException{
    if (clientAppSOAPService == null)
      _initClientAppSOAPServiceProxy();
    clientAppSOAPService.logPassenger(personID, passed);
  }
  
  public org.unibl.etf.mdp.model.Document getNextDocument(java.lang.String client) throws java.rmi.RemoteException{
    if (clientAppSOAPService == null)
      _initClientAppSOAPServiceProxy();
    return clientAppSOAPService.getNextDocument(client);
  }
  
  
}