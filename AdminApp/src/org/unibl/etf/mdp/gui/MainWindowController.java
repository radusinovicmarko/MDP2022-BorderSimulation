package org.unibl.etf.mdp.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.unibl.etf.mdp.model.BorderCrossingPoint;
import org.unibl.etf.mdp.model.BorderEntry;
import org.unibl.etf.mdp.model.BorderExit;
import org.unibl.etf.mdp.model.BorderTerminal;
import org.unibl.etf.mdp.model.User;
import org.unibl.etf.mdp.service.BorderTerminalService;
import org.unibl.etf.mdp.service.BorderTerminalServiceServiceLocator;
import org.unibl.etf.mdp.utils.GUIUtils;
import org.unibl.etf.mdp.utils.RESTUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javafx.event.ActionEvent;

import javafx.scene.control.PasswordField;

import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MainWindowController {

	@FXML
	private TextField newTerminalNameTF;
	@FXML
	private TextField numberOfEntriesTF;
	@FXML
	private TextField numberOfExitsTF;
	@FXML
	private Button addTerminalBtn;
	@FXML
	private TextField searchTerminalIDTF;
	@FXML
	private Button searchBtn;
	@FXML
	private TextField terminalIDTF;
	@FXML
	private TextField terminalNameTF;
	@FXML
	private VBox entriesVBox;
	@FXML
	private VBox exitsVBox;
	@FXML
	private Button deletePointBtn;
	@FXML
	private Button addEntryBtn;
	@FXML
	private Button addExitBtn;
	@FXML
	private ComboBox<Integer> pointsComboBox;
	@FXML
	private Button changeTerminalBtn;
	@FXML
	private Button deleteTerminalBtn;
	@FXML
	private TextField newUserTextField;
	@FXML
	private PasswordField newUserPassField;
	@FXML
	private Button addNewUserBtn;
	@FXML
	private TextField changeUserTextField;
	@FXML
	private PasswordField oldPasswordPassField;
	@FXML
	private PasswordField newPasswordPassField;
	@FXML
	private Button changeUserBtn;
	@FXML
	private TextField deleteUserTextField;
	@FXML
	private Button deleteUserBtn;
	@FXML
	private TextField personIDTF;
	@FXML
	private Button getBorderPapersBtn;
	@FXML
	private Button getWarrantsBtn;

	private ArrayList<BorderCrossingPoint> points = new ArrayList<>();

	// Event Listener on Button[#addTerminalBtn].onAction
	@FXML
	public void addTerminal(ActionEvent event) {
		Stage stage = (Stage) getWarrantsBtn.getScene().getWindow();
		if (newTerminalNameTF.getText().isEmpty() || numberOfEntriesTF.getText().isEmpty()
				|| numberOfExitsTF.getText().isEmpty()) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Niste unijeli sve parametre.");
			return;
		}
		try {
			BorderTerminalServiceServiceLocator locator = new BorderTerminalServiceServiceLocator();
			BorderTerminalService service = locator.getBorderTerminalService();
			BorderTerminal terminal = new BorderTerminal();
			terminal.setId(0);
			terminal.setName(newTerminalNameTF.getText());
			ArrayList<BorderEntry> entries = new ArrayList<>();
			ArrayList<BorderExit> exits = new ArrayList<>();

			int numberOfEntries = Integer.parseInt(numberOfEntriesTF.getText());
			int numberOfExits = Integer.parseInt(numberOfExitsTF.getText());

			for (int i = 0; i < numberOfEntries; i++)
				entries.add(new BorderEntry());
			for (int i = 0; i < numberOfExits; i++)
				exits.add(new BorderExit());

			terminal.setEntries(entries.toArray(new BorderEntry[entries.size()]));
			terminal.setExits(exits.toArray(new BorderExit[exits.size()]));

			terminal = service.addBorderTerminal(terminal);
			if (terminal != null)
				GUIUtils.showAlert(stage, AlertType.CONFIRMATION, "Informacija",
						"Uspješno dodano. Terminal ID : " + terminal.getId());
			else
				GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
		} catch (Exception ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	// Event Listener on Button[#searchBtn].onAction
	@FXML
	public void searchAction(ActionEvent event) {
		Stage stage = (Stage) getWarrantsBtn.getScene().getWindow();
		if (searchTerminalIDTF.getText().isEmpty()) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Niste unijeli sve parametre.");
			return;
		}
		try {
			int id = Integer.parseInt(searchTerminalIDTF.getText());
			BorderTerminalServiceServiceLocator locator = new BorderTerminalServiceServiceLocator();
			BorderTerminalService service = locator.getBorderTerminalService();
			BorderTerminal terminal = service.getBorderTerminal(id);
			showBorderTerminal(terminal);
			changeTerminalBtn.setDisable(false);
			deleteTerminalBtn.setDisable(false);
		} catch (Exception ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	private void showBorderTerminal(BorderTerminal terminal) {
		cleanUp();

		terminalIDTF.setText(String.valueOf(terminal.getId()));
		terminalNameTF.setText(terminal.getName());
		List<BorderEntry> entries = Arrays.asList(terminal.getEntries());
		List<BorderExit> exits = Arrays.asList(terminal.getExits());

		for (BorderEntry entry : entries)
			entriesVBox.getChildren().add(GUIUtils.createBorderCrossingPoint(entry, points));
		for (BorderExit exit : exits)
			exitsVBox.getChildren().add(GUIUtils.createBorderCrossingPoint(exit, points));

		deletePointBtn.setDisable(false);
		addEntryBtn.setDisable(false);
		addExitBtn.setDisable(false);
		updatePointsComboBox();
	}

	private void updatePointsComboBox() {
		pointsComboBox.getItems().clear();
		for (BorderCrossingPoint point : points)
			pointsComboBox.getItems().add(point.getId());
	}

	// Event Listener on Button[#changeTerminalBtn].onAction
	@FXML
	public void changeTerminal(ActionEvent event) {
		Stage stage = (Stage) getWarrantsBtn.getScene().getWindow();
		try {
			BorderTerminalServiceServiceLocator locator = new BorderTerminalServiceServiceLocator();
			BorderTerminalService service = locator.getBorderTerminalService();
			BorderTerminal terminal = new BorderTerminal();
			int id = Integer.parseInt(terminalIDTF.getText());
			terminal.setId(id);
			terminal.setName(terminalNameTF.getText());
			ArrayList<BorderEntry> entries = new ArrayList<>();
			ArrayList<BorderExit> exits = new ArrayList<>();

			for (BorderCrossingPoint point : points) {
				if (point instanceof BorderEntry)
					entries.add((BorderEntry) point);
				else
					exits.add((BorderExit) point);
			}

			terminal.setEntries(entries.toArray(new BorderEntry[entries.size()]));
			terminal.setExits(exits.toArray(new BorderExit[exits.size()]));

			terminal = service.updateBorderTerminal(terminal);
			if (terminal != null)
				GUIUtils.showAlert(stage, AlertType.CONFIRMATION, "Informacija", "Uspješna promjena.");
			else
				GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			cleanUp();
			searchAction(null);
		} catch (Exception ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	// Event Listener on Button[#deleteTerminalBtn].onAction
	@FXML
	public void deleteTerminal(ActionEvent event) {
		Stage stage = (Stage) getWarrantsBtn.getScene().getWindow();
		try {
			int id = Integer.parseInt(terminalIDTF.getText());
			BorderTerminalServiceServiceLocator locator = new BorderTerminalServiceServiceLocator();
			BorderTerminalService service = locator.getBorderTerminalService();
			BorderTerminal terminal = new BorderTerminal();
			terminal.setId(id);
			boolean success = service.deleteBorderTerminal(terminal);
			if (success)
				GUIUtils.showAlert(stage, AlertType.CONFIRMATION, "Informacija", "Uspješno obrisano.");
			else
				GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			cleanUp();
		} catch (Exception ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	// Event Listener on Button[#deletePointBtn].onAction
	@FXML
	public void deletePoint(ActionEvent event) {
		if (pointsComboBox.getSelectionModel().getSelectedItem() == null) {
			return;
		}
		int id = pointsComboBox.getSelectionModel().getSelectedItem();
		
		int ind = 0;
		
		for (int i = 0; i < points.size(); i++) {
			if (points.get(i).getId() == id)
				ind = i;
		}

		if (ind < entriesVBox.getChildren().size())
			entriesVBox.getChildren().remove(ind);
		else
			exitsVBox.getChildren().remove(ind - entriesVBox.getChildren().size());
		
		points.remove(ind);
		updatePointsComboBox();
	}

	// Event Listener on Button[#addEntry].onAction
	@FXML
	public void addEntry(ActionEvent event) {
		entriesVBox.getChildren().add(GUIUtils.createBorderCrossingPoint(new BorderEntry(), points));
	}

	// Event Listener on Button[#addExit].onAction
	@FXML
	public void addExit(ActionEvent event) {
		exitsVBox.getChildren().add(GUIUtils.createBorderCrossingPoint(new BorderExit(), points));
	}

	private void cleanUp() {
		points.clear();
		terminalIDTF.clear();
		terminalNameTF.clear();
		entriesVBox.getChildren().clear();
		exitsVBox.getChildren().clear();
		changeTerminalBtn.setDisable(true);
		deleteTerminalBtn.setDisable(true);
		pointsComboBox.getItems().clear();
	}

	// Event Listener on Button[#addNewUserBtn].onAction
	@FXML
	public void addNewUser(ActionEvent event) {
		Stage stage = (Stage) getWarrantsBtn.getScene().getWindow();
		if (newUserTextField.getText().isEmpty() || newUserPassField.getText().isEmpty()) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Niste unijeli sve parametre.");
			return;
		}
		User user = new User(newUserTextField.getText(), newUserPassField.getText());
		try {
			int responseCode = RESTUtils.jsonHttpRequest(MainWindow.getDBBaseURL(), "POST", user);
			if (responseCode == HttpURLConnection.HTTP_CREATED)
				GUIUtils.showAlert(stage, AlertType.CONFIRMATION, "Informacija", "Uspješno dodano.");
			else
				GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
		} catch (Exception ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	// Event Listener on Button[#changeUserBtn].onAction
	@FXML
	public void changeUser(ActionEvent event) {
		Stage stage = (Stage) getWarrantsBtn.getScene().getWindow();
		if (changeUserTextField.getText().isEmpty() || oldPasswordPassField.getText().isEmpty()
				|| newPasswordPassField.getText().isEmpty()) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Niste unijeli sve parametre.");
			return;
		}
		User user = new User(changeUserTextField.getText(), oldPasswordPassField.getText());
		try {
			JSONObject responseBody = RESTUtils
					.jsonObjectHttpRequest(MainWindow.getDBBaseURL() + "/" + changeUserTextField.getText());
			if (responseBody == null) {
				GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
				return;
			}

			User userFromJson = new User(responseBody.getString("username"), responseBody.getString("password"));
			if (!user.getPassword().equals(userFromJson.getPassword())) {
				GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Nevalidni kredencijali.");
				return;
			}
			user.setPassword(newPasswordPassField.getText());
			int responseCode = RESTUtils.jsonHttpRequest(MainWindow.getDBBaseURL(), "PUT", user);
			if (responseCode == HttpURLConnection.HTTP_NO_CONTENT)
				GUIUtils.showAlert(stage, AlertType.CONFIRMATION, "Informacija", "Uspješno dodano.");
			else
				GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
		} catch (Exception ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	// Event Listener on Button[#deleteUserBtn].onAction
	@FXML
	public void deleteUser(ActionEvent event) {
		Stage stage = (Stage) getWarrantsBtn.getScene().getWindow();
		if (deleteUserTextField.getText().isEmpty()) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Niste unijeli sve parametre.");
			return;
		}
		User user = new User(deleteUserTextField.getText(), "");
		try {
			user.setPassword(newPasswordPassField.getText());
			int responseCode = RESTUtils.jsonHttpRequest(MainWindow.getDBBaseURL(), "DELETE", user);
			if (responseCode == HttpURLConnection.HTTP_NO_CONTENT)
				GUIUtils.showAlert(stage, AlertType.CONFIRMATION, "Informacija", "Uspješno obrisano.");
			else
				GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
		} catch (Exception ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	// Event Listener on Button[#getBorderPapersBtn].onAction
	@FXML
	public void getBorderPapers(ActionEvent event) {
		Stage stage = (Stage) getWarrantsBtn.getScene().getWindow();
		String personID = personIDTF.getText().isEmpty() ? "" : "/" + personIDTF.getText();
		try {
			JSONArray responseBody = RESTUtils.jsonArrayHttpRequest(MainWindow.getFSBaseURL() + personID);

			DirectoryChooser chooser = new DirectoryChooser();
			File dirSelected = chooser.showDialog((Stage) getWarrantsBtn.getScene().getWindow());

			for (int i = 0; i < responseBody.length(); i++) {
				FileOutputStream fos = new FileOutputStream(dirSelected + File.separator + personIDTF.getText() + "_"
						+ System.currentTimeMillis() + ".zip");
				ObjectMapper mapper = new ObjectMapper();
				byte[] array = mapper.readValue(responseBody.get(i).toString(), byte[].class);
				fos.write(array);
				fos.close();
			}
		} catch (Exception ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	// Event Listener on Button[#getWarrantsBtn].onAction
	@FXML
	public void getWarrants(ActionEvent event) {
		Stage stage = (Stage) getWarrantsBtn.getScene().getWindow();
		try {
			Byte[] response = RESTUtils.byteArrayHttpRequest(MainWindow.getCRBaseURL());

			DirectoryChooser chooser = new DirectoryChooser();
			File dirSelected = chooser.showDialog((Stage) getWarrantsBtn.getScene().getWindow());

			FileOutputStream fos = new FileOutputStream(dirSelected + File.separator + "warrants.xml");
			for (byte b : response)
				fos.write(b);
			fos.close();
		} catch (Exception ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}
}
