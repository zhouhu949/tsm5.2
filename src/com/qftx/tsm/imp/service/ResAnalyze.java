package com.qftx.tsm.imp.service;

import com.qftx.tsm.imp.bean.ImportResFileBean;
import com.qftx.tsm.sys.bean.SysFileBean;
import com.qftx.tsm.sys.service.SysFileService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class ResAnalyze {
	@Autowired 
	ImportResFileService importFileService;
	@Autowired
	SysFileService sysFileService;
	public void analyze(String orgId,String id){
		ImportResFileBean importFile = importFileService.queryById(orgId, id);
		
		if(importFile!=null){
			SysFileBean sysFileBean = sysFileService.getByPrimaryKey(importFile.getFileId(),orgId);
			String fileUrl = sysFileBean.getFileUrl();
			File file = new File(fileUrl);
			if(file.exists()){
				
				
			}
		}
	}
	
	public void a(){
		
	}
}
