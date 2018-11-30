package com.qftx.tsm.cust.service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dao.*;
import com.qftx.tsm.cust.dto.ResOptDetailDto;
import com.qftx.tsm.cust.dto.ResourceGroupDto;
import com.qftx.tsm.cust.dto.StaffDto;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.log.service.LogStaffInfoService;
import com.qftx.tsm.log.service.LogSysOperateService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.dao.DataDictionaryMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

@Service
public class StaffResourceMgService {
	private static Logger logger = Logger.getLogger(StaffResourceMgService.class);
	@Autowired
	private StaffResourceMgMapper staffResourceMgMapper;
	@Autowired
	private ComResourceMapper comResourceMapper;
	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;

	@Autowired
	private AllocatRecycleLogBeanMapper allocatRecycleLogBeanMapper;
	@Autowired
	private ResOptorMapper resOptorMapper;
	// @Autowired
	// TaoCustService taoCustService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private TaoTagMapper taoTagMapper;
	@Autowired
	private ComResourceService comResourceService;
	@Autowired
	PlatformTransactionManager transactionManager;
	@Autowired
	transient private JdbcTemplate jdbcTemplate;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private LogBatchInfoService logBatchInfoService;
	@Autowired
	private LogSysOperateService logSysOperateService;
	@Autowired
	private LogStaffInfoService logStaffInfoService;
	@Autowired
	private LogUserOperateService logUserOperateService;

	public List<StaffDto> getList() {
		return staffResourceMgMapper.find();
	}

	public List<StaffDto> getListByCondtion(StaffDto entity) {
		return staffResourceMgMapper.findByCondtion(entity);
	}

	public List<StaffDto> getListPage(StaffDto entity) {
		return staffResourceMgMapper.findListPage(entity);
	}

	public StaffDto getByPrimaryKey(String id) {
		return staffResourceMgMapper.getByPrimaryKey(id);
	}

	public void create(StaffDto entity) {
		// TODO Auto-generated method stub

	}

	public void createBatch(List<StaffDto> entitys) {
		// TODO Auto-generated method stub

	}

	public void modify(StaffDto entity) {
		// TODO Auto-generated method stub

	}

	public void modifyTrends(StaffDto entity) {
		// TODO Auto-generated method stub

	}

	public void modifyBatch(List<StaffDto> entitys) {
		// TODO Auto-generated method stub

	}

	public void modifyTrendsBatch(List<StaffDto> entitys) {
		// TODO Auto-generated method stub

	}

	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	public void removeBatch(List<String> ids) {
		// TODO Auto-generated method stub

	}

	public List<StaffDto> getTeamMemberSum(Map<String, String> map) {
		return staffResourceMgMapper.findTeamMemberSum(map);
	}

	public List<StaffDto> queryStaffPageList(StaffDto staffDto) {
		return staffResourceMgMapper.findStaffListPage(staffDto);
	}

	public List<ResourceGroupDto> getResGroupList(Map<String, String> map) {
		return staffResourceMgMapper.findResGroupList(map);
	}

	public int getResourcemaxSum(Map<String, Object> map) {
		return staffResourceMgMapper.findResourcemaxSum(map);
	}

	public Map<String, String> getAssginResource(Map<String, String> map) {
		return staffResourceMgMapper.findAssginResource(map);
	}

	public int getRecyleMinSum(Map<String, Object> map) {
		return staffResourceMgMapper.findRecyleMinSum(map);
	}

	public Map<String, String> getRecycleResource(Map<String, String> map) {
		return staffResourceMgMapper.findRecycleResource(map);
	}

	public List<StaffDto> queryResAssinRecycleList(StaffDto staffDto) {
		return staffResourceMgMapper.getResAssinRecycleListPage(staffDto);
	}

	public List<StaffDto> queryStaffUnGroupPageList(StaffDto staffDto) {
		return staffResourceMgMapper.findStaffUnGroupListPage(staffDto);
	}

	public void createOption(StaffDto staffDto) {
		staffResourceMgMapper.insertOption(staffDto);
	}

