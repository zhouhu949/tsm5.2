package com.qftx.tsm.imp.dto;

import com.qftx.common.domain.BaseObject;

/**
 * 导入时 与数据库对比 判断是否重复 
 * @author: zwj
 * @since: 2016-2-29  上午9:37:28
 * @history: 4.x
 */
public class ImportRepeatDto extends BaseObject {
	private static final long serialVersionUID = -7244041593491465746L;
	
		private String orgId;
		private String isOpen = "0"; // 资源去重设置是否开启（0:不开启，1：开启）
		
		// 1：电话号码去重:未选中
		// 2：对签约客户电话号码进行去重保护
		// 3：对意向客户电话号码进行去重保护
		// 4：对所有资源进行电话号码去重保护
		private String isPhone = "1";

		// 0：单位名称去重：未选中
		// 1：对签约客户单位名称进行去重保护
		// 2：对意向客户单位名称进行去重保护
		// 3：对所有资源单位名称去重保护
		private String isCompanyName = "0";

		// 0：单位主页去重：未选中
		// 1：对签约客户单位主页进行去重保护
		// 2：对意向客户单位主页进行去重保护
		// 3：对所有资源单位主页去重保护
		private String isUnithome = "0";
		private String isImpSet = "0"; //  0:个人导入限制为：未选中，1:个人导入限制为：选中 
		private String limits = "0"; // 资源限制大小
		private String isFlowOpen = "0"; // 客户跟进设置开关是否开启（0:不开启，1：开启）
		private String mobelPhone; // 常用号码
		private String telPhone; //备用号码
		private String comPanyName; // 单位名称（个人资源使用）
		private String custName; // 单位名称(企业资源使用)
		private Integer isSys; // 0:个人资源，1：企业资源
		private String unitHome; // 单位主页
		
		public String getIsOpen() {
			return isOpen;
		}
		public void setIsOpen(String isOpen) {
			this.isOpen = isOpen;
		}
		public String getIsPhone() {
			return isPhone;
		}
		public void setIsPhone(String isPhone) {
			this.isPhone = isPhone;
		}
		public String getIsCompanyName() {
			return isCompanyName;
		}
		public void setIsCompanyName(String isCompanyName) {
			this.isCompanyName = isCompanyName;
		}
		public String getIsUnithome() {
			return isUnithome;
		}
		public void setIsUnithome(String isUnithome) {
			this.isUnithome = isUnithome;
		}
		public String getIsImpSet() {
			return isImpSet;
		}
		public void setIsImpSet(String isImpSet) {
			this.isImpSet = isImpSet;
		}
		public String getLimits() {
			return limits;
		}
		public void setLimits(String limits) {
			this.limits = limits;
		}
		public String getIsFlowOpen() {
			return isFlowOpen;
		}
		public void setIsFlowOpen(String isFlowOpen) {
			this.isFlowOpen = isFlowOpen;
		}
		public String getMobelPhone() {
			return mobelPhone;
		}
		public void setMobelPhone(String mobelPhone) {
			this.mobelPhone = mobelPhone;
		}
		public String getTelPhone() {
			return telPhone;
		}
		public void setTelPhone(String telPhone) {
			this.telPhone = telPhone;
		}
		public String getComPanyName() {
			return comPanyName;
		}
		public void setComPanyName(String comPanyName) {
			this.comPanyName = comPanyName;
		}
		public String getCustName() {
			return custName;
		}
		public void setCustName(String custName) {
			this.custName = custName;
		}
		public String getUnitHome() {
			return unitHome;
		}
		public void setUnitHome(String unitHome) {
			this.unitHome = unitHome;
		}
		public String getOrgId() {
			return orgId;
		}
		public void setOrgId(String orgId) {
			this.orgId = orgId;
		}
		public Integer getIsSys() {
			return isSys;
		}
		public void setIsSys(Integer isSys) {
			this.isSys = isSys;
		}
		
}
