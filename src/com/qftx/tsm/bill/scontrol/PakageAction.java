package com.qftx.tsm.bill.scontrol;

import com.alibaba.fastjson.JSONObject;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dto.OrgMemberDto;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.bill.dto.LogCommuOptorDto;
import com.qftx.tsm.bill.dto.LogFdOptorDto;
import com.qftx.tsm.bill.service.LogCommuOptorService;
import com.qftx.tsm.bill.util.HDUtils;
import com.qftx.tsm.bill.util.SmsUtils;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;


@Controller
@RequestMapping("/pakage/allocation")
public class PakageAction {
    private Logger logger = Logger.getLogger(PakageAction.class);
    private static final String SUCCESS_CODE = "_SUCCESS";
    @Autowired
    private UserService userService;
    @Autowired
    private LogCommuOptorService logCommuOptorService;
    @Autowired
    private OrgGroupUserService orgGroupUserService;

    @RequestMapping("/memberPakageList")
    public String memberPakageList(HttpServletRequest request, HttpServletResponse response, OrgMemberDto dto) {
        try {
            String flag = request.getParameter("flag");
            ShiroUser user = ShiroUtil.getShiroUser();
            dto.setOrgId(user.getOrgId());
            if (StringUtils.isBlank(flag)) {//打开菜单时同步一次
                Map<String, Object> requestMap = HDUtils.splitRequestParamOfUnitLens(user.getOrgId());
                String modelName = "【查单位用户余额请求】";
                String responeStr = HDUtils.sendMsgToHD(requestMap, modelName);
                if (!responeStr.equals("9008") && !responeStr.equals("9999")) {
                    Map<String, Object> return_map = JSONObject.parseObject(responeStr, Map.class);
                    if (return_map.get("code").equals(SUCCESS_CODE)) {
                        List<Map<String, Object>> balances = (List<Map<String, Object>>) return_map.get("acctList");
                        userService.updateUserLensByAcc(balances);
                    } else {
                        logger.error("*********" + modelName + "处理失败，返回结果（code:" + return_map.get("code") + "，desc:" + return_map.get("desc") + "）");
                    }
                }
            }
            User tmpUser = userService.getAdmin(user.getOrgId());
            request.setAttribute("totalTimes", tmpUser.getCommunicationsTimes() == null ? "0" : tmpUser.getCommunicationsTimes());
            request.setAttribute("totalSms", tmpUser.getMessagesNumber() == null ? "0" : tmpUser.getMessagesNumber());

            // 取得单位所有分组
//			List<TeamGroupBean> teamGroups = orgGroupService.queryByOrgId(user.getOrgId());
//			request.setAttribute("teamGroups", teamGroups);
            //处理查询条件
            if (dto.getdDateType() != null && dto.getdDateType() != 0 && dto.getdDateType() != 5) {
                dto.setStartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
                dto.setEndDate(getEndDateStr(dto.getdDateType()));
            }

            if (StringUtils.isNotBlank(dto.getGroupIdStr())) {
                dto.setGroupIds(Arrays.asList(dto.getGroupIdStr().split(",")));
            }
            List<OrgMemberDto> memberDtos = userService.getOrgMemberListPage(dto);

            request.setAttribute("memberDtos", memberDtos);
            request.setAttribute("orgMemberDto", dto);
        } catch (Exception e) {
            throw new SysRunException(e);
        }
        return "bill/member_pakage_list";
    }

