
package server;

/**
 * This class manage the server
 * @author reut
 *
 */
import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerMain extends Application {
	// **************************************************
	// Fields
	// **************************************************
	public static boolean serverIsConnected = false;

	/**
	 * this method run the server fxml window
	 * 
	 * @param primaryStage
	 * @throws IOException
	 */
	@Override
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/server/Server.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		ServerGUI server = loader.getController();
		server.initData();
		Stage window = new Stage();
		window.setScene(scene);
		window.show();

	}

	/**
	 * the main method of the server
	 */
	public static void main(String[] args) throws SQLException {

		launch(args);
	}
}
