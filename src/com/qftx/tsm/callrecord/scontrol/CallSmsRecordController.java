package com.qftx.tsm.callrecord.scontrol;

import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.sms.dto.TsmCallSendSmsDto;
import com.qftx.tsm.sms.service.TsmCallSendSmsService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 *	来电短信记录
 */
@Controller
@RequestMapping("/sms/call/record")
public class CallSmsRecordController{
	Logger logger=Logger.getLogger(CallSmsRecordController.class);
	@Autowired
	private TsmCallSendSmsService tsmCallSendSmsService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private UserService userServie;
	
	/** 来电短信发送记录 */
	@RequestMapping("/smsSendList")
	public String smsSendList(HttpServletRequest request,TsmCallSendSmsDto dto){
		try{
			String dDateType = request.getParameter("dDateType");
       
			ShiroUser user = ShiroUtil.getShiroUser();
			dto.setRoleType(user.getIssys()); // 角色类别：0--销售，1--管理者			
			dto.setOrgId(user.getOrgId());
			dto.setIsDel((short)0);
			//  发送时间
			if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
				dto.setStartDate(getStartDateStr(Integer.parseInt(dDateType)));
				dto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			
				// 所有者查询方式 1-全部 2-只看自己 3-选中查询
				dto.setOsType(StringUtils.isBlank(dto.getOsType()) ? "1" : dto.getOsType());
				if (StringUtils.isNotEmpty(dto.getAccs()) && "3".equals(dto.getOsType())) {
					String[] ownerAccs = dto.getAccs().split(",");
					List<String> owaList = Arrays.asList(ownerAccs);
					dto.setOwnerAccs(owaList);
				}else if("1".equals(dto.getOsType())){
					List<String>list = userServie.getUserAccByOrgId(user.getOrgId());
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
			
			String loginAcc=user.getAccount();
			dto.setOwnerAcc(loginAcc);
			dto.setOrderKey(" SEND_DATE DESC");
			List<TsmCallSendSmsDto> list = tsmCallSendSmsService.getSendSmsListPage(dto);
			if(list!= null && list.size() >0){
				 Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
					for(TsmCallSendSmsDto smsDto : list){
						smsDto.setOwnerName(nameMap.get(smsDto.getAccount()) == null ? smsDto.getAccount() : nameMap.get(smsDto.getAccount()));
					}
			}
			
			request.setAttribute("list", list);
			request.setAttribute("item", dto);
			request.setAttribute("dDateType", dDateType);
			request.setAttribute("queryType", dto.getQueryType());
			request.setAttribute("queryText", dto.getQueryText());
			setIsReplaceWord(request, user); // 设置是否开启用*替换电话号码中间四位
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return "/call/smsCallRecordList";
	}
	
	
	/** 删除短信发送记录 */
	@ResponseBody
	@RequestMapping("/deleteSmsSend")
	public String deleteSmsSend(HttpServletRequest request){
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
				tsmCallSendSmsService.removeBatch(ids,user.getOrgId());
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
	 * 设置是否开启用*替换电话号码中间四位
	 */
	private void setIsReplaceWord(HttpServletRequest request,ShiroUser user) {
		// 查询缓存
		List<DataDictionaryBean> dataMap=cachedService.getDirList(AppConstant.DATA24, user.getOrgId());
		// 对电话号码中间4位用*号模糊处理设置到session中
		Set<String> dicSet = new HashSet<String>();
		dicSet.add(AppConstant.DATA24 + "_" + user.getOrgId());
		Integer idReplaceWord = 0;
		if (!dataMap.isEmpty() && dataMap.get(0) != null && !cachedService.judgeHideWhiteList(user.getOrgId(), user.getAccount())) {
			idReplaceWord = new Integer(dataMap.get(0).getDictionaryValue());
		}
		request.setAttribute("idReplaceWord", idReplaceWord);
	}
}
