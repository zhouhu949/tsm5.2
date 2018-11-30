package com.qftx.tsm.sys.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 16个页面列表显示顺序字段 
 *
 */
public class SearchListShowCodeDto{
	
	// new String[]{developCode,sort,isState}
	// isState 0:个人，1:企业，2:共有
	
	// 客户跟进
	public static List<String[]>modult_1_List = new ArrayList<String[]>();
	static{
		modult_1_List.add(new String[]{"nextFollowTime", "1","2"});
		modult_1_List.add(new String[]{"custName", "2","1"});
		modult_1_List.add(new String[]{"company", "2","0"});
		modult_1_List.add(new String[]{"linkName", "3","1"});
		modult_1_List.add(new String[]{"name", "3","0"});
		modult_1_List.add(new String[]{"SALES_10002", "4","2"});		
		modult_1_List.add(new String[]{"SALES_10001", "5","2"});
		modult_1_List.add(new String[]{"lastFollowTime", "6","2"});
		modult_1_List.add(new String[]{"custStatus", "7","2"});
		modult_1_List.add(new String[]{"groupList", "8","2"});
		modult_1_List.add(new String[]{"firstFollowTime", "9","2"});
		modult_1_List.add(new String[]{"custSource", "10","2"});
		modult_1_List.add(new String[]{"ownerAcc", "11","2"});	
		modult_1_List.add(new String[]{"phone", "12","2"});
		modult_1_List.add(new String[]{"area", "13","2"});
		modult_1_List.add(new String[]{"companyTrade", "14","2"});
		modult_1_List.add(new String[]{"defined1", "15","2"});
		modult_1_List.add(new String[]{"defined2", "16","2"});
		modult_1_List.add(new String[]{"defined3", "17","2"});
		modult_1_List.add(new String[]{"defined4", "18","2"});
		modult_1_List.add(new String[]{"defined5", "19","2"});
		modult_1_List.add(new String[]{"defined6", "20","2"});
		modult_1_List.add(new String[]{"defined7", "21","2"});
		modult_1_List.add(new String[]{"defined8", "22","2"});
		modult_1_List.add(new String[]{"defined9", "23","2"});
		modult_1_List.add(new String[]{"defined10", "24","2"});
		modult_1_List.add(new String[]{"defined11", "25","2"});
		modult_1_List.add(new String[]{"defined12", "26","2"});
		modult_1_List.add(new String[]{"defined13", "27","2"});
		modult_1_List.add(new String[]{"defined14", "28","2"});
		modult_1_List.add(new String[]{"defined15", "29","2"});
		modult_1_List.add(new String[]{"defined16", "30","2"});
		modult_1_List.add(new String[]{"defined17", "31","2"});
		modult_1_List.add(new String[]{"defined18", "32","2"});
	}
		
	// 跟进记录
	public static List<String[]>modult_2_List = new ArrayList<String[]>();
	static{
		modult_2_List.add(new String[]{"followTime", "1","2"});//联系时间
		modult_2_List.add(new String[]{"custName", "2","1"});//客户名称
		modult_2_List.add(new String[]{"company", "2","0"});//单位名称
		modult_2_List.add(new String[]{"name", "3","0"});//客户姓名
		modult_2_List.add(new String[]{"linkName", "3","1"});//联系人
		modult_2_List.add(new String[]{"SALES_10002", "4","2"});//客户类型
		modult_2_List.add(new String[]{"custStatus", "5","2"});//客户状态
		modult_2_List.add(new String[]{"SALES_10001", "6","2"});//销售进程
		modult_2_List.add(new String[]{"effectivenes", "7","2"});//有效联系
		modult_2_List.add(new String[]{"groupList", "8","2"});//资源分组
		modult_2_List.add(new String[]{"nextFollowTime", "9","2"});//下次联系时间
		modult_2_List.add(new String[]{"ownerAcc", "10","2"});//所有者
		modult_2_List.add(new String[]{"followAcc", "11","2"});//跟进人
		modult_2_List.add(new String[]{"nextFollowType", "12","2"});//下次联系方式
		modult_2_List.add(new String[]{"phone", "13","2"});//联系电话
		modult_2_List.add(new String[]{"area", "14","2"});//所在地区
		modult_2_List.add(new String[]{"companyTrade", "15","2"});//所属行业
		modult_2_List.add(new String[]{"defined1", "16","2"});
		modult_2_List.add(new String[]{"defined2", "17","2"});
		modult_2_List.add(new String[]{"defined3", "18","2"});
		modult_2_List.add(new String[]{"defined4", "19","2"});
		modult_2_List.add(new String[]{"defined5", "20","2"});
		modult_2_List.add(new String[]{"defined6", "21","2"});
		modult_2_List.add(new String[]{"defined7", "22","2"});
		modult_2_List.add(new String[]{"defined8", "23","2"});
		modult_2_List.add(new String[]{"defined9", "24","2"});
		modult_2_List.add(new String[]{"defined10", "25","2"});
		modult_2_List.add(new String[]{"defined11", "26","2"});
		modult_2_List.add(new String[]{"defined12", "27","2"});
		modult_2_List.add(new String[]{"defined13", "28","2"});
		modult_2_List.add(new String[]{"defined14", "29","2"});
		modult_2_List.add(new String[]{"defined15", "30","2"});
		modult_2_List.add(new String[]{"defined16", "31","2"});
		modult_2_List.add(new String[]{"defined17", "32","2"});
		modult_2_List.add(new String[]{"defined18", "33","2"});
	}
	
	
	//客户交接
	public static List<String[]>modult_3_List = new ArrayList<String[]>();
	static{
			modult_3_List.add(new String[]{"custName", "1","1"});//客户名称
			modult_3_List.add(new String[]{"company", "1","0"});//单位名称
			modult_3_List.add(new String[]{"name", "2","0"});//客户姓名
			modult_3_List.add(new String[]{"linkName", "2","1"});//联系人
			modult_3_List.add(new String[]{"SALES_10002", "3","2"});//客户类型
			modult_3_List.add(new String[]{"custStatus1", "4","2"});//客户状态
			modult_3_List.add(new String[]{"groupList", "5","2"});//资源分组
			modult_3_List.add(new String[]{"SALES_10001", "6","2"});//销售进程
			modult_3_List.add(new String[]{"allotTime", "7","2"});//添加/分配时间
			modult_3_List.add(new String[]{"nextFollowTime", "8","2"});//下次联系时间
			modult_3_List.add(new String[]{"lastFollowTime", "9","2"});//最近联系时间
			modult_3_List.add(new String[]{"ownerAcc", "10","2"});//所有者						
			modult_3_List.add(new String[]{"phone", "11","2"});//联系电话
			modult_3_List.add(new String[]{"area", "12","2"});//所在地区
			modult_3_List.add(new String[]{"companyTrade", "13","2"});//所属行业
			modult_3_List.add(new String[]{"defined1", "14","2"});
			modult_3_List.add(new String[]{"defined2", "15","2"});
			modult_3_List.add(new String[]{"defined3", "16","2"});
			modult_3_List.add(new String[]{"defined4", "17","2"});
			modult_3_List.add(new String[]{"defined5", "18","2"});
			modult_3_List.add(new String[]{"defined6", "19","2"});
			modult_3_List.add(new String[]{"defined7", "20","2"});
			modult_3_List.add(new String[]{"defined8", "21","2"});
			modult_3_List.add(new String[]{"defined9", "22","2"});
			modult_3_List.add(new String[]{"defined10", "23","2"});
			modult_3_List.add(new String[]{"defined11", "24","2"});
			modult_3_List.add(new String[]{"defined12", "25","2"});
			modult_3_List.add(new String[]{"defined13", "26","2"});
			modult_3_List.add(new String[]{"defined14", "27","2"});
			modult_3_List.add(new String[]{"defined15", "28","2"});
			modult_3_List.add(new String[]{"defined16", "29","2"});
			modult_3_List.add(new String[]{"defined17", "30","2"});
			modult_3_List.add(new String[]{"defined18", "31","2"});
		}
	
	
	//我的客户（资源）
	public static List<String[]>modult_4_List = new ArrayList<String[]>();
	static{
			modult_4_List.add(new String[]{"custName", "1","1"});//客户名称
			modult_4_List.add(new String[]{"company", "1","0"});//单位名称
			modult_4_List.add(new String[]{"name", "2","0"});//客户姓名
			modult_4_List.add(new String[]{"linkName", "2","1"});//联系人
			modult_4_List.add(new String[]{"groupList", "3","2"});//资源分组
			modult_4_List.add(new String[]{"allotTime", "4","2"});//添加/分配时间
			modult_4_List.add(new String[]{"lastFollowTime", "5","2"});//最近联系时间
			modult_4_List.add(new String[]{"nextFollowTime", "6","2"});//下次联系时间
			modult_4_List.add(new String[]{"custSource", "7","2"});//客户来源
			modult_4_List.add(new String[]{"inputStatus", "8","2"});//筛查结果
			modult_4_List.add(new String[]{"ownerAcc", "9","2"});//所有者
			modult_4_List.add(new String[]{"resAddGroup", "10","2"});//资源录入部门			
			modult_4_List.add(new String[]{"phone", "11","2"});//联系电话
			modult_4_List.add(new String[]{"area", "12","2"});//所在地区
			modult_4_List.add(new String[]{"companyTrade", "13","2"});//所属行业
			modult_4_List.add(new String[]{"defined1", "14","2"});
			modult_4_List.add(new String[]{"defined2", "15","2"});
			modult_4_List.add(new String[]{"defined3", "16","2"});
			modult_4_List.add(new String[]{"defined4", "17","2"});
			modult_4_List.add(new String[]{"defined5", "18","2"});
			modult_4_List.add(new String[]{"defined6", "19","2"});
			modult_4_List.add(new String[]{"defined7", "20","2"});
			modult_4_List.add(new String[]{"defined8", "21","2"});
			modult_4_List.add(new String[]{"defined9", "22","2"});
			modult_4_List.add(new String[]{"defined10", "23","2"});
			modult_4_List.add(new String[]{"defined11", "24","2"});
			modult_4_List.add(new String[]{"defined12", "25","2"});
			modult_4_List.add(new String[]{"defined13", "26","2"});
			modult_4_List.add(new String[]{"defined14", "27","2"});
			modult_4_List.add(new String[]{"defined15", "28","2"});
			modult_4_List.add(new String[]{"defined16", "29","2"});
			modult_4_List.add(new String[]{"defined17", "30","2"});
			modult_4_List.add(new String[]{"defined18", "31","2"});
		}
	
