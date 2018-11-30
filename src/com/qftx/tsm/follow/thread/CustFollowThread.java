package com.qftx.tsm.follow.thread;

import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.CustOptorBean;
import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.cust.bean.TsmCustGuideProc;
import com.qftx.tsm.cust.dao.CustOptorMapper;
import com.qftx.tsm.cust.dao.TsmCustGuideMapper;
import com.qftx.tsm.cust.dao.TsmCustGuideProcMapper;
import com.qftx.tsm.log.service.LogContactDayDataService;
import com.qftx.tsm.log.util.ContactUtil;
import com.qftx.tsm.main.service.ContactDayDataService;
import com.qftx.tsm.plan.facade.PlanFactService;
import com.qftx.tsm.plan.facade.enu.PlanSignCR;
import com.qftx.tsm.plan.facade.enu.PlanWillCR;
import com.qftx.tsm.report.service.CallReportService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import java.util.*;


public class CustFollowThread  implements Runnable {
	private static Logger logger = Logger.getLogger(CustFollowThread.class);
	
	private CustOptorMapper custOptorMapper = (CustOptorMapper) AppContextUtil.getBean("custOptorMapper");
	private PlanFactService planFactService = (PlanFactService) AppContextUtil.getBean("planFactService");
	private ContactDayDataService contactDayDataService = (ContactDayDataService) AppContextUtil.getBean("contactDayDataService");
	private TsmCustGuideMapper tsmCustGuideMapper = (TsmCustGuideMapper) AppContextUtil.getBean("tsmCustGuideMapper");
	private TsmCustGuideProcMapper tsmCustGuideProcMapper = (TsmCustGuideProcMapper) AppContextUtil.getBean("tsmCustGuideProcMapper");
	private CallReportService callReportService = (CallReportService) AppContextUtil.getBean("callReportService");
	private LogContactDayDataService logContactDayDataService = (LogContactDayDataService) AppContextUtil.getBean("logContactDayDataService");
	
	
	private String userAccount;
	private String userName;
	private String orgId;
	private boolean is_follow;
	private boolean isSign;
	private String custId;
	private Integer status;
	private String saleProcessId;
	private String lastSaleProcessId;
	private String userId;
	private String groupId;
	private TsmCustGuide custGuide;
	private String suitProcId;
	private String actionType;
	private String custFollowId;
	private Date followDate;
	private String ownerAcc;
	private Integer signSetting;
	
	public CustFollowThread(String userAccount,String orgId,boolean is_follow,
			boolean isSign,String custId,String userName,Integer status,
			String saleProcessId,String lastSaleProcessId,String userId,String groupId,
			TsmCustGuide custGuide,String suitProcId,String actionType,String custFollowId,Date followDate,LogContactDayDataService logContactDayDataService,String ownerAcc,Integer signSetting){
		this.userAccount = userAccount;
		this.orgId = orgId;
		this.is_follow = is_follow;
		this.isSign = isSign;
		this.custId = custId;
		this.userName = userName;
		this.status = status;
		this.saleProcessId = saleProcessId;
		this.lastSaleProcessId = lastSaleProcessId;
		this.userId = userId;
		this.groupId = groupId;
		this.custGuide = custGuide;
		this.suitProcId = suitProcId;
		this.actionType = actionType;
		this.custFollowId = custFollowId;
		this.followDate = followDate;
		this.logContactDayDataService = logContactDayDataService;
		this.ownerAcc = ownerAcc;
		this.signSetting = signSetting;
	}

