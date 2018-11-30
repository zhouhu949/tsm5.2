package com.qftx.tsm.report.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.qftx.common.domain.BaseObject;

public class ResCustReportDto extends BaseObject{
	private static final long serialVersionUID = -2394078316730646035L;
	private String orgId;
	
	private String custTypeId;//客户类型
	private List<String> custTypeIds;
	private Integer custTypeMatch;
	
	private String qStatus; //客户状态
	private Map<Integer,String> qStatusMap;
	private List<String> types;
	private List<String> statuss;
	private Integer qStatusMatch;
	
	private String optionlistId; //销售进程
	private List<String> optionlistIds;
	private Integer optionlistMatch;
	
	private String accs;
	private List<String> ownerAccs;//所有者
	private Integer ownerAccMatch;
	
	private String resGroupId;//资源分组
	private List<String> resGroupIds;
	private Integer resGroupMatch;
	//最近联系时间
	private String actionDateStart;
	private String actionDateEnd;
	//下次联系时间
	private String nextFollowDateStart;
	private String nextFollowDateEnd;
	//淘到客户时间
	private String amoytocustomerDateStart;
	private String amoytocustomerDateEnd;
	//创建时间
	private String inputDateStart;
	private String inputDateEnd;
	//签约时间
	private String signDateStart;
	private String signDateEnd;
	//分配时间
	private String ownerStartDateStart;
	private String ownerStartDateEnd;
	
	private String companyTrade;//所属行业
	private List<String> companyTrades;
	private Integer companyTradeMatch;
	
	private String provinceId;
	private String provinceName;
	private String cityId;
	private String cityName;
	private String countyId;
	private String countyName;
	private Integer areaMatch;
	
	private String importDeptId;//资源录入部门
	private List<String> importDeptIds;
	private Integer importDeptMatch;
	
	private String source;//客户来源
	private List<String> sources;
	private Integer sourceMatch;
	
	private String opreateType;//放弃类型
	private List<String> opreateTypes;
	private Integer opreateTypeMatch;
	
	private String defined1;
	private List<String> defined1s;
	private Integer defined1Match;
	
	private String defined2;
	private List<String> defined2s;
	private Integer defined2Match;
	
	private String defined3;
	private List<String> defined3s;
	private Integer defined3Match;
	
	private String defined4;
	private List<String> defined4s;
	private Integer defined4Match;
	
	private String defined5;
	private List<String> defined5s;
	private Integer defined5Match;
	
	private String defined6;
	private List<String> defined6s;
	private Integer defined6Match;
	
	private String defined7;
	private List<String> defined7s;
	private Integer defined7Match;
	
	private String defined8;
	private List<String> defined8s;
	private Integer defined8Match;
	
	private String defined9;
	private List<String> defined9s;
	private Integer defined9Match;
	
	private String defined10;
	private List<String> defined10s;
	private Integer defined10Match;
	
	private String defined11;
	private List<String> defined11s;
	private Integer defined11Match;
	
	private String defined12;
	private List<String> defined12s;
	private Integer defined12Match;
	
	private String defined13;
	private List<String> defined13s;
	private Integer defined13Match;
	
	private String defined14;
	private List<String> defined14s;
	private Integer defined14Match;
	
	private String defined15;
	private List<String> defined15s;
	private Integer defined15Match;
	
	private String groupByType1;
	private String groupByType2;
	
