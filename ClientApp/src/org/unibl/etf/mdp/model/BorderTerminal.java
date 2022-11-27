/**
 * BorderTerminal.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.unibl.etf.mdp.model;

public class BorderTerminal  implements java.io.Serializable {
    private org.unibl.etf.mdp.model.BorderEntry[] entries;

    private org.unibl.etf.mdp.model.BorderExit[] exits;

    private int id;

    private java.lang.String name;

    private int serialIDCrossingPoint;

    public BorderTerminal() {
    }

    public BorderTerminal(
           org.unibl.etf.mdp.model.BorderEntry[] entries,
           org.unibl.etf.mdp.model.BorderExit[] exits,
           int id,
           java.lang.String name,
           int serialIDCrossingPoint) {
           this.entries = entries;
           this.exits = exits;
           this.id = id;
           this.name = name;
           this.serialIDCrossingPoint = serialIDCrossingPoint;
    }


    /**
     * Gets the entries value for this BorderTerminal.
     * 
     * @return entries
     */
    public org.unibl.etf.mdp.model.BorderEntry[] getEntries() {
        return entries;
    }


    /**
     * Sets the entries value for this BorderTerminal.
     * 
     * @param entries
     */
    public void setEntries(org.unibl.etf.mdp.model.BorderEntry[] entries) {
        this.entries = entries;
    }


    /**
     * Gets the exits value for this BorderTerminal.
     * 
     * @return exits
     */
    public org.unibl.etf.mdp.model.BorderExit[] getExits() {
        return exits;
    }


    /**
     * Sets the exits value for this BorderTerminal.
     * 
     * @param exits
     */
    public void setExits(org.unibl.etf.mdp.model.BorderExit[] exits) {
        this.exits = exits;
    }


    /**
     * Gets the id value for this BorderTerminal.
     * 
     * @return id
     */
    public int getId() {
        return id;
    }


    /**
     * Sets the id value for this BorderTerminal.
     * 
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets the name value for this BorderTerminal.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this BorderTerminal.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the serialIDCrossingPoint value for this BorderTerminal.
     * 
     * @return serialIDCrossingPoint
     */
    public int getSerialIDCrossingPoint() {
        return serialIDCrossingPoint;
    }


    /**
     * Sets the serialIDCrossingPoint value for this BorderTerminal.
     * 
     * @param serialIDCrossingPoint
     */
    public void setSerialIDCrossingPoint(int serialIDCrossingPoint) {
        this.serialIDCrossingPoint = serialIDCrossingPoint;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BorderTerminal)) return false;
        BorderTerminal other = (BorderTerminal) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.entries==null && other.getEntries()==null) || 
             (this.entries!=null &&
              java.util.Arrays.equals(this.entries, other.getEntries()))) &&
            ((this.exits==null && other.getExits()==null) || 
             (this.exits!=null &&
              java.util.Arrays.equals(this.exits, other.getExits()))) &&
            this.id == other.getId() &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            this.serialIDCrossingPoint == other.getSerialIDCrossingPoint();
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
        if (getEntries() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getEntries());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getEntries(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        if (getExits() != null) {
            for (int i=0;
                 i<java.lang.reflect.Array.getLength(getExits());
                 i++) {
                java.lang.Object obj = java.lang.reflect.Array.get(getExits(), i);
                if (obj != null &&
                    !obj.getClass().isArray()) {
                    _hashCode += obj.hashCode();
                }
            }
        }
        _hashCode += getId();
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        _hashCode += getSerialIDCrossingPoint();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BorderTerminal.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "BorderTerminal"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("entries");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "entries"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "BorderEntry"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://service.mdp.etf.unibl.org", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("exits");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "exits"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "BorderExit"));
        elemField.setNillable(true);
        elemField.setItemQName(new javax.xml.namespace.QName("http://service.mdp.etf.unibl.org", "item"));
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serialIDCrossingPoint");
        elemField.setXmlName(new javax.xml.namespace.QName("http://model.mdp.etf.unibl.org", "serialIDCrossingPoint"));
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
