package org.unibl.etf.mdp.service;

public class BorderTerminalServiceProxy implements org.unibl.etf.mdp.service.BorderTerminalService {
  private String _endpoint = null;
  private org.unibl.etf.mdp.service.BorderTerminalService borderTerminalService = null;
  
  public BorderTerminalServiceProxy() {
    _initBorderTerminalServiceProxy();
  }
  
  public BorderTerminalServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initBorderTerminalServiceProxy();
  }
  
  private void _initBorderTerminalServiceProxy() {
    try {
      borderTerminalService = (new org.unibl.etf.mdp.service.BorderTerminalServiceServiceLocator()).getBorderTerminalService();
      if (borderTerminalService != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)borderTerminalService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)borderTerminalService)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (borderTerminalService != null)
      ((javax.xml.rpc.Stub)borderTerminalService)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public org.unibl.etf.mdp.service.BorderTerminalService getBorderTerminalService() {
    if (borderTerminalService == null)
      _initBorderTerminalServiceProxy();
    return borderTerminalService;
  }
  
  public org.unibl.etf.mdp.model.BorderTerminal addBorderTerminal(org.unibl.etf.mdp.model.BorderTerminal terminal) throws java.rmi.RemoteException{
    if (borderTerminalService == null)
      _initBorderTerminalServiceProxy();
    return borderTerminalService.addBorderTerminal(terminal);
  }
  
  public org.unibl.etf.mdp.model.BorderTerminal getBorderTerminal(int id) throws java.rmi.RemoteException{
    if (borderTerminalService == null)
      _initBorderTerminalServiceProxy();
    return borderTerminalService.getBorderTerminal(id);
  }
  
  public boolean deleteBorderTerminal(org.unibl.etf.mdp.model.BorderTerminal terminal) throws java.rmi.RemoteException{
    if (borderTerminalService == null)
      _initBorderTerminalServiceProxy();
    return borderTerminalService.deleteBorderTerminal(terminal);
  }
  
  public org.unibl.etf.mdp.model.BorderTerminal updateBorderTerminal(org.unibl.etf.mdp.model.BorderTerminal terminal) throws java.rmi.RemoteException{
    if (borderTerminalService == null)
      _initBorderTerminalServiceProxy();
    return borderTerminalService.updateBorderTerminal(terminal);
  }
  
  
}