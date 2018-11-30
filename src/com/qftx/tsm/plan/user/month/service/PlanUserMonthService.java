package com.qftx.tsm.plan.user.month.service;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.team.service.TsmTeamGroupMemberService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.PhoneNumberUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.plan.ResultDTO;
import com.qftx.tsm.plan.group.month.bean.PlanGroupmonthBean;
import com.qftx.tsm.plan.group.month.service.PlanGroupmonthService;
import com.qftx.tsm.plan.user.month.bean.*;
import com.qftx.tsm.plan.user.month.dao.PlanUsermonthCustMapper;
import com.qftx.tsm.plan.user.month.dao.PlanUsermonthMapper;
import com.qftx.tsm.plan.user.month.dto.PlanUserMonthDTO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PlanUserMonthService {
	@Autowired
	private PlanUsermonthMapper planUsermonthMapper;
	@Autowired
	private PlanUsermonthCustMapper custMapper;
	@Autowired
	private PlanGroupmonthService groupService;
	@Autowired
	private PlanUsermonthCommontService commontService;
	@Autowired
	private PlanUsermonthCustService custService;
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired 
	private TsmMessageService tsmMessageService;
	@Autowired
	private CachedService cachedService;
	@Autowired 
	private UserService userService;
	@Autowired
	private TsmTeamGroupMemberService tsmTeamGroupMemberService;
	@Autowired
	private PlanUsermonthToSignLogService planUsermonthToSignLogService;
	@Autowired
	private PlanUsermonthToWillLogService planUsermonthToWillLogService;
	
	Logger logger = Logger.getLogger(PlanUserMonthService.class);
	
	public List<PlanUsermonthBean> findListPage(PlanUsermonthBean entity){
		return planUsermonthMapper.findListPage(entity);
	}
	
	/*获取全年月计划*/
	public Map<String, PlanUserMonthDTO> getPlansFullYear(String orgId,String userId,String year){
		Map<String, PlanUserMonthDTO> map = new HashMap<String, PlanUserMonthDTO>();
		
		List<PlanUsermonthBean> plans = queryByYear(orgId, userId, year);
		for (PlanUsermonthBean planUserMonthBean : plans) {
			String month = planUserMonthBean.getPlanMonth();
			if(month!=null && !map.containsKey(month)){
				PlanUserMonthDTO dto = new PlanUserMonthDTO();
				List<PlanUsermonthCommontBean> userPlanCommonts = commontService.queryByPlanId(orgId,  planUserMonthBean.getId());
				
				dto.setUserPlanCommonts(userPlanCommonts);
				dto.setPlanMonth(planUserMonthBean);
				map.put(month, dto);
			}else{
				logger.error("月计划 planmonth 为空或重复:"+month);
			}
		}
		return map;
	}
	//按日期查询月计划
	public PlanUsermonthBean queryByUserAndMonth(String orgId,String userId,Date date){
		int year = DateUtil.year(date);
		int month = DateUtil.month(date);
		return queryByUserAndMonth(orgId, userId,String.valueOf(year),String.valueOf(month));
	}
	
	//按日期查询月计划
	public PlanUsermonthBean queryByUserAndMonth(String orgId,String userId,String year,String month){
		PlanUsermonthBean bean = new PlanUsermonthBean();
		bean.setOrgId(orgId);
		bean.setUserId(userId);
		bean.setPlanYear(year);
		bean.setPlanMonth(month);
		bean.setIsDel(0);
		
		return planUsermonthMapper.getByCondtion(bean);
	}

	//按照年份月份查询用户计划
	public List<PlanUsermonthBean> queryByGroupIdYearMonth(String orgId,String groupId,String year,String month){
		PlanUsermonthBean bean = new PlanUsermonthBean();
		bean.setOrgId(orgId);
		bean.setGroupId(groupId);
		bean.setPlanYear(year);
		bean.setPlanMonth(month);
		bean.setIsDel(0);
		
		return planUsermonthMapper.findByCondtion(bean);
	}
	//按年查询全年月计划
	public List<PlanUsermonthBean> queryByYear(String orgId,String userId,String year){
		PlanUsermonthBean entity = new PlanUsermonthBean();
		entity.setOrgId(orgId);
		entity.setUserId(userId);
		entity.setPlanYear(year);
		entity.setIsDel(0);
		
		return planUsermonthMapper.findByCondtion(entity);
	}
	
	public List<PlanUsermonthBean> findByCondtion(String orgId,String[] userIds,int planYear,int planMonth){
		PlanUsermonthBean entity = new PlanUsermonthBean();
		entity.setOrgId(orgId);
		entity.setUserIds(userIds);
		entity.setIsDel(0);
		entity.setPlanYear(String.valueOf(planYear));
		entity.setPlanMonth(String.valueOf(planMonth));
		entity.setOrderKey("t3.USER_ACCOUNT");
		return planUsermonthMapper.findByCondtion(entity);
	}
	
	//根据id查询计划
	public PlanUsermonthBean getById(String orgId,String id){
		PlanUsermonthBean entity = new PlanUsermonthBean();
		entity.setOrgId(orgId);
		entity.setId(id);
		entity.setIsDel(0);
		
		return  planUsermonthMapper.getByCondtion(entity);
	}
	
	//查询团队所有已上报计划
	public List<PlanUsermonthBean> findByGroupId(String orgId,String groupId,String planYear,String planMonth){
		PlanUsermonthBean entity = new PlanUsermonthBean();
		entity.setOrgId(orgId);
		entity.setIsDel(0);
		entity.setStatus("1");
		entity.setGroupId(groupId);
		entity.setPlanYear(planYear);
		entity.setPlanMonth(planMonth);
		return planUsermonthMapper.findByCondtion(entity);
	}
	
	//查询团队所有已审核计划
	public List<PlanUsermonthBean> findByGroupId2(String orgId,String groupId,String planYear,String planMonth){
		PlanUsermonthBean entity = new PlanUsermonthBean();
		entity.setOrgId(orgId);
		entity.setIsDel(0);
		entity.setAuthState("2");
		entity.setGroupId(groupId);
		entity.setPlanYear(planYear);
		entity.setPlanMonth(planMonth);
		return planUsermonthMapper.findByCondtion(entity);
	}
	
	//更新最新评论
	public void upDateLastCommontId(String orgId,String planId,String lastCommontId){
		PlanUsermonthBean bean = new PlanUsermonthBean();
		bean.setId(planId);
		bean.setOrgId(orgId);
		bean.setLastCommontId(lastCommontId);
		planUsermonthMapper.updateTrends(bean);
	}
	
	//查询团队所有已上报计划
	public PlanUsermonthBean querySumByGroup(String orgId,String groupId,String planYear,String planMonth){
		PlanUsermonthBean entity = new PlanUsermonthBean();
		entity.setOrgId(orgId);
		entity.setIsDel(0);
		entity.setStatus("1");
		entity.setGroupId(groupId);
		entity.setPlanYear(planYear);
		entity.setPlanMonth(planMonth);
		entity.setAuthState("2");
		
		return planUsermonthMapper.querySumByGroup(entity);
	}
	
	/*保存计划*/
	public ResultDTO savePlan(ShiroUser user,PlanUsermonthBean plan,List<PlanUsermonthCustBean> custs,boolean isNew){
		/*if(plan.getPlanWillcustnumAdd()==0 || plan.getPlanSigncustnumAdd()==0 ||plan.getPlanWillcustnumMoney()==0){
			return ResultDTO.erro("待确定客户数值不能为空或0！");
		}*/
		
		if("1".equals(plan.getStatus())) {
			PlanGroupmonthBean groupPlan = groupService.getByGroupIdAndTime(user.getOrgId(), user.getGroupId(), plan.getPlanYear(),plan.getPlanMonth());
			if(groupPlan !=null && "1".equals(groupPlan.getStatus())){
				return ResultDTO.erro("团队计划已经上报，无法继续上报！");
			}
		}
		ResultDTO custDb = custService.saveCust(plan, custs);;
		
		//PlanUsermonthCustBean dbCustSum = custService.queryCustSum(user.getOrgId(), plan.getId());
		
		int custNum=0;
		double planMoney =0d;
		for (PlanUsermonthCustBean cust : custs) {
			custNum+=cust.getSingcustNum();
			planMoney = BigDecimal.valueOf(planMoney).add(BigDecimal.valueOf(cust.getPlanMoney())).doubleValue();
		}
		
		plan.setPlanWillcustnum(plan.getPlanWillcustnumAdd());
		plan.setPlanSigncustnum(plan.getPlanSigncustnumAdd()+custNum);
		plan.setPlanMoney(BigDecimal.valueOf(plan.getPlanWillcustnumMoney()).add(BigDecimal.valueOf(planMoney)).doubleValue());
		
		if(isNew){
			insert(user,plan);
		}else{
			if("1".equals(plan.getStatus())) plan.setAuthState("1"); 
			update(user,plan);
		}
		if("1".equals(plan.getStatus()))
		tsmMessageService.createAuditDailyMonthlyMessage(plan.getId(),AppConstant.MSG_TYPE_AUDIT_MONTHLY);
		
		return ResultDTO.Success();
	}
	
	public void update(ShiroUser user,PlanUsermonthBean entity){
		planUsermonthMapper.updateTrends(entity);
	}
	
	//查询月计划历史记录
	public List<PlanUsermonthBean> queryHistory(String orgId,String userId,Date from,Date to){
		List<QueryYearMonthBean> querys = getQuery(from, to);
		if(querys.size()>0){
			Map<String , Object> params = new HashMap<String, Object>();
			params.put("orgId", orgId);
			params.put("userId", userId);
			params.put("querys", querys);
			return planUsermonthMapper.queryHistory(params);
		}else{
			logger.debug("查询日期为空！");
			return new ArrayList<PlanUsermonthBean>();
		}
	}
	
	public List<QueryYearMonthBean> getQuery(Date from,Date to){
		List<QueryYearMonthBean> querys = new ArrayList<QueryYearMonthBean>();
		int monthSub = DateUtil.monthSub(from, to);
		
		for(int i=0;i<=monthSub;i++){
			QueryYearMonthBean findByCondtion = new QueryYearMonthBean();
			findByCondtion.setYear(DateUtil.year(from));
			findByCondtion.setMonth(DateUtil.month(from));
			querys.add(findByCondtion);
			from = DateUtil.addDate(from, 0, 1, 0);
		}
		return querys;
	}
	
	//上报退回计划    0未上报，1已上报
	public ResultDTO upReportPlan(String orgId,String id,String status){
		if(status!=null){
			PlanUsermonthBean userPlan = getById(orgId, id);
			
			PlanGroupmonthBean groupPlan = groupService.getByGroupIdAndTime(orgId, userPlan.getGroupId(),userPlan.getPlanYear(), userPlan.getPlanMonth());
			if("0".equals(status)||groupPlan==null||
					(groupPlan!=null && ("0".equals(groupPlan.getStatus())))){
				
				PlanUsermonthBean bean = new PlanUsermonthBean();
				bean.setId(id);
				bean.setOrgId(orgId);
				bean.setStatus(status);
				
				planUsermonthMapper.updateTrends(bean);
				
				if("1".equals(status)) tsmMessageService.createAuditDailyMonthlyMessage(id,AppConstant.MSG_TYPE_AUDIT_MONTHLY);
			
				return ResultDTO.Success();
			}else{
				return ResultDTO.erro("你所在的团队月计划已上报，该月计划无法再上报！");
			}
		}else {
			return ResultDTO.erro("status is empty!");
		}
	}
	
	public List<PlanUsermonthBean> findNoAuthPlan(String orgId,Date date){
		PlanUsermonthBean entity = new PlanUsermonthBean();
		entity.setOrgId(orgId);
		entity.setAuthState("1");
		entity.setStatus("1");
		entity.setIsDel(0);
		entity.setPlanYear(String.valueOf(DateUtil.year(date)));
		entity.setPlanMonth(String.valueOf(DateUtil.month(date)));
		return planUsermonthMapper.findNoAuthPlan(entity);
	}
	
	//审核计划
	//审核通过后汇总到团队计划
	public List<String> authPlan(String orgId,String authUserid,String authUsername,String[] ids,String authState,String authDesc){ 
		List<String> erroList= new ArrayList<String>();
		if("2".equals(authState)){
			for (String id : ids) {
				authSingle(orgId, id, authState, authUserid, authUsername, authDesc);
				
				PlanUsermonthBean userPlan = getById(orgId, id);
				String groupId = userPlan.getGroupId();
				String planYear = userPlan.getPlanYear();
				String planMonth = userPlan.getPlanMonth();
				if(groupId!=null && planYear!=null && planMonth!=null){
					
					PlanGroupmonthBean groupPlan = groupService.getByGroupIdAndTime(orgId, groupId, planYear, planMonth);
					if(groupPlan!=null){
						if("1".equals(groupPlan.getStatus())){
							logger.error("teamPlan is up report!:groupId["+groupId+"]teamPlanId["+groupPlan.getId()+"]");
							erroList.add(id);
							continue;
						}else{
							groupService.updateTeamPlan(orgId, groupId, planYear, planMonth,groupPlan);
						}
					}
				}
				
			}
		}else if("0".equals(authState)){
			authBatch(orgId, ids, authState, authUserid, authUsername, authDesc);
		}
		return erroList;
	}
	/*单条审核*/
	public void authSingle(String orgId,String id,String authState,String authUserid,String authUsername,String authDesc){
		PlanUsermonthBean bean = new PlanUsermonthBean();
		bean.setId(id);
		bean.setOrgId(orgId);
		bean.setAuthState(authState);
		bean.setAuthUserid(authUserid);
		bean.setAuthUsername(authUsername);
		bean.setAuthDesc(authDesc);
		bean.setAuthTime(new Date());
		planUsermonthMapper.updateTrends(bean);
	}
	/*批量审核  只适用于驳回*/
	public void authBatch(String orgId,String[] ids,String authState,String authUserid,String authUsername,String authDesc){
		PlanUsermonthBean bean = new PlanUsermonthBean();
		bean.setIds(ids);
		bean.setOrgId(orgId);
		bean.setAuthState(authState);
		bean.setAuthUserid(authUserid);
		bean.setAuthUsername(authUsername);
		bean.setAuthDesc(authDesc);
		bean.setAuthTime(new Date());
		planUsermonthMapper.updateTrends(bean);
	}
	
	//更新总计划实际执行数 参数为增量
	public void updateFactNum(PlanUsermonthBean plan,String orgId,int factWillcustnum,int factSigncustnum,double factMoney){
		PlanUsermonthBean bean = new PlanUsermonthBean();
		bean.setId(plan.getId());
		bean.setOrgId(orgId);
		bean.setFactWillcustnum(factWillcustnum);
		bean.setFactSigncustnum(factSigncustnum);
		bean.setFactMoney(factMoney);
		if(factWillcustnum>0 ||factSigncustnum>0||factMoney>0){
			if(plan.getPlanWillcustnumState()==0 &&
					(factWillcustnum+plan.getFactWillcustnum())>=plan.getPlanWillcustnum()){
				bean.setPlanWillcustnumState(1);
			}
			
			if(plan.getPlanSigncustnumState()==0 &&
					(factSigncustnum+plan.getFactSigncustnum())>=plan.getPlanSigncustnum()){
				bean.setPlanSigncustnumState(1);
			}
			
			if(plan.getPlanMoneyState()==0 &&
					(factMoney+plan.getFactMoney())>=plan.getPlanMoney()){
				bean.setPlanMoneyState(1);
			}
			
			if("0".equals(plan.getStatus())&&
					(factWillcustnum+plan.getFactWillcustnum())>=plan.getPlanWillcustnum() &&
					(factSigncustnum+plan.getFactSigncustnum())>=plan.getPlanSigncustnum() &&
					(factMoney+plan.getFactMoney())>=plan.getPlanMoney()){
				bean.setPlanStatus("1");
			}
		}else{
			logger.debug("calcen sign orgId "+orgId+" plan.getid "+plan.getId()+" factWillcustnum "+factWillcustnum+" factSigncustnum "+factSigncustnum+" factMoney "+factMoney);
			if(plan.getPlanWillcustnumState()==1 &&
					(factWillcustnum+plan.getFactWillcustnum()) < plan.getPlanWillcustnum()){
				bean.setPlanWillcustnumState(0);
			}
			
			if(plan.getPlanSigncustnumState()==1 &&
					(factSigncustnum+plan.getFactSigncustnum()) < plan.getPlanSigncustnum()){
				bean.setPlanSigncustnumState(0);
			}
			
			if(plan.getPlanMoneyState()==1 &&
					(factMoney+plan.getFactMoney()) < plan.getPlanMoney()){
				bean.setPlanMoneyState(0);
			}
			
			if("1".equals(plan.getStatus())&&(
					(factWillcustnum+plan.getFactWillcustnum()) < plan.getPlanWillcustnum() ||
					(factSigncustnum+plan.getFactSigncustnum()) < plan.getPlanSigncustnum() ||
					(factMoney+plan.getFactMoney()) < plan.getPlanMoney()
					)
					){
				bean.setPlanStatus("0");
			}
			
		}
		planUsermonthMapper.updateFactNum(bean);
	}
	
	//更新计划待确定客户实际执行数 参数为增量
	public void updateFactAddNum(PlanUsermonthBean plan,String orgId,int factWillcustnumAdd,int factSigncustnumAdd,double factWillcustnumMoney){
		PlanUsermonthBean bean = new PlanUsermonthBean();
		bean.setId(plan.getId());
		bean.setOrgId(orgId);
		
		bean.setFactWillcustnumAdd(factWillcustnumAdd);
		bean.setFactSigncustnumAdd(factSigncustnumAdd);
		bean.setFactWillcustnumMoney(factWillcustnumMoney);
		
		if(factWillcustnumAdd>0|| factSigncustnumAdd>0 ||factWillcustnumMoney>0){
			if(plan.getWaitConfirmCustState()==0 &&
					(factWillcustnumAdd+plan.getFactWillcustnumAdd())>=plan.getPlanWillcustnumAdd() &&
					(factSigncustnumAdd+plan.getFactSigncustnumAdd())>=plan.getPlanSigncustnumAdd() &&
					(factWillcustnumMoney+plan.getFactWillcustnumMoney())>=plan.getPlanWillcustnumMoney()
			)
				bean.setWaitConfirmCustState(1);
		}else{
			if(plan.getWaitConfirmCustState()==1 && 
					(
						(factWillcustnumAdd+plan.getFactWillcustnumAdd()) < plan.getPlanWillcustnumAdd() ||
						(factSigncustnumAdd+plan.getFactSigncustnumAdd()) < plan.getPlanSigncustnumAdd() ||
						(factWillcustnumMoney+plan.getFactWillcustnumMoney()) < plan.getPlanWillcustnumMoney()
				    )
			)
				bean.setWaitConfirmCustState(0);
		}
		planUsermonthMapper.updateFactAddNum(bean);
	}
	
	public boolean isLastTwoMonthPlanOver(String orgId,String userId){
		Date date = new Date();
		//上月
		PlanUsermonthBean month1 = queryByUserAndMonth(orgId, userId, DateUtil.addDate(date, 0, -1, 0));
		//上上月
		PlanUsermonthBean month2 = queryByUserAndMonth(orgId, userId, DateUtil.addDate(date, 0, -2, 0));
		
		if(month1!=null && month2!=null && "2".equals(month1.getPlanStatus())&& "2".equals(month2.getPlanStatus())){
			return true;
		}else{
			return false;
		}
	}
	
	public Point receivePoint(String orgId,String id,String userId,String userAccount,String type,boolean isLast){
		PointCache pointCache = new PointCache(orgId, cachedService);
		
		int newPoints = 0;
		
		PlanUsermonthBean bean = new PlanUsermonthBean();
		bean.setOrgId(orgId);
		bean.setId(id);
		if("all".equals(type)){
			bean.setPlanWillcustnumState(2);
			bean.setPlanSigncustnumState(2);
			bean.setPlanMoneyState(2);
			bean.setPlanStatus("2");
			newPoints = pointCache.getSinglePoint()*3 +pointCache.getPlanPoint();
			
			if(isLastTwoMonthPlanOver(orgId, userId)){
				newPoints +=pointCache.getPlan3MonthPoint();
			}
			
		}else if("will".equals(type)){
			newPoints =pointCache.getSinglePoint();
			bean.setPlanWillcustnumState(2);
		}else if("sign".equals(type)){
			newPoints =pointCache.getSinglePoint();
			bean.setPlanSigncustnumState(2);
		}else if("money".equals(type)){
			newPoints =pointCache.getSinglePoint();
			bean.setPlanMoneyState(2);
		}else{
			logger.error("type is undefined:"+type);
		}
		
		if(isLast){
			newPoints+=pointCache.getPlanPoint();
			if(isLastTwoMonthPlanOver(orgId, userId)){
				newPoints +=pointCache.getPlan3MonthPoint();
			}
			bean.setPlanStatus("2");
		}
		
		User user = userService.getByAccount(userAccount);
		Integer dbPoint = user.getPoints()==null ?0:user.getPoints();
		
		Point point = new Point();
		point.setDbPoint(dbPoint);
		point.setNewPoint(newPoints);
		
		
		dbPoint+=newPoints;
		
		//修改积分
		User updateUser = new User();
		updateUser.setOrgId(orgId);
		updateUser.setUserId(userId);
		updateUser.setPoints(dbPoint);
		
		userService.modify(updateUser);
		
		
		planUsermonthMapper.updateTrends(bean);
		return point;
	}
	
	//查询所有客户包含意向客户和签约客户
	public List<ResCustInfoDto> getAllCusts(String orgId,String userAccount,String[] custIds,ResCustInfoDto custInfoDto) throws Exception{
		List<String> rejectIds = Arrays.asList(custIds);
		custInfoDto.setOrgId(orgId);
		custInfoDto.setOwnerAcc(userAccount);
		custInfoDto.setRejectIds(rejectIds);
		custInfoDto.setQueryPhone(PhoneNumberUtil.isPhoneNumber(custInfoDto.getQueryText()));
		List<ResCustInfoDto> list = resCustInfoService.getAllTypeCustListPage(custInfoDto,null);
		
		if(list!=null && list.size()>0){
			Map<String, String> map = cachedService.getOrgSaleProcess(orgId);
			Map<String, String> map1 = cachedService.getOrgCustTypes(orgId);
			for (ResCustInfoDto resCustInfoDto : list) {
				if(map!=null && map.size()>0) resCustInfoDto.setSaleProcessName(map.get(resCustInfoDto.getLastOptionId()));
				if(map1!=null && map1.size()>0) resCustInfoDto.setCustTypeName(map1.get(resCustInfoDto.getCustTypeId()));;
			}
		}
		
		return list;
	}
	
	public PlanUsermonthBean newBean(String orgId,String groupId,String userId,String planYear,String planMonth){
		//TODO 插入计划同时插入 plan_groupmonth_user表
		Date date = new Date();
		PlanUsermonthBean bean = new PlanUsermonthBean();
		bean.setId(GuidUtil.getGuid());
		bean.setOrgId(orgId);
		bean.setGroupId(groupId); 
		bean.setUserId(userId);
		bean.setPlanYear(planYear);
		bean.setPlanMonth(planMonth);
		bean.setPlanMoney(0d);
		bean.setPlanSigncustnum(0);
		bean.setPlanWillcustnum(0);
		bean.setFactMoney(0d);
		bean.setFactSigncustnum(0);
		bean.setFactWillcustnum(0);
		bean.setPlanWillcustnumAdd(0);
		bean.setPlanSigncustnumAdd(0);
		bean.setPlanWillcustnumMoney(0d);
		bean.setFactWillcustnumAdd(0);
		bean.setFactSigncustnumAdd(0);
		bean.setFactWillcustnumMoney(0d);
		
		bean.setWaitConfirmCustState(0);
		bean.setPlanMoneyState(0);
		bean.setPlanSigncustnumState(0);
		bean.setPlanWillcustnumState(0);
		bean.setPlanStatus("0");
		bean.setStatus("0");
		bean.setAuthState("1");
		bean.setInputtime(date);
		bean.setUpdatetime(date);
		bean.setIsDel(0);
		
		return bean;
	}
	
	//插入月计划
	public PlanUsermonthBean insert(ShiroUser user,PlanUsermonthBean bean){
		
		String planMonth =bean.getPlanMonth();
		String planyear = bean.getPlanYear();
		
		Date date = new Date();
		if(DateUtil.year(date)==Integer.parseInt(planyear)&&DateUtil.month(date)==Integer.parseInt(planMonth)){
			PlanUsermonthToWillLogBean will = planUsermonthToWillLogService.findSumFact(user.getOrgId(), new String[]{user.getId()}, date);
			PlanUsermonthToSignLogBean sign = planUsermonthToSignLogService.findSumFact(user.getOrgId(), new String[]{user.getId()}, date);
			double factMoney = sign.getMoney();
			if(factMoney!=0){
				BigDecimal bigDecimal =new BigDecimal(factMoney/10000);  
				factMoney = bigDecimal.setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue();  
			}
			bean.setFactWillcustnum(will.getWillNum());
			bean.setFactSigncustnum(sign.getSignNum());
			bean.setFactMoney(factMoney);
			
			bean.setFactWillcustnumAdd(will.getWillNum());
			bean.setFactSigncustnumAdd(sign.getSignNum());
			bean.setFactWillcustnumMoney(factMoney);
		}else{
			bean.setFactSigncustnum(0);
			bean.setFactWillcustnum(0);
			bean.setFactMoney(0d);
			bean.setFactWillcustnumAdd(0);
			bean.setFactSigncustnumAdd(0);
			bean.setFactWillcustnumMoney(0d);
		}
		bean.setUserId(user.getId());
		bean.setGroupId(user.getGroupId());
		bean.setWaitConfirmCustState(0);
		bean.setPlanMoneyState(0);
		bean.setPlanSigncustnumState(0);
		bean.setPlanWillcustnumState(0);
		bean.setPlanStatus("0");
		bean.setAuthState("1");
		bean.setInputtime(date);
		bean.setUpdatetime(date);
		bean.setIsDel(0);
		
		planUsermonthMapper.insert(bean);
		return bean;
	}
	
	/** 
	 * 领取计划任务积分
	 * @param orgId 单位ID
	 * @param userAccount 用户账号
	 * @param pb 月计划
	 * @param type 类型 1-销售金额 2-签约客户 3-意向客户
	 * @param reward 奖励积分
	 * @return 用户当前积分
	 * @create  2016年2月19日 下午2:51:44 lixing
	 * @history  
	 */
	public Integer receivePoints(String orgId,String userAccount,PlanUsermonthBean pb,String type,Map<String, DataDictionaryBean> dictionaryMap){
		Integer totalPoints = 0;
		
		String dirVal = dictionaryMap.get(AppConstant.DATA_40022).getDictionaryValue();
		Integer rewardPoints = Integer.parseInt(StringUtils.isNotBlank(dirVal) ? dirVal : "0");//单个计划完成奖励积分
		
		String dirVal1 = dictionaryMap.get(AppConstant.DATA_40023).getDictionaryValue();
		Integer rewardPoints1 = Integer.parseInt(StringUtils.isNotBlank(dirVal1) ? dirVal1 : "0");//月计划完成奖励积分
		
		String dirVal2 = dictionaryMap.get(AppConstant.DATA_40024).getDictionaryValue();
		Integer rewardPoints2 = Integer.parseInt(StringUtils.isNotBlank(dirVal2) ? dirVal2 : "0");//月计划完成奖励积分
		
		User user = userService.getByAccount(userAccount);
		Integer userPoints = user.getPoints() == null ? 0 : user.getPoints();
		totalPoints=userPoints+rewardPoints;
		
		Integer allState = null;
		if(type.equals("1")){
			if(pb.getPlanSigncustnumState() ==2 && pb.getPlanWillcustnumState() == 2){
				allState = 2;
				totalPoints+=rewardPoints1;
			}
		}else if(type.equals("2")){
			if(pb.getPlanMoneyState() ==2 && pb.getPlanWillcustnumState() == 2){
				allState = 2;
				totalPoints+=rewardPoints1;
			}
		}else{
			if(pb.getPlanSigncustnumState() ==2 && pb.getPlanMoneyState() == 2){
				allState = 2;
				totalPoints+=rewardPoints1;
			}
		}
		
		if(allState !=null && isLastTwoMonthPlanOver(orgId, user.getUserId())){
			totalPoints+=rewardPoints2;
		}
		
		user.setPoints(totalPoints);
		//修改积分
		userService.modify(user);
		//修改状态
		planUsermonthMapper.receivePointsByType(pb.getId(), orgId, type,allState);
		return totalPoints;
	}
	//查询未提交计划的用户
	public List<String> noPlanUsers(String orgId,List<String> userIds,Date date){
		PlanUsermonthBean entity = new PlanUsermonthBean();
		entity.setOrgId(orgId);
		entity.setUserIds(userIds.toArray(new String[userIds.size()]));
		entity.setStatus("1");
		entity.setPlanYear(String.valueOf(DateUtil.year(date)));
		entity.setPlanMonth(String.valueOf(DateUtil.month(date)));
		
		return planUsermonthMapper.findNoReportUsers(entity);
	}
}
