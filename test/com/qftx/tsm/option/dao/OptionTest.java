package com.qftx.tsm.option.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.service.OrgService;
import com.qftx.base.shiro.ShiroUser;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.common.BaseUtest;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.service.DataDictionaryService;
import com.qftx.tsm.option.service.OptionService;

public class OptionTest extends BaseUtest {
	
	Logger logger = Logger.getLogger(OptionTest.class);
	@Autowired
	private OptionService optionService;
	@Autowired
	private OrgService orgService;
	
	/**<option value="">-请选择-</option>
	<option value="1">信息传输、软件和信息技术服务业</option>
	<option value="2">采矿业</option>
	<option value="3">制造业</option>
	<option value="4">电力、热力、燃气及水生产和供应业</option>
	<option value="5">建筑业</option>
	<option value="6">批发和零售业</option>
	<option value="7">交通运输、仓储和邮政业</option>
	<option value="8">住宿和餐饮业</option>
	<option value="9">农、林、牧、渔业</option>
	<option value="10">金融业</option>
	<option value="11">房地产业</option>
	<option value="12">租赁和商务服务业</option>
	<option value="13">科学研究和技术服务业</option>
	<option value="14">水利、环境和公共设施管理业</option>
	<option value="15">居民服务、修理和其他服务业</option>
	<option value="16">教育</option>
	<option value="17">卫生和社会工作</option>
	<option value="18">文化、体育和娱乐业</option>
	<option value="19">公共管理、社会保障和社会组织</option>
	<option value="20">国际组织</option>
	<option value="21">其他</option>*/
	
	@Test
	public void main(){
	    System.out.println("开始----");
        // 查询所有机构ID
		List<Org>orgs = new ArrayList<Org>();
		orgs = orgService.getAllOrgIdsByProductType1("6001");
		if(orgs != null && orgs.size() >0){
			for(Org org : orgs){
				if(org.getState() !=null && org.getState() == 1){
					execute(org.getOrgId());
				}
			}
		}
		
		//execute(null);
	}
	

	public void execute(String orgId){
		List<OptionBean>options = new ArrayList<OptionBean>();
		OptionBean optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("信息传输、软件和信息技术服务业");
		optionBean.setOptionValue("1");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)1);
		optionBean.setSort(1);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("采矿业");
		optionBean.setOptionValue("2");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(2);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("制造业");
		optionBean.setOptionValue("3");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(3);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("电力、热力、燃气及水生产和供应业");
		optionBean.setOptionValue("4");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(4);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("建筑业");
		optionBean.setOptionValue("5");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(5);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("批发和零售业");
		optionBean.setOptionValue("6");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(6);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("交通运输、仓储和邮政业");
		optionBean.setOptionValue("7");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(7);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("住宿和餐饮业");
		optionBean.setOptionValue("8");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(8);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("农、林、牧、渔业");
		optionBean.setOptionValue("9");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(9);
		options.add(optionBean);
		 optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("金融业");
		optionBean.setOptionValue("10");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(10);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("房地产业");
		optionBean.setOptionValue("11");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(11);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("租赁和商务服务业");
		optionBean.setOptionValue("12");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(12);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("科学研究和技术服务业");
		optionBean.setOptionValue("13");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(13);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("水利、环境和公共设施管理业");
		optionBean.setOptionValue("14");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(14);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("居民服务、修理和其他服务业");
		optionBean.setOptionValue("15");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(15);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("教育");
		optionBean.setOptionValue("16");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(16);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("卫生和社会工作");
		optionBean.setOptionValue("17");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(17);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("文化、体育和娱乐业");
		optionBean.setOptionValue("18");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(18);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("公共管理、社会保障和社会组织");
		optionBean.setOptionValue("19");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(19);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("国际组织");
		optionBean.setOptionValue("20");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(20);
		options.add(optionBean);
		optionBean = new OptionBean();
		optionBean.setOptionlistId(SysBaseModelUtil.getModelId());
		optionBean.setOrgId(orgId);
		optionBean.setOptionName("其他");
		optionBean.setOptionValue("21");
		optionBean.setItemCode("companyTrade");
		optionBean.setInputdate(new Date());
		optionBean.setInputerAcc("admin");
		optionBean.setIsDefault((short)0);
		optionBean.setSort(21);
		options.add(optionBean);
		
		if(options != null && options.size() >0){
			optionService.createBatch(options);
		}
	}
	
	
}
