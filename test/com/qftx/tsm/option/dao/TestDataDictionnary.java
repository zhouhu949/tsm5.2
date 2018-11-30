package com.qftx.tsm.option.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.service.DataDictionaryService;

/**
 * 字典测试类
 * 
 * @author: wuwei
 * @since: 2015年11月17日 下午1:25:32
 * @history:
 */
public class TestDataDictionnary extends BaseUtest {
	@Autowired
	private DataDictionaryService dataDictionaryService;

	/**
	 * 获取字典value
	 * 
	 * @create 2015年11月17日 下午1:26:10 wuwei
	 * @history
	 */
	@Ignore
	@Test
	public void getDataValueDictionnary() {
         System.out.println("查询字典值开始----");
         Map<String, String> param = new HashMap<String, String>();
         param.put("orgId", ShiroUtil.getUser().getOrgId());
         param.put("code", AppConstant.DATA15);
         int value =dataDictionaryService.getDataValueByOrgIdAndCode(param);
         System.out.println("返回值："+value);
         System.out.println("查询字典值结束----");
	}
	
	/**
	 * 获取字典isopen
	 * 
	 * @create 2015年11月17日 下午1:26:10 wuwei
	 * @history
	 */
	@Ignore
	@Test
	public void getDataDictionaryBean() {
         System.out.println("查询字典开启开始----");
         Map<String, String> param = new HashMap<String, String>();
         param.put("orgId", ShiroUtil.getUser().getOrgId());
         param.put("code", AppConstant.DATA15);
         DataDictionaryBean bean =dataDictionaryService.getDataDictionaryBean(param);
         System.out.println("返回值："+JsonUtil.getJsonString(bean));
         System.out.println("查询字典开启结束----");
	}	
	/** 
	 * 保存字典
	 * @create  2015年11月17日 下午1:42:01 wuwei
	 * @history  
	 */
	@Ignore
	@Test
	public void saveDataDictionnary() {
         System.out.println("保存字典内容开始----");
         DataDictionaryBean dataDictionaryBean = new DataDictionaryBean();
         dataDictionaryBean.setOrgId(ShiroUtil.getUser().getOrgId());
         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
         dataDictionaryBean.setDictionaryName("客户跟进设置开关");
         dataDictionaryBean.setDictionaryValue("1");
         dataDictionaryBean.setDictionaryValueNotes("0关闭1开启");
         dataDictionaryBean.setIsDel(new Short("0"));
         dataDictionaryBean.setDictionaryCode(AppConstant.DATA15);
         dataDictionaryService.create(dataDictionaryBean);
         System.out.println("返回值："+dataDictionaryBean.getDictionaryId());
         System.out.println("查保存字典内容结束----");
	}
	
//	@Test
	public void deleteDataDictionnary() {
         System.out.println("删除字典内容开始----");
         List<String>ids = new ArrayList<String>();
         ids.add("b91fb7e0253f4d08b2cd1d18e2f0532a");
         ids.add("34d51946d02f43c5887cfa2c2c3f2a8b");
         Map<String,Object>map = new HashMap<String, Object>();
         map.put("orgId","8decbe1278b646b5a462bbd4bc80bd58");
         map.put("modifierAcc","测试");
         map.put("isDel",0);
         map.put("ids", ids);
         dataDictionaryService.removeFakeBatch(map);
         System.out.println("删除字典内容结束----");
	}
	
	/** 
	 * 修改字典
	 * @create  2015年11月17日 下午1:42:01 wuwei
	 * @history  
	 */
	@Ignore
	@Test
	public void updateDataDictionnary() {
         System.out.println("修改字典内容开始----");
         DataDictionaryBean dataDictionaryBean = new DataDictionaryBean();
         dataDictionaryBean.setOrgId(ShiroUtil.getUser().getOrgId());
         dataDictionaryBean.setDictionaryId("b91fb7e0253f4d08b2cd1d18e2f0532a");
         dataDictionaryBean.setDictionaryName("个人资源上限的限制设置");
         dataDictionaryBean.setDictionaryValue("500");
         dataDictionaryBean.setDictionaryValueNotes("");
         dataDictionaryBean.setIsDel(new Short("0"));
         dataDictionaryBean.setIsOpen("1");
         dataDictionaryBean.setDictionaryCode(AppConstant.DATA28);
         dataDictionaryService.modifyTrends(dataDictionaryBean);
         System.out.println("返回值："+dataDictionaryBean.getDictionaryId());
         System.out.println("修改字典内容结束----");
	}
	
	
}
