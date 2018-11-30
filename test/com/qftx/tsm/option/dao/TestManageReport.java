package com.qftx.tsm.option.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.auth.service.OrgService;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.service.DataDictionaryService;

public class TestManageReport extends BaseUtest{
	
	@Autowired
	private DataDictionaryService dataDictionaryService;
	@Autowired
	private OrgService orgService;
	/**
	 * 获取字典value
	 * 
	 * @create 
	 * @history
	 */
	@Test
	public void setDicTionnary() {
         System.out.println("开始----");
         // 查询所有机构ID
		List<String>orgIds = new ArrayList<String>();
		orgIds = orgService.getAllOrgIdsByProductType("6001");
		if(orgIds != null && orgIds.size() >0){
			for(int i = 0; i<orgIds.size(); i++){
			   Map<String, String> param = new HashMap<String, String>();
			   param.put("orgId", orgIds.get(i));
			   param.put("code", AppConstant.DATA15);
			   if(!"8decbe1278b646b5a462bbd4bc80bd58".equals(orgIds.get(i))){
				   Integer value =dataDictionaryService.getDataValueByOrgIdAndCode(param);
			       if(value != null){
			    	   saveBatchDataDictionnary(orgIds.get(i));
			           //updateDataDictionnary(orgIds.get(i));
				   }      
			   } 
			}
		}
        saveBatchDataDictionnary(null);
        System.out.println("结束----");
	}
	
	/** 
	 * 批量保存字典
	 * @create  2015年11月17日 下午1:42:01 wuwei
	 * @history  
	 */
	private void saveBatchDataDictionnary(String orgId) {
         System.out.println("批量保存字典内容开始----");
         List<DataDictionaryBean>list = new ArrayList<DataDictionaryBean>();
         DataDictionaryBean dataDictionaryBean = new DataDictionaryBean();
         dataDictionaryBean.setOrgId(orgId);
         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
         dataDictionaryBean.setDictionaryName("经营报告设置");
         dataDictionaryBean.setDictionaryValue("1");
         dataDictionaryBean.setDictionaryValueNotes("1：当日18:00;2：当日20:00;3：当日22:00");
         dataDictionaryBean.setIsDel(new Short("0"));
         dataDictionaryBean.setInputdate(new Date());
         dataDictionaryBean.setInputerAcc("admin");
         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50059);
         dataDictionaryBean.setIsOpen("0");
         list.add(dataDictionaryBean);
         
         dataDictionaryBean = new DataDictionaryBean();
         dataDictionaryBean.setOrgId(orgId);
         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
         dataDictionaryBean.setDictionaryName("次日第二次8：00推送完整数据开关");
         dataDictionaryBean.setDictionaryValue("0");
         dataDictionaryBean.setDictionaryValueNotes("");
         dataDictionaryBean.setIsDel(new Short("0"));
         dataDictionaryBean.setInputdate(new Date());
         dataDictionaryBean.setInputerAcc("admin");
         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50060);
         dataDictionaryBean.setIsOpen("0");
         list.add(dataDictionaryBean);
         
         dataDictionaryBean = new DataDictionaryBean();
         dataDictionaryBean.setOrgId(orgId);
         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
         dataDictionaryBean.setDictionaryName("经营报告推送对象");
         dataDictionaryBean.setDictionaryValue("");
         dataDictionaryBean.setDictionaryValueNotes("推送对象账号，账号以英文逗号隔开");
         dataDictionaryBean.setIsDel(new Short("0"));
         dataDictionaryBean.setInputdate(new Date());
         dataDictionaryBean.setInputerAcc("admin");
         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50061);
         dataDictionaryBean.setIsOpen("0");
         list.add(dataDictionaryBean);
        
         dataDictionaryService.createBatch(list);
         System.out.println("批量返回值："+dataDictionaryBean.getDictionaryId());
         //CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.DATADICTIONARY);
		// CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.OPEN_MSG_MAP);
         
         System.out.println("批量查保存字典内容结束----");
	}
}
