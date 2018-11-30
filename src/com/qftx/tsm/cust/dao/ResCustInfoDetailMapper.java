package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.ResCustInfoDetailBean;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.imp.dto.ImportRepeatDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ResCustInfoDetailMapper extends BaseDao<ResCustInfoDetailBean> {

	/**
	 * 取得客户联系人列表
	 * 
	 * @param orgId
	 * @param resCustId
	 * @return
	 * @create 2015年12月3日 下午5:04:24 lixing
	 * @history
	 */
	List<ResCustInfoDetailBean> findCustsInfoDetails(@Param("orgId") String orgId, @Param("custId") String resCustId);

	ResCustInfoDetailBean getByPrimaryKeyAndOrgId(@Param("orgId") String orgId, @Param("tscidId") String tscidId);

	void deleteDefault(ResCustInfoDetailBean detailBean);

	List<ResCustInfoDetailBean> findCustDetailListPage(ResCustDto resCustDto);

	/** 号码去重 */
	public Integer getRepeatByPhone(ImportRepeatDto entity);

	String findCustDetailId(@Param("orgId") String orgId, @Param("phone") String phone, @Param("custId") String custId);

	Map<String, String> findCustDetailName(@Param("orgId") String orgId, @Param("phone") String phone, @Param("custId") String custId);
	
	public void updateCallNum(ResCustInfoDetailBean bean);
	
	public void cleanCallNum(@Param("orgId")String orgId,@Param("custId")String custId);
	
	public List<String> findLinkmanIds(@Param("orgId") String orgId, @Param("queryText") String queryText);
	
	public List<String> findLinkmanIdsByPhone(@Param("orgId") String orgId, @Param("queryText") String queryText);

	public void removeComResLinkBatch(Map<String, Object> map);
	
	public List<ResCustInfoDetailBean> findByIds(@Param("ids")List<String> ids,@Param("orgId") String orgId);
}