	//我的客户（意向客户）
	public static List<String[]>modult_5_List = new ArrayList<String[]>();
	static{
			modult_5_List.add(new String[]{"custName", "1","1"});//客户名称
			modult_5_List.add(new String[]{"company", "1","0"});//单位名称
			modult_5_List.add(new String[]{"name", "2","0"});//客户姓名
			modult_5_List.add(new String[]{"linkName", "2","1"});//联系人
			modult_5_List.add(new String[]{"SALES_10002", "3","2"});//客户类型
			modult_5_List.add(new String[]{"SALES_10001", "4","2"});//销售进程
			modult_5_List.add(new String[]{"lastFollowTime", "5","2"});//最近联系时间
			modult_5_List.add(new String[]{"nextFollowTime", "6","2"});//下次联系时间
			modult_5_List.add(new String[]{"amoytocustomerDate", "7","2"});//淘到客户时间
			modult_5_List.add(new String[]{"groupList", "8","2"});//资源分组
			modult_5_List.add(new String[]{"custSource", "9","2"});//客户来源
			modult_5_List.add(new String[]{"ownerAcc", "10","2"});//所有者
			modult_5_List.add(new String[]{"commonAcc", "11","2"});//共有者
			modult_5_List.add(new String[]{"phone", "12","2"});//联系电话
			modult_5_List.add(new String[]{"area", "13","2"});//所在地区
			modult_5_List.add(new String[]{"companyTrade", "14","2"});//所属行业
			modult_5_List.add(new String[]{"defined1", "15","2"});
			modult_5_List.add(new String[]{"defined2", "16","2"});
			modult_5_List.add(new String[]{"defined3", "17","2"});
			modult_5_List.add(new String[]{"defined4", "18","2"});
			modult_5_List.add(new String[]{"defined5", "19","2"});
			modult_5_List.add(new String[]{"defined6", "20","2"});
			modult_5_List.add(new String[]{"defined7", "21","2"});
			modult_5_List.add(new String[]{"defined8", "22","2"});
			modult_5_List.add(new String[]{"defined9", "23","2"});
			modult_5_List.add(new String[]{"defined10", "24","2"});
			modult_5_List.add(new String[]{"defined11", "25","2"});
			modult_5_List.add(new String[]{"defined12", "26","2"});
			modult_5_List.add(new String[]{"defined13", "27","2"});
			modult_5_List.add(new String[]{"defined14", "28","2"});
			modult_5_List.add(new String[]{"defined15", "29","2"});
			modult_5_List.add(new String[]{"defined16", "30","2"});
			modult_5_List.add(new String[]{"defined17", "31","2"});
			modult_5_List.add(new String[]{"defined18", "32","2"});
		}
	
