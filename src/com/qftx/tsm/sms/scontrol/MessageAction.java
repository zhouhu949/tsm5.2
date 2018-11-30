package com.qftx.tsm.sms.scontrol;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.BizMessageUtil;
import com.qftx.base.util.ExecutorUtils;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.pay.service.PaySmsCodeService;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.service.ComResourceService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.sms.dao.TsmMessageSendMapper;
import com.qftx.tsm.sms.dto.TsmMessageSendDto;
import com.qftx.tsm.sms.service.TsmMessageSendService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 消息中心
 */
@Controller
@RequestMapping("/message")
public class MessageAction {
    private Logger logger = Logger.getLogger(MessageAction.class);
    @Autowired
    private TsmMessageSendService tsmMessageSendService;
    @Autowired
    private CachedService cachedService;   
    @Autowired
    private TsmMessageService tsmMessageService;
    @Autowired
    private ResCustInfoService resCustInfoService;
    @Autowired
    private ComResourceService comResourceService;
    @Autowired
    private UserService userService;
	@Resource
	PaySmsCodeService paySmsCodeService;
	@Resource LogCustInfoService logCustInfoService;
    @Resource transient private JdbcTemplate jdbcTemplate;
   
    
    
    // 客户联系（1:客户跟进 ,2:延后呼叫,3:跟进警报,,4:客户回访）
	private static final String UPDATE_MSG_CUST= "UPDATE TSM_MESSAGE_SEND M SET M.IS_READ = 1 WHERE M.ORG_ID = 'orgId' AND M.SEND_TO = 'userAcc' AND M.IS_READ = 0 AND M.MSG_TYPE IN (1,2,3,4) AND M.MESSAGE_ID >= concat(date_format(date_sub(now(),interval 15 day),'%Y%m%d'),'000000%')";
	// 点评提醒 （10:工作点评 ,11:工作日报点评,12: 工作日报点评回复,13:月度计划点评）
	private static final String UPDATE_MSG_BBS= "UPDATE TSM_MESSAGE_SEND M SET M.IS_READ = 1 WHERE M.ORG_ID = 'orgId' AND M.SEND_TO = 'userAcc' AND M.IS_READ = 0 AND M.MSG_TYPE IN (10,11,12,13) AND M.MESSAGE_ID >= concat(date_format(date_sub(now(),interval 15 day),'%Y%m%d'),'000000%')";
	//  未接来电
	private static final String UPDATE_MSG_CALL= "UPDATE TSM_MESSAGE_SEND M SET M.IS_READ = 1 WHERE M.ORG_ID = 'orgId' AND M.SEND_TO = 'userAcc' AND M.IS_READ = 0 AND M.MSG_TYPE = 9 AND M.MESSAGE_ID >= concat(date_format(date_sub(now(),interval 15 day),'%Y%m%d'),'000000%')";
	// 到期提醒（5:短信不足,6:坐席到期,7:通信时长不足,14:计划未提交到期提醒,21:订单到期提醒,22:意向已经放公海提醒,23:签约客户已经放公海提醒,51:资源回收提醒）
	private static final String UPDATE_MSG_DATE= "UPDATE TSM_MESSAGE_SEND M SET M.IS_READ = 1 WHERE M.ORG_ID = 'orgId' AND M.SEND_TO = 'userAcc' AND M.IS_READ = 0 AND M.MSG_TYPE IN (5,6,7,14,21,22,23,51) AND M.MESSAGE_ID >= concat(date_format(date_sub(now(),interval 15 day),'%Y%m%d'),'000000%')";
	// 通知公告
	private static final String UPDATE_MSG_NOTICE= "UPDATE TSM_MESSAGE_SEND M SET M.IS_READ = 1 WHERE M.ORG_ID = 'orgId' AND M.SEND_TO = 'userAcc' AND M.IS_READ = 0 AND M.MSG_TYPE = 18 AND M.MESSAGE_ID = 'messageId' ";
	// 待办审核（8:订单审核,15:月度计划审核,16:日计划审核,17:延期审核,19:小组月计划审核,20:部门月计划审核,28：放款审核）
	private static final String UPDATE_MSG_AUTH= "UPDATE TSM_MESSAGE_SEND M SET M.IS_READ = 1 WHERE M.ORG_ID = 'orgId' AND M.SEND_TO = 'userAcc' AND M.IS_READ = 0 AND M.MSG_TYPE IN (8,15,16,17,19,20,28) AND M.MESSAGE_ID >= concat(date_format(date_sub(now(),interval 15 day),'%Y%m%d'),'000000%')";	
	// 系统公告 ( 24:系统维护消息,25:钱包通知 )
	private static final String UPDATE_MSG_SYSNOTICE= "UPDATE TSM_MESSAGE_SEND M SET M.IS_READ = 1 WHERE M.ORG_ID = 'orgId' AND M.SEND_TO = 'userAcc' AND M.IS_READ = 0 AND M.MSG_TYPE IN (24,25) AND M.MESSAGE_ID = 'messageId' ";
	// 其他（第三方）消息 (26:第三方平台消息 ,29:机器人消息)
	private static final String UPDATE_MSG_OTHERNOTICE= "UPDATE TSM_MESSAGE_SEND M SET M.IS_READ = 1 WHERE M.ORG_ID = 'orgId' AND M.SEND_TO = 'userAcc' AND M.IS_READ = 0 AND M.MSG_TYPE = IN (26,29) AND M.MESSAGE_ID = 'messageId' ";
	// 系统公告 ( 24:系统维护消息,25:钱包通知, )跟新所以未读消息为已读
	private static final String UPDATE_MSG_SYSNOTICE_All= "UPDATE TSM_MESSAGE_SEND M SET M.IS_READ = 1 WHERE M.ORG_ID = 'orgId' AND M.SEND_TO = 'userAcc' AND M.IS_READ = 0 AND M.MSG_TYPE IN (24,25) ";

