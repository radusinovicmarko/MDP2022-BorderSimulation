package org.unibl.etf.mdp.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class GUIUtils {

	public static void showAlert(Stage stage, AlertType type, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle("Obavještenje");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

}
