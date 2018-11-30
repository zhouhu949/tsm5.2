package com.qftx.tsm.contract.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.contract.bean.ContractBean;
import com.qftx.tsm.contract.dto.ContractDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ContractMapper extends BaseDao<ContractBean> {
	List<ContractDto> findUnitContractListPage(ContractDto dto);

	void deleteContract(@Param("orgId") String orgId, @Param("cancleRemark") String cancleRemark, @Param("id") String id);

	void deleteContractBatch(@Param("orgId") String orgId,@Param("cancleRemark") String cancleRemark,@Param("ids") List<String> ids);
	
	ContractDto findContractInfoByIdAndOrg(@Param("contractId") String contractId, @Param("orgId") String orgId);

	ContractBean findDateExpireHT(Map<String, String> map);
}
