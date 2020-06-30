package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import logic.Course;
import logic.Exam;
import logic.Question;
import logic.TeacherController;

/**
 * This class allows the teacher to create a new exam from the question
 * repository
 * 
 * @author reut
 *
 */
public class CreateExamGUI implements Initializable {

	// **************************************************
	// Fields
	// **************************************************
	@FXML
	private TableView<QuestionGUI> table;
	@FXML
	private TableColumn<QuestionGUI, String> questionID;
	@FXML
	private TableColumn<QuestionGUI, String> questionText;
	@FXML
	private TableColumn<QuestionGUI, String> author;
	@FXML
	private TableColumn<QuestionGUI, CheckBox> selected;
	@FXML
	private TableColumn<QuestionGUI, TextField> pointsColumn;
	@FXML
	private TextArea studentInsructions;
	@FXML
	private TextArea teacherInstructions;
	@FXML
	private TextField duration;
	@FXML
	private Button cancleButton;
	@FXML
	private Button saveButton;
	@FXML
	private Label examIDLabel;
	@FXML
	private ComboBox<String> subjectCombo;
	@FXML
	private ComboBox<String> courseCombo;

	private ArrayList<Question> questionArr;
	private ObservableList<String> coursesL;
	ObservableList<QuestionGUI> questionsList;

	TeacherController tc;

	/**
	 * Constructor. create a new instance of teacher controller class
	 */
	public CreateExamGUI() {

		tc = new TeacherController();

	}

	// **************************************************
	// Public methods
	// **************************************************
	/**
	 * initiates data for the GUI window
	 * 
	 * @param exam
	 *            - parameter passed from prevoius window
	 */
	public void initData(Exam exam) {
		courseCombo.setDisable(true);
		questionsList = FXCollections.observableArrayList();
		questionID.setCellValueFactory(new PropertyValueFactory<QuestionGUI, String>("questionID"));
		questionText.setCellValueFactory(new PropertyValueFactory<QuestionGUI, String>("questionTxt"));
		author.setCellValueFactory(new PropertyValueFactory<QuestionGUI, String>("teacherName"));
		selected.setCellValueFactory(new PropertyValueFactory<QuestionGUI, CheckBox>("checkButton"));
		pointsColumn.setCellValueFactory(new PropertyValueFactory<QuestionGUI, TextField>("points"));

		tc.getTeacherCourse();
		for (int i = 0; i < tc.getSubjects().size(); i++)
			subjectCombo.getItems().add(tc.getSubjects().get(i).getsName());
		coursesL = FXCollections.observableArrayList();

		courseCombo.getItems().addAll(coursesL);
		questionArr = tc.getAllQuestions();
		setObservableListForTable(questionArr);
		if (exam != null)
			initateExamDetails(exam);

	}

	/**
	 * This method saves the data inputed into the exam form into an exam object.
	 */
	private void initateExamDetails(Exam exam) {
		duration.setText(Integer.toString(exam.getDuration()));
		teacherInstructions.setText(exam.getInstructionForTeacher());
		studentInsructions.setText(exam.getInstructionForStudent());
		// examIDLabel.setText(exam.getExamID());

		if (exam.getQuestionsArrayList() != null) {
			ArrayList<Question> questionsInExam = new ArrayList<Question>();

			for (int i = 0; i < questionArr.size(); i++) {

				Question tempQuestion = questionArr.get(i);
				for (int j = 0; j < questionsInExam.size(); j++) {
					if (tempQuestion.equals(questionsInExam.get(j)))
						questionsList.get(i).getPoints()
								.setText(Integer.toString(exam.getQuestions().get(tempQuestion)));
					questionsList.get(i).getCheckButton().setSelected(true);
				}
			}

		}
	}

	/**
	 * This method validates the input in the text fields.
	 */
	private boolean validateTextField(TextField tf) {
		try {

			int temp = Integer.parseInt(duration.getText());
			if (tf.getText() == null || tf.getText().length() == 0 || temp == 0)
				throw new NumberFormatException();

		} catch (NumberFormatException e) {
			MyErrorMessage.show("Text Field is not valid!\nPlease enter only numbers grater than 0", "Wrong Input");
			return false;
		}

		return true;
	}

