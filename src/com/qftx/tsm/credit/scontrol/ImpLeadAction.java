package com.qftx.tsm.credit.scontrol;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.common.util.excel.ExcelUtils;
import com.qftx.tsm.credit.bean.ImportLeadFileBean;
import com.qftx.tsm.credit.dto.ImportLeadInfoDto;
import com.qftx.tsm.credit.dto.ImportLeadResultDto;
import com.qftx.tsm.credit.service.ImportLeadFileService;
import com.qftx.tsm.credit.service.ImportLeadInfoService;
import com.qftx.tsm.credit.service.ImportLeadResultService;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.imp.bean.ImportField;
import com.qftx.tsm.imp.enums.ImportLeadFieldValidateResultEnum;
import com.qftx.tsm.imp.excel.ExcelDataBean;
import com.qftx.tsm.imp.excel.ExcelReader;
import com.qftx.tsm.imp.util.MatchFiledUtil;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.SysFileBean;
import com.qftx.tsm.sys.service.SysFileService;
import com.qftx.tsm.util.CollectionUtil;

@Controller  
@RequestMapping("/credit/leadimp")
public class ImpLeadAction {
	Logger logger = Logger.getLogger(ImpLeadAction.class);
	@Autowired ImportLeadFileService importLeadFileService;
	@Autowired ImportLeadResultService leadResultService;
	@Autowired ImportLeadInfoService leadInfoService;
	@Autowired private SysFileService sysFileService;
	@Autowired private ComResourceGroupService comResourceGroupService;
	@Autowired private CachedService cachedService;
	@Autowired ImportLeadResultService resultService;
	
	private String filePath="d:\\test\\";
	
	
	@RequestMapping("/page")
	public String page(ModelMap model,String resGroupId,String status,String mark,String isDetail){
		//status 资源类型： 1： 未分配   2：已分配且未跟进 资源，3：意向客户，4：签约客户
		// mark 1:全部资源，4:待分配资源
		// isDetail 1: 导入联系人
		ShiroUser user = ShiroUtil.getUser();
		if(StringUtils.isBlank(resGroupId)){
			resGroupId=null;
		}
		List<ResourceGroupBean> groups = importLeadFileService.findResGroup(user.getOrgId());
		// 销售进程缓存
		List<OptionBean> saleProcessList = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
		model.put("saleProcessList", saleProcessList);
		model.put("resGroupId", resGroupId);
		model.put("status", status);
		model.put("mark", mark);
		model.put("resGroups", groups);		
		model.put("tsmUpLoadServiceUrl", ConfigInfoUtils.getStringValue("tsm_upload_service_url")); // 上传服务器路径
		model.put("isDetail", StringUtils.isBlank(isDetail) ? "0":"1");
		if("1".equals(status)){
			return "/qupai/lead/import";
		}
		return "/qupai/lead/importMy";
	}
	
	@RequestMapping("/result")
	public String resultPage(ModelMap model,ImportLeadResultDto dto){
		ShiroUser user = ShiroUtil.getUser();
		dto.setInputAcc(user.getAccount());
		List<ImportLeadResultDto> results = leadResultService.query(user.getOrgId(),dto);
		if(results != null && results.size() >0){
			Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			for(ImportLeadResultDto resultDto : results){
				resultDto.setImpTime(DateUtil.formatDate(resultDto.getInputtime(), DateUtil.Data_ALL));
				resultDto.setInputAcc(nameMap.get(resultDto.getInputAcc()) == null ? resultDto.getInputAcc() : nameMap.get(resultDto.getInputAcc()));
			}
		}
		model.put("results", results);
		return "/qupai/lead/importResult";
	}
	
