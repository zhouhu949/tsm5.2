package com.qftx.tsm.sys.scontrol;

import com.qftx.base.auth.bean.AuthOrgMessageBean;
import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.service.OrgService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.ExecutorUtils;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.dto.DataDictionaryModel;
import com.qftx.tsm.option.scontrol.MsgManageControl;
import com.qftx.tsm.option.service.DataDictionaryService;
import com.qftx.tsm.sys.bean.HookSmsConfig;
import com.qftx.tsm.sys.bean.SmsTemplate;
import com.qftx.tsm.sys.dto.HookSmsConfigDto;
import com.qftx.tsm.sys.dto.SmsTemplateGroupDto;
import com.qftx.tsm.sys.service.HookSmsConfigService;
import com.qftx.tsm.sys.service.SmsTemplateGroupService;
import com.qftx.tsm.sys.service.SmsTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 挂机短信设置
 * @author: zwj
 * @since: 2015-12-24  上午10:21:32
 * @history: 4.x
 */
@Controller
@RequestMapping("/sys/hookSms")
public class HookSmsSetController {
	Logger logger=Logger.getLogger(MsgManageControl.class);
	@Autowired
	private DataDictionaryService dataDictionaryService;	
	@Autowired
	private HookSmsConfigService hookSmsConfigService;	
	@Autowired
	private CachedService cachedService;
	@Autowired
	private SmsTemplateGroupService smsTemplateGroupService;
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private OrgService orgService;
	/** 
	 * 挂机短信规则设置【页面】
	 */
	@RequestMapping("/hookRulePage")
	public String hookRulePage(HttpServletRequest request){
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
			// 发送时间设置 开始时间（小时）温馨提示：挂机短信只能在该时间段发送
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40025));//下标[0]
			// 发送时间设置 结束时间（小时）
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40026));//下标[1]
			// 发送频率设置 温馨提示：针对同一号码重复呼入/呼出发送频率
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40027));//下标[2]	
			// 发送数量设置 每天最多发送短信xx 条  温馨提示：为空时无限制
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40028));//下标[3]	
			
			request.setAttribute("dictionaryList", dictionaryList);
		}catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/tsm/sys/hook/hookSmsRule";
	}
	
	/** 
	 *  挂机短信规则设置【操作】
	 */
	@RequestMapping("/updateHookRule")
	@ResponseBody
	public String updateHookRule(HttpServletRequest request,DataDictionaryModel dataDictionaryModel){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			List<DataDictionaryBean>dictionaryList = dataDictionaryModel.getDictionaryList();
			List<DataDictionaryBean>datas = cachedService.getDictionary(user.getOrgId());
			Date time = com.qftx.common.util.DateUtil.getDateCurrentDate(com.qftx.common.util.DateUtil.hour24HMSPattern);
			for (DataDictionaryBean obj : dictionaryList) {
				obj.setModifierAcc(user.getAccount());
				obj.setModifydate(time);
				obj.setOrgId(user.getOrgId());
				for(DataDictionaryBean dic:datas){
					if(dic.getDictionaryId().equals(obj.getDictionaryId())){
						dic.setDictionaryValue(obj.getDictionaryValue());
						dic.setModifierAcc(user.getAccount());
						dic.setModifydate(time);
						dic.setIsOpen(obj.getIsOpen());
						break;
					}
				}
			}
			// 修改数据库
			dataDictionaryService.modifyTrendsBatch(dictionaryList);
			//修改缓存
			cachedService.setDictionary(user.getOrgId(), datas);
			return AppConstant.RESULT_SUCCESS;
		}catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/***
	 * 挂机短信模板设置【列表页面】
	 */
	@RequestMapping("/hookTempPage")
	public String hookTempPage(HttpServletRequest request,HookSmsConfigDto dto){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			dto.setOrgId(user.getOrgId());
			// 创建时间
			if(dto.getdDateType() != null && dto.getdDateType() != 0 && dto.getdDateType() != 5){
				dto.setStartDate(getStartDateStr(dto.getdDateType()));
				dto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			List<HookSmsConfigDto>list = hookSmsConfigService.getHookTempListPage(dto);
			for(HookSmsConfigDto cdto:list){
				cdto.setSendTypeName(get_SendTypeName(cdto.getSendType()));
				cdto.setSendCondiName(get_SendCondiName(cdto.getSendCondi()));
				cdto.setStateName(get_StatusName(cdto.getStatus()));
			}
			request.setAttribute("list",list);
			request.setAttribute("item", dto);
			Org org  = new Org();
			org.setOrgId(user.getOrgId());
			org = orgService.getByCondtion(org);
			request.setAttribute("smslabel", org.getSignature()); // 签名
			return "/tsm/sys/hook/hookSmsTemp";
		}catch(Exception e){
			throw new SysRunException(e);
		}
	}
	
	/** 跳转至 编辑挂机短信模板 页面 */
	@RequestMapping("/toAddOrEditTemp")
	public String toAddOrEditTemp(HttpServletRequest request){
		String id = request.getParameter("id");
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			if(StringUtils.isNotBlank(id)){				
				HookSmsConfig entity = new HookSmsConfig();
				entity.setOrgId(shiroUser.getOrgId());
				entity.setId(id);
				entity = hookSmsConfigService.getByCondtion(entity);
				request.setAttribute("entity", entity);
			}	
			Org org  = new Org();
			org.setOrgId(shiroUser.getOrgId());
			org = orgService.getByCondtion(org);
			request.setAttribute("smslabel", org.getSignature()); // 签名
			// 查询单位下模板分类
			List<SmsTemplateGroupDto>smsTempGroups = smsTemplateGroupService.getTemplateGroupList(shiroUser.getOrgId());
			request.setAttribute("smsTempGroups", smsTempGroups);
			// 查询单位下短信模板
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", shiroUser.getOrgId());
			List<SmsTemplate> smsTemps = smsTemplateService.getTemplateList(map);
			request.setAttribute("smsTemps", smsTemps);
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return "/tsm/sys/hook/modfiyHookSmsTemp";
	}
	
	/** 跳转至 编辑挂机短信模板 右侧页面 */
	@RequestMapping("/toRightSmsTemp")
	public String toRightSmsTemp(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String tsgId = request.getParameter("tsgId"); // 短信分类ID
			// 查询单位下模板分类
			List<SmsTemplateGroupDto>smsTempGroups = smsTemplateGroupService.getTemplateGroupList(user.getOrgId());
			request.setAttribute("smsTempGroups", smsTempGroups);
			// 查询单位下短信模板
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
			map.put("tsgId", tsgId);
			List<SmsTemplate> smsTemps = smsTemplateService.getTemplateList(map);
			request.setAttribute("smsTemps", smsTemps);
			
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return "/tsm/sys/hook/modfiyHookSmsTempRight";
	}
	
	/** 新增或修改 挂机短信模板 */
	@RequestMapping("/addOrEditTemp")
	@ResponseBody
	public String addOrEditTemp(HttpServletRequest request,HookSmsConfig config){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			if(StringUtils.isNotBlank(config.getId())){
				// 修改 
				config.setOrgId(shiroUser.getOrgId());
				config.setUpdateAcc(shiroUser.getAccount());
				config.setUpdateTime(com.qftx.common.util.DateUtil.getDateCurrentDate(com.qftx.common.util.DateUtil.hour24HMSPattern));
				hookSmsConfigService.modifyTrends(config);
			}else{
				// 新增 
				config.setId(SysBaseModelUtil.getModelId());
				config.setOrgId(shiroUser.getOrgId());
				config.setInputAcc(shiroUser.getAccount());
				config.setInputTime(com.qftx.common.util.DateUtil.getDateCurrentDate(com.qftx.common.util.DateUtil.hour24HMSPattern));
				config.setEnable(0);
				hookSmsConfigService.create(config);
			}	
			return AppConstant.RESULT_SUCCESS;

			
		}catch(Exception e){
			throw new SysRunException(e);
		}
	}
	
	/** 挂机短信模板 启用*/
	@RequestMapping("/setEnable")
	@ResponseBody
	public String setEnable(HttpServletRequest request){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			String id = request.getParameter("id");
			String enable = request.getParameter("enable"); // 1 启用，0 不启用
			
			// 启用挂机短信模板
			HookSmsConfig entity = new HookSmsConfig();
			entity.setId(id);
			entity.setOrgId(shiroUser.getOrgId());
			entity.setUpdateAcc(shiroUser.getAccount());
			entity.setUpdateTime(com.qftx.common.util.DateUtil.getDateCurrentDate(com.qftx.common.util.DateUtil.hour24HMSPattern));
			entity.setEnable(Integer.parseInt(enable));
			hookSmsConfigService.modifyTrends(entity);
			// 发送事件，告知通话计算服务，对挂机短信消息发送做修改
			String isLocalWeb = ConfigInfoUtils.getStringValue("is_local_web"); // #是否本地部署 0:否，1:是
			if(StringUtils.isNotBlank(isLocalWeb) && "1".equals(isLocalWeb)){
				final String userOrgId = shiroUser.getOrgId();
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
							bean.setType("2");
							bean.setIsHookSet(isHookSet);
							bean.setIsUnCall(1);
							bean.setIsEffect(isEffect);
							bean.setEffectVal(effectVal);
							bean.setMachineCode(ConfigInfoUtils.getStringValue("auth_code"));
							Map<String,String>params = new HashMap<String, String>();
							params.put("data", JsonUtil.getJsonString(bean));
							String url = ConfigInfoUtils.getStringValue("total_jms_send_message_url");
							HttpUtil.doPost(url, params);
						}catch(Exception e){
							logger.error(userOrgId+"修改total_jms_send_message_url 挂机短信模板 异常！", e);
						}						
					}
				});
			}
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return AppConstant.RESULT_SUCCESS;
	}
	
		/** 
		 * 获取查询时间
		 * @param type 1-当天 2-本周 3-本月 4-半年
		 * @return 
		 * @create  2015年12月14日 下午3:48:05 lixing
		 * @history  
		 */
		private String getStartDateStr(Integer type){
			String str = "";
			if(type == 1){
				str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
			}else if(type == 2){
				str = DateUtil.getWeekFirstDay(new Date());
			}else if(type == 3){
				str = DateUtil.getMonthFirstDay(new Date());
			}else if(type == 4){
				str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), -180), DateUtil.DATE_DAY);
			}
			return str;
		}
		
		
		
		// 获取呼叫类型 列表显示名称
		private String get_StatusName(String status){
			String result = "";
			result = status.replace("0", "呼入").replace("1","呼出");
			return result;
		}
		
		// 获取发送对象 列表显示名称
		private String get_SendTypeName(String status){
			String result = "";
			result = status.replace("1", "资源").replace("2","签约客户").replace("3","沉默客户").replace("4","流失客户").replace("5","意向客户").replace("6","公海客户").replace("7","访客");
			return result;
		}
				
		// 获取发送条件 列表显示名称
		private String get_SendCondiName(String status){
			String result = "";
			result = status.replace("0", "未接通").replace("1","接通");
			return result;
		}
}
