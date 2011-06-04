/**
 * UserBean.java
 * TODO
 * neo 2011-4-22
 */
package cn.com.hotmaze.entity;

/**
 * @author neo
 *
 */
public class UserEntity {

	private String userID = null;
	
	private String userName = null;
	
	private int gameStatus = 0;
	
	
	

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setGameStatus(int gameStatus) {
		this.gameStatus = gameStatus;
	}

	public int getGameStatus() {
		return gameStatus;
	}
	
	
}
