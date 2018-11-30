package com.qftx.tsm.report.scontrol;

import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.contract.dto.ContractOrderDto;
import com.qftx.tsm.contract.service.ContractService;
import com.qftx.tsm.follow.service.CustSaleChanceService;
import com.qftx.tsm.main.service.MainInfoService;
import com.qftx.tsm.main.service.MainService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.plan.user.day.service.PlanUserDayResourceService;
import com.qftx.tsm.plan.user.day.service.PlanUserdayWillcustService;
import com.qftx.tsm.report.dto.CallReportDto;
import com.qftx.tsm.report.dto.TeamTodayContractDto;
import com.qftx.tsm.report.service.CallReportService;
import com.qftx.tsm.report.service.TsmNewWillCountService;
import com.qftx.tsm.rest.service.CallRecordService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * User�� bxl
 * Date�� 2015/12/18
 * Time�� 15:29
 */
@Controller
@RequestMapping(value = "/report/teamDayData")
public class TeamDayDataAction {
    private static final Logger logger = Logger.getLogger(TeamDayDataAction.class.getName());
    private static final String callSheetTitle = "今日呼叫数据";
    private static final String resConSheetTitle = "今日资源联系结果";
    private static final String intConSheetTitle = "今日意向客户联系结果";
    private static final String orderSheetTitle = "今日交易结果";
    private static final String[] callHeader = {"小组成员","所属小组","呼出已接（次）","呼出总数（次）","有效呼叫（次）","呼出时长（分钟）","呼入时长（分钟）","计费时长（分钟）","行动完成率"};
    private static final String[] resConHeader = {"小组成员","所属小组","计划联系数（个）","实际联系数（个）","添加资源数（个）","转签约（个）","转意向（个）","转公海（个）","联系无变化（个）","计划未联系（个）"};
    private static final String[] custConHeader = {"小组成员","所属小组","计划联系数（个）","实际联系数（个）","转签约（个）","意向变更（个）","转公海（个）","联系无变化（个）","计划未联系（个）"};
    private static final String[] orderHeader = {"小组成员","所属小组","新增签约数（个）","新增订单数（个）","回款金额（元）","签单金额（元）","预期签单金额（元）"};
    @Autowired
    private UserService userService;
    @Autowired
    private CallRecordService callRecordService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private MainService mainService;
    @Autowired
    private MainInfoService mainInfoService;
    @Autowired
    private PlanUserDayResourceService planUserDayResourceService;
    @Autowired
    private PlanUserdayWillcustService planUserdayWillcustService;
    @Autowired
    private CallReportService callReportService;
    @Autowired
    private CachedService cachedService;
    @Autowired
    private TsmNewWillCountService tsmNewWillCountService;
    @Autowired
    private CustSaleChanceService custSaleChanceService;
    
