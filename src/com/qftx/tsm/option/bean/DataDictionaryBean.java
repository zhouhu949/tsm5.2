package com.qftx.tsm.option.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

 /** 
 * 数据字典表实体
 */
public class DataDictionaryBean extends BaseObject {
	
	private static final long serialVersionUID = 1L;

	private String dictionaryId;           //选项ID
    private String dictionaryName;         //数据项名称
    private String dictionaryValue;        //参数值
    private String dictionaryValueNotes;   //字典值域说明
    private String inputerAcc;             //录入人帐号
    private Date inputdate;                //录入时间
    private String modifierAcc;            //修改者帐号
    private Date modifydate;               //修改时间
    private String orgId;                  //机构ID
    private Short isDel;                   //是否删除：0--否，1--是
    private String dictionaryCode;         //数据项代码
    private String isOpen; // 0-关闭，1-开启
    public String getDictionaryId() {
        return dictionaryId;
    }

    public void setDictionaryId(String dictionaryId) {
        this.dictionaryId = dictionaryId == null ? null : dictionaryId.trim();
    }

    public String getDictionaryName() {
        return dictionaryName;
    }

    public void setDictionaryName(String dictionaryName) {
        this.dictionaryName = dictionaryName == null ? null : dictionaryName.trim();
    }

    public String getDictionaryValue() {
        return dictionaryValue;
    }

    public void setDictionaryValue(String dictionaryValue) {
        this.dictionaryValue = dictionaryValue == null ? null : dictionaryValue.trim();
    }

    public String getDictionaryValueNotes() {
        return dictionaryValueNotes;
    }

    public void setDictionaryValueNotes(String dictionaryValueNotes) {
        this.dictionaryValueNotes = dictionaryValueNotes == null ? null : dictionaryValueNotes.trim();
    }

    public String getInputerAcc() {
        return inputerAcc;
    }

    public void setInputerAcc(String inputerAcc) {
        this.inputerAcc = inputerAcc == null ? null : inputerAcc.trim();
    }

    public Date getInputdate() {
        return inputdate;
    }

    public void setInputdate(Date inputdate) {
        this.inputdate = inputdate;
    }

    public String getModifierAcc() {
        return modifierAcc;
    }

    public void setModifierAcc(String modifierAcc) {
        this.modifierAcc = modifierAcc == null ? null : modifierAcc.trim();
    }

    public Date getModifydate() {
        return modifydate;
    }

    public void setModifydate(Date modifydate) {
        this.modifydate = modifydate;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public Short getIsDel() {
        return isDel;
    }

    public void setIsDel(Short isDel) {
        this.isDel = isDel;
    }

    public String getDictionaryCode() {
        return dictionaryCode;
    }

    public void setDictionaryCode(String dictionaryCode) {
        this.dictionaryCode = dictionaryCode == null ? null : dictionaryCode.trim();
    }

	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}

	public String getIsOpen() {
		return isOpen;
	}
	
	@Override
	public boolean equals(Object obj) {
		 if(this == obj) { 
             return true;  
    	 }
         if(obj == null) {
        	 return false; 
         } 
		if(obj instanceof DataDictionaryBean){
			DataDictionaryBean dataDictionary = (DataDictionaryBean)obj;
    		if(this.dictionaryId.equals(dataDictionary.dictionaryId)){
    			return true;
    		}
    	}
    	return false;
	}
	
	@Override
	public int hashCode() {
		 int result = 17;
         result = 31 * result + this.dictionaryId.hashCode();
         return result;
	}
}