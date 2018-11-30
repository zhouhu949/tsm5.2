package com.qftx.tsm.progress.demo;

import com.qftx.tsm.progress.dto.ProgressBarDTO;
import com.qftx.tsm.progress.service.ProgressBarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Main {
	int maxThread =5;
	private static int percent = 0;
	
	@Autowired
	ProgressBarService progressBarService;
	
	public static void main(String[] args) {
		Main main = new Main();
		main.run(new ProgressBarDTO());
	}

	public int run(ProgressBarDTO dto){
		int total =100;
		progressBarService.insertProgress(dto.getOrgId(), dto.getAccount(), dto.getType(), dto.getId(), total);
		
		List<ProcessObject> list = new ArrayList<ProcessObject>();
		for (int i=0;i<100;i++) {
			list.add(new ProcessObject(""+i));
		}
		
		int objNum = list.size()/maxThread;
		for(int i =0;i<maxThread;i++){
			List<ProcessObject> temp = new ArrayList<>();
			int start = i*objNum;
			int end = (i+1)*objNum;
			
			temp.addAll(list.subList(start, end));
		
			ThreadTest threadTest = new ThreadTest();
			threadTest.setDto(dto);
			threadTest.setProgressBarService(progressBarService);
			threadTest.setThreadId(i);
			threadTest.setList(temp);
			threadTest.start();
		}
		return total;
	}
}
