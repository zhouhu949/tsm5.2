package com.qftx.tsm.cust.service;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dao.OrgGroupMapper;
import com.qftx.base.auth.dao.OrgMapper;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.Page;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.SysRunException;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.contract.bean.ContractOrderBean;
import com.qftx.tsm.contract.dao.ContractOrderMapper;
import com.qftx.tsm.cust.bean.BlackListBean;
import com.qftx.tsm.cust.bean.CustDefinedDataBean;
import com.qftx.tsm.cust.bean.CustLabelCodeDataBean;
import com.qftx.tsm.cust.bean.LinkmanDefinedDataBean;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResCustInfoDetailBean;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.cust.bean.TsmCustGuideProc;
import com.qftx.tsm.cust.dao.BlackListBeanMapper;
import com.qftx.tsm.cust.dao.CustDefinedDataMapper;
import com.qftx.tsm.cust.dao.CustLabelCodeDataMapper;
import com.qftx.tsm.cust.dao.CustOptorMapper;
import com.qftx.tsm.cust.dao.LinkmanDefinedDataMapper;
import com.qftx.tsm.cust.dao.ResCustInfoDetailMapper;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.dao.ResCustinfoLogMapper;
import com.qftx.tsm.cust.dao.ResOptorMapper;
import com.qftx.tsm.cust.dao.ResourceGroupMapper;
import com.qftx.tsm.cust.dao.TsmCustGuideMapper;
import com.qftx.tsm.cust.dao.TsmCustGuideProcMapper;
import com.qftx.tsm.cust.dto.CompanyResDto;
import com.qftx.tsm.cust.dto.CustDto;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.thread.AssginCustThread;
import com.qftx.tsm.cust.thread.ChangeLosingThread;
import com.qftx.tsm.cust.thread.CustGiveUpThread;
import com.qftx.tsm.cust.thread.CustSilentOrWeekThread;
import com.qftx.tsm.cust.thread.CustTransferThread;
import com.qftx.tsm.cust.thread.QuhuiThread;
import com.qftx.tsm.cust.thread.QuhuiYxThread;
import com.qftx.tsm.cust.thread.ScreenCustGiveUpThread;
import com.qftx.tsm.follow.bean.CustFollowBean;
import com.qftx.tsm.follow.dao.CustFollowMapper;
import com.qftx.tsm.follow.dao.CustSaleChanceMapper;
import com.qftx.tsm.imp.dto.ImportRepeatDto;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.log.service.LogContactDayDataService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.log.util.ContactUtil;
import com.qftx.tsm.main.dao.ContactDayDataMapper;
import com.qftx.tsm.main.service.ContactDayDataService;
import com.qftx.tsm.main.service.MainInfoService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dao.OptionMapper;
import com.qftx.tsm.plan.facade.PlanFactService;
import com.qftx.tsm.plan.facade.enu.PlanResCR;
import com.qftx.tsm.report.dto.CustomReportDoubleDto;
import com.qftx.tsm.report.dto.CustomReportDoubleShowDto;
import com.qftx.tsm.report.dto.CustomReportSingleDto;
import com.qftx.tsm.report.dto.ResCustReportDto;
import com.qftx.tsm.report.dto.TeamCustStatusLayoutDto;
import com.qftx.tsm.report.service.RankingReportService;
import com.qftx.tsm.report.service.ResourceReportService;
import com.qftx.tsm.report.service.SilenceCustService;
import com.qftx.tsm.sys.bean.CustFieldSet;

/**
 * 资源-客户 服务类
 *
 * @author: lixing
 * @since: 2015年11月11日 上午9:25:17
 * @history: 4.0
 */
@Service
public class ResCustInfoService {
    private static final Logger logger = Logger.getLogger(ResCustInfoService.class);

    /**
     * 客户操作线程池，并发5个线程
     */
//	public static ThreadPoolExecutor RES_OPT_POOL = new ThreadPoolExecutor(5, 5, 3, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(100000));

    private static final String DELIVERY_CUST_SQL = "UPDATE TSM_RES_CUST_INFO SET UPDATE_ACC = ?, UPDATE_DATE = now(),OWNER_ACC = ?, OWNER_START_DATE = ?,SOURCE = 2 , COMMON_ACC = NULL,OWNER_ACTION_DATE=NULL,ACTION_DATE=NULL,AMOYTOCUSTOMER_DATE=now() WHERE ORG_ID = ? AND RES_CUST_ID = ?";
    private static final String CLEAR_CALLCOUNT_SQL = "DELETE FROM TSM_CALL_COUNT_CUST WHERE ORG_ID = ? AND CUST_ID = ?";
    private static final String CREATE_CUST_OPTOR_SQL = "INSERT INTO TSM_CUST_OPTOR (CUST_OPTOR_ID, CUST_ID, TYPE,TRANSFER_ACC, TRANSFERED_ACC, SALE_PROCESS_ID, OPTOR_RES_DATE, OPTOER_ACC, OWNER_ACC,ORG_ID,OWNER_NAME,REASON) VALUES (?,?,14,?,?,?,now(),?,?,?,?,?);";
    private static final String CLEAN_CUST_SALE_CHANCE = "UPDATE TSM_SALE_CHANCE SET IS_DEL = 1,UPDATE_DATE = NOW() WHERE ORG_ID = ? AND CUST_ID = ?";
    @Autowired
    private ResCustInfoMapper resCustInfoMapper;
    @Autowired
    private ResourceGroupMapper resourceGroupMapper;
    @Autowired
    private CustOptorMapper custOptorMapper;
    @Autowired
    private ResCustInfoDetailMapper resCustInfoDetailMapper;
    @Autowired
    private CustFollowMapper custFollowMapper;
    @Autowired
    private ResCustinfoLogMapper resCustInfoLogMapper;
    @Autowired
    private TsmCustGuideMapper custGuideMapper;
    @Autowired
    private MainInfoService mainInfoService;
    @Autowired
    private LogCustInfoService logCustInfoService;
    @Autowired
    private SilenceCustService silenceCustService;
    @Autowired
    private PlanFactService planFactService;
    @Autowired
    private CachedService cachedService;
    @Autowired
    private ComResourceGroupService comReesourceGroupService;
    @Autowired
    private TsmCustGuideProcMapper tsmCustGuideProcMapper;
    @Autowired
    private ResOptorMapper resOptorMapper;
    @Autowired
    private OptionMapper optionMapper;
    @Autowired
    private ResCustEventService resCustEventService;
    @Autowired
    private RankingReportService rankingReportService;
    @Autowired
    private ContactDayDataService contactDayDataService;
    @Autowired
    private ComResourceService comResourceService;
    @Autowired
    private LogBatchInfoService logBatchInfoService;
    @Autowired
    private ContactDayDataMapper contactDayDataMapper;
    @Autowired
    private BlackListBeanMapper blackListBeanMapper;
    @Autowired
    private LogUserOperateService logUserOperateService;
    @Autowired
    private CustDefinedDataMapper custDefinedDataMapper;
    @Resource
    transient private JdbcTemplate jdbcTemplate;
    @Resource
    private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private ResourceReportService resourceReportService;
	@Autowired
	private ContractOrderMapper contractOrderMapper;
	@Autowired
	private LogContactDayDataService logContactDayDataService;
	@Autowired
	private CustSaleChanceMapper custSaleChanceMapper;
	@Autowired
	private LinkmanDefinedDataMapper linkmanDefinedDataMapper;
	@Autowired
	private CustLabelCodeDataMapper custLabelCodeDataMapper;
	@Autowired
	private OrgMapper orgMapper;
	@Autowired
	private OrgGroupMapper orgGroupMapper;
	@Autowired
	private ComResourceGroupService comResourceGroupService;
   /* @Resource
	private ProgressBarService progressBarService;*/

    public List<ResCustInfoBean> find() {
        return resCustInfoMapper.find();
    }

    public ResCustInfoBean getCustInfoByOrgIdAndPk(String orgId, String custId) throws Exception {
        ResCustInfoBean bean = resCustInfoMapper.getByPrimaryKey(orgId, custId);
        if(bean != null && hasMultiDef(bean.getState(), orgId)){
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("orgId", orgId);
	        map.put("custIds", Arrays.asList(custId));
	        List<CustDefinedDataBean> datas = custDefinedDataMapper.findCustDefinedShowDatas(map);
	        Map<String, String> definedMap = new HashMap<String, String>();
	        for(CustDefinedDataBean cdb : datas){
	        	if(definedMap.containsKey(cdb.getFieldCode())){
	        		definedMap.put(cdb.getFieldCode(),definedMap.get(cdb.getFieldCode())+","+cdb.getFieldData());
	        	}else{
	        		definedMap.put(cdb.getFieldCode(), cdb.getFieldData());
	        	}
	        }
	        if(definedMap.size() > 0){
	        	Class<?> clazz = bean.getClass();
	        	Method setMethod;
	        	String setName;
	        	for (String key : definedMap.keySet()) {
	        		setName = "set" + key.substring(0,1).toUpperCase()+key.substring(1);
	        		setMethod = clazz.getDeclaredMethod(setName, String.class);
	        		setMethod.invoke(bean, definedMap.get(key));
				}
	        }
        }
        return bean;
    }

    public void create(ResCustInfoBean custInfoBean) throws Exception {
    	Map<String, String[]> multiDefMap = getInsertMultiDefined(custInfoBean);
    	if(multiDefMap.size() > 0) defWrite(custInfoBean.getResCustId(), custInfoBean.getOrgId(),0, multiDefMap);
        resCustInfoMapper.insert(custInfoBean);
    }

    public void insertBatch(List<ResCustInfoBean> list) {
        resCustInfoMapper.insertBatch(list);
    }

    public void createComRes(ResCustInfoBean custInfoBean, ResCustInfoDetailBean detailBean, String isSaveDetail) throws Exception {
    	Map<String, String[]> multiDefMap = getInsertMultiDefined(custInfoBean);
    	Map<String, String[]> multiDefMap1 = getInsertMultiDefined1(detailBean);
     	if(multiDefMap.size() > 0) defWrite(custInfoBean.getResCustId(), custInfoBean.getOrgId(),0, multiDefMap);
     	if(multiDefMap1.size() > 0) defWrite1(custInfoBean.getResCustId(), custInfoBean.getOrgId(),0,detailBean.getTscidId(), multiDefMap1);
    	resCustInfoMapper.insert(custInfoBean);
        if ("1".equals(isSaveDetail)) {
            resCustInfoDetailMapper.insert(detailBean);
        }
    }


	public void createCompResouce(ResCustInfoBean custInfoBean, ResCustInfoDetailBean detailBean) {
        custInfoBean.setTelphone(detailBean.getTelphone());
        custInfoBean.setMobilephone(detailBean.getTelphonebak());
        custInfoBean.setEmail(detailBean.getEmail());
        resCustInfoMapper.insert(custInfoBean);

        detailBean.setRciId(custInfoBean.getResCustId());
        detailBean.setIsDefault(1);
        detailBean.setInputtime(custInfoBean.getInputDate());
        resCustInfoDetailMapper.insert(detailBean);
    }

