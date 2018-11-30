package com.qftx.tsm.sys.dto;

import com.qftx.tsm.sys.bean.SmsTemplateGroup;

 /** 
 * 短信模板分类 dto
 */
public class SmsTemplateGroupDto extends SmsTemplateGroup{
	private static final long serialVersionUID = 3522700889563854252L;
	
    private Integer counts;		// 短信模板数

	public Integer getCounts() {
		return counts;
	}

	public void setCounts(Integer counts) {
		this.counts = counts;
	}

}