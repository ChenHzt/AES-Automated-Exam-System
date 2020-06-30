
package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
 * This class is the controller for student main GUI window Allows the student
 * to perform an exam and watch his grade
 * 
 * @author reut
 */
public class StudentMenuGUI implements Initializable {
	// **************************************************
	// Fields
	// **************************************************
	@FXML
	Button PerformanceTestsButton; // THE BUTTON TO DO TEST
	@FXML
	Button ExamScoresButton;// THE BUTTON TO SHOW THE STUDENT SCORES
	@FXML
	Button logoutButton; // to logout the system fron the menu
	@FXML
	Label helloMsgLabel; // to show the student name on the label

	private User student; // to save the student that loged in info
	private LoginController lc;

	// **************************************************
	// Public methods
	// **************************************************
	/**
	 * This methods handles the action of showing the user the list of exams he has
	 * to perform
	 * 
	 * @throws Exception
	 */
	public void PerformanceTestsButtonAction() throws Exception {
		PerformanceExamsGUI PG = new PerformanceExamsGUI(); // CREATE THE NEXT WINDOW GUI
		Stage primaryStage = new Stage();
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.setResizable(false);
		PG.start(primaryStage); // RUN THE NEW WINDOW GUI

	}

	/**
	 * This methods handles the action of showing the student the list of exams he
	 * has already performed and were approved by the teacher
	 * 
	 * @throws Exception
	 */
	public void ShowExamsScoresAction() throws Exception {
		ShowExamsScoresGUI PG = new ShowExamsScoresGUI(); // CREATE THE NEXT WINDOW GUI
		Stage primaryStage = new Stage();
		primaryStage.initModality(Modality.APPLICATION_MODAL);
		primaryStage.setResizable(false);
		PG.start(primaryStage); // RUN THE NEW WINDOW GUI

		// Stage stage = (Stage) ExamScoresButton.getScene().getWindow();
		// ShowExamsScoresGUI PG=new ShowExamsScoresGUI(); //CREATE THE NEXT WINDOW GUI
		// PG.start(stage); //RUN THE NEW WINDOW GUI
	}

	/**
	 * This methods handles the action pressing the logout button and logs out of
	 * the system
	 * 
	 * @throws Exception
	 */
	public void logoutButtonAction(ActionEvent ae) throws IOException {
		lc = new LoginController();
		lc.logoutUser();
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		LoginGUI lg = new LoginGUI(); // run login window
		lg.start(stage);
	}

	/**
	 * initialize the data on the student menu
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		LoginController lc = new LoginController();// save the user detailed
		this.student = lc.getUser(); // save the teacher that connected to the system
		helloMsgLabel.setText("Hello " + student.getuName());
	}

	/**
	 * this method start the student menu window using fxml
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("StudentMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.setTitle("Student Menu");
		primaryStage.show();
		lc = new LoginController();
		primaryStage.setOnCloseRequest(event -> { // LOG OUT THE USER IF HE PRESS "X"
			lc.logoutUser();
			System.exit(0);
		});
	}
}
