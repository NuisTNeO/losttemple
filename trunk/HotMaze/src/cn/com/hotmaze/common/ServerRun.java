/**
 * ServerRun.java
 * TODO
 * neo 2011-4-16
 */
package cn.com.hotmaze.common;

import cn.com.hotmaze.context.MapContext;
import cn.com.hotmaze.context.TriggerContext;
import cn.com.neo.common.StartServer;

/**
 * ����������
 * @author neo
 *
 */
public class ServerRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		//װ��Spring Bean
		BeanFactory.getInstance();
		
		//����mina��������
		StartServer server = new StartServer();
		server.start(new TransServerHandler());
		
		MapContext.getInstance();
		TriggerContext.getInstance();
		
		
	}

}
