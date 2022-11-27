package org.unibl.etf.mdp.model;

public class Document implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String personID;
	private byte[] document;

	public Document() {
		super();
	}

	public Document(String personID, byte[] document) {
		super();
		this.personID = personID;
		this.document = document;
	}

	public String getPersonID() {
		return personID;
	}

	public void setPersonID(String personID) {
		this.personID = personID;
	}

	public byte[] getDocument() {
		return document;
	}

	public void setDocument(byte[] document) {
		this.document = document;
	}

}
