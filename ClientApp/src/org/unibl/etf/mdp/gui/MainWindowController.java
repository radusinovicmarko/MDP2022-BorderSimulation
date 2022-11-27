package org.unibl.etf.mdp.gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.unibl.etf.mdp.control.BorderControlThread;
import org.unibl.etf.mdp.control.PoliceControlThread;
import org.unibl.etf.mdp.control.UserReceiverThread;
import org.unibl.etf.mdp.control.UserSenderThread;
import org.unibl.etf.mdp.protocol.ChatProtocolMessages;
import org.unibl.etf.mdp.service.ClientAppSOAPService;
import org.unibl.etf.mdp.service.ClientAppSOAPServiceServiceLocator;
import org.unibl.etf.mdp.service.PassengersSOAPService;
import org.unibl.etf.mdp.service.PassengersSOAPServiceServiceLocator;
import org.unibl.etf.mdp.utils.GUIUtils;

import javafx.application.Platform;
import javafx.event.ActionEvent;

public class MainWindowController implements Initializable {

	@FXML
	private Label chatLbl;
	@FXML
	private Button notifyBtn;
	@FXML
	private ListView<String> clientsLV;
	@FXML
	private ListView<String> unicastMessagesLV;
	@FXML
	private TextField receiverTerminalIDTF;
	@FXML
	private TextField receiverCrossingPointIDTF;
	@FXML
	private RadioButton policeRB;
	@FXML
	private RadioButton borderRB;
	@FXML
	private TextField unicastTF;
	@FXML
	private Button unicastSendBtn;
	@FXML
	private ListView<String> multicastMessagesLV;
	@FXML
	private TextField multicastTF;
	@FXML
	private Button multicastSendBtn;
	@FXML
	private ListView<String> broadcastMessagesLV;
	@FXML
	private TextField broadcastTF;
	@FXML
	private Button broadcastSendBtn;

	private ToggleGroup group = new ToggleGroup();

	private int terminalID, crossingPointID;
	private String username, senderName;

	private UserSenderThread sender;

	private HashMap<String, ArrayList<String>> messages = new HashMap<>();
	private ArrayList<String> multicastMessages = new ArrayList<>();
	private ArrayList<String> broadcastMessages = new ArrayList<>();

	private Consumer<String> messageReceivedCallback;

	private Object lock = new Object();
	private boolean close = false;

