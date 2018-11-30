package com.qftx.tsm.email.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.email.bean.MailboxCheckBean;
import com.qftx.tsm.email.bean.TsmEmailConfig;
import com.qftx.tsm.email.dao.MailboxCheckMapper;
import com.qftx.tsm.email.dao.TsmEmailConfigMapper;
import com.qftx.tsm.email.util.SysMail;
import com.qftx.tsm.email.util.SysMailSend;
import com.qftx.tsm.email.util.SysMailUtils;
import com.sun.istack.internal.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


 /** 
 *
 * @author: Administrator
 * @since: 2016年3月3日  下午2:59:34
 * @history:
 */

 /** 
 *
 * @author: Administrator
 * @since: 2016年3月3日  下午2:59:37
 * @history:
 */
@Service
public class MailboxCheckService {
	Logger logger = Logger.getLogger(MailboxCheckService.class);
	// 邮件登录用户名
	private static String email_username = ConfigInfoUtils.getStringValue("mail.username");
	// 邮件登录密码
	private static String email_password = ConfigInfoUtils.getStringValue("mail.password");
	//邮件主题
	private static String eamil_title = ConfigInfoUtils.getStringValue("mail.title");
	//解绑主题
	private static String unbind_title = ConfigInfoUtils.getStringValue("mail.unbind.title");
	
	@Autowired
	private MailboxCheckMapper mailboxCheckMapper;
	@Autowired
	private TsmEmailConfigMapper tsmEmailConfigMapper;
	// 邮件服务器地址
	public void create(String account,String orgId,String code,String email,String password,Integer type){
		MailboxCheckBean mcb = new MailboxCheckBean();
		mcb.setAccount(account);
		mcb.setOrgId(orgId);
		mcb.setType(type);
		mcb.setIsDel(0);
		//检查是否有已存在严重信息并删除
		List<MailboxCheckBean> beans = mailboxCheckMapper.findByCondtion(mcb);
		if(beans.size()>0){
			MailboxCheckBean delBean = beans.get(0);
			mailboxCheckMapper.delete(delBean.getId());
		}
		mcb.setId(SysBaseModelUtil.getModelId());
		mcb.setCode(code);
		mcb.setEmail(email);
		mcb.setPassword(password);
		mcb.setInputtime(new Date());
		mailboxCheckMapper.insert(mcb);
	}
	
