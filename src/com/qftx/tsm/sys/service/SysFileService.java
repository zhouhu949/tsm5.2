package com.qftx.tsm.sys.service;

import com.qftx.base.util.GuidUtil;
import com.qftx.tsm.sys.bean.SysFileBean;
import com.qftx.tsm.sys.dao.SysFileMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysFileService{

	Logger logger = Logger.getLogger(SysFileService.class);
	
	@Autowired
	private SysFileMapper sysFileMapper;

	public List<SysFileBean> getList() {
		return sysFileMapper.find();
	}

	public List<SysFileBean> getListByCondtion(SysFileBean entity) {
		return sysFileMapper.findByCondtion(entity);
	}
	 
	public SysFileBean getByCondtion(SysFileBean entity) {
		return sysFileMapper.getByCondtion(entity);
	}
	
	public SysFileBean getByPrimaryKey(String SysFileBeanId,String orgId) {
		Map<String,Object>map = new HashMap<String, Object>();
		map.put("id", SysFileBeanId);
		map.put("orgId", orgId);
		return sysFileMapper.getByOrgIdPrimaryKey(map);
	}
	
	public SysFileBean create(String orgId,String userId,String type,String filePath,
			String fileName,int fileSize,Date date,Integer oss) {
		SysFileBean bean =  new SysFileBean();
        String id=GuidUtil.getGuid();
        bean.setId(id);
        bean.setOrgId(orgId);
        bean.setUserId(userId);
        bean.setCode(id);
        bean.setType(type);
        bean.setFileType(fileName.substring(fileName.lastIndexOf(".")+1));
        bean.setFileName(fileName);
        bean.setFilePath(filePath);
        bean.setFileUrl(filePath+fileName);
        bean.setFileSize(fileSize);
        bean.setUpdatetime(date);
        bean.setInputtime(date);
        bean.setOss(oss);
		sysFileMapper.insert(bean);
		return bean;
	}
	
	public SysFileBean create(SysFileBean bean) {
		sysFileMapper.insert(bean);
		return bean;
	}

	 
	public void createBatch(List<SysFileBean> entitys) {
		sysFileMapper.insertBatch(entitys);
	}

	 
	public void modify(SysFileBean entity) {
		sysFileMapper.updateTrends(entity);
	}

	 
	public void modifyTrends(SysFileBean entity) {
		sysFileMapper.updateTrends(entity);	
	}

	 
	public void modifyBatch(List<SysFileBean> entitys) {
		
	}

	public void modifyTrendsBatch(List<SysFileBean> entitys) {
		// TODO Auto-generated method stub
		
	}

}