	public MainWindowController(boolean policeControl, int terminalID, int crossingPointID, String username) {
		this.terminalID = terminalID;
		this.crossingPointID = crossingPointID;
		this.username = username;
		String type = policeControl ? "p" : "b";

		messageReceivedCallback = message -> Platform.runLater(() -> {
			if (message.startsWith(ChatProtocolMessages.UNICAST_REQUEST.getMessage())) {
				String messageRaw = message.split(ChatProtocolMessages.MESSAGE_SEPARATOR.getMessage())[1];
				String sender = messageRaw.split(":")[0];
				if (!messages.containsKey(sender))
					messages.put(sender, new ArrayList<>());
				messages.get(sender).add(messageRaw);
				clientsLV.getItems().clear();
				clientsLV.getItems().addAll(messages.keySet());
			} else if (message.startsWith(ChatProtocolMessages.MULTICAST_REQUEST.getMessage())) {
				String messageRaw = message.split(ChatProtocolMessages.MESSAGE_SEPARATOR.getMessage())[1];
				if (messageRaw.split(":")[1].equals(ChatProtocolMessages.POLICE_NOTIFICATION_CLOSE.getMessage())) {
					synchronized (lock) {
						close = true;
					}
					logoutClient();
				} else if (messageRaw.split(":")[1]
						.equals(ChatProtocolMessages.POLICE_NOTIFICATION_OPEN.getMessage())) {
					synchronized (lock) {
						close = false;
						lock.notifyAll();
					}
					logClient();
				}
				multicastMessages.add(messageRaw);
				multicastMessagesLV.getItems().clear();
				multicastMessagesLV.getItems().addAll(multicastMessages);
			} else if (message.startsWith(ChatProtocolMessages.BROADCAST_REQUEST.getMessage())) {
				broadcastMessages.add(message.split(ChatProtocolMessages.MESSAGE_SEPARATOR.getMessage())[1]);
				broadcastMessagesLV.getItems().clear();
				broadcastMessagesLV.getItems().addAll(broadcastMessages);
			}
		});

		senderName = terminalID + ChatProtocolMessages.TERMINAL_USER_SEPARATOR.getMessage() + this.crossingPointID
				+ ChatProtocolMessages.TERMINAL_USER_SEPARATOR.getMessage() + type;

		Consumer<Boolean> notifyClosed = value -> Platform.runLater(() -> {
			close = value;
		});

		sender = new UserSenderThread(senderName, notifyClosed);
		new UserReceiverThread(senderName, messageReceivedCallback, notifyClosed);
		logClient();

		Runnable notifyCallback = () -> Platform.runLater(() -> {
			GUIUtils.showAlert((Stage) notifyBtn.getScene().getWindow(), AlertType.INFORMATION, "Informacija",
					"Privremeno zatvaranje prelaza.");
			multicastSend(String.valueOf(terminalID), ChatProtocolMessages.POLICE_NOTIFICATION_CLOSE.getMessage());
			synchronized (lock) {
				close = true;
			}
			logoutClient();
			notifyBtn.setDisable(false);
			notifyBtn.setStyle("-fx-background-color: red");
		});

		Consumer<String> filesSentCallback = m -> Platform.runLater(() -> {
			GUIUtils.showAlert((Stage) notifyBtn.getScene().getWindow(), AlertType.INFORMATION, "Informacija", m);
		});

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
		if (policeControl)
			new PoliceControlThread(senderName, notifyCallback, lock, this).start();
		else
			new BorderControlThread(senderName, filesSentCallback, lock, this).start();
	}

