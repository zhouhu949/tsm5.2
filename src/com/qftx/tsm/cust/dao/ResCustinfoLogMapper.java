package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.ResCustinfoLogBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ResCustinfoLogMapper extends BaseDao<ResCustinfoLogBean> {

	/**
	 * 根据主键和ORG_ID获取备注
	 * 
	 * @param orgId
	 * @param trclId
	 * @return
	 * @create 2015年12月10日 下午6:51:03 lixing
	 * @history
	 */
	ResCustinfoLogBean getByPrimaryKeyAndOrgId(@Param("orgId") String orgId, @Param("trclId") String trclId);

	void deleteCustLogs(@Param("orgId") String orgId, @Param("custIds") List<String> custIds);

	Map<String, String> findTopConcat(Map<String, String> map);

	int findTotalLog(Map<String, String> map);

	List<ResCustinfoLogBean> findRemarkList(@Param("orgId") String orgId, @Param("id") String id);
}
