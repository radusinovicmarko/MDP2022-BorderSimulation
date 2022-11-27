package org.unibl.etf.mdp.utils;

import java.util.ArrayList;

import org.unibl.etf.mdp.model.BorderCrossingPoint;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIUtils {

	public static void showAlert(Stage stage, AlertType type, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle("Obavještenje");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static VBox createBorderCrossingPoint(BorderCrossingPoint point, ArrayList<BorderCrossingPoint> points) {

		int labelsPrefWidth = 200;

		VBox vbox = new VBox();
		Label idLbl = new Label("ID");
		idLbl.setPrefWidth(labelsPrefWidth);
		TextField idTF = new TextField(String.valueOf(point.getId()));
		idTF.setPrefWidth(200);
		idTF.setEditable(false);
		HBox idHBox = new HBox(idLbl, idTF);
		idHBox.setSpacing(10);

		Label borderControlPortLbl = new Label("Port carinske kontrole");
		borderControlPortLbl.setPrefWidth(labelsPrefWidth);
		TextField borderControlPortTF = new TextField(String.valueOf(point.getBorderControlPort()));
		borderControlPortTF.setPrefWidth(200);
		borderControlPortTF.setEditable(false);
		HBox borderControlPortHBox = new HBox(borderControlPortLbl, borderControlPortTF);
		borderControlPortHBox.setSpacing(10);

		Label policeControlPortLbl = new Label("Port policijske kontrole");
		policeControlPortLbl.setPrefWidth(labelsPrefWidth);
		TextField policeControlPortTF = new TextField(String.valueOf(point.getPoliceControlPort()));
		policeControlPortTF.setPrefWidth(200);
		policeControlPortTF.setEditable(false);
		HBox policeControlPortHBox = new HBox(policeControlPortLbl, policeControlPortTF);
		policeControlPortHBox.setSpacing(10);

		VBox.setMargin(idHBox, new Insets(5, 5, 5, 5));
		VBox.setMargin(borderControlPortHBox, new Insets(5, 5, 5, 5));
		VBox.setMargin(policeControlPortHBox, new Insets(5, 5, 5, 5));
		vbox.getChildren().addAll(idHBox, borderControlPortHBox, policeControlPortHBox);
		vbox.setSpacing(5);
		vbox.setStyle("-fx-border-color: black");

		points.add(point);

		return vbox;
	}

}
