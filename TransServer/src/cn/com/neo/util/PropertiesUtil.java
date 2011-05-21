/**
 * PropertiesUtil.java
 * 读取属性文件 工具类
 * neo 2011-5-15
 */
package cn.com.neo.util;

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
			try {
				InputStream inputStream = new FileInputStream(fileName);
				resource = new PropertyResourceBundle(inputStream);
				
				instance = new PropertiesUtil();
				
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
