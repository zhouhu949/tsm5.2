package com.qftx.tsm.report.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.report.bean.LayoutCustStateBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LayoutCustStateMapper extends BaseDao<LayoutCustStateBean> {
	
	List<LayoutCustStateBean> findReportData(String orgId);

	List<LayoutCustStateBean> findCustStateLayoutForGroup(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds);

	LayoutCustStateBean findCustStateLayoutForPGroup(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds);
	
	List<LayoutCustStateBean> findCustStateLayoutForMember(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds);
	
	LayoutCustStateBean findCustStateLayoutChart(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds,@Param("account")String account);
}
