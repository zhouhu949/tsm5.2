package com.qftx.tsm.callrecord.scontrol;

import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.auth.service.OrgService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.common.util.constants.SysConstant;
import com.qftx.tsm.callrecord.service.CallSmsSendService;
import com.qftx.tsm.callrecord.util.SmsBatchCheckUtil;
import com.qftx.tsm.callrecord.util.SmsImportCheckUtil;
import com.qftx.tsm.callrecord.util.SmsImportIoUtil;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.sys.bean.SmsTemplate;
import com.qftx.tsm.sys.dto.SmsTemplateGroupDto;
import com.qftx.tsm.sys.service.SmsTemplateGroupService;
import com.qftx.tsm.sys.service.SmsTemplateService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *	通信管理 发送短信
 */
@Controller
@RequestMapping("/call/sms")
public class CallSmsSendController{
	Logger logger=Logger.getLogger(CallSmsSendController.class);
	@Autowired
	private SmsTemplateGroupService smsTemplateGroupService;
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private OrgService orgService; 
	@Autowired
	private UserService userService; 
	@Autowired
	private CallSmsSendService callSmsSendService;
	@Autowired
	private CachedService cachedService;
	@Autowired private OrgGroupService orgGroupService;
 	@Autowired private OrgGroupUserService orgGroupUserService;
 	
	/** 跳转至 发送短信弹窗 页面 */
	@RequestMapping("/toIdialogSmsSend")
	public String toIdialogSmsSend(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String phone=request.getParameter("phone")== null ? "" : request.getParameter("phone").trim();
			String name=request.getParameter("name")== null ? "" : request.getParameter("name").trim();
			String account=user.getAccount();
			String name_mobile = "";
			
			Org org  = new Org();
			org.setOrgId(user.getOrgId());
			org = orgService.getByCondtion(org);
			if("".equals(name)){
				name_mobile = phone;
			}else{
				name_mobile = name + '|'+ phone+';';
			}
			request.setAttribute("name_mobile", name_mobile);
			request.setAttribute("account", account);
			request.setAttribute("smslabel", org.getSignature()); // 签名
			request.setAttribute("isSim", org.getIsSim()); // 手机卡短信是否启用（1:启用、0:停用）
			int isEma = org.getIsEma() == null ? 0 : org.getIsEma(); // EMA短信是否开通(0:未开通，1:开通)
			if(isEma == 0){ // 查询用户
				User entity = userService.getByAccount(user.getAccount());
				isEma = entity.getIsEma() == null ? 0 : entity.getIsEma();
			}
			request.setAttribute("isEma", isEma);
			// 查询单位下模板分类
			List<SmsTemplateGroupDto>smsTempGroups = smsTemplateGroupService.getTemplateGroupList(user.getOrgId());
			request.setAttribute("smsTempGroups", smsTempGroups);
			// 查询单位下短信模板
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
			List<SmsTemplate> smsTemps = smsTemplateService.getTemplateList(map);
			request.setAttribute("smsTemps", smsTemps);
			setIsReplaceWord(request,user);
			 Integer groupType = (Integer) ShiroUtil.getSession(SysConstant.USER_GROUP_TYPE);
			 if(groupType == null){
				 OrgGroupUser usermeber = new OrgGroupUser();
		         usermeber.setOrgId(user.getOrgId());
		         usermeber.setUserId(user.getId());
		         OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
		         if(newmember!=null){
		        	 OrgGroup orgGroup = orgGroupService.getByGroupId(user.getOrgId(),newmember.getGroupId());
			         if(orgGroup!=null){
			        	 // 设置用户类型
				         ShiroUtil.setSession(SysConstant.USER_GROUP_TYPE, orgGroup.getGroupType());
				         groupType = (Integer) ShiroUtil.getSession(SysConstant.USER_GROUP_TYPE);
			         }		        	
		         }	
			 }
			 request.setAttribute("groupType",groupType);
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return "/call/idialog/sms_send";
	}
	
