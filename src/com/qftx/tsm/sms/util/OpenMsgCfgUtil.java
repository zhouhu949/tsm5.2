package com.qftx.tsm.sms.util;

import com.qftx.common.cached.CachedService;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.service.DataDictionaryService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/** 获取消息设置 数据 */
public class OpenMsgCfgUtil {
	protected static Log logger = LogFactory.getLog(OpenMsgCfgUtil.class);
	public static Map<String, DataDictionaryBean> getOpenMsgCfgMap(CachedService cachedService,DataDictionaryService dicService, String orgid, boolean update) {
		try{
			// 获取消息设置 缓存
			Object obj = cachedService.getMsgDictionary(orgid);
			if (!update && obj != null) {
				return (Map<String, DataDictionaryBean>)obj;
			}
			Map<String, String> param = new HashMap<String, String>();
			if (orgid != null) {
				param.put("orgId", orgid);
			}
			List<DataDictionaryBean> list = dicService.findOpenMsgArgs(param);
			if (list != null && list.size() > 0) {
				Map<String, Map<String, DataDictionaryBean>> map = new HashMap<String, Map<String, DataDictionaryBean>>();
				Map<String, DataDictionaryBean> cfgMap = null;
				for (DataDictionaryBean dic : list) {
					if (map.containsKey(dic.getOrgId())) {
						map.get(dic.getOrgId()).put(dic.getDictionaryCode(), dic);
					} else {
						cfgMap = new HashMap<String, DataDictionaryBean>();
						cfgMap.put(dic.getDictionaryCode(), dic);
						map.put(dic.getOrgId(), cfgMap);
					}
				}
				Iterator it = map.keySet().iterator();
				String orgId = null;
				while (it.hasNext()) {
					orgId = (String)it.next();
					cfgMap = map.get(orgId);
					if (cfgMap.containsKey(AppConstant.DATA_20001)
							&& "0".equals(cfgMap.get(AppConstant.DATA_20001).getDictionaryValue())) { // 客户跟进-关闭
						cfgMap.remove(AppConstant.DATA_20001);
						cfgMap.remove(AppConstant.DATA_20011);
					} 
					if (cfgMap.containsKey(AppConstant.DATA_20002)
							&& "0".equals(cfgMap.get(AppConstant.DATA_20002).getDictionaryValue())) { // 延后呼叫-关闭
						cfgMap.remove(AppConstant.DATA_20002);
						cfgMap.remove(AppConstant.DATA_20012);
					} 
					if (cfgMap.containsKey(AppConstant.DATA_20003)
							&& "0".equals(cfgMap.get(AppConstant.DATA_20003).getDictionaryValue())) { // 跟进警报-关闭
						cfgMap.remove(AppConstant.DATA_20003);
						cfgMap.remove(AppConstant.DATA_20013); 
					}  
					if (cfgMap.containsKey(AppConstant.DATA_20016)
							&& "0".equals(cfgMap.get(AppConstant.DATA_20016).getIsOpen())) { // 客户回访-关闭
						cfgMap.remove(AppConstant.DATA_20016);
					}
					if (cfgMap.containsKey(AppConstant.DATA_20005)
							&& "0".equals(cfgMap.get(AppConstant.DATA_20005).getIsOpen())) { // 短信剩余量提醒阀值-关闭
						cfgMap.remove(AppConstant.DATA_20005);
					}  
					if (cfgMap.containsKey(AppConstant.DATA_20006)
							&& "0".equals(cfgMap.get(AppConstant.DATA_20006).getIsOpen())) { //坐席剩余时间提醒阀值-关闭
						cfgMap.remove(AppConstant.DATA_20006);
					}  
					if (cfgMap.containsKey(AppConstant.DATA_20008)
							&& "0".equals(cfgMap.get(AppConstant.DATA_20008).getIsOpen())) { // 订单剩余时间提醒阀值-关闭
						cfgMap.remove(AppConstant.DATA_20008);
					}  
					if (cfgMap.containsKey(AppConstant.DATA_20014)
							&& "0".equals(cfgMap.get(AppConstant.DATA_20014).getIsOpen())) { // 意向客户放弃提前##天，发送提醒-关闭
						cfgMap.remove(AppConstant.DATA_20014);
					}   
					if (cfgMap.containsKey(AppConstant.DATA_20028)
							&& "0".equals(cfgMap.get(AppConstant.DATA_20028).getIsOpen())) { // 未接来电提醒阀值-关闭
						cfgMap.remove(AppConstant.DATA_20028);
					}  
					if (cfgMap.containsKey(AppConstant.DATA_20051)
							&& "0".equals(cfgMap.get(AppConstant.DATA_20051).getIsOpen())) { // 资源放弃提前-关闭
						cfgMap.remove(AppConstant.DATA_20051);
					}
					if (cfgMap.containsKey(AppConstant.DATA_20052)
							&& "0".equals(cfgMap.get(AppConstant.DATA_20052).getIsOpen())) { // 签约客户放弃提前-关闭
						cfgMap.remove(AppConstant.DATA_20052);
					}
					cachedService.setMsgDictionary(orgId, cfgMap);
					return cfgMap;
				}
			}
		}catch(Exception e){
			logger.error("获取消息设置 数据 异常",e);
		}		
		return null;
	}
}
