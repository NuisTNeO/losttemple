/**
 * HotMazeTransServerHandler.java
 * TODO
 * neo 2011-4-19
 */
package cn.com.hotmaze.common;


import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.neo.handler.AbsTransServerHandler;
import cn.com.neo.handler.IExecutorHandler;

/**
 * @author neo
 *
 */
public class TransServerHandler extends AbsTransServerHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(TransServerHandler.class);
	
	
	/* (non-Javadoc)
	 * @see cn.com.neo.handler.TransServerHandler#userExit()
	 */
	@Override
	public void userExit() {
		// TODO Auto-generated method stub
		System.out.println("有人退出啦。。。" + "");
	}

	/* (non-Javadoc)
	 * @see cn.com.neo.handler.TransServerHandler#userLogin()
	 */
	@Override
	public void userLogin() {
		// TODO Auto-generated method stub
		System.out.println("有人登陆进来啦。。。" + "");
	}

	/* (non-Javadoc)
	 * @see cn.com.neo.handler.AbsTransServerHandler#getExecutorHandler()
	 */
	@Override
	public IExecutorHandler getExecutorHandler() {
		
		return new ExecutorHandler();
	}

	/* (non-Javadoc)
	 * @see cn.com.neo.handler.AbsTransServerHandler#getExecutorName(org.apache.mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public String getExecutorName(IoSession session, Object message) {
		
		session.getAttribute("u_lg");
		return null;
	}

}
