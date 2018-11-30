package com.qftx.tsm.credit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.cust.dto.LogBatchInfoDto;
import com.qftx.tsm.log.bean.LogBatchInfoBean;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.sys.bean.CustSearchSet;

public class CustFieldQupaiTest  extends BaseUtest{
	@Autowired
	private CachedService  cachedService;
	@Autowired
	private TsmMessageService  tsmMessageService;
//	@Test
//	public void queryLogResOperateList(){
//		List<CustSearchSet> list1=cachedService.getQupaiCustSearchSetList("rd");
//		cachedService.getQupaiFiledSet("rd");
//		cachedService.getQupaiFiledSets("rd");
//		cachedService.getQupaiMultiFiledSet("rd");
//	}

	@Test
	public void queryLogResOperateList(){
		List<String> accList=new ArrayList<String>();
		accList.add("rd001");
		accList.add("rd002");
		accList.add("rd003");
		tsmMessageService.createQupaiMessage("111111", "rd", "rd001", accList, "测试审核信息");
	}

}
