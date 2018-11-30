package com.qftx.tsm.credit.scontrol;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.credit.service.CustFieldSetQupaiService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.service.OptionService;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.service.CustFieldSetService;
import com.qftx.tsm.sys.service.CustSearchSetService;



/**
 * 数据订正
 */
@Controller
@RequestMapping("/correct")
public class QupaiFieldDataAction {
    private static final Logger logger = Logger.getLogger(QupaiFieldDataAction.class.getName());

	@Autowired
	private CustFieldSetQupaiService  custFieldSetQupaiService;
    
    
    @RequestMapping("/field/{orgId}")
    public void field(@PathVariable String orgId) {
    	try{
        	logger.error("/correct/field/"+orgId);
        	if(StringUtils.isNotBlank(orgId)){
        		List<String> orgIds=new ArrayList<String>();
        		orgIds.add(orgId);
        		custFieldSetQupaiService.toData(orgIds);
        	}
    	}catch(Exception e){
    		logger.error("/correct/field/"+orgId,e);
    	}
    }
    
  
  
}
