package com.qftx.tsm.log.bean;

import com.qftx.tsm.option.dto.DictionProcessJsonDto;
import com.qftx.tsm.option.dto.DictionaryWatersDto;

import java.util.List;

public class TestRetDictionaryBean {
	private String code;
	private String value;
	private List<DictionaryWatersDto> grouplist;
	private List<DictionProcessJsonDto> optionist;
	private String open;
	private String modfiyAcc;
	private String modfiyDate;
	private String name;
	private String notes;
	private int  paixu;
	
	public int getPaixu() {
		return paixu;
	}
	public void setPaixu(int paixu) {
		this.paixu = paixu;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
    public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	//	public Object getValue() {
//		return value;
//	}
//	public void setValue(Object value) {
//		if(value.getClass() != String.class){
//			System.out.println(value+"$$$$$");
//		}
//		this.value = value;
//	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getModfiyAcc() {
		return modfiyAcc;
	}
	public void setModfiyAcc(String modfiyAcc) {
		this.modfiyAcc = modfiyAcc;
	}
	
	public String getModfiyDate() {
		return modfiyDate;
	}
	public void setModfiyDate(String modfiyDate) {
		this.modfiyDate = modfiyDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public List<DictionaryWatersDto> getGrouplist() {
		return grouplist;
	}
	public void setGrouplist(List<DictionaryWatersDto> grouplist) {
		this.grouplist = grouplist;
	}
	public List<DictionProcessJsonDto> getOptionist() {
		return optionist;
	}
	public void setOptionist(List<DictionProcessJsonDto> optionist) {
		this.optionist = optionist;
	}
    
	
}
