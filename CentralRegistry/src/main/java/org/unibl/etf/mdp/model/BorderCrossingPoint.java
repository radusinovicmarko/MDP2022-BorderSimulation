package org.unibl.etf.mdp.model;

import java.io.Serializable;

public abstract class BorderCrossingPoint implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private int policeControlPort, borderControlPort;
	
	public BorderCrossingPoint() {
		this(0, 0, 0);
	}

	public BorderCrossingPoint(int id, int policeControlPort, int borderControlPort) {
		super();
		this.id = id;
		this.policeControlPort = policeControlPort;
		this.borderControlPort = borderControlPort;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPoliceControlPort() {
		return policeControlPort;
	}

	public void setPoliceControlPort(int policeControlPort) {
		this.policeControlPort = policeControlPort;
	}

	public int getBorderControlPort() {
		return borderControlPort;
	}

	public void setBorderControlPort(int borderControlPort) {
		this.borderControlPort = borderControlPort;
	}
	
}
