package com.qftx.tsm.email.util;

import com.qftx.common.exception.SysRunException;
import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import javax.activation.URLDataSource;
import javax.mail.*;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 邮件发送
 * <p>	正确格式如：(之间用逗号","分隔)
 * <br>		郭<guo@163.com>, guo@163.com
 */
public class SysMailSend {

	// 是否忽略错误的邮件格式，为true表示忽略：
	private boolean ignoreErrorMode = false;
	
	// 验证带名称的：如("郭<aaa@163.com>")
	public static final Pattern pattern = Pattern
			.compile("^(\\S+)(<[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+>)$");
	
	/** 
	 * 邮件的正则表达式：(不带姓名，如：guobl@163.com)
	 * <br>
	 */
	public static final String REGEX_MAIL = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
	
	// 验证不带称呼的：如("aaa@163.com")
	public static final Pattern pattern_noName = Pattern.compile(REGEX_MAIL);
	
	
	/**
	 * 发送邮件主方法
	 * @param mail
	 * @param ignoreErrorMode 		是否忽略错误的邮件格式，为true表示忽略
	 * @throws Exception
	 */
	public static void sendMail(SysMail mail, boolean ignoreErrorMode) throws Exception {
		new SysMailSend().send(mail, ignoreErrorMode);
	}
		
	/**
	 * 发送邮件主方法
	 * @param mail
	 * @param ignoreErrorMode 		是否忽略错误的邮件格式，为true表示忽略
	 * @throws Exception
	 */
	private void send(SysMail mail, boolean ignoreErrorMode) throws Exception {
		this.ignoreErrorMode = ignoreErrorMode;
		
		Properties props = this.getProperties();
		
		Session session = Session.getInstance(props);
		//session.setDebug(true);
		
		MimeMessage msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(this.encodeMailUrl(mail.getMailFrom(), true)));
		msg.setSubject(mail.getMailSubject());
		this.addMailUrls(msg, mail.getMailTo(), mail.getMailCc(), mail.getMailBcc());
		
		// 无附件，只是简单的添加，但如果有附件，需创建MimeMultipart：
		if (mail.getMailAttach() == null || mail.getMailAttach().size() == 0) {
			msg.setContent(mail.getMailContent(), "text/html; charset=GBK");
		} else {
			Multipart msgMultipart = new MimeMultipart("mixed");	
			// 加主体内容：
			BodyPart htmlPart = new MimeBodyPart();
			htmlPart.setContent(mail.getMailContent(), "text/html; charset=GBK");					
			// 加附件：			
			for (String[] attachUrl : mail.getMailAttach()) {
				String url = attachUrl[0];
				int ttt=url.lastIndexOf("/");
				String path = url.substring(0,ttt+1);
				URL urlfj=new URL(path+java.net.URLEncoder.encode(url.substring(ttt+1), "UTF-8"));
				URLDataSource ur=new URLDataSource(urlfj); // 远程附件 本地附件使用DataSource
				//注:这里用的参数只能为URL对象,不能为URL字串.
				DataHandler dh=new DataHandler(ur);					
				String urlname=attachUrl[1];
				BodyPart attach = new MimeBodyPart();
				attach.setDataHandler(dh);
				attach.setFileName(MimeUtility.encodeWord(urlname));
				msgMultipart.addBodyPart(attach);
			}
			msgMultipart.addBodyPart(htmlPart);
			msg.setContent(msgMultipart);  
			msg.saveChanges();  	
		}		
		//把实际发送的条数存在mail中：
		mail.setReallySendMailCount(msg.getRecipients(Message.RecipientType.TO).length);
		
