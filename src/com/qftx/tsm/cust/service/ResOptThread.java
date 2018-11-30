package com.qftx.tsm.cust.service;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.tsm.cust.dto.ResOptDetailDto;
import com.qftx.tsm.log.service.LogStaffInfoService;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * ��Դ�����߳�
 * Created by Administrator on 2016/5/31.
 */

public class ResOptThread implements Callable<ResOptDetailDto> {
    private Logger logger = Logger.getLogger(ResOptThread.class);
    private List<String> resList;
    private String reqId ;
    private String ownerAcc;
    private String orgId;
    private String account;
    private Integer type;
    private String cahcedName;
    private CachedService cachedService;
    private ComResourceService comResourceService;
    private LogStaffInfoService logStaffInfoService;
    private String name;
    private String newResGroupId;
    private String taskId;
    private Map<String,Object> map;
    private String batchId;
    
    public ResOptThread(String reqId,List<String> resList, String ownerAcc, String orgId,
                        String account, CachedService cachedService, ComResourceService comResourceService, 
                        int type, String cachedName,LogStaffInfoService logStaffInfoService,String name,
                        String newResGroupId,String taskId,Map<String,Object> map,String batchId) {
        this.account = account;
        this.reqId = reqId;
        this.orgId = orgId;
        this.ownerAcc = ownerAcc;
        this.resList = resList;
        this.cachedService = cachedService;
        this.comResourceService = comResourceService;
        this.type = type;
        this.cahcedName = cachedName;
        this.logStaffInfoService = logStaffInfoService;
        this.name = name;
        this.newResGroupId = newResGroupId;
        this.taskId = taskId;
        this.map = map;
        this.batchId = batchId;
    }

    public ResOptDetailDto call() throws Exception {
        try {
        	if(resList !=null && resList.size()>0){
        		if (type == 1) {
        			comResourceService.dealData(reqId,resList, ownerAcc, orgId, account,name,taskId,map,batchId,"staff");
        		} else if (type == 2) {
        			comResourceService.recyleRes(reqId,resList, ownerAcc,orgId, account,name,newResGroupId,taskId,map,batchId,"staff");
        		}
        	}
            String context = "ResOptThread.call reqId=" +reqId+",account="+account +"ownerAcc="+ownerAcc +",resList=" +JsonUtil.getJsonString(resList) +",size=" + resList.size();
            logStaffInfoService.saveLogInfo(orgId, account, "员工资源工作线程操作结束", context, reqId);
        } catch (Exception e) {
        	StringBuffer sb = new StringBuffer();
        	sb.append("reqId="+ reqId);
        	sb.append("account="+account);
        	sb.append("orgId="+orgId);
        	sb.append("ownerAcc="+ownerAcc);
        	sb.append("resList="+JsonUtil.getJsonString(resList));
        	sb.append("type="+type);
        	sb.append("cahcedName="+cahcedName);
        	logStaffInfoService.saveLogInfo(orgId, account, "员工资源工作线程操作异常", sb.toString()+e.getMessage(), reqId);
            logger.error("ResOptThread  reqId="+reqId+"。resList=" + JsonUtil.getJsonString(resList) + "|ownerAcc=" + ownerAcc + "|orgId=" + orgId + "|account=" + account + e.getMessage(), e);
        } finally {
        }
        return null;
    }
}
