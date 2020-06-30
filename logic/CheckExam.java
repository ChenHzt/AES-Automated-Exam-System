package logic;

/**
 * This class performs the automated checking of a locked exam and checking 
 * for cheating between the student
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import gui.QuestionInExam;

public class CheckExam {
	// **************************************************
	// Fields
	// **************************************************

	// the exams details
	String examID;
	int executionID;
	// the student we want to check their exams
	ArrayList<StudentInExam> studentToCheck;

	/**
	 * This method performs the automated exam checking for specified students When
	 * an exam is locked the server begins this method automatically
	 * 
	 * @param arr
	 *            - the students who performed the exam
	 * @return an array list of students and their matching answers in the exam
	 */
	public HashMap<StudentInExam, ArrayList<Integer>> checkExams(ArrayList<StudentInExam> arr) {
		HashMap<StudentInExam, ArrayList<Integer>> studentResultsMap = new HashMap<StudentInExam, ArrayList<Integer>>();

		for (int i = 0; i < arr.size(); i++) {
			ArrayList<Integer> studentResults = new ArrayList<Integer>();
			StudentInExam student = arr.get(i);
			if (student.getGrade() == 0)
				continue;
			Integer grade = 0;
			Integer correctAns = 0;
			Integer wrongAns = 0;
			for (Map.Entry<QuestionInExam, Integer> entry : student.getCheckedAnswers().entrySet()) {
				System.out.println("Question id: " + entry.getKey().getQuestion().getQuestionID() + " selected ans: "
						+ entry.getValue() + " correct ans: " + entry.getKey().getQuestion().getCorrectAnswer());
			}
			for (Map.Entry<QuestionInExam, Integer> entry : student.getCheckedAnswers().entrySet()) {
				if (entry.getKey().getQuestion().getCorrectAnswer() == entry.getValue()) {
					grade += entry.getKey().getPointsInExam();
					correctAns++;
				} else
					wrongAns++;
			}
			studentResults.add(grade);
			System.out.println("grade of " + student.getStudentID() + " is:" + grade);
			studentResults.add(correctAns);
			studentResults.add(wrongAns);
			studentResultsMap.put(student, studentResults);
		}
		return studentResultsMap;
	}

	/**
	 * This method performs the automated cheating checking for specified students
	 * When an exam is checked the server begins this method automatically
	 * 
	 * @param result
	 *            array list of students and their matching answers in the exam
	 * @return - array list of the checked students with the results
	 */
	public HashSet<StudentInExam> checkForCopies(HashMap<StudentInExam, ArrayList<Integer>> result) {
		HashSet<StudentInExam> studentCopied = new HashSet<StudentInExam>();
		for (Map.Entry<StudentInExam, ArrayList<Integer>> entry1 : result.entrySet()) {
			StudentInExam student1 = entry1.getKey();
			Integer wrongAns1 = entry1.getValue().get(2);
			for (Map.Entry<StudentInExam, ArrayList<Integer>> entry2 : result.entrySet()) {
				StudentInExam student2 = entry2.getKey();
				Integer wrongAns2 = entry2.getValue().get(2);
				System.out.println("1: " + student1.getStudentID() + " 2: " + student2.getStudentID());
				System.out.println("1w: " + wrongAns1 + " 2w: " + wrongAns2);
				if (!(student1.getStudentID().equals(student2.getStudentID())) && wrongAns1 == wrongAns2
						&& wrongAns1 >= 3) {
					studentCopied.add(student1);
					studentCopied.add(student2);
				}
			}
		}

		return studentCopied;
	}

}
