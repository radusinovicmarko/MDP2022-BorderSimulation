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

import org.unibl.etf.mdp.service.TestAppSOAPService;
import org.unibl.etf.mdp.service.TestAppSOAPServiceServiceLocator;
import org.unibl.etf.mdp.utils.GUIUtils;

import javafx.event.ActionEvent;

public class StartupController {
	@FXML
	private TextField terminalIDTF;
	@FXML
	private TextField crossingPointIDTF;
	@FXML
	private Button nextBtn;

	// Event Listener on Button[#nextBtn].onAction
	@FXML
	public void next(ActionEvent event) {
		Stage s = (Stage) nextBtn.getScene().getWindow();
		if (terminalIDTF.getText().isEmpty() || crossingPointIDTF.getText().isEmpty()) {
			GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Niste unijeli sve parametre.");
			return;
		}
		try {
			TestAppSOAPServiceServiceLocator locator = new TestAppSOAPServiceServiceLocator();
			TestAppSOAPService service = locator.getTestAppSOAPService();
			String client = terminalIDTF.getText() + "-" + crossingPointIDTF.getText();
			if (service.checkIfActive(client)) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("PoliceControl.fxml"));
				loader.setControllerFactory(c -> new PoliceControlController(client));
				Parent root = loader.load();
				Scene scene = new Scene(root, 500, 300);
				Stage newStage = new Stage();
				newStage.setResizable(false);
				newStage.setTitle("Policijska kontrola");
				newStage.setScene(scene);
				Stage stage = (Stage) nextBtn.getScene().getWindow();
				stage.hide();
				newStage.show();
			} else {
				GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Terminal ili ulaz nisu trenutno aktivni.");
			}
		} catch (Exception ex) {
			GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}
}