	@Override
	public void run() {
		try{
			if(!is_follow){ // 非主动放弃操作 新增操作记录
				CustOptorBean custOptor = new CustOptorBean();
			    custOptor.setCustOptorId(SysBaseModelUtil.getModelId());
				custOptor.setCustId(custId);
				custOptor.setSaleProcessId(custGuide.getSaleProcessId());
				if(isSign){
					custOptor.setType(AppConstant.OPREATE_TYPE15);
					/*if (signSetting == 1) {
						logContactDayDataService.addLogContactDayData(ContactUtil.CUST_TO_SIGN, orgId, custId, ownerAcc, lastSaleProcessId, saleProcessId);
					}*/
				}else{
					custOptor.setType(AppConstant.OPREATE_TYPE10);	
					if (status != 6 && status != 7 && status != 8) {
						logContactDayDataService.addLogContactDayData(ContactUtil.CUST_CONTACT, orgId, custId, ownerAcc, lastSaleProcessId, saleProcessId);
					}
					//tsmReportWillService.addTsmReportWillOptionSumCheck(orgId,userAccount,userName,lastSaleProcessId,saleProcessId,"",custId,1);
				}		
				custOptor.setOptorResDate(new Date());
				custOptor.setOptoerAcc(userAccount);
				custOptor.setOwnerAcc(userAccount);
				custOptor.setTransferAcc(userAccount);
				custOptor.setOwnerName(userName);
				custOptor.setOrgId(orgId);
				custOptorMapper.insert(custOptor);
			}
			
			/************* 修改日计划信息 updateContactResult **************/
			if(status == 1 || status==2 || status==3){ // 意向客户
				String planWillCr = PlanWillCR.TURN_WILL.getResult();
				if(!is_follow){ // 非主动放弃
					if(saleProcessId.equals(lastSaleProcessId)){
						planWillCr = PlanWillCR.NO_CHANGE.getResult();
						if (followDate != null) {
							if (DateUtil.formatDate(followDate, "yyyy-MM-dd").equals(DateUtil.formatDate(new Date(), "yyyy-MM-dd"))) {
								if (status == 1) {
									planFactService.updateContactResult(orgId,groupId,userId,custId,"res",true,"will");
								}else {
									planFactService.updateContactResult(orgId,groupId,userId,custId,"will",true,"will");
								}
							}else {
								planFactService.updateContactResult(orgId,groupId, userId,custId, "will", planWillCr);
							}
						}else {
							planFactService.updateContactResult(orgId,groupId, userId,custId, "will", planWillCr);
						}
					}else{
						if (followDate != null) {
							if (DateUtil.formatDate(followDate, "yyyy-MM-dd").equals(DateUtil.formatDate(new Date(), "yyyy-MM-dd"))) {
								if (status == 1) {
									planFactService.updateContactResult(orgId,groupId,userId,custId,"res",true,"will");
								}else {
									planFactService.updateContactResult(orgId,groupId,userId,custId,"will",true,"will");
								}
							}else {
								planFactService.updateContactResult(orgId,groupId, userId,custId, "will", planWillCr,saleProcessId);
							}
						}else {
							planFactService.updateContactResult(orgId,groupId, userId,custId, "will", planWillCr,saleProcessId);
						}
					}				
				}					
			}else if((status==6 || status==7 || status==8) && !isSign){ // 签约客户
				String planSignCr = PlanSignCR.NO_CHANGE.getResult();
				if (DateUtil.formatDate(followDate, "yyyy-MM-dd").equals(DateUtil.formatDate(new Date(), "yyyy-MM-dd"))) {
					planFactService.updateContactResult(orgId,groupId,userId,custId,"sign",true,"sign");
				}else {
					planFactService.updateContactResult(orgId,groupId, userId, custId, "sign", planSignCr);
				}
			}
			
			if(StringUtils.isNotBlank(custGuide.getCustGuideId())){
				//  修改销售导航
				custGuide.setOrgId(orgId);
				custGuide.setSaleProcessId(saleProcessId);
				custGuide.setCustId(custId);
				custGuide.setInputerAcc(userAccount);
				custGuide.setInputdate(new Date());
				tsmCustGuideMapper.updateTrends(custGuide);		
			}else{
				// 新增销售导航记录
				String guideId = SysBaseModelUtil.getModelId();
				custGuide.setCustGuideId(guideId);
				custGuide.setCustId(custId);
				custGuide.setInputerAcc(ShiroUtil.getUser().getAccount());
				custGuide.setInputdate(new Date());
				custGuide.setOrgId(orgId);
				custGuide.setSaleProcessId(saleProcessId);
				tsmCustGuideMapper.insert(custGuide);
			}
			
			/**  修改 适用产品 */	
			// 先删除 与销售导航相关产品
			Map<String,String>map = new HashMap<String, String>();
			map.put("orgId", orgId);
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
						proc.setOrgId(orgId);
						suitProList.add(proc);
					}				
				}
				if (suitProList.size() > 0) {
					tsmCustGuideProcMapper.insertBatch(suitProList);
				}
			}
			
//			/*if(!is_follow){ // 非放弃
//				// 联系方式为：会客联系时，增加上门拜访数
//				if("2".equals(actionType)){
////					callReportService.addVisit(orgId,userAccount,1);
//				}
//			}*/
			logger.debug("【"+orgId+"】"+custFollowId+"【客户跟进异步线程】end!!");
		}catch(Exception e){
			logger.error("【"+orgId+"】"+custFollowId+"【客户跟进异步线程】exception:", e);
		}
	}
	

}