	/**
	 * This method cancels the actions performed so far and resets the teacher main
	 * menu.
	 * 
	 * @throws Exception
	 */
	public void cancleButtonAction() throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ExamRepository.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ExamRepositoryGUI examRep = loader.getController();
		examRep.initData();
		Stage window = (Stage) cancleButton.getScene().getWindow();
		window.setScene(scene);
		window.show();

	}

	/**
	 * This method opens a display of the final exam form to approve.
	 * 
	 * @throws Exception
	 */
	public void saveButtonAction() throws Exception {
		if (courseCombo.getValue() == null || subjectCombo.getValue() == null) {
			MyErrorMessage.show("You must select subject and course!", "Incomplete details!");
			return;
		}
		if (!validateTextField(duration))
			return;

		HashMap<Question, Integer> temp = new HashMap<Question, Integer>();
		ArrayList<QuestionInExam> selectedQuestion = new ArrayList<QuestionInExam>();
		for (int i = 0, j = 0; i < questionsList.size(); i++) {
			if (questionsList.get(i).getCheckButton().isSelected()) {
				if (validateTextField(questionsList.get(i).getPoints())) {
					int pointsPerQuestion = Integer.parseInt(questionsList.get(i).getPoints().getText());
					selectedQuestion.add(new QuestionInExam(pointsPerQuestion, questionArr.get(i), ++j));
					temp.put(questionArr.get(i), pointsPerQuestion);
				} else {
					return;
				}
			}
		}
		if (selectedQuestion.size() == 0 || selectedQuestion == null) {
			MyErrorMessage.show("You must select at least one question!", "Incomplete details");
			return;
		}

		Exam exam = new Exam();
		exam.setDuration(Integer.parseInt(duration.getText()));
		exam.setTeacherName(tc.getTeacher().getuName());
		exam.setTeacherID(tc.getTeacher().getuID());
		exam.setQuestions(temp);
		exam.setInstructionForStudent(studentInsructions.getText());
		exam.setInstructionForTeacher(teacherInstructions.getText());
		Course c = tc.getCourseFromName((String) courseCombo.getValue());
		exam.setCourse(c);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ExamFormForTeacher.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ExamFormForTeacherGUI eFormForTeacher = loader.getController();
		eFormForTeacher.initData(exam, selectedQuestion);
		Stage stage = (Stage) table.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * This method opens a list of the assigned courses according to the selected
	 * subject.
	 * 
	 * @throws Exception
	 */
	public void subjectComboBoxAction(ActionEvent ae) {
		if (subjectCombo.getValue() != null)
			courseCombo.setDisable(false);
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
	 * This method opens a table view with the questions associated to course and
	 * subject selected
	 * 
	 * @param event
	 */
	@FXML
	public void courseComboBoxAction(ActionEvent event) {
		if (courseCombo.getValue() == null || subjectCombo.getValue() == null)
			return;
		else
			table.setVisible(true);
		String course = courseCombo.getValue();
		ArrayList<Question> arr = new ArrayList<>();
		for (int i = 0; i < questionArr.size(); i++) {
			ArrayList<Course> cl = questionArr.get(i).getCourseList();
			for (int j = 0; j < cl.size(); j++)
				if (cl.get(j).getcName().equals(course)) {
					arr.add(questionArr.get(i));
					break;
				}
		}

		setObservableListForTable(arr);
	}

	/**
	 * implement the Initializable
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	/**
	 * This method sets the observable list for the table
	 */
	private void setObservableListForTable(ArrayList<Question> arr) {
		questionsList.clear();
		for (int i = 0; i < arr.size(); i++) {
			CheckBox cb = new CheckBox();
			TextField tf = new TextField();
			cb.setVisible(true);
			tf.setVisible(true);
			QuestionGUI qgui = new QuestionGUI(arr.get(i).getQuestionID(), arr.get(i).getTeacherName(),
					arr.get(i).getQuestionTxt(), cb, tf);
			questionsList.add(qgui);
		}
		table.setItems(questionsList);
	}
}
