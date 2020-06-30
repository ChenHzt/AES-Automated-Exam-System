package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.ToggleSwitch;
import org.controlsfx.control.textfield.CustomTextField;

import client.ClientConsole;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import logic.Exam;
import logic.ExamInExecution;
import logic.StudentInExam;
import logic.TeacherController;
import logic.User;

public class ExecuteNewExamGUI implements Initializable {

	// **************************************************
	// Fields
	// **************************************************
	@FXML
	private TableView<ExamInExecutionRow> table;

	@FXML
	private TableColumn<ExamInExecutionRow, ImageView> previewCol;

	@FXML
	private TableColumn<ExamInExecutionRow, String> examIDCol;

	@FXML
	private TableColumn<ExamInExecutionRow, String> teacherNameCol;

	@FXML
	private TableColumn<ExamInExecutionRow, String> cNameCol;

	@FXML
	private TableColumn<ExamInExecutionRow, Integer> durationCol;

	@FXML
	private TableColumn<ExamInExecutionRow, RadioButton> selectExamCol;

	@FXML
	private ComboBox<String> subjectCombo;

	@FXML
	private ComboBox<String> courseCombo;

	@FXML
	private Button searchButton;

	@FXML
	private Button backBtn;

	@FXML
	private Button nextBtn;

	@FXML
	private TextField searchByExamTF;

	@FXML
	private RadioButton searchByCourseRadio;

	@FXML
	private RadioButton searchByExamIDRadio;

	@FXML
	private Button confirmBtn;

	@FXML
	private Button cancleBtn;

	@FXML
	private TableView<UserRow> studentTable;

	@FXML
	private TableColumn<UserRow, CheckBox> checkboxCol;

	@FXML
	private TableColumn<UserRow, String> sIDCol;

	@FXML
	private TableColumn<UserRow, String> sNameCol;

	@FXML
	private Button selectExamBtn;

	@FXML
	private CustomTextField examCodeTF;

	@FXML
	private ToggleSwitch isGroupToggle;

	@FXML
	private TextArea infoMessage;

	TeacherController tc;

	ObservableList<String> coursesL;

	ObservableList<UserRow> students;

	ClientConsole client;

	private ArrayList<Exam> examsList;

	final ToggleGroup groupSearchOption;
	final ToggleGroup groupExamSelection;

	private ObservableList<ExamInExecutionRow> examOL;

	ExamInExecutionRow chosenExam;
	private ArrayList<User> studentList;
	ImageView warningImage;
	ImageView okImage;
	Boolean examCodeFlag;
	ArrayList<StudentInExam> selectedStudentsList;