    @RequestMapping()
    public String list(HttpServletRequest request, HttpServletResponse response,CallReportDto callReportDto,String groupIdStr) throws Exception {
        try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<TeamGroupBean> teamGroups = mainService.queryManageGroupList(user.getAccount(),user.getOrgId());
			int timeLength = getTimeElngth(user.getOrgId());
			request.setAttribute("timeLength", timeLength);
			request.setAttribute("teamGroups", teamGroups);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
        return "report/teamDayData";
    }
    
    
    @ResponseBody
    @RequestMapping("/callData")
    public List<CallReportDto> callData(HttpServletRequest request, String groupIds){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		List<CallReportDto> list = callReportService.getTeamDayCallReport(user.getOrgId(),user.getAccount(),StringUtils.isNotBlank(groupIds) ? Arrays.asList(groupIds.split(",")) : null);
    		analy(list, user.getOrgId());
    		return list;
		} catch (Exception e) {
			logger.error("小组今日通话数据异常！",e);
			return null;
		}
    }
    
    /** 
     * 计算行动完成率<br />
     * 计算公式：<b>{(每日呼出已接次数/每日呼出已接次数达标值)*接通次数指标系数+（每日通话时长/每日通话时长达标值）*通话时长指标系数}乘以100%</b>
     * @create  2017年7月11日 下午2:26:03 lixing
     * @history  
     */
    public void analy(List<CallReportDto> reports,String orgId){
    	//每日呼出指数达标值 默认值100
    	int callNumPoint = 100;
    	List<DataDictionaryBean> callNumPointDirs = cachedService.getDirList(AppConstant.DATA_50008, orgId);
    	if(callNumPointDirs.size() > 0 && StringUtils.isNotBlank(callNumPointDirs.get(0).getDictionaryValue())){
    		callNumPoint = Integer.parseInt(callNumPointDirs.get(0).getDictionaryValue());
    	}
    	//每日呼出已接次数达标值 默认值200
    	int callTimePoint = 200;
    	List<DataDictionaryBean> callTimePointDirs = cachedService.getDirList(AppConstant.DATA_50005, orgId);
    	if(callTimePointDirs.size() > 0 && StringUtils.isNotBlank(callTimePointDirs.get(0).getDictionaryValue())){
    		callTimePoint = Integer.parseInt(callTimePointDirs.get(0).getDictionaryValue());
    	}
    	
    	// 行动完成率基数设置 呼入时长
    	String callInTime4Point = "0";
    	List<DataDictionaryBean> callInTimePointDirs = cachedService.getDirList(AppConstant.DATA_50006, orgId);
    	if(callInTimePointDirs.size() > 0 && StringUtils.isNotBlank(callInTimePointDirs.get(0).getDictionaryValue())){
    		callInTime4Point = callInTimePointDirs.get(0).getDictionaryValue();
    	}
    	
    	// 行动完成率基数设置 呼出时长
    	String callOutTime4Point = "0";
    	List<DataDictionaryBean> callOutTimePointDirs = cachedService.getDirList(AppConstant.DATA_50007, orgId);
    	if(callOutTimePointDirs.size() > 0 && StringUtils.isNotBlank(callOutTimePointDirs.get(0).getDictionaryValue())){
    		callOutTime4Point = callOutTimePointDirs.get(0).getDictionaryValue();
    	}
    	
    	//呼出已结次数达标值
    	float callNumStandard = 1f;
    	//通话时长达标值
    	float callTimeStandard = 1f;

    	List<DataDictionaryBean> stanrdPowerDirs = cachedService.getDirList(AppConstant.DATA_50004, orgId);
    	if(stanrdPowerDirs.size() > 0 && StringUtils.isNotBlank(stanrdPowerDirs.get(0).getDictionaryValue()) && stanrdPowerDirs.get(0).getDictionaryValue().equals("1")){
    		callNumStandard = 0.5f;
    		callTimeStandard = 0.5f;
    		
    		List<DataDictionaryBean> callNumStandardDirs = cachedService.getDirList(AppConstant.DATA_50010, orgId);
    		if(callNumStandardDirs.size() > 0 && StringUtils.isNotBlank(callNumStandardDirs.get(0).getDictionaryValue())){
    			callNumStandard = Float.parseFloat(callNumStandardDirs.get(0).getDictionaryValue());
    		}
    		
    		List<DataDictionaryBean> callTimeStandardDirs = cachedService.getDirList(AppConstant.DATA_50009, orgId);
    		if(callTimeStandardDirs.size() > 0 && StringUtils.isNotBlank(callTimeStandardDirs.get(0).getDictionaryValue())){
    			callTimeStandard = Float.parseFloat(callTimeStandardDirs.get(0).getDictionaryValue());
    		}
    	}
    	
    	for(CallReportDto bean : reports){
    		int callTimeLength = 0;
    		if(callInTime4Point.equals("1")){
    			callTimeLength+=bean.getCallinTime();
    		}
    		if(callOutTime4Point.equals("1")){
    			callTimeLength+=bean.getCalloutTime();
    		}
    		float rate = bean.getCalloutAnswer()*1.0f/callNumPoint*callNumStandard + callTimeLength*1.0f/callTimePoint*callTimeStandard;
    		NumberFormat percentFormat = NumberFormat.getPercentInstance();
    		percentFormat.setMaximumFractionDigits(2); //最大小数位数 
    		String acr = percentFormat.format(rate);
    		bean.setActionCompletionRate(acr);
    	}
    	
    }
    
    @RequestMapping("/resContact")
    public String contact(HttpServletRequest request,HttpServletResponse response,TeamTodayContractDto contractDto,String groupIdStr){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		contractDto.setAccount(user.getAccount());
    		contractDto.setOrgId(user.getOrgId());
    		contractDto.setType("0");
    		contractDto.setGroupIds(StringUtils.isNotBlank(groupIdStr) ? Arrays.asList(groupIdStr.split(",")) : null);
    		List<TeamTodayContractDto> resDtos = mainInfoService.findTeamTodayContractReportListPage(contractDto);
    		List<TeamGroupBean> teamGroups = mainService.queryManageGroupList(user.getAccount(),user.getOrgId());
			int timeLength = getTimeElngth(user.getOrgId());
			request.setAttribute("timeLength", timeLength);
			request.setAttribute("teamGroups", teamGroups);
    		request.setAttribute("resDtos", resDtos);
    		request.setAttribute("contractDto", contractDto);
    		request.setAttribute("groupIdStr", groupIdStr);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
    	return "report/teamDayDataContact";
    }
    
    public List<String> getAccounts(List<TeamTodayContractDto> resDtos){
    	List<String> accounts = new ArrayList<String>();
    	for(TeamTodayContractDto dto : resDtos) accounts.add(dto.getAccount());
    	return accounts;
    }
    
    @RequestMapping("/custContact")
    public String custContact(HttpServletRequest request,HttpServletResponse response,TeamTodayContractDto contractDto,String groupIdStr){
    	try {
			ShiroUser user = ShiroUtil.getShiroUser();
			contractDto.setAccount(user.getAccount());
    		contractDto.setOrgId(user.getOrgId());
    		contractDto.setType("1");
    		contractDto.setGroupIds(StringUtils.isNotBlank(groupIdStr) ? Arrays.asList(groupIdStr.split(",")) : null);
    		List<TeamTodayContractDto> intDtos = mainInfoService.findTeamTodayContractReportListPage(contractDto);
    		List<TeamGroupBean> teamGroups = mainService.queryManageGroupList(user.getAccount(),user.getOrgId());
			int timeLength = getTimeElngth(user.getOrgId());
			request.setAttribute("timeLength", timeLength);
			request.setAttribute("teamGroups", teamGroups);
    		request.setAttribute("intDtos", intDtos);
    		request.setAttribute("contractDto", contractDto);
    		request.setAttribute("groupIdStr", groupIdStr);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
    	return "report/teamDayDataCustContact";
    }
    
    @RequestMapping("/deals")
    public String deals(HttpServletRequest request,HttpServletResponse response,ContractOrderDto contractOrderDto,String groupIdStr){
    	try {
			ShiroUser user = ShiroUtil.getShiroUser();
			contractOrderDto.setOrgId(user.getOrgId());
			contractOrderDto.setUserId(user.getAccount());
			contractOrderDto.setGroupIds(StringUtils.isNotBlank(groupIdStr) ? Arrays.asList(groupIdStr.split(",")) : null);
			//排序
			if(StringUtils.isBlank(contractOrderDto.getOrderColumn())){
				contractOrderDto.setOrderKey("W.MEMBER_ACC ASC");
			}else{
				contractOrderDto.setOrderKey(contractOrderDto.getOrderColumn()+" "+contractOrderDto.getOrderType()+",W.MEMBER_ACC ASC");
			}
			List<ContractOrderDto> orderDtos = contractService.findTeamTodayReportListPage(contractOrderDto);
//			if(orderDtos.size() > 0){
//				List<String> ownerAccs = new ArrayList<String>();
//				Map<String, BigDecimal> willMap = new HashMap<String, BigDecimal>();
//				for(ContractOrderDto cod : orderDtos) ownerAccs.add(cod.getUserId());
//				List<Map<String, Object>> willList = custSaleChanceService.getTodayWillSignMoneyByAccs(user.getOrgId(), ownerAccs);
//				for(Map<String, Object> wm : willList){
//					BigDecimal willMoeny = BigDecimal.valueOf((long)wm.get("money"));
//					willMap.put(wm.get("ownerAcc").toString(),willMoeny);
//				}
//				for(ContractOrderDto cod : orderDtos){
//					if(willMap.containsKey(cod.getUserId())) cod.setWillSignMoney(willMap.get(cod.getUserId()));
//				}
//			}
			
			List<TeamGroupBean> teamGroups = mainService.queryManageGroupList(user.getAccount(),user.getOrgId());
			int timeLength = getTimeElngth(user.getOrgId());
			request.setAttribute("timeLength", timeLength);
			request.setAttribute("teamGroups", teamGroups);
			request.setAttribute("orderDtos", orderDtos);
			request.setAttribute("contractOrderDto", contractOrderDto);
			request.setAttribute("groupIdStr", groupIdStr);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
    	return "report/teamDayDataDeals";
    }
    
    public int getTimeElngth(String orgId) {
    	int timeElngth = 0;
    	if(cachedService.getDirList(AppConstant.DATA16, orgId).size()>0){
			String isEffect = cachedService.getDirList(AppConstant.DATA16, orgId).get(0).getDictionaryValue();
			if ("1".equals(isEffect)) {
				timeElngth = new Integer(cachedService.getDirList(AppConstant.DATA04, orgId).get(0).getDictionaryValue());
			}
    	}
		return timeElngth;
	}
    
    @RequestMapping("/export")
    @ResponseBody
    public void export(HttpServletRequest request,HttpServletResponse response){
    	try {
    		ShiroUser user = ShiroUtil.getShiroUser();
    		String groupIds = request.getParameter("groupIds");
    		String expType = request.getParameter("expType");
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
    		switch (expType) {
			case "1":
				/**今日呼叫数据**/
	    		HSSFSheet callSheet = workbook.createSheet(callSheetTitle);
	    		List<CallReportDto> callReportDtos = callReportService.getTeamDayCallReport(user.getOrgId(), user.getAccount(), StringUtils.isNotBlank(groupIds) ? Arrays.asList(groupIds.split(",")) : null);
	    		analy(callReportDtos, user.getOrgId());
	    		createCallSheet(callReportDtos,callSheet,headerStyle,bodyStyle);
	    		expName="今日呼叫数据";
				break;
			case "2":
				/**今日资源联系结果**/
	    		HSSFSheet resConSheet = workbook.createSheet(resConSheetTitle);
	    		List<TeamTodayContractDto> resDtos = mainInfoService.findTeamTodayContractReport(user.getAccount(), user.getOrgId(), "0",StringUtils.isNotBlank(groupIds) ? Arrays.asList(groupIds.split(",")) : null);
	    		createResConSheet(resDtos,resConSheet,headerStyle,bodyStyle,resConHeader);
	    		expName="今日资源联系结果";
				break;
			case "3":
				/**今日意向客户联系结果**/
	    		HSSFSheet intConSheet = workbook.createSheet(intConSheetTitle);
	    		List<TeamTodayContractDto> intDtos = mainInfoService.findTeamTodayContractReport(user.getAccount(), user.getOrgId(), "1",StringUtils.isNotBlank(groupIds) ? Arrays.asList(groupIds.split(",")) : null);
	    		createCustConSheet(intDtos,intConSheet,headerStyle,bodyStyle,custConHeader);
	    		expName="今日意向客户联系结果";
				break;
			case "4":
				/**今日交易结果**/
	    		HSSFSheet orderSheet = workbook.createSheet(orderSheetTitle);
	    		List<ContractOrderDto> orderDtos = contractService.findTeamTodayReport(user.getOrgId(), user.getAccount(), StringUtils.isNotBlank(groupIds) ? Arrays.asList(groupIds.split(",")) : null);
//	    		if(orderDtos.size() > 0){
//					List<String> ownerAccs = new ArrayList<String>();
//					Map<String, BigDecimal> willMap = new HashMap<String, BigDecimal>();
//					for(ContractOrderDto cod : orderDtos) ownerAccs.add(cod.getUserId());
//					List<Map<String, Object>> willList = custSaleChanceService.getTodayWillSignMoneyByAccs(user.getOrgId(), ownerAccs);
//					for(Map<String, Object> wm : willList){
//						BigDecimal willMoeny = BigDecimal.valueOf((long)wm.get("money"));
//						willMap.put(wm.get("ownerAcc").toString(),willMoeny);
//					}
//					for(ContractOrderDto cod : orderDtos){
//						if(willMap.containsKey(cod.getUserId())){
//							cod.setWillSignMoney(willMap.get(cod.getUserId()));
//						}else{
//							cod.setWillSignMoney(BigDecimal.valueOf(0));
//						}
//					}
//				}
	    		createOrderSheet(orderDtos,orderSheet,headerStyle,bodyStyle);
	    		expName="今日交易结果";
				break;
			default:
				break;
			}
    		
    		response.setContentType("application/vnd.ms-excel");    
    		response.setHeader("Content-disposition", "attachment;filename="+DateUtil.formatDate(new Date(),"yyyyMMdd")+URLEncoder.encode("团队今日数据.xls","UTF-8"));
            OutputStream ouputStream = response.getOutputStream();
            workbook.write(ouputStream);
            ouputStream.flush();    
            ouputStream.close();
    	} catch (Exception e) {
			throw new SysRunException(e);
		}
    }
    
    
    /** 
     * 创建呼叫数据
     * @param dtos
     * @param sheet
     * @param headerStyle
     * @param bodyStyle 
     * @create  2016年1月26日 下午5:15:53 lixing
     * @history  
     */
    public void createCallSheet(List<CallReportDto> dtos,HSSFSheet sheet,HSSFCellStyle headerStyle,HSSFCellStyle bodyStyle){
    	HSSFRow rowm = sheet.createRow(0);
    	for(int i=0;i<callHeader.length;i++){
			String head = callHeader[i];
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(head);
			cell.setCellStyle(headerStyle);
			if(i == 2){
				sheet.setColumnWidth(i, 32*180);
			}else{
				sheet.setColumnWidth(i, 32*160);
			}
		}
    	Integer tCalloutAnswer = 0;
    	Integer tCalloutTotal = 0;
    	Integer tValidCalls = 0;
    	Integer tRestCalls = 0;
    	Integer tCustCalls = 0;
    	Integer tCalloutTime = 0;
    	Integer tCallinTime = 0;
    	Integer tBillingTime = 0;
    	for(int i=0;i<dtos.size();i++){
    		CallReportDto crd = dtos.get(i);
    		HSSFRow datarow = sheet.createRow(i+1);
    		for(int j=0;j<callHeader.length;j++){
    			HSSFCell cell = datarow.createCell(j);
				cell.setCellStyle(bodyStyle);
				if(j == 0){
					cell.setCellValue(crd.getUserName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j == 1){
					cell.setCellValue(crd.getGroupName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j == 2){
					cell.setCellValue(crd.getCalloutAnswer());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 3){
					cell.setCellValue(crd.getCalloutTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 4){
					cell.setCellValue(crd.getValidCalls());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}/*else if(j == 4){
					cell.setCellValue(crd.getRestCalls());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 5){
					cell.setCellValue(crd.getCustCalls());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 4){
					cell.setCellValue(crd.getVisitNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}*/else if(j == 5){
					cell.setCellValue(crd.getCalloutTime());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 6){
					cell.setCellValue(crd.getCallinTime());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 7){
					cell.setCellValue(crd.getBillingTime());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 8){
					cell.setCellValue(crd.getActionCompletionRate());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
    		}
    		tCalloutAnswer+= crd.getCalloutAnswer();
	    	tCalloutTotal+= crd.getCalloutTotal();
	    	tValidCalls+= crd.getValidCalls();
	    	tRestCalls+= crd.getRestCalls();
	    	tCustCalls+= crd.getCustCalls();
	    	tCalloutTime+= crd.getCalloutTime();
	    	tCallinTime+= crd.getCallinTime();
	    	tBillingTime+= crd.getBillingTime();
    	}
    	
    	//合并
		HSSFRow totalRow = sheet.createRow(dtos.size()+1);
		HSSFCell totalCell = totalRow.createCell(0);
		totalCell.setCellStyle(headerStyle);
		sheet.addMergedRegion(new CellRangeAddress(dtos.size()+1, dtos.size()+1, 0, 1));
		totalCell.setCellValue("合计");
		HSSFCell totalCell2 = totalRow.createCell(2);
		totalCell2.setCellStyle(headerStyle);
		totalCell2.setCellValue(tCalloutAnswer);
		totalCell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell total = totalRow.createCell(3);
		total.setCellStyle(headerStyle);
		total.setCellValue(tCalloutTotal);
		total.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell3 = totalRow.createCell(4);
		totalCell3.setCellStyle(headerStyle);
		totalCell3.setCellValue(tValidCalls);
		totalCell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
//		HSSFCell totalCell4 = totalRow.createCell(4);
//		totalCell4.setCellStyle(headerStyle);
//		totalCell4.setCellValue(tRestCalls);
//		totalCell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//		
//		HSSFCell totalCell5 = totalRow.createCell(5);
//		totalCell5.setCellStyle(headerStyle);
//		totalCell5.setCellValue(tCustCalls);
//		totalCell5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
//		HSSFCell totalCell6 = totalRow.createCell(4);
//		totalCell6.setCellStyle(headerStyle);
//		totalCell6.setCellValue(tVisitNum);
//		totalCell6.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell7 = totalRow.createCell(5);
		totalCell7.setCellStyle(headerStyle);
		totalCell7.setCellValue(tCalloutTime);
		totalCell7.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell8 = totalRow.createCell(6);
		totalCell8.setCellStyle(headerStyle);
		totalCell8.setCellValue(tCallinTime);
		totalCell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell9 = totalRow.createCell(7);
		totalCell9.setCellStyle(headerStyle);
		totalCell9.setCellValue(tBillingTime);
		totalCell9.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell10 = totalRow.createCell(8);
		totalCell10.setCellStyle(headerStyle);
		totalCell10.setCellValue("/");
		totalCell10.setCellType(HSSFCell.CELL_TYPE_STRING);
    }
    
    /** 
     * 创建资源联系结果
     * @param dtos
     * @param sheet
     * @param headerStyle
     * @param bodyStyle 
     * @create  2016年1月21日 下午3:12:47 lixing
     * @history  
     */
    public void createResConSheet(List<TeamTodayContractDto> dtos,HSSFSheet sheet,HSSFCellStyle headerStyle,HSSFCellStyle bodyStyle,String[] conHeader){
    	HSSFRow rowm = sheet.createRow(0);
		for(int i=0;i<conHeader.length;i++){
			String head = conHeader[i];
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(head);
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, 32*160);
		}
		
		Integer hjPlanNum=0;
		Integer hjTotalNum=0;
		Integer addResTotalNum = 0;
		Integer hjSignNum=0;
		Integer hjCustNum=0;
		Integer hjPoolNum=0;
		Integer hjNoNum=0;
		Integer hjNoContactCount=0;
		for(int i=0;i<dtos.size();i++){
			TeamTodayContractDto dto = dtos.get(i);
			HSSFRow datarow = sheet.createRow(i+1);
			hjPlanNum+=dto.getPlanNum();
			hjTotalNum+=dto.getTotalNum();
			addResTotalNum+=dto.getAddResCount();
			hjSignNum+=dto.getSignNum();
			hjCustNum+=dto.getCustNum();
			hjPoolNum+=dto.getPoolNum();
			hjNoNum+=dto.getNoNum();
			hjNoContactCount+=dto.getNoContactCount();
			for(int j=0;j<conHeader.length;j++){
				HSSFCell cell = datarow.createCell(j);
				cell.setCellStyle(bodyStyle);
				if(j == 0){
					cell.setCellValue(dto.getUserName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j == 1){
					cell.setCellValue(dto.getGroupName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j == 2){
					cell.setCellValue(dto.getPlanNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 3){
					cell.setCellValue(dto.getTotalNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 4){
					cell.setCellValue(dto.getAddResCount());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 5){
					cell.setCellValue(dto.getSignNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 6){
					cell.setCellValue(dto.getCustNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 7){
					cell.setCellValue(dto.getPoolNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 8){
					cell.setCellValue(dto.getNoNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else{
					cell.setCellValue(dto.getNoContactCount());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}
			}
		}
		//合并
		HSSFRow totalRow = sheet.createRow(dtos.size()+1);
		HSSFCell totalCell = totalRow.createCell(0);
		totalCell.setCellStyle(headerStyle);
		sheet.addMergedRegion(new CellRangeAddress(dtos.size()+1, dtos.size()+1, 0, 1));
		totalCell.setCellValue("合计");
		HSSFCell totalCell2 = totalRow.createCell(2);
		totalCell2.setCellStyle(headerStyle);
		totalCell2.setCellValue(hjPlanNum);
		totalCell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell3 = totalRow.createCell(3);
		totalCell3.setCellStyle(headerStyle);
		totalCell3.setCellValue(hjTotalNum);
		totalCell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell4 = totalRow.createCell(4);
		totalCell4.setCellStyle(headerStyle);
		totalCell4.setCellValue(addResTotalNum);
		totalCell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell5 = totalRow.createCell(5);
		totalCell5.setCellStyle(headerStyle);
		totalCell5.setCellValue(hjSignNum);
		totalCell5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell6 = totalRow.createCell(6);
		totalCell6.setCellStyle(headerStyle);
		totalCell6.setCellValue(hjCustNum);
		totalCell6.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell7 = totalRow.createCell(7);
		totalCell7.setCellStyle(headerStyle);
		totalCell7.setCellValue(hjPoolNum);
		totalCell7.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell8 = totalRow.createCell(8);
		totalCell8.setCellStyle(headerStyle);
		totalCell8.setCellValue(hjNoNum);
		totalCell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell9 = totalRow.createCell(9);
		totalCell9.setCellStyle(headerStyle);
		totalCell9.setCellValue(hjNoContactCount);
		totalCell9.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    }
    
    
    /** 
     * 创建资意向系结果
     * @param dtos
     * @param sheet
     * @param headerStyle
     * @param bodyStyle 
     * @create  2016年1月21日 下午3:12:47 lixing
     * @history  
     */
    public void createCustConSheet(List<TeamTodayContractDto> dtos,HSSFSheet sheet,HSSFCellStyle headerStyle,HSSFCellStyle bodyStyle,String[] conHeader){
    	HSSFRow rowm = sheet.createRow(0);
		for(int i=0;i<conHeader.length;i++){
			String head = conHeader[i];
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(head);
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, 32*160);
		}
		
		Integer hjPlanNum=0;
		Integer hjTotalNum=0;
		Integer hjSignNum=0;
		Integer hjCustNum=0;
		Integer hjPoolNum=0;
		Integer hjNoNum=0;
		Integer hjNoContactCount=0;
		for(int i=0;i<dtos.size();i++){
			TeamTodayContractDto dto = dtos.get(i);
			HSSFRow datarow = sheet.createRow(i+1);
			hjPlanNum+=dto.getPlanNum();
			hjTotalNum+=dto.getTotalNum();
			hjSignNum+=dto.getSignNum();
			hjCustNum+=dto.getCustNum();
			hjPoolNum+=dto.getPoolNum();
			hjNoNum+=dto.getNoNum();
			hjNoContactCount+=dto.getNoContactCount();
			for(int j=0;j<conHeader.length;j++){
				HSSFCell cell = datarow.createCell(j);
				cell.setCellStyle(bodyStyle);
				if(j == 0){
					cell.setCellValue(dto.getUserName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j == 1){
					cell.setCellValue(dto.getGroupName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j == 2){
					cell.setCellValue(dto.getPlanNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 3){
					cell.setCellValue(dto.getTotalNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 4){
					cell.setCellValue(dto.getSignNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 5){
					cell.setCellValue(dto.getCustNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 6){
					cell.setCellValue(dto.getPoolNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 7){
					cell.setCellValue(dto.getNoNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else{
					cell.setCellValue(dto.getNoContactCount());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}
			}
		}
		//合并
		HSSFRow totalRow = sheet.createRow(dtos.size()+1);
		HSSFCell totalCell = totalRow.createCell(0);
		totalCell.setCellStyle(headerStyle);
		sheet.addMergedRegion(new CellRangeAddress(dtos.size()+1, dtos.size()+1, 0, 1));
		totalCell.setCellValue("合计");
		HSSFCell totalCell2 = totalRow.createCell(2);
		totalCell2.setCellStyle(headerStyle);
		totalCell2.setCellValue(hjPlanNum);
		totalCell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell3 = totalRow.createCell(3);
		totalCell3.setCellStyle(headerStyle);
		totalCell3.setCellValue(hjTotalNum);
		totalCell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell4 = totalRow.createCell(4);
		totalCell4.setCellStyle(headerStyle);
		totalCell4.setCellValue(hjSignNum);
		totalCell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell5 = totalRow.createCell(5);
		totalCell5.setCellStyle(headerStyle);
		totalCell5.setCellValue(hjCustNum);
		totalCell5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell6 = totalRow.createCell(6);
		totalCell6.setCellStyle(headerStyle);
		totalCell6.setCellValue(hjPoolNum);
		totalCell6.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell7 = totalRow.createCell(7);
		totalCell7.setCellStyle(headerStyle);
		totalCell7.setCellValue(hjNoNum);
		totalCell7.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell8 = totalRow.createCell(8);
		totalCell8.setCellStyle(headerStyle);
		totalCell8.setCellValue(hjNoContactCount);
		totalCell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    }
    
    /** 
     * 创建交易结果
     * @param dtos
     * @param sheet
     * @param headerStyle
     * @param bodyStyle 
     * @create  2016年1月21日 下午3:31:53 lixing
     * @history  
     */
    public void createOrderSheet(List<ContractOrderDto> dtos,HSSFSheet sheet,HSSFCellStyle headerStyle,HSSFCellStyle bodyStyle){
    	HSSFRow rowm = sheet.createRow(0);
		for(int i=0;i<orderHeader.length;i++){
			String head = orderHeader[i];
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(head);
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, 32*160);
		}
		Integer hjSignNum = 0;
		Integer hjOrderNum = 0;
		BigDecimal hjMoney = BigDecimal.valueOf(0); 
		BigDecimal hjSignMoney = BigDecimal.valueOf(0); 
		BigDecimal hjWillSignMoney = BigDecimal.valueOf(0); 
		for(int i=0;i<dtos.size();i++){
			ContractOrderDto dto = dtos.get(i);
			HSSFRow datarow = sheet.createRow(i+1);
			hjSignNum+=dto.getSignNum();
			hjOrderNum+=dto.getOrderNum();
			hjMoney = hjMoney.add(dto.getMoney());
			hjSignMoney = hjSignMoney.add(dto.getSignMoney());
			hjWillSignMoney = hjWillSignMoney.add(dto.getWillSignMoney());
			
			for(int j=0;j<orderHeader.length;j++){
				HSSFCell cell = datarow.createCell(j);
				cell.setCellStyle(bodyStyle);
				if(j == 0){
					cell.setCellValue(dto.getUserName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j == 1){
					cell.setCellValue(dto.getGroupName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j == 2){
					cell.setCellValue(dto.getSignNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 3){
					cell.setCellValue(dto.getOrderNum());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}else if(j == 4){
					cell.setCellValue(dto.getMoney().toString());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j == 5){
					cell.setCellValue(dto.getSignMoney().toString());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}else if(j == 6){
					cell.setCellValue(dto.getWillSignMoney().toString());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
			}
		}
		//合并
		HSSFRow totalRow = sheet.createRow(dtos.size()+1);
		HSSFCell totalCell = totalRow.createCell(0);
		totalCell.setCellStyle(headerStyle);
		sheet.addMergedRegion(new CellRangeAddress(dtos.size()+1, dtos.size()+1, 0, 1));
		totalCell.setCellValue("合计");
		HSSFCell totalCell2 = totalRow.createCell(2);
		totalCell2.setCellStyle(headerStyle);
		totalCell2.setCellValue(hjSignNum);
		totalCell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell3 = totalRow.createCell(3);
		totalCell3.setCellStyle(headerStyle);
		totalCell3.setCellValue(hjOrderNum);
		totalCell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		
		HSSFCell totalCell4 = totalRow.createCell(4);
		totalCell4.setCellStyle(headerStyle);
		totalCell4.setCellValue(hjMoney.toString());
		totalCell4.setCellType(HSSFCell.CELL_TYPE_STRING);
		
		HSSFCell totalCell5 = totalRow.createCell(5);
		totalCell5.setCellStyle(headerStyle);
		totalCell5.setCellValue(hjSignMoney.toString());
		totalCell5.setCellType(HSSFCell.CELL_TYPE_STRING);
		
		HSSFCell totalCell6 = totalRow.createCell(6);
		totalCell6.setCellStyle(headerStyle);
		totalCell6.setCellValue(hjWillSignMoney.toString());
		totalCell6.setCellType(HSSFCell.CELL_TYPE_STRING);
    }
    
}