	//我的客户（签约客户）
	public static List<String[]>modult_6_List = new ArrayList<String[]>();
	static{
			modult_6_List.add(new String[]{"custName", "1","1"});//客户名称
			modult_6_List.add(new String[]{"company", "1","0"});//单位名称
			modult_6_List.add(new String[]{"name", "2","0"});//客户姓名
			modult_6_List.add(new String[]{"linkName", "2","1"});//联系人			
			modult_6_List.add(new String[]{"phone", "3","2"});//联系电话
			modult_6_List.add(new String[]{"orderCountMonty", "4","2"});//订单总金额
			modult_6_List.add(new String[]{"orderAddMonty", "5","2"});//回款总金额
			modult_6_List.add(new String[]{"lastFollowTime", "6","2"});//最近联系时间
			modult_6_List.add(new String[]{"nextFollowTime", "7","2"});//下次联系时间
			modult_6_List.add(new String[]{"signTime", "8","2"});//签约时间
			modult_6_List.add(new String[]{"commonAcc", "9","2"});//共有者
			modult_6_List.add(new String[]{"SALES_10002", "10","2"});//客户类型			
			modult_6_List.add(new String[]{"groupList", "11","2"});//资源分组
			modult_6_List.add(new String[]{"custSource", "12","2"});//客户来源
			modult_6_List.add(new String[]{"ownerAcc", "13","2"});//所有者	
			modult_6_List.add(new String[]{"serviceAcc", "14","2"});//客服人员	
			modult_6_List.add(new String[]{"area", "15","2"});//所在地区
			modult_6_List.add(new String[]{"companyTrade", "16","2"});//所属行业
			modult_6_List.add(new String[]{"defined1", "17","2"});
			modult_6_List.add(new String[]{"defined2", "18","2"});
			modult_6_List.add(new String[]{"defined3", "19","2"});
			modult_6_List.add(new String[]{"defined4", "20","2"});
			modult_6_List.add(new String[]{"defined5", "21","2"});
			modult_6_List.add(new String[]{"defined6", "22","2"});
			modult_6_List.add(new String[]{"defined7", "23","2"});
			modult_6_List.add(new String[]{"defined8", "24","2"});
			modult_6_List.add(new String[]{"defined9", "25","2"});
			modult_6_List.add(new String[]{"defined10", "26","2"});
			modult_6_List.add(new String[]{"defined11", "27","2"});
			modult_6_List.add(new String[]{"defined12", "28","2"});
			modult_6_List.add(new String[]{"defined13", "29","2"});
			modult_6_List.add(new String[]{"defined14", "30","2"});
			modult_6_List.add(new String[]{"defined15", "31","2"});
			modult_6_List.add(new String[]{"defined16", "32","2"});
			modult_6_List.add(new String[]{"defined17", "33","2"});
			modult_6_List.add(new String[]{"defined18", "34","2"});
		}
	
	//我的客户（全部客户）
	public static List<String[]>modult_7_List = new ArrayList<String[]>();
	static{
			modult_7_List.add(new String[]{"custName", "1","1"});//客户名称
			modult_7_List.add(new String[]{"company", "1","0"});//单位名称
			modult_7_List.add(new String[]{"name", "2","0"});//客户姓名
			modult_7_List.add(new String[]{"linkName", "2","1"});//联系人
			modult_7_List.add(new String[]{"groupList", "3","2"});//资源分组
			modult_7_List.add(new String[]{"custStatus1", "4","2"});//客户状态
			modult_7_List.add(new String[]{"SALES_10001", "5","2"});//销售进程
			modult_7_List.add(new String[]{"allotTime", "6","2"});//添加/分配时间
			modult_7_List.add(new String[]{"lastFollowTime", "7","2"});//最近联系时间
			modult_7_List.add(new String[]{"nextFollowTime", "8","2"});//下次联系时间
			modult_7_List.add(new String[]{"custSource", "9","2"});//客户来源
			modult_7_List.add(new String[]{"ownerAcc", "10","2"});//所有者
			modult_7_List.add(new String[]{"phone", "11","2"});//联系电话
			modult_7_List.add(new String[]{"area", "12","2"});//所在地区
			modult_7_List.add(new String[]{"companyTrade", "13","2"});//所属行业
			modult_7_List.add(new String[]{"defined1", "14","2"});
			modult_7_List.add(new String[]{"defined2", "15","2"});
			modult_7_List.add(new String[]{"defined3", "16","2"});
			modult_7_List.add(new String[]{"defined4", "17","2"});
			modult_7_List.add(new String[]{"defined5", "18","2"});
			modult_7_List.add(new String[]{"defined6", "19","2"});
			modult_7_List.add(new String[]{"defined7", "20","2"});
			modult_7_List.add(new String[]{"defined8", "21","2"});
			modult_7_List.add(new String[]{"defined9", "22","2"});
			modult_7_List.add(new String[]{"defined10", "23","2"});
			modult_7_List.add(new String[]{"defined11", "24","2"});
			modult_7_List.add(new String[]{"defined12", "25","2"});
			modult_7_List.add(new String[]{"defined13", "26","2"});
			modult_7_List.add(new String[]{"defined14", "27","2"});
			modult_7_List.add(new String[]{"defined15", "28","2"});
			modult_7_List.add(new String[]{"defined16", "29","2"});
			modult_7_List.add(new String[]{"defined17", "30","2"});
			modult_7_List.add(new String[]{"defined18", "31","2"});
		}
	
	//公海客户
	public static List<String[]>modult_8_List = new ArrayList<String[]>();
	static{
			modult_8_List.add(new String[]{"opreateTime", "1","2"});//放弃时间
			modult_8_List.add(new String[]{"custName", "2","1"});//客户名称
			modult_8_List.add(new String[]{"company", "2","0"});//单位名称			
			modult_8_List.add(new String[]{"name", "3","0"});//客户姓名
			modult_8_List.add(new String[]{"linkName", "3","1"});//联系人
			modult_8_List.add(new String[]{"phone", "4","2"});//联系电话
			modult_8_List.add(new String[]{"SALES_10001", "5","2"});//销售进程
			modult_8_List.add(new String[]{"opreateType", "6","2"});//放弃类型
			modult_8_List.add(new String[]{"SALES_10006", "7","2"});//资源放弃原因
			modult_8_List.add(new String[]{"sellAcc", "8","2"});//销售人员
			modult_8_List.add(new String[]{"opreateAcc", "9","2"});//放弃人
			modult_8_List.add(new String[]{"unFollowDay", "10","2"});//未联系天数
			modult_8_List.add(new String[]{"duration", "11","2"});//在库时长
			modult_8_List.add(new String[]{"lastFollowTime", "12","2"});//最近联系时间
			modult_8_List.add(new String[]{"area", "13","2"});//所在地区
			modult_8_List.add(new String[]{"companyTrade", "14","2"});//所属行业
			modult_8_List.add(new String[]{"defined1", "15","2"});
			modult_8_List.add(new String[]{"defined2", "16","2"});
			modult_8_List.add(new String[]{"defined3", "17","2"});
			modult_8_List.add(new String[]{"defined4", "18","2"});
			modult_8_List.add(new String[]{"defined5", "19","2"});
			modult_8_List.add(new String[]{"defined6", "20","2"});
			modult_8_List.add(new String[]{"defined7", "21","2"});
			modult_8_List.add(new String[]{"defined8", "22","2"});
			modult_8_List.add(new String[]{"defined9", "23","2"});
			modult_8_List.add(new String[]{"defined10", "24","2"});
			modult_8_List.add(new String[]{"defined11", "25","2"});
			modult_8_List.add(new String[]{"defined12", "26","2"});
			modult_8_List.add(new String[]{"defined13", "27","2"});
			modult_8_List.add(new String[]{"defined14", "28","2"});
			modult_8_List.add(new String[]{"defined15", "29","2"});
			modult_8_List.add(new String[]{"defined16", "30","2"});
			modult_8_List.add(new String[]{"defined17", "31","2"});
			modult_8_List.add(new String[]{"defined18", "32","2"});
		}
	
