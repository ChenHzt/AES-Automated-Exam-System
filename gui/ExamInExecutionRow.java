package gui;

import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import logic.ExamInExecution;

/**
 * this class represent an exam in execution row in table
 * 
 * @author chen1
 *
 */
public class ExamInExecutionRow {
	// **************************************************
	// Fields
	// **************************************************
	ExamInExecution exam;
	String examID;
	String courseName;
	String courseID;
	String subjectID;
	int executionID;
	String executeTeacherName;
	String authorTeacherName;
	int duration;
	RadioButton selectExamRB;
	private ImageView preview;

	// **************************************************
	// Constructors
	// **************************************************
	public ExamInExecutionRow(ExamInExecution exam, ImageView iv) {
		super();
		this.exam = exam;
		this.examID = exam.getExamDet().getExamID();
		this.courseName = exam.getCourseName();
		this.executionID = exam.getExecutionID();
		this.executeTeacherName = exam.getExecTeacher().getuName();
		this.preview = iv;

	}

	public ExamInExecutionRow() {

	}

	// **************************************************
	// Public methods
	// **************************************************
	/**
	 * return exam id
	 * 
	 * @return
	 */
	public String getExamID() {
		return examID;
	}

	/**
	 * set the exam id
	 * 
	 * @param examID
	 */
	public void setExamID(String examID) {
		this.examID = examID;
	}

	/**
	 * return the course name of the exam
	 * 
	 * @return
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * set course name of the exam
	 * 
	 * @param courseName
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * return the course ID
	 * 
	 * @return
	 */
	public String getCourseID() {
		return courseID;
	}

	/**
	 * set the exam's course ID
	 * 
	 * @param courseID
	 */
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	/**
	 * return the execution id
	 * 
	 * @return
	 */
	public int getExecutionID() {
		return executionID;
	}

	/**
	 * set the exam's execution ID
	 * 
	 * @param executionID
	 */
	public void setExecutionID(int executionID) {
		this.executionID = executionID;
	}

	/**
	 * return the image of preview icon
	 * 
	 * @return
	 */
	public ImageView getPreview() {
		return preview;
	}

	/**
	 * set the preview imageview icon
	 * 
	 * @param previewL
	 */
	public void setPreview(ImageView previewL) {
		this.preview = previewL;
	}

	/**
	 * return the name of teacher who executed the exam
	 * 
	 * @return
	 */
	public String getExecuteTeacherName() {
		return executeTeacherName;
	}

	/**
	 * set the name of the teache rwho executed the exam
	 * 
	 * @param executeTeacherName
	 */
	public void setExecuteTeacherName(String executeTeacherName) {
		this.executeTeacherName = executeTeacherName;
	}

	/**
	 * return the name of the teacher who wrote the exam
	 * 
	 * @return
	 */
	public String getAuthorTeacherName() {
		return authorTeacherName;
	}

	/**
	 * set the name of the teacher who wrote the exam
	 * 
	 * @param authorTeacherName
	 */
	public void setAuthorTeacherName(String authorTeacherName) {
		this.authorTeacherName = authorTeacherName;
	}

	/**
	 * return the exam duration
	 * 
	 * @return
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * set the exam duration
	 * 
	 * @param duration
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * set the exam's subject ID
	 * 
	 * @param subjectID
	 */
	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	/**
	 * returns the subject ID
	 * 
	 * @return
	 */
	public String getSubjectID() {
		return subjectID;
	}

	/**
	 * return the radio button to choos exam
	 * 
	 * @return
	 */
	public RadioButton getSelectExamRB() {
		return selectExamRB;
	}

	/**
	 * set the radiobutton for the table
	 * 
	 * @param selectExamRB
	 */
	public void setSelectExamRB(RadioButton selectExamRB) {
		this.selectExamRB = selectExamRB;
	}

	/**
	 * return the exam Details
	 * 
	 * @return
	 */
	public ExamInExecution getExam() {
		return exam;
	}

}
