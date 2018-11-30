package com.qftx.tsm.email.service;

import com.qftx.common.util.CodeUtils;
import com.qftx.common.util.IContants;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.email.bean.TsmEmailConfig;
import com.qftx.tsm.email.bean.TsmEmailSend;
import com.qftx.tsm.email.bean.TsmEmailSendLog;
import com.qftx.tsm.email.dao.TsmEmailConfigMapper;
import com.qftx.tsm.email.dao.TsmEmailSendLogMapper;
import com.qftx.tsm.email.dao.TsmEmailSendMapper;
import com.qftx.tsm.email.dto.EmailRecordDto;
import com.qftx.tsm.email.util.SysMail;
import com.qftx.tsm.email.util.SysMailSend;
import com.qftx.tsm.email.util.SysMailUtils;
import com.qftx.tsm.sys.bean.SysFileBean;
import com.qftx.tsm.sys.dao.SysFileMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class TsmEmailSendService{

	@Autowired
	private TsmEmailSendMapper tsmEmailSendMapper;
	@Autowired
	private TsmEmailConfigMapper tsmEmailConfigMapper;
	@Autowired
	private SysFileMapper  sysFileMapper; 
	
	@Autowired
	private TsmEmailSendLogMapper tsmEmailSendLogMapper;
	public List<TsmEmailSend> getList() {
		return tsmEmailSendMapper.find();
	}
 
	public List<TsmEmailSend> getListByCondtion(TsmEmailSend entity) {
		return tsmEmailSendMapper.findByCondtion(entity);
	} 
	 
	public TsmEmailSend geByCondtion(TsmEmailSend entity) {
		return tsmEmailSendMapper.getByCondtion(entity);
	} 
	
	public void create(TsmEmailSend entity) {
		tsmEmailSendMapper.insert(entity);
	}
 
	public void createBatch(List<TsmEmailSend> entitys) {
		tsmEmailSendMapper.insertBatch(entitys);
	}

	public void modify(TsmEmailSend entity) {
		tsmEmailSendMapper.update(entity);
	}
	 
	public void modifyTrends(TsmEmailSend entity) {
		tsmEmailSendMapper.updateTrends(entity);
	}

	public void remove(String id) {
		tsmEmailSendMapper.delete(id);
	}
 
	public void removeBatch(List<String> ids,String orgId) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("list", ids);
		tsmEmailSendMapper.deleteBatchBy(map);
	}
	
	/** 保存并发送邮件  
	 * @throws Exception */
	public void saveBySend(String userId,String orgId,String accName,String name_email,String content,String title,String fileIds) throws Exception{
		// 查询自己的邮件绑定
		 TsmEmailConfig entity = new TsmEmailConfig(); 
		 entity.setOrgId(orgId);
		 entity.setUserId(userId);
		 entity.setIsDel(0);
		 List<TsmEmailConfig> tecs =  tsmEmailConfigMapper.findByCondtion(entity);
		 if(tecs != null && tecs.size() > 0){
			 TsmEmailConfig tec = tecs.get(0);
			 // 发邮件：
			String pwd = tec.getPassWord();
			String password = new String(CodeUtils.decrypt(AppConstant.EMAIL_PWD_KEY.getBytes(IContants.CHAR_ENCODING), CodeUtils.base64Decode(pwd)),IContants.CHAR_ENCODING);
			SysMail mail = SysMailUtils.getDefaultMail("",tec.getLoginUser(), password);
			mail.setMailTo(name_email);
			mail.setMailSubject(title);
			mail.setMailContent(content);
			// 获取附件
			if(StringUtils.isNotBlank(fileIds)){
				List<String[]>attch = new ArrayList<String[]>();
				String[] ids = fileIds.split(",");
				List<String> fileList= Arrays.asList(ids);
				SysFileBean entity1 = new SysFileBean();
				entity1.setOrgId(orgId);
				for(String id : fileList){
					entity1.setId(id);
					SysFileBean sysFile = sysFileMapper.getByCondtion(entity1);
					if(sysFile != null && StringUtils.isNotBlank(sysFile.getFileUrl())
							&& StringUtils.isNotBlank(sysFile.getFileName())){					
						attch.add(new String[]{sysFile.getFileUrl(),sysFile.getFileName()});
					}					
				}
				mail.setMailAttach(attch);
			}
			// 发送
			SysMailSend.sendMail(mail, true);
								
			// 保存邮件发送 主记录
			String id = SysBaseModelUtil.getModelId();
			TsmEmailSend emailSend = new TsmEmailSend();
			emailSend.setOrgId(orgId);
			emailSend.setId(id);
			emailSend.setInputTime(new Date());
			emailSend.setIsDel(0);
			emailSend.setSendUser(accName);
			emailSend.setTitle(title);
			emailSend.setFileIds(fileIds);
			create(emailSend);
			
			// 保存 邮件记录：
			String[] emails = name_email.split(",");
			// 接收人列表
			List<String> users = Arrays.asList(emails);
			List<TsmEmailSendLog> sendLogs = new ArrayList<TsmEmailSendLog>();
			for(String use :users){
				TsmEmailSendLog sendLog = new TsmEmailSendLog();
				sendLog.setId(SysBaseModelUtil.getModelId());
				sendLog.setEmailSendId(id); // 邮件主记录ID
				sendLog.setOrgId(orgId);
				sendLog.setInputTime(new Date());
				sendLog.setIsDel(0);
				sendLog.setStatus(1);
				sendLog.setEmailReviceUser(use);
				sendLogs.add(sendLog);
			}
			if(sendLogs != null && sendLogs.size() > 0){
				tsmEmailSendLogMapper.insertBatch(sendLogs);
			}	
		 }
		
	}
	
	/** 邮件记录 */
	public List<EmailRecordDto>getSendLogListPage(EmailRecordDto entity){
		return tsmEmailSendMapper.findSendLogListPage(entity);
	}
	
	/** 邮件记录 右侧信息 */
	public List<EmailRecordDto>getSendLogByRight(EmailRecordDto entity){
		return tsmEmailSendMapper.findSendLogByRight(entity);
	}

}
