package org.unibl.etf.mdp.gui;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static final String LOG_PATH = "./resources/test.log";
	
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
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Startup.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root, 300, 200);
		stage.setTitle("Testna aplikacija");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
