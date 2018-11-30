package com.qftx.tsm.callrecord.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.FileUtil;
import com.qftx.common.util.IContants;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.email.dto.EmailRecordDto;
import com.qftx.tsm.email.service.TsmEmailSendService;
import com.qftx.tsm.sys.bean.SysFileBean;
import com.qftx.tsm.sys.service.SysFileService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.*;

/**
 *	邮件记录
 */
@Controller
@RequestMapping("/email/record")
public class EmailRecordController{
	Logger logger=Logger.getLogger(EmailRecordController.class);
	@Autowired
	private TsmEmailSendService tsmEmailSendService;
	@Autowired
    private SysFileService sysFileService;
	@Autowired
    private CachedService cachedService;
	@Autowired
    private TsmGroupShareinfoService tsmGroupShareinfoService;
	/** 邮件发送记录 */
	@RequestMapping("/emailSendList")
	public String emailSendList(HttpServletRequest request,EmailRecordDto dto){
		try{
			String dDateType = request.getParameter("dDateType");
			
			ShiroUser user = ShiroUtil.getShiroUser();
			dto.setRoleType(user.getIssys()); // 角色类别：0--销售，1--管理者			
			dto.setOrgId(user.getOrgId());
			dto.setIsDel(0);
			//  发送时间
			if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
				dto.setStartDate(getStartDateStr(Integer.parseInt(dDateType)));
				dto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}	
			
			 // 管理者查询
			if(dto.getRoleType() != null && dto.getRoleType()==1){
				// 所有者查询方式 1-全部 2-只看自己 3-选中查询
				dto.setOsType(StringUtils.isBlank(dto.getOsType()) ? "1" : dto.getOsType());
				if (StringUtils.isNotEmpty(dto.getAccs()) && "3".equals(dto.getOsType())) {
					String[] ownerAccs = dto.getAccs().split(",");
					List<String> owaList = Arrays.asList(ownerAccs);
					dto.setOwnerAccs(owaList);
				}else if("1".equals(dto.getOsType())){
					List<String>list = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(),user.getAccount());
					if(list!=null && list.size()>0){			
						StringBuffer sb = new StringBuffer();
						for(String str: list){
							sb.append(str);
							sb.append(",");
						}
						if(sb.length()>0){
							sb = sb.deleteCharAt(sb.length() - 1);
							dto.setAccs(sb.toString());
						}	
						list.add(user.getAccount());
						dto.setOwnerAccs(list);
					}  					
				}	
			}
			
			String loginAcc=user.getAccount();
			dto.setOwnerAcc(loginAcc);
			List<EmailRecordDto> list = tsmEmailSendService.getSendLogListPage(dto);
			Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			for(EmailRecordDto erd : list){
				erd.setSendUser(nameMap.get(erd.getSendUser()));
			}
			request.setAttribute("list", list);
			request.setAttribute("item", dto);
			request.setAttribute("dDateType", dDateType);
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return "/call/emailRecordList";
	}
	
	/** 邮件发送记录 右侧信息 */
	@RequestMapping("/rightEmailPage")
	public String rightEmailPage(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();	
			String sendLogId = request.getParameter("sendLogId");
			if(StringUtils.isNotBlank(sendLogId)){				
				EmailRecordDto entity = new EmailRecordDto();
				entity.setOrgId(user.getOrgId());
				entity.setSendLogId(sendLogId);
				List<EmailRecordDto> list = tsmEmailSendService.getSendLogByRight(entity);
				if(list != null && list.size() >0){
					if(StringUtils.isNotBlank(list.get(0).getFileIds())){
						// 查询附件
						List<String> fileds = Arrays.asList(list.get(0).getFileIds().split(","));
						List<SysFileBean>sysFiles = new ArrayList<SysFileBean>();
						for(String filed:fileds){
							SysFileBean sysFile = new SysFileBean();
							sysFile.setId(filed);
							sysFile.setOrgId(user.getOrgId());
							SysFileBean sysFileBean = sysFileService.getByCondtion(sysFile);
							if(sysFileBean != null){
								sysFiles.add(sysFileBean);
							}
						}
						request.setAttribute("sysFiles", sysFiles);
					}
					Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
					for(EmailRecordDto erd : list){
						erd.setSendUser(nameMap.get(erd.getSendUser()));
					}
					request.setAttribute("tes",list.get(0));
				}
			}
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return "/call/submodual/emailRecordList_right";
	}
	
	/** 删除短信发送记录 */
	@ResponseBody
	@RequestMapping("/deleteEmailSend")
	public String deleteEmailSend(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();	
			String str=request.getParameter("ids");
			if(StringUtils.isNotEmpty(str)){
				List<String> ids=new ArrayList<String>();
				for (String id : str.split(",")) {
					if(StringUtils.isNotEmpty(id)){
					    ids.add(id);
					}
				}
				tsmEmailSendService.removeBatch(ids,user.getOrgId());
			}
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return AppConstant.RESULT_SUCCESS;
	}
	
	/** 
	 * 获取查询时间
	 * @param type 1-当天 2-本周 3-本月 4-半年
	 * @return 
	 * @create  2015年12月14日 下午3:48:05 lixing
	 * @history  
	 */
	private String getStartDateStr(Integer type){
		String str = "";
		if(type == 1){
			str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
		}else if(type == 2){
			str = DateUtil.getWeekFirstDay(new Date());
		}else if(type == 3){
			str = DateUtil.getMonthFirstDay(new Date());
		}else if(type == 4){
			str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), -180), DateUtil.DATE_DAY);
		}
		return str;
	}
	
	/**
	 * 邮件发送记录下载
	 * */
	@RequestMapping("/emailDownLoad")
	public String emailDownLoad(HttpServletRequest request,HttpServletResponse response){
		try{
			String filePath = request.getParameter("filePath");
			String fileName = request.getParameter("fileName");
			if(StringUtils.isNotBlank(filePath) && StringUtils.isNotBlank(fileName)){
				fileName = URLDecoder.decode(fileName, IContants.CHAR_ENCODING);
				FileUtil.downloadByteFile(response, filePath, fileName);
			}			
		}catch(Exception e){
			logger.error("邮件记录附件下载异常！", e);
		}		
		return null;
	}
}
