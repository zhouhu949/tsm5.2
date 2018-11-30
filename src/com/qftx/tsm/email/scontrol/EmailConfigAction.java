package com.qftx.tsm.email.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.CodeUtils;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.email.bean.TsmEmailConfig;
import com.qftx.tsm.email.service.MailboxCheckService;
import com.qftx.tsm.email.service.TsmEmailConfigService;
import com.qftx.tsm.email.util.SysMailUtils;
import com.qftx.tsm.log.service.LogCustInfoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 邮箱绑定
 */
@Controller
@RequestMapping("/email/config")
public class EmailConfigAction {
    private static final Logger logger = Logger.getLogger(EmailConfigAction.class.getName());
    @Autowired
    private TsmEmailConfigService tsmEmailConfigService;
    @Autowired
    private MailboxCheckService mailboxCheckService;
    @Autowired
	private LogCustInfoService logCustInfoService;
    
    /** 判断是否有过 邮箱绑定 */
    @ResponseBody
    @RequestMapping("/getEmailConfig")
    public String getEmailConfig(HttpServletRequest request){
    	try{
    		ShiroUser user = ShiroUtil.getShiroUser();
    		TsmEmailConfig entity = new TsmEmailConfig();
    		entity.setOrgId(user.getOrgId());
    		entity.setUserId(user.getId());
    		entity.setIsDel(0);
    		List<TsmEmailConfig> tecs = tsmEmailConfigService.getListByCondtion(entity);
    		if(tecs != null && tecs.size() >0){
    			return AppConstant.RESULT_SUCCESS;
    		}
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}
    	return null;
    }
    
    /** 邮箱绑定 */
    @ResponseBody
    @RequestMapping("/saveEmailConfig")
    public String saveEmailConfig(HttpServletRequest request, String email,String password){
    	String re = AppConstant.RESULT_SUCCESS;
    	try{
    		if(SysMailUtils.checkMailPwd(email, password)){
	    		ShiroUser user = ShiroUtil.getShiroUser();
	    		int port = request.getServerPort();
	    		String url = "";
	    		if(port == 80){
	    			url = request.getScheme()+"://"+request.getServerName()+request.getContextPath()+"/email/config/bindEmail";
	    		}else{
	    			url = request.getScheme()+"://"+request.getServerName()+":"+port+request.getContextPath()+"/email/config/bindEmail";
	    		}
	    		byte[] data = CodeUtils.encrypt(AppConstant.EMAIL_PWD_KEY, password);
	    		String pwdStr = CodeUtils.base64Encode(data).replace("/r/n", "");
	    		mailboxCheckService.sendBindCheckEmail(user, email, pwdStr, url);
	    		// 新增用户操作日志
				LogBean logBean = new LogBean();
				logBean.setOrgId(user.getOrgId());
				logBean.setUserAcc(user.getAccount());
				logBean.setUserName(user.getName());
				logBean.setModuleId(AppConstant.Module_id11);
				logBean.setModuleName(AppConstant.Module_Name11);
				logBean.setOperateId(AppConstant.Operate_id72);
				logBean.setOperateName(AppConstant.Operate_Name72);
				logBean.setContent("账号绑定邮箱至"+email);
				logCustInfoService.addTableStoreLog(logBean, null);
    		}else{
    			re = AppConstant.RESULT_FAILURE;
    		}
    	}catch(Exception e){    		
    		logger.error("邮箱绑定失败!");
    		re = AppConstant.RESULT_EXCEPTION;
    	}
    	return re;
    }
    
    @RequestMapping("/bindEmail")
    public String bindEmail(HttpServletRequest request,String c){
    	String re = "";
    	try {
			if(StringUtils.isNotBlank(c)){
				re = mailboxCheckService.bindEmail(c);
			}else{
				re = "-1";
			}
		} catch (Exception e) {
			logger.error("绑定邮箱失败!",e);
			re = "-2";
		}
    	request.setAttribute("re", re);
    	return "follow/idialog/email_bind_info";
    }
    
    
    
    /** 
     * 跳转解绑页面
     * @param request
     * @return 
     * @create  2016年5月24日 上午10:26:25 lixing
     * @history  
     */
    @RequestMapping("/toUnBind")
    public String toUnBind(HttpServletRequest request){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		TsmEmailConfig entity = new TsmEmailConfig();
    		entity.setOrgId(user.getOrgId());
    		entity.setUserId(user.getId());
    		entity.setIsDel(0);
    		List<TsmEmailConfig> tecs = tsmEmailConfigService.getListByCondtion(entity);
    		if(tecs != null && tecs.size() >0){
    			request.setAttribute("bindEmail", tecs.get(0).getLoginUser());
    		}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
    	return "follow/idialog/unbind_email";
    }
    
    
    /** 
     * 发送邮箱解绑验证码
     * @return 
     * @create  2016年5月24日 上午11:07:35 lixing
     * @history  
     */
    @ResponseBody
    @RequestMapping("/sendUnBindKeyCode")
    public String sendUnBindKeyCode(){
    	String re = AppConstant.RESULT_SUCCESS;
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		TsmEmailConfig entity = new TsmEmailConfig();
    		entity.setOrgId(user.getOrgId());
    		entity.setUserId(user.getId());
    		entity.setIsDel(0);
    		List<TsmEmailConfig> tecs = tsmEmailConfigService.getListByCondtion(entity);
    		if(tecs != null && tecs.size() >0){
    			byte[] data = CodeUtils.encrypt(AppConstant.EMAIL_PWD_KEY, tecs.get(0).getPassWord());
	    		String pwdStr = CodeUtils.base64Encode(data).replace("/r/n", "");
    			mailboxCheckService.sendUnBindCheckEmail(user, tecs.get(0).getLoginUser(), pwdStr);
    			// 新增用户操作日志
				LogBean logBean = new LogBean();
				logBean.setOrgId(user.getOrgId());
				logBean.setUserAcc(user.getAccount());
				logBean.setUserName(user.getName());
				logBean.setModuleId(AppConstant.Module_id11);
				logBean.setModuleName(AppConstant.Module_Name11);
				logBean.setOperateId(AppConstant.Operate_id72);
				logBean.setOperateName(AppConstant.Operate_Name72);
				logBean.setContent("账号解绑邮箱"+tecs.get(0).getLoginUser());
				logCustInfoService.addTableStoreLog(logBean, null);
    		}
		} catch (Exception e) {
			logger.error("解绑邮箱发送验证码失败!",e);
			re = AppConstant.RESULT_EXCEPTION;
		}
    	return re;
    }
    
    
    /** 
     * 邮箱解绑验证码校验
     * @param code
     * @return 
     * @create  2016年5月24日 上午11:07:16 lixing
     * @history  
     */
    @ResponseBody
    @RequestMapping("/checkUnBindCode")
    public String checkUnBindCode(String code){
    	String re = AppConstant.RESULT_SUCCESS;
    	try {
			re = mailboxCheckService.unBindCodeCheck(code);
		} catch (Exception e) {
			logger.error("解绑邮箱发送验证码失败!",e);
			re = AppConstant.RESULT_EXCEPTION;
		}
    	return re;
    }
}
