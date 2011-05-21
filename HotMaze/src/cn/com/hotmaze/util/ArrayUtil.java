package cn.com.hotmaze.util;

import java.lang.reflect.Field;
import java.util.Collection;

public class ArrayUtil {

	/**
	 * �������԰�װ��ָ���ַ�������
	 * @param obj ������
	 * @param fields �ַ��������ʽ
	 * @return
	 */
	public static Object[] toArray(Object obj, String[] fields) {
		Object[] datas = new Object[fields.length];
		int i = 0;
		try {
			for(String field : fields) {
				
				Field f = obj.getClass().getDeclaredField(field);
				f.setAccessible(true);
				datas[i] = f.get(obj);
				i++;
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return datas;
	}
	
	/**
	 * ���������װ��ָ����ά�ַ�������
	 * @param objs
	 * @param fields
	 * @return
	 */
	public static Object[] toArray(Object[] objs, String[] fields) {
		
		Object[] datas = new Object[objs.length];
		int i = 0;
		for(Object obj : objs) {
			Object[] data = toArray(obj, fields);
			datas[i] = data;
			i++;
		}
		return datas;
	}
	
	/**
	 * �����ϰ�װ��ָ����ά�ַ�������
	 * @param col
	 * @param fields
	 * @return
	 */
	public static Object[] toArray(Collection<?> col, String[] fields) {
		
		return toArray(col.toArray(), fields);
	}
	
}