    public ResCustInfoDto getTeamCustByCustId(ResCustInfoDto dto) {
        Map<String, String> nameMap = cachedService.getOrgUserNames(dto.getOrgId());

        List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, dto.getOrgId());
        Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);

        List<OptionBean> wasts = cachedService.getOptionList(AppConstant.SALES_TYPE_WAST, dto.getOrgId());
        Map<String,String> losingMap = cachedService.changeOptionListToMap(wasts);
        
        ResCustInfoDto custDto = resCustInfoMapper.findTeamCustByCustId(dto);

        if (custDto != null) {
            custDto.setOwnerName(StringUtils.isNotBlank(custDto.getOwnerAcc()) ? nameMap.get(custDto.getOwnerAcc()) : null);
            custDto.setSaleProcessId(custDto.getLastOptionId());
            custDto.setSaleProcessName(StringUtils.isNotBlank(custDto.getLastOptionId()) ? saleProcMap.get(custDto.getLastOptionId()) : null);
            custDto.setLosingId(StringUtils.isNotBlank(custDto.getLosingId()) ? losingMap.get(custDto.getLosingId()) : "");
        }
        return custDto;
    }
    
    /**
     * 资源列表
     *
     * @param custInfoDto
     * @return
     * @throws Exception 
     * @create 2015年11月13日 下午5:28:29 lixing
     * @history
     */
    public List<ResCustInfoDto> getMyResCustListPage(ResCustInfoDto custInfoDto,List<String> multiList) throws Exception {
    	List<ResCustInfoDto> rests = new ArrayList<ResCustInfoDto>();
    	List<String> cids = new ArrayList<String>();
    	if(custInfoDto.getState() != null && 
    			custInfoDto.getState() == 1 && 
    			StringUtils.isNotBlank(custInfoDto.getQueryText()) && 
    			(custInfoDto.getQueryType().equals("mobilephone") || custInfoDto.getQueryType().equals("mainLinkman"))){
    		if(custInfoDto.getQueryType().equals("mobilephone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}
    		if(cids.size() == 0) return rests;
    		custInfoDto.setCustIds(cids);
    		custInfoDto.setQueryText(null);
    	}
    	
    	if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(custInfoDto, multiList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(custIds.size() > 0){
					custInfoDto.setCustIds(custIds);
				}else{
					return rests;
				}
			}
		}
		if (StringUtils.isNotBlank(custInfoDto.getQueryText()) && custInfoDto.getQueryType().equals("mobilephone") && (custInfoDto.getState() == null || custInfoDto.getState() == 0)) {
			rests = resCustInfoMapper.findMyResCustPhoneListPage(custInfoDto);
		} else {
			rests = resCustInfoMapper.findMyResCustListPage(custInfoDto);
		}
		return rests;
    }
    
    public List<ResCustInfoDto> getMyCommonCustListPage(ResCustInfoDto custInfoDto) throws Exception{
    	List<ResCustInfoDto> custs = new ArrayList<ResCustInfoDto>();
    	List<String> cids = new ArrayList<String>();
    	if(custInfoDto.getState() != null && 
    			custInfoDto.getState() == 1 && 
    			StringUtils.isNotBlank(custInfoDto.getQueryText()) && 
    			(custInfoDto.getQueryType().equals("3") || custInfoDto.getQueryType().equals("2"))){
    		if(custInfoDto.getQueryType().equals("3")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}
    		if(cids.size() == 0) return custs;
    		custInfoDto.setCustIds(cids);
    		custInfoDto.setQueryText(null);
    	}
    	Map<String, Object> paramMap = getMultiDefinedSearchParam(custInfoDto, Arrays.asList(AppConstant.SEARCH_LABEL));
		if(paramMap.size() > 0){
			if(cids.size() > 0) paramMap.put("custIds", cids);
			List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
			if(custIds.size() > 0){
				custInfoDto.setCustIds(custIds);
			}else{
				return custs;
			}
		}
    	if (StringUtils.isNotBlank(custInfoDto.getQueryText()) && "3".equals(custInfoDto.getQueryType()) && (custInfoDto.getState() == null || custInfoDto.getState() == 0)) {
    		custs = resCustInfoMapper.findMyCommonCustPhoneListPage(custInfoDto);
        } else {
        	custs = resCustInfoMapper.findMyCommonCustListPage(custInfoDto);
        }
    	return custs;
    }

    /**
     * 意向客户列表
     *
     * @param custInfoDto
     * @return
     * @throws Exception 
     * @create 2015年11月13日 下午5:28:45 lixing
     * @history
     */
    public List<ResCustInfoDto> getMyCustListPage(ResCustInfoDto custInfoDto,List<String> multiList) throws Exception {
        List<ResCustInfoDto> custs = new ArrayList<ResCustInfoDto>();
        List<String> cids = new ArrayList<String>();
        if(custInfoDto.getNoteType() != null && custInfoDto.getNoteType().equals("10") && (custInfoDto.getIds() == null || custInfoDto.getIds().size() == 0)) return custs;
    	
        if(custInfoDto.getState() != null && 
    			custInfoDto.getState() == 1 && 
    			StringUtils.isNotBlank(custInfoDto.getQueryText()) && 
    			(custInfoDto.getQueryType().equals("mobilephone") || custInfoDto.getQueryType().equals("mainLinkman"))){
    		if(custInfoDto.getQueryType().equals("mobilephone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}
    		if(cids.size() == 0) return custs;
    		custInfoDto.setCustIds(cids);
    		custInfoDto.setQueryText(null);
    	}
    	
        if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(custInfoDto, multiList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(custIds.size() > 0){
					custInfoDto.setCustIds(custIds);
				}else{
					return custs;
				}
			}
		}
        
        if(custInfoDto.getIds() != null && custInfoDto.getIds().size() > 0){
        	if(custInfoDto.getCustIds() != null && custInfoDto.getCustIds().size() > 0){
        		List<String> searchIds = new ArrayList<String>();
        		Map<String, String> mm = new HashMap<String, String>();
        		for(String i1 : custInfoDto.getCustIds()) mm.put(i1, i1);
        		for(String i2 : custInfoDto.getIds()){
        			if(mm.containsKey(i2)) searchIds.add(i2);
        		}
        		if(searchIds.size() == 0) return custs;
        		custInfoDto.setCustIds(searchIds);
        	}else{
        		custInfoDto.setCustIds(custInfoDto.getIds());
        	}
        }
        
        if (StringUtils.isNotBlank(custInfoDto.getQueryText()) && custInfoDto.getQueryType().equals("mobilephone") && (custInfoDto.getState() == null || custInfoDto.getState() == 0)) {
        	custs = resCustInfoMapper.findMyCustPhoneListPage(custInfoDto);
        } else {
        	custs = resCustInfoMapper.findMyCustListPage(custInfoDto);
        }
        return custs;
    }

    /**
     * 签约客户列表
     *
     * @param custInfoDto
     * @return
     * @throws Exception 
     * @create 2015年11月16日 上午11:42:33 lixing
     * @history
     */
    public List<ResCustInfoDto> getSignCustListPage(ResCustInfoDto custInfoDto,List<String> multiList) throws Exception {
    	List<ResCustInfoDto> custs = new ArrayList<ResCustInfoDto>();
    	List<String> cids = new ArrayList<String>();
    	if(custInfoDto.getState() != null && 
    			custInfoDto.getState() == 1 && 
    			StringUtils.isNotBlank(custInfoDto.getQueryText()) && 
    			(custInfoDto.getQueryType().equals("mobilephone") || custInfoDto.getQueryType().equals("mainLinkman"))){
    		if(custInfoDto.getQueryType().equals("mobilephone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}
    		if(cids.size() == 0) return custs;
    		custInfoDto.setCustIds(cids);
    		custInfoDto.setQueryText(null);
    	}
    	if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(custInfoDto, multiList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(custIds.size() > 0){
					custInfoDto.setCustIds(custIds);
				}else{
					return custs;
				}
			}
		}
		if (StringUtils.isNotBlank(custInfoDto.getQueryText()) && "mobilephone".equals(custInfoDto.getQueryType()) && (custInfoDto.getState() == null || custInfoDto.getState() == 0)) {
			custs = resCustInfoMapper.findSignCustPhoneListPage(custInfoDto);
		} else {
			custs = resCustInfoMapper.findSignCustListPage(custInfoDto);
		}
		List<String> custIds = new ArrayList<String>();
		Map<String, BigDecimal> moneyMap = new HashMap<String, BigDecimal>();
		for(ResCustInfoDto rci : custs) custIds.add(rci.getResCustId());
		if(custIds.size() > 0){
			List<ContractOrderBean> orders = contractOrderMapper.findTotalMoneyByCustIds(custInfoDto.getOrgId(), custIds);
			for(ContractOrderBean order : orders) moneyMap.put(order.getCustId(), order.getMoney());
			for(ResCustInfoDto rci : custs){
				if(moneyMap.containsKey(rci.getResCustId())){
					rci.setOrderCountMonty(moneyMap.get(rci.getResCustId()));
				}else{
					rci.setOrderCountMonty(BigDecimal.valueOf(0));
				}
			}
		}
		return custs;
    }
    
    /**
     * 签约客户列表
     *
     * @param custInfoDto
     * @return
     * @throws Exception 
     * @create 2015年11月16日 上午11:42:33 lixing
     * @history
     */
    public List<ResCustInfoDto> getQypaiSignCustListPage(ResCustInfoDto custInfoDto,List<String> multiList) throws Exception {
    	List<ResCustInfoDto> custs = new ArrayList<ResCustInfoDto>();
    	List<String> cids = new ArrayList<String>();
    	if(custInfoDto.getState() != null && 
    			custInfoDto.getState() == 1 && 
    			StringUtils.isNotBlank(custInfoDto.getQueryText()) && 
    			(custInfoDto.getQueryType().equals("mobilephone") || custInfoDto.getQueryType().equals("mainLinkman"))){
    		if(custInfoDto.getQueryType().equals("mobilephone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}
    		if(cids.size() == 0) return custs;
    		custInfoDto.setCustIds(cids);
    		custInfoDto.setQueryText(null);
    	}
    	if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(custInfoDto, multiList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(custIds.size() > 0){
					custInfoDto.setCustIds(custIds);
				}else{
					return custs;
				}
			}
		}
		if (StringUtils.isNotBlank(custInfoDto.getQueryText()) && "mobilephone".equals(custInfoDto.getQueryType()) && (custInfoDto.getState() == null || custInfoDto.getState() == 0)) {
			custs = resCustInfoMapper.findQupaiSignCustPhoneListPage(custInfoDto);
		} else {
			custs = resCustInfoMapper.findQupaiSignCustListPage(custInfoDto);
		}
		List<String> custIds = new ArrayList<String>();
		Map<String, BigDecimal> moneyMap = new HashMap<String, BigDecimal>();
		for(ResCustInfoDto rci : custs) custIds.add(rci.getResCustId());
		if(custIds.size() > 0){
			List<ContractOrderBean> orders = contractOrderMapper.findTotalMoneyByCustIds(custInfoDto.getOrgId(), custIds);
			for(ContractOrderBean order : orders) moneyMap.put(order.getCustId(), order.getMoney());
			for(ResCustInfoDto rci : custs){
				if(moneyMap.containsKey(rci.getResCustId())){
					rci.setOrderCountMonty(moneyMap.get(rci.getResCustId()));
				}else{
					rci.setOrderCountMonty(BigDecimal.valueOf(0));
				}
			}
		}
		return custs;
    }

    /**
     * 沉默客户列表
     *
     * @param custInfoDto
     * @return
     * @throws Exception 
     * @create 2015年12月4日 下午5:22:11 lixing
     * @history
     */
    public List<ResCustInfoDto> getSilentCustListPage(ResCustInfoDto custInfoDto) throws Exception {
    	List<ResCustInfoDto> custs = new ArrayList<ResCustInfoDto>();
    	List<String> cids = new ArrayList<String>();
    	if(custInfoDto.getState() != null && 
    			custInfoDto.getState() == 1 && 
    			StringUtils.isNotBlank(custInfoDto.getQueryText()) && 
    			(custInfoDto.getQueryType().equals("3") || custInfoDto.getQueryType().equals("2"))){
    		if(custInfoDto.getQueryType().equals("3")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}
    		if(cids.size() == 0) return custs;
    		custInfoDto.setCustIds(cids);
    		custInfoDto.setQueryText(null);
    	}
    	
    	Map<String, Object> paramMap = getMultiDefinedSearchParam(custInfoDto, Arrays.asList(AppConstant.SEARCH_LABEL));
		if(paramMap.size() > 0){
			if(cids.size() > 0) paramMap.put("custIds", cids);
			List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
			if(custIds.size() > 0){
				custInfoDto.setCustIds(custIds);
			}else{
				return custs;
			}
		}
    	
    	if (StringUtils.isNotBlank(custInfoDto.getQueryText()) && "3".equals(custInfoDto.getQueryType()) && (custInfoDto.getState() == null || custInfoDto.getState() == 0)) {
			return resCustInfoMapper.findSilentCustPhoneListPage(custInfoDto);
		} else {
			return resCustInfoMapper.findSilentCustListPage(custInfoDto);
		}
    }

    /**
     * 流失客户列表
     *
     * @param custInfoDto
     * @return
     * @throws Exception 
     * @create 2015年12月17日 下午6:56:32 lixing
     * @history
     */
    public List<ResCustInfoDto> getLosingCustListPage(ResCustInfoDto custInfoDto) throws Exception {
    	List<ResCustInfoDto> custs = new ArrayList<ResCustInfoDto>();
    	List<String> cids = new ArrayList<String>();
    	if(custInfoDto.getState() != null && 
    			custInfoDto.getState() == 1 && 
    			StringUtils.isNotBlank(custInfoDto.getQueryText()) && 
    			(custInfoDto.getQueryType().equals("3") || custInfoDto.getQueryType().equals("2"))){
    		if(custInfoDto.getQueryType().equals("3")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}
    		if(cids.size() == 0) return custs;
    		custInfoDto.setCustIds(cids);
    		custInfoDto.setQueryText(null);
    	}
    	
    	Map<String, Object> paramMap = getMultiDefinedSearchParam(custInfoDto, Arrays.asList(AppConstant.SEARCH_LABEL));
		if(paramMap.size() > 0){
			if(cids.size() > 0) paramMap.put("custIds", cids);
			List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
			if(custIds.size() > 0){
				custInfoDto.setCustIds(custIds);
			}else{
				return custs;
			}
		}
    	
    	if (StringUtils.isNotBlank(custInfoDto.getQueryText()) && "3".equals(custInfoDto.getQueryType()) && (custInfoDto.getState() == null || custInfoDto.getState() == 0)) {
			return resCustInfoMapper.findLosingCustPhoneListPage(custInfoDto);
		} else {
			return resCustInfoMapper.findLosingCustListPage(custInfoDto);
		}
    }

    public List<ResCustInfoDto> getManageLosingCustListPage(ResCustInfoDto custInfoDto,List<String> multiList) throws Exception {
    	List<ResCustInfoDto> custs = new ArrayList<ResCustInfoDto>();
    	List<String> cids = new ArrayList<String>();
    	if(custInfoDto.getState() != null && 
    			custInfoDto.getState() == 1 && 
    			StringUtils.isNotBlank(custInfoDto.getQueryText()) && 
    			(custInfoDto.getQueryType().equals("mobilephone") || custInfoDto.getQueryType().equals("mainLinkman"))){
    		if(custInfoDto.getQueryType().equals("mobilephone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}
    		if(cids.size() == 0) return custs;
    		custInfoDto.setCustIds(cids);
    		custInfoDto.setQueryText(null);
    	}
    	
    	if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(custInfoDto, multiList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(custIds.size() > 0){
					custInfoDto.setCustIds(custIds);
				}else{
					return custs;
				}
			}
		}
    	if (StringUtils.isNotBlank(custInfoDto.getQueryText()) && "mobilephone".equals(custInfoDto.getQueryType()) && (custInfoDto.getState() == null || custInfoDto.getState() == 0)) {
    		custs = resCustInfoMapper.findManageLosingCustPhoneListPage(custInfoDto);
		} else {
			custs = resCustInfoMapper.findManageLosingCustListPage(custInfoDto);
		}
    	return custs;
    }
    
    /**
     * 全部客户分页列表
     *
     * @param custInfoDto
     * @return
     * @throws Exception 
     * @create 2015年11月17日 下午6:42:19 lixing
     * @history
     */
    public List<ResCustInfoDto> getAllTypeCustListPage(ResCustInfoDto custInfoDto,List<String> multiList) throws Exception {
	    	List<ResCustInfoDto> custs = new ArrayList<ResCustInfoDto>();
	    	List<String> cids = new ArrayList<String>();
	    	if(custInfoDto.getState() != null && 
	    			custInfoDto.getState() == 1 && 
	    			StringUtils.isNotBlank(custInfoDto.getQueryText()) && 
	    			(custInfoDto.getQueryType().equals("mobilephone") || custInfoDto.getQueryType().equals("mainLinkman"))){
	    		if(custInfoDto.getQueryType().equals("mobilephone")){
	    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(custInfoDto.getOrgId(), custInfoDto.getQueryText());
	    		}else{
	    			cids = resCustInfoDetailMapper.findLinkmanIds(custInfoDto.getOrgId(), custInfoDto.getQueryText());
	    		}
	    		if(cids.size() == 0) return custs;
	    		custInfoDto.setCustIds(cids);
	    		custInfoDto.setQueryText(null);
	    	}
	    	
	    	if(custInfoDto != null){
    	        if(multiList != null && multiList.size() > 0){
    				Map<String, Object> paramMap = getMultiDefinedSearchParam(custInfoDto, multiList);
    				if(paramMap.size() > 0){
    					if(cids.size() > 0) paramMap.put("custIds", cids);
    					List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
    					if(custIds.size() > 0){
    						custInfoDto.setCustIds(custIds);
    					}else{
    						return custs;
    					}
    				}
    			}
	    		if (StringUtils.isNotBlank(custInfoDto.getQueryText()) && "mobilephone".equals(custInfoDto.getQueryType()) && (custInfoDto.getState() == null || custInfoDto.getState() == 0)) {
	    			custs = resCustInfoMapper.findAllTypeCustPhoneListPage(custInfoDto);
	    		} else {
	    			custs = resCustInfoMapper.findAllTypeCustListPage(custInfoDto);
	    		}
	    	}
    		return custs;
    }

    /**
     * 设置/取消设置优先级
     *
     * @param updateAcc
     * @param isPrecedence
     * @param ids
     * @create 2015年11月13日 下午7:46:43 lixing
     * @history
     */
    public void setPrecedenceBatch(String updateAcc, Integer isPrecedence, List<String> ids, ShiroUser user) {
        resCustInfoMapper.setPrecedenceBatch(updateAcc, isPrecedence, ids, user.getOrgId());
    }

    /**
     * 客户再分配
     *
     * @param ids
     * @param transAcc
     * @param transName
     * @param user
     * @create 2015年11月18日 上午10:16:23 lixing
     * @history
     */
    public void againAssginCust(List<String> ids, String transAcc, String transName, ShiroUser user, String isClean,String module,String poolType) {
        Date upDate = new Date();
        // 操作资源表
        ResCustInfoDto custInfoDto = new ResCustInfoDto();
        custInfoDto.setUpdateDate(upDate);
        custInfoDto.setUpdateAcc(user.getAccount());
        custInfoDto.setOwnerAcc(transAcc);
        custInfoDto.setOwnerStartDate(upDate);
        custInfoDto.setIds(ids);
//			custInfoDto.setResCustId(custId);
        custInfoDto.setIsDel(0);
        custInfoDto.setStatus(2);
        custInfoDto.setFilterType(0);
        custInfoDto.setOpreateType(13);
        custInfoDto.setIsConcat(0);
        custInfoDto.setType(1);
        custInfoDto.setOrgId(user.getOrgId());
        custInfoDto.setIsClean(Integer.parseInt(isClean));
        custInfoDto.setLifeCode(SysBaseModelUtil.getModelId());
        custInfoDto.setSource(2);
        resCustInfoMapper.updateByIds(custInfoDto);
        taskExecutor.execute(new AssginCustThread(ids, transAcc, transName, user, isClean, upDate,module,poolType));
    }

    /**
     * 客户放弃原因列表
     *
     * @param user
     * @create 2017年5月31日 上午11:07:26 wfg
     * @history
     */
    public List<String> custGiveUpReasonList(ShiroUser user) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orgId",user.getOrgId());
        return resCustInfoMapper.findCustGiveUpReasonList(params);
    }

    /**
     * 客户交接、放弃
     *
     * @param moveName
     * @param moveAcc
     * @param ids
     * @create 2015年11月13日 下午7:47:06 lixing
     * @history
     */
    public void modifyBatchCust(String moveName, String moveAcc, List<String> ids, String reason, ShiroUser user, Short operateType) {
        String logAct = user.getAccount();
        String orgId = user.getOrgId();
        String name = user.getName();
        // 客户交接
        if (StringUtils.isNotBlank(moveAcc)) {
            List<String> custIds = new ArrayList<String>();
            for (String resCustIdStr : ids) {
                String resCustId = resCustIdStr.split("_")[0];
                String ownerAcc = resCustIdStr.split("_")[2];
                if (!ownerAcc.equals(moveAcc)) {
                        custIds.add(resCustId);
                }
            }
            // 更新所有者帐号
            if (custIds.size() > 0) {
                resCustInfoMapper.updateMoveCust(logAct, moveAcc, custIds, orgId);
                resCustInfoMapper.cleanCallCustCountByCustId(custIds, orgId);
            }
            //记录日志
            taskExecutor.execute(new CustTransferThread(orgId, ids, moveAcc,moveName, logAct, reason,name));
        } else {// 放弃客户
            giveUp(user, ids, operateType, reason,null);
        }
    }


    /**
     * 放弃客户
     *
     * @param user
     * @param ids
     * @param operateType
     * @param reason
     * @create 2016年6月28日 上午11:06:06 lixing
     * @history
     */
    public void giveUp(ShiroUser user, List<String> ids, Short operateType, String reason,String module) {
        // 更新所有者为空和状态为4
        List<String> custIds = new ArrayList<String>();
        for (String idStr : ids) {
            custIds.add(idStr.split("_")[0]);
        }
    	resCustInfoMapper.updateBatchCust(AppConstant.STATUS_4, user.getAccount(), custIds, operateType, user.getOrgId(), reason);
        resCustInfoMapper.cleanCallCustCountByCustId(custIds, user.getOrgId());
		custSaleChanceMapper.updateIsDelByCustIds(user.getOrgId(), custIds);
        //记录日志
        taskExecutor.execute(new CustGiveUpThread(ids, user,reason,operateType,module));
//        if(operateType==5){
//        	for(String resCustId: custIds){
//			ResourceReportBean resrep=new ResourceReportBean();
//			resrep.setOrgId(user.getOrgId());
//			resrep.setResCustId(resCustId);
//			resrep.setInformationError(1);
//			resourceReportService.intsertOrUpdate(resrep);
//        	}
//        }
    }

    /**
     * 号码筛查自动转公海
     * @param ids
     * @create 2018年8月14日 下午16:36:05 lub
     * @history
     */   
	public void resScreeningcustRemoveBatch(List<String> ids,String account, String orgId){
		try {
		Short operateType = AppConstant.OPREATE_TYPE5;
		String reason="号码筛查自动转公海";
		if(ids.size()>0){
			resCustInfoMapper.updateBatchCust(AppConstant.STATUS_4, account, ids, operateType, orgId, reason);
		}
		taskExecutor.execute(new ScreenCustGiveUpThread(ids, account,orgId,reason,operateType,reason));
		
		} catch (Exception e) {
			logger.error("号码筛查自动转公海失败", e);
		}
	}
    
    public void modifyBatchCustByQuHui(String loginAcc, List<String> ids, String orgId,String isShare,String name,String module,ShiroUser user) {
        List<String> qhIds = resCustInfoMapper.findQuHuiIds(orgId, ids,isShare);
        if(qhIds.size() > 0){
        	// 修改状态
        	ResCustInfoDto upDto = new ResCustInfoDto();
        	upDto.setIds(qhIds);
        	upDto.setUpdateAcc(loginAcc);
        	upDto.setOwnerAcc(loginAcc);
        	upDto.setOrgId(orgId);
        	upDto.setLifeCode(SysBaseModelUtil.getModelId());
        	resCustInfoMapper.modifyQuHui(upDto);
        	//记录日志
        	taskExecutor.execute(new QuhuiThread(loginAcc, qhIds, orgId,name,module,user));
        }
    }
    
    public void modifyBatchCustByQuHuiYx(String loginAcc, List<String> ids, String orgId,String isShare,String name,String module,ShiroUser user) {
    	List<String> qhIds = resCustInfoMapper.findQuHuiIds(orgId, ids,isShare);
        if(qhIds.size() > 0){
        	// 修改状态
        	ResCustInfoDto upDto = new ResCustInfoDto();
        	upDto.setIds(qhIds);
        	upDto.setUpdateAcc(loginAcc);
        	upDto.setOwnerAcc(loginAcc);
        	upDto.setOrgId(orgId);
        	upDto.setLifeCode(SysBaseModelUtil.getModelId());
        	upDto.setSource(3);
        	String option="";
    		List<OptionBean> saleProcessList = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, orgId);
    		if (saleProcessList != null && saleProcessList.size() > 0) {
    			for (OptionBean optionBean : saleProcessList) {
    				if(optionBean.getIsDefault()==1){
    					option=optionBean.getOptionlistId();	
    				};
    				
        			if("".endsWith(option)){
        				option=saleProcessList.get(0).getOptionlistId();	
        			}
        		}
    			}

    		upDto.setLastOptionId(option);
    		List<DataDictionaryBean> data1 = cachedService.getDirList(AppConstant.DATA_40032, orgId);
    		List<DataDictionaryBean> data2 = cachedService.getDirList(AppConstant.DATA_50055, orgId);

            if ((!data1.isEmpty() && "1".equals(data1.get(0).getDictionaryValue()))&&(!data2.isEmpty() && "1".equals(data2.get(0).getIsOpen()))) {
        		int dates= Integer.valueOf(data2.get(0).getDictionaryValue());
                Calendar calen = Calendar.getInstance();
                calen.add(Calendar.DAY_OF_YEAR,dates);
                Date nextFollowDate=calen.getTime();
                upDto.setNextFollowDate(nextFollowDate);   
            }
        	resCustInfoMapper.modifyQuHuiYx(upDto);
        	//记录日志
        	taskExecutor.execute(new QuhuiYxThread(loginAcc, qhIds, orgId,name,option,module,user));
        }
    }

    /**
     * 设置流失客户
     *
     * @param custInfoDto
     * @create 2015年11月17日 下午2:26:49 lixing
     * @history
     */
    public void changeToLosing(ResCustInfoDto custInfoDto, ShiroUser user,String module) {
        resCustInfoMapper.updateByIds(custInfoDto);
        taskExecutor.execute(new ChangeLosingThread(custInfoDto, user,module));
    }

    /**
     * 沉默/唤醒
     *
     * @param custInfoDto
     * @create 2015年11月17日 下午2:26:31 lixing
     * @history
     */
    public void changeSilentStatus(ResCustInfoDto custInfoDto, ShiroUser user) {
        resCustInfoMapper.updateByIds(custInfoDto);
        taskExecutor.execute(new CustSilentOrWeekThread(custInfoDto, user));
    }

    /**
     * 删除
     *
     * @param custInfoDto
     * @create 2015年11月18日 上午10:34:14 lixing
     * @history
     */
    public void delBatchCust(ResCustInfoDto custInfoDto) {
        resCustInfoMapper.updateByIds(custInfoDto);
//        taskExecutor.execute(new CustDeleteThread(custInfoDto));
    }

    /**
     * 清空公海客户池 查询
     *
     * @param custInfoDto
     * @return
     * @create 2015年11月18日 上午11:58:31 lixing
     * @history
     */
    public List<ResCustInfoDto> getClearPoolListPage(ResCustInfoDto custInfoDto) {
        if (custInfoDto.getIsShareRes().equals("0")) {
            if (StringUtils.isNotBlank(custInfoDto.getQueryText()) && "mobilephone".equals(custInfoDto.getQueryType())
            		 && (custInfoDto.getState() == null || custInfoDto.getState() == 0)) {
                return resCustInfoMapper.findClearPoolPhoneListPage(custInfoDto);
            } else {
                return resCustInfoMapper.findClearPoolListPage(custInfoDto);
            }
        } else {
            if (StringUtils.isNotBlank(custInfoDto.getQueryText()) && "mobilephone".equals(custInfoDto.getQueryType())
            		 && (custInfoDto.getState() == null || custInfoDto.getState() == 0)) {
                return resCustInfoMapper.findClearPoolResPhoneListPage(custInfoDto);
            } else {
                return resCustInfoMapper.findClearPoolResListPage(custInfoDto);
            }
        }
    }

    /**
     * 取得 销售交接 分页 ID
     *
     * @param custInfoDto
     * @return
     * @create 2015年11月24日 上午9:41:27 lixing
     * @history
     */
    public List<ResCustInfoDto> getDeliveryIdsListPage(ResCustInfoDto custInfoDto) {
        return resCustInfoMapper.findDeliveryIdsListPage(custInfoDto);
    }

    /**
     * 取得个人剩余资源数
     *
     * @param ownerAcc
     * @return
     * @create 2015年11月26日 上午9:37:15 lixing
     * @history
     */
    public Integer getMyResSum(String ownerAcc, String orgId) {
        return resCustInfoMapper.findMyResSum(ownerAcc, orgId);
    }
    
    /**
     * 取得个人剩余意向数
     *
     * @param ownerAcc
     * @return
     * @create 2015年11月26日 上午9:37:15 lixing
     * @history
     */
    public Integer getMyCustSum(String ownerAcc, String orgId) {
        return resCustInfoMapper.findMyCustSum(ownerAcc, orgId);
    }

    /**
     * 修改计划日期
     *
     * @param planDate
     * @param orgId
     * @param custId
     * @create 2015年12月9日 下午1:54:05 lixing
     * @history
     */
    public void modifyPlanDate(Date planDate, String orgId, String custId) {
        resCustInfoMapper.updatePlanDate(planDate, orgId, custId);
    }

    /**
     * 延后呼叫提醒
     */
    public List<ResCustInfoBean> getDelayCallAlert(Map<String, Object> map) {
        return resCustInfoMapper.findDelayCallAlert(map);
    }

    public void modify(ResCustInfoBean custInfoBean) throws Exception {
    	Map<String, String[]> multiDefMap = getInsertMultiDefined(custInfoBean);
    	if(multiDefMap.size() > 0) defWrite(custInfoBean.getResCustId(), custInfoBean.getOrgId(),1, multiDefMap);
    	resCustInfoMapper.update2(custInfoBean);
    }
    
    // 修改表单
    public void modify2(ResCustInfoBean custInfoBean) throws Exception {
    	Map<String, String[]> multiDefMap = getInsertMultiDefined(custInfoBean);
    	if(multiDefMap.size() > 0) defWrite(custInfoBean.getResCustId(), custInfoBean.getOrgId(),1, multiDefMap);
    	//resCustInfoMapper.update(custInfoBean);
    	resCustInfoMapper.updateForm(custInfoBean);
    }

    public void defWrite(String custId,String orgId,int type,Map<String, String[]> multiDefMap){
    	if(type == 1) custDefinedDataMapper.deleteByCustId(orgId, custId);
    	List<CustDefinedDataBean> list = new ArrayList<CustDefinedDataBean>();
    	CustDefinedDataBean dataBean;
    	for(String key : multiDefMap.keySet()){
    		for(String code : multiDefMap.get(key)){
    			dataBean = new CustDefinedDataBean();
    			dataBean.setId(SysBaseModelUtil.getModelId());
    			dataBean.setOrgId(orgId);
    			dataBean.setCustId(custId);
    			dataBean.setFieldCode(key);
    			dataBean.setFieldData(code);
    			list.add(dataBean);
    		}
    	}
    	custDefinedDataMapper.insertBatch(list);
    }
    
    public void defWrite1(String custId,String orgId,int type,String linkmanId,Map<String, String[]> multiDefMap){
    	if(type == 1) linkmanDefinedDataMapper.deleteBylinkmanId(orgId, custId,linkmanId);
    	List<LinkmanDefinedDataBean> list = new ArrayList<LinkmanDefinedDataBean>();
    	LinkmanDefinedDataBean dataBean;
    	for(String key : multiDefMap.keySet()){
    		for(String code : multiDefMap.get(key)){
    			dataBean = new LinkmanDefinedDataBean();
    			dataBean.setId(SysBaseModelUtil.getModelId());
    			dataBean.setOrgId(orgId);
    			dataBean.setCustId(custId);
    			dataBean.setLinkmanId(linkmanId);
    			dataBean.setFieldCode(key);
    			dataBean.setFieldData(code);
    			list.add(dataBean);
    		}
    	}
    	linkmanDefinedDataMapper.insertBatch(list);
    }
    
    public boolean hasMultiDef(int state,String orgId){
    	boolean flg = false;
    	List<CustFieldSet> fieldSets = null;
		if (state == 1) {// 企业资源
			fieldSets = cachedService.getComFiledSet(orgId);
		} else {
			fieldSets = cachedService.getPersonFiledSet(orgId);
		}
		for(CustFieldSet field : fieldSets){
			if(field.getDataType() == 4){
				flg = true;
				break;
			}
		}
		return flg;
    }
    
    public Map<String, String[]> getInsertMultiDefined(ResCustInfoBean custInfoBean) throws Exception{
    	Map<String, String[]> map = new HashMap<String, String[]>();
    	List<CustFieldSet> fieldSets = null;
		if (custInfoBean.getState() == 1) {// 企业资源
			fieldSets = cachedService.getComFiledSet(custInfoBean.getOrgId());
		} else {
			fieldSets = cachedService.getPersonFiledSet(custInfoBean.getOrgId());
		}
		Method getMethod;
		Method setMethod;
		String getName;
    	String setName;
    	Class<?> clazz = custInfoBean.getClass();
		for(CustFieldSet field : fieldSets){
			if(field.getDataType() == 4){
				getName = "get" + field.getFieldCode().substring(0, 1).toUpperCase() + field.getFieldCode().substring(1);
				getMethod = clazz.getDeclaredMethod(getName);
				Object val = getMethod.invoke(custInfoBean);
				if(val != null){
					map.put(field.getFieldCode(), val.toString().split(","));
					setName =  "set" + field.getFieldCode().substring(0, 1).toUpperCase() + field.getFieldCode().substring(1);
	    			setMethod = clazz.getDeclaredMethod(setName, String.class);
	    			setMethod.invoke(custInfoBean, "");
				}
			}
		}
		return map;
    }
    
    public Map<String, String[]> getInsertMultiDefined1(ResCustInfoDetailBean detailBean) throws Exception{
    	Map<String, String[]> map = new HashMap<String, String[]>();
    	List<CustFieldSet> concatFieldSets = null;
		concatFieldSets = cachedService.getContactsFiledSets(detailBean.getOrgId());
		Method getMethod;
		Method setMethod;
		String getName;
    	String setName;
    	Class<?> clazz = detailBean.getClass();
		for(CustFieldSet field : concatFieldSets){
			if(field.getDataType() == 4){
				getName = "get" + field.getFieldCode().substring(0, 1).toUpperCase() + field.getFieldCode().substring(1);
				getMethod = clazz.getDeclaredMethod(getName);
				Object val = getMethod.invoke(detailBean);
				if(val != null){
					map.put(field.getFieldCode(), val.toString().split(","));
					setName =  "set" + field.getFieldCode().substring(0, 1).toUpperCase() + field.getFieldCode().substring(1);
	    			setMethod = clazz.getDeclaredMethod(setName, String.class);
	    			setMethod.invoke(detailBean, "");
				}
			}
		}
		return map;
    }
    
    /**
     * 沉默客户筛选
     *
     * @param dto
     * @return
     * @create 2015年12月16日 下午6:04:52 lixing
     * @history
     */
    public List<ResCustInfoDto> silentCustsSxListPage(ResCustInfoDto dto) {
        return resCustInfoMapper.silentCustsSxListPage(dto);
    }

    /**
     * 查询是否关注
     *
     * @param orgId
     * @param custIds
     * @return
     * @create 2015年12月21日 上午11:05:34 lizhihui
     * @history
     */
    public List<ResCustInfoBean> findByCustIds(String orgId, List<String> custIds) {
        ResCustInfoDto entity = new ResCustInfoDto();
        entity.setOrgId(orgId);
        entity.setIds(custIds);
        return resCustInfoMapper.findByCondtion(entity);
    }
    
    /**
     *查询custi
     *
     * @param orgId
     * @param custIds对应的数据
     * @return
     * @create 2015年12月21日 上午11:05:34 xiaoxh
     * @history
     */
    public List<ResCustInfoBean> findAllByCustIds(String orgId, List<String> custIds) {
        ResCustInfoDto entity = new ResCustInfoDto();
        entity.setOrgId(orgId);
        entity.setIds(custIds);
        return resCustInfoMapper.findAllByCustIds(entity);
    }

    /**
     * 查询是否关注
     *
     * @param orgId
     * @param custId
     * @return
     * @create 2015年12月21日 上午11:05:34 lizhihui
     * @history
     */
    public Integer findIsPrecedence(String orgId, String custId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orgId", orgId);
        params.put("resCustId", custId);
        return resCustInfoMapper.findIsPrecedence(params);
    }

    /**
     * 我的客户 页签 数量
     *
     * @param dto
     * @return
     * @create 2015年12月21日 上午11:05:34 lixing
     * @history
     */
    public Map<String, Integer> getCustNums(ResCustInfoDto dto) {
        return resCustInfoMapper.findCustNums(dto);
    }

    /**
     * 公海客户池
     *
     * @param dto
     * @return
     * @create 2015年12月21日 下午2:28:07 lixing
     * @history
     */
    public List<ResCustInfoDto> getCustPoolListPage(ResCustInfoDto dto) {
    	dto.setIsOrderBy("0");
        List<ResCustInfoDto> dtos = getClearPoolListPage(dto);
        List<ResCustInfoDto> list = new ArrayList<ResCustInfoDto>();
        if(dtos.size() > 0){
        	List<String> ids = new ArrayList<String>();
        	for(ResCustInfoDto rci : dtos){
        		ids.add(rci.getResCustId());
        	}
        	list = resCustInfoMapper.findCustPoolList(ids, dto.getOrgId());
        }
        return list;
    }

    public List<ResCustInfoDto> getCustPoolListPageCache(ResCustInfoDto dto,List<String> multiList) throws Exception {
    	dto.setIsOrderBy("0");
        List<ResCustInfoDto> list = new ArrayList<ResCustInfoDto>();
        List<String> cids = new ArrayList<String>();
    	if(dto.getState() != null && 
    			dto.getState() == 1 && 
    			StringUtils.isNotBlank(dto.getQueryText()) && 
    			(dto.getQueryType().equals("mobilephone") || dto.getQueryType().equals("mainLinkman"))){
    		if(dto.getQueryType().equals("mobilephone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(dto.getOrgId(), dto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(dto.getOrgId(), dto.getQueryText());
    		}
    		if(cids.size() == 0) return list;
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
					return list;
				}
			}
		}
    	List<ResCustInfoDto> dtos = getClearPoolListPage(dto);
        if(dtos.size() > 0){
        	List<String> ids = new ArrayList<String>();
        	for(ResCustInfoDto rci : dtos){
        		ids.add(rci.getResCustId());
        	}
        	list = resCustInfoMapper.findCustPoolList(ids, dto.getOrgId());
        }
        return list;
    }

    /**
     * 获得签约数量
     *
     * @param dto
     * @return
     * @create 2015年12月23日 下午4:32:44 lixing
     * @history
     */
    public Map<String, Integer> getSignNum(ResCustInfoDto dto) {
        return resCustInfoMapper.getSignNum(dto);
    }

    public ResCustInfoBean getByCondtion(ResCustInfoBean entity) {
        return resCustInfoMapper.getByCondtion(entity);
    }

    /**
     * 收件箱短信接口调用 通话电话号码和机构id获取客户资源信息
     *
     * @create zwj
     */
    public List<ResCustInfoBean> getInterfaceCust(CustDto entity) {
        return resCustInfoMapper.findInterfaceCust(entity);
    }

    /**
     * 撞单查询
     *
     * @param custInfoDto
     * @return
     * @create 2016年1月25日 下午1:36:53 lixing
     * @history
     */
    public List<ResCustInfoDto> hitSigleListPage(ResCustInfoDto custInfoDto) {
        List<ResCustInfoDto> list = new ArrayList<ResCustInfoDto>();
        List<String> ids = getHitSigleIds(custInfoDto);
        if(ids.size() > 0){
        	list = resCustInfoMapper.hitSigleList(custInfoDto.getOrgId(), ids);
        }
        return list;
    }

    public List<String> getHitSigleIds(ResCustInfoDto custInfoDto){
    	List<String> ids = new ArrayList<String>();
    	List<ResCustInfoDto> dtos = new ArrayList<ResCustInfoDto>();
    	if(custInfoDto.getState() != null && 
    			custInfoDto.getState() == 1 && 
    			StringUtils.isNotBlank(custInfoDto.getQueryText()) && 
    			(custInfoDto.getQueryType().equals("3") || custInfoDto.getQueryType().equals("2"))){
    		if(custInfoDto.getQueryType().equals("3")){
    			ids = resCustInfoDetailMapper.findLinkmanIdsByPhone(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}else{
    			ids = resCustInfoDetailMapper.findLinkmanIds(custInfoDto.getOrgId(), custInfoDto.getQueryText());
    		}
    		return ids;
    	}
    	
    	if (StringUtils.isNotBlank(custInfoDto.getQueryText()) && "3".equals(custInfoDto.getQueryType()) && (custInfoDto.getState() == null || custInfoDto.getState() == 0)) {
    		dtos = resCustInfoMapper.hitSiglePhoneListPage(custInfoDto);
    	}else{
    		dtos = resCustInfoMapper.hitSigleListPage(custInfoDto);
    	}
    	for(ResCustInfoDto dto : dtos){
    		ids.add(dto.getResCustId());
    	}
    	return ids;
    }
    
    /**
     * 销售进程分布图
     *
     * @param custInfoDto
     * @return
     * @create 2016年1月27日 下午3:33:03 lixing
     * @history
     */
    public List<Map<String, Object>> getSaleProcLayoutChart(ResCustInfoDto custInfoDto) {
        return resCustInfoMapper.findSaleProcLayoutChart(custInfoDto);
    }

    /**
     * 客户类型分布图
     *
     * @param custInfoDto
     * @return
     * @create 2016年1月27日 下午4:57:25 lixing
     * @history
     */
    public List<Map<String, Object>> getCustTypeLayoutChart(ResCustInfoDto custInfoDto) {
        return resCustInfoMapper.findCustTypeLayoutChart(custInfoDto);
    }

    /**
     * 产品类型分布图
     *
     * @param custInfoDto
     * @return
     * @create 2016年1月27日 下午6:52:43 lixing
     * @history
     */
    public List<Map<String, Object>> getProductChart(ResCustInfoDto custInfoDto) {
        return resCustInfoMapper.findProductChart(custInfoDto);
    }

    /**
     * 客户状态分布详情
     *
     * @param orgId
     * @param adminAcc
     * @param groupIds
     * @return
     * @create 2016年1月28日 上午9:47:07 lixing
     * @history
     */
    public List<TeamCustStatusLayoutDto> getTeamCustStatusLayout(@Param("orgId") String orgId, @Param("adminAcc") String adminAcc,
                                                                 @Param("groupIds") List<String> groupIds) {
        return resCustInfoMapper.findTeamCustStatusLayout(orgId, adminAcc, groupIds);
    }

    public List<TeamCustStatusLayoutDto> getTeamGroupStatusLayout(@Param("orgId") String orgId, @Param("adminAcc") String adminAcc,
                                                                  @Param("groupIds") List<String> groupIds) {
        return resCustInfoMapper.findTeamGroupStatusLayout(orgId, adminAcc, groupIds);
    }

    public List<TeamCustStatusLayoutDto> getTeamCustStatusMemberLayout(@Param("orgId") String orgId, @Param("accounts") List<String> accounts, @Param("groupIds") List<String> groupIds) {
        return resCustInfoMapper.findTeamMemberCustStatusLayout(orgId, accounts, groupIds);
    }

    /**
     * 销售进程分布详情
     *
     * @param orgId
     * @param adminAcc
     * @param groupIds
     * @return
     * @create 2016年1月28日 下午1:49:17 lixing
     * @history
     */
    public List<ResCustInfoDto> getSaleProcLayout(String orgId, String adminAcc, List<String> groupIds) {
        return resCustInfoMapper.findSaleProcLayout(orgId, adminAcc, groupIds);
    }

    public List<ResCustInfoDto> getSaleGroupProcLayout(String orgId, String adminAcc, List<String> groupIds) {
        return resCustInfoMapper.findSaleGroupProcLayout(orgId, adminAcc, groupIds);
    }

    public List<ResCustInfoDto> getSaleProcMemberLayout(String orgId, List<String> accounts, List<String> groupIds) {
        return resCustInfoMapper.findSaleProcMemberLayout(orgId, accounts, groupIds);
    }

    /**
     * 客户类型分布详情
     *
     * @param orgId
     * @param adminAcc
     * @param groupIds
     * @return
     * @create 2016年2月1日 上午9:33:58 lixing
     * @history
     */
    public List<ResCustInfoDto> getCustTypeLayout(String orgId, String adminAcc, List<String> groupIds) {
        return resCustInfoMapper.findCustTypeLayout(orgId, adminAcc, groupIds);
    }

    public List<ResCustInfoDto> getCustTypeMemberLayout(String orgId, List<String> accounts, String groupId) {
        return resCustInfoMapper.findCustTypeMemberLayout(orgId, accounts, groupId);
    }

    /**
     * 产品分布详情
     *
     * @param orgId
     * @param adminAcc
     * @param groupIds
     * @return
     * @create 2016年2月1日 下午1:26:30 lixing
     * @history
     */
    public List<ResCustInfoDto> getCustProductLayout(@Param("orgId") String orgId, @Param("adminAcc") String adminAcc, @Param("groupIds") List<String> groupIds) {
        return resCustInfoMapper.findCustProductLayout(orgId, adminAcc, groupIds);
    }

    public List<ResCustInfoDto> getCustProductMemberLayout(@Param("orgId") String orgId, @Param("groupId") String groupId) {
        return resCustInfoMapper.findCustProductMemberLayout(orgId, groupId);
    }

    // 【导入去重】查询号码是否存在
    public Integer getRepeatByPhone(ImportRepeatDto entity) {
        return resCustInfoMapper.getRepeatByPhone(entity);
    }

    // 【导入去重】查询单位名称是否存在
    public Integer getRepeatByName(ImportRepeatDto entity) {
        return resCustInfoMapper.getRepeatByName(entity);
    }

    // 【导入去重】查询单位主页是否存在
    public Integer getRepeatByUnitHome(ImportRepeatDto entity) {
        return resCustInfoMapper.getRepeatByUnitHome(entity);
    }

    public void saveCust(HttpServletRequest request, String orgId, String account, String userName, String deptId, String userId, Integer isState, TsmCustGuide custGuide, CustFollowBean custFollow, String suitProcId, String isSaveDetail, ShiroUser user) {
        CompanyResDto resDto = (CompanyResDto) request.getSession().getAttribute("companyResDto");
        if (resDto != null) {
            ResCustInfoBean resInfo = resDto.getCustInfo();
            resInfo.setImportDeptId(deptId);
            String resCustId = resInfo.getResCustId();
            Date optDate = new Date();
            int resType = user.getIsState();
            int status = 3;
            int type = 2;
            Date followDate = custFollow.getFollowDate();
            String followId = custFollow.getCustFollowId();
            String lastOptionId = custFollow.getSaleProcessId();
            int optType = 10;
            try {
                if ("".equals(resInfo.getResGroupId()) || resInfo.getResGroupId() == null) {
                    String groupId = comReesourceGroupService.saveUnGroup(orgId);
                    resInfo.setResGroupId(groupId);
                }
                resInfo.setNextFollowDate(followDate);
                resInfo.setActionDate(optDate);
                resInfo.setLastCustFollowID(followId);
                resInfo.setLastOptionId(lastOptionId);
                resInfo.setLastCustTypeId(custGuide.getCustTypeId());
                resInfo.setOrgId(orgId);
                resInfo.setOpreateType(optType);
                resInfo.setStatus(status);
                resInfo.setType(type);
                resInfo.setIsDel(0);
                resInfo.setInputStatus(0);
                resInfo.setInputAcc(account);
                resInfo.setOwnerAcc(account);
                resInfo.setOwnerStartDate(optDate);
                resInfo.setAmoytocustomerDate(optDate);
                resInfo.setInputDate(optDate);
                resInfo.setLabelCode(custFollow.getLabelCode());
                resInfo.setLabelName(custFollow.getLabelName());
                resInfo.setFilterType(AppConstant.FILTER_TYPE0.intValue());
                String fax = resInfo.getFax() == null ? "" : resInfo.getFax();
                resInfo.setFax(fax.replace(",", ""));
                resInfo.setLifeCode(GuidUtil.getId());
                resInfo.setOwnerActionDate(resInfo.getActionDate());
                resInfo.setSource(1);
                String resDetailInfoId = null;
                if (isState == 1 && "1".equals(isSaveDetail)) {
                    resDetailInfoId = SysBaseModelUtil.getModelId();
                }
                if (resType == 1) {// 企业资源
                    resInfo.setState(1);// 企业资源
                    ResCustInfoDetailBean resDetailInfo = resDto.getCustInfoDetail();
                    if (resDetailInfo == null) {
                        resDetailInfo = new ResCustInfoDetailBean();
                    }
                    // 常用电话
                    resInfo.setMobilephone(StringUtils.toCheckPhone(formatPhone(resDetailInfo.getTelphone(), request, user))); // 判断是否是固话，是，再判断是否系统设置要添加区号。
                    // 备用电话
                    resInfo.setTelphone(StringUtils.toCheckPhone(formatPhone(resDetailInfo.getTelphonebak(), request, user))); //
                    resInfo.setName(StringUtils.isNotBlank(resInfo.getName()) ? resInfo.getName().trim() : "");
    				resInfo.setCompany(StringUtils.isNotBlank(resInfo.getCompany()) ? resInfo.getCompany().trim() : "");
    				resInfo.setUnithome(StringUtils.isNotBlank(resInfo.getUnithome()) ? resInfo.getUnithome().trim() : "");
    				resDetailInfo.setName(StringUtils.isNotBlank(resDetailInfo.getName()) ? resDetailInfo.getName().trim() : "");
    				
                    resInfo.setMainLinkman(resDetailInfo.getName());// 主要联系人
                    resDetailInfo.setOrgId(orgId);
                    resDetailInfo.setInputtime(optDate);
                    resDetailInfo.setTelphone(StringUtils.toCheckPhone(formatPhone(resDetailInfo.getTelphone(), request, user)));
                    resDetailInfo.setTelphonebak(StringUtils.toCheckPhone(formatPhone(resDetailInfo.getTelphonebak(), request, user)));
                   
                    resDetailInfo.setRciId(resInfo.getResCustId());
                    resDetailInfo.setTscidId(resDetailInfoId);
                    resDetailInfo.setIsDefault(1);
                    createComRes(resInfo, resDetailInfo, isSaveDetail);
                    custFollow.setCustDetailMobile(StringUtils.toCheckPhone(formatPhone(resDetailInfo.getTelphone(), request, user)));
                    custFollow.setCustDetailName(resDetailInfo.getName());
                } else {// 个人资源
                    resInfo.setState(0);// 企业资源
                    resInfo.setTelphone(StringUtils.toCheckPhone(formatPhone(resInfo.getTelphone(), request, user))); // 判断是否是固话，是，再判断是否系统设置要添加区号。
                    resInfo.setMobilephone(StringUtils.toCheckPhone(formatPhone(resInfo.getMobilephone(), request, user))); //
                    resInfo.setName(StringUtils.isNotBlank(resInfo.getName()) ? resInfo.getName().trim() : "");
    				resInfo.setCompany(StringUtils.isNotBlank(resInfo.getCompany()) ? resInfo.getCompany().trim() : "");
    				resInfo.setUnithome(StringUtils.isNotBlank(resInfo.getUnithome()) ? resInfo.getUnithome().trim() : "");
                    create(resInfo);
                }
    			if (isState == 0) { // 个人版
    				custFollow.setOldCustName(resInfo.getName());
    				custFollow.setCustDetailName(resInfo.getCompany());
    			} else {
    				custFollow.setOldCustName(resInfo.getName());
    			}
                saveFollow(resInfo, orgId, account, userName, custGuide, custFollow, suitProcId, resCustId, optDate);
                //插入日志
                logCustInfoService.addLog(orgId, account, resCustId, null, OperateEnum.LOG_WILL);
                
				LogBean logBean =new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_CUST.getCode(), OperateEnum.LOG_CUST.getDesc(), 1,SysBaseModelUtil.getModelId()); 
				logBean.setModuleId(AppConstant.Module_id16);
				logBean.setModuleName(AppConstant.Module_Name16);
				logBean.setOperateId(AppConstant.Operate_id83);
				logBean.setOperateName(AppConstant.Operate_Name83);
				logBean.setContent((resInfo.getCompany() == null || "".equals(resInfo.getCompany()))? resInfo.getName() : resInfo.getCompany());				
				Map<String, Object> logMap = new HashMap<String, Object>();
				logMap.put(resCustId, "");
				logCustInfoService.addTableStoreLog(logBean, logMap);
                
                
                planFactService.toWill(orgId, deptId, userId);
                planFactService.updateContactResult(orgId, deptId, userId, resCustId, "res", PlanResCR.TURN_WILL.getResult());
                OptionBean optionBean = optionMapper.getByPrimaryKeyAndOrgId(custGuide.getSaleProcessId(), orgId);
                if (optionBean == null) {
                    optionBean = new OptionBean();
                }
                if (resInfo != null && resInfo.getResCustId() != null && !"".equals(resInfo.getResCustId())) {
                    Map<String, Object> jsonMap = new HashMap<String, Object>();
                    jsonMap.put("inputDate", new Date());
                    jsonMap.put("mainLinkman", isState == 1 ? resInfo.getMainLinkman() : resInfo.getName());
                    jsonMap.put("userName", (userName == null || "".equals(userName) ? account : userName));
                    jsonMap.put("telphone",
                            (resInfo.getMobilephone() == null || "".equals(resInfo.getMobilephone()) ? resInfo.getTelphone() : resInfo.getMobilephone()));
                    jsonMap.put("type", "1");
                    jsonMap.put("saleProcessName", optionBean.getOptionName());
                    jsonMap.put("labels", custFollow.getLabelName());
                    jsonMap.put("remark", custFollow.getFeedbackComment());
                    jsonMap.put("custFollowId", custFollow.getCustFollowId());
					jsonMap.put("nextConcatTime", followDate);
                    resCustEventService.create(orgId, resCustId, "1", JsonUtil.getJsonString(jsonMap));
                }
                rankingReportService.updateRankingData(orgId, account, new BigDecimal(0), 0, 1);

//                ContactDayDataBean contactBean = new ContactDayDataBean();
//                contactBean.setOrgId(orgId);
//                contactBean.setResId(resCustId);
//                contactBean.setAccount(account);
//                contactBean.setCurrDate(DateUtil.format(new Date(), DateUtil.defaultPattern));
//                ContactDayDataBean tempBean = contactDayDataMapper.getByCondtion(contactBean);
//                if (tempBean == null) {
//                	contactBean.setId(SysBaseModelUtil.getModelId());
//                    contactBean.setType(1);
//                    contactBean.setStatus(1);
//                    contactBean.setInitProcessId(lastOptionId);
//                    contactBean.setCurrProcessId(lastOptionId);
//                    contactBean.setId(SysBaseModelUtil.getModelId());
//                    contactDayDataMapper.insert(contactBean);
//                } else {
//                    tempBean.setType(1);
//                    tempBean.setStatus(1);
//                    tempBean.setInitProcessId(lastOptionId);
//                    tempBean.setCurrProcessId(lastOptionId);
//                    contactDayDataMapper.update(tempBean);
//                }

            } catch (Exception e) {
                throw new SysRunException(e);
            }
        }
    }

    public void saveFollow(ResCustInfoBean resInfo, String orgId, String account, String userName, TsmCustGuide custGuide, CustFollowBean custFollow, String suitProcId, String resCustId,
                           Date opDate) {
        try {
            Map<String, String> map = new HashMap<String, String>();
            map.put("custId", resCustId);
            map.put("orgId", orgId);
            custGuideMapper.removeByCustId(map);

            // 新增销售导航记录
            String guideId = SysBaseModelUtil.getModelId();
            custGuide.setCustGuideId(guideId);
            custGuide.setCustId(resCustId);
            custGuide.setInputerAcc(account);
            custGuide.setInputdate(opDate);
            custGuide.setOrgId(orgId);
            custGuide.setSaleProcessId(custFollow.getSaleProcessId());
            custGuideMapper.insert(custGuide);

            String[] proIds = suitProcId.split(",");
            List<TsmCustGuideProc> suitProList = new ArrayList<TsmCustGuideProc>();
            for (String proId : proIds) {
                TsmCustGuideProc proc = new TsmCustGuideProc();
                proc.setId(GuidUtil.getId());
                proc.setGuideId(guideId);
                proc.setProcId(proId);
                proc.setOrgId(orgId);
                suitProList.add(proc);
            }
            if (suitProList.size() > 0) {
                tsmCustGuideProcMapper.insertBatch(suitProList);
            }
            
         // 需要删除 多选项表中 行动标签值
    		Map<String,Object>delMap = new HashMap<String, Object>();
    		delMap.put("orgId", orgId);
    		delMap.put("custId", resInfo.getResCustId());
    		delMap.put("fieldCode", "labelCode");
    		custDefinedDataMapper.deleteByFieldCode(delMap);
    		if(StringUtils.isNotBlank(custFollow.getLabelCode())){
    			List<CustDefinedDataBean>mulitBeans = new ArrayList<CustDefinedDataBean>();
    			for(String labelCode:custFollow.getLabelCode().split("#")){
    				CustDefinedDataBean mulitDefinedBean = new CustDefinedDataBean();
    				mulitDefinedBean.setId(SysBaseModelUtil.getModelId());
    				mulitDefinedBean.setOrgId(orgId);
    				mulitDefinedBean.setCustId(resInfo.getResCustId());
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
    				mulitDefinedBean1.setOrgId(orgId);
    				mulitDefinedBean1.setCustId(resInfo.getResCustId());
    				mulitDefinedBean1.setActionId(custFollow.getCustFollowId());
    				mulitDefinedBean1.setFieldCode("labelCode");
    				mulitDefinedBean1.setFieldData(labelCode);
    				mulitBeans1.add(mulitDefinedBean1);
    			}
    			if(mulitBeans1 != null && mulitBeans1.size()>0){
    				custLabelCodeDataMapper.insertBatch(mulitBeans1);
    			}	
    		}
            // 新增客户跟进记录
            custFollow.setCustId(resCustId);
            custFollow.setFollowAcc(account);
            custFollow.setOrgId(orgId);
            custFollow.setActionDate(opDate);
            custFollow.setInputAcc(account);
            custFollow.setInputDate(opDate);
//            custFollow.setActionType(AppConstant.ACTION_TYPE1); // 行动方式
            custFollow.setSaleProcessId(custGuide.getSaleProcessId());
            custFollow.setOrgId(orgId);
            custFollow.setIsFollow(1);
            custFollow.setCustStatus(5);
            custFollow.setCustTypeId(custGuide.getCustTypeId());
            custFollowMapper.insert(custFollow);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 销售交接 获取客户数量
     *
     * @param custInfoDto
     * @param request
     * @return
     * @throws CloneNotSupportedException
     * @create 2016年4月19日 下午2:16:57 lixing
     * @history
     */
    public Integer saleCustMoveNum(ResCustInfoDto custInfoDto, HttpServletRequest request, ShiroUser user) throws CloneNotSupportedException {
        logger.debug("****【销售交接】  开始");
        custInfoDto.setOrgId(user.getOrgId());
        custInfoDto.setItemCode(AppConstant.SALES_TYPE_ONE);
        List<ResCustInfoDto> arrs = getDeliveryIdsListPage(custInfoDto);
        int num = custInfoDto.getPage().getTotalResult();
        return num;
    }
    
    /**
     * 销售交接
     *
     * @param custInfoDto
     * @param request
     * @return
     * @throws Exception 
     * @create 2016年4月19日 下午2:16:57 lixing
     * @history
     */
    public String saleCustMove(ResCustInfoDto custInfoDto, HttpServletRequest request, ShiroUser user) throws Exception {
        ResCustInfoDto tmpDto = null;
        Page tmppage = null;
        int i = 0;
        logger.debug("****【销售交接】  开始");
        String reason = request.getParameter("reason");
        custInfoDto.setOrgId(user.getOrgId());
        custInfoDto.setItemCode(AppConstant.SALES_TYPE_ONE);
        List<ResCustInfoDto> arrs = getDeliveryIdsListPage(custInfoDto);
        if (arrs == null || arrs.size() == 0) {
            logger.debug("****【销售交接】  没有找到要交接的资源 、 客户");
            return "9";
        }

        logger.debug("****【销售交接】  资源、客户SIZE:" + custInfoDto.getPage().getTotalResult());
        List<ResCustInfoDto> arrs2 = null;
        List<List<ResCustInfoDto>> res_id_list = null;
        String progressId = GuidUtil.getId();
		//progressBarService.insertProgress(user.getOrgId(), user.getAccount(), ProgressType.CUST_MOVE.getValue(), progressId, custInfoDto.getPage().getTotalResult());
        int page = custInfoDto.getPage().getTotalResult() / 20000;
        if (custInfoDto.getPage().getTotalResult() % 20000 != 0) {
            page++;
        }
        tmpDto = (ResCustInfoDto) custInfoDto.clone();
        tmppage = new Page();
        tmppage.setShowCount(20000);
        tmppage.setPageStr(null);
        tmppage.setPageSubStr(null);
        tmppage.setTotalResult(custInfoDto.getPage().getTotalResult());
        tmppage.setTotalPage(page);
        tmppage.setCurrentPage(page);
        tmpDto.setPage(tmppage);
        while (tmppage.getCurrentPage() >= 1) {
            arrs2 = getDeliveryIdsListPage(tmpDto);
            if (arrs2 != null && arrs2.size() > 0) {
                res_id_list = splitList(arrs2, 4000);
                for (final List<ResCustInfoDto> subList : res_id_list) {
                    i++;
                    batchDelivery(subList, user, tmpDto, reason);
                    //progressBarService.updateProgress(user.getOrgId(), user.getAccount(), progressId, ProgressType.CUST_MOVE.getValue(), subList.size());
                }
            }
            if (tmppage.getCurrentPage() == 1) {
                break;
            }
            tmppage.setCurrentPage(tmppage.getCurrentPage() - 1);
        }
//        logUserOperateService.setUserOperateLog( AppConstant.Module_id103,AppConstant.Module_Name103, AppConstant.Operate_id24, AppConstant.Operate_Name24, custInfoDto.getPage().getTotalResult()+"条，接收人："+custInfoDto.getOwnerName(),"");
        logger.debug("****【销售交接】  结束" + i);
        return "1";
    }


    public void batchDelivery(final List<ResCustInfoDto> subList, final ShiroUser user, final ResCustInfoDto dto, final String reason) {
        long timerBegin = System.currentTimeMillis();
        jdbcTemplate.batchUpdate(DELIVERY_CUST_SQL, new BatchPreparedStatementSetter() {
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, user.getAccount());
                ps.setString(2, dto.getMoveToAcc());
                ps.setTimestamp(3, new Timestamp(subList.get(i).getOwnerStartDate().getTime()));
                ps.setString(4, user.getOrgId());
                ps.setString(5, subList.get(i).getResCustId());
            }

            public int getBatchSize() {
                return subList.size();
            }
        });

        //清空销售机会
        taskExecutor.submit(new Runnable() {
			@Override
			public void run() {
				jdbcTemplate.batchUpdate(CLEAN_CUST_SALE_CHANCE,new BatchPreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						 ps.setString(1, user.getOrgId());
	                     ps.setString(2, subList.get(i).getResCustId());
					}
					
					@Override
					public int getBatchSize() {
						return subList.size();
					}
				});
			}
		});
        
        
        taskExecutor.submit(new Runnable() {
            public void run() {
                jdbcTemplate.batchUpdate(CLEAR_CALLCOUNT_SQL, new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, user.getOrgId());
                        ps.setString(2, subList.get(i).getResCustId());
                    }

                    public int getBatchSize() {
                        return subList.size();
                    }
                });
            }
        });

        taskExecutor.submit(new Runnable() {
            public void run() {
                jdbcTemplate.batchUpdate(CREATE_CUST_OPTOR_SQL, new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, SysBaseModelUtil.getModelId());
                        ps.setString(2, subList.get(i).getResCustId());
                        ps.setString(3, dto.getMoveToAcc());
                        ps.setString(4, dto.getOwnerAcc());
                        if (StringUtils.isBlank(subList.get(i).getLastOptionId())) {
                            ps.setNull(5, Types.VARCHAR);
                        } else {
                            ps.setString(5, subList.get(i).getLastOptionId());
                        }
                        ps.setString(6, user.getAccount());
                        ps.setString(7, dto.getOwnerAcc());
                        ps.setString(8, dto.getOrgId());
                        ps.setString(9, dto.getOwnerName());
                        if (StringUtils.isBlank(reason)) {
                            ps.setNull(10, Types.VARCHAR);
                        } else {
                            ps.setString(10, reason);
                        }
                    }

                    public int getBatchSize() {
                        return subList.size();
                    }
                });
            }
        });
        List<String> custIds = new ArrayList<String>();
        Map<String, Object> logMap = new HashMap<String, Object>();
        for (ResCustInfoDto rcid : subList) {
        	//AppConstant.Module_id103,AppConstant.Module_Name103, AppConstant.Operate_id24, AppConstant.Operate_Name24
//        	logCustInfoService.addLog(user.getOrgId(), user.getAccount(), rcid.getResCustId(), "{\"transferedAcc\":\""+dto.getOwnerAcc()+"\",\"transferAcc\":\""+dto.getMoveToAcc()+"\"}", OperateEnum.LOG_SALETRANSFER);
        	logMap.put(rcid.getResCustId(), "{\"transferedAcc\":\""+dto.getOwnerAcc()+"\",\"transferAcc\":\""+dto.getMoveToAcc()+"\"}");
        	custIds.add(rcid.getResCustId());
        }
        LogBean logBean = new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_SALETRANSFER.getCode(), OperateEnum.LOG_SALETRANSFER.getDesc(), subList.size(), SysBaseModelUtil.getModelId());
        logBean.setModuleId(AppConstant.Module_id103);
        logBean.setModuleName(AppConstant.Module_Name103);
        logBean.setOperateId(AppConstant.Operate_id24);
        logBean.setOperateName(AppConstant.Operate_Name24);
        logBean.setContent(subList.size()+"条，接收人："+dto.getOwnerName());
        logCustInfoService.addTableStoreLog(logBean, logMap);
        comResourceService.freshTaoCache(user.getOrgId(), user.getAccount());
