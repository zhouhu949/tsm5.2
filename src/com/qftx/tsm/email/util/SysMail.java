package com.qftx.tsm.email.util;

import java.util.List;

public class SysMail {
	
	private String mailHost;				//发送邮件的服务器 ("smtp.163.com")
	private String mailUser;				//用户名("user")
	private String mailPassword;			//用户密码("password")
	private String mailFrom;				//发件人("user@163.com")
	private String mailTo;					//收件人("张三 <zhangshan@qq.com>,李四<ls@163.com>")
	private String mailCc;					//抄送
	private String mailBcc;					//密送
	private String mailSubject;				//主题("发给张三的email")
	private String mailContent;				//内容体
	private int reallySendMailCount;		//实际发送的邮件数
	private List<String[]> mailAttach;		//附件的文件名列表(含路径)

	
	public String getMailHost() {
		return mailHost;
	}

	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getMailTo() {
		return mailTo;
	}

	public void setMailTo(String mailTo) {
		this.mailTo = mailTo;
	}

	public String getMailCc() {
		return mailCc;
	}

	public void setMailCc(String mailCc) {
		this.mailCc = mailCc;
	}

	public String getMailBcc() {
		return mailBcc;
	}

	public void setMailBcc(String mailBcc) {
		this.mailBcc = mailBcc;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailContent() {
		return mailContent;
	}

	public void setMailContent(String mailContent) {
		this.mailContent = mailContent;
	}

	public int getReallySendMailCount() {
		return reallySendMailCount;
	}

	public void setReallySendMailCount(int reallySendMailCount) {
		this.reallySendMailCount = reallySendMailCount;
	}

	public List<String[]> getMailAttach() {
		return mailAttach;
	}

	public void setMailAttach(List<String[]> mailAttach) {
		this.mailAttach = mailAttach;
	}

	
}
