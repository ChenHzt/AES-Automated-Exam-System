package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * This class creates alert boxes for system errors.
 * @author reut
 */
public class MyErrorMessage {
	
	
	static Alert alert;
	
	/**
	 * Constructor. create the alert box
	 * @param content - the String to display in the content string of the alert 
	 * @param header - the String to display in the header string of the alert
	 */
	public static void show(String content,String header){
		alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Dialog");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	

}
