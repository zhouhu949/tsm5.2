package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.LayoutCustProductBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LayoutCustProductMapper extends BaseDao<LayoutCustProductBean> {
	
	void deleteByType(@Param("orgId")String orgId,@Param("type")Integer type);
	
	List<LayoutCustProductBean> findLayoutIntProductReportData(String orgId);
	
	List<LayoutCustProductBean> findLayoutSignProductReportData(String orgId);
	
	List<LayoutCustProductBean> findCustProductLayoutForGroup(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds);
	
	List<LayoutCustProductBean> findCustProductLayoutForPGroup(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds);
	
	List<LayoutCustProductBean> findCustProductLayoutForMember(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds);
	
	List<LayoutCustProductBean> findCustProductLayoutChart(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds,@Param("account")String account);
}
