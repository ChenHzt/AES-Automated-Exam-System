package gui;

import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import logic.Exam;
import logic.TeacherController;

/**
 * This class provides the teacher a review of the details inputed into the
 * crate exam window and awaits final approval.
 * 
 * @author reut
 *
 */
public class ExamFormForTeacherGUI implements Initializable {

	// **************************************************
	// Fields
	// **************************************************
	private ObservableList<QuestionInExam> observableQuestions;

	@FXML
	private TextArea insrtuctionsForStudent;
	@FXML
	private TextArea insrtuctionsForTeacher;
	@FXML
	private TextField duration;
	@FXML
	private Label examIDLabel;
	@FXML
	private Label teacherNameLabel;
	@FXML
	private TextField subjectName;
	@FXML
	private TextField courseName;
	@FXML
	private ListView<QuestionInExam> listView;
	@FXML
	private Button cancleButton;
	@FXML
	private Button saveButton;
	private Exam exam;
	private TeacherController tc;

	/**
	 * this method set the exam parameters on the exam form to perform
	 * 
	 * @param exam
	 *            - the exam we want to show
	 * @param selectedQuestions
	 *            - ArrayList<QuestionInExam> with the exam's questions
	 */
	public void initData(Exam exam, ArrayList<QuestionInExam> selectedQuestions) {
		tc = new TeacherController();
		this.exam = exam;
		courseName.setText(exam.getCourse().getcName());
		subjectName.setText(exam.getCourse().getSubject().getsName());
		insrtuctionsForTeacher.setText(exam.getInstructionForTeacher());
		insrtuctionsForStudent.setText(exam.getInstructionForStudent());
		duration.setText(Integer.toString(exam.getDuration()));
		String id = tc.getExamID(exam.getCourse());
		examIDLabel.setText(id);
		exam.setExamID(id);
		teacherNameLabel.setText(exam.getTeacherName());

		observableQuestions = FXCollections.observableArrayList(selectedQuestions);
		listView.setItems(observableQuestions);
		listView.setCellFactory(QuestionListView -> new QuestionListViewCellForTeacher<QuestionInExam>());
		System.out.println("fdfd");
	}

	/**
	 * implement the Initializable
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

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
	 * This method saves the exam into the database.
	 * 
	 * @throws Exception
	 */
	public void saveButtonAction() {
		exam.setWasUsed(false);
		tc.saveExam(exam);
		Stage window = (Stage) cancleButton.getScene().getWindow();
		window.close();

	}
}
