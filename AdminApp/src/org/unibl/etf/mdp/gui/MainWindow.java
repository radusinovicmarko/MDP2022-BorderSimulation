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

public class MainWindow extends Application {

	public static final String CONFIG_PATH = "./resources/config.properties";
	public static final String LOG_PATH = "./resources/admin.log";
	private static String DBBaseURL, FSBaseURL, CRBaseURL;
	private static FileHandler handler;

	static {
		try {
			handler = new FileHandler(LOG_PATH, true);
			Logger.getLogger(MainWindow.class.getName()).setUseParentHandlers(false);
			Logger.getLogger(MainWindow.class.getName()).addHandler(handler);
		} catch (IOException ex) {
			Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root, 600, 500);
		stage.setTitle("Administratorska aplikacija");
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();
	}

	public static void main(String[] args) {
		try {
			loadProperties();
			launch(args);
		} catch (Exception ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	public static String getDBBaseURL() {
		return DBBaseURL;
	}

	public static String getFSBaseURL() {
		return FSBaseURL;
	}

	public static String getCRBaseURL() {
		return CRBaseURL;
	}

	private static void loadProperties() throws Exception {
		Properties prop = new Properties();
		prop.load(new FileInputStream(CONFIG_PATH));
		DBBaseURL = prop.getProperty("DB_BASE_URL");
		FSBaseURL = prop.getProperty("FS_BASE_URL");
		CRBaseURL = prop.getProperty("CR_BASE_URL");
	}

}
