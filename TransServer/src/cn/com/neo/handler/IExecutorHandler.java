/**
 * IExecutorHandler.java
 * TODO
 * neo 2011-4-26
 */
package cn.com.neo.handler;

import org.apache.mina.core.session.IoSession;

/**
 * @author neo
 *
 */
public interface IExecutorHandler {

	public void execute(IoSession session, Object message) throws Exception;
}
