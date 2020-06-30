package logic;

import java.io.Serializable;

/**
 * This class holds the key details to identify an exam in execution
 * 
 * @author reut
 *
 */
public class ExecutionDetails implements Serializable {
	// **************************************************
	// Fields
	// **************************************************
	private String ExamID;
	private int ExecutionID;
	// **************************************************
	// constructors
	// **************************************************

	/**
	 * Constructor. save exam ID and execution ID of an exam that was executed
	 * (pulled out of drawer)
	 * 
	 * @param examID
	 *            The exam's id
	 * @param executionID
	 *            the number of times the exam was pulled out of the drawer
	 */
	public ExecutionDetails(String examID, int executionID) {
		super();
		ExamID = examID;
		ExecutionID = executionID;
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
	 * Return the execution ID for thi exam, the number of times this exam was used
	 * before at the timed teacher pulled it out of the drawer.
	 * 
	 * @return int with the execution ID
	 */
	public int getExecutionID() {
		return ExecutionID;
	}

	/**
	 * Sets the execution ID for this exam
	 * 
	 * @param executionID
	 *            get the execution ID as int
	 */
	public void setExecutionID(int executionID) {
		ExecutionID = executionID;
	}

	/**
	 * String representation of this class as [ExamID=%d, ExecutionID=%d]
	 */
	@Override
	public String toString() {
		return "ExecutionDetails [ExamID=" + ExamID + ", ExecutionID=" + ExecutionID + "]";
	}

	public boolean equals(Object arg0) {
		ExecutionDetails e = (ExecutionDetails) arg0;
		if (e.getExecutionID() != this.ExecutionID)
			return false;
		if (!e.getExamID().equals(this.ExamID))
			return false;
		return true;
	}

}
