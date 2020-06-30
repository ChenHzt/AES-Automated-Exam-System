package gui;

/**
 * Extends QuestionListViewCell<T>
 * Overrides abstract methods in super class
 * used for exam form for teacher list view when a teacher reviews the exam
 * she created before saving.
 * @author vakni
 */
public class QuestionListViewCellForTeacher<T> 
 					extends QuestionListViewCell<QuestionInExam>{

	@Override
	public Boolean setCorrectAnswerLabel() {
		return true;
	}

	@Override
	public void setCheckBoxInQuestion(String s) {
			
	}

}