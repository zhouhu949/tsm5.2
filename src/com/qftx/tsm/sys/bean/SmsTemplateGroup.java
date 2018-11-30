package com.qftx.tsm.sys.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;



 /** 
 * 短信模板分类
 */
public class SmsTemplateGroup extends BaseObject{
	private static final long serialVersionUID = 3522700889563854252L;

	private String tsgId; 				// 分类ID

    private String groupName; 	// 分类名称

    private Integer isDel;  			// 是否删除：0--否，1--是

    private Integer groupIndex; // 分组顺序
    
    private String inputAcc;		// 录入人
    	
    private Date inputdate;		// 录入时间

    private String modifierAcc;

    private Date modifydate;

    private String orgId;				// 机构ID

	public String getTsgId() {
		return tsgId;
	}

	public void setTsgId(String tsgId) {
		this.tsgId = tsgId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getIsDel() {
		return isDel;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public Integer getGroupIndex() {
		return groupIndex;
	}

	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}

	public String getInputAcc() {
		return inputAcc;
	}

	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
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

}