package server;

// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.mysql.jdbc.Connection;

import common.Message;
import common.MyFile;
import logic.ExecutionDetails;
import logic.StudentInExam;
import logic.User;
import ocsf.server.AbstractServer;
import ocsf.server.ConnectionToClient;

/**
 * This class overrides some of the methods in the abstract superclass in order
 * to give more functionality to the server.
 *
 * @author Dr Timothy C. Lethbridge
 * @author Dr Robert Lagani&egrave;re
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Paul Holden
 * @version July 2000
 */
public class EchoServer extends AbstractServer {
	// *******************************
	// Class variables
	// *******************************

	/**
	 * The default port to listen on.
	 */
	final public static int DEFAULT_PORT = 5555;
	final public static String HOST = "localhost";
	private String DBName;
	private String DBPassword;
	private Boolean isDBLoggedIn = false;
	protected Connection conn;
	protected HashMap<User, ConnectionToClient> connectedClients;
	protected ArrayList<User> connected;

	protected HashMap<ExecutionDetails, ArrayList<StudentInExam>> exemanieeList;

	// *******************************
	// Constructors
	// *******************************

	/**
	 * Constructs an instance of the echo server.
	 *
	 * @param port
	 *            The port number to connect on.
	 */
	public EchoServer(int port, String dbName, String dbPass) {
		super(port);
		this.DBName = dbName;
		this.DBPassword = dbPass;
		connectedClients = new HashMap<User, ConnectionToClient>();
		connected = new ArrayList<User>();
		exemanieeList = new HashMap<ExecutionDetails, ArrayList<StudentInExam>>();

	}

	// *******************************
	// Instance methods
	// *******************************

	protected Connection connectToDB() {
		Connection dbh = null;
		try {
			dbh = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DBName, "root", DBPassword);
			isDBLoggedIn = true;
		} catch (SQLException ex) {
			System.out.print("Sorry we had a problem, could not connect to DB server\n");
			sendToAllClients("DBConnectFail");
			isDBLoggedIn = false;
		}
		this.conn = dbh;
		return dbh;
	}

	/**
	 * This method cehcks if the DB is connected.
	 * 
	 * @return yes if the DB is connected
	 */
	public Boolean checkIfDBConnects() {
		Boolean result;
		if (connectToDB() != null)
			result = true;
		else
			result = false;
		try {
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;

	}

	/**
	 * This method handles any messages received from the client.
	 *
	 * @param msg
	 *            The message received from the client.
	 * @param client
	 *            The connection from which the message originated.
	 */
	public void handleMessageFromClient(Object msg, ConnectionToClient client) throws SQLException, IOException {
		IDBHandler handler;
		conn = connectToDB();
		if (((Message) msg).getHandler() == null) {
			handler = new DBHandler(port, DBName, DBPassword, conn);
			if (msg instanceof MyFile) {
				((DBHandler) handler).uploadExamToServer(msg, client);
				return;
			}
		} else
			handler = new StubDBHandler();

		Message m = (Message) msg;
		if (m.getClassType().equalsIgnoreCase("User"))
			handler.userHandler(m, client, conn);
		else if (m.getClassType().equalsIgnoreCase("Teacher"))
			handler.teacherHandler(m, client, conn);
		else if (m.getClassType().equalsIgnoreCase("Student")) {
			System.out.println("bla bla");
			handler.StudentrHandler(m, client, conn);
		} else if (m.getClassType().equalsIgnoreCase("Principal"))
			handler.principalHandler(m, client, conn);
		conn.close();

	}

	// *************************************************************************
	// get data or change data in DB methods
	// *************************************************************************

	/**
	 * This method overrides the one in the superclass. Called when the server
	 * starts listening for connections.
	 */
	protected void serverStarted() {
		System.out.println("Server listening for connections on port " + getPort());
	}

	/**
	 * This method overrides the one in the superclass. Called when the server stops
	 * listening for connections.
	 */
	protected void serverStopped() {
		System.out.println("Server has stopped listening for connections.");
	}

	// Class methods ***************************************************

}
// End of EchoServer class
