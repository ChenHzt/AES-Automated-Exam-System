package server;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * This class manage the server fxml
 * 
 * @author vakni
 *
 */
public class ServerGUI {

	// **************************************************
	// Fields
	// **************************************************
	@FXML
	private TextField portTF;

	@FXML
	private TextField DBNameTF;

	@FXML
	private PasswordField DBPasswordTF;

	@FXML
	private Label connectLabel;

	@FXML
	private Button connectButton;

	@FXML
	private Label connectLabel1;

	@FXML
	private Button disconnectButton;

	@FXML
	private TextArea OutputMessage;

	@FXML
	private ImageView connectImg;

	@FXML
	private Label connL;

	@FXML
	private ImageView disConnectImg;

	@FXML
	private Label disconnL;

	private EchoServer myServer;

	/**
	 * this method is to handel when the user press on the connect button to conect
	 * the server
	 * 
	 * @param event
	 */
	@SuppressWarnings("deprecation")
	@FXML
	void connectButtonAction(ActionEvent event) {
		connectButton.setDisable(true);
		connectLabel.setVisible(false);
		connL.setVisible(false);
		connectImg.setVisible(false);
		disconnectButton.setDisable(false);
		connectLabel1.setVisible(true);
		disconnL.setVisible(true);
		disConnectImg.setVisible(true);
		portTF.setDisable(true);
		DBNameTF.setDisable(true);
		int port = 0; // Port to listen on
		port = Integer.parseInt(portTF.getText()); // Get port from textField

		this.myServer = new EchoServer(port, DBNameTF.getText(), DBPasswordTF.getText());
		// serverIsConnected=true;
		try {
			myServer.listen(); // Start listening for connections
			OutputMessage.appendText("Server listening for connections on port " + myServer.getPort() + "...\n");
			ServerMain.serverIsConnected = true;
			if (myServer.checkIfDBConnects())
				OutputMessage.appendText("Server can connect to DB\n");
			else
				OutputMessage.appendText("Server can't connect to DB\n");
		} catch (Exception ex) {
			System.out.println("ERROR - Could not listen for clients!");
			OutputMessage.appendText("ERROR - Could not listen for clients! We are closing the connection!");
		}

		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Do not exit without disconnecting");

		alert.setContentText("Do not exit before disconnecting from the server!");

		alert.showAndWait();

	}

	/**
	 * this method disconect the server when the user press on the disconnect button
	 * 
	 * @param event
	 */
	@FXML
	void disconnectButtonAction(ActionEvent event) {
		OutputMessage.clear();
		connectButton.setDisable(false);
		connectLabel.setVisible(true);
		connL.setVisible(true);
		connectImg.setVisible(true);
		disconnectButton.setDisable(true);
		connectLabel1.setVisible(false);
		disconnL.setVisible(false);
		disConnectImg.setVisible(false);
		portTF.setDisable(false);
		DBNameTF.setDisable(false);
		try {
			if (ServerMain.serverIsConnected == true) {
				myServer.close();
				System.out.println("The server closed sucessfully!");
				OutputMessage.appendText("The server closed sucessfully!");
				ServerMain.serverIsConnected = false;
				this.myServer = null;
			}
		} catch (IOException e) {
			System.out.println("We could not close the server because of error");
			OutputMessage.appendText("We could not close the server because of error\n");

		}

	}

	public void initData() {

	}

}
