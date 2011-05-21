/**
 * UserAction.java
 * TODO
 * neo 2011-4-22
 */
package cn.com.hotmaze.action;

import org.apache.mina.core.session.IoSession;
import org.springframework.stereotype.Controller;

import cn.com.neo.action.IActionAdapter;

/**
 * @author neo
 *
 */
@Controller
public class UserAction extends IActionAdapter {

	public void userLogin(IoSession session, Object message) {
		
		System.out.println("execute userLogin.....");
	}
	
	public void userExit(IoSession session, Object message) {
		
		System.out.println("execute userExit.....");
	}
	
	
}
