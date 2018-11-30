package com.qftx.tsm.credit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qftx.tsm.credit.bean.TsmLeadReviewInfo;
import com.qftx.tsm.credit.dto.TsmLoanReviewInfoDto;

public interface TsmLeadReviewInfoMapper {
	int deleteByPrimaryKey(String reviewId);

	int insert(TsmLeadReviewInfo record);

	int insertSelective(TsmLeadReviewInfo record);

	TsmLeadReviewInfo selectByPrimaryKey(String reviewId);

	int updateByPrimaryKeySelective(TsmLeadReviewInfo record);

	int updateByPrimaryKey(TsmLeadReviewInfo record);

	List<String> findLeadIdList(TsmLoanReviewInfoDto tsmLoanReviewInfoDto);

	List<TsmLeadReviewInfo> findReviewsByLeadId(@Param("orgId") String orgId, @Param("leadId") String leadId);

	void deleteReviewInfo(@Param("orgId") String orgId, @Param("leadIds") List<String> list);

	TsmLeadReviewInfo getAleadyInfo(@Param("leadId") String leadId, @Param("orgId") String orgId, @Param("reviewNode") int node);

	void deleteReviewInfoOne(@Param("orgId") String orgId,@Param("leadId") String leadId);
}