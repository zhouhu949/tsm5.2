package com.qftx.tsm.contract.service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dao.UserMapper;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.pay.api.PayApiFacade;
import com.qftx.pay.api.PayApiRequest;
import com.qftx.pay.api.PayApiTypeEnum;
import com.qftx.tsm.contract.bean.*;
import com.qftx.tsm.contract.dao.*;
import com.qftx.tsm.contract.dto.*;
import com.qftx.tsm.contract.scontrol.ContractAction;
import com.qftx.tsm.cust.bean.CustDefinedDataBean;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.cust.dao.CustDefinedDataMapper;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.dto.SignDto;
import com.qftx.tsm.cust.service.ResCustEventService;
import com.qftx.tsm.cust.service.TsmCustGuideProcService;
import com.qftx.tsm.cust.service.TsmCustGuideService;
import com.qftx.tsm.cust.service.TsmCustSynConfigService;
import com.qftx.tsm.follow.bean.CustFollowBean;
import com.qftx.tsm.follow.dto.CustFollowDto;
import com.qftx.tsm.follow.service.CustFollowService;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.log.service.LogContactDayDataService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.log.util.ContactUtil;
import com.qftx.tsm.main.service.MainService;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dto.MoneyJsonDto;
import com.qftx.tsm.option.service.DataDictionaryService;
import com.qftx.tsm.plan.facade.PlanFactService;
import com.qftx.tsm.plan.facade.enu.PlanResCR;
import com.qftx.tsm.plan.facade.enu.PlanSignCR;
import com.qftx.tsm.plan.facade.enu.PlanWillCR;
import com.qftx.tsm.report.dto.SilenceCustDto;
import com.qftx.tsm.report.service.CustInfoNalysisService;
import com.qftx.tsm.report.service.RankingReportService;
import com.qftx.tsm.report.service.SilenceCustService;
import com.qftx.tsm.report.service.TsmReportPlanService;
import com.qftx.tsm.sys.bean.Product;
import com.qftx.tsm.sys.dao.SysFileMapper;

import net.sf.json.JSONObject;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

@Service
public class ContractService {
	@Autowired
	private ContractMapper contractMapper;
	@Autowired
	private ContractAuthMapper contractAuthMapper;
	@Autowired
	private ContractOrderMapper contractOrderMapper;
	@Autowired
	private ContractOrderDetailMapper contractOrderDetailMapper;
	@Autowired
	private ContractFileMapper contractFileMapper;
	@Autowired
	private ContractMoneyMapper contractMoneyMapper;
	@Autowired
	private SysFileMapper sysFileMapper;
	@Autowired
	private ContractOrderAuthlogMapper contractOrderAuthlogMapper;
	@Autowired
	private ResCustInfoMapper resCustInfoMapper;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private PlanFactService planFactService;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private ResCustEventService resCustEventService;
	@Autowired
	private TsmMessageService tsmMessageService;
	@Autowired
	private SilenceCustService silenceCustService;
	@Autowired
	private RankingReportService rankingReportService;
	@Autowired
	private DataDictionaryService dataDictionaryService;
	@Autowired
	private CustInfoNalysisService custInfoNalysisService;
	@Autowired
	private OrgGroupUserService orgGroupUserService;
	@Autowired
	private CustFollowService custFollowService;
	@Autowired
	private UserService userService;
	@Autowired
	private LogCustInfoService logCustInfoService;
	@Autowired
	private MainService mainService;
	@Autowired
	private LogUserOperateService logUserOperateService;
	@Autowired
	private CustDefinedDataMapper custDefinedDataMapper;
	@Autowired
	private LogContactDayDataService logContactDayDataService;
	@Autowired
	private TsmCustGuideService tsmCustGuideService;
	@Autowired
	private TsmCustGuideProcService tsmCustGuideProcService;
	@Autowired
	private TsmCustSynConfigService tsmCustSynConfigService;
	@Autowired
	private PayApiFacade payApiFacade;
	@Autowired
	private TsmReportPlanService tsmReportPlanService;
	
	private static final Logger logger = Logger.getLogger(ContractService.class);
	
	public Integer getCustContractNum(String orgId,String custId){
		ContractBean bean = new ContractBean();
		bean.setOrgId(orgId);
		bean.setCustId(custId);
		bean.setIsDel(0);
		return contractMapper.findByCondtion(bean).size();
	}
	
	public Integer getCustContractOrderNum(String orgId,String custId){
		ContractOrderBean bean = new ContractOrderBean();
		bean.setOrgId(orgId);
		bean.setCustId(custId);
		bean.setIsDel(0);
		bean.setAuthState(2);
		return contractOrderMapper.findByCondtion(bean).size();
	}
	
	public List<ContractBean> getContractListPage(ContractBean bean){
		return contractMapper.findListPage(bean);
	}
	
	public List<ContractOrderDto> getContractOrderListPage(ContractOrderDto bean){
		return contractOrderMapper.findCardListPage(bean);
	}
	
