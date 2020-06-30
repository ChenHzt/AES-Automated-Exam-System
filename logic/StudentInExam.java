package logic;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import gui.QuestionInExam;

/**
 * This class hold the data associated with student in a particular exam.
 * 
 * @author reut
 *
 */
public class StudentInExam implements Serializable {
	// **************************************************
	// Fields
	// **************************************************
	private String studentID;
	private String studentName;
	private int grade;
	private String examID;
	private int executionID;
	private Timestamp date; // the date the student took the exam
	private String courseName;
	private String studentStatus;// status = {started,finished, not started}
	// Holds a Map of every question in the exam and the answer the student checked.
	private HashMap<QuestionInExam, Integer> checkedAnswers;
	private int NumberOfCorrectAnswer;
	private int NumberOfWrongAnswer;
	private Boolean isComp;// was the executed manually or was it computerized
	private int actualDuration;// the time it took the student to solve the exam
	private String reasonForGradeChenges;
	private Boolean isCopied;

	// **************************************************
	// constructors
	// **************************************************

	/**
	 * Default Constructor. Create an empty instance of this class
	 */
	public StudentInExam() {
		super();
	}

	/**
	 * Cnstructor that set the student in exam info
	 * 
	 * @param examID
	 *            get the ID of the exam
	 * @param grade
	 *            get the grade of the student in this exam
	 * @param date
	 *            get the date of perform this exam
	 * @param courseName
	 *            get the course name of the exam
	 * @param studentid
	 *            get the student id
	 */
	public StudentInExam(String examID, int grade, Timestamp date, String course_name, String studentID) {
		this.grade = grade;
		this.examID = examID;
		this.date = date;
		this.courseName = course_name;
		this.studentID = studentID;
	}

	/**
	 * Cnstructor that set the student in exam info
	 * 
	 * @param examID
	 *            get the ID of the exam
	 * @param grade
	 *            get the grade of the student in this exam
	 * @param date
	 *            get the date of perform this exam
	 * @param courseName
	 *            get the course name of the exam
	 */
	public StudentInExam(String examID, int grade, Timestamp date, String course_name) {
		this.grade = grade;
		this.examID = examID;
		this.date = date;
		this.courseName = course_name;
	}

	// **************************************************
	// Public methods
	// **************************************************

	/**
	 * Sets the course name of the exam
	 * 
	 * @param courseName
	 *            get as String
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Return the course name the exam assignes to
	 * 
	 * @return the course name as String
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Return the date the student took the exam
	 * 
	 * @return the date of the exam
	 */
	public Timestamp getDate() {
		return date;
	}

	/**
	 * Sets the date of the the exam
	 * 
	 * @param date
	 *            - the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
	}

	/**
	 * Return the student's grade in this exam
	 * 
	 * @return
	 */
	public int getGrade() {
		return grade;
	}

	/**
	 * Sets the student grade in this exam
	 * 
	 * @param grade
	 *            - the grade to be set
	 */
	public void setGrade(int grade) {
		this.grade = grade;
	}

	/**
	 * Return the ID of the exam
	 * 
	 * @return the exam as String
	 */
	public String getExamID() {
		return examID;
	}

	/**
	 * Sets the exam ID
	 * 
	 * @param The
	 *            examId to set
	 */
	public void setExamID(String examID) {
		this.examID = examID;
	}

	/**
	 * Return the student who took this exam
	 * 
	 * @return The student details
	 */
	public String getStudentID() {
		return studentID;
	}

	/**
	 * Sets the Student who took this exam
	 * 
	 * @param student
	 *            - The student to set
	 */
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	/**
	 * Sets the status of the student in the exam
	 * 
	 * @param studentStatus
	 *            - the status to be set
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	/**
	 * Returns the status of the student in the exam, started, finished or not
	 * started
	 * 
	 * @return the status
	 */
	public String getStudentStatus() {
		return studentStatus;
	}

	/**
	 * Sets the status of the student in the exam
	 * 
	 * @param studentStatus
	 *            - the status to be set
	 */
	public void setStudentStatus(String studentStatus) {
		this.studentStatus = studentStatus;
	}

	/**
	 * Return the execution ID for this exam, the number of times this exam was used
	 * before at the timed teacher pulled it out of the drawer.
	 * 
	 * @return int with the execution ID
	 */
	public int getExecutionID() {
		return executionID;
	}

