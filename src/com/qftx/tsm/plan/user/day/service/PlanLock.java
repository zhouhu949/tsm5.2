package com.qftx.tsm.plan.user.day.service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class PlanLock {
	private static PlanLock planLockLock;
	private ConcurrentMap<String,String> lockMap;
	
	private PlanLock() {
	}
	
	public static PlanLock getInstance() {
		if(planLockLock==null){
			synchronized (PlanLock.class) {
				if (planLockLock == null) {
					planLockLock = new PlanLock();
					planLockLock.lockMap= new ConcurrentHashMap<String, String>();
                }
			}
		}
		return planLockLock;
	}

	public String getLock(String orgId){
		if(!lockMap.containsKey(orgId)){
			lockMap.put(orgId, orgId);
		}
		return lockMap.get(orgId);
	}
}
