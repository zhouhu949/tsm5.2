package com.qftx.tsm.log.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.qftx.common.filter.AppContextUtil;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.tsm.log.bean.LogCustInfoBean;
import com.qftx.tsm.log.service.LogCustInfoService;


 /** 
 * <b>日志入库线程</b><br /><br />
 * 系统初始化开启一个守护线程，每隔3S从队列取出日志信息入库
 * @author: lixing
 * @since: 2016年12月29日  上午11:22:45
 * @version 1.0
 */
public class LogDataWriteThread implements Runnable {
	private Log log = LogFactory.getLog(LogDataWriteThread.class);
	private LogCustInfoService logCustInfoService = (LogCustInfoService)AppContextUtil.getBean("logCustInfoService");
	private long waitTime = 3000;
	private boolean flag = true;
	private String write_log_to_db ="";// ConfigInfoUtils.getStringValue("write_log_to_db");
	
	
	@Override
	public void run() {
		List<LogCustInfoBean> beans = new ArrayList<LogCustInfoBean>();
		LogCustInfoBean bean;
		while (flag) {
			try {
				while((bean = LogCustInfoService.LOG_QUEUE.poll()) != null){
					beans.add(bean);
					if(beans.size() == 200){
						write(beans,false);
					}
				}
				write(beans,true);
			} catch (Exception e) {
				log.error("☆☆☆☆☆☆☆资源日志入库线程异常，exception:"+e.getMessage()+"☆☆☆☆☆☆☆☆☆",e);
				if(beans.size() > 0){
					LogCustInfoService.LOG_QUEUE.addAll(beans);
					beans.clear();
				}
				sleep();
			}
		}
	}
	
	public void write(List<LogCustInfoBean> beans,boolean sleep){
		if(beans.size() > 0){
//			if(write_log_to_db.equals("1")){
				//入库
				Map<String, List<LogCustInfoBean>> map = new HashMap<String, List<LogCustInfoBean>>();
				for(LogCustInfoBean bean : beans){
					if(map.containsKey(bean.getOrgId())){
						map.get(bean.getOrgId()).add(bean);
					}else{
						List<LogCustInfoBean> datas = new ArrayList<LogCustInfoBean>();
						datas.add(bean);
						map.put(bean.getOrgId(), datas);
					}
				}
				//按单位插入日志
				for(String key : map.keySet()){
					if(write_log_to_db.equals("1")){
						writeToMongoDb(map.get(key));
						logCustInfoService.insertBatch(map.get(key));
					}else if(write_log_to_db.equals("2")){
						writeToMongoDb(map.get(key));
					}else{
						logCustInfoService.insertBatch(map.get(key));
					}
				}
//			}
			beans.clear();
		}
		if(sleep){
			sleep();
		}
	}
	
	public void writeToMongoDb(List<LogCustInfoBean> beans){
		try {
		} catch (Exception e) {
			log.error("@@@@@@操作日志写入mongodb失败！",e);
		}
	}
	
	public void sleep(){
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			log.error(e);
		}
	}
	
	public void setFlag(boolean flag){
		this.flag = flag;
	}
}
