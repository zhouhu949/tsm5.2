package com.qftx.tsm.log.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.api.LogTableStoreApi;
import com.qftx.log.query.bean.LogUserOperateQueryDto;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.log.tablestore.bean.LogDto;
import com.qftx.log.tablestore.bean.TsGetRangePage;
import com.qftx.log.tablestore.bean.TsGetRangePageResp;
import com.qftx.tsm.log.bean.LogUserOperateBean;
import com.qftx.tsm.log.bean.ModuleResourceBean;
import com.qftx.tsm.log.dao.LogUserOperateMapper;
import com.qftx.tsm.log.dao.ModuleResourceMapper;
import com.qftx.tsm.log.dto.DtoLogUserOperateBean;
import com.qftx.tsm.option.bean.DataDictionaryBean;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LogUserOperateService {
	private static final Logger logger = Logger.getLogger(LogUserOperateService.class);
    @Autowired
    private  LogUserOperateMapper logUserOperateMapper;
    @Autowired
    private  ModuleResourceMapper moduleResourceMapper;
    @Autowired
    private  CachedService cachedService;
    
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
	

    
    public void insertlogUserOperate(LogUserOperateBean logUserOperateBean){
    	logUserOperateMapper.insertlogUserOperate(logUserOperateBean);
    }
    
    public void insertlogUserOperateBeach(List<LogUserOperateBean> logUserOperateBeanList){
    	for(LogUserOperateBean logUserOperateBean :logUserOperateBeanList){
        	logUserOperateMapper.insertlogUserOperate(logUserOperateBean);;
    	}

    }
    public List<LogUserOperateBean> findlogUserOperateListPage(DtoLogUserOperateBean dtologUserOperateBean){
		return logUserOperateMapper.findlogUserOperateListPage(dtologUserOperateBean);
    	
    }	
    
    public List<String> findlogUserOperateIds(DtoLogUserOperateBean dtoLogUserOperateBean){
    	return logUserOperateMapper.findlogUserOperateIds(dtoLogUserOperateBean);
    }
    
	public int findlogUserOperateCount(DtoLogUserOperateBean dtoLogUserOperateBean){
		return logUserOperateMapper.findlogUserOperateCount(dtoLogUserOperateBean);
	}
    
    public LogUserOperateBean findlogUserOperateData(LogUserOperateBean LogUserOperateBean){
		return logUserOperateMapper.findlogUserOperateData(LogUserOperateBean);
    	
    }
    
    public List<ModuleResourceBean> findModuleReslist (){
		return moduleResourceMapper.findModuleReslist();
    }
    
    public List<ModuleResourceBean> findModuleReslistByPid (String pid){
		return moduleResourceMapper.findModuleReslistByPid(pid);
    }
	public List<ModuleResourceBean> findModuleRes (Map<String,String> map){
		return moduleResourceMapper.findModuleRes(map);
	}
	
	/** 
	 * 插入操作日志
	 * 	orgId        //机构id
	 *	userAccount  //用户账号
	 *	userName     //用户名
	 *	moduleId     //模块id，取值来源于：AppConstant中AppConstant.Module_id，根据自己模块选择对应的id
	 *	moduleName   //模块名称，取值来源于：AppConstant中AppConstant.Module_Name，根据自己模块选择对应的值
	 *	operateId    //功能、操作id,取值来源于：AppConstant中AppConstant.Operate_id，根据自己功能或者操作选择对应的id
	 *	operateName  //功能、操作名,取值来源于：AppConstant中AppConstant.Operate_Name，根据自己功能或者操作选择对应的值
	 *	content      //详细描述,详情见操作日志
	 *	systemOperateId//销售管理id，销售管理保存时传入systemOperateId，其他模块为""即可。
	 *  */
	public void setUserOperateLog(String moduleId ,String moduleName ,String operateId ,String operateName ,String content,String systemOperateId ) throws Exception {
		ShiroUser user = ShiroUtil.getShiroUser();
//		String orgId = user.getOrgId();
//		LogUserOperateBean logUserOperateBean=new LogUserOperateBean();
//		logUserOperateBean.setId(SysBaseModelUtil.getModelId());
//		logUserOperateBean.setOrgId(orgId);          //机构id
//		logUserOperateBean.setUserAccount(user.getAccount());//用户账号
//		logUserOperateBean.setUserName(user.getName());      //用户名
//		logUserOperateBean.setModuleId(moduleId);      //模块id，取值来源于：AppConstant中AppConstant.Module_id，根据自己模块选择对应的id
//		logUserOperateBean.setModuleName(moduleName);   //模块名称，取值来源于：AppConstant中AppConstant.Module_Name，根据自己模块选择对应的值
//		logUserOperateBean.setOperateId(operateId);//功能、操作id,取值来源于：AppConstant中AppConstant.Operate_id，根据自己功能或者操作选择对应的id
//		logUserOperateBean.setOperateName(operateName);  //功能、操作名,取值来源于：AppConstant中AppConstant.Operate_Name，根据自己功能或者操作选择对应的值
//		logUserOperateBean.setContent(content);          //详细描述,
//		logUserOperateBean.setSystemOperateId(systemOperateId);//销售管理id，销售管理保存时传入systemOperateId，其他模块为""即可。
//		insertlogUserOperate(logUserOperateBean);
		   LogBean logBean=new LogBean();
		   logBean.setOrgId(user.getOrgId());
	       logBean.setUserAcc(user.getAccount());
	       logBean.setUserName(user.getName());
	       logBean.setModuleId(moduleId);
	       logBean.setModuleName(moduleName);
	       logBean.setOperateId(operateId);
	       logBean.setOperateName(operateName);
	       logBean.setContent(content);
	       logBean.setData(systemOperateId);
	       saveLogTableStore(logBean);

	}
	
	public void saveLogTableStore(LogBean logBean){
		try {
		   LogTableStoreApi logTableStoreApi=getLogTableStoreApi();
	       LogDto logDto = new LogDto();
	       logDto.setLogBean(logBean);
	       logTableStoreApi.saveLog(logDto);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}

	}
	
	public TsGetRangePageResp selectLogTableStore(LogUserOperateQueryDto dto,TsGetRangePage pages,int pageSize, int pageNo){
		TsGetRangePageResp rep=new TsGetRangePageResp();
		try {
			 LogTableStoreApi logTableStoreApi=getLogTableStoreApi();
			if(pageNo==0||pages.getOperation()==null){//首次查询
				List<DataDictionaryBean> dlist0 = cachedService.getDirList(AppConstant.DATA_40019, dto.getOrgId());//分页设置
				if(!dlist0.isEmpty() && dlist0.get(0) != null){
			         String   fy = dlist0.get(0).getDictionaryValue();
			         if(fy!=null&&!"".endsWith(fy)){
			        	 dto.setPageSize(Integer.valueOf(fy)); 
			         }
			         }
	    	    rep=logTableStoreApi.searchLogUserOperateByPage(dto);
	       }else{
                if(pages.getOperation()==1||pages.getOperation()==2||pages.getOperation()==3||pages.getOperation()==4){
                	rep= logTableStoreApi.logChangePage(pages);
                }else{
                	if(pageSize!=0&&pageNo!=0){
    			    dto.setPageSize(pageSize);
    			    dto.setPageNo(1);
                	}
    			    rep=logTableStoreApi.searchLogUserOperateByPage(dto);  	
                }
	       }


		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
        return rep;
	}
}
