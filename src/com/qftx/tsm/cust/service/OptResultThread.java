package com.qftx.tsm.cust.service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.dto.ResOptDetailDto;
import com.qftx.tsm.log.service.LogStaffInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.Future;

/**
 * ���������
 * Created by Administrator on 2016/6/1.
 */

public class OptResultThread extends Thread {
    private Logger logger = Logger.getLogger(OptResultThread.class);
    private List<Future<ResOptDetailDto>> list;
    private String orgId;
    private String reqId;
    private Integer type;
    private CachedService cachedService;
    private String cacheName;
    private LogStaffInfoService  logStaffInfoService;
    private String account ;
    private String[] accs;
    private ComResourceService comResourceService;
    private LogUserOperateService logUserOperateService;
    private int count;
    public OptResultThread(String reqId,String account,List<Future<ResOptDetailDto>> list, String orgId,Integer type,CachedService cachedService,String cacheName ,LogStaffInfoService  logStaffInfoService,ComResourceService comResourceService,LogUserOperateService logUserOperateService,int count) {
        this.account =account;
        this.reqId = reqId;
    	this.list = list;
        this.orgId = orgId;
        this.type =type;
        this.cachedService = cachedService;
        this.cacheName = cacheName;
        this.logStaffInfoService = logStaffInfoService;
        this.comResourceService = comResourceService;
        this.logUserOperateService = logUserOperateService;
        this.count = count;
    }

    @Override
    public void run() {
//        ResOptDto resOptDto = new ResOptDto();
        for (int i = 0; i < list.size(); i++) {
            try {
                ResOptDetailDto dto = list.get(i).get();
//                resOptDto.getList().add(dto);
            } catch (Exception e) {
            	StringBuffer sb = new StringBuffer();
            	sb.append("reqId="+ reqId);
            	sb.append("orgId="+orgId);
            	sb.append("resList="+JsonUtil.getJsonString(list));
            	sb.append("type="+type);
            	sb.append("cahcedName="+cacheName);
            	logStaffInfoService.saveLogInfo(orgId, account, "员工资源结果线程操作异常", sb.toString()+e.getMessage(), reqId);
                logger.error("OptResultThread reqId+"+reqId+"。dto= " + JSON.toJSONString(list.get(i) )+ e.getMessage(), e);
                cachedService.delOptMap(reqId, orgId, type==1?CachedNames.ORG_ASSIGN:CachedNames.ORG_RECYLE,account);
            }
        }
//        resOptDto.setOrgId(orgId);
//        resOptDto.setTotal(list.size());
//        resOptDto.setOptedNum(list.size());
//        resOptDto.getTotalDiff(resOptDto.getList());
//        resOptDto.setType(type);
//        resOptDto.setIsFinished(true);
//        logger.debug("reqId="+reqId+"OptResultThread 处理结果=" + JSON.toJSONString(resOptDto));
        logStaffInfoService.saveLogInfo(orgId, account, "员工资源结果线程处理结束", "员工资源结果线程处理结束", reqId);
		try {
			/*if ("org_recyle".equals(cacheName)) {
				logUserOperateService.setUserOperateLog( AppConstant.Module_id108,AppConstant.Module_Name108, AppConstant.Operate_id34, AppConstant.Operate_Name43, "分配资源"+count+"条","");
			} else {
				logUserOperateService.setUserOperateLog( AppConstant.Module_id108,AppConstant.Module_Name108, AppConstant.Operate_id34, AppConstant.Operate_Name34, "分配资源"+count+"条","");
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
        cachedService.delOptMap(reqId,orgId, cacheName,account);
        if(accs!=null){
        	for(String acc:accs){
        		comResourceService.freshTaoCache(orgId,acc);
        	}
        }
    }

	public String[] getAccs() {
		return accs;
	}

	public void setAccs(String[] accs) {
		this.accs = accs;
	}
}