	private List<String> custIds;
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getCustTypeId() {
		return custTypeId;
	}
	public void setCustTypeId(String custTypeId) {
		this.custTypeId = custTypeId;
	}
	public Integer getCustTypeMatch() {
		return custTypeMatch;
	}
	public void setCustTypeMatch(Integer custTypeMatch) {
		this.custTypeMatch = custTypeMatch;
	}
	public String getqStatus() {
		return qStatus;
	}
	public void setqStatus(String qStatus) {
		this.qStatus = qStatus;
	}
	public Integer getqStatusMatch() {
		return qStatusMatch;
	}
	public void setqStatusMatch(Integer qStatusMatch) {
		this.qStatusMatch = qStatusMatch;
	}
	public String getOptionlistId() {
		return optionlistId;
	}
	public void setOptionlistId(String optionlistId) {
		this.optionlistId = optionlistId;
	}
	public Integer getOptionlistMatch() {
		return optionlistMatch;
	}
	public void setOptionlistMatch(Integer optionlistMatch) {
		this.optionlistMatch = optionlistMatch;
	}
	public String getAccs() {
		return accs;
	}
	public void setAccs(String accs) {
		this.accs = accs;
	}
	public Integer getOwnerAccMatch() {
		return ownerAccMatch;
	}
	public void setOwnerAccMatch(Integer ownerAccMatch) {
		this.ownerAccMatch = ownerAccMatch;
	}
	public String getResGroupId() {
		return resGroupId;
	}
	public void setResGroupId(String resGroupId) {
		this.resGroupId = resGroupId;
	}
	public Integer getResGroupMatch() {
		return resGroupMatch;
	}
	public void setResGroupMatch(Integer resGroupMatch) {
		this.resGroupMatch = resGroupMatch;
	}
	public String getActionDateStart() {
		return actionDateStart;
	}
	public void setActionDateStart(String actionDateStart) {
		this.actionDateStart = actionDateStart;
	}
	public String getActionDateEnd() {
		return actionDateEnd;
	}
	public void setActionDateEnd(String actionDateEnd) {
		this.actionDateEnd = actionDateEnd;
	}
	public String getNextFollowDateStart() {
		return nextFollowDateStart;
	}
	public void setNextFollowDateStart(String nextFollowDateStart) {
		this.nextFollowDateStart = nextFollowDateStart;
	}
	public String getNextFollowDateEnd() {
		return nextFollowDateEnd;
	}
	public void setNextFollowDateEnd(String nextFollowDateEnd) {
		this.nextFollowDateEnd = nextFollowDateEnd;
	}
	public String getAmoytocustomerDateStart() {
		return amoytocustomerDateStart;
	}
	public void setAmoytocustomerDateStart(String amoytocustomerDateStart) {
		this.amoytocustomerDateStart = amoytocustomerDateStart;
	}
	public String getAmoytocustomerDateEnd() {
		return amoytocustomerDateEnd;
	}
	public void setAmoytocustomerDateEnd(String amoytocustomerDateEnd) {
		this.amoytocustomerDateEnd = amoytocustomerDateEnd;
	}
	public String getInputDateStart() {
		return inputDateStart;
	}
	public void setInputDateStart(String inputDateStart) {
		this.inputDateStart = inputDateStart;
	}
	public String getInputDateEnd() {
		return inputDateEnd;
	}
	public void setInputDateEnd(String inputDateEnd) {
		this.inputDateEnd = inputDateEnd;
	}
	public String getSignDateStart() {
		return signDateStart;
	}
	public void setSignDateStart(String signDateStart) {
		this.signDateStart = signDateStart;
	}
	public String getSignDateEnd() {
		return signDateEnd;
	}
	public void setSignDateEnd(String signDateEnd) {
		this.signDateEnd = signDateEnd;
	}
	public String getOwnerStartDateStart() {
		return ownerStartDateStart;
	}
	public void setOwnerStartDateStart(String ownerStartDateStart) {
		this.ownerStartDateStart = ownerStartDateStart;
	}
	public String getOwnerStartDateEnd() {
		return ownerStartDateEnd;
	}
	public void setOwnerStartDateEnd(String ownerStartDateEnd) {
		this.ownerStartDateEnd = ownerStartDateEnd;
	}
	public String getCompanyTrade() {
		return companyTrade;
	}
	public void setCompanyTrade(String companyTrade) {
		this.companyTrade = companyTrade;
	}
	public Integer getCompanyTradeMatch() {
		return companyTradeMatch;
	}
	public void setCompanyTradeMatch(Integer companyTradeMatch) {
		this.companyTradeMatch = companyTradeMatch;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCountyId() {
		return countyId;
	}
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}
	public Integer getAreaMatch() {
		return areaMatch;
	}
	public void setAreaMatch(Integer areaMatch) {
		this.areaMatch = areaMatch;
	}
	public String getImportDeptId() {
		return importDeptId;
	}
	public void setImportDeptId(String importDeptId) {
		this.importDeptId = importDeptId;
	}
	public Integer getImportDeptMatch() {
		return importDeptMatch;
	}
	public void setImportDeptMatch(Integer importDeptMatch) {
		this.importDeptMatch = importDeptMatch;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public Integer getSourceMatch() {
		return sourceMatch;
	}
	public void setSourceMatch(Integer sourceMatch) {
		this.sourceMatch = sourceMatch;
	}
	public String getOpreateType() {
		return opreateType;
	}
	public void setOpreateType(String opreateType) {
		this.opreateType = opreateType;
	}
	public Integer getOpreateTypeMatch() {
		return opreateTypeMatch;
	}
	public void setOpreateTypeMatch(Integer opreateTypeMatch) {
		this.opreateTypeMatch = opreateTypeMatch;
	}
	public String getDefined1() {
		return defined1;
	}
	public void setDefined1(String defined1) {
		this.defined1 = defined1;
	}
	public Integer getDefined1Match() {
		return defined1Match;
	}
	public void setDefined1Match(Integer defined1Match) {
		this.defined1Match = defined1Match;
	}
	public String getDefined2() {
		return defined2;
	}
	public void setDefined2(String defined2) {
		this.defined2 = defined2;
	}
	public Integer getDefined2Match() {
		return defined2Match;
	}
	public void setDefined2Match(Integer defined2Match) {
		this.defined2Match = defined2Match;
	}
	public String getDefined3() {
		return defined3;
	}
	public void setDefined3(String defined3) {
		this.defined3 = defined3;
	}
	public Integer getDefined3Match() {
		return defined3Match;
	}
	public void setDefined3Match(Integer defined3Match) {
		this.defined3Match = defined3Match;
	}
	public String getDefined4() {
		return defined4;
	}
	public void setDefined4(String defined4) {
		this.defined4 = defined4;
	}
	public Integer getDefined4Match() {
		return defined4Match;
	}
	public void setDefined4Match(Integer defined4Match) {
		this.defined4Match = defined4Match;
	}
	public String getDefined5() {
		return defined5;
	}
	public void setDefined5(String defined5) {
		this.defined5 = defined5;
	}
	public Integer getDefined5Match() {
		return defined5Match;
	}
	public void setDefined5Match(Integer defined5Match) {
		this.defined5Match = defined5Match;
	}
	public String getDefined6() {
		return defined6;
	}
	public void setDefined6(String defined6) {
		this.defined6 = defined6;
	}
	public Integer getDefined6Match() {
		return defined6Match;
	}
	public void setDefined6Match(Integer defined6Match) {
		this.defined6Match = defined6Match;
	}
	public String getDefined7() {
		return defined7;
	}
	public void setDefined7(String defined7) {
		this.defined7 = defined7;
	}
	public Integer getDefined7Match() {
		return defined7Match;
	}
	public void setDefined7Match(Integer defined7Match) {
		this.defined7Match = defined7Match;
	}
	public String getDefined8() {
		return defined8;
	}
	public void setDefined8(String defined8) {
		this.defined8 = defined8;
	}
	public Integer getDefined8Match() {
		return defined8Match;
	}
	public void setDefined8Match(Integer defined8Match) {
		this.defined8Match = defined8Match;
	}
	public String getDefined9() {
		return defined9;
	}
	public void setDefined9(String defined9) {
		this.defined9 = defined9;
	}
	public Integer getDefined9Match() {
		return defined9Match;
	}
	public void setDefined9Match(Integer defined9Match) {
		this.defined9Match = defined9Match;
	}
	public String getDefined10() {
		return defined10;
	}
	public void setDefined10(String defined10) {
		this.defined10 = defined10;
	}
	public Integer getDefined10Match() {
		return defined10Match;
	}
	public void setDefined10Match(Integer defined10Match) {
		this.defined10Match = defined10Match;
	}
	public String getDefined11() {
		return defined11;
	}
	public void setDefined11(String defined11) {
		this.defined11 = defined11;
	}
	public Integer getDefined11Match() {
		return defined11Match;
	}
	public void setDefined11Match(Integer defined11Match) {
		this.defined11Match = defined11Match;
	}
	public String getDefined12() {
		return defined12;
	}
	public void setDefined12(String defined12) {
		this.defined12 = defined12;
	}
	public Integer getDefined12Match() {
		return defined12Match;
	}
	public void setDefined12Match(Integer defined12Match) {
		this.defined12Match = defined12Match;
	}
	public String getDefined13() {
		return defined13;
	}
	public void setDefined13(String defined13) {
		this.defined13 = defined13;
	}
	public Integer getDefined13Match() {
		return defined13Match;
	}
	public void setDefined13Match(Integer defined13Match) {
		this.defined13Match = defined13Match;
	}
	public String getDefined14() {
		return defined14;
	}
	public void setDefined14(String defined14) {
		this.defined14 = defined14;
	}
	public Integer getDefined14Match() {
		return defined14Match;
	}
	public void setDefined14Match(Integer defined14Match) {
		this.defined14Match = defined14Match;
	}
	public String getDefined15() {
		return defined15;
	}
	public void setDefined15(String defined15) {
		this.defined15 = defined15;
	}
	public Integer getDefined15Match() {
		return defined15Match;
	}
	public void setDefined15Match(Integer defined15Match) {
		this.defined15Match = defined15Match;
	}
	public String getGroupByType1() {
		return groupByType1;
	}
	public void setGroupByType1(String groupByType1) {
		this.groupByType1 = groupByType1;
	}
	public String getGroupByType2() {
		return groupByType2;
	}
	public void setGroupByType2(String groupByType2) {
		this.groupByType2 = groupByType2;
	}
	public List<String> getCustIds() {
		return custIds;
	}
	public void setCustIds(List<String> custIds) {
		this.custIds = custIds;
	}
	public List<String> getOwnerAccs() {
		return ownerAccs;
	}
	public void setOwnerAccs(List<String> ownerAccs) {
		this.ownerAccs = ownerAccs;
	}
	public List<String> getImportDeptIds() {
		return importDeptIds;
	}
	public void setImportDeptIds(List<String> importDeptIds) {
		this.importDeptIds = importDeptIds;
	}
	public List<String> getCustTypeIds() {
		return custTypeIds;
	}
	public void setCustTypeIds(List<String> custTypeIds) {
		this.custTypeIds = custTypeIds;
	}
	public List<String> getTypes() {
		return types;
	}
	public void setTypes(List<String> types) {
		this.types = types;
	}
	public List<String> getStatuss() {
		return statuss;
	}
	public void setStatuss(List<String> statuss) {
		this.statuss = statuss;
	}
	public List<String> getOptionlistIds() {
		return optionlistIds;
	}
	public void setOptionlistIds(List<String> optionlistIds) {
		this.optionlistIds = optionlistIds;
	}
	public List<String> getResGroupIds() {
		return resGroupIds;
	}
	public void setResGroupIds(List<String> resGroupIds) {
		this.resGroupIds = resGroupIds;
	}
	public List<String> getCompanyTrades() {
		return companyTrades;
	}
	public void setCompanyTrades(List<String> companyTrades) {
		this.companyTrades = companyTrades;
	}
	public List<String> getSources() {
		return sources;
	}
	public void setSources(List<String> sources) {
		this.sources = sources;
	}
	public List<String> getOpreateTypes() {
		return opreateTypes;
	}
	public void setOpreateTypes(List<String> opreateTypes) {
		this.opreateTypes = opreateTypes;
	}
	public List<String> getDefined1s() {
		return defined1s;
	}
	public void setDefined1s(List<String> defined1s) {
		this.defined1s = defined1s;
	}
	public List<String> getDefined2s() {
		return defined2s;
	}
	public void setDefined2s(List<String> defined2s) {
		this.defined2s = defined2s;
	}
	public List<String> getDefined3s() {
		return defined3s;
	}
	public void setDefined3s(List<String> defined3s) {
		this.defined3s = defined3s;
	}
	public List<String> getDefined4s() {
		return defined4s;
	}
	public void setDefined4s(List<String> defined4s) {
		this.defined4s = defined4s;
	}
	public List<String> getDefined5s() {
		return defined5s;
	}
	public void setDefined5s(List<String> defined5s) {
		this.defined5s = defined5s;
	}
	public List<String> getDefined6s() {
		return defined6s;
	}
	public void setDefined6s(List<String> defined6s) {
		this.defined6s = defined6s;
	}
	public List<String> getDefined7s() {
		return defined7s;
	}
	public void setDefined7s(List<String> defined7s) {
		this.defined7s = defined7s;
	}
	public List<String> getDefined8s() {
		return defined8s;
	}
	public void setDefined8s(List<String> defined8s) {
		this.defined8s = defined8s;
	}
	public List<String> getDefined9s() {
		return defined9s;
	}
	public void setDefined9s(List<String> defined9s) {
		this.defined9s = defined9s;
	}
	public List<String> getDefined10s() {
		return defined10s;
	}
	public void setDefined10s(List<String> defined10s) {
		this.defined10s = defined10s;
	}
	public List<String> getDefined11s() {
		return defined11s;
	}
	public void setDefined11s(List<String> defined11s) {
		this.defined11s = defined11s;
	}
	public List<String> getDefined12s() {
		return defined12s;
	}
	public void setDefined12s(List<String> defined12s) {
		this.defined12s = defined12s;
	}
	public List<String> getDefined13s() {
		return defined13s;
	}
	public void setDefined13s(List<String> defined13s) {
		this.defined13s = defined13s;
	}
	public List<String> getDefined14s() {
		return defined14s;
	}
	public void setDefined14s(List<String> defined14s) {
		this.defined14s = defined14s;
	}
	public List<String> getDefined15s() {
		return defined15s;
	}
	public void setDefined15s(List<String> defined15s) {
		this.defined15s = defined15s;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public Map<Integer, String> getqStatusMap() {
		return qStatusMap;
	}
	public void setqStatusMap(Map<Integer, String> qStatusMap) {
		this.qStatusMap = qStatusMap;
	}
	
	
	
	
	
	
	
}









