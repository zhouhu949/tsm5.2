package com.qftx.tsm.bill.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.bill.bean.LogFdOptorBean;
import com.qftx.tsm.bill.dto.LogFdOptorDto;

import java.util.List;


 /** 
 * 蜂豆操作日志接口
 * @author: lixing
 * @since: 2016年2月29日  下午7:50:47
 * @history:
 */
public interface LogFdOptorMapper extends BaseDao<LogFdOptorBean>{
	List<LogFdOptorDto> findLogFdOptorListPage(LogFdOptorDto optorDto);
}