    @RequestMapping("/groupMemberPakageList")
    public String groupMemberPakageList(HttpServletRequest request, HttpServletResponse response, OrgMemberDto dto) {
        try {
            String flag = request.getParameter("flag");
            ShiroUser user = ShiroUtil.getShiroUser();
            dto.setOrgId(user.getOrgId());
            if (StringUtils.isBlank(flag)) {//打开菜单时同步一次
                Map<String, Object> requestMap = HDUtils.splitRequestParamOfUnitLens(user.getOrgId());
                String modelName = "【查单位用户余额请求】";
                String responeStr = HDUtils.sendMsgToHD(requestMap, modelName);
                if (!responeStr.equals("9008") && !responeStr.equals("9999")) {
                    Map<String, Object> return_map = JSONObject.parseObject(responeStr, Map.class);
                    if (return_map.get("code").equals(SUCCESS_CODE)) {
                        List<Map<String, Object>> balances = (List<Map<String, Object>>) return_map.get("acctList");
                        userService.updateUserLensByAcc(balances);
                    } else {
                        logger.error("*********" + modelName + "处理失败，返回结果（code:" + return_map.get("code") + "，desc:" + return_map.get("desc") + "）");
                    }
                }
            }
            User tmpUser = userService.getByAccount(user.getAccount());
            request.setAttribute("totalTimes", tmpUser.getCommunicationsTimes() == null ? "0" : tmpUser.getCommunicationsTimes());
//			request.setAttribute("totalSms", tmpUser.getMessagesNumber()==null?"0":tmpUser.getMessagesNumber());

            // 取得单位所有分组
//			List<TeamGroupBean> teamGroups = orgGroupService.queryByOrgId(user.getOrgId());
//			request.setAttribute("teamGroups", teamGroups);
            //处理查询条件
            if (dto.getdDateType() != null && dto.getdDateType() != 0 && dto.getdDateType() != 5) {
                dto.setStartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
                dto.setEndDate(getEndDateStr(dto.getdDateType()));
            }

            if (StringUtils.isNotBlank(dto.getGroupIdStr())) {
                dto.setGroupIds(Arrays.asList(dto.getGroupIdStr().split(",")));
            } else {
                Map<String, String> map = new HashMap<String, String>();
                map.put("orgId", user.getOrgId());
                map.put("account", user.getAccount());
                List<String> shareGroupIds = orgGroupUserService.getShareGroupId(map);
                dto.setGroupIds(shareGroupIds);
            }
            dto.setUserAccount(user.getAccount());
            List<OrgMemberDto> memberDtos = userService.getOrgMemberListPage(dto);
            request.setAttribute("memberDtos", memberDtos);
            request.setAttribute("orgMemberDto", dto);
        } catch (Exception e) {
            throw new SysRunException(e);
        }
        return "bill/group_member_pakage_list";
    }

    /**
     * 通信时长操作日志
     *
     * @param request
     * @param response
     * @param optorDto
     * @return
     * @create 2016年3月1日 下午3:25:11 lixing
     * @history
     */
    @RequestMapping("/findLogCommuOptorList")
    public String findLogCommuOptorList(HttpServletRequest request, HttpServletResponse response, LogCommuOptorDto optorDto) {
        try {
            ShiroUser user = ShiroUtil.getShiroUser();
            optorDto.setOrgId(user.getOrgId());
            if (StringUtils.isNotBlank(optorDto.getAccountsStr())) {
                optorDto.setAccounts(Arrays.asList(optorDto.getAccountsStr().split(",")));
            }
            if (optorDto.getdDateType() != null && optorDto.getdDateType() != 0 && optorDto.getdDateType() != 5) {
                optorDto.setStartDate(getStartDateStr(optorDto.getdDateType()));
                optorDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
            }
            List<LogCommuOptorDto> optorDtos = logCommuOptorService.getLogCommuOptorListPage(optorDto);

            request.setAttribute("optorDtos", optorDtos);
            request.setAttribute("optorDto", optorDto);
        } catch (Exception e) {
            throw new SysRunException(e);
        }
        return "bill/log_commuoptor_list";
    }

    /**
     * 蜂豆操作日志
     *
     * @param request
     * @param response
     * @param optorDto
     * @return
     * @create 2016年3月1日 下午3:25:11 lixing
     * @history
     */
    @RequestMapping("/findLogFdOptorList")
    public String findLogFdOptorList(HttpServletRequest request, HttpServletResponse response, LogFdOptorDto optorDto) {
        try {
            ShiroUser user = ShiroUtil.getShiroUser();
            optorDto.setOrgId(user.getOrgId());
            if (StringUtils.isNotBlank(optorDto.getAccountsStr())) {
                optorDto.setAccounts(Arrays.asList(optorDto.getAccountsStr().split(",")));
            }
            if (optorDto.getdDateType() != null && optorDto.getdDateType() != 0 && optorDto.getdDateType() != 5) {
                optorDto.setStartDate(getStartDateStr(optorDto.getdDateType()));
                optorDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
            }
            List<LogFdOptorDto> optorDtos = logCommuOptorService.getLogFdOptorListPage(optorDto);

            request.setAttribute("optorDtos", optorDtos);
            request.setAttribute("optorDto", optorDto);
        } catch (Exception e) {
            throw new SysRunException(e);
        }
        return "bill/log_commuoptor_currency_list";
    }
    
