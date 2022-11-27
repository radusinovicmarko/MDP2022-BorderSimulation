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

public class PoliceControlController {
	@FXML
	private TextField personIDTF;
	@FXML
	private Button nextBtn;

	private String client;

	public PoliceControlController(String client) {
		this.client = client;
	}

	// Event Listener on Button[#nextBtn].onAction
	@FXML
	public void next(ActionEvent event) {
		Stage s = (Stage) nextBtn.getScene().getWindow();
		if (personIDTF.getText().isEmpty()) {
			GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Greška.");
			return;
		}
		try {
			String personID = personIDTF.getText();
			TestAppSOAPServiceServiceLocator locator = new TestAppSOAPServiceServiceLocator();
			TestAppSOAPService service = locator.getTestAppSOAPService();
			service.addPoliceControl(client, personID);

			int passed = 0;
			while (passed == 0) {
				passed = service.checkIfPassed(personID);
				if (passed == 1) {
					GUIUtils.showAlert(s, AlertType.WARNING, "Upozorenje", "Na potjernici ste!");
					break;
				} else if (passed == 2) {
					break;
				}
				Thread.sleep(2000);
			}
			if (passed == 2) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("BorderControl.fxml"));
				loader.setControllerFactory(c -> new BorderControlController(client, personID));
				Parent root = loader.load();
				Scene scene = new Scene(root, 500, 300);
				Stage newStage = new Stage();
				newStage.setResizable(false);
				newStage.setTitle("Carinska kontrola");
				newStage.setScene(scene);
				Stage stage = (Stage) nextBtn.getScene().getWindow();
				stage.hide();
				newStage.show();
			}
		} catch (Exception ex) {
			GUIUtils.showAlert(s, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}
}
