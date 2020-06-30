package logic;

import java.util.ArrayList;

import client.ClientConsole;
import common.Message;
import login.LoginController;
import login.LoginGUI;

/**
 * This class sends data gotten teacher from UI and sends to server and vice
 * versa
 * 
 * @author reut
 *
 */
public class TeacherController {
	// **************************************************
	// Fields
	// **************************************************
	User teacher;
	LoginController lc;
	ClientConsole client;
	ArrayList<Course> courses;
	ArrayList<Subject> subjects;
	ArrayList<Question> selectedQuestions;

	// **************************************************
	// constructors
	// **************************************************

	/**
	 * Default constructor
	 */
	public TeacherController() {
		lc = new LoginController();
		teacher = lc.getUser();
		client = new ClientConsole(LoginGUI.IP, LoginGUI.port, null);
	}

	/**
	 * Fetches all questions from DB
	 * 
	 * @return
	 */
	public ArrayList<Question> getAllQuestions() {
		Message msg = new Message();
		msg.setSentObj(teacher);
		msg.setqueryToDo("getAllQuestionRelevantToTeacher");
		msg.setClassType("Teacher");
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<Question> arrOfQuestions = new ArrayList<Question>();
		arrOfQuestions = (ArrayList<Question>) msg.getReturnObj();
		return arrOfQuestions;

	}

	/**
	 * Return the next available exam id for this course
	 * 
	 * @param course
	 *            - the course to check
	 * @return the next available exam id in course
	 */
	public String getExamID(Course course) {
		Message msg = new Message();
		msg.setSentObj(course);
		msg.setqueryToDo("getNextExamID");
		msg.setClassType("Teacher");
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg = client.getMessage();

		String str = (String) msg.getReturnObj();
		return str;
	}

	/**
	 * Deleltse a question from data base
	 * 
	 * @param qToDel
	 *            - the questions to delete
	 * @return true if succeeded, false otherwise
	 */
	public Boolean deleteQuestion(Question qToDel) {
		Message msg = new Message();
		msg.setClassType("teacher");
		msg.setqueryToDo("deleteQuestion");
		msg.setSentObj(qToDel);
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		Boolean b = (Boolean) msg.getReturnObj();
		return b;
	}

