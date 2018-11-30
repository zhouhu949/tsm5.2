package com.qftx.tsm.follow.service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.enums.FollowCustEnum;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.cust.bean.*;
import com.qftx.tsm.cust.dao.*;
import com.qftx.tsm.cust.dto.TsmCustSynFollowDto;
import com.qftx.tsm.cust.service.ResCustEventService;
import com.qftx.tsm.cust.service.TsmCustSynConfigService;
import com.qftx.tsm.cust.thread.CustGiveUpThread;
import com.qftx.tsm.follow.bean.CustFollowBean;
import com.qftx.tsm.follow.bean.CustSaleChanceBean;
import com.qftx.tsm.follow.dao.CustFollowMapper;
import com.qftx.tsm.follow.dao.CustSaleChanceMapper;
import com.qftx.tsm.follow.dto.CustFollowDto;
import com.qftx.tsm.follow.dto.CustSaleChanceDto;
import com.qftx.tsm.follow.dto.RestDto;
import com.qftx.tsm.follow.thread.CustFollowThread;
import com.qftx.tsm.log.service.LogContactDayDataService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.util.ContactUtil;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.plan.facade.PlanFactService;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;
import com.qftx.tsm.plan.user.day.dao.PlanUserDayMapper;
import com.qftx.tsm.plan.user.day.dao.PlanUserdaySigncustMapper;
import com.qftx.tsm.plan.user.day.dao.PlanUserdayWillcustMapper;
import com.qftx.tsm.plan.user.day.service.PlanUserdayWillcustService;
import com.qftx.tsm.report.service.RankingReportService;
import com.qftx.tsm.sys.bean.Product;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;


 /** 
 * 客户跟进记录 服务类
 * @author: lixing
 * @since: 2015年11月13日  上午11:12:57
 * @history:
 */
@Service
public class CustFollowService {
	private static Logger logger = Logger.getLogger(CustFollowService.class);
	@Autowired
	private CustFollowMapper custFollowMapper;
	
	@Autowired
	private PlanUserDayMapper planUserDayMapper;
	@Autowired
	private PlanUserdayWillcustMapper planUserdayWillcustMapper;
	@Autowired
	private PlanUserdaySigncustMapper planUserDaySigncustMapper;
	@Autowired
	transient private ComResourceMapper comResourceMapper;	
    @Autowired
	private ResCustInfoMapper resCustInfoMapper;
	@Autowired
	transient private ResCustEventService resCustEventService;
	@Autowired
	private CachedService cachedService;
	@Resource
    private ThreadPoolTaskExecutor taskExecutor;	
	@Autowired
	private TsmCustGuideMapper tsmCustGuideMapper;
	@Autowired
	private TsmCustGuideProcMapper tsmCustGuideProcMapper;
	@Autowired
	private LogCustInfoService logCustInfoService;
	@Autowired
	private CustDefinedDataMapper custDefinedDataMapper;
	@Autowired
	private ResCustInfoDetailMapper resCustInfoDetailMapper;
	@Autowired
	private LogContactDayDataService logContactDayDataService;
	@Autowired
	private TsmCustSynConfigService tsmCustSynConfigService;
	@Autowired
	private RankingReportService rankingReportService;
	@Autowired
	private CustLabelCodeDataMapper custLabelCodeDataMapper;
	@Autowired
	private PlanUserdayWillcustService planUserdayWillcustService;
	@Autowired
	private PlanFactService planFactService;
	@Autowired
	private OrgGroupUserService orgGroupUserService;
	@Autowired
	private CustSaleChanceMapper custSaleChanceMapper;
	/** 
	 * 根据客户ID 查询客户跟进记录
	 * @param custId
	 * @return 
	 * @create  2015年11月25日 上午10:46:54 lixing
	 * @history  
	 */
	public List<CustFollowDto> queryCustFollowByCustId(Map<String,Object>map){		
		return custFollowMapper.queryCustFollowByCustId(map);
	}
	
	public String getSignBeforeSaleProcessId(String orgId,String custId,String inputDate){
		return custFollowMapper.getSignBeforeSaleProcessId(orgId, custId, inputDate);
	}
	
	/** 
	 * 查询客户跟进记录 分页
	 * @param dto
	 * @return 
	 * @throws Exception 
	 * @create  2015年11月30日 上午10:27:45 lixing
	 * @history  
	 */
	public List<CustFollowDto> queryCustFollowsListPage(CustFollowDto dto) throws Exception{
		List<CustFollowDto> custs = new ArrayList<CustFollowDto>();
    	List<String> cids = new ArrayList<String>();
		/*Map<String, Object> paramMap = getMultiDefinedSearchParam(dto, Arrays.asList(AppConstant.SEARCH_LABEL));
		if(paramMap.size() > 0){
			if(cids.size() > 0) paramMap.put("custIds", cids);
			List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
			if(custIds.size() > 0){
				dto.setCustIds(custIds);
			}else{
				return custs;
			}
		}*/
		Map<String, Object> map = new HashMap<String, Object>();
    	if(dto.getLabels() != null && dto.getLabels().length > 0){
			List<String> fieldCodes = new ArrayList<String>();
	    	List<String> fieldDatas = new ArrayList<String>();
			fieldCodes.add(AppConstant.SEARCH_LABEL);
			fieldDatas.addAll(Arrays.asList(dto.getLabels()));
			dto.setLabels(null);
			if(fieldCodes.size() > 0){
	    		map.put("fieldCodes", fieldCodes);
	    		map.put("fieldDatas", fieldDatas);
	    		map.put("orgId", dto.getOrgId());
	    		//map.put("custIds", dto.getCustIds());				
	    		List<String> actionIds = custLabelCodeDataMapper.findActionIdsByLabelCodeData(map);
	    		if(actionIds.size() > 0){
	    			dto.setIds(actionIds);
				}else{
					return custs;
				}
	    	}
		}
    	if (StringUtils.isNotBlank(dto.getQueryText()) && ("phone".equals(dto.getQueryType()))) {
			return custFollowMapper.queryCustFollowsPhoneListPage(dto);
		} else {
			return custFollowMapper.queryCustFollowsListPage(dto);
		}
	}
	
	/** 根据资源IDS 查询资源信息 */
	public List<CustFollowDto> getResCustsByCustIds(CustFollowDto dto){
		return custFollowMapper.findResCustsByCustIds(dto);
	}
	
	/**分页查询 跟进警报  
	 * @throws Exception */
	public List<CustFollowDto> getTsmCustFollowAlarmPageList(CustFollowDto dto,List<String>multiList) throws Exception{
		List<CustFollowDto> custFollowDtos = new ArrayList<CustFollowDto>();
    	List<String> cids = new ArrayList<String>();
    	if(dto.getState() != null && dto.getState() == 1 && StringUtils.isNotBlank(dto.getQueryText()) && (dto.getQueryType().equals("3") || dto.getQueryType().equals("2"))){
    		if(dto.getQueryType().equals("3")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(dto.getOrgId(), dto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(dto.getOrgId(), dto.getQueryText());
    		}
    		if(cids.size() == 0) return custFollowDtos;
    		dto.setCustIds(cids);
    		dto.setQueryText(null);
    	}

    	if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(dto, multiList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(custIds.size() > 0){
					dto.setCustIds(custIds);
				}else{
					return custFollowDtos;
				}
			}
		}
    	Integer count = dto.getPage().getTotalResult();
    	if (StringUtils.isNotBlank(dto.getQueryText()) && dto.getQueryType().equals("3") && (dto.getState() == null || dto.getState() == 0)) {
			if (dto.getPage().getCurrentPage() < 2) {
				count = custFollowMapper.findTsmCustFollowAlarmPhoneCount(dto);
				dto.getPage().setTotalResult(count);
				dto.getPage().setTotalPage(dto.getPage().getTotalPage());
			}
			if (count == 0) {
				return custFollowDtos;
			}
			custFollowDtos =  custFollowMapper.findTsmCustFollowAlarmPhonePageList(dto);
		}else{
			if (dto.getPage().getCurrentPage() < 2) {
				count = custFollowMapper.findTsmCustFollowAlarmCount(dto);
				dto.getPage().setTotalResult(count);
				dto.getPage().setTotalPage(dto.getPage().getTotalPage());
			}
			if (count == 0) {
				return custFollowDtos;
			}
			custFollowDtos =  custFollowMapper.findTsmCustFollowAlarmPageList(dto);
		}
		return custFollowDtos;
	}
	
