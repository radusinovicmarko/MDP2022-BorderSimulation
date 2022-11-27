package org.unibl.etf.mdp.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.unibl.etf.mdp.model.User;
import org.unibl.etf.mdp.utils.GUIUtils;
import org.unibl.etf.mdp.utils.RESTUtils;

import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

public class LoginController {
	
	
	@FXML
	private TextField usernameTF;
	@FXML
	private PasswordField passwordPF;
	@FXML
	private Button loginBtn;
	
	private boolean policeControl = false;
	private int terminalID, crossingPointID;

	public LoginController(boolean policeControl, int terminalID, int crossingPointID) {
		this.policeControl = policeControl;
		this.terminalID = terminalID;
		this.crossingPointID = crossingPointID;
	}

	// Event Listener on Button[#loginBtn].onAction
	@FXML
	public void login(ActionEvent event) {
		Stage s = (Stage) loginBtn.getScene().getWindow();
		if (usernameTF.getText().isEmpty() || passwordPF.getText().isEmpty()) {
			GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Niste unijeli sve parametre.");
			return;
		}
		try {
			String username = usernameTF.getText();
			JSONObject responseBody = RESTUtils.jsonObjectHttpRequest(Main.getDBBaseURL() + "/" + username);
			if (responseBody == null) {
				GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Greška.");
				return;
			}
			User userEntered = new User(username, passwordPF.getText());
			User userFromJson = new User(responseBody.getString("username"), responseBody.getString("password"));
			if (!userEntered.getPassword().equals(userFromJson.getPassword())) {
				GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Nevalidni kredencijali");
				return;
			}
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainWindow.fxml"));
			loader.setControllerFactory(c -> new MainWindowController(policeControl, terminalID, crossingPointID, username));
			Parent root = loader.load();
			Scene scene = new Scene(root, 600, 600);
			Stage newStage = new Stage();
			newStage.setResizable(false);
			newStage.setTitle("Klijentska aplikacija");
			newStage.setScene(scene);
			Stage stage = (Stage) loginBtn.getScene().getWindow();
			stage.hide();
			newStage.show();
		} catch (Exception ex) {
			GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
		
	}
}
