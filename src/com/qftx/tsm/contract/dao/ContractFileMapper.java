package com.qftx.tsm.contract.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.contract.bean.ContractFileBean;
import com.qftx.tsm.contract.dto.ContractFileDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContractFileMapper extends BaseDao<ContractFileBean> {
	List<ContractFileDto> findContractFiles(@Param("caId")String caId,@Param("orgId")String orgId);
}
