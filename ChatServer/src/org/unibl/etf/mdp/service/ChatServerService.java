package org.unibl.etf.mdp.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServerService {

	public static final String CONFIG_PATH = "./resources/config.properties";
	public static final String LOG_PATH = "./resources/chat.log";

	private static final HashMap<String, ArrayList<String>> messages = new HashMap<>();
	
	private static final HashSet<String> currentlyClosedTerminals = new HashSet<String>();

	private static String keystorePath;
	private static String keystorePassword;
	private static int senderPort, receiverPort;

	private static FileHandler handler;

	static {
		try {
			handler = new FileHandler(LOG_PATH, true);
			Logger.getLogger(ChatServerService.class.getName()).setUseParentHandlers(false);
			Logger.getLogger(ChatServerService.class.getName()).addHandler(handler);
		} catch (IOException ex) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	public static void main(String[] args) {
		try {
			loadProperties();
			System.setProperty("javax.net.ssl.keyStore", keystorePath);
			System.setProperty("javax.net.ssl.keyStorePassword", keystorePassword);

			new ChatServerSenderService(senderPort);
			new ChatServerReceiverService(receiverPort);
		} catch (Exception ex) {
			Logger.getLogger(ChatServerService.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	public static HashMap<String, ArrayList<String>> getMessages() {
		return messages;
	}

	public static HashSet<String> getCurrentlyclosedterminals() {
		return currentlyClosedTerminals;
	}

	private static void loadProperties() throws Exception {
		Properties prop = new Properties();
		prop.load(new FileInputStream(CONFIG_PATH));
		keystorePath = prop.getProperty("KEYSTORE_PATH");
		keystorePassword = prop.getProperty("KEYSTORE_PASSWORD");
		senderPort = Integer.parseInt(prop.getProperty("SENDER_PORT"));
		receiverPort = Integer.parseInt(prop.getProperty("RECEIVER_PORT"));
	}

}
