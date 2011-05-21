/**
 * ServerRun.java
 * TODO
 * neo 2011-4-16
 */
package cn.com.hotmaze.common;

import cn.com.neo.common.StartServer;

/**
 * 服务启动类
 * @author neo
 *
 */
public class ServerRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//装载Spring Bean
		BeanFactory.getInstance();
		
		//启动mina监听服务
		StartServer server = new StartServer();
		server.start(new TransServerHandler());
		
		
		
		
	}

}
