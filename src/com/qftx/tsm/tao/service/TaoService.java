package com.qftx.tsm.tao.service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.Org;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.util.*;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.pay.api.PayApiFacade;
import com.qftx.pay.api.PayApiRequest;
import com.qftx.pay.api.PayApiTypeEnum;
import com.qftx.tsm.cust.bean.*;
import com.qftx.tsm.cust.dao.*;
import com.qftx.tsm.cust.dto.*;
import com.qftx.tsm.cust.service.ComResourceService;
import com.qftx.tsm.cust.service.ResCustEventService;
import com.qftx.tsm.cust.service.ResCustInfoDetailService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.cust.tao.TaoCacheListUtil;
import com.qftx.tsm.follow.bean.CustFollowBean;
import com.qftx.tsm.follow.dao.CustFollowMapper;
import com.qftx.tsm.follow.dao.CustSaleChanceMapper;
import com.qftx.tsm.follow.dto.CustFollowDto;
import com.qftx.tsm.log.dao.LogCustInfoMapper;
import com.qftx.tsm.log.service.LogContactDayDataService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.log.util.ContactUtil;
import com.qftx.tsm.main.bean.MainInfoBean;
import com.qftx.tsm.main.dao.ContactDayDataMapper;
import com.qftx.tsm.main.service.ContactDayDataService;
import com.qftx.tsm.main.service.MainInfoService;
import com.qftx.tsm.main.service.MainService;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dao.OptionMapper;
import com.qftx.tsm.plan.facade.PlanFactService;
import com.qftx.tsm.plan.facade.enu.PlanResCR;
import com.qftx.tsm.plan.facade.enu.PlanSignCR;
import com.qftx.tsm.report.bean.ResourceReportBean;
import com.qftx.tsm.report.service.*;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class TaoService {
	transient Logger logger = Logger.getLogger(TaoService.class);
	@Autowired
	transient private ComResourceMapper comResourceMapper;
	// @Autowired
	// private TaoTagMapper taoTagMapper;
	@Autowired		
	private CachedService cachedService;
	@Autowired
	private MainInfoService mainInfoService;
	@Autowired
	transient private ResourceGroupMapper resourceGroupMapper;
	@Resource
	transient private ResOptorMapper resOptorMapper;
	@Autowired
	private CustFollowMapper custFollowMapper;
	@Autowired
	private CustOptorMapper custOptorMapper;
	@Autowired
	private TsmCustGuideMapper tsmCustGuideMapper;
	@Autowired
	private TsmCustGuideProcMapper tsmCustGuideProcMapper;
	@Autowired
	private PlanFactService planFactService;
	@Autowired
	private ResCustEventService resCustEventService;
	@Autowired
	private LogCustInfoMapper logCustInfoMapper;
	@Autowired
	private RankingReportService rankingReportService;
	@Autowired
	private OptionMapper optionMapper;
	@Autowired
	private ResCustInfoDetailService resCustInfoDetailService;
	@Autowired
	private CustInfoNalysisService custInfoNalysisService;
	@Autowired
	private ContactDayDataMapper contactDayDataMapper;
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private ThreadPoolTaskExecutor taskExecutor;
	@Autowired
	private ResCustInfoMapper resCustInfoMapper;
	@Autowired
	private ContactDayDataService contactDayDataService;
	@Autowired
	private SilenceCustService silenceCustService;
	@Autowired
	private TsmMessageService tsmMessageService;
	@Autowired
	private WxBindMapper wxBindMapper;
	@Autowired
	private LogCustInfoService logCustInfoService;
	@Autowired
	private MainService mainService;
	@Autowired
	private LogUserOperateService logUserOperateService;
	@Autowired
	private TsmReportWillService tsmReportWillService;
	@Autowired 
	private CustDefinedDataMapper custDefinedDataMapper;
	@Autowired
	private LogContactDayDataService logContactDayDataService;
	@Autowired
	private ResourceReportService resourceReportService;
	@Autowired
	private PayApiFacade payApiFacade;
	@Autowired
	private CustLabelCodeDataMapper custLabelCodeDataMapper;
	@Autowired
	private CustSaleChanceMapper custSaleChanceMapper;
	@Autowired
	private ComResourceService comResourceService;


	public CallListDto saveTaoCust(String pool, String reqId, HttpServletRequest request, String resourceId, String resourceGroupId, String orderType,
			int isConcat, String orgId, String account) {
		CallListDto listDto = new CallListDto();
		boolean isSave = false;
		TaoDto taoDao = null;
		String currResId = "";
		ResCustDto resCustDto = null;
		List<GroupDto> groupDtoList = new ArrayList<GroupDto>();// 组列表
		List<ResourceGroupBean> groupList = new ArrayList<ResourceGroupBean>();// 组列表
		List<TaoResDto> otherList = new ArrayList<TaoResDto>();
		String defaultOrder = ConfigInfoUtils.getStringValue("tao_order");// 默认按时间倒序排序
		TaoTagBean tagBean = cachedService.getLoction(orgId, account);
		try {
			if (tagBean != null) {
				isSave = true;
			}
			tagBean = getTag(tagBean, defaultOrder, resourceGroupId, orderType, isConcat, orgId, account);
			tagBean.setPool(pool);
			if ("".equals(resourceId) || resourceId == null) { // 其他页面链接
				if (!isSave) {
					taoDao = firstLog(tagBean, orgId, account, pool);
					if (taoDao != null && taoDao.getTaoResDtos().size() > 0) {
						resourceId = taoDao.getResId();
					}
					tagBean.setResourceId(resourceId);
				} else {
					taoDao = getTaoDto(orgId, account, tagBean, pool);
					if (taoDao != null && taoDao.getTaoResDtos().size() > 0) {
						resourceId = taoDao.getResId();
					}
					tagBean.setAccount(account);
					tagBean.setOrgId(orgId);
					tagBean.setResourceId(resourceId);
				}
			} else {
				if (!isSave) {
					taoDao = firstLog(tagBean, orgId, account, pool);
					tagBean.setResourceId(resourceId);
				} else {
					taoDao = getTaoDto(orgId, account, tagBean, pool);
					tagBean.setAccount(account);
					tagBean.setOrgId(orgId);
					tagBean.setResourceId(resourceId);
				}
			}
			if (taoDao != null && taoDao.getTaoResDtos().size() > 0) {
				Map<String, String> resMap = new HashMap<String, String>();
				resMap.put("resId", resourceId);
				resMap.put("orgId", orgId);
				resCustDto = comResourceMapper.findResById(resMap);
				// 处理签约保存后，跳转问题
				if (resCustDto != null && (resCustDto.getStatus() == 2 || resCustDto.getStatus() == 3) && resCustDto.getType() == 1) {
					otherList = TaoCacheListUtil.getList(taoDao.getTaoResDtos(), taoDao.getUpId());
				} else {
					Integer taoNum =Integer.valueOf((ConfigInfoUtils.getStringValue("tao_num_cache")));
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("ownerAcc", account);
					map.put("orgId", orgId);
					map.put("resourceGroupId", tagBean.getGroupId());
					map.put("orderType", tagBean.getOrderType());
					map.put("isConcat", tagBean.getIsConcat());
					map.put("startLength", 0);
					map.put("length", taoNum);
					taoDao = getTaoDto(map, orgId, account, tagBean, pool);
					if (taoDao != null && taoDao.getTaoResDtos().size() > 0) {
						otherList = TaoCacheListUtil.getList(taoDao.getTaoResDtos(), taoDao.getUpId());
						if (taoDao != null && taoDao.getTaoResDtos().size() > 0) {
							resourceId = taoDao.getResId();
						}
					}
				}
				currResId = taoDao.getResId();
			}
			groupList = cachedService.getResGroupList(orgId);
			if (groupList != null && groupList.size() > 0) {
				for (ResourceGroupBean resourceGroupBean : groupList) {
					GroupDto bean = new GroupDto();
					bean.setGroupId(resourceGroupBean.getResGroupId());
					bean.setGroupName(resourceGroupBean.getGroupName());
					groupDtoList.add(bean);
				}
			}
			cachedService.setLocation(orgId, account, tagBean);
			listDto.setGroupList(groupDtoList);
			listDto.setOtherList(otherList);
			listDto.setCurrResId(currResId);
			listDto.setCustId(resourceId);
			listDto.setResourceGroupId(tagBean.getGroupId());
			listDto.setIsConcat(tagBean.getIsConcat() == null ? "0" : tagBean.getIsConcat() + "");
			listDto.setOrderType(tagBean.getOrderType());

			/*
			 * request.setAttribute("groupList", groupList);
			 * request.setAttribute("custId", resourceId);
			 * request.setAttribute("currResId", currResId);
			 * request.setAttribute("otherList", otherList);
			 * request.setAttribute("resourceGroupId", tagBean.getGroupId());
			 * request.setAttribute("isConcat", tagBean.getIsConcat());
			 * request.setAttribute("orderType", tagBean.getOrderType());
			 * request.setAttribute("pool", pool);
			 */
		} catch (Exception e) {
			logger.error("reqId=" + reqId + e.getMessage(), e);
			throw new SysRunException(e);
		}
		return listDto;
	}

	/**
	 * 首次登陆时
	 * 
	 * @param tagBean
	 * @param user
	 * @return
	 * @create 2016年1月19日 上午9:21:20 wuwei
	 * @history
	 */
	public TaoDto firstLog(TaoTagBean tagBean, String orgId, String account, String pool) {
		tagBean.setGroupId("today");
		TaoDto taoDao = getTaoDto(orgId, account, tagBean, pool);
		if (taoDao == null) {
			tagBean.setGroupId("temp");
			taoDao = getTaoDto(orgId, account, tagBean, pool);
		}
		if (taoDao == null) {
			tagBean.setGroupId("all");
			taoDao = getTaoDto(orgId, account, tagBean, pool);
		}
		return taoDao;
	}

	/**
	 * 获取淘客户资源
	 * 
	 * @return
	 * @create 2015年12月31日 上午11:20:00 wuwei
	 * @history
	 */
	public TaoDto getTaoDto(String orgId, String account, TaoTagBean tagBean, String pool) {
		Integer taoNum =Integer.valueOf((ConfigInfoUtils.getStringValue("tao_num_cache")));
		Map<String, Object> map = new HashMap<String, Object>();
		TaoDto taoDto = null;
		taoDto = cachedService.getTaoDto(orgId, account, pool);
		if (taoDto == null || taoDto.getTaoResDtos().size() <= 0) {
			map.put("ownerAcc", account);
			map.put("orgId", orgId);
			map.put("resourceGroupId", tagBean.getGroupId());
			map.put("orderType", tagBean.getOrderType());
			map.put("isConcat", tagBean.getIsConcat());
			map.put("startLength", 0);
			map.put("length", taoNum);
			taoDto = getTaoDto(map, orgId, account, tagBean, pool);
		}
		return taoDto;
	}

	/**
	 * 从数据库获取淘资源
	 * 
	 * @param map
	 * @param user
	 * @param tagBean
	 * @return
	 * @create 2016年1月4日 下午2:18:37 wuwei
	 * @history
	 */
	public TaoDto getTaoDto(Map<String, Object> map, String orgId, String account, TaoTagBean tagBean, String pool) {
		TaoDto taoDto = null;
		List<TaoResDto> taoResDtos = null;
		try {
			Integer pageNum =Integer.valueOf((ConfigInfoUtils.getStringValue("tao_num_page")));
			String isEffect = cachedService.getDirList(AppConstant.DATA_40020, orgId).get(0).getDictionaryValue();
			int open = (isEffect == null || "".equals(isEffect)) ? 0 : new Integer(isEffect);
			map.put("open", open);
			int totalResult = 0;
			if ("1".equals(pool)) {
				totalResult = comResourceService.getResListCount(map);
			} else {
				totalResult = comResourceMapper.findDelayResCountList(map);
			}

			logger.debug("********totalResult =" + totalResult + "查询淘客户资源参数：" + JSON.toJSONString(map) + "*******");
			String upId = "";
			String downId = "";
			if (totalResult > 0) {
				taoDto = new TaoDto();
				if ("1".equals(pool)) {
					taoResDtos = comResourceService.getTaoResList(map);
				} else {
					taoResDtos = comResourceMapper.findDelayResList(map);
				}
				taoDto.setTaoResDtos(taoResDtos);
				if (taoDto.getTaoResDtos() != null && taoDto.getTaoResDtos().size() > 0) {
					upId = taoDto.getTaoResDtos().get(0).getResId();
					if (taoDto.getTaoResDtos().size() <= pageNum) {
						downId = taoDto.getTaoResDtos().get(taoDto.getTaoResDtos().size() - 1).getResId();
					} else {
						downId = taoDto.getTaoResDtos().get(pageNum - 1).getResId();
					}
					taoDto.setTaoResDtos(taoResDtos);
					taoDto.setUpId(upId);
					taoDto.setResId(upId);
					taoDto.setDownId(downId);
					cachedService.setTaoDto(orgId, account, taoDto, pool);
				}
			} else {
				cachedService.removeTaoDto(orgId, account, pool);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysRunException(e);
		}
		return taoDto;
	}

	/**
	 * 获取定位标签tag对象
	 * 
	 * @param tag
	 * @param defaultOrder
	 * @param resourceGroupId
	 * @return
	 * @create 2015年4月25日 下午4:10:02 wuwei
	 * @history
	 */
	private TaoTagBean getTag(TaoTagBean tag, String defaultOrder, String resourceGroupId, String orderType, int isConcat, String orgId, String account) {
		if (tag == null) {
			tag = new TaoTagBean();
			tag.setGroupId("toady");// 今日
			tag.setOrderType(defaultOrder);
			tag.setIsConcat(2);// 全部
			tag.setAccount(account);
			tag.setOrgId(orgId);
			return tag;
		} else {
			if ("".equals(tag.getGroupId()) || tag.getGroupId() == null) {
				tag.setGroupId("all");
			}
			if ("".equals(orderType) || orderType == null) {
				tag.setOrderType(defaultOrder);
			}
		}
		return tag;
	}

	public CallListDto updateTaoTag(String pool, String reqId, HttpServletRequest request, TaoTagBean tagBean, String orgId, String account) {
		Integer taoNum =Integer.valueOf((ConfigInfoUtils.getStringValue("tao_num_cache")));
		String resId = "";
		CallListDto listDto = new CallListDto();
		Map<String, Object> map = new HashMap<String, Object>();
		List<GroupDto> groupDtoList = new ArrayList<GroupDto>();// 组列表
		List<ResourceGroupBean> groupList = new ArrayList<ResourceGroupBean>();// 组列表
		List<TaoResDto> otherList = new ArrayList<TaoResDto>();

		try {
			map.put("ownerAcc", account);
			map.put("orgId", orgId);
			map.put("resourceGroupId", tagBean.getGroupId());
			map.put("orderType", tagBean.getOrderType());
			map.put("isConcat", tagBean.getIsConcat());
			map.put("startLength", 0);
			map.put("length", taoNum);
			map.put("pool", pool);
			TaoDto taoDto = getTaoDto(map, orgId, account, tagBean, pool);
			String currResId = "";
			if (taoDto != null && taoDto.getTaoResDtos().size() > 0) {
				otherList = TaoCacheListUtil.getList(taoDto.getTaoResDtos(), taoDto.getUpId());
				resId = taoDto.getTaoResDtos().get(0).getResId();
				currResId = otherList.get(0).getResId();
			}
			tagBean.setResourceId(resId);
			if (StringUtils.isEmpty(tagBean.getGroupId())) {
				tagBean.setGroupId("all");
			}
			cachedService.setLocation(orgId, account, tagBean);
			groupList = cachedService.getResGroupList(orgId);
			if (groupList != null && groupList.size() > 0) {
				for (ResourceGroupBean resourceGroupBean : groupList) {
					GroupDto bean = new GroupDto();
					bean.setGroupId(resourceGroupBean.getResGroupId());
					bean.setGroupName(resourceGroupBean.getGroupName());
					groupDtoList.add(bean);
				}
			}
			listDto.setGroupList(groupDtoList);
			listDto.setOtherList(otherList);
			listDto.setCurrResId(currResId);
			listDto.setCustId(resId);
			listDto.setResourceGroupId(tagBean.getGroupId());
			listDto.setIsConcat(tagBean.getIsConcat() == null ? "0" : tagBean.getIsConcat() + "");
			listDto.setOrderType(tagBean.getOrderType());
		} catch (Exception e) {
			logger.error("updateTaoTag reqId=" + reqId, e);
			throw new SysRunException(e);
		}
		return listDto;
	}

	/**
	 * 沟通失败等操作
	 * 
	 * @return
	 * @create 2016年1月4日 下午1:25:10 wuwei
	 * @history
	 */
	public String modifyRes2GH(String resId, String dealTypeStr, String resourceGroupId, String orderType, int isConcat, String orgId, String account,
			String pool, String userId, String groupId,String custInfo_name) {
		ShiroUser user = new ShiroUser();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		ResCustDto resCustDto = null;
		String[] dealTypeArr = dealTypeStr.split("_");
		int dealType = 0;
		Short giveUpType = 4;
		String subDealType = "";
		try {
			// 判断是否是子按钮触发的。比如信息错误，包括（手机号码错误、固话号码错误 等等）
			if (dealTypeArr.length > 1) {
				dealType = Short.parseShort(dealTypeArr[0]);
				Object obj = cachedService.getOpion(orgId);
				List<OptionBean> optList = (List<OptionBean>) obj;
				for (OptionBean opt : optList) {
					if (opt.getOptionlistId().equals(dealTypeArr[1])) {
						subDealType = opt.getOptionName();
						break;
					}
				}
			} else {
				dealType = Short.parseShort(dealTypeArr[0]);
			}
			// 设置沟通失败、信息错误、非目标客户
			if (dealType == AppConstant.OPREATE_TYPE4) {
				giveUpType = AppConstant.OPREATE_TYPE4;
			} else if (dealType == AppConstant.OPREATE_TYPE5) {
				giveUpType = AppConstant.OPREATE_TYPE5;
			}

			if (StringUtils.isBlank(subDealType)) {
				subDealType = "沟通失败";
				if (dealType == AppConstant.OPREATE_TYPE5) {
					subDealType = "信息错误";
				}
			}
			// 处理类型：与资源客户状态保持一致，便于处理
			if (StringUtils.isNotEmpty(resId)) {

				user.setId(userId);
				user.setOrgId(orgId);
				user.setGroupId(groupId);
				user.setAccount(account);
				resCustInfoService.giveUp(user, Arrays.asList(resId + "_1"), giveUpType, subDealType,"");//
				// modifyBatchCust(null, null, Arrays.asList(resId+"_1"),
				// subDealType);
				TaoDto taoDto = cachedService.getTaoDto(orgId, account, pool);
				if (taoDto != null) {
					if (taoDto.getTaoResDtos().size() > 1) {
						setUpOrDownId(resId, taoDto, 2, orgId, account, pool);
					}
					updateCacheStatus(orgId, account, resId, pool);
					TaoTagBean tagBean = cachedService.getLoction(orgId, account);
					if (tagBean == null) {
						String defaultOrder = ConfigInfoUtils.getStringValue("tao_order");// 默认按时间倒序排序
						tagBean = new TaoTagBean();
						tagBean.setGroupId("toady");// 今日
						tagBean.setOrderType(defaultOrder);
						tagBean.setIsConcat(2);// 全部
						tagBean.setAccount(account);
						tagBean.setOrgId(orgId);
						tagBean.setResourceId(taoDto.getResId());
					} else {
						tagBean.setAccount(account);
						tagBean.setOrgId(orgId);
						tagBean.setResourceId(taoDto.getResId());
					}

					cachedService.setLocation(orgId, account, tagBean);
				}
				Map<String, String> resMap = new HashMap<String, String>();
				resMap.put("resId", resId);
				resMap.put("orgId", orgId);
				resCustDto = comResourceMapper.findResById(resMap);
				if (resCustDto != null) {
					resultMap.put("resId", resCustDto.getResCustId());
					resultMap.put("status", resCustDto.getStatus() + "");
					resultMap.put("name", resCustDto.getName());
					resultMap.put("isCall", resCustDto.getIsConcat());
					resultMap.put("phone", resCustDto.getMobilephone());
					resultMap.put("type", resCustDto.getType());
					resultMap.put("pool", pool);
					resultMap.put("filterType", resCustDto.getFilterType());
				}
			}
//			LogBean logBean = new LogBean(orgId, account, user.getName(), OperateEnum.LOG_GIVE.getCode(),OperateEnum.LOG_GIVE.getDesc(), 1,SysBaseModelUtil.getModelId()); 
//
//			logBean.setModuleId(AppConstant.Module_id9);
//			logBean.setModuleName(AppConstant.Module_Name9);
//			if (dealType == AppConstant.OPREATE_TYPE4) {//沟通失败
////				logUserOperateService.setUserOperateLog(AppConstant.Module_id9 ,AppConstant.Module_Name9 ,AppConstant.Operate_id66 ,AppConstant.Operate_Name66 ,custInfo_name,"" );
//				logBean.setOperateId(AppConstant.Operate_id66);
//				logBean.setOperateName(AppConstant.Operate_Name66);
//				logBean.setContent((resCustDto.getCompany() == null || "".equals(resCustDto.getCompany()))? resCustDto.getName() : resCustDto.getCompany());
//							
//			} else if (dealType == AppConstant.OPREATE_TYPE5) {//信息错误
////				logUserOperateService.setUserOperateLog(AppConstant.Module_id9 ,AppConstant.Module_Name9 ,AppConstant.Operate_id65 ,AppConstant.Operate_Name65 ,custInfo_name,"" );
//				logBean.setOperateId(AppConstant.Operate_id65);
//				logBean.setOperateName(AppConstant.Operate_Name65);
//				logBean.setContent((resCustDto.getCompany() == null || "".equals(resCustDto.getCompany()))? resCustDto.getName() : resCustDto.getCompany());
//			}
//			List<String> ids = new ArrayList<String>();
//			ids.add(resId);
//			custSaleChanceMapper.updateIsDelByCustIds(orgId,ids);
//			Map<String, Object> logMap = new HashMap<String, Object>();
//			logMap.put(resId, "放入公海原因："+subDealType);
//			logCustInfoService.addTableStoreLog(logBean, logMap);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysRunException(e);
		}
		return JsonUtil.getJsonString(resultMap);
	}

	/**
	 * 置为公海状态
	 * 
	 * @param user
	 * @param resId
	 * @create 2016年1月4日 下午1:36:24 wuwei
	 * @history
	 */
	public void updateCacheStatus(String orgId, String account, String resId, String pool) {
		TaoDto taoDto = cachedService.getTaoDto(orgId, account, pool);
		if (taoDto != null && taoDto.getTaoResDtos() != null && taoDto.getTaoResDtos().size() > 0) {
			for (TaoResDto taoResDto : taoDto.getTaoResDtos()) {
				if (resId.equals(taoResDto.getResId())) {
					taoDto.getTaoResDtos().remove(taoResDto);
					cachedService.setTaoDto(orgId, account, taoDto, pool);
					break;
				}
			}
		}
	}

	public void setUpOrDownId(String resId, TaoDto taoDto, int upOrDown, String orgId, String account, String pool) {
		String tempId = "";
		try {
			if (upOrDown == 1) {
				tempId = TaoCacheListUtil.removeUp(taoDto.getTaoResDtos(), resId);
				if (!tempId.equals(resId)) {
					taoDto.setResId(tempId);
				}
				if (resId.equals(taoDto.getUpId())) {
					taoDto.setUpId(tempId);
				}

				if (resId.equals(taoDto.getDownId())) {
					taoDto.setDownId(tempId);
				}
			} else if (upOrDown == 2) {
				tempId = TaoCacheListUtil.removeNext(taoDto.getTaoResDtos(), resId);
				if (!tempId.equals(resId)) {
					taoDto.setResId(tempId);
				}
				if (resId.equals(taoDto.getUpId())) {
					taoDto.setUpId(tempId);
				}

				if (resId.equals(taoDto.getDownId())) {
					taoDto.setDownId(tempId);
				}
			}
			cachedService.setTaoDto(orgId, account, taoDto, pool);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 保存淘客户
	 * 
	 * @param custInfo
	 * @param custFollow
	 * @param custGuide
	 * @create 2015年12月9日 下午6:58:31 wuwei
	 * @history
	 */
	public String addMyCust(String reqId, String orgId, String account, String deptId, String userId, String userName, Integer isState,
			ResCustInfoBean custInfo, CustFollowBean custFollow, TsmCustGuide custGuide, String suitProcId, TaoTagBean tagBean, String concatPhone,
			String concatName, String pool, String opterType,Integer isAddLog) {
		String custId = custInfo.getResCustId();
		Map<String, String> map = new HashMap<String, String>();
		ResCustDto resCustDto = null;
		Map<String, String> resMap = new HashMap<String, String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		map.put("custId", custInfo.getResCustId());
		map.put("orgId", orgId);
		// 跟进类型为放弃客户时的区别字段
		try {
			resMap.put("resId", custId);
			resMap.put("orgId", orgId);
			resCustDto = comResourceMapper.findResById(resMap);
			boolean isOwnerAcc = account.equals(resCustDto.getOwnerAcc());
			if (!isOwnerAcc) {
				resultMap.put("resId", custId);
				resultMap.put("status", isOwnerAcc ? resCustDto.getStatus() : "");
				resultMap.put("name", resCustDto.getName());
				resultMap.put("isCall", resCustDto.getIsConcat());
				resultMap.put("phone", resCustDto.getMobilephone());
				resultMap.put("type", "2");
				resultMap.put("filterType", resCustDto.getFilterType());
				resultMap.put("pool", pool);
				return JsonUtil.getJsonString(resultMap);
			}
			custInfo.setName(resCustDto == null ? "" : resCustDto.getName());
			custInfo.setCompany(resCustDto == null ? "" : resCustDto.getCompany());
			planFactService.toWill(orgId, deptId, userId);
			boolean result=false;
				if(custFollow.getFollowDate()!=null){
					 result = DateUtil.isNow(custFollow.getFollowDate());
					if(result==true){
						if("1".equals(opterType)){
							planFactService.updateContactResult(orgId,deptId,userId,custId,"res",true,"will");
						}else{
							planFactService.updateContactResult(orgId,deptId,userId,custId,"res",true,"sign");
						}
	
					}
				}
			if(result==false){
				planFactService.updateContactResult(orgId, deptId, userId, custId, "res", PlanResCR.TURN_WILL.getResult());	
			}
			saveOptLog(reqId, orgId, account, custInfo, custFollow, custGuide, suitProcId, tagBean, opterType, concatPhone, concatName,
					resCustDto.getBirthday(), pool, isState, userName);
			if (resCustDto != null) {
				resultMap.put("resId", custId);
				resultMap.put("status", "1".equals(opterType) ? "3" : "6");
				resultMap.put("name", resCustDto.getName());
				resultMap.put("isCall", resCustDto.getIsConcat());
				resultMap.put("phone", resCustDto.getMobilephone());
				resultMap.put("type", "2");
				resultMap.put("pool", pool);
				resultMap.put("filterType", resCustDto.getFilterType());
			}
			addCustLog(reqId, custGuide.getSaleProcessId(), orgId, custId, account, resCustDto, custFollow.getLabelName(), custFollow.getFeedbackComment(),
					custFollow.getCustFollowId(), isState, concatPhone, concatName, userName, custFollow.getFollowDate(), opterType, userId, deptId,isAddLog);
			logContactDayDataService.addLogContactDayData(ContactUtil.RES_TO_WILL, orgId, custId, account, custFollow.getLastSaleProcessId(), custGuide.getSaleProcessId());
			//			tsmReportWillService.addTsmReportWillandOption(orgId, account, userName, 1, custGuide.getSaleProcessId(), "", 1);
			// 需要删除 多选项表中 行动标签值
			
			Map<String,Object>delMap = new HashMap<String, Object>();
			delMap.put("orgId", orgId);
			delMap.put("custId", custInfo.getResCustId());
			delMap.put("fieldCode", custFollow.getLabelCode());
			custDefinedDataMapper.deleteByFieldCode(delMap);
			if(StringUtils.isNotBlank(custFollow.getLabelCode())){
				List<CustDefinedDataBean>mulitBeans = new ArrayList<CustDefinedDataBean>();
				for(String labelCode:custFollow.getLabelCode().split("#")){
					CustDefinedDataBean mulitDefinedBean = new CustDefinedDataBean();
					mulitDefinedBean.setId(SysBaseModelUtil.getModelId());
					mulitDefinedBean.setOrgId(orgId);
					mulitDefinedBean.setCustId(custInfo.getResCustId());
					mulitDefinedBean.setFieldCode("labelCode");
					mulitDefinedBean.setFieldData(labelCode);
					mulitBeans.add(mulitDefinedBean);
				}
				if(mulitBeans != null && mulitBeans.size()>0){
					custDefinedDataMapper.insertBatch(mulitBeans);
				}			
			}
			
			// 标签记录增加
			List<CustLabelCodeDataBean>mulitBeans1 = new ArrayList<CustLabelCodeDataBean>();
			for(String labelCode:custFollow.getLabelCode().split("#")){
				CustLabelCodeDataBean mulitDefinedBean1 = new CustLabelCodeDataBean();
				mulitDefinedBean1.setId(SysBaseModelUtil.getModelId());
				mulitDefinedBean1.setOrgId(orgId);
				mulitDefinedBean1.setCustId(custInfo.getResCustId());
				mulitDefinedBean1.setActionId(custFollow.getCustFollowId());
				mulitDefinedBean1.setFieldCode("labelCode");
				mulitDefinedBean1.setFieldData(labelCode);
				mulitBeans1.add(mulitDefinedBean1);
			}
			if(mulitBeans1 != null && mulitBeans1.size()>0){
				custLabelCodeDataMapper.insertBatch(mulitBeans1);
			}	

		
		} catch (Exception e) {
			logger.error("reqId=" + reqId + "。addMyCust异常。" + e.getMessage(), e);
			throw new SysRunException(e);
		}
		return JsonUtil.getJsonString(resultMap);
	}

	public void addCustLog(final String reqId, final String optionId, final String orgId, final String custId, final String account,
			final ResCustDto resCustDto, final String labelName, final String feedbackComment, final String custFollowId, final int state,
			final String concatPhone, final String concatName, final String userName, final Date followDate, final String opType, final String userId,
			final String deptId,final Integer isAddLog) {
		taskExecutor.submit(new Runnable() {
			public void run() {
				long startTime = System.currentTimeMillis();
				try {
					logger.debug("addCustLog reqId=" + reqId + " 处理开始");
					String optionName = "";
					Map<String, String> optionMap = cachedService.getOrgSaleProcess(orgId);
					if (optionMap != null) {
						optionName = optionMap.get(optionId);
					}
					if ("1".equals(opType)) {
//						logCustInfoService.addLog(orgId, account, custId, "", OperateEnum.LOG_WILL);
						willLog(orgId, custId, account, optionId, resCustDto,userName,isAddLog);
					} else {
//						logCustInfoService.addLog(orgId, account, custId, "", OperateEnum.LOG_SIGN);
						signLog(custId, orgId, account, userName, userId, deptId, resCustDto);
					}
					if (resCustDto != null && resCustDto.getResCustId() != null && !"".equals(resCustDto.getResCustId())) {
						Map<String, Object> jsonMap = new HashMap<String, Object>();
						if (state == 1) {
							jsonMap.put("telphone", concatPhone);
						} else {
							jsonMap.put("telphone", StringUtils.isEmpty(resCustDto.getMobilephone()) ? resCustDto.getTelphone() : resCustDto.getMobilephone());
						}
						jsonMap.put("mainLinkman", state == 1 ? concatName : resCustDto.getName());
						jsonMap.put("inputDate", new Date());
						jsonMap.put("userName", (userName == null || "".equals(userName) ? account : userName));
						jsonMap.put("type", "1");
						jsonMap.put("saleProcessName", optionName);
						jsonMap.put("labels", labelName);
						jsonMap.put("remark", feedbackComment);
						jsonMap.put("custFollowId", custFollowId);
						jsonMap.put("nextConcatTime", followDate);
						jsonMap.put("custName", resCustDto.getName());
						resCustEventService.create(orgId, custId, "1", JsonUtil.getJsonString(jsonMap));
					}
					logger.debug("addCustLog reqId=" + reqId + " 处理结束,耗时" + (System.currentTimeMillis() - startTime) + "毫秒");
				} catch (Exception e) {
					logger.error("reqId=" + reqId + "。optionId=" + optionId + ",orgId=" + orgId + ",custId=" + custId + ",account=" + account + ",labelName="
							+ labelName + ",feedbackComment=" + feedbackComment + ",custFollowId=" + custFollowId + ",state=" + state + ",concatPhone="
							+ concatPhone + ",concatName=" + concatName + ",userName=" + userName);
					logger.error("reqId=" + reqId + "。resCustDto=" + JsonUtil.getJsonString(resCustDto));
					logger.error("reqId=" + reqId + "。updateBatchResToGroup 多线程异常" + e.getMessage(), e);
				}
			}
		});
	}

	public void willLog(String orgId, String custId, String account, String optionId, ResCustDto resCustDto,String userName,Integer isAddLog) {
//		contactDayDataService.contactStatusLog(orgId, account, custId, 1, 1);
//		ShiroUser user = new ShiroUser();
		rankingReportService.updateRankingData(orgId, account, new BigDecimal(0), 0, 1);
		if (!(1 == isAddLog)) {
			LogBean logBean = new LogBean(orgId, account, userName, OperateEnum.LOG_WILL.getCode(), OperateEnum.LOG_WILL.getDesc(), 1,SysBaseModelUtil.getModelId());
			logBean.setOperateId(AppConstant.Operate_id63);
			logBean.setOperateName(AppConstant.Operate_Name63);
			logBean.setModuleId(AppConstant.Module_id9);
			logBean.setModuleName(AppConstant.Module_Name9);
			logBean.setContent((resCustDto.getCompany() == null || "".equals(resCustDto.getCompany()))? resCustDto.getName() : resCustDto.getCompany());
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(custId, "");
			logCustInfoService.addTableStoreLog(logBean, logMap);
		}
	}

	/**
	 * 直接签约
	 * 
	 * @param custId
	 * @param user
	 * @create 2016年8月10日 下午3:25:57 lixing
	 * @history
	 */
	public void signLog(String custId, String orgId, String account, String userName, String userId, String deptId, ResCustDto resCustDto) {
		// 修改计划
		planFactService.toSign(orgId, deptId, userId, custId, 1, 0);
//		contactDayDataService.contactStatusLog(orgId, account, custId, 1, 2);
//		boolean result=false;
//		if(resCustDto.getNextFollowDate()!=null){
//			 result = DateUtil.isNow(resCustDto.getNextFollowDate());
//			if(result==true){
//				planFactService.updateContactResult(orgId,deptId,userId,custId,"res",true,"sign");	
//			}
//		}
//			if(result==false){
				planFactService.updateContactResult(orgId, deptId, userId, custId, "res", PlanResCR.TURN_SIGN.getResult());
//			}
		
		ShiroUser tempUser = new ShiroUser();
		tempUser.setOrgId(orgId);
		tempUser.setAccount(account);
		silenceCustService.updateSilentLossReport(null, 1, 1, tempUser);
		// 新客户
		planFactService.updateContractNum(orgId, deptId, 1, 1);
		rankingReportService.updateRankingData(orgId, account, BigDecimal.valueOf(0), 1, 0);
		
		// custInfoNalysisService.saveOrUpdate(custId, 2, orgId);
		
		logContactDayDataService.addLogContactDayData(ContactUtil.RES_TO_SIGN, orgId, custId, account, null, resCustDto.getSaleProcessId());
		// 发送弹幕消息
//		tsmMessageService.sendBarrage("恭喜" + (userName == null ? account : userName) + "成功签约" + resCustDto.getName() + "!", orgId, account, userName);
		if(mainService.getBarrageSign(orgId)==1&&mainService.getBarrageSign3(orgId)==0){
			String retName="";
			if(mainService.getBarrageSign2(orgId)==0){
				retName=((resCustDto.getCompany() == null || "".equals(resCustDto.getCompany()))? resCustDto.getName() : resCustDto.getCompany());	
			}
			if(retName==null){
				retName="";
			}
			mainService.sendSingBarrage("恭喜" + (userName == null ? account : userName) + "成功签约" + retName + "!", "1", orgId, account, userName,"0",0);	
			List<DataDictionaryBean> dlist0 = cachedService.getDirList(AppConstant.DATA_50047, orgId);//红包设置
			if ((!dlist0.isEmpty() && !"0".equals(dlist0.get(0).getDictionaryValue()))) {
				PayApiRequest payrequest=new PayApiRequest();

				payrequest.setOrgId(orgId);
				payrequest.setUserAcc(account);
				payrequest.setPayApiEnum(PayApiTypeEnum.SIGN);
				payrequest.setMoney(Double.valueOf(dlist0.get(0).getDictionaryValue()));
				payrequest.setDesc((userName == null ? account : userName) + "获得签约奖励红包"+dlist0.get(0).getDictionaryValue()+"元！");
				payrequest.setData((userName == null ? account : userName) + "成功签约" + retName + "!");
				BaseJsonResult json=payApiFacade.pay(payrequest);
				if((boolean) json.get("status")==false){
					logger.error("-------调用pay接口返回结果：--------"+JSONObject.fromObject(json)+"时间："+new Date()); 	
				}
	        }
		}
		try {
//			ShiroUser user = new ShiroUser();
//			logUserOperateService.setUserOperateLog(AppConstant.Module_id9 ,AppConstant.Module_Name9 ,AppConstant.Operate_id64 ,AppConstant.Operate_Name64 ,resCustDto.getName(),"" );
			LogBean logBean = new LogBean(orgId, account, userName, OperateEnum.LOG_SIGN.getCode(), OperateEnum.LOG_SIGN.getDesc(), 1,SysBaseModelUtil.getModelId());
			logBean.setOperateId(AppConstant.Operate_id8);
			logBean.setOperateName(AppConstant.Operate_Name8);
			logBean.setModuleId(AppConstant.Module_id9);
			logBean.setModuleName(AppConstant.Module_Name9);
			logBean.setContent((resCustDto.getCompany() == null || "".equals(resCustDto.getCompany()))? resCustDto.getName() : resCustDto.getCompany());
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(custId, "");
			logCustInfoService.addTableStoreLog(logBean, logMap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void saveOptLog(String reqId, String orgId, String account, ResCustInfoBean custInfo, CustFollowBean custFollow, TsmCustGuide custGuide,
			String suitProcId, TaoTagBean tagBean, String opterType, String concatPhone, String concatName, Date birthDay, String pool, int state,
			String userName) {
		String custId = custInfo.getResCustId();
		TaoDto taoDto = cachedService.getTaoDto(orgId, account, pool);
		Date nowTime = new Date();
		String nextId = "";
		// 跟进类型为放弃客户时的区别字段
		Short optType = AppConstant.OPREATE_TYPE10;
		Short status = AppConstant.STATUS_3;
		try {
			if ("2".equals(opterType)) {
				optType = AppConstant.OPREATE_TYPE15;
				status = AppConstant.STATUS_6;
				custInfo.setActionDate(new Date());
				custInfo.setSignAcc(account);
				custInfo.setSignName(userName);
				custInfo.setSignDate(new Date());
				custInfo.setSignBeforeStatus(2);
				custInfo.setSignBeforeType(1);
			}
			custInfo.setOwnerActionDate(new Date());
			custInfo.setStatus(new Integer(status));
			custInfo.setOpreateType(new Integer(optType));
			custInfo.setUpdateAcc(account);
			custInfo.setUpdateDate(nowTime);
			custInfo.setType(new Integer(AppConstant.CUST_TYPE2));
			custInfo.setAmoytocustomerDate(nowTime); // 淘到客户时间
			custInfo.setNextFollowDate(custFollow.getFollowDate());
			custInfo.setActionDate(nowTime);
			custInfo.setLastCustFollowID(custFollow.getCustFollowId());// 最近客户跟进id
			custInfo.setForTheCustomerTime(nowTime);
			custInfo.setOrgId(orgId);
			custInfo.setIsConcat(1);
			custInfo.setLastOptionId(custGuide.getSaleProcessId());
			custInfo.setIsDel(0);
			custInfo.setLastCustTypeId(custGuide.getCustTypeId());
			custInfo.setBirthday(birthDay);
			custInfo.setLastCustTypeId(custGuide.getCustTypeId());
			custInfo.setFilterType(1);

			resCustInfoMapper.update(custInfo);

			if (state == 0) { // 个人版
				custFollow.setOldCustName(custInfo.getName());
				custFollow.setCustDetailName(custInfo.getCompany());
			} else {
				custFollow.setOldCustName(custInfo.getName());
				custFollow.setCustDetailName(concatName);
			}
			if ("1".equals(opterType)) {
				custFollow.setCustStatus(5);
			} else {
				custFollow.setCustStatus(6);
			}
			insertFollowInfo(reqId, custId, orgId, account, concatPhone, concatName, custInfo.getName(), nowTime, custGuide, suitProcId, custFollow);
			if (tagBean != null) {
				if (taoDto != null && taoDto.getTaoResDtos().size() > 0) {
					if (taoDto.getTaoResDtos().size() <= 1) {
						nextId = "";
						updateCacheStatus(orgId, account, custId, pool);
					} else {
						nextId = TaoCacheListUtil.removeNext(taoDto.getTaoResDtos(), custId);
						setUpOrDownId(custId, taoDto, 2, orgId, account, pool);
						updateCacheStatus(orgId, account, custId, pool);
						setOnId(nextId, orgId, account, pool);
					}
				}
				tagBean.setResourceId(nextId);
			} else {
				String defaultOrder = ConfigInfoUtils.getStringValue("tao_order");// 默认按时间倒序排序
				tagBean = new TaoTagBean();
				tagBean.setGroupId("all");// 今日
				tagBean.setOrderType(defaultOrder);
				tagBean.setIsConcat(2);// 全部
				tagBean.setAccount(account);
				tagBean.setOrgId(orgId);
				tagBean.setResourceId(nextId);
				tagBean.setPool(pool);
			}
			cachedService.setLocation(orgId, account, tagBean);
		} catch (Exception e) {
			logger.error("custId=" + custId + ",orgId=" + orgId + ",account=" + account + ", concatPhone=" + concatPhone + ", concatName=" + concatName
					+ ", custName=" + custInfo.getName() + ", nowTime=" + nowTime + ",suitProcId=" + suitProcId + "。" + e.getMessage(), e);
			logger.error("reqId=" + reqId + "。resCustDto=" + JsonUtil.getJsonString(custGuide));
			logger.error("reqId=" + reqId + "。custFollow=" + JsonUtil.getJsonString(custFollow));
			throw new SysRunException(e);
		}
	}

	private void insertFollowInfo(final String reqId, final String custId, final String orgId, final String account, final String concatPhone,
			final String concatName, final String custName, final Date nowTime, final TsmCustGuide custGuide, final String suitProcId,
			final CustFollowBean custFollow) {
		taskExecutor.submit(new Runnable() {
			public void run() {
				logger.debug("insertFollowInfo reqId=" + reqId + " 处理开始");
				long startTime = System.currentTimeMillis();
				try {
					Map<String, String> map = new HashMap<String, String>();
					map.put("custId", custId);
					map.put("orgId", orgId);
					tsmCustGuideMapper.removeByCustId(map);
					// 新增销售导航记录
					String guideId = SysBaseModelUtil.getModelId();
					custGuide.setCustGuideId(guideId);
					custGuide.setCustId(custId);
					custGuide.setInputerAcc(account);
					custGuide.setInputdate(nowTime);
					custGuide.setOrgId(orgId);
					tsmCustGuideMapper.insert(custGuide);

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
					// 新增客户跟进记录
					custFollow.setCustId(custId);
					custFollow.setFollowAcc(account);
					custFollow.setOrgId(orgId);
					custFollow.setActionDate(nowTime);
					custFollow.setInputAcc(account);
					custFollow.setInputDate(nowTime);
					custFollow.setActionType(AppConstant.ACTION_TYPE1); // 行动方式
					custFollow.setSaleProcessId(custGuide.getSaleProcessId());
					custFollow.setOrgId(orgId);
					custFollow.setIsFollow(1);
					custFollow.setCustDetailMobile(concatPhone);
					custFollow.setCustTypeId(custGuide.getCustTypeId());
					custFollowMapper.insert(custFollow);
					logger.debug("insertFollowInfo reqId=" + reqId + " 处理结束。耗时=" + (System.currentTimeMillis() - startTime) + "毫秒");
				} catch (Exception e) {
					logger.error("custId=" + custId + ",orgId=" + orgId + ",account=" + account + ", concatPhone=" + concatPhone + ", concatName=" + concatName
							+ ", custName=" + custName + ", nowTime=" + nowTime + ",suitProcId=" + suitProcId);
					logger.error("reqId=" + reqId + "。resCustDto=" + JsonUtil.getJsonString(custGuide));
					logger.error("reqId=" + reqId + "。custFollow=" + JsonUtil.getJsonString(custFollow));
					logger.error("reqId=" + reqId + "。insertFollowInfo 多线程异常" + e.getMessage(), e);
				}
			}
		});
	}

	public void updateMainInfo(ShiroUser user, int addType) {
		MainInfoBean mb = new MainInfoBean();
		mb.setAccount(user.getAccount());
		mb.setOrgId(user.getOrgId());
		mb.setType("0");
		mainInfoService.updateMainInfo(mb, addType, 1);
	}

	public String updateDelayCallReason(TaoTagBean tagBean, Map<String, String> map, String resId, String orgId, String account, String pool) {
		TaoDto taoDto = cachedService.getTaoDto(orgId, account, pool);
		ResCustDto resCustDto = null;
		Map<String, String> resMap = new HashMap<String, String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			comResourceMapper.modifyDelayCallReason(map);
			resMap.put("resId", resId);
			resMap.put("orgId", orgId);
			resCustDto = comResourceMapper.findResById(resMap);
			if (taoDto != null) {
				if (taoDto.getTaoResDtos().size() > 1) {
					setUpOrDownId(resId, taoDto, 2, orgId, account, pool);
				}
				updateCacheStatus(orgId, account, map.get("resCustId"), pool);
			}
			if (resCustDto != null) {
				resultMap.put("resId", resId);
				resultMap.put("status", resCustDto.getStatus());
				resultMap.put("name", resCustDto.getName());
				resultMap.put("isCall", resCustDto.getIsConcat());
				resultMap.put("phone", resCustDto.getMobilephone());
				resultMap.put("type", resCustDto.getType());
				resultMap.put("filterType", resCustDto.getFilterType());
				resultMap.put("pool", pool);
			}
			// updateTag(tagBean, resCustDto.getResCustId());
			tagBean.setResourceId(resCustDto.getResCustId());
			cachedService.setLocation(orgId, account, tagBean);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return JsonUtil.getJsonString(resultMap);
	}

	public void addCustGuide(ResCustInfoBean custInfo, HttpServletRequest request, String orgId, String account) {
		String resourceId = custInfo.getResCustId();
		CustFollowDto lastCustFollow = new CustFollowDto();
		try {
			if (!"".equals(resourceId) && resourceId != null) {
				Map<String, Object> fMap = new HashMap<String, Object>();
				fMap.put("orgId", orgId);
				fMap.put("custId", resourceId);
				fMap.put("custFollowId", custInfo.getLastCustFollowID());
				lastCustFollow = custFollowMapper.queryLastCustFollowByCustId(fMap);
			}
			request.setAttribute("tsmCustFollow", lastCustFollow);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void taoCustDelay(HttpServletRequest request, String orgId, String account) {
		List<ResCustInfoBean> delayResList = null;
		Map<String, Object> delayMap = new HashMap<String, Object>();
		try {
			// 延后资源
			delayMap.put("account", account);
			delayMap.put("orgId", orgId);
			delayMap.put("length", 4);
			delayResList = comResourceMapper.findDelayCustList(delayMap);
			request.setAttribute("delayResList", delayResList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void upateCustDelay(String resId, String orgId, String account) {
		ResCustInfoBean bean = new ResCustInfoBean();
		try {
			bean.setUpdateDate(new Date());
			bean.setUpdateAcc(account);
			bean.setResCustId(resId);
			bean.setFilterType(1);
			bean.setOrgId(orgId);
			comResourceMapper.update(bean);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public String initTaoRes(ShiroUser user, String pool) {
		TaoDto taoDto = cachedService.getTaoDto(user.getOrgId(), user.getAccount(), pool);
		List<TaoResDto> list = null;
		if (taoDto != null && taoDto.getTaoResDtos().size() > 0) {
			list = TaoCacheListUtil.getList(taoDto.getTaoResDtos(), taoDto.getUpId());
		}
		return JsonUtil.getJsonString(list);
	}

	public String getMyRes(String resId, String orgId, String account, int upOrDownId, TaoTagBean tagBean, String pool) {
		Map<String, String> map = new HashMap<String, String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String resultStr = "";
		map.put("resId", resId);
		map.put("orgId", orgId);
		ResCustDto resDto = comResourceMapper.findResById(map);
		boolean isOwnerAcc = account.equals(resDto.getOwnerAcc());
		if (resDto != null) {
			if ((resDto.getStatus() == 2 || resDto.getStatus() == 3) && isOwnerAcc) {
				setOnId(resDto.getResCustId(), orgId, account, pool);
				tagBean.setResourceId(resId);
				cachedService.setLocation(orgId, account, tagBean);
			} else {
				TaoDto taoDto = cachedService.getTaoDto(orgId, account, pool);
				if (taoDto != null && taoDto.getTaoResDtos().size() > 0) {
					setUpOrDownId(resId, taoDto, upOrDownId, orgId, account, pool);
					updateCacheStatus(orgId, account, resId, pool);
				}
			}
			resultMap.put("resId", resDto.getResCustId());
			resultMap.put("name", resDto.getName());
			resultMap.put("phone", resDto.getMobilephone());
			resultMap.put("status", isOwnerAcc ? resDto.getStatus() : ""); // 拥有者不是自己
			resultMap.put("isCall", resDto.getIsConcat());
			resultMap.put("type", resDto.getType());
			resultMap.put("filterType", resDto.getFilterType());
			resultMap.put("followId", SysBaseModelUtil.getModelId());
			resultMap.put("pool", pool);
			resultStr = JSON.toJSONString(resultMap);
		}
		return resultStr;
	}

	public void setOnId(String resId, String orgId, String account, String pool) {
		TaoDto taoDto = cachedService.getTaoDto(orgId, account, pool);
		if (taoDto != null && taoDto.getTaoResDtos().size() > 0) {
			for (TaoResDto taoResDto : taoDto.getTaoResDtos()) {
				if (resId.equals(taoResDto.getResId())) {
					taoDto.setResId(resId);
					cachedService.setTaoDto(orgId, account, taoDto, pool);
				}
			}
		}
	}

	public CallListDto getFresh(HttpServletRequest request, TaoTagBean tagBean, int upOrDown, String orgId, String account, int isState, String pool) {
		CallListDto listDto = new CallListDto();
		Integer taoNum =Integer.valueOf((ConfigInfoUtils.getStringValue("tao_num_cache")));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ownerAcc", account);
		map.put("orgId", orgId);
		map.put("resourceGroupId", tagBean.getGroupId());
		map.put("orderType", tagBean.getOrderType());
		map.put("isConcat", tagBean.getIsConcat());
		map.put("startLength", 0);
		map.put("length", taoNum);
		ResCustDto resCustDto = null;
		String currResId = "";
		List<GroupDto> groupDtoList = new ArrayList<GroupDto>();// 组列表
		List<ResourceGroupBean> groupList = new ArrayList<ResourceGroupBean>();// 组列表
		Map<String, String> resMap = new HashMap<String, String>();
		List<TaoResDto> otherList = new ArrayList<TaoResDto>();
		try {

			TaoDto taoDto = cachedService.getTaoDto(orgId, account, pool);

			if (taoDto != null && taoDto.getTaoResDtos().size() > 0) {
				if (upOrDown == 1) {
					if (isTop(taoDto)) { // 是否为缓存头部，是重新取
						taoDto = getTaoDto(map, orgId, account, tagBean, pool);
						if (taoDto != null && taoDto.getTaoResDtos().size() > 0) {
							currResId = taoDto.getResId();
							otherList = TaoCacheListUtil.getList(taoDto.getTaoResDtos(), taoDto.getUpId());
						}
					} else {
						otherList = TaoCacheListUtil.getUpList(taoDto.getTaoResDtos(), taoDto.getUpId());
						if (otherList != null && otherList.size() > 0) {
							taoDto.setUpId(otherList.get(0).getResId());
							taoDto.setDownId(otherList.get(otherList.size() - 1).getResId());
							taoDto.setResId(otherList.get(0).getResId());
							currResId = otherList.get(otherList.size() - 1).getResId();
							taoDto.setResId(currResId);
							cachedService.setTaoDto(orgId, account, taoDto, pool);
						}
					}
				} else {
					if (isTail(taoDto)) {
						taoDto = getTaoDto(map, orgId, account, tagBean, pool);
						if (taoDto != null && taoDto.getTaoResDtos().size() > 0) {
							otherList = TaoCacheListUtil.getList(taoDto.getTaoResDtos(), taoDto.getUpId());
							currResId = taoDto.getResId();
						}
					} else {
						otherList = TaoCacheListUtil.getNextList(taoDto.getTaoResDtos(), taoDto.getDownId());
						if (otherList != null && otherList.size() > 0) {
							taoDto.setUpId(otherList.get(0).getResId());
							taoDto.setDownId(otherList.get(otherList.size() - 1).getResId());
							taoDto.setResId(otherList.get(0).getResId());
							currResId = otherList.get(0).getResId();
							taoDto.setResId(currResId);
							cachedService.setTaoDto(orgId, account, taoDto, pool);
						}
					}
				}
			} else {
				taoDto = getTaoDto(map, orgId, account, tagBean, pool);
				if (taoDto != null && taoDto.getTaoResDtos().size() > 0) {
					otherList = TaoCacheListUtil.getList(taoDto.getTaoResDtos(), taoDto.getUpId());
					currResId = taoDto.getResId();
				}
			}
			tagBean.setResourceId(currResId);
			cachedService.setLocation(orgId, account, tagBean);
			resMap.put("resId", currResId);
			resMap.put("orgId", orgId);
			resCustDto = comResourceMapper.findResById(resMap);
			groupList = cachedService.getResGroupList(orgId);
			if (groupList != null && groupList.size() > 0) {
				for (ResourceGroupBean resourceGroupBean : groupList) {
					GroupDto bean = new GroupDto();
					bean.setGroupId(resourceGroupBean.getResGroupId());
					bean.setGroupName(resourceGroupBean.getGroupName());
					groupDtoList.add(bean);
				}
			}
			listDto.setGroupList(groupDtoList);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysRunException(e);
		}

		listDto.setGroupList(groupDtoList);
		listDto.setOtherList(otherList);
		listDto.setCurrResId(currResId);
		listDto.setCustId(resCustDto == null ? "" : resCustDto.getResCustId());
		listDto.setResourceGroupId(tagBean.getGroupId());
		listDto.setIsConcat(tagBean.getIsConcat() == null ? "0" : tagBean.getIsConcat() + "");
		listDto.setOrderType(tagBean.getOrderType());
		return listDto;
	}

	public boolean isTop(TaoDto taoDto) {
		if (taoDto.getUpId().equals(taoDto.getTaoResDtos().get(0).getResId())) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isTail(TaoDto taoDto) {
		if (taoDto.getDownId().equals(taoDto.getTaoResDtos().get(taoDto.getTaoResDtos().size() - 1).getResId())) {
			return true;
		} else {
			return false;
		}
	}

	public WxBindBean getWxBindInfo(WxBindBean bean) {
		WxBindBean wxBean = wxBindMapper.findWxBindInfo(bean);
		if (wxBean != null) {
			return wxBean;
		} else {
			return null;
		}
	}

	public synchronized void addOrUpdateWx(WxBindBean bean, String account, String orgId, String type, Integer state) throws Exception {
		if ("unbind".equals(type)) {
			wxBindMapper.deleteBindedByWxId(bean);
		} else {
			WxBindBean wxBean = wxBindMapper.findBindedByWx(bean);
			if (wxBean != null) { // 判断是否有重复的微信id
				wxBindMapper.deleteBindedByWxId(bean);
			}
			wxBean = wxBindMapper.findWxBindInfo(bean);
			if (wxBean != null) { // 判断是否重复的联系人
				wxBindMapper.deleteBindedByLinkId(bean);
			}
			String custId = "";
			if (state == 1) {
				ResCustInfoDetailBean detailBean = resCustInfoDetailService.getByPrimaryKeyAndOrgId(orgId, bean.getLinkNameId(),bean.getCustId());
				if (detailBean != null) {
					custId = detailBean.getRciId();
				}
			} else {
				custId = bean.getLinkNameId();
			}
			bean.setCustId(custId);
			bean.setId(SysBaseModelUtil.getModelId());
			bean.setInputAcc(account);
			bean.setInputDate(new Date());
			wxBindMapper.insert(bean);
		}

	}

	// 客户端登录时，返回已经绑定的信息
	public List<BindWxDto> bindWxList(WxBindBean bean) {
		return wxBindMapper.findWxBindList(bean);
	}

	public Map<String, String> getBindedByWx(WxBindBean bean) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String company = "";
		map.put("code", "");
		map.put("msg", "");
		bean = wxBindMapper.findBindedByWx(bean);
		String custName = "";
		if (bean != null) {
			map.put("code", "1");
			ResCustInfoBean custInfoBean = resCustInfoMapper.getByPrimaryKey(bean.getOrgId(), bean.getCustId());
			if (custInfoBean != null) {
				if (1 == custInfoBean.getState()) {
					ResCustInfoDetailBean detailBean = resCustInfoDetailService.getByPrimaryKeyAndOrgId(bean.getOrgId(), bean.getLinkNameId(),bean.getCustId());
					if (detailBean != null) {
						custName = detailBean.getName() == null ? "" : detailBean.getName();
					}
					company = custInfoBean.getName() == null ? "" : custInfoBean.getName();
					custName = "（" + company + "/" + custName + "）";
				} else {
					company = custInfoBean.getCompany() == null ? "" : custInfoBean.getCompany();
					custName = "（" + company + "/" + custInfoBean.getName() + "）";
				}
				map.put("msg", custName);
			}
		}
		return map;
	}

	public void updateConcat4Res(String custId, String lianXiId, String orgId, String account, String isConcat, String timeLength,ShiroUser user) {
		
		Org org = cachedService.getAuthOrgCatch(orgId);
		int istate = 0;
		int length = StringUtils.isBlank(timeLength) ? 0 : Integer.parseInt(timeLength);
		ResCustInfoBean bean = new ResCustInfoBean();
		Date date = new Date();
		ResCustInfoBean resbean=resCustInfoMapper.getByPrimaryKey(orgId, custId);
		if(resbean!=null){
			bean.setOrgId(orgId);
			bean.setResCustId(custId);
		if(account.endsWith(resbean.getOwnerAcc())){//联系资源的人为该资源的所有者	
		    bean.setIsConcat(1);
		    bean.setOwnerActionDate(date);
			bean.setUpdateAcc(account);
			bean.setUpdateDate(date);
		String custType="res";
        if(resbean.getType() == 2 && resbean.getStatus() == 6){
			custType="sign";
		}else if(resbean.getType() == 2){
			custType="will";
		}else {//bean.getType()== 1
			custType="res";
		}
		planFactService.updateContactResult(orgId, user.getGroupId(), user.getId(), custId, custType,"已联系");
			}
			bean.setActionDate(date);
			resCustInfoMapper.update(bean);
		}
		if (org != null) {
			istate = org.getState() == null ? 0 : org.getState();
		}
//		if (StringUtils.isBlank(isConcat) || Integer.parseInt(isConcat) <= 0) {
//			bean.setIsConcat(1);
//			bean.setActionDate(date);
//			resCustInfoMapper.update(bean);
//		}
		if (length > 0) {
			if (istate == 1) {
				ResCustInfoDetailBean detailBean = new ResCustInfoDetailBean();
				detailBean.setTscidId(lianXiId);
				detailBean.setOrgId(orgId);
				detailBean.setUpdatetime(date);
				resCustInfoDetailService.updateCallNum(detailBean);
//				//资源分析入库
//				ResourceReportBean resrepbean=new ResourceReportBean();
//				resrepbean.setOrgId(orgId);
//				resrepbean.setResCustId(custId);
//				resrepbean.setCalled(1);
//				resrepbean.setCallLength(length);
//				resourceReportService.intsertOrUpdate(resrepbean);
			} else {
				resCustInfoMapper.updateCallNum(bean);
			}

		}
	}
	
	public Map<String, String> getCallNum(int state, String id, String orgId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		String num = "0";
		if (state == 1) {
			ResCustInfoDetailBean bean = resCustInfoDetailService.getByPrimaryKeyAndOrgId(orgId, id,"");
			if (bean != null) {
				num = bean.getCallNum() == null ? "0" : bean.getCallNum();
			}
		} else {
			ResCustInfoBean bean = resCustInfoMapper.getByPrimaryKey(orgId, id);
			if (bean != null) {
				num = bean.getTaoNo() == null ? "0" : bean.getTaoNo() + "";
			}
		}

		map.put("num", num);
		return map;
	}
}
