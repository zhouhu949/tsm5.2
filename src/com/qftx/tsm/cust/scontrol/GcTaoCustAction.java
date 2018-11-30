package com.qftx.tsm.cust.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.bean.Role;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.*;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.common.action.BaseAction;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.FwCodeUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.TaoTagBean;
import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.follow.bean.CustFollowBean;
import com.qftx.tsm.tao.service.TaoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 淘客户
 *
 * @author: wuwei
 * @since: 2015年12月4日 下午1:55:43
 * @history:
 */
@Controller
@RequestMapping(value = "/gcres/tao/")
public class GcTaoCustAction extends BaseAction {
	private static Logger logger = Logger.getLogger(GcTaoCustAction.class);
	@Autowired
	private TaoService taoCustService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrgGroupUserService orgGroupUserService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private OrgService orgService;
	@Autowired
	private OrgGroupService orgGroupService;

	@ResponseBody
	@RequestMapping(value = "gcAddMyCust")
	public String gcAddMyCust(HttpServletRequest request, String resCustId, String followDate, String effectiveness, String labelCode, String labelName,
			String feedbackComment, String followType, String orgId, String account, String tsmCustGuide, String suitProcId, String concatPhone,
			String concatName,String reqId) {
		try {
			labelName = new String(FwCodeUtils.base64Decode(labelName), "utf-8");
			feedbackComment = new String(FwCodeUtils.base64Decode(feedbackComment), "utf-8");
			concatName = new String(FwCodeUtils.base64Decode(concatName), "utf-8");
			tsmCustGuide = new String(FwCodeUtils.base64Decode(tsmCustGuide), "utf-8");
			logger.debug("reqId="+reqId+"。labelName=" + labelName + "|feedbackComment=" + feedbackComment + "|concatName=" + concatName);
			logger.debug("reqId="+reqId+"。tsmCustGuide=" + tsmCustGuide);
			User us = userService.getByAccount(account);
			ShiroUser user = getShiroUser(us);
			String custFollowId = SysBaseModelUtil.getModelId();
			TsmCustGuide custGuide = JSON.parseObject(tsmCustGuide, TsmCustGuide.class);
			CustFollowBean custFollow = new CustFollowBean();
			TaoTagBean tagBean = null;
			custFollow.setCustFollowId(custFollowId);
			custFollow.setLabelCode(labelCode);
			custFollow.setLabelName(labelName);
			custFollow.setFeedbackComment(feedbackComment);
			custFollow.setEffectiveness(new Integer(effectiveness));
			custFollow.setFollowType(followType);
			custFollow.setFollowDate(DateUtil.parse(followDate, DateUtil.hour24HMSPattern));
			ResCustInfoBean custInfo = new ResCustInfoBean();
			custInfo.setResCustId(resCustId);
			custInfo.setLabelCode(labelCode);
			custInfo.setLabelName(labelName);
			resCustId = taoCustService.addMyCust(reqId,user.getOrgId(),user.getAccount(),user.getGroupId(),user.getId(),user.getName(),user.getIsState(), custInfo, custFollow, custGuide, suitProcId, tagBean, concatPhone, concatName,"1","1",0);
			logger.debug("reqId="+reqId+"。gcAddMyCust 返回 =" + resCustId);
		} catch (Exception e) {
			logger.error("reqId="+reqId+"。gcAddMyCust异常:" + e.getMessage(), e);
			return "1";
		}
		return "0";
	}

	public ShiroUser getShiroUser(User user) {
		ShiroUser shiroUser = new ShiroUser();
		if (user != null) {
			shiroUser.setId(user.getUserId());
			shiroUser.setAccount(user.getUserAccount());
			shiroUser.setOrgId(user.getOrgId());
			shiroUser.setName(user.getUserName());
			shiroUser.setOrgName(user.getOrgName());
			shiroUser.setPassword(user.getUserPassword());
			shiroUser.setIsBackground(user.getIsbackground());
			shiroUser.setImgUrl(user.getImgUrl());
			shiroUser.setIsWarn(user.getIsWarn());
			shiroUser.setCommunicationNo(user.getCommunicationNo());
			OrgGroupUser usermeber = new OrgGroupUser();
			usermeber.setOrgId(user.getOrgId());
			usermeber.setUserId(user.getUserId());
			List<Role> roles = roleService.getRoleByUserIdList(user.getOrgId(), user.getUserId());
			if (roles != null && roles.size() > 0) {
				shiroUser.setIssys(roles.get(0).getRoleType()); // 角色类别：0--销售，1--管理者
			}
			shiroUser.setIsState(orgService.getStateByOrgId(user.getOrgId())); // 0:个人资源，1：企业资源
			OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
			logger.debug("是否有组织结构=" + newmember);
			if (newmember != null) {
				shiroUser.setGroupId(newmember.getGroupId());
				OrgGroup orgGroup = orgGroupService.getByGroupId(user.getOrgId(),newmember.getGroupId());
				if (orgGroup != null) {
					shiroUser.setGroupName(orgGroup.getGroupName());
				}
			}
			shiroUser.setServeTime(user.getServeTime());
		}
		return shiroUser;
	}

	@InitBinder
	public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}
}
