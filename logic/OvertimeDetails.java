package logic;

import java.io.Serializable;

/**
 * This class holds all details for request for overtime sent from teacher to
 * principal.
 */
public class OvertimeDetails implements Serializable {

	// **************************************************
	// Fields
	// **************************************************
	private String ExamID;
	private int ExecutionID;
	private int requestedTime;
	private String reason;
	private Boolean isApproved;

	// **************************************************
	// constructors
	// **************************************************
	/**
	 * 
	 */
	public OvertimeDetails(String examID, int executionID, int requestedTime, String reason, Boolean isApproved) {
		super();
		ExamID = examID;
		ExecutionID = executionID;
		this.requestedTime = requestedTime;
		this.reason = reason;
		this.isApproved = isApproved;
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
	 * get the requested amount of time to add to the current exam
	 */
	public int getRequestedTime() {
		return requestedTime;
	}

	/**
	 * Set the requested amount of time to add to the current exam
	 */
	public void setRequestedTime(int requestedTime) {
		this.requestedTime = requestedTime;
	}

	/**
	 * Returns the reason for the overtime request
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * Sets reason for overtime request
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * Returns true if overtime request was approved, false otherwise
	 */
	public Boolean getIsApproved() {
		return isApproved;
	}

	/**
	 * Sets the boolean flag isApproved. true if overtime was approved by proncipal,
	 * false otherwise
	 */
	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

}
