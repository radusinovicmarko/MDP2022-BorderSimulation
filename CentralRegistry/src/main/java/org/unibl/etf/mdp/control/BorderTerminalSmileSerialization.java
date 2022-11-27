package org.unibl.etf.mdp.control;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.mdp.model.BorderTerminal;

import com.fasterxml.jackson.dataformat.smile.databind.SmileMapper;

public class BorderTerminalSmileSerialization implements BorderTerminalSerializationInterface {

	@Override
	public boolean serialize(BorderTerminal terminal) {
		try {
			SmileMapper mapper = new SmileMapper();
			byte[] terminalAsBytes = mapper.writeValueAsBytes(terminal);
			FileOutputStream fos = new FileOutputStream(
					BorderTerminalSerializationFactory.TERMINALS_PATH + "terminal_" + terminal.getId() + ".bin");
			fos.write(terminalAsBytes);
			fos.close();
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
		try (FileInputStream fis = new FileInputStream(
				BorderTerminalSerializationFactory.TERMINALS_PATH + "terminal_" + id + ".bin")) {
			SmileMapper mapper = new SmileMapper();
			terminal = mapper.readValue(fis.readAllBytes(), BorderTerminal.class);
		} catch (IOException ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
			return null;
		}
		return terminal;
	}

}
