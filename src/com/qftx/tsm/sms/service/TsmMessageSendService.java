package com.qftx.tsm.sms.service;


import com.qftx.base.auth.service.UserService;
import com.qftx.base.enums.SubmitStatus;
import com.qftx.base.util.BizMessageUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.follow.bean.CustFollowBean;
import com.qftx.tsm.follow.dto.CustFollowDto;
import com.qftx.tsm.message.bean.SendMessageBean;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.sms.bean.TsmMessageSend;
import com.qftx.tsm.sms.dao.TsmMessageSendMapper;
import com.qftx.tsm.sms.dto.TsmMessageSendDto;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;


@Service("tsmMessageSendService")
public class TsmMessageSendService{ 
	private static final String MESSAGE_INSERT_MON_SQL = "INSERT INTO TSM_MESSAGE_SEND(MESSAGE_ID, SEND_DATE, ORG_ID, SUBMIT_STATUS, SEND_FROM, SEND_TO, TITLE, MESSAGE_CONTENT, REMARK, MSG_TYPE, IS_READ,BUSSINESS_ID,MSG_CENTER_CONTENT) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	 @Autowired
	 private UserService userService;
	 @Autowired
	 private TsmMessageService tsmMessageService;
	 @Resource transient private JdbcTemplate jdbcTemplate;
	 private static final ExecutorService threadPool = Executors.newFixedThreadPool(3);

	@Autowired
	private TsmMessageSendMapper tsmMessageSendMapper;
	
	 
	public List<TsmMessageSend> getList() {
		return tsmMessageSendMapper.find();
	}
 
	public List<TsmMessageSend> getListByCondtion(TsmMessageSend entity) {
		return tsmMessageSendMapper.findByCondtion(entity);
	}
	 
	public List<TsmMessageSend> getListPage(TsmMessageSend entity) {
		return tsmMessageSendMapper.findListPage(entity);
	}
	 
	public TsmMessageSend getByPrimaryKey(String id) {
		return tsmMessageSendMapper.getByPrimaryKey(id);
	}
	 
	public void create(TsmMessageSend entity) {
		tsmMessageSendMapper.insert(entity);
	}
 
	public void createBatch(List<TsmMessageSend> entitys) {
		tsmMessageSendMapper.insertBatch(entitys);
	}

	public void modify(TsmMessageSend entity) {
		tsmMessageSendMapper.update(entity);
	}
	 
	public void modifyTrends(TsmMessageSend entity) {
		tsmMessageSendMapper.updateTrends(entity);
	}

	public void remove(String id) {
		tsmMessageSendMapper.delete(id);
	}
 