	/** 跟进警报 条数*/
	public Integer getTsmCustFollowAlarmCount(CustFollowDto dto){
		if (StringUtils.isNotBlank(dto.getQueryText()) && "3".equals(dto.getQueryType())) {
			return custFollowMapper.findTsmCustFollowAlarmPhoneCount(dto);
		}else{
			return custFollowMapper.findTsmCustFollowAlarmCount(dto);
		}
	
	}
	
	/**分页查询 客户跟进  
	 * @throws Exception */
	public List<CustFollowDto> getTsmCustFollowListPage(CustFollowDto dto,List<String> multiList) throws Exception{
		List<CustFollowDto> custFollows = new ArrayList<CustFollowDto>();
    	List<String> cids = new ArrayList<String>();
    	if(dto.getState() != null && dto.getState() == 1 && StringUtils.isNotBlank(dto.getQueryText()) && (dto.getQueryType().equals("phone") || dto.getQueryType().equals("linkName"))){
    		if(dto.getQueryType().equals("phone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(dto.getOrgId(), dto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(dto.getOrgId(), dto.getQueryText());
    		}
    		if(cids.size() == 0) return custFollows;
    		dto.setCustIds(cids);
    		dto.setQueryText(null);
    	}
    	
    	if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(dto, multiList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(custIds.size() > 0){
					dto.setCustIds(custIds);
				}else{
					return custFollows;
				}
			}
		}
    	
		if (StringUtils.isNotBlank(dto.getQueryText()) && "phone".equals(dto.getQueryType()) && (dto.getState() == null || dto.getState() == 0)) {
			return custFollowMapper.findTsmCustFollowPhoneListPage(dto);
		} else {
			return custFollowMapper.findTsmCustFollowListPage(dto);
		}
	}
	
	/** 客户跟进 签约客户计划 右侧待跟进列表 */
	public List<RestDto> getFollowPlanSign(String orgId,String userId,String owerAcc,String custId,String lastCustType,String lastOptionId){
		//TODO 查询今日计划ID 
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("userId",userId);
		params.put("fromDate", DateUtil.dateBegin(new Date()));
		params.put("toDate", DateUtil.dateEnd(new Date()));
		PlanUserDayBean planUserDayBean = planUserDayMapper.queryByUserAndDate(params);	
		List<RestDto>restDtos = new ArrayList<RestDto>();
		if(planUserDayBean != null && StringUtils.isNotBlank(planUserDayBean.getId())){
			RestDto restDto = new RestDto();
			restDto.setResCustId(custId);
			restDto.setOrgId(orgId);
			restDto.setSudId(planUserDayBean.getId());
			restDto.setOwnerAcc(owerAcc);
			restDto.setLastCustType(lastCustType);
			restDto.setLastOptionId(lastOptionId);
			restDto.setStartPage(0);
			restDto.setEndPage(100);
			restDtos = custFollowMapper.findFollowPlanSign(restDto);
		}
		return restDtos;
	}
	public List<RestDto> getFollowPlanSign(String orgId,String userId,String owerAcc,String custId,String lastCustType,String lastOptionId, Integer tDateType, String taoStartDate,
			String taoEndDate, String resGroupId, String orderType) {
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("userId",userId);
		params.put("fromDate", DateUtil.dateBegin(new Date()));
		params.put("toDate", DateUtil.dateEnd(new Date()));
		PlanUserDayBean planUserDayBean = planUserDayMapper.queryByUserAndDate(params);	
		List<RestDto>restDtos = new ArrayList<RestDto>();
		if(planUserDayBean != null && StringUtils.isNotBlank(planUserDayBean.getId())){
			RestDto restDto = new RestDto();
			restDto.setResCustId(custId);
			restDto.setOrgId(orgId);
			restDto.setSudId(planUserDayBean.getId());
			restDto.setOwnerAcc(owerAcc);
			restDto.setLastCustType(lastCustType);
			restDto.setLastOptionId(lastOptionId);
			restDto.settDateType(tDateType);
			restDto.setTaoStartDate(taoStartDate);
			restDto.setTaoEndDate(taoEndDate);
			restDto.setResGroupId(resGroupId);
			restDto.setOrderType(orderType);
			restDto.setStartPage(0);
			restDto.setEndPage(100);
			restDtos = custFollowMapper.findFollowPlanSign(restDto);
		}
		return restDtos;
	}

	
	/** 客户跟进 签约客户计划 右侧待跟进列表 */
	public List<RestDto> getFollowPlanSign(Map<String, Object> map){
		//TODO 查询今日计划ID 
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("orgId", (String)map.get("orgId"));
		params.put("userId",(String)map.get("userId"));
		params.put("fromDate", DateUtil.dateBegin(new Date()));
		params.put("toDate", DateUtil.dateEnd(new Date()));
		PlanUserDayBean planUserDayBean = planUserDayMapper.queryByUserAndDate(params);	
		List<RestDto>restDtos = new ArrayList<RestDto>();
		if(planUserDayBean != null && StringUtils.isNotBlank(planUserDayBean.getId())){
			RestDto restDto = new RestDto();
			restDto.setResCustId((String)map.get("custId"));
			restDto.setOrgId((String)map.get("orgId"));
			restDto.setSudId(planUserDayBean.getId());
			restDto.setOwnerAcc((String)map.get("owerAcc"));
			restDto.setLastCustType((String)map.get("lastCustType"));
			restDto.setLastOptionId((String)map.get("lastOptionId"));
			restDto.settDateType((Integer)map.get("tDateType"));
			restDto.setTaoStartDate((String)map.get("taoStartDate"));
			restDto.setTaoEndDate((String)map.get("taoEndDate"));
			restDto.setResGroupId((String)map.get("resGroupId"));
			restDto.setOrderType((String)map.get("orderType"));
			restDto.setStartPage(0);
			restDto.setEndPage(100);
			restDtos = custFollowMapper.findFollowPlanSign(restDto);
		}
		return restDtos;
	}
	
	/** 客户跟进 意向客户计划 右侧待跟进列表 */
	public List<RestDto> getFollowPlanWill(String orgId,String userId,String owerAcc,String custId,String lastCustType,String lastOptionId){
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("userId",userId);
		params.put("fromDate", DateUtil.dateBegin(new Date()));
		params.put("toDate", DateUtil.dateEnd(new Date()));
		PlanUserDayBean planUserDayBean = planUserDayMapper.queryByUserAndDate(params);	
		List<RestDto>restDtos = new ArrayList<RestDto>();
		if(planUserDayBean != null && StringUtils.isNotBlank(planUserDayBean.getId())){
			RestDto restDto = new RestDto();
			restDto.setResCustId(custId);
			restDto.setOrgId(orgId);
			restDto.setSudId(planUserDayBean.getId());
			restDto.setOwnerAcc(owerAcc);
			restDto.setLastCustType(lastCustType);
			restDto.setLastOptionId(lastOptionId);
			restDto.setStartPage(0);
			restDto.setEndPage(100);
			restDtos = custFollowMapper.findFollowPlanWill(restDto);
		}
		return restDtos;
	}
	/** 客户跟进 意向客户计划 右侧待跟进列表 */
	public List<RestDto> getFollowPlanWill(Map<String, Object> map){
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("orgId", (String)map.get("orgId"));
		params.put("userId",(String)map.get("userId"));
		params.put("fromDate", DateUtil.dateBegin(new Date()));
		params.put("toDate", DateUtil.dateEnd(new Date()));
		PlanUserDayBean planUserDayBean = planUserDayMapper.queryByUserAndDate(params);	
		List<RestDto>restDtos = new ArrayList<RestDto>();
		if(planUserDayBean != null && StringUtils.isNotBlank(planUserDayBean.getId())){
			RestDto restDto = new RestDto();
			restDto.setResCustId((String)map.get("custId"));
			restDto.setOrgId((String)map.get("orgId"));
			restDto.setSudId(planUserDayBean.getId());
			restDto.setOwnerAcc((String)map.get("owerAcc"));
			restDto.setLastCustType((String)map.get("lastCustType"));
			restDto.setLastOptionId((String)map.get("lastOptionId"));
			restDto.settDateType((Integer)map.get("tDateType"));
			restDto.setTaoStartDate((String)map.get("taoStartDate"));
			restDto.setTaoEndDate((String)map.get("taoEndDate"));
			restDto.setResGroupId((String)map.get("resGroupId"));
			restDto.setOrderType((String)map.get("orderType"));
			restDto.setStartPage(0);
			restDto.setEndPage(100);
			restDtos = custFollowMapper.findFollowPlanWill(restDto);
		}
		return restDtos;
	}
	
