package com.qftx.tsm.report.service;

import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.report.bean.ResourceReportBean;
import com.qftx.tsm.report.dao.ResourceReportMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class ResourceReportService {
	private static final Logger logger = Logger.getLogger(ResourceReportService.class);
	@Autowired
	private ResourceReportMapper resourceReportMapper;
	
//	public void insertTsmReportResource(ResourceReportBean bean){
//		resourceReportMapper.insertTsmReportResource(bean);
//	};
//	
//	public void updateResource(ResourceReportBean bean){
//		resourceReportMapper.updateResource(bean);
//	};
//	
//	public  ResourceReportBean selectResource(ResourceReportBean bean){
//	return	resourceReportMapper.selectResource(bean);
//	};
//	
//	public void intsertOrUpdate(ResourceReportBean bean){
//		try {
//			ResourceReportBean res=resourceReportMapper.selectResource(bean);
//			if(res!=null){
//				if(bean.getCallLength()!=null&&bean.getCallLength()>0){
//					if(res.getCallLength()!=null&&res.getCallLength()>0){
//						if(res.getCallLength()>bean.getCallLength()){
//							bean.setCallLength(res.getCallLength());	
//						}
//					}
//				}
//				resourceReportMapper.updateResource(bean);
//			}else{
//				bean.setId(SysBaseModelUtil.getModelId());
//				resourceReportMapper.insertTsmReportResource(bean);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e.getMessage());
//		}
//	}

}