	private void logClient() {
		try {
			ClientAppSOAPServiceServiceLocator locator = new ClientAppSOAPServiceServiceLocator();
			ClientAppSOAPService service = locator.getClientAppSOAPService();
			service.login(senderName);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void notify(ActionEvent event) {
		multicastSend(String.valueOf(terminalID), ChatProtocolMessages.POLICE_NOTIFICATION_OPEN.getMessage());
		synchronized (lock) {
			close = false;
			lock.notifyAll();
		}
		logClient();
		notifyBtn.setDisable(true);
		notifyBtn.setStyle("-fx-background-color: transparent");
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void logout(ActionEvent event) {
		Stage stage = (Stage) notifyBtn.getScene().getWindow();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Startup.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root, 500, 300);
			Stage newStage = new Stage();
			newStage.setResizable(false);
			newStage.setTitle("Klijentska aplikacija");
			newStage.setScene(scene);
			close();
			newStage.show();
		} catch (IOException ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void changePassword(ActionEvent event) {
		Stage stage = (Stage) notifyBtn.getScene().getWindow();
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangePassword.fxml"));
			loader.setControllerFactory(c -> new ChangePasswordController(username));
			Parent root = loader.load();
			Scene scene = new Scene(root, 400, 300);
			Stage newStage = new Stage();
			newStage.setResizable(false);
			newStage.setTitle("Promjena lozinke");
			newStage.setScene(scene);
			newStage.showAndWait();
		} catch (IOException ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	// Event Listener on MenuItem.onAction
	@FXML
	public void showPassengers(ActionEvent event) {
		Stage stage = (Stage) notifyBtn.getScene().getWindow();
		try {
			PassengersSOAPServiceServiceLocator locator = new PassengersSOAPServiceServiceLocator();
			PassengersSOAPService service = locator.getPassengersSOAPService();
			byte[] logs = service.getLog();

			DirectoryChooser chooser = new DirectoryChooser();
			File dirSelected = chooser.showDialog((Stage) notifyBtn.getScene().getWindow());

			FileOutputStream fos = new FileOutputStream(dirSelected + File.separator + "logs.txt");
			fos.write(logs);
			fos.close();
		} catch (Exception ex) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Greška.");
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	// Event Listener on Button[#unicastSendBtn].onAction
	@FXML
	public void unicastSend(ActionEvent event) {
		Stage stage = (Stage) notifyBtn.getScene().getWindow();
		if (receiverTerminalIDTF.getText().isEmpty() || receiverCrossingPointIDTF.getText().isEmpty()
				|| unicastTF.getText().isEmpty() || group.getSelectedToggle() == null) {
			GUIUtils.showAlert(stage, AlertType.ERROR, "Greška", "Niste unijeli sve parametre.");
			return;
		}
		String receiverTerminalID = receiverTerminalIDTF.getText();
		String receiverCrossingPointID = receiverCrossingPointIDTF.getText();
		String message = unicastTF.getText();
		String type = policeRB.isSelected() ? "p" : "b";
		String receiver = receiverTerminalID + ChatProtocolMessages.TERMINAL_USER_SEPARATOR.getMessage()
				+ receiverCrossingPointID + ChatProtocolMessages.TERMINAL_USER_SEPARATOR.getMessage() + type;
		if (!messages.containsKey(receiver))
			messages.put(receiver, new ArrayList<>());
		messages.get(receiver).add(message);
		clientsLV.getItems().clear();
		clientsLV.getItems().addAll(messages.keySet());
		sender.send(ChatProtocolMessages.UNICAST_REQUEST.getMessage() + receiverTerminalID
				+ ChatProtocolMessages.MESSAGE_SEPARATOR.getMessage() + receiverCrossingPointID
				+ ChatProtocolMessages.MESSAGE_SEPARATOR.getMessage() + type
				+ ChatProtocolMessages.MESSAGE_SEPARATOR.getMessage() + message);
	}

	// Event Listener on ListView[#clientsLV].onAction
	@FXML
	public void clientSelected(MouseEvent event) {
		String client = clientsLV.getSelectionModel().getSelectedItem();
		if (client == null)
			return;
		receiverTerminalIDTF.setText(client.split("-")[0]);
		receiverCrossingPointIDTF.setText(client.split("-")[1]);
		if (client.split("-")[2].equals("p"))
			policeRB.setSelected(true);
		else
			borderRB.setSelected(true);

		unicastMessagesLV.getItems().clear();
		unicastMessagesLV.getItems().addAll(messages.get(client));
	}

	// Event Listener on Button[#multicastSendBtn].onAction
	@FXML
	public void multicastSend(ActionEvent event) {
		if (multicastTF.getText().isEmpty()) {
			return;
		}
		String receiverTerminalID = String.valueOf(terminalID);
		String message = multicastTF.getText();
		multicastSend(receiverTerminalID, message);
	}

	private void multicastSend(String receiverTerminalID, String message) {
		multicastMessages.add(message);
		multicastMessagesLV.getItems().add(message);
		sender.send(ChatProtocolMessages.MULTICAST_REQUEST.getMessage() + receiverTerminalID
				+ ChatProtocolMessages.MESSAGE_SEPARATOR.getMessage() + message);
	}

	// Event Listener on Button[#broadcastSendBtn].onAction
	@FXML
	public void broadcastSend(ActionEvent event) {
		if (broadcastTF.getText().isEmpty()) {
			return;
		}
		String message = broadcastTF.getText();
		broadcastMessages.add(message);
		broadcastMessagesLV.getItems().add(message);
		sender.send(ChatProtocolMessages.BROADCAST_REQUEST.getMessage() + message);
	}

	private void close() {
		sender.close();
		logoutClient();
		Stage stage = (Stage) broadcastSendBtn.getScene().getWindow();
		stage.hide();
	}

	private void logoutClient() {
		try {
			ClientAppSOAPServiceServiceLocator locator = new ClientAppSOAPServiceServiceLocator();
			ClientAppSOAPService service = locator.getClientAppSOAPService();
			service.logout(senderName);
		} catch (Exception ex) {
			Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.fillInStackTrace().toString());
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		chatLbl.setText(chatLbl.getText() + " : " + senderName);
		policeRB.setToggleGroup(group);
		borderRB.setToggleGroup(group);
	}

	public boolean isClose() {
		return close;
	}
}
