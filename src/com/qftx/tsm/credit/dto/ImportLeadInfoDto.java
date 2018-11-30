package com.qftx.tsm.credit.dto;

import com.qftx.tsm.credit.bean.ImportLeadInfoBean;


/**
 * 导入失败资源 dto
 * <p>    company：浙江中恩通信技术有限公司
 * @author zwj
 */
public class ImportLeadInfoDto extends ImportLeadInfoBean{
	private static final long serialVersionUID = -9216717763509675875L;
	
	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
