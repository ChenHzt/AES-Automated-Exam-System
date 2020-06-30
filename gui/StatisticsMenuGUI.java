package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import logic.Course;
import logic.ExamReport;
import logic.PrincipalController;
import logic.StudentInExam;
import logic.TeacherController;
import logic.User;

/**
 * This class is the controller for for viewing statistic reports. principal can
 * view median, average, and histogram on exam grade sorted by: teacher, student
 * and course.
 * 
 * @author nbitas
 *
 */
public class StatisticsMenuGUI implements Initializable {
	// *********************************
	// Fields
	// *********************************
	@FXML
	private Label medianLabel;

	@FXML
	private Label avgLabel;

	@FXML
	private ComboBox<String> reportCombo;

	@FXML
	private ComboBox<User> studentCombo;

	@FXML
	private ComboBox<User> teacherCombo;

	@FXML
	private ComboBox<ExamReport> examCombo;

	@FXML
	private ComboBox<Course> courseCombo;

	@FXML
	private GridPane grid;

	BarChart<String, Number> barChart = null;
	private ArrayList<User> teacherL;
	private ArrayList<User> studentL;
	private ArrayList<Course> courseL;
	private PrincipalController pc;

	/**
	 * This method presents the report for the selected exam.
	 */
	@FXML
	void examComboAction(ActionEvent event) {
		if (barChart != null) {
			grid.getChildren().remove(barChart);
			medianLabel.setVisible(false);
			avgLabel.setVisible(false);
		}
		ExamReport reportToDisplay = examCombo.getValue();
		if (reportToDisplay == null)
			return;

		initiateReport(reportToDisplay);
	}

	/**
	 * This method chooses the student for which to show the report.
	 * 
	 * @param event
	 */
	@FXML
	void studentComboAction(ActionEvent event) {
		if (barChart != null) {
			grid.getChildren().remove(barChart);
			medianLabel.setVisible(false);
			avgLabel.setVisible(false);
		}
		User temp = studentCombo.getValue();
		if (temp == null)
			return;
		ArrayList<StudentInExam> studentGrades = pc.getAllExamReportsStudentPerformed(temp);
		ArrayList<Integer> gradeArr = new ArrayList<Integer>();
		if (studentGrades == null)
			return;
		for (int i = 0; i < studentGrades.size(); i++) {
			gradeArr.add(studentGrades.get(i).getGrade());
		}
		ExamReport er = new ExamReport(gradeArr, null, 0);
		initiateReport(er);
	}

	/**
	 * This method chooses the course for which to show the report.
	 */
	@FXML
	void courseComboAction(ActionEvent event) {
		if (barChart != null) {
			grid.getChildren().remove(barChart);
			medianLabel.setVisible(false);
			avgLabel.setVisible(false);
		}
		examCombo.getItems().clear();
		examCombo.setPromptText("Choose Exam");
		Course temp = courseCombo.getValue();
		if (temp == null)
			return;
		ArrayList<ExamReport> arr = pc.getAllExamsInCourse(temp);
		examCombo.getItems().clear();
		examCombo.getItems().addAll(arr);
	}

	/**
	 * This method chooses the teacher for which to show the report.
	 */
	@FXML
	void teacherComboAction(ActionEvent event) {
		if (barChart != null) {
			grid.getChildren().remove(barChart);
			medianLabel.setVisible(false);
			avgLabel.setVisible(false);
		}
		User temp = teacherCombo.getValue();
		if (temp == null)
			return;
		examCombo.getItems().clear();
		examCombo.setPromptText("Choose Exam");
		ArrayList<ExamReport> arr = pc.getAllExamReportsTeacherWrote(temp);
		examCombo.getItems().addAll(arr);
	}

	/**
	 * This method enables the choice of report: student, teacher, course.
	 */
	@FXML
	void reportComboAction(ActionEvent event) {
		if (barChart != null) {
			grid.getChildren().remove(barChart);
			medianLabel.setVisible(false);
			avgLabel.setVisible(false);
		}
		examCombo.getItems().clear();
		examCombo.setPromptText("Choose Exam");
		String temp = reportCombo.getValue();
		if (temp == null)
			return;
		if (temp.equals("Student")) {
			studentCombo.setVisible(true);
			courseCombo.setVisible(false);
			teacherCombo.setVisible(false);
			examCombo.setVisible(false);
			studentCombo.setPromptText("Student List");
		} else if (temp.equals("Teacher")) {
			studentCombo.setVisible(false);
			courseCombo.setVisible(false);
			teacherCombo.setVisible(true);
			examCombo.setVisible(true);
			teacherCombo.setPromptText("Teacher List");
		} else {
			studentCombo.setVisible(false);
			courseCombo.setVisible(true);
			teacherCombo.setVisible(false);
			examCombo.setVisible(true);
			courseCombo.setPromptText("Course List");
		}
	}

	/**
	 * this method initialize the data on the statistics menue
	 * 
	 * @param arg0
	 * @param arg1
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	/**
	 * This method used to pass parameters between UI windows
	 * 
	 * @param user
	 */
	public void initData(User user) {
		medianLabel.setVisible(false);
		avgLabel.setVisible(false);
		if (user.getTitle().equals("Teacher")) {
			// teacher can also watch statistic reports for exam she wrote
			TeacherController tc = new TeacherController();
			teacherCombo.setDisable(true);
			studentCombo.setVisible(false);
			courseCombo.setVisible(false);
			reportCombo.setDisable(true);
			ArrayList<ExamReport> arr = tc.getAllExamReportsTeacherWrote(user);
			examCombo.getItems().addAll(arr);
		} else {
			pc = new PrincipalController();
			reportCombo.getItems().addAll("Student", "Teacher", "Course");
			studentL = pc.getAllStudentsInData();
			studentCombo.getItems().addAll(studentL);
			teacherL = pc.getAllTeachersInData();
			teacherCombo.getItems().addAll(teacherL);
			courseL = pc.getAllCoursesInData();
			courseCombo.getItems().addAll(courseL);

		}

	}

	/**
	 * This method generates the GUI presentation of the histogram
	 * 
	 * @param report
	 */
	private void initiateReport(ExamReport report) {
		if (report == null) {
			return;
		}

		medianLabel.setVisible(true);
		avgLabel.setVisible(true);
		medianLabel.setText("The Midean is: " + report.getMidean());
		avgLabel.setText("The Avarage is: " + report.getAvg());
		CategoryAxis xAxis = new CategoryAxis();

		// creating X and Y axis for BarChart
		xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(report.headers)));
		xAxis.setLabel("percentiles");
		NumberAxis yAxis = new NumberAxis();
		yAxis.setLabel("gradse");
		barChart = new BarChart<>(xAxis, yAxis);

		// populating the data
		XYChart.Series<String, Number> series1 = new XYChart.Series<String, Number>();
		series1.setName(report.getExamID() + " " + report.getExecutionID());
		for (int i = 0; i < report.getPercentages().length; i++) {
			series1.getData().add(new XYChart.Data<>(report.headers[i], report.getPercentages()[i]));
		}
		// adding the data to the BarChart
		barChart.getData().add(series1);

		grid.add(barChart, 0, 1);
	}
}