	//待分配资源
	public static List<String[]>modult_9_List = new ArrayList<String[]>();
	static{
			modult_9_List.add(new String[]{"custName", "1","1"});//客户名称
			modult_9_List.add(new String[]{"company", "1","0"});//单位名称
			modult_9_List.add(new String[]{"name", "2","0"});//客户姓名
			modult_9_List.add(new String[]{"linkName", "2","1"});//联系人
			modult_9_List.add(new String[]{"inputTime", "3","2"});//创建时间
			modult_9_List.add(new String[]{"mobilePhone", "4","2"});//常用电话
			modult_9_List.add(new String[]{"telPhone", "5","2"});//备用电话
			modult_9_List.add(new String[]{"groupList", "6","2"});//资源分组
			modult_9_List.add(new String[]{"resAddGroup", "7","2"});//资源录入部门
			modult_9_List.add(new String[]{"inputStatus", "8","2"});//筛查结果
			modult_9_List.add(new String[]{"phone", "9","2"});//联系电话
			modult_9_List.add(new String[]{"area", "10","2"});//所在地区
			modult_9_List.add(new String[]{"companyTrade", "11","2"});//所属行业
			modult_9_List.add(new String[]{"defined1", "12","2"});
			modult_9_List.add(new String[]{"defined2", "13","2"});
			modult_9_List.add(new String[]{"defined3", "14","2"});
			modult_9_List.add(new String[]{"defined4", "15","2"});
			modult_9_List.add(new String[]{"defined5", "16","2"});
			modult_9_List.add(new String[]{"defined6", "17","2"});
			modult_9_List.add(new String[]{"defined7", "18","2"});
			modult_9_List.add(new String[]{"defined8", "19","2"});
			modult_9_List.add(new String[]{"defined9", "20","2"});
			modult_9_List.add(new String[]{"defined10", "21","2"});
			modult_9_List.add(new String[]{"defined11", "22","2"});
			modult_9_List.add(new String[]{"defined12", "23","2"});
			modult_9_List.add(new String[]{"defined13", "24","2"});
			modult_9_List.add(new String[]{"defined14", "25","2"});
			modult_9_List.add(new String[]{"defined15", "26","2"});
			modult_9_List.add(new String[]{"defined16", "27","2"});
			modult_9_List.add(new String[]{"defined17", "28","2"});
			modult_9_List.add(new String[]{"defined18", "29","2"});
		}
	
	//已分配资源
	public static List<String[]>modult_10_List = new ArrayList<String[]>();
	static{
			modult_10_List.add(new String[]{"allotTime", "1","2"});//分配时间
			modult_10_List.add(new String[]{"custName", "2","1"});//客户名称
			modult_10_List.add(new String[]{"company", "2","0"});//单位名称	
			modult_10_List.add(new String[]{"name", "3","0"});//客户姓名
			modult_10_List.add(new String[]{"linkName", "3","1"});//联系人
			modult_10_List.add(new String[]{"mobilePhone", "4","2"});//常用电话
			modult_10_List.add(new String[]{"telPhone", "5","2"});//备用电话
			modult_10_List.add(new String[]{"resStatus", "6","2"});//资源状态			
			modult_10_List.add(new String[]{"groupList", "7","2"});//资源分组
			modult_10_List.add(new String[]{"resAddGroup", "8","2"});//资源录入部门
			modult_10_List.add(new String[]{"ownerAcc", "9","2"});//所有者
			modult_10_List.add(new String[]{"phone", "10","2"});//联系电话
			modult_10_List.add(new String[]{"area", "11","2"});//所在地区
			modult_10_List.add(new String[]{"companyTrade", "12","2"});//所属行业
			modult_10_List.add(new String[]{"defined1", "13","2"});
			modult_10_List.add(new String[]{"defined2", "14","2"});
			modult_10_List.add(new String[]{"defined3", "15","2"});
			modult_10_List.add(new String[]{"defined4", "16","2"});
			modult_10_List.add(new String[]{"defined5", "17","2"});
			modult_10_List.add(new String[]{"defined6", "18","2"});
			modult_10_List.add(new String[]{"defined7", "19","2"});
			modult_10_List.add(new String[]{"defined8", "20","2"});
			modult_10_List.add(new String[]{"defined9", "21","2"});
			modult_10_List.add(new String[]{"defined10", "22","2"});
			modult_10_List.add(new String[]{"defined11", "23","2"});
			modult_10_List.add(new String[]{"defined12", "24","2"});
			modult_10_List.add(new String[]{"defined13", "25","2"});
			modult_10_List.add(new String[]{"defined14", "26","2"});
			modult_10_List.add(new String[]{"defined15", "27","2"});
			modult_10_List.add(new String[]{"defined16", "28","2"});
			modult_10_List.add(new String[]{"defined17", "29","2"});
			modult_10_List.add(new String[]{"defined18", "30","2"});
		}
	
