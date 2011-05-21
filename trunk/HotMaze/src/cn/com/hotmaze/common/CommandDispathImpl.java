/**
 * CommandDispathImpl.java
 * TODO
 * neo 2011-4-16
 */
package cn.com.hotmaze.common;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.neo.acceptor.Message;
import cn.com.neo.action.UserAction;

/**
 * @author neo
 *
 */
public class CommandDispathImpl {



//	private static Logger logger = LoggerFactory.getLogger(CommandDispathImpl.class);
	
//	private static ApplicationContext context = new ClasspathApplicationContext("applicationContext.xml");
	
	private static CommandDispathImpl instance = null;
	
	static {
		
	}
	
	public static CommandDispathImpl getInstance() {
		if(instance == null) {
			instance = new CommandDispathImpl();
		}
		return instance;
	}
	
	//TODO ���������ڴ˴����
	interface CommandDefine {
		
		public static final String LOGIN = "login";
		public static final String EXIT = "exit";
	}
	
	
	
	
	
	
	public void dispatchCommand(IoSession session, Object message) {
		
		Message msg = null;
		if(message instanceof Message) {
			msg = (Message) message;
		} else {
//			logger.error("�յ��Ƿ���Ϣ:" + message.toString());
			System.out.println("�յ��Ƿ���Ϣ:" + message.toString());
			if(message instanceof Object[]){
				Object[] datas = (Object[]) message;
				for (int i = 0; i < datas.length; i++) {
					System.out.println("�յ� " + datas[i]);
				}
			}
			int[] array = {1,2,3,4,5};
			session.write(array);
//			session.write("congratulations... got it...");
//			session.write("�յ����������� " + message);
			return;
		}
		
		
		
		String cmd = msg.getCmd();
//		logger.info("�յ���Ϣ:" + cmd);
		System.out.println("�յ���Ϣ:" + cmd);
		if(CommandDefine.LOGIN.equals(cmd)) {
			UserAction userAct = (UserAction) BeanFactory.getInstance().getBean("userAction", UserAction.class);
			userAct.userLogin(session, message);
		} else if(CommandDefine.EXIT.equals(cmd)) {
			UserAction userAct = (UserAction) BeanFactory.getInstance().getBean("userAction", UserAction.class);
			userAct.userExit(session, message);
		} else {
//			logger.warn("δ֪���" + cmd);
			System.out.println("δ֪���" + cmd);
		}
		
	}

}
