package logic;

import java.io.Serializable;

/**
 * This class holds the course data
 * 
 * @author Rotem Vaknin
 *
 */
public class Course implements Serializable {
	// **************************************************
	// Fields
	// **************************************************
	String cID;
	String cName;
	Subject subject;
	String teacherID;

	// **************************************************
	// constructors
	// **************************************************
	/**
	 * Constructor.
	 * 
	 * @param cID
	 *            the course ID
	 * @param cName
	 *            the course name
	 * @param teacherID
	 *            the teacher ID
	 * @param s
	 *            the subject of the course
	 */
	public Course(String cID, String cName, String teacherID, Subject s) {
		super();
		this.cID = cID;
		this.cName = cName;
		this.teacherID = teacherID;
		subject = s;
	}

	/**
	 * Copy constructor.
	 * 
	 * @param course
	 */
	public Course(Course course) {
		this(course.getcID(), course.getcName(), course.getcName(), course.getSubject());
	}

	/**
	 * constructor that save the course ID and the course Name
	 * 
	 * @param cID
	 *            get the course ID as String
	 * @param sID
	 *            get the course name as String
	 */
	public Course(String cID, String sID) {
		this.cID = cID;
		subject = new Subject(sID, null);
	}

	// **************************************************
	// Public methods
	// **************************************************

	/**
	 * Return the course ID
	 * 
	 * @return the course ID as String
	 */
	public String getcID() {
		return cID;
	}

	/**
	 * Sets the course ID
	 * 
	 * @param cID-
	 *            The course ID
	 */
	public void setcID(String cID) {
		this.cID = cID;
	}

	/**
	 * Returns the course name
	 * 
	 * @return The course name as String
	 */
	public String getcName() {
		return cName;
	}

	/**
	 * Sets the course name
	 * 
	 * @param cName
	 *            - The name of the course
	 */
	public void setcName(String cName) {
		this.cName = cName;
	}

	/**
	 * Return the subject that the course associated with.
	 * 
	 * @return the subject as Subject object
	 */
	public Subject getSubject() {
		return subject;
	}

	/**
	 * Sets the subject of the course
	 * 
	 * @param subject
	 *            - The subject the course is associated with
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	/**
	 * get teacher Id that teaches the course
	 */
	public String getTeacherID() {
		return teacherID;
	}

	/**
	 * Sets the teacher id that teacher the course
	 */
	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}

	/**
	 * Return in Sting the format details of course ; course name , course ID ,
	 * Subject
	 */
	public String toString() {
		String s = "";
		s += "Course name: " + getcName() + " Course ID: \n" + getcID() + subject.toString();
		return s;
	}

}
