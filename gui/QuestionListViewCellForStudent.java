package gui;

/**
 * Extends QuestionListViewCell<T>
 * Overrides abstract methods in super class
 * used for exam form for student list view when a student solves a test. 
 * @author vakni
 */
public class QuestionListViewCellForStudent<T> extends QuestionListViewCell<QuestionInExam> {

	@Override
	public Boolean setCorrectAnswerLabel() {
		return false;
	}

	@Override
	public void setCheckBoxInQuestion(String s) {

	}

}