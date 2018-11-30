package com.qftx.tsm.report.scontrol;

import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.util.SysRunException;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.report.dto.CustInfoNalysisDto;
import com.qftx.tsm.report.service.CustInfoNalysisService;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ��Դ��Ϣ���� User�� bxl Date�� 2015/12/18 Time�� 15:29
 */
@Controller
@RequestMapping(value = "/report/custInfoNalysis")
public class CustInfoNalysisAction {
	private static final Logger logger = Logger.getLogger(LossCustAction.class.getName());
	private static final String custInfoSheetTitle = "资源信息分析";
	private static final String[] custInfoHeader = { "资源组", "资源总数（个）", "资源拨通人数（个）", "资源拨通率", "有效通话人数（个）", "资源有效通话率", "转意向人数（个）", "意向转化率 ", "签约客户数（个）", "签约转化率" };
	@Autowired
	private UserService userService;
	@Autowired
	private CallRecordService callRecordService;
	@Autowired
	private CustInfoNalysisService custInfoNalysisService;
	@Autowired
	private ComResourceGroupService comResourceGroupService;

	@RequestMapping()
	public String list(HttpServletRequest request, HttpServletResponse response, String groupId) throws Exception {
		ShiroUser user = ShiroUtil.getShiroUser();
		groupId = groupId == null ? "" : groupId;
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgId", user.getOrgId());
		map.put("account", user.getAccount());
		map.put("groupId", groupId);
		try {
			List<ResourceGroupBean> groupList = comResourceGroupService.getShareGroupList(map);
			custInfoNalysisService.list(map, request);
			request.setAttribute("groupId", groupId);
			request.setAttribute("groupList", groupList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysRunException(e);
		}
		return "report/custInfoNalysis";
	}

	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response, String groupId) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			groupId = groupId == null ? "" : groupId;
			Map<String, String> map = new HashMap<String, String>();
			map.put("orgId", user.getOrgId());
			map.put("account", user.getAccount());
			map.put("groupId", groupId);
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
			/** 资源信息分析 **/
			HSSFSheet resConSheet = workbook.createSheet(custInfoSheetTitle);
			List<CustInfoNalysisDto> resDtos = custInfoNalysisService.getCustInfoNalysisList(map);
			List<CustInfoNalysisDto> totalCust = custInfoNalysisService.getTotalCustInfoNalysis(map);
			createConSheet(resDtos, totalCust==null?null:totalCust.get(0), resConSheet, headerStyle, bodyStyle);
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
	public void createConSheet(List<CustInfoNalysisDto> dtos, CustInfoNalysisDto totalCust, HSSFSheet sheet, HSSFCellStyle headerStyle, HSSFCellStyle bodyStyle) {
		HSSFRow rowm = sheet.createRow(0);
		for (int i = 0; i < custInfoHeader.length; i++) {
			String head = custInfoHeader[i];
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(head);
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, 32 * 160);
		}
		for (int i = 0; i < dtos.size(); i++) {
			CustInfoNalysisDto dto = dtos.get(i);
			HSSFRow datarow = sheet.createRow(i + 1);
			for (int j = 0; j < custInfoHeader.length; j++) {
				HSSFCell cell = datarow.createCell(j);
				cell.setCellStyle(bodyStyle);
				if (j == 0) {
					cell.setCellValue(dto.getGroupName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 1) {
					cell.setCellValue(dto.getResTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 2) {
					cell.setCellValue(dto.getCalledTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else if (j == 3) {
					cell.setCellValue(dto.getCalledRate() + "%");
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 4) {
					cell.setCellValue(dto.getEffectCallTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else if (j == 5) {
					cell.setCellValue(dto.getEffectCallRate() + "%");
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 6) {
					cell.setCellValue(dto.getWillTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else if (j == 7) {
					cell.setCellValue(dto.getWillRate() + "%");
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 8) {
					cell.setCellValue(dto.getSignTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else {
					cell.setCellValue(dto.getSignRate() + "%");
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
			}
		}
		// 合并
		HSSFRow totalRow = sheet.createRow(dtos.size() + 1);
		HSSFCell totalCell = totalRow.createCell(0);
		totalCell.setCellStyle(headerStyle);
		// sheet.addMergedRegion(new CellRangeAddress(dtos.size()+1,
		// dtos.size()+1, 0, 1));
		totalCell.setCellValue("合计");
		HSSFCell totalCell1 = totalRow.createCell(1);
		totalCell1.setCellStyle(headerStyle);
		totalCell1.setCellValue(totalCust.getResTotal());
		totalCell1.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		HSSFCell totalCell2 = totalRow.createCell(2);
		totalCell2.setCellStyle(headerStyle);
		totalCell2.setCellValue(totalCust.getCalledTotal());
		totalCell2.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

		HSSFCell totalCell3 = totalRow.createCell(3);
		totalCell3.setCellStyle(headerStyle);
		totalCell3.setCellValue(totalCust.getCalledRate()+ "%");
		totalCell3.setCellType(HSSFCell.CELL_TYPE_STRING);

		HSSFCell totalCell4 = totalRow.createCell(4);
		totalCell4.setCellStyle(headerStyle);
		totalCell4.setCellValue(totalCust.getEffectCallTotal());
		totalCell4.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

		HSSFCell totalCell5 = totalRow.createCell(5);
		totalCell5.setCellStyle(headerStyle);
		totalCell5.setCellValue(totalCust.getEffectCallRate()+ "%");
		totalCell5.setCellType(HSSFCell.CELL_TYPE_STRING);

		HSSFCell totalCell6 = totalRow.createCell(6);
		totalCell6.setCellStyle(headerStyle);
		totalCell6.setCellValue(totalCust.getWillTotal());
		totalCell6.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

		HSSFCell totalCell7 = totalRow.createCell(7);
		totalCell7.setCellStyle(headerStyle);
		totalCell7.setCellValue(totalCust.getWillRate()+ "%");
		totalCell7.setCellType(HSSFCell.CELL_TYPE_STRING);

		HSSFCell totalCell8 = totalRow.createCell(8);
		totalCell8.setCellStyle(headerStyle);
		totalCell8.setCellValue(totalCust.getSignTotal());
		totalCell8.setCellType(HSSFCell.CELL_TYPE_NUMERIC);

		HSSFCell totalCell9 = totalRow.createCell(9);
		totalCell9.setCellStyle(headerStyle);
		totalCell9.setCellValue(totalCust.getSignRate()+ "%");
		totalCell9.setCellType(HSSFCell.CELL_TYPE_STRING);
	}
}
