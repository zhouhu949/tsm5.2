package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.TsmReportWillBean;

import java.util.List;


public interface TsmReportWillMapper extends BaseDao<TsmReportWillBean>{
	
   public String findTsmNewWillCountData(TsmReportWillBean tsmReportWillBean);
//   
//   public String findTsmNewWillCountDataBydate(TsmReportWillBean tsmReportWillBean);
//   
   public void updateNum(TsmReportWillBean tsmReportWillBean);
   
   public void insertTsmReportNewWill (TsmReportWillBean tsmReportWillBean);
   
   public void updateOptionNum(TsmReportWillBean tsmReportWillBean);
   
   public String findTsmNewWillOptincount (TsmReportWillBean tsmReportWillBean);

   public List<TsmReportWillBean> findNewWillUserByGroupByDay (TsmReportWillBean tsmReportWillBean);   
   
   public List<TsmReportWillBean> findNewWilldateByUser (TsmReportWillBean tsmReportWillBean);
   
   public List<TsmReportWillBean>  findNewWilldateCount (TsmReportWillBean tsmReportWillBean);
   
   public List<String> findNewWillAllUser(TsmReportWillBean bean);
   
   public List<TsmReportWillBean>  findNewWillAllByAccount(TsmReportWillBean bean);
   
   public List<String> findNewWilldate (TsmReportWillBean tsmReportWillBean);
   
   public List<TsmReportWillBean>  findNewWillBydate(TsmReportWillBean bean);
   
   public List<TsmReportWillBean>  findNewWillByGroup(TsmReportWillBean bean);
   
   public List<TsmReportWillBean>  findNewWillUserByGroup(TsmReportWillBean bean);
   
   public List<TsmReportWillBean>  findNewWillUserByGroupAndUser(TsmReportWillBean bean);
   
   public List<TsmReportWillBean>  findAllUser (TsmReportWillBean bean);
   
   
   
   /***********************新增意向3.0***********************************************************/
   
   public List<TsmReportWillBean>  findNewWillAllByAccount_new(TsmReportWillBean bean);
   
   public List<TsmReportWillBean>  findNewWillAllByAccount_new_2(TsmReportWillBean bean);
   
   public List<TsmReportWillBean>  findNewWillAllByAccount_new_3(TsmReportWillBean bean);
   
   

}
