/**
 * TransServerHandler.java
 * TODO
 * neo 2011-4-9
 */
package cn.com.neo.handler;

import java.util.concurrent.ExecutorService;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.neo.handler.TaskQueue.Worker;


/**
 * @author neo
 *
 */
public abstract class AbsTransServerHandler extends IoHandlerAdapter {
    private final static Logger LOGGER = LoggerFactory.getLogger(AbsTransServerHandler.class);
    
    @Override
    public void sessionCreated(IoSession session) {
        session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        
        //TODO 性能调优 再处理
//        session.getConfig().setReadBufferSize(2048);
        // We're going to use SSL negotiation notification.
//        session.setAttribute(SslFilter.USE_NOTIFICATION);
        
        this.userLogin();
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        LOGGER.info("CLOSED");
        
        this.userExit();
        
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        LOGGER.info("OPENED");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) {
        LOGGER.info("*** IDLE #" + session.getIdleCount(IdleStatus.BOTH_IDLE) + " ***");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) {
        session.close(true);
        LOGGER.error(cause.getMessage());
        cause.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message)
            throws Exception {
        LOGGER.info( "Received : " + message );
        //由命令管理器统一分发 调用不同方法， 此处也可以改成读配置文件反射进行分发
        execute(session, message);
    }
    
    private void execute(IoSession session, Object message) {
    	
    	//获取绑定线程
    	String execKey = getExecutorName(session, message);
    	ExecutorService execService = ThreadManager.getStateExcutor(execKey);
    	if(null == execService) {
    		execService = ThreadManager.getStatelessExcutor();
    	}
    	
    	Runnable task = getRunnable(session, message);
    	execService.execute(task);
    }
    
    /**
     * 从执行队列中获取执行任务对象
     * @param session
     * @param message
     * @return
     */
    public Runnable getRunnable(IoSession session, Object message) {
    	
    	Worker task = TaskQueue.getInstance().getTask();
    	
    	IExecutorHandler executor = getExecutorHandler();
    	task.init(session, message, executor );
    	
    	return task;
    }

    /**
     * 获取线程绑定key
     * @param session
     * @param message
     * @return
     */
    public abstract String getExecutorName(IoSession session, Object message);
    
    /**
     * 获取执行器
     * @return
     */
    public abstract IExecutorHandler getExecutorHandler();
    
    
    public abstract void userLogin();
    
    public abstract void userExit();
    
}
