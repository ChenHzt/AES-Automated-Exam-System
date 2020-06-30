package server;

import java.io.IOException;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

import common.Message;
import ocsf.server.ConnectionToClient;

public interface IDBHandler {
	public void getUserDet(String q, ConnectionToClient client);

	public void loginUser(String s, ConnectionToClient client);

	public void userHandler(Message m, ConnectionToClient client, Connection conn) throws SQLException, IOException;

	public void teacherHandler(Message m, ConnectionToClient client, Connection conn) throws SQLException, IOException;

	public void StudentrHandler(Message m, ConnectionToClient client, Connection conn) throws SQLException, IOException;

	public void principalHandler(Message m, ConnectionToClient client, Connection conn)
			throws SQLException, IOException;
}
