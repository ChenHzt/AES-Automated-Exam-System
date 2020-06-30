package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.Exam;
import logic.TeacherController;

/**
 * This class allows the teacher to manage a question repository including
 * adding new question, deleting a question and editing a question.
 * @author reut
 *
 */
public class ExamRepositoryGUI implements Initializable {
	// **************************************************
	// Fields
	// **************************************************
	@FXML
	private TableView<Exam> table;
	@FXML
	private TableColumn<Exam, String> examIDCol;
	@FXML
	private TableColumn<Exam, String> cNameCol;
	@FXML
	private TableColumn<Exam, String> teacherNameCol;
	@FXML
	private TableColumn<Exam, String> durationCol;
	@FXML
	private Button editButton;
	@FXML
	private Button insertButton;

	private ArrayList<Exam> arr;
	private ObservableList<Exam> examList;

	private TeacherController tc;

	// **************************************************
    // Public methods
    // **************************************************
	/**
	 * This method allows the teacher to create a new exam from the questions
	 * assigned to his courses, taken from question repository.
	 * @throws Exception
	 */
	public void insertButtonAction(ActionEvent ae) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("CreateExam.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		CreateExamGUI newExam = loader.getController();
		newExam.initData(null);
		Stage stage = (Stage) table.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * This method allows the teacher to delete a new exam from the exam repository.
	 * 
	 * @throws Exception
	 */
	public void deleteButtonAction(ActionEvent ae) {
		Exam tToDel = (Exam) table.getSelectionModel().getSelectedItem();
		if (tToDel == null) {
			MyErrorMessage.show("Select an exam!", "No selection");
			return;

		}
		tc.deleteExam(tToDel);
		for (int i = 0; i < examList.size(); i++)
			if (examList.get(i).getExamID().equals(tToDel.getExamID())) {
				examList.remove(i);
				break;
			}
	}
	
	
	/**
	 * implement the Initializable
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * set all the data of the exam in the window fxml
	 */
	public void initData() {
		tc = new TeacherController();
		examIDCol.setCellValueFactory(new PropertyValueFactory<>("ExamID"));
		cNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		teacherNameCol.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
		durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
		arr = tc.getAllExams();
		System.out.println("size of exam array: " + arr.size());
		examList = FXCollections.observableArrayList();
		examList.addAll(arr);
		table.setItems(examList);

	}
}
