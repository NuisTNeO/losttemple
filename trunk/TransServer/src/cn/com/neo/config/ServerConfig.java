/**
 * ServerConfig.java
 * TODO
 * neo 2011-4-9
 */
package cn.com.neo.config;

import cn.com.neo.util.PropertiesUtil;

/**
 * ��ȡϵͳ�����ļ�
 * @author neo
 * 
 */
public class ServerConfig {

	private static PropertiesUtil prop = null;

	static {
		try {
			System.out.println("��ȡ�����ļ�server.properties");
			prop = PropertiesUtil.getInstance("cfg\\server.properties");
		} catch (Exception e) {
			System.out.println("��ȡserver.properties�ļ�����");
			e.printStackTrace();
		}
	}

	public static String getServer() {
//		return "192.168.1.106";
		return prop.getProperty("server");
	}

	public static int getServerPort() {
//		return 8888;
		return parseInt(prop.getProperty("server_port"));
	}

	public static int getThreadStateCount() {
//		return 3;
		return parseInt(prop.getProperty("thread_state_count"));
	}

	public static int getThreadStatelessCount() {
//		return 3;
		return parseInt(prop.getProperty("thread_stateless_count"));
	}

	public static int getMaxOnline() {
//		return 1000;
		return parseInt(prop.getProperty("online_max"));
	}

	private static int parseInt(String str) {
		if (str == null || "".equals(str))
			return 0;

		return Integer.parseInt(str.trim());
	}

}
