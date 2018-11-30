package com.qftx.tsm.sys.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.tsm.sys.bean.SysDataHelpBean;
import com.qftx.tsm.sys.dao.SysDataHelpMapper;

@Service
public class SysDataHelpService{

	Logger logger = Logger.getLogger(SysDataHelpService.class);
	
	@Autowired
	private SysDataHelpMapper sysDataHelpMapper;

	public List<SysDataHelpBean> findList(){
		return sysDataHelpMapper.getList();
	}
	

}
