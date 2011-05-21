/**
 * PropertiesUtil.java
 * ��ȡ�����ļ� ������
 * neo 2011-5-15
 */
package cn.com.hotmaze.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.PropertyResourceBundle;

/**
 * @author neo
 *
 */
public class PropertiesUtil {

	private static PropertiesUtil instance = null;
	
	private PropertiesUtil() {
		
	}
	
	private static PropertyResourceBundle resource = null;
	
	public static PropertiesUtil getInstance(String fileName) {
		if(instance == null) {
			instance = new PropertiesUtil();
			
			InputStream inputStream;
			try {
				inputStream = new FileInputStream(fileName);
				resource = new PropertyResourceBundle(inputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return instance;
	}
	
	public String getProperty(String key) {
		
		return resource.getString(key);
	}
	
	public Integer getPropertyForInt(String key) {
		
		return Integer.parseInt(resource.getString(key));
	}
	
	
}