	public List<RestDto> getFollowPlanWill(String orgId,String userId,String owerAcc,String custId,String lastCustType,String lastOptionId, Integer tDateType, String taoStartDate,
			String taoEndDate, String resGroupId, String orderType) {
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("userId",userId);
		params.put("fromDate", DateUtil.dateBegin(new Date()));
		params.put("toDate", DateUtil.dateEnd(new Date()));
		PlanUserDayBean planUserDayBean = planUserDayMapper.queryByUserAndDate(params);	
		List<RestDto>restDtos = new ArrayList<RestDto>();
		if(planUserDayBean != null && StringUtils.isNotBlank(planUserDayBean.getId())){
			RestDto restDto = new RestDto();
			restDto.setResCustId(custId);
			restDto.setOrgId(orgId);
			restDto.setSudId(planUserDayBean.getId());
			restDto.setOwnerAcc(owerAcc);
			restDto.setLastCustType(lastCustType);
			restDto.setLastOptionId(lastOptionId);
			restDto.settDateType(tDateType);
			restDto.setTaoStartDate(taoStartDate);
			restDto.setTaoEndDate(taoEndDate);
			restDto.setResGroupId(resGroupId);
			restDto.setOrderType(orderType);
			restDto.setStartPage(0);
			restDto.setEndPage(100);
			restDtos = custFollowMapper.findFollowPlanWill(restDto);
		}
		return restDtos;
	}
	
	/** 客户跟进详细页面 右侧待跟进列表 */
	public List<RestDto> getFollowRightListPage(RestDto restDto){
			return custFollowMapper.findFollowRightViewListPage(restDto);
	}
	
	/**客户跟进详细页面 右侧其他待跟进列表 下次跟进时间等于当日的数据 */
	public List<RestDto> getDayFollowRightViewListPage(RestDto restDto){
		return custFollowMapper.findDayFollowRightViewListPage(restDto);
	}
	
	/** 客户跟进详细页面 右侧其他待跟进列表 待跟进警报 */
	public List<RestDto> getFollowRightAralmView(RestDto restDto){
		return custFollowMapper.findFollowRightAralmView(restDto);
	}
	
	/** 客户跟进详细页面 右侧其他待跟进列表 签约未跟进客户 */
	public List<RestDto>getSignFollowRightView(RestDto restDto){
		return custFollowMapper.findSignFollowRightView(restDto);
	}
	
	/**
	 * 查询待跟进记录
	 */
	public List<CustFollowDto> getNextFollowList(Map<String, Object> map){
		return custFollowMapper.findNextFollowList(map);
	}
	
