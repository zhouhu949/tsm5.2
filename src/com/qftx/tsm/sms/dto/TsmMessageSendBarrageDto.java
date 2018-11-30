package com.qftx.tsm.sms.dto;

import com.qftx.tsm.sms.bean.TsmMessageSend;

import java.util.Map;

public class TsmMessageSendBarrageDto extends TsmMessageSend{
	private Map<String,Object> sendMap;

	public Map<String, Object> getSendMap() {
		return sendMap;
	}

	public void setSendMap(Map<String, Object> sendMap) {
		this.sendMap = sendMap;
	}
	

}
