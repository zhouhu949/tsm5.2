package com.qftx.tsm.sys.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.TsmCustReview;
import com.qftx.tsm.sys.dto.TsmCustReviewDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TsmCustReviewMapper extends BaseDao<TsmCustReview>{
	
	List<TsmCustReviewDto> findCustCardListPage(TsmCustReviewDto entity);
	
	/**
	 * 根据录音ID查询出该录音示范实体
	 * @param retaId
	 * @return 
	 * @create  2013-11-6 下午06:09:43 徐承恩
	 */
	TsmCustReview findReview(@Param("orgId")String orgId,@Param("retaId")String retaId);
	
	/**
	 * 录音范例分页查询
	 * @param tsmCustReviewDto
	 * @return 
	 * @create  2013-11-7 下午12:00:02 徐承恩
	 */
	List<TsmCustReviewDto> findRecordReviewListPage(TsmCustReviewDto tsmCustReviewDto);

	
	/** 
	 * 单个资源客户的点评记录
	 * @param tsmCustReview
	 * @return 
	 * @create  2015-3-9 上午10:46:57 wangchao
	 * @history  
	 */
	List<TsmCustReview> getCustReview(TsmCustReview tsmCustReview);

	List<TsmCustReview> findCustReviewListPage(TsmCustReview tsmCustReview);
	
	void deleteBatchBy(Map<String,Object>map);
}