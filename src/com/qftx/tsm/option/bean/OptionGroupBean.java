package com.qftx.tsm.option.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

 /** 
 * 数据选项分组表实体
 */
public class OptionGroupBean extends BaseObject {
	
	private static final long serialVersionUID = 1L;

	private String groupId;     //分组ID
    private String itemCode;         //数据项CODE
    private String groupName;       //分组名称
    private Integer sort;            //排序
    private String inputerAcc;       //录入人帐号
    private Date inputdate;          //录入时间
    private String modifierAcc;      //修改者帐号
    private Date modifydate;         //修改时间
    private String orgId;            //机构
    private Integer isDefault; // 是否可编辑：0-否；1-是
    
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getInputerAcc() {
		return inputerAcc;
	}
	public void setInputerAcc(String inputerAcc) {
		this.inputerAcc = inputerAcc;
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
		this.modifierAcc = modifierAcc;
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
		this.orgId = orgId;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}

	@Override
    public boolean equals(Object obj) {
    	 if(this == obj) { 
             return true;  
    	 }
         if(obj == null) {
        	 return false; 
         } 
    	if(obj instanceof OptionGroupBean){
    		OptionGroupBean option = (OptionGroupBean)obj;
    		if(this.groupId.equals(option.groupId)){
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
    	  int result = 17;
          result = 31 * result + this.groupId.hashCode();
          return result;
    }
}