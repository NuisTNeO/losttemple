/**
 * UserContext.java
 * TODO
 * neo 2011-4-22
 */
package cn.com.hotmaze.context;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.mina.core.session.IoSession;

import cn.com.hotmaze.common.Constants.GameStatus;
import cn.com.hotmaze.entity.UserEntity;

/**
 * 用户内存管理类
 * @author neo
 *
 */
public class UserContext {

	private static ConcurrentMap<String, UserEntity> userEntities = new ConcurrentHashMap<String, UserEntity>();
	
	private static ConcurrentMap<String, UserEntity> userEntitynames = new ConcurrentHashMap<String, UserEntity>(); // 服务器用户模板

	private static ConcurrentMap<String, IoSession> userEntitySessions = new ConcurrentHashMap<String, IoSession>();// 用户和会话关联

	private static ConcurrentMap<String, UserEntity> hallUserEntities = new ConcurrentHashMap<String, UserEntity>();// 大厅用户集合

	/**
	 * 服务器新用户添加方法
	 * 
	 * @param UserEntity
	 * @param session
	 */
	public static void addUserEntity(UserEntity UserEntity, IoSession session) {
		userEntities.putIfAbsent(UserEntity.getUserID(), UserEntity); // 保存当前用户模型到UserEntitys
		userEntitySessions.putIfAbsent(UserEntity.getUserID(), session); // 将ISession和用户关联
		userEntitynames.putIfAbsent(UserEntity.getUserName(), UserEntity);
	}

	/**
	 * 服务器用户移除方法
	 * 
	 * @param UserEntity
	 * @param session
	 */
	public static void removeUserEntity(UserEntity UserEntity) {
		if (UserEntity == null) {
			return;
		}
		removeUserEntity(UserEntity.getUserID());
	}

	/**
	 * 服务器用户移除方法
	 * 
	 */
	public static void removeUserEntity(String userId) {

		IoSession session = userEntitySessions.remove(userId); // 解除ISession和用户关联
		session = null;
		UserEntity userEntity = userEntities.remove(userId); // 移除当前用户模型
		String userName = userEntity.getUserName();
		userEntity = null;
		userEntity = userEntitynames.remove(userName);
		userEntity = null;
		
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("移除用户 : " + userId);
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	}

	/**
	 * 根据用户ID读取UserEntitys集合中对应用户模型方法
	 * 
	 * @param userId
	 * @return UserEntity
	 */
	public static UserEntity getUserEntity(String userId) {
		return userEntities.get(userId);
	}

	/**
	 * 根据用户UserEntityNAME读取UserEntitys集合中对应用户模型方法
	 * 
	 * @param UserEntityId
	 * @return UserEntity
	 */
	public static UserEntity getUserEntityByName(String userName) {
		return userEntitynames.get(userName);
	}

	public static UserEntity[] getUserEntitys(Object[] userIds) {
		UserEntity[] userEntities = new UserEntity[userIds.length];
		for (int i = 0; i < userIds.length; i++) {
			userEntities[i] = UserContext.getUserEntity((String) userIds[i]);
		}
		return userEntities;
	}

	public static UserEntity[] getUserEntitys(Set<String> userIds) {
		return getUserEntitys(userIds.toArray());
	}

	/**
	 * 读取整个UserEntitys集合方法
	 * 
	 * @return Map
	 */
	public static ConcurrentMap<String, UserEntity> getUserEntitys() {
		return userEntities;
	}

	/**
	 * 读取UserEntitys集合长度方法
	 * 
	 * @return int
	 */
	public static int getUserEntitySize() {
		return userEntities.size();
	}

	/**
	 * 读取UserEntitySessions集合中单个元素方法
	 * 
	 * @return IoSession
	 */
	public static IoSession getUserEntitySession(String userId) {
		return userEntitySessions.get(userId);
	}

	/**
	 * 读取整个UserEntitySessions集合方法
	 * 
	 * @return Map
	 */
	public static ConcurrentMap<String, IoSession> getUserEntitySessions() {
		return userEntitySessions;
	}

	/**
	 * 获取大厅玩家列表
	 * 
	 * @return Map<String, UserEntity>
	 */
	public static Map<String, UserEntity> getHallUserEntitys() {
		hallUserEntities.clear();
		for (UserEntity entity : userEntities.values()) {
			if (entity.getGameStatus() == GameStatus.HALL) {
				hallUserEntities.put(entity.getUserID(), entity);
			}
		}
		return hallUserEntities;
	}


	
	
}
