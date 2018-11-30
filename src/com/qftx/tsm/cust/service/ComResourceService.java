package com.qftx.tsm.cust.service;

import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.util.*;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.cust.bean.CustDefinedDataBean;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.bean.TaoTagBean;
import com.qftx.tsm.cust.dao.*;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.cust.dto.TaoDto;
import com.qftx.tsm.cust.dto.TaoResDto;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.service.LogStaffInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dao.DataDictionaryMapper;
import com.qftx.tsm.plan.user.day.service.PlanUserDayService;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.enums.SysEnum;
import com.qftx.tsm.tao.service.TaoService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ComResourceService {
	transient Logger logger = Logger.getLogger(ComResourceService.class);
	@Autowired
	transient private ComResourceMapper comResourceMapper;
	@Autowired
	private DataDictionaryMapper dataDictionaryMapper;

	@Autowired
	private AllocatRecycleLogBeanMapper allocatRecycleLogBeanMapper;
	@Autowired
	private ResOptorMapper resOptorMapper;
	@Autowired
	TaoService taoCustService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private TaoTagMapper taoTagMapper;
	@Autowired
	transient private OrgGroupUserService orgGroupUserService;
	@Autowired
	private ResCustInfoMapper ResCustInfoMapper;
	@Autowired
	PlatformTransactionManager transactionManager;
	@Autowired
	transient private JdbcTemplate jdbcTemplate;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private StaffResourceMgService staffResourceMgService;
	@Autowired
	private LogBatchInfoService logBatchInfoService;
	@Autowired
	private LogStaffInfoService logStaffInfoService;
	@Autowired
	private CustDefinedDataMapper custDefinedDataMapper;
	@Autowired
	private LogUserOperateService logUserOperateService;
	@Autowired
	private ComResourceGroupService comResourceGroupService;
	@Autowired
	private LogCustInfoService logCustInfoService;
	@Autowired
    private ResCustInfoDetailMapper resCustInfoDetailMapper;
	@Autowired
    private PlanUserDayService planUserDayService;
	
	protected static final String RECYLE_CUST_SQL = "UPDATE TSM_RES_CUST_INFO  SET followup_date = null,life_code = null,effective_followup_date = null,last_Option_Id = null,last_Loss_Date = null,last_log_id = null,ACTION_DATE = null,owner_acc = null ,LAST_CUST_FOLLOW_ID = null,UPDATE_ACC = ?, UPDATE_DATE = now(), status = 1,FILTER_TYPE=0 ,OWNER_START_DATE = null, OPREATE_TYPE=7 WHERE  ORG_ID = ?  and  RES_CUST_ID = ? ";
	protected static final String ASSIGN_CUST_SQL = "UPDATE TSM_RES_CUST_INFO  SET followup_date = null,life_code = ?,effective_followup_date = null,last_Option_Id = null,last_Loss_Date = null,last_log_id = null,ACTION_DATE = null,owner_acc = ? ,LAST_CUST_FOLLOW_ID = null,UPDATE_ACC = ?, UPDATE_DATE = now(), status = 2,FILTER_TYPE=0 ,OWNER_START_DATE = now(), OPREATE_TYPE=3 WHERE  ORG_ID = ?  and  RES_CUST_ID = ? ";
	private static final String CLEAR_ALL_CUST_SQL = "UPDATE TSM_RES_CUST_INFO SET IS_DEL = 1,UPDATE_ACC = ?, UPDATE_DATE = now() WHERE  IS_DEL = 0 and ORG_ID = ?  and  RES_CUST_ID = ? ";


	public List<ResCustInfoBean> getList() {
		return comResourceMapper.find();
	}

	public List<ResCustInfoBean> getListByCondtion(ResCustInfoBean entity) {
		return comResourceMapper.findByCondtion(entity);
	}

	public List<ResCustInfoBean> getListPage(ResCustInfoBean entity) {
		return comResourceMapper.findListPage(entity);
	}

	public ResCustInfoBean getByPrimaryKey(Map<String, String> map) {
		return comResourceMapper.getByPrimaryKey(map);
	}

	public Map<String, String> getResCustById(Map<String, String> map) {
		return comResourceMapper.findResCustById(map);
	}

	public ResCustDto findResCallByCustId(String orgId, String resCustId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgId", orgId);
		map.put("resCustId", resCustId);
		return comResourceMapper.findResCallByCustId(map);
	}

	public void create(ResCustInfoBean entity) {
		comResourceMapper.insert(entity);

	}

	public void createBatch(List<ResCustInfoBean> entitys) {
		comResourceMapper.insertBatch(entitys);

	}

	public void modify(ResCustInfoBean entity) {
		comResourceMapper.update(entity);
	}

	public void modifyTrends(ResCustInfoBean entity) {
		comResourceMapper.updateTrends(entity);
	}

	public void modifyBatch(List<ResCustInfoBean> entitys) {
		for (ResCustInfoBean entity : entitys) {
			comResourceMapper.update(entity);
		}

	}

	public void modifyTrendsBatch(List<ResCustInfoBean> entitys) {
		for (ResCustInfoBean entity : entitys) {
			comResourceMapper.updateTrends(entity);
		}

	}

	public void remove(String id) {
		comResourceMapper.delete(id);

	}

	public void removeBatch(List<String> ids) {
		comResourceMapper.deleteBatch(ids);

	}

	public void removeComResBatch(Map<String, Object> map, List<String> ids, String orgId, String account) {
		comResourceMapper.removeComResBatch(map);
		freshTaoCache(orgId, account);

	}

	/**
	 * 资源维护-待分配资源-资源分配
	 *
	 * @param map
	 * @return
	 * @create 2015年11月17日 下午1:09:51 wuwei
	 * @history
	 */
	public void saveAssginRes(String reqId, Map<String, String> map, Map<String, String> resultMap, String orgId, String account,String name,String taskId) {
		List<String> idsList = new ArrayList<String>();
		Map<String, String> valMap = new HashMap<String, String>();
		Map<String, Object> map1 = new HashMap<>();
		try {
			idsList = Arrays.asList(map.get("ids").split(","));
			map1.put("total", idsList);
			map1.put("finished", 0);
			map1.put("status", true);
			cachedService.setProgress(orgId, taskId, map1);
			// List<ResCustInfoBean> resList =
			// comResourceMapper.findResList(idsList);
			valMap = getDataDictionaryBean(map.get("orgId"));
			int myResNum = comResourceMapper.findresourceNum(map);
			String batchId =  SysBaseModelUtil.getModelId();
			String result = isAssign(valMap);
			if ("0".equals(result)) {
				int num = Integer.valueOf(valMap.get("val"));
				if (num > myResNum) {
					if (myResNum + idsList.size() > num) {
						idsList = idsList.subList(0, num - myResNum);
					}
					dealData(reqId, idsList, map.get("ownerAcc"), orgId, account,name,taskId,map1,batchId,"unAssign");
				} else {
					resultMap.put("result", "0001");
					resultMap.put("msg", "个人拥有资源数量超过设定上限!");
					return;
				}
			} else if ("1".equals(result)) {
				dealData(reqId, idsList, map.get("ownerAcc"), orgId, account,name,taskId,map1,batchId,"unAssign");
			}
			resultMap.put("result", "0");
		} catch (Exception e) {
			logger.error("saveAssginRes reqId=" + reqId + "请求内容：" + JsonUtil.getJsonString(map), e);
			throw new SysRunException(e);
		}
	}

	/*
	 * public void dealData4Mult(List<String> resList, String ownerAcc, String
	 * sorgId, String saccount, String idenify) { final List<String> idsList =
	 * resList; final String staffAcc = ownerAcc; final String orgId = sorgId;
	 * final String account = saccount; final String id = idenify;
	 * taskExecutor.submit(new Runnable() { public void run() { Map<String,
	 * ResOptDto> optDtoMap = cachedService.getAssignMap(orgId); ResOptDetailDto
	 * dto = new ResOptDetailDto(); ResOptDto resOptDto = optDtoMap.get(orgId);
	 * if (resOptDto == null) { cachedService.delAssignMap(orgId); return; } for
	 * (ResOptDetailDto temp : resOptDto.getList()) { if
	 * (id.equals(temp.getId())) { dto = temp; break; } } try {
	 * dto.setIsFinished(false); dto.setStartTime(new Date()); dealData(idsList,
	 * staffAcc, orgId, account); dto.setIsSucss(true); } catch (Exception e) {
	 * dto.setIsSucss(false); logger.error("dealData4Mult 异常。resList=" +
	 * JsonUtil.getJsonString(idsList) + "|ownerAcc=" + staffAcc + "|orgId=" +
	 * orgId + "|account=" + account + e.getMessage(), e); } finally {
	 * dto.setIsFinished(true); dto.setLength(dto.getDiff()); dto.setEndTime(new
	 * Date()); resOptDto.getList().add(dto); optDtoMap.put(orgId, resOptDto);
	 * logger.debug("返回状态：" + JsonUtil.getJsonString(resOptDto));
	 * cachedService.setAssignMap(optDtoMap, orgId);
	 * cachedService.setResOPtDto(orgId); optDtoMap =
	 * cachedService.getAssignMap(orgId); if (optDtoMap == null) { return; }
	 * resOptDto = optDtoMap.get(orgId); if (resOptDto != null &&
	 * resOptDto.getIsFinished()) { logger.debug("线程处理完结果：" +
	 * JsonUtil.getJsonString(resOptDto)); cachedService.delAssignMap(orgId); }
	 * 
	 * } }
	 * 
	 * 
	 * }); }
	 */

	public void dealData(final String reqId, List<String> resList, final String ownerAcc,
			final String orgId, final String account,final String name,String taskId,Map<String,Object> map1,final String batchId,final String module) {
		try {
			long startTime = System.currentTimeMillis();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ownerAcc", ownerAcc);
			map.put("account", account);
			map.put("ids", resList);
			map.put("orgId", orgId);
			map.put("lifeCode", SysBaseModelUtil.getModelId());
			comResourceMapper.updateResByassignCust(map);
			map1.put("finished", (Integer)map1.get("finished") + resList.size());
			cachedService.setProgress(orgId, taskId, map1);
			logger.debug("dealData reqId=" + reqId + "耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
			List<String> ids = new ArrayList<String>();
			if (resList == null || resList.size() <= 0) {
				return;
			}
			for (String resId : resList) {
				ids.add(resId);
			}
			final List<String> idList = ids;
			final Map<String, String> nameMap = cachedService.getOrgUserNames(orgId);
			taskExecutor.submit(new Runnable() {
				public void run() {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						logger.error("dealData 多线程异常.reqId=" + reqId + e.getMessage(), e);
					}
					Map<String,Object> custIdContextMap = new HashMap<>();
					for (String id : idList) {
						custIdContextMap.put(id, ownerAcc);
					}
					if ("staff".equals(module)) {
						logBatchInfoService.saveLog(orgId, account, name, OperateEnum.LOG_RES_ASSIGN, AppConstant.Module_id108,AppConstant.Module_Name108, AppConstant.Operate_id34, AppConstant.Operate_Name34, "分配资源"+idList.size()+"条给'"+(nameMap.get(ownerAcc) == null ? ownerAcc : nameMap.get(ownerAcc))+"'", "", idList, ownerAcc, batchId, custIdContextMap);
					}else {
						logBatchInfoService.saveLog(orgId, account, name, OperateEnum.LOG_RES_ASSIGN, AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id34, AppConstant.Operate_Name34, "分配资源"+idList.size()+"条给'"+(nameMap.get(ownerAcc) == null ? ownerAcc : nameMap.get(ownerAcc))+"'", "", idList, ownerAcc, batchId, custIdContextMap);
					}
				}
			});
		} catch (Exception e) {
			logger.error("reqId=" + reqId + "。dealData -- 异常" + e.getMessage(), e);
			String context = "reqId=" + reqId + "。dealData -- 异常" + e.getMessage();
			logStaffInfoService.saveLogInfo(orgId, account, "dealData处理异常", context, reqId);
			map1.put("status", false);
			map1.put("errorMsg", e);
		}
	}

	public void freshTaoCache(String orgId, String account) {
		TaoTagBean tagBean = cachedService.getLoction(orgId, account);
		if (tagBean == null) {
			String defaultOrder = ConfigInfoUtils.getStringValue("tao_order");// 默认按时间倒序排序
			tagBean = new TaoTagBean();
			tagBean.setGroupId("all");// 今日
			tagBean.setOrderType(defaultOrder);
			tagBean.setIsConcat(2);// 全部
			tagBean.setAccount(account);
			tagBean.setOrgId(orgId);
			tagBean.setPool("1");
		}
		String pool = tagBean.getPool();
		Integer taoNum = new Integer((ConfigInfoUtils.getStringValue("tao_num_cache")));
		Map<String, Object> recyMap = new HashMap<String, Object>();
		recyMap.put("ownerAcc", account);
		recyMap.put("orgId", orgId);
		recyMap.put("resourceGroupId", tagBean.getGroupId());
		recyMap.put("orderType", tagBean.getOrderType());
		recyMap.put("isConcat", tagBean.getIsConcat());
		recyMap.put("startLength", 0);
		recyMap.put("length", taoNum);
		TaoDto taoDto = taoCustService.getTaoDto(recyMap, orgId, account, tagBean, pool);
		if (taoDto != null && taoDto.getTaoResDtos().size() > 0) {
			tagBean.setResourceId(taoDto.getResId());
			cachedService.setLocation(orgId, account, tagBean);
		} else {
			tagBean.setResourceId("");
			cachedService.setLocation(orgId, account, tagBean);
		}
	}

	// 是否分配
	public String isAssign(Map<String, String> map) {
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
	 * @return map
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

	public ComResourceMapper getResCustMapper() {
		return comResourceMapper;
	}

	public void setResCustMapper(ComResourceMapper comResourceMapper) {
		this.comResourceMapper = comResourceMapper;
	}

	public void updateBatchResToGroup(final Map<String, Object> map, final String orgId, final String account,final String name,final String module,final String groupName) {
		String[] array = (String[]) map.get("ids");
		final List<String> list = Arrays.asList(array);
		if (list == null || list.size() <= 0) {
			return;
		}
		final List<ResCustInfoBean> resList = comResourceMapper.findResListByIds(list, orgId);
		final String batchId =  SysBaseModelUtil.getModelId();
		comResourceMapper.updateBatchResToGroup(map);
		taskExecutor.submit(new Runnable() {
			public void run() {
				try {
					Thread.sleep(300);
				} catch (InterruptedException e) {
					logger.error("updateBatchResToGroup 多线程异常" + e.getMessage(), e);
				}
				Map<String, Object> custIdContextMap = new HashMap<String, Object>();
				for (ResCustInfoBean bean : resList) {
					String context = "变更前：" + cachedService.getGroupName(bean.getResGroupId(), orgId) + "；变更后："
							+ cachedService.getGroupName((String) map.get("groupId"), orgId);
					//logCustInfoService.addLog(orgId, account, bean.getResCustId(), context, OperateEnum.LOG_CHANGE_GROUP);
					custIdContextMap.put(bean.getResCustId(), context);
				}
				if ("unAssgin".equals(module)) {
					logBatchInfoService.saveLog(orgId, account, name, OperateEnum.LOG_CHANGE_GROUP,AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id33, AppConstant.Operate_Name33, list.size() + "条资源变更到资源组'"+groupName+"'", "", list, "", batchId, custIdContextMap);
				} else if ("assgin".equals(module)) {
					logBatchInfoService.saveLog(orgId, account, name, OperateEnum.LOG_CHANGE_GROUP,AppConstant.Module_id107,AppConstant.Module_Name107, AppConstant.Operate_id33, AppConstant.Operate_Name33, list.size() + "条资源变更到资源组'"+groupName+"'", "", list, "", batchId, custIdContextMap);
				} else if ("all".equals(module)) {
					logBatchInfoService.saveLog(orgId, account, name, OperateEnum.LOG_CHANGE_GROUP,AppConstant.Module_id118,AppConstant.Module_Name118, AppConstant.Operate_id33, AppConstant.Operate_Name33, list.size() + "条资源变更到资源组'"+groupName+"'", "", list, "", batchId, custIdContextMap);
				}
			}
		});
	}

	public void megResToGroup(Map<String, Object> map) {
		comResourceMapper.megResToGroup(map);
	}

	public List<ResCustDto> queryAssginResListPage(ResCustDto entity,List<String> multiList) throws Exception {
		List<ResCustDto> resCustDtos = new ArrayList<>();
		List<String> cids = new ArrayList<String>();
		if (entity.getState() != null& entity.getState() == 1 && ("phone".equals(entity.getQueryType()) || "mainLinkman".equals(entity.getQueryType())) && StringUtils.isNotBlank(entity.getqKeyWord())) {
			if (entity.getQueryType().equals("phone")) {
				cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(entity.getOrgId(), entity.getqKeyWord());
			} else {
    			cids = resCustInfoDetailMapper.findLinkmanIds(entity.getOrgId(), entity.getqKeyWord());
			}
			if(cids.size() == 0) return resCustDtos;
			entity.setResIds(cids);
			entity.setqKeyWord(null);
		}
		if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(entity, multiList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> resIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(resIds.size() > 0){
					entity.setResIds(resIds);
				}else{
					return resCustDtos;
				}
			}
		}
		Integer count = entity.getPage().getTotalResult();
		if ((entity.getState() == null || entity.getState() == 0) && "phone".equals(entity.getQueryType()) && StringUtils.isNotEmpty(entity.getqKeyWord())) {
			if (entity.getPage().getCurrentPage() < 2) {
				count = comResourceMapper.findAssginResWithPhoneCount(entity);
				entity.getPage().setTotalResult(count);
				entity.getPage().setTotalPage(entity.getPage().getTotalPage());
			}
			if (count == 0) {
				return resCustDtos;
			}
			List<String> ids = comResourceMapper.findAssginResIdsWithPhone(entity);
			if (ids != null && ids.size() != 0) {
				Map<String, Object> map = new HashMap<>();
				map.put("orgId",entity.getOrgId());
				map.put("ids",ids);
				resCustDtos = comResourceMapper.findAssginResWithPhonePageList(map);
				return resCustDtos;
			}
			return resCustDtos;
		} else {
			if (entity.getPage().getCurrentPage() < 2) {
				count = comResourceMapper.findAssginResCount(entity);
				entity.getPage().setTotalResult(count);
				entity.getPage().setTotalPage(entity.getPage().getTotalPage());
			}
			if (count == 0) {
				return resCustDtos;
			}
			List<String> ids = comResourceMapper.findAssginResIds(entity);
			if (ids != null && ids.size() != 0) {
				Map<String, Object> map = new HashMap<>();
				map.put("orgId",entity.getOrgId());
				map.put("ids",ids);
				resCustDtos = comResourceMapper.findAssginResPageList(map);
				return resCustDtos;
			}
			return resCustDtos;
		}
	}

	public List<ResCustDto> queryUnAssginResListPage(ResCustDto entity,List<String> multiList) throws Exception {
		List<ResCustDto> resCustDtos = new ArrayList<>();
		List<String> cids = new ArrayList<String>();
		if (entity.getState() != null && entity.getState() == 1 && ("phone".equals(entity.getQueryType()) || "mainLinkman".equals(entity.getQueryType())) && StringUtils.isNotBlank(entity.getqKeyWord())) {
			if (entity.getQueryType().equals("phone")) {
				cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(entity.getOrgId(), entity.getqKeyWord());
			} else {
    			cids = resCustInfoDetailMapper.findLinkmanIds(entity.getOrgId(), entity.getqKeyWord());
			}
			if(cids.size() == 0) return resCustDtos;
			entity.setResIds(cids);
			entity.setqKeyWord(null);
		}
		if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(entity, multiList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> resIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(resIds.size() > 0){
					entity.setResIds(resIds);
				}else{
					return resCustDtos;
				}
			}
		}
		Integer count = entity.getPage().getTotalResult();
		if ("phone".equals(entity.getQueryType()) && StringUtils.isNotBlank(entity.getqKeyWord()) && (entity.getState() == null || entity.getState() == 0)) {
			if (entity.getPage().getCurrentPage() < 2) {
				count = comResourceMapper.findUnAssginResWithPhoneCount(entity);
				entity.getPage().setTotalResult(count);
				entity.getPage().setTotalPage(entity.getPage().getTotalPage());
			}
			if (count == 0) {
				return resCustDtos;
			}
			List<String> ids = comResourceMapper.findUnAssginResIdsWithPhone(entity);
			if (ids != null && ids.size() != 0) {
				Map<String, Object> map = new HashMap<>();
				map.put("orgId",entity.getOrgId());
				map.put("ids",ids);
				resCustDtos = comResourceMapper.findUnAssginResWithPhonePageList(map);
				return resCustDtos;
			}
			return resCustDtos;
		} else {
			if (entity.getPage().getCurrentPage() < 2) {
				count = comResourceMapper.findUnAssginResCount(entity);
				entity.getPage().setTotalResult(count);
				entity.getPage().setTotalPage(entity.getPage().getTotalPage());
			}
			if (count == 0) {
				return resCustDtos;
			}
			List<String> ids = comResourceMapper.findUnAssginResIds(entity);
			if (ids != null && ids.size() != 0) {
				Map<String, Object> map = new HashMap<>();
				map.put("orgId",entity.getOrgId());
				map.put("ids",ids);
				resCustDtos = comResourceMapper.findUnAssginResPageList(map);
				return resCustDtos;
			}
			return resCustDtos;
		}
	}
	
	public List<ResCustDto> queryResListPage(ResCustDto entity,List<String> multiList) throws Exception {
		List<ResCustDto> resCustDtos = new ArrayList<>();
		List<String> cids = new ArrayList<String>();
		if (entity.getState() != null && entity.getState() == 1 && ("phone".equals(entity.getQueryType()) || "mainLinkman".equals(entity.getQueryType())) && StringUtils.isNotBlank(entity.getqKeyWord())) {
			if (entity.getQueryType().equals("phone")) {
				cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(entity.getOrgId(), entity.getqKeyWord());
			} else {
    			cids = resCustInfoDetailMapper.findLinkmanIds(entity.getOrgId(), entity.getqKeyWord());
			}
			if(cids.size() == 0) return resCustDtos;
			entity.setResIds(cids);
			entity.setqKeyWord(null);
		}
		if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(entity, multiList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> resIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(resIds.size() > 0){
					entity.setResIds(resIds);
				}else{
					return resCustDtos;
				}
			}
		}
		Integer count = entity.getPage().getTotalResult();
		if ("phone".equals(entity.getQueryType()) && StringUtils.isNotEmpty(entity.getqKeyWord()) && (entity.getState() == null || entity.getState() == 0)) {
			if (entity.getPage().getCurrentPage() < 2) {
				count = comResourceMapper.findResWithPhoneCount(entity);
				entity.getPage().setTotalResult(count);
				entity.getPage().setTotalPage(entity.getPage().getTotalPage());
			}
			if (count == 0) {
				return resCustDtos;
			}
			List<String> ids = comResourceMapper.findResIdsWithPhone(entity);
			if (ids != null && ids.size() != 0) {
				Map<String, Object> map = new HashMap<>();
				map.put("orgId",entity.getOrgId());
				map.put("ids",ids);
				resCustDtos = comResourceMapper.findResWithPhonePageList(map);
				return resCustDtos;
			}
			return resCustDtos;
		} else {
			if (entity.getPage().getCurrentPage() < 2) {
				count = comResourceMapper.findResCount(entity);
				entity.getPage().setTotalResult(count);
				entity.getPage().setTotalPage(entity.getPage().getTotalPage());
			}
			if (count == 0) {
				return resCustDtos;
			}
			List<String> ids = comResourceMapper.findResIds(entity);
			if (ids != null && ids.size() != 0) {
				Map<String, Object> map = new HashMap<>();
				map.put("orgId",entity.getOrgId());
				map.put("ids",ids);
				resCustDtos = comResourceMapper.findResPageList(map);
				return resCustDtos;
			}
			return resCustDtos;
		}
	}

	public void getAssginRecyleResource(Map<String, String> map) {
		comResourceMapper.findAssginRecyleResource(map);
	}

	public List<String> getAssginUnGroupResAll(ResCustInfoBean entity) {
		return comResourceMapper.findAssginUnGroupResAll(entity);
	}

	public List<String> getAssginGroupedResAll(ResCustInfoBean entity) {
		return comResourceMapper.findAssginGroupedResAll(entity);
	}

	public List<ResCustInfoBean> queryUnAssginResIdsListPage(ResCustDto entity) throws Exception {
		//return comResourceMapper.findUnAssginResIdsListPage(resCustDto);
				//查出多选项查询字段
				List<String> multiList = cachedService.getMultiSelectDefinedSearchFiled(entity.getOrgId(),SysEnum.SEARCH_SET_MODULE_9.getState());
				List<ResCustInfoBean> resCustDtos = new ArrayList<>();
				List<String> cids = new ArrayList<String>();
				if(multiList != null && multiList.size() > 0){
					Map<String, Object> paramMap = getMultiDefinedSearchParam(entity, multiList);
					if(paramMap.size() > 0){
						if(cids.size() > 0) paramMap.put("custIds", cids);
						List<String> resIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
						if(resIds.size() > 0){
							entity.setResIds(resIds);
						}else{
							return resCustDtos;
						}
					}
				}
				return comResourceMapper.findUnAssginResIdsListPage(entity);
	}

	public String clearMyRes(ResCustDto entity, String orgId, String account,String name,String taskId) {
		logger.debug("资源清空start");
		ResCustDto tmpentity = null;
		Page tmppage = null;

		TransactionDefinition def = null;
		TransactionStatus status = null;
		Map<String, Object> map = new HashMap<>();
		int i = 0;
		try {
			List<ResCustInfoBean> arr = queryUnAssginResIdsListPage(entity);
			String batchId =  SysBaseModelUtil.getModelId();
			map.put("total", entity.getPage().getTotalResult());
			map.put("finished", 0);
			map.put("status", true);
			cachedService.setProgress(orgId, taskId, map);
			logger.info("资源清空size:" + entity.getPage().getTotalResult());
			List<ResCustInfoBean> arr2 = null;
			if (arr != null && arr.size() > 0) {
				List<List<ResCustInfoBean>> res_id_list = null;
				int page = entity.getPage().getTotalResult() / IContants.BATCH_GET_SIZE;
				if (entity.getPage().getTotalResult() % IContants.BATCH_GET_SIZE > 0) {
					page++;
				}
				tmpentity = (ResCustDto) entity.clone();
				tmppage = new Page();
				tmppage.setShowCount(IContants.BATCH_GET_SIZE);
				tmppage.setPageStr(null);
				tmppage.setPageSubStr(null);
				tmppage.setTotalResult(entity.getPage().getTotalResult());
				tmppage.setTotalPage(page);
				tmppage.setCurrentPage(page);
				tmpentity.setPage(tmppage);

				while (tmppage.getCurrentPage() >= 1) {
					arr2 = queryUnAssginResIdsListPage(tmpentity);// 从最后一页开始处理
					if (arr2 != null && arr2.size() > 0) {
						res_id_list = splitIds(arr2, IContants.BATCH_PROCESS_SIZE);
						def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);
						for (final List<ResCustInfoBean> sublist : res_id_list) {
							i++;
							batchClearRes(sublist, account, orgId,name,map,batchId);
							map.put("finished", (Integer)map.get("finished") + sublist.size());
							cachedService.setProgress(orgId, taskId, map);
						}
					}
					if (tmppage.getCurrentPage() == 1) {
						break;
					}
					tmppage.setCurrentPage(tmppage.getCurrentPage() - 1);
				}
				logger.debug("资源清空end:" + i);
			}
			
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			if (transactionManager != null) {
				transactionManager.rollback(status);
			}
			map.put("status", false);
			map.put("errorMsg", e);
			cachedService.setProgress(orgId, taskId, map);
			logger.debug("资源清空exception:" + e.getMessage(), e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	public void batchClearRes(final List<ResCustInfoBean> sublist, final String userAcc, final String orgid, final String name,final Map<String, Object> map,final String batchId) {
		long timerBegin = System.currentTimeMillis();
		try {
			jdbcTemplate.batchUpdate(CLEAR_ALL_CUST_SQL, new BatchPreparedStatementSetter() {
				// 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setString(1, userAcc);
					ps.setString(2, orgid);
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
			logBatchInfoService.saveLog(orgid, userAcc, name, OperateEnum.LOG_RES_CLEAR,AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id42, AppConstant.Operate_Name42, "清空"+ids.size()+"条资源", "", ids, "", batchId, null);
		} catch (Exception e) {
			logger.error("batchClearRes 异常。userAcc=" + userAcc + "|orgid=" + orgid, e);
		}
		long min = (System.currentTimeMillis() - timerBegin);
		logger.info("...清空待分配资源:，耗时(毫秒)：" + min);
	}

	// public void batchRecyleRes(final List<ResCustInfoBean> sublist, final
	// String userAcc, final String orgid) {
	// long timerBegin = System.currentTimeMillis();
	// try {
	//
	// jdbcTemplate.batchUpdate(RECYLE_CUST_SQL, new
	// BatchPreparedStatementSetter() {
	// // 为prepared statement设置参数。这个方法将在整个过程中被调用的次数
	// public void setValues(PreparedStatement ps, int i) throws SQLException {
	// ps.setString(1, userAcc);
	// ps.setInt(2, sublist.get(i).getStatus());
	// ps.setString(3, orgid);
	// ps.setString(4, sublist.get(i).getResCustId());
	// }
	//
	// // 返回更新的结果集条数
	// public int getBatchSize() {
	// return sublist.size();
	// }
	// });
	// List<String> ids = new ArrayList<String>();
	// for (ResCustInfoBean custInfo : sublist) {
	// ids.add(custInfo.getResCustId());
	// }
	// OperateEnum.LOG_RES_RECYLE, ids);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// long min = (System.currentTimeMillis() - timerBegin);
	// logger.info("...回收资源:，耗时(毫秒)：" + min);
	// }

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

	/**
	 * 合并分组
	 *
	 * @param ids
	 * @param newGroup
	 * @param entity
	 * @create 2015年11月18日 下午4:40:14 wuwei
	 * @history
	 */
	public String saveMergeGroup(String ids, String newGroup, ResCustDto entity, String orgId, String account,String name) {
		// 获取查询条件
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String[] groupIds = ids.split("\\,");
			List<String> resList1 = null;
			List<String> resList2 = null;
			ArrayList<String> arr = new ArrayList<String>();
			entity.setOrgId(orgId);
			for (int i = 0; i < groupIds.length; i++) {
				if ("111null111".equals(groupIds[i])) {
					resList2 = getAssginUnGroupResAll(entity);// 未分组
					arr.addAll(resList2);
				} else {
					entity.setResGroupId(groupIds[i]);
					resList1 = getAssginGroupedResAll(entity);// 已分组
					arr.addAll(resList1);
				}
			}
			int count = arr.size() / 1000;
			int yu = arr.size() % 1000;
			for (int i = 0; i < count + 1; i++) {
				List<String> subList = new ArrayList<String>();
				if (i < count) {
					subList = arr.subList(i * 1000, 1000 * (i + 1));
					localMegArray(subList, newGroup, orgId, account);
				} else {
					subList = arr.subList(arr.size() - yu, arr.size());
					localMegArray(subList, newGroup, orgId, account);
				}
			}
			//查询groupname
			Map<String, Object> map1 = new HashMap<>();
			map1.put("groupIds", ids.split(","));
			map1.put("orgId", user.getOrgId());
			List<ResourceGroupBean> groups1 = comResourceGroupService.findByGroupIds(map1);
			StringBuffer groupNames= new StringBuffer();
			if (groups1 != null && groups1.size() != 0) {
				for (int i = 0; i < groups1.size(); i++) {
					if (i == groups1.size() - 1) {
						groupNames.append("'"+groups1.get(i).getGroupName()+"'");
					} else {
						groupNames.append("'"+groups1.get(i).getGroupName()+"'").append(",");
					}
				}
			}
			//查询groupname
			Map<String, Object> map2 = new HashMap<>();
			map2.put("groupIds", newGroup.split(","));
			map2.put("orgId", user.getOrgId());
			List<ResourceGroupBean> groups2 = comResourceGroupService.findByGroupIds(map2);
			StringBuffer groupName= new StringBuffer();
			if (groups2 != null && groups2.size() == 1) {
				groupName.append("'"+groups2.get(0).getGroupName()+"'");
			}
			String batchId =  SysBaseModelUtil.getModelId();
			logBatchInfoService.saveLog(orgId, account, name, OperateEnum.LOG_RES_MERGE,AppConstant.Module_id107,AppConstant.Module_Name107, AppConstant.Operate_id44, AppConstant.Operate_Name44, groupNames.toString()+"合并后分组名:"+groupName.toString(),"", arr, "", batchId, null);
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			logger.error("合并分组异常。ids=" + ids + "|newGroup=" + newGroup, e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	private void localMegArray(@SuppressWarnings("rawtypes") List arr, String newGroup, String orgId, String account) {
		StringBuffer sb = new StringBuffer();
		try {
			sb.append("'");
			for (int i = 0; i < arr.size(); i++) {

				if (i < arr.size() - 1) {
					sb.append(arr.get(i)).append("','");
				} else {
					sb.append(arr.get(i)).append("'");
				}
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", sb.toString());
			map.put("account", account);
			map.put("groupId", newGroup);
			map.put("orgId", orgId);
			megResToGroup(map);
		} catch (Exception e) {
			logger.error("localMegArray 合并异常" + e.getMessage(), e);
		}
	}

	/**
	 * 资源维护-已分配资源-回收资源
	 *
	 * @param paramMap
	 * @return
	 * @create 2015年11月18日 下午5:36:14 wuwei
	 * @history
	 */
	public String updateBatchRes(String reqId, String orgId, String account,Map<String,List<String>> logMap,String name,String taskId,Map<String, Object> map,String batchId,String module) {
		for(String ownerAcc : logMap.keySet()) {
			List<String> ids = logMap.get(ownerAcc);
			List<ResCustInfoBean> list = comResourceMapper.findResList(ids, orgId);
			if (list != null && list.size() > 0) {
				recyleRes(reqId, ids, ownerAcc, orgId, account,name,null,taskId,map,batchId,module);
				freshTaoCache(orgId, account);
			}
		}
		return AppConstant.RESULT_SUCCESS;
	}

	public void recyleRes(final String reqId, List<String> resList, final String ownerAcc, final String orgId, 
			final String account,final String name,final String newResGroupId,final String taskId,
			final Map<String, Object> map1,final String batchId,final String module) {
		try {
			long startTime = System.currentTimeMillis();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ownerAcc", ownerAcc);
			map.put("account", account);
			map.put("ids", resList);
			map.put("orgId", orgId);
			map.put("newResGroupId", newResGroupId);
			if ("before".equals(newResGroupId)) {
				map.put("newResGroupId", null);
			}
			comResourceMapper.updateResByRecyleCust(map);
			map1.put("finished", (Integer)map1.get("finished") + resList.size());
			cachedService.setProgress(orgId, taskId, map1);
			logger.debug("dealData reqId=" + reqId + "耗时：" + (System.currentTimeMillis() - startTime) + "毫秒");
			List<String> ids = new ArrayList<String>();
			if (resList == null || resList.size() <= 0) {
				return;
			}
			for (String resId : resList) {
				ids.add(resId);
			}
			final List<String> idList = ids;
			final Map<String,String> nameMap = cachedService.getOrgUserNames(orgId);
			taskExecutor.submit(new Runnable() {
				public void run() {
					try {
						Thread.sleep(300);
					} catch (InterruptedException e) {
						logger.error("dealData 多线程异常.reqId=" + reqId + e.getMessage(), e);
					}
					Map<String, Object> custIdContextMap = new HashMap<String, Object>();
					for (String id : idList) {
						custIdContextMap.put(id, "回收待分配");
					}
					if ("staff".equals(module)) {
						logBatchInfoService.saveLog(orgId, account, name, OperateEnum.LOG_RES_RECYLE, AppConstant.Module_id108,AppConstant.Module_Name108,AppConstant.Operate_id43, AppConstant.Operate_Name43,  + idList.size()+"条,回收对象:"+(nameMap.get(ownerAcc) == null ? ownerAcc : nameMap.get(ownerAcc)), "", idList, ownerAcc, batchId, custIdContextMap);
					}else {
						logBatchInfoService.saveLog(orgId, account, name, OperateEnum.LOG_RES_RECYLE, AppConstant.Module_id107,AppConstant.Module_Name107,AppConstant.Operate_id43, AppConstant.Operate_Name43, "回收" + idList.size()+"条资源", "", idList, ownerAcc, batchId, custIdContextMap);
					}
				}
			});
		} catch (Exception e) {
			logger.error("reqId=" + reqId + "。dealData -- 异常" + e.getMessage(), e);
			String context = "reqId=" + reqId + "。dealData -- 异常" + e.getMessage();
			logStaffInfoService.saveLogInfo(orgId, account, "recyleRes处理异常", context, reqId);
			map1.put("status", false);
			map1.put("errorMsg", e);
			cachedService.setProgress(orgId, taskId, map1);
		}
	}


	public int getMyCustSum(Map<String, String> map) {
		return comResourceMapper.findMyCustSum(map);

	}

	public String getGroupNameByResId(Map<String, String> map) {
		return comResourceMapper.findGroupNameByResId(map);
	}

	public void modifyResCustInfo(ResCustInfoBean resCust) {
		this.comResourceMapper.updateResCustInfo(resCust);
	}

	public int updateDelayCallReason(Map<String, String> map) {
		return comResourceMapper.modifyDelayCallReason(map);
	}

	public void modifyMajorId(Map<String, String> map) {
		comResourceMapper.updateMajorId(map);
	}

	public int getMyResByGroupId(Map<String, Object> map) {
		return comResourceMapper.findResListCount(map);
	}

	/**

	 * 满足筛选条件的总数
	 *
	 * @param map
	 * @return
	 * @create 2016年1月3日 上午10:59:46 wuwei
	 * @history
	 */
	public int getResListCount(Map<String, Object> map) {
		int sum=0;
		if("today".equals(map.get("resourceGroupId"))||map.get("resourceGroupId")=="today"||"temp".equals(map.get("resourceGroupId"))||map.get("resourceGroupId")=="temp"){
			List<String> ids=planUserDayService.taoGetPlanCustId(map.get("orgId").toString(), map.get("ownerAcc").toString());
			if(ids!=null&&ids.size()>0){
				map.put("ids", ids);
				sum=comResourceMapper.findResListCount(map);
			}
		}else{
			sum=comResourceMapper.findResListCount(map);
		}
		return sum;
	}

	/**

	 * 满足所有条件淘客户资源
	 *
	 * @param map
	 * @return
	 * @create 2016年1月3日 上午11:21:44 WUWEI
	 * @history
	 */
	public List<TaoResDto> getTaoResList(Map<String, Object> map) {
		List<TaoResDto> taoResDto_=new ArrayList<TaoResDto>();
		if("today".equals(map.get("resourceGroupId"))||map.get("resourceGroupId")=="today"||"temp".equals(map.get("resourceGroupId"))||map.get("resourceGroupId")=="temp"){
			List<String> ids=planUserDayService.taoGetPlanCustId(map.get("orgId").toString(), map.get("ownerAcc").toString());
			if(ids!=null&&ids.size()>0){
				map.put("ids", ids);
				taoResDto_=comResourceMapper.findTaoResList(map);
			}
		}else{
			taoResDto_=comResourceMapper.findTaoResList(map);
		}
		
		return taoResDto_;
	}

	public ResCustDto getResById(Map<String, String> map) {
		return comResourceMapper.findResById(map);
	}

	public String getCustInfo(Map<String, String> map, int type) {
		if (type == 1) {
			return comResourceMapper.findCustIdByPhone(map);
		} else {
			return comResourceMapper.findPCustIdByPhone(map);
		}
	}
	//共有客户
	public String getComCustInfo(Map<String, String> map, int type) {
		if (type == 1) {
			return comResourceMapper.findComMonCustIdByPhone(map);
		} else {
			return comResourceMapper.findComPCustIdByPhone(map);
		}
	}

	public List<ResCustInfoBean> getDPPhone(Map<String, Object> map) {
		return comResourceMapper.findDPPhone(map);
	}

	public String getOptionName(Map<String, String> map) {
		return comResourceMapper.findOptionName(map);
	}

	public List<ResCustInfoBean> getDPUnit(Map<String, Object> map) {
		List<ResCustInfoBean> reslist=new ArrayList<ResCustInfoBean>();
		if(map.get("resCustId")==""||"".equals(map.get("resCustId"))||map.get("resCustId")==null){
			return comResourceMapper.findDPUnit(map);
		}else{
			List<ResCustInfoBean> list=comResourceMapper.findDPUnit(map);
			if(list!=null&&list.size()>0){
				if(list.size()==1){
					if(!map.get("resCustId").equals(list.get(0).getResCustId())){
						return list;	
					}else{
						return reslist;
					}
				}else{
					List<ResCustInfoBean> list_new=new ArrayList<ResCustInfoBean>();	
					for(ResCustInfoBean bean :list){
						if(list.get(0).getResCustId()!=map.get("resCustId")
								||!map.get("resCustId").equals(list.get(0).getResCustId())){
							list_new.add(bean);
							return list_new;
						}
					}
					
				}
			}
			return list;
		}
		
	}

	public List<ResCustInfoBean> getDPUnitHome(Map<String, Object> map) {
		List<ResCustInfoBean> reslist=new ArrayList<ResCustInfoBean>();
		if(map.get("resCustId")==""||"".equals(map.get("resCustId"))||map.get("resCustId")==null){
			return comResourceMapper.findDPUnitHome(map);
		}else{
			List<ResCustInfoBean> list=comResourceMapper.findDPUnitHome(map);
			if(list!=null&&list.size()>0){
				if(list.size()==1){
					if(!map.get("resCustId").equals(list.get(0).getResCustId())){
						return list;	
					}else{
						return reslist;
					}
				}else{
					List<ResCustInfoBean> list_new=new ArrayList<ResCustInfoBean>();	
					for(ResCustInfoBean bean :list){
						if(list.get(0).getResCustId()!=map.get("resCustId")
								||!map.get("resCustId").equals(list.get(0).getResCustId())){
							list_new.add(bean);
							return list_new;
						}
					}
					
				}
			}
			return list;
		}
		
		
		

	}

	public void changeResImportDept(Map<String, Object> map) {
		comResourceMapper.changeResImportDept(map);
	}

	public void getCustData(List<CustFieldSet> fieldSets, int state, String pname, String cname, String oname, ResCustDto resDto) throws Exception {
		if (resDto != null) {
			for (CustFieldSet fieldSet : fieldSets) {
				if ("comArea".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(pname)) {
					resDto.setLocationArea(pname + "&nbsp;&nbsp;" + cname + "&nbsp;&nbsp;" + oname);
				}
				if ("defined1".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined1())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined2".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined2())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined3".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined3())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined4".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined4())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined5".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined5())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined6".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined6())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined7".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined7())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined8".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined8())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined9".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined9())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined10".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined10())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined11".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined11())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined12".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined12())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined13".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined13())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined14".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined14())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined15".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(resDto.getDefined15())) {
					setFieldValues(fieldSet, resDto);
				} else if ("defined16".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && resDto.getDefined16() != null) {
					resDto.setShowdefined16(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resDto.getDefined16()));
				} else if ("defined17".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && resDto.getDefined17() != null) {
					resDto.setShowdefined17(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resDto.getDefined17()));
				} else if ("defined18".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && resDto.getDefined18() != null) {
					resDto.setShowdefined18(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(resDto.getDefined18()));
				}
			}
		}
		
	}
	public void setFieldValues(CustFieldSet fieldSet,ResCustDto resDto) throws Exception {
		Class<?> classType = resDto.getClass();
		String fieldCode = fieldSet.getFieldCode();
		// 得到属性名称的第一个字母并转成大写
		String firstLetter = fieldCode.substring(0, 1).toUpperCase();
		// 获得和属性对应的getXXX()方法的名字：get+属性名称的第一个字母并转成大写+属性名去掉第一个字母，
		// 如属性名称为name，则：get+N+ame
		String getMethodName = "get" + firstLetter + fieldCode.substring(1);
		// 获得和属性对应的setXXX()方法的名字：get+属性名称的第一个字母并转成大写+属性名去掉第一个字母，
		// 如属性名称为name，则：set+N+ame
		String setMethodName = "set" + firstLetter + fieldCode.substring(1);
		
		// 获得和属性对应的getXXX()方法
		Method getMethod = classType.getMethod(getMethodName,new Class[] {});

		// 获得和属性对应的setXXX()方法
		Method setMethod = classType.getMethod(setMethodName,String.class);
		// 调用原对象的getXXX()方法
		//Object value = getMethod.invoke(custInfo, new Object[] {});
		if (fieldSet.getDataType() == 3) {
			 List<OptionBean> optionList = fieldSet.getOptionList();
			 String id =(String)getMethod.invoke(resDto, new Object[] {});
			 setMethod.invoke(resDto,(Object)null);
			 if (org.apache.commons.lang3.StringUtils.isNotBlank(id) && optionList != null) {
				 for (OptionBean optionBean : optionList) {
						if (id.equals(optionBean.getOptionlistId())) {
							setMethod.invoke(resDto,optionBean.getOptionName());
							break;
						}		 
				 }
			}
		}else if (fieldSet.getDataType() == 4) {
			 List<OptionBean> optionList = fieldSet.getOptionList();
			 String ids = (String)getMethod.invoke(resDto, new Object[] {});
			 String[] idss = ids.split(",");
			 StringBuffer showview = new StringBuffer();
			 setMethod.invoke(resDto,(Object)null);
			 if (org.apache.commons.lang3.StringUtils.isNotBlank(ids) && optionList != null) {
				a:for (String id : idss) {
					b:for (OptionBean optionBean : optionList) {
						if (id.equals(optionBean.getOptionlistId())) {
							if (idss[idss.length-1] == id) {
								showview.append(optionBean.getOptionName());
								break b;
							}else {
								showview.append(optionBean.getOptionName()).append(",");
								break b;
							}
						}		 
					}
				}
			 setMethod.invoke(resDto,showview.toString());
			}
		}
	}
	/**设置扩展表查询条件*/
    public Map<String, Object> getMultiDefinedSearchParam(ResCustDto resCustDto,List<String> multiList) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	Class<?> clazz = resCustDto.getClass().getSuperclass();
    	List<String> fieldCodes = new ArrayList<String>();
    	List<String> fieldDatas = new ArrayList<String>();
    	String getName;
    	String setName;
    	Method getMethod;
    	Method setMethod;
    	for(String fieldCode : multiList){
    		getName =  "get" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
    		getMethod = clazz.getDeclaredMethod(getName);
    		Object val = getMethod.invoke(resCustDto);
    		if(val != null && StringUtils.isNotBlank(val.toString())){
    			fieldCodes.add(fieldCode);
    			fieldDatas.addAll(Arrays.asList(val.toString().split(",")));
    			setName =  "set" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
    			setMethod = clazz.getDeclaredMethod(setName, String.class);
    			setMethod.invoke(resCustDto, "");
    		}
    	}
    	if(fieldCodes.size() > 0){
    		map.put("fieldCodes", fieldCodes);
    		map.put("fieldDatas", fieldDatas);
    		map.put("orgId", resCustDto.getOrgId());
    	}
    	return map;
    }

	public void multiDefinedShowChange(List<ResCustDto> list,List<String> multiList, List<String> singleList,String orgId)  throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
    	Map<String, Map<String, String>> custDataMap = new HashMap<String, Map<String,String>>();
    	Map<String, Map<String, String>> codeNameMap = new HashMap<String, Map<String,String>>();
    	List<CustDefinedDataBean> definedDatas = new ArrayList<CustDefinedDataBean>();
    	Map<String, String> dataMap;
		List<OptionBean> option;
    	if(multiList.size() > 0){
    		List<String> custIds = getShowCustIds(list);
        	map.put("orgId", orgId);
        	map.put("fieldCodes", multiList);
        	map.put("custIds", custIds);
        	definedDatas = custDefinedDataMapper.findCustDefinedShowDatas(map);
    		String val;
    		String oldVal;
    		for(CustDefinedDataBean definedData : definedDatas){
    			if(!codeNameMap.containsKey(definedData.getFieldCode())){
    				option = cachedService.getOptionList(definedData.getFieldCode(), orgId);
    				codeNameMap.put(definedData.getFieldCode(), cachedService.changeOptionListToMap(option));
    			}
    			val = codeNameMap.get(definedData.getFieldCode()).get(definedData.getFieldData());
    			if(val != null){
    				if(custDataMap.containsKey(definedData.getCustId())){
    					if(custDataMap.get(definedData.getCustId()).containsKey(definedData.getFieldCode())){
    						oldVal = custDataMap.get(definedData.getCustId()).get(definedData.getFieldCode());
    						custDataMap.get(definedData.getCustId()).put(definedData.getFieldCode(), oldVal+"，"+val);
    					}else{
    						custDataMap.get(definedData.getCustId()).put(definedData.getFieldCode(), val);
    					}
    				}else{
    					dataMap = new HashMap<String, String>();
    					dataMap.put(definedData.getFieldCode(), val);
    					custDataMap.put(definedData.getCustId(), dataMap);
    				}
    			}
    		}
    	}
    	
    	//组装
    	Map<String,String> valueMap;
		Class tCls;
		String setName;
		Method setMethod;
		String getName;
		Method getMethod;
		for(ResCustDto cust : list){
			tCls = cust.getClass().getSuperclass();
			if(definedDatas.size() > 0){
				if(custDataMap.containsKey(cust.getResCustId())){
					valueMap = custDataMap.get(cust.getResCustId());
					for(String key : valueMap.keySet()){
						setName =  "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
						setMethod = tCls.getDeclaredMethod(setName, String.class);
						setMethod.invoke(cust, valueMap.get(key));
					}
				}
			}
			
			for(String singleDefined : singleList){
				if(!codeNameMap.containsKey(singleDefined)){
    				option = cachedService.getOptionList(singleDefined, orgId);
    				codeNameMap.put(singleDefined, cachedService.changeOptionListToMap(option));
    			}
				getName = "get" + singleDefined.substring(0, 1).toUpperCase() + singleDefined.substring(1);
				getMethod = tCls.getDeclaredMethod(getName);
				Object definedVal = getMethod.invoke(cust);
				if(definedVal != null){
					String definedValueName = codeNameMap.get(singleDefined).get(definedVal.toString());
					definedValueName = definedValueName == null ? "" : definedValueName;
					setName =  "set" + singleDefined.substring(0, 1).toUpperCase() + singleDefined.substring(1);
					setMethod = tCls.getDeclaredMethod(setName, String.class);
					setMethod.invoke(cust, definedValueName);
				}
			}
		}
	}
		
	public List<String> getShowCustIds(List<ResCustDto> list){
    	List<String> custIds = new ArrayList<String>();
    	for(ResCustDto dto : list) custIds.add(dto.getResCustId());
    	return custIds;
    }

	public List<ResCustInfoBean> getListByGroupIds(ResCustInfoBean entity) {
		return comResourceMapper.getListByGroupIds(entity);
	}

	public List<ResCustInfoBean> queryResByNameOrCompany(String queryText,
			String orgId,Integer state,List<String> ownerAccs) {
		Map<String, Object> map = new HashMap<>();
		map.put("queryText", queryText);
		map.put("orgId", orgId);
		map.put("ownerAccs",ownerAccs);
		map.put("state", state);
		return comResourceMapper.queryResByNameOrCompany(map);
	}
	
	/**
	 * @Description:待分配资源——二级分配
	 * @param @param reqId
	 * @param @param ids
	 * @param @param deptId
	 * @param @param orgId
	 * @param @return
	 * @return BaseJsonResult
	 * @create 2018-11-27 上午9:42:16 lubo 
	 */
	public BaseJsonResult secondDistribution(String reqId,List<String> ids,String deptId,String orgId,String account){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", orgId);
		map.put("account", account);
		map.put("deptId", deptId);
		map.put("ids", ids);
		comResourceMapper.updateResByDeptId(map);
		return BaseJsonResult.success();
	}
}
