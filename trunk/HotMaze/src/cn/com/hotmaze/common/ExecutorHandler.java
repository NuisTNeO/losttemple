/**
 * ExecutorHandler.java
 * TODO
 * neo 2011-4-27
 */
package cn.com.hotmaze.common;

import org.apache.mina.core.session.IoSession;

import cn.com.neo.handler.AbsExecutorHandler;

/**
 * @author neo
 *
 */
public class ExecutorHandler extends AbsExecutorHandler {

	public void execute(IoSession session, Object message) throws Exception {


		CommandDispathImpl.getInstance().dispatchCommand(session, message);
	}
}