		Transport transprot = session.getTransport();
		transprot.connect(mail.getMailHost(), mail.getMailUser(), mail.getMailPassword());
		//transprot.sendMessage(msg, msg.getRecipients(Message.RecipientType.TO));
		transprot.sendMessage(msg, msg.getAllRecipients());
		transprot.close();
	}
	
	
	//-------------------------------------------------------------------------
	// Private Methods：
	//-------------------------------------------------------------------------
	
	// 获取Properties：
	private Properties getProperties() {
		Properties props = new Properties();
		// 设置要求认证，否则会发送失败：
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		return props;
	}
	
	/** 添加收件人、抄送、密送：(这里进行简单的空格判断及过滤) */
	private void addMailUrls(MimeMessage msg, String mailTo, String mailCC, String mailBCC)
			throws AddressException, UnsupportedEncodingException, MessagingException {
		
		// 收件人：
		if (StringUtils.isBlank(mailTo)) {
			throw new SysRunException("收件人不能为空！");
		} else {
			msg.setRecipients(RecipientType.TO, this.getAddresses(mailTo.replaceAll(" ", "")));
		}
		
		// 抄送：
		if (StringUtils.isNotBlank(mailCC)) {
			msg.setRecipients(RecipientType.CC, this.getAddresses(mailCC.replaceAll(" ", "")));
		}
		
		// 密送：
		if (StringUtils.isNotBlank(mailBCC)) {
			msg.setRecipients(RecipientType.BCC, this.getAddresses(mailBCC.replaceAll(" ", "")));
		}
	}
	
	/**
	 * 对Email地址进行中文转码：
	 * @param mailsUrl 				样式如："郭<aaa@163.com>,张三 <adal@126.com>,"
	 */
	private Address[] getAddresses(String mailsUrl) throws AddressException,
			UnsupportedEncodingException {
		
		StringBuffer sb = new StringBuffer();
		for (String mailUrl : mailsUrl.split(",")) {
			mailUrl = this.encodeMailUrl(mailUrl.trim(), false);
			if (mailUrl == null) {
				continue;
			}
			sb.append(mailUrl + ",");
		}
		return InternetAddress.parse(sb.toString());
	}
	
	/** 
	 * 转码及验证：
	 * @param mailUrl				待转码的Mail，如："张三<zhangshan@163.com>"
	 * @param isFormUrl				是否是mailForm的地址，为true表示是mailForm的地址，如不正确则直接抛异常
	 */
	private String encodeMailUrl(String mailUrl, boolean isFormUrl) throws UnsupportedEncodingException {
		// 1.1、与含姓名的Pattern比对：
		Matcher m = pattern.matcher(mailUrl);
		if (m.find()) {
			return MimeUtility.encodeText(m.group(1)) + m.group(2);
		}
		
		// 1.2、与不含姓名的Pattern比对：
		m = pattern_noName.matcher(mailUrl);
		if (m.find()) {
			return mailUrl;
		}
		
		// 2.1、对于如果是mailForm的地址，则直接抛出异常
		if (isFormUrl) {
			throw new SysRunException("发件人的邮箱地址不正确");
		}
		
		// 2.2、否则的话，则应该是mailTo的地址，此时再根据ignoreErrorMode来判断
		if (!ignoreErrorMode) {
			// 如果不忽略错误的邮件格式，则直接抛出异常
			throw new SysRunException("Email地址：“" + mailUrl + "” 格式不正确");
		} else {
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
		String mailUrl = "周<123@123.com>";
		Matcher m = pattern.matcher(mailUrl);
		if (m.find()) {
			System.out.print(m.group(1) +"-----"+ m.group(2));
		}
//		SysMail mail = SysMailUtils.getDefaultMail("","a82327636@126.com", "zhenwenjie..88");
//		mail.setMailTo("82327636@qq.com");
//		mail.setMailSubject("测试测试！");
//		mail.setMailContent("测试测试！");
//		List<String[]>attch = new ArrayList<String[]>();
//		String[]attch1 = new String[]{"http://record.qftx.cn:8070/ds?v=1&p=73e053c3e2e7bf5b81ed719357f43756160304mp34a86c50e58e934b3914","出歌啦.mp3"};
//		attch.add(attch1);
//		mail.setMailAttach(attch);
//		// 发送
//		SysMailSend.sendMail(mail, true);
//		String attchUrl = "http://file.qftx.net/慧营销4.0调整建议-20160220_20160317112959.docx";
//		int ttt=attchUrl.lastIndexOf("/");
//		System.out.println(ttt);
//		String path = attchUrl.substring(0,ttt+1);
//		String urlname=attchUrl.substring(ttt+1);
//		System.out.println(java.net.URLEncoder.encode(urlname, "UTF-8"));
//		System.out.println(path+java.net.URLEncoder.encode(urlname, "UTF-8"));
		
	
	
	}
}
