package org.unibl.etf.mdp.control;

import java.io.IOException;
import java.util.HashMap;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BorderTerminalSerializationFactory {
	
	private static HashMap<Integer, Class<?>> serializationTypes = new HashMap<>();
	
	public static final int NUMBER_OF_TYPES = 4;
	
	public static final String ROOT = "./CentralRegistry/";
	public static final String RESOURCES_PATH = ROOT + "resources/";
	public static final String TERMINALS_PATH = RESOURCES_PATH + "terminals/";
	public static final String CONFIG_PATH = RESOURCES_PATH + "config.properties";
	public static final String LOG_PATH = RESOURCES_PATH + "central_registry.log";
	private static FileHandler handler;

	static {
		try {
			handler = new FileHandler(LOG_PATH, true);
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).setUseParentHandlers(false);
			Logger.getLogger(BorderTerminalSerializationFactory.class.getName()).addHandler(handler);
		} catch (IOException ex) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}
	
	static {
		serializationTypes.put(0, BorderTerminalJavaSerialization.class);
		serializationTypes.put(1, BorderTerminalKryoSerialization.class);
		serializationTypes.put(2, BorderTerminalProtoBuffersSerialization.class);
		serializationTypes.put(3, BorderTerminalSmileSerialization.class);
	}
	
	public static BorderTerminalSerializationInterface getInstance(int id) throws Exception {
		return (BorderTerminalSerializationInterface) serializationTypes.get((id % NUMBER_OF_TYPES)).getConstructor().newInstance();
	}

}
