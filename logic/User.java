package logic;

import java.io.Serializable;

/**
 * This class holds user private information and status in the system
 * 
 * @author reut
 *
 */
public class User implements Serializable {

	// **************************************************
	// Fields
	// **************************************************

	private String uID;
	private String uName;
	private String password;
	private String Title;// holds the user type in the system to determine his permeations
	private String isLoggedIn; // flag to determine user status in the system

	// **************************************************
	// constructors
	// **************************************************

	/**
	 * Constructor. create a user with an id and password
	 * 
	 * @param uid
	 *            The id of the user
	 * @param upass
	 *            The password of the user
	 */
	public User(String uid, String upass) {
		uID = uid;
		password = upass;
	}

	public User(String uID, String uName, String password, String title, String isLoggedIn) {
		super();
		this.uID = uID;
		this.uName = uName;
		this.password = password;
		Title = title;
		this.isLoggedIn = isLoggedIn;
	}

	/**
	 * Default constructor.
	 */
	public User() {

	}

	/**
	 * Copy constructor.
	 * 
	 * @param user
	 *            - The user to copy
	 */
	public User(User user) {
		this(user.getuID(), user.getuName());
	}

	/**
	 * Return the user id
	 * 
	 * @return the user id
	 */
	public String getuID() {
		return uID;
	}

	/**
	 * Sets the user id
	 * 
	 * @param uID
	 *            - the id to be set
	 */
	public void setuID(String uID) {
		this.uID = uID;
	}

	/**
	 * Returns the user name
	 * 
	 * @return This user name
	 */
	public String getuName() {
		return uName;
	}

	/**
	 * Sets this user name
	 * 
	 * @param uName
	 *            - The name to be set
	 */
	public void setuName(String uName) {
		this.uName = uName;
	}

	/**
	 * Returns this user password
	 * 
	 * @return This user password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets this user Password
	 * 
	 * @param password
	 *            - the password to be set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Returns if the use is logged in
	 * 
	 * @return if the user is logged in
	 */
	public String getIsLoggedIn() {
		return isLoggedIn;
	}

	/**
	 * ' Sets user as logged in or disconnected
	 * 
	 * @param isLoggedIn
	 *            - the new connection status
	 */
	public void setIsLoggedIn(String isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	/**
	 * Returns user's title
	 * 
	 * @return the user title
	 */
	public String getTitle() {
		return Title;
	}

	/**
	 * Sets user title
	 * 
	 * @param title
	 *            - the title to be set
	 */
	public void setTitle(String title) {
		Title = title;
	}

	@Override
	public String toString() {
		return "User name: " + getuName() + "\nUser ID: " + getuID();
	}

	@Override
	public boolean equals(Object o) {

		if (o == this)
			return true;
		if (!(o instanceof User)) {
			return false;
		}

		User user = (User) o;

		return user.uID.equals(uID) && user.uName == uName && user.password == password && user.Title.equals(Title);
	}

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + uID.hashCode();
		result = 31 * result + uName.hashCode();
		result = 31 * result + password.hashCode();
		result = 31 * result + Title.hashCode();
		return result;
	}

}