package com.qftx.tsm.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

/**
 * 集合工具类
 * 
 * @author chenhm
 *
 */
public class CollectionUtil {
	private static final Logger logger = Logger.getLogger(CollectionUtil.class);

	/**
	 * 获取属性值列表
	 * 
	 * @param list
	 * @param propertyName
	 * @return
	 */
	public static <T> List<String> getPropertyValueList(List<T> list, String propertyName) {
		
		List<String> resultList = new ArrayList<String>();
		try {
			for (T bean : list)
				resultList.add(BeanUtils.getProperty(bean, propertyName));
		} catch (Exception e) {
			logger.error(String.format("获取对象的%s属性值失败", propertyName), e);
		}
		
		return resultList;
	}
	
	/**
	 * 判断List列表中的对象属性值是否有值为value的对象
	 * 
	 * @param list
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static <T> boolean exists(List<T> list, String propertyName, Object value) {
		try {
			for (T bean : list) {
				Object propertyValue = PropertyUtils.getProperty(bean, propertyName);
				if (null!=value && value.equals(propertyValue)) {
					return true;
				}
			}
		} catch (Exception e) {
			logger.error(String.format("获取对象的%s属性值失败", propertyName), e);
		}
		
		return false;
	}
	
	/**
	 * 判断List列表中的对象属性值是否有值为value的对象
	 * 
	 * @param list
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static <T> T get(List<T> list, String propertyName, Object value) {
		try {
			for (T bean : list) {
				Object propertyValue = PropertyUtils.getProperty(bean, propertyName);
				if (null!=value && value.equals(propertyValue)) {
					return bean;
				}
			}
		} catch (Exception e) {
			logger.error(String.format("获取对象的%s属性值失败", propertyName), e);
		}
		
		return null;
	}
	
	/**
	 * 将List列表中对象属性值为value的对象剔除
	 * 
	 * @param list
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static <T> List<T> remove(List<T> list, String propertyName, Object value) {
		List<T> resultList = new ArrayList<T>();
		try {
			for (T bean : list) {
				Object propertyValue = PropertyUtils.getProperty(bean, propertyName);
				if (null!=value && !value.equals(propertyValue)) {
					resultList.add(bean);
				}
			}
		} catch (Exception e) {
			logger.error(String.format("获取对象的%s属性值失败", propertyName), e);
		}
		return resultList;
	}
	
	
	/**
	 * 将List列表中对象属性值为value的对象剔除
	 * 
	 * @param list
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static <T> List<T> remove(List<T> list, String propertyName, List<Object> values) {
		List<T> resultList = new ArrayList<T>();
		try {
			for (T bean : list) {
				Object propertyValue = PropertyUtils.getProperty(bean, propertyName);
				if (!values.contains(propertyValue)) {
					resultList.add(bean);
				}
			}
		} catch (Exception e) {
			logger.error(String.format("获取对象的%s属性值失败", propertyName), e);
		}
		return resultList;
	}
	
	
	/**
	 * 将List列表中对象属性值为value的对象组成列表返回
	 * 
	 * @param list
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public static <T> List<T> equals(List<T> list, String propertyName, List<Object> values) {
		List<T> resultList = new ArrayList<T>();
		try {
			for (T bean : list) {
				Object propertyValue = PropertyUtils.getProperty(bean, propertyName);
				if (values.contains(propertyValue)) {
					resultList.add(bean);
				}
			}
		} catch (Exception e) {
			logger.error(String.format("获取对象的%s属性值失败", propertyName), e);
		}
		return resultList;
	}
	
	
}
