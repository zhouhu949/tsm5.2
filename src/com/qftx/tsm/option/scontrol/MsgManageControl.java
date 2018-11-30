package com.qftx.tsm.option.scontrol;

import com.qftx.base.auth.bean.AuthOrgMessageBean;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.ExecutorUtils;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.dto.DataDictionaryModel;
import com.qftx.tsm.option.service.DataDictionaryService;
import com.qftx.tsm.sms.util.OpenMsgCfgUtil;
import com.qftx.tsm.sys.service.HookSmsConfigService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/***
 * 消息设置
 * @author: zwj
 * @since: 2015-12-4  上午9:59:25
 * @history: 4.x
 */
@Controller
@RequestMapping("/msg/manage")
public class MsgManageControl {
	@Autowired
	private DataDictionaryService dataDictionaryService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private HookSmsConfigService hookSmsConfigService;	
	Logger logger=Logger.getLogger(MsgManageControl.class);
	
	/***************************【慧营销4.0 开始】******************************************/
	
	/** 
	 * 消息设置【页面】
	 */
	@RequestMapping("/msgmanage")
	public String salesmanagePage(HttpServletRequest request){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			DataDictionaryBean dictionary = new DataDictionaryBean();
			dictionary.setOrgId(shiroUser.getOrgId());
			
			List<DataDictionaryBean>dictionaryList = dataDictionaryService.getListByCondtion(dictionary);
			Map<String, DataDictionaryBean> dictionaryMap = new HashMap<String, DataDictionaryBean>();
			for (DataDictionaryBean obj : dictionaryList) {
				if(null != obj.getDictionaryCode()){
					dictionaryMap.put(obj.getDictionaryCode(), obj);
				}
			}
			dictionaryList = new ArrayList<DataDictionaryBean>();
			// 客户跟进提醒
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20001));//下标[0]
			// 延后呼叫提醒
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20002));//下标[1]
			 // 跟进警报提醒
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20003));//下标[2]	
			 // 月计划提前时间
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40015));//下标[3]	
			// 短信剩余量提醒阀值
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20005));//下标[4]
			 // 坐席剩余时间提醒阀值
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20006));//下标[5]
			// 通信时长剩余提醒阀值
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20007));//下标[6]
			// 订单剩余时间提醒阀值
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20008));//下标[7]
			// 客户跟进提前时间
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20011));//下标[8]
			// 延后呼叫提前时间
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20012));//下标[9]
			// 跟进警报提前时间
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20013));//下标[10]
			// 意向客户放弃提前##天，发送提醒
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20014));//下标[11]
			// 客户回访提前时间
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20016));//下标[12]
			// 发送未接来电消息提醒
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20028));//下标[13]
			// 资源放弃前：提前‘1天/2天/3天/4天’发送提醒  isOpen 0：关闭，1：开启
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20051));//下标[14]
			 //  签约客户放弃前：提前1天/2天/3天/4天发送提醒； isOpen 0：关闭，1：开启
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_20052));//下标[15]
			request.setAttribute("dictionaryList", dictionaryList);
		}catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/tsm/option/msgmanage";
	}
	
	/** 
	 *  消息设置【操作】
	 */
	@RequestMapping("/updateMsgCfg")
	@ResponseBody
	public String updateMsgCfg(HttpServletRequest request,DataDictionaryModel dataDictionaryModel){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			List<DataDictionaryBean>dictionaryList = dataDictionaryModel.getDictionaryList();
			// 客户跟进开关
			dictionaryList.get(0).setDictionaryValue(
					dictionaryList.get(0).getDictionaryValue() == null ? "1" : 
						dictionaryList.get(0).getDictionaryValue());
			// 延后呼叫开关
			dictionaryList.get(1).setDictionaryValue(
					dictionaryList.get(1).getDictionaryValue() == null ? "1" : 
						dictionaryList.get(1).getDictionaryValue());
			// 跟进警报开关
			dictionaryList.get(2).setDictionaryValue(
					dictionaryList.get(2).getDictionaryValue() == null ? "1" : 
						dictionaryList.get(2).getDictionaryValue());
			// 月计划提前时间
			dictionaryList.get(3).setDictionaryValue(
					dictionaryList.get(3).getDictionaryValue() == null ? "3" : 
						dictionaryList.get(3).getDictionaryValue());
			// 短信剩余量阀值
			dictionaryList.get(4).setDictionaryValue(
					dictionaryList.get(4).getDictionaryValue() == null ? "20" : 
						dictionaryList.get(4).getDictionaryValue());
			// 坐席剩余阀值
			dictionaryList.get(5).setDictionaryValue(
					dictionaryList.get(5).getDictionaryValue() == null ? "20" : 
						dictionaryList.get(5).getDictionaryValue());
			// 通信时长阀值
			dictionaryList.get(6).setDictionaryValue(
					dictionaryList.get(6).getDictionaryValue() == null ? "20" : 
						dictionaryList.get(6).getDictionaryValue());
			// 订单剩余阀值
			dictionaryList.get(7).setDictionaryValue(
					dictionaryList.get(7).getDictionaryValue() == null ? "20" : 
						dictionaryList.get(7).getDictionaryValue());
			
			// 客户跟进阀值
			dictionaryList.get(8).setDictionaryValue(
					dictionaryList.get(8).getDictionaryValue() == null ? "5" : 
						dictionaryList.get(8).getDictionaryValue());
			// 延后呼叫阀值
			dictionaryList.get(9).setDictionaryValue(
					dictionaryList.get(9).getDictionaryValue() == null ? "5" : 
						dictionaryList.get(9).getDictionaryValue());
			// 跟进警报阀值
			dictionaryList.get(10).setDictionaryValue(
					dictionaryList.get(10).getDictionaryValue() == null ? "5" : 
						dictionaryList.get(10).getDictionaryValue());
			
			Date dbtime = DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern);
			for (DataDictionaryBean obj : dictionaryList) {
				obj.setModifierAcc(shiroUser.getAccount());
				obj.setModifydate(dbtime);
				obj.setOrgId(shiroUser.getOrgId());
			}
			dataDictionaryService.modifyTrendsBatch(dictionaryList);
			List<DataDictionaryBean>datas = cachedService.getDictionary(shiroUser.getOrgId());
			Date time = DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern);
			for (DataDictionaryBean obj : dictionaryList) {
				obj.setModifierAcc(shiroUser.getAccount());
				obj.setModifydate(time);
				obj.setOrgId(shiroUser.getOrgId());
				for(DataDictionaryBean dic:datas){
					if(dic.getDictionaryId().equals(obj.getDictionaryId())){
						dic.setDictionaryValue(obj.getDictionaryValue());
						dic.setModifierAcc(shiroUser.getAccount());
						dic.setModifydate(time);
						dic.setIsOpen(obj.getIsOpen());
						break;
					}
				}
			}
			//修改缓存
			cachedService.setDictionary(shiroUser.getOrgId(), datas);			
			OpenMsgCfgUtil.getOpenMsgCfgMap(cachedService,dataDictionaryService, shiroUser.getOrgId(), true); // 刷消息配置项，定时发消息时用
			String isLocalWeb = ConfigInfoUtils.getStringValue("is_local_web"); // #是否本地部署 0:否，1:是
			if(StringUtils.isNotBlank(isLocalWeb) && "1".equals(isLocalWeb)){
				final String userOrgId = ShiroUtil.getShiroUser().getOrgId();
				ExecutorUtils.THREAD_POOL.submit(new Runnable() {
					public void run() {	
						try{
							int isHookSet = 0;
							if(hookSmsConfigService.findExistByOrgId(userOrgId) > 0){ 
								 isHookSet = 1;
							 }	 
							String isEffect = "0";  // 有效呼叫设置是否开启 ：0 关闭，1 开启
							String effectVal = "0"; // 有效呼叫设置值
							isEffect = cachedService.getDirList(AppConstant.DATA16, userOrgId).get(0).getDictionaryValue();
							if ("1".equals(isEffect)) {
								List<DataDictionaryBean> list1 = cachedService.getDirList(AppConstant.DATA04, userOrgId);
								if (list1 != null && list1.size() > 0) {
									effectVal = list1.get(0).getDictionaryValue();
								}
							}
							AuthOrgMessageBean bean = new AuthOrgMessageBean();
							bean.setId(SysBaseModelUtil.getModelId());
							bean.setOrgId(userOrgId);
							bean.setType("1");
							bean.setIsHookSet(isHookSet);
							bean.setIsUnCall(1);
							bean.setIsEffect(isEffect);
							bean.setEffectVal(effectVal);
							bean.setMachineCode(ConfigInfoUtils.getStringValue("auth_code"));
							Map<String,String>params = new HashMap<String, String>();
							params.put("data", JsonUtil.getJsonString(bean));
							//
							String url = ConfigInfoUtils.getStringValue("total_jms_send_message_url");
							String jsonStr = HttpUtil.doPost(url, params);
							System.out.println(jsonStr);
						}catch(Exception e){
							logger.error(userOrgId+"修改total_jms_send_message_url异常！", e);
						}					
					}
				});
			}			
			return AppConstant.RESULT_SUCCESS;
		}catch (Exception e) {
			throw new SysRunException(e);
		}

	}
	  /***************************【慧营销4.0 结束】******************************************/

}
