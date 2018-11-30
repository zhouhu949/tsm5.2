package com.qftx.tsm.callrecord.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 通话记录 接口返回json 值
 * @author: zwj
 * @since: 2015-12-12  下午2:30:27
 * @history: 4.x
 */
public class TsmRecordCallDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private TsmRecordCallBean item=new TsmRecordCallBean(); // 查询参数
	private List<TsmRecordCallBean> list=new ArrayList<TsmRecordCallBean>(); // 列表
	
	
	public TsmRecordCallBean getItem() {
		return item;
	}
	public void setItem(TsmRecordCallBean item) {
		this.item = item;
	}
	public List<TsmRecordCallBean> getList() {
		return list;
	}
	public void setList(List<TsmRecordCallBean> list) {
		this.list = list;
	}
	
}