	@RequestMapping("/result/json")
	@ResponseBody
	public Map<String, Object> resultJson(HttpServletRequest request,ImportLeadResultDto dto){
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			ShiroUser user = ShiroUtil.getUser();		
			dto.setInputAcc(user.getAccount());
			List<ImportLeadResultDto> results = leadResultService.query(user.getOrgId(),dto);
			if(results != null && results.size() >0){
				for(ImportLeadResultDto resultDto : results){
					resultDto.setImpTime(DateUtil.formatDate(resultDto.getInputtime(), DateUtil.Data_ALL));
				}
			}			
			map.put("item", dto);
			map.put("results", results);
		}catch(Exception e){
			logger.error("异步加载导入结果异常！", e);
		}	
		return map;
	}
	
	/**
	 * 匹配字段保存
	 * @param id  import_res_file 主键
	 * @param headerStr 文件列头
	 * @param fieldsStr 匹配字段
	 * @param firstCheck 0:不忽略第一行，1：忽略导入第一行
	 * @return 
	 * @throws IOException 
	 * @create  2016-2-26 上午11:15:50 zwj
	 * @history  4.x
	 */
	@RequestMapping("/matchFields")
	public void matchFields(HttpServletResponse response,String id,String fieldsStr,String firstCheck) throws IOException{
		ShiroUser user = ShiroUtil.getUser();
		
		Map<String, Object> model = new HashMap<String, Object>();
		String [] fields=fieldsStr.split(",");	
		boolean result =true;
		if(result){
			model.put("status", "success");
		}else{
			model.put("status", "erro");
			model.put("msg", "字段映射错误！");
		}	
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(JsonUtil.getJsonString(model));
	}
	
	
	 /**
     * 上传文件
	 * @return 
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public void upload(HttpServletResponse response,MultipartHttpServletRequest request, String resGroupId,Integer status) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        ShiroUser user = ShiroUtil.getUser();
        Date date = new Date();
        
        if(StringUtils.isBlank(resGroupId)){
        	resGroupId = comResourceGroupService.saveUnGroup(user.getOrgId());
        }
        MultipartFile file = request.getFile("f");
        if (file == null||file.getSize()==0) {// step-2 判断file
        	 result.put("status", "erro");
             result.put("message", "文件为空");
        }else{
        	 String fileName = getFileName(file.getOriginalFilename(),date);
             if (!(new File(filePath).exists())) {
                 new File(filePath).mkdirs();
             }
             file.transferTo(new File(filePath + fileName));//写入目标文件
             //保存user file
             SysFileBean sysFile = sysFileService.create(user.getOrgId(), user.getId(), "3", filePath, fileName, (int) file.getSize(), date,1);
         	 
             /**  资源客户导入 */
             // 入库 import_res_file 表
         	ImportLeadFileBean importFile = importLeadFileService.insert(user.getOrgId(),user.getGroupId(), resGroupId,status,user.getId(), sysFile.getId(), date);
         	// 获取excel 文件列头以及第一行数据
         	ExcelDataBean headerAndFistRowData = importLeadFileService.getExcelHeaders(user.getOrgId(), sysFile.getId(), filePath+fileName, date);
         	
         	// 修改 import_res_file 表 header，rowcount 字段
         	importLeadFileService.updateHeader(user.getOrgId(), importFile.getId(), JsonUtil.getJsonString(headerAndFistRowData.getHeader()),headerAndFistRowData.getRows());
         	// 获取 用户系统字段列表
         	Object[] obj = importLeadFileService.getImportFields(user.getIsState(),user.getOrgId());
         	List<ImportField> importFields = (List<ImportField>) obj[0];
         	// 根据excel文件列头 匹配系统字段
         	List<String> header = headerAndFistRowData.getHeader();
         	if(header != null && header.size() >0){
         		List<String>	headerFiled = new ArrayList<String>();
             	for(String str : header){
             		headerFiled.add(MatchFiledUtil.getFiledKey(str, user.getIsState()));
             	}
             	headerAndFistRowData.setHeaderFiled(headerFiled);
         	}
         	
         	result.put("id",importFile.getId());
         	result.put("importFields", importFields);
         	result.put("headerAndFistRowData", headerAndFistRowData);
         	result.put("filedSets",obj[1]);
	        result.put("status", "success");
	        result.put("message", "上传成功");
        }
        
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().print(JsonUtil.getJsonString(result));
    }
    
