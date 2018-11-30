package com.qftx.tsm.report;



import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;






import com.qftx.common.BaseUtest;
import com.qftx.tsm.report.bean.TsmReportWillSumBean;
import com.qftx.tsm.report.bean.TsmfindWillSumBean;
import com.qftx.tsm.report.service.NewWillService;


public class NewWillServiceTest extends BaseUtest{
	@Autowired
	private NewWillService newWillService;
	
    //今日新增数对应的资源信息
	@Test
	public void getToDayNewWill() {
		System.out.println("开始执行======");
		TsmReportWillSumBean bean=new TsmReportWillSumBean();
		bean.setOrgId("fhtx");
		List<String> list=new ArrayList<String>();
		list.add("fhtx001");
		list.add("fhtx002");
		bean.setUserAccounts(list);
		List<TsmReportWillSumBean> list2=newWillService.findNewWillSumByAccountsListPage(bean);
		System.out.println("今日新增返回数据大小："+list2.size());
		if(list2!=null&&list2.size()>0){
			for(TsmReportWillSumBean beans:list2){
				System.out.println(beans.getCustName()+"___"+beans.getMainLinkman()+"___"+beans.getToDateInitType()+"__"+beans.getCurrProcessName()+"__"+beans.getCustId());
			}
		}
	}
	
	//销售进程数对应的资源信息
	@Test
	public void getLossSortList() {
		System.out.println("开始执行======");
		TsmReportWillSumBean bean=new TsmReportWillSumBean();
		bean.setOrgId("fhtx");
		List<String> list=new ArrayList<String>();
		list.add("fhtx001");
		list.add("fhtx002");
		bean.setUserAccounts(list);
		List<TsmReportWillSumBean> list2=newWillService.findNewWillSumByOptionListPage(bean);
		System.out.println("销售进程返回数据大小："+list2.size());
		if(list2!=null&&list2.size()>0){
			for(TsmReportWillSumBean beans:list2){
				System.out.println(beans.getCustName()+"___"+beans.getMainLinkman()+"___"+beans.getToDateInitType()+"__"+beans.getCurrProcessName()+"__"+beans.getCustId());
			}
		}
	}

}
