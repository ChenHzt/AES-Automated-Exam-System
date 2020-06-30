package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.Course;
import logic.Exam;
import logic.ExamInExecution;
import logic.PrincipalController;
import logic.Question;
import logic.StudentInExam;
import logic.Subject;
import logic.User;

/**
 * This class is the controller for for viewing of system data. principal can
 * view all data saved in data base.
 * 
 * @author nbitas
 *
 */
public class SystemDetailsMenuGUI implements Initializable {
	// *********************************
	// Fields
	// *********************************
	@FXML
	private ComboBox<String> optionCombo;
	@FXML
	private GridPane grid;
	@FXML
	private AnchorPane ancor;

	private User principle;

	/**
	 * This method presents a choice to principal, what kind of data she wants to
	 * diaplsy. The appropriate data will appear on screen according to her choice.
	 */
	public void optionComboAction() {
		String choise = optionCombo.getValue();

		PrincipalController pc = new PrincipalController();
		switch (choise) {

		case "Student List":

			ListView<User> listView1 = new ListView<User>();
			ObservableList<User> list1 = FXCollections.observableArrayList(pc.getAllStudentsInData());
			listView1.setItems(list1);
			listView1.setEditable(false);
			listView1.setMouseTransparent(true);
			listView1.setFocusTraversable(false);
			grid.add(listView1, 0, 1);
			break;
		case "Teacher List":
			ListView<User> listView2 = new ListView<User>();
			listView2.setEditable(false);
			ObservableList<User> list2 = FXCollections.observableArrayList(pc.getAllTeachersInData());
			listView2.setItems(list2);
			listView2.setMouseTransparent(true);
			listView2.setFocusTraversable(false);
			grid.add(listView2, 0, 1);
			break;
		case "Course List":
			System.out.println("nndkjdfn");
			ListView<Course> listView3 = new ListView<Course>();
			listView3.setEditable(false);
			ObservableList<Course> list3 = FXCollections.observableArrayList(pc.getAllCoursesInData());
			listView3.setItems(list3);
			listView3.setMouseTransparent(true);
			listView3.setFocusTraversable(false);
			grid.add(listView3, 0, 1);
			break;
		case "Subject List":
			ListView<Subject> listView4 = new ListView<Subject>();
			listView4.setEditable(false);
			ObservableList<Subject> list4 = FXCollections.observableArrayList(pc.getAllSubjectsInData());
			listView4.setItems(list4);
			listView4.setMouseTransparent(true);
			listView4.setFocusTraversable(false);
			grid.add(listView4, 0, 1);
			break;
		case "Exam List":
			ListView<Exam> listView5 = new ListView<Exam>();
			listView5.setEditable(false);
			ObservableList<Exam> list5 = FXCollections.observableArrayList(pc.getAllExamsInData());
			listView5.setItems(list5);
			listView5.setMouseTransparent(true);
			listView5.setFocusTraversable(false);
			grid.add(listView5, 0, 1);
			break;
		case "Question List":
			ListView<Question> listView6 = new ListView<Question>();
			listView6.setEditable(false);
			ObservableList<Question> list6 = FXCollections.observableArrayList(pc.getAllQuestionsInData());
			listView6.setItems(list6);
			listView6.setMouseTransparent(true);
			listView6.setFocusTraversable(false);
			grid.add(listView6, 0, 1);
			break;
		case "Executed Exam List":
			ListView<ExamInExecution> listView7 = new ListView<ExamInExecution>();
			listView7.setEditable(false);
			ObservableList<ExamInExecution> list7 = FXCollections.observableArrayList(pc.getAllExamInExecution());
			listView7.setItems(list7);
			listView7.setMouseTransparent(true);
			listView7.setFocusTraversable(false);
			grid.add(listView7, 0, 1);
			break;
		case "Student Result In Exam":
			ListView<StudentInExam> listView8 = new ListView<StudentInExam>();
			listView8.setEditable(false);
			ObservableList<StudentInExam> list8 = FXCollections.observableArrayList(pc.getAllStudentInExam());
			listView8.setItems(list8);
			listView8.setMouseTransparent(true);
			listView8.setFocusTraversable(false);
			grid.add(listView8, 0, 1);
			break;

		}

	}

	/**
	 * initialize the system details fxml window
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		optionCombo.getItems().addAll("Student List", "Teacher List", "Subject List", "Course List", "Exam List",
				"Question List", "Student Result In Exam");
	}

	/**
	 * start to run the system details window
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("SystemDetailsMenu.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();
	}
}
