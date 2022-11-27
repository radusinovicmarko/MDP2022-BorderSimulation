package org.unibl.etf.mdp.gui;

import javafx.fxml.FXML;

import java.net.HttpURLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.unibl.etf.mdp.model.User;
import org.unibl.etf.mdp.utils.GUIUtils;
import org.unibl.etf.mdp.utils.RESTUtils;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChangePasswordController {
	@FXML
	private PasswordField oldPasswordPF;
	@FXML
	private PasswordField newPasswordPF;

	private String username;

	public ChangePasswordController(String username) {
		this.username = username;
	}

	// Event Listener on Button.onAction
	@FXML
	public void changePassword(ActionEvent event) {
		Stage stage = (Stage) newPasswordPF.getScene().getWindow();
		if (oldPasswordPF.getText().isEmpty() || newPasswordPF.getText().isEmpty()) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Niste unijeli sve parametre.");
			return;
		}
		User user = new User(username, oldPasswordPF.getText());
		try {
			JSONObject responseBody = RESTUtils.jsonObjectHttpRequest(Main.getDBBaseURL() + "/" + username);
			if (responseBody == null) {
				GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
				return;
			}

			User userFromJson = new User(responseBody.getString("username"), responseBody.getString("password"));
			if (!user.getPassword().equals(userFromJson.getPassword())) {
				GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Nevalidni kredencijali.");
				return;
			}
			user.setPassword(newPasswordPF.getText());
			int responseCode = RESTUtils.jsonHttpRequest(Main.getDBBaseURL(), "PUT", user);
			if (responseCode == HttpURLConnection.HTTP_NO_CONTENT)
				GUIUtils.showAlert(stage, AlertType.CONFIRMATION, "Informacija", "Uspješna promjena.");
			else
				GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
		} catch (Exception ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}
}
