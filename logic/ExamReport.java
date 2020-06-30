package logic;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * This class holds the statistic calculation of an exam It holds the histogram
 * divided by 10%, the median grade of the exam and the average.
 * 
 * @author reut
 *
 */
public class ExamReport implements Serializable {
	// **************************************************
	// Constants
	// **************************************************
	public final String[] headers = { "0-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90",
			"91-100" };

	// **************************************************
	// Fields
	// **************************************************
	private String examID;
	private int executionID;
	private int[] percentages;
	private float avg;
	private int midean;
	private Boolean student;
	private int counter;

	// **************************************************
	// constructors
	// **************************************************
	/**
	 * Default constructor. creates an empty ExamReport object
	 */
	public ExamReport() {
		student = false;

	}

	/**
	 * Constructor. receives an array list of grades as integers and populates the
	 * histogram, median and
	 * 
	 * @param gradeArr
	 *            - The list of the grade to compute
	 * @param examID
	 *            - The exam ID
	 * @param executionID
	 *            - The number of the execution of the exam
	 */
	public ExamReport(ArrayList<Integer> gradeArr, String examID, int executionID) {

		if (gradeArr == null || gradeArr.size() <= 0)
			return;

		student = true;
		this.examID = examID;
		this.executionID = executionID;
		int sum = 0;
		int[] per = new int[10];
		setCounter(gradeArr.size());
		/*
		 * dividing the grades into the different percentages. preparing the data for
		 * the histogram
		 */
		for (int i = 0; i < gradeArr.size(); i++) {
			int grade = gradeArr.get(i);
			sum += grade;
			if (0 <= grade && grade <= 10)
				per[0]++;
			if (11 <= grade && grade <= 20)
				per[1]++;
			if (21 <= grade && grade <= 30)
				per[2]++;
			if (31 <= grade && grade <= 40)
				per[3]++;
			if (41 <= grade && grade <= 50)
				per[4]++;
			if (51 <= grade && grade <= 60)
				per[5]++;
			if (61 <= grade && grade <= 70)
				per[6]++;
			if (71 <= grade && grade <= 80)
				per[7]++;
			if (81 <= grade && grade <= 90)
				per[8]++;
			if (91 <= grade && grade <= 100)
				per[9]++;
		}

		Collections.sort(gradeArr);
		this.percentages = per;
		midean = gradeArr.get(gradeArr.size() / 2);
		avg = (float) sum / gradeArr.size();
	}

	/**
	 * Returns an array of the grades divided into percentages
	 * 
	 * @return the populated grade as an array of Integer
	 */
	public int[] getPercentages() {
		return percentages;
	}

	/**
	 * Sets the array of the percentages
	 * 
	 * @param percentages
	 *            - the array to save
	 */
	public void setPercentages(int[] percentages) {
		this.percentages = percentages;
	}

	/**
	 * Returns the average of this exam
	 * 
	 * @return The average of this exam's grades
	 */
	public float getAvg() {
		return avg;
	}

	/**
	 * Sets the average of the exam
	 * 
	 * @param avg
	 */
	public void setAvg(float avg) {
		this.avg = avg;
	}

	/**
	 * Returns the median of this exam
	 * 
	 * @return The median grade of the exam
	 */
	public int getMidean() {
		return midean;
	}

	/**
	 * Sets the exam ID
	 * 
	 * @param The
	 *            examId to set
	 */
	public void setMidean(int midean) {
		this.midean = midean;
	}

	/**
	 * Return the execution ID for thi exam, the number of times this exam was used
	 * before at the timed teacher pulled it out of the drawer.
	 * 
	 * @return int with the execution ID
	 */
	public int getExecutionID() {
		return executionID;
	}

	/**
	 * Sets the execution ID for this exam
	 * 
	 * @param executionID
	 *            get the execution ID as int
	 */
	public void setExecutionID(int executionID) {
		this.executionID = executionID;
	}

	/**
	 * Return the ID of the exam
	 * 
	 * @return the exam as String
	 */
	public String getExamID() {
		return examID;
	}

	/**
	 * Sets the exam ID
	 * 
	 * @param The
	 *            examId to set
	 */
	public void setExamID(String examID) {
		this.examID = examID;
	}

	/**
	 * Returns the number of students calculated int the histogram
	 */
	public int getCounter() {
		return counter;
	}

	/**
	 * Sets the number of students calculated int the histogram
	 * 
	 * @param The
	 *            number to set
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}

	@Override
	public String toString() {
		return "ExamReport [examID=" + examID + ", executionID=" + executionID + "]";
	}

}
