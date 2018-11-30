package com.qftx.tsm.imp.service;

import com.qftx.base.util.GuidUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.dao.ResourceGroupMapper;
import com.qftx.tsm.imp.bean.ImportField;
import com.qftx.tsm.imp.bean.ImportResFileBean;
import com.qftx.tsm.imp.dao.ImportResFileMapper;
import com.qftx.tsm.imp.excel.ExcelDataBean;
import com.qftx.tsm.imp.excel.ExcelReader;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.SysFileBean;
import com.qftx.tsm.sys.service.SysFileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ImportResFileService {
	@Autowired 
	ImportResFileMapper mapper;
	@Autowired
	ImportResInfoService importResInfoService;
	@Autowired
	ResourceGroupMapper resGroupMapper;
	@Autowired
    private SysFileService sysFileService;
	@Autowired private CachedService cachedService;
	@Autowired private ImportResInfoService resInfoService;
	@Autowired private ImportResResultService importResResultService;
	Logger logger = Logger.getLogger(ImportResFileService.class);
	
	/* 查询*/
	public ImportResFileBean queryById(String orgId,String id){
		ImportResFileBean entity = new ImportResFileBean();
		entity.setOrgId(orgId);
		entity.setId(id);
		return mapper.getByCondtion(entity);
		
	}
	
	/* 查询所有资源分组*/
	public  List<ResourceGroupBean> findResGroup(String orgId){
		return resGroupMapper.findResGroup(orgId);
	}
	
	
	/* excel拆分入库*/
	public  boolean excel2Db(String orgId,String id,List<String>fields,String firstCheckVal){
		boolean flag=false;
		ImportResFileBean bean = queryById(orgId, id);
		if(bean!=null&&bean.getFileId()!=null){
			SysFileBean sysFile = sysFileService.getByPrimaryKey(bean.getFileId(),orgId);
			if(sysFile!=null&&StringUtils.isNotBlank(sysFile.getFileUrl())){
				try {
					ExcelReader er = new ExcelReader(sysFile.getFileUrl());
					ExcelDataBean data;
					data = er.getSheetData(fields,firstCheckVal);
					List<String> datas = data.getDatas();					
					importResInfoService.insertBatch(orgId, id, datas, new Date());
					flag=true;
				} catch (Exception e) {
					e.printStackTrace();
					flag=false;
				}
			}else{
				logger.error("文件为空 or 文件目录为空！sysFileID"+bean.getFileId());
			}
		}else{
			logger.error("导入文件为空！id:"+id);
		}
		return flag;
	}
	
	/**获取字段
	 * @param isState 0:个人资源，1：企业资源 
	 * */
	public Object[] getImportFields(Integer isState,String orgId){		
			List<ImportField> fields = new ArrayList<ImportField>();
			List<CustFieldSet> list = new ArrayList<CustFieldSet>();
			List<CustFieldSet> custFieldSetList = new ArrayList<CustFieldSet>();
			if(isState == 0){
				// 获取个人资源字段缓存
				list = cachedService.getPersonFiledSet(orgId);
				for(CustFieldSet custFieldSet : list){
					if(custFieldSet.getEnable() == 1){ // 只需要启用的字段
						custFieldSetList.add(custFieldSet);
					}
				}
			}else{
				// 获取企业资源字段缓存 & 联系人字段缓存
				list = cachedService.getComFiledSet(orgId);
				list.addAll(cachedService.getContactsFiledSet(orgId));
				for(CustFieldSet custFieldSet : list){
					if(custFieldSet.getEnable() == 1){ // 只需要启用的字段
						custFieldSetList.add(custFieldSet);
					}
				}
			}
			StringBuffer strb = new StringBuffer();
			if(list != null && list.size() >0){
				for(CustFieldSet cfs:custFieldSetList){
					// 1：设置匹配字段
					fields.add(new ImportField(cfs.getFieldCode(), cfs.getFieldName(), cfs.getDataType(), cfs.getFieldLength(),cfs.getState()));
					// 2：设置字段必填项	
					if(cfs.getIsRequired()==1){
						strb.append(cfs.getFieldCode());
						strb.append(",");
						strb.append(cfs.getFieldName());
						strb.append(";");
					}				
				}
			}
			Object[]obj = {fields,strb.toString()};
			return obj;
	}
	
	/* 获取Excel的表头和第一行数据*/
	public ExcelDataBean getExcelHeaders(String orgId,String importFileId,String fileUrl,Date date){
		ExcelReader er = new ExcelReader(fileUrl);
		ExcelDataBean data = er.getSheetHeaders();
		return data;
	}
	/*更新表头 行数*/
	public void updateHeader(String orgId,String id,String header,int rowcount){
		ImportResFileBean entity= new ImportResFileBean();
		entity.setId(id);
		entity.setOrgId(orgId);
		entity.setHeader(header);
		entity.setRowcount(rowcount);
		entity.setUpdatetime(new Date());
		mapper.updateTrends(entity);
	}
	
	/*更新映射数据*/
	public void updateMatch(String orgId,String id,String match){
		ImportResFileBean entity= new ImportResFileBean();
		entity.setId(id);
		entity.setOrgId(orgId);
		entity.setMapper(match);
		entity.setUpdatetime(new Date());
		mapper.updateTrends(entity);
	}
	
	/* 插入*/
	public ImportResFileBean insert(String orgId,String groupId,String resGroupId,Integer resStatus,String userId,String fileId,Date date){
		ImportResFileBean bean = new ImportResFileBean();
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setOrgId(orgId);
		bean.setGroupId(groupId);
		bean.setResGroupId(resGroupId);
		bean.setResStatus(resStatus);
		bean.setUserId(userId);
		bean.setFileId(fileId);
		bean.setInputtime(date);
		bean.setUpdatetime(date);
		bean.setIsDel(0);
		bean.setStatus(0);
		bean.setState(0);
		
		mapper.insert(bean);
		return bean;
	}
	
	
}
