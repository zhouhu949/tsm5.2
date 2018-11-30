package com.qftx.tsm.report.scontrol;

import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.util.DateUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.tsm.main.service.MainService;
import com.qftx.tsm.report.dto.SilenceCustDto;
import com.qftx.tsm.report.service.SilenceCustService;
import com.qftx.tsm.rest.service.CallRecordService;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.*;

/**
 * ��Ĭ�ͻ�ͳ�� User�� bxl Date�� 2015/12/18 Time�� 15:29
 */
@Controller
@RequestMapping(value = "/report/silenceCust")
public class SilenceCustAction {
	private static final Logger logger = Logger.getLogger(LossCustAction.class.getName());
	private static String silentSheetTitle = "客户沉默率统计";
	private static final String[] silentHeader = { "日期", "签约客户总数（个）", "沉默客户总数（个）", "本月沉默客户数（个）", "本月到期客户数（个）", "客户沉默率" };
	private static final String[] silentDetailHeader = { "部门成员", "签约客户总数（个）", "沉默客户总数（个）", "唤醒客户数(个)", "本月沉默客户数(个)", "本月唤醒客户数(个)", "客户沉默率", "客户唤醒率" };
	@Autowired
	private UserService userService;
	@Autowired
	private CallRecordService callRecordService;
	@Autowired
	private SilenceCustService silenceCustService;
	@Autowired
	private MainService mainService;

	@RequestMapping()
	public String list(HttpServletRequest request, HttpServletResponse response, String startTime, String endTime) throws Exception {
		try {
			startTime = (startTime == null || "".equals(startTime)) ? DateUtil.getCurrMonth(DateUtil.halfYearFirstDate(new Date())) : startTime;
			endTime = (endTime == null || "".equals(endTime)) ? DateUtil.getCurrMonth(new Date()) : endTime;
			ShiroUser user = ShiroUtil.getShiroUser();
			Map<String, String> map = new HashMap<String, String>();
			map.put("orgId", user.getOrgId());
			map.put("account", user.getAccount());
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			List<SilenceCustDto> list = new ArrayList<SilenceCustDto>();
			list = silenceCustService.getSilentList(map);
			request.setAttribute("list", list);
			request.setAttribute("startTime", startTime);
			request.setAttribute("endTime", endTime);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysRunException(e);
		}
		return "report/silenceCust";
	}


