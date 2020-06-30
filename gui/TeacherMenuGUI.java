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
 * This class is the controller for teacher main GUI window Allows the teacher
 * to execute an exam, approve grade, create questions and exams.
 * 
 * @author reut
 */
public class TeacherMenuGUI implements Initializable {
	// **************************************************
	// Fields
	// **************************************************
	@FXML
	Button testRepositoryButton;
	@FXML
	Button questionRepositoryButton;
	@FXML
	Button testInExecutionButton;
	@FXML
	Label helloMsgLabel;
	@FXML
	Button logoutButton;
	private User teacher;
	private LoginController lc;

	/**
	 * This method open the question repository window which allow the teacher to
	 * manage the repository.
	 * 
	 * @throws Exception
	 */
	public void questionRepositoryButtonAction(ActionEvent ae) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("QuestionRepository.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		QuestionRepositoryGUI qrg = loader.getController();
		qrg.initData();
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		window.setScene(scene);
		window.setTitle("Question Repository Menu");
		window.show();

	}

	/**
	 * This method open the exam repository window which allow the teacher to manage
	 * the repository.
	 * 
	 * @throws Exception
	 */
	public void testRepositoryButtonAction(ActionEvent ae) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ExamRepository.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ExamRepositoryGUI examRep = loader.getController();
		examRep.initData();
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		window.setScene(scene);

		window.show();
	}

	/**
	 * This method open the execute exam window which allow the teacher to send an
	 * exam to execution
	 * 
	 * @throws Exception
	 */
	public void testInExecutionButtonAction(ActionEvent ae) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ExamInExecutionMenu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ExamInExecutionMenuGUI examInExecMenu = loader.getController();
		examInExecMenu.initData();
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		window.setScene(scene);
		window.show();
	}

	/**
	 * This method open the report window which allow the teacher to view statistic
	 * report for the exam she wrote.
	 * 
	 * @throws Exception
	 */
	public void reportButtonAction(ActionEvent ae) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("StatisticsMenu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		StatisticsMenuGUI sm = loader.getController();
		sm.initData(teacher);
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		window.setScene(scene);
		window.show();
	}

	/**
	 * This methods handles the action pressing the logout button and logs out of
	 * the system
	 * 
	 * @throws Exception
	 */
	public void logoutButtonAction(ActionEvent ae) throws IOException {
		lc.logoutUser();
		Stage stage = (Stage) logoutButton.getScene().getWindow();
		LoginGUI lg = new LoginGUI(); // run login window
		lg.start(stage);

	}

	/**
	 * imolement serializable
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Stage stage=(Stage) testRepositoryButton.getScene().getWindow();

	}

	/**
	 * set all the teacher menu data on the fxml window
	 */
	public void initData() {

		lc = new LoginController();
		lc.getUser().setTitle("Teacher");
		this.teacher = lc.getUser();
		helloMsgLabel.setText("Hello " + teacher.getuName() + ",");

	}
}
