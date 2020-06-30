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
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logic.ExamInExecution;
import logic.StudentController;

/**
 * this class manage the fxml of Computerized Exam
 * @author Rotem Vaknin
 *
 */
public class ComputerizedExamGUI implements Initializable {

	// **************************************************
    // Fields
    // **************************************************
	@FXML
	Label examID;
	@FXML
	Label courseName;
	@FXML
	Button okButton;
	@FXML
	TextField insertExamCode;
	@FXML
	private Label errorL;

	ExamInExecution exam;

	String examCode;

	@FXML
	private Text enterIDLabel;

	@FXML
	private TextField idTF;

	@FXML
	private ImageView okImgID;

	@FXML
	private Button okButtonID;

	StudentController st;
	private Stage examFormStage;
	ExamFormForStudentGUI ExamForStudent;

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
	 * set al the data on the Computerized exam window
	 * @param examInExecution
	 */
	public void initData(ExamInExecution examInExecution) {
		exam = examInExecution;
		examID.setText(exam.getExamDet().getExamID());
		examID.setDisable(true);
		courseName.setText(exam.getCourseName());
		courseName.setDisable(true);

	}

	/**
	 * this method handle the ok button of the user and check if the exam code that the user entered is correct
	 * @throws IOException
	 */
	public void okButtonAction() throws IOException {
		System.out.println("c0");
		examCode = insertExamCode.getText();

		if (examCode.equals("")) // check if the student entered ok before enter the exam data
		{
			/// if the student enter 'ok' and didn't enter data
			errorL.setText("You have to enter the exam code first");
		}

		else {
			/// if the student enter the write exam code
			if (examCode.equals(exam.getExamCode())) {
				System.out.println("exam code is correct");
				okButtonID.setVisible(true);
				okImgID.setVisible(true);
				idTF.setVisible(true);
				enterIDLabel.setVisible(true);

				st = new StudentController();
				ExamInExecution examToPerform = st.performCompExam(exam); // to get the questions details of the exam
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("ExamFormForStudent.fxml"));
				Parent root = loader.load();
				Scene scene = new Scene(root);
				ExamForStudent = loader.getController();
				ExamForStudent.initData(examToPerform, true, 0);
				this.examFormStage = new Stage();
				examFormStage.hide();
				examFormStage.setOnCloseRequest(e -> {
					if (!ExamForStudent.isSubmittedOrCancelled)
						e.consume();
				});
				examFormStage.setScene(scene);

			}
			// if the student didnt write the good code
			else {
				errorL.setText("The exam code is incorrect!");
			}
		}

	}

	/**
	 * this method check the user ID , if the ID is correct it will open the exam form and the student will be able 
	 * to answer the exam
	 */
	@FXML
	void okButtonIDAction(ActionEvent event) {
		if (idTF.getText().equalsIgnoreCase(st.getStudent().getuID())) {
			examFormStage.show();
			ExamForStudent.startCountDown();
			Stage s = (Stage) okButton.getScene().getWindow();
			s.close();
		} else
			errorL.setText("wrong ID");

	}

}