	//全部资源
	public static List<String[]>modult_11_List = new ArrayList<String[]>();
	static{
			modult_11_List.add(new String[]{"custName", "1","1"});//客户名称
			modult_11_List.add(new String[]{"company", "1","0"});//单位名称
			modult_11_List.add(new String[]{"name", "2","0"});//客户姓名
			modult_11_List.add(new String[]{"linkName", "2","1"});//联系人
			modult_11_List.add(new String[]{"mobilePhone", "3","2"});//常用电话
			modult_11_List.add(new String[]{"telPhone", "4","2"});//备用电话
			modult_11_List.add(new String[]{"allResStatus", "5","2"});//资源状态
			modult_11_List.add(new String[]{"groupList", "6","2"});//资源分组
			modult_11_List.add(new String[]{"ownerAcc", "7","2"});//所有者
			modult_11_List.add(new String[]{"resAddGroup", "8","2"});//资源录入部门
			modult_11_List.add(new String[]{"inputTime", "9","2"});//创建时间
			modult_11_List.add(new String[]{"phone", "10","2"});//联系电话
			modult_11_List.add(new String[]{"area", "11","2"});//所在地区
			modult_11_List.add(new String[]{"companyTrade", "12","2"});//所属行业
			modult_11_List.add(new String[]{"defined1", "13","2"});
			modult_11_List.add(new String[]{"defined2", "14","2"});
			modult_11_List.add(new String[]{"defined3", "15","2"});
			modult_11_List.add(new String[]{"defined4", "16","2"});
			modult_11_List.add(new String[]{"defined5", "17","2"});
			modult_11_List.add(new String[]{"defined6", "18","2"});
			modult_11_List.add(new String[]{"defined7", "19","2"});
			modult_11_List.add(new String[]{"defined8", "20","2"});
			modult_11_List.add(new String[]{"defined9", "21","2"});
			modult_11_List.add(new String[]{"defined10", "22","2"});
			modult_11_List.add(new String[]{"defined11", "23","2"});
			modult_11_List.add(new String[]{"defined12", "24","2"});
			modult_11_List.add(new String[]{"defined13", "25","2"});
			modult_11_List.add(new String[]{"defined14", "26","2"});
			modult_11_List.add(new String[]{"defined15", "27","2"});
			modult_11_List.add(new String[]{"defined16", "28","2"});
			modult_11_List.add(new String[]{"defined17", "29","2"});
			modult_11_List.add(new String[]{"defined18", "30","2"});
		}
	
	
	//流失客户
	public static List<String[]>modult_12_List = new ArrayList<String[]>();
	static{
			modult_12_List.add(new String[]{"custName", "1","1"});//客户名称
			modult_12_List.add(new String[]{"company", "1","0"});//单位名称
			modult_12_List.add(new String[]{"name", "2","0"});//客户姓名
			modult_12_List.add(new String[]{"linkName", "2","1"});//联系人
			modult_12_List.add(new String[]{"SALES_40001", "3","2"});//流失原因
			modult_12_List.add(new String[]{"contractExpireTime", "4","2"});//合同到期时间
			modult_12_List.add(new String[]{"unFollowDay", "5","2"});//未联系天数
			modult_12_List.add(new String[]{"ownerAcc", "6","2"});//所有者
			modult_12_List.add(new String[]{"lastFollowTime", "7","2"});//上次联系时间
			modult_12_List.add(new String[]{"SALES_10002", "8","2"});//客户类型
			modult_12_List.add(new String[]{"groupList", "9","2"});//资源分组
			modult_12_List.add(new String[]{"remark", "10","2"});//备注
			modult_12_List.add(new String[]{"phone", "11","2"});//联系电话
			modult_12_List.add(new String[]{"area", "12","2"});//所在地区
			modult_12_List.add(new String[]{"companyTrade", "13","2"});//所属行业
			modult_12_List.add(new String[]{"defined1", "14","2"});
			modult_12_List.add(new String[]{"defined2", "15","2"});
			modult_12_List.add(new String[]{"defined3", "16","2"});
			modult_12_List.add(new String[]{"defined4", "17","2"});
			modult_12_List.add(new String[]{"defined5", "18","2"});
			modult_12_List.add(new String[]{"defined6", "19","2"});
			modult_12_List.add(new String[]{"defined7", "20","2"});
			modult_12_List.add(new String[]{"defined8", "21","2"});
			modult_12_List.add(new String[]{"defined9", "22","2"});
			modult_12_List.add(new String[]{"defined10", "23","2"});
			modult_12_List.add(new String[]{"defined11", "24","2"});
			modult_12_List.add(new String[]{"defined12", "25","2"});
			modult_12_List.add(new String[]{"defined13", "26","2"});
			modult_12_List.add(new String[]{"defined14", "27","2"});
			modult_12_List.add(new String[]{"defined15", "28","2"});
			modult_12_List.add(new String[]{"defined16", "29","2"});
			modult_12_List.add(new String[]{"defined17", "30","2"});
			modult_12_List.add(new String[]{"defined18", "31","2"});
		}
	
	
	// 服务管理（我的客户）
		public static List<String[]>modult_13_List = new ArrayList<String[]>();
		static{		
			modult_13_List.add(new String[]{"nextFollowTime", "1","2"});// 下次联系时间
			modult_13_List.add(new String[]{"custName", "2","1"});//客户名称
			modult_13_List.add(new String[]{"company", "2","0"});//单位名称
			modult_13_List.add(new String[]{"name", "3","0"});//客户姓名
			modult_13_List.add(new String[]{"linkName", "3","1"});//联系人
			modult_13_List.add(new String[]{"SALES_10011", "4","2"});// 客户等级
			modult_13_List.add(new String[]{"SALES_10012", "5","2"});//服务标签
			modult_13_List.add(new String[]{"lastFollowTime", "6","2"});//最近联系时间
			modult_13_List.add(new String[]{"signTime", "7","2"});// 签约时间
			modult_13_List.add(new String[]{"serviceAcc", "8","2"});//客服人员
			modult_13_List.add(new String[]{"sellAcc", "9","2"});//销售人员
			modult_13_List.add(new String[]{"sellLastFollowTime", "10","2"});// 最近销售联系
			modult_13_List.add(new String[]{"SALES_10002", "11","2"});//客户类型
			modult_13_List.add(new String[]{"groupList", "12","2"});//资源分组
			modult_13_List.add(new String[]{"phone", "13","2"});//联系电话
			modult_13_List.add(new String[]{"area", "14","2"});//所在地区
			modult_13_List.add(new String[]{"companyTrade", "15","2"});//所属行业
			modult_13_List.add(new String[]{"defined1", "16","2"});
			modult_13_List.add(new String[]{"defined2", "17","2"});
			modult_13_List.add(new String[]{"defined3", "18","2"});
			modult_13_List.add(new String[]{"defined4", "19","2"});
			modult_13_List.add(new String[]{"defined5", "20","2"});
			modult_13_List.add(new String[]{"defined6", "21","2"});
			modult_13_List.add(new String[]{"defined7", "22","2"});
			modult_13_List.add(new String[]{"defined8", "23","2"});
			modult_13_List.add(new String[]{"defined9", "24","2"});
			modult_13_List.add(new String[]{"defined10", "25","2"});
			modult_13_List.add(new String[]{"defined11", "26","2"});
			modult_13_List.add(new String[]{"defined12", "27","2"});
			modult_13_List.add(new String[]{"defined13", "28","2"});
			modult_13_List.add(new String[]{"defined14", "29","2"});
			modult_13_List.add(new String[]{"defined15", "30","2"});
			modult_13_List.add(new String[]{"defined16", "31","2"});
			modult_13_List.add(new String[]{"defined17", "32","2"});
			modult_13_List.add(new String[]{"defined18", "33","2"});
		}
		
		// 服务管理（共享客户）
		public static List<String[]>modult_14_List = new ArrayList<String[]>();
		static{		
			modult_14_List.add(new String[]{"nextFollowTime", "1","2"});// 下次联系时间
			modult_14_List.add(new String[]{"custName", "2","1"});//客户名称
			modult_14_List.add(new String[]{"company", "2","0"});//单位名称
			modult_14_List.add(new String[]{"name", "3","0"});//客户姓名
			modult_14_List.add(new String[]{"linkName", "3","1"});//联系人
			modult_14_List.add(new String[]{"SALES_10011", "4","2"});// 客户等级
			modult_14_List.add(new String[]{"SALES_10012", "5","2"});//服务标签
			modult_14_List.add(new String[]{"lastFollowTime", "6","2"});//最近联系时间
			modult_14_List.add(new String[]{"signTime", "7","2"});// 签约时间
			modult_14_List.add(new String[]{"serviceAcc", "8","2"});//服务人员
			modult_14_List.add(new String[]{"sellAcc", "9","2"});//销售人员
			modult_14_List.add(new String[]{"sellLastFollowTime", "10","2"});// 最近销售联系
			modult_14_List.add(new String[]{"SALES_10002", "11","2"});//客户类型
			modult_14_List.add(new String[]{"groupList", "12","2"});//资源分组
			modult_14_List.add(new String[]{"phone", "13","2"});//联系电话
			modult_14_List.add(new String[]{"area", "14","2"});//所在地区
			modult_14_List.add(new String[]{"companyTrade", "15","2"});//所属行业
			modult_14_List.add(new String[]{"defined1", "16","2"});
			modult_14_List.add(new String[]{"defined2", "17","2"});
			modult_14_List.add(new String[]{"defined3", "18","2"});
			modult_14_List.add(new String[]{"defined4", "19","2"});
			modult_14_List.add(new String[]{"defined5", "20","2"});
			modult_14_List.add(new String[]{"defined6", "21","2"});
			modult_14_List.add(new String[]{"defined7", "22","2"});
			modult_14_List.add(new String[]{"defined8", "23","2"});
			modult_14_List.add(new String[]{"defined9", "24","2"});
			modult_14_List.add(new String[]{"defined10", "25","2"});
			modult_14_List.add(new String[]{"defined11", "26","2"});
			modult_14_List.add(new String[]{"defined12", "27","2"});
			modult_14_List.add(new String[]{"defined13", "28","2"});
			modult_14_List.add(new String[]{"defined14", "29","2"});
			modult_14_List.add(new String[]{"defined15", "30","2"});
			modult_14_List.add(new String[]{"defined16", "31","2"});
			modult_14_List.add(new String[]{"defined17", "32","2"});
			modult_14_List.add(new String[]{"defined18", "33","2"});
		}
		
