package com.qftx.tsm.sys.scontrol;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.enums.SubmitStatus;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.BizMessageUtil;
import com.qftx.base.util.ExecutorUtils;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.sms.bean.SendMessageToApp;
import com.qftx.tsm.sms.bean.TsmMessageSend;
import com.qftx.tsm.sms.service.TsmSendMessageToAppService;
import com.qftx.tsm.sys.bean.AnnouncementBean;
import com.qftx.tsm.sys.dto.AnnouncementDto;
import com.qftx.tsm.sys.service.AnnouncementService;
import com.qftx.tsm.workAll.service.WorkAllIndexService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 通知公告
 */
@Controller
@RequestMapping("/notice")
public class AnnouncementController{
	Logger logger=Logger.getLogger(AnnouncementController.class);
	
	@Resource
	private AnnouncementService announcementService;
	 @Autowired private UserService userService;
	 @Resource transient private JdbcTemplate jdbcTemplate;
	 @Resource transient private TsmMessageService tsmMessageService;
	 @Resource transient private CachedService cachedService;
	 @Autowired private WorkAllIndexService workAllIndexService;
	 @Autowired private TsmSendMessageToAppService tsmSendMessageToAppService;
	 @Autowired private LogUserOperateService logUserOperateService;	 
	 
	private static final ExecutorService threadPool = Executors.newFixedThreadPool(3);
	private static final String MESSAGE_INSERT_SQL = "INSERT INTO TSM_MESSAGE_SEND(MESSAGE_ID, SEND_DATE, ORG_ID, SUBMIT_STATUS, SEND_FROM, SEND_TO, TITLE, MESSAGE_CONTENT, REMARK, MSG_TYPE, IS_READ,BUSSINESS_ID,MSG_CENTER_CONTENT) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	// 通知公告
	private static final String UPDATE_MSG_NOTICE= "UPDATE TSM_MESSAGE_SEND M SET M.IS_READ = 1 WHERE M.ORG_ID = 'orgId' AND M.SEND_TO = 'userAcc' AND M.IS_READ = 0 AND M.MSG_TYPE = 18 AND M.BUSSINESS_ID = 'messageId' ";

	/**
	 * 通知公告列表页
	 */
	@RequestMapping("/noticelist")
	public String showNoticeList(HttpServletRequest request,AnnouncementDto announcementDto){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			announcementDto.setOrgId(user.getOrgId());
			announcementDto.setIsDel((short)0);
			List<AnnouncementDto> entitys = announcementService.getNoticeListPage(announcementDto);
			AnnouncementDto dto_news=new AnnouncementDto();
			List<String> list=new ArrayList();
			if(entitys !=null && entitys.size()>0){
			for(AnnouncementDto dto:entitys){
				list.add(dto.getAnnounceId());
			}
			dto_news.setIdList(list);
			dto_news.setOrgId(user.getOrgId());
			dto_news.setIsDel((short)0);
			List<AnnouncementDto> entitys_new=announcementService.findNoticeReadersum(dto_news);
			for(AnnouncementDto dto:entitys){
				for(AnnouncementDto dto_new:entitys_new){
					if(dto.getAnnounceId().equals(dto_new.getAnnounceId()) || dto.getAnnounceId() ==dto_new.getAnnounceId()){
						dto.setSum(dto_new.getSum());
					}
				}
				if("hyx".equals(dto.getInputerAcc())){
					dto.setInputerAcc("慧营销");
				}else{
			        User	users = userService.getByAccount(dto.getInputerAcc());
			    if(users!=null){
			    	dto.setInputerAcc(users.getUserName());
			    }
				}
			}
		}


			request.setAttribute("entitys", entitys);
			request.setAttribute("item", announcementDto);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/tsm/sys/notice/noticelist";
	}
	

	/** 跳转至 公告详情页面 */
	@RequestMapping("/toNoticeInfoPage")
	public String toNoticeInfoPage(HttpServletRequest request){
		String id = request.getParameter("id");
		ShiroUser user = ShiroUtil.getShiroUser();
		String url = "";
		if(StringUtils.isNotBlank(id)){
			Map<String,String>map = new HashMap<String, String>();
			map.put("id",id);
			map.put("orgId",user.getOrgId());
			AnnouncementDto announcementDto = announcementService.getNoticeInfoById(map);
			List<String> list=announcementService.getNoticeReaderUserById(map);
			if(list !=null && list.size()>0){
			announcementDto.setReadeUser(list);
			}
			
			if("hyx".equals(announcementDto.getInputerAcc())){
				announcementDto.setInputerAcc("慧营销");
			}else{
		        User	users = userService.getByAccount(announcementDto.getInputerAcc());
		    if(users!=null){
		    	announcementDto.setInputerAcc(users.getUserName());
		    }
			}
			request.setAttribute("announcementDto",announcementDto);
			url = "/tsm/sys/notice/noticeInfo";
		}
		return url;
	}
	
