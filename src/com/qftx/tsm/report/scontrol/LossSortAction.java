package com.qftx.tsm.report.scontrol;

import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.bean.TeamGroupBean;
import com.qftx.base.util.DateUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.main.service.MainService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dao.OptionMapper;
import com.qftx.tsm.report.dto.GroupDto;
import com.qftx.tsm.report.dto.LossSortDetial;
import com.qftx.tsm.report.service.LossSortService;
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
 * ��ʧ�ͻ�ԭ�����ͳ�� User�� bxl Date�� 2015/12/18 Time�� 15:29
 */
@Controller
@RequestMapping(value = "/report/lossSort")
public class LossSortAction {
	private static final Logger logger = Logger.getLogger(LossSortAction.class.getName());
	private static final String lossDetailSheetTitle = "客户流失原因分析";
	private static final String[] lossDetailHeader = { "部门", "	客户名称", "联系人", "签约日期", "合同累计金额（万）", "未联系天数", "上次联系时间", "合同失效日期 ", "所有者" };
	private static String lossSheetTitle = "客户流失原因统计";
	@Autowired
	private UserService userService;
	@Autowired
	private CallRecordService callRecordService;
	@Autowired
	private LossSortService lossSortService;
	@Autowired
	private OptionMapper optionMapper;
	@Autowired
	private ComResourceGroupService comResourceGroupService;
	@Autowired
	private MainService mainService;
	@RequestMapping()
	public String list(HttpServletRequest request, HttpServletResponse response, String startTime, String endTime) throws Exception {
		startTime = (startTime == null || "".equals(startTime)) ? DateUtil.getCurrMonth(DateUtil.halfYearFirstDate(new Date())) : startTime;
		endTime = (endTime == null || "".equals(endTime)) ? DateUtil.getCurrMonth(new Date()) : endTime;
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgId", user.getOrgId());
		map.put("account", user.getAccount());
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<OptionBean> optionList = new ArrayList<OptionBean>();
		List<List<Object>> resultList = new ArrayList<List<Object>>();
		List<Object> totalList = new ArrayList<Object>();
		try {
			optionList = optionMapper.findOptionList(user.getOrgId());
			if (optionList != null && optionList.size() > 0) {
				resultList = lossSortService.getLossSortList(map);
				totalList = lossSortService.getLossSortTotal(map);
			}else{
				return "report/lost_customer_reason_none";
			}
			request.setAttribute("optionList", optionList);
			request.setAttribute("list", resultList);
			request.setAttribute("totalList", totalList);
			request.setAttribute("startTime", startTime);
			request.setAttribute("endTime", endTime);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysRunException(e);
		}
		return "report/lossSort";
	}