    /**
     * 跳转分配通信时长
     *
     * @param request
     * @param response
     * @param accs
     * @return
     * @create 2016年2月29日 下午7:18:03 lixing
     * @history
     */
    @RequestMapping("/toRechargeTime")
    public String toRechargeTime(HttpServletRequest request, HttpServletResponse response, String accs, String module) {
        try {
            ShiroUser user = ShiroUtil.getShiroUser();
            User tmpUser;
            if (StringUtils.isNotBlank(module) && module.equals("1")) {//小组
                tmpUser = userService.getByAccount(user.getAccount());
            } else {
                tmpUser = userService.getAdmin(user.getOrgId());
            }
            double useTime = tmpUser.getCommunicationsTimes() == null ? Double.valueOf("0") : tmpUser.getCommunicationsTimes();
            int nums = accs.split(",").length;
            request.setAttribute("totalTimes", useTime);
            request.setAttribute("rechargeNum", nums);
            request.setAttribute("maxRechargeTime", Math.floor((useTime / nums)*10)/10);
            request.setAttribute("restTimes", useTime - (Math.floor((useTime / nums)*10)/10) * nums);
            request.setAttribute("accs", accs);
            request.setAttribute("module", module);
        } catch (Exception e) {
            throw new SysRunException(e);
        }
        return "bill/recharge_time";
    }


    /**
     * 分配时长
     *
     * @param request
     * @param response
     * @param accs
     * @param rechargeTimes
     * @return
     * @create 2016年2月29日 下午8:11:39 lixing
     * @history
     */
    @RequestMapping("/rechargeBatch")
    @ResponseBody
    public String rechargeBatch(HttpServletRequest request, HttpServletResponse response, String accs, Double rechargeTimes, String module) {
        String re = "1";
        try {
            ShiroUser user = ShiroUtil.getShiroUser();
            if (StringUtils.isNotBlank(accs)) {
                User tmpUser;
                if (StringUtils.isNotBlank(module) && module.equals("1")) {//小组
                    tmpUser = userService.getByAccount(user.getAccount());
                } else {
                    tmpUser = userService.getAdmin(user.getOrgId());
                }
                Map<String, Object> requestMap = HDUtils.splitRequestParamOfTx(tmpUser.getUserAccount(), accs.replaceAll(",", ";"), rechargeTimes, user.getOrgId());
                String modelName = "【通信时长分配请求】";
                String responeStr = HDUtils.sendMsgToHD(requestMap, modelName);
                if (!responeStr.equals("9008") && !responeStr.equals("9999")) {
                    Map<String, Object> return_map = JSONObject.parseObject(responeStr, Map.class);
                    if (return_map.get("code").equals(SUCCESS_CODE)) {
                        List<Map<String, Object>> balances = (List<Map<String, Object>>) return_map.get("acctList");
                        logCommuOptorService.rechargeCommuTimes(balances, user, accs, rechargeTimes, user.getOrgId(), null, module);
                    } else {
                        re = "-1";
                        logger.error("*********" + modelName + "处理失败，返回结果（code:" + return_map.get("code") + "，desc:" + return_map.get("desc") + "）");
                    }
                } else {
                    re = "-1";
                }
            }
        } catch (Exception e) {
            logger.error("分配通信时长失败!", e);
            re = "-1";
        }
        return re;
    }


    /**
     * 跳转通信回收
     *
     * @param request
     * @param response
     * @param accs
     * @return
     * @create 2016年3月1日 上午10:28:17 lixing
     * @history
     */
    @RequestMapping("/toRecoverTime")
    public String toRecoverTime(HttpServletRequest request, HttpServletResponse response, String accs, String module) {
        try {
            ShiroUser user = ShiroUtil.getShiroUser();
            User tmpUser;
            if (StringUtils.isNotBlank(module) && module.equals("1")) {//小组
                tmpUser = userService.getByAccount(user.getAccount());
            } else {
                tmpUser = userService.getAdmin(user.getOrgId());
            }
            double useTime = tmpUser.getCommunicationsTimes() == null ? Double.valueOf("0") : tmpUser.getCommunicationsTimes();
            int nums = accs.split(",").length;
            request.setAttribute("totalTimes", useTime);
            request.setAttribute("recoverNum", nums);
            request.setAttribute("accs", accs);
            request.setAttribute("module", module);
        } catch (Exception e) {
            throw new SysRunException(e);
        }
        return "bill/recover_time";
    }

