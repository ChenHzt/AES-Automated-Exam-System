package gui;

import java.util.HashMap;

/**
 * this class save the student selection on answers in exam
 */
public class StudentSelection {
	// **************************************************
    // Fields
    // **************************************************	
	private static StudentSelection single_instance = null;

	public HashMap<QuestionInExam, Integer> studentAnswers;
	public QuestionInExam question;

	// **************************************************
    // constructor
    // **************************************************
	/**
	 * constructor that set the studentAnswers parameter
	 */
	public StudentSelection() {
		studentAnswers = new HashMap<QuestionInExam, Integer>();
	}

	// **************************************************
    // Public methods
    // **************************************************
	/**
	 * this method return StudentSelection object
	 * if single_instance == null return null , else return StudentSelection
	 * @return StudentSelection 
	 */
	public static StudentSelection getInstance() {
		if (single_instance == null)
			single_instance = new StudentSelection();

		return single_instance;
	}

}
