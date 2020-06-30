// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

package client;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import common.ChatIF;
import common.Message;
import common.MyFile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import logic.ExecutionDetails;
import logic.StudentInExam;
import logic.User;
import ocsf.client.AbstractClient;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;
 * @author Fran&ccedil;ois B&eacute;langer
 * @version July 2000
 */
public class ChatClient extends AbstractClient {
	// Instance variables **********************************************

	/**
	 * The interface type variable. It allows the implementation of the display
	 * method in the client.
	 */
	ChatIF clientUI;
	public ObservableSet<User> connected;
	public ObservableMap<ExecutionDetails, ArrayList<StudentInExam>> examnieeList;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the chat client.
	 *
	 * @param host
	 *            The server to connect to.
	 * @param port
	 *            The port number to connect on.
	 * @param clientUI
	 *            The interface type variable.
	 */

	public ChatClient(String host, int port, ChatIF clientUI) throws IOException {
		super(host, port); // Call the superclass constructor
		this.clientUI = clientUI;
		openConnection();
		connected = FXCollections.observableSet();
		examnieeList = FXCollections.observableHashMap();

	}

	// Instance methods ************************************************

	/**
	 * This method handles all data that comes in from the server.
	 *
	 * @param msg
	 *            The message from the server.
	 */
	public void handleMessageFromServer(Object msg) {
		if (msg instanceof ArrayList) {

			connected.clear();
			connected.addAll((ArrayList) msg);
		} else if (msg instanceof HashMap) {
			examnieeList.clear();
			HashMap<ExecutionDetails, ArrayList<StudentInExam>> temp = (HashMap<ExecutionDetails, ArrayList<StudentInExam>>) msg;
			Iterator it = temp.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				examnieeList.put((ExecutionDetails) pair.getKey(), (ArrayList<StudentInExam>) pair.getValue());
			}
		} else
			clientUI.display(msg);

	}

	/**
	 * This method handles all data coming from the UI
	 *
	 * @param message
	 *            The message from the UI.
	 */
	public void handleMessageFromClientUI(Object message) {
		Message msg = (Message) message;
		if (msg.getqueryToDo().equals("uploadWordFileExam")) {

			MyFile file = new MyFile(((String) msg.getSentObj()));
			String LocalfilePath = ("C:\\exams\\" + (String) msg.getSentObj());
			System.out.println((String) msg.getSentObj());
			File newFile = new File(LocalfilePath);
			byte[] mybytearray = new byte[(int) newFile.length()];
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(newFile);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			BufferedInputStream bis = new BufferedInputStream(fis);
			file.initArray(mybytearray.length);
			file.setSize(mybytearray.length);
			try {
				bis.read(file.getMybytearray(), 0, mybytearray.length);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				System.out.println("err1");
				e1.printStackTrace();
			}
			try {
				sendToServer(file);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("err2");
			}

		} else {
			try {
				sendToServer(message);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println(("Could not send message to server.  Terminating client."));

				quit();
			}
		}
	}

	/**
	 * This method terminates the client.
	 */
	public void quit() {
		try {
			closeConnection();
		} catch (IOException e) {
		}
		System.exit(0);
	}

	public void addCilentToConnectedList(User u) {
		connected.add(u);

	}

	public void removeCilentFromConnectedList(User u) {
		connected.add(u);
	}

	public void printAllConnectedUsers() {
		for (User u : connected) {
			System.out.println(u);
		}
	}
}
// End of ChatClient class
