package server;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import com.mysql.jdbc.Connection;

import common.Message;
import logic.User;
import ocsf.server.ConnectionToClient;

public class StubDBHandler implements IDBHandler, Serializable {
	ArrayList<User> users;

	public StubDBHandler() {
		users = new ArrayList<User>();
		users.add(new User("11111", "Donald Duck", "0000", "Teacher", "NO"));
		users.add(new User("12121", "Mickey Mouse", "1111", "Student", "NO"));
		users.add(new User("22222", "Harry Potter", "1212", "Principal", "NO"));
		users.add(new User("33333", "Minney Mouse", "5555", "Teacher", "YES"));
	}

	@Override
	public void getUserDet(String q, ConnectionToClient client) {
		Message m = new Message();

		for (User u : users) {
			if (u.getuID().equals(q))
				m.setReturnObj(u);
		}
		try {
			client.sendToClient(m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void loginUser(String s, ConnectionToClient client) {
		Message m = new Message();
		for (User u : users) {
			if (u.getuID().equals(s) && u.getIsLoggedIn().equals("NO")) {
				u.setIsLoggedIn("YES");
				m.setReturnObj(u);
				break;
			}
		}
		try {
			client.sendToClient(m);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void userHandler(Message m, ConnectionToClient client, Connection conn) {
		if (m.getqueryToDo().equals("getUserDetails"))
			getUserDet(((User) m.getSentObj()).getuID(), client);
		else if (m.getqueryToDo().equals("signIn"))
			loginUser(((User) m.getSentObj()).getuID(), client);

	}

	@Override
	public void teacherHandler(Message m, ConnectionToClient client, Connection conn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void StudentrHandler(Message m, ConnectionToClient client, Connection conn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void principalHandler(Message m, ConnectionToClient client, Connection conn) {
		// TODO Auto-generated method stub

	}

}
