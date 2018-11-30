package com.qftx.tsm.plan;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomFieldUtils {
	@Autowired
	private  CachedService  cachedService;
	
	public  Map<String, String> getCustomFiled(ShiroUser user){
		List<CustFieldSet> fieldSets = null;
		if (user.getIsState() == 1) {// 企业资源
			fieldSets = cachedService.getComFiledSet(user.getOrgId());
		}else{
			fieldSets = cachedService.getPersonFiledSet(user.getOrgId());
		}
		Map<String,String> filedMap = new HashMap<String, String>();
		for(CustFieldSet filed : fieldSets){
			filedMap.put(filed.getFieldCode(), filed.getFieldName());
		}
		return filedMap;
	}
	 
	public Integer getIsReplaceWord(ShiroUser user) {
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA24, user.getOrgId());
		Integer idReplaceWord = 0;
		if (list.size() > 0) {
			idReplaceWord = new Integer(list.get(0).getDictionaryValue());
			if(idReplaceWord==1&&cachedService.judgeHideWhiteList(user.getOrgId(),user.getAccount())){
				idReplaceWord = 0;
			}
		}
		return idReplaceWord;
	}
	/*自动审核时间*/
	public Date getAutoAuthTime(String orgId) {
		List<DataDictionaryBean> data = cachedService.getDirList(AppConstant.DATA_40031,orgId);
		String day="1";
		if(data!=null && data.size()>0){
			day = data.get(0).getDictionaryValue();
			if("2".equals(day)){
				day = "5";
			}else if("3".equals(day)){
				day = "10";
			}
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DATE, Integer.parseInt(day));
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND, 0);
		
		return calendar.getTime();
	}
	
	/*自动审核日期*/
	public String getAutoAuthDay(String orgId) {
		List<DataDictionaryBean> data = cachedService.getDirList(AppConstant.DATA_40031,orgId);
		String day="1";
		if(data!=null && data.size()>0){
			day = data.get(0).getDictionaryValue();
			if("2".equals(day)){
				day = "5";
			}else if("3".equals(day)){
				day = "10";
			}
		}
		return day;
	}
	
}