	// **************************************************
	// Constructors
	// **************************************************
	/**
	 * constructor the set the variabels of these class
	 */
	public ExecuteNewExamGUI() {
		super();
		tc = new TeacherController();
		groupSearchOption = new ToggleGroup();
		groupExamSelection = new ToggleGroup();
		studentList = new ArrayList<User>();
		examCodeFlag = false;
		selectedStudentsList = new ArrayList<StudentInExam>();
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
	 * search for exam by course or exam id
	 * 
	 * @param event
	 */
	@FXML
	void searchButtonAction(ActionEvent event) {

		RadioButton button = (RadioButton) groupSearchOption.getSelectedToggle();

		if (button.getText().equals("choose examID:")) {
			examOL.clear();
			for (int i = 0; i < examsList.size(); i++) {
				if (examsList.get(i).getExamID().equals(searchByExamTF.getText())) {
					ExamInExecutionRow e = new ExamInExecutionRow();
					ImageView im = new ImageView(new Image("/images/preview.png"));
					im.setVisible(true);
					im.setFitHeight(30);
					im.setFitWidth(30);
					e.setPreview(im);
					e.setAuthorTeacherName(examsList.get(i).getTeacherName());
					e.setDuration(examsList.get(i).getDuration());
					e.setCourseName(examsList.get(i).getCourseName());
					e.setExamID(examsList.get(i).getExamID());
					e.setCourseID(examsList.get(i).getCourse().getcID());
					e.setSubjectID(examsList.get(i).getCourse().getSubject().getSubjectID());
					RadioButton rb = new RadioButton();
					rb.setVisible(true);
					rb.setText(e.getExamID());
					e.setSelectExamRB(rb);
					e.getSelectExamRB().setToggleGroup(groupExamSelection);
					e.getSelectExamRB().setOnAction(ev -> selectExam());
					examOL.add(e);
				}
			}
		} else {
			examOL.clear();
			for (int i = 0; i < examsList.size(); i++) {
				if (courseCombo.getValue() != null && examsList.get(i).getCourseName().equals(courseCombo.getValue())) {
					ExamInExecutionRow e = new ExamInExecutionRow();
					ImageView im = new ImageView(new Image("/images/preview.png"));
					im.setVisible(true);
					im.setFitHeight(30);
					im.setFitWidth(30);
					e.setPreview(im);
					e.setAuthorTeacherName(examsList.get(i).getTeacherName());
					e.setDuration(examsList.get(i).getDuration());
					e.setCourseName(examsList.get(i).getCourseName());
					e.setExamID(examsList.get(i).getExamID());
					e.setCourseID(examsList.get(i).getCourse().getcID());
					e.setSubjectID(examsList.get(i).getCourse().getSubject().getSubjectID());
					RadioButton rb = new RadioButton();
					rb.setVisible(true);
					rb.setText(e.getExamID());
					e.setSelectExamRB(rb);
					e.getSelectExamRB().setToggleGroup(groupExamSelection);
					e.getSelectExamRB().setOnAction(ev -> selectExam());
					examOL.add(e);
				}
			}
		}

	}

	/**
	 * show in course combo box all courses of selected subject
	 * 
	 * @param event
	 */
	@FXML
	public void chooseSubject(ActionEvent event) {
		int i;
		courseCombo.getItems().removeAll(coursesL);
		for (i = 0; i < coursesL.size(); i++)
			coursesL.remove(i);
		for (i = 0; i < tc.getCourses().size(); i++)
			if (tc.getCourses().get(i).getSubject().getsName().equals(subjectCombo.getValue()))
				coursesL.add(tc.getCourses().get(i).getcName());
		courseCombo.getItems().addAll(coursesL);
	}

	/**
	 * save all the info the teacher selected and execute the exam
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void confirmBtnAction(ActionEvent event) throws IOException {

		ExamInExecution e = new ExamInExecution();
		ExamInExecutionRow ex = new ExamInExecutionRow();
		ex = chosenExam;
		if (chosenExam == null)
			return;
		e.setExecTeacher(tc.getTeacher());
		e.setLocked(false);
		e.setCourseID(ex.getCourseID());
		e.setSubjectID(ex.getSubjectID());
		e.getExamDet().setDuration(ex.getDuration());
		e.getExamDet().setExamID(ex.getExamID());
		e.getExamDet().setCourseName(ex.getCourseName());
		e.getExamDet().setTeacherName(ex.getAuthorTeacherName());
		e.setIsGroup(isGroupToggle.isSelected());
		if (examCodeFlag)
			e.setExamCode(examCodeTF.getText());
		else
			return;
		if (isGroupToggle.isSelected()) {
			for (UserRow u : students) {
				if (u.getCheck().isSelected()) {
					StudentInExam s = new StudentInExam();
					s.setStudentID(u.getUserID());
					s.setStudentName(u.getUserName());
					selectedStudentsList.add(s);
				}
			}
			e.setStudents(selectedStudentsList);
			if (e.getStudents().size() == 0) {

				MyErrorMessage.show("You must select students to group!", "No selection");
				return;
			}
		}
		e = tc.executeNewExam(e);
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("examInExecutionMenu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ExamInExecutionMenuGUI examInExecMenu = loader.getController();
		examInExecMenu.initData();
		Stage window = (Stage) confirmBtn.getScene().getWindow();
		window.setScene(scene);
		window.show();
		System.out.println("just checking");
	}

	/**
	 * this method close thecurrent window and discards all changes
	 * 
	 * @param event
	 * @throws IOException
	 */
	public void cancleBtnAction(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ExamInExecutionMenu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ExamInExecutionMenuGUI examInExecMenu = loader.getController();
		examInExecMenu.initData();
		Stage window = (Stage) cancleBtn.getScene().getWindow();
		window.setScene(scene);
		window.show();
	}

	/**
	 * this method saves the details of the chosen exam to execute
	 */
	public void selectExam() {
		RadioButton r = new RadioButton();
		r = (RadioButton) groupExamSelection.getSelectedToggle();
		for (ExamInExecutionRow examInExec : examOL) {
			if (examInExec.getExamID().equals(r.getText()))
				chosenExam = examInExec;
		}
		table.getItems().removeAll(students);
		students.clear();
		studentList = tc.getAllStudentsInCourse(chosenExam.getSubjectID(), chosenExam.getCourseID());
		for (int i = 0; i < studentList.size(); i++) {
			CheckBox c = new CheckBox();
			c.setVisible(true);
			UserRow u = new UserRow(studentList.get(i), c);
			students.add(u);
		}
		studentTable.setItems(students);
	}

	/**
	 * this method checks if the exam code that teacher entered is valid
	 * 
	 * @param event
	 */
	@FXML
	public void examCodeAction(KeyEvent event) {
		System.out.println("text is:" + examCodeTF.getText());
		char[] c;
		if (examCodeTF.getText().length() < 4 || examCodeTF.getText().length() > 4) {
			examCodeTF.setLeft(warningImage);
			examCodeFlag = false;
			return;
		}
		for (int i = 0; i < examCodeTF.getText().length(); i++) {
			c = examCodeTF.getText().toCharArray();
			if (!Character.isLetterOrDigit(c[i])) {
				examCodeFlag = false;
				examCodeTF.setLeft(warningImage);
				return;
			}
		}
		examCodeTF.setLeft(okImage);
		examCodeFlag = true;
		System.out.println("everything gonna be alright");
	}

	/**
	 * checks if teacher chose that the exam will be open to all studens in course
	 * or to selected students only
	 */
	public void isGroupToggeleAction() {
		if (isGroupToggle.isSelected()) {
			studentTable.setVisible(true);
		} else
			studentTable.setVisible(false);
	}

	/**
	 * shows label with instruction for choosing exam code
	 * 
	 * @param event
	 */
	@FXML
	public void infoEntered(MouseEvent event) {
		infoMessage.setVisible(true);
	}

	/**
	 * hides the label with instruction for choosing exam code
	 * 
	 * @param event
	 */
	@FXML
	public void infoExit(MouseEvent event) {
		infoMessage.setVisible(false);
	}

	/**
	 * this method initiates window data
	 */
	public void initData() {
		tc.getTeacherCourse();
		for (int i = 0; i < tc.getSubjects().size(); i++)
			subjectCombo.getItems().add(tc.getSubjects().get(i).getsName());
		coursesL = FXCollections.observableArrayList();
		examOL = FXCollections.observableArrayList();
		examsList = tc.getAllExams();
		for (int i = 0; i < examsList.size(); i++) {
			ExamInExecutionRow e = new ExamInExecutionRow();
			ImageView im = new ImageView(new Image("/images/preview.png"));
			im.setVisible(true);
			im.setFitHeight(30);
			im.setFitWidth(30);
			e.setPreview(im);
			e.setAuthorTeacherName(examsList.get(i).getTeacherName());
			e.setDuration(examsList.get(i).getDuration());
			e.setCourseName(examsList.get(i).getCourseName());
			e.setExamID(examsList.get(i).getExamID());
			e.setCourseID(examsList.get(i).getCourse().getcID());
			e.setSubjectID(examsList.get(i).getCourse().getSubject().getSubjectID());
			RadioButton rb = new RadioButton();
			rb.setVisible(true);
			rb.setText(e.getExamID());
			e.setSelectExamRB(rb);
			e.getSelectExamRB().setToggleGroup(groupExamSelection);
			e.getSelectExamRB().setOnAction(ev -> selectExam());
			examOL.add(e);
		}
		previewCol.setCellValueFactory(new PropertyValueFactory<>("preview"));
		selectExamCol.setCellValueFactory(new PropertyValueFactory<>("selectExamRB"));
		examIDCol.setCellValueFactory(new PropertyValueFactory<>("examID"));
		teacherNameCol.setCellValueFactory(new PropertyValueFactory<>("authorTeacherName"));
		cNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
		table.setItems(examOL);
		searchByCourseRadio.setToggleGroup(groupSearchOption);
		searchByExamIDRadio.setToggleGroup(groupSearchOption);
		searchByExamIDRadio.setSelected(true);
		students = FXCollections.observableArrayList();
		studentList = new ArrayList<User>();
		checkboxCol.setCellValueFactory(new PropertyValueFactory<>("check"));
		sIDCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
		sNameCol.setCellValueFactory(new PropertyValueFactory<>("userName"));
		warningImage = new ImageView("/images/warning.png");
		warningImage.setFitHeight(20);
		warningImage.setFitWidth(20);
		okImage = new ImageView("/images/ok.png");
		okImage.setFitHeight(20);
		okImage.setFitWidth(20);
		examCodeTF.setLeft(warningImage);

	}

}