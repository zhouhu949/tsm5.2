package com.qftx.tsm.log.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.log.bean.LogContactDayDataBean;
import com.qftx.tsm.log.dto.LogContactDayDataDto;

import java.util.List;

public interface LogContactDayDataMapper extends BaseDao<LogContactDayDataBean> {
	
	List<LogContactDayDataDto> findLogDetailInfoListPage(LogContactDayDataDto dto);
	
}
