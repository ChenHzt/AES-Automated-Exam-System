package logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class save the exam in execution details
 * 
 * @author Rotem Vaknin
 *
 */
public class ExamInExecution implements Serializable {
	// **************************************************
	// Fields
	// **************************************************
	private Exam examDet;
	/*
	 * Since every exam can be used multiple time we needed a way to differentiate
	 * between those times execution id combined with the examID will identify the
	 * exam.
	 */
	private int ExecutionID;
	private boolean isLocked;
	private User execTeacher;
	private String courseName;
	private String courseID;
	private String subjectID;
	// In order to enable students to perform an exam a teacher must set an exam
	// code
	private String examCode;
	private ArrayList<StudentInExam> students;
	// Exam can be performed for singles or for groups.
	private boolean isGroup;

	// **************************************************
	// constructors
	// **************************************************
	/**
	 * constructor. this constructor takes no parametera and only sets the objects
	 * fields.
	 */
	public ExamInExecution() {
		examDet = new Exam();
		execTeacher = new User();
		students = new ArrayList<StudentInExam>();
	}

	// **************************************************
	// Public methods
	// **************************************************

	/**
	 * Return the exam details
	 * 
	 * @return the exam details as Exam
	 */
	public Exam getExamDet() {
		return examDet;
	}

	/**
	 * Sets the Exam object
	 * 
	 * @param examDet
	 *            get the exam details as Exam
	 */
	public void setExamDet(Exam examDet) {
		this.examDet = examDet;
	}

	/**
	 * Return the execution ID for thi exam, the number of times this exam was used
	 * before at the timed teacher pulled it out of the drawer.
	 * 
	 * @return int with the execution ID
	 */
	public int getExecutionID() {
		return ExecutionID;
	}

	/**
	 * Sets the execution ID for this exam
	 * 
	 * @param executionID
	 *            get the execution ID as int
	 */
	public void setExecutionID(int executionID) {
		ExecutionID = executionID;
	}

	/**
	 * Return the status of the exam true the exam is locked, false if not.
	 * 
	 * @return boolean value of the status
	 */
	public boolean isLocked() {
		return isLocked;
	}

	/**
	 * Sets the exam as locked, when receiving true, false otherwise.
	 * 
	 * @param isLocked
	 *            get the locked status as boolean
	 */
	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	/**
	 * Returns the teacher who pulled this exam out of the drawer (who executed this
	 * exam).
	 * 
	 * @return the teacher that execute the exam as User
	 */
	public User getExecTeacher() {
		return execTeacher;
	}

	/**
	 * Sets the the teacher that executed this exam
	 * 
	 * @param execTeacher
	 *            get the teacher details by User object
	 */
	public void setExecTeacher(User execTeacher) {
		this.execTeacher = execTeacher;
	}

	/**
	 * Return the course name that this exam is assigned to.
	 * 
	 * @return the course name as String
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the course name that this exam is assigned to.
	 * 
	 * @param courseName
	 *            get the course name by String
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Return the list of the student that can perform this exam
	 * 
	 * @return Arraylist of StudentInExam
	 */
	public ArrayList<StudentInExam> getStudents() {
		return students;
	}

	/**
	 * Sets the students list that can perform this exam
	 * 
	 * @param students
	 *            get the student as Arraylist of StudentInExam
	 */
	public void setStudents(ArrayList<StudentInExam> students) {
		this.students = students;
	}

	/**
	 * Return the course ID of the exam
	 * 
	 * @return exam ID as String
	 */
	public String getCourseID() {
		return courseID;
	}

	/**
	 * Sets the course ID
	 * 
	 * @param courseID
	 *            get as String
	 */
	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	/**
	 * Return the exam's subject ID
	 * 
	 * @return the subject ID as String
	 */
	public String getSubjectID() {
		return subjectID;
	}

	/**
	 * Sets the subject id
	 * 
	 * @param subjectID
	 *            get as String
	 */
	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	/**
	 * Returns the exam code
	 * 
	 * @return the exam code as String
	 */
	public String getExamCode() {
		return examCode;
	}

	/**
	 * Sets the exam code of the exam
	 * 
	 * @param examCode
	 *            get the exam code as String
	 */
	public void setExamCode(String examCode) {
		this.examCode = examCode;
	}

	/**
	 * Returns status if the exam in execution is for gruop of students
	 * 
	 * @return boolean value that continue the status of isGroup
	 */
	public boolean isGroup() {
		return isGroup;
	}

	/**
	 * Sets the exam in for agroup of students
	 * 
	 * @param isGroup
	 *            get as boolean
	 */
	public void setIsGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}

	/**
	 * Returns a String format
	 */
	@Override
	public String toString() {
		String s = "";
		s += "Exam Id: " + getExamDet().getExamID() + " Execution ID: " + getExecutionID() + "\nExam code: "
				+ getExamCode() + "\nExecuting teacher name: " + getExecTeacher().getuName() + "\nCourse name: "
				+ getCourseName() + "\nThe exam is " + (isLocked ? "" : "not ") + "locked";
		return s;
	}

}