	/**
	 * save new question to database
	 * 
	 * @param qToAdd
	 *            - the question we want to add
	 */
	public Question createNewQuestion(Question qToAdd) {
		Message questionToSend = new Message();
		questionToSend.setClassType("Teacher");
		questionToSend.setqueryToDo("createQuestion");
		questionToSend.setSentObj(qToAdd);
		client.accept(questionToSend);
		try {
			Thread.sleep(3500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return (Question) client.getMessage().getReturnObj();
	}

	/**
	 * Fetches from DB all courses that a teacher teaches.
	 */
	public void getTeacherCourse() {
		Message msg = new Message();
		msg.setSentObj(teacher);
		msg.setqueryToDo("getAllCourseRelevantToTeacher");
		msg.setClassType("Teacher");
		client.accept(msg);
		try {
			Thread.sleep(2500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<Course> arrOfCourses = new ArrayList<Course>();
		arrOfCourses = (ArrayList<Course>) msg.getReturnObj();
		courses = arrOfCourses;
		subjects = new ArrayList<Subject>();
		ArrayList<String> subjectFlag = new ArrayList<String>();
		for (int i = 0; i < courses.size(); i++)
			if (!subjectFlag.contains(courses.get(i).getSubject().getSubjectID())) {
				subjectFlag.add(courses.get(i).getSubject().getSubjectID());
				subjects.add(courses.get(i).getSubject());
			}
	}

	/**
	 * Edit an existing question in DB
	 * 
	 * @param updatedQuestion
	 *            - the question with the details that nedd to be updated
	 */
	public void editQuestion(Question updatedQuestion) {
		Message questionToSend = new Message();
		questionToSend.setClassType("Teacher");
		questionToSend.setqueryToDo("updadeQuestion");
		questionToSend.setSentObj(updatedQuestion);
		client.accept(questionToSend);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the user teacher
	 * 
	 * @return the current user
	 */
	public User getTeacher() {
		return teacher;
	}

	/**
	 * Returns a subject list
	 * 
	 * @return list of all subjects
	 */
	public ArrayList<Subject> getSubjects() {
		return subjects;
	}

	/**
	 * Returns a courses list
	 * 
	 * @return list of all courses
	 */
	public ArrayList<Course> getCourses() {
		return courses;
	}

	/**
	 * Returns the complete course details matching a course name
	 * 
	 * @param courseName
	 *            the course to compare
	 * @return the Course details
	 */
	public Course getCourseFromName(String courseName) {
		for (int i = 0; i < courses.size(); i++) {
			String s = courses.get(i).getcName();
			if (s.equals(courseName))
				return new Course(courses.get(i));
		}
		return null;
	}

	/**
	 * Fetch all questions relevant to a teacher in course from DB
	 * 
	 * @param course
	 *            - the course the teacher teacher
	 * @return the array off questions
	 */
	public ArrayList<Question> getQuestionsForTeacherInCourse(Course course) {
		Message msg = new Message();
		msg.setqueryToDo("QuestionForTeacherInCourse");
		msg.setClassType("Teacher");
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg.setSentObj(course);
		msg = client.getMessage();
		ArrayList<Question> questionsInCourse = (ArrayList<Question>) msg.getReturnObj();
		return questionsInCourse;

	}

	/**
	 * Returns the teacher details from DB
	 * 
	 * @param uID
	 *            - the id of the teacher
	 * @return - The teacher details matching the id
	 */
	public User teacherDet(String uID) {
		Message msg = new Message();
		msg.setSentObj(uID);
		msg.setqueryToDo("getTeacherDetails");
		msg.setClassType("Teacher");
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		User teacher = (User) msg.getReturnObj();
		return teacher;
	}

	/**
	 * Delete exam From DB. possible only if exam is locked (all students submitted)
	 * 
	 * @param eToDel
	 *            - the exam to delete
	 */
	public void deleteExam(Exam eToDel) {
		Message msg = new Message();
		msg.setClassType("teacher");
		msg.setqueryToDo("deleteExam");
		msg.setSentObj(eToDel);
		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Fetchs all exams from DB
	 * 
	 * @return array list of exams
	 */
	public ArrayList<Exam> getAllExams() {
		Message msg = new Message();
		msg.setSentObj(teacher);
		msg.setqueryToDo("getAllExamsRelevantToTeacher");
		msg.setClassType("Teacher");

		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<Exam> arrOfExams = new ArrayList<Exam>();
		arrOfExams = (ArrayList<Exam>) msg.getReturnObj();
		return arrOfExams;
	}

	/**
	 * Fetchs all exams currently in execution from DB
	 * 
	 * @return array list of exams in execution
	 */
	public ArrayList<ExamInExecution> getAllExamsInExecutionForTeacher() {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("getAllExamsInExecutionRelevantToTeacher");
		msg.setSentObj(teacher);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
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
	 * Fetchs all students assigned to course
	 * 
	 * @param sID
	 *            - the Subject of the course id
	 * @param cID
	 *            - the course id
	 * @return array list of student of type user
	 */
	public ArrayList<User> getAllStudentsInCourse(String sID, String cID) {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("getStudentsInCourse");
		Course c = new Course(cID, sID);
		msg.setSentObj(c);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<User> arrOfstudents = new ArrayList<User>();
		arrOfstudents = (ArrayList<User>) msg.getReturnObj();
		return arrOfstudents;
	}

	/**
	 * Creates new exam in execution and saved to DB
	 */
	public ExamInExecution executeNewExam(ExamInExecution exam) {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("executeNewExam");
		msg.setSentObj(exam);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ExamInExecution examInExec = new ExamInExecution();
		examInExec = (ExamInExecution) msg.getReturnObj();
		return examInExec;
	}

	/**
	 * Fetch examinee group in exam from DB
	 * 
	 * @param exam
	 *            the exam to search
	 * @return array list of student in exam
	 */
	public ArrayList<StudentInExam> getExamnieesOfExam(ExamInExecution exam) {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("getExamnieesOfExam");
		msg.setSentObj(exam);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		ArrayList<StudentInExam> sList = new ArrayList<StudentInExam>();
		sList = (ArrayList<StudentInExam>) msg.getReturnObj();
		return sList;
	}

	/**
	 * This method locks an exam which is currently in execution
	 * 
	 * @param exam
	 *            the exam to be locked
	 */
	public void lockExam(ExamInExecution exam) {

		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("lockExam");
		msg.setSentObj(exam);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Fetches all locked exams that a teache rsent to execution
	 * 
	 * @return array list of exam in execution
	 */
	public ArrayList<ExamInExecution> getLockedExamsForTeacher() {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("getAllLockedExamsInExecutionRelevantToTeacher");
		msg.setSentObj(teacher);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
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
	 * Returns all the Exams in execution relevant to a teache
	 */
	public ArrayList<ExamInExecution> getWrittenExamsForTeacher() {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("getAllWrittenExamsInExecutionRelevantToTeacher");
		msg.setSentObj(teacher);
		client.accept(msg);
		try {
			Thread.sleep(2000L);
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
	 * Returns an arrayList from the type Question that were selected
	 * 
	 * @return array list of questions
	 */
	public ArrayList<Question> getSelectedQuestions() {
		return selectedQuestions;
	}

	/**
	 * Set the array list of the selected questions
	 * 
	 * @param array
	 */
	public void setSelectedQuestions(ArrayList<Question> array) {
		this.selectedQuestions = array;
	}

	/**
	 * Saves new exam to DB
	 * 
	 * @param exam
	 *            - the exam to be saved
	 */
	public void saveExam(Exam exam) {
		Message msg = new Message();
		msg.setSentObj(exam);
		msg.setClassType("Teacher");
		msg.setqueryToDo("saveExamToDB");
		client.accept(msg);
	}

	/**
	 * Sends request overTime to principal
	 * 
	 * @param overTimeDet
	 *            - the detailts about the exam and the amount of time
	 */
	public void sendRequestToOverTime(OvertimeDetails overTimeDet) {
		Message msg = new Message();
		msg.setSentObj(overTimeDet);
		msg.setqueryToDo("sendOverTimeRequest");
		msg.setClassType("Teacher");
		client.accept(msg);
	}

	/**
	 * This method returns all exam reports teacher wrote
	 */
	public ArrayList<ExamReport> getAllExamReportsTeacherWrote(User user) {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("getAllExamReportsTeacherWrote");
		msg.setSentObj(user);
		client.accept(msg);
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg = client.getMessage();
		return (ArrayList<ExamReport>) msg.getReturnObj();
	}

	/**
	 * gets all approved grades for student in exam
	 * 
	 * @param s
	 */
	public void approveGrade(StudentInExam s) {
		Message msg = new Message();
		msg.setSentObj(s);
		msg.setqueryToDo("approveGrade");
		msg.setClassType("Teacher");
		client.accept(msg);
	}

	/**
	 * changes the grade of a student
	 */
	public void changeGrade(StudentInExam s) {
		Message msg = new Message();
		msg.setSentObj(s);
		msg.setqueryToDo("changeGrade");
		msg.setClassType("Teacher");
		client.accept(msg);
	}

	/**
	 * get all grades teacher needs to approve
	 * 
	 * @return
	 */
	public ArrayList<StudentInExam> getAllGradesForApprval() {
		Message msg = new Message();
		msg.setClassType("Teacher");
		msg.setqueryToDo("getAllGradesForApprval");
		msg.setSentObj(teacher);
		client.accept(msg);
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msg = client.getMessage();
		return (ArrayList<StudentInExam>) msg.getReturnObj();
	}
}