		// 回访记录
		public static List<String[]>modult_15_List = new ArrayList<String[]>();
		static{		
			modult_15_List.add(new String[]{"followTime", "1","2"});// 联系时间
			modult_15_List.add(new String[]{"custName", "2","1"});//客户名称
			modult_15_List.add(new String[]{"company", "2","0"});//单位名称
			modult_15_List.add(new String[]{"name", "3","0"});//客户姓名
			modult_15_List.add(new String[]{"linkName", "3","1"});//联系人
			modult_15_List.add(new String[]{"SALES_10011", "4","2"});// 客户等级
			modult_15_List.add(new String[]{"SALES_10012", "5","2"});//服务标签
			modult_15_List.add(new String[]{"servicePacc", "6","2"});//客服人员
			modult_15_List.add(new String[]{"sellAcc", "7","2"});//销售人员
			modult_15_List.add(new String[]{"visitAcc", "8","2"});//回访客服
			modult_15_List.add(new String[]{"effectivenes", "9","2"});// 有效联系
			modult_15_List.add(new String[]{"followType", "10","2"});//联系方式
			modult_15_List.add(new String[]{"phone", "11","2"});//联系电话
			modult_15_List.add(new String[]{"area", "12","2"});//所在地区
			modult_15_List.add(new String[]{"companyTrade", "13","2"});//所属行业
			modult_15_List.add(new String[]{"defined1", "14","2"});
			modult_15_List.add(new String[]{"defined2", "15","2"});
			modult_15_List.add(new String[]{"defined3", "16","2"});
			modult_15_List.add(new String[]{"defined4", "17","2"});
			modult_15_List.add(new String[]{"defined5", "18","2"});
			modult_15_List.add(new String[]{"defined6", "19","2"});
			modult_15_List.add(new String[]{"defined7", "20","2"});
			modult_15_List.add(new String[]{"defined8", "21","2"});
			modult_15_List.add(new String[]{"defined9", "22","2"});
			modult_15_List.add(new String[]{"defined10", "23","2"});
			modult_15_List.add(new String[]{"defined11", "24","2"});
			modult_15_List.add(new String[]{"defined12", "25","2"});
			modult_15_List.add(new String[]{"defined13", "26","2"});
			modult_15_List.add(new String[]{"defined14", "27","2"});
			modult_15_List.add(new String[]{"defined15", "28","2"});
			modult_15_List.add(new String[]{"defined16", "29","2"});
			modult_15_List.add(new String[]{"defined17", "30","2"});
			modult_15_List.add(new String[]{"defined18", "31","2"});
		}
		
		// 客户回访
		public static List<String[]>modult_16_List = new ArrayList<String[]>();
		static{		
			modult_16_List.add(new String[]{"nextFollowTime", "1","2"});// 下次联系时间
			modult_16_List.add(new String[]{"custName", "2","1"});//客户名称
			modult_16_List.add(new String[]{"company", "2","0"});//单位名称
			modult_16_List.add(new String[]{"name", "3","0"});//客户姓名
			modult_16_List.add(new String[]{"linkName", "3","1"});//联系人
			modult_16_List.add(new String[]{"SALES_10011", "4","2"});// 客户等级
			modult_16_List.add(new String[]{"SALES_10012", "5","2"});//服务标签
			modult_16_List.add(new String[]{"lastFollowTime", "6","2"});//最近联系时间
			modult_16_List.add(new String[]{"servicePacc", "7","2"});// 客服人员
			modult_16_List.add(new String[]{"sellAcc", "8","2"});//销售人员
			modult_16_List.add(new String[]{"sellLastFollowTime", "9","2"});//最近销售联系
			modult_16_List.add(new String[]{"phone", "10","2"});//联系电话
			modult_16_List.add(new String[]{"area", "11","2"});//所在地区
			modult_16_List.add(new String[]{"companyTrade", "12","2"});//所属行业
			modult_16_List.add(new String[]{"defined1", "13","2"});
			modult_16_List.add(new String[]{"defined2", "14","2"});
			modult_16_List.add(new String[]{"defined3", "15","2"});
			modult_16_List.add(new String[]{"defined4", "16","2"});
			modult_16_List.add(new String[]{"defined5", "17","2"});
			modult_16_List.add(new String[]{"defined6", "18","2"});
			modult_16_List.add(new String[]{"defined7", "19","2"});
			modult_16_List.add(new String[]{"defined8", "20","2"});
			modult_16_List.add(new String[]{"defined9", "21","2"});
			modult_16_List.add(new String[]{"defined10", "22","2"});
			modult_16_List.add(new String[]{"defined11", "23","2"});
			modult_16_List.add(new String[]{"defined12", "24","2"});
			modult_16_List.add(new String[]{"defined13", "25","2"});
			modult_16_List.add(new String[]{"defined14", "26","2"});
			modult_16_List.add(new String[]{"defined15", "27","2"});
			modult_16_List.add(new String[]{"defined16", "28","2"});
			modult_16_List.add(new String[]{"defined17", "29","2"});
			modult_16_List.add(new String[]{"defined18", "30","2"});
		}
		
