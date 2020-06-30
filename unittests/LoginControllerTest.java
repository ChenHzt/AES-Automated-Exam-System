package unittests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import logic.User;
import login.LoginController;

public class LoginControllerTest {
	LoginController lc;

	@Before
	public void setUp() throws Exception {
		lc = new LoginController();
	}

	// ****************************************************
	// checkUserDetails
	// ****************************************************

	/**
	 * check if user id and password are valid not null or empty case userID is
	 * 11111 and password is null expected false- not all details are valid
	 */
	@Test
	public void checkUserDetails_userID11111_passwordNull() {

		Boolean expected = false;
		User IDNoPassword = new User("11111", null);
		Boolean result = lc.checkUserDetails(IDNoPassword);
		assertTrue(expected.equals(result));
	}

	/**
	 * check if user id and password are valid not null or empty case userID is
	 * 11111 and password is an empty string expected false- not all details are
	 * valid
	 */
	@Test
	public void checkUserDetails_userID11111_passwordEmpty() {

		Boolean expected = false;
		User IDNoPassword = new User("11111", "");
		Boolean result = lc.checkUserDetails(IDNoPassword);
		assertTrue(expected.equals(result));
	}

	/**
	 * check if user id and password are valid not null or empty case userID is null
	 * and password is qqqq expected false- not all details are valid
	 */
	@Test
	public void checkUserDetails_nullUserID_qqqqqPassword() {

		Boolean expected = false;
		User passwordNoID = new User(null, "qqqq");
		Boolean result = lc.checkUserDetails(passwordNoID);
		assertTrue(expected.equals(result));
	}

	/**
	 * check if user id and password are valid not null or empty case userID is
	 * empty string and password is qqqq expected false- not all details are valid
	 */
	@Test
	public void checkUserDetails_emptyUserID_qqqqqPassword() {

		Boolean expected = false;
		User passwordNoID = new User("", "qqqq");
		Boolean result = lc.checkUserDetails(passwordNoID);
		assertTrue(expected.equals(result));
	}

	/**
	 * check if user id and password are valid not null or empty case userID is
	 * 11111 and password is null expected false- not all details are valid
	 */
	@Test
	public void checkUserDetails_nullUserID_nullPassword() {

		Boolean expected = false;
		User noPasswordNoID = new User(null, null);
		Boolean result = lc.checkUserDetails(noPasswordNoID);
		assertTrue(expected.equals(result));
	}

	/**
	 * check if user id and password are valid not null or empty case userID is
	 * 11111 and password is aaaa expected true- all entered details are valid
	 */
	@Test
	public void checkUserDetails_11111UserID_aaaaPassword() {
		Boolean expected = true;
		User passwordAndID = new User("11111", "aaaa");
		Boolean result = lc.checkUserDetails(passwordAndID);
		assertTrue(expected.equals(result));
	}

	// ****************************************************
	// checkIfUserIDExist
	// ****************************************************

	/**
	 * check if user id exist in database case userID is 11111 and password is 0000
	 * expected true- user with ID 11111 indeed exist in database
	 */
	@Test
	public void checkIfUserIDExist_11111UserID_0000Password_testCorrect() {
		Boolean expected = true;
		User userEntered = new User("11111", "0000");
		User userRecived = lc.checkIfUserIDExist(userEntered, "test");
		Boolean result = userRecived == null ? false : true;
		assertTrue(expected.equals(result));
	}

	/**
	 * check if user id exist in database case userID is 11133 and password is 0000
	 * expected false- user with ID 11133 doesn't exist in database
	 */
	@Test
	public void checkIfUserIDExist_11133UserID_0000Password_IDNotExist() {
		Boolean expected = false;
		User userEntered = new User("11133", "0000");
		User userRecived = lc.checkIfUserIDExist(userEntered, "test");
		Boolean result = userRecived == null ? false : true;
		assertTrue(expected.equals(result));
	}

	// ****************************************************
	// CheckPassword
	// ****************************************************

