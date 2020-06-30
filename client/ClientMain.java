package client;

import javafx.application.Application;
import javafx.stage.Stage;
import login.LoginGUI;

/**
 * this class is the main of the client thread. it start to run the login GUI
 * 
 * @author chen1
 *
 */
public class ClientMain extends Application {
	/**
	 * LoginContol variable to create instance of LoginContol to access the start
	 * function in LoginContol class
	 */
	private LoginGUI LoginWindow;

	public static void main(String[] args) {
		launch(args);

	}

	/**
	 * open login window
	 */
	@Override
	public void start(Stage arg0) throws Exception {

		LoginWindow = new LoginGUI();
		LoginWindow.start(arg0);

	}
}
