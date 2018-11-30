package com.qftx.tsm.sys.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.qftx.common.domain.BaseObject;

import java.util.Date;


 /** 
 * 知识库
 * @author: wuwei
 * @since: 2015年11月11日  下午2:41:26
 * @history:
 */
public class Knowlege extends BaseObject{
	
	private static final long serialVersionUID = 372037701981504186L;
	private String knowlegeId; // 知识ID
	private String groupId; // 所在组
	private String question; // 问题
	private String answer; // 回复
	private String inputerAcc; // 录入人
	private Date inputdate; // 录入时间
	private String modifierAcc; // 修改人
	private Date modifydate; // 修改时间
	private Short isDel; // 是否删除：0-否，1-是
	private String orgId; // 单位ID
	private Integer sort; //排序值
	public String getKnowlegeId() {
		return knowlegeId;
	}
	public void setKnowlegeId(String knowlegeId) {
		this.knowlegeId = knowlegeId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
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
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
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
	public Short getIsDel() {
		return isDel;
	}
	public void setIsDel(Short isDel) {
		this.isDel = isDel;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
