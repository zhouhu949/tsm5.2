package com.qftx.tsm.report.utils;

import java.util.Comparator;
import java.util.Map;

public class GroupComparator implements Comparator<Map<String, Object>> {

	@Override
	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
		return (o1.get("groupId").toString()).compareToIgnoreCase(o2.get("groupId").toString());
	}

}
