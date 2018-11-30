package com.qftx.tsm.imp.dto;

import com.qftx.tsm.imp.bean.ImportResInfoBean;


/**
 * 导入失败资源 dto
 * <p>    company：浙江中恩通信技术有限公司
 * @author zwj
 */
public class ImportResInfoDto extends ImportResInfoBean{
	private static final long serialVersionUID = -9216717763509675875L;
	
	private String errorMsg;

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
