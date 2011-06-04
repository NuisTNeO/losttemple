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
 * �û��ڴ������
 * @author neo
 *
 */
public class UserContext {

	private static ConcurrentMap<String, UserEntity> userEntities = new ConcurrentHashMap<String, UserEntity>();
	
	private static ConcurrentMap<String, UserEntity> userEntitynames = new ConcurrentHashMap<String, UserEntity>(); // �������û�ģ��

	private static ConcurrentMap<String, IoSession> userEntitySessions = new ConcurrentHashMap<String, IoSession>();// �û��ͻỰ����

	private static ConcurrentMap<String, UserEntity> hallUserEntities = new ConcurrentHashMap<String, UserEntity>();// �����û�����

	/**
	 * ���������û���ӷ���
	 * 
	 * @param UserEntity
	 * @param session
	 */
	public static void addUserEntity(UserEntity UserEntity, IoSession session) {
		userEntities.putIfAbsent(UserEntity.getUserID(), UserEntity); // ���浱ǰ�û�ģ�͵�UserEntitys
		userEntitySessions.putIfAbsent(UserEntity.getUserID(), session); // ��ISession���û�����
		userEntitynames.putIfAbsent(UserEntity.getUserName(), UserEntity);
	}

	/**
	 * �������û��Ƴ�����
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
	 * �������û��Ƴ�����
	 * 
	 */
	public static void removeUserEntity(String userId) {

		IoSession session = userEntitySessions.remove(userId); // ���ISession���û�����
		session = null;
		UserEntity userEntity = userEntities.remove(userId); // �Ƴ���ǰ�û�ģ��
		String userName = userEntity.getUserName();
		userEntity = null;
		userEntity = userEntitynames.remove(userName);
		userEntity = null;
		
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		System.out.println("�Ƴ��û� : " + userId);
		System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
	}

	/**
	 * �����û�ID��ȡUserEntitys�����ж�Ӧ�û�ģ�ͷ���
	 * 
	 * @param userId
	 * @return UserEntity
	 */
	public static UserEntity getUserEntity(String userId) {
		return userEntities.get(userId);
	}

	/**
	 * �����û�UserEntityNAME��ȡUserEntitys�����ж�Ӧ�û�ģ�ͷ���
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
	 * ��ȡ����UserEntitys���Ϸ���
	 * 
	 * @return Map
	 */
	public static ConcurrentMap<String, UserEntity> getUserEntitys() {
		return userEntities;
	}

	/**
	 * ��ȡUserEntitys���ϳ��ȷ���
	 * 
	 * @return int
	 */
	public static int getUserEntitySize() {
		return userEntities.size();
	}

	/**
	 * ��ȡUserEntitySessions�����е���Ԫ�ط���
	 * 
	 * @return IoSession
	 */
	public static IoSession getUserEntitySession(String userId) {
		return userEntitySessions.get(userId);
	}

	/**
	 * ��ȡ����UserEntitySessions���Ϸ���
	 * 
	 * @return Map
	 */
	public static ConcurrentMap<String, IoSession> getUserEntitySessions() {
		return userEntitySessions;
	}

	/**
	 * ��ȡ��������б�
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
