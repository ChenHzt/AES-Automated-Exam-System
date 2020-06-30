package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import logic.OvertimeDetails;
import logic.PrincipalController;

/**
 * This class mnage the overtime request window.
 * @author reut
 */
public class OvertimeRequestMenuGUI {
	// **************************************************
	// Fields
	// **************************************************
	@FXML
	private TableView<OvertimeDetails> table; // table of overtime requests

	@FXML
	private TableColumn<OvertimeDetails, String> examIDCol; // examID column in the overtime requests table

	@FXML
	private TableColumn<OvertimeDetails, Integer> execIDCol; // executionID column in the overtime requests table

	@FXML
	private TableColumn<OvertimeDetails, Integer> timeCol; // amount of requested overtime in minutes column in the
															// overtime requests table

	@FXML
	private TableColumn<OvertimeDetails, String> reasonCol; // reason for overtime column in the overtime requests table

	@FXML
	private Button approveBtn; // approve request button

	@FXML
	private Button denyBtn; // deny request button

	private PrincipalController pc;

	private ObservableList<OvertimeDetails> overtimeOL;

	
	/**
	 * this method send request to server to approve the teacher overtime request
	 * 
	 * @param event
	 */
	@FXML
	public void approveBtnAction(ActionEvent event) {
		if (table.getSelectionModel().getSelectedItem() == null)
			return;
		OvertimeDetails r = table.getSelectionModel().getSelectedItem();
		pc.approveOvertime(r);
		for (OvertimeDetails otd : overtimeOL)
			if (otd.getExamID().equals(r.getExamID()) && otd.getExecutionID() == r.getExecutionID())
				overtimeOL.remove(r);
		table.getItems().clear();
		table.getItems().addAll(overtimeOL);
	}

	/**
	 * this method send request to server to deny the teacher overtime request
	 * 
	 * @param event
	 */
	@FXML
	public void denyBtnAction(ActionEvent event) {
		if (table.getSelectionModel().getSelectedItem() == null)
			return;
		OvertimeDetails r = table.getSelectionModel().getSelectedItem();
		pc.denyOvertime(r);
		for (OvertimeDetails otd : overtimeOL)
			if (otd.getExamID().equals(r.getExamID()) && otd.getExecutionID() == r.getExecutionID())
				overtimeOL.remove(r);
		table.getItems().clear();
		table.getItems().addAll(overtimeOL);
	}

	/**
	 * this method initiates the window information about overtime requests
	 */
	public void initData() {
		examIDCol.setCellValueFactory(new PropertyValueFactory<OvertimeDetails, String>("examID"));
		execIDCol.setCellValueFactory(new PropertyValueFactory<OvertimeDetails, Integer>("ExecutionID"));
		timeCol.setCellValueFactory(new PropertyValueFactory<OvertimeDetails, Integer>("requestedTime"));
		reasonCol.setCellValueFactory(new PropertyValueFactory<OvertimeDetails, String>("reason"));
		pc = new PrincipalController();
		overtimeOL = FXCollections.observableArrayList();
		overtimeOL.addAll(pc.getAllOverTimeRequests());
		table.getItems().addAll(overtimeOL);
	}

}
