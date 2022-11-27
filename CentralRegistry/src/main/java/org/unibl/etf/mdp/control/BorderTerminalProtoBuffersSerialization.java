package org.unibl.etf.mdp.control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.mdp.model.BorderEntry;
import org.unibl.etf.mdp.model.BorderExit;
import org.unibl.etf.mdp.model.BorderTerminal;
import org.unibl.etf.mdp.model.protobuf.BorderTerminalProtos;
import org.unibl.etf.mdp.model.protobuf.BorderTerminalProtos.BorderEntryWrapper;
import org.unibl.etf.mdp.model.protobuf.BorderTerminalProtos.BorderExitWrapper;

public class BorderTerminalProtoBuffersSerialization implements BorderTerminalSerializationInterface {

	@Override
	public boolean serialize(BorderTerminal terminal) {
		ArrayList<BorderEntryWrapper> entries = new ArrayList<>();
		ArrayList<BorderExitWrapper> exits = new ArrayList<>();
		
		for (BorderEntry entry : terminal.getEntries())
			entries.add(BorderTerminalProtos.BorderEntryWrapper.newBuilder().setId(entry.getId())
					.setBorderPort(entry.getBorderControlPort()).setPolicePort(entry.getPoliceControlPort()).build());
		
		for (BorderExit exit : terminal.getExits())
			exits.add(BorderTerminalProtos.BorderExitWrapper.newBuilder().setId(exit.getId())
					.setBorderPort(exit.getBorderControlPort()).setPolicePort(exit.getPoliceControlPort()).build());
		
		BorderTerminalProtos.BorderTerminalWrapper wrapper = BorderTerminalProtos.BorderTerminalWrapper.newBuilder()
				.setId(terminal.getId()).setName(terminal.getName()).setSerialID(terminal.getSerialIDCrossingPoint())
				.addAllEntries(entries).addAllExits(exits).build();

		try (FileOutputStream fos = new FileOutputStream(
				BorderTerminalSerializationFactory.TERMINALS_PATH + "terminal_" + terminal.getId() + ".bin")) {
			wrapper.writeTo(fos);
		} catch (IOException ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
			return false;
		}
		return true;
	}

	@Override
	public BorderTerminal deserialize(int id) {
		BorderTerminalProtos.BorderTerminalWrapper wrapper = null;
		try (FileInputStream fis = new FileInputStream(
				BorderTerminalSerializationFactory.TERMINALS_PATH + "terminal_" + id + ".bin")) {
			wrapper = BorderTerminalProtos.BorderTerminalWrapper.newBuilder().mergeFrom(fis).build();
		} catch (IOException ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
			return null;
		}
		ArrayList<BorderEntry> entries = new ArrayList<>();
		ArrayList<BorderExit> exits = new ArrayList<>();
		for (BorderEntryWrapper entryWrapper : wrapper.getEntriesList())
			entries.add(
					new BorderEntry(entryWrapper.getId(), entryWrapper.getPolicePort(), entryWrapper.getBorderPort()));

		for (BorderExitWrapper exitWrapper : wrapper.getExitsList())
			exits.add(new BorderExit(exitWrapper.getId(), exitWrapper.getPolicePort(), exitWrapper.getBorderPort()));
		BorderTerminal terminal = new BorderTerminal(wrapper.getName(), wrapper.getId(),
				entries.toArray(new BorderEntry[entries.size()]), exits.toArray(new BorderExit[exits.size()]));
		terminal.setSerialIDCrossingPoint(wrapper.getSerialID());
		return terminal;
	}

}
