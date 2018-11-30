package com.qftx.tsm.report;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.report.bean.ResourceReportBean;
import com.qftx.tsm.report.service.ResourceReportService;

public class ResourceReportTest extends BaseUtest {
	@Autowired
	private ResourceReportService resourceReportService;

	@Test
	public void getSilentCustDetail(){
		//资源分析入库
//		ResourceReportBean resrepbean=new ResourceReportBean();
//		resrepbean.setOrgId("fhtx");
//		resrepbean.setResCustId("cdcc03c116d54619a604953276a74222");
//		resrepbean.setCalled(1);
//		resrepbean.setCallLength(2);
//		resourceReportService.intsertOrUpdate(resrepbean);
	}
}
