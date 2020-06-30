package logic;

import java.util.ArrayList;

import client.ClientConsole;
import common.Message;
import gui.QuestionInExam;
import login.LoginController;
import login.LoginGUI;

/**
 * This class sends data gotten from principal UI and sends to server and vice
 * versa
 * 
 * @author reut
 *
 */
public class PrincipalController {

	// **************************************************
	// Fields
	// **************************************************
	User principal;
	LoginController lc;
	ClientConsole client;

	// **************************************************
	// constructors
	// **************************************************

	/**
	 * Default constructor
	 */
	public PrincipalController() {
		lc = new LoginController();
		principal = lc.getUser();
		client = new ClientConsole(LoginGUI.IP, LoginGUI.port, null);
	}

	/**
	 * This method calls for echo server to get all of the exams and return them the
	 * client.
	 */
	public ArrayList<Exam> getAllExamsInData() {
		Message msg = new Message();
		msg.setSentObj(principal);
		msg.setqueryToDo("getAllExmasInDB");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<Exam> arrOfExams = new ArrayList<Exam>();
		arrOfExams = (ArrayList<Exam>) msg.getReturnObj();
		return arrOfExams;
	}

	/**
	 * This method calls for echo server to get all of the questions and return them
	 * the client.
	 */
	public ArrayList<Question> getAllQuestionsInData() {
		Message msg = new Message();
		msg.setSentObj(principal);
		msg.setqueryToDo("getAllQuestionsInDB");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<Question> arrOfQuestions = new ArrayList<Question>();
		arrOfQuestions = (ArrayList<Question>) msg.getReturnObj();
		return arrOfQuestions;
	}

	/**
	 * This method calls for echo server to get all of the students and return them
	 * the client.
	 */
	public ArrayList<User> getAllStudentsInData() {
		Message msg = new Message();
		msg.setSentObj(principal);
		msg.setqueryToDo("getAllStudentsInDB");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<User> arrOfStudents = new ArrayList<User>();
		arrOfStudents = (ArrayList<User>) msg.getReturnObj();
		return arrOfStudents;
	}

	/**
	 * This method calls for echo server to get all of the teachers and return them
	 * the client.
	 */
	public ArrayList<User> getAllTeachersInData() {
		Message msg = new Message();
		msg.setSentObj(principal);
		msg.setqueryToDo("getAllTeachersInDB");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<User> arrOfTeachers = new ArrayList<User>();
		arrOfTeachers = (ArrayList<User>) msg.getReturnObj();
		return arrOfTeachers;
	}

	/**
	 * This method gets all overtime requests for all exams in execution for the
	 * principal
	 */
	public ArrayList<OvertimeDetails> getAllOverTimeRequests() {
		Message msg = new Message();
		msg.setqueryToDo("getAllOverTimeRequests");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<OvertimeDetails> arrOfovertime = new ArrayList<OvertimeDetails>();
		arrOfovertime = (ArrayList<OvertimeDetails>) msg.getReturnObj();
		return arrOfovertime;

	}

	/**
	 * This method allows principal to approve overtime request from teacher
	 */
	public void approveOvertime(OvertimeDetails overtimeDetails) {
		Message msg = new Message();
		msg.setClassType("Principal");
		msg.setqueryToDo("approveOvertime");
		msg.setSentObj(overtimeDetails);
		client.accept(msg);

	}

	/**
	 * This method allows principal to deny overtime request from teacher
	 */
	public void denyOvertime(OvertimeDetails overtimeDetails) {
		Message msg = new Message();
		msg.setClassType("Principal");
		msg.setqueryToDo("denyOvertime");
		msg.setSentObj(overtimeDetails);
		client.accept(msg);

	}

	/**
	 * This method gets all exam report for exams that teacher wrote
	 */
	public ArrayList<ExamReport> getAllExamReportsTeacherWrote(User user) {
		Message msg = new Message();
		msg.setSentObj(user);
		msg.setqueryToDo("getAllExamReportsTeacherWrote");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<ExamReport> arr = (ArrayList<ExamReport>) msg.getReturnObj();
		return arr;
	}

	/**
	 * This methos gets all exam reports for student for the tests he performed
	 */
	public ArrayList<StudentInExam> getAllExamReportsStudentPerformed(User user) {
		Message msg = new Message();
		// Now we are going to the principal handler
		msg.setSentObj(user);
		msg.setqueryToDo("getAllExamReportsStudentPerformed");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<StudentInExam> arr = (ArrayList<StudentInExam>) msg.getReturnObj();
		return arr;
	}

	/**
	 * This method returns all the subjects listed in the system
	 */
	public ArrayList<Subject> getAllSubjectsInData() {
		Message msg = new Message();
		msg.setSentObj("Course");
		msg.setqueryToDo("getAllSubjectsInDB");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msg = client.getMessage();
		return (ArrayList<Subject>) msg.getReturnObj();
	}

	/**
	 * This method returns all the courses listed in the system
	 */
	public ArrayList<Course> getAllCoursesInData() {
		Message msg = new Message();
		msg.setSentObj("Course");
		msg.setqueryToDo("getAllCoursesInDB");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(3500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<Course> arr = (ArrayList<Course>) msg.getReturnObj();
		return arr;
	}

	/**
	 * This method returns all the exam in execution currently in the system
	 */
	public ArrayList<ExamInExecution> getAllExamInExecution() {
		Message msg = new Message();
		msg.setSentObj("Course");
		msg.setqueryToDo("getAllExamInExecution");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msg = client.getMessage();
		return (ArrayList<ExamInExecution>) msg.getReturnObj();
	}

	/**
	 * This method returns all the student result in exams for a spicific student
	 */
	public ArrayList<StudentInExam> getAllStudentInExam() {
		Message msg = new Message();
		msg.setSentObj("Course");
		msg.setqueryToDo("getAllStudentInExam");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msg = client.getMessage();
		return (ArrayList<StudentInExam>) msg.getReturnObj();
	}

	/**
	 * This method returns all the questions listed in the system
	 */
	public ArrayList<QuestionInExam> getAllQuestionInExam() {
		Message msg = new Message();
		msg.setSentObj("Course");
		msg.setqueryToDo("getAllQuestionInExam");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(5000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msg = client.getMessage();
		return (ArrayList<QuestionInExam>) msg.getReturnObj();
	}

	/**
	 * This method returns all the exams assigned to a spicific course
	 */
	public ArrayList<ExamReport> getAllExamsInCourse(Course c) {
		Message msg = new Message();
		msg.setSentObj(c);
		msg.setqueryToDo("getAllExamsInCourseInDB");
		msg.setClassType("Principal");

		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		msg = client.getMessage();
		ArrayList<ExamReport> arr = (ArrayList<ExamReport>) msg.getReturnObj();
		return arr;
	}
}