    /**
     * 批量回收通信时长
     *
     * @param request
     * @param response
     * @param accs
     * @param recoverTimes
     * @return
     * @create 2016年3月1日 上午11:04:09 lixing
     * @history
     */
    @RequestMapping("/recoverBatch")
    @ResponseBody
    public String recoverBatch(HttpServletRequest request, HttpServletResponse response, String accs, Double recoverTimes, String module) {
        String re = "1";
        try {
            ShiroUser user = ShiroUtil.getShiroUser();
            if (StringUtils.isNotBlank(accs)) {
                User tmpUser;
                if (StringUtils.isNotBlank(module) && module.equals("1")) {//小组
                    tmpUser = userService.getByAccount(user.getAccount());
                } else {
                    tmpUser = userService.getAdmin(user.getOrgId());
                }
                Map<String, Object> requestMap = HDUtils.splitRequestParamOfTxRec(tmpUser.getUserAccount(), accs.replaceAll(",", ";"), recoverTimes, user.getOrgId());
                String moduleName = "【通信时长回收请求】";
                String responeStr = HDUtils.sendMsgToHD(requestMap, moduleName);
                if (!responeStr.equals("9008") && !responeStr.equals("9999")) {
                    Map<String, Object> return_map = JSONObject.parseObject(responeStr, Map.class);
                    if (return_map.get("code").equals(SUCCESS_CODE)) {
                        List<Map<String, Object>> balances = (List<Map<String, Object>>) return_map.get("acctList");
                        logCommuOptorService.recoverCommuTimes(balances, user, module);
                    } else {
                        re = "-1";
                        logger.error("*********" + moduleName + "处理失败，返回结果（code:" + return_map.get("code") + "，desc:" + return_map.get("desc") + "）");
                    }
                } else {
                    re = "-1";
                }
            }
        } catch (Exception e) {
            logger.error("批量回收通信时长失败!", e);
            re = "-1";
        }
        return re;
    }


    /**
     * 跳转分配短信
     *
     * @param request
     * @param response
     * @param accs
     * @return
     * @create 2016年3月1日 上午11:42:44 lixing
     * @history
     */
    @RequestMapping("/toRechargeSms")
    public String toRechargeSms(HttpServletRequest request, HttpServletResponse response, String accs) {
        try {
            ShiroUser user = ShiroUtil.getShiroUser();
            User tmpUser = userService.getAdmin(user.getOrgId());
            long useSms = tmpUser.getMessagesNumber() == null ? Long.parseLong("0") : tmpUser.getMessagesNumber();
            int nums = accs.split(",").length;
            request.setAttribute("totalSms", useSms);
            request.setAttribute("rechargeNum", nums);
            request.setAttribute("maxRechargeSms", (int) useSms / nums);
            request.setAttribute("restSms", useSms - ((int) useSms / nums) * nums);
            request.setAttribute("accs", accs);
        } catch (Exception e) {
            throw new SysRunException(e);
        }
        return "bill/recharge_sms";
    }


    /**
     * 批量分配短信
     *
     * @param request
     * @param response
     * @param accs
     * @param rechargeSms
     * @return
     * @create 2016年3月1日 下午1:44:57 lixing
     * @history
     */
    @RequestMapping("/rechargeSmsBatch")
    @ResponseBody
    public String rechargeSmsBatch(HttpServletRequest request, HttpServletResponse response, String accs, Integer rechargeSms) {
        String re = "1";
        try {
            ShiroUser user = ShiroUtil.getShiroUser();
            if (StringUtils.isNotBlank(accs)) {
                User tmpUser = userService.getAdmin(user.getOrgId());
                String moduleName = "【批量分配短信】";
                Map<String, Object> requestMap = SmsUtils.splitRequestParamOfSms(tmpUser.getUserAccount(), accs.replaceAll(",", ";"), rechargeSms);
                String responeStr = SmsUtils.sendMsgToSms(requestMap, moduleName);
                if (!responeStr.equals("9008") && !responeStr.equals("9999")) {
                    Map<String, Object> return_map = JSONObject.parseObject(responeStr, Map.class);
                    if (return_map.get("code").equals(SUCCESS_CODE)) {
                        List<Map<String, Object>> balances = (List<Map<String, Object>>) return_map.get("balances");
                        logCommuOptorService.smsRecharge(user, tmpUser, balances, rechargeSms, user.getOrgId());
                    } else {
                        re = "-1";
                        logger.error("*********" + moduleName + "处理失败，返回结果（code:" + return_map.get("code") + "，descr:" + return_map.get("descr") + "）");
                    }
                } else {
                    re = "-1";
                }
            }
        } catch (Exception e) {
            logger.error("批量分配短信失败!", e);
            re = "-1";
        }
        return re;
    }

