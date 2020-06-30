package login;

import client.ClientConsole;
import common.Message;
import logic.User;
import ocsf.server.ConnectionToClient;
import server.IDBHandler;

/**
 * this class controls all interaction about the login.
 * 
 * @author chen1
 *
 */
public class LoginController {
	// **************************************************
	// Fields
	// **************************************************
	User userToSend;
	static User user;
	ClientConsole client;
	private IDBHandler handler;

	// **************************************************
	// constructors
	// **************************************************

	/**
	 * Default constructor
	 */
	public LoginController() {
		if (LoginGUI.flag)
			this.client = new ClientConsole(LoginGUI.IP, LoginGUI.port, null);
		else
			this.client = new ClientConsole("localhost", 5555, null);
	}

	/**
	 * saves in server the map of connected users and the connection to each user
	 * 
	 * @return
	 */
	public ConnectionToClient getClientConnection() {
		Message msg = new Message();
		msg.setSentObj(null);
		msg.setqueryToDo("getConnection");
		msg.setClassType("User");
		client.accept(msg);

		try {
			Thread.sleep(4000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		return (ConnectionToClient) msg.getReturnObj();
	}

	/**
	 * --tested-- this method checks if user ID and password are entered
	 * 
	 * @param u
	 * @return
	 */
	public Boolean checkUserDetails(User u) {
		if (u.getuID() == null || u.getPassword() == null || u.getuID().equals("") || u.getPassword().equals(""))
			return false;
		return true;

	}

	/**
	 * --tested-- this method receive user to check- u- and full real
	 * user-userToCompare- and check if u ID is the same as ID of userToCompare
	 * 
	 * @param u
	 * @param userToCompare
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Boolean checkPassword(User u, User userToCompare) throws IllegalArgumentException {
		if (!u.getuID().equals(userToCompare.getuID()))
			throw new IllegalArgumentException("ID of userToCompare is diffrent from ID of user");
		if (u.getPassword().equals(userToCompare.getPassword()))
			return true;
		return false;
	}

	/**
	 * --tested-- sent message to db to get user details and check if user id exist
	 * 
	 * @return
	 */
	public User checkIfUserIDExist(User u, String handler) {

		Message userMsg = new Message();
		userMsg.setSentObj(u);
		userMsg.setqueryToDo("getUserDetails");
		userMsg.setClassType("User");
		userMsg.setHandler(handler);
		client.accept(userMsg);

		try {
			Thread.sleep(2500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		userMsg = client.getMessage();
		user = (User) userMsg.getReturnObj();
		if (user == null || user.getuName() == null)
			return null;
		return user;
	}

	/**
	 * --tested-- this method send message to server to login the user that received
	 * in the constructor
	 * 
	 * @return
	 */
	public User loginUser(User userToLog, String handler) throws IllegalArgumentException {
		if (userToLog == null)
			throw new IllegalArgumentException("entered user is null");
		if (handler != null && !handler.equalsIgnoreCase("test"))
			throw new IllegalArgumentException("handler is wrong");
		Message msg = new Message();
		msg.setClassType("user");
		msg.setqueryToDo("signIn");
		userToLog.setIsLoggedIn("YES");
		msg.setHandler(handler);
		msg.setSentObj(userToLog);
		client.accept(msg);
		try {
			Thread.sleep(2500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		msg = client.getMessage();
		user = (User) msg.getReturnObj();
		return user;

	}

	/**
	 * --tested-- this method checks the port user entered. if it is empty method
	 * return the default port- 5555
	 * 
	 * @param txtPort
	 * @return
	 */
	public int checkPort(String txtPort) {
		int port;

		// Port
		if (txtPort == null || txtPort.compareTo("") == 0)// empty text field
		{
			port = 5555;
		} else {
			port = Integer.parseInt(txtPort);
		}
		return port;
	}

	/**
	 * --tested-- this method checks the ip user entered. if it is empty method
	 * return the default ip
	 * 
	 * @param txtIP
	 * @return
	 */
	public String checkIP(String txtIP) {
		String ip;
		// IP
		if (txtIP == null || txtIP.compareTo("") == 0)// empty text field
		{
			ip = "localhost";
		} else {
			ip = txtIP;
		}
		return ip;
	}

	/**
	 * this method checks if user id exist and if the password is valid and if the
	 * user isn't logged in already in case of error method return error message to
	 * gui
	 * 
	 * @param enteredUser
	 * @return
	 */
	public String checkAllLoginDetails(User enteredUser, String handler) {
		User userRecived;
		if (!checkUserDetails(enteredUser)) // if one or more of fields are empty
			return ("details are missing");
		else {
			userRecived = checkIfUserIDExist(enteredUser, handler);
			if (userRecived == null) // if user id doesn't exist
			{
				return ("User ID doesn't exist");
			} else if (!checkPassword(enteredUser, userRecived)) // if password is incorrect
			{
				return ("password is wrong");
			} else if (userRecived.getIsLoggedIn().equalsIgnoreCase("YES")) // if user already connected
			{
				return ("user already logged in");
			}
		}
		return "OK" + userRecived.getTitle();
	}

	/**
	 * this method send message to server to logout the user that is logged in
	 */
	public void logoutUser() {
		Message msg = new Message();
		msg.setClassType("user");
		msg.setqueryToDo("logout");
		user.setIsLoggedIn("NO");
		msg.setSentObj(user);

		client.accept(msg);
		try {
			Thread.sleep(1500L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * return the logged user details
	 * 
	 * @return
	 */
	public User getUser() {
		return user;
	}

}
