/**
 * BorderTerminalServiceServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.mdp.service;

public class BorderTerminalServiceServiceLocator extends org.apache.axis.client.Service implements org.unibl.etf.mdp.service.BorderTerminalServiceService {

    public BorderTerminalServiceServiceLocator() {
    }


    public BorderTerminalServiceServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public BorderTerminalServiceServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for BorderTerminalService
    private java.lang.String BorderTerminalService_address = "http://localhost:8080/CentralRegistry/services/BorderTerminalService";

    public java.lang.String getBorderTerminalServiceAddress() {
        return BorderTerminalService_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String BorderTerminalServiceWSDDServiceName = "BorderTerminalService";

    public java.lang.String getBorderTerminalServiceWSDDServiceName() {
        return BorderTerminalServiceWSDDServiceName;
    }

    public void setBorderTerminalServiceWSDDServiceName(java.lang.String name) {
        BorderTerminalServiceWSDDServiceName = name;
    }

    public org.unibl.etf.mdp.service.BorderTerminalService getBorderTerminalService() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(BorderTerminalService_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getBorderTerminalService(endpoint);
    }

    public org.unibl.etf.mdp.service.BorderTerminalService getBorderTerminalService(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.unibl.etf.mdp.service.BorderTerminalServiceSoapBindingStub _stub = new org.unibl.etf.mdp.service.BorderTerminalServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getBorderTerminalServiceWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setBorderTerminalServiceEndpointAddress(java.lang.String address) {
        BorderTerminalService_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.unibl.etf.mdp.service.BorderTerminalService.class.isAssignableFrom(serviceEndpointInterface)) {
                org.unibl.etf.mdp.service.BorderTerminalServiceSoapBindingStub _stub = new org.unibl.etf.mdp.service.BorderTerminalServiceSoapBindingStub(new java.net.URL(BorderTerminalService_address), this);
                _stub.setPortName(getBorderTerminalServiceWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("BorderTerminalService".equals(inputPortName)) {
            return getBorderTerminalService();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://service.mdp.etf.unibl.org", "BorderTerminalServiceService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://service.mdp.etf.unibl.org", "BorderTerminalService"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("BorderTerminalService".equals(portName)) {
            setBorderTerminalServiceEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
