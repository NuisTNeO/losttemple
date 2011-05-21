/**
 * TaskQueue.java
 * TODO
 * neo 2011-4-22
 */
package cn.com.neo.handler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.mina.core.session.IoSession;

/**
 * 任务队列
 * @author neo
 *
 */
public class TaskQueue {
	

	private static final int DEFAULT_SIZE = 500;
	
	private BlockingQueue<Worker> tasks = new LinkedBlockingQueue<Worker>();
	
	private static TaskQueue taskQueue = null;
	
	public static TaskQueue getInstance() {
		if(taskQueue == null) {
			taskQueue = new TaskQueue();
			taskQueue.init();
		}
		return taskQueue;
	}
	
	private void init() {
		for (int i = 0; i < DEFAULT_SIZE; i++) {
			tasks.add(new Worker());
		}
	}
	
	public Worker getTask() {
		Worker task = tasks.poll();
		if(task == null) {
			task = new Worker();
			tasks.add(task);
		}
		return task;
	}
	
	private void addTask(Worker task) {
		tasks.add(task);
	}

	
	class Worker implements Runnable {

		private IoSession session = null;
		
		private Object message = null;
		
		private IExecutorHandler executor = null;
		
		public Worker() {
			
		}
		
		public void init(IoSession session, Object message, IExecutorHandler executor) {
			this.session = session;
			this.message = message;
			this.executor = executor;
		}
		
		
		@Override
		public void run() {
			
			try {
				executor.execute(session, message);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				//运行结束后，重新加入队列
				addTask(this);
			}
		}
		
		
	}
	
}