//        logBatchInfoService.saveLogInfo(user.getOrgId(), user.getAccount(),user.getName(), OperateEnum.LOG_SALETRANSFER, custIds,dto.getOwnerAcc());
        long min = (System.currentTimeMillis() - timerBegin);
        logger.info("****【销售交接】  耗时(毫秒)：" + min);
    }

    /**
     * 分割
     *
     * @param idsList
     * @param spSize
     * @return
     * @create 2015年11月18日 下午1:47:05 lixing
     * @history
     */
    public static List<List<ResCustInfoDto>> splitList(List<ResCustInfoDto> idsList, int spSize) {
        List<List<ResCustInfoDto>> rtnList = new ArrayList<List<ResCustInfoDto>>();
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
     * 个人资源数量是否超过最大资源数(返回集合{flag:0-没有超出/1-超出最大数,bSize:操作数量,resNum:当前资源数,maxNum:
     * 最大资源数})
     *
     * @param account 帐号
     * @param orgId   单位ID
     * @param bSize   操作数量
     * @return Map<String,Integer>
     * @create 2015年11月26日 上午9:51:50 lixing
     * @history
     */
    public Map<String, Integer> isResBeyondMax(String account, String orgId, Integer bSize) {
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("flag", 0);
        map.put("bSize", bSize);
        Integer resNum = getMyResSum(account, orgId);
        List<DataDictionaryBean> openList = cachedService.getDirList(AppConstant.DATA15, orgId);
        Integer maxNum = 0;
        if (!openList.isEmpty() && openList.get(0).getDictionaryValue() != null && openList.get(0).getDictionaryValue().equals("1")) {
            List<DataDictionaryBean> valueList = cachedService.getDirList(AppConstant.DATA28, orgId);
            if (!valueList.isEmpty() && valueList.get(0).getIsOpen() != null && valueList.get(0).getIsOpen().equals("1")) {
                maxNum = Integer.parseInt(valueList.get(0).getDictionaryValue() == null ? "0" : valueList.get(0).getDictionaryValue());
                if ((resNum + bSize) > maxNum) {
                    map.put("flag", 1);
                }
            }
        }
        map.put("resNum", resNum);
        map.put("maxNum", maxNum);
        return map;
    }

    /**
     * 格式化固话号码 说明：首位加0及去除区号后的“-”字符
     *
     * @param phone
     * @param request
     * @return
     * @create 2015年11月17日 下午3:14:52 lixing
     * @history
     */
    public String formatPhone(String phone, HttpServletRequest request, ShiroUser user) {
        if (phone == null || "".equals(phone)) {
            return "";
        } else {
            phone = phone.trim();
        }
        String rstPhnoe = phone;
        if (StringUtils.isBlank(phone)) {
            return "";
        }
        String tel1 = "^\\d{3,4}-\\d{3,4}-\\d{3,4}$";
        Pattern p1 = Pattern.compile(tel1);
        Matcher m1 = p1.matcher(rstPhnoe);
        if (m1.matches()) {
            rstPhnoe = rstPhnoe.replaceAll("-", "");
        } else {
            if (rstPhnoe.length() >= 5 && rstPhnoe.substring(3, 5).contains("-")) {
                rstPhnoe = rstPhnoe.replaceFirst("-", "");
            }
            if (phone.length() == 7 || phone.length() == 8) {
                List<DataDictionaryBean> openList = cachedService.getDirList(AppConstant.DATA18, user.getOrgId());
                String isOpen = openList.get(0).getDictionaryValue();
                isOpen = isOpen == null ? "0" : isOpen;
                if (isOpen.equals("1")) {
                    List<DataDictionaryBean> itemValList = cachedService.getDirList(AppConstant.DATA19, user.getOrgId());
                    String px = itemValList.get(0).getDictionaryValue();
                    px = px == null ? "0" : px;
                    rstPhnoe = px + rstPhnoe;
                }
            }
        }
        return rstPhnoe;
    }

    // 根据资源ID集合，查询公海客户
 	public List<String> getResByResId(Map<String,Object>map){
 		return resCustInfoMapper.findResByResId(map);
 	}
 	
 	/**
     * 查询部门下资源数量
     *
     * @param dto
     * @return
     * @create 2015年12月21日 上午11:05:34 lizhihui
     * @history
     */
    public Integer findResCountByGroupId(String orgId, String groupId) {
        ResCustInfoDto entity = new ResCustInfoDto();
        entity.setOrgId(orgId);
        entity.setImportDeptId(groupId);
        entity.setImportDeptIdsStr(groupId+"_F");
        return resCustInfoMapper.findResCountByGroupId(entity);
    }
    
 	
 	//查询黑名单
 	public List<BlackListBean> findBlackListPage (BlackListBean blackListBean){
 	return	blackListBeanMapper.findBlackListPage(blackListBean);
 	}

	//查询黑名单,不分页
	public List<String> findBlackList(BlackListBean black){
		return	blackListBeanMapper.findBlackList(black);
	}
 	
 	
 	//插入黑名单
 	public void insertBlackList (BlackListBean blackListBean){
 		blackListBeanMapper.insertBlack(blackListBean);
 		
 	}
 	
 	//删除黑名单
 	public void deleteBlackList (BlackListBean black){
 		blackListBeanMapper.deleteBlack(black);
 	}

 	//设置共有者
 	public void updateCustsCommonAcc(String orgId,String operateAcc,String operateName,List<String> custIds,String commonAcc,String context){
 		LogBean logBean = new LogBean(orgId, operateAcc, operateName, null, null, custIds.size(), SysBaseModelUtil.getModelId());
 		Map<String,Object> logMap = new HashMap<String, Object>();
 		logBean.setModuleId(AppConstant.Module_id1000);
	 	logBean.setModuleName(AppConstant.Module_Name1000);
 		logBean.setContent(context);
 		if(StringUtils.isNotBlank(commonAcc)){//设置共有者
 			logBean.setResOperateId(OperateEnum.LOG_SET_COMMON_ACC.getCode());
 			logBean.setResOperateName(OperateEnum.LOG_SET_COMMON_ACC.getDesc());
 			for(String custId : custIds){
 				logMap.put(custId, "");
 			}
 			logBean.setOperateId(AppConstant.Operate_id9);
 	 		logBean.setOperateName(AppConstant.Operate_Name9);
 		}else{//取消设置共有者
 			logBean.setResOperateId(OperateEnum.LOG_CLEAN_COMMON_ACC.getCode());
 			logBean.setResOperateName(OperateEnum.LOG_CLEAN_COMMON_ACC.getDesc());
 			for(String custId : custIds){
 				logMap.put(custId, "");
 			}
 			logBean.setOperateId(AppConstant.Operate_id81);
 	 		logBean.setOperateName(AppConstant.Operate_Name81);
 		}
 		logCustInfoService.addTableStoreLog(logBean, logMap);
 		resCustInfoMapper.updateCustsCommonAcc(orgId, custIds, commonAcc);
 	}
 	
 	//设置共有者
 	public void updateCustsNextFollowDate(String orgId,String custId,Date nextFollowDate){
 		resCustInfoMapper.updateCustsNextFollowDate(orgId, custId, nextFollowDate);
 	}

	public ResCustInfoBean getCustData(List<CustFieldSet> fieldSets,String pname, String cname, String oname,ResCustInfoBean custInfoBean) throws Exception {
		for (CustFieldSet fieldSet : fieldSets) {
			
			if ("defined1".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined1())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined2".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined2())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined3".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined3())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined4".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined4())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined5".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined5())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined6".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined6())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined7".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined7())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined8".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined8())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined9".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined9())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined10".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined10())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined11".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined11())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined12".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined12())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined13".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined13())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined14".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined14())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined15".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfoBean.getDefined15())) {
				setFieldValues(fieldSet, custInfoBean);
			} else if ("defined16".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && custInfoBean.getDefined16() != null) {
				custInfoBean.setShowdefined16(new SimpleDateFormat("yyyy-MM-dd").format(custInfoBean.getDefined16()));
			} else if ("defined17".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && custInfoBean.getDefined17() != null) {
				custInfoBean.setShowdefined17(new SimpleDateFormat("yyyy-MM-dd").format(custInfoBean.getDefined17()));
			} else if ("defined18".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && custInfoBean.getDefined18() != null) {
				custInfoBean.setShowdefined18(new SimpleDateFormat("yyyy-MM-dd").format(custInfoBean.getDefined18()));
			}
		}
		return custInfoBean;
	}

	public void setFieldValues(CustFieldSet fieldSet,ResCustInfoBean custInfoBean) throws Exception {
		Class<?> classType = custInfoBean.getClass();
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
			 String id =(String)getMethod.invoke(custInfoBean, new Object[] {});
			 setMethod.invoke(custInfoBean,(Object)null);
			 if (org.apache.commons.lang3.StringUtils.isNotBlank(id) && optionList != null) {
				 for (OptionBean optionBean : optionList) {
						if (id.equals(optionBean.getOptionlistId())) {
							setMethod.invoke(custInfoBean,optionBean.getOptionName());
						}		 
				 }
			}
		}else if (fieldSet.getDataType() == 4) {
			 List<OptionBean> optionList = fieldSet.getOptionList();
			 String ids = (String)getMethod.invoke(custInfoBean, new Object[] {});
			 String[] idss = ids.split(",");
			 StringBuffer showview = new StringBuffer();
			 setMethod.invoke(custInfoBean,(Object)null);
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
			 setMethod.invoke(custInfoBean,showview.toString());
			}
		}
	}
	
	public void setCustHighSearchDefined(ResCustInfoDto cdto,Map<String,String> map,String orgId)throws Exception{
 		if(map != null && map.size() > 0){
			 Class tCls = cdto.getClass().getSuperclass();
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
	
	public List<String> getShowCustIds(List<ResCustInfoDto> list){
    	List<String> custIds = new ArrayList<String>();
    	for(ResCustInfoDto dto : list) custIds.add(dto.getResCustId());
    	return custIds;
    }
    
    public void multiDefinedShowChange(List<ResCustInfoDto> list,List<String> multiList,List<String> singleList,String orgId) throws Exception{
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
		for(ResCustInfoDto cust : list){
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
    
    /**设置扩展表查询条件*/
    public Map<String, Object> getMultiDefinedSearchParam(ResCustInfoDto custInfoDto,List<String> multiList) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	Class<?> clazz = custInfoDto.getClass().getSuperclass();
    	List<String> fieldCodes = new ArrayList<String>();
    	List<String> fieldDatas = new ArrayList<String>();
    	String getName;
    	String setName;
    	Method getMethod;
    	Method setMethod;
    	for(String fieldCode : multiList){
    		if(fieldCode.equals(AppConstant.SEARCH_LABEL) && custInfoDto.getLabels() != null && custInfoDto.getLabels().size() > 0){
				fieldCodes.add(AppConstant.SEARCH_LABEL);
				fieldDatas.addAll(custInfoDto.getLabels());
				custInfoDto.setLabels(null);
				continue;
    		}
    		getName =  "get" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
    		getMethod = clazz.getDeclaredMethod(getName);
    		Object val = getMethod.invoke(custInfoDto);
    		if(val != null && StringUtils.isNotBlank(val.toString())){
    			fieldCodes.add(fieldCode);
    			fieldDatas.addAll(Arrays.asList(val.toString().split(",")));
    			setName =  "set" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
    			setMethod = clazz.getDeclaredMethod(setName, String.class);
    			setMethod.invoke(custInfoDto, "");
    		}
    	}
    	if(fieldCodes.size() > 0){
    		map.put("fieldCodes", fieldCodes);
    		map.put("fieldDatas", fieldDatas);
    		map.put("orgId", custInfoDto.getOrgId());
    	}
    	return map;
    }

    
    public Integer getResExpireCount(Map<String, Object> map){
    	return resCustInfoMapper.findResExpireCount(map);
    }
    
    public Integer getCustExpireCount(Map<String, Object> map){
    	return resCustInfoMapper.findCustExpireCount(map);
    }
    
    public List<String> getCustExpireIds(Map<String, Object> map){
    	return resCustInfoMapper.findCustExpireIds(map);
    }
    
    public List<ResCustInfoBean> getSignOrderExpireCusts(String orgId,List<String> custIds){
		return resCustInfoMapper.findSignOrderExpireCusts(orgId,custIds);
	}
    

	public List<CustomReportDoubleShowDto> makeCustomReport2(ResCustReportDto dto) throws Exception {
		ShiroUser user = ShiroUtil.getShiroUser();
		String orgId = user.getOrgId();
		Class<?> classType = dto.getClass();
		List<CustFieldSet> defineds = new ArrayList<>();
		if (user.getIsState() == 1) {
			defineds = cachedService.getComFiledSets(user.getOrgId());
		}else if(user.getIsState() == 0) {
			defineds = cachedService.getPersonFiledSets(user.getOrgId());
		}
		Map<String,Object> map = new HashMap<>();
		List<String> fieldCodes = new ArrayList<String>();
    	List<String> fieldDatas = new ArrayList<String>();
		a:for (int i = 1; i < 16; i++) {
			String BaseGetDefined = "getDefined";
			String BaseSetDefined = "setDefined";
			String definedName = "defined"+i;
			b:for (CustFieldSet custFieldSet : defineds) {
				if (custFieldSet.getFieldCode().equals(definedName) && custFieldSet.getDataType().equals(4)) {
					String getDefinedMethodName = BaseGetDefined + i;
					String getDefinedMatchMethodName = BaseGetDefined + i + "Match";
					String setDefinedMethodName = BaseSetDefined + i;
					String setDefinedMatchMethodName = BaseSetDefined + i + "Match";
					Method getDefined = classType.getMethod(getDefinedMethodName,new Class[] {});
					Method getDefinedMatch= classType.getMethod(getDefinedMatchMethodName,new Class[] {});
					Method setDefined = classType.getMethod(setDefinedMethodName,String.class);
					Method setDefinedMatch= classType.getMethod(setDefinedMatchMethodName,Integer.class);
					String value = (String)getDefined.invoke(dto, new Object[] {});
					Integer match = (Integer)getDefinedMatch.invoke(dto, new Object[] {});
					if (value != null && !"".equals(value) && match != null){
						fieldCodes.add(definedName);
						fieldDatas.addAll(Arrays.asList(value.split(",")));
					}
					setDefined.setAccessible(true);
					setDefined.invoke(dto, (Object)null);
					setDefinedMatch.setAccessible(true);
					setDefinedMatch.invoke(dto, (Object)null);
					break b;
				}
			}
		}
    	if(fieldCodes.size() > 0){
    		map.put("fieldCodes", fieldCodes);
    		map.put("fieldDatas", fieldDatas);
    		map.put("orgId", user.getOrgId());
    	}
		if(map.size() > 0){
			List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(map);
			if(custIds.size() > 0){
				dto.setCustIds(custIds);
			}else{
				return null;
			}
		}
		List<CustomReportDoubleDto> dtos = resCustInfoMapper.makeCustomReport2(dto);
		//封装数据
		List<CustomReportDoubleShowDto> list = new ArrayList<>();
		String groupByName1 = dto.getGroupByType1();
		String groupByName2 = dto.getGroupByType2();
		List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO,user.getOrgId());
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
		//List<Map<String, Object>> groups = cachedService.getResGroupList1(user.getOrgId());
		List<OptionBean> companyTrades = cachedService.getOptionList("companyTrade",user.getOrgId());
		Map<String, OrgGroup> orgGroupMap = cachedService.getOrgGroupMap(user.getOrgId());
		List<ResourceGroupBean> resGroupList= resourceGroupMapper.findOnlyResGroup(orgId);
		Map<String, CustFieldSet> defineds1 = new HashMap<>();
		if (defineds.size() > 0) {
			for (CustFieldSet fieldSet : defineds) {
				if (fieldSet.getDataType() == 3 && fieldSet.getOptionList().size() > 0 && fieldSet.getFieldCode().contains("defined")) {
					defineds1.put(fieldSet.getFieldCode(),fieldSet);
				}
			}
		}
		
		if ("last_cust_type_id".equals(groupByName1)) {
			for (OptionBean option : custTypeList) {
				List<CustomReportSingleDto> singleList = new ArrayList<>();
				for(CustomReportDoubleDto bean : dtos) {
					if (bean.getCount() > 0 && option.getOptionlistId().equals(bean.getSingleName1())) {
						CustomReportSingleDto crsd = new CustomReportSingleDto();
						if ("qStatus".equals(groupByName2)) {
							if ("1".equals(bean.getSingleName2())) {
								crsd.setSingleName("待分配资源");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("2".equals(bean.getSingleName2())){
								crsd.setSingleName("已分配资源");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("3".equals(bean.getSingleName2())){
								crsd.setSingleName("意向客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("4".equals(bean.getSingleName2())){
								crsd.setSingleName("签约客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("5".equals(bean.getSingleName2())){
								crsd.setSingleName("流失客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("6".equals(bean.getSingleName2())){
								crsd.setSingleName("沉默客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("7".equals(bean.getSingleName2())){
								crsd.setSingleName("公海客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if ("last_option_Id".equals(groupByName2)) {
							for(OptionBean option1 :options) {
								if (option1.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option1.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("import_dept_id".equals(groupByName2)) {
							OrgGroup orgGroup = orgGroupMap.get(bean.getSingleName2());
							if (orgGroup != null) {
								crsd.setSingleName(orgGroup.getGroupName());
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if ("res_group_id".equals(groupByName2)) {
							for(ResourceGroupBean rgb : resGroupList) {
								if (rgb.getResGroupId().equals(bean.getSingleName2())) {
									crsd.setSingleName(rgb.getGroupName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("company_trade".equals(groupByName2)) {
							for(OptionBean option1 : companyTrades) {
								if (option1.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option1.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("opreate_type".equals(groupByName2)){
							if ("11".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-到期未签约");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("16".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-到期未跟进");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("12".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-主动放弃");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("21".equals(bean.getSingleName2())) {
								crsd.setSingleName("签约客户-流失");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("23".equals(bean.getSingleName2())) {
								crsd.setSingleName("签约-到期未续签");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("4".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-沟通失败");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("5".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-信息错误");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("24".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-到期未联系");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if (groupByName2.contains("defined")) {
							CustFieldSet field = defineds1.get(groupByName2);
							if (field != null) {
								List<OptionBean> optionList = field.getOptionList();
								for (OptionBean optionBean : optionList) {
									if (optionBean.getOptionlistId().equals(bean.getSingleName2())) {
										crsd.setSingleName(optionBean.getOptionName());
										crsd.setCount(bean.getCount());
										singleList.add(crsd);
									}
								}
							}
						}
					}
				}
				if (singleList.size() > 0) {
					CustomReportDoubleShowDto crdsd = new CustomReportDoubleShowDto();
					crdsd.setList(singleList);
					crdsd.setGroupByName(option.getOptionName());
					list.add(crdsd);
				}
			}
		} else if ("qStatus".equals(groupByName1)) {
			for(int i = 1;i <= 7;i++) {
				List<CustomReportSingleDto> singleList = new ArrayList<>();
				for(CustomReportDoubleDto bean : dtos) {
					CustomReportSingleDto crsd = new CustomReportSingleDto();
					if (bean.getCount() > 0 && (i+"").equals(bean.getSingleName1())) {
						if ("last_cust_type_id".equals(groupByName2)) {
							for(OptionBean option : custTypeList) {
								if (option.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("last_option_Id".equals(groupByName2)){
							for(OptionBean option : options) {
								if (option.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("import_dept_id".equals(groupByName2)){
							OrgGroup orgGroup = orgGroupMap.get(bean.getSingleName2());
							if (orgGroup != null) {
								crsd.setSingleName(orgGroup.getGroupName());
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if ("res_group_id".equals(groupByName2)) {
							for(ResourceGroupBean rgb : resGroupList) {
								if (rgb.getResGroupId().equals(bean.getSingleName2())) {
									crsd.setSingleName(rgb.getGroupName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("company_trade".equals(groupByName2)) {
							for(OptionBean option : companyTrades) {
								if (option.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("opreate_type".equals(groupByName2)) {
							if ("11".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-到期未签约");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("16".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-到期未跟进");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("12".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-主动放弃");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("21".equals(bean.getSingleName2())) {
								crsd.setSingleName("签约客户-流失");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("23".equals(bean.getSingleName2())) {
								crsd.setSingleName("签约-到期未续签");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("4".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-沟通失败");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("5".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-信息错误");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("24".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-到期未联系");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if (groupByName2.contains("defined")) {
							CustFieldSet field = defineds1.get(groupByName2);
							if (field != null) {
								List<OptionBean> optionList = field.getOptionList();
								for (OptionBean optionBean : optionList) {
									if (optionBean.getOptionlistId().equals(bean.getSingleName2())) {
										crsd.setSingleName(optionBean.getOptionName());
										crsd.setCount(bean.getCount());
										singleList.add(crsd);
									}
								}
							}
						}
					}
				}
				if (singleList.size() > 0) {
					CustomReportDoubleShowDto crdsd = new CustomReportDoubleShowDto();
					if (i == 1) crdsd.setGroupByName("待分配资源");
					else if(i == 2) crdsd.setGroupByName("已分配资源");
					else if(i == 3) crdsd.setGroupByName("意向客户");
					else if(i == 4) crdsd.setGroupByName("签约客户");
					else if(i == 5) crdsd.setGroupByName("流失客户");
					else if(i == 6) crdsd.setGroupByName("沉默客户");
					else if(i == 7) crdsd.setGroupByName("公海客户");
					crdsd.setList(singleList);
					list.add(crdsd);
				}
			}
		}else if ("last_option_Id".equals(groupByName1)) {
			for(OptionBean option : options) {
				List<CustomReportSingleDto> singleList = new ArrayList<>();
				for(CustomReportDoubleDto bean : dtos) {
					CustomReportSingleDto crsd = new CustomReportSingleDto();
					if (bean.getCount() > 0 && option.getOptionlistId().equals(bean.getSingleName1())) {
						if ("last_cust_type_id".equals(groupByName2)) {
							for(OptionBean option1 : custTypeList) {
								if (option1.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option1.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("qStatus".equals(groupByName2)) {
							if ("1".equals(bean.getSingleName2())) {
								crsd.setSingleName("待分配资源");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("2".equals(bean.getSingleName2())){
								crsd.setSingleName("已分配资源");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("3".equals(bean.getSingleName2())){
								crsd.setSingleName("意向客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("4".equals(bean.getSingleName2())){
								crsd.setSingleName("签约客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("5".equals(bean.getSingleName2())){
								crsd.setSingleName("流失客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("6".equals(bean.getSingleName2())){
								crsd.setSingleName("沉默客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("7".equals(bean.getSingleName2())){
								crsd.setSingleName("公海客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if ("import_dept_id".equals(groupByName2)) {
							OrgGroup orgGroup = orgGroupMap.get(bean.getSingleName2());
							if (orgGroup != null) {
								crsd.setSingleName(orgGroup.getGroupName());
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if ("res_group_id".equals(groupByName2)) {
							for(ResourceGroupBean rgb : resGroupList) {
								if (rgb.getResGroupId().equals(bean.getSingleName2())) {
									crsd.setSingleName(rgb.getGroupName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("company_trade".equals(groupByName2)) {
							for(OptionBean option1 : companyTrades) {
								if (option1.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option1.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("opreate_type".equals(groupByName2)){
							if ("11".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-到期未签约");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("16".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-到期未跟进");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("12".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-主动放弃");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("21".equals(bean.getSingleName2())) {
								crsd.setSingleName("签约客户-流失");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("23".equals(bean.getSingleName2())) {
								crsd.setSingleName("签约-到期未续签");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("4".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-沟通失败");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("5".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-信息错误");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("24".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-到期未联系");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if (groupByName2.contains("defined")) {
							CustFieldSet field = defineds1.get(groupByName2);
							if (field != null) {
								List<OptionBean> optionList = field.getOptionList();
								for (OptionBean optionBean : optionList) {
									if (optionBean.getOptionlistId().equals(bean.getSingleName2())) {
										crsd.setSingleName(optionBean.getOptionName());
										crsd.setCount(bean.getCount());
										singleList.add(crsd);
									}
								}
							}
						}
					}
				}
				if (singleList.size() > 0) {
					CustomReportDoubleShowDto crdsd = new CustomReportDoubleShowDto();
					crdsd.setList(singleList);
					crdsd.setGroupByName(option.getOptionName());
					list.add(crdsd);
				}
			}
		}else if ("import_dept_id".equals(groupByName1)) {
			for(String key :orgGroupMap.keySet()) {
				List<CustomReportSingleDto> singleList = new ArrayList<>();
				for(CustomReportDoubleDto bean : dtos) {
					CustomReportSingleDto crsd = new CustomReportSingleDto();
					if (bean.getCount() > 0 && orgGroupMap.get(key).getGroupId().equals(bean.getSingleName1())) {
						if ("last_cust_type_id".equals(groupByName2)) {
							for(OptionBean option1 : custTypeList) {
								if (option1.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option1.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("qStatus".equals(groupByName2)) {
							if ("1".equals(bean.getSingleName2())) {
								crsd.setSingleName("待分配资源");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("2".equals(bean.getSingleName2())){
								crsd.setSingleName("已分配资源");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("3".equals(bean.getSingleName2())){
								crsd.setSingleName("意向客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("4".equals(bean.getSingleName2())){
								crsd.setSingleName("签约客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("5".equals(bean.getSingleName2())){
								crsd.setSingleName("流失客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("6".equals(bean.getSingleName2())){
								crsd.setSingleName("沉默客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("7".equals(bean.getSingleName2())){
								crsd.setSingleName("公海客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if ("last_option_Id".equals(groupByName2)){
							for(OptionBean option : options) {
								if (option.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("res_group_id".equals(groupByName2)) {
							for(ResourceGroupBean rgb : resGroupList) {
								if (rgb.getResGroupId().equals(bean.getSingleName2())) {
									crsd.setSingleName(rgb.getGroupName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("company_trade".equals(groupByName2)) {
							for(OptionBean option1 : companyTrades) {
								if (option1.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option1.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("opreate_type".equals(groupByName2)){
							if ("11".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-到期未签约");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("16".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-到期未跟进");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("12".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-主动放弃");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("21".equals(bean.getSingleName2())) {
								crsd.setSingleName("签约客户-流失");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("23".equals(bean.getSingleName2())) {
								crsd.setSingleName("签约-到期未续签");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("4".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-沟通失败");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("5".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-信息错误");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("24".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-到期未联系");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if (groupByName2.contains("defined")) {
							CustFieldSet field = defineds1.get(groupByName2);
							if (field != null) {
								List<OptionBean> optionList = field.getOptionList();
								for (OptionBean optionBean : optionList) {
									if (optionBean.getOptionlistId().equals(bean.getSingleName2())) {
										crsd.setSingleName(optionBean.getOptionName());
										crsd.setCount(bean.getCount());
										singleList.add(crsd);
									}
								}
							}
						}
					}
				}
				if (singleList.size() > 0 ) {
					CustomReportDoubleShowDto crdsd = new CustomReportDoubleShowDto();
					crdsd.setList(singleList);
					crdsd.setGroupByName(orgGroupMap.get(key).getGroupName());
					list.add(crdsd);
				}
			}
		}else if ("res_group_id".equals(groupByName1)) {
			for(ResourceGroupBean rgb : resGroupList) {
				List<CustomReportSingleDto> singleList = new ArrayList<>();
				for(CustomReportDoubleDto bean : dtos) {
					CustomReportSingleDto crsd = new CustomReportSingleDto();
					if (bean.getCount() > 0 && rgb.getResGroupId().equals(bean.getSingleName1())) {
						if ("last_cust_type_id".equals(groupByName2)) {
							for(OptionBean option1 : custTypeList) {
								if (option1.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option1.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("qStatus".equals(groupByName2)) {
							if ("1".equals(bean.getSingleName2())) {
								crsd.setSingleName("待分配资源");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("2".equals(bean.getSingleName2())){
								crsd.setSingleName("已分配资源");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("3".equals(bean.getSingleName2())){
								crsd.setSingleName("意向客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("4".equals(bean.getSingleName2())){
								crsd.setSingleName("签约客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("5".equals(bean.getSingleName2())){
								crsd.setSingleName("流失客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("6".equals(bean.getSingleName2())){
								crsd.setSingleName("沉默客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("7".equals(bean.getSingleName2())){
								crsd.setSingleName("公海客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if ("last_option_Id".equals(groupByName2)){
							for(OptionBean option : options) {
								if (option.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("import_dept_id".equals(groupByName2)) {
							OrgGroup orgGroup = orgGroupMap.get(bean.getSingleName2());
							if (orgGroup != null) {
								crsd.setSingleName(orgGroup.getGroupName());
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if ("company_trade".equals(groupByName2)) {
							for(OptionBean option1 : companyTrades) {
								if (option1.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option1.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("opreate_type".equals(groupByName2)){
							if ("11".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-到期未签约");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("16".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-到期未跟进");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("12".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-主动放弃");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("21".equals(bean.getSingleName2())) {
								crsd.setSingleName("签约客户-流失");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("23".equals(bean.getSingleName2())) {
								crsd.setSingleName("签约-到期未续签");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("4".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-沟通失败");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("5".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-信息错误");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("24".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-到期未联系");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if (groupByName2.contains("defined")) {
							CustFieldSet field = defineds1.get(groupByName2);
							if (field != null) {
								List<OptionBean> optionList = field.getOptionList();
								for (OptionBean optionBean : optionList) {
									if (optionBean.getOptionlistId().equals(bean.getSingleName2())) {
										crsd.setSingleName(optionBean.getOptionName());
										crsd.setCount(bean.getCount());
										singleList.add(crsd);
									}
								}
							}
						}
					}
				}
				if (singleList.size() > 0 ) {
					CustomReportDoubleShowDto crdsd = new CustomReportDoubleShowDto();
					crdsd.setList(singleList);
					crdsd.setGroupByName(rgb.getGroupName());
					list.add(crdsd);
				}
			}
		}else if ("company_trade".equals(groupByName1)) {
			for(OptionBean option : companyTrades) {
				List<CustomReportSingleDto> singleList = new ArrayList<>();
				for(CustomReportDoubleDto bean : dtos) {
					CustomReportSingleDto crsd = new CustomReportSingleDto();
					if (bean.getCount() > 0 && option.getOptionlistId().equals(bean.getSingleName1())) {
						if ("last_cust_type_id".equals(groupByName2)) {
							for(OptionBean option1 : custTypeList) {
								if (option1.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option1.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("qStatus".equals(groupByName2)) {
							if ("1".equals(bean.getSingleName2())) {
								crsd.setSingleName("待分配资源");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("2".equals(bean.getSingleName2())){
								crsd.setSingleName("已分配资源");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("3".equals(bean.getSingleName2())){
								crsd.setSingleName("意向客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("4".equals(bean.getSingleName2())){
								crsd.setSingleName("签约客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("5".equals(bean.getSingleName2())){
								crsd.setSingleName("流失客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("6".equals(bean.getSingleName2())){
								crsd.setSingleName("沉默客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("7".equals(bean.getSingleName2())){
								crsd.setSingleName("公海客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if ("last_option_Id".equals(groupByName2)){
							for(OptionBean option1 : options) {
								if (option1.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option1.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("import_dept_id".equals(groupByName2)) {
							OrgGroup orgGroup = orgGroupMap.get(bean.getSingleName2());
							if (orgGroup != null) {
								crsd.setSingleName(orgGroup.getGroupName());
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if ("res_group_id".equals(groupByName2)) {
							for(ResourceGroupBean rgb : resGroupList) {
								if (rgb.getResGroupId().equals(bean.getSingleName2())) {
									crsd.setSingleName(rgb.getGroupName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("opreate_type".equals(groupByName2)){
							if ("11".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-到期未签约");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("16".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-到期未跟进");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("12".equals(bean.getSingleName2())) {
								crsd.setSingleName("客户-主动放弃");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("21".equals(bean.getSingleName2())) {
								crsd.setSingleName("签约客户-流失");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("23".equals(bean.getSingleName2())) {
								crsd.setSingleName("签约-到期未续签");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("4".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-沟通失败");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("5".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-信息错误");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("24".equals(bean.getSingleName2())) {
								crsd.setSingleName("资源-到期未联系");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if (groupByName2.contains("defined")) {
							CustFieldSet field = defineds1.get(groupByName2);
							if (field != null) {
								List<OptionBean> optionList = field.getOptionList();
								for (OptionBean optionBean : optionList) {
									if (optionBean.getOptionlistId().equals(bean.getSingleName2())) {
										crsd.setSingleName(optionBean.getOptionName());
										crsd.setCount(bean.getCount());
										singleList.add(crsd);
									}
								}
							}
						}
					}
				}
				if (singleList.size() > 0) {
					CustomReportDoubleShowDto crdsd = new CustomReportDoubleShowDto();
					crdsd.setList(singleList);
					crdsd.setGroupByName(option.getOptionName());
					list.add(crdsd);
				}
			}
		}else if ("opreate_type".equals(groupByName1)) {
			String opreateType = "";
			for(int i = 1;i <= 8;i++) {
				List<CustomReportSingleDto> singleList = new ArrayList<>();
				if (i == 1) {opreateType = "11";}
				else if(i == 2) {opreateType = "16";}
				else if(i == 3) {opreateType = "12";}
				else if(i == 4) {opreateType = "21";}
				else if(i == 5) {opreateType = "23";}
				else if(i == 6) {opreateType = "4";}
				else if(i == 7) {opreateType = "5";}
				else if(i == 8) {opreateType = "24";}
				for(CustomReportDoubleDto bean : dtos) {
					CustomReportSingleDto crsd = new CustomReportSingleDto();
					if (bean.getCount() > 0 && opreateType.equals(bean.getSingleName1())) {
						if ("last_cust_type_id".equals(groupByName2)) {
							for(OptionBean option : custTypeList) {
								if (option.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("qStatus".equals(groupByName2)) {
							if ("1".equals(bean.getSingleName2())) {
								crsd.setSingleName("待分配资源");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("2".equals(bean.getSingleName2())){
								crsd.setSingleName("已分配资源");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("3".equals(bean.getSingleName2())){
								crsd.setSingleName("意向客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("4".equals(bean.getSingleName2())){
								crsd.setSingleName("签约客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("5".equals(bean.getSingleName2())){
								crsd.setSingleName("流失客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("6".equals(bean.getSingleName2())){
								crsd.setSingleName("沉默客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}else if ("7".equals(bean.getSingleName2())){
								crsd.setSingleName("公海客户");
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if ("last_option_Id".equals(groupByName2)){
							for(OptionBean option : options) {
								if (option.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("import_dept_id".equals(groupByName2)){
							OrgGroup orgGroup = orgGroupMap.get(bean.getSingleName2());
							if (orgGroup != null) {
								crsd.setSingleName(orgGroup.getGroupName());
								crsd.setCount(bean.getCount());
								singleList.add(crsd);
							}
						}else if ("res_group_id".equals(groupByName2)) {
							for(ResourceGroupBean rgb : resGroupList) {
								if (rgb.getResGroupId().equals(bean.getSingleName2())) {
									crsd.setSingleName(rgb.getGroupName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if ("company_trade".equals(groupByName2)) {
							for(OptionBean option : companyTrades) {
								if (option.getOptionlistId().equals(bean.getSingleName2())) {
									crsd.setSingleName(option.getOptionName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}else if (groupByName2.contains("defined")) {
							CustFieldSet field = defineds1.get(groupByName2);
							if (field != null) {
								List<OptionBean> optionList = field.getOptionList();
								for (OptionBean optionBean : optionList) {
									if (optionBean.getOptionlistId().equals(bean.getSingleName2())) {
										crsd.setSingleName(optionBean.getOptionName());
										crsd.setCount(bean.getCount());
										singleList.add(crsd);
									}
								}
							}
						}
					}
				}
				if (singleList.size() > 0) {
					CustomReportDoubleShowDto crdsd = new CustomReportDoubleShowDto();
					if (i == 1) {crdsd.setGroupByName("客户-到期未签约");opreateType = "11";}
					else if(i == 2) {crdsd.setGroupByName("客户-到期未跟进");opreateType = "16";}
					else if(i == 3) {crdsd.setGroupByName("客户-主动放弃");opreateType = "12";}
					else if(i == 4) {crdsd.setGroupByName("签约客户-流失");opreateType = "21";}
					else if(i == 5) {crdsd.setGroupByName("签约-到期未续签");opreateType = "23";}
					else if(i == 6) {crdsd.setGroupByName("资源-沟通失败");opreateType = "4";}
					else if(i == 7) {crdsd.setGroupByName("资源-信息错误");opreateType = "5";}
					else if(i == 8) {crdsd.setGroupByName("资源-到期未联系");opreateType = "24";}
					crdsd.setList(singleList);
					list.add(crdsd);
				}
			}
		}else if (groupByName1.contains("defined")) {
			CustFieldSet field = defineds1.get(groupByName1);
			if (field != null) {
				List<OptionBean> optionList = field.getOptionList();
				for (OptionBean optionBean : optionList) {
					List<CustomReportSingleDto> singleList = new ArrayList<>();
					for(CustomReportDoubleDto bean : dtos) {
						CustomReportSingleDto crsd = new CustomReportSingleDto();
						if (bean.getCount() > 0 && optionBean.getOptionlistId().equals(bean.getSingleName1())) {
							if ("last_cust_type_id".equals(groupByName2)) {
								for(OptionBean option1 : custTypeList) {
									if (option1.getOptionlistId().equals(bean.getSingleName2())) {
										crsd.setSingleName(option1.getOptionName());
										crsd.setCount(bean.getCount());
										singleList.add(crsd);
									}
								}
							}else if ("qStatus".equals(groupByName2)) {
								if ("1".equals(bean.getSingleName2())) {
									crsd.setSingleName("待分配资源");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}else if ("2".equals(bean.getSingleName2())){
									crsd.setSingleName("已分配资源");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}else if ("3".equals(bean.getSingleName2())){
									crsd.setSingleName("意向客户");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}else if ("4".equals(bean.getSingleName2())){
									crsd.setSingleName("签约客户");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}else if ("5".equals(bean.getSingleName2())){
									crsd.setSingleName("流失客户");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}else if ("6".equals(bean.getSingleName2())){
									crsd.setSingleName("沉默客户");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}else if ("7".equals(bean.getSingleName2())){
									crsd.setSingleName("公海客户");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}else if ("last_option_Id".equals(groupByName2)){
								for(OptionBean option1 : options) {
									if (option1.getOptionlistId().equals(bean.getSingleName2())) {
										crsd.setSingleName(option1.getOptionName());
										crsd.setCount(bean.getCount());
										singleList.add(crsd);
									}
								}
							}else if ("import_dept_id".equals(groupByName2)) {
								OrgGroup orgGroup = orgGroupMap.get(bean.getSingleName2());
								if (orgGroup != null) {
									crsd.setSingleName(orgGroup.getGroupName());
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}else if ("res_group_id".equals(groupByName2)) {
								for(ResourceGroupBean rgb : resGroupList) {
									if (rgb.getResGroupId().equals(bean.getSingleName2())) {
										crsd.setSingleName(rgb.getGroupName());
										crsd.setCount(bean.getCount());
										singleList.add(crsd);
									}
								}
							}else if ("company_trade".equals(groupByName2)) {
								for(OptionBean option : companyTrades) {
									if (option.getOptionlistId().equals(bean.getSingleName2())) {
										crsd.setSingleName(option.getOptionName());
										crsd.setCount(bean.getCount());
										singleList.add(crsd);
									}
								}
							}else if ("opreate_type".equals(groupByName2)){
								if ("11".equals(bean.getSingleName2())) {
									crsd.setSingleName("客户-到期未签约");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}else if ("16".equals(bean.getSingleName2())) {
									crsd.setSingleName("客户-到期未跟进");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}else if ("12".equals(bean.getSingleName2())) {
									crsd.setSingleName("客户-主动放弃");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}else if ("21".equals(bean.getSingleName2())) {
									crsd.setSingleName("签约客户-流失");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}else if ("23".equals(bean.getSingleName2())) {
									crsd.setSingleName("签约-到期未续签");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}else if ("4".equals(bean.getSingleName2())) {
									crsd.setSingleName("资源-沟通失败");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}else if ("5".equals(bean.getSingleName2())) {
									crsd.setSingleName("资源-信息错误");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}else if ("24".equals(bean.getSingleName2())) {
									crsd.setSingleName("资源-到期未联系");
									crsd.setCount(bean.getCount());
									singleList.add(crsd);
								}
							}
						}
					}
					if (singleList.size() > 0) {
						CustomReportDoubleShowDto crdsd = new CustomReportDoubleShowDto();
						crdsd.setList(singleList);
						crdsd.setGroupByName(optionBean.getOptionName());
						list.add(crdsd);
					}
				}
			}
		}
		return list;
	}
	

	

	public List<CustomReportSingleDto> makeCustomReport1(ResCustReportDto dto) throws Exception {
		ShiroUser user = ShiroUtil.getShiroUser();
		String orgId = user.getOrgId();
		Class<?> classType = dto.getClass();
		List<CustFieldSet> defineds = new ArrayList<>();
		if (user.getIsState() == 1) {
			defineds = cachedService.getComFiledSets(user.getOrgId());
		}else if(user.getIsState() == 0) {
			defineds = cachedService.getPersonFiledSets(user.getOrgId());
		}
		Map<String,Object> map = new HashMap<>();
		List<String> fieldCodes = new ArrayList<String>();
    	List<String> fieldDatas = new ArrayList<String>();
		a:for (int i = 1; i < 16; i++) {
			String BaseGetDefined = "getDefined";
			String BaseSetDefined = "setDefined";
			String definedName = "defined"+i;
			b:for (CustFieldSet custFieldSet : defineds) {
				if (custFieldSet.getFieldCode().equals(definedName) && custFieldSet.getDataType().equals(4)) {
					String getDefinedMethodName = BaseGetDefined + i;
					String getDefinedMatchMethodName = BaseGetDefined + i + "Match";
					String setDefinedMethodName = BaseSetDefined + i;
					String setDefinedMatchMethodName = BaseSetDefined + i + "Match";
					Method getDefined = classType.getMethod(getDefinedMethodName,new Class[] {});
					Method getDefinedMatch= classType.getMethod(getDefinedMatchMethodName,new Class[] {});
					Method setDefined = classType.getMethod(setDefinedMethodName,String.class);
					Method setDefinedMatch= classType.getMethod(setDefinedMatchMethodName,Integer.class);
					String value = (String)getDefined.invoke(dto, new Object[] {});
					Integer match = (Integer)getDefinedMatch.invoke(dto, new Object[] {});
					if (value != null && !"".equals(value) && match != null){
						fieldCodes.add(definedName);
						fieldDatas.addAll(Arrays.asList(value.split(",")));
					}
					setDefined.setAccessible(true);
					setDefined.invoke(dto, (Object)null);
					setDefinedMatch.setAccessible(true);
					setDefinedMatch.invoke(dto, (Object)null);
					break b;
				}
			}
		}
    	if(fieldCodes.size() > 0){
    		map.put("fieldCodes", fieldCodes);
    		map.put("fieldDatas", fieldDatas);
    		map.put("orgId", user.getOrgId());
    	}
		if(map.size() > 0){
			List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(map);
			if(custIds.size() > 0){
				dto.setCustIds(custIds);
			}else{
				return null;
			}
		}
		List<CustomReportSingleDto> list = resCustInfoMapper.makeCustomReport1(dto);
		String groupByName1 = dto.getGroupByType1();
		List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO,user.getOrgId());
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
		//List<Map<String, Object>> groups = cachedService.getResGroupList1(user.getOrgId());
		List<OptionBean> companyTrades = cachedService.getOptionList("companyTrade",user.getOrgId());
		Map<String, OrgGroup> orgGroupMap = cachedService.getOrgGroupMap(user.getOrgId());
		List<ResourceGroupBean> resGroupList= resourceGroupMapper.findOnlyResGroup(orgId);
		Map<String, CustFieldSet> defineds1 = new HashMap<>();
		if (defineds.size() > 0) {
			for (CustFieldSet fieldSet : defineds) {
				if (fieldSet.getDataType() == 3 && fieldSet.getOptionList().size() > 0 && fieldSet.getFieldCode().contains("defined")) {
					defineds1.put(fieldSet.getFieldCode(),fieldSet);
				}
			}
		}
		List<CustomReportSingleDto> list1 = new ArrayList<>();
		if ("last_cust_type_id".equals(groupByName1)) {
			a:for(CustomReportSingleDto crsd : list) {
				CustomReportSingleDto crsd1 = new CustomReportSingleDto();
				b:for(OptionBean option :custTypeList) {
					if (crsd.getCount() > 0 && option.getOptionlistId().equals(crsd.getSingleName())) {
						crsd1.setSingleName(option.getOptionName());
						crsd1.setCount(crsd.getCount());
						list1.add(crsd1);
						break b;
					}
				}
			}
		}else if ("qStatus".equals(groupByName1)) {
			for(CustomReportSingleDto crsd : list) { 
				CustomReportSingleDto crsd1 = new CustomReportSingleDto();
				if (crsd.getCount() > 0 && "1".equals(crsd.getSingleName())) {crsd1.setSingleName("待分配资源");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
				else if (crsd.getCount() > 0 && "2".equals(crsd.getSingleName())) {crsd1.setSingleName("已分配资源");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
				else if (crsd.getCount() > 0 && "3".equals(crsd.getSingleName())) {crsd1.setSingleName("意向客户");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
				else if (crsd.getCount() > 0 && "4".equals(crsd.getSingleName())) {crsd1.setSingleName("签约客户");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
				else if (crsd.getCount() > 0 && "5".equals(crsd.getSingleName())) {crsd1.setSingleName("流失客户");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
				else if (crsd.getCount() > 0 && "6".equals(crsd.getSingleName())) {crsd1.setSingleName("沉默客户");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
				else if (crsd.getCount() > 0 && "7".equals(crsd.getSingleName())) {crsd1.setSingleName("公海客户");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
			}
		}else if ("last_option_Id".equals(groupByName1)) {
			a:for(CustomReportSingleDto crsd : list) {
				CustomReportSingleDto crsd1 = new CustomReportSingleDto();
				b:for(OptionBean option :options) {
					if (crsd.getCount() > 0 && option.getOptionlistId().equals(crsd.getSingleName())) {
						crsd1.setSingleName(option.getOptionName());
						crsd1.setCount(crsd.getCount());
						list1.add(crsd1);
						break b;
					}
				}
			}
		}else if ("import_dept_id".equals(groupByName1)) {
			for(CustomReportSingleDto crsd : list) {
				OrgGroup orgGroup = orgGroupMap.get(crsd.getSingleName());
				if (crsd.getCount() > 0 && orgGroup != null) {
					CustomReportSingleDto crsd1 = new CustomReportSingleDto();
					crsd1.setSingleName(orgGroup.getGroupName());
					crsd1.setCount(crsd.getCount());
					list1.add(crsd1);
				}
			}
		}else if ("res_group_id".equals(groupByName1)) {
			a:for(CustomReportSingleDto crsd : list) {
				CustomReportSingleDto crsd1 = new CustomReportSingleDto();
				b:for(ResourceGroupBean rgb : resGroupList) {
					if (crsd.getCount() > 0 && rgb.getResGroupId().equals(crsd.getSingleName())) {
						crsd1.setSingleName(rgb.getGroupName());
						crsd1.setCount(crsd.getCount());
						list1.add(crsd1);
						break b;
					}
				}
			}
		}else if ("company_trade".equals(groupByName1)) {
			a:for(CustomReportSingleDto crsd : list) {
				CustomReportSingleDto crsd1 = new CustomReportSingleDto();
				b:for(OptionBean option : companyTrades) {
					if (crsd.getCount() > 0 && option.getOptionlistId().equals(crsd.getSingleName())) {
						crsd1.setSingleName(option.getOptionName());
						crsd1.setCount(crsd.getCount());
						list1.add(crsd1);
						break b;
					}
				}
			}
		}else if("opreate_type".equals(groupByName1)) {
			for(CustomReportSingleDto crsd : list) { 
				CustomReportSingleDto crsd1 = new CustomReportSingleDto();
				if (crsd.getCount() > 0 && "11".equals(crsd.getSingleName())) {crsd1.setSingleName("客户-到期未签约");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
				else if (crsd.getCount() > 0 && "16".equals(crsd.getSingleName())) {crsd1.setSingleName("客户-到期未跟进");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
				else if (crsd.getCount() > 0 && "12".equals(crsd.getSingleName())) {crsd1.setSingleName("客户-主动放弃");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
				else if (crsd.getCount() > 0 && "21".equals(crsd.getSingleName())) {crsd1.setSingleName("签约客户-流失");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
				else if (crsd.getCount() > 0 && "23".equals(crsd.getSingleName())) {crsd1.setSingleName("签约-到期未续签");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
				else if (crsd.getCount() > 0 && "4".equals(crsd.getSingleName())) {crsd1.setSingleName("资源-沟通失败");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
				else if (crsd.getCount() > 0 && "5".equals(crsd.getSingleName())) {crsd1.setSingleName("资源-信息错误");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
				else if (crsd.getCount() > 0 && "24".equals(crsd.getSingleName())) {crsd1.setSingleName("资源-到期未联系");crsd1.setCount(crsd.getCount());list1.add(crsd1);}
			}
		}else if (groupByName1.contains("defined")) {
			CustFieldSet field = defineds1.get(groupByName1);
			if (field != null) {
				List<OptionBean> optionList = field.getOptionList();
				a:for(CustomReportSingleDto crsd : list) {
					CustomReportSingleDto crsd1 = new CustomReportSingleDto();
					b:for(OptionBean option : optionList) {
						if (crsd.getCount() > 0 && option.getOptionlistId().equals(crsd.getSingleName())) {
							crsd1.setSingleName(option.getOptionName());
							crsd1.setCount(crsd.getCount());
							list1.add(crsd1);
							break b;
						}
					}
				}
			}
		}
		return list1;
	}
	
	
	//新增资源
	public void addMyRes(ResCustInfoBean resInfo,String orgId, String account){
		resInfo.setLifeCode(SysBaseModelUtil.getModelId());
		String resCustId = SysBaseModelUtil.getModelId();
		Date optDate = new Date();
		Org org = orgMapper.getByPrimaryKey(orgId);
		int resType=0;
        if (org != null) {
    		 resType = org.getState();
         }
		String reqId = SysBaseModelUtil.getModelId();
		logger.debug("addMyRes reqId=" + reqId + ",account=" + resInfo.getOwnerAcc() + ",resInfo=" + JsonUtil.getJsonString(resInfo));
		try {
			
			if ("".equals(resInfo.getResGroupId()) || resInfo.getResGroupId() == null) {
				String resGroupId = comResourceGroupService.saveUnGroup(orgId);
				resInfo.setResGroupId(resGroupId);
			}
			
			if ("".equals(resInfo.getImportDeptId()) || resInfo.getImportDeptId() == null) {
				String myDeptId = orgGroupMapper.finMyDept(orgId,account);
				resInfo.setImportDeptId(myDeptId);
			}
			ResCustInfoDetailBean resDetailInfo = new ResCustInfoDetailBean();
			resInfo.setResCustId(resCustId);

			String fax = resInfo.getFax() == null ? "" : resInfo.getFax();
			// 去除空格
			resInfo.setUnithome(StringUtils.isNotBlank(resInfo.getUnithome()) ? resInfo.getUnithome().trim() : "");
			resInfo.setFax(fax.replace(",", ""));
			resInfo.setOrgId(orgId);
			resInfo.setOpreateType(AppConstant.OPREATE_TYPE2.intValue());
			resInfo.setStatus(AppConstant.STATUS_2.intValue());
			resInfo.setType(AppConstant.CUST_TYPE1.intValue());
			resInfo.setIsDel(0);
			resInfo.setInputStatus(0);
			resInfo.setFilterType(AppConstant.FILTER_TYPE0.intValue());
			resInfo.setInputAcc(account);
			resInfo.setOwnerAcc(account);
			resInfo.setOwnerStartDate(optDate);
			resInfo.setInputDate(optDate);

			// 常用电话
			resInfo.setMobilephone(toCheckPhone(formatPhone(resInfo.getMobilephone(), orgId))); // 判断是否是固话，是，再判断是否系统设置要添加区号。
			// 备用电话
			resInfo.setTelphone(toCheckPhone(formatPhone(resInfo.getTelphone(), orgId))); //
			resInfo.setLifeCode(GuidUtil.getId());
			resInfo.setSource(5);//在线表单
			resInfo.setOwnerActionDate(resInfo.getActionDate());
			if (resType == 1) {// 企业资源
				resInfo.setState(1);// 企业资源
				resInfo.setName(StringUtils.isNotBlank(resInfo.getName()) ? resInfo.getName().trim() : "");
				resInfo.setCompany(StringUtils.isNotBlank(resInfo.getCompany()) ? resInfo.getCompany().trim() : "");
				
				resDetailInfo.setOrgId(orgId);
				resDetailInfo.setInputtime(optDate);
				resDetailInfo.setRciId(resCustId);
				resDetailInfo.setName(StringUtils.isNotBlank(resInfo.getMainLinkman()) ? resInfo.getMainLinkman().trim() : "");
				resDetailInfo.setTscidId(SysBaseModelUtil.getModelId());
				resDetailInfo.setIsDefault(1);
				resDetailInfo.setIsDel(0);
				resDetailInfo.setTelphone(toCheckPhone(resInfo.getMobilephone()));
				resDetailInfo.setTelphonebak(toCheckPhone(resInfo.getTelphone()));
				createComRes(resInfo, resDetailInfo, "1");
			} else {// 个人资源
				resInfo.setState(0);// 个人资源
				resInfo.setCompany(StringUtils.isNotBlank(resInfo.getName()) ? resInfo.getName().trim() : "");
				resInfo.setName(resInfo.getMainLinkman());
				resInfo.setCompanyAddr(resInfo.getAddress());
				resInfo.setAddress("");
				create(resInfo);
			}
			
			    Map<String,User> userMap=cachedService.getOrgUserMapByAccount(orgId);
			    User u=userMap.get(account);
				LogBean logBean =new LogBean(orgId, account, u.getUserName(), OperateEnum.LOG_ADD.getCode(), OperateEnum.LOG_ADD.getDesc(), 1,SysBaseModelUtil.getModelId()); 
				logBean.setModuleId(AppConstant.Module_id17);
				logBean.setModuleName(AppConstant.Module_Name17);
				logBean.setOperateId(AppConstant.Operate_id12);
				logBean.setOperateName(AppConstant.Operate_Name12);
				logBean.setContent((resInfo.getCompany() == null || "".equals(resInfo.getCompany()))? resInfo.getName() : resInfo.getCompany());				
				Map<String, Object> logMap = new HashMap<String, Object>();
				logMap.put(reqId, "");
				logCustInfoService.addTableStoreLog(logBean, logMap);
				logContactDayDataService.addLogContactDayData(ContactUtil.ADD_RES, orgId, resCustId, account, null, null);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		
	}
    
	
	/**
	 * 格式化固话号码 说明：首位加0及去除区号后的“-”字符
	 *
	 * @param phone
	 * @param request
	 * @return
	 * @create 2015年11月17日 下午3:14:52 lixing
	 * @history
	 */
	public String formatPhone(String phone,String orgId) {
		if (phone == null || "".equals(phone)) {
			return "";
		} else {
			phone = phone.trim();
		}
		String rstPhnoe = phone;
		if (StringUtils.isBlank(phone)) {
			return "";
		}
		String tel1 = "^\\d{3,4}-\\d{3,4}-\\d{3,4}$";
		Pattern p1 = Pattern.compile(tel1);
		Matcher m1 = p1.matcher(rstPhnoe);
		if (m1.matches()) {
			rstPhnoe = rstPhnoe.replaceAll("-", "");
		} else {
			if (rstPhnoe.length() >= 5 && rstPhnoe.substring(3, 5).contains("-")) {
				rstPhnoe = rstPhnoe.replaceFirst("-", "");
			}
			if (phone.length() == 7 || phone.length() == 8) {
				List<DataDictionaryBean> openList = cachedService.getDirList(AppConstant.DATA18, orgId);
				String isOpen = openList.get(0).getDictionaryValue();
				isOpen = isOpen == null ? "0" : isOpen;
				if (isOpen.equals("1")) {
					List<DataDictionaryBean> itemValList = cachedService.getDirList(AppConstant.DATA19, orgId);
					String px = itemValList.get(0).getDictionaryValue();
					px = px == null ? "0" : px;
					if (px.equals(rstPhnoe.substring(0, 4))) { //防止重复
						rstPhnoe = rstPhnoe.substring(4);
					}
					rstPhnoe = px + rstPhnoe;
				}
			}
		}
		return rstPhnoe;
	}
	

	
	/**
	* 把12位非010开头的其他以01开头的0手机号码，抹零处理，对固话区号不做处理
	* @param str
	* 手机号
	* @return 返回抹零后的手机号
	*/
	public static  String toCheckPhone(String phone)
	{
		try {
		Pattern pattern = Pattern.compile("^([0-9]{3}-?[0-9]{8})|([0-9]{4}-?[0-9]{7})$");
		Matcher matcher = pattern.matcher(phone);
		boolean b = matcher.matches();
		
		if(b==true){
//			System.out.println("座机");
		}else if(b==false){
//			System.out.println("不是座机");
	    if(phone.length()==12){
		
		if(!phone.substring(0, 3).endsWith("010")){
			if(phone.startsWith("01")){
				phone=phone.substring(1);
			}
		}
		}
		}

      
		} catch (Exception e) {
			// TODO: handle exception
		}
		 return phone;
	}

	/**
	 * 资源-放弃客户
	 *
	 * @param user
	 * @param ids
	 * @param operateType
	 * @param reason
	 * @create 2016年6月28日 上午11:06:06 lixing
	 * @history
	 */
	public void givePublicUp(ShiroUser user, List<String> ids, Short operateType, String reason) {
		resCustInfoMapper.updateBatchCust(AppConstant.STATUS_4, user.getAccount(), ids, operateType, user.getOrgId(), reason);
		resCustInfoMapper.cleanCallCustCountByCustId(ids, user.getOrgId());
		custSaleChanceMapper.updateIsDelByCustIds(user.getOrgId(), ids);
		//记录日志
		//taskExecutor.execute(new CustGiveUpThread(ids, user,reason,operateType,module));
		logBatchInfoService.savePublicLog(ids);

	}
	
	public Integer findNoChooseCount(ResCustInfoDto custInfoDto){
		return resCustInfoMapper.findNoChooseCount(custInfoDto);
	}
	
	/**
	 * 批量修改淘到客户时间
	 * 
	 * @param amoytocustomerDate
	 * @param orgId
	 * @param ids
	 */
	public void updateAmoytocustomerDate(List<ResCustInfoDto> list) {
		resCustInfoMapper.updateAmoytocustomerDate(list);
	}
}
