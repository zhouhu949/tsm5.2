package com.qftx.export.scrontrol;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.StringUtils;
import com.qftx.export.bean.LogExportInfoBean;
import com.qftx.export.service.CustExportInfoService;

@Controller
@RequestMapping("/export/log")
public class CustDataExportLogAction {
	private Log log = LogFactory.getLog(CustDataExportLogAction.class);
	@Autowired
	private CustExportInfoService custExportInfoService;
	@Autowired
	private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Autowired
	private CachedService cachedService;
	
	@RequestMapping("/data")
	@ResponseBody
	public Map<String, Object> data(HttpServletRequest request,LogExportInfoBean exportInfoBean){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			exportInfoBean.setOrgId(user.getOrgId());
			if(user.getIssys() != null && user.getIssys() == 1){
				String osType = request.getParameter("osType");
				osType = StringUtils.isNotBlank(osType) ? osType : "1";
				if(StringUtils.isBlank(exportInfoBean.getUserAccsStr()) && osType.equals("1")){
					List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
					if(!accs.contains(user.getAccount())){
						accs.add(user.getAccount());
					}
					exportInfoBean.setUserAccs(accs);
				}else if(StringUtils.isBlank(exportInfoBean.getUserAccsStr()) && osType.equals("2")){
					exportInfoBean.setAccount(user.getAccount());
				}else{
					exportInfoBean.setUserAccs(Arrays.asList(exportInfoBean.getUserAccsStr().split(",")));
				}
			}else{
				exportInfoBean.setAccount(user.getAccount());
			}
			List<LogExportInfoBean> list = custExportInfoService.getLogListPage(exportInfoBean);
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			for(LogExportInfoBean bean : list){
				bean.setUserName(nameMap.get(bean.getAccount()) == null ? bean.getAccount() : nameMap.get(bean.getAccount()));
				if(DateUtil.addDay(bean.getExportDate(), 7).compareTo(new Date()) < 0 ){
					bean.setDeletedFile(1);
				}
			}
			map.put("list", list);
			map.put("item", exportInfoBean);
			map.put("service_url", ConfigInfoUtils.getStringValue("tsm_upload_service_url"));
		} catch (Exception e) {
			log.error("【数据导出】日志信息异常！",e);
		}
		return map;
	}
	
	 @RequestMapping(value = "download")
	 public void download(HttpServletRequest request,HttpServletResponse response,String exportId){
		 try {
			 ShiroUser user = ShiroUtil.getShiroUser();
			 LogExportInfoBean bean = custExportInfoService.getByPrimaryKeyAndOrgId(user.getOrgId(),exportId);
			 if(bean != null){
				 String fileName = "";
				 if(bean.getExportCustType() == 1){
					 fileName = "资源（"+DateUtil.formatDate(bean.getExportDate(), DateUtil.DATE_DAY)+"）";
				 }else if(bean.getExportCustType() == 2){
					 fileName = "意向客户（"+DateUtil.formatDate(bean.getExportDate(), DateUtil.DATE_DAY)+"）";
				 }else if(bean.getExportCustType() == 3){
					 fileName = "签约客户（"+DateUtil.formatDate(bean.getExportDate(), DateUtil.DATE_DAY)+"）";
				 }else if(bean.getExportCustType() == 4){
					 fileName = "沉默客户（"+DateUtil.formatDate(bean.getExportDate(), DateUtil.DATE_DAY)+"）";
				 }else if(bean.getExportCustType() == 5){
					 fileName = "流失客户（"+DateUtil.formatDate(bean.getExportDate(), DateUtil.DATE_DAY)+"）";
				 }
				 fileName+=".xlsx";
				 response.setCharacterEncoding("utf-8");
	 	         response.setContentType("multipart/form-data");
	 	         response.setHeader("Content-Disposition", "attachment;fileName="
	 	                + URLEncoder.encode(fileName,"UTF-8"));
	 	         InputStream inputStream = new FileInputStream(new File(bean.getExportFilePath()));
	 	         OutputStream os = response.getOutputStream();
	             byte[] b = new byte[1024];
	             int length;
	             while ((length = inputStream.read(b)) > 0) {
	                 os.write(b, 0, length);
	             }
	             // 这里主要关闭。
	             os.close();
	             inputStream.close();
			 }
		} catch (Exception e) {
			log.error("下载文件出错,exportId="+exportId,e);
		}
	 }
}
