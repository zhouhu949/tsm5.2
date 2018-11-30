package com.qftx.tsm.contract.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.contract.bean.ContractOrderBean;
import com.qftx.tsm.contract.dto.ContractOrderDto;
import com.qftx.tsm.contract.service.ContractService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.message.service.TsmMessageService;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.OutputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

 /** 
 * 订单导出
 * @author: lixing
 * @since: 2016年5月9日  下午3:51:48
 * @history:
 */
@Controller
@RequestMapping("/contract/order/export")
public class ContractOrderExportAction extends BaseAction {
	private static final Logger logger = Logger.getLogger(ContractOrderExportAction.class);
	private static final String sheetTitle = "订单列表";
//	private static final String[] header = {"交易日期","客户名称","订单号","交易金额(元)","提交人","所有者","订单状态","生效日期","失效日期","审核备注"};
//	private static final String[] header_person = {"交易日期","客户姓名","联系电话","联系地址","单位名称","订单号","交易金额(元)","提交人","所有者","订单状态","生效日期","失效日期","审核备注"};
	@Autowired
    private ContractService contractService;
    @Autowired
    private CachedService cachedService;
    @Autowired
    private TsmMessageService tsmMessageService;
    @Resource
	private TsmGroupShareinfoService tsmGroupShareinfoService;
    @Autowired
    private LogUserOperateService logUserOperateService;
  
    
    @RequestMapping()
    public String Index(HttpServletRequest request){
    	ShiroUser user = ShiroUtil.getShiroUser();
    	request.setAttribute("user", user);
    	return "contract/order_output";
    }
    
