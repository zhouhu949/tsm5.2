package com.qftx.tsm.callrecord.scontrol;

import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.common.util.constants.SysConstant;
import com.qftx.tsm.callrecord.util.SmsImportCheckUtil;
import com.qftx.tsm.callrecord.util.SmsImportIoUtil;
import com.qftx.tsm.email.bean.TsmEmailConfig;
import com.qftx.tsm.email.bean.TsmEmailSend;
import com.qftx.tsm.email.bean.TsmEmailSign;
import com.qftx.tsm.email.service.TsmEmailConfigService;
import com.qftx.tsm.email.service.TsmEmailSendService;
import com.qftx.tsm.email.service.TsmEmailSignService;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *	通信管理 发送邮件
 */
@Controller
@RequestMapping("/call/email")
public class EmailSendController{
	Logger logger=Logger.getLogger(EmailSendController.class);
	@Autowired
	private TsmEmailSignService tsmEmailSignService; 
	@Autowired private TsmEmailSendService tsmEmailSendService;
	@Autowired private TsmEmailConfigService tsmEmailConfigService;
	@Autowired private OrgGroupService orgGroupService;
 	@Autowired private OrgGroupUserService orgGroupUserService;
 	@Autowired private CachedService cachedService;
 	
 	
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(3);
	/** 跳转至 发送邮件弹窗 页面 */
	@RequestMapping("/toIdialogEmailSend")
	public String toIdialogEmailSend(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String email=request.getParameter("email")== null ? "" : request.getParameter("email").trim();
			String name=request.getParameter("name")== null ? "" : request.getParameter("name").trim();
			String account=user.getAccount();
			String name_email = "";
			if("".equals(name)){
				name_email = email;
			}else{
				name_email = name + '<'+ email+'>'+',';
			}
			request.setAttribute("name_email", name_email);
			request.setAttribute("account", account);
			
			// 查询单位下邮件签名
			TsmEmailSign entity = new TsmEmailSign();
			entity.setOrgId(user.getOrgId());
			List<TsmEmailSign> signs = tsmEmailSignService.getListByCondtion(entity);
			request.setAttribute("signs", signs);
			
			 // 查询自己的邮件绑定
			 TsmEmailConfig centity = new TsmEmailConfig(); 
			 centity.setOrgId(user.getOrgId());
			 centity.setUserId(user.getId());
			 centity.setIsDel(0);
			 List<TsmEmailConfig> tecs =  tsmEmailConfigService.getListByCondtion(centity);
			 if(tecs!=null && tecs.size() >0){
				 request.setAttribute("tecs", tecs.get(0));
			 }
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
			 request.setAttribute("tsmUpLoadServiceUrl", ConfigInfoUtils.getStringValue("tsm_upload_service_url")); // 上传服务器路径
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return "/call/idialog/email_send";
	}	
	
