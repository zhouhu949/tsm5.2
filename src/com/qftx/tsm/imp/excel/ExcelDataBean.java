package com.qftx.tsm.imp.excel;

import java.util.ArrayList;
import java.util.List;

public class ExcelDataBean {
	private List<String> header; // 存储文件列头
	private List<String> datas;	//  存储文件第一行数据(其实是第二行，列头为第一行)
	private List<String>	headerFiled; // 存储文件列头转换为系统字段
	private int rows; // 文件总行数
	
	public List<String> getHeader() {
		if(header==null){
			header= new ArrayList<String>();
		}
		return header;
	}
	public void setHeader(List<String> headers) {
		this.header = headers;
	}
	public List<String> getDatas() {
		if(datas==null){
			datas= new ArrayList<String>();
		}
		return datas;
	}
	public void setDatas(List<String> datas) {
		this.datas = datas;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public List<String> getHeaderFiled() {
		return headerFiled;
	}
	public void setHeaderFiled(List<String> headerFiled) {
		this.headerFiled = headerFiled;
	}
	
}