    @RequestMapping("/data")
    @ResponseBody
    public void data(HttpServletRequest request,HttpServletResponse response,ContractOrderDto contractOrderDto){
    	Map<String, BigDecimal> map = new HashMap<String, BigDecimal>();
    	try {
    	   ShiroUser user = ShiroUtil.getShiroUser();
//		   contractOrderDto.setsDateType(2);
       	   contractOrderDto.setOrgId(user.getOrgId());
       	   if(user.getIssys() != null && user.getIssys() == 1){
			   contractOrderDto.setOsType(StringUtils.isBlank(contractOrderDto.getOsType()) ? "1" : contractOrderDto.getOsType());
	 		   if(contractOrderDto.getOsType().equals("1")){
//	 			   contractOrderDto.setAdminAcc(user.getAccount());
	 			  List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
	 			  if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
	 			  contractOrderDto.setOwnerAccs(accs);
	 		   }else if(contractOrderDto.getOsType().equals("2")){
	 			   contractOrderDto.setOwnerAcc(user.getAccount());
	 		   }else{
	 			   if(StringUtils.isNotBlank(contractOrderDto.getOwnerAccStr())){
		    			   contractOrderDto.setOwnerAccs(Arrays.asList(contractOrderDto.getOwnerAccStr().split(",")));
		    		   }else{
		    			   contractOrderDto.setOwnerAccStr(user.getAccount());
		    			   contractOrderDto.setOwnerAccs(Arrays.asList(user.getAccount()));
		    		   }
	 		   }
	 	    }else{
	 		   contractOrderDto.setOwnerAcc(user.getAccount());
	 	    }
       	   
	       	if(StringUtils.isNotBlank(contractOrderDto.getUserIdsStr())){
				contractOrderDto.setUserIds(Arrays.asList(contractOrderDto.getUserIdsStr().split(",")));
			}
       	   
       	   if(contractOrderDto.getsDateType() != null && contractOrderDto.getsDateType() != 0 && contractOrderDto.getsDateType() != 5){
       		    contractOrderDto.setStartDate(getStartDateStr(contractOrderDto.getsDateType()));
       		    contractOrderDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
   	   	   }
   	   		
       	  if(contractOrderDto.geteDateType() != null && contractOrderDto.geteDateType() != 0 && contractOrderDto.geteDateType() != 5){
		   		if(contractOrderDto.geteDateType() == 4){
		   			contractOrderDto.setStartEffecDate(getStartDateStr(contractOrderDto.geteDateType()));
  		   			contractOrderDto.setEndEffecDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		   		}else{
		   		contractOrderDto.setStartEffecDate(getStartDateStr(contractOrderDto.geteDateType()));
	   			contractOrderDto.setEndEffecDate(getEndDateStr(contractOrderDto.geteDateType()));
		   		}
	   	   }
	   		
	   	   if(contractOrderDto.getiDateType() != null && contractOrderDto.getiDateType() != 0 && contractOrderDto.getiDateType() != 5){
		   		if(contractOrderDto.getiDateType() == 4){
		   			contractOrderDto.setStartInvalidDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		   			contractOrderDto.setEndInvalidDate(getEndDateStr(contractOrderDto.getiDateType()));
		   		}else{
		   			contractOrderDto.setStartInvalidDate(getStartDateStr(contractOrderDto.getiDateType()));
	   			contractOrderDto.setEndInvalidDate(getEndDateStr(contractOrderDto.getiDateType()));
		   		}
	   	   }
	   	   
   	   	   contractOrderDto.setShowCheck("1");
   	   	   if(getCommonOwnerOpenState(user.getOrgId()) == 1){//如果开启共有者  设置查询共有者
   	   		   contractOrderDto.setCommonAcc(user.getAccount());
   	   	   }
   	   	   if(StringUtils.isBlank(contractOrderDto.getNoteType())) contractOrderDto.setNoteType("1");
   	   	   List<ContractOrderDto> orderDtos = contractService.findContractOrderList(contractOrderDto);
	   	   Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
	  	   for(ContractOrderDto cod : orderDtos) {
				cod.setUserName(StringUtils.isNotBlank(cod.getUserId()) ? nameMap.get(cod.getUserId()) : "");
				cod.setSaleAcc(StringUtils.isNotBlank(cod.getSaleAcc()) ? nameMap.get(cod.getSaleAcc()) : "");
	  	   }
   	   	   List<ContractOrderDto> orderCountDtos = contractService.getContractOrderCountData(contractOrderDto);
	   	   BigDecimal totalMoney = BigDecimal.valueOf(0);
	   	   for(ContractOrderDto orderDto : orderCountDtos){
	   		   totalMoney = totalMoney.add(orderDto.getMoney() == null ? BigDecimal.valueOf(0) : orderDto.getMoney());
	   		   map.put(orderDto.getAuthState().toString(), orderDto.getMoney().divide(BigDecimal.valueOf(10000),2,BigDecimal.ROUND_HALF_EVEN));
	   	   }
	   	   map.put("5", totalMoney.divide(BigDecimal.valueOf(10000),2,BigDecimal.ROUND_HALF_EVEN));
	   	   logUserOperateService.setUserOperateLog( AppConstant.Module_id1007,AppConstant.Module_Name1007, AppConstant.Operate_id30, AppConstant.Operate_Name30, "", "");
	   	   String exportParams = request.getParameter("exportParams");
	   	   export(response, user, contractOrderDto, orderDtos, map,exportParams);
		} catch (Exception e) {
			logger.error("订单导出异常!"+e.getMessage());
		}
    }
    