	@RequestMapping("/list")
    public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try{
        	  ShiroUser user = ShiroUtil.getShiroUser();
        	  String type = request.getParameter("type");
        	  if(StringUtils.isBlank(type)){
        		  type = "1"; //默认为 客户联系提醒
        	  }
        	  TsmMessageSendDto entity = new TsmMessageSendDto();
        	  entity.setOrgId(user.getOrgId());
        	  entity.setSendTo(user.getAccount());
        	  TsmMessageSendDto item = tsmMessageSendService.getNoReadByCount(entity);
        	  request.setAttribute("item", item);
        	  request.setAttribute("type", type);
        }catch(Exception e){
        	throw new SysRunException(e);
        }      
        return "/message/list";
    }

	/** 客户联系 */
    @RequestMapping("/custList")
    public String custList(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String result = "/message/cust_list";
    	try{
          ShiroUser user = ShiroUtil.getShiroUser();
       	  TsmMessageSendDto entity = new TsmMessageSendDto();
       	  entity.setOrgId(user.getOrgId());
       	  entity.setSendTo(user.getAccount());
       	  entity.setState(1);
       	  List<TsmMessageSendDto> list = tsmMessageSendService.getMessageByList(entity);
	      if (list != null && list.size() > 0) {
				TsmMessageSendDto sendDto = null;
				List<TsmMessageSendDto> historyList = new ArrayList<TsmMessageSendDto>();
				Iterator it = list.iterator();
				while (it.hasNext()) {
					sendDto = (TsmMessageSendDto)it.next();				
					if (sendDto.getIsRead() == 1) { // 已读
						historyList.add(sendDto); // 已读的放历史中
						it.remove();
					}
				}
				if (list.size() > 0) {
					final String orgId_ = user.getOrgId();
					final String account_ = user.getAccount();
					ExecutorUtils.THREAD_POOL.submit(new Runnable() {
						public void run() {								
							jdbcTemplate.update(UPDATE_MSG_CUST.replaceFirst("orgId", orgId_).replaceFirst("userAcc",account_)); // 更新为已读
							tsmMessageService.sendNotReadNum(account_, orgId_);
						}
					});					
				}
				if (historyList.size() > 0) {
					request.setAttribute("historyList", historyList);
				}
				
				 request.setAttribute("list", list);
			}else{
				request.setAttribute("type", 1);
				result = "/message/notMessage";
			}      	 
        }catch(Exception e){
        	throw new SysRunException(e);
        }
        return result;
    }

    /** 点评提醒 */
    @RequestMapping("/bbsList")
    public String bbsList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String result = "/message/bbs_list";
    	try{
	          ShiroUser user = ShiroUtil.getShiroUser();
	     	  TsmMessageSendDto entity = new TsmMessageSendDto();
	     	  entity.setOrgId(user.getOrgId());
	     	  entity.setSendTo(user.getAccount());
	     	  entity.setState(2);
	     	  List<TsmMessageSendDto> list = tsmMessageSendService.getMessageByList(entity);	     	  
	     	  if (list != null && list.size() > 0) {
					TsmMessageSendDto sendDto = null;
					List<TsmMessageSendDto> historyList = new ArrayList<TsmMessageSendDto>();
					Iterator it = list.iterator();
					while (it.hasNext()) {
						sendDto = (TsmMessageSendDto)it.next();
						if (sendDto.getIsRead() == 1) { // 已读
							historyList.add(sendDto); // 已读的放历史中
							it.remove();
						}
					}
					if (list.size() > 0) {
						final String orgId_ = user.getOrgId();
						final String account_ = user.getAccount();
						ExecutorUtils.THREAD_POOL.submit(new Runnable() {
							public void run() {								
								jdbcTemplate.update(UPDATE_MSG_BBS.replaceFirst("orgId", orgId_).replaceFirst("userAcc", account_)); // 更新为已读
								tsmMessageService.sendNotReadNum(account_, orgId_);
							}
						});
						
					}
					if (historyList.size() > 0) {
						request.setAttribute("historyList", historyList);
					}
					 request.setAttribute("list", list);
				}else{
					request.setAttribute("type", 2);
					result = "/message/notMessage";
				}
	     	 
          }catch(Exception e){
          	throw new SysRunException(e);
          }
        return result;
    }

    /** 未接来电 */
    @RequestMapping("/callList")
    public String callList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String result = "/message/call_list";
    	try{
	          ShiroUser user = ShiroUtil.getShiroUser();
	     	  TsmMessageSendDto entity = new TsmMessageSendDto();
	     	  entity.setOrgId(user.getOrgId());
	     	  entity.setSendTo(user.getAccount());
	     	  entity.setState(3);
	     	  List<TsmMessageSendDto> list = tsmMessageSendService.getMessageByList(entity);
	     	  if (list != null && list.size() > 0) {
					TsmMessageSendDto sendDto = null;
					List<TsmMessageSendDto> historyList = new ArrayList<TsmMessageSendDto>();
					Iterator it = list.iterator();
					String regex = "\\((.*?)\\)";
					Pattern p = Pattern.compile(regex);
					// 查询缓存
					List<DataDictionaryBean> dataMap=cachedService.getDirList(AppConstant.DATA24, user.getOrgId());
					Integer idReplaceWord = 0;
					if (!dataMap.isEmpty() && dataMap.get(0) != null && !cachedService.judgeHideWhiteList(user.getOrgId(),user.getAccount())) {
						idReplaceWord = new Integer(dataMap.get(0).getDictionaryValue());
					}
					 String phone = null;
					 while (it.hasNext()) {
						sendDto = (TsmMessageSendDto)it.next();
						if(idReplaceWord ==1){ // 消息内容隐藏号码
							Matcher m = p.matcher(sendDto.getMsgCenterContent());
							  while (m.find()) {
								  phone = m.group(1);
								  sendDto.setMsgCenterContent(sendDto.getMsgCenterContent().replace(phone, StringUtils.phoneReplace(phone)));
							  }
						}						
						if (sendDto.getIsRead() == 1) { // 已读
							historyList.add(sendDto); // 已读的放历史中
							it.remove();
						}
					}
					if (list.size() > 0) {
						final String orgId_ = user.getOrgId();
						final String account_ = user.getAccount();
						ExecutorUtils.THREAD_POOL.submit(new Runnable() {
							public void run() {								
								jdbcTemplate.update(UPDATE_MSG_CALL.replaceFirst("orgId", orgId_).replaceFirst("userAcc", account_)); // 更新为已读
								tsmMessageService.sendNotReadNum(account_, orgId_);
							}
						});		
					
					}
					if (historyList.size() > 0) {
						request.setAttribute("historyList", historyList);
					}
					  request.setAttribute("list", list);
				}else{
					request.setAttribute("type", 3);
					result = "/message/notMessage";
				}   	     	
        }catch(Exception e){
        	throw new SysRunException(e);
        }
        return result;
    }

    /** 到期提醒 */
    @RequestMapping("/dateList")
    public String dateList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String result = "/message/date_list";
    	try{
	          ShiroUser user = ShiroUtil.getShiroUser();
	     	  TsmMessageSendDto entity = new TsmMessageSendDto();
	     	  entity.setOrgId(user.getOrgId());
	     	  entity.setSendTo(user.getAccount());
	     	  entity.setState(4);
	     	  List<TsmMessageSendDto> list = tsmMessageSendService.getMessageByList(entity);
	     	  if (list != null && list.size() > 0) {
					TsmMessageSendDto sendDto = null;
					List<TsmMessageSendDto> historyList = new ArrayList<TsmMessageSendDto>();
					Iterator it = list.iterator();
					while (it.hasNext()) {
						sendDto = (TsmMessageSendDto)it.next();
						// 已放入公海提醒，页面格式
						if(sendDto.getMsgType() == AppConstant.MSG_TYPE_WILL_WATERS || sendDto.getMsgType() == AppConstant.MSG_TYPE_SIGN_WATERS|| sendDto.getMsgType() == AppConstant.MSG_TYPE_RES_WATERS){
							sendDto.setMsgCenterContent(getMsgContent(sendDto.getMsgCenterContent(),user.getOrgId(),sendDto.getMsgType()));
						}
						if (sendDto.getIsRead() == 1) { // 已读
							historyList.add(sendDto); // 已读的放历史中
							it.remove();
						}
					}
					if (list.size() > 0) {
						final String orgId_ = user.getOrgId();
						final String account_ = user.getAccount();
						ExecutorUtils.THREAD_POOL.submit(new Runnable() {
							public void run() {								
								jdbcTemplate.update(UPDATE_MSG_DATE.replaceFirst("orgId", orgId_).replaceFirst("userAcc", account_)); // 更新为已读
								tsmMessageService.sendNotReadNum(account_, orgId_);
							}
						});	
					
					}
					if (historyList.size() > 0) {
						request.setAttribute("historyList", historyList);
					}
					request.setAttribute("list", list);
				}else{
					request.setAttribute("type", 4);
					result = "/message/notMessage";
				} 	     	 
        }catch(Exception e){
        	throw new SysRunException(e);
        }
        return result;
    }

    /** 通知公告 */
    @RequestMapping("/noticeList")
    public String noticeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String result = "/message/notice_list";
    	try{
	          ShiroUser user = ShiroUtil.getShiroUser();
	     	  TsmMessageSendDto entity = new TsmMessageSendDto();
	     	  entity.setOrgId(user.getOrgId());
	     	  entity.setSendTo(user.getAccount());
	     	  entity.setState(5);
	     	  List<TsmMessageSendDto> list = tsmMessageSendService.getMessageByList(entity);
	     	  if (list != null && list.size() > 0) {
	     		  	cachedService.setMessageNotice(user.getOrgId(), user.getAccount(), list);
					TsmMessageSendDto sendDto = null;
					List<TsmMessageSendDto> historyList = new ArrayList<TsmMessageSendDto>();
					Iterator it = list.iterator();
					while (it.hasNext()) {
						sendDto = (TsmMessageSendDto)it.next();
						if (sendDto.getIsRead() == 1) { // 已读
							historyList.add(sendDto); // 已读的放历史中
							it.remove();
						}
					}	
					if (historyList.size() > 0) {
						request.setAttribute("historyList", historyList);
					}
					request.setAttribute("list", list);
				}else{
					request.setAttribute("type", 5);
					result = "/message/notMessage";
				} 	     	  
      }catch(Exception e){
      	throw new SysRunException(e);
      }
        return result;
    }

    /** 代办审核 */
    @RequestMapping("/authList")
    public String authList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String result = "/message/auth_list";
    	try{
	          ShiroUser user = ShiroUtil.getShiroUser();
	     	  TsmMessageSendDto entity = new TsmMessageSendDto();
	     	  entity.setOrgId(user.getOrgId());
	     	  entity.setSendTo(user.getAccount());
	     	  entity.setState(6);
	     	  List<TsmMessageSendDto> list = tsmMessageSendService.getMessageByList(entity);
	     	  if (list != null && list.size() > 0) {
					TsmMessageSendDto sendDto = null;
					List<TsmMessageSendDto> historyList = new ArrayList<TsmMessageSendDto>();
					Iterator it = list.iterator();
					while (it.hasNext()) {
						sendDto = (TsmMessageSendDto)it.next();
						if (sendDto.getIsRead() == 1) { // 已读
							historyList.add(sendDto); // 已读的放历史中
							it.remove();
						}
					}
					if (list.size() > 0) {
						final String orgId_ = user.getOrgId();
						final String account_ = user.getAccount();
						ExecutorUtils.THREAD_POOL.submit(new Runnable() {
							public void run() {								
								jdbcTemplate.update(UPDATE_MSG_AUTH.replaceFirst("orgId", orgId_).replaceFirst("userAcc", account_)); // 更新为已读
								tsmMessageService.sendNotReadNum(account_, orgId_);
							}
						});							
					}
					if (historyList.size() > 0) {
						request.setAttribute("historyList", historyList);
					}
					request.setAttribute("list", list);
				}else{
					request.setAttribute("type", 6);
					result = "/message/notMessage";
				} 	     	  
	    }catch(Exception e){
	    	throw new SysRunException(e);
	    }
        return result;
    }
    
    
    /** 系统消息 */
    @RequestMapping("/sysNoticeList")
    public String sysNoticeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String result = "/message/sys_notice_list";
    	try{
	          ShiroUser user = ShiroUtil.getShiroUser();
	     	  TsmMessageSendDto entity = new TsmMessageSendDto();
	     	  entity.setOrgId(user.getOrgId());
	     	  entity.setSendTo(user.getAccount());
	     	  entity.setState(7);
	     	  List<TsmMessageSendDto> list = tsmMessageSendService.getMessageByList(entity);
	     	  if (list != null && list.size() > 0) {
	     		  	cachedService.setSysNotice(user.getOrgId(), user.getAccount(), list);
					TsmMessageSendDto sendDto = null;
					List<TsmMessageSendDto> historyList = new ArrayList<TsmMessageSendDto>();
					Iterator it = list.iterator();
					while (it.hasNext()) {
						sendDto = (TsmMessageSendDto)it.next();
						if (sendDto.getIsRead() == 1) { // 已读
							historyList.add(sendDto); // 已读的放历史中
							it.remove();
						}
					}
					if (list.size() > 0) {
						final String orgId_ = user.getOrgId();
						final String account_ = user.getAccount();
						ExecutorUtils.THREAD_POOL.submit(new Runnable() {
							public void run() {								
								jdbcTemplate.update(UPDATE_MSG_SYSNOTICE_All.replaceFirst("orgId", orgId_).replaceFirst("userAcc", account_)); // 更新为已读
								tsmMessageService.sendNotReadNum(account_, orgId_);
							}
						});							
					}					
					
					if (historyList.size() > 0) {
						request.setAttribute("historyList", historyList);
					}
					request.setAttribute("list", list);
				}else{
					request.setAttribute("type", 7);
					result = "/message/notMessage";
				} 	     	  
      }catch(Exception e){
      	throw new SysRunException(e);
      }
        return result;
    }
    
    /** 其他公告 */
    @RequestMapping("/otherNoticeList")
    public String otherNoticeList(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String result = "/message/other_notice_list";
    	try{
	          ShiroUser user = ShiroUtil.getShiroUser();
	     	  TsmMessageSendDto entity = new TsmMessageSendDto();
	     	  entity.setOrgId(user.getOrgId());
	     	  entity.setSendTo(user.getAccount());
	     	  entity.setState(8);
	     	  List<TsmMessageSendDto> list = tsmMessageSendService.getMessageByList(entity);
	     	  if (list != null && list.size() > 0) {
	     		  	cachedService.setOtherNotice(user.getOrgId(), user.getAccount(), list);
					TsmMessageSendDto sendDto = null;
					List<TsmMessageSendDto> historyList = new ArrayList<TsmMessageSendDto>();
					Iterator it = list.iterator();
					while (it.hasNext()) {
						sendDto = (TsmMessageSendDto)it.next();
						if (sendDto.getIsRead() == 1) { // 已读
							historyList.add(sendDto); // 已读的放历史中
							it.remove();
						}
					}
					if (list.size() > 0) {
						final String orgId_ = user.getOrgId();
						final String account_ = user.getAccount();
						tsmMessageSendService.updateRobotMessageState(orgId_, account_); // 更新为已读
						tsmMessageService.sendBizNotReadNum(account_, orgId_);
						tsmMessageService.sendNotReadNum(account_, orgId_);
					}	
					if (historyList.size() > 0) {
						request.setAttribute("historyList", historyList);
					}
					request.setAttribute("list", list);
				}else{
					request.setAttribute("type", 8);
					result = "/message/notMessage";
				} 	     	  
      }catch(Exception e){
      	throw new SysRunException(e);
      }
        return result;
    }
    
    /** 系统消息 详情 */
    @RequestMapping("/getSysNoticeInfo")
    public String getSysNoticeInfo(HttpServletRequest request){
    	try{
    		ShiroUser user = ShiroUtil.getShiroUser();
    		String index = request.getParameter("indexSize"); // 获取指定一条数据
    		List<TsmMessageSendDto> list = cachedService.getSysNotice(user.getOrgId(), user.getAccount());
    		if(list != null && StringUtils.isNotBlank(index)){
    			TsmMessageSendDto dto = list.get(Integer.parseInt(index));
    			if(dto != null){
    				request.setAttribute("dto", dto);
    			}
    			if(list.size() == Integer.parseInt(index)+1){
    				request.setAttribute("isLast",1); // 最后一条 
    			}
    			request.setAttribute("indexSize", index); // 选中条数 位置
    			if (dto != null && StringUtils.isNotBlank(dto.getMessageId())) {
    				final String orgId_ = user.getOrgId();
					final String account_ = user.getAccount();
					final String messageId_ = dto.getMessageId();
					ExecutorUtils.THREAD_POOL.submit(new Runnable() {
						public void run() {								
							jdbcTemplate.update(UPDATE_MSG_SYSNOTICE.replaceFirst("orgId", orgId_).replaceFirst("userAcc", account_).replaceFirst("messageId", messageId_)); // 更新为已读
							tsmMessageService.sendNotReadNum(account_, orgId_);
						}
					});					
    			}
    		}
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}
    	return "/message/idialog/sys_notice_dialog";
    }
    
    
    /** 其他消息（第三方消息） 详情 */
    @RequestMapping("/getOtherNoticeInfo")
    public String getOtherNoticeInfo(HttpServletRequest request){
    	try{
    		ShiroUser user = ShiroUtil.getShiroUser();
    		String index = request.getParameter("indexSize"); // 获取指定一条数据
    		List<TsmMessageSendDto> list =cachedService.getOtherNotice(user.getOrgId(), user.getAccount());
    		if(list != null && StringUtils.isNotBlank(index)){
    			TsmMessageSendDto dto = list.get(Integer.parseInt(index));
    			if(dto != null){
    				request.setAttribute("dto", dto);
    			}
    			if(list.size() == Integer.parseInt(index)+1){
    				request.setAttribute("isLast",1); // 最后一条 
    			}
    			request.setAttribute("indexSize", index); // 选中条数 位置
    			if (dto != null && StringUtils.isNotBlank(dto.getMessageId())) {
    				final String orgId_ = user.getOrgId();
					final String account_ = user.getAccount();
					final String messageId_ = dto.getMessageId();
					ExecutorUtils.THREAD_POOL.submit(new Runnable() {
						public void run() {								
							jdbcTemplate.update(UPDATE_MSG_OTHERNOTICE.replaceFirst("orgId", orgId_).replaceFirst("userAcc", account_).replaceFirst("messageId", messageId_)); // 更新为已读
							tsmMessageService.sendNotReadNum(account_, orgId_);
						}
					});					
    			}
    		}
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}
    	return "/message/idialog/other_notice_dialog";
    }
    
    
    /** 公告消息 详情 */
    @RequestMapping("/getNoticeInfo")
    public String getNoticeInfo(HttpServletRequest request){
    	try{
    		ShiroUser user = ShiroUtil.getShiroUser();
    		String index = request.getParameter("indexSize"); // 获取指定一条数据
    		List<TsmMessageSendDto> list = cachedService.getMessageNotice(user.getOrgId(), user.getAccount());
    		if(list != null && StringUtils.isNotBlank(index)){
    			TsmMessageSendDto dto = list.get(Integer.parseInt(index));
    			if(dto != null){
    				if("hyx".equals(dto.getSendAcc())){
    					dto.setSendAcc("慧营销");
    				}else{
    			        User	users = userService.getByAccount(dto.getSendAcc());
    			    if(users!=null){
    			    	dto.setSendAcc(users.getUserName());
    			    }
    				}
    				request.setAttribute("dto", dto);
    			}
    			if(list.size() == Integer.parseInt(index)+1){
    				request.setAttribute("isLast",1); // 最后一条 
    			}
    			request.setAttribute("indexSize", index); // 选中条数 位置
    			if (dto != null && StringUtils.isNotBlank(dto.getMessageId())) {
    				final String orgId_ = user.getOrgId();
					final String account_ = user.getAccount();
					final String messageId_ = dto.getMessageId();
					ExecutorUtils.THREAD_POOL.submit(new Runnable() {
						public void run() {								
							jdbcTemplate.update(UPDATE_MSG_NOTICE.replaceFirst("orgId", orgId_).replaceFirst("userAcc", account_).replaceFirst("messageId", messageId_)); // 更新为已读
							tsmMessageService.sendNotReadNum(account_, orgId_);
						}
					});					
    			}
    		}
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}
    	return "/message/idialog/notice_dialog";
    }
    
    
    /** 未阅读消息包括普通公告和系统公告
     * 查询是否有未阅读公告消息 
     * */
    @RequestMapping("/IfNoReadnotice")
    @ResponseBody
    public Map<String,String> IfNoReadnotice(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	Map<String,String> map=new HashMap();
    	try{
	          ShiroUser user = ShiroUtil.getShiroUser();
	     	  TsmMessageSendDto entity = new TsmMessageSendDto();
	     	  entity.setOrgId(user.getOrgId());
	     	  entity.setSendTo(user.getAccount());
	     	  cachedService.removeNoReadMessageNotice(user.getOrgId(),user.getAccount());
	     	  List<TsmMessageSendDto> list =tsmMessageSendService.findNOReadMessageByList(entity);
	     	  if (list != null && list.size() > 0) {
	     		  	cachedService.setNoReadMessageNotice(user.getOrgId(), user.getAccount(), list);
	     		  	map.put("isok", "0");
	     		  	int sum=list.size();
	     		  	map.put("sum",String.valueOf(sum));
				}else{
					map.put("isok", "1");
				} 	     	  
      }catch(Exception e){
//      	throw new SysRunException(e);
    	    logger.error(e.getMessage());
    	    e.printStackTrace();
    	    map.put("isok", "1");
    	    return map;
      }
      return map;
    }
    
    /** 未阅读公告消息 详情 */
    @RequestMapping("/getNOReadNoticeInfo")
    public String getNOReadNoticeInfo(HttpServletRequest request){
    	try{
    		  ShiroUser user = ShiroUtil.getShiroUser();
	     	  TsmMessageSendDto entity = new TsmMessageSendDto();
	     	  entity.setOrgId(user.getOrgId());
	     	  entity.setSendTo(user.getAccount());
    		  String index = request.getParameter("indexSize"); // 获取指定一条数据
    		  List<TsmMessageSendDto> list =cachedService.getNoReadMessageNotice(user.getOrgId(), user.getAccount());
    		  int sum=0;
    		  if(list !=null&& list.size()>0){
    			  sum=list.size();
    		  }
    		  request.setAttribute("sum", sum); // 总条数
    		if(list != null && StringUtils.isNotBlank(index)){
    			TsmMessageSendDto dto = list.get(Integer.parseInt(index));
    			if(dto != null){
    				if("hyx".equals(dto.getSendAcc())){
    					dto.setSendAcc("慧营销");
    				}else{
    			        User	users = userService.getByAccount(dto.getSendAcc());
    			    if(users!=null){
    			    	dto.setSendAcc(users.getUserName());
    			    }
    				}
    				request.setAttribute("dto", dto);
    			}
    			if(list.size() == Integer.parseInt(index)+1){
    				request.setAttribute("isLast",1); // 最后一条 
    			}
    			request.setAttribute("indexSize", index); // 选中条数 位置
    			if (dto != null && StringUtils.isNotBlank(dto.getMessageId())) {
    				final String orgId_ = user.getOrgId();
					final String account_ = user.getAccount();
					final String messageId_ = dto.getMessageId();
					final Integer  mesType = dto.getMsgType();
					if(mesType!=null && mesType ==18){
						ExecutorUtils.THREAD_POOL.submit(new Runnable() {
							public void run() {								
								jdbcTemplate.update(UPDATE_MSG_NOTICE.replaceFirst("orgId", orgId_).replaceFirst("userAcc", account_).replaceFirst("messageId", messageId_)); // 更新为已读
								tsmMessageService.sendNotReadNum(account_, orgId_);
							}
						});	
					}else if(mesType!=null && mesType ==24 || mesType ==25){
						ExecutorUtils.THREAD_POOL.submit(new Runnable() {
							public void run() {								
								jdbcTemplate.update(UPDATE_MSG_SYSNOTICE.replaceFirst("orgId", orgId_).replaceFirst("userAcc", account_).replaceFirst("messageId", messageId_)); // 更新为已读
								tsmMessageService.sendNotReadNum(account_, orgId_);
							}
						});	
					}
				
    			}
    		}
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}
    	return "/message/idialog/noread_notice_dialog";
    }
    
    
    /** 系统版本说明 */
    @RequestMapping("/sysVersion")
    public String sysVersion(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try{
	          ShiroUser user = ShiroUtil.getShiroUser();
	     	  TsmMessageSendDto entity = new TsmMessageSendDto();
	     	  entity.setOrgId(user.getOrgId());
	     	  entity.setSendTo(user.getAccount());
	     	  TsmMessageSendDto list = tsmMessageSendService.findSysVersion(entity);
	     	  request.setAttribute("list", list);
      }catch(Exception e){
      	throw new SysRunException(e);
      }
        return "/version_description";
    }
    
    /** 系统版本说明 */
    @RequestMapping("/sysVersion_chara")
    public String sysVersion_chara(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	try{
      }catch(Exception e){
      	throw new SysRunException(e);
      }
        return "/version_characteristics";
    }
    
    
    /** 清空已读消息 */
    @RequestMapping("/deleteMessage")
    @ResponseBody
    public String deleteMessage(HttpServletRequest request){
    	try{
    		String type = request.getParameter("type");
    		if(StringUtils.isNotBlank(type)){
        		ShiroUser user = ShiroUtil.getShiroUser();
        		TsmMessageSendDto entity = new TsmMessageSendDto();
        		entity.setOrgId(user.getOrgId());
        		entity.setSendTo(user.getAccount());
        		entity.setState(Integer.parseInt(type));
            	tsmMessageSendService.removeByMessage(entity);
            	if("5".equals(type)){ // 公告
            		// 清空缓存
            		cachedService.removeMessageNotice(user.getOrgId(),user.getAccount());
            	}
    		}
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}		 
    	return AppConstant.RESULT_SUCCESS;
    }
    
    /** 短信接收号码绑定 页面 */
    @RequestMapping("/smsReceiveBindPage1")
    public String smsReceiveBindPage1(HttpServletRequest request){
    	try{
    		ShiroUser user = ShiroUtil.getShiroUser();
    		User user1 = userService.getByAccount(user.getAccount());
    		request.setAttribute("mobile", user1.getMobile());
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}
    	return "/call/idialog/sms_receive_bind";
    }
    
    /** 短信接收号码绑定 页面 */
    @RequestMapping("/smsReceiveBindPage")
    public String smsReceiveBindPage(HttpServletRequest request){
    	try{
    		ShiroUser user = ShiroUtil.getShiroUser();
    		User user1 = userService.getByAccount(user.getAccount());
    		request.setAttribute("mobile", user1.getMobile());
    		if(StringUtils.isNotBlank(user1.getMobile())){
    			return "/call/idialog/sms_receive_bind_show";
    		}
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}
    	return "/call/idialog/sms_receive_bind";
    }
    
    /** 
     * 短信接收号码绑定
     * @history  
     */
    @ResponseBody
    @RequestMapping("/smsReceiveBind")
    public String smsReceiveBind(HttpServletRequest request){
    	try {
    		String mobile = request.getParameter("mobile");
    		if(StringUtils.isNotBlank(mobile)){
    			ShiroUser user = ShiroUtil.getShiroUser();
    			User user1 = userService.getByAccount(user.getAccount());
        		String operateId = null,operateName = null,content = null;
        		if(StringUtils.isNotBlank(user1.getMobile())){
        			operateId = AppConstant.Operate_id75;
        			operateName = AppConstant.Operate_Name75;
        			content = "帐号变更手机号码:"+mobile;
        		}else{
        			operateId = AppConstant.Operate_id73;
        			operateName = AppConstant.Operate_Name73;
        			content = "帐号绑定手机号码:"+mobile;
        		}
    			
    			User entity = new User();
    			entity.setUserId(user.getId());
    			entity.setOrgId(user.getOrgId());
    			entity.setMobile(mobile);
				userService.modifyTrends(entity);
				// 新增用户操作日志
				LogBean logBean = new LogBean();
				logBean.setOrgId(user.getOrgId());
				logBean.setUserAcc(user.getAccount());
				logBean.setUserName(user.getName());
				logBean.setModuleId(AppConstant.Module_id11);
				logBean.setModuleName(AppConstant.Module_Name11);
				logBean.setOperateId(operateId);
				logBean.setOperateName(operateName);
				logBean.setContent(content);
				logCustInfoService.addTableStoreLog(logBean, null);
    		}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
    	return AppConstant.RESULT_SUCCESS;
    }
    
    /** 
     * 短信接收号码解除绑定
     * @history  
     */
    @ResponseBody
    @RequestMapping("/smsReceiveUnBind")
    public String smsReceiveUnBind(HttpServletRequest request){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
			userService.smsReceiveUnBind(user.getOrgId(),user.getId());
			// 新增用户操作日志
			LogBean logBean = new LogBean();
			logBean.setOrgId(user.getOrgId());
			logBean.setUserAcc(user.getAccount());
			logBean.setUserName(user.getName());
			logBean.setModuleId(AppConstant.Module_id11);
			logBean.setModuleName(AppConstant.Module_Name11);
			logBean.setOperateId(AppConstant.Operate_id74);
			logBean.setOperateName(AppConstant.Operate_Name74);
			logBean.setContent("帐号解绑手机号");
			logCustInfoService.addTableStoreLog(logBean, null);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
    	return AppConstant.RESULT_SUCCESS;
    }
    
    
    
    /** 查看资源姓名或单位名称 */
    @RequestMapping("/findResShowName")
    @ResponseBody
    public Map<String,String> findResShowName(HttpServletRequest request){
    	Map<String,String>returnMap = new HashMap<String, String>();
    	String name = "客户卡片";
    	try{
    		ShiroUser user = ShiroUtil.getShiroUser();   	
    		String custId = request.getParameter("custId");
    		Map<String,String>map = new HashMap<String, String>();
    		map.put("orgId", user.getOrgId());
			map.put("custId", custId);
			ResCustInfoBean custInfo = comResourceService.getByPrimaryKey(map);
			if(custInfo != null){				
				if(custInfo.getState() == 1){ // 企业资源
					if(StringUtils.isNotBlank(custInfo.getName())){
						name = custInfo.getName();
					}
				}else{ // 个人资源
					if(StringUtils.isNotBlank(custInfo.getCompany())){
						name = custInfo.getCompany();
					}else if(StringUtils.isNotBlank(custInfo.getName())){
						name = custInfo.getName();
					}
				}			
			}  		
    	}catch(Exception e){
    		logger.error(" 查看资源是否被删除 异常！", e);
    	}		 
    	returnMap.put("name", name);
    	return returnMap;
    }
    
    // msgType in(22,23) 的消息中心内容需要做判断，用户是否还存在于公海客户,如果不在公海客户页面不能点击
	private String getMsgContent(String msgContent,String orgId,Integer msgType){
     	try{
	     	// 正在表达式获取之前 ，先按符号 ### 进行分割， [0]公共内容、[1]客户集合
	       	 String msgConten1 = msgContent.split("###")[0];
	   		 String regex = "\\[(.+?)\\]";  
	   		 Pattern pattern = Pattern.compile (regex);  
	   		 Matcher matcher = pattern.matcher (msgContent.split("###")[1]);  
	   		 List<String>ids = new ArrayList<String>();
	   		 List<String>values = new ArrayList<String>();
	   		 Map<String,String>map =new HashMap<String, String>();
	   		 String custId = null;
	   		 StringBuffer sbf = new StringBuffer();
	   		 while (matcher.find ())  
	   		 {  
	   			 custId = matcher.group (1).split("#@")[1];
	   			 ids.add(custId);
	   			 values.add(matcher.group (1));
	   		 }
	   		 if(ids.size() >0){
	   			 Map<String,Object>map1 = new HashMap<String, Object>();
	   			 map1.put("orgId", orgId);
	   			 map1.put("ids", ids);
	   			 List<String>list = resCustInfoService.getResByResId(map1);
	   			 if(list!= null && list.size() >0){				
	   				 for(String id : list){
	   					 map.put(id, id);
	   				 }
	   			 }
	
	   			 String nameShow = null;
	   			 int i = 0;
	   			 // 为了加一个颜色，呵呵！
	   			 if(msgType == AppConstant.MSG_TYPE_WILL_WATERS){
	   				 String regex1 = "<<(.+?)>>";  
		   	   		 Pattern pattern1 = Pattern.compile (regex1);  
		   	   		 Matcher matcher1 = pattern1.matcher (msgConten1);
		   	   		 if(matcher1.find ()){
		   	   			 String process = matcher1.group (1).split("@")[0];
		   	   			 String day = matcher1.group (1).split("@")[1];		   	   			 
		   	   			 sbf.append(msgConten1.split("@@")[0]);
		   	   			 sbf.append("<label style='color: red;float:none;'>");
		   	   			 sbf.append(process).append("</label>").append("<label style='color: green;float:none;'>");
		   	   			 sbf.append(day).append("</label>").append(msgConten1.split("@@")[2]);  	
		   	   		 }else{
		   	   			 sbf.append(msgConten1);
		   	   		 }		   	   		 
	   			 }else{
	   				 sbf.append(msgConten1);
	   			 }
	   			
	   			 for(String content : values){
	   				 nameShow = content.split("#@")[0];
	   				 custId = content.split("#@")[1];
	   				 if(map.get(custId)!=null){
	   					 sbf.append("<span name='expire_' class='font_related_person' cust_id='"+custId+"'>");
	   					 sbf.append(nameShow);
	   					 if ((i + 1) < values.size()) {
	   							sbf.append("、");
	   					 }
	   					sbf.append("</span>");
	   				 }else{
	   					 sbf.append(nameShow);
	   					 if ((i + 1) < values.size()) {
	   							sbf.append("、");
	   					 }
	   				 }				 
	   				i++;
	   			 }
	   		 }		 
	       	return sbf.toString();
     	}catch(Exception e){
     		logger.error(e.getMessage(), e);
     		return null;
     	}
    	
    }
    
	public static void main(String[] args) {
		 StringBuffer sbf = new StringBuffer();
		 String msgConten1 = "原因：@@<<1@aaa>>@@222";
		 String regex1 = "<<(.+?)>>";  
   		 Pattern pattern1 = Pattern.compile (regex1);  
   		 Matcher matcher1 = pattern1.matcher (msgConten1);  
   		 String process = "";
   		 String day = "";
   		while (matcher1.find ())  
  		 { 
   			process = matcher1.group (1).split("@")[0];
   			day = matcher1.group (1).split("@")[1];
  		 }  		
   		sbf.append(msgConten1.split("@@")[0]);
   		sbf.append("<label style='color: red'>");
   		sbf.append(process).append("</label>").append("<label style='color: green'>");
   		sbf.append(day).append("</label>").append(msgConten1.split("@@")[2]);  		
   		System.out.println(sbf.toString());
	}
   
}
