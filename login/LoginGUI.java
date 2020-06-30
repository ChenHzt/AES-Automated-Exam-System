package login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientConsole;
import gui.PrincipleMenuGUI2;
import gui.StudentMenuGUI;
import gui.TeacherMenuGUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import logic.User;

/**
 * This class manage fxml that the the exam in execution form
 * 
 * @author rotem
 *
 */
public class LoginGUI implements Initializable {

	// **************************************************
	// Fields
	// **************************************************
	@FXML
	private ImageView logoIV;
	@FXML
	private TextField userIDTF;
	@FXML
	private PasswordField passwordTF;
	@FXML
	private Button loginButton;
	@FXML
	private Label errorL;
	@FXML
	private ImageView ImageZerli;
	@FXML
	private Button btnExit;
	@FXML
	private TextField txtServerIP;
	@FXML
	private TextField txtPORT;

	public static int port;
	public static String IP;

	String uid;
	String upass;

	private ClientConsole client;
	public ChatClient chat;
	static Boolean flag = false;
	// **************************************************
	// Public methods
	// **************************************************

	/**
	 * implement the Initializable
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * when the user press the exit
	 */
	@FXML
	void Exit(ActionEvent event) {
		System.out.println("exit AES Application");
		System.exit(0);
	}

	/**
	 * this method manage the login action when the user press on the login button
	 * 
	 * @param ae
	 * @throws Exception
	 */
	public void loginButtonAction(ActionEvent ae) throws Exception {
		LoginController lc = new LoginController();
		IP = lc.checkIP(txtServerIP.getText());
		port = lc.checkPort(txtPORT.getText());
		client = new ClientConsole(IP, port, null);
		uid = userIDTF.getText();
		upass = passwordTF.getText();
		User u = new User(uid, upass);
		String msg = lc.checkAllLoginDetails(u, null);
		if (!msg.substring(0, 2).equals("OK"))
			errorL.setText(msg);
		else {
			flag = true;
			u = lc.loginUser(u, null);
			((Node) ae.getSource()).getScene().getWindow().hide();
			if (msg.endsWith("Teacher"))// if user is teacher
			{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/gui/TeacherMenu.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				TeacherMenuGUI teacherMenu = loader.getController();
				teacherMenu.initData();
				Stage window = new Stage();
				window.setScene(scene);
				window.setTitle("Teacher Menu");
				window.show();
				window.setOnCloseRequest(event -> {
					System.out.println("exit AES Application");
					lc.logoutUser();

					System.exit(0);
				});

			}

			if (msg.endsWith("Student")) // if user is student
			{
				Stage primaryStage = new Stage();
				StudentMenuGUI tmg = new StudentMenuGUI();
				tmg.start(primaryStage);
			}
			if (msg.endsWith("Principle")) // if user is principle
			{
				Stage primaryStage = new Stage();
				PrincipleMenuGUI2 tmg = new PrincipleMenuGUI2();
				tmg.start(primaryStage);
			}

		}

	}

	/**
	 * this method run the fxml login window
	 */
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/login/Login.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("/gui/application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.setTitle("Login Menu");
		primaryStage.show();

		primaryStage.setOnCloseRequest(event -> {

			System.out.println("exit AES Application");
			System.exit(0);
		});
	}

}
