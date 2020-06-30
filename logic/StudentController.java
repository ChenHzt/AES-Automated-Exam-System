package logic;

import java.util.ArrayList;

import client.ClientConsole;
import common.Message;
import login.LoginController;
import login.LoginGUI;

/**
 * This class sends data gotten student from UI and sends to server and vice
 * versa
 * 
 * @author reut
 *
 */
public class StudentController {
	// **************************************************
	// fields
	// **************************************************
	User student; // the user is student type
	LoginController lc; // save all the login parameters
	ClientConsole client;
	ArrayList<Course> courses;
	ArrayList<Subject> subjects;
	static StudentInExam studentResults;

	// **************************************************
	// constructors
	// **************************************************

	/**
	 * Default Constructor
	 */
	public StudentController() {
		lc = new LoginController();
		student = lc.getUser();
		client = new ClientConsole(LoginGUI.IP, LoginGUI.port, null);
	}

	/**
	 * get student result in exam
	 */
	public StudentInExam getStudentResults() {
		return studentResults;
	}

	/**
	 * Return student's user information details
	 */
	public User getStudent() {
		return student;
	}

	/**
	 * Sets the User with the parameter details
	 * 
	 * @param student
	 *            - the parameter details
	 */
	public void setStudent(User student) {
		this.student = student;
	}

	/**
	 * Returns all the grades of a student
	 * 
	 * @return array list of the student's grades
	 */
	public ArrayList<StudentInExam> getAllgrades()// send request to db to get all grades of the student
	{
		Message msg = new Message();
		msg.setSentObj(student); // save the object of client
		msg.setqueryToDo("getAllGradesRelevantToStusent");
		msg.setClassType("Student");
		client.accept(msg);// send the message to the client

		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<StudentInExam> arrOfGrades;
		arrOfGrades = (ArrayList<StudentInExam>) msg.getReturnObj();
		System.out.println("***************");
		return arrOfGrades;
	}

	/**
	 * Returns all exam in execution for a student
	 */
	public ArrayList<ExamInExecution> getAllExamsInExecutin() {
		Message msg = new Message();
		msg.setSentObj(student);
		msg.setqueryToDo("getAllPerformExamsRelevantToStudent");
		msg.setClassType("Student");

		client.accept(msg);
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<ExamInExecution> arrOfExams = new ArrayList<ExamInExecution>();
		arrOfExams = (ArrayList<ExamInExecution>) msg.getReturnObj();
		return arrOfExams;
	}

	/**
	 * Returns the result of a student in exam
	 * 
	 * @param student
	 *            - the student who performed the exam
	 * @param exam
	 *            - the exam
	 */
	public void getStudentResultInExam(String studentID, ExamInExecution exam) {
		Message msg = new Message();

		msg.setqueryToDo("getStudentAnswersInExam");
		msg.setClassType("Student");

		StudentInExam sie = new StudentInExam();
		sie.setExamID(exam.getExamDet().getExamID());
		sie.setExecutionID(exam.getExecutionID());
		sie.setStudentID(studentID);

		msg.setSentObj(sie);
		client.accept(msg);
		try {
			Thread.sleep(12000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg = client.getMessage();
		studentResults = (StudentInExam) msg.getReturnObj();

	}

	/**
	 * displays the exam student performed and the grade of that exam.
	 * 
	 * @param selectedItem
	 *            - selected exam from list of exams
	 * @return the selected exam details
	 */
	public ExamInExecution getExamForStudent(StudentInExam selectedItem) {
		Message msg = new Message();
		msg.setSentObj(selectedItem);
		msg.setqueryToDo("getTheExamForStudentToShow");
		msg.setClassType("Student");

		client.accept(msg);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ExamInExecution exam = new ExamInExecution();
		exam = (ExamInExecution) msg.getReturnObj();
		return exam;
	}

	/**
	 * This method receives an exam in executions and gets the exam details
	 * (questions list, duration, ...) matching the examID
	 * 
	 * @param exam
	 *            - the executed exam
	 * @return an exam object with the requested details
	 */
	public Exam getExamByExamID(ExamInExecution exam) {
		Message msg = new Message();
		msg.setSentObj(exam.getExamDet());
		msg.setqueryToDo("getExamByExamID");
		msg.setClassType("Student");

		client.accept(msg);
		try {
			Thread.sleep(4000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		Exam e = new Exam();
		e = (Exam) msg.getReturnObj();
		return e;
	}

	/**
	 * This method uploads a file into the server's file system
	 * 
	 * @param fileExamName
	 *            - the path to save the file in
	 */
	public void uploadManualExam(String fileExamName) {
		Message msg = new Message();
		msg.setSentObj(fileExamName);
		msg.setqueryToDo("uploadWordFileExam");
		msg.setClassType("Student");

		client.accept(msg);
		try {
			Thread.sleep(4000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("finished uploading");
	}

	/**
	 * This method is called after a student selects an exam to perform in a
	 * computerized way. this method gets all questions in this exam for the student
	 * to answer.
	 * 
	 * @param exam
	 *            - the exam student chose
	 * @return the exam student needs to perform
	 */
	public ExamInExecution performCompExam(ExamInExecution exam) {
		Message msg = new Message();
		msg.setSentObj(exam);
		msg.setqueryToDo("getTheExamToPerformComputerized");
		msg.setClassType("Student");

		client.accept(msg);
		try {
			Thread.sleep(4000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ExamInExecution examToReturn = new ExamInExecution();
		examToReturn = (ExamInExecution) msg.getReturnObj();
		return exam;
	}

	/**
	 * Changes the student status in the exam. for example: from started to finished
	 * 
	 * @param s
	 *            - the students and the exam details
	 */
	public void changeStudentInExamStatus(StudentInExam s) {
		Message msg = new Message();
		msg.setSentObj(s);
		msg.setqueryToDo("changeStudentInExamStatus");
		msg.setClassType("Student");
		client.accept(msg);
		System.out.println("test1");

	}
}
