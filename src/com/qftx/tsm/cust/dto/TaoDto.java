package com.qftx.tsm.cust.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 存放要淘资源的列表
 * 
 * @author: wuwei
 * @since: 2016年1月3日 上午9:57:04
 * @history:
 */
public class TaoDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7827896877107125536L;
	private List<TaoResDto> taoResDtos;
	private String upId;
	private String downId;
	private String resId;

	public List<TaoResDto> getTaoResDtos() {
		return taoResDtos;
	}

	public void setTaoResDtos(List<TaoResDto> taoResDtos) {
		this.taoResDtos = taoResDtos;
	}

	public String getUpId() {
		return upId;
	}

	public void setUpId(String upId) {
		this.upId = upId;
	}

	public String getDownId() {
		return downId;
	}

	public void setDownId(String downId) {
		this.downId = downId;
	}

	public String getResId() {
		return resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

}
