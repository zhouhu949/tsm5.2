package com.qftx.tsm.imp.util;

import com.qftx.common.util.constants.AppConstant;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/***
 * 字段匹配
 * @author zwj
 */
public class MatchFiledUtil {

	/*** 如果后期还有匹配字段添加，请添加至前面，每个数组后面值保证为：AppConstant.*  */

	// 个人客户字段
	private static String[] name = {"名字","联系人","姓名","名称",AppConstant.name+"_0"};
	private static String[] sex = {"男","女","性别",AppConstant.sex+"_0"};
	private static String[] birthday = {"生日",AppConstant.birthday+"_0"};
	private static String[] telphone = {"备用电话","备用号码","备用固话","固话号码",AppConstant.telphone+"_0"};
	private static String[] mobilephone = {"常用电话","手机号码","电话号码","常用号码",AppConstant.mobilephone+"_0"};
	private static String[] fax = {"传真",AppConstant.fax+"_0"};
	private static String[] company = {"单位名","企业名",AppConstant.company+"_0"};
	private static String[] keyWord = {"关键字",AppConstant.keyWord+"_0"};
	private static String[] email = {"邮箱","email",AppConstant.email+"_0"};
	private static String[] qq = {"qq","im","微信",AppConstant.qq+"_0"};
	private static String[] wangwang = {"旺旺",AppConstant.ww+"_0"};
	private static String[] unithome = {"单位主页","单位网页",AppConstant.unithome+"_0"};
	private static String[] companyOrg = {"部门",AppConstant.companyOrg+"_0"};
	private static String[] duty = {"职务","职责",AppConstant.duty+"_0"};
	private static String[] companyAddr = {"地址","住址",AppConstant.companyAddr+"_0"};
	private static String[] remark = {"备注","其他",AppConstant.remark+"_0"};
	// private static String[] area = {"所在地区",AppConstant.area}; 数据库中存储值为 省ID,市ID,区ID

	// 企业客户字段
	private static String[] com_name = {"名称",AppConstant.com_name+"_1"};
	private static String[] com_capital = {"注册资本",AppConstant.com_capital+"_1"};
	private static String[] com_trade = {"所属行业",AppConstant.com_trade+"_1"};
	private static String[] com_user = {"法人代表",AppConstant.com_user+"_1"};
	private static String[] com_fax = {"公司传真",AppConstant.com_fax+"_1"};
	private static String[] com_keyWord = {"关键字",AppConstant.com_keyWord+"_1"};
	private static String[] com_unithome = {"单位主页",AppConstant.com_unithome+"_1"};
	// private static String[] com_area = {"所在地区",AppConstant.com_area}; 数据库中存储值为 省ID,市ID,区ID
	private static String[] com_address = {"联系地址",AppConstant.com_address+"_1"};
	private static String[] com_scope = {"经营范围",AppConstant.com_scope+"_1"};
	private static String[] com_remark = {"备注","其他",AppConstant.com_remark+"_1"};

	// 联系人字段
	private static String[] contacts_name = {"名字","姓名","联系人",AppConstant.contacts_name+"_2"};
	private static String[] contacts_telphone = {"常用电话","手机号码","电话号码","常用号码",AppConstant.contacts_telphone+"_2"};
	private static String[] contacts_telphonebak = {"备用电话","备用号码","备用固话","固话号码",AppConstant.contacts_telphonebak+"_2"};
	private static String[] contacts_sex = {"男","女","性别",AppConstant.contacts_sex+"_2"};
	private static String[] contacts_birthday = {"生日",AppConstant.contacts_birthday+"_2"};
	private static String[] contacts_email = {"邮箱","email",AppConstant.contacts_email+"_2"};
	private static String[] contacts_fax = {"传真",AppConstant.contacts_fax+"_2"};
	private static String[] contacts_qq = {"qq","im","微信",AppConstant.contacts_qq+"_2"};
	private static String[] contacts_ww = {"旺旺",AppConstant.contacts_ww+"_2"};
	private static String[] contacts_work = {"职务","职责",AppConstant.contacts_work+"_2"};
	private static String[] contacts_groupname = {"部门",AppConstant.contacts_groupname+"_2"};

	/** 如果匹配成功，返回数组中最后一个字符串 */
	public static String getFiledKey(String str,Integer isState) throws Exception{
		if(StringUtils.isNotBlank(str)){
			Iterator<String[]> it1 = null;
			if(isState == 1){ // 企业客户
				it1 = getComFiledList().iterator();
			}else{
				it1 = getFiledList().iterator();
			}
			while(it1.hasNext()){
				String[] strs = it1.next();
				for(String str1 : strs){
					if(str.toLowerCase().indexOf(str1)!= -1){
						return strs[strs.length - 1];
					}
				}
			}
		}
		return "";
	}

	// 个人客户 返回一个List
	private static List<String[]> getFiledList(){
		List<String[]> list = new ArrayList<String[]>();
		list.add(0,company);
		list.add(1,sex);
		list.add(2,birthday);
		list.add(3,telphone);
		list.add(4,mobilephone);
		list.add(5,fax);
		list.add(6,name);
		list.add(7,keyWord);
		list.add(8,email);
		list.add(9,qq);
		list.add(10,unithome);
		list.add(11,companyOrg);
		list.add(12,duty);
		list.add(13,companyAddr);
		list.add(14,remark);
		list.add(15,wangwang);
		return list;
	}

	// 企业客户&联系人 返回一个List
	private static List<String[]> getComFiledList(){
		List<String[]> list = new ArrayList<String[]>();
		list.add(0,com_name);
		list.add(1,com_capital);
		list.add(2,com_trade);
		list.add(3,com_user);
		list.add(4,com_fax);
		list.add(5,com_keyWord);
		list.add(6,com_unithome);
		list.add(7,com_address);
		list.add(8,com_scope);
		list.add(9,com_remark);
		list.add(10,contacts_name);
		list.add(11,contacts_telphone);
		list.add(12,contacts_telphonebak);
		list.add(13,contacts_sex);
		list.add(14,contacts_birthday);
		list.add(15,contacts_email);
		list.add(16,contacts_fax);
		list.add(17,contacts_qq);
		list.add(18,contacts_ww);
		list.add(19,contacts_work);
		list.add(20,contacts_groupname);
		return list;
	}

}
