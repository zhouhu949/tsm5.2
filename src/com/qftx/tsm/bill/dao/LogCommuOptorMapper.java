package com.qftx.tsm.bill.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.bill.bean.LogCommuOptorBean;
import com.qftx.tsm.bill.dto.LogCommuOptorDto;

import java.util.List;


 /** 
 * 通信包操作日志接口
 * @author: lixing
 * @since: 2016年2月29日  下午7:50:47
 * @history:
 */
public interface LogCommuOptorMapper extends BaseDao<LogCommuOptorBean>{
	List<LogCommuOptorDto> findLogCommuOptorListPage(LogCommuOptorDto optorDto);
}