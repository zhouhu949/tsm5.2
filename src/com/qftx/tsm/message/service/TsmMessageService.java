package com.qftx.tsm.message.service;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dao.UserMapper;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.auth.service.RoleResourceService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.enums.SubmitStatus;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.BizMessageUtil;
import com.qftx.base.util.ExecutorUtils;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.contract.bean.ContractOrderBean;
import com.qftx.tsm.contract.dao.ContractOrderMapper;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.follow.bean.TsmCustExtension;
import com.qftx.tsm.sms.bean.SendMessageToApp;
import com.qftx.tsm.sms.bean.TsmMessageSend;
import com.qftx.tsm.sms.dao.TsmMessageSendMapper;
import com.qftx.tsm.sms.dto.TsmMessageSendDto;
import com.qftx.tsm.sms.service.TsmSendMessageToAppService;
import com.qftx.tsm.sys.bean.TsmCustReview;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

import javax.servlet.http.HttpServletRequest;


@Service
public class TsmMessageService{
	private static Logger logger = Logger.getLogger(TsmMessageService.class);
	@Autowired
	private TsmMessageSendMapper tsmMessageSendMapper;
	@Autowired
	private ResCustInfoMapper resCustInfoMapper;
	@Autowired
	private OrgGroupUserService orgGroupUserService;
	@Autowired
	private ContractOrderMapper contractOrderMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired private UserService userService;
	@Autowired private RoleResourceService roleResourceService;
	@Autowired private TsmSendMessageToAppService tsmSendMessageToAppService;
	
