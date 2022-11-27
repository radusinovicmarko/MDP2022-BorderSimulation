package org.unibl.etf.mdp.model;

public class BorderEntry extends BorderCrossingPoint {

	private static final long serialVersionUID = 1L;
	
	public BorderEntry() {
		super();
	}

	public BorderEntry(int id, int policeControlPort, int borderControlPort) {
		super(id, policeControlPort, borderControlPort);
	}

}
