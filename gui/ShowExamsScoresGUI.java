package gui;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.ExamInExecution;
import logic.StudentController;
import logic.StudentInExam;
import logic.User;
/**
 * This class is the controller for the Gui window the shows the student the 
 * grades of the exams he performed and a copy of the exams
 * @author reut
 *
 */
public class ShowExamsScoresGUI implements Initializable {
	// **************************************************
    // Fields
    // **************************************************
	@FXML
	Button ShowExamButton; // THE BUTTON that show the exam of the student
	// @FXML
	// Button MainMenuButton; //THE BUTTON that go back to the main menu

	@FXML
	private TableView<StudentInExam> gradeTable;
	@FXML
	private TableColumn<StudentInExam, String> examID;
	@FXML
	private TableColumn<StudentInExam, Integer> grade;
	@FXML
	private TableColumn<StudentInExam, Timestamp> dateCol;
	@FXML
	private TableColumn<StudentInExam, String> courseName;

	private User student; // to save the student that loged in info

	private ArrayList<StudentInExam> arr; // for all the grades of the student

	StudentController st;

	ObservableList<StudentInExam> GradesList;

	/**
	 * this method handles the action when the student choose an exam to watch from 
	 * the table and than press show exam it will open the ExamFormForStudent 
	 * with the exam the student choose
	 * @throws Exception
	 */
	public void ShowExamButtonAction() throws Exception {
		StudentInExam sie = gradeTable.getSelectionModel().getSelectedItem();
		if(sie==null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Error");
			alert.setContentText("Please chose an exam!");
			alert.showAndWait();
			
		}else {
			ExamInExecution exam = st.getExamForStudent(sie);

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ExamFormForStudent.fxml"));

			Parent root = loader.load();
			Scene scene = new Scene(root);
			ExamFormForStudentGUI ExamForStudent = loader.getController();
			ExamForStudent.initData(exam, false, sie.getGrade() );
			Stage stage = (Stage) gradeTable.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		}
		

	}


	/**
	 * this method inisialize the exems approved grades
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		st = new StudentController();
		examID.setCellValueFactory(new PropertyValueFactory<>("examID"));
		grade.setCellValueFactory(new PropertyValueFactory<>("grade"));
		dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
		courseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));

		arr = st.getAllgrades(); // save all the student grades in arr

		GradesList = FXCollections.observableArrayList();
		GradesList.addAll(arr);
		gradeTable.setItems(GradesList);
	}

	/**
	 * this method start the student's grades window using fxml
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("ShowExamsScores.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}
}
