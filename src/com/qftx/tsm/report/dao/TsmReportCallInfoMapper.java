package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.TsmReportCallInfoBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TsmReportCallInfoMapper extends BaseDao<TsmReportCallInfoBean> {
	List<TsmReportCallInfoBean> findSumByCondtion(TsmReportCallInfoBean entity);
	
	List<TsmReportCallInfoBean> findMonthSaleChance(TsmReportCallInfoBean entity);
	
	List<TsmReportCallInfoBean> findSumByDate(TsmReportCallInfoBean entity);
	
	List<TsmReportCallInfoBean> findSumByGroup(TsmReportCallInfoBean entity);
	
	void updateNum(TsmReportCallInfoBean entity);
}
