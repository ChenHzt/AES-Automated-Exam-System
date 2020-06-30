package gui;

import java.io.Serializable;

import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * this method save the question details
 * @author vakni
 */
public class QuestionGUI implements Serializable {
	// **************************************************
    // Fields
    // **************************************************	
	private String questionID;
	private String teacherName;
	private String questionTxt;
	private ImageView image;
	private CheckBox checkButton;
	private TextField points;

	// **************************************************
    // Constructors
    // **************************************************	
	/**
	 * this constructor save the question details
	 * @param questionID -the ID of the question as String
	 * @param teacherName - the teacher name that wrote the exam as String
	 * @param questionTxt - the text of the question as String
	 * @param image
	 */
	public QuestionGUI(String questionID, String teacherName, String questionTxt, ImageView image) {
		super();
		this.questionID = questionID;
		this.teacherName = teacherName;
		this.questionTxt = questionTxt;
		this.image = image;
	}

	/**
	 * constructor that save the question details
	 * @param questionID -the ID of the question as String
	 * @param teacherName - the teacher name that wrote the exam as String
	 * @param questionTxt - the text of the question as String
	 * @param cb
	 * @paramtf
	 */
	public QuestionGUI(String questionID, String teacherName, String questionTxt, CheckBox cb, TextField tf) {
		super();
		this.questionID = questionID;
		this.teacherName = teacherName;
		this.questionTxt = questionTxt;
		this.checkButton = cb;
		points = tf;
	}

	/**
	 * this method save the image
	 * @param value as ImageView
	 */
	public void setImage(ImageView value) {
		image = value;
	}

	/**
	 * this method return the image
	 * @return ImageView
	 */
	public ImageView getImage() {
		return image;
	}

	/**
	 * this method return the question ID
	 * @return String with the question ID
	 */
	public String getQuestionID() {
		return questionID;
	}

	/**
	 * return the teacher name that wrote the question
	 * @return String with the teacher name
	 */
	public String getTeacherName() {
		return teacherName;
	}

	/**
	 * save the teacher name
	 * @param teacherName as String
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	/**
	 * return the question text
	 * @return String with the question text
	 */
	public String getQuestionTxt() {
		return questionTxt;
	}

	/**
	 * save the question text
	 * @param questionTxt as String
	 */
	public void setQuestionTxt(String questionTxt) {
		this.questionTxt = questionTxt;
	}

	/**
	 * save the question ID
	 * @param questionID as String
	 */
	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	/**
	 * return referance of object with radio button
	 * @return CheckBox
	 */
	public CheckBox getCheckButton() {
		return checkButton;
	}

	/**
	 * return the amount of points on the question
	 * @return TextField
	 */
	public TextField getPoints() {
		return points;
	}

	/**
	 * save the points of the exam 
	 * @param points 
	 */
	public void setPoints(TextField points) {
		this.points = points;
	}
}