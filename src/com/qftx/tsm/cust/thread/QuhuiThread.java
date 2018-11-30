package com.qftx.tsm.cust.thread;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.log.service.LogContactDayDataService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.util.ContactUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QuhuiThread implements Runnable {
	private Log log = LogFactory.getLog(QuhuiThread.class);
	private ResCustInfoService resCustInfoService = (ResCustInfoService)AppContextUtil.getBean("resCustInfoService");
	private LogCustInfoService logCustInfoService = (LogCustInfoService)AppContextUtil.getBean("logCustInfoService");
	private LogContactDayDataService logContactDayDataService = (LogContactDayDataService)AppContextUtil.getBean("logContactDayDataService"); 
	
	private String loginAcc;
	private List<String> ids;
	private String orgId;
	private String name;
	private String module;
	private ShiroUser user;
	public QuhuiThread(String loginAcc, List<String> ids, String orgId,String name,String module,ShiroUser user) {
		this.loginAcc = loginAcc;
		this.ids = ids;
		this.orgId = orgId;
		this.name = name;
		this.module = module;
		this.user = user;
	}
	
	@Override
	public void run() {
		try {
			Map<String, Object> logMap = new HashMap<String, Object>();
			for (String id : ids) {
				ResCustInfoDto dto = new ResCustInfoDto();
				dto.setItemCode(AppConstant.SALES_TYPE_ONE);
				dto.setResCustId(id);
				dto.setOrgId(orgId);
				ResCustInfoDto custDto = resCustInfoService.getTeamCustByCustId(dto);
				if (custDto != null) {
					logContactDayDataService.addLogContactDayData(ContactUtil.SEA_TO_RES, orgId, id, custDto.getOwnerAcc(), null, null);
				}
				logMap.put(id, "");
			}
			LogBean logBean = new LogBean(orgId, loginAcc, name, OperateEnum.LOG_QUHUI.getCode(), OperateEnum.LOG_QUHUI.getDesc(), ids.size(),SysBaseModelUtil.getModelId());
			if(module.equals("0")){
				logBean.setModuleId(AppConstant.Module_id1004);
				logBean.setModuleName(AppConstant.Module_Name1004);
			}else if(module.equals("1")){
				logBean.setModuleId(AppConstant.Module_id1005);
				logBean.setModuleName(AppConstant.Module_Name1005);
			}else if(module.equals("2")){
				logBean.setModuleId(AppConstant.Module_id1004);
				logBean.setModuleName(AppConstant.Module_Name1004);
			}
			if(ids.size() > 1){
				logBean.setOperateId(AppConstant.Operate_id20);
				logBean.setOperateName(AppConstant.Operate_Name20);
				logBean.setContent(ids.size()+"条");
			}else{
				StringBuffer context = new StringBuffer("");
				ResCustInfoBean bean = resCustInfoService.getCustInfoByOrgIdAndPk(user.getOrgId(), ids.get(0));
				if(user.getIsState() == 1 || StringUtils.isEmpty(bean.getCompany())){
					context.append(StringUtils.isEmpty(bean.getName()) ? "" : bean.getName());
				}else{
					context.append(StringUtils.isEmpty(bean.getCompany()) ? "" : bean.getCompany());
				}
				logBean.setOperateId(AppConstant.Operate_id22);
				logBean.setOperateName(AppConstant.Operate_Name22);
				logBean.setContent(context.toString());
			}
			logCustInfoService.addTableStoreLog(logBean, logMap);
		} catch (Exception e) {
			log.error("取回异常！",e);
		}
	}
	
}
