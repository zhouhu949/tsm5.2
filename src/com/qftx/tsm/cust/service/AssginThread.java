package com.qftx.tsm.cust.service;

import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dao.ComResourceMapper;
import com.qftx.tsm.cust.dto.ResOptDetailDto;
import com.qftx.tsm.log.service.LogStaffInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import org.apache.log4j.Logger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

public class AssginThread extends Thread {
	private Logger logger = Logger.getLogger(AssginThread.class);
	private ComResourceMapper comResourceMapper;
	private String[] staffAccs;
	private String orgId;
	private String reqId;
	private String everySum;
	private ThreadPoolTaskExecutor taskExecutor;
	private CachedService cachedService;
	private ComResourceService comResourceService;
	private String account;
	private LogStaffInfoService logStaffInfoService;
	private Map<String, String> dataMap;
	private List<ResCustInfoBean> totalResList;
	private Map<String, Object> resMap;
	private LogUserOperateService logUserOperateService;
	private String name;
	private Map<String, Object> map;
	private String taskId;
	private String batchId;
	


	public AssginThread(ComResourceMapper comResourceMapper, String[] staffAccs, String orgId, String reqId, String everySum,
			ThreadPoolTaskExecutor taskExecutor, CachedService cachedService, ComResourceService comResourceService, String account,
			LogStaffInfoService logStaffInfoService, Map<String, String> dataMap, Map<String, Object> resMap, 
			LogUserOperateService logUserOperateService,String name,Map<String,Object> map,String taskId,String batchId) {
		this.comResourceMapper = comResourceMapper;
		this.staffAccs = staffAccs;
		this.orgId = orgId;
		this.reqId = reqId;
		this.everySum = everySum;
		this.taskExecutor = taskExecutor;
		this.cachedService = cachedService;
		this.comResourceService = comResourceService;
		this.account = account;
		this.logStaffInfoService = logStaffInfoService;
		this.dataMap = dataMap;
		this.resMap = resMap;
		this.logUserOperateService =logUserOperateService;
		this.name = name;
		this.taskId = taskId;
		this.map = map;
		this.batchId = batchId;
		
	}

	public void run() {
		totalResList = comResourceMapper.findResListByMap(resMap);
		if (totalResList == null || totalResList.size() <= 0) {
			cachedService.delOptMap(reqId, orgId, CachedNames.ORG_ASSIGN, account);
			return;
		}
		List<ResCustInfoBean> delResList = new ArrayList<ResCustInfoBean>();
		List<String> idsList = new ArrayList<String>();
		Map<String, String> ownerResMap = new HashMap<String, String>(); // 获取已分配资源
		ArrayList<Future<ResOptDetailDto>> resultList = new ArrayList<Future<ResOptDetailDto>>();
		int mark = 0;
		int count = 0;
		
		for (int i = 0; i < staffAccs.length; i++) {
			String staffAcc = staffAccs[i];
			idsList = new ArrayList<String>();
			idsList.clear();
			ownerResMap.put("ownerAcc", staffAcc);
			ownerResMap.put("orgId", orgId);
			int myResNum = comResourceMapper.findresourceNum(ownerResMap);// 个人拥有资源数量
			String result = isAssign(dataMap, myResNum);
			if ("0".equals(result)) {// 验证是否开启限制资源上限和是否超过上限数
				if (myResNum < new Integer(dataMap.get("val"))) {
					Integer remainSum = new Integer(dataMap.get("val")) - myResNum;
					if (new Integer(everySum) > remainSum) {
						for (int j = 0; j < remainSum; j++) {
							idsList.add(totalResList.get(j).getResCustId());
							delResList.add(totalResList.get(j));
						}
						totalResList.removeAll(delResList);
						mark++;
						resultList.add(taskExecutor.submit(new ResOptThread(reqId, idsList, staffAcc, orgId, account, cachedService, comResourceService, 1,
								CachedNames.ORG_ASSIGN, logStaffInfoService,name,null,taskId,map,batchId)));
						count += idsList.size();
					} else { // 够分配一个人，不够分配这个人则停止当天操作，继续循环
						for (int j = 0; j < new Integer(everySum); j++) {
							idsList.add(totalResList.get(j).getResCustId());
							delResList.add(totalResList.get(j));
						}
						totalResList.removeAll(delResList);
						mark++;
						resultList.add(taskExecutor.submit(new ResOptThread(reqId, idsList, staffAcc, orgId, account, cachedService, comResourceService, 1,
								CachedNames.ORG_ASSIGN, logStaffInfoService,name,null,taskId,map,batchId)));
						count += idsList.size();
					}
				} else {
					logger.debug("reqId" + reqId + "。个人拥有资源数量超过设定上限！");
					continue;
				}
			} else if ("1".equals(result)) {
				for (int j = 0; j < new Integer(everySum); j++) {
					idsList.add(totalResList.get(j).getResCustId());
					delResList.add(totalResList.get(j));
				}
				totalResList.removeAll(delResList);
				mark++;
				resultList.add(taskExecutor.submit(new ResOptThread(reqId, idsList, staffAcc, orgId, account, cachedService, comResourceService, 1,
						CachedNames.ORG_ASSIGN, logStaffInfoService,name,null,taskId,map,batchId)));
				count += idsList.size();
			}
		}
		if (mark > 0) {
			OptResultThread optThread = new OptResultThread(reqId, account, resultList, orgId, 1, cachedService, CachedNames.ORG_ASSIGN, logStaffInfoService,
					comResourceService,logUserOperateService,count);
			optThread.setAccs(staffAccs);
			optThread.start();
		} else {
			logStaffInfoService.saveLogInfo(orgId, account, "公司资源分配未处理", "mark=" + mark, reqId);
			cachedService.delOptMap(reqId, orgId, CachedNames.ORG_ASSIGN, account);
		}
	}

	// 是否分配
	public String isAssign(Map<String, String> map, int resNum) {
		if ("1".equals(map.get("followIsOpen")) && "1".equals(map.get("limitIsOpen"))) {
			return "0";
		} else {
			return "1";
		}
	}
}
