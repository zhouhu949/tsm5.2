package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.LayoutCustOptionBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LayoutCustOptionMapper extends BaseDao<LayoutCustOptionBean> {
	
	void deleteByType(@Param("orgId")String orgId,@Param("type")Integer type);
	
	List<LayoutCustOptionBean> findLayoutSaleProcReportData(String orgId);
	
	List<LayoutCustOptionBean> findLayoutCustTypeReportData(String orgId);
	
	List<LayoutCustOptionBean> findCustOptionLayoutForGroup(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds,@Param("type")Integer type);
	
	List<LayoutCustOptionBean> findCustOptionLayoutForPGroup(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds,@Param("type")Integer type);
	
	List<LayoutCustOptionBean> findCustSaleProcLayoutForMember(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds,@Param("type")Integer type);
	
	List<LayoutCustOptionBean> findCustSaleProcLayoutChart(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds,@Param("type")Integer type,@Param("account")String account);
}