	/**
	 * 员工资源管理-分配资源
	 *
	 * @param map
	 * @create 2015年12月2日 下午2:20:23 wuwei
	 * @history
	 */
	public void saveAssginRes(String reqId, Map<String, Object> map, Map<String, String> resultMap, String orgId, String account,String name,String taskId) {
		logger.debug("员工资源管理-分配资源 map=" + map.toString());
		String code = "";
		long startTime = System.currentTimeMillis();
		try {
			String staffIds = (String)map.get("v_staffIds");
			String[] staffAccs = staffIds.split(",");
			Map<String, String> dataMap = getDataDictionaryBean((String)map.get("v_org_id"));
			String planSum = (String)map.get("v_plan_sum");
			String totalRes = (String)map.get("resNum");
			String everySum = StringUtils.isEmpty((String)map.get("v_every_sum")) ? "0" : (String)map.get("v_every_sum");
			List<String> deptIds = (List<String>) map.get("v_deptIds");
			Map<String, Object> resMap = new HashMap<String, Object>(); // 获取可分配资源
			resMap.put("groupId", map.get("v_groupId"));
			resMap.put("starttime", map.get("v_starttime"));
			resMap.put("endtime", map.get("v_endtime"));
			resMap.put("everyNum", staffAccs.length * new Integer(everySum));
			resMap.put("offSet", new Integer(0));
			resMap.put("account", account);
			resMap.put("deptIds", deptIds);
			resMap.put("orgId", map.get("v_org_id"));
			if (new Integer(planSum) > new Integer(totalRes)) {// 判断资源数和计划分配数比较
				resultMap.put("result", "0001");
				resultMap.put("msg", "没有足够资源分配，请重新分配！！");
				cachedService.delOptMap(reqId, orgId, CachedNames.ORG_ASSIGN, account);
				return;
			}
			Integer totalCount = 0;
			Map<String, String> ownerResMap = new HashMap<String, String>(); // 获取已分配资源
			for (int i = 0; i < staffAccs.length; i++) {
				String staffAcc = staffAccs[i];
				ownerResMap.put("ownerAcc", staffAcc);
				ownerResMap.put("orgId", orgId);
				int myResNum = comResourceMapper.findresourceNum(ownerResMap);// 个人拥有资源数量
				String result = isAssign(dataMap, myResNum);
				if ("0".equals(result)) {// 验证是否开启限制资源上限和是否超过上限数
					if (myResNum < new Integer(dataMap.get("val"))) {
						Integer remainSum = new Integer(dataMap.get("val")) - myResNum;
						if (new Integer(everySum) > remainSum) {
							totalCount = totalCount + remainSum;
						}else {
							totalCount = totalCount + new Integer(everySum);
						}
					}else {
						continue;
					}
				}else if ("1".equals(result)) {
					totalCount = totalCount + new Integer(everySum);
				}
			}
			Map<String, Object> map1 = new HashMap<>();
			map1.put("total", totalCount);
			map1.put("finished", 0);
			map1.put("status", true);
			cachedService.setProgress(orgId, taskId, map1);
			String batchId =  SysBaseModelUtil.getModelId();
			AssginThread thread = new AssginThread(comResourceMapper, staffAccs, orgId, reqId, everySum, taskExecutor, cachedService, comResourceService,
					account, logStaffInfoService, dataMap, resMap,logUserOperateService,name,map1,taskId,batchId);
			thread.start();
			resultMap.put("result", "".equals(code) ? "0" : code);
			resultMap.put("msg", "提交成功！");
			long endTime = System.currentTimeMillis();
			logger.debug("saveAssginRes reqId=" + reqId + "。员工资源管理-分配资源 staffAccs=" + JSON.toJSONString(staffAccs) + "耗时：" + (endTime - startTime) + " 毫秒");
		} catch (Exception e) {
			logger.error("saveAssginRes reqId=" + reqId + "。jsonStr：" + JsonUtil.getJsonString(map) + e.getMessage(), e);
			String context = "saveAssginRes reqId=" + reqId + "。jsonStr：" + JsonUtil.getJsonString(map) + e.getMessage();
			logStaffInfoService.saveLogInfo(orgId, account, "公司资源分配处理异常", context, reqId);
			cachedService.delOptMap(reqId, orgId, CachedNames.ORG_ASSIGN, account);
			throw new SysRunException(e);
		}
	}
	
	// 是否分配
		public String isAssign(Map<String, String> map, int resNum) {
			if ("1".equals(map.get("followIsOpen")) && "1".equals(map.get("limitIsOpen"))) {
				return "0";
			} else {
				return "1";
			}
		}

