package com.qftx.tsm.email.util;

import com.qftx.common.exception.SysRunException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import java.util.ArrayList;
import java.util.Properties;
public class SysMailUtils {
	private static Logger logger = Logger.getLogger(SysMailUtils.class);
	/**
	 * 根据3个参数拼接一下之后，返回一个Mail对象
	 * @param mailDescr				发件人称呼 
	 * @param mailName				邮箱名 
	 * @param mailPwd				邮箱密码 
	 */
	public static SysMail getDefaultMail(String mailDescr, String mailName, String mailPwd) {
		if (StringUtils.isBlank(mailName) || StringUtils.isBlank(mailPwd)) {
			throw new SysRunException("邮箱名 或 邮箱密码为空！");
		}
		
		if (!mailName.matches(SysMailSend.REGEX_MAIL)) {
			throw new SysRunException("邮箱名格式不正确！");
		}
		
		SysMail mail = new SysMail();
		
		int index = mailName.indexOf("@");
		String mailUser = mailName.substring(0, index);
		String mailHost = mailName.substring(index + 1, mailName.indexOf("."));
		
		mail.setMailHost("smtp." + mailHost + ".com");
		if(StringUtils.isNotBlank(mailDescr)){
			mail.setMailFrom(mailDescr + "<" + mailName + ">");
		}else{
			mail.setMailFrom(mailName);
		}
		
		mail.setMailUser(mailUser);
		mail.setMailPassword(mailPwd);
		
		mail.setMailAttach(new ArrayList<String[]>());
		
		return mail;
	}
	
	
	/** 
	 * 验证邮箱密码
	 * @param email
	 * @param pwd
	 * @return 
	 * @create  2016年3月3日 下午7:12:25 lixing
	 * @history  
	 */
	public static boolean checkMailPwd(String email,String pwd){
		boolean flag = true;
		Transport transport = null;
		try {
			int index = email.indexOf("@");
			String mailHost = email.substring(index + 1, email.indexOf("."));
			String host = "smtp." + mailHost + ".com";
			Properties props = new Properties();
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");
			if(email.toLowerCase().endsWith("@qq.com")){
				props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		        props.setProperty("mail.smtp.socketFactory.fallback", "false");
		        props.setProperty("mail.smtp.port", "465");
		        props.setProperty("mail.smtp.socketFactory.port", "465");
			}
			SmtpAuth smtpAuth = new SmtpAuth(email, pwd);
			Session session = Session.getDefaultInstance(props, smtpAuth);
			transport = session.getTransport("smtp");
			transport.connect(host, email, pwd);
		} catch(AuthenticationFailedException e1){
			logger.error("邮箱密码不正确!",e1);
			flag = false;
		}catch (Exception e) {
			logger.error("验证邮箱密码发生未知错误!",e);
			flag = false;
		}finally{
			if(transport != null){
				try {
					transport.close();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}
}