	/** 【工作点评】消息发送 */
	public void createReviewMessage(final TsmCustReview entity) {
		final ShiroUser user = ShiroUtil.getShiroUser();
		final Calendar cal = Calendar.getInstance();
		ExecutorUtils.THREAD_POOL.submit(new Runnable() {
			public void run() {
				try{
					// 查询资源拥有者
					ResCustInfoBean rcib =resCustInfoMapper.getByPrimaryKey(user.getOrgId(), entity.getCustId());
					// 记录消息
					if(rcib != null){
						// 消息中心内容
						StringBuilder builder = new StringBuilder(128);
						builder.append(DateUtil.format(cal.getTime(), DateUtil.localPattern))
								.append("，您收到一条来自于").append(user.getName())
								.append("的工作点评！");
						// 小助手内容
						StringBuilder builder1 = new StringBuilder(128);
						builder1.append(DateUtil.format(cal.getTime(), DateUtil.localPattern))
								.append("，您收到一条来自于").append(user.getName())
								.append("的工作点评！");					
						TsmMessageSend msgSend = getMessageSend(entity.getReviewId(),
								entity.getCustId(),entity.getReviewAcc(),rcib.getOwnerAcc(),user.getOrgId(),"工作点评",
								AppConstant.MSG_TYPE_REVIEW_WORK,builder.toString(),builder1.toString());
						// 冗余点评内容
						msgSend.setContent(entity.getRevComment());
						// 保存至数据库
						tsmMessageSendMapper.insert(msgSend);
						// 发点评消息
						BizMessageUtil.sendBizMsg(msgSend,"2");
						// 发送15天内未读消息
						sendNotReadNum(msgSend.getSendTo(),msgSend.getOrgId());
						
						String isopen = ConfigInfoUtils.getStringValue("send_message_to_app_isopen");
						if(isopen=="0"||"0".equals(isopen)){
							StringBuilder builder3 = new StringBuilder(128);
							builder3
							.append(StringUtils.isNotBlank(entity.getReviewName()) ? entity.getReviewName() : entity.getReviewAcc()).append("于")
							.append(cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE))
							.append("点评了你的工作！");
						//向app发送公告
						SendMessageToApp appbean=new SendMessageToApp();
						appbean.setOrgId(user.getOrgId());
						appbean.setUserAccount(rcib.getOwnerAcc());
						appbean.setTitle("工作点评提醒");
						appbean.setSummary(builder3.toString());
						appbean.setLevel("3");
						appbean.setType("8");
						appbean.setObj(entity.getReviewId());
						tsmSendMessageToAppService.sendMessage(appbean);
						}
					}	
				}catch(Exception e){
					logger.error("工作点评异常！！", e);
				}
							
			}
		});		
	}
	
	/** 【计划点评】消息发送
	 * 		@param id 接收人ID
	 * 		@param content 点评内容
	 * 		@param revId 点评ID
	 *  */
	public void createPlanReviewMessage(final String id,final String content,final String revId) {
		final ShiroUser user = ShiroUtil.getShiroUser();
		final Calendar cal = Calendar.getInstance();
		ExecutorUtils.THREAD_POOL.submit(new Runnable() {
			public void run() {
				// 查询接收人信息
				User entity = new User();
				entity.setUserId(id);
				entity.setOrgId(user.getOrgId());
				entity = userMapper.getByCondtion(entity);
				// 记录消息
				if(entity != null){
					// 消息中心内容
					StringBuilder builder = new StringBuilder(128);
					builder.append(DateUtil.format(cal.getTime(), DateUtil.localPattern))
							.append("，您收到一条来自于").append(user.getName())
							.append("的计划点评！");
					// 小助手内容
					StringBuilder builder1 = new StringBuilder(128);
					builder1.append(DateUtil.format(cal.getTime(), DateUtil.localPattern))
							.append("，您收到一条来自于").append(user.getName())
							.append("的计划点评！");					
					TsmMessageSend msgSend = getMessageSend(revId,"",user.getAccount(),entity.getUserName(),user.getOrgId(),"计划点评",
							AppConstant.MSG_TYPE_REVIEW_MONTHLY,builder.toString(),builder1.toString());
					// 冗余点评内容
					msgSend.setContent(content);
					// 保存至数据库
					tsmMessageSendMapper.insert(msgSend);
					// 发点评消息
					BizMessageUtil.sendBizMsg(msgSend,"2");
					// 发送15天内未读消息
					sendNotReadNum(msgSend.getSendTo(),msgSend.getOrgId());
				}				
			}
		});		
	}
	
	/** 【延期审核】消息发送 */
	public void createAuditDelayMessage(final TsmCustExtension entity) {
		final ShiroUser user = ShiroUtil.getShiroUser();
		final Calendar cal = Calendar.getInstance();
		ExecutorUtils.THREAD_POOL.submit(new Runnable() {
			public void run() {
				// 查询 审核人
				Map<String,Object>map = new HashMap<String, Object>();
				map.put("orgId", user.getOrgId());
				map.put("account",entity.getApplicantExtensionId());
				List<String>accList = orgGroupUserService.getShareAccsByAcc(map);
				if(accList != null && accList.size() >0){
					ResCustInfoBean rcib =resCustInfoMapper.getByPrimaryKey(user.getOrgId(), entity.getCustId());
					// 记录消息
					for(String toAcc: accList){
						// 查询根据账号查询用户ID
						User uentity = new User();
						uentity.setOrgId(user.getOrgId());
						uentity.setUserAccount(toAcc);
						uentity = userService.getByCondtion(uentity);
						if(uentity != null){
							// 查询是否拥有审核权限
							if(roleResourceService.confirmResIsExist(user.getOrgId(), uentity.getUserId(), AppConstant.DEFERR_AUDIT_AUTH_ID)){
								// 消息中心内容
								StringBuilder builder = new StringBuilder(128);
								builder.append("您收到来自").append(entity.getApplicantExtensionName())
										.append("对客户").append(rcib.getName()).append("的延期审核申请，延期天数为：")
										.append(entity.getDaysExtension()).append("天");
								// 小助手内容
								StringBuilder builder1 = new StringBuilder(128);
								builder1.append(DateUtil.format(cal.getTime(), DateUtil.localPattern)).append("您收到一条新的延期审核信息");
								
								TsmMessageSend msgSend = getMessageSend(entity.getExtensionId(),
										entity.getCustId(),entity.getApplicantExtensionId(),toAcc,user.getOrgId(),"延期审核",
										AppConstant.MSG_TYPE_AUDIT_DELAY,builder.toString(),builder1.toString());
								// 保存至数据库
								tsmMessageSendMapper.insert(msgSend);
								// 发消息
								BizMessageUtil.sendBizMsg(msgSend,"6");
								// 发送15天内未读消息
								sendNotReadNum(msgSend.getSendTo(),msgSend.getOrgId());
							}
						}						
					}				
				}				
			}
		});		
	}
 
	/**  【订单审核】消息发送 */
	public void createAuditOrderMessage(final String orderId,final String orgId,final String account,final String name) {
		ExecutorUtils.THREAD_POOL.submit(new Runnable() {
			public void run() {				
				Calendar cal = Calendar.getInstance();
				logger.debug(DateUtil.format(cal.getTime(), DateUtil.localPattern)+"【订单审核】消息发送,审核人查询"+account);
				// 查询 审核人
				Map<String,Object>map = new HashMap<String, Object>();
				map.put("orgId", orgId);
				map.put("account",account);
				List<String>accList = orgGroupUserService.getShareAccsByAcc(map);
				// 查询订单信息
				ContractOrderBean orderBean = contractOrderMapper.getOrderInfoByIdAndOrg(orderId, orgId);
				logger.debug("【订单审核】消息发送,审核人size"+accList.size());
				if(accList != null && accList.size() >0){			
					// 记录消息
					for(String toAcc: accList){
						// 查询根据账号查询用户ID
						User uentity = new User();
						uentity.setOrgId(orgId);
						uentity.setUserAccount(toAcc);
						uentity = userService.getByCondtion(uentity);		
						if(uentity != null){
							// 查询是否拥有审核权限
							if(roleResourceService.confirmResIsExist(orgId, uentity.getUserId(), AppConstant.ORDER_AUDIT_AUTH_ID)){
								logger.debug("【订单审核】消息发送,审核人"+toAcc);
								// 消息中心内容
								StringBuilder builder = new StringBuilder(128);
								builder.append("您收到来自").append(name)
										.append("的订单审核申请，订单号为：")
										.append(orderBean.getCode());
								// 小助手内容
								StringBuilder builder1 = new StringBuilder(128);
								builder1.append(DateUtil.format(cal.getTime(), DateUtil.localPattern)).append("您收到一条新的订单审核信息");
							
								TsmMessageSend msgSend = getMessageSend(orderId,"",
										account,toAcc,orgId,"订单审核",
										AppConstant.MSG_TYPE_AUDIT_ORDER,builder.toString(),builder1.toString());
								// 保存至数据库
								tsmMessageSendMapper.insert(msgSend);
								// 发消息
								BizMessageUtil.sendBizMsg(msgSend,"6");
								// 发送15天内未读消息
								sendNotReadNum(msgSend.getSendTo(),msgSend.getOrgId());
							}
						}										
					}				
				}				
			}
		});		
	}
	
	/**  	【个人计划审核】消息发送 
	 * 	 	@param planId 计划ID
	 *  	@param type 16: 日计划 15：月计划
	 * */
	public void createAuditDailyMonthlyMessage(final String planId,final Integer type) {
		final ShiroUser user = ShiroUtil.getShiroUser();
		ExecutorUtils.THREAD_POOL.submit(new Runnable() {
			public void run() {
				// 查询 审核人
				Map<String,Object>map = new HashMap<String, Object>();
				map.put("orgId", user.getOrgId());
				map.put("account",user.getAccount());
				List<String>accList = orgGroupUserService.getShareAccsByAcc(map);
				if(accList != null && accList.size() >0){
					String title="";
					Integer msgType;
					boolean ifMonth=false;
					if(type == 16){
						title = "今日计划审核";
						msgType = AppConstant.MSG_TYPE_AUDIT_DAILY;
					}else{
						title = "月计划审核";
						msgType = AppConstant.MSG_TYPE_AUDIT_MONTHLY;
						ifMonth=true;
					}
					// 记录消息
					for(String toAcc: accList){
						// 查询根据账号查询用户ID
						User uentity = new User();
						uentity.setOrgId(user.getOrgId());
						uentity.setUserAccount(toAcc);
						uentity = userService.getByCondtion(uentity);		
						if(uentity != null){
							// 查询是否拥有审核权限
							if(roleResourceService.confirmResIsExist(user.getOrgId(), uentity.getUserId(), ifMonth?AppConstant.PLAN_TEAM_EDIT_ID:AppConstant.PLAN_AUDIT_AUTH_ID)){
								// 消息中心内容
								StringBuilder builder = new StringBuilder(128);
								builder.append("您收到来自").append(user.getName())
										.append("的").append(title).append("申请");
								// 小助手内容
								StringBuilder builder1 = new StringBuilder(128);
								builder1.append("您收到来自").append(user.getName())
								.append("的").append(title).append("申请");			
								TsmMessageSend msgSend = getMessageSend(planId,"",
										user.getAccount(),toAcc,user.getOrgId(),title,
										msgType,builder.toString(),builder1.toString());
								// 保存至数据库
								tsmMessageSendMapper.insert(msgSend);
								// 发消息
								BizMessageUtil.sendBizMsg(msgSend,"6");
								// 发送15天内未读消息
								sendNotReadNum(msgSend.getSendTo(),msgSend.getOrgId());
							}
						}
												
					}				
				}				
			}
		});		
	}
	
	/**  	【团队计划审核】消息发送 
	 * 	 	@param planId 计划ID
	 * 		@param groupId 部门ID
	 * 		@param groupName 部门名称
	 *  	@param type 19: 小组月计划审核 20：部门月计划审核
	 * */
	public void createAuditDailyGroupMonthlyMessage(final String planId,final String groupId,final String groupName,final Integer type) {
		final ShiroUser user = ShiroUtil.getShiroUser();
		ExecutorUtils.THREAD_POOL.submit(new Runnable() {
			public void run() {
				// 查询 审核人
				Map<String,Object>map = new HashMap<String, Object>();
				map.put("orgId", user.getOrgId());
				map.put("groupId",groupId);
				List<String>accList = orgGroupUserService.getShareAccsByGroupId(map);
				if(accList != null && accList.size() >0){
					
					String title="";
					Integer msgType;
					if(type == 19){
						title = "小组月计划审核";
						msgType = AppConstant.MSG_TYPE_AUDIT_GROUP_MONTHLY;
					}else{
						title = "部门月计划审核";
						msgType = AppConstant.MSG_TYPE_AUDIT_DEPAT_MONTHLY;
					}
					// 记录消息
					for(String toAcc: accList){
						// 查询根据账号查询用户ID
						User uentity = new User();
						uentity.setOrgId(user.getOrgId());
						uentity.setUserAccount(toAcc);
						uentity = userService.getByCondtion(uentity);						
						// 查询是否拥有审核权限
						if(roleResourceService.confirmResIsExist(user.getOrgId(), uentity.getUserId(),AppConstant.PLAN_GROUP_EDIT_ID)){
							// 消息中心内容
							StringBuilder builder = new StringBuilder(128);
							builder.append("您收到来自").append(groupName)
									.append("的").append(title).append("申请");
							// 小助手内容
							StringBuilder builder1 = new StringBuilder(128);
							builder1.append("您收到来自").append(groupName)
							.append("的").append(title).append("申请");			
							TsmMessageSend msgSend = getMessageSend(planId,"",
									user.getAccount(),toAcc,user.getOrgId(),title,
									msgType,builder.toString(),builder1.toString());
							// 保存至数据库
							tsmMessageSendMapper.insert(msgSend);
							// 发消息
							BizMessageUtil.sendBizMsg(msgSend,"6");
							// 发送15天内未读消息
							sendNotReadNum(msgSend.getSendTo(),msgSend.getOrgId());
						}
					}				
				}				
			}
		});		
	}
	
