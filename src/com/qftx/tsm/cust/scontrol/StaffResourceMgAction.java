package com.qftx.tsm.cust.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.dto.OrgMemberDto;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.MD5Utils;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.common.util.constants.SysConstant;
import com.qftx.tsm.cust.dto.StaffDto;
import com.qftx.tsm.cust.service.StaffResourceMgService;
import com.qftx.tsm.log.service.LogStaffInfoService;
import com.qftx.tsm.option.dao.DataDictionaryMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 员工资源管理
 *
 * @author: wuwei
 * @since: 2015年12月1日 下午7:07:22
 * @history:
 */
@Controller
@RequestMapping(value = "res/staffMg/")
public class StaffResourceMgAction extends BaseAction {
	private static Logger logger = Logger.getLogger(StaffResourceMgAction.class);
	public static String LOG_STAFF = "0";
	@Autowired
	private StaffResourceMgService staffResourceMgService;
	@Autowired
	transient private OrgGroupService orgGroupService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private OrgGroupUserService orgGroupUserService;
	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;
	@Autowired
	private TsmTeamGroupMemberService tsmTeamGroupMemberService;
	@Autowired
	private UserService userService;
	@Autowired
	private LogStaffInfoService logStaffInfoService;

	@ResponseBody
	@RequestMapping(value = "setLogStaff/{key}")
	public String setLogStaff(HttpServletRequest request, @PathVariable String key) {
		if ("1".equals(key)) {
			LOG_STAFF = "1";
		} else {
			LOG_STAFF = "0";
		}
		return LOG_STAFF;
	}

	@ResponseBody
	@RequestMapping(value = "getLogStaff")
	public String getLogStaff(HttpServletRequest request) {
		return LOG_STAFF;
	}

