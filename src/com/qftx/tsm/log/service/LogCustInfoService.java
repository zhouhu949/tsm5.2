package com.qftx.tsm.log.service;

import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.log.api.LogTableStoreApi;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.log.tablestore.bean.LogDto;
import com.qftx.tsm.log.bean.LogCustInfoBean;
import com.qftx.tsm.log.dao.LogCustInfoMapper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class LogCustInfoService {
	protected static Log logger = LogFactory.getLog(LogCustInfoService.class);
	/**资源日志队列**/
	public static ConcurrentLinkedQueue<LogCustInfoBean> LOG_QUEUE = new ConcurrentLinkedQueue<LogCustInfoBean>();
	public static final String log_save_url = ConfigInfoUtils.getStringValue("log_save_url");
	
	@Autowired
	private LogCustInfoMapper logCustInfoMapper;
	
	public List<LogCustInfoBean> getListPage(LogCustInfoBean bean){
		return logCustInfoMapper.findCustLogInfosListPage(bean);
	}
	
	/** 
	 * 操作日志插入队列
	 * @param orgId 单位ID
	 * @param account 操作人帐号
	 * @param custId 资源ID
	 * @param context 说明，根据操作类型组成特定文本
	 * @param operateEnum 操作类型
	 * @create  2016年12月29日 上午11:15:55 lixing
	 * @history  
	 */
	public void addLog(String orgId,String account,String custId,String context,OperateEnum operateEnum){
		LogCustInfoBean bean = new LogCustInfoBean();
		bean.setId(SysBaseModelUtil.getModelId());
		bean.setOrgId(orgId);
		bean.setCustId(custId);
		bean.setUserId(account);
		bean.setName(operateEnum.getDesc());
		bean.setType(operateEnum.getCode());
		bean.setContext(context);
		bean.setInputtime(new Date());
		bean.setIsDel(0);
		LOG_QUEUE.offer(bean);
	}

   
	/** 
	 * 操作日志批量入库
	 * @param beans 
	 * @create  2016年12月29日 上午11:19:09 lixing
	 * @history  
	 */
	public void insertBatch(List<LogCustInfoBean> beans){
	   logCustInfoMapper.insertBatch(beans);
	}
	
	
	/** 
	 * 新增日志（tablestore存储）
	 * @param log 
	 * @param map 
	 * @create  2017年11月21日 下午3:04:39 lixing
	 * @history  
	 */
	public void addTableStoreLog(LogBean log,Map<String, Object> map){
		LogDto logDto = new LogDto();
		logDto.setLogBean(log);
		if(map != null) logDto.setCustIdContextMap(map);
		try {
			 //LogTableStoreApi logTableStoreApi = new LogTableStoreApi(endPoint, accessId, accessKey, instanceName);
			boolean re = LogUserOperateService.getLogTableStoreApi().saveLog(logDto);
			if(!re){
				throw new SysRunException("tablestore写入失败");
			}
		} catch (Exception e) {
			logger.error("日志存储远程调用失败！",e);
			//操作...
		}
	}
}
