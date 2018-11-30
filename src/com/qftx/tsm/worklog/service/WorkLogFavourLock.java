package com.qftx.tsm.worklog.service;

import java.util.concurrent.ConcurrentHashMap;

public class WorkLogFavourLock {
	private static WorkLogFavourLock instance;
	private ConcurrentHashMap<String,String> lockMap;
	
	public WorkLogFavourLock() {
	}
	
	public static WorkLogFavourLock getInstance(){
		if(instance==null){
			synchronized(WorkLogFavourLock.class){
				if(instance==null){
					instance= new WorkLogFavourLock();
					instance.lockMap = new ConcurrentHashMap<String, String>();
				}
			}
		}
		return instance;
	}
	
	public String lock(String lockKey){
		if(!instance.lockMap.contains(lockKey)){
			instance.lockMap.put(lockKey, lockKey);
		}
		return instance.lockMap.get(lockKey);
	}
}