	public void removeBatch(List<String> ids,String orgId) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("list", ids);
		tsmMessageSendMapper.deleteBatchBy(map);
	}

	public List<TsmMessageSendDto> getMsgList(Map<String, Object> map) {
		return tsmMessageSendMapper.findMsgList(map);
	}

	public List<CustFollowDto> findFollowAlert(CustFollowBean follow) {
		return tsmMessageSendMapper.findFollowAlert(follow);
	}

	public List<TsmMessageSend> getHalfHourExtend(String orgId){
		return tsmMessageSendMapper.findHalfHourExtend(orgId);
	}
	
	public TsmMessageSendDto getNoReadByCount(TsmMessageSendDto entity){
		return tsmMessageSendMapper.findNoReadByCount(entity);
	}
	
	public TsmMessageSendDto getNoReadByCount2(TsmMessageSendDto entity){
		return tsmMessageSendMapper.findNoReadByCount2(entity);
	}
	
	/** 查询对应分类消息列表 */
	public List<TsmMessageSendDto>getMessageByList(TsmMessageSendDto entity){
		return tsmMessageSendMapper.findMessageByList(entity);
	}
	
	/** 清空已读消息 */
	public void removeByMessage(TsmMessageSendDto entity){
		tsmMessageSendMapper.deleteByMessage(entity);
	}
	
	/** 查询15天内未读消息条数 */
	public Integer findNotReadNum(Map<String,Object>map){
		return tsmMessageSendMapper.findNotReadNum(map);
	}
	
	
	/** 查询用户未读消息列表 */
	public List<TsmMessageSendDto>findNOReadMessageByList(TsmMessageSendDto entity){
		return tsmMessageSendMapper.findNOReadMessageByList(entity);
	}
	public TsmMessageSendDto  findSysVersion(TsmMessageSendDto entity){
		return tsmMessageSendMapper.findSysVersion(entity);
	}
	
	public void updateRobotMessageState(String orgId,String sendTo ){
		TsmMessageSend bean  =  new TsmMessageSend();
		bean.setOrgId(orgId);
		bean.setSendTo(sendTo);
		tsmMessageSendMapper.updateRobotMessageState(bean);
	}
	
     /*
      * 系统消息
      * */
	public void addSysMessage(String orgId,String sendFromAccount,String sendToAccount,String title,String content) throws Exception{
		try{
//			logger.debug("orgId:"+orgId+"___"+"account:"+account+"___"+"title:"+title+"___"+"content:"+content+"___"+"type:"+type);
			String announceId = SysBaseModelUtil.getModelId();
			//插入通知消息表
			TsmMessageSend msgSend = null;
			final List<TsmMessageSend> msgSends = new ArrayList<TsmMessageSend>();
			
				msgSend = new TsmMessageSend();
				msgSend.setMessageId(StringUtils.getDateAndRandStr(15));
				msgSend.setBusinessId(announceId); // 公告ID
				msgSend.setSendFrom(sendFromAccount);
				msgSend.setSendTo(sendToAccount);
				msgSend.setTitle(title);
				msgSend.setMsgType(AppConstant.MSG_TYPE_SYS);
				msgSend.setMessageContent("消息：" +title);
				msgSend.setMsgCenterContent(content);
				msgSend.setIsRead(AppConstant.MSG_IS_NOT_READ);
				msgSend.setSubmitStatus(SubmitStatus.SUBMIT_SUCC.getStatus());
				msgSend.setSendDate(new Date());
				msgSend.setOrgId(orgId);
				msgSend.setRemark("");
				msgSends.add(msgSend);
			
			if (msgSends.size() > 0) {
				jdbcTemplate.batchUpdate(MESSAGE_INSERT_MON_SQL, new BatchPreparedStatementSetter() {
					//为prepared statement设置参数。这个方法将在整个过程中被调用的次数
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, msgSends.get(0).getMessageId());
						ps.setTimestamp(2, new Timestamp(msgSends.get(0).getSendDate().getTime()));
						ps.setString(3, msgSends.get(0).getOrgId());
						ps.setString(4, msgSends.get(0).getSubmitStatus());
						ps.setString(5, msgSends.get(0).getSendFrom());
						ps.setString(6, msgSends.get(0).getSendTo());
						ps.setString(7, msgSends.get(0).getTitle());
						ps.setString(8, msgSends.get(0).getMessageContent());
						ps.setString(9, msgSends.get(0).getRemark());
						ps.setInt(10, msgSends.get(0).getMsgType());
						ps.setInt(11, msgSends.get(0).getIsRead());
						ps.setString(12, msgSends.get(0).getBusinessId());
						ps.setString(13, msgSends.get(0).getMsgCenterContent());

					}
					//返回更新的结果集条数
					public int getBatchSize() {
						return msgSends.size();
					}
					});
					for (final TsmMessageSend send : msgSends) {
						threadPool.submit(new Runnable() {
							public void run() {
								BizMessageUtil.sendBizMsg(send,"8");
								// 发送15天内未读消息条数
								tsmMessageService.sendNotReadNum(send.getSendTo(), send.getOrgId());
							}
						});
					}
			}


		}catch(Exception e) {
			throw new SysRunException(e);

		}

	}
}