	/**
	 * 返回系统客户跟进设置
	 *
	 * @param orgId
	 * @return
	 * @create 2015年11月17日 下午2:37:04 wuwei
	 * @history
	 */
	public Map<String, String> getDataDictionaryBean(String orgId) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("orgId", orgId);
		paramMap.put("code", AppConstant.DATA15);
		DataDictionaryBean ddBean = dataDictionaryMapper.findDataDictionaryBean(paramMap);
		map.put("followIsOpen", ddBean.getDictionaryValue());
		paramMap.put("code", AppConstant.DATA28);
		ddBean = dataDictionaryMapper.findDataDictionaryBean(paramMap);
		map.put("val", ddBean.getDictionaryValue());
		map.put("limitIsOpen", ddBean.getIsOpen());
		return map;
	}

	/**
	 * 回收资源
	 *
	 * @param map
	 * @param resultMap
	 * @create 2015年12月3日 上午10:40:26 wuwei
	 * @history
	 */
	public void saveRecycleResource(String reqId, Map<String, String> map, Map<String, String> resultMap, String orgId, String account,String taskId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String name = user.getName();
		String operateAcc = user.getAccount();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		// Map<String, Object> optMap = new HashMap<String, Object>();
		String[] staffAccs = map.get("v_staffAccs").split(",");
		String resGroupId = map.get("v_resGroupId");
		String newResGroupId = map.get("v_newResGroupId");
		String recyleSum = map.get("v_recyleSum");
		String concatStatus = map.get("concatStatus");
		paramMap.put("orgId", orgId);
		ArrayList<Future<ResOptDetailDto>> resultList = new ArrayList<Future<ResOptDetailDto>>();
		Map<String, Object> map1 = new HashMap<>();
		Integer totalCount = 0;
 		try {
			int mark = 0;
			int count = 0; 
			for (int i = 0; i < staffAccs.length; i++) {
				String staffAcc = staffAccs[i];
				List<String> ids = new ArrayList<String>();
				paramMap.put("ownerAcc", staffAcc);
				paramMap.put("resGroupId", resGroupId);
				paramMap.put("concatStatus", concatStatus);
				int ableRecyleRes = comResourceMapper.findMaxRecyleRes(paramMap);
				if (new Integer(recyleSum) >= ableRecyleRes) {
					totalCount = totalCount + ableRecyleRes;
				} else {
					totalCount = totalCount + new Integer(recyleSum);
				}
			}
			map1.put("total", totalCount);
			map1.put("finished", 0);
			map1.put("status", true);
			cachedService.setProgress(orgId, taskId, map1);
			String batchId =  SysBaseModelUtil.getModelId();
			for (int i = 0; i < staffAccs.length; i++) {
				String staffAcc = staffAccs[i];
				List<String> ids = new ArrayList<String>();
				paramMap.put("ownerAcc", staffAcc);
				paramMap.put("resGroupId", resGroupId);
				paramMap.put("concatStatus", concatStatus);
				int ableRecyleRes = comResourceMapper.findMaxRecyleRes(paramMap);
				if (new Integer(recyleSum) >= ableRecyleRes) {
					paramMap.put("ableRecyleRes", ableRecyleRes);
				} else {
					paramMap.put("ableRecyleRes", new Integer(recyleSum));
				}
				List<ResCustInfoBean> myResList = comResourceMapper.findRecyleRes(paramMap);
				if (myResList != null && myResList.size() > 0) {
					for (ResCustInfoBean res : myResList) {
						res.setStatus(1);
						ids.add(res.getResCustId());
					}
					mark++;
					resultList.add(taskExecutor.submit(new ResOptThread(reqId, ids, staffAcc, orgId, account, cachedService, comResourceService, 2,
							CachedNames.ORG_RECYLE, logStaffInfoService,name,newResGroupId,taskId,map1,batchId)));
					count += ids.size();
				}
			}
			if (mark > 0) {
				OptResultThread optThread = new OptResultThread(reqId, account, resultList, orgId, 2, cachedService, CachedNames.ORG_RECYLE,
						logStaffInfoService, comResourceService,logUserOperateService,count);
				optThread.setAccs(staffAccs);
				optThread.start();
			} else {
				cachedService.delOptMap(reqId, orgId, CachedNames.ORG_RECYLE, account);
				logStaffInfoService.saveLogInfo(orgId, account, "公司资源回收未处理", "mark=" + mark, reqId);
			}
			resultMap.put("result", "0");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logStaffInfoService.saveLogInfo(orgId, account, "资源回收处理异常", e.getMessage(), reqId);
			throw new SysRunException(e);
		}

	}

	public void batchRecyleRes(final String reqId, final List<ResCustInfoBean> sublist, final String ownerAcc, final String userAcc, final String orgId,
			String sql) {
		long timerBegin = System.currentTimeMillis();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, userAcc);
					ps.setString(2, orgId);
					ps.setString(3, sublist.get(i).getResCustId());
				}

				// 返回更新的结果集条数
				public int getBatchSize() {
					return sublist.size();
				}
			});

			List<String> ids = new ArrayList<String>();
			for (ResCustInfoBean custInfo : sublist) {
				ids.add(custInfo.getResCustId());
			}
			ids.add("ownerAcc=" + ownerAcc);
			ids.add("size=" + ids.size());
			final List<String> idList = ids;
			//logBatchInfoService.saveLogInfo(orgId, userAcc,user.getName(),OperateEnum.LOG_RES_RECYLE, idList,ownerAcc);
		} catch (Exception e) {
			logger.error(
					"reqId=" + reqId + "。batchRecyleRes 异常.+ sql=" + sql + "|userAcc=" + userAcc + "|orgId=" + orgId + "|ownerAcc=" + ownerAcc + e.getMessage(),
					e);
		}
		long min = (System.currentTimeMillis() - timerBegin);
		logger.info("reqId=" + reqId + "。...回收资源:，耗时(毫秒)：" + min);
	}

	public void batchAssginRes(final String reqId, final List<ResCustInfoBean> sublist, final String userAcc, final String orgId, String sql,
			final String ownerAcc) {
		long timerBegin = System.currentTimeMillis();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, SysBaseModelUtil.getModelId());
					ps.setString(2, ownerAcc);
					ps.setString(3, userAcc);
					ps.setString(4, orgId);
					ps.setString(5, sublist.get(i).getResCustId());
				}

				// 返回更新的结果集条数
				public int getBatchSize() {
					return sublist.size();
				}
			});
			List<String> ids = new ArrayList<String>();
			for (ResCustInfoBean custInfo : sublist) {
				ids.add(custInfo.getResCustId());
			}
			ids.add("ownerAcc=" + ownerAcc);
			ids.add("size=" + ids.size());
			final List<String> idList = ids;
			//logBatchInfoService.saveLogInfo(orgId, userAcc,user.getName(), OperateEnum.LOG_RES_ASSIGN, idList,ownerAcc);

		} catch (Exception e) {
			logger.error("reqId=" + reqId + e.getMessage(), e);
		}
		long min = (System.currentTimeMillis() - timerBegin);
		logger.info("reqId=" + reqId + "...回收资源:，耗时(毫秒)：" + min);
	}

	/**
	 * 按spSize将idsList分割成多个
	 *
	 * @param idsList
	 * @param spSize
	 * @return
	 */
	public static List<List<ResCustInfoBean>> splitIds(List<ResCustInfoBean> idsList, int spSize) {
		List<List<ResCustInfoBean>> rtnList = new ArrayList<List<ResCustInfoBean>>();
		int i = 0, start = 0, end = 0;
		int size = idsList.size();
		while (size > end) {
			start = (i++) * spSize;
			end = (size > start + spSize) ? start + spSize : size;
			rtnList.add(idsList.subList(start, end));
		}
		return rtnList;
	}

	// public void assginOrRecyleRes(List<ResCustInfoBean> list, String account,
	// String orgId, String sql, int type) {
	// TransactionDefinition def = null;
	// TransactionStatus status = null;
	// List<List<ResCustInfoBean>> res_id_list = null;
	// int i = 0;
	// if (list != null && list.size() > 0) {
	// res_id_list = splitIds(list, IContants.BATCH_PROCESS_SIZE);
	// def = new
	// DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
	// status = (TransactionStatus) transactionManager.getTransaction(def);
	// for (final List<ResCustInfoBean> sublist : res_id_list) {
	// i++;
	// if (type == 1) { // 1- 分配；2-回收
	// batchAssginRes(sublist, account, orgId, sql);
	// } else {
	// batchRecyleRes(sublist, account, orgId, sql);
	// }
	// }
	// transactionManager.commit(status);
	// }
	// logger.debug("资源回收end:" + i);
	// }
}