	/**分页查询 销售进程  */
	public List<CustFollowDto> getTeamSaleAndFollowListPage(CustFollowDto dto){
		List<CustFollowDto> custs = new ArrayList<CustFollowDto>();
    	List<String> cids = new ArrayList<String>();
    	if(dto.getState() != null && dto.getState() == 1 && StringUtils.isNotBlank(dto.getQueryText()) && (dto.getQueryType().equals("3") || dto.getQueryType().equals("2"))){
    		if(dto.getQueryType().equals("3")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(dto.getOrgId(), dto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(dto.getOrgId(), dto.getQueryText());
    		}
    		if(cids.size() == 0) return custs;
    		dto.setCustIds(cids);
    		dto.setQueryText(null);
    	}
		return custFollowMapper.findTeamSaleAndFollowListPage(dto);
	}
	
	/** 查询最近一条跟进信息 */
	public CustFollowDto queryLastCustFollowByCustId(Map<String, Object> map){
		return custFollowMapper.queryLastCustFollowByCustId(map);
	}
	
	
	public CustFollowBean getLabelByCustId(Map<String, String> map){
		return custFollowMapper.findLabelByCustId(map);
	}
	
	public void savePoolCustFollow(String custId,String suitProcId,TsmCustGuide custGuide,CustFollowBean custFollow,ShiroUser user,String isTao){
		Date date=new Date();
		custFollow.setCustStatus(9);
		// 修改客户信息
		ResCustInfoBean resCust1 = new ResCustInfoBean();
		resCust1.setResCustId(custId);
		resCust1.setOrgId(user.getOrgId());
		ResCustInfoBean resCust = comResourceMapper.getByCondtion(resCust1);
//		resCust1.setUpdateDate(date);
//		resCust1.setUpdateAcc(user.getAccount());
		resCust1.setLastCustFollowID(custFollow.getCustFollowId());	
		resCust1.setActionDate(custFollow.getActionDate());
		resCust1.setNextFollowDate(custFollow.getFollowDate());
		resCust1.setLastOptionId(custFollow.getSaleProcessId());
		resCust1.setLastCustTypeId(custGuide.getCustTypeId());
		resCust1.setLabelCode(custFollow.getLabelCode());
		resCust1.setLabelName(custFollow.getLabelName());
		if(isTao.equals("1")){
			resCust1.setOwnerAcc(user.getAccount());
			resCust1.setOwnerName(user.getName());
			resCust1.setOpreateType(Integer.valueOf(AppConstant.OPREATE_TYPE22));
			resCust1.setType(2);
			resCust1.setStatus(3);
			resCust1.setAmoytocustomerDate(date);
			resCust1.setOwnerStartDate(date);
			resCust1.setSource(3);
			resCust1.setOwnerActionDate(date);
			resCust1.setOwnerActionDate(date);
			logContactDayDataService.addLogContactDayData(ContactUtil.SEA_TO_WILL, user.getOrgId(), custId, user.getAccount(), custFollow.getSaleProcessId(), custFollow.getSaleProcessId());
//			logCustInfoService.addLog(user.getOrgId(), user.getAccount(), custId, "", OperateEnum.LOG_WILL);
//			tsmReportWillService.addTsmReportWillandOption(user.getOrgId(),user.getAccount(),user.getName(),1,custFollow.getSaleProcessId(),"",1);
			rankingReportService.updateRankingData(user.getOrgId(), user.getAccount(), BigDecimal.valueOf(0), 0, 1);
			
			LogBean logBean = new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_WILL.getCode(), OperateEnum.LOG_WILL.getDesc(), 1,SysBaseModelUtil.getModelId());
			logBean.setOperateId(AppConstant.Operate_id63);
			logBean.setOperateName(AppConstant.Operate_Name63);
			logBean.setModuleId(AppConstant.Module_id1004);
			logBean.setModuleName(AppConstant.Module_Name1004);
			logBean.setContent((resCust.getCompany() == null || "".equals(resCust.getCompany()))? resCust.getName() : resCust.getCompany());
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(custId, "");
			logCustInfoService.addTableStoreLog(logBean, logMap);
		}
		
		// 需要删除 多选项表中 行动标签值
		Map<String,Object>delMap = new HashMap<String, Object>();
		delMap.put("orgId", resCust1.getOrgId());
		delMap.put("custId", resCust1.getResCustId());
		delMap.put("fieldCode", "labelCode");
		custDefinedDataMapper.deleteByFieldCode(delMap);
		if(StringUtils.isNotBlank(custFollow.getLabelCode())){
			List<CustDefinedDataBean>mulitBeans = new ArrayList<CustDefinedDataBean>();
			for(String labelCode:custFollow.getLabelCode().split("#")){
				CustDefinedDataBean mulitDefinedBean = new CustDefinedDataBean();
				mulitDefinedBean.setId(SysBaseModelUtil.getModelId());
				mulitDefinedBean.setOrgId(resCust1.getOrgId());
				mulitDefinedBean.setCustId(resCust1.getResCustId());
				mulitDefinedBean.setFieldCode("labelCode");
				mulitDefinedBean.setFieldData(labelCode);
				mulitBeans.add(mulitDefinedBean);
			}
			if(mulitBeans != null && mulitBeans.size()>0){
				custDefinedDataMapper.insertBatch(mulitBeans);
			}			
		}
		
		comResourceMapper.update(resCust1);
		
		//新增本次跟进行动
		if(user.getIsState() == 0){ // 个人版 
			custFollow.setOldCustName(resCust.getName());
			custFollow.setCustDetailName(resCust.getCompany());
		}else{
			custFollow.setOldCustName(resCust.getName());
		}
		custFollow.setCustTypeId(custGuide.getCustTypeId());
		custFollow.setCustId(resCust.getResCustId());
		custFollow.setFollowAcc(user.getAccount());
		custFollow.setOrgId(user.getOrgId());
		custFollow.setInputAcc(user.getAccount());
		custFollow.setInputDate(date);
		custFollowMapper.insert(custFollow);
		
	    if(StringUtils.isNotBlank(custGuide.getCustGuideId())){
			//  修改销售导航
			custGuide.setOrgId(user.getOrgId());
			custGuide.setSaleProcessId(custFollow.getSaleProcessId());
			custGuide.setCustId(custId);
			custGuide.setInputerAcc(user.getAccount());
			custGuide.setInputdate(new Date());
			tsmCustGuideMapper.updateTrends(custGuide);		
		}else{
			// 新增销售导航记录
			String guideId = SysBaseModelUtil.getModelId();
			custGuide.setCustGuideId(guideId);
			custGuide.setCustId(custId);
			custGuide.setInputerAcc(ShiroUtil.getUser().getAccount());
			custGuide.setInputdate(new Date());
			custGuide.setOrgId(user.getOrgId());
			custGuide.setSaleProcessId(custFollow.getSaleProcessId());
			tsmCustGuideMapper.insert(custGuide);
		}
		
		/**  修改 适用产品 */	
		// 先删除 与销售导航相关产品
		Map<String,String>map = new HashMap<String, String>();
		map.put("orgId", user.getOrgId());
		map.put("guideId", custGuide.getCustGuideId());
		tsmCustGuideProcMapper.deleteBy(map);		
		//  新增适用产品
		if(StringUtils.isNotBlank(suitProcId)){
			String[] proIds = suitProcId.split(",");
			List<TsmCustGuideProc> suitProList = new ArrayList<TsmCustGuideProc>();
			for (String proId : proIds) {
				if(StringUtils.isNotBlank(proId)){
					TsmCustGuideProc proc = new TsmCustGuideProc();
					proc.setId(GuidUtil.getId());
					proc.setGuideId(custGuide.getCustGuideId());
					proc.setProcId(proId);
					proc.setOrgId(user.getOrgId());
					suitProList.add(proc);
				}				
			}
			if (suitProList.size() > 0) {
				tsmCustGuideProcMapper.insertBatch(suitProList);
			}
		}
		
		// 增加事件数据
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("inputDate", new Date());
		if(user.getIsState() == 1){ // 企业资源 根据联系人ID 查询实际联系人
			String linkMan = resCust.getMainLinkman();
			if(StringUtils.isNotBlank(custFollow.getCustDetailName())){
				linkMan = custFollow.getCustDetailName();
			}
			jsonMap.put("mainLinkman", linkMan);
		}else{
			jsonMap.put("mainLinkman", resCust.getName());
		}		
		jsonMap.put("custName", resCust.getName());
		jsonMap.put("userName", (user.getName() == null || "".equals(user.getName()) ? user.getAccount() : user.getName()));
		jsonMap.put("telphone", resCust.getMobilephone());
		jsonMap.put("type", custFollow.getActionType());
		jsonMap.put("nextConcatTime", custFollow.getFollowDate());
		// 跟进销售进程ID 从缓存中 查询销售进程名称
		List<OptionBean> saleProcessList = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
		if(saleProcessList!= null && saleProcessList.size() >0){
			for(OptionBean opb : saleProcessList){
				if(opb.getOptionlistId().equals(custFollow.getSaleProcessId())){
					jsonMap.put("saleProcessName", opb.getOptionName());
					break;
				}
			}		
		}	
		jsonMap.put("labels", custFollow.getLabelName());
		jsonMap.put("remark", custFollow.getFeedbackComment());
		jsonMap.put("custFollowId", custFollow.getCustFollowId());
	    resCustEventService.create(user.getOrgId(), custId, "1", JsonUtil.getJsonString(jsonMap));
	}
	/** 
	 * 缓存读取签约是否与合同无关 0-需添加合同 1-无需添加合同
	 * @param request 
	 * @create  2016年8月10日 下午2:59:09 lixing
	 * @history  
	 */
	public Integer getSignSetting(){
		ShiroUser user = ShiroUtil.getShiroUser();
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_40039, user.getOrgId());
		Integer signSetting = 0;
		if(!list.isEmpty() && list.get(0) != null){
			signSetting = new Integer(list.get(0).getDictionaryValue());
		}
		Map<String, String> map = cachedService.getAuthUrlButton(user);
		if (map.get("mySignCust_addContract") == null && signSetting == 0) {
			signSetting = 1;
		}
		return signSetting;
	}
	
	/** 跟进保存 
	 *  @param suitProcId 使用产品
	 *  @param isSign 是否是签约操作
	 *  @param is_follow 是否放弃客户操作
	 * @throws Exception 
	 * */
	public void saveCustFollow(String custId,String suitProcId,TsmCustGuide custGuide,CustFollowBean custFollow,boolean isSign,boolean is_follow,ShiroUser user,String isAddDate) throws Exception{
	    Date date=new Date();
	   
	    /************* 新增客户操作 **************/
	    if(is_follow){ // 主动放弃
			// 资源客户呼叫数统计 清除
			List<String>ids = new ArrayList<String>();
			ids.add(custId);
			resCustInfoMapper.cleanCallCustCountByCustId(ids, user.getOrgId());
		    custSaleChanceMapper.updateIsDelByCustIds(user.getOrgId(),ids);
			//记录日志
			ids.clear();
			ids.add(custId+"_2"); // _2:意向，_1：资源
			taskExecutor.execute(new CustGiveUpThread(ids, user,null,AppConstant.OPREATE_TYPE12,""));
	    }	   
	    Integer signSetting = getSignSetting();
	    
		// 修改客户信息
		ResCustInfoBean resCust1 = new ResCustInfoBean();
		ResCustInfoBean resCust = new ResCustInfoBean();
		resCust1.setResCustId(custId);
		resCust1.setOrgId(user.getOrgId());
		resCust = comResourceMapper.getByCondtion(resCust1);
		if ("1".equals(isAddDate)) custFollow.setFollowDate(null);
		if(isSign){
			custFollow.setCustStatus(Integer.valueOf(AppConstant.STATUS_6)); // 设置跟进信息表中的客户状态
			resCust1.setStatus(Integer.valueOf(AppConstant.STATUS_6));
			resCust1.setOpreateType(Integer.valueOf(AppConstant.OPREATE_TYPE15));
		}else{
			if(is_follow){ // 主动放弃
				resCust1.setStatus(Integer.valueOf(AppConstant.STATUS_4));
				resCust1.setOpreateType(Integer.valueOf(AppConstant.OPREATE_TYPE12));
				 //resCust.setOwnerAcc("");	
			}else{
				if(resCust.getStatus() != 6 && resCust.getStatus() != 7 && resCust.getStatus() != 8 && resCust.getStatus() != 4){ // 签约客户与沉默客户以及公海客户 状态不做修改
					resCust1.setStatus(Integer.valueOf(AppConstant.STATUS_3));
					resCust1.setOpreateType(Integer.valueOf(AppConstant.OPREATE_TYPE10));
				}
				if(resCust.getStatus() == 2 || resCust.getStatus() == 3){ // 设置跟进信息中的状态为意向客户，或者为客户最新状态
					custFollow.setCustStatus(5);
				}else{
					if(resCust.getStatus() !=1 && resCust.getStatus() !=4 && resCust.getStatus() !=5){
						custFollow.setCustStatus(resCust.getStatus());
					}		
				}
			}								
		}
		resCust1.setUpdateDate(date);
		resCust1.setUpdateAcc(user.getAccount());
		resCust1.setLastCustFollowID(custFollow.getCustFollowId());	
		resCust1.setActionDate(custFollow.getActionDate());
		resCust1.setNextFollowDate(custFollow.getFollowDate());
		resCust1.setLastOptionId(custFollow.getSaleProcessId());
		resCust1.setLastCustTypeId(custGuide.getCustTypeId());
		resCust1.setLabelCode(custFollow.getLabelCode());
		resCust1.setLabelName(custFollow.getLabelName());
		if (user.getAccount().equals(resCust.getOwnerAcc())) {
			resCust1.setOwnerActionDate(custFollow.getActionDate());
		}
		// 需要删除 多选项表中 行动标签值
		Map<String,Object>delMap = new HashMap<String, Object>();
		delMap.put("orgId", resCust1.getOrgId());
		delMap.put("custId", resCust1.getResCustId());
		delMap.put("fieldCode", "labelCode");
		custDefinedDataMapper.deleteByFieldCode(delMap);
		if(StringUtils.isNotBlank(custFollow.getLabelCode())){
			List<CustDefinedDataBean>mulitBeans = new ArrayList<CustDefinedDataBean>();
			for(String labelCode:custFollow.getLabelCode().split("#")){
				CustDefinedDataBean mulitDefinedBean = new CustDefinedDataBean();
				mulitDefinedBean.setId(SysBaseModelUtil.getModelId());
				mulitDefinedBean.setOrgId(resCust1.getOrgId());
				mulitDefinedBean.setCustId(resCust1.getResCustId());
				mulitDefinedBean.setFieldCode("labelCode");
				mulitDefinedBean.setFieldData(labelCode);
				mulitBeans.add(mulitDefinedBean);
			}
			if(mulitBeans != null && mulitBeans.size()>0){
				custDefinedDataMapper.insertBatch(mulitBeans);
			}	
			// 标签记录增加
			List<CustLabelCodeDataBean>mulitBeans1 = new ArrayList<CustLabelCodeDataBean>();
			for(String labelCode:custFollow.getLabelCode().split("#")){
				CustLabelCodeDataBean mulitDefinedBean1 = new CustLabelCodeDataBean();
				mulitDefinedBean1.setId(SysBaseModelUtil.getModelId());
				mulitDefinedBean1.setOrgId(resCust.getOrgId());
				mulitDefinedBean1.setCustId(resCust.getResCustId());
				mulitDefinedBean1.setActionId(custFollow.getCustFollowId());
				mulitDefinedBean1.setFieldCode("labelCode");
				mulitDefinedBean1.setFieldData(labelCode);
				mulitBeans1.add(mulitDefinedBean1);
			}
			if(mulitBeans1 != null && mulitBeans1.size()>0){
				custLabelCodeDataMapper.insertBatch(mulitBeans1);
			}	
		}
		
		comResourceMapper.update(resCust1);				
	
		//新增本次跟进行动
		if(user.getIsState() == 0){ // 个人版 
			custFollow.setOldCustName(resCust.getName());
			custFollow.setCustDetailName(resCust.getCompany());
		}else{
			custFollow.setOldCustName(resCust.getName());
		}
		custFollow.setCustTypeId(custGuide.getCustTypeId());
		custFollow.setCustId(resCust.getResCustId());
		custFollow.setFollowAcc(user.getAccount());
		custFollow.setOrgId(user.getOrgId());
		custFollow.setInputAcc(user.getAccount());
		custFollow.setInputDate(date);
		custFollowMapper.insert(custFollow);
		
		/** 客户跟进异步线程 start  */
		taskExecutor.execute(new CustFollowThread(user.getAccount(), user.getOrgId(), is_follow, 
				 isSign, custId,  user.getName(), resCust.getStatus(), custFollow.getSaleProcessId(), 
				 custFollow.getLastSaleProcessId(), user.getId(), user.getGroupId(), custGuide, suitProcId,custFollow.getActionType(),custFollow.getCustFollowId(),custFollow.getFollowDate(),logContactDayDataService,resCust.getOwnerAcc(),signSetting));
		 
		// 增加事件数据
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("inputDate", new Date());
		if(user.getIsState() == 1){ // 企业资源 根据联系人ID 查询实际联系人
			String linkMan = resCust.getMainLinkman();
			if(StringUtils.isNotBlank(custFollow.getCustDetailName())){
				linkMan = custFollow.getCustDetailName();
			}
			jsonMap.put("mainLinkman", linkMan);
		}else{
			jsonMap.put("mainLinkman", resCust.getName());
		}		
		jsonMap.put("custName", resCust.getName());
		jsonMap.put("userName", (user.getName() == null || "".equals(user.getName()) ? user.getAccount() : user.getName()));
		jsonMap.put("telphone", resCust.getMobilephone());
		jsonMap.put("type", custFollow.getActionType());
		jsonMap.put("nextConcatTime", custFollow.getFollowDate());
		// 跟进销售进程ID 从缓存中 查询销售进程名称
		List<OptionBean> saleProcessList = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
		if(saleProcessList!= null && saleProcessList.size() >0){
			for(OptionBean opb : saleProcessList){
				if(opb.getOptionlistId().equals(custFollow.getSaleProcessId())){
					jsonMap.put("saleProcessName", opb.getOptionName());
					break;
				}
			}		
		}	
		jsonMap.put("labels", custFollow.getLabelName());
		jsonMap.put("remark", custFollow.getFeedbackComment());
		jsonMap.put("custFollowId", custFollow.getCustFollowId());
	    resCustEventService.create(user.getOrgId(), custId, "1", JsonUtil.getJsonString(jsonMap));
	    
	    // 资源同步接口
	    if(tsmCustSynConfigService.isConfigExist(user.getOrgId(), 1)){
	    	Map<String, String> custTypeMap = cachedService.getOrgCustTypes(user.getOrgId());
			Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());			
	    	TsmCustSynFollowDto synDto = new TsmCustSynFollowDto();
	    	synDto.setId(SysBaseModelUtil.getModelId());
	    	if(user.getIsState() == 0){ // 个人版 
	    		synDto.setCustName(custFollow.getCustDetailName());
	    		synDto.setLinkName(custFollow.getOldCustName());
	    	}else{
	    		synDto.setCustName(custFollow.getOldCustName());
	    		synDto.setLinkName(custFollow.getCustDetailName());
	    	}
	    	synDto.setLinkPhone(custFollow.getCustDetailMobile());
	    	synDto.setOwnerAcc(nameMap.get(resCust.getOwnerAcc()) != null ? nameMap.get(resCust.getOwnerAcc()) : resCust.getOwnerAcc());
	    	synDto.setFollowAcc(nameMap.get(custFollow.getFollowAcc()) != null ? nameMap.get(custFollow.getFollowAcc()) : custFollow.getFollowAcc());
	    	synDto.setSaleProcess(optionMap.get(custFollow.getSaleProcessId()) != null ? optionMap.get(custFollow.getSaleProcessId()) : null);
	    	synDto.setCustStatus(custFollow.getCustStatus() != null ? FollowCustEnum.getEnum1(custFollow.getCustStatus().toString(), FollowCustEnum.getCustFollowStatus()).getDescr() : null);
	    	synDto.setActionDate(DateUtil.formatDate(custFollow.getActionDate(), DateUtil.Data_ALL));
	    	synDto.setActionType(custFollow.getActionType() != null ? FollowCustEnum.getEnum1(custFollow.getActionType().toString(), FollowCustEnum.getCustFollowActionType()).getDescr() : null);
	    	synDto.setNextActionDate(custFollow.getFollowDate() == null ? null : DateUtil.formatDate(custFollow.getFollowDate(), DateUtil.Data_ALL));
	    	synDto.setNextActionType(custFollow.getFollowType() != null ? FollowCustEnum.getEnum1(custFollow.getFollowType().toString(), FollowCustEnum.getCustFollowActionType()).getDescr() : null);
	    	synDto.setCustType(custTypeMap.get(custFollow.getCustTypeId()) != null ? custTypeMap.get(custFollow.getCustTypeId()) : null );
	    	synDto.setEffectiveness(custFollow.getEffectiveness()==0 ? "无效联系" : "有效联系");
	    	synDto.setLabelCode(custFollow.getLabelName());
	    	if(StringUtils.isNotBlank(suitProcId)){
				String[] proIds = suitProcId.split(",");
				List<Product> suitProcList = cachedService.getOpionProduct(user.getOrgId());
				// 从缓存中获取 产品列表,放入map ，匹配上次跟进时 选择的产品
				StringBuffer sbf = new StringBuffer();
				Map<String,String> map1 = new HashMap<String, String>();
				for(Product product1 : suitProcList){
					map1.put(product1.getId(), product1.getName());
				}
				for (String proId : proIds) {
					if(StringUtils.isNotBlank(proId) && map1.get(proId) != null){						
						sbf.append(map1.get(proId)).append("#");
					}				
				}
				synDto.setProduct(sbf.toString());
	    	}
	    	synDto.setActionInfo(custFollow.getFeedbackComment());
	    	tsmCustSynConfigService.insetCustSynTempData(user.getOrgId(), 1, JSON.toJSONString(synDto));
	    }
	
	}
	
	public String getSalesProcessByCustId(String orgId,String custId){
		return custFollowMapper.findSalesProcessByCustId(orgId, custId);
	}
	
	/** 下次跟进时间调整 */
	public void modfiyNextActionDate(Date nextActionDate,String followIds,String resCustIds){
		ShiroUser user = ShiroUtil.getShiroUser();
		Date date=new Date();
		
		// 修改客户信息
		if(StringUtils.isNotBlank(resCustIds)){
			String[]custIds = resCustIds.split(",");
			for(String custId:custIds){
				ResCustInfoBean resCust =new ResCustInfoBean();
				resCust.setResCustId(custId);
				resCust.setOrgId(user.getOrgId());
				resCust.setUpdateDate(date);
				resCust.setUpdateAcc(user.getAccount());
				resCust.setNextFollowDate(nextActionDate);
				comResourceMapper.updateTrends(resCust);
			}
		}
		boolean result=false;
		String[] ids = resCustIds.split(",");
		if(nextActionDate != null){
			 result = DateUtil.isNow(nextActionDate);
			 if(result==true){
				 List<ResCustInfoBean> beans = comResourceMapper.findResListByIds(Arrays.asList(ids),user.getOrgId());
				 for (ResCustInfoBean bean : beans) {
					 OrgGroupUser usermeber = new OrgGroupUser();
		             usermeber.setOrgId(user.getOrgId());
		             usermeber.setMemberAcc(bean.getOwnerAcc());
		             OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
		             if (bean.getStatus() == 2 || bean.getStatus() == 3) {
		            	 planFactService.updateContactResult(user.getOrgId(),newmember.getGroupId(), newmember.getUserId(),bean.getResCustId(),"will",true,"will");	
					}else if (bean.getStatus() == 6) {
						planFactService.updateContactResult(user.getOrgId(),newmember.getGroupId(), newmember.getUserId(),bean.getResCustId(),"sign",true,"sign");
					}
				}
			}
		}
		LogBean logBean = new LogBean();
		logBean.setOrgId(user.getOrgId());
		logBean.setUserAcc(user.getAccount());
		logBean.setUserName(user.getName());
		logBean.setOperateName(AppConstant.Operate_Name62);
		logBean.setOperateId(AppConstant.Operate_id62);
		logBean.setModuleId(AppConstant.Module_id113);
		logBean.setModuleName(AppConstant.Module_Name113);
		logBean.setContent(ids.length + "条");
		logCustInfoService.addTableStoreLog(logBean, null);
				
		// 修改跟进信息
		/*if(StringUtils.isNotBlank(followIds)){
			String[]follows = followIds.split(",");
			for(String followId:follows){
				CustFollowBean custFollow = new CustFollowBean();
				custFollow.setCustFollowId(followId);
				custFollow.setOrgId(user.getOrgId());
				//custFollow.setFollowDate(nextActionDate);
				custFollowMapper.updateTrends(custFollow);
			}
		}*/

	}
	
	public Map<String,String> getLastConcatDay(Map<String, String> map){
		return custFollowMapper.findLastConcatDay(map);
	}
	public List<CustFollowDto> queryCustFollows4TPListPage(CustFollowDto custFollowDto){
		return custFollowMapper.findCustFollows4TPListPage(custFollowDto);
	}

	/** 根据计划id获取签约客户计划 资源集合 */
	public List<String>getCustIdBySignPlanIds(Map<String,Object>map){
		return custFollowMapper.queryCustIdBySignPlanIds(map);
	}
	/** 根据计划id获取意向客户计划 资源集合 */
	public List<String>getCustIdByWillPlanIds(Map<String,Object>map){
		return custFollowMapper.queryCustIdByWillPlanIds(map);
	}
	
	/*******************************客户跟进详情右侧列表
	 * @throws Exception ********************************/
	
	//客户跟进列表点击 跟进操作
	public List<RestDto>getFollowRightViewByFollowRecordListPage(RestDto restDto,List<String> multiList) throws Exception{
		List<RestDto> restDtos = new ArrayList<>();
    	List<String> cids = new ArrayList<String>();
    	if(restDto.getState() != null && restDto.getState() == 1 && StringUtils.isNotBlank(restDto.getQueryText()) && (restDto.getQueryType().equals("phone") || restDto.getQueryType().equals("linkName"))){
    		if(restDto.getQueryType().equals("phone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(restDto.getOrgId(), restDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(restDto.getOrgId(), restDto.getQueryText());
    		}
    		if(cids.size() == 0) return restDtos;
    		restDto.setResIds(cids);
    		restDto.setQueryText(null);
    	}
		if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(restDto, multiList);
			if(paramMap.size() > 0){
				List<String> resIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(resIds.size() > 0){
					restDto.setResIds(resIds);
				}else{
					return restDtos;
				}
			}
		}
		return custFollowMapper.findFollowRightViewByFollowRecordListPage(restDto);
	}
	
	
	/**设置扩展表查询条件*/
    public Map<String, Object> getMultiDefinedSearchParam(RestDto resDto,List<String> multiList) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	Class<?> clazz = resDto.getClass();
    	List<String> fieldCodes = new ArrayList<String>();
    	List<String> fieldDatas = new ArrayList<String>();
    	String getName;
    	String setName;
    	Method getMethod;
    	Method setMethod;
    	for(String fieldCode : multiList){
    		if(fieldCode.equals(AppConstant.SEARCH_LABEL) && resDto.getLabels() != null && resDto.getLabels().length > 0){
				fieldCodes.add(AppConstant.SEARCH_LABEL);
				fieldDatas.addAll(Arrays.asList(resDto.getLabels()));
				resDto.setLabels(null);
				continue;
    		}
    		getName =  "get" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
    		getMethod = clazz.getDeclaredMethod(getName);
    		Object val = getMethod.invoke(resDto);
    		if(val != null && StringUtils.isNotBlank(val.toString())){
    			fieldCodes.add(fieldCode);
    			fieldDatas.addAll(Arrays.asList(val.toString().split(",")));
    			setName =  "set" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
    			setMethod = clazz.getDeclaredMethod(setName, String.class);
    			setMethod.invoke(resDto, "");
    		}
    	}
    	if(fieldCodes.size() > 0){
    		map.put("fieldCodes", fieldCodes);
    		map.put("fieldDatas", fieldDatas);
    		map.put("orgId", resDto.getOrgId());
    	}
    	return map;
    }
	
	// 我的客户--【意向客户】 列表点击 跟进操作
	public List<RestDto> getFollowRightViewByMyCustListPage(RestDto restDto,List<String> multiList) throws Exception {   	
		List<RestDto> list = new ArrayList<RestDto>();
		List<String> cids = new ArrayList<String>();
    	if(restDto.getState() != null && 
    			restDto.getState() == 1 && 
    			StringUtils.isNotBlank(restDto.getQueryText()) && 
    			(restDto.getQueryType().equals("mobilephone") || restDto.getQueryType().equals("mainLinkman"))){
    		if(restDto.getQueryType().equals("mobilephone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(restDto.getOrgId(), restDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(restDto.getOrgId(), restDto.getQueryText());
    		}
    		if(cids.size() == 0) return list;
    		restDto.setResIds(cids);
    		restDto.setQueryText(null);
    	}
		if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(restDto, multiList);
			if(paramMap.size() > 0){
				List<String> resIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(resIds.size() > 0){
					restDto.setResIds(resIds);
				}else{
					return list;
				}
			}
		}
    	if (restDto != null && "3".equals(restDto.getNoteType())) {
            //今日需联系，查询计划需联系客户
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("orgId", restDto.getOrgId());
            params.put("userIds", restDto.getOwnerUserIds());
            params.put("fromDate", DateUtil.dateBegin(new Date()));
            params.put("toDate", DateUtil.dateEnd(new Date()));
            params.put("authState", "1");
            List<String> planIds = planUserDayMapper.findUsersPlanId(params);
            if (planIds.size() > 0) {
                params.put("planIds", planIds);
                params.put("status", 0);
                List<String> ids = planUserdayWillcustMapper.queryCustIdByPlanIds(params);
                restDto.setResCustIds(ids);
            }           
        }
    	if (StringUtils.isNotBlank(restDto.getQueryText()) && restDto.getQueryType().equals("mobilephone") && (restDto.getState() == null || restDto.getState() == 0)) {
    		list = custFollowMapper.findCustFollowRightByMyCustWithPhoneListPage(restDto);
        } else {
        	list = custFollowMapper.findCustFollowRightByMyCustListPage(restDto);
        }
		return list;
    }
    
    // 我的客户--【签约客户】 列表点击 跟进操作
    public List<RestDto> getFollowRightViewByMySignListPage(RestDto restDto,List<String> multiList) throws Exception{
    	List<RestDto> list =new ArrayList<RestDto>();
    	List<String> cids = new ArrayList<String>();
    	if(restDto.getState() != null && 
    			restDto.getState() == 1 && 
    			StringUtils.isNotBlank(restDto.getQueryText()) && 
    			(restDto.getQueryType().equals("mobilephone") || restDto.getQueryType().equals("mainLinkman"))){
    		if(restDto.getQueryType().equals("mobilephone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(restDto.getOrgId(), restDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(restDto.getOrgId(), restDto.getQueryText());
    		}
    		if(cids.size() == 0) return list;
    		restDto.setResIds(cids);
    		restDto.setQueryText(null);
    	}
    	if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(restDto, multiList);
			if(paramMap.size() > 0){
				List<String> resIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(resIds.size() > 0){
					restDto.setResIds(resIds);
				}else{
					return list;
				}
			}
		}
    	if (restDto != null && "4".equals(restDto.getNoteType())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orgId", restDto.getOrgId());
			params.put("userIds", restDto.getOwnerUserIds());
			params.put("fromDate", DateUtil.dateBegin(new Date()));
            params.put("toDate", DateUtil.dateEnd(new Date()));
			params.put("authState", "1");
			List<String> planIds = planUserDayMapper.findUsersPlanId(params);
			if (planIds.size() > 0) {
				params.put("planIds", planIds);
				params.put("status", 0);
				List<String> ids = planUserDaySigncustMapper.queryCustIdByPlanIds(params);
				restDto.setResCustIds(ids);
			}
		}
    	if (StringUtils.isNotBlank(restDto.getQueryText()) && "mobilephone".equals(restDto.getQueryType()) && (restDto.getState() == null || restDto.getState() == 0)) {
    		list = custFollowMapper.findCustFollowRightByMySignWithPhoneListPage(restDto);
		} else {
			list = custFollowMapper.findCustFollowRightByMySignListPage(restDto);
		}
    	return list;
    }

    //	我的客户--沉默客户 点击 跟进操作
    public List<RestDto> getFollowRightViewByMySilentListPage(RestDto restDto) throws Exception{
    	List<RestDto> custs = new ArrayList<RestDto>();
    	List<String> cids = new ArrayList<String>();
    	if(restDto.getState() != null && 
    			restDto.getState() == 1 && 
    			StringUtils.isNotBlank(restDto.getQueryText()) && 
    			(restDto.getQueryType().equals("3") || restDto.getQueryType().equals("2"))){
    		if(restDto.getQueryType().equals("3")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(restDto.getOrgId(), restDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(restDto.getOrgId(), restDto.getQueryText());
    		}
    		if(cids.size() == 0) return custs;
    		restDto.setResIds(cids);
    		restDto.setQueryText(null);
    	}
    	Map<String, Object> paramMap = getMultiDefinedSearchParam(restDto, Arrays.asList(AppConstant.SEARCH_LABEL));
		if(paramMap.size() > 0){
			if(cids.size() > 0) paramMap.put("custIds", cids);
			List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
			if(custIds.size() > 0){
				restDto.setResIds(custIds);
			}else{
				return custs;
			}
		}
    	return custFollowMapper.findCustFollowRightByMySilentListPage(restDto);
    }
    
	//	我的客户--流失客户 点击 跟进操作
	public List<RestDto>getCustFollowRightByMyLosListPage(RestDto restDto) throws Exception{
		List<RestDto> custs = new ArrayList<RestDto>();
    	List<String> cids = new ArrayList<String>();
    	if(restDto.getState() != null && 
    			restDto.getState() == 1 && 
    			StringUtils.isNotBlank(restDto.getQueryText()) && 
    			(restDto.getQueryType().equals("3") || restDto.getQueryType().equals("2"))){
    		if(restDto.getQueryType().equals("3")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(restDto.getOrgId(), restDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(restDto.getOrgId(), restDto.getQueryText());
    		}
    		if(cids.size() == 0) return custs;
    		restDto.setResIds(cids);
    		restDto.setQueryText(null);
    	}
    	Map<String, Object> paramMap = getMultiDefinedSearchParam(restDto, Arrays.asList(AppConstant.SEARCH_LABEL));
		if(paramMap.size() > 0){
			if(cids.size() > 0) paramMap.put("custIds", cids);
			List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
			if(custIds.size() > 0){
				restDto.setResIds(custIds);
			}else{
				return custs;
			}
		}
		return custFollowMapper.findCustFollowRightByMyLosListPage(restDto);
	}
	
	//	我的客户--全部客户 点击 跟进操作
	public List<RestDto> getCustFollowRightByMyAllListPage(RestDto restDto,List<String> multiList) throws Exception{
		List<RestDto> list = new ArrayList<RestDto>();
		if(restDto != null){
			List<String> cids = new ArrayList<String>();
	    	if(restDto.getState() != null && 
	    			restDto.getState() == 1 && 
	    			StringUtils.isNotBlank(restDto.getQueryText()) && 
	    			(restDto.getQueryType().equals("mobilephone") || restDto.getQueryType().equals("mainLinkman"))){
	    		if(restDto.getQueryType().equals("mobilephone")){
	    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(restDto.getOrgId(), restDto.getQueryText());
	    		}else{
	    			cids = resCustInfoDetailMapper.findLinkmanIds(restDto.getOrgId(), restDto.getQueryText());
	    		}
	    		if(cids.size() == 0) return list;
	    		restDto.setResIds(cids);
	    		//restDto.setQueryText(null);
	    	}
			if(multiList != null && multiList.size() > 0){
				Map<String, Object> paramMap = getMultiDefinedSearchParam(restDto, multiList);
				if(paramMap.size() > 0){
					List<String> resIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
					if(resIds.size() > 0){
						restDto.setResIds(resIds);
					}else{
						return list;
					}
				}
			}	
			
			if ("1".equals(restDto.getCustType()) && "4".equals(restDto.getNoteType())) {
                //今日需联系，查询计划需联系客户
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("orgId", restDto.getOrgId());
                params.put("userIds", restDto.getOwnerUserIds());
                params.put("fromDate", DateUtil.dateBegin(new Date()));
                params.put("toDate", DateUtil.dateEnd(new Date()));
                List<String> planIds = planUserDayMapper.findUsersPlanId(params);
                if (planIds.size() > 0) {
                    params.put("planIds", planIds);
                    List<String> ids = planUserdayWillcustMapper.queryCustIdByPlanIds(params);
                    restDto.setResCustIds(ids);
                }
            }else if ("2".equals(restDto.getCustType()) && "3".equals(restDto.getNoteType())) {
                //今日需联系，查询计划需联系客户
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("orgId", restDto.getOrgId());
                params.put("userIds", restDto.getOwnerUserIds());
                params.put("fromDate", DateUtil.dateBegin(new Date()));
                params.put("toDate", DateUtil.dateEnd(new Date()));
                params.put("authState", "1");
                List<String> planIds = planUserDayMapper.findUsersPlanId(params);
                if (planIds.size() > 0) {
                    params.put("planIds", planIds);
                    List<String> ids = planUserdayWillcustMapper.queryCustIdByPlanIds(params);
                    restDto.setResCustIds(ids);
                }
            }else if("3".equals(restDto.getCustType()) && "4".equals(restDto.getNoteType())){
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("orgId", restDto.getOrgId());
                params.put("userIds", restDto.getOwnerUserIds());
                params.put("fromDate", DateUtil.dateBegin(new Date()));
                params.put("toDate", DateUtil.dateEnd(new Date()));
                params.put("authState", "1");
                List<String> planIds = planUserDayMapper.findUsersPlanId(params);
                if (planIds.size() > 0) {
                    params.put("planIds", planIds);
                    List<String> ids = planUserDaySigncustMapper.queryCustIdByPlanIds(params);
                    restDto.setResCustIds(ids);
                }
            }
			if (StringUtils.isNotBlank(restDto.getQueryText()) && "mobilephone".equals(restDto.getQueryType()) && (restDto.getState() == null || restDto.getState() == 0)) {
    			list = custFollowMapper.findCustFollowRightByMyAllWithPhoneListPage(restDto);
    		} else {
    			list = custFollowMapper.findCustFollowRightByMyAllListPage(restDto);
    		}
    	}
    	return list;
	}
	
	// 单选、多选dto列表展示
	public void setCustFollowDefined(CustFollowDto cdto,Map<String,String> map,String orgId)throws Exception{		
		if(map != null && map.size() > 0){
			 Class tCls = cdto.getClass();
			 String getMethodName;
			 String setMethodName;
             Method getMethod =null;            
			for (Map.Entry<String, String> entry : map.entrySet()) {  	
				 getMethodName =  "get" + entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
				 getMethod = tCls.getMethod(getMethodName, new Class[]{});
				 Object value = getMethod.invoke(cdto, new Object[]{});
				 if(value != null){
					 String setVal = cachedService.getSearchOptionField(orgId, entry.getKey(), (String) value);
					 setMethodName =  "set" + entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
					 getMethod = tCls.getMethod(setMethodName, new Class[]{String.class});
					 getMethod.invoke(cdto, new Object[]{setVal});	  
				 }				 
			}  
		}
	}
	 /**设置扩展表查询条件*/
    public Map<String, Object> getMultiDefinedSearchParam(CustFollowDto custFollowDto,List<String> multiList) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	Class<?> clazz = custFollowDto.getClass().getSuperclass();
    	List<String> fieldCodes = new ArrayList<String>();
    	List<String> fieldDatas = new ArrayList<String>();
    	String getName;
    	String setName;
    	Method getMethod;
    	Method setMethod;
    	for(String fieldCode : multiList){
    		if(fieldCode.equals(AppConstant.SEARCH_LABEL) && custFollowDto.getLabels() != null && custFollowDto.getLabels().length > 0){
				fieldCodes.add(AppConstant.SEARCH_LABEL);
				fieldDatas.addAll(Arrays.asList(custFollowDto.getLabels()));
				custFollowDto.setLabels(null);
				continue;
    		}
    		getName =  "get" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
    		getMethod = clazz.getDeclaredMethod(getName);
    		Object val = getMethod.invoke(custFollowDto);
    		if(val != null && StringUtils.isNotBlank(val.toString())){
    			fieldCodes.add(fieldCode);
    			fieldDatas.addAll(Arrays.asList(val.toString().split(",")));
    			setName =  "set" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
    			setMethod = clazz.getDeclaredMethod(setName, String.class);
    			setMethod.invoke(custFollowDto, "");
    		}
    	}
    	if(fieldCodes.size() > 0){
    		map.put("fieldCodes", fieldCodes);
    		map.put("fieldDatas", fieldDatas);
    		map.put("orgId", custFollowDto.getOrgId());
    	}
    	return map;
    }

	public List<RestDto> getCustFollowRightByMyComListPage(RestDto restDto) throws Exception {
		List<RestDto> custs = new ArrayList<RestDto>();
    	List<String> cids = new ArrayList<String>();
    	if(restDto.getState() != null && 
    			restDto.getState() == 1 && 
    			StringUtils.isNotBlank(restDto.getQueryText()) && 
    			(restDto.getQueryType().equals("3") || restDto.getQueryType().equals("2"))){
    		if(restDto.getQueryType().equals("3")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(restDto.getOrgId(), restDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(restDto.getOrgId(), restDto.getQueryText());
    		}
    		if(cids.size() == 0) return custs;
    		restDto.setResIds(cids);
    		restDto.setQueryText(null);
    	}
    	Map<String, Object> paramMap = getMultiDefinedSearchParam(restDto, Arrays.asList(AppConstant.SEARCH_LABEL));
		if(paramMap.size() > 0){
			if(cids.size() > 0) paramMap.put("custIds", cids);
			List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
			if(custIds.size() > 0){
				restDto.setResIds(custIds);
			}else{
				return custs;
			}
		}
    	if (StringUtils.isNotBlank(restDto.getQueryText()) && "3".equals(restDto.getQueryType()) && (restDto.getState() == null || restDto.getState() == 0)) {
    		custs = custFollowMapper.findCustFollowRightByMyComWithPhoneListPage(restDto);
        } else {
        	custs = custFollowMapper.findCustFollowRightByMyComListPage(restDto);
        }
    	return custs;
	}
	
	public void multiDefinedShowChange(List<CustFollowDto> list,List<String> multiList, List<String> singleList,String orgId)  throws Exception {
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
		for(CustFollowDto cust : list){
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
	
	public List<String> getShowCustIds(List<CustFollowDto> list){
    	List<String> custIds = new ArrayList<String>();
    	for(CustFollowDto dto : list) custIds.add(dto.getResCustId());
    	return custIds;
    }
	
}