	/** 邮件发送  */
	@RequestMapping("/emailSend")
	@ResponseBody
	public String emailSend(HttpServletRequest request,TsmEmailSend emailSend){
		try{
			String name_email = request.getParameter("name_email");
			ShiroUser user = ShiroUtil.getShiroUser();
			final String userId = user.getId();
			final String orgId = user.getOrgId();
			final String account = user.getAccount();
			if(StringUtils.isNotBlank(name_email)){
				tsmEmailSendService.saveBySend(userId,orgId,account,name_email, emailSend.getContext(),emailSend.getTitle(),emailSend.getFileIds());
			}			
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return AppConstant.RESULT_SUCCESS;
	}
	
	/**  下载导入模板文件*/
	@RequestMapping("/downloadTemp")
	public String downloadTemp(HttpServletRequest request,HttpServletResponse response){
		try {
			String path = request.getParameter("path");
			String fileName = request.getParameter("fileName");
			if(StringUtils.isBlank(path)){			
				path = "d:\\email_send_temp.xls";		
			}
			if(StringUtils.isBlank(fileName)){
				fileName = "邮件发送导入模板.xls";
			}
			return SmsImportIoUtil.downloadFile(path, fileName,response);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/** 跳转至 【导入邮箱地址】 批量发送邮件页面  */
	@RequestMapping("/toEmailBatchSend")
	public String toEmailBatchSend(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			// 查询单位下邮件签名
			TsmEmailSign entity = new TsmEmailSign();
			entity.setOrgId(user.getOrgId());
			entity.setIsDel(0);
			List<TsmEmailSign> signs = tsmEmailSignService.getListByCondtion(entity);
			// 查询自己的邮件绑定
			 TsmEmailConfig centity = new TsmEmailConfig(); 
			 centity.setOrgId(user.getOrgId());
			 centity.setUserId(user.getId());
			 centity.setIsDel(0);
			 List<TsmEmailConfig> tecs =  tsmEmailConfigService.getListByCondtion(centity);
			 if(tecs!=null && tecs.size() >0){
				 request.setAttribute("tecs", tecs.get(0));
			 }
			request.setAttribute("signs", signs);
			request.setAttribute("tsmUpLoadServiceUrl", ConfigInfoUtils.getStringValue("tsm_upload_service_url")); // 上传服务器路径
			request.setAttribute("taskId", SysBaseModelUtil.getModelId());
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return "/call/emailBatchSend";
	}
	
	/** 跳转至 【导入邮箱地址】右侧签名页面  */
	@RequestMapping("/toEmailBatchSendRight")
	@ResponseBody
	public Map<String, Object> toEmailBatchSendRight(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String id = request.getParameter("id");
			// 查询单位下邮件签名
			TsmEmailSign entity = new TsmEmailSign();
			entity.setOrgId(user.getOrgId());
			entity.setIsDel(0);
			if(StringUtils.isNotBlank(id)){
				entity.setId(id);
				TsmEmailSign sign = tsmEmailSignService.getByCondtion(entity);
				map.put("item", sign);
			}
			entity.setId(null);
			List<TsmEmailSign> signs = tsmEmailSignService.getListByCondtion(entity);
			map.put("signs", signs);
		}catch(Exception e){
			map.put("status", "error");
			return map;
		}
		map.put("status", "success");
		return map;
	}
	
	/** 异步 导入邮件地址  */
	@RequestMapping(value ="iptEmailSendFile",method = RequestMethod.POST)
	public void iptEmailSendFile(MultipartHttpServletRequest request,HttpServletResponse response)throws Exception {
		Map<String,Object>result = new HashMap<String, Object>();
		        ShiroUser user = ShiroUtil.getUser();
		        String filePath = "d:\\emailSend\\";
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
						Object[]  objs =SmsImportIoUtil.getExcelContentMap(bigRealFilePath,1); // 获取excel文件中数据
				        if(objs != null){
				        	File delFile = new File(bigRealFilePath); 
							delFile.delete(); // 删除文件
							
				        	validValMap = (Map<Integer, String[]>) objs[1];
							fieldList = (List<String>)objs[2];
							
							// 2、验证格式：
							validValMap = SmsImportCheckUtil.checkFormat(validValMap, failureRowMap, fieldList,10000,1);
							SmsImportCheckUtil.checkEmpty(validValMap);
							
							// 3、返回值：
							String impRetnStr = SmsImportCheckUtil.getRetnMsg(user.getAccount(), (Workbook)objs[0],
									fieldList.size(), "d:\\", validValMap, failureRowMap, true);
							// 4、设置缓存，发送邮件时调用
				        	result.put("msg",JsonUtil.htmlEncode(impRetnStr));
				        }	
		        } 
		        response.setContentType("text/html; charset=UTF-8");
		        response.getWriter().print(JsonUtil.getJsonString(result));
	}
	
	/** 【导入号码】邮件批量发送  */
	@RequestMapping("/emailBatchSend")
	@ResponseBody
	public String emailBatchSend(HttpServletRequest request,TsmEmailSend emailSend,String taskId){
		try{			
			ShiroUser user = ShiroUtil.getUser();
			logger.debug("测试sesson=" + ShiroUtil.getSession("test_session"));
			// 获取导入的邮箱与姓名
			Map<Integer, String[]> phoneMap = (Map<Integer, String[]>) CachedUtil.getInstance().get(user.getId()+"_emailSend");
			logger.debug(user.getId()+"邮件导入catch 获取"+phoneMap.size());
			if(phoneMap != null){
				final StringBuffer sbf = new StringBuffer();
				//  组装邮箱地址与姓名
				Set<Integer> set = phoneMap.keySet();
				Integer[] keyArr = new Integer[set.size()];
				set.toArray(keyArr);
				Arrays.sort(keyArr); //keyArr是所有行号下表组成的数组，并按照升序排列				
				int len = 2; // 目前只有两列，姓名与邮箱地址
				for(Integer k : keyArr){
					String[] arr = phoneMap.get(k); //取出某行 对应的内容数组
					if("".equals(arr[0])){
						sbf.append(arr[1]);
						sbf.append(",");
					}else{
						sbf.append(arr[0]);
						sbf.append("<");
						sbf.append(arr[1]);
						sbf.append(">");
						sbf.append(",");
					}
				}	
				if(sbf.length() > 0){
					final String content1 = emailSend.getContext();
					final String title = emailSend.getTitle();
					final String fileIds = emailSend.getFileIds();
					final String userId = user.getId();
					final String orgId = user.getOrgId();
					final String account = user.getAccount();
					final String taskId1 = taskId;
					threadPool.submit(new Runnable() {						
						public void run() {							
							String[] mails = sbf.toString().split(",");		
							Map<String, Object> map = new HashMap<>();
							map.put("total", mails.length);
							map.put("finished", 0);
							map.put("status", true);
							cachedService.setProgress(orgId, taskId1, map);
							for(String s :mails){
								try {
									tsmEmailSendService.saveBySend(userId,orgId,account,s, content1,title,fileIds);
									map.put("finished", (Integer)map.get("finished") + 1);
									cachedService.setProgress(orgId, taskId1, map);
								} catch (Exception e) {									
									logger.error("群发邮件报错!",e);
								}
							}
						}
					});										
				}	
			}
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return AppConstant.RESULT_SUCCESS;
	}
	
}
