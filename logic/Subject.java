package logic;

import java.io.Serializable;

/**
 * This class holds the information of a Subject: Subject's id in the system and
 * subject's name
 * 
 * @author reut
 *
 */
public class Subject implements Serializable {
	// **************************************************
	// Fields
	// **************************************************
	String subjectID;
	String sName;

	// **************************************************
	// constructors
	// **************************************************

	/**
	 * Constructor.
	 * 
	 * @param subjectID
	 *            - the subject ID
	 * @param sName
	 *            - the subject name
	 */
	public Subject(String subjectID, String sName) {
		super();
		this.subjectID = subjectID;
		this.sName = sName;
	}

	// **************************************************
	// Public methods
	// **************************************************

	/**
	 * Returns the subject ID
	 * 
	 * @return subject's ID
	 */
	public String getSubjectID() {
		return subjectID;
	}

	/**
	 * Sets the subject ID
	 * 
	 * @param subjectID
	 *            to set
	 */
	public void setSubjectID(String subjectID) {
		this.subjectID = subjectID;
	}

	/**
	 * Return the subject name
	 * 
	 * @return subject's name
	 */
	public String getsName() {
		return sName;
	}

	/**
	 * Sets the subject name
	 * 
	 * @param sName
	 *            - the name to set
	 */
	public void setsName(String sName) {
		this.sName = sName;
	}

	/**
	 * String representation of Subject class
	 */
	@Override
	public String toString() {
		return "Subject name: " + getsName() + " Subject ID: " + getSubjectID();
	}

}
