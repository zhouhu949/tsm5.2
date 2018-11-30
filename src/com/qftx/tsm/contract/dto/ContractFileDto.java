package com.qftx.tsm.contract.dto;

import com.qftx.tsm.contract.bean.ContractFileBean;

public class ContractFileDto extends ContractFileBean {
	private static final long serialVersionUID = -199922723738542592L;
	
	private String fileName;
	private String fileUrl;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	
}
