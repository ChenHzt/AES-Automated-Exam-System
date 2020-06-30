package client;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
import java.io.IOException;
import java.util.ArrayList;

import common.ChatIF;
import common.Message;
import logic.Question;
import logic.User;
import login.LoginGUI;

/**
 * This class constructs the UI for a chat client. It implements the chat
 * interface in order to activate the display() method. Warning: Some of the
 * code here is cloned in ServerConsole
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @version July 2000
 */
public class ClientConsole implements ChatIF {
	// Class variables *************************************************

	/**
	 * The default port to connect on.
	 */
	final public static int DEFAULT_PORT = 5555;

	// Instance variables **********************************************

	/**
	 * The instance of the client that created this ConsoleChat.
	 */
	public static ArrayList<Question> questions = new ArrayList<Question>();

	public String host;
	public LoginGUI lg;
	static ChatClient client;
	private static Message msg;
	int port;

	// Constructors ****************************************************

	/**
	 * Constructs an instance of the ClientConsole UI.
	 *
	 * @param host
	 *            The host to connect to.
	 * @param port
	 *            The port to connect on.
	 */
	public ClientConsole(String host, int port, String clientHandler) {
		this.host = host;
		this.port = port;

		try {
			client = new ChatClient(host, port, this);

		} catch (IOException exception) {
			System.out.println("Error: Can't setup connection!" + " Terminating client.");
			System.exit(1);
		}

	}

	public void addConnectedUser(User u) {
		client.addCilentToConnectedList(u);
	}

	public void removeConnectedUser(User u) {
		client.addCilentToConnectedList(u);
	}

	// Instance methods ************************************************

	/**
	 * This method waits for input from the console. Once it is received, it sends
	 * it to the client's message handler.
	 */
	public void accept(Object message) {
		client.handleMessageFromClientUI(message);
	}

	/**
	 * This method overrides the method in the ChatIF interface. It displays a
	 * message onto the screen.
	 *
	 * @param message
	 *            The string to be displayed.
	 */
	public void display(Object message) {
		this.msg = (Message) message;
		if (((Message) msg).getReturnObj() instanceof String)
			if (((String) ((Message) msg).getReturnObj()).equals("examIsLocked"))
				System.out.println("locked recived");
		if (((Message) msg).getReturnObj() instanceof String)
			if (((String) ((Message) msg).getReturnObj()).equals("newOverTimeRequest"))
				System.out.println("bla bla bla");

	}

	// Class methods ***************************************************

	/**
	 * This method is responsible for the creation of the Client UI.
	 *
	 * @param args[0]
	 *            The host to connect to.
	 */

	public Message getMessage() {
		if (msg == null)
			msg = new Message();
		return msg;
	}

}
// End of ConsoleChat class
