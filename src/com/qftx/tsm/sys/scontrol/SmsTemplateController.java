package com.qftx.tsm.sys.scontrol;

import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.service.OrgService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.sys.bean.SmsTemplate;
import com.qftx.tsm.sys.bean.SmsTemplateGroup;
import com.qftx.tsm.sys.dto.SmsTemplateGroupDto;
import com.qftx.tsm.sys.service.SmsTemplateGroupService;
import com.qftx.tsm.sys.service.SmsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 短信模板设置
 * @author: zwj
 * @since: 2015-12-4  上午10:21:32
 * @history: 4.x
 */
@Controller
@RequestMapping("/sys/smsTemp")
public class SmsTemplateController {
	@Autowired
	private SmsTemplateGroupService smsTemplateGroupService;
	@Autowired
	private SmsTemplateService smsTemplateService;
	@Autowired
	private OrgService orgService;
	/** 短信模板页面  */
	 @RequestMapping("/smsTempList")
	public String smsTempList(HttpServletRequest request){
		try{
			ShiroUser user= ShiroUtil.getShiroUser();
			String tsgId = request.getParameter("tsgId");
			// 查询全部短信模板个数
			Integer allCount = smsTemplateService.getTemplateCount(user.getOrgId());
			request.setAttribute("allCount", allCount);
			// 查询单位下模板分类
			List<SmsTemplateGroupDto>smsTempGroups = smsTemplateGroupService.getTemplateGroupList(user.getOrgId());
			request.setAttribute("smsTempGroups", smsTempGroups);
			// 查询单位下短信模板
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
			if(StringUtils.isNotBlank(tsgId)){
				map.put("tsgId", tsgId);
			}			
			List<SmsTemplate> smsTemps = smsTemplateService.getTemplateList(map);
			request.setAttribute("smsTemps", smsTemps);
			request.setAttribute("tsgId", tsgId);
		}catch(Exception e){
			throw new SysRunException(e);
		}		
		return "/tsm/sys/smsTemp/smsTemp";
	}
	 /** 跳转至 短信模板 弹窗 */
		@RequestMapping("/reditTempPage")
		public String reditTempPage(HttpServletRequest request){
			try{
				ShiroUser user= ShiroUtil.getShiroUser();
				String id = request.getParameter("id");
				if(StringUtils.isNotBlank(id)){
					SmsTemplate st = new SmsTemplate();
					st.setOrgId(user.getOrgId());
					st.setId(id);
					st= smsTemplateService.getByCondtion(st);
					request.setAttribute("item", st);
				}else{
					String tsgId = request.getParameter("tsgId");
					request.setAttribute("tsgId", tsgId);
				}
				// 查询单位下模板分类
				List<SmsTemplateGroupDto>smsTempGroups = smsTemplateGroupService.getTemplateGroupList(user.getOrgId());
				request.setAttribute("smsTempGroups", smsTempGroups);
				Org org  = new Org();
				org.setOrgId(user.getOrgId());
				org = orgService.getByCondtion(org);
				request.setAttribute("smslabel", org.getSignature()); // 签名
				return "/tsm/sys/smsTemp/operaSmsTemp";
			}catch(Exception e){
				throw new SysRunException(e);
			}
		}
		
	 /** 编辑短信模板 */
	@ResponseBody
	@RequestMapping("/operaSmsTemp")
	public String operaSmsTemp(HttpServletRequest request,SmsTemplate stp){
		try{
			ShiroUser user= ShiroUtil.getShiroUser();
			if(StringUtils.isNotBlank(stp.getId())){
				stp.setOrgId(user.getOrgId());
				stp.setModifierAcc(user.getAccount());
				stp.setModifydate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
				smsTemplateService.modifyTrends(stp);
			}else{
				stp.setId(SysBaseModelUtil.getModelId());
				stp.setOrgId(user.getOrgId());
				stp.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
				stp.setInputerAcc(user.getAccount());
				smsTemplateService.create(stp);
			}
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			throw new SysRunException(e);
		}
	}
	
	/** 跳转至 短信模板分类弹窗 */
	@RequestMapping("/reditTempGroupPage")
	public String reditTempGroupPage(HttpServletRequest request){
		try{
			ShiroUser user= ShiroUtil.getShiroUser();
			String tsgId = request.getParameter("tsgId");
			if(StringUtils.isNotBlank(tsgId)){
				SmsTemplateGroup stg = new SmsTemplateGroup();
				stg.setOrgId(user.getOrgId());
				stg.setTsgId(tsgId);
				stg.setIsDel(0);
				stg = smsTemplateGroupService.getByCondtion(stg);
				request.setAttribute("item", stg);
				request.setAttribute("tsgId", tsgId);
			}
			return "/tsm/sys/smsTemp/operaSmsTempGroup";
		}catch(Exception e){
			throw new SysRunException(e);
		}
	}
	
	/** 编辑短信模板分类 */
	@ResponseBody
	@RequestMapping("/operaSmsTempGroup")
	public String operaSmsTempGroup(HttpServletRequest request,SmsTemplateGroup stg){
		try{
			ShiroUser user= ShiroUtil.getShiroUser();
			if(StringUtils.isNotBlank(stg.getTsgId())){
				stg.setOrgId(user.getOrgId());
				stg.setModifierAcc(user.getAccount());
				stg.setModifydate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
				smsTemplateGroupService.modifyTrends(stg);
			}else{
				stg.setTsgId(SysBaseModelUtil.getModelId());
				stg.setOrgId(user.getOrgId());
				stg.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
				stg.setInputAcc(user.getAccount());
				// 查询最大模板顺序值，设置值+1
				Map<String,Object>map = new HashMap<String, Object>();
				map.put("orgId", user.getOrgId());
				Integer maxIndex = smsTemplateGroupService.getMaxByIndex(map)+1;
				stg.setGroupIndex(maxIndex);
				stg.setIsDel(0);
				smsTemplateGroupService.create(stg);
			}
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			throw new SysRunException(e);
		}
	}
		
		/**  删除短信模板 */
		@ResponseBody
		@RequestMapping("/removeSmsTemp")
		public String removeSmsTemp(HttpServletRequest request){
			try{
				ShiroUser user= ShiroUtil.getShiroUser();
				String smsTempId = request.getParameter("id");
				Map<String,String>map = new HashMap<String, String>();
				map.put("orgId", user.getOrgId());
				map.put("id", smsTempId);
				smsTemplateService.remove(map);
				return AppConstant.RESULT_SUCCESS;
			}catch(Exception e){
				throw new SysRunException(e);
			}
		}
		
		/**  删除短信模板分类 */
		@ResponseBody
		@RequestMapping("/removeSmsTempGroup")
		public String removeSmsTempGroup(HttpServletRequest request){
			try{
				ShiroUser user= ShiroUtil.getShiroUser();
				String tsgId = request.getParameter("tsgId");
				Map<String,String>map = new HashMap<String, String>();
				map.put("orgId", user.getOrgId());
				map.put("tsgId", tsgId);
				smsTemplateGroupService.remove(map);
				return AppConstant.RESULT_SUCCESS;
			}catch(Exception e){
				throw new SysRunException(e);
			}
		}
}