	@RequestMapping(value = "staffMg")
	public String staffMg(HttpServletRequest request, OrgMemberDto orgMemberDto) throws Exception {
		try {
			setDyField(request);
			ShiroUser user = ShiroUtil.getShiroUser();
			// 查询该单位下所有成员
			OrgGroup orgGroup = new OrgGroup();
			orgGroup.setOrgId(user.getOrgId());
			Map<String, String> map1 = new HashMap<String, String>();
			map1.put("account", user.getAccount());
			map1.put("orgId", user.getOrgId());
			List<String> groupIds = orgGroupUserService.getShareGroupId(map1);
			if (groupIds != null && groupIds.size() > 0) {
				ShiroUtil.setSession(SysConstant._MEMBERSESSION, groupIds);
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/tsm/resource/queryStaffList";
	}

	/**
	 * 查询员工资源管理列表
	 *
	 * @return
	 * @create 2015年12月1日 下午7:32:13 wuwei
	 * @history
	 */
	@RequestMapping(value = "queryStaffResMgListold")
	public String queryStaffResMgListold(HttpServletRequest request, StaffDto staffDto) {
		String mark = request.getParameter("mark"); // 1：点击了部门树触发
		String groupId = request.getParameter("groupId");
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			staffDto.setOrgId(orgId);

			List<String> groupIds = (List) ShiroUtil.getSession(SysConstant._MEMBERSESSION);
			if (!(groupIds != null && groupIds.size() > 0)) {
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("account", user.getAccount());
				map1.put("orgId", user.getOrgId());
				groupIds = orgGroupUserService.getShareGroupId(map1);
				ShiroUtil.setSession(SysConstant._MEMBERSESSION, groupIds);
			}
			if ("1".equals(mark)) {
				// 查询 所选部门成员列表
				if (StringUtils.isNotBlank(groupId)) { // 查询该部门以及子部门成员
					ShiroUtil.removeSession(SysConstant._MEMBERSESSION);
					List<String> groupIds1 = orgGroupUserService.getTreeGroupIds(user.getOrgId(), groupId);
					if (groupIds1 != null && groupIds1.size() > 0) { // 所有子部门ID集合，与权限集合对比，得到交集
						Map<String, String> map = new HashMap<String, String>();
						for (String group : groupIds1) {
							map.put(group, group);
						}
						Iterator<String> ite = groupIds.iterator();
						while (ite.hasNext()) {
							if (map.get(ite.next()) == null) {
								ite.remove();
							}
						}
					}
				}
			}
			// 查询团队成员信息
			List<StaffDto> staffList = new ArrayList<StaffDto>();
			List<OrgGroupUser> memberGroupList = cachedService.getOrgGroupMember(orgId);
			List<String> accounts = new ArrayList<String>();
			if (memberGroupList != null && memberGroupList.size() > 0) {
				for (OrgGroupUser orgGroupUser : memberGroupList) {
					if (groupIds != null && groupIds.size() > 0) {
						for (String gId : groupIds) {
							if (gId.equals(orgGroupUser.getGroupId())) {
								accounts.add(orgGroupUser.getMemberAcc());
								break;
							}
						}
					}
				}
			}
			staffDto.setOrgId(orgId);
			staffDto.setAccounts(accounts);
			if (accounts != null && accounts.size() > 0) {
				// 模糊查询处理
				if (StringUtils.isNotBlank(staffDto.getQueryContition())) {
					String queryText = staffDto.getQueryContition().trim();
					staffDto.setQueryContition(queryText);
					if (queryText.matches("[0-9]+")) {
						staffDto.setQueryPhone(true);
					} else {
						staffDto.setQueryPhone(false);
					}
				}
				staffList = staffResourceMgService.queryStaffPageList(staffDto);
			}

			setStaffList(orgId, staffList);
			request.setAttribute("groupId", groupId);
			request.setAttribute("staffList", staffList);
			request.setAttribute("staffDto", staffDto);
			request.setAttribute("mark", "1");
		} catch (Exception e) {
			logger.debug("查询员工资源管理列表.loginName =" + ShiroUtil.getUser().getName() + "groupId=" + groupId);
			logger.error(e.getMessage(), e);
		}
		return "tsm/resource/staffMgRight";
	}

	/**
	 * 查询员工资源管理列表
	 *
	 * @return
	 * @create 2015年12月1日 下午7:32:13 wuwei
	 * @history
	 */
	@RequestMapping(value = "queryStaffResMgList")
	public String queryStaffResMgList(HttpServletRequest request, StaffDto staffDto) {
		String mark = request.getParameter("mark"); // 1：点击了部门树触发
		String groupId = request.getParameter("groupId");
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			staffDto.setOrgId(orgId);
			
			List<String> groupIds = (List) ShiroUtil.getSession(SysConstant._MEMBERSESSION);
			if (!(groupIds != null && groupIds.size() > 0)) {
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("account", user.getAccount());
				map1.put("orgId", user.getOrgId());
				groupIds = orgGroupUserService.getShareGroupId(map1);
				ShiroUtil.setSession(SysConstant._MEMBERSESSION, groupIds);
			}
			if ("1".equals(mark)) {
				// 查询 所选部门成员列表
				if (StringUtils.isNotBlank(groupId)) { // 查询该部门以及子部门成员
					ShiroUtil.removeSession(SysConstant._MEMBERSESSION);
					List<String> groupIds1 = orgGroupUserService.getTreeGroupIds(user.getOrgId(), groupId);
					if (groupIds1 != null && groupIds1.size() > 0) { // 所有子部门ID集合，与权限集合对比，得到交集
						Map<String, String> map = new HashMap<String, String>();
						for (String group : groupIds1) {
							map.put(group, group);
						}
						Iterator<String> ite = groupIds.iterator();
						while (ite.hasNext()) {
							if (map.get(ite.next()) == null) {
								ite.remove();
							}
						}
					}
				}
			}
			// 查询团队成员信息
			List<OrgGroupUser> memberGroupList = cachedService.getOrgGroupMember(orgId);
			List<String> accounts = new ArrayList<String>();
			if (memberGroupList != null && memberGroupList.size() > 0) {
				for (OrgGroupUser orgGroupUser : memberGroupList) {
					if (groupIds != null && groupIds.size() > 0) {
						for (String gId : groupIds) {
							if (gId.equals(orgGroupUser.getGroupId())) {
								accounts.add(orgGroupUser.getMemberAcc());
								break;
							}
						}
					}
				}
			}
			staffDto.setOrgId(orgId);
			staffDto.setAccounts(accounts);
			request.setAttribute("groupId", groupId);
			request.setAttribute("staffDto", staffDto);
			request.setAttribute("mark", "1");
		} catch (Exception e) {
			logger.debug("查询员工资源管理列表.loginName =" + ShiroUtil.getUser().getName() + "groupId=" + groupId);
			logger.error(e.getMessage(), e);
		}
		return "tsm/resource/staffMgRight";
	}

