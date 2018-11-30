package com.qftx.tsm.sys.service;

import com.qftx.tsm.sys.bean.ServiceVisit;
import com.qftx.tsm.sys.dao.ServiceVisitMapper;
import com.qftx.tsm.sys.dto.ServiceVisitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 服务回访服务实现类
 */
@Service
public class ServiceVisitService {

	@Autowired
	private ServiceVisitMapper serviceVisitMapper;

	public void create(ServiceVisit entity) {
		serviceVisitMapper.insert(entity);

	}

	public void createBatch(List<ServiceVisit> entitys) {
		serviceVisitMapper.insertBatch(entitys);
	}

	/**
	 * 查询回访记录
	 */
	public List<ServiceVisitDto> queryTeamTodayVisitListPage(ServiceVisitDto serviceVisitDto) {
		return serviceVisitMapper.findTeamTodayVisitListPage(serviceVisitDto);
	}

	/**
	 * 根据客户id查询所有客户回访记录
	 */
	public List<ServiceVisitDto> getAllServiceInfo(String custId) {
		return serviceVisitMapper.getAllServiceInfo(custId);
	}

	public List<ServiceVisit> queryCustVisitListPage(ServiceVisitDto serviceVisitDto) {
		return serviceVisitMapper.findCustVisistListPage(serviceVisitDto);
	}
}