		// 放款管理模块 个人客户
		public static List<String[]> modult_17_List_0 = new ArrayList<String[]>();
		static {
			modult_17_List_0.add(new String[] { "leadCode", "1", "2" });	// 放款编号
			//modult_17_List_0.add(new String[] { "company", "2", "2" });	// 单位名称
			modult_17_List_0.add(new String[] { "custName", "3", "2" });	// 客户名称（客户姓名）
			modult_17_List_0.add(new String[] { "cardId", "4", "2" });	// 身份证号
			modult_17_List_0.add(new String[] { "phone", "5", "2" });	// 联系电话
			modult_17_List_0.add(new String[] { "status", "6", "2" });	// 放款状态(待放款、已放款、驳回)
			modult_17_List_0.add(new String[] { "borrowMoney", "7", "2" });	// 借款金额
			modult_17_List_0.add(new String[] { "accountMoney", "8", "2" });	// 用户到账金额
			modult_17_List_0.add(new String[] { "serviceMoney", "9", "2" });	// 综合服务费
			modult_17_List_0.add(new String[] { "serviceMoney2", "10", "2" });	// 服务费2
			modult_17_List_0.add(new String[] { "serviceMoney3", "11", "2" });	// 服务费3
			modult_17_List_0.add(new String[] { "billMoney", "12", "2" });		// 借据金额
			modult_17_List_0.add(new String[] { "borrowDate", "13", "2" });	// 借款日
			modult_17_List_0.add(new String[] { "repayDate", "14", "2" });	// 到期日
			modult_17_List_0.add(new String[] { "bankCard", "15", "2" });		// 银行卡号
			modult_17_List_0.add(new String[] { "lender", "16", "2" });		// 出借人
			modult_17_List_0.add(new String[] { "openingBank", "17", "2" });	// 开户行
			modult_17_List_0.add(new String[] { "loanBill", "18", "2" });		// 借款凭证
			modult_17_List_0.add(new String[] { "ownerAcc", "19", "2" });		// 创建人
			modult_17_List_0.add(new String[] { "importDeptName", "20", "2" });	// 团队名称
			modult_17_List_0.add(new String[] { "fundAccount", "21", "2" });		// 打款账户
			modult_17_List_0.add(new String[] { "inchargeAcc", "22", "2" });		// 负责人
			modult_17_List_0.add(new String[] { "batch", "23", "2" });			// 放贷批次
			modult_17_List_0.add(new String[] { "defined1", "24", "2" });
			modult_17_List_0.add(new String[] { "defined2", "25", "2" });
			modult_17_List_0.add(new String[] { "defined3", "26", "2" });
			modult_17_List_0.add(new String[] { "defined4", "27", "2" });
			modult_17_List_0.add(new String[] { "defined5", "28", "2" });
			modult_17_List_0.add(new String[] { "defined6", "29", "2" });
			modult_17_List_0.add(new String[] { "defined7", "30", "2" });
			modult_17_List_0.add(new String[] { "defined8", "31", "2" });
			modult_17_List_0.add(new String[] { "defined9", "32", "2" });
			modult_17_List_0.add(new String[] { "defined10", "33", "2" });
			modult_17_List_0.add(new String[] { "defined11", "34", "2" });
			modult_17_List_0.add(new String[] { "defined12", "35", "2" });
			modult_17_List_0.add(new String[] { "defined13", "36", "2" });
			modult_17_List_0.add(new String[] { "defined14", "37", "2" });
			modult_17_List_0.add(new String[] { "defined15", "38", "2" });
			modult_17_List_0.add(new String[] { "defined16", "39", "2" });
			modult_17_List_0.add(new String[] { "defined17", "40", "2" });
			modult_17_List_0.add(new String[] { "defined18", "41", "2" });
		}
		
		
		// 放款管理模块 企业客户
		public static List<String[]> modult_17_List_1 = new ArrayList<String[]>();
		static {
			modult_17_List_1.add(new String[] { "leadCode", "1", "2" });	// 放款编号
			modult_17_List_1.add(new String[] { "custName", "2", "2" });	// 客户名称（客户姓名）
			modult_17_List_1.add(new String[] { "cardId", "3", "2" });	// 身份证号
			modult_17_List_1.add(new String[] { "phone", "4", "2" });	// 联系电话
			modult_17_List_1.add(new String[] { "status", "5", "2" });	// 放款状态(待放款、已放款、驳回)
			modult_17_List_1.add(new String[] { "borrowMoney", "6", "2" });	// 借款金额
			modult_17_List_1.add(new String[] { "accountMoney", "7", "2" });	// 用户到账金额
			modult_17_List_1.add(new String[] { "serviceMoney", "8", "2" });	// 综合服务费
			modult_17_List_1.add(new String[] { "serviceMoney2", "9", "2" });	// 服务费2
			modult_17_List_1.add(new String[] { "serviceMoney3", "10", "2" });	// 服务费3
			modult_17_List_1.add(new String[] { "billMoney", "11", "2" });		// 借据金额
			modult_17_List_1.add(new String[] { "borrowDate", "12", "2" });	// 借款日
			modult_17_List_1.add(new String[] { "repayDate", "13", "2" });	// 到期日
			modult_17_List_1.add(new String[] { "bankCard", "14", "2" });		// 银行卡号
			modult_17_List_1.add(new String[] { "lender", "15", "2" });		// 出借人
			modult_17_List_1.add(new String[] { "openingBank", "16", "2" });	// 开户行
			modult_17_List_1.add(new String[] { "loanBill", "17", "2" });		// 借款凭证
			modult_17_List_1.add(new String[] { "ownerAcc", "18", "2" });		// 创建人
			modult_17_List_1.add(new String[] { "importDeptName", "19", "2" });	// 团队名称
			modult_17_List_1.add(new String[] { "fundAccount", "20", "2" });		// 打款账户
			modult_17_List_1.add(new String[] { "inchargeAcc", "21", "2" });		// 负责人
			modult_17_List_1.add(new String[] { "batch", "22", "2" });			// 放贷批次
			modult_17_List_1.add(new String[] { "defined1", "23", "2" });
			modult_17_List_1.add(new String[] { "defined2", "24", "2" });
			modult_17_List_1.add(new String[] { "defined3", "25", "2" });
			modult_17_List_1.add(new String[] { "defined4", "26", "2" });
			modult_17_List_1.add(new String[] { "defined5", "27", "2" });
			modult_17_List_1.add(new String[] { "defined6", "28", "2" });
			modult_17_List_1.add(new String[] { "defined7", "29", "2" });
			modult_17_List_1.add(new String[] { "defined8", "30", "2" });
			modult_17_List_1.add(new String[] { "defined9", "31", "2" });
			modult_17_List_1.add(new String[] { "defined10", "32", "2" });
			modult_17_List_1.add(new String[] { "defined11", "33", "2" });
			modult_17_List_1.add(new String[] { "defined12", "34", "2" });
			modult_17_List_1.add(new String[] { "defined13", "35", "2" });
			modult_17_List_1.add(new String[] { "defined14", "36", "2" });
			modult_17_List_1.add(new String[] { "defined15", "37", "2" });
			modult_17_List_1.add(new String[] { "defined16", "38", "2" });
			modult_17_List_1.add(new String[] { "defined17", "39", "2" });
			modult_17_List_1.add(new String[] { "defined18", "40", "2" });
		}
		
		
	// 放款管理模块
	public static List<String[]> modult_18_List = new ArrayList<String[]>();
	static {
		modult_18_List.add(new String[] { "leadCode", "1", "2" });	// 放款编号
		modult_18_List.add(new String[] { "company", "2","1"});//单位名称
		modult_18_List.add(new String[] { "custName", "3","2"});//客户姓名
		modult_18_List.add(new String[] { "cardId", "4", "2" });	// 身份证号
		modult_18_List.add(new String[] { "auditStatus", "5", "2" });	// 放款状态(待放款、已放款、驳回)
		
		modult_18_List.add(new String[] { "borrowMoney", "6", "2" });	// 借款金额
		modult_18_List.add(new String[] { "accountMoney", "7", "2" });	// 用户到账金额
		modult_18_List.add(new String[] { "serviceMoney", "8", "2" });	// 综合服务费
		modult_18_List.add(new String[] { "serviceMoney2", "9", "2" });	// 服务费2
		modult_18_List.add(new String[] { "serviceMoney3", "10", "2" });	// 服务费3
		modult_18_List.add(new String[] { "billMoney", "11", "2" });		// 借据金额
		modult_18_List.add(new String[] { "borrowDate", "12", "2" });	// 借款日
		modult_18_List.add(new String[] { "repayDate", "13", "2" });	// 到期日
		modult_18_List.add(new String[] { "loanBill", "14", "2" });		// 借款凭证
		modult_18_List.add(new String[] { "ownerAcc", "15", "2" });		// 提交人
		modult_18_List.add(new String[] { "submitTime", "16", "2" });	// 提交时间
		modult_18_List.add(new String[] { "auditTime", "17", "2" });	// 审核时间
		
		modult_18_List.add(new String[] { "importDeptName", "18", "2" });	// 团队名称
		modult_18_List.add(new String[] { "fundAccount", "19", "2" });		// 打款账户
		modult_18_List.add(new String[] { "batch", "20", "2" });			// 放贷批次
		
		modult_18_List.add(new String[] { "phone", "21", "2" });	// 联系电话
		modult_18_List.add(new String[] { "bankCard", "22", "2" });		// 银行卡号
		modult_18_List.add(new String[] { "openingBank", "23", "2" });	// 开户行
		modult_18_List.add(new String[] { "lender", "24", "2" });		// 出借人
		
		modult_18_List.add(new String[] { "defined1", "25", "2" });
		modult_18_List.add(new String[] { "defined2", "26", "2" });
		modult_18_List.add(new String[] { "defined3", "27", "2" });
		modult_18_List.add(new String[] { "defined4", "28", "2" });
		modult_18_List.add(new String[] { "defined5", "29", "2" });
		modult_18_List.add(new String[] { "defined6", "30", "2" });
		modult_18_List.add(new String[] { "defined7", "31", "2" });
		modult_18_List.add(new String[] { "defined8", "32", "2" });
		modult_18_List.add(new String[] { "defined9", "33", "2" });
		modult_18_List.add(new String[] { "defined10", "34", "2" });
		modult_18_List.add(new String[] { "defined11", "35", "2" });
		modult_18_List.add(new String[] { "defined12", "36", "2" });
		modult_18_List.add(new String[] { "defined13", "37", "2" });
		modult_18_List.add(new String[] { "defined14", "38", "2" });
		modult_18_List.add(new String[] { "defined15", "39", "2" });
		modult_18_List.add(new String[] { "defined16", "40", "2" });
		modult_18_List.add(new String[] { "defined17", "41", "2" });
		modult_18_List.add(new String[] { "defined18", "42", "2" });
	}
	
