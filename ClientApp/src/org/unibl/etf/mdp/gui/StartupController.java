package org.unibl.etf.mdp.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.mdp.model.BorderEntry;
import org.unibl.etf.mdp.model.BorderExit;
import org.unibl.etf.mdp.model.BorderTerminal;
import org.unibl.etf.mdp.service.BorderTerminalService;
import org.unibl.etf.mdp.service.BorderTerminalServiceServiceLocator;
import org.unibl.etf.mdp.service.ClientAppSOAPService;
import org.unibl.etf.mdp.service.ClientAppSOAPServiceServiceLocator;
import org.unibl.etf.mdp.utils.GUIUtils;

import javafx.event.ActionEvent;

import javafx.scene.control.RadioButton;

public class StartupController {
	@FXML
	private TextField terminalIDTF;
	@FXML
	private TextField crossingPointIDTF;
	@FXML
	private RadioButton policeRB;
	@FXML
	private RadioButton borderRB;
	@FXML
	private Button nextBtn;

	private ToggleGroup group = new ToggleGroup();

	@FXML
	public void initialize() {
		policeRB.setToggleGroup(group);
		borderRB.setToggleGroup(group);
		policeRB.setSelected(true);
	}

	// Event Listener on Button[#nextBtn].onAction
	@FXML
	public void next(ActionEvent event) {
		Stage s = (Stage) nextBtn.getScene().getWindow();
		if (terminalIDTF.getText().isEmpty() || crossingPointIDTF.getText().isEmpty()) {
			GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Niste unijeli sve parametre.");
			return;
		}
		try {
			BorderTerminalServiceServiceLocator locator = new BorderTerminalServiceServiceLocator();
			BorderTerminalService service = locator.getBorderTerminalService();
			BorderTerminal terminal = service.getBorderTerminal(Integer.parseInt(terminalIDTF.getText()));
			if (terminal == null) {
				GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Greška.");
				return;
			}
			int crossingPointID = Integer.parseInt(crossingPointIDTF.getText());
			boolean found = false;
			for (BorderEntry entry : terminal.getEntries())
				if (entry.getId() == crossingPointID)
					found = true;
			for (BorderExit exit : terminal.getExits())
				if (exit.getId() == crossingPointID)
					found = true;

			if (found) {
				boolean check = checkIfActive(terminal.getId() + "-" + crossingPointID + "-" + (policeRB.isSelected() ? "p" : "b"));
				if (check) {
					GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Ulaz/izlaz je vec aktivan.");
					return;
				}
				FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
				boolean policeControl = policeRB.isSelected();
				loader.setControllerFactory(c -> new LoginController(policeControl, terminal.getId(), crossingPointID));
				Parent root = loader.load();
				Scene scene = new Scene(root, 500, 300);
				Stage newStage = new Stage();
				newStage.setResizable(false);
				newStage.setTitle("Prijava");
				newStage.setScene(scene);
				Stage stage = (Stage) nextBtn.getScene().getWindow();
				stage.hide();
				newStage.show();
			} else {
				GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Terminal ili ulaz ne postoje ili nisu aktivni.");
			}
		} catch (Exception ex) {
			GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	private boolean checkIfActive(String client) {
		try {
			ClientAppSOAPServiceServiceLocator locator = new ClientAppSOAPServiceServiceLocator();
			ClientAppSOAPService service = locator.getClientAppSOAPService();
			return service.checkIfActive(client);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
		return false;
	}
}
