package com.qftx.tsm.sys.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;


 /** 
 * 知识库分组
 * @author: wuwei
 * @since: 2015年11月11日  下午2:44:43
 * @history:
 */
public class KnowlegeGroup extends BaseObject{
	private static final long serialVersionUID = -779881411619272855L;
	private String groupId;      //分组ID
    private String groupName;    //分组名称
    private Short isDel;         //是否删除：0-否，1-是
    private Integer groupIndex;  //分组顺序
    private String inputerAcc;   //录入人
    private Date inputdate;      //录入时间
    private String modifierAcc;  //修改人
    private Date modifydate;     //修改时间
    private String orgId;        //机构ID
    private Integer groupSort;   //分组排序
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Short getIsDel() {
		return isDel;
	}
	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}
	public Integer getGroupIndex() {
		return groupIndex;
	}
	public void setGroupIndex(Integer groupIndex) {
		this.groupIndex = groupIndex;
	}
	public String getInputerAcc() {
		return inputerAcc;
	}
	public void setInputerAcc(String inputerAcc) {
		this.inputerAcc = inputerAcc;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
	public Integer getGroupSort() {
		return groupSort;
	}
	public void setGroupSort(Integer groupSort) {
		this.groupSort = groupSort;
	}
    
}