	@RequestMapping(value = "silentDetail")
	public String silentDetail(HttpServletRequest request, HttpServletResponse response, String currDate, String deptId) throws Exception {
		ShiroUser user = ShiroUtil.getShiroUser();
		deptId = deptId == null ? "" : deptId;
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgId", user.getOrgId());
		map.put("account", user.getAccount());
		map.put("currDate", currDate);
		map.put("deptId", deptId);
		try {
			List<TeamGroupBean> teamGroups = mainService.queryManageGroupList(user.getAccount(),user.getOrgId());
			request.setAttribute("teamGroups", teamGroups);
			List<SilenceCustDto> list = new ArrayList<SilenceCustDto>();
			list = silenceCustService.getSilentDetailList(map);
			request.setAttribute("list", list);
			request.setAttribute("deptId", deptId);
			request.setAttribute("currDate", currDate);
			;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysRunException(e);
		}
		return "report/silentDetailCust";
	}

	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response,String startTime,String endTime,String deptId) {
		try {
			startTime = (startTime == null || "".equals(startTime)) ? DateUtil.getCurrMonth(new Date()) : startTime;
			endTime = (endTime == null || "".equals(endTime)) ? DateUtil.getCurrMonth(DateUtil.halfYearFirstDate(new Date())) : endTime;
			ShiroUser user = ShiroUtil.getShiroUser();
			Map<String, String> map = new HashMap<String, String>();
			map.put("orgId", user.getOrgId());
			map.put("account", user.getAccount());
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("deptId", deptId);
			HSSFWorkbook workbook = new HSSFWorkbook();
			/** 表头样式 **/
			HSSFCellStyle headerStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.BLACK.index);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 粗体
			font.setFontHeightInPoints((short) 12);
			headerStyle.setFont(font);
			headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
			headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
			/** 内容样式 **/
			HSSFCellStyle bodyStyle = workbook.createCellStyle();
			HSSFFont bodyFont = workbook.createFont();
			bodyFont.setColor(HSSFColor.BLACK.index);
			bodyFont.setFontHeightInPoints((short) 11);
			bodyStyle.setFont(bodyFont);
			bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
			bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
			/** 客户沉默率 **/
			HSSFSheet resConSheet = workbook.createSheet(silentSheetTitle);
			List<SilenceCustDto> resDtos = silenceCustService.getSilentList(map);
			createSilentSheet(resDtos, resConSheet, headerStyle, bodyStyle);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + DateUtil.formatDate(new Date(), "yyyyMMdd") + "团队今日数据.xls");
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			throw new SysRunException(e);
		}
	}

	/**
	 * 创建联系结果
	 * 
	 * @param dtos
	 * @param sheet
	 * @param headerStyle
	 * @param bodyStyle
	 * @create 2016年1月21日 下午3:12:47 lixing
	 * @history
	 */
	public void createSilentSheet(List<SilenceCustDto> dtos, HSSFSheet sheet, HSSFCellStyle headerStyle, HSSFCellStyle bodyStyle) {
		HSSFRow rowm = sheet.createRow(0);
		for (int i = 0; i < silentHeader.length; i++) {
			String head = silentHeader[i];
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(head);
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, 32 * 160);
		}
		String currDate = "";
		int signTotal = 0;
		int silentTotal = 0;
		int expireCustTotal = 0;
		int addSilentTotal = 0;
		double silentRate = 0;
		for (int i = 0; i < dtos.size(); i++) {
			SilenceCustDto dto = dtos.get(i);
			HSSFRow datarow = sheet.createRow(i + 1);
			currDate = dto.getCurrDate();
			signTotal += dto.getSignTotal();
			silentTotal += dto.getSilentTotal();
			expireCustTotal += dto.getExpireCustTotal();
			addSilentTotal += dto.getAddSilentTotal();
			silentRate += dto.getSilentRate();
			for (int j = 0; j < silentHeader.length; j++) {
				HSSFCell cell = datarow.createCell(j);
				cell.setCellStyle(bodyStyle);
				if (j == 0) {
					cell.setCellValue(currDate);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 1) {
					cell.setCellValue(dto.getSignTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else if (j == 2) {
					cell.setCellValue(dto.getSilentTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else if (j == 3) {
					cell.setCellValue(dto.getAddSilentTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else if (j == 4) {
					cell.setCellValue(dto.getExpireCustTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else {
					cell.setCellValue(dto.getSilentRate()+"%");
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
			}
			// 合并
//			HSSFRow totalRow = sheet.createRow(dtos.size() + 1);
//			HSSFCell totalCell = totalRow.createCell(0);
//			totalCell.setCellStyle(headerStyle);
			// sheet.addMergedRegion(new CellRangeAddress(dtos.size()+1,
			// dtos.size()+1, 0, 1));
//			totalCell.setCellValue("合计");
//			HSSFCell totalCell1 = totalRow.createCell(1);
//			totalCell1.setCellStyle(headerStyle);
//			totalCell1.setCellValue(signTotal);
//			totalCell1.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//			HSSFCell totalCell2 = totalRow.createCell(2);
//			totalCell2.setCellStyle(headerStyle);
//			totalCell2.setCellValue(silentTotal);
//			totalCell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//
//			HSSFCell totalCell3 = totalRow.createCell(3);
//			totalCell3.setCellStyle(headerStyle);
//			totalCell3.setCellValue(addSilentTotal);
//			totalCell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//
//			HSSFCell totalCell4 = totalRow.createCell(4);
//			totalCell4.setCellStyle(headerStyle);
//			totalCell4.setCellValue(expireCustTotal);
//			totalCell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//
//			HSSFCell totalCell5 = totalRow.createCell(5);
//			totalCell5.setCellStyle(headerStyle);
//			totalCell5.setCellValue(silentRate);
//			totalCell5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		}
	}

	@RequestMapping("/exportDetail")
	public void exportDetail(HttpServletRequest request, HttpServletResponse response, String deptId, String currDate) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			deptId = deptId == null ? "" : deptId;
			Map<String, String> map = new HashMap<String, String>();
			map.put("orgId", user.getOrgId());
			map.put("account", user.getAccount());
			map.put("deptId", deptId);
			map.put("currDate", currDate);
			HSSFWorkbook workbook = new HSSFWorkbook();
			/** 表头样式 **/
			HSSFCellStyle headerStyle = workbook.createCellStyle();
			HSSFFont font = workbook.createFont();
			font.setColor(HSSFColor.BLACK.index);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 粗体
			font.setFontHeightInPoints((short) 12);
			headerStyle.setFont(font);
			headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
			headerStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
			/** 内容样式 **/
			HSSFCellStyle bodyStyle = workbook.createCellStyle();
			HSSFFont bodyFont = workbook.createFont();
			bodyFont.setColor(HSSFColor.BLACK.index);
			bodyFont.setFontHeightInPoints((short) 11);
			bodyStyle.setFont(bodyFont);
			bodyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
			bodyStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
			/** 客户沉默率 明细 **/
			List<SilenceCustDto> list = silenceCustService.getSilentDetailList(map);
			HSSFSheet resConSheet = workbook.createSheet(silentSheetTitle);
			if (list != null && list.size() > 0) {
				silentSheetTitle = silentSheetTitle + "(" + list.get(0).getDeptName() + currDate + ")";
				createSilentDetailSheet(list, resConSheet, headerStyle, bodyStyle);
			} else {
				List<SilenceCustDto> resDtos = new ArrayList<SilenceCustDto>();
				createSilentDetailSheet(resDtos, resConSheet, headerStyle, bodyStyle);
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "attachment;filename=" + DateUtil.formatDate(new Date(), "yyyyMMdd") + "团队今日数据.xls");
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (Exception e) {
			throw new SysRunException(e);
		}
	}

	/**
	 * 创建联系结果
	 * 
	 * @param dtos
	 * @param sheet
	 * @param headerStyle
	 * @param bodyStyle
	 * @create 2016年1月21日 下午3:12:47 lixing
	 * @history
	 */
	public void createSilentDetailSheet(List<SilenceCustDto> dtos, HSSFSheet sheet, HSSFCellStyle headerStyle, HSSFCellStyle bodyStyle) {
		HSSFRow rowm = sheet.createRow(0);
		for (int i = 0; i < silentDetailHeader.length; i++) {
			String head = silentDetailHeader[i];
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(head);
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, 32 * 160);
		}
		int signTotal = 0;
		int silentTotal = 0;
		int addSilentTotal = 0;
		double silentRate = 0;
		String account = "";
		int wakeTotal = 0;
		int addWakeTotal = 0;
		double wakeRate = 0;

		for (int i = 0; i < dtos.size(); i++) {
			SilenceCustDto dto = dtos.get(i);
			HSSFRow datarow = sheet.createRow(i + 1);
			account = dto.getAccount();
			signTotal += dto.getSignTotal();
			silentTotal += dto.getSilentTotal();
			addSilentTotal += dto.getAddSilentTotal();
			silentRate = dto.getSilentRate();
			wakeTotal += dto.getWakeTotal();
			addWakeTotal += dto.getAddSilentTotal();
			wakeRate = dto.getWakeRate();
			for (int j = 0; j < silentDetailHeader.length; j++) {
				HSSFCell cell = datarow.createCell(j);
				cell.setCellStyle(bodyStyle);
				if (j == 0) {
					cell.setCellValue(account);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 1) {
					cell.setCellValue(dto.getSignTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else if (j == 2) {
					cell.setCellValue(dto.getSilentTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else if (j == 3) {
					cell.setCellValue(dto.getWakeTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else if (j == 4) {
					cell.setCellValue(dto.getAddSilentTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else if (j == 5) {
					cell.setCellValue(dto.getAddWakeTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else if (j == 6) {
					cell.setCellValue(dto.getSilentRate() + "%");
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else {
					cell.setCellValue(dto.getWakeRate() + "%");
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
			}
			// 合并
//			HSSFRow totalRow = sheet.createRow(dtos.size() + 1);
//			HSSFCell totalCell = totalRow.createCell(0);
//			totalCell.setCellStyle(headerStyle);
			// sheet.addMergedRegion(new CellRangeAddress(dtos.size()+1,
			// dtos.size()+1, 0, 1));
//			totalCell.setCellValue("合计");
//
//			HSSFCell totalCell1 = totalRow.createCell(1);
//			totalCell1.setCellStyle(headerStyle);
//			totalCell1.setCellValue(signTotal);
//			totalCell1.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//			HSSFCell totalCell2 = totalRow.createCell(2);
//			totalCell2.setCellStyle(headerStyle);
//			totalCell2.setCellValue(silentTotal);
//			totalCell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//
//			HSSFCell totalCell3 = totalRow.createCell(3);
//			totalCell3.setCellStyle(headerStyle);
//			totalCell3.setCellValue(wakeTotal);
//			totalCell3.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//
//			HSSFCell totalCell4 = totalRow.createCell(4);
//			totalCell4.setCellStyle(headerStyle);
//			totalCell4.setCellValue(addSilentTotal);
//			totalCell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//
//			HSSFCell totalCell5 = totalRow.createCell(5);
//			totalCell5.setCellStyle(headerStyle);
//			totalCell5.setCellValue(addWakeTotal);
//			totalCell5.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
//
//			HSSFCell totalCell6 = totalRow.createCell(6);
//			totalCell6.setCellStyle(headerStyle);
//			totalCell6.setCellValue(silentRate + "%");
//			totalCell6.setCellType(HSSFCell.CELL_TYPE_STRING);
//
//			HSSFCell totalCell7 = totalRow.createCell(7);
//			totalCell7.setCellStyle(headerStyle);
//			totalCell7.setCellValue(wakeRate + "%");
//			totalCell7.setCellType(HSSFCell.CELL_TYPE_STRING);
		}
	}
}