	/** 
	 * 新增合同
	 * @param cb 
	 * @create  2015年12月1日 下午2:33:59 lixing
	 * @return 1-签约 2-新增合同
	 * @throws Exception 
	 * @history  
	 */
	public int addContract(ContractBean cb,List<String> fileIds,ShiroUser user,String saleProc,String module,Object[] obj,SignDto signDto) throws Exception{
		//判断是否需要签约
		int rt;
		ResCustInfoBean resCustInfoBean = resCustInfoMapper.getByPrimaryKey(cb.getOrgId(), cb.getCustId());
		Date opDate = new Date();
		if(resCustInfoBean.getStatus() != 6){
			rt = 1;
			//查询groupId
			OrgGroupUser usermeber = new OrgGroupUser();
            usermeber.setOrgId(user.getOrgId());
            usermeber.setMemberAcc(resCustInfoBean.getOwnerAcc());
            OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
			newmember = newmember == null ? new OrgGroupUser() : newmember;
			//修改计划
			planFactService.toSign(user.getOrgId(),newmember.getGroupId(),newmember.getUserId(), cb.getCustId(), 1, 0);
			if((resCustInfoBean.getStatus() == 2 || resCustInfoBean.getStatus() == 3) 
					&& (resCustInfoBean.getType() == 1 || resCustInfoBean.getType() == 3) ){//资源
				logContactDayDataService.addLogContactDayData(ContactUtil.RES_TO_SIGN, user.getOrgId(), resCustInfoBean.getResCustId(),resCustInfoBean.getOwnerAcc(), null, saleProc);
//				contactDayDataService.contactStatusLog(user.getOrgId(), newmember.getMemberAcc(), cb.getCustId(), 1,2);
				planFactService.updateContactResult(user.getOrgId(), newmember.getGroupId(), newmember.getUserId(), resCustInfoBean.getResCustId(), "res", PlanResCR.TURN_SIGN.getResult());
				resCustInfoBean.setAmoytocustomerDate(opDate);
			}else{//意向
				logContactDayDataService.addLogContactDayData(ContactUtil.CUST_TO_SIGN, user.getOrgId(), resCustInfoBean.getResCustId(),resCustInfoBean.getOwnerAcc(), resCustInfoBean.getLastOptionId(), saleProc);
//				contactDayDataService.contactStatusLog(user.getOrgId(), newmember.getMemberAcc(), cb.getCustId(), 2,2);
				planFactService.updateContactResult(user.getOrgId(), newmember.getGroupId(), newmember.getUserId(), resCustInfoBean.getResCustId(), "will", PlanWillCR.TURN_SIGN.getResult());
			}
			
			resCustInfoBean.setActionDate(opDate);
			resCustInfoBean.setSignAcc(user.getAccount());
			resCustInfoBean.setSignName(user.getName());
			resCustInfoBean.setSignDate(opDate);
			resCustInfoBean.setUpdateAcc(user.getAccount());
			resCustInfoBean.setUpdateDate(opDate);
			resCustInfoBean.setOpreateType(AppConstant.OPREATE_TYPE15.intValue());
			resCustInfoBean.setSignBeforeStatus(resCustInfoBean.getStatus());
			resCustInfoBean.setSignBeforeType(resCustInfoBean.getType());
			resCustInfoBean.setStatus(AppConstant.STATUS_6.intValue());
			resCustInfoBean.setType(AppConstant.CUST_TYPE2.intValue());
			if(resCustInfoBean.getType() == 1){
				resCustInfoBean.setAmoytocustomerDate(opDate);
			}
			resCustInfoMapper.update(resCustInfoBean);
			
			silenceCustService.updateSilentLossReport(null,1, 1,user);
			if(cb.getInvalidDate() != null){
				silenceCustService.updateSilentLossReport(cb.getInvalidDate(),4, 1,user);
			}
			//新客户
			planFactService.updateContractNum(user.getOrgId(), newmember.getGroupId(), 1, 1);
			rankingReportService.updateRankingData(user.getOrgId(), newmember.getMemberAcc(), BigDecimal.valueOf(0), 1, 0);
//			custInfoNalysisService.saveOrUpdate(resCustInfoBean.getResCustId(), 2,user.getOrgId());
			
			//开放接口
			 if(tsmCustSynConfigService.isConfigExist(resCustInfoBean.getOrgId(), 2))  sign_api(opDate, resCustInfoBean, obj, signDto);
		}else{
			rt = 2;
			Date signDate = resCustInfoBean.getSignDate();
			if(signDate == null || signDate.before(DateUtil.monthBegin(opDate))){
				//老客户
				planFactService.updateContractNum(user.getOrgId(), user.getGroupId(), 0, 1);
			}else{
				//新客户
				planFactService.updateContractNum(user.getOrgId(), user.getGroupId(), 1, 1);
			}

				planFactService.updateContactResult(user.getOrgId(), user.getGroupId(), user.getId(), resCustInfoBean.getResCustId(), "sign", PlanSignCR.TURN_SIGN.getResult());
			
			if(cb.getInvalidDate() != null){
				Map<String, String> map = new HashMap<String, String>();
				SilenceCustDto sBean = new SilenceCustDto();
				sBean.setAccount(user.getAccount());
				sBean.setOrgId(user.getOrgId());
				sBean.setCurrDate(DateUtil.getDataMonth(cb.getInvalidDate()));
				map.put("orgId", ShiroUtil.getUser().getOrgId());
				map.put("custId",resCustInfoBean.getResCustId());
				map.put("account", ShiroUtil.getUser().getAccount());
				map.put("currDate", sBean.getCurrDate());
				ContractBean conBen = contractMapper.findDateExpireHT(map);//判断该资源在指定时间是否存在签约合同
				if(conBen==null){
					silenceCustService.updateSilentLossReport(cb.getInvalidDate(),4, 1,user);
				}
			}
		}
		//插入合同
		contractMapper.insert(cb);
		//插入附件
		for(String fileId : fileIds){
//			sysFileMapper.insert(sfb);
			ContractFileBean cfb = new ContractFileBean();
			cfb.setId(SysBaseModelUtil.getModelId());
			cfb.setCaId(cb.getId());
			cfb.setFileId(fileId);
			cfb.setOrgId(cb.getOrgId());
			cfb.setInputtime(cb.getInputtime());
			cfb.setIsDel(0);
			cfb.setType(1);
			contractFileMapper.insert(cfb);
		}
		resCustEventService.create(user.getOrgId(), cb.getCustId(), "5", JSONObject.fromObject(getEventsMap(cb)).toString());
		String resName = "";
		if(StringUtils.isNotBlank(resCustInfoBean.getName())){
			resName = resCustInfoBean.getName();
		}
     // 需要删除 多选项表中 行动标签值
     		Map<String,Object>delMap = new HashMap<String, Object>();
     		delMap.put("orgId", user.getOrgId());
     		delMap.put("custId", cb.getCustId());
     		delMap.put("fieldCode", resCustInfoBean.getLabelCode());
     		custDefinedDataMapper.deleteByFieldCode(delMap);
     		if(StringUtils.isNotBlank(resCustInfoBean.getLabelCode())){
     			List<CustDefinedDataBean>mulitBeans = new ArrayList<CustDefinedDataBean>();
     			for(String labelCode:resCustInfoBean.getLabelCode().split("#")){
     				CustDefinedDataBean mulitDefinedBean = new CustDefinedDataBean();
     				mulitDefinedBean.setId(SysBaseModelUtil.getModelId());
     				mulitDefinedBean.setOrgId(user.getOrgId());
     				mulitDefinedBean.setCustId(cb.getCustId());
     				mulitDefinedBean.setFieldCode("labelCode");
     				mulitDefinedBean.setFieldData(labelCode);
     				mulitBeans.add(mulitDefinedBean);
     			}
     			if(mulitBeans != null && mulitBeans.size()>0){
     				custDefinedDataMapper.insertBatch(mulitBeans);
     			}			
     		}

        
        
        StringBuffer context = new StringBuffer("");
		if(rt == 1){
			LogBean logBean = new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_SIGN.getCode(), OperateEnum.LOG_SIGN.getDesc(), 1,SysBaseModelUtil.getModelId());
			logBean.setOperateId(AppConstant.Operate_id8);
			logBean.setOperateName(AppConstant.Operate_Name8);
			if(StringUtils.isNotBlank(module)){
				if(user.getIsState() == 1 || StringUtils.isEmpty(resCustInfoBean.getCompany())){
					context.append(StringUtils.isEmpty(resCustInfoBean.getName()) ? "" : resCustInfoBean.getName());
				}else{
					context.append(StringUtils.isEmpty(resCustInfoBean.getCompany()) ? "" : resCustInfoBean.getCompany());
				}
//				if(StringUtils.isNotBlank(resCustInfoBean.getMobilephone())) context.append("(").append(resCustInfoBean.getMobilephone()).append(")");
				if(module.equals("1")){
					logBean.setModuleId(AppConstant.Module_id1001);
					logBean.setModuleName(AppConstant.Module_Name1001);
				}else if(module.equals("2")){
					logBean.setModuleId(AppConstant.Module_id1000);
					logBean.setModuleName(AppConstant.Module_Name1000);
				}else if(module.equals("0")){
					logBean.setModuleId(AppConstant.Module_id9);
					logBean.setModuleName(AppConstant.Module_Name9);
				}else if(module.equals("3")){
					logBean.setModuleId(AppConstant.Module_id113);
					logBean.setModuleName(AppConstant.Module_Name113);
				}else if(module.equals("4")){
					logBean.setModuleId(AppConstant.Module_id119);
					logBean.setModuleName(AppConstant.Module_Name119);
				}
				logBean.setContent(context.toString());
			}
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(cb.getCustId(), "");
			logCustInfoService.addTableStoreLog(logBean, logMap);
			//发送弹幕消息
//			tsmMessageService.sendBarrage("恭喜"+(user.getName() == null ? user.getAccount() : user.getName())+"成功签约"+resName+"!",user.getOrgId(),user.getAccount(),user.getName());
			boolean result=false;
			if(signDto != null && signDto.getFollowDate()!=null&&!"".endsWith(signDto.getFollowDate())){
				 result = DateUtil.isNow(DateUtil.parseDate(signDto.getFollowDate()));
				if(result==true){
					planFactService.updateContactResult(user.getOrgId(),user.getGroupId(),user.getId(),resCustInfoBean.getResCustId(),"res",true,"sign");	
				}
			}
			if(mainService.getBarrageSign(user.getOrgId())==1&&mainService.getBarrageSign3(user.getOrgId())==0){
				String retName="";
				if(mainService.getBarrageSign2(user.getOrgId())==0){
					retName=((resCustInfoBean.getCompany() == null || "".equals(resCustInfoBean.getCompany())) ? resName : resCustInfoBean.getCompany());
				}
				if(retName==null){
					retName="";
				}
				mainService.sendSingBarrage("恭喜"+(user.getName() == null ? user.getAccount() : user.getName())+"成功签约"+retName+"!", "1",user.getOrgId(),user.getAccount(),user.getName(),String.valueOf(cb.getMoney()),0);
				List<DataDictionaryBean> dlist0 = cachedService.getDirList(AppConstant.DATA_50047, user.getOrgId());//红包设置
				if ((!dlist0.isEmpty() && !"0".equals(dlist0.get(0).getDictionaryValue()))) {
					PayApiRequest payrequest=new PayApiRequest();
					
					payrequest.setOrgId(user.getOrgId());
					payrequest.setUserAcc(user.getAccount());
					payrequest.setPayApiEnum(PayApiTypeEnum.SIGN);
					payrequest.setMoney(Double.valueOf(dlist0.get(0).getDictionaryValue()));
					payrequest.setDesc((user.getName() == null ? user.getAccount() : user.getName())+"获得签约奖励红包"+dlist0.get(0).getDictionaryValue()+"元！");
					payrequest.setData("恭喜"+(user.getName() == null ? user.getAccount() : user.getName())+"成功签约"+retName);
					BaseJsonResult json=payApiFacade.pay(payrequest);
					if((boolean) json.get("status")==false){
						logger.error("-------调用pay接口返回结果：--------"+JSONObject.fromObject(json)+"时间："+new Date()); 	
					}
					
		        }
			
			}
		}else{
			if(user.getIsState() == 1 || StringUtils.isEmpty(resCustInfoBean.getCompany())){
				context.append(StringUtils.isEmpty(resCustInfoBean.getName()) ? "" : resCustInfoBean.getName());
			}else{
				context.append(StringUtils.isEmpty(resCustInfoBean.getCompany()) ? "" : resCustInfoBean.getCompany());
			}
//			if(StringUtils.isNotBlank(resCustInfoBean.getMobilephone())) context.append("(").append(resCustInfoBean.getMobilephone()).append(")");
			context.append("，合同编号："+cb.getCode());
			logUserOperateService.setUserOperateLog( AppConstant.Module_id1002,AppConstant.Module_Name1002, AppConstant.Operate_id15, AppConstant.Operate_Name15, context.toString(), "");
		}
        return rt;
	}
	
	public void sign_api(Date signDate,ResCustInfoBean cust,Object[] obj,SignDto signDto) throws Exception{
		Map<String, String> data = new HashMap<String, String>();
		data.put("id", SysBaseModelUtil.getModelId());
		Map<String, String> nameMap = cachedService.getOrgUserNames(cust.getOrgId());
		// 从缓存读取销售进程列表
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, cust.getOrgId());
        Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
        // 从缓存读取客户类型列表
        List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, cust.getOrgId());
        Map<String, String> typeNameMap = cachedService.changeOptionListToMap(typeOptions);
        List<Product> products = cachedService.getOpionProduct(cust.getOrgId());
        Map<String, String> productMap = new HashMap<String, String>();
        for(Product pro : products) productMap.put(pro.getId(), pro.getName());
        if(cust.getState() == 1){//企业
			data.put("custName", cust.getName());
		}else{//个人
			data.put("custName", cust.getCompany());
			data.put("linkName", cust.getName());
		}
		data.put("ownerAcc", StringUtils.isBlank(nameMap.get(cust.getOwnerAcc())) ? cust.getOwnerAcc() : nameMap.get(cust.getOwnerAcc()));
		data.put("custStatus", "签约客户");
		data.put("signDate", DateUtil.formatDate(signDate, DateUtil.Data_ALL));
		if(obj != null){
			CustFollowBean follow = (CustFollowBean) obj[3];
			TsmCustGuide guide = (TsmCustGuide) obj[2];
			String suitProcId = (String) obj[1];
			data.put("saleProcess", StringUtils.isBlank(follow.getSaleProcessId()) ? "" : saleProcMap.get(follow.getSaleProcessId()));
			data.put("actionDate", follow.getActionDate() == null ? "" : DateUtil.formatDate(follow.getActionDate(), DateUtil.Data_ALL));
			data.put("actionType", getActionName(follow.getActionType()));
			data.put("nextActionDate", follow.getFollowDate() == null ? "" : DateUtil.formatDate(follow.getFollowDate(), DateUtil.Data_ALL));
			data.put("nextActionType", getActionName(follow.getFollowType()));
			data.put("custType", StringUtils.isBlank(guide.getCustTypeId()) ? "" : typeNameMap.get(guide.getCustTypeId()));
			if(StringUtils.isNotBlank(suitProcId)){
				String[] procIds = suitProcId.split(",");
				String procNames = "";
				for(String pid : procIds){
					if(productMap.containsKey(pid)) procNames+=productMap.containsKey(pid)+"#";
				}
				if(procNames.length() > 0) procNames = procNames.substring(0,procNames.length() - 1);
				data.put("product", procNames);
			}else{
				data.put("product", "");
			}
		}else if(signDto != null){
			String suitProcId = signDto.getSuitProcId();
			data.put("saleProcess", StringUtils.isBlank(signDto.getSaleProcessId()) ? "" : saleProcMap.get(signDto.getSaleProcessId()));
			data.put("actionDate", DateUtil.formatDate(signDate, DateUtil.Data_ALL));
			data.put("actionType", getActionName(null));
			data.put("nextActionDate", signDto.getFollowDate());
			data.put("nextActionType", getActionName(signDto.getFollowType()));
			data.put("custType", StringUtils.isBlank(signDto.getCustTypeId()) ? "" : typeNameMap.get(signDto.getCustTypeId()));
			if(StringUtils.isNotBlank(suitProcId)){
				String[] procIds = suitProcId.split(",");
				String procNames = "";
				for(String pid : procIds){
					if(productMap.containsKey(pid)) procNames+=productMap.containsKey(pid)+"#";
				}
				if(procNames.length() > 0) procNames = procNames.substring(0,procNames.length() - 1);
				data.put("product", procNames);
			}else{
				data.put("product", "");
			}
		}else{
			if(StringUtils.isNotBlank(cust.getLastCustFollowID())){
				// 查询上次跟进信息
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("orgId",cust.getOrgId());
				map.put("custId", cust.getResCustId());
				map.put("custFollowId", cust.getLastCustFollowID());
				CustFollowDto follow = custFollowService.queryLastCustFollowByCustId(map);	
				if(follow != null){
					data.put("saleProcess", StringUtils.isBlank(follow.getSaleProcessId()) ? "" : saleProcMap.get(follow.getSaleProcessId()));
					data.put("actionDate", follow.getActionDate() == null ? "" : DateUtil.formatDate(follow.getActionDate(), DateUtil.Data_ALL));
					data.put("actionType", getActionName(follow.getActionType()));
					data.put("nextActionDate", follow.getFollowDate() == null ? "" : DateUtil.formatDate(follow.getFollowDate(), DateUtil.Data_ALL));
					data.put("nextActionType", getActionName(follow.getFollowType()));
				}else{
					data.put("saleProcess", StringUtils.isBlank(cust.getLastOptionId()) ? "" : saleProcMap.get(cust.getLastOptionId()));
					data.put("actionDate", cust.getActionDate() == null ? "" : DateUtil.formatDate(cust.getActionDate(), DateUtil.Data_ALL));
					data.put("actionType", getActionName(null));
					data.put("nextActionDate", "");
					data.put("nextActionType", "");
				}
				
				// 获取指定客户导航
				TsmCustGuide tsmCustGuideEntity = new TsmCustGuide();
				tsmCustGuideEntity.setCustId(cust.getResCustId());
				tsmCustGuideEntity.setOrgId(cust.getOrgId());
				List<TsmCustGuide> tsmCustGuideList = tsmCustGuideService.getListByCondtion(tsmCustGuideEntity);
				TsmCustGuide guide = tsmCustGuideList.size() > 0 ? tsmCustGuideList.get(0) : null;
				
				if(guide != null){
					data.put("custType", StringUtils.isBlank(guide.getCustTypeId()) ? "" : typeNameMap.get(guide.getCustTypeId()));
					List<String> procIds = tsmCustGuideProcService.getProcIdsByCustId(cust.getOrgId(), cust.getResCustId());
					String procNames = "";
					for(String pid : procIds){
						if(productMap.containsKey(pid)) procNames+=productMap.containsKey(pid)+"#";
					}
					if(procNames.length() > 0) procNames = procNames.substring(0,procNames.length() - 1);
					data.put("product", procNames);
				}else{
					data.put("custType", "");
					data.put("product", "");
				}
			}
		}
		
		tsmCustSynConfigService.insetCustSynTempData(cust.getOrgId(), 2, JSON.toJSONString(data));
		
	}
	
	public String getActionName(String actionType){
		String actionName = "电话联系";
		if(StringUtils.isNotBlank(actionType)){
			switch (actionType) {
				case "1":
					break;
				case "2":
					actionName = "会客联系";
					break;
				case "3":
					actionName = "客户来电";
					break;
				case "4":
					actionName = "短信联系";
					break;
				case "5":
					actionName = "QQ联系";
					break;
				case "6":
					actionName = "邮件联系";
					break;
				default:
					break;
			}
		}
		return actionName;
	}
	
	/** 
	 * 直接签约
	 * @param custId
	 * @param user 
	 * @throws Exception 
	 * @create  2016年8月10日 下午3:25:57 lixing
	 * @history  
	 */
	public void sign(String custId,ShiroUser user,String module,Date followDate) throws Exception{
		ResCustInfoBean resCustInfoBean = resCustInfoMapper.getByPrimaryKey(user.getOrgId(), custId);
		Date opDate = new Date();
		if(resCustInfoBean.getStatus() != 6){
			//查询groupId
			OrgGroupUser usermeber = new OrgGroupUser();
            usermeber.setOrgId(user.getOrgId());
            usermeber.setMemberAcc(resCustInfoBean.getOwnerAcc());
            OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
			newmember = newmember == null ? new OrgGroupUser() : newmember;
			//修改计划
			planFactService.toSign(user.getOrgId(),newmember.getGroupId(), newmember.getUserId(), custId, 1, 0);
			if((resCustInfoBean.getStatus() == 2 || resCustInfoBean.getStatus() == 3) && (resCustInfoBean.getType() == 1 || resCustInfoBean.getType() == 3) ){//资源
				logContactDayDataService.addLogContactDayData(ContactUtil.RES_TO_SIGN, user.getOrgId(), resCustInfoBean.getResCustId(),resCustInfoBean.getOwnerAcc(), null, null);
				boolean result=false;
				if(followDate != null){
					 result = DateUtil.isNow(followDate);
					if(result==true){
						planFactService.updateContactResult(user.getOrgId(),newmember.getGroupId(), newmember.getUserId(),resCustInfoBean.getResCustId(),"res",true,"sign");	
					}
				}
				if(result==false){
					planFactService.updateContactResult(user.getOrgId(), newmember.getGroupId(), newmember.getUserId(), resCustInfoBean.getResCustId(), "res", PlanResCR.TURN_SIGN.getResult());
				}
				resCustInfoBean.setAmoytocustomerDate(new Date());
			}else{//意向
				logContactDayDataService.addLogContactDayData(ContactUtil.CUST_TO_SIGN, user.getOrgId(), resCustInfoBean.getResCustId(),resCustInfoBean.getOwnerAcc(), resCustInfoBean.getLastOptionId(), resCustInfoBean.getLastOptionId());
				boolean result=false;
				if(followDate != null){
					 result = DateUtil.isNow(followDate);
					if(result==true){
						planFactService.updateContactResult(user.getOrgId(),newmember.getGroupId(), newmember.getUserId(),resCustInfoBean.getResCustId(),"will",true,"sign");	
					}
				}
			if(result==false){
				planFactService.updateContactResult(user.getOrgId(), newmember.getGroupId(), newmember.getUserId(), resCustInfoBean.getResCustId(), "will", PlanWillCR.TURN_SIGN.getResult());
			}
				
			}
			
			resCustInfoBean.setActionDate(opDate);
			resCustInfoBean.setOwnerActionDate(opDate);
			resCustInfoBean.setSignAcc(user.getAccount());
			resCustInfoBean.setSignName(user.getName());
			resCustInfoBean.setSignDate(opDate);
			resCustInfoBean.setUpdateAcc(user.getAccount());
			resCustInfoBean.setUpdateDate(opDate);
			resCustInfoBean.setOpreateType(AppConstant.OPREATE_TYPE15.intValue());
			resCustInfoBean.setSignBeforeStatus(resCustInfoBean.getStatus());
			resCustInfoBean.setSignBeforeType(resCustInfoBean.getType());
			resCustInfoBean.setStatus(AppConstant.STATUS_6.intValue());
			resCustInfoBean.setType(AppConstant.CUST_TYPE2.intValue());
			resCustInfoMapper.update(resCustInfoBean);
			
			silenceCustService.updateSilentLossReport(null,1, 1,user);
			//新客户
			planFactService.updateContractNum(user.getOrgId(), newmember.getGroupId(), 1, 1);
			rankingReportService.updateRankingData(user.getOrgId(), newmember.getMemberAcc(), BigDecimal.valueOf(0), 1, 0);
			//custInfoNalysisService.saveOrUpdate(resCustInfoBean.getResCustId(), 2,user.getOrgId());
			//发送弹幕消息
			String resName = "";
			if(StringUtils.isNotBlank(resCustInfoBean.getName())){
				resName = resCustInfoBean.getName();
			}
//			tsmMessageService.sendBarrage("恭喜"+(user.getName() == null ? user.getAccount() : user.getName())+"成功签约"+resName+"!",user.getOrgId(),user.getAccount(),user.getName());
			if(mainService.getBarrageSign(user.getOrgId())==1&&mainService.getBarrageSign3(user.getOrgId())==0){
				String retName="";
				if(mainService.getBarrageSign2(user.getOrgId())==0){
					retName=((resCustInfoBean.getCompany() == null || "".equals(resCustInfoBean.getCompany())) ? resName : resCustInfoBean.getCompany());
				}
				if(retName==null){
					retName="";
				}
				mainService.sendSingBarrage("恭喜"+(user.getName() == null ? user.getAccount() : user.getName())+"成功签约"+retName+"!", "1",user.getOrgId(),user.getAccount(),user.getName(),"0",0);
				List<DataDictionaryBean> dlist0 = cachedService.getDirList(AppConstant.DATA_50047, user.getOrgId());//红包设置
				if ((!dlist0.isEmpty() && !"0".equals(dlist0.get(0).getDictionaryValue()))) {
					PayApiRequest payrequest=new PayApiRequest();
					
					payrequest.setOrgId(user.getOrgId());
					payrequest.setUserAcc(user.getAccount());
					payrequest.setPayApiEnum(PayApiTypeEnum.SIGN);
					payrequest.setMoney(Double.valueOf(dlist0.get(0).getDictionaryValue()));
					payrequest.setDesc((user.getName() == null ? user.getAccount() : user.getName())+"获得签约奖励红包"+dlist0.get(0).getDictionaryValue()+"元！");
					payrequest.setData("恭喜"+(user.getName() == null ? user.getAccount() : user.getName())+"成功签约"+retName);
					BaseJsonResult json=payApiFacade.pay(payrequest);
					if((boolean) json.get("status")==false){
						logger.error("-------调用pay接口返回结果：--------"+JSONObject.fromObject(json)+"时间："+new Date()); 	
					}
					
		        }
			}
			
			LogBean logBean = new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_SIGN.getCode(), OperateEnum.LOG_SIGN.getDesc(), 1,SysBaseModelUtil.getModelId());
			logBean.setOperateId(AppConstant.Operate_id8);
			logBean.setOperateName(AppConstant.Operate_Name8);
			StringBuffer context = new StringBuffer("");
			if(StringUtils.isNotBlank(module)){
				if(user.getIsState() == 1 || StringUtils.isEmpty(resCustInfoBean.getCompany())){
					context.append(StringUtils.isEmpty(resCustInfoBean.getName()) ? "" : resCustInfoBean.getName());
				}else{
					context.append(StringUtils.isEmpty(resCustInfoBean.getCompany()) ? "" : resCustInfoBean.getCompany());
				}
				if(module.equals("1")){
					logBean.setModuleId(AppConstant.Module_id1001);
					logBean.setModuleName(AppConstant.Module_Name1001);
				}else if(module.equals("2")){
					logBean.setModuleId(AppConstant.Module_id1000);
					logBean.setModuleName(AppConstant.Module_Name1000);
				}else if(module.equals("0")){
					logBean.setModuleId(AppConstant.Module_id9);
					logBean.setModuleName(AppConstant.Module_Name9);
				}else if(module.equals("3")){
					logBean.setModuleId(AppConstant.Module_id113);
					logBean.setModuleName(AppConstant.Module_Name113);
				}else {
					logBean.setModuleId(AppConstant.Module_id119);
					logBean.setModuleName(AppConstant.Module_Name119);
				}
				logBean.setContent(context.toString());
			}
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(custId, "");
			logCustInfoService.addTableStoreLog(logBean, logMap);
	        //开放接口
	        if(tsmCustSynConfigService.isConfigExist(resCustInfoBean.getOrgId(), 2))  sign_api(opDate, resCustInfoBean, null, null);
		}
	}
	
	public void unSign(String custId,ShiroUser user) throws Exception{
		ResCustInfoBean resCustInfoBean = resCustInfoMapper.getByPrimaryKey(user.getOrgId(), custId);
		if(resCustInfoBean.getStatus() == 6){
			//插入日志
//			logCustInfoService.addLog(user.getOrgId(), user.getAccount(), custId, null, OperateEnum.LOG_UNSIGN);
			Map<String, Object> logMap = new HashMap<String, Object>();
			logMap.put(custId, "");
			LogBean logBean = new LogBean(user.getOrgId(), user.getAccount(), user.getName(), OperateEnum.LOG_UNSIGN.getCode(), OperateEnum.LOG_UNSIGN.getDesc(), 1,SysBaseModelUtil.getModelId());
			StringBuffer context = new StringBuffer("");
			if(user.getIsState() == 1 || StringUtils.isEmpty(resCustInfoBean.getCompany())){
				context.append(StringUtils.isEmpty(resCustInfoBean.getName()) ? "" : resCustInfoBean.getName());
			}else{
				context.append(StringUtils.isEmpty(resCustInfoBean.getCompany()) ? "" : resCustInfoBean.getCompany());
			}
//			if(StringUtils.isNotBlank(resCustInfoBean.getMobilephone())) context.append("(").append(resCustInfoBean.getMobilephone()).append(")");
			logBean.setOperateId(AppConstant.Operate_id77);
			logBean.setOperateName(AppConstant.Operate_Name77);
			logBean.setModuleId(AppConstant.Module_id1002);
			logBean.setModuleName(AppConstant.Module_Name1002);
			logBean.setContent(context.toString());
			logCustInfoService.addTableStoreLog(logBean, logMap);
			
			String lastOptionId = null;
			if(resCustInfoBean.getSignDate() != null){
				//今日联系结果修改
				if(DateUtil.formatDate(resCustInfoBean.getSignDate(),DateUtil.DATE_DAY).equals(DateUtil.formatDate(new Date(),DateUtil.DATE_DAY))){
					//如果是当天 联系结果修改
					logContactDayDataService.addLogContactDayData(ContactUtil.CUST_CONTACT, user.getOrgId(), resCustInfoBean.getResCustId(),resCustInfoBean.getOwnerAcc(), null, null);
				}
				//修改计划部分
				User signUser = userService.getByAccount(resCustInfoBean.getOwnerAcc());
				if(signUser != null){
					OrgGroupUser usermeber = new OrgGroupUser();
					usermeber.setOrgId(signUser.getOrgId());
					usermeber.setUserId(signUser.getUserId());
					OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
					planFactService.cancelSign(user.getOrgId(), newmember.getGroupId(), signUser.getUserId(), custId, -1, Double.valueOf("0"), resCustInfoBean.getSignDate(), 0);
				}
				
				//修改排行榜数据 如果签约前是资源 签约减1，意向加1
				if(resCustInfoBean.getSignBeforeType() != null && resCustInfoBean.getSignBeforeType().intValue() == 1){
					rankingReportService.updateRankingData(user.getOrgId(), user.getAccount(), BigDecimal.valueOf(0), -1, 1,resCustInfoBean.getSignDate());
					// 从缓存读取销售进程列表
		            List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
		            for(int i=0;i<options.size();i++){
		            	OptionBean ob = options.get(i);
		            	if(i == 0){
		            		lastOptionId = ob.getOptionlistId();
		            	}
		            	if(ob.getIsDefault().intValue() == 1){
		            		lastOptionId = ob.getOptionlistId();
		            	}
		            }
				}else{
					rankingReportService.updateRankingData(user.getOrgId(), signUser.getUserAccount(), BigDecimal.valueOf(0), -1, 0,resCustInfoBean.getSignDate());
					lastOptionId = custFollowService.getSignBeforeSaleProcessId(user.getOrgId(), custId,DateUtil.formatDate(resCustInfoBean.getSignDate()));
				}
			}else{
				// 从缓存读取销售进程列表
	            List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
	            for(int i=0;i<options.size();i++){
	            	OptionBean ob = options.get(i);
	            	if(i == 0){
	            		lastOptionId = ob.getOptionlistId();
	            	}
	            	if(ob.getIsDefault().intValue() == 1){
	            		lastOptionId = ob.getOptionlistId();
	            	}
	            }
			}
			
			resCustInfoBean.setUpdateAcc(user.getAccount());
			resCustInfoBean.setSignBeforeType(null);
			resCustInfoBean.setLastOptionId(lastOptionId);
			resCustInfoMapper.rebackSignBeforeStatus(resCustInfoBean);
			//删除合同
			ContractBean cb = new ContractBean();
			cb.setCustId(custId);
			cb.setOrgId(user.getOrgId());
			cb.setIsDel(0);
			List<ContractBean> beans = contractMapper.findByCondtion(cb);
			if(beans.size() > 0){
				String contractIds = "";
				for(ContractBean bean : beans){
					contractIds+=bean.getId()+",";
				}
				deleteContractBatch(user, contractIds.substring(0,contractIds.length()-1), custId, "取消签约","1",false);
			}
			//订单设置为已失效
			ContractOrderBean orderBean = new ContractOrderBean();
			orderBean.setOrgId(user.getOrgId());
			orderBean.setCustId(custId);
			orderBean.setAuthState(5);
			contractOrderMapper.updateTrends(orderBean);
		}
	}
	
	public Map<String, String> getEventsMap(ContractBean cb){
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", cb.getId());
		map.put("custName", cb.getCustName());
		map.put("code", cb.getCode());
		map.put("contractName", cb.getContractName());
		map.put("signUsername", cb.getSignUsername());
		map.put("signDate", DateUtil.formatDate(cb.getSignDate(),DateUtil.DATE_DAY));
		map.put("effectiveDate", cb.getEffectiveDate() == null ? "" : DateUtil.formatDate(cb.getEffectiveDate(),DateUtil.DATE_DAY));
		map.put("invalidDate", cb.getInvalidDate() == null ? "" : DateUtil.formatDate(cb.getInvalidDate(),DateUtil.DATE_DAY));
		map.put("inputtime", DateUtil.formatDate(cb.getInputtime()));
		map.put("money", cb.getMoney() == null ? "" : cb.getMoney().toString());
		map.put("orgId", cb.getOrgId());
		map.put("custId", cb.getCustId());
		return map;
	}
	
	public List<ContractBean> getContractBeansByCode(String code,String orgId){
		ContractBean cb = new ContractBean();
		cb.setOrgId(orgId);
		cb.setCode(code);
//		cb.setIsDel(0);
		return contractMapper.findByCondtion(cb);
	}
	
	public List<ContractOrderBean> getContractOrderBeansByCode(String code,String orgId){
		ContractOrderBean cob = new ContractOrderBean();
		cob.setOrgId(orgId);
		cob.setCode(code);
		cob.setIsDel(0);
		return contractOrderMapper.findByCondtion(cob);
	}
	
	public List<ContractBean> getContractBeansByCustId(String orgId,String custId){
		ContractBean cb = new ContractBean();
		cb.setOrgId(orgId);
		cb.setIsDel(0);
		cb.setCustId(custId);
		return contractMapper.findByCondtion(cb);
	}
	
	/** 
	 * 编辑合同
	 * @param cb
	 * @param sfbList
	 * @param delIdsList 
	 * @throws Exception 
	 * @create  2015年12月28日 下午1:53:57 lixing
	 * @history  
	 */
	public void editContract(ContractBean cb,List<String> fileIds,List<String> delIdsList) throws Exception{
		contractMapper.update(cb);
		//插入附件
		for(String fileId : fileIds){
			ContractFileBean cfb = new ContractFileBean();
			cfb.setId(SysBaseModelUtil.getModelId());
			cfb.setCaId(cb.getId());
			cfb.setFileId(fileId);
			cfb.setOrgId(cb.getOrgId());
			cfb.setInputtime(cb.getUpdatetime());
			cfb.setIsDel(0);
			cfb.setType(1);
			contractFileMapper.insert(cfb);
		}
		//删除附件
		for(String fileId : delIdsList){
			ContractFileBean conFileBean = new ContractFileBean();
			conFileBean.setId(fileId);
			conFileBean.setOrgId(cb.getOrgId());
			conFileBean.setIsDel(1);
			conFileBean.setUpdatetime(cb.getUpdatetime());
			contractFileMapper.update(conFileBean);
		}
		ContractBean entity = new ContractBean();
		entity.setId(cb.getId());
		entity.setOrgId(cb.getOrgId());
		ContractBean con = contractMapper.getByCondtion(entity);
		ResCustInfoBean bean = resCustInfoMapper.getByPrimaryKey(cb.getOrgId(), con.getCustId());
		StringBuffer context = new StringBuffer();
		if(bean.getState() == 1 || StringUtils.isEmpty(bean.getCompany())){
			context.append(StringUtils.isEmpty(bean.getName()) ? "" : bean.getName());
		}else{
			context.append(StringUtils.isEmpty(bean.getCompany()) ? "" : bean.getCompany());
		}
//		if(StringUtils.isNotBlank(bean.getMobilephone())) context.append("(").append(bean.getMobilephone()).append(")");
		context.append("，合同编号：").append(con.getCode());
		logUserOperateService.setUserOperateLog( AppConstant.Module_id1006,AppConstant.Module_Name1006, AppConstant.Operate_id26, AppConstant.Operate_Name26, context.toString(), "");
	}
	
	public ContractDto getContractInfoByIdAndOrg(String contractId,String orgId){
		return contractMapper.findContractInfoByIdAndOrg(contractId, orgId);
	}
	
	/** 
	 * 合同维护
	 * @param dto
	 * @return 
	 * @create  2015年12月23日 下午6:56:50 lixing
	 * @history  
	 */
	public List<ContractDto> getUnitContractListPage(ContractDto dto){
		List<ContractDto> list = contractMapper.findUnitContractListPage(dto);
		if(list.size() > 0){
			List<String> custIds = new ArrayList<String>();
			for(ContractDto contract : list) custIds.add(contract.getCustId());
			List<ResCustInfoDto> custs = resCustInfoMapper.findCustPoolList(custIds, dto.getOrgId());
			Map<String, ResCustInfoDto> map = new HashMap<String, ResCustInfoDto>();
			for(ResCustInfoDto cust : custs) map.put(cust.getResCustId(), cust);
			for(ContractDto contract : list){
				if(map.containsKey(contract.getCustId())) {
					contract.setOwnerAcc(map.get(contract.getCustId()).getOwnerAcc());
					contract.setCommonAcc(map.get(contract.getCustId()).getCommonAcc());
					contract.setNewCompany(map.get(contract.getCustId()).getCompany());
					contract.setNewCustName(map.get(contract.getCustId()).getName());
					contract.setMobilephone(map.get(contract.getCustId()).getMobilephone());
					contract.setTelphone(map.get(contract.getCustId()).getTelphone());
				}
			}
		}
		return list;
	}
	
	
	/** 
	 * 批量删除合同
	 * @param orgId
	 * @param ids 
	 * @throws Exception 
	 * @create  2015年12月24日 下午3:20:22 lixing
	 * @history  
	 */
	public void deleteContractBatch(ShiroUser user,String id,String custId,String cancleRemark,String isRemoveOrders,boolean handDel) throws Exception{
		//查询出合同下所有已审核订单
		if(isRemoveOrders.equals("1")){
			ContractOrderDto cod = new ContractOrderDto();
			cod.setOrgId(user.getOrgId());
			cod.setCaIds(Arrays.asList(id.split(",")));
			List<ContractOrderDto> dtos = contractOrderMapper.findContractOrders(cod);
			if(dtos.size() > 0){
				Map<String, BigDecimal> moneyMap = new HashMap<String, BigDecimal>();
				List<DeleteOrderDto> deleteOrders = new ArrayList<DeleteOrderDto>();
				for(ContractOrderDto orderDto : dtos){
					if(orderDto.getMoney() != null && orderDto.getMoney().compareTo(BigDecimal.valueOf(0)) == 1){
						if(moneyMap.containsKey(orderDto.getCustId())){
							BigDecimal money  = moneyMap.get(orderDto.getCustId());
							money = money.add(orderDto.getMoney());
							moneyMap.put(orderDto.getCustId(), money);
						}else{
							moneyMap.put(orderDto.getCustId(),orderDto.getMoney());
						}
						deleteOrders.add(new DeleteOrderDto(orderDto.getUpdatetime(),orderDto.getCustId(), orderDto.getSignUserId(),orderDto.getSignUserAcc(),orderDto.getGroupId(), orderDto.getMoney()));
					}
				}
				//修改合同累积金额
				if(moneyMap.keySet().size() > 0){
					for(String key : moneyMap.keySet()){
						BigDecimal money = moneyMap.get(key);
						ContractMoneyBean cmb = new ContractMoneyBean();
						cmb.setIsDel(0);
						cmb.setCustId(key);
						cmb.setOrgId(user.getOrgId());
						List<ContractMoneyBean> cmbBeans = contractMoneyMapper.findByCondtion(cmb);
						if(cmbBeans.size() > 0){
							ContractMoneyBean bean = cmbBeans.get(0);
							bean.setMoney(bean.getMoney().add(money.negate()));
							bean.setUpdatetime(new Date());
							contractMoneyMapper.update(bean);
						}
					}
				}
				
				for (DeleteOrderDto dto : deleteOrders) {
					//修改排行榜
					rankingReportService.updateRankingData(user.getOrgId(), dto.getSignAcc(), dto.getMoney().negate(), 0, 0, dto.getCheckDate());
					planFactService.cancelSign(user.getOrgId(), dto.getGroupId(), dto.getSignUserId(), custId, 0, dto.getMoney().negate().doubleValue(), dto.getCheckDate(), 0);
				}
			}
			//删除订单
			contractOrderMapper.cancelledOrderBatch(cod);
			//contractOrderMapper.deleteOrderDetailBatch(cod);
		}
		List<String> contractIds = Arrays.asList(id.split(","));
		//删除合同
		contractMapper.deleteContractBatch(user.getOrgId(), cancleRemark, contractIds);
		if(handDel){
			if(contractIds.size() > 1){
				logUserOperateService.setUserOperateLog( AppConstant.Module_id1006,AppConstant.Module_Name1006, AppConstant.Operate_id25, AppConstant.Operate_Name25, contractIds.size()+"条，取消原因："+(StringUtils.isNotBlank(cancleRemark) ? cancleRemark : "无"), "");
			}else{
				ContractBean entity = new ContractBean();
				entity.setId(contractIds.get(0));
				entity.setOrgId(user.getOrgId());
				ContractBean cb = contractMapper.getByCondtion(entity);
				ResCustInfoBean bean = resCustInfoMapper.getByPrimaryKey(user.getOrgId(), cb.getCustId());
				StringBuffer context = new StringBuffer();
				if(bean.getState() == 1 || StringUtils.isEmpty(bean.getCompany())){
					context.append(StringUtils.isEmpty(bean.getName()) ? "" : bean.getName());
				}else{
					context.append(StringUtils.isEmpty(bean.getCompany()) ? "" : bean.getCompany());
				}
//				if(StringUtils.isNotBlank(bean.getMobilephone())) context.append("(").append(bean.getMobilephone()).append(")");
				context.append("，合同编号：").append(cb.getCode()).append("，取消原因：").append(StringUtils.isNotBlank(cancleRemark) ? cancleRemark : "无");
				logUserOperateService.setUserOperateLog( AppConstant.Module_id1006,AppConstant.Module_Name1006, AppConstant.Operate_id25, AppConstant.Operate_Name25, context.toString(), "");
			}
		}
	}
	
	
	/** 
	 * 查询订单维护列表
	 * @param dto
	 * @return 
	 * @create  2015年12月24日 下午3:48:32 lixing
	 * @history  
	 */
	public List<ContractOrderDto> findContractOrderListPage(ContractOrderDto dto){
		List<ContractOrderDto> dtos = contractOrderMapper.findContractOrderListPage(dto);
		if(dtos.size() > 0){
			List<String> custIds = new ArrayList<String>();
			for(ContractOrderDto orderDto : dtos) custIds.add(orderDto.getCustId());
			List<ResCustInfoDto> custs = resCustInfoMapper.findCustPoolList(custIds, dto.getOrgId());
			Map<String, ResCustInfoDto> map = new HashMap<String, ResCustInfoDto>();
			for(ResCustInfoDto cust : custs) map.put(cust.getResCustId(), cust);
			for(ContractOrderDto orderDto : dtos){
				if(orderDto.getAuthState() == 2 && orderDto.getInvalidDate() != null && orderDto.getInvalidDate().compareTo(DateUtil.parseDate(DateUtil.formatDate(new Date()))) == -1){
					orderDto.setAuthState(5);
				}
				if(map.containsKey(orderDto.getCustId())) {
					orderDto.setSaleAcc(map.get(orderDto.getCustId()).getOwnerAcc());
					orderDto.setCommonAcc(map.get(orderDto.getCustId()).getCommonAcc());
//					orderDto.setCompany(map.get(orderDto.getCustId()).getCompany());
//					orderDto.setCustName(map.get(orderDto.getCustId()).getName());
				}
			}
		}
		return dtos;
	}
	
	public List<ContractOrderDto> findContractOrderList(ContractOrderDto dto){
		List<ContractOrderDto> orderDtos = contractOrderMapper.findContractOrderList(dto);
		List<ContractOrderDto> returns = new ArrayList<ContractOrderDto>();
		if(orderDtos.size() > 0){
			int total = orderDtos.size();
			int subSize = 200;
			int page = total/subSize;
			page = total%subSize == 0 ? page : page+1; 
			List<ContractOrderDto> subList = new ArrayList<ContractOrderDto>();
			List<String> custIds = new ArrayList<String>();
			Map<String, ResCustInfoDto> map = new HashMap<String, ResCustInfoDto>();
			for(int i=0;i<page;i++){
				if((i+1) < page){
					subList = orderDtos.subList(i*subSize, (i+1)*subSize);
				}else{
					subList = orderDtos.subList(i*subSize, total);
				}
				for(ContractOrderDto orderDto : subList) custIds.add(orderDto.getCustId());
				List<ResCustInfoDto> custs = resCustInfoMapper.findCustPoolList(custIds, dto.getOrgId());
				for(ResCustInfoDto cust : custs) map.put(cust.getResCustId(), cust);
				for(ContractOrderDto orderDto : subList){
					if(orderDto.getAuthState() == 2 && orderDto.getInvalidDate() != null && orderDto.getInvalidDate().compareTo(DateUtil.parseDate(DateUtil.formatDate(new Date()))) == -1){
						orderDto.setAuthState(5);
					}
					if(map.containsKey(orderDto.getCustId())) {
						orderDto.setSaleAcc(map.get(orderDto.getCustId()).getOwnerAcc());
						orderDto.setCommonAcc(map.get(orderDto.getCustId()).getCommonAcc());
//						orderDto.setCompany(map.get(orderDto.getCustId()).getCompany());
//						orderDto.setCustName(map.get(orderDto.getCustId()).getName());
//						orderDto.setMobilephone(StringUtils.isNotBlank(map.get(orderDto.getCustId()).getMobilephone()) ? map.get(orderDto.getCustId()).getMobilephone() : map.get(orderDto.getCustId()).getTelphone());
//						orderDto.setCompanyAddr(map.get(orderDto.getCustId()).getCompanyAddr());
					}
				}
				returns.addAll(subList);
				custIds.clear();
				map.clear();
			}
		}
		return returns;
	}
	
	public List<ContractOrderDto> getContractOrderCountData(ContractOrderDto dto){
		return contractOrderMapper.findContractOrderCountData(dto);
	}
	
	/** 
	 * 查询合同附件列表
	 * @param caId
	 * @param orgId
	 * @return 
	 * @create  2015年12月26日 下午1:40:42 lixing
	 * @history  
	 */
	public List<ContractFileDto> getContractFiles(@Param("caId")String caId,@Param("orgId")String orgId){
		return contractFileMapper.findContractFiles(caId, orgId);
	}
	
	
	/** 
	 * 查询合同列表
	 * @param orderBean
	 * @return 
	 * @create  2015年12月26日 下午1:45:22 lixing
	 * @history  
	 */
	public List<ContractOrderBean> getContractOrderBeans(String orgId,String caId){
		return contractOrderMapper.findViewContractOrders(orgId,caId);
	}
	
	/** 
	 * 新增订单
	 * @throws Exception 
	 * @create  2015年12月1日 下午4:02:59 lixing
	 * @history  
	 */
	public void addOrder(ContractOrderBean cob,List<ContractOrderDetailBean> codbList,List<String> fileIds,String module) throws Exception{
		ResCustInfoBean custInfoBean = resCustInfoMapper.getByPrimaryKey(cob.getOrgId(), cob.getCustId());
		if(custInfoBean != null){
			cob.setSaleAcc(custInfoBean.getOwnerAcc());
		}
		
		String productNames="",productIds="";
		for(ContractOrderDetailBean codb : codbList){
			productNames+=codb.getProductName()+",";
			productIds+=codb.getProductId()+",";
		}
		productNames=productNames.substring(0, productNames.length()-1);
		productIds=productIds.substring(0, productIds.length()-1);
		cob.setProductIds(productIds);
		cob.setProductNames(productNames);
		
		if(cob.getAuthState() == 1){
			cob.setReportDate(new Date());
			if(custInfoBean != null){
				tsmReportPlanService.plusSaleMoney(cob.getOrgId(), custInfoBean.getOwnerAcc(), cob.getTradeDate(), cob.getMoney().doubleValue());
			}
		}
		//插入订单
		contractOrderMapper.insert(cob);
		//插入订单详情
		contractOrderDetailMapper.insertBatch(codbList);
		
		//插入附件
		for(String fileId : fileIds){
//					sysFileMapper.insert(sfb);
			ContractFileBean cfb = new ContractFileBean();
			cfb.setId(SysBaseModelUtil.getModelId());
			cfb.setCaId(cob.getId());
			cfb.setFileId(fileId);
			cfb.setOrgId(cob.getOrgId());
			cfb.setInputtime(cob.getInputtime());
			cfb.setType(2);
			cfb.setIsDel(0);
			contractFileMapper.insert(cfb);
		}
		
		if(StringUtils.isNotBlank(module)){
			StringBuffer context = new StringBuffer("");
			if(custInfoBean.getState() == 1 || StringUtils.isEmpty(custInfoBean.getCompany())){
				context.append(StringUtils.isEmpty(custInfoBean.getName()) ? "" : custInfoBean.getName());
			}else{
				context.append(StringUtils.isEmpty(custInfoBean.getCompany()) ? "" : custInfoBean.getCompany());
			}
//			if(StringUtils.isNotBlank(custInfoBean.getMobilephone())) context.append("(").append(custInfoBean.getMobilephone()).append(")");
			context.append("，订单编号：").append(cob.getCode());
			if(module.equals("3")){
				logUserOperateService.setUserOperateLog( AppConstant.Module_id1002,AppConstant.Module_Name1002, AppConstant.Operate_id16, AppConstant.Operate_Name16, context.toString(), "");
			}else if(module.equals("10")){
				logUserOperateService.setUserOperateLog( AppConstant.Module_id1006,AppConstant.Module_Name1006, AppConstant.Operate_id16, AppConstant.Operate_Name16, context.toString(), "");
			}
		}
		
		//开放接口
		if(tsmCustSynConfigService.isConfigExist(custInfoBean.getOrgId(), 3,1)) add_order_api(custInfoBean, cob, codbList);
	}
	
	public void add_order_api(ResCustInfoBean cust,ContractOrderBean cob,List<ContractOrderDetailBean> codbList) throws Exception{
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("id", SysBaseModelUtil.getModelId());
		Map<String,String> nameMap = cachedService.getOrgUserNames(cust.getOrgId());
		DecimalFormat decimalFormat = new DecimalFormat("#.##");
		if(cust.getState() == 1){
			data.put("custName", cust.getName());
			data.put("linkName", cust.getMainLinkman());
			Map<Integer, String> provinceMap = cachedService.getAreaMap(CachedNames.PROVINCE);
			Map<Integer, String> cityMap = cachedService.getAreaMap(CachedNames.CITY);
			Map<Integer, String> countyMap = cachedService.getAreaMap(CachedNames.COUNTY);
			String area = "";
			if(cust.getProvinceId() != null){
				area = provinceMap.get(cust.getProvinceId());
				if(cust.getCityId() != null) area+=cityMap.get(cust.getCityId());
				if(cust.getCountyId() != null) area+=countyMap.get(cust.getCountyId());
			}
			data.put("area", area);
		}else{
			data.put("custName", cust.getCompany());
			data.put("linkName", cust.getName());
		}
		data.put("linkPhone", StringUtils.isBlank(cust.getMobilephone()) ? cust.getTelphone() : cust.getMobilephone());
		data.put("address", cust.getAddress());
		data.put("orderCode", cob.getCode());
		if(StringUtils.isNotBlank(cob.getCaId())){
			ContractDto contract = getContractInfoByIdAndOrg(cob.getCaId(), cust.getOrgId());
			data.put("contractCode", contract.getCode());
		}else{
			data.put("contractCode", "");
		}
		data.put("tradeDate", DateUtil.formatDate(cob.getTradeDate(),DateUtil.DATE_DAY));
		data.put("payType", getPayName(cob.getPayType()));
		data.put("tradeMoney",  cob.getMoney() == null ? "" : decimalFormat.format(cob.getMoney()));
		data.put("effectiveDate", cob.getEffectiveDate() == null ? "" : DateUtil.formatDate(cob.getEffectiveDate(),DateUtil.DATE_DAY));
		data.put("invalidDate", cob.getInvalidDate() == null ? "" :  DateUtil.formatDate(cob.getInvalidDate(),DateUtil.DATE_DAY));
		data.put("ownerAcc", nameMap.get(cust.getOwnerAcc()) == null ? cust.getOwnerAcc() : nameMap.get(cust.getOwnerAcc()));
		data.put("subAcc", nameMap.get(cob.getUserId()) == null ? cob.getUserId() : nameMap.get(cob.getUserId()));
		data.put("context", cob.getContext());
		List<Map<String, String>> infos = new ArrayList<Map<String,String>>();
		for(ContractOrderDetailBean info : codbList){
			Map<String, String> info_map = new HashMap<String, String>();
			info_map.put("code", cob.getCode());
			info_map.put("name", info.getProductName());
			info_map.put("price", info.getProductPrice() == null ? "" : decimalFormat.format(info.getProductPrice()));
			info_map.put("buyNum", info.getBuyNum() == null ? "" : decimalFormat.format(info.getBuyNum()));
			info_map.put("buyPrice", info.getBuyPrice() == null ? "" : decimalFormat.format(info.getBuyPrice()));
			info_map.put("buyMoney", info.getBuyMoney() == null ? "" : decimalFormat.format(info.getBuyMoney()));
			infos.add(info_map);
		}
		data.put("products", infos);
		tsmCustSynConfigService.insetCustSynTempData(cust.getOrgId(), 3, JSON.toJSONString(data));
	}
	
	public String getPayName(String payType){
		String payName = "" ;
		switch (payName) {
			case "1":
				payName = "现金";
				break;
			case "2":
				payName = "银行转账/汇款";
				break;
			case "3":
				payName = "在线支付";
				break;
			case "4":
				payName = "支付宝转账";
				break;
			default:
				break;
		}
		return payName;
	}
	
	/** 
	 * 编辑订单
	 * @param cob
	 * @param codbList
	 * @param delDetailIds 
	 * @throws Exception 
	 * @create  2015年12月28日 下午6:43:55 lixing
	 * @history  
	 */
	public void editOrder(ContractOrderBean cob,List<ContractOrderDetailBean> codbList,List<String> delDetailIds,List<String> fileIds,List<String> delIdsList) throws Exception{
		String productNames="",productIds="";
		for(ContractOrderDetailBean codb : codbList){
			productNames+=codb.getProductName()+",";
			productIds+=codb.getProductId()+",";
		}
		productNames=productNames.substring(0, productNames.length()-1);
		productIds=productIds.substring(0, productIds.length()-1);
		cob.setProductIds(productIds);
		cob.setProductNames(productNames);
		
		if(cob.getAuthState() != null && cob.getAuthState() == 1){
			cob.setReportDate(new Date());
			ResCustInfoBean custInfoBean = resCustInfoMapper.getByPrimaryKey(cob.getOrgId(), cob.getCustId());
			if(custInfoBean != null){
				tsmReportPlanService.plusSaleMoney(cob.getOrgId(), custInfoBean.getOwnerAcc(), cob.getTradeDate(), cob.getMoney().doubleValue());
			}
			StringBuffer context = new StringBuffer("");
			if(custInfoBean.getState() == 1 || StringUtils.isEmpty(custInfoBean.getCompany())){
				context.append(StringUtils.isEmpty(custInfoBean.getName()) ? "" : custInfoBean.getName());
			}else{
				context.append(StringUtils.isEmpty(custInfoBean.getCompany()) ? "" : custInfoBean.getCompany());
			}
//			if(StringUtils.isNotBlank(custInfoBean.getMobilephone())) context.append("(").append(custInfoBean.getMobilephone()).append(")");
			context.append("，订单编号：").append(cob.getCode());
			logUserOperateService.setUserOperateLog( AppConstant.Module_id1007,AppConstant.Module_Name1007, AppConstant.Operate_id29, AppConstant.Operate_Name29, context.toString(), "");
		}
		//修改订单信息
		contractOrderMapper.update(cob);
		//保存订单详细信息或修改订单详细信息
		for(ContractOrderDetailBean codb : codbList){
			if(StringUtils.isNotBlank(codb.getProductId())){
				if(StringUtils.isNotBlank(codb.getId())){
					codb.setOrgId(cob.getOrgId());
					codb.setUpdatetime(cob.getUpdatetime());
					contractOrderDetailMapper.update(codb);
				}else{
					codb.setId(SysBaseModelUtil.getModelId());
					codb.setOrgId(cob.getOrgId());
					codb.setInputtime(cob.getUpdatetime());
					codb.setOrderId(cob.getId());
					codb.setCode(cob.getCode());
					codb.setIsDel(0);
					contractOrderDetailMapper.insert(codb);
				}
			}
		}
		//删除订单详细信息
		for(String detailId : delDetailIds){
			ContractOrderDetailBean codb = new ContractOrderDetailBean();
			codb.setOrgId(cob.getOrgId());
			codb.setId(detailId);
			codb.setUpdatetime(cob.getUpdatetime());
			codb.setIsDel(1);
			contractOrderDetailMapper.update(codb);
		}
		
		//插入附件
		for(String fileId : fileIds){
			ContractFileBean cfb = new ContractFileBean();
			cfb.setId(SysBaseModelUtil.getModelId());
			cfb.setCaId(cob.getId());
			cfb.setFileId(fileId);
			cfb.setOrgId(cob.getOrgId());
			cfb.setInputtime(cob.getUpdatetime());
			cfb.setIsDel(0);
			cfb.setType(2);
			contractFileMapper.insert(cfb);
		}
		//删除附件
		for(String fileId : delIdsList){
			ContractFileBean conFileBean = new ContractFileBean();
			conFileBean.setId(fileId);
			conFileBean.setOrgId(cob.getOrgId());
			conFileBean.setIsDel(1);
			conFileBean.setUpdatetime(cob.getUpdatetime());
			contractFileMapper.update(conFileBean);
		}
		
	}
	
	
	/** 
	 * 审核订单
	 * @param dto 
	 * @throws Exception 
	 * @create  2015年12月29日 下午4:24:30 lixing
	 * @history  
	 */
	public void CheckOrder(ContractOrderDto dto,ShiroUser user) throws Exception{
		List<String> ids = contractOrderMapper.findCheckIds(dto);//防止重复审核
		if(ids.size() > 0){
			dto.setIds(ids);
			contractOrderMapper.checkContractOrder(dto);
			//插入审核信息
			for(String orderId : dto.getIds()){
				ContractOrderAuthlogBean coab = new ContractOrderAuthlogBean();
				coab.setId(SysBaseModelUtil.getModelId());
				coab.setOrderId(orderId);
				coab.setInputtime(new Date());
				coab.setAuthState(dto.getAuthState().toString());
				coab.setContext(dto.getAuthDesc());
				coab.setUserAcc(user.getAccount());
				coab.setUserName(user.getName());
				coab.setOrgId(user.getOrgId());
				coab.setIsDel(0);
				contractOrderAuthlogMapper.insert(coab);
			}
			
			StringBuffer context = new StringBuffer("");
			if(ids.size() > 1){
				context.append(ids.size()).append("条");
			}else{
				ContractOrderBean entity = new ContractOrderBean();
				entity.setOrgId(user.getOrgId());
				entity.setId(ids.get(0));
				ContractOrderBean cob = contractOrderMapper.getByCondtion(entity);
				ResCustInfoBean bean = resCustInfoMapper.getByPrimaryKey(cob.getOrgId(), cob.getCustId());
				if(bean.getState() == 1 || StringUtils.isEmpty(bean.getCompany())){
					context.append(StringUtils.isEmpty(bean.getName()) ? "" : bean.getName());
				}else{
					context.append(StringUtils.isEmpty(bean.getCompany()) ? "" : bean.getCompany());
				}
//				if(StringUtils.isNotBlank(bean.getMobilephone())) context.append("(").append(bean.getMobilephone()).append(")");
				context.append("，订单编号：").append(cob.getCode());
			}
			
			//修改合同金额
			List<ContractOrderDto> orderDtos = contractOrderMapper.getOrderInfoByIdSAndOrg(dto.getIds(),user.getOrgId());
			
			if(dto.getAuthState() == 2){//如果审核通过
				// 查询积分规则			
				DataDictionaryBean dictionary = new DataDictionaryBean();
				dictionary.setOrgId(user.getOrgId());
				List<DataDictionaryBean>dictionaryList = dataDictionaryService.getListByCondtion(dictionary);
				Map<String, DataDictionaryBean> dictionaryMap = new HashMap<String, DataDictionaryBean>();
				for (DataDictionaryBean obj : dictionaryList) {
					if(null != obj.getDictionaryCode()){
						dictionaryMap.put(obj.getDictionaryCode(), obj);
					}
				}
				for(ContractOrderDto cob : orderDtos){
					resCustEventService.create(cob.getOrgId(), cob.getCustId(), "6", JSONObject.fromObject(getEventMap(cob)).toString());
					//修改客户累计金额
					ContractMoneyBean cmb = new ContractMoneyBean();
					cmb.setIsDel(0);
					cmb.setCustId(cob.getCustId());
					cmb.setOrgId(user.getOrgId());
					List<ContractMoneyBean> cmbBeans = contractMoneyMapper.findByCondtion(cmb);
					if(cmbBeans.size() == 0){
						cmb.setId(SysBaseModelUtil.getModelId());
						cmb.setMoney(cob.getMoney());
						cmb.setInputtime(new Date());
						contractMoneyMapper.insert(cmb);
					}else{
						ContractMoneyBean bean = cmbBeans.get(0);
						bean.setMoney(bean.getMoney().add(cob.getMoney()));
						bean.setUpdatetime(new Date());
						contractMoneyMapper.update(bean);
					}
					//修改用户计划、积分
					DecimalFormat df  = new DecimalFormat("###.00");
					//修改计划
					User orderUser = userMapper.findByAccount(cob.getSaleAcc());
					//查询groupId
					OrgGroupUser usermeber = new OrgGroupUser();
		            usermeber.setOrgId(orderUser.getOrgId());
		            usermeber.setUserId(orderUser.getUserId());
		            OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
		            String orderUserGroupId = newmember != null ? newmember.getGroupId() : "";
					planFactService.toSign(user.getOrgId(),orderUserGroupId,orderUser.getUserId(), cob.getCustId(), 0, Double.valueOf(df.format(cob.getMoney().doubleValue())));
					//修改排行榜月数据
					rankingReportService.updateRankingData(user.getOrgId(), cob.getSaleAcc(), cob.getMoney(), 0, 0);
					ResCustInfoBean resCustInfoBean = resCustInfoMapper.getByPrimaryKey(user.getOrgId(), cob.getCustId());
					Date signDate = resCustInfoBean.getSignDate();
					if(signDate.before(DateUtil.monthBegin(new Date()))){
						//老客户
						planFactService.updateContractMoney(user.getOrgId(), orderUserGroupId, 0, Double.valueOf(df.format(cob.getMoney().doubleValue())));
					}else{
						//新客户
						planFactService.updateContractMoney(user.getOrgId(), orderUserGroupId, 1, Double.valueOf(df.format(cob.getMoney().doubleValue())));
					}
					//赠送积分
					Double realMoney = cob.getMoney().doubleValue();
					Double restMoney = orderUser.getSignMoneyRest() == null ? 0 : orderUser.getSignMoneyRest();
					realMoney=realMoney+restMoney;
					Integer pv = Integer.parseInt(dictionaryMap.get(AppConstant.DATA_40021).getDictionaryValue());
					Double qy = realMoney%pv;
					Integer pointsAdd =  (int)Math.floor((realMoney-qy)/pv);
					Integer userPoints = orderUser.getPoints() == null ? 0 : orderUser.getPoints();
					userPoints+=pointsAdd;
					orderUser.setPoints(userPoints);
					orderUser.setSignMoneyRest(qy);
					userMapper.updateTrends(orderUser);
					
					List<DataDictionaryBean> dlist0 = cachedService.getDirList(AppConstant.DATA_50033, cob.getOrgId());//弹幕总开关
					List<DataDictionaryBean> dlist1 = cachedService.getDirList(AppConstant.DATA_50048, cob.getOrgId());//回款弹幕开关 
			        if ((!dlist0.isEmpty() && "1".equals(dlist0.get(0).getDictionaryValue()) && (!dlist1.isEmpty() && "1".equals(dlist1.get(0).getDictionaryValue()) ))) {
						//发送回款弹幕
						mainService.sendSaleBarrage("恭喜"+(cob.getUserId() == null ? cob.getUserId() : cob.getUserName())+"成功回款"+cob.getMoney()+"元!", "1", cob.getOrgId(), cob.getUserId(), cob.getUserName());
						List<DataDictionaryBean> dlist2 = cachedService.getDirList(AppConstant.DATA_50049, user.getOrgId());//
						if (!dlist2.isEmpty() && "1".equals(dlist2.get(0).getDictionaryValue())) {//订单金额不小于
							List<DataDictionaryBean> dlist3 = cachedService.getDirList(AppConstant.DATA_50050, user.getOrgId());//
								String mon=dlist3.get(0).getDictionaryValue();
									if(mon==null||"".endsWith(mon)){
										mon="0";
									}
								BigDecimal bd = new BigDecimal(mon);
							if(cob.getMoney().compareTo(bd)==0||cob.getMoney().compareTo(bd)==1){//订单金额不小于设置值
								List<DataDictionaryBean> dlist4 = cachedService.getDirList(AppConstant.DATA_50051, user.getOrgId());//红包设置
		
								if ((!dlist4.isEmpty() && !"0".equals(dlist4.get(0).getDictionaryValue())&& !"".equals(dlist4.get(0).getDictionaryValue()))) {
									PayApiRequest payrequest=new PayApiRequest();
									
									payrequest.setOrgId(user.getOrgId());
									payrequest.setUserAcc(cob.getSaleAcc());
									payrequest.setPayApiEnum(PayApiTypeEnum.MONEY);
									payrequest.setMoney(Double.valueOf(dlist4.get(0).getDictionaryValue()));
									payrequest.setDesc((cob.getUserId() == null ? cob.getUserId() : cob.getUserName())+ "获得回款奖励红包"+dlist4.get(0).getDictionaryValue()+"元！");
									payrequest.setData("恭喜"+(cob.getUserId() == null ? cob.getUserId() : cob.getUserName())+"成功回款"+cob.getMoney());
									BaseJsonResult json=payApiFacade.pay(payrequest);
									if((boolean) json.get("status")==false){
										logger.error("-------调用pay接口返回结果：--------"+JSONObject.fromObject(json)+"时间："+new Date()); 	
									}

						        }
							}	
						}else if("2".equals(dlist2.get(0).getDictionaryValue())){//订单金额在区间范围内
							List<DataDictionaryBean> dlist5 = cachedService.getDirList(AppConstant.DATA_50052, user.getOrgId());//红包设置
	                        String json=dlist5.get(0).getDictionaryValue();
	                        if(json!=null&&!"".endsWith(json)){
	                        List<MoneyJsonDto> list= JsonUtil.getListJson(json, MoneyJsonDto.class);
	                        if(list!=null&&list.size()>0){
	                        	for(MoneyJsonDto mdto:list){
									String max=mdto.getMaxM();
									String min=mdto.getMinM();
									if(max==null||"".endsWith(max)){
										max="0";
									}
									if(min==null||"".endsWith(min)){
										min="0";
									}
	                        		if((new BigDecimal(min).compareTo(cob.getMoney())==-1
//	                        				||new BigDecimal(min).compareTo(cob.getMoney())==0
	                        				)&&
	                        		   (cob.getMoney().compareTo(new BigDecimal(max))==-1||cob.getMoney().compareTo(new BigDecimal(max))==0)	
	                        		  ){
	    								if ( !"0".equals(mdto.getRedPacket())&&!"".equals(mdto.getRedPacket())) {
	    									PayApiRequest payrequest=new PayApiRequest();
	    									
	    									payrequest.setOrgId(user.getOrgId());
	    									payrequest.setUserAcc(cob.getSaleAcc());
	    									payrequest.setPayApiEnum(PayApiTypeEnum.MONEY);
	    									payrequest.setMoney(Double.valueOf(mdto.getRedPacket()));
	    									payrequest.setDesc((cob.getUserId() == null ? cob.getUserId() : cob.getUserName())+"获得回款奖励红包"+mdto.getRedPacket()+"元！");
	    									payrequest.setData("恭喜"+(cob.getUserId() == null ? cob.getUserId() : cob.getUserName())+"成功回款"+cob.getMoney());
	    									BaseJsonResult json2=payApiFacade.pay(payrequest);
	    									if((boolean) json2.get("status")==false){
	    										logger.error("-------调用pay接口返回结果：--------"+JSONObject.fromObject(json2)+"时间："+new Date()); 	
	    									}
	    						        }	
	                        		}
	                        	}
	                        }
						}
						}
						

					}
					
					//开放接口
					if(tsmCustSynConfigService.isConfigExist(user.getOrgId(), 3, 2)){
						ResCustInfoBean custInfoBean = resCustInfoMapper.getByPrimaryKey(cob.getOrgId(), cob.getCustId());
						ContractOrderBean orderBean = getOrderInfoByIdAndOrg(cob.getId(), user.getOrgId());
						List<ContractOrderDetailBean> detailBeans = getOrderDetailBeans(cob.getId(), user.getOrgId());
						add_order_api(custInfoBean, orderBean, detailBeans);
					}
				}
				
				logUserOperateService.setUserOperateLog( AppConstant.Module_id105,AppConstant.Module_Name105, AppConstant.Operate_id31, AppConstant.Operate_Name31, context.toString(), "");
				
			}else{
				for(ContractOrderDto cob : orderDtos){
					tsmReportPlanService.plusSaleMoney(user.getOrgId(), cob.getSaleAcc(), cob.getTradeDate(), cob.getMoney().negate().doubleValue());
				}
				logUserOperateService.setUserOperateLog( AppConstant.Module_id105,AppConstant.Module_Name105, AppConstant.Operate_id32, AppConstant.Operate_Name32, context.toString(), "");
			}
		}
	}
	
	public Map<String, String> getEventMap(ContractOrderDto cod){
		Map<String, String> map = new HashMap<String, String>();
		ContractDto cd = contractMapper.findContractInfoByIdAndOrg(cod.getCaId(),cod.getOrgId());
		map.put("id", cod.getId());
		map.put("contractName", cd != null ? cd.getContractName() : "");
		map.put("code", cod.getCode());
		map.put("money", cod.getMoney().toString());
		map.put("userName", cod.getUserName());
		map.put("contractCode", cd != null ? cd.getCode() : "");
		map.put("payType", cod.getPayType());
		map.put("inputtime", DateUtil.formatDate(cod.getInputtime(),DateUtil.DATE_DAY));
		return map;
	}
	
	/** 
	 * 上报订单
	 * @param orderId
	 * @param orgId 
	 * @throws Exception 
	 * @create  2015年12月29日 下午4:24:22 lixing
	 * @history  
	 */
	public void reportOrder(@Param("orderId")String orderId,@Param("orgId")String orgId,String account,String name) throws Exception{
		contractOrderMapper.reportOrder(orderId, orgId);		
		// 发送消息
		tsmMessageService.createAuditOrderMessage(orderId,orgId,account,name);

		StringBuffer context = new StringBuffer("");
		ContractOrderBean entity = new ContractOrderBean();
		entity.setOrgId(orgId);
		entity.setId(orderId);
		ContractOrderBean cob = contractOrderMapper.getByCondtion(entity);
		ResCustInfoBean bean = resCustInfoMapper.getByPrimaryKey(cob.getOrgId(), cob.getCustId());
		
		tsmReportPlanService.plusSaleMoney(orgId, bean.getOwnerAcc(), cob.getTradeDate(), cob.getMoney().doubleValue());
		
		if(bean.getState() == 1 || StringUtils.isEmpty(bean.getCompany())){
			context.append(StringUtils.isEmpty(bean.getName()) ? "" : bean.getName());
		}else{
			context.append(StringUtils.isEmpty(bean.getCompany()) ? "" : bean.getCompany());
		}
//		if(StringUtils.isNotBlank(bean.getMobilephone())) context.append("(").append(bean.getMobilephone()).append(")");
		context.append("，订单编号：").append(cob.getCode());
		logUserOperateService.setUserOperateLog( AppConstant.Module_id1007,AppConstant.Module_Name1007, AppConstant.Operate_id29, AppConstant.Operate_Name29, context.toString(), "");
		//发送弹幕
		
		//根据配置，上报订单发送弹幕，需加上订单金额
		if(mainService.getBarrageSign(orgId)==1&&mainService.getBarrageSign3(orgId)==1){
			String retName="";
			if(mainService.getBarrageSign2(orgId)==0){
				retName=((cob.getCompany() == null || "".equals(cob.getCompany()))? cob.getCustName() : cob.getCompany());	
			}
			if(retName==null){
				retName="";
			}
			mainService.sendSingBarrage("恭喜"+(name == null ? account : name)+"成功签约"+retName+"!", "1",orgId,account,name,String.valueOf(cob.getMoney()),1);
			List<DataDictionaryBean> dlist0 = cachedService.getDirList(AppConstant.DATA_50047, orgId);//红包设置
			if ((!dlist0.isEmpty() && !"0".equals(dlist0.get(0).getDictionaryValue()))) {
				PayApiRequest payrequest=new PayApiRequest();
				
				payrequest.setOrgId(orgId);
				payrequest.setUserAcc(account);
				payrequest.setPayApiEnum(PayApiTypeEnum.SIGN);
				payrequest.setMoney(Double.valueOf(dlist0.get(0).getDictionaryValue()));
				payrequest.setDesc((name == null ? account : name)+"获得签约奖励红包"+dlist0.get(0).getDictionaryValue()+"元！");
				payrequest.setData("恭喜"+(name == null ? account : name)+"成功签约"+retName+cob.getMoney());
				BaseJsonResult json=payApiFacade.pay(payrequest);
				if((boolean) json.get("status")==false){
					logger.error("-------调用pay接口返回结果：--------"+JSONObject.fromObject(json)+"时间："+new Date()); 	
				}
				
	        }
		}
	}
	
	/** 
	 * 撤回订单
	 * @param orderId
	 * @param orgId 
	 * @throws Exception 
	 * @create  2015年12月29日 下午4:24:22 lixing
	 * @history  
	 */
	public void rebackOrder(@Param("orderId")String orderId,@Param("orgId")String orgId) throws Exception{
		contractOrderMapper.rebackOrder(orderId, orgId);
		StringBuffer context = new StringBuffer("");
		ContractOrderBean entity = new ContractOrderBean();
		entity.setOrgId(orgId);
		entity.setId(orderId);
		ContractOrderBean cob = contractOrderMapper.getByCondtion(entity);
		ResCustInfoBean bean = resCustInfoMapper.getByPrimaryKey(cob.getOrgId(), cob.getCustId());
		
		tsmReportPlanService.plusSaleMoney(orgId, bean.getOwnerAcc(), cob.getTradeDate(), cob.getMoney().negate().doubleValue());
		
		if(bean.getState() == 1 || StringUtils.isEmpty(bean.getCompany())){
			context.append(StringUtils.isEmpty(bean.getName()) ? "" : bean.getName());
		}else{
			context.append(StringUtils.isEmpty(bean.getCompany()) ? "" : bean.getCompany());
		}
//		if(StringUtils.isNotBlank(bean.getMobilephone())) context.append("(").append(bean.getMobilephone()).append(")");
		context.append("，订单编号：").append(cob.getCode());
		logUserOperateService.setUserOperateLog( AppConstant.Module_id1007,AppConstant.Module_Name1007, AppConstant.Operate_id27, AppConstant.Operate_Name27, context.toString(), "");
	}
	
	
	/** 
	 * 作废订单
	 * @param orderId
	 * @param orgId 
	 * @throws Exception 
	 * @create  2016年12月14日 上午9:43:51 lixing
	 * @history  
	 */
	public void cancelledOrder(String orderId,String orgId) throws Exception{
		ContractOrderBean orderBean = getOrderInfoByIdAndOrg(orderId, orgId);
		if(orderBean != null && orderBean.getAuthState() == 2){
			ContractOrderDto cod = new ContractOrderDto();
			cod.setOrgId(orgId);
			cod.setIds(Arrays.asList(orderId));
			List<ContractOrderDto> dtos = contractOrderMapper.findContractOrders(cod);
			if(dtos.size() > 0){
				Map<String, BigDecimal> moneyMap = new HashMap<String, BigDecimal>();
				List<DeleteOrderDto> deleteOrders = new ArrayList<DeleteOrderDto>();
				for(ContractOrderDto orderDto : dtos){
					if(orderDto.getMoney() != null && orderDto.getMoney().compareTo(BigDecimal.valueOf(0)) == 1){
						if(moneyMap.containsKey(orderDto.getCustId())){
							BigDecimal money  = moneyMap.get(orderDto.getCustId());
							money = money.add(orderDto.getMoney());
							moneyMap.put(orderDto.getCustId(), money);
						}else{
							moneyMap.put(orderDto.getCustId(),orderDto.getMoney());
						}
						deleteOrders.add(new DeleteOrderDto(orderDto.getUpdatetime(),orderDto.getCustId(), orderDto.getSignUserId(),orderDto.getSignUserAcc(),orderDto.getGroupId(), orderDto.getMoney()));
					}
				}
				//修改合同累积金额
				if(moneyMap.keySet().size() > 0){
					for(String key : moneyMap.keySet()){
						BigDecimal money = moneyMap.get(key);
						ContractMoneyBean cmb = new ContractMoneyBean();
						cmb.setIsDel(0);
						cmb.setCustId(key);
						cmb.setOrgId(orgId);
						List<ContractMoneyBean> cmbBeans = contractMoneyMapper.findByCondtion(cmb);
						if(cmbBeans.size() > 0){
							ContractMoneyBean bean = cmbBeans.get(0);
							bean.setMoney(bean.getMoney().add(money.negate()));
							bean.setUpdatetime(new Date());
							contractMoneyMapper.update(bean);
						}
					}
				}
				
				for (DeleteOrderDto dto : deleteOrders) {
					//修改排行榜
					rankingReportService.updateRankingData(orgId, dto.getSignAcc(), dto.getMoney().negate(), 0, 0, dto.getCheckDate());
					planFactService.cancelSign(orgId, dto.getGroupId(), dto.getSignUserId(), orderBean.getCustId(), 0, dto.getMoney().negate().doubleValue(), dto.getCheckDate(), 0);
				}
				
				contractOrderMapper.cancelledOrder(orderId, orgId);
				
				StringBuffer context = new StringBuffer("");
				ContractOrderBean entity = new ContractOrderBean();
				entity.setOrgId(orgId);
				entity.setId(orderId);
				ContractOrderBean cob = contractOrderMapper.getByCondtion(entity);
				ResCustInfoBean bean = resCustInfoMapper.getByPrimaryKey(cob.getOrgId(), cob.getCustId());
				if(bean.getState() == 1 || StringUtils.isEmpty(bean.getCompany())){
					context.append(StringUtils.isEmpty(bean.getName()) ? "" : bean.getName());
				}else{
					context.append(StringUtils.isEmpty(bean.getCompany()) ? "" : bean.getCompany());
				}
//				if(StringUtils.isNotBlank(bean.getMobilephone())) context.append("(").append(bean.getMobilephone()).append(")");
				context.append("，订单编号：").append(cob.getCode());
				logUserOperateService.setUserOperateLog( AppConstant.Module_id1007,AppConstant.Module_Name1007, AppConstant.Operate_id28, AppConstant.Operate_Name28, context.toString(), "");
			}
		}
	}
	
	public ContractOrderBean getOrderInfoByIdAndOrg(String orderId,String orgId){
		return contractOrderMapper.getOrderInfoByIdAndOrg(orderId, orgId);
	}
	
	public List<ContractOrderDetailBean> getOrderDetailBeans(String orderId,String orgId){
		ContractOrderDetailBean cod = new ContractOrderDetailBean();
		cod.setOrderId(orderId);
		cod.setOrgId(orgId);
		cod.setIsDel(0);
		return contractOrderDetailMapper.findByCondtion(cod);
	}
	
	public List<ContractOrderDetailDto> getContractOrderDetailListPage(ContractOrderDetailDto codd){
		List<ContractOrderDetailDto> list = contractOrderDetailMapper.findContractOrderDetailListPage(codd);
		if(list.size() > 0){
			List<String> custIds = new ArrayList<String>();
			for(ContractOrderDetailDto contract : list) custIds.add(contract.getCustId());
			List<ResCustInfoDto> custs = resCustInfoMapper.findCustPoolList(custIds, codd.getOrgId());
			Map<String, ResCustInfoDto> map = new HashMap<String, ResCustInfoDto>();
			for(ResCustInfoDto cust : custs) map.put(cust.getResCustId(), cust);
			for(ContractOrderDetailDto contract : list){
				if(map.containsKey(contract.getCustId())) {
					contract.setOwnerAcc(map.get(contract.getCustId()).getOwnerAcc());
					contract.setCommonAcc(map.get(contract.getCustId()).getCommonAcc());
//					contract.setCompany(map.get(contract.getCustId()).getCompany());
//					contract.setCustName(map.get(contract.getCustId()).getName());
				}
			}
		}
		return list;
	}
	
	public List<ContractOrderAuthlogBean> getAuthlogBeans(ContractOrderAuthlogBean coab){
		return contractOrderAuthlogMapper.findByCondtion(coab);
	}
	
	public void deleteOrderBatch(ContractOrderDto cod) throws Exception{
		contractOrderMapper.deleteOrderBatch(cod);
		contractOrderMapper.deleteOrderDetailBatch(cod);
		StringBuffer context = new StringBuffer("");
		if(cod.getIds().size() > 1){
			context.append(cod.getIds().size()+"条");
		}else{
			ContractOrderBean entity = new ContractOrderBean();
			entity.setOrgId(cod.getOrgId());
			entity.setId(cod.getIds().get(0));
			ContractOrderBean cob = contractOrderMapper.getByCondtion(entity);
			ResCustInfoBean bean = resCustInfoMapper.getByPrimaryKey(cob.getOrgId(), cob.getCustId());
			if(bean.getState() == 1 || StringUtils.isEmpty(bean.getCompany())){
				context.append(StringUtils.isEmpty(bean.getName()) ? "" : bean.getName());
			}else{
				context.append(StringUtils.isEmpty(bean.getCompany()) ? "" : bean.getCompany());
			}
//			if(StringUtils.isNotBlank(bean.getMobilephone())) context.append("(").append(bean.getMobilephone()).append(")");
			context.append("，订单编号：").append(cob.getCode());
		}
		logUserOperateService.setUserOperateLog( AppConstant.Module_id1007,AppConstant.Module_Name1007, AppConstant.Operate_id5, AppConstant.Operate_Name5, context.toString(), "");
	}
	
	public ContractOrderDto findTodayReport(String orgId,String userId){
		return contractOrderMapper.findTodayReport(orgId, userId);
	}
	
	public List<ContractOrderDto> findTeamTodayReport(String orgId,String userId,List<String> groupIds){
		return contractOrderMapper.findTeamTodayReport(orgId, userId, groupIds);
	}
	
	public List<ContractOrderDto> findTeamTodayReportListPage(ContractOrderDto contractOrderDto){
		return contractOrderMapper.findTeamTodayReportListPage(contractOrderDto);
	}
	
	/** 
	 * 产品分布图
	 * @param orgId
	 * @param ownerAcc
	 * @param adminAcc
	 * @param groupId
	 * @return 
	 * @create  2016年1月27日 下午6:23:25 lixing
	 * @history  
	 */
	public List<Map<String,Object>> getProductChart(@Param("orgId")String orgId,@Param("ownerAcc")String ownerAcc,
												@Param("adminAcc")String adminAcc,@Param("groupId")String groupId){
		return contractOrderDetailMapper.findProductChart(orgId, ownerAcc, adminAcc, groupId);
	}
	
	/** 
	 * 签约客户产品分布详情
	 * @param orgId
	 * @param ownerAcc
	 * @param adminAcc
	 * @param groupId
	 * @return 
	 * @create  2016年2月1日 下午1:59:49 lixing
	 * @history  
	 */
	public List<Map<String, Object>> getProductLayout(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds){
		return contractOrderDetailMapper.findProductLayout(orgId, adminAcc, groupIds);
	}
	
	public List<Map<String, Object>> getProductMemberLayout(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupId")String groupId){
		return contractOrderDetailMapper.findProductMemberLayout(orgId, adminAcc, groupId);
	}
	
	public List<String> getOrderExpireCustIds(String orgId,String expireDate,String ownerAcc){
		return contractOrderMapper.findOrderExpireCustIds(orgId, expireDate,ownerAcc);
	}
}
