package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * This class holds all the exam information
 * 
 * @author Rotem Vaknin
 *
 */
public class Exam implements Serializable {
	// **************************************************
	// Fields
	// **************************************************
	private HashMap<Question, Integer> questions;// holds the points for every question in the exam
	private String teacherID;
	private String teacherName;
	private Boolean wasUsed;
	private String ExamID;
	private String instructionForTeacher;
	private String instructionForStudent;
	private int duration;
	private Course course;
	private String courseName;

	// **************************************************
	// constructors
	// **************************************************
	/**
	 * Default constructor
	 */
	public Exam() {

	}

	/**
	 * save the exam details
	 * 
	 * @param teacherID
	 *            the id of the teacher that wrote the exam
	 * @param teacherName
	 *            the name of the teacher that wrote the exam
	 * @param wasUsed
	 *            the used status of the exam
	 * @param examID
	 *            the exam ID
	 * @param instructionForTeacher
	 *            instructions for teacher
	 * @param instructionForStudent
	 *            instructions for student
	 * @param duration
	 *            the duration of the exam
	 * @param course
	 *            the course of the exam
	 */
	public Exam(String teacherID, String teacherName, Boolean wasUsed, String examID, String instructionForTeacher,
			String instructionForStudent, int duration, Course course) {
		super();
		this.teacherID = teacherID;
		this.teacherName = teacherName;
		this.wasUsed = wasUsed;
		ExamID = examID;
		this.instructionForTeacher = instructionForTeacher;
		this.instructionForStudent = instructionForStudent;
		this.duration = duration;
		this.course = course;
	}

	// **************************************************
	// Public methods
	// **************************************************
	/**
	 * Return a hashMap of the questions in this exam and the number of points
	 * assigned to each question.
	 * 
	 * @return A HashMap of the questions and the corresponding points number
	 */
	public HashMap<Question, Integer> getQuestions() {
		return questions;
	}

	/**
	 * Sets the hashMap of the questions in this exam and the number of points
	 * assigned to each question.
	 * 
	 * @param The
	 *            HashMap to assign to this exam
	 */
	public void setQuestions(HashMap<Question, Integer> questions) {
		this.questions = questions;
	}

	/**
	 * Return the teacher ID who wrote the exam
	 * 
	 * @return The teacher ID as String
	 */
	public String getTeacherID() {
		return teacherID;
	}

	/**
	 * Sets the teacher ID field with the id of the teacher who wrote this exam
	 * 
	 * @param teacherID
	 *            get as String
	 */
	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	/**
	 * Return the teacher name of the teacher who wrote this exam
	 * 
	 * @return The teacher name as String
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * Sets the teacher name of the teacher who wrote this exam
	 * 
	 * @param teacherName
	 *            get as String
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**
	 * Return true if the exam was used before, false if it was never used.
	 * 
	 * @return True if the exam was used before, false otherwise.
	 */
	public Boolean getWasUsed() {
		return wasUsed;
	}

	/**
	 * Sets the boolean value of the status of the exam status-true if the exam was
	 * used, false otherwise.
	 * 
	 * @param The
	 *            status to update
	 */
	public void setWasUsed(Boolean wasUsed) {
		this.wasUsed = wasUsed;
	}

	/**
	 * Return the ID of the exam
	 * 
	 * @return the exam as String
	 */
	public String getExamID() {
		return ExamID;
	}

	/**
	 * Sets the exam ID
	 * 
	 * @param The
	 *            examId to set
	 */
	public void setExamID(String examID) {
		ExamID = examID;
	}

	/**
	 * Return the instructions for teacher, an optional field - could be null
	 * 
	 * @return the instructions for teacher as String
	 */
	public String getInstructionForTeacher() {
		return instructionForTeacher;
	}

	/**
	 * Sets the instructions for teacher, an optional field - could be null
	 * 
	 * @param instructionForTeacher
	 *            get as String
	 */
	public void setInstructionForTeacher(String instructionForTeacher) {
		this.instructionForTeacher = instructionForTeacher;
	}

	/**
	 * Return the instructions for student, an optional field - could be null
	 * 
	 * @return the instructions for student as String
	 */
	public String getInstructionForStudent() {
		return instructionForStudent;
	}

	/**
	 * Sets the instructions for student, an optional field - could be null
	 * 
	 * @param instructionForStudent
	 *            get as String
	 */
	public void setInstructionForStudent(String instructionForStudent) {
		this.instructionForStudent = instructionForStudent;
	}

	/**
	 * Return the duration allocated for the exam in minutes
	 * 
	 * @return the duration as int
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Sets the duration allocated for the exam in minutes
	 * 
	 * @param duration
	 *            get as int
	 */
	public void setDuration(int duration) {
		this.duration = duration;
	}

	/**
	 * Return the course the exam is assigned to
	 * 
	 * @return the course as Course object
	 */
	public Course getCourse() {
		return course;
	}

	/**
	 * Sets the course details - assigns an exam to a course
	 * 
	 * @param course
	 *            get as Course object
	 */
	public void setCourse(Course course) {
		this.course = course;
		courseName = course.getcName();
	}

	/**
	 * Return the course name the exam assignes to
	 * 
	 * @return the course name as String
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the course name of the exam
	 * 
	 * @param courseName
	 *            get as String
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Return the question of the exam as an arrayList of type Question
	 * 
	 * @return the question as ArrayList<Question>
	 */
	public ArrayList<Question> getQuestionsArrayList() {
		if (questions == null)
			return null;
		ArrayList<Question> questionsArr = new ArrayList<Question>();
		for (Map.Entry<Question, Integer> entry : questions.entrySet()) {
			questionsArr.add(entry.getKey());
		}
		return questionsArr;
	}

	/**
	 * Return the String include the exam data ; exam ID , duration , instruction
	 * for teacher , instruction for student , questions of the exam
	 */
	public String toString() {
		String s = "";
		s += "Exam ID: " + getExamID() + "\tThe exam was " + (wasUsed ? "used\n" : "not used\n") + "Course name: "
				+ getCourseName() + "\n" + "duration: " + getDuration() + "\n" + "Teacher instructions: "
				+ getInstructionForTeacher() + "\nStudent instructions: " + getInstructionForStudent();
		for (Entry<Question, Integer> entry : questions.entrySet()) {
			s += "\n(" + entry.getValue() + " point";
			s += (entry.getValue() < 10 ? ")\n" : "s)\n");
			s += entry.getKey().toString();
		}
		return s;
	}

}