	/**
	 * Sets the execution ID for this exam
	 * 
	 * @param executionID
	 *            get the execution ID as int
	 */
	public void setExecutionID(int executionID) {
		this.executionID = executionID;
	}

	/**
	 * Return a hashMap of the questions in this exam and the answer the student
	 * checked for each question.
	 * 
	 * @return A HashMap of the questions and the corresponding points number
	 */
	public HashMap<QuestionInExam, Integer> getCheckedAnswers() {
		return checkedAnswers;
	}

	/**
	 * Sets the hashMap of the questions in this exam and the answer the student
	 * checked for each question.
	 * 
	 * @param The
	 *            HashMap to assign to this student who solved the exam
	 */
	public void setCheckedAnswers(HashMap<QuestionInExam, Integer> checkedAnswers) {
		this.checkedAnswers = checkedAnswers;
	}

	/**
	 * Returns the number of wrong answers the student answered in the exam
	 * 
	 * @return The number of wrong answers
	 */
	public int getNumberOfWrongAnswer() {
		return NumberOfWrongAnswer;
	}

	/**
	 * Sets the number of wrong answers the student answered in the exam
	 * 
	 * @param numberOfWrongAnswer
	 *            - the number to set
	 */
	public void setNumberOfWrongAnswer(int numberOfWrongAnswer) {
		NumberOfWrongAnswer = numberOfWrongAnswer;
	}

	/**
	 * Returns the number of correct answers the student answered in the exam
	 * 
	 * @return The number of correct answers
	 */
	public int getNumberOfCorrectAnswer() {
		return NumberOfCorrectAnswer;
	}

	/**
	 * Sets the number of correct answers the student answered in the exam
	 * 
	 * @param numberOfCorrectAnswer
	 *            - the number to set
	 */
	public void setNumberOfCorrectAnswer(int numberOfCorrectAnswer) {
		NumberOfCorrectAnswer = numberOfCorrectAnswer;
	}

	/**
	 * Return the actual duration that took to perform the exam by the student
	 * 
	 * @return the duration of the exam
	 */
	public int getActualDuration() {
		return actualDuration;
	}

	/**
	 * set the actual duration that took to perform the exam by the student
	 * 
	 * @param actualDuration
	 *            the duration
	 */
	public void setActualDuration(int actualDuration) {
		this.actualDuration = actualDuration;
	}

	/**
	 * Return true if the exam was computerized, false if manually
	 * 
	 * @return true if the exam was computerized false otherwise
	 */
	public Boolean getIsComp() {
		return isComp;
	}

	/**
	 * if the exam exam was computerized set as true, otherwise false
	 * 
	 * @param isComp
	 *            - the parameter to be set
	 */
	public void setIsComp(Boolean isComp) {
		this.isComp = isComp;
	}

	/**
	 * Returns the reason for changing the student's grade from the teacher
	 */
	public String getReasonForGradeChenges() {
		return reasonForGradeChenges;
	}

	/**
	 * Sets the reason for changing the student's grade from the teacher.
	 * 
	 * @param reasonForGradeChenges
	 *            - the reason as string
	 */
	public void setReasonForGradeChenges(String reasonForGradeChenges) {
		this.reasonForGradeChenges = reasonForGradeChenges;
	}

	/**
	 * Return true the system found suspition for copying, false otherwise
	 * 
	 * @return true if the exam was copied, false otherwise
	 */
	public Boolean getIsCopied() {
		return isCopied;
	}

	/**
	 * If the system found suspition for copying set as true, otherwise false
	 * 
	 * @param isComp
	 *            - the parameter to be set
	 */
	public void setIsCopied(Boolean isCopied) {
		this.isCopied = isCopied;
	}

	@Override
	public boolean equals(Object arg0) {
		StudentInExam s = (StudentInExam) arg0;
		if (!s.getStudentID().equals(this.studentID))
			return false;
		if (!s.getExamID().equals(this.examID))
			return false;
		if (s.getExecutionID() != this.executionID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		String s = "";
		s += "\nExam ID: " + getExamID() + "\tExam Date" + getDate() + "\tCourse name: " + getCourseName() + "\n";

		s += "Student Grade: " + getGrade() + "\n" + "Correct Answers: " + getNumberOfCorrectAnswer()
				+ "\tWrong Answers: " + getNumberOfWrongAnswer() + "\n";
		for (Map.Entry<QuestionInExam, Integer> entry : checkedAnswers.entrySet()) {
			s += entry.getKey().toString() + "\nAnswer selected: " + entry.getValue();
		}

		return s;
	}

}