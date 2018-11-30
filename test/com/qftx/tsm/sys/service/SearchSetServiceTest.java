package com.qftx.tsm.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.plan.user.day.service.TestValue;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.bean.SysFileBean;

public class SearchSetServiceTest extends BaseUtest{
	@Autowired 
	private CustSearchSetService custSearchSetService;
	
	/** 客户名称_1_1_1_1 (searchName_developCode_searchCode_sort_isQuery_isShow_dataType_state_isDisabled)*/
	
	// 客户跟进
	private String[] search1 = {"客户名称_custName_custName_1_1_1_1_1_2","单位名称_company_company_1_1_1_1_0_2","客户类型_SALES#10002_custTypeId_2_1_0_3","下次联系时间_nextFollowTime_nextStartActionDate_3_1_1_2","销售进程_SALES#10001_optionlistId_4_1_1_3","最近联系时间_lastFollowTime_lastStartActionDate_5_1_1_2","所有者_ownerAcc_accs_6_1_1_5_2_0","客户状态_custStatus_status_7_1_0_3","资源分组_groupList_resGroup_8_1_1_3","开始联系时间_firstFollowTime_initStartFollowDate_9_0_1_2","联系人_linkName_linkName_10_1_0_1_1_2","客户姓名_name_custName_10_1_1_1_0_2","联系电话_phone_phone_11_1_0_1_2_2","所在地区_area_area_12_3_0_3_1_1","所属行业_companyTrade_companyTrade_13_3_0_3_1_1"};
	// 跟进记录
	private String[] search2 = {"客户名称_custName_custName_1_1_1_1_1_2","单位名称_company_company_1_1_1_1_0_2","客户类型_SALES#10002_custTypeId_2_1_0_3","联系时间_followTime_lastStartActionDate_3_1_1_2","客户状态_custStatus_status_4_1_1_3","销售进程_SALES#10001_optionlistId_5_1_1_3","资源分组_groupList_resGroup_6_0_1_3","所有者_ownerAcc_accs_7_1_1_5_2_0","下次联系时间_nextFollowTime_nextStartActionDate_8_1_1_2","有效联系_effectivenes_effectiveness_9_1_1_3","跟进人_followAcc_faccs_10_1_0_5","下次联系方式_nextFollowType_followType_11_1_0_3","联系人_linkName_linkName_12_1_0_1_1_2","客户姓名_name_custName_12_1_1_1_0_2","联系电话_phone_phone_13_1_0_1_2_2","所在地区_area_area_14_3_0_3_1_1","所属行业_companyTrade_companyTrade_15_3_0_3_1_1"};
	// 客户交接
	private String[] search3 = {"客户名称_custName_custName_1_1_1_1_1_2","单位名称_company_company_1_1_1_1_0_2","客户类型_SALES#10002_custTypeId_2_1_1_3","客户状态_custStatus1_custType_3_1_1_3","资源分组_groupList_resGroupId_4_1_1_3","销售进程_SALES#10001_saleProcessId_5_1_1_3","所有者_ownerAcc_ownerAccsStr_6_1_1_5_2_0","添加/分配时间_allotTime_pstartDate_7_1_1_2","最近联系时间_lastFollowTime_startActionDate_8_1_1_2","联系人_linkName_mainLinkman_9_1_0_1_1_2","客户姓名_name_custName_9_1_1_1_0_2","联系电话_phone_mobilephone_10_1_0_1_2_2","所在地区_area_area_11_3_0_3_1_1","所属行业_companyTrade_companyTrade_12_3_0_3_1_1"};
	// 我的客户（资源）
	private String[] search4 = {"客户名称_custName_custName_1_1_1_1_1_2","单位名称_company_company_1_1_1_1_0_2","资源分组_groupList_groupId_2_1_1_3","添加/分配时间_allotTime_pstartDate_3_1_1_2","最近联系时间_lastFollowTime_startActionDate_4_1_1_2","下次联系时间_nextFollowTime_startDate_5_1_1_2","所有者_ownerAcc_ownerAccsStr_6_1_1_5_2_0","资源录入部门_resAddGroup_importDeptIdsStr_7_0_0_5","联系人_linkName_mainLinkman_8_1_0_1_1_2","客户姓名_name_custName_8_1_1_1_0_2","联系电话_phone_mobilephone_9_1_0_1_2_2","所在地区_area_area_10_3_0_3_1_1","所属行业_companyTrade_companyTrade_11_3_0_3_1_1"};
	// 我的客户（意向客户）
	private String[] search5 = {"客户名称_custName_custName_1_1_1_1_1_2","单位名称_company_company_1_1_1_1_0_2","客户类型_SALES#10002_custTypeId_2_1_1_3","销售进程_SALES#10001_saleProcessId_3_1_1_3","最近联系时间_lastFollowTime_startActionDate_4_1_1_2","下次联系时间_nextFollowTime_startDate_5_1_1_2","所有者_ownerAcc_ownerAccsStr_6_1_1_5_2_0","淘到客户时间_amoytocustomerDate_pstartDate_7_1_1_2","资源分组_groupList_groupId_8_1_1_3","共有者_commonAcc_commonAccsStr_9_1_1_5","联系人_linkName_mainLinkman_9_1_0_1_1_2","客户姓名_name_custName_9_1_1_1_0_2","联系电话_phone_mobilephone_10_1_0_1_2_2","所在地区_area_area_11_3_0_3_1_1","所属行业_companyTrade_companyTrade_12_3_0_3_1_1"};
	// 我的客户（签约客户）
	private String[] search6 = {"客户名称_custName_custName_1_1_1_1_1_2","单位名称_company_company_1_1_1_1_0_2","联系人_linkName_mainLinkman_2_1_0_1_1_2","客户姓名_name_custName_2_1_1_1_0_2","联系电话_phone_mobilephone_3_1_1_1_2_2","订单累计金额_orderAddMonty_orderAddMonty_4_3_1_7","最近联系时间_lastFollowTime_startActionDate_5_1_1_2","所有者_ownerAcc_ownerAccsStr_6_1_1_5_2_0","下次联系时间_nextFollowTime_pstartDate_7_1_1_2","签约时间_signTime_startDate_8_1_1_2","共有者_commonAcc_commonAccsStr_9_1_1_5","客户类型_SALES#10002_custTypeId_9_0_0_3","资源分组_groupList_groupId_10_1_1_3","所在地区_area_area_11_3_0_3_1_1","所属行业_companyTrade_companyTrade_12_3_0_3_1_1"};
	// 我的客户（全部客户）
	private String[] search7 = {"客户名称_custName_custName_1_1_1_1_1_2","单位名称_company_company_1_1_1_1_0_2","资源分组_groupList_resGroupId_2_1_1_3","客户状态_custStatus1_custType_3_1_1_3_2_0","销售进程_SALES#10001_saleProcessId_4_1_1_3","添加/分配时间_allotTime_pstartDate_5_1_1_2","所有者_ownerAcc_ownerAccsStr_6_1_1_5_2_0","最近联系时间_lastFollowTime_startActionDate_7_1_1_2","下次联系时间_nextFollowTime_startDate_8_0_0_2","联系人_linkName_mainLinkman_9_1_0_1_1_2","客户姓名_name_custName_9_1_1_1_0_2","联系电话_phone_mobilephone_10_1_0_1_2_2","所在地区_area_area_11_3_0_3_1_1","所属行业_companyTrade_companyTrade_12_3_0_3_1_1"};
	// 公海客户
	private String[] search8 = {"客户名称_custName_custName_1_1_1_1_1_2","单位名称_company_company_1_1_1_1_0_2","放弃时间_opreateTime_startDate_2_1_1_2","联系人_linkName_mainLinkman_3_1_0_1_1_2","客户姓名_name_custName_3_1_1_1_0_2","联系电话_phone_mobilephone_4_1_1_1_2_2","销售进程_SALES#10001_saleProcessId_5_1_1_3","放弃类型_opreateType_opreateType_6_1_1_3","资源放弃原因_SALES#10006_reason_7_1_1_3","销售人员_sellAcc_ownerAccsStr_8_1_1_5","放弃人_opreateAcc_updateAccsStr_9_3_1_5","未联系天数_unFollowDay_unFollowDay_10_3_1_3","在库时长_duration_duration_11_3_1_6","最近联系时间_lastFollowTime_startActionDate_12_1_1_2","所在地区_area_area_13_3_0_3_1_1","所属行业_companyTrade_companyTrade_14_3_0_3_1_1"};
	// 待分配资源
	private String[] search9 = {"客户名称_custName_name_1_1_1_1_1_2","单位名称_company_company_1_1_1_1_0_2","创建时间_inputTime_startDate_2_1_1_2","常用电话_mobilePhone_mobilePhone_3_3_1_1","备用电话_telPhone_telPhone_4_3_1_1","资源分组_groupList_groupList_5_3_1_3","资源录入部门_resAddGroup_deptId_6_1_1_5","联系人_linkName_mainLinkman_7_1_0_1_1_2","客户姓名_name_name_7_1_1_1_0_2","联系电话_phone_phone_8_1_0_1_2_2","所在地区_area_area_9_3_0_3_1_1","所属行业_companyTrade_companyTrade_10_3_0_3_1_1"};
	// 已分配资源
	private String[] search10 = {"客户名称_custName_name_1_1_1_1_1_2","单位名称_company_company_1_1_1_1_0_2","分配时间_allotTime_startDate_2_1_1_2","常用电话_mobilePhone_mobilePhone_3_3_1_1","备用电话_telPhone_telPhone_4_3_1_1","资源状态_resStatus_qStatus_5_1_1_3","所有者_ownerAcc_ownerAccsStr_6_1_1_5_2_0","资源分组_groupList_groupList_7_3_1_3","资源录入部门_resAddGroup_deptId_8_1_1_5","联系人_linkName_mainLinkman_9_1_0_1_1_2","客户姓名_name_name_9_1_1_1_0_2","联系电话_phone_phone_10_1_0_1_2_2","所在地区_area_area_11_3_0_3_1_1","所属行业_companyTrade_companyTrade_12_3_0_3_1_1"};
	// 全部资源
	private String[] search11 = {"客户名称_custName_name_1_1_1_1_1_2","单位名称_company_company_1_1_1_1_0_2","常用电话_mobilePhone_mobilePhone_2_3_1_1","备用电话_telPhone_telPhone_3_3_1_1","资源状态_allResStatus_qStatus_4_1_1_3","资源分组_groupList_groupList_5_3_1_3","所有者_ownerAcc_ownerAcc_6_3_1_3","资源录入部门_resAddGroup_deptId_7_1_1_5","创建时间_inputTime_startDate_8_1_1_2","联系人_linkName_mainLinkman_9_1_0_1_1_2","客户姓名_name_name_9_1_1_1_0_2","联系电话_phone_phone_10_1_0_1_2_2","所在地区_area_area_11_3_0_3_1_1","所属行业_companyTrade_companyTrade_12_3_0_3_1_1"};
	// 流失客户
	private String[] search12 = {"客户名称_custName_custName_1_1_1_1_1_2","单位名称_company_company_1_1_1_1_0_2","联系人_linkName_mainLinkman_2_1_0_1_1_2","客户姓名_name_custName_2_1_1_1_0_2","流失原因_SALES#40001_losingId_3_1_1_3","合同到期时间_contractExpireTime_pstartDate_4_1_1_2","未联系天数_unFollowDay_unFollowDay_5_3_1_3","所有者_ownerAcc_ownerAccsStr_6_1_1_5_2_0","上次联系时间_lastFollowTime_startActionDate_7_1_1_2","客户类型_SALES#10002_custTypeId_8_0_0_3","资源分组_groupList_resGroupId_9_0_0_3","备注_remark_remark_10_3_1_1","联系电话_phone_mobilephone_11_1_0_1_2_2","所在地区_area_area_12_3_0_3_1_1","所属行业_companyTrade_companyTrade_13_3_0_3_1_1"};
	// 服务管理（我的客户）
	private String[] search13 = {"客户名称_custName_1_1_1_1_1_2","单位名称_company_1_1_1_1_0_2","下次联系时间_nextFollowTime_2_1_1_2","客户等级_custLevel_3_1_1_3","服务标签_serviceLabel_4_3_1_3","最近联系时间_lastFollowTime_5_1_1_2","签约时间_signTime_6_1_1_2","客服人员_serviceAcc_7_1_1_5","销售人员_sellAcc_8_1_1_5","最近销售联系_sellLastFollowTime_9_1_1_2","客户类型_SALES#10002_10_0_0_3","资源分组_groupList_11_0_0_3","联系人_linkName_12_1_0_1_1_2","客户姓名_name_12_1_1_1_0_2","联系电话_phone_13_1_0_1_2_2","所在地区_area_14_3_0_3_1_1","所属行业_companyTrade_15_3_0_3_1_1"};
	// 服务管理（共享客户）
	private String[] search14 = {"客户名称_custName_1_1_1_1_1_2","单位名称_company_1_1_1_1_0_2","下次联系时间_nextFollowTime_2_1_1_2","客户等级_custLevel_3_1_1_3","服务标签_serviceLabel_4_3_1_3","最近联系时间_lastFollowTime_5_1_1_2","签约时间_signTime_6_1_1_2","销售人员_sellAcc_7_1_1_5","最近销售联系_sellLastFollowTime_8_1_1_2","客户类型_SALES#10002_9_0_0_3","资源分组_groupList_10_0_0_3","联系人_linkName_10_1_0_1_1_2","客户姓名_name_10_1_1_1_0_2","联系电话_phone_11_1_0_1_2_2","所在地区_area_12_3_0_3_1_1","所属行业_companyTrade_13_3_0_3_1_1"};
	// 回访记录
	private String[] search15 = {"客户名称_custName_1_1_1_1_1_2","单位名称_company_1_1_1_1_0_2","联系时间_followTime_2_1_1_2","客户等级_custLevel_3_1_1_3","服务标签_serviceLabel_4_3_1_3","回访客服_visitAcc_5_1_1_5","有效联系_effectivenes_6_1_1_3","联系方式_followType_7_1_1_3","联系人_linkName_8_1_0_1_1_2","客户姓名_name_8_1_1_1_0_2","联系电话_phone_9_1_0_1_2_2","所在地区_area_10_3_0_3_1_1","所属行业_companyTrade_11_3_0_3_1_1"};
	// 客户回访
	private String[] search16 = {"客户名称_custName_1_1_1_1_1_2","单位名称_company_1_1_1_1_0_2","下次联系时间_nextFollowTime_2_1_1_2","客户等级_custLevel_3_1_1_3","服务标签_serviceLabel_4_3_1_3","最近联系时间_lastFollowTime_5_1_1_2","客服人员_serviceAcc_6_1_1_5","销售人员_sellAcc_7_1_1_5","最近销售联系_sellLastFollowTime_8_1_1_2","联系人_linkName_9_1_0_1_1_2","客户姓名_name_9_1_1_1_0_2","联系电话_phone_10_1_0_1","所在地区_area_11_3_0_3_1_1","所属行业_companyTrade_12_3_0_3_1_1"};

	
	private Logger logger = Logger.getLogger(SearchSetServiceTest.class);
	
