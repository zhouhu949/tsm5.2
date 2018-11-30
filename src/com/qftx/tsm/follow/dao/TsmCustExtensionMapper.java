package com.qftx.tsm.follow.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.follow.bean.TsmCustExtension;
import com.qftx.tsm.follow.dto.TsmCustExtensionDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 客户跟进延期表
 */
public interface TsmCustExtensionMapper extends BaseDao<TsmCustExtension>{
	
	/**
	 * 延期审核列表
	 */
	List<TsmCustExtensionDto> findDeferredAuditListPage(TsmCustExtensionDto entity);
	
	
	/**
	 * 批量更新审核状态
	 * @param statusExtended   状态
	 * @param updateAcc        更新人
	 * @param updateName       更新名称
	 * @param ids              批量编号
	 */
	void updateBatchStatus(@Param("orgId")String orgId,@Param("statusExtended")String statusExtended,@Param("updateAcc")String updateAcc,
			@Param("updateName")String updateName,@Param("ids")List<String> ids); 
}

  
