package com.qftx.tsm.log.bean;

import com.qftx.tsm.option.dto.CustFollowProcessJsonDto;
import com.qftx.tsm.option.dto.DictionProcessJsonDto;
import com.qftx.tsm.option.dto.DictionaryWatersDto;
import com.qftx.tsm.option.dto.MoneyJsonDto;

import java.io.Serializable;
import java.util.List;

public class RetDictionaryBean  implements Serializable{

	private static final long serialVersionUID = 2191499573323490708L;
	private String code;
	private String value;
	private List<DictionaryWatersDto> grouplist;
	private List<DictionProcessJsonDto> optionist;
	private List<MoneyJsonDto> moneys;
	private List<CustFollowProcessJsonDto> followProcess;
	private String open;
	private String modfiyAcc;
	private String modfiyDate;
	private String name;
	private String notes;
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
	public List<MoneyJsonDto> getMoneys() {
		return moneys;
	}
	public void setMoneys(List<MoneyJsonDto> moneys) {
		this.moneys = moneys;
	}
	public List<CustFollowProcessJsonDto> getFollowProcess() {
		return followProcess;
	}
	public void setFollowProcess(List<CustFollowProcessJsonDto> followProcess) {
		this.followProcess = followProcess;
	}   
	
}
