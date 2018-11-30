package com.qftx.export.scrontrol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.export.service.CustDataExportService;
import com.qftx.export.service.CustDataExportSmsCheckService;
import com.qftx.export.service.CustExportInfoService;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.sys.bean.CustFieldSet;

@Controller
@RequestMapping("/export")
public class CustDataExportAction {
	
	private Log log = LogFactory.getLog(CustDataExportAction.class);
	
	@Autowired
	private CustDataExportService custDataExportService;
	@Autowired
	private CustDataExportSmsCheckService custDataExportSmsCheckService;
	@Autowired
	private CustExportInfoService custExportInfoService;
	@Autowired
	private CachedService cachedService;
	
	@RequestMapping()
	public String index(HttpServletRequest request){
		ShiroUser user = ShiroUtil.getShiroUser();
		request.setAttribute("isSys", user.getIssys());
		return "data-output/data-output";
	}
	
	@RequestMapping("/toPage")
	public String searchSetPage(HttpServletRequest request){
		try{
			String module = request.getParameter("module"); // 模块类型值
			String exportType = request.getParameter("exportType");
			request.setAttribute("module", module);
			request.setAttribute("exportType", exportType);
			request.setAttribute("service_url", ConfigInfoUtils.getStringValue("tsm_upload_service_url"));
			request.setAttribute("validTime", AppConstant.SMS_CODE_VALID_TIME);
		}catch(Exception e){
			log.error("数据导出异常！", e);
		}	
		return "data-output/pop-data-output";
	}
	
	@RequestMapping("/toExportContextPage")
	public String searchSetExportContextPage(HttpServletRequest request){
		try{
			String exportSearchContext = request.getParameter("exportSearchContext"); // 查询条件
			request.setAttribute("exportSearchContext", exportSearchContext);
		}catch(Exception e){
			log.error("【数据导出】查询条件页面异常！", e);
		}	
		return "data-output/pop-export-search-context";
	}
	
	public Map<String, String> getFiledMap(String orgId,Integer isState){
		List<CustFieldSet> fieldSets = new ArrayList<CustFieldSet>();
		Map<String, String> map = new HashMap<String, String>();
		if (isState == 1) {// 企业资源
			fieldSets = cachedService.getComFiledSets(orgId);
		} else {
			fieldSets = cachedService.getPersonFiledSets(orgId);
		}
		for(CustFieldSet fieldSet : fieldSets){
			if(fieldSet.getEnable() == 1){
				map.put(fieldSet.getFieldCode(), fieldSet.getFieldName());
			}
		}
		if(isState == 1){
			map.put("mobilephone", "常用电话");
			map.put("telphone", "备用电话");
			map.put("mainLinkman", "联系人");
		}
		return map;
	}
	
