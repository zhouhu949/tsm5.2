package com.qftx.export.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.export.bean.LogExportInfoBean;
import com.qftx.export.dao.LogExportInfoMapper;
import com.qftx.export.util.ExportExcelUtil;
import com.qftx.tsm.cust.bean.ResCustInfoBean;

@Service
public class CustExportInfoService {
	@Autowired
	private LogExportInfoMapper logExportInfoMapper;
//	@Value("#{config['export_excel_path']}")
//	private String export_excel_path;
	
	public void insert(LogExportInfoBean bean){
		logExportInfoMapper.insert(bean);
	}
	
	public List<LogExportInfoBean> getLogListPage(LogExportInfoBean bean){
		return logExportInfoMapper.findLogListPage(bean);
	}
	
	public String export(List<ResCustInfoBean> list,List<String> columns,List<String> headers,String orgId,String account,Integer exportType,String searchContext){
		String exportId = SysBaseModelUtil.getModelId();
		String filePath = "";//export_excel_path+exportId+".xlsx";
		OutputStream out = null;
		try {
			out = new FileOutputStream(filePath);
			ExportExcelUtil.exportExcel(headers, columns, list,out, "yyyy-MM-dd");
		} catch (Exception e) {
			throw new SysRunException("导出数据异常！");
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		LogExportInfoBean bean = new LogExportInfoBean();
		bean.setExportId(exportId);
		bean.setAccount(account);
		bean.setOrgId(orgId);
		bean.setExportCustType(exportType);
		bean.setExportSearchContext(searchContext);
		bean.setExportNum(list.size());
		bean.setDeletedFile(0);
		bean.setExportFilePath(filePath);
		bean.setExportDate(new Date());
		insert(bean);
		return exportId;
	}
	
	public LogExportInfoBean getByPrimaryKeyAndOrgId(String orgId,String exportId){
		return logExportInfoMapper.getByPrimaryKeyAndOrgId(orgId, exportId);
	}
} 