//	// 发送弹幕消息
//	public void sendBarrage(String content,String orgId,String account,String name){
//		User entity = new User();
//		entity.setOrgId(orgId);
//		// 单位下面所有人员 都可以接收到信息
//		List<User>users = userService.getListByCondtion(entity);
//		List<TsmMessageSend> sends = new ArrayList<TsmMessageSend>();
//		if(users != null && users.size() > 0){
//			for(User u : users){
////				if(!u.getUserAccount().equals(account) 
//////						&& u.getIsBarOpen()!=null && u.getIsBarOpen() == 1
////						){ // 发布者不需要收到消息&弹幕设置开启才能收到弹幕消息
//					TsmMessageSend msgSend = new TsmMessageSend();
//					msgSend.setMessageId(StringUtils.getDateAndRandStr(15));
//					msgSend.setSendTo(u.getUserAccount()); // 接收者
//					msgSend.setSendFrom(name); // 发送者
//					msgSend.setTitle("弹幕！");
//					msgSend.setSubmitStatus(SubmitStatus.SUBMIT_SUCC.getStatus());
//					msgSend.setSendDate(new Date());
//					msgSend.setMessageContent(content);
//					sends.add(msgSend);						
////				}						
//			}			
//			// 发送消息
//			if(sends != null && sends.size()>0){
//				for (final TsmMessageSend send : sends) {
//					ExecutorUtils.THREAD_POOL.submit(new Runnable() {
//						public void run() {
//							BizMessageUtil.sendBarMsg(send);
//						}
//					});
//				}
//			}				
//		}
//	}
	
	// 发送弹幕消息
	public List<TsmMessageSend> sendBarrage(String content,String orgId,String account,String name){
		User entity = new User();
		entity.setOrgId(orgId);
		// 单位下面所有人员 都可以接收到信息
		List<User>users = userService.getListByCondtion(entity);
		List<TsmMessageSend> sends = new ArrayList<TsmMessageSend>();
		if(users != null && users.size() > 0){
			for(User u : users){
//				if(!u.getUserAccount().equals(account) 
////						&& u.getIsBarOpen()!=null && u.getIsBarOpen() == 1
//						){ // 发布者不需要收到消息&弹幕设置开启才能收到弹幕消息
					TsmMessageSend msgSend = new TsmMessageSend();
					msgSend.setMessageId(StringUtils.getDateAndRandStr(15));
					msgSend.setSendTo(u.getUserAccount()); // 接收者
					msgSend.setSendFrom(name); // 发送者
					msgSend.setTitle("弹幕！");
					msgSend.setSubmitStatus(SubmitStatus.SUBMIT_SUCC.getStatus());
					msgSend.setSendDate(new Date());
					msgSend.setMessageContent(content);
					sends.add(msgSend);						
//				}						
			}	
			// 发送消息
//			if(sends != null && sends.size()>0){
//				for (final TsmMessageSend send : sends) {
//					ExecutorUtils.THREAD_POOL.submit(new Runnable() {
//						public void run() {
//							BizMessageUtil.sendBarMsg(send);
//						}
//					});
//				}
//			}				
		}
		return sends;
	}
	
	// 发送弹幕消息,V5.0,弹幕消息
	public List<TsmMessageSend> sendBarrageMessage(String content,String orgId,String account,String name,String id){
		User entity = new User();
		entity.setOrgId(orgId);
		// 单位下面所有人员 都可以接收到信息
		List<User>users = userService.getListByCondtion(entity);
		List<TsmMessageSend> sends = new ArrayList<TsmMessageSend>();
		if(users != null && users.size() > 0){
			for(User u : users){
//				if(u.getIsBarOpen()!=null && u.getIsBarOpen() == 1
//			){ // 发布者不需要收到消息&弹幕设置开启才能收到弹幕消息
					TsmMessageSend msgSend = new TsmMessageSend();
					msgSend.setMessageId(id);
					msgSend.setSendTo(u.getUserAccount()); // 接收者
					msgSend.setSendFrom(name); // 发送者
					msgSend.setTitle("弹幕！");
					msgSend.setSubmitStatus(SubmitStatus.SUBMIT_SUCC.getStatus());
					msgSend.setSendDate(new Date());
					msgSend.setMessageContent(content);
					sends.add(msgSend);	
//			}
			}	
			// 发送消息
			if(sends != null && sends.size()>0){
				for (final TsmMessageSend send : sends) {
					ExecutorUtils.THREAD_POOL.submit(new Runnable() {
						public void run() {
							BizMessageUtil.sendBarMsg(send);
						}
					});
				}
			}				
		}
		return sends;
	}
	
	
	// 发送弹幕消息,V5.0
	public List<TsmMessageSend> sendBarrage_new(String content,String orgId,String account,String name){
		User entity = new User();
		entity.setOrgId(orgId);
		// 单位下面所有人员 都可以接收到信息
		List<User>users = userService.getListByCondtion(entity);
		List<TsmMessageSend> sends = new ArrayList<TsmMessageSend>();
		if(users != null && users.size() > 0){
			for(User u : users){
//				if(u.getIsBarOpen()!=null && u.getIsBarOpen() == 1
//			){ // 发布者不需要收到消息&弹幕设置开启才能收到弹幕消息
					TsmMessageSend msgSend = new TsmMessageSend();
					msgSend.setMessageId(StringUtils.getDateAndRandStr(15));
					msgSend.setSendTo(u.getUserAccount()); // 接收者
					msgSend.setSendFrom(name); // 发送者
					msgSend.setTitle("弹幕！");
					msgSend.setSubmitStatus(SubmitStatus.SUBMIT_SUCC.getStatus());
					msgSend.setSendDate(new Date());
					msgSend.setMessageContent(content);
					sends.add(msgSend);	
//			}
			}	
			// 发送消息
			if(sends != null && sends.size()>0){
				for (final TsmMessageSend send : sends) {
					ExecutorUtils.THREAD_POOL.submit(new Runnable() {
						public void run() {
							BizMessageUtil.sendBarMsg(send);
						}
					});
				}
			}				
		}
		return sends;
	}
	
	// 发送黑名单消息,V5.0
		public List<TsmMessageSend> sendBlack(String content,String orgId,String account,String name){
			User entity = new User();
			entity.setOrgId(orgId);
			// 单位下面所有人员 都可以接收到信息
			List<User>users = userService.getListByCondtion(entity);
			List<TsmMessageSend> sends = new ArrayList<TsmMessageSend>();
			if(users != null && users.size() > 0){
				for(User u : users){
				 // 发布者不需要收到消息&弹幕设置开启才能收到弹幕消息
						TsmMessageSend msgSend = new TsmMessageSend();
						msgSend.setMessageId(StringUtils.getDateAndRandStr(15));
						msgSend.setSendTo(u.getUserAccount()); // 接收者
						msgSend.setSendFrom(name); // 发送者
						msgSend.setTitle("黑名单！");
						msgSend.setSubmitStatus(SubmitStatus.SUBMIT_SUCC.getStatus());
						msgSend.setSendDate(new Date());
						msgSend.setMessageContent(content);
						sends.add(msgSend);	
				}
					
				// 发送消息
				if(sends != null && sends.size()>0){
					for (final TsmMessageSend send : sends) {
						ExecutorUtils.THREAD_POOL.submit(new Runnable() {
							public void run() {
								BizMessageUtil.sendBlackMsg(send);
							}
						});
					}
				}				
			}
			return sends;
		}
		
	
	// 发送未读消息条数
	public void sendNotReadNum(String sendTo,String orgId){
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("sendTo", sendTo);
		Integer notReadNum = tsmMessageSendMapper.findNotReadNum(map);
		logger.info(sendTo+"未读消息条数："+notReadNum);
		BizMessageUtil.sendNotReadNumMsg(StringUtils.getDateAndRandStr(15),sendTo,notReadNum == null ? "0" : notReadNum.toString());
	}
	
	// 发送未读消息条数
		public void sendBizNotReadNum(String sendTo,String orgId){
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("sendTo", sendTo);
			Integer notReadNum = tsmMessageSendMapper.findNotReadNum(map);
			logger.info(sendTo+"未读消息条数："+notReadNum);
			BizMessageUtil.sendBizNotReadNumMsg(StringUtils.getDateAndRandStr(15),sendTo,notReadNum == null ? "0" : notReadNum.toString());
		}
	
	/**
	 * 组装消息实体 
	 * @param businessId 						业务ID
	 * @param remark							备注
	 * @param sendFrom						发送账号
	 * @param sendTo							接收账号
	 * @param orgId								单位ID
	 * @param title								标题
	 * @param msgType						消息类型
	 * @param msgCenterContent			消息中心内容
	 * @param messageContent			小助手内容
	 * @return 
	 * @create  2016-1-27 上午10:42:39 zwj
	 * @history  4.x
	 */
	private TsmMessageSend getMessageSend(String businessId,String remark,String sendFrom,String sendTo,
							String orgId,String title,Integer msgType,String msgCenterContent,String messageContent){
		TsmMessageSend msgSend = new TsmMessageSend();
		msgSend.setMessageId(StringUtils.getDateAndRandStr(15));
		msgSend.setBusinessId(businessId); // 业务ID
		msgSend.setSendTo(sendTo); // 接收者
		msgSend.setRemark(remark); // 备注
		msgSend.setSendFrom(sendFrom); // 发送者
		msgSend.setOrgId(orgId);
		msgSend.setTitle(title);
		msgSend.setIsRead(AppConstant.MSG_IS_NOT_READ);
		msgSend.setSubmitStatus(SubmitStatus.SUBMIT_SUCC.getStatus());
		msgSend.setSendDate(new Date());
		msgSend.setMsgType(msgType);
		msgSend.setMsgCenterContent(msgCenterContent);
		msgSend.setMessageContent(messageContent);
		return msgSend;
	}
	
	
	public void createBatch(List<TsmMessageSend> entitys) {
		tsmMessageSendMapper.insertBatch(entitys);
	}
	
	
	/**  【qupai放款管理  审核提醒】消息发送 */
	public void createQupaiMessage(final String leadId,final String orgId,final String account , final List<String> accList ,final String message) {
		ExecutorUtils.THREAD_POOL.submit(new Runnable() {
			public void run() {				
				Calendar cal = Calendar.getInstance();
				logger.debug(DateUtil.format(cal.getTime(), DateUtil.localPattern)+"【放款审核】消息发送,"+account);

				logger.debug("【放款审核】消息发送,审核人size"+accList.size());
				if(accList != null && accList.size() >0){			
					// 记录消息
					for(String toAcc: accList){
//						// 查询根据账号查询用户ID
//						User uentity = new User();
//						uentity.setOrgId(orgId);
//						uentity.setUserAccount(toAcc);
//						uentity = userService.getByCondtion(uentity);		
//						if(uentity != null){
								logger.debug("【放款审核】消息发送,审核人"+toAcc);
								// 小助手内容
								StringBuilder builder1 = new StringBuilder(128);
								builder1.append(DateUtil.format(cal.getTime(), DateUtil.localPattern)).append("您收到一条新的放款审核信息");
							
								TsmMessageSend msgSend = getMessageSend(leadId,"",
										account,toAcc,orgId,"放款审核",
										AppConstant.MSG_TYPE_QUPAI_ORDER,message,builder1.toString());
								// 保存至数据库
								tsmMessageSendMapper.insert(msgSend);
								// 发消息
								BizMessageUtil.sendBizMsg(msgSend,"6");
								// 发送15天内未读消息
								sendNotReadNum(msgSend.getSendTo(),msgSend.getOrgId());
							
//						}										
					}				
				}				
			}
		});		
	}

	
	/**  【qupai放款确认  确认放款】消息发送 */
	public void createQupaiQtMessage(final String leadId,final String orgId,final String account , final List<String> accList ,final String message) {
		ExecutorUtils.THREAD_POOL.submit(new Runnable() {
			public void run() {				
				Calendar cal = Calendar.getInstance();
				logger.debug(DateUtil.format(cal.getTime(), DateUtil.localPattern)+"【放款确认】消息发送,"+account);

				logger.debug("【放款确认】消息发送,审核人size"+accList.size());
				if(accList != null && accList.size() >0){			
					// 记录消息
					for(String toAcc: accList){
								logger.debug("【放款确认】消息发送,审核人"+toAcc);
								// 小助手内容
								StringBuilder builder1 = new StringBuilder(128);
								builder1.append(DateUtil.format(cal.getTime(), DateUtil.localPattern)).append("您收到一条新的放款确认信息");
							
								TsmMessageSend msgSend = getMessageSend(leadId,"",
										account,toAcc,orgId,"放款确认",
										AppConstant.MSG_TYPE_OTHER,message,builder1.toString());
								// 保存至数据库
								tsmMessageSendMapper.insert(msgSend);
								// 发消息
								BizMessageUtil.sendBizMsg(msgSend,"9");
								// 发送15天内未读消息
								sendNotReadNum(msgSend.getSendTo(),msgSend.getOrgId());										
					}				
				}				
			}
		});		
	}


	
}
