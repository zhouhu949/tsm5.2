package com.qftx.tsm.option.dao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.auth.service.OrgService;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.util.CodeUtils;
import com.qftx.common.util.IContants;
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
public class TestDataDictionnary51 extends BaseUtest {
	@Autowired
	private DataDictionaryService dataDictionaryService;
	@Autowired
	private OrgService orgService;
	/**
	 * 获取字典value
	 * 
	 * @create 2015年11月17日 下午1:26:10 wuwei
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
         dataDictionaryBean.setDictionaryName("弹幕设置开关");
         dataDictionaryBean.setDictionaryValue("1");
         dataDictionaryBean.setDictionaryValueNotes("弹幕设置开关 dictionary_value 0：关闭，1：开启");
         dataDictionaryBean.setIsDel(new Short("0"));
         dataDictionaryBean.setInputdate(new Date());
         dataDictionaryBean.setInputerAcc("admin");
         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50033);
         list.add(dataDictionaryBean);
        
         dataDictionaryService.createBatch(list);
         System.out.println("批量返回值："+dataDictionaryBean.getDictionaryId());
         //CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.DATADICTIONARY);
		// CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.OPEN_MSG_MAP);
         
         System.out.println("批量查保存字典内容结束----");
	}
	
	/** 
	 * 修改字典
	 * @create  2015年11月17日 下午1:42:01 wuwei
	 * @history  
	 */
	private void updateDataDictionnary(String orgId) {
         System.out.println("修改字典内容开始----");
         DataDictionaryBean bean = new DataDictionaryBean();
         bean.setOrgId(orgId);
         List<DataDictionaryBean> list = dataDictionaryService.getListByCondtion(bean);
         for(DataDictionaryBean ddb : list){
        	 if(AppConstant.DATA20.equals(ddb.getDictionaryCode())){
        		 ddb.setIsOpen("1");
        		 dataDictionaryService.modifyTrends(ddb);
        	 }
         }
         System.out.println("修改字典内容结束----");
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException, Exception {
			String msgMain = "eyJtZXNzYWdlaW5mbyI6eyJtc2ciOiI1Ynk1NWJtVlVWRT0iLCJ3YXYiOiIxIiwidGl0bGUiOiI1Ynk1NWJtVjc3eUIifX0=";
			String msgXml = new String(CodeUtils.base64Decode(msgMain), IContants.CHAR_ENCODING);		
			System.out.println(msgXml);
	}
}
