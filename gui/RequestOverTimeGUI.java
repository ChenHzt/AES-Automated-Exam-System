package gui;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class RequestOverTimeGUI {
	public Boolean result;
	public int time;
	public String reason;
	@FXML
	private CustomTextField timeTF;

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

	@FXML
	void cancleButton(ActionEvent event) {
		result = false;
		Stage s = (Stage) cancleButton.getScene().getWindow();
		s.hide();
	}

	@FXML
	void okButtonAction(ActionEvent event) {
		if (timeTF.getText() == null) {
			errTime.setVisible(true);
			return;
		}
		try {
			time = Integer.parseInt(timeTF.getText());
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
