package org.unibl.etf.mdp.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.mdp.control.BorderTerminalSerializationFactory;
import org.unibl.etf.mdp.model.BorderEntry;
import org.unibl.etf.mdp.model.BorderExit;
import org.unibl.etf.mdp.model.BorderTerminal;

public class BorderTerminalService {

	// TODO: Check for repetition on code add -> update
	public BorderTerminal addBorderTerminal(BorderTerminal terminal) {
		int serialId = 0;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BorderTerminalSerializationFactory.CONFIG_PATH));
			serialId = Integer.parseInt(prop.getProperty("SERIAL"));
			prop.setProperty("SERIAL", serialId + 1 + "");
			prop.store(new FileOutputStream(BorderTerminalSerializationFactory.CONFIG_PATH), null);
		} catch (IOException ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
			return null;
		}

		try {
			terminal.setId(serialId);
			BorderTerminalSerializationFactory.getInstance(terminal.getId()).serialize(terminal);
			terminal = updateBorderTerminal(terminal);
		} catch (Exception ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
		}
		return terminal;
	}

	public BorderTerminal updateBorderTerminal(BorderTerminal terminal) {
		int port = 0;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(BorderTerminalSerializationFactory.CONFIG_PATH));
			port = Integer.parseInt(prop.getProperty("PORT"));
		} catch (IOException ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
			return null;
		}
		
		BorderTerminal desTerm = null;
		try {
			desTerm = BorderTerminalSerializationFactory.getInstance(terminal.getId()).deserialize(terminal.getId());
		} catch (Exception ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
		}

		int serialIdCrossingPoint = desTerm.getSerialIDCrossingPoint();
		if (serialIdCrossingPoint == 0)
			serialIdCrossingPoint++;

		for (BorderEntry entry : terminal.getEntries()) {
			if (entry.getId() == 0) {
				entry.setId(serialIdCrossingPoint++);
				entry.setPoliceControlPort(port++);
				entry.setBorderControlPort(port++);
			}
		}
		for (BorderExit exit : terminal.getExits()) {
			if (exit.getId() == 0) {
				exit.setId(serialIdCrossingPoint++);
				exit.setPoliceControlPort(port++);
				exit.setBorderControlPort(port++);
			}
		}
		terminal.setSerialIDCrossingPoint(serialIdCrossingPoint);
		prop.setProperty("PORT", String.valueOf(port));

		try {
			prop.store(new FileOutputStream(BorderTerminalSerializationFactory.CONFIG_PATH), null);
			BorderTerminalSerializationFactory.getInstance(terminal.getId()).serialize(terminal);
		} catch (Exception ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
			return null;
		}

		return terminal;
	}

	public boolean deleteBorderTerminal(BorderTerminal terminal) {
		try {
			File file = new File(
					BorderTerminalSerializationFactory.TERMINALS_PATH + "terminal_" + terminal.getId() + ".bin");
			return file.delete();
		} catch (Exception ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
			return false;
		}
	}

	public BorderTerminal getBorderTerminal(int id) {
		try {
			BorderTerminal terminal = BorderTerminalSerializationFactory.getInstance(id).deserialize(id);
			return terminal;
		} catch (Exception ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
			return null;
		}
	}

}
