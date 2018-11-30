package com.qftx.tsm.report.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.dao.ResourceGroupMapper;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.report.bean.ResourceCountBean;
import com.qftx.tsm.report.service.ResourceCountService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.*;
/**
 * User�� xiaoxh
 * Time�� 15:29
 */
@Controller
@RequestMapping(value = "/Resource")
public class ResourceDateAction {
    private static final Logger logger = Logger.getLogger(ResourceDateAction.class.getName());
    @Autowired
    private CachedService cachedService;
    @Autowired
    private ResourceCountService resourceCountService;
    @Autowired
	private ResourceGroupMapper resourceGroupMapper;
    
	@RequestMapping("/toResourceNalysis")
	public String toResourceNalysis(HttpServletRequest request) {
	return "report/resourceNalysis";
	}
    
	@RequestMapping("/getGroupData")
	@ResponseBody
	public List<Map<String, Object>> deleteBlackList(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<Map<String, Object>> resGroups = cachedService.getResGroupList1(user.getOrgId());
	return resGroups;
	}
	
	@RequestMapping("/getAllResource")
	@ResponseBody
	public List<Map<String,Object>> getAllResource(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String groupIds=request.getParameter("groupIds");
		String strDate=request.getParameter("strDate");
		String endDate=request.getParameter("endDate");
		List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
		try {
		ResourceCountBean bean=new ResourceCountBean();
		bean.setOrgId(user.getOrgId());
		bean.setStartDate(strDate);
		bean.setEndDate(endDate);
		if (StringUtils.isNotEmpty(groupIds)) {
 		    String[] groupIds_list = groupIds.split(",");
 		    bean.setGroupList(Arrays.asList(groupIds_list));
  	}
		int isOpen=getCheck(user.getOrgId());
		if(isOpen==1){
			List<DataDictionaryBean> data2 = cachedService.getDirList(AppConstant.DATA04, user.getOrgId());
			bean.setErrorValue(Integer.valueOf(data2.get(0).getDictionaryValue()));
		}
		if(groupIds==null||"".endsWith(groupIds)){
			 bean.setGroupList(resourceGroupMapper.findAllGroup(user.getOrgId()));
		}
		returnList=resourceCountService.getGrouplist(bean);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	return returnList;
	}
	
	
	/**有效通话设置是否打开：1为打开，0为为没有打开*/
	public int getCheck(String orgId){
		int open = 1;
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA16, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getDictionaryValue();
			open = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "1");
		}
		return open;
	}


	@RequestMapping("/exportResourceData")
    public void exportResourceData(HttpServletRequest request,HttpServletResponse response){
    	try {	    	
    		ShiroUser user = ShiroUtil.getShiroUser();
    		String groupIds=request.getParameter("groupIds");
    		String strDate=request.getParameter("strDate");
    		String endDate=request.getParameter("endDate");
    		List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
    		ResourceCountBean bean=new ResourceCountBean();
    		bean.setOrgId(user.getOrgId());
    		bean.setStartDate(strDate);
    		bean.setEndDate(endDate);
    		if (StringUtils.isNotEmpty(groupIds)) {
     		    String[] groupIds_list = groupIds.split(",");
     		    bean.setGroupList(Arrays.asList(groupIds_list));
      	}
    		int isOpen=getCheck(user.getOrgId());
    		if(isOpen==1){
    			List<DataDictionaryBean> data2 = cachedService.getDirList(AppConstant.DATA04, user.getOrgId());
    			bean.setErrorValue(Integer.valueOf(data2.get(0).getDictionaryValue()));
    		}
    		if(groupIds==null||"".endsWith(groupIds)){
    			 bean.setGroupList(resourceGroupMapper.findAllGroup(user.getOrgId()));
    		}
    		returnList=resourceCountService.getGrouplist(bean);
    		String expType = "1";
    		HSSFWorkbook workbook = new HSSFWorkbook();
    		
    		/**表头样式**/
    		HSSFCellStyle headerStyle = workbook.createCellStyle();
    		HSSFFont font = workbook.createFont();  
    		font.setColor(HSSFColor.BLACK.index);  
    		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 粗体  
    		font.setFontHeightInPoints((short)12);
    		headerStyle.setFont(font);  
    		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中  
    		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中 
    		/**内容样式**/
    		HSSFCellStyle bodyStyle = workbook.createCellStyle();
    		HSSFFont bodyFont = workbook.createFont();
    		bodyFont.setColor(HSSFColor.BLACK.index);
    		bodyFont.setFontHeightInPoints((short)11);
    		bodyStyle.setFont(bodyFont);
    		bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中  
    		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中 
    		String expName="";
				/**资源分析**/
	    		HSSFSheet callSheet = workbook.createSheet("资源分析"); 		
	    	List<String> list_new =new ArrayList<String>();
	    	list_new.add("资源分类");
	    	list_new.add("资源组");
	    	list_new.add("客户总数");
	    	list_new.add("意向客户数");
	    	list_new.add("签约客户数");
	    	list_new.add("接通数");
	    	list_new.add("有效呼叫数");
	    	list_new.add("信息错误数");
	    	createConSheetbyGroup(returnList,callSheet,headerStyle,bodyStyle,list_new);
	    	expName="资源分析";
    		
    		response.setContentType("application/vnd.ms-excel");    
    		response.setHeader("Content-disposition", "attachment;filename="+DateUtil.formatDate(new Date(),"yyyyMMdd")+URLEncoder.encode("资源分析.xls","UTF-8"));
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();    
            ouputStream.close();
    	} catch (Exception e) {
			throw new SysRunException(e);
		}
    }
	/**
	 * 
	 *
	 */
	public void createConSheetbyGroup(List<Map<String,Object>> dtos,HSSFSheet sheet, HSSFCellStyle headerStyle, HSSFCellStyle bodyStyle,List<String> item) {
		
				
//		Collections.reverse(dtos);
		HSSFRow rowm = sheet.createRow(0);
		for (int i = 0; i < item.size(); i++) {
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(item.get(i));
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, 32 * 160);
		}
		int abc=0;
		int lastNum=0;
		for (int i = 0; i < dtos.size(); i++) {
			Map<String,Object> map=new HashMap<String,Object>();
			List<ResourceCountBean> resourceList=(List<ResourceCountBean>) dtos.get(i).get("valus");
			
			if(resourceList!=null&&resourceList.size()>0){
				  for(int cl = 0; cl < resourceList.size(); cl++){
					  abc=abc+1;
					  HSSFRow datarow = sheet.createRow(abc);
			for (int j = 0; j < item.size(); j++) {
				HSSFCell cell = datarow.createCell(j);
				cell.setCellStyle(bodyStyle);
				if (j == 0) {
					cell.setCellValue(dtos.get(i).get("GroupFlName").toString());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					if(i==0){
						sheet.addMergedRegion(new Region(1,(short)0,resourceList.size(),(short)0));	
					}
						else if(i>0){
						List<ResourceCountBean> resourceList2=(List<ResourceCountBean>) dtos.get(i-1).get("valus");
						sheet.addMergedRegion(new Region(resourceList2.size()+1,(short)0,resourceList2.size()+resourceList.size(),(short)0));	
					}

				}else 
					if(j==1){
					cell.setCellValue(resourceList.get(cl).getGroupName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
				}else if(j==2){
					cell.setCellValue(resourceList.get(cl).getResCount());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j==3){
					cell.setCellValue(resourceList.get(cl).getWillCountstr());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j==4){
					cell.setCellValue(resourceList.get(cl).getSignCountstr());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j==5){
					cell.setCellValue(resourceList.get(cl).getCallCountstr());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j==6){
					cell.setCellValue(resourceList.get(cl).getVaildCallCountstr());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j==7){
					cell.setCellValue(resourceList.get(cl).getErrorCountstr());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);	
				}
				}
				}
			}
		}

	}

	
	
}
