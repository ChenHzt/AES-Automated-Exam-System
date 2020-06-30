package gui;

import java.io.IOException;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logic.StudentInExam;
import logic.TeacherController;

public class ApproveGradesGUI {

	@FXML
	private TableView<StudentInExam> table; // table of all student's grades that waiting for approval

	@FXML
	private TableColumn<StudentInExam, String> examIDCol; // examID column of student grade

	@FXML
	private TableColumn<StudentInExam, Integer> execIDCol; // executionID column of student grade

	@FXML
	private TableColumn<StudentInExam, String> sNameCol; // student name column of student grade

	@FXML
	private TableColumn<StudentInExam, Integer> gradeCol;// grade column

	@FXML
	private TableColumn<StudentInExam, Boolean> isCopiedCol; // column that show if there is suspicion for copy

	@FXML
	private Button approveBtn; // approve student grade button

	@FXML
	private Button changeBtn;// change student grade button

	private TeacherController tc;

	private ObservableList<StudentInExam> studentsGradesOL; // list of all students grades that needs approval

	private Stage stage;// this window stage

	/**
	 * this method sent message to server that the selected grade is approved
	 * 
	 * @param event
	 */
	@FXML
	public void approveBtnAction(ActionEvent event) {
		if (table.getSelectionModel().getSelectedItem() == null)
			return;
		StudentInExam s = table.getSelectionModel().getSelectedItem();
		tc.approveGrade(s);
		int i = 0;
		for (Iterator<StudentInExam> iterator = studentsGradesOL.iterator(); iterator.hasNext(); i++) {
			StudentInExam student = iterator.next();
			if (student.getExamID().equals(s.getExamID()) && student.getExecutionID() == s.getExecutionID()
					&& student.getStudentID().equals(s.getStudentID()))
				i = studentsGradesOL.indexOf(s);
		}
		studentsGradesOL.remove(i);
		table.getItems().clear();
		table.getItems().addAll(studentsGradesOL);
	}

	/**
	 * this method open dialog to get the new grade and the explanation and sends
	 * message to server to update the grade
	 * 
	 * @param event
	 * @throws IOException
	 */
	@FXML
	public void changeBtnAction(ActionEvent event) throws IOException {
		if (table.getSelectionModel().getSelectedItem() == null)
			return;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ChangeGradeDialog.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ChangeGradeDialogGUI changeG = loader.getController();
		changeG.initData();
		Stage window = new Stage();
		window.initModality(Modality.WINDOW_MODAL);
		window.setScene(scene);
		window.setOnCloseRequest(e -> {
			e.consume();
			changeG.cancleButton(null);
		});
		window.initOwner(this.stage);
		window.showAndWait();
		if (changeG.result) {
			System.out.println("c1");
			StudentInExam s = table.getSelectionModel().getSelectedItem();
			System.out.println("c2");
			s.setGrade(changeG.newGrade);
			System.out.println("c3");
			s.setReasonForGradeChenges(changeG.reason);
			System.out.println("c4");
			tc.changeGrade(s);
			System.out.println("c5");
			int i = 0;
			for (Iterator<StudentInExam> iterator = studentsGradesOL.iterator(); iterator.hasNext();) {
				StudentInExam student = iterator.next();
				if (student.getExamID().equals(s.getExamID()) && student.getExecutionID() == s.getExecutionID()
						&& student.getStudentID().equals(s.getStudentID()))
					i = studentsGradesOL.indexOf(s);
			}
			studentsGradesOL.remove(i);
			table.getItems().clear();
			table.getItems().addAll(studentsGradesOL);
		}

	}

	/**
	 * this method initiates the window information about grades approval requests
	 */
	public void initData(Stage window) {
		this.stage = window;
		examIDCol.setCellValueFactory(new PropertyValueFactory<StudentInExam, String>("examID"));
		execIDCol.setCellValueFactory(new PropertyValueFactory<StudentInExam, Integer>("ExecutionID"));
		sNameCol.setCellValueFactory(new PropertyValueFactory<StudentInExam, String>("studentName"));
		gradeCol.setCellValueFactory(new PropertyValueFactory<StudentInExam, Integer>("grade"));
		isCopiedCol.setCellValueFactory(new PropertyValueFactory<StudentInExam, Boolean>("isCopied"));
		tc = new TeacherController();
		studentsGradesOL = FXCollections.observableArrayList();
		studentsGradesOL.addAll(tc.getAllGradesForApprval());
		table.getItems().addAll(studentsGradesOL);
	}

}