	@RequestMapping("/export")
	public void export(HttpServletRequest request, HttpServletResponse response, String startTime, String endTime) {
		try {
			startTime = (startTime == null || "".equals(startTime)) ? DateUtil.getCurrMonth(new Date()) : startTime;
			endTime = (endTime == null || "".equals(endTime)) ? DateUtil.getCurrMonth(DateUtil.halfYearFirstDate(new Date())) : endTime;
			ShiroUser user = ShiroUtil.getShiroUser();
			Map<String, String> map = new HashMap<String, String>();
			map.put("orgId", user.getOrgId());
			map.put("account", user.getAccount());
			map.put("startTime", startTime);
			map.put("endTime", endTime);
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
			List<String> list = new ArrayList<String>();
			List<OptionBean> optionList = optionMapper.findOptionList(user.getOrgId());
			List<Object> totalList = new ArrayList<Object>();
			List<List<Object>> resultList = new ArrayList<List<Object>>();
			if (optionList != null && optionList.size() > 0) {
				list.add("部门");
				for (OptionBean bean : optionList) {
					list.add(bean.getOptionName());
				}
				resultList = lossSortService.getLossSortList(map);
				totalList = lossSortService.getLossSortTotal(map);
			}
			final int size = list.size();
			String[] losstHeader = (String[]) list.toArray(new String[size]);

			HSSFSheet resConSheet = workbook.createSheet(lossSheetTitle);
			createLossSheet(losstHeader, resultList, totalList, resConSheet, headerStyle, bodyStyle);
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
	public void createLossSheet(String[] losstHeader, List<List<Object>> dtos, List<Object> totalList, HSSFSheet sheet, HSSFCellStyle headerStyle,
			HSSFCellStyle bodyStyle) {
		HSSFRow rowm = sheet.createRow(0);
		for (int i = 0; i < losstHeader.length; i++) {
			String head = losstHeader[i];
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(head);
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, 32 * 160);
		}
		for (int i = 0; i < dtos.size(); i++) {
			List<Object> dto = dtos.get(i);
			HSSFRow datarow = sheet.createRow(i + 1);
			for (int j = 0; j < losstHeader.length; j++) {
				HSSFCell cell = datarow.createCell(j);
				cell.setCellStyle(bodyStyle);
				if (j == 0) {
					cell.setCellValue(((GroupDto) dto.get(j)).getDeptName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else {
					cell.setCellValue(((GroupDto) dto.get(j)).getTotal());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				}
			}
		}
		// 合并
		HSSFRow totalRow = sheet.createRow(dtos.size() + 1);
		HSSFCell totalCell = totalRow.createCell(0);
		totalCell.setCellStyle(headerStyle);
		totalCell.setCellValue("合计");
		totalCell.setCellType(HSSFCell.CELL_TYPE_STRING);
		for (int j = 0; j < totalList.size(); j++) {
			HSSFCell cell = totalRow.createCell(j + 1);
			cell.setCellStyle(bodyStyle);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(((GroupDto) totalList.get(j)).getTotal());
			cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		}
	}

	@RequestMapping("/lossSortDetail")
	public String lossSortDetail(HttpServletRequest request, String deptId, String startTime, String endTime, String optionId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map.put("account", user.getAccount());
			map.put("orgId", user.getOrgId());
			map.put("deptId", deptId);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("optionId", optionId);
			OptionBean optionBean = optionMapper.getByPrimaryKeyAndOrgId(optionId,user.getOrgId());
			List<TeamGroupBean> groupList = mainService.queryManageGroupList(user.getAccount(),user.getOrgId());
			List<LossSortDetial> list = lossSortService.getlossSortDetailList(map);
			request.setAttribute("deptId", deptId);
			request.setAttribute("endTime", endTime);
			request.setAttribute("startTime", startTime);
			request.setAttribute("optionId", optionId);
			request.setAttribute("optionName", optionBean==null?"":optionBean.getOptionName());
			request.setAttribute("list", list);
			request.setAttribute("groupList", groupList);
		} catch (Exception e) {
			logger.debug("流失客户原因明细异常！loginname=" + user.getAccount());
			e.printStackTrace();
			throw new SysRunException(e);
		}
		return "/report/lossSortDetail";
	}

	@RequestMapping("/detailExport")
	public void export(HttpServletResponse response, HttpServletRequest request, String deptId, String startTime, String endTime, String optionId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String, String> map = new HashMap<String, String>();
		try {
			map.put("account", user.getAccount());
			map.put("orgId", user.getOrgId());
			map.put("deptId", deptId);
			map.put("startTime", startTime);
			map.put("endTime", endTime);
			map.put("optionId", optionId);
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
			HSSFSheet resConSheet = workbook.createSheet(lossDetailSheetTitle);
			List<LossSortDetial> detailList = new ArrayList<LossSortDetial>();
			detailList = lossSortService.getlossSortDetailList(map);
			createConSheet(detailList, resConSheet, headerStyle, bodyStyle);
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
	public void createConSheet(List<LossSortDetial> detailList, HSSFSheet sheet, HSSFCellStyle headerStyle, HSSFCellStyle bodyStyle) {
		HSSFRow rowm = sheet.createRow(0);
		for (int i = 0; i < lossDetailHeader.length; i++) {
			String head = lossDetailHeader[i];
			HSSFCell cell = rowm.createCell(i);
			cell.setCellValue(head);
			cell.setCellStyle(headerStyle);
			sheet.setColumnWidth(i, 32 * 160);
		}

		for (int i = 0; i < detailList.size(); i++) {
			LossSortDetial dto = detailList.get(i);
			HSSFRow datarow = sheet.createRow(i + 1);
			for (int j = 0; j < lossDetailHeader.length; j++) {
				HSSFCell cell = datarow.createCell(j);
				cell.setCellStyle(bodyStyle);
				if (j == 0) {
					cell.setCellValue(dto.getDeptName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 1) {
					cell.setCellValue(dto.getName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 2) {
					cell.setCellValue(dto.getLinkName());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 3) {
					cell.setCellValue(com.qftx.common.util.DateUtil.format(dto.getSignDate()));
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 4) {
					cell.setCellValue(dto.getMoney());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 5) {
					cell.setCellValue(dto.getDayDiff());
					cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				} else if (j == 6) {
					cell.setCellValue(com.qftx.common.util.DateUtil.format(dto.getActionDate()));
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 7) {
					cell.setCellValue(com.qftx.common.util.DateUtil.format(dto.getInvalidDate()));
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				} else if (j == 8) {
					cell.setCellValue(dto.getOwnerAcc());
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				}
			}
		}
	}
}