	/** 客户端触发 跳转至 发送短信弹窗 页面 */
	@RequestMapping("/clientToIdialogSmsSend")
	public String clientToIdialogSmsSend(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
		
			String account=user.getAccount();	
			Org org  = new Org();
			org.setOrgId(user.getOrgId());
			org = orgService.getByCondtion(org);
			request.setAttribute("account", account);
			request.setAttribute("smslabel", org.getSignature()); // 签名			
			request.setAttribute("isSim", org.getIsSim()); // 手机卡短信是否启用（1:启用、0:停用）
			int isEma = org.getIsEma() == null ? 0 : org.getIsEma(); // EMA短信是否开通(0:未开通，1:开通)
			if(isEma == 0){ // 查询用户
				User entity = userService.getByAccount(user.getAccount());
				isEma = entity.getIsEma() == null ? 0 : entity.getIsEma();
			}
			request.setAttribute("isEma", isEma);
			
			// 查询单位下模板分类
			List<SmsTemplateGroupDto>smsTempGroups = smsTemplateGroupService.getTemplateGroupList(user.getOrgId());
			request.setAttribute("smsTempGroups", smsTempGroups);
			// 查询单位下短信模板
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
			List<SmsTemplate> smsTemps = smsTemplateService.getTemplateList(map);
			request.setAttribute("smsTemps", smsTemps);
			setIsReplaceWord(request,user);
			 Integer groupType = (Integer) ShiroUtil.getSession(SysConstant.USER_GROUP_TYPE); // 用户类型
			 if(groupType == null){
				 OrgGroupUser usermeber = new OrgGroupUser();
		         usermeber.setOrgId(user.getOrgId());
		         usermeber.setUserId(user.getId());
		         OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
		         if(newmember!=null){
		        	 OrgGroup orgGroup = orgGroupService.getByGroupId(user.getOrgId(),newmember.getGroupId());
			         if(orgGroup!=null){
			        	 // 设置用户类型
				         ShiroUtil.setSession(SysConstant.USER_GROUP_TYPE, orgGroup.getGroupType());
				         groupType = (Integer) ShiroUtil.getSession(SysConstant.USER_GROUP_TYPE);
			         }		        	
		         }		        
			 }
			 request.setAttribute("groupType",groupType);
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return "/call/idialog/sms_send";
	}
	
