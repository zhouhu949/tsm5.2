package com.qftx.tsm.log.service;

import com.alibaba.druid.util.StringUtils;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.api.LogTableStoreApi;
import com.qftx.log.query.bean.LogResOperateBatchQueryDto;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.log.tablestore.bean.LogDto;
import com.qftx.log.tablestore.bean.TsGetRangePage;
import com.qftx.log.tablestore.bean.TsGetRangePageResp;
import com.qftx.tsm.cust.dto.LogBatchInfoDto;
import com.qftx.tsm.log.bean.LogBatchInfoBean;
import com.qftx.tsm.log.dao.LogBatchInfoMapper;
import com.qftx.tsm.option.bean.DataDictionaryBean;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogBatchInfoService {
	private static final Logger logger = Logger.getLogger(LogBatchInfoService.class);
	@Autowired
	private LogBatchInfoMapper logBatchInfoMapper;
	@Autowired
	private CachedService cachedService;
	private static String endPoint;
	private static String instanceName;
	private static LogTableStoreApi logTableStoreApi = null;
	
	public static LogTableStoreApi getLogTableStoreApi(){
		if(logTableStoreApi==null){
			endPoint=ConfigInfoUtils.getStringValue("tablestore_end_point");
			instanceName=ConfigInfoUtils.getStringValue("tablestore_instance_name");
		    logTableStoreApi= new LogTableStoreApi(endPoint, instanceName);

		}
	    return logTableStoreApi;
	}
	
	/** 
	 * 插入批量操作日志
	 * @param orgId 单位ID
	 * @param userAcc 操作人
	 * @param em 操作类型枚举
	 * @param ids 操作资源ID集合
	 * @create  2016年4月28日 上午11:07:19 lixing
	 * @history  
	 */
	
	public void saveLogInfo(String orgId,String operateAcc,String operateName, OperateEnum em,List<String> ids,String ownerAcc,String batchId,Map<String,Object> custIdContextMap){
		/*LogBatchInfoBean bean = new LogBatchInfoBean();
		String idStr = JSONArray.fromObject(ids).toString();
		bean.setId(SysBaseModelUtil.getModelId());
		bean.setType(em.getCode());
		bean.setContext(em.getDesc());
		bean.setOperateAcc(operateAcc);
		bean.setOperateName(operateName);
		bean.setOrgId(orgId);
		bean.setData(idStr);
		bean.setOperateDate(new Date());
		bean.setOwnerAcc(ownerAcc);
		bean.setSize(ids.size()+"");
		logBatchInfoMapper.insert(bean);*/
		ShiroUser user = ShiroUtil.getShiroUser();
		LogBean logBean=new LogBean();
		logBean.setOrgId(orgId);
		logBean.setUserAcc(operateAcc);
	    logBean.setUserName(operateName);
	    logBean.setResOperateId(em.getCode());
	    logBean.setResOperateName(em.getDesc());
	    logBean.setOperateNum(ids.size());
	    logBean.setOwnerAcc(ownerAcc);
	    saveLogTableStore(logBean,custIdContextMap);
	}
	
	public void saveLog(String orgId,String userAcc,String userName, OperateEnum em,
			String moduleId ,String moduleName,String operateId ,String operateName,String content,String systemOperateId,
			List<String> ids,String ownerAcc,String batchId,Map<String,Object> custIdContextMap){
		/*LogBatchInfoBean bean = new LogBatchInfoBean();
		String idStr = JSONArray.fromObject(ids).toString();
		bean.setId(SysBaseModelUtil.getModelId());
		bean.setType(em.getCode());
		bean.setContext(em.getDesc());
		bean.setOperateAcc(operateAcc);
		bean.setOperateName(operateName);
		bean.setOrgId(orgId);
		bean.setData(idStr);
		bean.setOperateDate(new Date());
		bean.setOwnerAcc(ownerAcc);
		bean.setSize(ids.size()+"");
		logBatchInfoMapper.insert(bean);*/
		ShiroUser user = ShiroUtil.getShiroUser();
		LogBean logBean=new LogBean();
		logBean.setOrgId(orgId);
		logBean.setUserAcc(userAcc);
	    logBean.setUserName(userName);
	    logBean.setResOperateId(em.getCode());
	    logBean.setResOperateName(em.getDesc());
	    logBean.setOperateNum(ids.size());
	    logBean.setOwnerAcc(ownerAcc);
	    logBean.setModuleId(moduleId);
	    logBean.setModuleName(moduleName);
	    logBean.setOperateId(operateId);
	    logBean.setOperateName(operateName);
	    logBean.setContent(content);
	    logBean.setData(systemOperateId);
	    saveLogTableStore(logBean,custIdContextMap);
	    
	}
	
	
	
	
	public void saveLogTableStore(LogBean logBean,Map<String,Object> custIdContextMap){
		try {
	       LogTableStoreApi logTableStoreApi = getLogTableStoreApi();
	       LogDto logDto = new LogDto();
	       logDto.setLogBean(logBean);
	       logDto.setCustIdContextMap(custIdContextMap);
	       logTableStoreApi.saveLog(logDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}


	public List<LogBatchInfoDto> getLogResOperateListPage(
			LogBatchInfoDto logBatchInfoDto) {
		return logBatchInfoMapper.findLogResOperateListPage(logBatchInfoDto);
	}

	public TsGetRangePageResp logBatchInfoService(LogResOperateBatchQueryDto dto, TsGetRangePage pages, int pageSize,int pageNo) {
		TsGetRangePageResp rep=new TsGetRangePageResp();
		try {
	       LogTableStoreApi logTableStoreApi = getLogTableStoreApi();
	       if(pageNo==0||pages.getOperation()==null){//首次查询
	    	   List<DataDictionaryBean> dlist0 = cachedService.getDirList(AppConstant.DATA_40019, dto.getOrgId());//分页设置
				if(!dlist0.isEmpty() && dlist0.get(0) != null){
			         String   fy = dlist0.get(0).getDictionaryValue();
			         if(fy!=null&&!"".endsWith(fy)){
			        	 dto.setPageSize(Integer.valueOf(fy)); 
			         }
			    }
	    	    rep=logTableStoreApi.searchLogResOperateBatchByPage(dto);
	       }else{
                if(pages.getOperation()==1||pages.getOperation()==2||pages.getOperation()==3||pages.getOperation()==4){
                	rep= logTableStoreApi.logChangePage(pages);
                }else{
                	if(pageSize!=0&&pageNo!=0){
	    			    dto.setPageSize(pageSize);
	    			    dto.setPageNo(pageNo);
                	}
    			    rep=logTableStoreApi.searchLogResOperateBatchByPage(dto);  	
                }
	       }


		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
        return rep;
	}

	public void savePublicLog(List<String> ids){
		Map<String, Object> logMap = new HashMap<>();
		ShiroUser user = ShiroUtil.getShiroUser();
		for (String custId : ids) {
			logMap.put(custId, "");
		}
		LogBean logBean = new LogBean(user.getOrgId(), user.getAccount(),
				user.getName(), OperateEnum.LOG_PUBLIC_GIVE.getCode(), OperateEnum.LOG_PUBLIC_GIVE.getDesc(),
				ids.size(), SysBaseModelUtil.getModelId());

		logBean.setContent(ids.size() + "条");
		logBean.setOperateId(AppConstant.Operate_id7);
		logBean.setOperateName(AppConstant.Operate_Name7);

		logBean.setModuleId(AppConstant.Module_id106);
		logBean.setModuleName(AppConstant.Module_Name106);

		saveLogTableStore(logBean,logMap);

	}
}