	@ResponseBody
	@RequestMapping(value = "getStaffResMgJson")
	public Map<String, Object> getStaffResMgJson(HttpServletRequest request, StaffDto staffDto) {
		Map<String, Object> jsonMap = new HashMap<>();
		String mark = request.getParameter("mark"); // 1：点击了部门树触发
		String groupId = request.getParameter("groupId");
		List<StaffDto> staffList = new ArrayList<StaffDto>();
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			staffDto.setOrgId(orgId);
			String key = CachedNames.CACHE_RES + CachedNames.SEPARATOR + MD5Utils.getMD5String(JSON.toJSONString(staffDto)+groupId);
			Map<String, Object> cacheList = (Map<String, Object>) CachedUtil.getInstance().get(key);
			if (cacheList != null) {
				return cacheList;
			}
			List<String> groupIds = (List) ShiroUtil.getSession(SysConstant._MEMBERSESSION);
			if (!(groupIds != null && groupIds.size() > 0)) {
				Map<String, String> map1 = new HashMap<String, String>();
				map1.put("account", user.getAccount());
				map1.put("orgId", user.getOrgId());
				groupIds = orgGroupUserService.getShareGroupId(map1);
				ShiroUtil.setSession(SysConstant._MEMBERSESSION, groupIds);
			}
			if ("1".equals(mark)) {
				// 查询 所选部门成员列表
				if (StringUtils.isNotBlank(groupId)) { // 查询该部门以及子部门成员
					ShiroUtil.removeSession(SysConstant._MEMBERSESSION);
					List<String> groupIds1 = orgGroupUserService.getTreeGroupIds(user.getOrgId(), groupId);
					if (groupIds1 != null && groupIds1.size() > 0) { // 所有子部门ID集合，与权限集合对比，得到交集
						Map<String, String> map = new HashMap<String, String>();
						for (String group : groupIds1) {
							map.put(group, group);
						}
						Iterator<String> ite = groupIds.iterator();
						while (ite.hasNext()) {
							if (map.get(ite.next()) == null) {
								ite.remove();
							}
						}
					}
				}
			}
			// 查询团队成员信息
			List<OrgGroupUser> memberGroupList = cachedService.getOrgGroupMember(orgId);
			List<String> accounts = new ArrayList<String>();
			if (memberGroupList != null && memberGroupList.size() > 0) {
				for (OrgGroupUser orgGroupUser : memberGroupList) {
					if (groupIds != null && groupIds.size() > 0) {
						for (String gId : groupIds) {
							if (gId.equals(orgGroupUser.getGroupId())) {
								accounts.add(orgGroupUser.getMemberAcc());
								break;
							}
						}
					}
				}
			}
			staffDto.setOrgId(orgId);
			staffDto.setAccounts(accounts);
			if(staffDto.getdDateType() != null && staffDto.getdDateType() != 0 && staffDto.getdDateType() != 5){
				staffDto.setStartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				staffDto.setEndDate(getEndDateStr(staffDto.getdDateType()));
			}
			if (accounts != null && accounts.size() > 0) {
				// 模糊查询处理
				if (StringUtils.isNotBlank(staffDto.getQueryContition())) {
					String queryText = staffDto.getQueryContition().trim();
					staffDto.setQueryContition(queryText);
					if (queryText.matches("[0-9]+")) {
						staffDto.setQueryPhone(true);
					} else {
						staffDto.setQueryPhone(false);
					}
				}
				staffList = staffResourceMgService.queryStaffPageList(staffDto);
			}
			setStaffList(orgId, staffList);
			// 是否有数据标记
			jsonMap.put("item", staffDto);
			jsonMap.put("list", staffList);
			CachedUtil.getInstance().set(key, jsonMap, 10);
		} catch (Exception e) {
			logger.debug("查询员工资源管理列表.loginName =" + ShiroUtil.getUser().getName() + "groupId=" + groupId);
			logger.error(e.getMessage(), e);
		}
		return jsonMap;
	}

	/**
	 * 操作列表
	 *
	 * @param request
	 * @param staffDto
	 * @return
	 * @create 2015年12月1日 下午7:13:13 wuwei
	 * @history
	 */
	@RequestMapping(value = "queryOptList")
	public String queryResAssinRecycleList(HttpServletRequest request, StaffDto staffDto) {
		try {
			staffDto.setOrgId(ShiroUtil.getUser().getOrgId());
			List<StaffDto> staffDtoList = staffResourceMgService.queryResAssinRecycleList(staffDto);
			request.setAttribute("staffList", staffDtoList);
			request.setAttribute("staffDto", staffDto);
		} catch (Exception e) {
			logger.error("操作列表异常!loginName= " + ShiroUtil.getUser().getName() + "|orgId=" + ShiroUtil.getUser().getOrgId());
			e.printStackTrace();
			throw new SysRunException(e);
		}
		return "/tsm/resource/queryOptResList";
	}

	/**
	 * 跳转到员工资源管理-分配页面
	 *
	 * @return
	 * @create 2015年12月2日 上午10:01:26 wuwei
	 * @history
	 */
	@RequestMapping(value = "toResAssgin")
	public String toResAssgin(HttpServletRequest request) {
		try {
			String staffAccs = request.getParameter("staffAccs") == null ? "" : request.getParameter("staffAccs").trim() + "";
			Map<String, String> map = new HashMap<String, String>();
			String orgId = ShiroUtil.getUser().getOrgId();
			map.put("orgId", orgId);
			List<Map<String, Object>> resGroupList = new ArrayList<>();
			resGroupList = cachedService.getResGroupList1(orgId);
			Map<String, String> dataMap = getDataDictionaryBean(orgId);
			request.setAttribute("isLimtis", StringUtils.isEmpty(dataMap.get("val")) ? "0" : dataMap.get("val"));
			request.setAttribute("limitIsOpen", StringUtils.isEmpty(dataMap.get("limitIsOpen")) ? "0" : dataMap.get("limitIsOpen"));
			request.setAttribute("resGroupList", resGroupList);
			request.setAttribute("inputAcc", ShiroUtil.getUser().getAccount());
			request.setAttribute("staffAccs", staffAccs.substring(0, staffAccs.length()));
			request.setAttribute("staffLen", staffAccs.substring(0, staffAccs.length()).split(",").length);
			String taskId = GuidUtil.getUUID();
			request.setAttribute("taskId", taskId);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/tsm/resource/assign_res";
	}

	/**
	 * 资源分组下资源数量
	 *
	 * @create 2015年12月2日 上午10:19:08 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "getResourceSum")
	public String getResourceSum(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getUser();
			String orgId = user.getOrgId();
			String account = user.getAccount();
			List<String> groups = new ArrayList<String>();
			if (user.getIssys() != null && user.getIssys() == 1) {
				// 查询 所选部门成员列表
				if (StringUtils.isNotBlank(user.getGroupId())) { // 查询该部门以及子部门成员
					Map<String, String> map1 = new HashMap<String, String>();
					map1.put("account", account);
					map1.put("orgId", orgId);
					groups = cachedService.getShareGroupId(map1);
					if (groups.size() == 0 || groups == null) {
						return 0+"";
					}
				}
			} 
			String resGroupId = request.getParameter("resGroupId") == null ? "" : request.getParameter("resGroupId").trim();
			String starttime = request.getParameter("starttime") == null ? "" : request.getParameter("starttime").trim();
			String endtime = request.getParameter("endtime") == null ? "" : request.getParameter("endtime").trim();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("groupId", resGroupId);
			map.put("starttime", starttime);
			map.put("endtime", endtime);
			map.put("account", user.getAccount());
			map.put("deptIds", groups.size() > 0 ? groups : null);
			// 1获取 可分配资源总数
			int resSum = staffResourceMgService.getResourcemaxSum(map);
			return resSum + "";
		} catch (Exception e) {
			return AppConstant.RESULT_EXCEPTION;
		}

	}

	/**
	 * 公司资源分配
	 *
	 * @create 2013-10-29 上午10:19:40 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "getAssginResource")
	public String getAssginResource(HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<String, String>();
		ShiroUser user = ShiroUtil.getShiroUser();
		String orgId = user.getOrgId();
		String account = user.getAccount();
		String name = user.getName();
		List<String> groups = new ArrayList<String>();
		String reqId = SysBaseModelUtil.getModelId();
		Map<String, Object> map = new HashMap<String, Object>();
		String taskId = request.getParameter("taskId");
		String groupId = request.getParameter("resGroupId") == null ? "" : request.getParameter("resGroupId").trim();
		String starttime = request.getParameter("starttime") == null ? "" : request.getParameter("starttime").trim();
		String endtime = request.getParameter("endtime") == null ? "" : request.getParameter("endtime").trim();
		String plansum = request.getParameter("plansum") == null ? "" : request.getParameter("plansum").trim();
		String every_sum = request.getParameter("every_sum") == null ? "" : request.getParameter("every_sum").trim();
		String inputAcc = request.getParameter("inputAcc") == null ? "" : request.getParameter("inputAcc").trim();
		String resNum = request.getParameter("resNum") == null ? "" : request.getParameter("resNum").trim();
		String staffAccs = request.getParameter("staffAccs") == null ? "" : request.getParameter("staffAccs").trim();
		String context = "reqId=" + reqId + "account=" + user.getAccount() + "staffAccs=" + staffAccs;
		try {
			logger.debug("getAssginResource reqId=" + reqId + " ,resGroupId=" + groupId + ",starttime=" + starttime + ",endtime=" + endtime + ",plansum="
					+ plansum + ",every_sum=" + every_sum + ",staffAccs=" + staffAccs + ",resNum=" + resNum);
			context = "getAssginResource reqId=" + reqId + " ,resGroupId=" + groupId + ",starttime=" + starttime + ",endtime=" + endtime + ",plansum="
					+ plansum + ",every_sum=" + every_sum + ",staffAccs=" + staffAccs + ",resNum=" + resNum;
			logStaffInfoService.saveLogInfo(orgId, user.getAccount(), "公司分配请求", context, reqId);
			logger.debug("getAssginResource reqId=" + reqId + ",account=" + user.getAccount() + "staffAccs=" + staffAccs);
			if (StringUtils.isEmpty(resNum) || resNum == "0") {
				resultMap.put("result", "0006");
				resultMap.put("msg", "分配资源数为0");
				return JSON.toJSONString(resultMap);
			}
			long startTime = System.currentTimeMillis();
			boolean isExist = cachedService.isExist(orgId, CachedNames.ORG_ASSIGN);
			if (user.getIssys() != null && user.getIssys() == 1) {
				// 查询 所选部门成员列表
				if (StringUtils.isNotBlank(user.getGroupId())) { // 查询该部门以及子部门成员
					Map<String, String> map1 = new HashMap<String, String>();
					map1.put("account", account);
					map1.put("orgId", orgId);
					groups = cachedService.getShareGroupId(map1);
				}
			}
			if (isExist) {
				resultMap.put("result", "0005");
				resultMap.put("msg", "正在分配中");
				logger.debug("正在分配中" + "orgId=" + orgId);
				context = context + "。正在分配中";
				logStaffInfoService.saveLogInfo(orgId, account, "正在分配中", context, reqId);
				return JSON.toJSONString(resultMap);
			}
			map.put("v_staffIds", staffAccs);
			map.put("v_groupId", groupId);
			map.put("resNum", resNum);
			map.put("v_starttime", starttime);
			map.put("v_endtime", endtime);
			map.put("v_plan_sum", plansum);
			map.put("v_every_sum", every_sum);
			map.put("v_org_id", orgId);
			map.put("v_input_acc", inputAcc);
			map.put("v_deptIds", groups);
			staffResourceMgService.saveAssginRes(reqId, map, resultMap, orgId,account,name,taskId);
			if (!"0".equals(resultMap.get("result"))) {
				logger.debug(resultMap.get("msg"));
			}
			logger.debug("getAssginResource reqId=" + reqId + "耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
			context = "reqId=" + reqId + "耗时：" + (System.currentTimeMillis() - startTime) + "毫秒";
			logStaffInfoService.saveLogInfo(orgId, user.getAccount(), "公司资源分配请求成功", context, reqId);
			return JSON.toJSONString(resultMap);
		} catch (Exception e) {
			logger.error("getAssginResource reqId=" + reqId + "staffAccs=" + staffAccs + "参数=" + map.toString(), e);
			context = "getAssginResource reqId=" + reqId + "staffAccs=" + staffAccs + "参数=" + map.toString() + e.getMessage();
			logStaffInfoService.saveLogInfo(orgId, account, "公司资源分配请求异常", context, reqId);
			cachedService.delOptMap(reqId, orgId, CachedNames.ORG_ASSIGN, user.getAccount());
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	/**
	 * 跳转到资源回收弹出框页面
	 *
	 * @return
	 * @create 2015年12月3日 上午10:30:27 wuwei
	 * @history
	 */
	@RequestMapping(value = "toRecyleRes")
	public String toRecyleRes(HttpServletRequest request) {
		String staffAccs = request.getParameter("staffAccs") == null ? "" : request.getParameter("staffAccs").trim();
		try {
			String[] ids = staffAccs.split("\\,");
			Map<String, Object> map = new HashMap<String, Object>();
			String orgId = ShiroUtil.getUser().getOrgId();
			map.put("orgId", orgId);
			map.put("ids", ids);
			// 获取每人可回收的最大条数
			int minSum = staffResourceMgService.getRecyleMinSum(map);
			List<Map<String, Object>> resGroupList = cachedService.getResGroupList1(orgId);
			request.setAttribute("resGroupList", resGroupList);			
			request.setAttribute("staffAcc", ShiroUtil.getUser().getAccount());
			request.setAttribute("minSum", minSum);
			request.setAttribute("staffAccs", staffAccs);
			String taskId = GuidUtil.getUUID();
			request.setAttribute("taskId", taskId);
		} catch (Exception e) {
			logger.error("员工资源管理-跳转资源回收页面异常。loginName=" + ShiroUtil.getUser().getName() + "参数：staffAccs=" + staffAccs + e.getMessage(), e);
			throw new SysRunException(e);
		} finally {
		}
		return "/tsm/resource/recyleRes";
	}

	/**
	 * 回收资源
	 *
	 * @create 2015年12月3日 上午10:34:12 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping("recyleRes")
	public String recyleRes(HttpServletRequest request) {
		// 获取查询条件
		Map<String, String> resultMap = new HashMap<String, String>();
		ShiroUser user = ShiroUtil.getShiroUser();
		String orgId = user.getOrgId();
		String taskId = request.getParameter("taskId");
		String resGroupId = request.getParameter("resGroupId") == null ? "" : request.getParameter("resGroupId").trim();
		String staffAccs = request.getParameter("staffAccs") == null ? "" : request.getParameter("staffAccs").trim();
		String recyleSum = request.getParameter("recyleSumid") == null ? "" : request.getParameter("recyleSumid").trim();
		String concatStatus = request.getParameter("concatStatus") == null ? "" : request.getParameter("concatStatus").trim();
		String newResGroupId = request.getParameter("newResGroupId") == null ? "" : request.getParameter("newResGroupId").trim();
		String reqId = SysBaseModelUtil.getModelId();
		String context = "reqId=" + reqId + ",account=" + user.getAccount()+ ",resGroupId=" + resGroupId + ",staffAccs=" + staffAccs;
		logger.debug("recyleRes reqId=" + reqId + ",account=" + user.getAccount() + ",resGroupId=" + resGroupId + ",staffAccs=" + staffAccs + ",recyleSum=" + recyleSum);
		context = "recyleRes reqId=" + reqId + ",account=" + user.getAccount() + ",resGroupId=" + resGroupId + ",staffAccs=" + staffAccs + ",recyleSum=" + recyleSum;
		logStaffInfoService.saveLogInfo(orgId, user.getAccount(), "公司回收请求", context, reqId);
		boolean isExist = cachedService.isExist(orgId, CachedNames.ORG_RECYLE);
		if (isExist) {
			resultMap.put("result", "0005");
			resultMap.put("msg", "正在回收中");
			logger.debug("正在回收中" + "orgId=" + orgId);
			logStaffInfoService.saveLogInfo(orgId, user.getAccount(), "正在回收中", context + "正在回收中", reqId);
			return JSON.toJSONString(resultMap);
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("v_staffAccs", staffAccs);
		map.put("v_resGroupId", resGroupId);
		map.put("v_recyleSum", recyleSum);
		map.put("concatStatus", concatStatus);
		map.put("v_newResGroupId", newResGroupId);
		try {
			staffResourceMgService.saveRecycleResource(reqId, map, resultMap, user.getOrgId(), user.getAccount(),taskId);
			logStaffInfoService.saveLogInfo(orgId, user.getAccount(), "公司资源回收请求成功", "", reqId);
			return resultMap.get("result");
		} catch (Exception e) {
			logger.error("reqId=" + reqId + "loginName=" + ShiroUtil.getUser().getName() + "参数：map=" + map.toString(), e);
			context = "reqId=" + reqId + "loginName=" + ShiroUtil.getUser().getName() + "参数：map=" + map.toString() + e.getMessage();
			logStaffInfoService.saveLogInfo(orgId, user.getAccount(), "公司资源回收请求异常", context, reqId);
			cachedService.delOptMap(reqId, orgId, CachedNames.ORG_RECYLE, user.getAccount());
			return AppConstant.RESULT_EXCEPTION;
		}
	}

}