    public void export(HttpServletResponse response,ShiroUser user,ContractOrderDto contractOrderDto,List<ContractOrderDto> orderDtos,Map<String, BigDecimal> map,String exportParams) throws Exception{
    	HSSFWorkbook workbook = new HSSFWorkbook();
    	HSSFCellStyle headerStyle = getHeadStyle(workbook);
    	HSSFCellStyle bodyStyle = getBodyStyle(workbook);
    	
    	HSSFSheet sheet = workbook.createSheet(sheetTitle);
    	HSSFRow rowm = sheet.createRow(0);
    	String params[] = exportParams.split("\\$");
    	List<String> fields = new ArrayList<String>();
    	for(int i=0;i<params.length;i++){
    		fields.add(params[i].split("\\|")[0]);
			String head = params[i].split("\\|")[1];
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(head);
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, 32*180);
		}
    	String fieldName ="",getMethodName="";
    	Class<? extends ContractOrderBean> clazz;
    	Method getMethod;
    	Object obj;
    	for (int i = 0; i < orderDtos.size(); i++) {
    		ContractOrderDto orderDto = orderDtos.get(i);
    		HSSFRow datarow = sheet.createRow(i+1);
    		datarow.setHeight((short)(20*24));
    		clazz = orderDto.getClass();
    		for(int j=0;j<fields.size();j++){
    			fieldName = fields.get(j);
    			getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    			getMethod = clazz.getMethod(getMethodName);
    			Object value = getMethod.invoke(orderDto, new Object[]{});
    			HSSFCell cell = datarow.createCell(j);
    			cell.setCellStyle(bodyStyle);
    			if(fieldName.equals("authState")){
    				Integer authState = (Integer)value;
    				switch (authState) {
						case 1:
							cell.setCellValue("待审核");
							break;
						case 2:
							cell.setCellValue("生效中");
							break;
						case 3:
							cell.setCellValue("驳回");
							break;
						case 4:
							cell.setCellValue("作废");
							break;
						case 5:
							cell.setCellValue("已失效");
							break;
						default:
							cell.setCellValue("未上报");
							break;
					}
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    			}else if(fieldName.equals("courierStatus") && value != null){
    				//物流状态 1 暂无记录 2 在途中 3 派送中 4 已签收 5 用户拒签 6 疑难件 7 无效单8 超时单 9 签收失败 10 退回
    				Integer courierStatus = (Integer)value;
    				switch (courierStatus) {
						case 1:
							cell.setCellValue("暂无记录");
							break;
						case 2:
							cell.setCellValue("在途中");
							break;
						case 3:
							cell.setCellValue("派送中");
							break;
						case 4:
							cell.setCellValue("已签收");
							break;
						case 5:
							cell.setCellValue("用户拒签");
							break;
						case 6:
							cell.setCellValue("疑难件");
							break;
						case 7:
							cell.setCellValue("无效单");
							break;
						case 8:
							cell.setCellValue("超时单");
							break;
						case 9:
							cell.setCellValue("签收失败");
							break;
						case 10:
							cell.setCellValue("退回");
							break;
						default:
							break;
					}
    			}else if(value != null){
    				if(value instanceof Date){
    					cell.setCellValue(DateUtil.formatDate((Date)value,DateUtil.DATE_DAY));
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    				}else{
    					cell.setCellValue(value.toString());
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    				}
    			}else{
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    			}
    		}
		}
    	
    	exportCountData(sheet, getCountTitleStyle(workbook),getCountValueStyle(workbook), orderDtos.size()+1, map);
    	