	/** 跳转至 新增或修改页面 */
	@RequestMapping("/toAddOrEditPage")
	public String toAddOrEditPage(HttpServletRequest request){
		String id = request.getParameter("id");
		ShiroUser user = ShiroUtil.getShiroUser();
		String url = "";
		if(StringUtils.isNotBlank(id)){
			//  修改
			AnnouncementBean entity = new AnnouncementBean();
			entity.setAnnounceId(id);
			entity.setOrgId(user.getOrgId());
			AnnouncementBean announcement= announcementService.getByCondtion(entity);
			request.setAttribute("announcement",announcement);
			url = "/tsm/sys/notice/addnotice";
		}else{
			// 新增
			url = "/tsm/sys/notice/addnotice";
		}
		return url;
	}
	
	/**
	 * 添加一条通知公告
	 */
	@ResponseBody
	@RequestMapping("/addnotice")
	public String addNotice(HttpServletRequest request,AnnouncementBean announcement){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			if(announcement == null){
				return AppConstant.RESULT_FAILURE;
			}
			String announceId = SysBaseModelUtil.getModelId();
			Date inputDate = new Date();
			announcement.setAnnounceId(announceId);
			announcement.setType((short)1);
			announcement.setInputerAcc(user.getAccount() == null ? user.getName() : user.getAccount());
			announcement.setInputdate(inputDate);
			announcement.setIsDel((short)0);
			announcement.setOrgId(user.getOrgId());
			announcementService.create(announcement);
			
			User user_u=new User();
			user_u.setOrgId(user.getOrgId());
			user_u.setUserAccount(user.getAccount());
			user_u.setUserId(user.getId());
			//插入索引维护表数据
			workAllIndexService.insert(announceId,announcement.getInputdate(),user_u,4);
			//插入通知消息表
			User authUser = new User();
			authUser.setEnabled(1);
			authUser.setSysType("5");
			//authUser.setIssys(0);
			//authUser.setIsunit(0);
			authUser.setIsbackground(0);
			authUser.setOrgId(user.getOrgId());
			List<User> userList = userService.getListByCondtion(authUser);
			TsmMessageSend msgSend = null;
			final List<TsmMessageSend> msgSends = new ArrayList<TsmMessageSend>();
			for (User u : userList) {
				msgSend = new TsmMessageSend();
				msgSend.setMessageId(StringUtils.getDateAndRandStr(15));
				msgSend.setBusinessId(announceId); // 公告ID
				msgSend.setSendFrom(u.getUserAccount());
				msgSend.setSendTo(u.getUserAccount());
				msgSend.setTitle(announcement.getTitle());
				msgSend.setMsgType(AppConstant.MSG_TYPE_NOTICE);
				msgSend.setMessageContent("公告：" + announcement.getTitle());
				msgSend.setMsgCenterContent(announcement.getContent());
				msgSend.setIsRead(AppConstant.MSG_IS_NOT_READ);
				msgSend.setSubmitStatus(SubmitStatus.SUBMIT_SUCC.getStatus());
				msgSend.setSendDate(new Date());
				msgSend.setOrgId(user.getOrgId());
				msgSend.setRemark("");
				msgSends.add(msgSend);
			}
			if (msgSends.size() > 0) {
				jdbcTemplate.batchUpdate(MESSAGE_INSERT_SQL, new BatchPreparedStatementSetter() {
					//为prepared statement设置参数。这个方法将在整个过程中被调用的次数
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, msgSends.get(i).getMessageId());
						ps.setTimestamp(2, new Timestamp(msgSends.get(i).getSendDate().getTime()));
						ps.setString(3, msgSends.get(i).getOrgId());
						ps.setString(4, msgSends.get(i).getSubmitStatus());
						ps.setString(5, msgSends.get(i).getSendFrom());
						ps.setString(6, msgSends.get(i).getSendTo());
						ps.setString(7, msgSends.get(i).getTitle());
						ps.setString(8, msgSends.get(i).getMessageContent());
						ps.setString(9, msgSends.get(i).getRemark());
						ps.setInt(10, msgSends.get(i).getMsgType());
						ps.setInt(11, msgSends.get(i).getIsRead());
						ps.setString(12, msgSends.get(i).getBusinessId());
						ps.setString(13, msgSends.get(i).getMsgCenterContent());
					}
					//返回更新的结果集条数
					public int getBatchSize() {
						return msgSends.size();
					}
					});
					for (final TsmMessageSend send : msgSends) {
						threadPool.submit(new Runnable() {
							public void run() {
								BizMessageUtil.sendBizMsg(send,"5");
								// 发送15天内未读消息条数
								tsmMessageService.sendNotReadNum(send.getSendTo(), send.getOrgId());
							}
						});
					}

			}
			logUserOperateService.setUserOperateLog(AppConstant.Module_id117, AppConstant.Module_Name117, AppConstant.Operate_id2, AppConstant.Operate_Name2, announcement.getTitle(), null);
			String isopen = ConfigInfoUtils.getStringValue("send_message_to_app_isopen");
			if(isopen=="0"||"0".equals(isopen)){
			//向app发送公告
			SendMessageToApp appbean=new SendMessageToApp();
			appbean.setOrgId(user.getOrgId());
			appbean.setUserAccount("");
			appbean.setTitle(announcement.getTitle());
			appbean.setSummary(announcement.getContent());
			appbean.setLevel("3");
			appbean.setType("5");

			Map<String,String> map=new HashMap<String,String>();
			map.put("id", announceId);
			map.put("orgId", user.getOrgId());
			appbean.setObj(JsonUtil.getJsonString(map));
			tsmSendMessageToAppService.sendMessageToAll(appbean);
			}
			 return AppConstant.RESULT_SUCCESS;
		}catch(Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/**
	 * 修改一条通知公告
	 */
	@ResponseBody
	@RequestMapping("/editnotice")
	public String editNotice(HttpServletRequest request,AnnouncementBean announcement){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			if(announcement == null){
				return AppConstant.RESULT_FAILURE;
			}
			announcement.setOrgId(user.getOrgId());
			announcement.setModifierAcc(user.getName() == null ? user.getAccount() : user.getName());
			announcement.setModifydate(new Date());
			announcementService.modifyTrends(announcement);
			logUserOperateService.setUserOperateLog(AppConstant.Module_id117, AppConstant.Module_Name117, AppConstant.Operate_id4, AppConstant.Operate_Name4, announcement.getTitle(), null);
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/**
	 * 批量删除通知公告
	 */
	@ResponseBody
	@RequestMapping("/delnotice")
	public String deleteNotice(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			String[] ids_ = ids.split("\\,");
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("isDel", 1);
			param.put("modifierAcc", user.getAccount());
			param.put("ids", ids_);
			param.put("orgId",user.getOrgId());
			announcementService.removeFakeBatch(param);		
			// 清空缓存
    		cachedService.removeMessageNotice(user.getOrgId(),user.getAccount());
			//删除同时更新所有用户消息为已读
			List<String> owaList = Arrays.asList(ids_);
			if (owaList != null && owaList.size()>0) {
				for(String id:owaList){
					User entity = new User();
					entity.setOrgId(user.getOrgId());
					// 单位下面所有人员 都可以接收到信息
					List<User> users = userService.getListByCondtion(entity);
					if(users != null && users.size() > 0){
						for(User u : users){							
							final String orgId_ = user.getOrgId();
							final String account_ = u.getUserAccount();
							final String messageId_ =id;
								ExecutorUtils.THREAD_POOL.submit(new Runnable() {
									public void run() {								
										jdbcTemplate.update(UPDATE_MSG_NOTICE.replaceFirst("orgId", orgId_).replaceFirst("userAcc", account_).replaceFirst("messageId", messageId_)); // 更新为已读
										tsmMessageService.sendNotReadNum(account_, orgId_);
									}
								});	
				
			}
			}
					if(StringUtils.isNotBlank(id)){
						//  修改
						AnnouncementBean bean = new AnnouncementBean();
						bean.setAnnounceId(id);
						bean.setOrgId(user.getOrgId());
						AnnouncementBean announcement= announcementService.getByCondtion(bean);
						logUserOperateService.setUserOperateLog(AppConstant.Module_id117, AppConstant.Module_Name117, AppConstant.Operate_id5, AppConstant.Operate_Name5, announcement.getTitle(), null);
					}
		    }
			}
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e) {
			throw new SysRunException(e);
		}
	}

}
