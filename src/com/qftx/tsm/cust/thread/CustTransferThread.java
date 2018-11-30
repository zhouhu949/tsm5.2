package com.qftx.tsm.cust.thread;

import com.qftx.common.cached.CachedService;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.CustOptorBean;
import com.qftx.tsm.cust.dao.CustOptorMapper;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.follow.dao.CustSaleChanceMapper;
import com.qftx.tsm.follow.service.CustSaleChanceService;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.log.service.LogCustInfoService;

import java.util.*;

public class CustTransferThread implements Runnable {
	
	private ResCustInfoService resCustInfoService = (ResCustInfoService)AppContextUtil.getBean("resCustInfoService");
	private CustOptorMapper custOptorMapper = (CustOptorMapper)AppContextUtil.getBean("custOptorMapper");;
	private LogBatchInfoService logBatchInfoService = (LogBatchInfoService) AppContextUtil.getBean("logBatchInfoService");
	private CachedService cachedService = (CachedService)AppContextUtil.getBean("cachedService");
	private LogCustInfoService logCustInfoService = (LogCustInfoService)AppContextUtil.getBean("logCustInfoService");
	private CustSaleChanceMapper custSaleChanceMapper = (CustSaleChanceMapper)AppContextUtil.getBean("custSaleChanceMapper");
	
	private String orgId;
	private List<String> custIdStrList;
	private String moveAcc;
	private String moveName;
	private String logAct;
	private String reason;
	private String name;
	public CustTransferThread(String orgId,List<String> custIdStrList,String moveAcc,String moveName,String logAct,String reason,String name) {
		this.custIdStrList = custIdStrList;
		this.orgId = orgId;
		this.moveAcc = moveAcc;
		this.moveName = moveName;
		this.logAct = logAct;
		this.reason = reason;
		this.name = name;
	}
	
	public void run() {
		
		List<CustOptorBean> optorBeans = new ArrayList<CustOptorBean>();
		List<ResCustInfoDto> list = new ArrayList<ResCustInfoDto>();
		List<String> ids = new ArrayList<String>();
		Map<String, String> nameMap = cachedService.getOrgUserNames(orgId);
		Map<String, Object> logMap = new HashMap<String, Object>();
		for (String resCustIdStr : custIdStrList) {
			String resCustId = resCustIdStr.split("_")[0];
			String ownerAcc = resCustIdStr.split("_")[2];
			if(!ownerAcc.equals(moveAcc)){
				ids.add(resCustId);
				ResCustInfoDto dto = new ResCustInfoDto();
				dto.setItemCode(AppConstant.SALES_TYPE_ONE);
				dto.setResCustId(resCustId);
				dto.setOrgId(orgId);
				ResCustInfoDto custDto = resCustInfoService.getTeamCustByCustId(dto);
				CustOptorBean optor = new CustOptorBean();
				String custOptorId = SysBaseModelUtil.getModelId();
				optor.setCustOptorId(custOptorId);
				optor.setCustId(resCustId);
				optor.setType(AppConstant.OPREATE_TYPE14);
				optor.setTransferAcc(moveAcc); // 转入至
				optor.setTransferedAcc(ownerAcc); // 转出自
				Date currentDate = new Date();
				optor.setOptorResDate(currentDate);
				optor.setOptoerAcc(logAct);
				optor.setOrgId(orgId); // 机构编号
				if (custDto != null) {
					optor.setOwnerAcc(ownerAcc); // 所有者帐号
					optor.setOwnerName(nameMap.get(ownerAcc)); // 所有人名称
					optor.setSaleProcessId(custDto.getSaleProcessId()); // 销售进程选项ID
				}
				if (StringUtils.isNotBlank(reason)) {
					optor.setReason(reason);
				}
				optorBeans.add(optor);
				logMap.put(resCustId, "{\"transferedAcc\":\""+ownerAcc+"\",\"transferAcc\":\""+moveAcc+"\"}");
				
				// 设置淘到客户时间
				dto.setAmoytocustomerDate(currentDate);
				list.add(dto);
			}
		}
		
		custSaleChanceMapper.updateIsDelByCustIds(orgId,ids);
		
		custOptorMapper.insertBatch(optorBeans);
		
		resCustInfoService.updateAmoytocustomerDate(list);
		
		if(ids.size() > 0){
			LogBean logBean = new LogBean(orgId, logAct, name, OperateEnum.LOG_TRANSFER.getCode(), OperateEnum.LOG_TRANSFER.getDesc(), ids.size(), SysBaseModelUtil.getModelId());
			logBean.setOperateId(AppConstant.Operate_id23);
			logBean.setOperateName(AppConstant.Operate_Name23);
			logBean.setModuleId(AppConstant.Module_id103);
			logBean.setModuleName(AppConstant.Module_Name103);
			logBean.setContent(ids.size()+"条，接收人："+moveName);
			logCustInfoService.addTableStoreLog(logBean, logMap);
		}
	}

}
