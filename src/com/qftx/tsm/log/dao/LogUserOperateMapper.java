package com.qftx.tsm.log.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.log.bean.LogUserOperateBean;
import com.qftx.tsm.log.dto.DtoLogUserOperateBean;

import java.util.List;

public interface LogUserOperateMapper extends BaseDao<LogUserOperateBean>  {
	
	public List<LogUserOperateBean> findlogUserOperateListPage(DtoLogUserOperateBean dtoLogUserOperateBean);
	
	public List<String> findlogUserOperateIds(DtoLogUserOperateBean dtoLogUserOperateBean);
	
	public int findlogUserOperateCount(DtoLogUserOperateBean dtoLogUserOperateBean);
	
	public LogUserOperateBean findlogUserOperateData(LogUserOperateBean LogUserOperateBean);
	
	public void insertlogUserOperate(LogUserOperateBean logUserOperateBean);

}
