package com.qftx.tsm.sys.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.ServiceVisit;
import com.qftx.tsm.sys.dto.ServiceVisitDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 服务记录数据库访问
 */
public interface ServiceVisitMapper extends BaseDao<ServiceVisit> {
	
	/**
	 * 查询回访记录
	 */
	List<ServiceVisitDto> findTeamTodayVisitListPage(ServiceVisitDto serviceVisitDto);

	/**
	 * 根据客户id查询所有客户回访记录
	 */
	List<ServiceVisitDto> getAllServiceInfo(@Param("custId")String custId);

	public List<ServiceVisit> findCustVisistListPage(ServiceVisitDto serviceVisitDto);
	
}