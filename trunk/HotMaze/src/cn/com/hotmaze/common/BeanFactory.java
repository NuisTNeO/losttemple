/**
 * BeanFactory.java
 * TODO
 * neo 2011-4-19
 */
package cn.com.hotmaze.common;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bean¹¤³§
 * @author neo
 *
 */
public class BeanFactory {

	private static ApplicationContext context = null;
	
	public static ApplicationContext getInstance() {
		
		if(context == null) {
			context = new ClassPathXmlApplicationContext("applicationContext.xml");
		}
		return context;
	}
	
	public Object getBean(String beanName) {
		
		return context.getBean(beanName);
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanName, Class<T> clazz) {
		return (T) context.getBean(beanName, clazz);
	}
	
}
