/**
 * UserContext.java
 * TODO
 * neo 2011-4-22
 */
package cn.com.hotmaze.context;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import cn.com.hotmaze.entity.UserEntity;

/**
 * �û��ڴ������
 * @author neo
 *
 */
public class UserContext {

	private static ConcurrentMap<String, UserEntity> users = new ConcurrentHashMap<String, UserEntity>();
	
	
	
	
}
