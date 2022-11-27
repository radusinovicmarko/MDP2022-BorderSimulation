package org.unibl.etf.mdp.gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.unibl.etf.mdp.model.Document;
import org.unibl.etf.mdp.service.TestAppSOAPService;
import org.unibl.etf.mdp.service.TestAppSOAPServiceServiceLocator;
import org.unibl.etf.mdp.utils.GUIUtils;

import javafx.event.ActionEvent;

import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class BorderControlController implements Initializable {
	@FXML
	private Button addDocumentsBtn;
	@FXML
	private ListView<String> documentsLV;
	@FXML
	private Button finishBtn;

	private String client, personID;

	public BorderControlController(String client, String personID) {
		this.client = client;
		this.personID = personID;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	// Event Listener on Button[#addDocumentsBtn].onAction
	@FXML
	public void addDocuments(ActionEvent event) {
		FileChooser chooser = new FileChooser();
		Stage stage = (Stage) finishBtn.getScene().getWindow();
		List<File> filesChosen = chooser.showOpenMultipleDialog(stage);
		ArrayList<String> files = new ArrayList<>();
		for (File f : filesChosen)
			files.add(f.getAbsolutePath());
		documentsLV.getItems().addAll(files);
	}

	// Event Listener on Button[#finishBtn].onAction
	@FXML
	public void finish(ActionEvent event) {
		Stage stage = (Stage) finishBtn.getScene().getWindow();
		try {
			TestAppSOAPServiceServiceLocator locator = new TestAppSOAPServiceServiceLocator();
			TestAppSOAPService service = locator.getTestAppSOAPService();
			byte[] files = zipFiles();
			service.addBorderControl(client, new Document(files, personID));
			
			int passed = 0;
			while (passed == 0) {
				passed = service.checkIfPassed(personID);
				if (passed == 1) {
					GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška prilikom slanja.");
					break;
				} else if (passed == 2) {
					break;
				}
				Thread.sleep(2000);
			}
			
			if (passed == 2) {
				GUIUtils.showAlert(stage, AlertType.CONFIRMATION, "Potvrda", "Uspješno poslano.");
				stage.close();
			}
		} catch (Exception ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}
	
	private byte[] zipFiles() {
		List<String> documents = documentsLV.getItems();
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ZipOutputStream zipOut = new ZipOutputStream(baos);
			for (String document : documents) {
				File fileToZip = new File(document);
				FileInputStream fis = new FileInputStream(fileToZip);
				ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
				zipOut.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				fis.close();
			}
			zipOut.close();
			baos.close();
			return baos.toByteArray();
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
		return null;
	}
}
