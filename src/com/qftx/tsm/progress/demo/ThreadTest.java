package com.qftx.tsm.progress.demo;

import com.qftx.tsm.progress.dto.ProgressBarDTO;
import com.qftx.tsm.progress.service.ProgressBarService;

import java.util.List;

public class ThreadTest extends Thread {
	private int threadId;
	private List<ProcessObject> list ;
	private ProgressBarService progressBarService;
	private ProgressBarDTO dto;
	
	public ProgressBarService getProgressBarService() {
		return progressBarService;
	}

	public void setProgressBarService(ProgressBarService progressBarService) {
		this.progressBarService = progressBarService;
	}

	public ProgressBarDTO getDto() {
		return dto;
	}

	public void setDto(ProgressBarDTO dto) {
		this.dto = dto;
	}

	public List<ProcessObject> getList() {
		return list;
	}

	public void setList(List<ProcessObject> list) {
		this.list = list;
	}

	public int getThreadId() {
		return threadId;
	}

	public void setThreadId(int threadId) {
		this.threadId = threadId;
	}

	@Override
	public void run(){
		for (ProcessObject obj : list) {
			process(obj);
		}
	}
	
	public void sleep(int millis){
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void process(ProcessObject obj){
		//System.out.println(threadId+"process "+obj.getName());
		progressBarService.updateProgress(dto.getOrgId(), dto.getAccount(), dto.getId(), dto.getType(), 1);
		sleep(1000);
	}
}
