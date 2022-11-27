package org.unibl.etf.mdp.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class BorderTerminal implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private int id;
	private int serialIDCrossingPoint = 1;
	
	private BorderEntry[] entries;
	private BorderExit[] exits;
	
	public BorderTerminal() {
		this("N/A", 1, new BorderEntry[0], new BorderExit[0]);
	}

	public BorderTerminal(String name, int id, BorderEntry[] entries, BorderExit[] exits) {
		super();
		this.name = name;
		this.id = id;
		this.entries = entries;
		this.exits = exits;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BorderEntry[] getEntries() {
		return entries;
	}

	public void setEntries(BorderEntry[] entries) {
		this.entries = entries;
	}

	public BorderExit[] getExits() {
		return exits;
	}

	public void setExits(BorderExit[] exits) {
		this.exits = exits;
	}
	
	public boolean addEntry(BorderEntry entry) {
		List<BorderEntry> entries = Arrays.asList(this.entries);
		for (BorderEntry item : entries) 
			if (item.getId() == entry.getId())
				return false;
		entries.add(entry);
		this.entries = entries.toArray(new BorderEntry[entries.size()]);
		return true;
	}

	public boolean addExit(BorderExit exit) {
		List<BorderExit> exits = Arrays.asList(this.exits);
		for (BorderExit item : exits) 
			if (item.getId() == exit.getId())
				return false;
		exits.add(exit);
		this.exits = exits.toArray(new BorderExit[exits.size()]);
		return true;
	}

	public int getSerialIDCrossingPoint() {
		return serialIDCrossingPoint;
	}

	public void setSerialIDCrossingPoint(int serialIDCrossingPoint) {
		this.serialIDCrossingPoint = serialIDCrossingPoint;
	}
	
}
