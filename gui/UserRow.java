package gui;

import javafx.scene.control.CheckBox;
import logic.User;

/**
 * this class represent user in table
 * 
 * @author chen1
 *
 */
public class UserRow {
	// **************************************************
	// Fields
	// **************************************************
	private User user;
	private String userID;
	private String userName;
	private CheckBox check;

	// **************************************************
	// Constructors
	// **************************************************
	public UserRow(User user, CheckBox c) {
		super();
		this.user = user;
		this.userID = user.getuID();
		this.userName = user.getuName();
		check = c;
	}

	/**
	 * return user id
	 * 
	 * @return
	 */
	public String getUserID() {
		return userID;
	}

	/**
	 * set the user id
	 * 
	 * @param userID
	 */
	public void setUserID(String userID) {
		this.userID = userID;
	}

	/**
	 * return the user name
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * set the user name
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * return check box to choose students
	 * 
	 * @return
	 */
	public CheckBox getCheck() {
		return check;
	}

	/**
	 * set checkbox to choose students
	 * 
	 * @param check
	 */
	public void setCheck(CheckBox check) {
		this.check = check;
	}

}