	/** 选择 短信模板右侧操作 */
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
		return "/call/submodual/smsBatchSend_right";
	}
	
	/** 批量发送短信 */
	@RequestMapping("/smsSend")
	@ResponseBody
	public String smsSend(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			Map<String, String> map = new HashMap<String, String>();
			// channelSelect 0:手机卡，1：106短信，2：ema短信
			Integer channelSelect = StringUtils.isNotBlank(request.getParameter("channelSelect")) ? Integer.valueOf(request.getParameter("channelSelect")) : null;
			String smsContent = request.getParameter("smsContent") == null ? ""
					: request.getParameter("smsContent").trim();
			String smslabel = request.getParameter("smslabel") == null ? ""
					: request.getParameter("smslabel").trim(); // 加上签名的内容
			String name_mobile = request.getParameter("name_mobile") == null ? ""
					: request.getParameter("name_mobile").trim(); // 加上签名的内容
			logger.info("*******  发送短信时页面传递的相关参数 。账号：【" + user.getAccount()
					+ "】 短信内容：【" + smsContent + "】 签名：【" + smslabel + "】接收人：【"
					+ name_mobile + "】   ************");
			if(channelSelect == null || channelSelect == 1){ // 手机卡发送短信不需要判断
				if(StringUtils.isBlank(smslabel) || StringUtils.isBlank(smsContent)){
					return "9007";
				}
			}			
			if(channelSelect!=null && (channelSelect==0 || channelSelect == 2)){
				String[] arrays = name_mobile.split(",");
				if(arrays==null || arrays.length<0){
					return "接收人不能为空！";
				}
				if(arrays.length>20){
					return "单次提交不能超过20个号码";
				}		
			}	
			map.put("smsContent", smsContent);
			map.put("smslabel", smslabel);
			map.put("name_mobile", name_mobile);

			String code = "";
			String errMsg = "";
			/** 参数验证 */
			Map<String, Object> reqMap = SmsBatchCheckUtil.checkParam4Send(
					user.getOrgId(), map.get("smslabel"),
					map.get("smsContent"), user.getAccount(),
					map.get("name_mobile"), userService);
			code = (String) reqMap.get("code");
			if ("9001".equals(code) || "9002".equals(code)
					|| "9003".equals(code) || "9005".equals(code)|| "9004".equals(code)) {
				return code;
			}
			
			errMsg = callSmsSendService.send(user.getOrgId(),smsContent, reqMap, smslabel,channelSelect);
			return errMsg;
		} catch (Exception e) {
			logger.error("smsSend 发送短信异常！", e);
			return "3";
		}
		
	}
	
	/**  下载导入模板文件*/
	@RequestMapping("/downloadTemp")
	public String downloadTemp(HttpServletRequest request,HttpServletResponse response){
		try {
			String path = request.getParameter("path");
			String fileName = request.getParameter("fileName");
			if(StringUtils.isBlank(path)){			
				path = "d:\\sms_send_temp.xls";		
			}
			if(StringUtils.isBlank(fileName)){
				fileName = "短信发送号码导入模板.xls";
			}
			return SmsImportIoUtil.downloadFile(path, fileName,response);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/** 跳转至 【导入号码】 批量发送短信页面  */
	@RequestMapping("/toSmsBatchSend")
	public String toSmsBatchSend(HttpServletRequest request){
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
			Org org  = new Org();
			org.setOrgId(user.getOrgId());
			org = orgService.getByCondtion(org);
			request.setAttribute("smslabel", org.getSignature()); // 签名
			request.setAttribute("tsmUpLoadServiceUrl", ConfigInfoUtils.getStringValue("tsm_upload_service_url")); // 上传服务器路径
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return "/call/smsBatchSend";
	}
	
	/** 选择 短信模板右侧操作 */
	@RequestMapping("/toRightSmsBatchTemp")
	public String toRightSmsBatchTemp(HttpServletRequest request){
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
		return "/call/submodual/smsBatchSend_right";
	}
	
	/** 异步 导入号码  */
	@RequestMapping(value ="iptSmsSendFile",method = RequestMethod.POST)
	public void iptSmsSendFile(MultipartHttpServletRequest request,HttpServletResponse response){
		Map<String,Object>result = new HashMap<String, Object>();
		try{
		        ShiroUser user = ShiroUtil.getUser();
		        String filePath = "d:\\smsSend\\";
		        Date baseDate = new Date();
		        MultipartFile file = request.getFile("f"); //file      
		        if (file == null||file.getSize()==0) {// step-2 判断file
		        	result.put("msg", "导入文件为空！");
		        }else{
		        	  String orgFileName = file.getOriginalFilename();
				        orgFileName = (orgFileName == null) ? "" : orgFileName;
				        Pattern p = Pattern.compile("\\s|\t|\r|\n");
				        Matcher m = p.matcher(orgFileName);
				        orgFileName = m.replaceAll("_");
				        
				        String fileName = FilenameUtils.getBaseName(orgFileName).concat("_") + DateUtil.formatDate(baseDate,"yyyyMMddHHmmss").concat(".").concat(FilenameUtils.getExtension(orgFileName).toLowerCase());
				        if (!(new File(filePath).exists())) {
				            new File(filePath).mkdirs();
				        }
				        String bigRealFilePath = filePath + fileName;
				        File targetFile = new File(bigRealFilePath);
				        file.transferTo(targetFile);//写入目标文件		
				        List<String> fieldList = null; //字段列表
						Map<Integer, String[]> validValMap = null;
						Map<Integer, String> failureRowMap = new HashMap<Integer, String>();
				        // 1、获取值
						Object[]  objs =SmsImportIoUtil.getExcelContentMap(bigRealFilePath,0); // 获取excel文件中数据
				        if(objs != null){
				        	File delFile = new File(bigRealFilePath); 
							delFile.delete(); // 删除文件
							
				        	validValMap = (Map<Integer, String[]>) objs[1];
							fieldList = (List<String>)objs[2];
							
							// 2、验证格式：
							validValMap = SmsImportCheckUtil.checkFormat(validValMap, failureRowMap, fieldList,10000,0);
							SmsImportCheckUtil.checkEmpty(validValMap);
							
							// 3、返回值：
							String impRetnStr = SmsImportCheckUtil.getRetnMsg(user.getAccount(), (Workbook)objs[0],
									fieldList.size(), "d:\\", validValMap, failureRowMap, true);
							// 4、设置缓存，发送短信时调用 					
							result.put("msg",JsonUtil.htmlEncode(impRetnStr));		
		        }		            
		        }		
		    	response.setContentType("text/html; charset=UTF-8");
		        response.getWriter().print(JsonUtil.getJsonString(result));
		}catch(Exception e){
			throw new SysRunException(e);			
		}
	
	}
	
	/** 批量发送短信【导入号码】 */
	@RequestMapping("/smsBatchSend")
	@ResponseBody
	public String smsBatchSend(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, String> map = new HashMap<String, String>();
		String smsContent = request.getParameter("smsContent") == null ? ""
				: request.getParameter("smsContent").trim();
		String smslabel = request.getParameter("smslabel") == null ? ""
				: request.getParameter("smslabel").trim(); // 加上签名的内容
		if(StringUtils.isBlank(smslabel) || StringUtils.isBlank(smsContent)){
			return "9007";
		}
		map.put("smsContent", smsContent);
		map.put("smslabel", smslabel);
		try{
			// 获取导入的号码与姓名
			Map<Integer, String[]> phoneMap = (Map<Integer, String[]>)CachedUtil.getInstance().get(user.getId()+"_smsSend");			
			if(phoneMap != null){
				StringBuffer sbf = new StringBuffer();
				//  组装号码与姓名
				Set<Integer> set = phoneMap.keySet();
				Integer[] keyArr = new Integer[set.size()];
				set.toArray(keyArr);
				Arrays.sort(keyArr); //keyArr是所有行号下表组成的数组，并按照升序排列				
				int len = 2; // 目前只有两列，姓名与号码
				for(Integer k : keyArr){
					String[] arr = phoneMap.get(k); //取出某行 对应的内容数组
					if("".equals(arr[0])){
						sbf.append(arr[1]);
						sbf.append(",");
					}else{
						sbf.append(arr[0]);
						sbf.append("|");
						sbf.append(arr[1]);
						sbf.append(",");
					}
				}
				map.put("name_mobile", sbf.toString());
				String code = "";
				String errMsg = "";
				/** 参数验证 */
				Map<String, Object> reqMap = SmsBatchCheckUtil.checkParam4Send(
						user.getOrgId(), map.get("smslabel"),
						map.get("smsContent"), user.getAccount(),
						map.get("name_mobile"), userService);
				code = (String) reqMap.get("code");
				if ("9001".equals(code) || "9002".equals(code)
						|| "9003".equals(code) || "9005".equals(code)|| "9004".equals(code)) {
					return code;
				}
				errMsg = callSmsSendService.send(user.getOrgId(),smsContent, reqMap, smslabel,null);
				return errMsg;
			}else{				
				return "9006";
			}
		}catch(Exception e){
			logger.error("smsBatchSend 发送短信异常！", e);
			return "3";
		}
	}
	
	/**
	 * 设置是否开启用*替换电话号码中间四位
	 */
	private void setIsReplaceWord(HttpServletRequest request,ShiroUser user) {
		// 查询缓存
		List<DataDictionaryBean> dataMap=cachedService.getDirList(AppConstant.DATA24, user.getOrgId());
		Integer idReplaceWord = 0;
		if (!dataMap.isEmpty() && dataMap.get(0) != null && !cachedService.judgeHideWhiteList(user.getOrgId(), user.getAccount())) {
			idReplaceWord = new Integer(dataMap.get(0).getDictionaryValue());
		}
		request.setAttribute("idReplaceWord", idReplaceWord);
	}
}