	public void sendBindCheckEmail(ShiroUser user,String email,String password,String serviceUrl) throws Exception{
		//校验码
		String code = SysBaseModelUtil.getModelId();
		//创建校验信息
		create(user.getId(),user.getOrgId(),code,email,password,0);
		//发送邮件
		SysMail mail = SysMailUtils.getDefaultMail("",email_username, email_password);
		mail.setMailTo(email);
		mail.setMailSubject(eamil_title);
		StringBuffer contentBuffer = new StringBuffer("亲爱的用户&nbsp;您好，<br />");
		contentBuffer.append("&nbsp;&nbsp;&nbsp;&nbsp;您刚才在慧营销绑定了以下邮箱：<br />");
		contentBuffer.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\"mailto:"+email+"\" target=\"_blank\">").append(email+"</a><br />");
		contentBuffer.append("&nbsp;&nbsp;&nbsp;&nbsp;您现在必须先验证你的邮箱地址，请您点击以下链接验证您所绑定的电子邮箱地址：<br/>");
		serviceUrl+="?c="+code;
		contentBuffer.append("&nbsp;&nbsp;&nbsp;&nbsp;<a href=\""+serviceUrl+"\" target=\"_blank\">").append(serviceUrl).append("</a><br />");
		contentBuffer.append("&nbsp;&nbsp;&nbsp;&nbsp;如果点击不能打开以上链接，请手动复制到浏览器地址栏打开！");
		mail.setMailContent(contentBuffer.toString());
		// 发送
		SysMailSend.sendMail(mail, true);
	}
	
	/** 
	 *
	 * @param code
	 * @return -1 无效 0 过期  1成功
	 * @create  2016年3月3日 下午2:59:39 lixing
	 * @history  
	 */
	public String bindEmail(String code){
		String re = "";
		MailboxCheckBean mcb = new MailboxCheckBean();
		mcb.setCode(code);
		List<MailboxCheckBean> beans = mailboxCheckMapper.findByCondtion(mcb);
		if(beans.size() > 0){
			MailboxCheckBean bean = beans.get(0);
			if(bean.getIsDel() == 0){//状态为未删除
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, -1);
				Date curDate = calendar.getTime();
				if(bean.getInputtime().compareTo(curDate) == 1){//24小时之内有效
					String email = bean.getEmail();
					String userId = bean.getAccount();
					String orgId = bean.getOrgId();
					String passWord = bean.getPassword();
					TsmEmailConfig tec = new TsmEmailConfig();
					tec.setId(SysBaseModelUtil.getModelId());
					tec.setOrgId(orgId);
					tec.setUserId(userId);
					tec.setLoginUser(email);
					tec.setPassWord(passWord);
					tsmEmailConfigMapper.insert(tec);
					//设置为无效
					mailboxCheckMapper.delete(bean.getId());
					re = bean.getEmail();
				}else{
					re = "0";
				}
			}else{
				re = "0";
			}
		}else{
			re = "-1";
		}
		return re;
	}
	
	
	
	/** 
	 * 解除邮箱绑定验证码发送
	 * @param user
	 * @param email
	 * @param password
	 * @throws Exception 
	 * @create  2016年5月24日 上午10:50:26 lixing
	 * @history  
	 */
	public void sendUnBindCheckEmail(ShiroUser user,String email,String password) throws Exception{
		String code = ((int)((Math.random()*9+1)*100000))+"";
		create(user.getId(), user.getOrgId(), code, email, password, 1);
		//发送邮件
		SysMail mail = SysMailUtils.getDefaultMail("",email_username, email_password);
		mail.setMailTo(email);
		mail.setMailSubject(unbind_title);
		StringBuffer contentBuffer = new StringBuffer("亲爱的用户&nbsp;您好，<br />");
		contentBuffer.append("&nbsp;&nbsp;&nbsp;&nbsp;解绑邮箱的验证码为：<b>"+code+"</b><br />");
		contentBuffer.append("&nbsp;&nbsp;&nbsp;&nbsp;验证码有效时间为10分钟，请尽快完成验证！");
		mail.setMailContent(contentBuffer.toString());
		// 发送
		SysMailSend.sendMail(mail, true);
		
	}
	
	
	/** 
	 * 解绑验证
	 * @param code
	 * @return 
	 * @create  2016年5月24日 上午11:02:16 lixing
	 * @history  
	 */
	public String unBindCodeCheck(String code){
		String re = "";
		MailboxCheckBean mcb = new MailboxCheckBean();
		mcb.setCode(code);
		List<MailboxCheckBean> beans = mailboxCheckMapper.findByCondtion(mcb);
		if(beans.size() > 0){
			MailboxCheckBean bean = beans.get(0);
			if(bean.getIsDel() == 0){//状态为未删除
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MINUTE, -10);
				Date curDate = calendar.getTime();
				if(bean.getInputtime().compareTo(curDate) == 1){//10分钟之内有效
					TsmEmailConfig entity = new TsmEmailConfig();
		    		entity.setOrgId(bean.getOrgId());
		    		entity.setUserId(bean.getAccount());
		    		entity.setIsDel(0);
		    		List<TsmEmailConfig> tecs = tsmEmailConfigMapper.findByCondtion(entity);
		    		if(tecs != null && tecs.size() > 0){
		    			Map<String, Object> map = new HashMap<String, Object>();
		    			map.put("orgId", bean.getOrgId());
		    			map.put("list", Arrays.asList(tecs.get(0).getId()));
		    			tsmEmailConfigMapper.deleteBatchBy(map);
		    		}
					//设置为无效
					mailboxCheckMapper.delete(bean.getId());
					re = "0";
				}else{
					re = "-1";
				}
			}else{
				re = "-1";
			}
		}else{
			re = "-1";
		}
		return re;
	}
}
