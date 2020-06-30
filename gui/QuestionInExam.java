package gui;

import java.io.Serializable;

import logic.Question;

/**
 * This class save details on question in exam
 * 
 * @author reut
 *
 */
public class QuestionInExam implements Serializable {

	// **************************************************
	// Fields
	// **************************************************
	private int pointsInExam;
	private Question question;
	private int serialNumberInExam;

	// **************************************************
	// constructors
	// **************************************************

	/**
	 * this constructor set the question in exam details
	 * 
	 * @param pointsInExam
	 *            as int - the point on the question in the exam
	 * @param question
	 *            as Question
	 * @param serialNumberInExam
	 *            as int
	 */
	public QuestionInExam(int pointsInExam, Question question, int serialNumberInExam) {
		this.setPointsInExam(pointsInExam);
		this.setQuestion(question);
		this.setSerialNumberInExam(serialNumberInExam);
	}

	/**
	 * return the question object that in the exam
	 * 
	 * @return questio as Question object
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * save the question in the exam
	 * 
	 * @param question
	 *            as Question
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * return the points of the qoestion in the exam
	 * 
	 * @return pointsInExam as int
	 */
	public int getPointsInExam() {
		return pointsInExam;
	}

	/**
	 * save the point of the question in the exam
	 * 
	 * @param pointsInExam
	 *            as int
	 */
	public void setPointsInExam(int pointsInExam) {
		this.pointsInExam = pointsInExam;
	}

	/**
	 * return the serial number of the question in exam
	 * 
	 * @return serialNumberInExam as int
	 */
	public int getSerialNumberInExam() {
		return serialNumberInExam;
	}

	/**
	 * save the serial number of the question in exam
	 * 
	 * @param serialNumberInExam
	 *            as int
	 */
	public void setSerialNumberInExam(int serialNumberInExam) {
		this.serialNumberInExam = serialNumberInExam;
	}

	/**
	 * set the parameters to run over the equal method
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((question == null) ? 0 : question.hashCode());
		return result;
	}

	/**
	 * to run over the equal method
	 * 
	 * @param obj
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QuestionInExam other = (QuestionInExam) obj;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}

	@Override
	public String toString() {

		return serialNumberInExam + ")\t( " + pointsInExam + (pointsInExam > 10 ? " point)\n" : " points)\n")
				+ question.toString();
	}

}
