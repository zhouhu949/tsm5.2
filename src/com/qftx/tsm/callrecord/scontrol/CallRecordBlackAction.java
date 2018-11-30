package com.qftx.tsm.callrecord.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.callrecord.bean.CallRecordBlack;
import com.qftx.tsm.callrecord.dto.CallRecordBlackDto;
import com.qftx.tsm.callrecord.service.CallRecordBlackService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 黑名单
 * Date： 2016/1/12
 * Time： 19:33
 */
@Controller
@RequestMapping("/callrecord/black")
public class CallRecordBlackAction {
    private Logger logger = Logger.getLogger(CallRecordBlackAction.class);

    @Autowired
    private CallRecordBlackService callRecordBlackService;
    
    /** 通信管理 黑名单列表 */
	@RequestMapping("/list")
	public String list(HttpServletRequest request,CallRecordBlackDto dto){
		try{
			String dDateType = request.getParameter("dDateType");			
			ShiroUser user = ShiroUtil.getShiroUser();				
			dto.setOrgId(user.getOrgId());
			//  发送时间
			if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
				dto.setStartDate(getStartDateStr(Integer.parseInt(dDateType)));
				dto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}	
			if(StringUtils.isNotBlank(dto.getAccs())){			
				String[]accss = dto.getAccs().split(",");
				dto.setOwnerAccs(Arrays.asList(accss));
			}
			List<CallRecordBlack> list = callRecordBlackService.getCallRecordBlackListPage(dto);
			request.setAttribute("list", list);
			request.setAttribute("item", dto);
			request.setAttribute("dDateType", dDateType);
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return "/call/callRecordBlackList";
	}

	/** 通信管理 黑名单添加 */
	@RequestMapping("/addBlack")
	@ResponseBody
	public String addBlack(HttpServletRequest request,CallRecordBlack bean){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			bean.setId(SysBaseModelUtil.getModelId());
			bean.setOrgId(user.getOrgId());
			bean.setInputAcc(user.getAccount());
			bean.setInputDate(new Date());
			callRecordBlackService.create(bean);
		}catch(Exception e){
			throw new SysRunException(e);
		}
		return AppConstant.RESULT_SUCCESS;
	}
	
	/** 删除 黑名单 */
	@ResponseBody
	@RequestMapping("/deleteCallBlack")
	public String deleteCallBlack(HttpServletRequest request){
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
				Map<String,Object>map = new HashMap<String,Object>();
				map.put("orgId", user.getOrgId());
				map.put("list",ids);
				callRecordBlackService.removeBatchBy(map);
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
}
