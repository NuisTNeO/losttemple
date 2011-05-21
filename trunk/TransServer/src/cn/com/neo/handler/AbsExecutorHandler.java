/**
 * ExecutorHandler.java
 * TODO
 * neo 2011-4-24
 */
package cn.com.neo.handler;

import org.apache.mina.core.session.IoSession;

/**
 * @author neo
 *
 */
public abstract class AbsExecutorHandler implements IExecutorHandler {

//	private static final ThreadLocal<TaskQueue> threadLocal = new ThreadLocal<TaskQueue>();
	
	public void execute(IoSession session, Object message) throws Exception {
		
		throw new Exception("This method must be overrided!");
	}
	
}
