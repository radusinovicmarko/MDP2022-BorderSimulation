package org.unibl.etf.mdp.gui;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	public static final String CONFIG_PATH = "./resources/config.properties";
	public static final String LOG_PATH = "./resources/client.log";

	private static String policyPath, DBBaseURL, filesRMIName, warrantsRMIName, keystorePath, keystorePassword, host;

	private static int filesRMIPort, warrantsRMIPort, senderPort, receiverPort;
	
	private static FileHandler handler;

	static {
		try {
			handler = new FileHandler(LOG_PATH, true);
			Logger.getLogger(Main.class.getName()).setUseParentHandlers(false);
			Logger.getLogger(Main.class.getName()).addHandler(handler);
		} catch (IOException e) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, e.fillInStackTrace().toString());
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		System.setProperty("java.security.policy", policyPath);
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Startup.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root, 400, 300);
		stage.setTitle("Klijentska aplikacija");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		try {
			loadProperties();
			launch(args);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	public static String getPolicyPath() {
		return policyPath;
	}

	public static String getDBBaseURL() {
		return DBBaseURL;
	}

	public static int getFilesRMIPort() {
		return filesRMIPort;
	}

	public static String getFilesRMIName() {
		return filesRMIName;
	}

	public static String getWarrantsRMIName() {
		return warrantsRMIName;
	}

	public static int getWarrantsRMIPort() {
		return warrantsRMIPort;
	}

	public static String getKeystorePath() {
		return keystorePath;
	}

	public static String getKeystorePassword() {
		return keystorePassword;
	}

	public static int getSenderPort() {
		return senderPort;
	}

	public static int getReceiverPort() {
		return receiverPort;
	}

	public static String getHost() {
		return host;
	}

	private static void loadProperties() throws Exception {
		Properties prop = new Properties();
		prop.load(new FileInputStream(CONFIG_PATH));
		policyPath = prop.getProperty("POLICY_PATH");
		DBBaseURL = prop.getProperty("DB_BASE_URL");
		filesRMIPort = Integer.parseInt(prop.getProperty("FILES_RMI_PORT"));
		filesRMIName = prop.getProperty("FILES_RMI_NAME");
		warrantsRMIPort = Integer.parseInt(prop.getProperty("WARRANTS_RMI_PORT"));
		warrantsRMIName = prop.getProperty("WARRANTS_RMI_NAME");
		keystorePath = prop.getProperty("KEYSTORE_PATH");
		keystorePassword = prop.getProperty("KEYSTORE_PASSWORD");
		senderPort = Integer.parseInt(prop.getProperty("SENDER_PORT"));
		receiverPort = Integer.parseInt(prop.getProperty("RECEIVER_PORT"));
		host = prop.getProperty("HOST");
	}
}