	// 放款管理模块
		public static List<String[]> modult_20_List = new ArrayList<String[]>();
		static {
			modult_20_List.add(new String[] { "leadCode", "1", "2" });	// 放款编号
			modult_20_List.add(new String[] { "custName", "2","1"});//客户名称
			modult_20_List.add(new String[] { "cardId", "3", "2" });	// 身份证号
			modult_20_List.add(new String[] { "auditStatus", "4", "2" });	// 放款状态(待放款、已放款、驳回)
			
			modult_20_List.add(new String[] { "borrowMoney", "5", "2" });	// 借款金额
			modult_20_List.add(new String[] { "accountMoney", "6", "2" });	// 用户到账金额
			modult_20_List.add(new String[] { "serviceMoney", "7", "2" });	// 综合服务费
			modult_20_List.add(new String[] { "serviceMoney2", "8", "2" });	// 服务费2
			modult_20_List.add(new String[] { "serviceMoney3", "9", "2" });	// 服务费3
			modult_20_List.add(new String[] { "billMoney", "10", "2" });		// 借据金额
			modult_20_List.add(new String[] { "borrowDate", "11", "2" });	// 借款日
			modult_20_List.add(new String[] { "repayDate", "12", "2" });	// 到期日
			modult_20_List.add(new String[] { "loanBill", "13", "2" });		// 借款凭证
			modult_20_List.add(new String[] { "ownerAcc", "14", "2" });		// 提交人
			modult_20_List.add(new String[] { "submitTime", "15", "2" });	// 提交时间
			modult_20_List.add(new String[] { "auditTime", "16", "2" });	// 审核时间
			
			modult_20_List.add(new String[] { "importDeptName", "17", "2" });	// 团队名称
			modult_20_List.add(new String[] { "fundAccount", "18", "2" });		// 打款账户
			modult_20_List.add(new String[] { "batch", "19", "2" });			// 放贷批次
			
			modult_20_List.add(new String[] { "phone", "20", "2" });	// 联系电话
			modult_20_List.add(new String[] { "bankCard", "21", "2" });		// 银行卡号
			modult_20_List.add(new String[] { "openingBank", "22", "2" });	// 开户行
			modult_20_List.add(new String[] { "lender", "23", "2" });		// 出借人
			
			modult_20_List.add(new String[] { "defined1", "24", "2" });
			modult_20_List.add(new String[] { "defined2", "25", "2" });
			modult_20_List.add(new String[] { "defined3", "26", "2" });
			modult_20_List.add(new String[] { "defined4", "27", "2" });
			modult_20_List.add(new String[] { "defined5", "28", "2" });
			modult_20_List.add(new String[] { "defined6", "29", "2" });
			modult_20_List.add(new String[] { "defined7", "30", "2" });
			modult_20_List.add(new String[] { "defined8", "31", "2" });
			modult_20_List.add(new String[] { "defined9", "32", "2" });
			modult_20_List.add(new String[] { "defined10", "33", "2" });
			modult_20_List.add(new String[] { "defined11", "34", "2" });
			modult_20_List.add(new String[] { "defined12", "35", "2" });
			modult_20_List.add(new String[] { "defined13", "36", "2" });
			modult_20_List.add(new String[] { "defined14", "37", "2" });
			modult_20_List.add(new String[] { "defined15", "38", "2" });
			modult_20_List.add(new String[] { "defined16", "39", "2" });
			modult_20_List.add(new String[] { "defined17", "40", "2" });
			modult_20_List.add(new String[] { "defined18", "41", "2" });
		}
	
	
	
	// 统计金额
		public static List<String[]> modult_19_List = new ArrayList<String[]>();
		static {
			modult_19_List.add(new String[] { "borrowMoney", "1", "2" });
			modult_19_List.add(new String[] { "accountMoney", "2", "2" });
			modult_19_List.add(new String[] { "serviceMoney", "3", "2" });
			modult_19_List.add(new String[] { "serviceMoney2", "4", "2" });
			modult_19_List.add(new String[] { "serviceMoney3", "5", "2" });
			modult_19_List.add(new String[] { "billMoney", "6", "2" });
			modult_19_List.add(new String[] { "defined1", "7", "2" });
			modult_19_List.add(new String[] { "defined2", "8", "2" });
			modult_19_List.add(new String[] { "defined3", "9", "2" });
			modult_19_List.add(new String[] { "defined4", "10", "2" });
			modult_19_List.add(new String[] { "defined5", "11", "2" });
			modult_19_List.add(new String[] { "defined6", "12", "2" });
			modult_19_List.add(new String[] { "defined7", "13", "2" });
			modult_19_List.add(new String[] { "defined8", "14", "2" });
			modult_19_List.add(new String[] { "defined9", "15", "2" });
			modult_19_List.add(new String[] { "defined10", "16", "2" });
			modult_19_List.add(new String[] { "defined11", "17", "2" });
			modult_19_List.add(new String[] { "defined12", "18", "2" });
			modult_19_List.add(new String[] { "defined13", "19", "2" });
			modult_19_List.add(new String[] { "defined14", "20", "2" });
			modult_19_List.add(new String[] { "defined15", "21", "2" });
		}
		
}
