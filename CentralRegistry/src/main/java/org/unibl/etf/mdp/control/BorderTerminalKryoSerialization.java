package org.unibl.etf.mdp.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.mdp.model.BorderTerminal;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class BorderTerminalKryoSerialization implements BorderTerminalSerializationInterface {

	@Override
	public boolean serialize(BorderTerminal terminal) {
		Kryo kryo = new Kryo();
		kryo.register(BorderTerminal.class);
		try (Output out = new Output(new FileOutputStream(new File(BorderTerminalSerializationFactory.TERMINALS_PATH + "terminal_" + terminal.getId() + ".bin")))) {
			kryo.writeClassAndObject(out, terminal);
			return true;
		} catch (Exception ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
			return false;
		}
		
	}

	@Override
	public BorderTerminal deserialize(int id) {
		Kryo kryo = new Kryo();
		kryo.register(BorderTerminal.class);
		try (Input in = new Input(new FileInputStream(new File(BorderTerminalSerializationFactory.TERMINALS_PATH + "terminal_" + id + ".bin")))) {
			BorderTerminal terminal = (BorderTerminal) kryo.readClassAndObject(in);
			return terminal;
		} catch (Exception ex) {
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).log(Level.SEVERE,
					ex.fillInStackTrace().toString());
			return null;
		}
	}

}