//    /** 忽略并导入 */
//    @RequestMapping("/ignoreImp")
//	@ResponseBody
//	public String ignoreImp(HttpServletRequest request,HttpServletResponse response){
//		try{
//			ShiroUser user = ShiroUtil.getUser();
//			String fileId = request.getParameter("fileId");
//			String errorCode = request.getParameter("errorCode");	
//			ImportLeadResultBean entity = new ImportLeadResultBean();
//			entity.setOrgId(user.getOrgId());
//			entity.setFileId(fileId);
//			entity.setStatus("0");
//			resultService.modfiyByFileId(entity);
//			
//			leadInfoService.ignoreImp(user.getOrgId(), fileId, errorCode, user.getIsState(),
//					user.getGroupId(), user.getAccount());
//			return AppConstant.RESULT_SUCCESS;
//		}catch(Exception e){
//			throw new SysRunException(e);
//		}
//	}
    
    /** 导出错误详情excel */
    @RequestMapping("/expErrorExcel")
    public String expErrorExcel(HttpServletRequest request,HttpServletResponse response){
    	try{
    		ShiroUser user = ShiroUtil.getUser();
        	String errorCode = request.getParameter("errorCode"); // 错误类型
        	String fileId = request.getParameter("fileId"); // 导入批次ID
        	// 查询 匹配好的字段
        	ImportLeadFileBean importLeadFileBean = importLeadFileService.queryById(user.getOrgId(), fileId);
    		String mapperJsonData = importLeadFileBean.getMapper();
    		if(StringUtils.isNotBlank(mapperJsonData)){
    			String[] mapperList = JsonUtil.getStringArrayJson(mapperJsonData);    	
    			// 查询导入出错的临时资源
    			Map<String,Object>map = new HashMap<String, Object>();
    			map.put("orgId", user.getOrgId());
    			map.put("fileId", fileId);
    			map.put("errorCode", errorCode);
    			List<ImportLeadInfoDto> errorList = leadInfoService.getErrorLeadInfos(map);
    			if(errorList != null && errorList.size()>0){
    				/** 获取<fieldCode,fieldName> */
    				Map<String,String>map1 = new HashMap<String, String>();
    				List<CustFieldSet> lists = new ArrayList<CustFieldSet>();
    				List<CustFieldSet> custFieldSetList = new ArrayList<CustFieldSet>();

    				custFieldSetList = cachedService.getQupaiFieldSet(user.getOrgId());	//cachedService.getPersonFiledSet(user.getOrgId());
    				
    				// 剔除字段 贷款批次
    				custFieldSetList = CollectionUtil.remove(custFieldSetList, "fieldCode", ImportLeadInfoService.IMPORT_EXCLUDE_FIELDCODES);
    				
    				// 剔除字段 图片字段
    				custFieldSetList = CollectionUtil.remove(custFieldSetList, "dataType", CustFieldSet.DATATYPE_PICTURE);
    				
    				for (CustFieldSet custFieldSet : custFieldSetList) {    					
    					map1.put(custFieldSet.getFieldCode(),custFieldSet.getFieldName());   					
    				}
    				map1.put(AppConstant.signDate,"签约时间");  
    				map1.put(AppConstant.amoytocustomerDate,"淘到客户时间");  
    				map1.put(AppConstant.lastOptionId,"销售进程");  
    				// 组装excel文件
    				HSSFWorkbook book = ExcelReader.writeAllErrorInfoToExcel(errorList, mapperList, map1);
    				byte[] content = ExcelUtils.getBytes(book);
    				String fileName = ImportLeadFieldValidateResultEnum.getEnum1(errorCode, ImportLeadFieldValidateResultEnum.getImportResults()).getDescr();
    				return this.downloadFile(content, fileName+".xls",response);
    			}
    		}
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}
    	return null;
    }
    
    /**
     * 导出EXCEL模板
     * 
     * @param mark
     * @return
     */
    @RequestMapping("/expTempExcel")
    public String expTempExcel(HttpServletRequest request,HttpServletResponse response, String mark){
    	try{
    		ShiroUser user = ShiroUtil.getUser();
    		
        	// 查询 匹配好的字段
			List<CustFieldSet> fieldSet = new ArrayList<CustFieldSet>();
//			if(user.getIsState() == 0){
				// 获取个人资源字段缓存
				fieldSet = cachedService.getQupaiFieldSet(user.getOrgId());	//cachedService.getPersonFiledSet(user.getOrgId());
				
				// 剔除字段 贷款批次
				fieldSet = CollectionUtil.remove(fieldSet, "fieldCode", ImportLeadInfoService.IMPORT_EXCLUDE_FIELDCODES);
				
				// 剔除字段 图片字段
				fieldSet = CollectionUtil.remove(fieldSet, "dataType", CustFieldSet.DATATYPE_PICTURE);
//			}else{
				// 获取企业资源字段缓存 & 联系人字段缓存
//				lists = cachedService.getComFiledSet(user.getOrgId());
//				lists.addAll(cachedService.getContactsFiledSet(user.getOrgId()));
//				for(CustFieldSet custFieldSet : lists){
//					if(custFieldSet.getEnable() == 1){ // 只需要启用的字段
//						custFieldSetList.add(custFieldSet);
//					}					
//				}
//			}
				
				
			// 组装excel文件
			HSSFWorkbook book = writeTempToExcel(fieldSet, mark);
			byte[] content = ExcelUtils.getBytes(book);
			String fileName =user.getOrgId()+"_leadimport";
			return this.downloadFile(content, fileName+".xls",response);
    	}catch(Exception e){
    		logger.error("导出EXCEL模板 异常！", e);
    	}
    	return null;
    }
    
    /**
     * 生成待导入放款信息EXCEL模板
     * 
     * @param filedMatchs
     * @param mark
     * @return
     */
    private static HSSFWorkbook writeTempToExcel(List<CustFieldSet> filedMatchs, String mark) {
		HSSFWorkbook workBook = new HSSFWorkbook();					// 创建 一个excel文档对象
		HSSFSheet sheet = workBook.createSheet("放款信息导入模板"); // 创建一个工作薄对象
		HSSFRow row = sheet.createRow(0); 							// 创建行对象;
		HSSFCell cell = null;
		CellStyle cellStyle = null;
		Font font = ExcelUtils.getDelFont(workBook);
		HSSFDataFormat format = workBook.createDataFormat();
		cellStyle = workBook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        cellStyle.setDataFormat(format.getFormat("@")); // 设置单位格式为文本
        font.setFontHeightInPoints((short) 12); // 字体大小
		font.setColor(HSSFColor.BLACK.index); // 字体颜色
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 是否粗体
		cellStyle.setFont(font);
        for (int j = 0; j < filedMatchs.size(); j++) {
			cell = row.createCell(j); // 创建单元格	
			sheet.setColumnWidth(j, 4000);
			cell.setCellValue(filedMatchs.get(j).getFieldName());
			cell.setCellStyle(cellStyle);				
		}
		return workBook;
	}
    
    
    /** 导出联系人excel模板
     * @param mark 2：意向导入，3：签约导入
     *  */
    @RequestMapping("/expDetailTempExcel")
    public String expDetailTempExcel(HttpServletRequest request,HttpServletResponse response){
    	try{
    		ShiroUser user = ShiroUtil.getUser();
    		
        	// 查询 匹配好的字段
			List<CustFieldSet> lists = new ArrayList<CustFieldSet>();
			List<CustFieldSet> custFieldSetList = new ArrayList<CustFieldSet>();
			CustFieldSet custFieldSet1 = new CustFieldSet();
			custFieldSet1.setFieldCode(AppConstant.com_name);
			custFieldSet1.setState(1);
			custFieldSet1.setFieldName("客户名称");
			custFieldSetList.add(custFieldSet1);
			// 获取企业资源字段缓存 & 联系人字段缓存
			lists = cachedService.getContactsFiledSet(user.getOrgId());
			for(CustFieldSet custFieldSet : lists){				
				if(custFieldSet.getEnable() == 1){ // 只需要启用的字段
					custFieldSetList.add(custFieldSet);
				}					
			}
			
			// 组装excel文件
			HSSFWorkbook book = ExcelReader.writeTempToExcel(custFieldSetList,null);
			byte[] content = ExcelUtils.getBytes(book);
			String fileName =user.getOrgId()+"_detail_import";
			return this.downloadFile(content, fileName+".xls",response);
    	}catch(Exception e){
    		logger.error("导出联系人excel模板 异常！", e);
    	}
    	return null;
    }
	/** 
	 * 下载：根据字节数组来下载内容(错误时将抛出SysRunException异常)
	 * @param content			要下载的文件的字节数组
	 * @param fileName			文件名
	 */
	protected String downloadFile(byte[] content, String fileName,HttpServletResponse response) {
		try {
			response.setContentType("application/x-msdownload");
			// 注意：此种形式文件名不能带有";"，否则会造成传出的文件名错乱
			response.setHeader("Content-disposition", "attachment; filename="
					+ new String(fileName.getBytes("GBK"), "ISO8859-1"));
			
			ServletOutputStream sos = response.getOutputStream();
			sos.write(content);
			
			sos.flush();
			sos.close();
			return null;
			
		} catch (IOException e) {
			if ("ClientAbortException".equals(e.getClass().getSimpleName())) {
				return null;
			} else {
				throw new SysRunException("下载失败");
			}
		}
	}
	
    public String getFileName(String orgFileName,Date date){
		orgFileName = (orgFileName == null) ? "" : orgFileName;
		Pattern p = Pattern.compile("\\s|\t|\r|\n");
		Matcher m = p.matcher(orgFileName);
		orgFileName = m.replaceAll("_");
		return FilenameUtils.getBaseName(orgFileName).concat(".") + DateUtil.formatDate(date,"yyyyMMddHHmmss").concat(".").concat(FilenameUtils.getExtension(orgFileName).toLowerCase());
    }
}
