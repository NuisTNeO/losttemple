package cn.com.hotmaze.util;

import java.lang.reflect.Field;
import java.util.Collection;

public class ArrayUtil {

	/**
	 * 将类属性包装成指定字符串数组
	 * @param obj 数据类
	 * @param fields 字符串数组格式
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
	 * 将类数组包装成指定二维字符串数组
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
	 * 将集合包装成指定二维字符串数组
	 * @param col
	 * @param fields
	 * @return
	 */
	public static Object[] toArray(Collection<?> col, String[] fields) {
		
		return toArray(col.toArray(), fields);
	}
	
}
