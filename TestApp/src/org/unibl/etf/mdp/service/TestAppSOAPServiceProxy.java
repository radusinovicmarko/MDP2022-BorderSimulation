package org.unibl.etf.mdp.service;

public class TestAppSOAPServiceProxy implements org.unibl.etf.mdp.service.TestAppSOAPService {
  private String _endpoint = null;
  private org.unibl.etf.mdp.service.TestAppSOAPService testAppSOAPService = null;
  
  public TestAppSOAPServiceProxy() {
    _initTestAppSOAPServiceProxy();
  }
  
  public TestAppSOAPServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initTestAppSOAPServiceProxy();
  }
  
  private void _initTestAppSOAPServiceProxy() {
    try {
      testAppSOAPService = (new org.unibl.etf.mdp.service.TestAppSOAPServiceServiceLocator()).getTestAppSOAPService();
      if (testAppSOAPService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)testAppSOAPService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)testAppSOAPService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (testAppSOAPService != null)
      ((javax.xml.rpc.Stub)testAppSOAPService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.unibl.etf.mdp.service.TestAppSOAPService getTestAppSOAPService() {
    if (testAppSOAPService == null)
      _initTestAppSOAPServiceProxy();
    return testAppSOAPService;
  }
  
  public boolean checkIfActive(java.lang.String client) throws java.rmi.RemoteException{
    if (testAppSOAPService == null)
      _initTestAppSOAPServiceProxy();
    return testAppSOAPService.checkIfActive(client);
  }
  
  public void addPoliceControl(java.lang.String client, java.lang.String personID) throws java.rmi.RemoteException{
    if (testAppSOAPService == null)
      _initTestAppSOAPServiceProxy();
    testAppSOAPService.addPoliceControl(client, personID);
  }
  
  public void addBorderControl(java.lang.String client, org.unibl.etf.mdp.model.Document document) throws java.rmi.RemoteException{
    if (testAppSOAPService == null)
      _initTestAppSOAPServiceProxy();
    testAppSOAPService.addBorderControl(client, document);
  }
  
  public int checkIfPassed(java.lang.String personID) throws java.rmi.RemoteException{
    if (testAppSOAPService == null)
      _initTestAppSOAPServiceProxy();
    return testAppSOAPService.checkIfPassed(personID);
  }
  
  
}