package com.qftx.tsm.sys.dto;

import com.qftx.tsm.sys.bean.Points;

import java.util.List;
/***
 * Points list表单对象组装
 * @author: zwj
 * @since: 2015-11-16  上午10:57:01
 * @history: 4.x
 */
public class PointsModel {
	private List<Points> points;

	public List<Points> getPoints() {
		return points;
	}

	public void setPoints(List<Points> points) {
		this.points = points;
	}
	
}
