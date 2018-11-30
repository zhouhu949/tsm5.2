package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.cust.dto.TsmCustGuideDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface TsmCustGuideMapper extends BaseDao<TsmCustGuide> {

	/** 查询资源 销售导航 */
	List<TsmCustGuideDto> findCustGuideByCustId(TsmCustGuideDto entity);

	/** 
	 * 根据custid，删除导航(在公海客户池转成资源时，原先的导航记录删除)
	 * @param id 
	 * @create  2014-4-9 下午04:50:43 wuwei
	 * @history  
	 */
	public void removeByCustId(Map<String, String> map);
	
	public void deleteCustGuids(@Param("orgId")String orgId,@Param("custIds")List<String> custIds);
	
	public void updateByCustId(TsmCustGuide guide);
}