	@Test
	public void main() { 
		List<String[]>list = new ArrayList<String[]>();
		list.add(search1);
		list.add(search2);
		list.add(search3);
		list.add(search4);
		list.add(search5);
		list.add(search6);
		list.add(search7);
		list.add(search8);
		list.add(search9);
		list.add(search10);
		list.add(search11);
		list.add(search12);
//		list.add(search13);
//		list.add(search14);
//		list.add(search15);
//		list.add(search16);
		insertTest(list);
	}

	public void insertTest(List<String[]>list) { 
		Integer i = 1;
		List<CustSearchSet>searchSets = new ArrayList<CustSearchSet>();
		String searchName;
		String developCode;
		String searchCode;
		String isQuery;
		String sort;
		String isShow;
		String dataType;
		String state;
		String isDisabled; 
		String[]spi = null;
		for(String[] strs : list){
			for(String str: strs){
				CustSearchSet custSearchSet = new CustSearchSet();
				spi = str.split("_");
				searchName = spi[0];
				developCode = spi[1];
				searchCode = spi[2];
				sort = spi[3];
				isQuery = spi[4];
				isShow = spi[5];
				System.out.println(i.toString()+"+++"+searchName);
				dataType = spi[6];		
				state = "2";  // 0：个人客户，1：企业客户，2：共有
				isDisabled = "1"; // 是否可编辑：0-全部不可编辑；1-可编辑；2-查询不可编辑；3-列表显示不可编辑；
				if(spi.length>7){
					state = spi[7];	
					
					isDisabled = spi[8];	
				}
				custSearchSet.setId(SysBaseModelUtil.getModelId());
				custSearchSet.setEnable(1);
				custSearchSet.setSearchModule(i.toString());
				custSearchSet.setSearchName(searchName);
				custSearchSet.setIsQuery(Short.valueOf(isQuery));
				custSearchSet.setSort(Short.valueOf(sort));
				custSearchSet.setInputerAcc("admin");
				custSearchSet.setInputdate(new Date());
				custSearchSet.setOrgId(null);
				custSearchSet.setSearchCode(searchCode);
				custSearchSet.setDevelopCode(developCode.replaceAll("#","_"));
				custSearchSet.setIsShow(Short.valueOf(isShow));
				custSearchSet.setIsDisabled(Integer.valueOf(isDisabled));
				custSearchSet.setState(Integer.valueOf(state));
				custSearchSet.setDataType(Integer.valueOf(dataType));
				custSearchSet.setIsFiledSet(0);
				searchSets.add(custSearchSet);
			}
			i++;
		}
		
		if(searchSets !=null && searchSets.size() >0){
			custSearchSetService.createBatch(searchSets);
		}
	}
}
