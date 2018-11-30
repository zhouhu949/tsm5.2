package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.TsmNewWillCountBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TsmNewWillCountMapper extends BaseDao<TsmNewWillCountBean>{
	
   public String findTsmNewWillCountData(TsmNewWillCountBean tsmNewWillCountBean);
   
   public TsmNewWillCountBean findTsmNewWillCountData_new(TsmNewWillCountBean tsmNewWillCountBean);
   
   public String findTsmNewWillCountDataBydate(TsmNewWillCountBean tsmNewWillCountBean);
   
   public void updateNum(TsmNewWillCountBean tsmNewWillCountBean);
   
   public void insertTsmNewWillCount (TsmNewWillCountBean tsmNewWillCountBean);
   
   public List<TsmNewWillCountBean> getUsersCount(@Param("orgId")String orgId,@Param("accounts")List<String> accounts);

}