	/**
	 * method under test receives entered user details to check and full user with
	 * same ID and checks if password is the same case user to check is 12121 with
	 * password 0000 and full user is with id 11111 expected exception- ID of
	 * userToCompare is different from ID of user- exception correct
	 */
	@Test
	public void checkPassword_userToCheck12121_FullUserDetailsOf11111_correctExeption() {

		User passwordCorrect = new User("12121", "0000");
		User userToCompare = new User("11111", "Donald Duck", "0000", "Teacher", "NO");
		Boolean result = null;
		try {
			result = lc.checkPassword(passwordCorrect, userToCompare);
			fail("This should have thrown an exception");
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), ("ID of userToCompare is diffrent from ID of user"));
		}
	}

	/**
	 * method under test receives entered user details to check and full user with
	 * same ID and checks if password is the same case user to check is 12121 with
	 * password 0000 and full user is with id 11111 expected exception- ID of
	 * userToCompare is different from ID of user- exception incorrect
	 */
	@Test
	public void checkPassword_userToCheck12121_FullUserDetailsOf11111_wrongExeption() {

		User passwordCorrect = new User("12121", "0000");
		User userToCompare = new User("11111", "Donald Duck", "0000", "Teacher", "NO");
		Boolean result = null;
		try {
			result = lc.checkPassword(passwordCorrect, userToCompare);
			fail("This should have thrown an exception");
		} catch (IllegalArgumentException e) {
			assertFalse(e.getMessage().equals("wrong message"));
		}
	}

	/**
	 * method under test receives entered user details to check and full user with
	 * same ID and checks if password is the same case user to check is 11111 with
	 * password 3333 and full user is with id 11111 with password 0000- password is
	 * incorrect expected false- password is wrong
	 */
	@Test
	public void checkPassword_userToCheck11111_FullUserDetailsOf11111_passwordWrong() {
		Boolean expected = false;
		User passwordCorrect = new User("11111", "3333");
		User userToCompare = new User("11111", "Donald Duck", "0000", "Teacher", "NO");
		Boolean result = null;
		try {
			result = lc.checkPassword(passwordCorrect, userToCompare);
			assertTrue(result.equals(expected));
		} catch (IllegalArgumentException e) {
			fail("This should not have thrown an exception");
		}
	}

	/**
	 * method under test receives entered user details to check and full user with
	 * same ID and checks if password is the same case user to check is 11111 with
	 * password 0000 and full user is with id 11111 with password 0000 expected
	 * true- password is correct
	 */
	@Test
	public void checkPassword_userToCheck11111_FullUserDetailsOf11111_passwordCorrect() {
		Boolean expected = true;
		User passwordCorrect = new User("11111", "0000");
		User userToCompare = new User("11111", "Donald Duck", "0000", "Teacher", "NO");
		Boolean result = null;
		try {
			result = lc.checkPassword(passwordCorrect, userToCompare);
			assertTrue(result.equals(expected));
		} catch (IllegalArgumentException e) {
			fail("This should not have thrown an exception");
		}
	}

	// ****************************************************
	// loginUser
	// ****************************************************
	/**
	 * method under test change user isLoggedStatus to "YES" case userID to log is
	 * 33243 -user to login does not exist expected null- user not exist
	 */
	@Test
	public void loginUser_33243UserID_IDDoesNotExist() {
		User expected = null;
		User userToLog = new User();
		userToLog.setuID("33243");
		User result = lc.loginUser(userToLog, "test");
		assertTrue(result == expected);
	}

	/**
	 * method under test change user isLoggedStatus to "YES" case userID to log is
	 * 11111 - existing user expected full user details with isLoggedStatus equals
	 * to yes
	 */
	@Test
	public void loginUser_33243UserID_IDExist_UserNotConnectedAlready() {
		User expected = new User("11111", "Donald Duck", "0000", "Teacher", "YES");
		User userToLog = new User();
		userToLog.setuID("11111");
		User result = lc.loginUser(userToLog, "test");
		assertTrue(result.getuID().equals(expected.getuID()));
		assertTrue(result.getuName().equals(expected.getuName()));
		assertTrue(result.getTitle().equals(expected.getTitle()));
		assertTrue(result.getIsLoggedIn().equals(expected.getIsLoggedIn()));
		assertTrue(result.getPassword().equals(expected.getPassword()));
	}

	/**
	 * method under test change user isLoggedStatus to "YES" case userID to log is
	 * 33333 - existing user expected full user details with isLoggedStatus equals
	 * to yes
	 */
	@Test
	public void loginUser_33243UserID_IDExist_UserConnectedAlready() {
		User expected = null;
		User userToLog = new User();
		userToLog.setuID("33333");
		User result = null;
		try {
			result = lc.loginUser(userToLog, "test");
			assertTrue(expected == result);
		} catch (IllegalArgumentException e) {
			fail("this should have not thrown an exception");
		}
	}

	/**
	 * method under test change user isLoggedStatus to "YES" case user is null
	 * expected exception- entered user is null- exception correct
	 */
	@Test
	public void loginUser_nullUser_exceptionCorrect() {
		User userToLog = null;
		User result = null;
		try {
			result = lc.loginUser(userToLog, "test");
			fail("this should have thrown an exception");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("entered user is null"));
		}
	}

	/**
	 * method under test change user isLoggedStatus to "YES" case user is null
	 * expected exception- entered user is null- exception wrong
	 */
	@Test
	public void loginUser_nullUser_exceptionWrong() {
		User userToLog = null;
		User result = null;
		try {
			result = lc.loginUser(userToLog, "test");
			fail("this should have thrown an exception");
		} catch (IllegalArgumentException e) {
			assertFalse(e.getMessage().equals("user is null"));
		}
	}

	/**
	 * method under test change user isLoggedStatus to "YES" case user with ID 11111
	 * expected exception- handler is wrong- exception correct
	 */
	@Test
	public void loginUser_11111User_handlerWrongInput_ExceptionCorrect() {
		User userToLog = new User();
		userToLog.setuID("11111");
		User result = null;
		try {
			result = lc.loginUser(userToLog, "handler");
			fail("this should have thrown an exception");
		} catch (IllegalArgumentException e) {
			assertTrue(e.getMessage().equals("handler is wrong"));
		}
	}

	/**
	 * method under test change user isLoggedStatus to "YES" case user with ID 11111
	 * expected exception- handler is wrong- exception wrong
	 */
	@Test
	public void loginUser_11111User_handlerWrongInput_ExceptionWrong() {
		User userToLog = new User();
		userToLog.setuID("11111");
		User result = null;
		try {
			result = lc.loginUser(userToLog, "handler");
			fail("this should have thrown an exception");
		} catch (IllegalArgumentException e) {
			assertFalse(e.getMessage().equals("exception here"));
		}
	}

	// ****************************************************
	// checkPort
	// ****************************************************
	/**
	 * method under test check the port- if null or empty return deafult port check
	 * case that received port is null expected- port is 5555
	 */
	@Test
	public void checkPort_portIsNull() {
		int expected = 5555;
		User userToLog = new User();
		userToLog.setuID("11111");
		int result = 0;
		try {
			result = lc.checkPort(null);
			assertTrue(expected == result);

		} catch (NumberFormatException e) {
			fail("this should have not thrown an exception");
		}
	}

	/**
	 * method under test check the port- if null or empty return deafult port check
	 * case that received port is empty string expected- port is 5555
	 */
	@Test
	public void checkPort_portIsEmptyString() {
		int expected = 5555;
		User userToLog = new User();
		userToLog.setuID("11111");
		int result = 0;
		try {
			result = lc.checkPort("");
			assertTrue(expected == result);

		} catch (NumberFormatException e) {
			fail("this should have not thrown an exception");
		}
	}

	/**
	 * method under test check the port- if null or empty return deafult port check
	 * case that received port is not a number expected- NumberFormatException is
	 * thrown with message For input string:"aa"
	 */
	@Test
	public void checkPort_portIsNotNumber() {
		int result = 0;
		try {
			result = lc.checkPort("aa");
			fail("this should have thrown an exception");

		} catch (NumberFormatException e) {
			assertTrue(e.getMessage().equals("For input string: \"aa\""));
		}
	}

	/**
	 * method under test check the port- if null or empty return deafult port check
	 * case that received port is correct and equels to 1234 expected- integer port-
	 * 1234
	 */
	@Test
	public void checkPort_portIs1234() {
		int expected = 1234;
		int result = 0;
		try {
			result = lc.checkPort("1234");
			assertTrue(expected == result);

		} catch (NumberFormatException e) {
			fail("this should have not thrown an exception");
		}
	}

	// ****************************************************
	// checkIP
	// ****************************************************

	/**
	 * method under test check the IP- if null or empty return default IP=localhost
	 * check case that received IP is null expected- IP is localhost
	 */
	@Test
	public void checkIP_IPIsNull() {
		String expected = "localhost";
		String result;
		result = lc.checkIP(null);
		assertTrue(expected.equals(result));
	}

	/**
	 * method under test check the IP- if null or empty return default IP=localhost
	 * check case that received IP is empty string expected- IP is localhost
	 */
	@Test
	public void checkIP_IPIsEmptyString() {
		String expected = "localhost";
		String result;
		result = lc.checkIP("");
		assertTrue(expected.equals(result));
	}

	/**
	 * method under test check the IP- if null or empty return default IP=localhost
	 * check case that received IP valid ip address expected- IP is the received IP
	 */
	@Test
	public void checkIP_IPCorrect() {
		String expected = "192.168.171.47";
		String result;
		result = lc.checkIP("192.168.171.47");
		assertTrue(expected.equals(result));
	}

	// ****************************************************
	// checkAllLoginDetails
	// ****************************************************

	/**
	 * method under test check if all condition to login user are filled check case
	 * that user id is null expected message- details are missing
	 */
	@Test
	public void checkAllLoginDetails_missingUserID() {
		String expected = "details are missing";
		String result;
		result = lc.checkAllLoginDetails(new User(null, "1212"), "test");
		assertTrue(expected.equals(result));
	}

	/**
	 * method under test check if all condition to login user are filled check case
	 * that password is null expected message- details are missing
	 */
	@Test
	public void checkAllLoginDetails_missingPassword() {
		String expected = "details are missing";
		String result;
		result = lc.checkAllLoginDetails(new User("11111", null), "test");
		assertTrue(expected.equals(result));
	}

	/**
	 * method under test check if all condition to login user are filled check case
	 * that user id is empty String expected message- details are missing
	 */
	@Test
	public void checkAllLoginDetails_emptyUserID() {
		String expected = "details are missing";
		String result;
		result = lc.checkAllLoginDetails(new User("", "1121"), "test");
		assertTrue(expected.equals(result));
	}

	/**
	 * method under test check if all condition to login user are filled check case
	 * that password is empty string expected message- details are missing
	 */
	@Test
	public void checkAllLoginDetails_emptyPassword() {
		String expected = "details are missing";
		String result;
		result = lc.checkAllLoginDetails(new User("11111", ""), "test");
		assertTrue(expected.equals(result));
	}

	/**
	 * method under test check if all condition to login user are filled check case
	 * that all details are valid but user id doesn't exist expected message- "User
	 * ID doesn't exist"
	 */
	@Test
	public void checkAllLoginDetails_userIDNotExist() {
		String expected = "User ID doesn't exist";
		String result;
		result = lc.checkAllLoginDetails(new User("55555", "1212"), "test");
		assertTrue(expected.equals(result));
	}

	/**
	 * method under test check if all condition to login user are filled check case
	 * that user id exist but password is incorrect expected message- "password is
	 * wrong"
	 */
	@Test
	public void checkAllLoginDetails_UserIDCorrect_passwordIsWrong() {
		String expected = "password is wrong";
		String result;
		result = lc.checkAllLoginDetails(new User("11111", "3434"), "test");
		assertTrue(expected.equals(result));
	}

	/**
	 * method under test check if all condition to login user are filled check case
	 * that password is correct but user already logged in expected message- "user
	 * already logged in"
	 */
	@Test
	public void checkAllLoginDetails_userAlreadyConnected() {
		String expected = "user already logged in";
		String result;
		result = lc.checkAllLoginDetails(new User("33333", "5555"), "test");
		assertTrue(expected.equals(result));
	}

	/**
	 * method under test check if all condition to login user are filled check case
	 * that all conditions are fulfilled and user is a teacher expected message-
	 * "OKTeacher"
	 */
	@Test
	public void checkAllLoginDetails_userIsTeacher() {
		String expected = "OKTeacher";
		String result;
		result = lc.checkAllLoginDetails(new User("11111", "0000"), "test");
		assertTrue(expected.equals(result));
	}

	/**
	 * method under test check if all condition to login user are filled check case
	 * that all conditions are fulfilled and user is a Student expected message-
	 * "OKStudent"
	 */
	@Test
	public void checkAllLoginDetails_userIsStudent() {
		String expected = "OKStudent";
		String result;
		result = lc.checkAllLoginDetails(new User("12121", "1111"), "test");
		assertTrue(expected.equals(result));
	}

	/**
	 * method under test check if all condition to login user are filled check case
	 * that all conditions are fulfilled and user is a Principal expected message-
	 * "OKPrincipal"
	 */
	@Test
	public void checkAllLoginDetails_userIsPrincipal() {
		String expected = "OKPrincipal";
		String result;
		result = lc.checkAllLoginDetails(new User("22222", "1212"), "test");
		assertTrue(expected.equals(result));
	}
}
