/**
 * BorderCrossingPoint.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.mdp.model;

public abstract class BorderCrossingPoint  implements java.io.Serializable {
    private int borderControlPort;

    private int id;

    private int policeControlPort;

    public BorderCrossingPoint() {
    }

    public BorderCrossingPoint(
           int borderControlPort,
           int id,
           int policeControlPort) {
           this.borderControlPort = borderControlPort;
           this.id = id;
           this.policeControlPort = policeControlPort;
    }


    /**
     * Gets the borderControlPort value for this BorderCrossingPoint.
     * 
     * @return borderControlPort
     */
    public int getBorderControlPort() {
        return borderControlPort;
    }


    /**
     * Sets the borderControlPort value for this BorderCrossingPoint.
     * 
     * @param borderControlPort
     */
    public void setBorderControlPort(int borderControlPort) {
        this.borderControlPort = borderControlPort;
    }


    /**
     * Gets the id value for this BorderCrossingPoint.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this BorderCrossingPoint.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the policeControlPort value for this BorderCrossingPoint.
     * 
     * @return policeControlPort
     */
    public int getPoliceControlPort() {
        return policeControlPort;
    }


    /**
     * Sets the policeControlPort value for this BorderCrossingPoint.
     * 
     * @param policeControlPort
     */
    public void setPoliceControlPort(int policeControlPort) {
        this.policeControlPort = policeControlPort;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BorderCrossingPoint)) return false;
        BorderCrossingPoint other = (BorderCrossingPoint) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.borderControlPort == other.getBorderControlPort() &&
            this.id == other.getId() &&
            this.policeControlPort == other.getPoliceControlPort();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getBorderControlPort();
        _hashCode += getId();
        _hashCode += getPoliceControlPort();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BorderCrossingPoint.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "BorderCrossingPoint"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("borderControlPort");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "borderControlPort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policeControlPort");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "policeControlPort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
