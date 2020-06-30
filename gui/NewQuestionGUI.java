package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.controlsfx.control.CheckComboBox;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import logic.Course;
import logic.Question;
import logic.Subject;
import logic.TeacherController;
import logic.User;

/**
 * this class manage the new question fxml window
 * 
 * @author vakni
 */
public class NewQuestionGUI implements Initializable {
	// **************************************************
	// Fields
	// **************************************************

	@FXML
	ComboBox<String> correctAnswerCombo;
	@FXML
	Button cancleButton;
	@FXML
	Button saveButton;
	@FXML
	TextArea QuestionLabel;
	@FXML
	TextArea answer1Label;
	@FXML
	TextArea answer2Label;
	@FXML
	TextArea answer3Label;
	@FXML
	TextArea answer4Label;
	@FXML
	Label teacherNameLabel;
	@FXML
	TextArea instructionLabel;

	@FXML
	Label qtxt;
	@FXML
	Label qans1;
	@FXML
	Label qans2;
	@FXML
	Label qans3;
	@FXML
	Label qans4;
	@FXML
	Label corAns;
	@FXML
	Label subjectcourseL;
	@FXML
	ComboBox<String> subjectCombo;
	@FXML
	CheckComboBox<String> courseCombo;

	Question q;

	// GUImanager m;
	User teacher;
	TeacherController tc;
	ObservableList<String> coursesL;

	// **************************************************
	// Constructors
	// **************************************************
	/**
	 * constructor that set the theacher that write the question
	 */
	public NewQuestionGUI() {

		// m=new GUImanager();
		tc = new TeacherController();
		teacher = tc.getTeacher();
	}

	// **************************************************
	// Public methods
	// **************************************************

	/**
	 * run the new question fxml window
	 */
	public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("NewQuestion.fxml"));
		Scene Scene = new Scene(root);
		Scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(Scene);
		primaryStage.show();

	}

	/**
	 * initialize the new question window data
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		teacherNameLabel.setText(teacher.getuName());
		teacherNameLabel.setDisable(true);
		tc.getTeacherCourse();
		for (int i = 0; i < tc.getSubjects().size(); i++)
			subjectCombo.getItems().add(tc.getSubjects().get(i).getsName());
		coursesL = FXCollections.observableArrayList();

		courseCombo.getItems().addAll(coursesL);
		correctAnswerCombo.getItems().addAll("1", "2", "3", "4");
	}

	/**
	 * get the fills details on the new question that the teacher wrote
	 */
	protected Question getFilledDetails() {
		int flag = 0;
		Question updatedQuestion = new Question();
		qtxt.setVisible(false);
		qans1.setVisible(false);
		qans2.setVisible(false);
		qans3.setVisible(false);
		qans4.setVisible(false);
		corAns.setVisible(false);

		updatedQuestion.setTeacherName(tc.getTeacher().getuName());
		updatedQuestion.setTeacherID(tc.getTeacher().getuID());
		if (QuestionLabel.getText().equals("")) {
			qtxt.setVisible(true);
			flag = 1;
		} else
			updatedQuestion.setQuestionTxt(QuestionLabel.getText());
		updatedQuestion.setInstruction(instructionLabel.getText());

		if (answer1Label.getText().equals("")) {
			qans1.setVisible(true);
			flag = 1;
		}
		if (answer2Label.getText().equals("")) {
			qans2.setVisible(true);
			flag = 1;
		}
		if (answer3Label.getText().equals("")) {
			qans3.setVisible(true);
			flag = 1;
		}
		if (answer4Label.getText().equals("")) {
			qans4.setVisible(true);
			flag = 1;
		}
		if (flag == 0)
			updatedQuestion.setAnswers(answer1Label.getText(), answer2Label.getText(), answer3Label.getText(),
					answer4Label.getText());
		if (correctAnswerCombo.getValue() == null) {
			corAns.setVisible(true);
			flag = 1;
		} else
			updatedQuestion.setCorrectAnswer(Integer.parseInt((String) correctAnswerCombo.getValue()));

		if (flag == 0)
			return updatedQuestion;
		else
			MyErrorMessage.show("Please fill all marked fields", "Missing details!");

		return null;
	}

	/**
	 * save the new question the teacher wrote
	 * 
	 * @param ae
	 * @throws Exception
	 */
	public void saveButtonAction(ActionEvent ae) throws Exception {
		System.out.println("save has been pressed");
		Question updatedQuestion = getFilledDetails();
		if (updatedQuestion == null)
			return;

		if (subjectCombo.getValue() == null || courseCombo.getCheckModel().getCheckedItems().isEmpty()) {
			System.out.println("no subject or course selected");
			subjectcourseL.setVisible(true);
			return;
		}
		Subject s = null;
		ArrayList<Course> selectedcourses;
		selectedcourses = new ArrayList<Course>();
		ArrayList<String> selected = new ArrayList<String>();

		for (String c : courseCombo.getCheckModel().getCheckedItems()) {
			selected.add(c);
		}
		int j;
		for (int i = 0; i < selected.size(); i++)
			for (j = 0; j < tc.getCourses().size(); j++)
				if (selected.get(i).equals(tc.getCourses().get(j).getcName())) {
					selectedcourses.add(tc.getCourses().get(j));
					break;
				}
		for (int i = 0; i < tc.getSubjects().size(); i++) {
			if (tc.getSubjects().get(i).getsName().equals(subjectCombo.getValue()))
				s = tc.getSubjects().get(i);
		}
		updatedQuestion.setCourseList(selectedcourses);
		updatedQuestion.setQuestionID(s.getSubjectID());
		q = tc.createNewQuestion(updatedQuestion);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("QuestionRepository.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		QuestionRepositoryGUI qrg = loader.getController();
		qrg.initData();
		Stage window = (Stage) cancleButton.getScene().getWindow();
		window.setScene(scene);
		window.show();

	}

	/**
	 * when the teacher cancle the function of create new question by press the
	 * cancel button
	 * 
	 * @param ae
	 * @throws Exception
	 */
	public void cancleButtonAction(ActionEvent ae) throws Exception {
		System.out.println("cancle has been pressed");
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("QuestionRepository.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		QuestionRepositoryGUI qrg = loader.getController();
		qrg.initData();
		Stage window = (Stage) cancleButton.getScene().getWindow();
		window.setScene(scene);
		window.show();
		// Stage stage = (Stage) cancleButton.getScene().getWindow();
		// //m.setSelectedQuestion(null);
		// QuestionRepositoryGUI qrg=new QuestionRepositoryGUI();
		// qrg.start(stage);
	}

	public void correctAnswerTextField(ActionEvent ae) {

	}

	/**
	 * handel the choose of the subject and the course of the question by the
	 * teacher after she choose the subject she can choose the courses of the
	 * subject that in the list
	 * 
	 * @param ae
	 */
	public void subjectComboBoxAction(ActionEvent ae) {
		int i;
		courseCombo.getItems().removeAll(coursesL);
		coursesL.clear();
		for (i = 0; i < tc.getCourses().size(); i++)
			if (tc.getCourses().get(i).getSubject().getsName().equals(subjectCombo.getValue()))
				coursesL.add(tc.getCourses().get(i).getcName());
		courseCombo.getItems().addAll(coursesL);

	}

	/**
	 * implements serializable
	 */
	public void initData() {
		// TODO Auto-generated method stub

	}

}