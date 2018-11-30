package com.qftx.tsm.option.dto;

import com.qftx.tsm.option.bean.OptionBean;

import java.math.BigDecimal;

/*
 * 数据选项实体拓展类
 * @author: 徐承恩
 * @since: 2013-11-7  上午10:13:52
 */
public class OptionDto extends OptionBean {

	private static final long serialVersionUID = 1L;
	
	private Integer reviewCount;	//点评数量
	private Integer totalCount;     //意向客户数
	private BigDecimal  signMoney;
	private BigDecimal  propo;//占用比例
	
	//查询条件
	private String logAct;         //登录帐号
	private String orgId;          //机构id
	private String itemCode;       //类型编码
	private String groupId;        //分组编号

	public Integer getReviewCount() {
		return reviewCount;
	}

	public void setReviewCount(Integer reviewCount) {
		this.reviewCount = reviewCount == null ? null : reviewCount;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getLogAct() {
		return logAct;
	}

	public void setLogAct(String logAct) {
		this.logAct = logAct;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public BigDecimal getSignMoney() {
		return signMoney;
	}

	public void setSignMoney(BigDecimal signMoney) {
		this.signMoney = signMoney;
	}

	public void setPropo(BigDecimal propo) {
		this.propo = propo;
	}

	public BigDecimal getPropo() {
		return propo;
	}


}