    /**
     * 跳转短信回收
     *
     * @param request
     * @param response
     * @param accs
     * @return
     * @create 2016年3月1日 上午10:28:17 lixing
     * @history
     */
    @RequestMapping("/toRecoverSms")
    public String toRecoverSms(HttpServletRequest request, HttpServletResponse response, String accs) {
        try {
            ShiroUser user = ShiroUtil.getShiroUser();
            User tmpUser = userService.getAdmin(user.getOrgId());
            long useSms = tmpUser.getMessagesNumber() == null ? Long.parseLong("0") : tmpUser.getMessagesNumber();
            int nums = accs.split(",").length;
            request.setAttribute("totalSms", useSms);
            request.setAttribute("recoverNum", nums);
            request.setAttribute("accs", accs);
        } catch (Exception e) {
            throw new SysRunException(e);
        }
        return "bill/recover_sms";
    }


    /**
     * 批量回收短信
     *
     * @param request
     * @param response
     * @param accs
     * @param recoverSms
     * @return
     * @create 2016年3月1日 下午1:51:36 lixing
     * @history
     */
    @RequestMapping("/recoverSmsBatch")
    @ResponseBody
    public String recoverSmsBatch(HttpServletRequest request, HttpServletResponse response, String accs, Integer recoverSms) {
        String re = "1";
        try {
            ShiroUser user = ShiroUtil.getShiroUser();
            User tmpUser = userService.getAdmin(user.getOrgId());
            String moduleName = "【批量回收短信】";
            Map<String, Object> requestMap = SmsUtils.splitRequestParamOfSmsRec(tmpUser.getUserAccount(), accs.replaceAll(",", ";"), recoverSms);
            String responeStr = SmsUtils.sendMsgToSms(requestMap, moduleName);
            if (!responeStr.equals("9008") && !responeStr.equals("9999")) {
                Map<String, Object> return_map = JSONObject.parseObject(responeStr, Map.class);
                if (return_map.get("code").equals(SUCCESS_CODE)) {
                    List<Map<String, Object>> balances = (List<Map<String, Object>>) return_map.get("balances");
                    logCommuOptorService.smsRecoverCommuTimes(balances, user, tmpUser);
                } else {
                    re = "-1";
                    logger.error("*********" + moduleName + "处理失败，返回结果（code:" + return_map.get("code") + "，descr:" + return_map.get("descr") + "）");
                }
            } else {
                re = "-1";
            }
        } catch (Exception e) {
            logger.error("批量回收短信失败!", e);
            re = "-1";
        }
        return re;
    }

    
    /**
	 * 设置用户是否闲置
	 */
	@ResponseBody
	@RequestMapping(value = "setUnUsed")
	public String setUnUsed(HttpServletRequest request) {
	 try{
		String isUnUsed = request.getParameter("isUnUsed");
		String userAccount = request.getParameter("userAccount");
		if (StringUtils.isNotBlank(isUnUsed) && StringUtils.isNotBlank(userAccount)) {
			User entity = new User();
			entity.setUserAccount(userAccount);
			entity.setIsUnUsed(Integer.parseInt(isUnUsed));
			userService.updateByAccount(entity);
			return AppConstant.RESULT_SUCCESS;
		}
		return AppConstant.RESULT_FAILURE;
		} catch (Exception e) {
			return AppConstant.RESULT_EXCEPTION;
		}
	}
    
    
    /**
     * 获取第一天
     *
     * @param type 1-当天 2-本周 3-本月 4-半年
     * @return
     * @create 2015年12月14日 下午3:48:05 lixing
     * @history
     */
    public String getStartDateStr(Integer type) {
        String str = "";
        if (type == 1) {
            str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
        } else if (type == 2) {
            str = DateUtil.getWeekFirstDay(new Date());
        } else if (type == 3) {
            str = DateUtil.getMonthFirstDay(new Date());
        } else if (type == 4) {
            str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), -180), DateUtil.DATE_DAY);
        }
        return str;
    }


    /**
     * 获取最后一天
     *
     * @param type 1-当天 2-本周 3-本月 4-半年
     * @return
     * @create 2015年12月14日 下午3:48:05 lixing
     * @history
     */
    public String getEndDateStr(Integer type) {
        String str = "";
        if (type == 1) {
            str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
        } else if (type == 2) {
            str = DateUtil.getWeekLastDay(new Date());
        } else if (type == 3) {
            str = DateUtil.getMonthLastDay(new Date());
        } else if (type == 4) {
            str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), 180), DateUtil.DATE_DAY);
        }
        return str;
    }
}
