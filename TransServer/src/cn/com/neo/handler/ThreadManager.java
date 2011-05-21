/**
 * ThreadManager.java
 * TODO
 * neo 2011-4-18
 */
package cn.com.neo.handler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.mina.filter.executor.OrderedThreadPoolExecutor;

import cn.com.neo.config.ServerConfig;

/**
 * @author neo
 *
 */
public class ThreadManager {

	
	private static int curStateCount = 0;
	
	private static int stateThreadTotal = ServerConfig.getThreadStateCount();
	
	private static ExecutorService statePoolExecutor = Executors.newFixedThreadPool(stateThreadTotal);
	
	private static ExecutorService statelessPoolExecutor = Executors.newFixedThreadPool(ServerConfig.getThreadStatelessCount());
	
//	private static ExecutorService orderExecutors = new OrderedThreadPoolExecutor(3, 5, 3, TimeUnit.SECONDS);
	
	private static ExecutorService[] stateExecutors = new ExecutorService[stateThreadTotal];
	
	private static ConcurrentMap<String, ExecutorService> executorMap = new ConcurrentHashMap<String, ExecutorService>();
	
	static {
		for(int i = 0; i < stateThreadTotal; i++) {
			stateExecutors[i] = Executors.newSingleThreadExecutor();
		}
	}
	
	public static ExecutorService getStateExcutor(String execKey) {
		
		if(null == execKey || "".equals(execKey)) {
			return null;
		}
		ExecutorService executor = executorMap.get(execKey);
		if(executor == null) {
			executor = stateExecutors[curStateCount++ % stateThreadTotal];
			executorMap.put(execKey, executor);
		}
		return executor;
	}
	
	public static ExecutorService getStatelessExcutor() {
		return statelessPoolExecutor;
	}
}