    	response.setContentType("application/vnd.ms-excel");    
		response.setHeader("Content-disposition", "attachment;filename="+URLEncoder.encode("订单导出列表.xls","UTF-8"));
        OutputStream ouputStream = response.getOutputStream();
        workbook.write(ouputStream);
        ouputStream.flush();    
        ouputStream.close();
    	
    }
    
    
    /** 
     * 底部统计
     * @param sheet
     * @param titleStyle
     * @param valueStyle
     * @param index
     * @param map 
     * @create  2016年5月9日 下午4:33:15 lixing
     * @history  
     */
    public void exportCountData(HSSFSheet sheet,HSSFCellStyle titleStyle,HSSFCellStyle valueStyle,Integer index,Map<String,BigDecimal> map){
    	HSSFRow totalRow = sheet.createRow(index);
    	
    	HSSFCell totalCell = totalRow.createCell(0);
    	totalCell.setCellStyle(titleStyle);
    	totalCell.setCellValue("订单总额：");
    	totalCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    	
    	HSSFCell totalValueCell = totalRow.createCell(1);
    	totalValueCell.setCellStyle(valueStyle);
    	totalValueCell.setCellValue(map.get("5") == null ? "0万" : map.get("5").doubleValue()+"万");
    	totalValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    	
    	HSSFCell passCell = totalRow.createCell(2);
    	passCell.setCellStyle(titleStyle);
    	passCell.setCellValue("通过金额：");
    	passCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    	
    	HSSFCell passValueCell = totalRow.createCell(3);
    	passValueCell.setCellStyle(valueStyle);
    	passValueCell.setCellValue(map.get("2") == null ? "0万" : map.get("2").doubleValue()+"万");
    	passValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    	
    	HSSFCell waitCell = totalRow.createCell(4);
    	waitCell.setCellStyle(titleStyle);
    	waitCell.setCellValue("待审金额：");
    	waitCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    	
    	HSSFCell waitValueCell = totalRow.createCell(5);
    	waitValueCell.setCellStyle(valueStyle);
    	waitValueCell.setCellValue(map.get("1") == null ? "0万" : map.get("1").doubleValue()+"万");
    	waitValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    	
    	HSSFCell returnCell = totalRow.createCell(6);
    	returnCell.setCellStyle(titleStyle);
    	returnCell.setCellValue("驳回金额：");
    	returnCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    	
    	HSSFCell returnValueCell = totalRow.createCell(7);
    	returnValueCell.setCellStyle(valueStyle);
    	returnValueCell.setCellValue(map.get("3") == null ? "0万" : map.get("3").doubleValue()+"万");
    	returnValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    	
    	HSSFCell noreportCell = totalRow.createCell(8);
    	noreportCell.setCellStyle(titleStyle);
    	noreportCell.setCellValue("未上报：");
    	noreportCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    	
    	HSSFCell noreportValueCell = totalRow.createCell(9);
    	noreportValueCell.setCellStyle(valueStyle);
    	noreportValueCell.setCellValue(map.get("0") == null ? "0万" : map.get("0").doubleValue()+"万");
    	noreportValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    	
    	HSSFCell cancelreportCell = totalRow.createCell(10);
    	cancelreportCell.setCellStyle(titleStyle);
    	cancelreportCell.setCellValue("作废金额：");
    	cancelreportCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    	
    	HSSFCell cancelreportValueCell = totalRow.createCell(11);
    	cancelreportValueCell.setCellStyle(valueStyle);
    	cancelreportValueCell.setCellValue(map.get("4") == null ? "0万" : map.get("4").doubleValue()+"万");
    	cancelreportValueCell.setCellType(HSSFCell.CELL_TYPE_STRING);
    	
    }
    
    /**表头样式**/
    public HSSFCellStyle getHeadStyle(HSSFWorkbook workbook){
    	HSSFCellStyle headerStyle = workbook.createCellStyle();
		HSSFFont font = workbook.createFont();  
		font.setColor(HSSFColor.BLACK.index);  
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 粗体  
		font.setFontHeightInPoints((short)12);
		headerStyle.setFont(font);  
		headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中  
		headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中 
		return headerStyle;
    }
    
    /**内容样式**/
    public HSSFCellStyle getBodyStyle(HSSFWorkbook workbook){
    	HSSFCellStyle bodyStyle = workbook.createCellStyle();
		HSSFFont bodyFont = workbook.createFont();
		bodyFont.setColor(HSSFColor.BLACK.index);
		bodyFont.setFontHeightInPoints((short)11);
		bodyStyle.setFont(bodyFont);
		bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中  
		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中 
		return bodyStyle;
    }
    
    /**内容样式**/
    public HSSFCellStyle getCountTitleStyle(HSSFWorkbook workbook){
    	HSSFCellStyle bodyStyle = workbook.createCellStyle();
		HSSFFont bodyFont = workbook.createFont();
		bodyFont.setColor(HSSFColor.BLACK.index);
		bodyFont.setFontHeightInPoints((short)12);
		bodyStyle.setFont(bodyFont);
		bodyStyle.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 水平居中  
		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中 
		return bodyStyle;
    }
    
    /**内容样式**/
    public HSSFCellStyle getCountValueStyle(HSSFWorkbook workbook){
    	HSSFCellStyle bodyStyle = workbook.createCellStyle();
		HSSFFont bodyFont = workbook.createFont();
		bodyFont.setColor(HSSFColor.RED.index);
		bodyFont.setFontHeightInPoints((short)12);
		bodyStyle.setFont(bodyFont);
		bodyStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平居中  
		bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中 
		return bodyStyle;
    }
    
   	@InitBinder
    public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
