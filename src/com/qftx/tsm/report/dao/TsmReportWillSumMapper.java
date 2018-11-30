package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.TsmReportWillSumBean;

import java.util.List;


public interface TsmReportWillSumMapper extends BaseDao<TsmReportWillSumBean>{

   
   /***********************当日新增意向数对应的资源信息5.1***********************************************************/
   public List<TsmReportWillSumBean>  findNewWillSumByAccountsListPage(TsmReportWillSumBean bean);
   /**********************当日销售进程数对应的资源信息5.1***********************************************************/
   public List<TsmReportWillSumBean>  findNewWillSumByAccount_new1ListPage(TsmReportWillSumBean bean);
   
   public List<TsmReportWillSumBean>  findNewWillSumByAccount_new2ListPage(TsmReportWillSumBean bean);
   
   //整合1和2，union链接，分页
   public List<TsmReportWillSumBean>  findNewWillSumByAccount_new3ListPage(TsmReportWillSumBean bean);
   
   
   /***********************历史新增意向数对应的资源信息5.1***********************************************************/
   public List<TsmReportWillSumBean>  findOldNewWillSumByDateListPage(TsmReportWillSumBean bean);
   /**********************历史销售进程数对应的资源信息5.1***********************************************************/
   public List<TsmReportWillSumBean>  findNewWillSumByDate_new1ListPage(TsmReportWillSumBean bean);
   
   public List<TsmReportWillSumBean>  findNewWillSumByDate_new2ListPage(TsmReportWillSumBean bean);
   
   public List<TsmReportWillSumBean>  findNewWillSumByDate_new3ListPage(TsmReportWillSumBean bean);
   
}
