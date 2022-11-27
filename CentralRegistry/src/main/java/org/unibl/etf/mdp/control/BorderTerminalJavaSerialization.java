package org.unibl.etf.mdp.control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.mdp.model.BorderTerminal;

public class BorderTerminalJavaSerialization implements BorderTerminalSerializationInterface {

	@Override
	public boolean serialize(BorderTerminal terminal) {

		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				BorderTerminalSerializationFactory.TERMINALS_PATH + "terminal_" + terminal.getId() + ".bin"))) {
			oos.writeObject(terminal);
		} catch (IOException ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
			return false;
		}

		return true;

	}

	@Override
	public BorderTerminal deserialize(int id) {
		BorderTerminal terminal = null;
		try (ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream(BorderTerminalSerializationFactory.TERMINALS_PATH + "terminal_" + id + ".bin"))) {
			terminal = (BorderTerminal) ois.readObject();
		} catch (IOException | ClassNotFoundException ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
		}
		return terminal;
	}

}
