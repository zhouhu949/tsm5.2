package com.qftx.tsm.report.scontrol;

import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.tsm.callrecord.dto.CallNalysisDto;
import com.qftx.tsm.callrecord.util.CallRecordGetUtil;
import com.qftx.tsm.main.service.MainService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �Ŷ�ͨ��Ч�ʷ���
 * User�� bxl
 * Date�� 2015/12/18
 * Time�� 15:29
 */
@Controller
@RequestMapping(value = "/report")
public class TeamCallNalysisAction {
    private static final Logger logger = Logger.getLogger(TeamCallNalysisAction.class.getName());

    @Autowired
    private OrgGroupUserService orgGroupUserService;
    @Autowired
    private MainService mainService;
    @Autowired private CachedService cachedService;
    /**
     * 团队通话效率统计分析
     *
     * @create 2016-1-25 上午9:30:29 zwj
     * @history 4.x
     */
    @RequestMapping("/teamCallNalysis")
    public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            ShiroUser user = ShiroUtil.getShiroUser();
            Map<String, Object> map = new HashMap<String, Object>();
            String groupId = request.getParameter("groupId");
            String startDate = request.getParameter("startDate");
            String endDate = request.getParameter("endDate");
            String dateType = request.getParameter("dateType");
            // 获取部门集合
            List<TeamGroupBean> teamGroups = mainService.queryManageGroupList(user.getAccount(),user.getOrgId());
            if (StringUtils.isNotBlank(dateType) && "1".equals(dateType)) {
                startDate =DateUtil.formatDate(DateUtil.dateBegin(new Date()), "YYYY-MM-dd HH:mm:ss");
                endDate = DateUtil.formatDate(DateUtil.dateEnd(new Date()), "YYYY-MM-dd HH:mm:ss");
            }else if(StringUtils.isNotBlank(dateType) && "2".equals(dateType)){
            	startDate =DateUtil.formatDate(DateUtil.getWeekBegin(new Date()), "YYYY-MM-dd HH:mm:ss");
            	endDate = DateUtil.formatDate(DateUtil.getWeekEnd(new Date()), "YYYY-MM-dd HH:mm:ss");
            }else{
            	if(StringUtils.isNotBlank(startDate)){
            		startDate = startDate+" 00:00:00";
            	}else{
            		dateType = "1";
            		startDate =DateUtil.formatDate(DateUtil.dateBegin(new Date()), "YYYY-MM-dd HH:mm:ss");
            	}
            	if(StringUtils.isNotBlank(endDate)){
            		endDate = endDate+" 23:59:59";
            	}else{
            		endDate = DateUtil.formatDate(DateUtil.dateEnd(new Date()), "YYYY-MM-dd HH:mm:ss");
            	}
            }
            // 查询 用户集合
            Map<String, String> userMap = new HashMap<String, String>();
            userMap.put("orgId", user.getOrgId());
            userMap.put("account", user.getAccount());
            if (StringUtils.isNotBlank(groupId)) {
                userMap.put("groupId", groupId);
            }
         
            List<String> users = orgGroupUserService.getShareGroupUser(userMap);
            if (users != null && users.size() > 0) {
                map.put("inputAccs", users);
                if (StringUtils.isNotBlank(startDate)) {
                    map.put("startTimeBegin", startDate);
                }
                if (StringUtils.isNotBlank(endDate)) {
                    map.put("startTimeEnd", endDate);
                }
                map.put("orgId", user.getOrgId());
                map.put("callState","2");
                map.put("timeLengthBegin","1");
                // 调用 接口查询
                List<CallNalysisDto> list = CallRecordGetUtil.getRecordeCallNalysis(map);

                StringBuffer inputAccs = new StringBuffer();
                StringBuffer times = new StringBuffer();
                StringBuffer nums = new StringBuffer();
                if (list != null && list.size() > 0) {
                	Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
                    for (CallNalysisDto call : list) {
                    	Integer times1 = null;
                        Integer times2 = null;
                        if (call != null) {         
                        	if(nameMap != null){
                        		 inputAccs.append(nameMap.get(call.getInputAcc()) == null ? call.getInputAcc() : nameMap.get(call.getInputAcc()));
                        	}else{
                        		 inputAccs.append(call.getInputAcc());
                        	}                           
                            inputAccs.append(",");
                            times1 = call.getTimeLength()/60;
                            times2 = call.getTimeLength()%60;
                            if(times2 != null && times2 >0){
                            	times1 = times1+1;
                            }
                            times.append(times1);
                            times.append(",");
                            nums.append(call.getCallNum());
                            nums.append(",");
                        }
                    }               
                    request.setAttribute("inputAccs", inputAccs.substring(0, inputAccs.length() - 1));
                    request.setAttribute("times", times.substring(0, times.length() - 1));
                    request.setAttribute("nums", nums.substring(0, nums.length() - 1));
                }
            }
            Date date = new Date();
            request.setAttribute("toWeekFrom", DateUtil.formatDate(DateUtil.getWeekBegin(date), "YYYY-MM-dd"));
            request.setAttribute("toWeekTo", DateUtil.formatDate(DateUtil.getWeekEnd(date), "YYYY-MM-dd"));
            request.setAttribute("toDayFrom",DateUtil.formatDate(DateUtil.dateBegin(date), "YYYY-MM-dd"));
            request.setAttribute("toDayTo", DateUtil.formatDate(DateUtil.dateEnd(date), "YYYY-MM-dd"));
            
            request.setAttribute("startDate", DateUtil.getDateDay(DateUtil.parseDate(startDate)));
            request.setAttribute("endDate", DateUtil.getDateDay(DateUtil.parseDate(endDate)));
            request.setAttribute("teamGroups", teamGroups);
            request.setAttribute("groupId", groupId);
            request.setAttribute("dateType", dateType);
        } catch (Exception e) {
            throw new SysRunException(e);
        }
        return "report/teamCallNalysis";
    }

    	public static void main(String[] args) {	
    		if(Integer.parseInt("8000")%60>0){
    			System.out.println(Integer.parseInt("8000")/60+1);
    		}
    		
    		
    	}
}
