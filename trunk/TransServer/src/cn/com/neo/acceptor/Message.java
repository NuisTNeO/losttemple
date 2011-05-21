/**
 * Message.java
 * TODO
 * neo 2011-4-10
 */
package cn.com.neo.acceptor;

import java.io.Serializable;

/**
 * @author neo
 *
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5120721443304404101L;

	private String cmd = null;
	
	private Object data = null;
	
	public Message() {
		
	}
	
	public Message(String _cmd) {
		setCmd(_cmd);
	}
	
	public Message(String _cmd, Object _data) {
		
		setCmd(_cmd);
		setData(_data);
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getData() {
		return data;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getCmd() {
		return cmd;
	}
	
	
	
}
