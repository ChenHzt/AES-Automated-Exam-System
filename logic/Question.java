package logic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class saves all the questions details
 * 
 * @author Rotem Vaknin
 *
 */
public class Question implements Serializable {
	// **************************************************
	// Fields
	// **************************************************
	private String QuestionTxt;
	private String teacherID;
	private String questionID;
	private String[] answers;
	private int correctAnswer;
	private String instruction;
	private String teacherName;
	private ArrayList<Course> courseList;
	// **************************************************
	// constructors
	// **************************************************

	/**
	 * Constructor set the question details
	 * 
	 * @param questionTxt
	 *            the question
	 * @param questionID
	 *            the question ID
	 * @param teacherId
	 *            the teacher ID that create the question
	 * @param instruction
	 *            for the question
	 * @param answer1
	 *            optional answer of the question
	 * @param answer2
	 *            optional answer of the question
	 * @param answer3
	 *            optional answer of the question
	 * @param answer4
	 *            optional answer of the question
	 * @param correctAnswer
	 *            the number of the correct answer of the question
	 */
	public Question(String questionTxt, String questionID, String teacherId, String instruction, String answer1,
			String answer2, String answer3, String answer4, int correctAnswer) {
		this.QuestionTxt = questionTxt;
		this.teacherID = teacherId;
		this.questionID = questionID;
		this.answers = new String[4];
		this.answers[0] = answer1;
		this.answers[1] = answer2;
		this.answers[2] = answer3;
		this.answers[3] = answer4;
		this.correctAnswer = correctAnswer;
		this.instruction = instruction;
		courseList = new ArrayList<Course>();
	}

	/**
	 * Copy constructor.
	 * 
	 * @param q
	 *            the question we want to save
	 */
	public Question(Question q) {
		this(q.getQuestionTxt(), q.getQuestionID(), q.getTeacherID(), q.getInstruction(), q.getAnswers()[0],
				q.getAnswers()[1], q.getAnswers()[2], q.getAnswers()[3], q.getCorrectAnswer());
	}

	/**
	 * Default constructor
	 */
	public Question() {
		this.answers = new String[4];
		courseList = new ArrayList<Course>();
	}

	// **************************************************
	// Public methods
	// **************************************************
	/**
	 * Return the question's text
	 * 
	 * @return the question body text
	 */
	public String getQuestionTxt() {
		return QuestionTxt;
	}

	/**
	 * Return the Id of the teacher who wrote this question
	 * 
	 * @return the teacher ID
	 */
	public String getTeacherID() {
		return teacherID;
	}

	/**
	 * Return the question ID
	 * 
	 * @return question ID
	 */
	public String getQuestionID() {
		return questionID;
	}

	/**
	 * Return the i-th answer of the question
	 * 
	 * @param index
	 *            i- the number of the answer
	 * @return the answer
	 */
	public String getAnswerI(int i) {
		return answers[i];
	}

	/**
	 * Return all the 4 possible answers of the question
	 * 
	 * @return array of the 4 answers
	 */
	public String[] getAnswers() {
		return answers;
	}

	/**
	 * Return the correct answer of question
	 * 
	 * @return the correct answer
	 */
	public int getCorrectAnswer() {
		return correctAnswer;
	}

	/**
	 * Return the instruction of the question. This if an optional field. could
	 * return null
	 * 
	 * @return the instruction
	 */
	public String getInstruction() {
		return instruction;
	}

	/**
	 * Set the question text
	 * 
	 * @param questionTxt
	 *            set the question's body text
	 */
	public void setQuestionTxt(String questionTxt) {
		QuestionTxt = questionTxt;
	}

	/**
	 * Save the teacher ID
	 * 
	 * @param teacherId
	 *            String that include the teacher ID
	 */
	public void setTeacherID(String teacherId) {
		this.teacherID = teacherId;
	}

	/**
	 * Save the question ID
	 * 
	 * @param questionID
	 *            get String of the question ID
	 */
	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	/**
	 * Save the answer of the question
	 * 
	 * @param answers
	 *            get array of the possible answers
	 */
	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	/**
	 * Receives four Strings and saves them into answers array
	 * 
	 * @param answer1
	 *            String type option 1 for answer
	 * @param answer2
	 *            String type option 2 for answer
	 * @param answer3
	 *            String type option 3 for answer
	 * @param answer4
	 *            String type option 4 for answer
	 */
	public void setAnswers(String answer1, String answer2, String answer3, String answer4) {
		this.answers[0] = answer1;
		this.answers[1] = answer2;
		this.answers[2] = answer3;
		this.answers[3] = answer4;
	}

	/**
	 * Save the instructions of the question
	 * 
	 * @param instruction
	 *            get String that include the instruction
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	/**
	 * Save the correct answer of the question
	 * 
	 * @param correctAnswer
	 *            get int with the number of the correct answer
	 */
	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;

	}

	/**
	 * Save the teacher name that wrote the question
	 * 
	 * @return teacher name by String
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * Set the teacher name that wrote the question
	 * 
	 * @param teacherName
	 *            get the teacher name by String
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**
	 * Return the course list that the question belongs to
	 * 
	 * @return ArrayList with the courses of the question
	 */
	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	/**
	 * Set the courses list of the question
	 * 
	 * @param courseList
	 *            get ArrayList with the courses
	 */
	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

	/**
	 * Returns a String format with the question info question ID , instructions ,
	 * text of the question , the answers , the correct answer
	 */
	public String toString() {
		String s = "";
		// s+="Teacher name: "+getTeacherName() +" Teacher id: "+getTeacherID();
		s += "\nQuestion ID: " + getQuestionID() + "\nInstructions: " + getInstruction() + "\nQuestion Body: "
				+ getQuestionTxt() + "\n";
		for (int i = 0; i < answers.length; i++)
			s += "\tanswer " + (i + 1) + ": " + answers[i] + "\n";
		s += "The correct answer is: " + getCorrectAnswer();
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((questionID == null) ? 0 : questionID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Question other = (Question) obj;
		if (questionID == null) {
			if (other.questionID != null)
				return false;
		} else if (!questionID.equals(other.questionID))
			return false;
		return true;
	}

}
