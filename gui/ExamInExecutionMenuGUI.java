package gui;

import java.io.IOException;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.ExamInExecution;
import logic.TeacherController;
/**
 * This class manage the exam in execution for the teacher
 * @author reut
 *
 */
public class ExamInExecutionMenuGUI implements Initializable {

	// **************************************************
    // Fields
    // **************************************************	
	@FXML
	private TableView<ExamInExecutionRow> examsTable;

	@FXML
	private TableColumn<ExamInExecutionRow, ImageView> imageCol;

	@FXML
	private TableColumn<ExamInExecutionRow, String> examIDCol;

	@FXML
	private TableColumn<ExamInExecutionRow, String> executionIDCol;

	@FXML
	private TableColumn<ExamInExecutionRow, String> courseNameCol;

	@FXML
	private TableView<ExamInExecutionRow> examsTableLocked;

	@FXML
	private TableColumn<ExamInExecutionRow, ImageView> imageColLocked;

	@FXML
	private TableColumn<ExamInExecutionRow, String> examIDColLocked;

	@FXML
	private TableColumn<ExamInExecutionRow, String> executionIDColLocked;

	@FXML
	private TableColumn<ExamInExecutionRow, String> courseNameColLocked;

	@FXML
	private TableView<ExamInExecutionRow> examsTableWritten;

	@FXML
	private TableColumn<ExamInExecutionRow, ImageView> imageColWritten;

	@FXML
	private TableColumn<ExamInExecutionRow, String> examIDColWritten;

	@FXML
	private TableColumn<ExamInExecutionRow, String> executionIDColWritten;

	@FXML
	private TableColumn<ExamInExecutionRow, String> courseNameColWritten;

	@FXML
	private Button executeNewExamBtn;

	@FXML
	private Button approveGradesViewBtn;

	private TeacherController tc;

	private ObservableList<ExamInExecutionRow> examArr;
	private ObservableList<ExamInExecutionRow> LockedExamArr;
	private ObservableList<ExamInExecutionRow> WrittenExamArr;
	private ArrayList<ExamInExecution> arr;
	private ArrayList<ExamInExecution> arrLocked;
	private ArrayList<ExamInExecution> arrWritten;

	// **************************************************
    // Constructors
    // **************************************************
	
	/**
	 * constructor the set the variabels of these class
	 */
	public ExamInExecutionMenuGUI() {
		tc = new TeacherController();
		examArr = FXCollections.observableArrayList();
		LockedExamArr = FXCollections.observableArrayList();
		WrittenExamArr = FXCollections.observableArrayList();
		arr = tc.getAllExamsInExecutionForTeacher();
		arrLocked = tc.getLockedExamsForTeacher();
		arrWritten = tc.getWrittenExamsForTeacher();

	}
	
	// **************************************************
    // Public methods
    // **************************************************

	/**
	 * implement the Initializable
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	/**
	 * this clas let the teacher choose exam in execution to preview that locked
	 * @param me
	 * @throws IOException
	 */
	@FXML
	public void chooseExamInExecutionL(MouseEvent me) throws IOException {
		if (me.getClickCount() == 2 && examsTableLocked.getSelectionModel().getSelectedItem() != null) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ExamInExecutionPreview.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage window = (Stage) examsTableLocked.getScene().getWindow();
			ExamInExecutionPreviewGUI examInExecMenu = loader.getController();
			examInExecMenu.initData(examsTableLocked.getSelectionModel().getSelectedItem(), "locked", window);

			window.setScene(scene);
			window.show();
		}
	}

	/**
	 * this method let the teacher choose exam in execution to preview that written
	 * @param me
	 * @throws IOException
	 */
	@FXML
	public void chooseExamInExecutionW(MouseEvent me) throws IOException {
		if (me.getClickCount() == 2 && examsTableWritten.getSelectionModel().getSelectedItem() != null) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ExamInExecutionPreview.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage window = (Stage) examsTableWritten.getScene().getWindow();
			ExamInExecutionPreviewGUI examInExecMenu = loader.getController();
			examInExecMenu.initData(examsTableWritten.getSelectionModel().getSelectedItem(), "written", window);
			window.setScene(scene);
			window.show();
		}
	}

	/**
	 * this method let the teacher choose exam in execution to preview that open
	 * @param me
	 * @throws IOException
	 */
	@FXML
	public void chooseExamInExecution(MouseEvent me) throws IOException {
		if (me.getClickCount() == 2 && examsTable.getSelectionModel().getSelectedItem() != null) {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("ExamInExecutionPreview.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage window = (Stage) examsTable.getScene().getWindow();
			ExamInExecutionPreviewGUI examInExecMenu = loader.getController();
			examInExecMenu.initData(examsTable.getSelectionModel().getSelectedItem(), "open", window);

			window.setScene(scene);
			window.show();
		}
	}

	/**
	 * this method handel the action when the teacher want to aprove the grade of the exam
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void approveGradesViewBtnAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ApproveGrades.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ApproveGradesGUI approveG = loader.getController();
		Stage window = (Stage) examsTable.getScene().getWindow();
		approveG.initData(window);
		window.setScene(scene);
		window.show();

	}

	/**
	 * this method let the teacher execute new exam
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void executeNewExamBtnAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ExecuteNewExam.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ExecuteNewExamGUI executeNewExam = loader.getController();
		executeNewExam.initData();
		Stage window = (Stage) examsTable.getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	/**
	 * this method set all the deatails of this window that show the exam in exection for the teacher in table 
	 */
	public void initData() {
		examIDCol.setCellValueFactory(new PropertyValueFactory<>("examID"));
		executionIDCol.setCellValueFactory(new PropertyValueFactory<>("executionID"));
		courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		imageCol.setCellValueFactory(new PropertyValueFactory<>("preview"));
		int i;
		for (i = 0; i < arr.size(); i++) {
			ImageView im = new ImageView(new Image("/images/preview.png"));
			im.setVisible(true);
			im.setFitHeight(30);
			im.setFitWidth(30);
			ExamInExecutionRow e = new ExamInExecutionRow(arr.get(i), im);
			examArr.add(e);
		}
		examsTable.setItems(examArr);
		examIDColLocked.setCellValueFactory(new PropertyValueFactory<>("examID"));
		executionIDColLocked.setCellValueFactory(new PropertyValueFactory<>("executionID"));
		courseNameColLocked.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		imageColLocked.setCellValueFactory(new PropertyValueFactory<>("preview"));

		for (i = 0; i < arrLocked.size(); i++) {
			ImageView im = new ImageView(new Image("/images/padlock.png"));
			im.setVisible(true);
			im.setFitHeight(30);
			im.setFitWidth(30);
			ExamInExecutionRow e = new ExamInExecutionRow(arrLocked.get(i), im);
			LockedExamArr.add(e);
		}
		examsTableLocked.setItems(LockedExamArr);

		examIDColWritten.setCellValueFactory(new PropertyValueFactory<>("examID"));
		executionIDColWritten.setCellValueFactory(new PropertyValueFactory<>("executionID"));
		courseNameColWritten.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		imageColWritten.setCellValueFactory(new PropertyValueFactory<>("preview"));

		for (i = 0; i < arrWritten.size(); i++) {
			ImageView im = new ImageView(new Image("/images/edit.png"));
			im.setVisible(true);
			im.setFitHeight(30);
			im.setFitWidth(30);
			ExamInExecutionRow e = new ExamInExecutionRow(arrWritten.get(i), im);
			WrittenExamArr.add(e);
		}
		examsTableWritten.setItems(WrittenExamArr);

	}

}
