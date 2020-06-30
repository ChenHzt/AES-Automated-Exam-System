package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientConsole;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.User;
import login.LoginController;
import login.LoginGUI;

/**
 * This class is the controller of principal menu GUI main window. In this
 * window she can watch the system's data, get statistic reports and
 * approve/deny overtime requests for exams.
 * 
 * @author reut
 *
 */
public class PrincipleMenuGUI2 implements Initializable {
	// ************************************
	// Fields
	// ************************************
	@FXML
	Button ExtraTimeRequestsButton;
	@FXML
	Button StatisticsButton;
	@FXML
	Button SystemDetailsButton;
	@FXML
	Label helloMsgLabel_P;
	@FXML
	private Label notification;
	@FXML
	private Button viewRequests;
	@FXML
	Button logoutButton;
	private User principal;
	private LoginController lc;
	private boolean flag = false;
	private Thread overtimeThread;
	private ClientConsole client;

	/**
	 * This method opens a new scene, in this secene teacher can approve or deny
	 * overtime request for ongoing exams.
	 * 
	 * @throws Exception
	 */
	public void ExtraTimeRequestsButtonAction() throws Exception {
		flag = false;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("OvertimeRequestMenu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		OvertimeRequestMenuGUI rot = loader.getController();
		rot.initData();
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		window.setScene(scene);
		window.show();
	}

	/**
	 * This method opens a new scene that allows the principal to view various
	 * statistic reports.
	 * 
	 * @throws Exception
	 */
	public void StatisticsButtonAction() throws Exception { // When we click on the Statistics button, it will send us
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("StatisticsMenu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		StatisticsMenuGUI sm = loader.getController();
		sm.initData(principal);
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		window.setScene(scene);
		window.show();
	}

	/**
	 * This method opens a new scene that allows the principal to view The system
	 * data.
	 * 
	 * @throws Exception
	 */
	public void SystemDetailsButtonAction() throws Exception {
		// When we click on the System Details button, it will send us to the System
		// Details menu.
		SystemDetailsMenuGUI qrg = new SystemDetailsMenuGUI();
		Stage primaryStage = new Stage();
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.setResizable(false);
		qrg.start(primaryStage);
	}

	/**
	 * this method show the data on the principle menu
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		this.client = new ClientConsole(LoginGUI.IP, LoginGUI.port, null);
		LoginController lc = new LoginController();
		lc.getUser().setTitle("Principal");
		this.principal = lc.getUser();
		helloMsgLabel_P.setText("Hello " + principal.getuName() + ",");
		// now, after the initialization, the title is "Hello XXX YYY,"
		overtimeThread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (client.getMessage().getReturnObj() instanceof String) {
						if (((String) client.getMessage().getReturnObj()).equals("newOverTimeRequest")
								&& flag == false) {
							notification.setVisible(true);
							viewRequests.setVisible(true);
							flag = true;

							try {
								Thread.sleep(3000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							notification.setVisible(false);
							viewRequests.setVisible(false);

						}
					}
				}

			}

		});
		overtimeThread.start();
	}

	/**
	 * this method run the principle menu gui
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("principleMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.setTitle("Principal Menu");
		primaryStage.show();
		// from this line the code logs out the user when he press on X button:
		lc = new LoginController();
		primaryStage.setOnCloseRequest(event -> {
			lc.logoutUser();
			System.exit(0);
		});
	}

	/**
	 * THis method logs principal out of the system
	 * 
	 * @throws IOException
	 */
	public void logoutButtonAction(ActionEvent ae) throws IOException {
		lc = new LoginController();
		lc.logoutUser();
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		LoginGUI lg = new LoginGUI(); // run login window
		lg.start(stage);

	}
}
