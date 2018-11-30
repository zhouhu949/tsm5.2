package com.qftx.tsm.cust.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;
import java.util.List;

/**
 * 资源分组
 * 
 * @author: wuwei
 * @since: 2013-10-30 上午10:43:58
 * @history:
 */
public class ResourceGroupBean extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6673631313344768451L;

	private String resGroupId; // 资源分组ID
	private String groupName; // 分组名称
	private Short isDel; // 是否删除
	private Integer groupIndex; // 分组顺序
	private String inputAcc; // 录入人
	private Date inputdate; // 录入时间
	private String modifierAcc; // 修改人
	private Date modifydate; // 修改时间
	private String orgId; // 机构ID
	private Short isSharePool;//是否设置为共享客户池
    private String type;
    private int level;
    private String pid;
    private List<ResourceGroupBean> childrenList;
	public String getResGroupId() {
		return resGroupId;
	}

	public void setResGroupId(String resGroupId) {
		this.resGroupId = resGroupId == null ? null : resGroupId.trim();
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName == null ? null : groupName.trim();
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

	public String getInputAcc() {
		return inputAcc;
	}

	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc == null ? null : inputAcc.trim();
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

	public Short getIsSharePool() {
		return isSharePool;
	}

	public void setIsSharePool(Short isSharePool) {
		this.isSharePool = isSharePool;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<ResourceGroupBean> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<ResourceGroupBean> childrenList) {
		this.childrenList = childrenList;
	}
	
	
	
}