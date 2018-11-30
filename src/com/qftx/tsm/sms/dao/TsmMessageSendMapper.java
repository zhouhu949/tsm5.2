package com.qftx.tsm.sms.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.follow.bean.CustFollowBean;
import com.qftx.tsm.follow.dto.CustFollowDto;
import com.qftx.tsm.sms.bean.TsmMessageSend;
import com.qftx.tsm.sms.dto.TsmMessageSendDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


public interface TsmMessageSendMapper extends BaseDao<TsmMessageSend> {
	
	public List<TsmMessageSendDto> findMsgList(Map<String, Object> map);
	
	public List<TsmMessageSend> findHalfHourExtend(String orgId);
	
	public List<CustFollowDto> findFollowAlert(CustFollowBean follow);

	public void deleteBatchBy(Map<String,Object>map);
	
	/** 查询消息中心 未读消息个数  */
	public TsmMessageSendDto findNoReadByCount(TsmMessageSendDto entity);
	
	/** 查询消息中心 未读消息个数  */
	public TsmMessageSendDto findNoReadByCount2(TsmMessageSendDto entity);
	
	/** 查询对应分类消息列表 */
	public List<TsmMessageSendDto>findMessageByList(TsmMessageSendDto entity);
	
	/** 清空已读消息 */
	public void deleteByMessage(TsmMessageSendDto entity);
	
	/** 查询15天内未读消息条数 */
	public Integer findNotReadNum(Map<String,Object>map);
	
	/** 查询用户未读消息列表 */
	public List<TsmMessageSendDto>findNOReadMessageByList(TsmMessageSendDto entity);
	
	/** 查询版本说明信息 */
	public TsmMessageSendDto  findSysVersion(TsmMessageSendDto entity);
	
	/**修改F2机器人消息状态 */
	public void updateRobotMessageState(TsmMessageSend entity);
}