package com.qftx.tsm.util;

import java.util.ArrayList;
import java.util.List;

import com.qftx.common.util.StringUtils;

/**
 * @author chenhm
 *
 */
public class StringUtil {
	/**
	 * 默认分割符号
	 */
	public static final String DEFAULT_SPLIT = ",";
	
	/**
	 * 将字符串转换成List
	 * 
	 * @param idsStr 以split符号分割的字符串
	 * @param split
	 * @return
	 */
	public static final List<String> toList(String idsStr, String split) {
		List<String> ids = new ArrayList<String>();
		for (String id : idsStr.split(split)) {
			if (StringUtils.isNotBlank(id)) {
				ids.add(id);
			}
		}
		return ids;
	}
	

}
