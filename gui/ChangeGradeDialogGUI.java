package gui;

/**
 * this class is the controller of the change grade dialog of teacher
 */
import org.controlsfx.control.textfield.CustomTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class ChangeGradeDialogGUI {
	// *********************************
	// Fields
	// *********************************
	public Boolean result;
	public int newGrade;
	public String reason;
	@FXML
	private CustomTextField gradeTF;

	@FXML
	private TextArea reasonTA;

	@FXML
	private Button okButton;

	@FXML
	private Button cancleButton;

	@FXML
	private Label errReason;

	@FXML
	private Label errTime;

	/**
	 * this method closes the dialog and discards changes that made in the class
	 * (grade doesn't change)
	 * 
	 * @param event
	 */
	@FXML
	void cancleButton(ActionEvent event) {
		result = false;
		Stage s = (Stage) cancleButton.getScene().getWindow();
		s.hide();
	}

	/**
	 * this method closes the dialog and saves the new grade and the reason for the
	 * change
	 * 
	 * @param event
	 */
	@FXML
	void okButtonAction(ActionEvent event) {
		if (gradeTF.getText() == null) {
			errTime.setVisible(true);
			return;
		}
		try {
			newGrade = Integer.parseInt(gradeTF.getText());
			if (newGrade < 0 || newGrade > 100) {
				errTime.setVisible(true);
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			errTime.setVisible(true);
			return;
		}
		reason = reasonTA.getText();
		if (reason == null)
			return;
		result = true;
		Stage s = (Stage) cancleButton.getScene().getWindow();
		s.hide();
	}

	public void initData() {

	}

}
