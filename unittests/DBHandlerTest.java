package unittests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import common.Message;
import logic.User;
import server.DBHandler;

public class DBHandlerTest {
	User user_id11111;

	@Before
	public void setUp() throws Exception {
		user_id11111 = new User("11111", "Donald Duck", "0000", "Teacher", "NO");

	}

	/**
	 * checking generateQuery method in DBHandler class check received query when we
	 * need to get specific user details- user id:11111
	 */
	@Test
	public void generateQuery_getUserDetail() {
		Message msg = new Message();
		msg.setqueryToDo("getUserDetails");
		msg.setSentObj(user_id11111);
		String expected = "SELECT * FROM user WHERE userID=11111";
		String result = DBHandler.generateQuery(msg);
		assertEquals(expected, result);
	}

	/**
	 * checking generateQuery method in DBHandler class check received query when we
	 * need to log specific user- user id:11111
	 */
	@Test
	public void generateQuery_loginUser() {
		Message msg = new Message();
		msg.setqueryToDo("signIn");
		msg.setSentObj(user_id11111);
		String expected = "SELECT * FROM user WHERE userID=11111";
		String result = DBHandler.generateQuery(msg);
		assertEquals(expected, result);
	}

	/**
	 * checking generateQuery method in DBHandler class check received query when we
	 * need to logout specific user- user id:11111
	 */
	@Test
	public void generateQuery_logoutUser() {
		Message msg = new Message();
		msg.setqueryToDo("logout");
		msg.setSentObj(user_id11111);
		String expected = "SELECT * FROM user WHERE userID=11111";
		String result = DBHandler.generateQuery(msg);
		assertEquals(expected, result);
	}

	/**
	 * checking generateQuery method in DBHandler class check received query when we
	 * send wrong message
	 */
	@Test
	public void generateQuery_wrongMessage() {
		Message msg = new Message();
		msg.setqueryToDo("wrong input here");
		msg.setSentObj(user_id11111);
		String expected = null;
		String result = DBHandler.generateQuery(msg);
		assertEquals(expected, result);
	}

}