	@RequestMapping("/exportPhoneCheck")
	@ResponseBody
	public Map<String, Object> exportPhoneCheck(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50056, user.getOrgId());
			if(list.size() > 0 && list.get(0).getIsOpen() != null && list.get(0).getIsOpen().equals("1") && StringUtils.isNotBlank(list.get(0).getDictionaryValue())){
				map.put("isCheckPhone", true);
				map.put("phone", list.get(0).getDictionaryValue());
				List<DataDictionaryBean> list2 = cachedService.getDirList(AppConstant.DATA_50057, user.getOrgId());
				if(list2.size() > 0 && StringUtils.isNotBlank(list2.get(0).getDictionaryValue())){
					String acc = list2.get(0).getDictionaryValue().replace(",", "");
					Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
					String userName = nameMap.get(acc) == null ? acc : nameMap.get(acc);
					map.put("checkUserName", userName);
				}
			}else{
				map.put("isCheckPhone", false);
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "读取校验号码设置失败！");
			log.error("【数据导出】读取校验号码失败！",e);
		}
		return map;
	}
	
	@RequestMapping("/sendSmsCheckCode")
	@ResponseBody
	public Map<String, Object> sendSmsCheckCode(Integer exportType){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50056, user.getOrgId());
			String mobilephone = list.get(0).getDictionaryValue();
			boolean flag = custDataExportSmsCheckService.sendSmsCheckCode(mobilephone, user.getOrgId(), user.getAccount(),user.getName(),exportType);
			map.put("success", flag);
			if(!flag) map.put("msg", "发送短信验证码失败！");
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "发送短信验证码异常！");
			log.error("【数据导出】发送短信验证码失败！",e);
		}
		return map;
	}
	
	@RequestMapping("/smsCodeCheck")
	@ResponseBody
	public Map<String, Object> smsCodeCheck(String code){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			boolean flag = custDataExportSmsCheckService.smsCodeCheck(code, user.getOrgId(), user.getAccount());
			map.put("success", flag);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "短信验证码校验异常！");
			log.error("【数据导出】短信验证码校验异常！",e);
		}
		return map;
	}
	
	
	
	@RequestMapping("/exportFields")
	@ResponseBody
	public Map<String, Object> exportFields(){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<CustFieldSet> fieldSets = new ArrayList<CustFieldSet>();
			List<CustFieldSet> enabledFeilds = new ArrayList<CustFieldSet>();
			if (user.getIsState() == 1) {// 企业资源
				fieldSets = cachedService.getComFiledSets(user.getOrgId());
			} else {
				fieldSets = cachedService.getPersonFiledSets(user.getOrgId());
			}
			for(CustFieldSet fieldSet : fieldSets){
				if(fieldSet.getEnable() == 1){
					enabledFeilds.add(fieldSet);
				}
			}
			map.put("success", true);
			map.put("isState", user.getIsState());
			map.put("fields", enabledFeilds);
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "读取字段失败！");
			log.error("【数据导出】用户字段读取失败",e);
		}
		return map;
	}
	
	@RequestMapping("/exportCount")
	@ResponseBody
	public Map<String, Object> exportCount(ResCustInfoDto custInfoDto,Integer exportType){
		Integer totalCount = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			switch (exportType) {
				case 1:
					totalCount = custDataExportService.getExportResNum(custInfoDto, user);
					break;
				case 2:
					totalCount = custDataExportService.getExportCustNum(custInfoDto, user);
					break;
				case 3:
					totalCount = custDataExportService.getExportSignCustNum(custInfoDto, user);
					break;
				case 4:
					totalCount = custDataExportService.getExportSilentCustNum(custInfoDto, user);
					break;
				case 5:
					totalCount = custDataExportService.getExportLosingCustNum(custInfoDto, user);
					break;
				default:
					break;
			}
			map.put("success", true);
		} catch (Exception e) {
			map.put("success", false);
			log.error("数据导出查询数量失败！",e);
		}
		map.put("totalCount", totalCount);
		return map;
	}
	
	@RequestMapping("/exportData")
	@ResponseBody
	public Map<String, Object> exportData(ResCustInfoDto custInfoDto,Integer exportType,String exportColumnStr,String exportSearchContext,String code){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			boolean flag = custDataExportSmsCheckService.smsCodeCheck(code, user.getOrgId(), user.getAccount());
			if(flag){
				List<ResCustInfoBean> list = new ArrayList<ResCustInfoBean>();
				List<String> columns = Arrays.asList(exportColumnStr.split(","));
				List<String> exportColumns = new ArrayList<String>();
				Map<String, String> fieldMap = getFiledMap(user.getOrgId(), user.getIsState());
				List<String> heads = new ArrayList<String>();
				boolean isShowArea = false;
				for(String column : columns){
					heads.add(fieldMap.get(column));
					if(column.equals("comArea")){
						exportColumns.add("losingRemark");
						isShowArea = true;
					}else{
						exportColumns.add(column);
					}
				}
				
				switch (exportType) {
					case 1:
						list = custDataExportService.getExportResList(custInfoDto, user,exportColumns,isShowArea);
						break;
					case 2:
						list = custDataExportService.getExportCustList(custInfoDto, user,exportColumns,isShowArea);
						break;
					case 3:
						list = custDataExportService.getExportSignCustList(custInfoDto, user,exportColumns,isShowArea);
						break;
					case 4:
						list = custDataExportService.getExportSilentCustList(custInfoDto, user,exportColumns,isShowArea);
						break;
					case 5:
						list = custDataExportService.getExportLosingCustList(custInfoDto, user,exportColumns,isShowArea);
						break;
					default:
						break;
				}
				
				if(list.size() > 0){
					String exportId = custExportInfoService.export(list, exportColumns, heads, user.getOrgId(), user.getAccount(), exportType, exportSearchContext);
					map.put("success", true);
					map.put("exportId", exportId);
				}else{
					map.put("success", false);
					map.put("msg", "导出数据为0条！");
				}
			}else{
				map.put("success", false);
				map.put("msg", "验证码错误！");
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("msg", "导出数据失败！");
			log.error("数据导出失败！",e);
		}
		return map;
	}
	
	
	
	
}
