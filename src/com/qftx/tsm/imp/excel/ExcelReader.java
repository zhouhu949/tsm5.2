package com.qftx.tsm.imp.excel;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.excel.ExcelUtils;
import com.qftx.tsm.credit.dto.ImportLeadInfoDto;
import com.qftx.tsm.imp.dto.ImportResInfoDto;
import com.qftx.tsm.imp.util.TempletExcelUtil;
import com.qftx.tsm.sys.bean.CustFieldSet;


public class ExcelReader {
    private String filePath;
    private Workbook workBook;    
    private Sheet sheet;

    public ExcelReader(String filePath) {
        this.filePath = filePath;
        this.load();
    }    

    private void load() {
    // FileInputStream inStream = new FileInputStream(new File(filePath));
   	 InputStream is = null;
       try {
       	URL url=new URL(filePath);
       	is=url.openStream(); 
           workBook = WorkbookFactory.create(is);
           sheet =  workBook.getSheetAt(0);
       } catch (Exception e) {
           e.printStackTrace();
       }finally{
           try {
               if(is!=null){
               	is.close();
               }                
           } catch (IOException e) {                
               e.printStackTrace();
           }
       }
   }

    private String getCellValue(Cell cell) {
        String cellValue = "";
        DataFormatter formatter = new DataFormatter();
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        cellValue = formatter.formatCellValue(cell);
                    } else {
                        double value = cell.getNumericCellValue();
                        int intValue = (int) value;
                        cellValue = value - intValue == 0 ? String.valueOf(intValue) : String.valueOf(value);
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cellValue = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    cellValue = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    cellValue = String.valueOf(cell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_BLANK:
                    cellValue = null;
                    break;
                case Cell.CELL_TYPE_ERROR:
                    cellValue = null;
                    break;
                default:
                    cellValue = cell.toString().trim();
                    break;
            }
        }
        if(cellValue!=null){
        	cellValue=cellValue.trim();
        	if("".equals(cellValue)){
        		cellValue=null;
        	}
        }
        return cellValue;
    }

    /***
     * 获取excel 文件数据，组装临时资源
     * @param fields 匹配好的字段
     * @param firstCheckVal 0:不忽略首行数据，1：忽略首行数据
     * @return
     * @throws Exception 
     * @create  2016-2-27 下午12:58:17 zwj
     * @history  4.x
     */
    public  ExcelDataBean getSheetData(List<String>fields,String firstCheckVal) throws Exception{
    	ExcelDataBean data = new ExcelDataBean();
    	// 读取excel 前两行数据
    	Object[] returns = TempletExcelUtil.readAllExcel(sheet);
    	Map<Integer, String[]>dataMap = (Map<Integer, String[]>) returns[1];
    	String[] st1 = (String[]) returns[2];
    	Integer key = (Integer) returns[0]; // 标题属于第几行
 
    	for(int i = 0;i<st1.length;i++){ // 设置标题行
    		data.getHeader().add(st1[i]);
    	}   
    	for (Integer key1 : dataMap.keySet()) {
    		// 去首行
    		if(key1.equals(key) && "1".equals(firstCheckVal)){
    			continue;
    		}else{
    			String[]datas = dataMap.get(key1);    		
    			List<String> rowData = new ArrayList<String>();
                 for (int j = 0; j < data.getHeader().size(); j++) {	                
	               	if(fields.get(j)!=null && StringUtils.isNotBlank(fields.get(j))){ // 如果为空 表示匹配字段为 不导入该字段            		
	                   	rowData.add(datas[j]);
	               	}else{
	               		rowData.add(" ");
	               	}	               	
                }
                 data.getDatas().add(JsonUtil.getJsonString(rowData));
    		}   		 
    	}
        return data;
    }
    
    public  ExcelDataBean getSheetHeaders() {
    	ExcelDataBean data = new ExcelDataBean();
    	// 读取excel 前两行数据
    	Object[] returns = TempletExcelUtil.readFromExcel(sheet,2);
    	Map<Integer, String[]>dataMap = (Map<Integer, String[]>) returns[1];
    	String[] st1 = (String[]) returns[2];
    	Integer key = (Integer) returns[0]; // 标题行
    	Integer row = (Integer) returns[3]; // 共多少行
    	data.setRows(row);
    	for(int i = 0;i<st1.length;i++){ // 设置标题行
    		data.getHeader().add(st1[i]);
    	}   	  	
    	String[]st2 = dataMap.get(key+1); // 除标题行外首行数据
    	if(st2 != null){
    		for(int i = 0;i<st2.length;i++){ // 设置标题行
        		data.getDatas().add(st2[i]);
        	}
    	}
    	
        return data;
    }
    
    /** 错误的导入资源 返回 HSSFWorkbook
     * 	@param fails 错误的资源集合
     * @param filedMatchs 匹配字段
     * @param map<filedCode,filedName>
     * */
    public static HSSFWorkbook writeAllInfoToExcel(List<ImportResInfoDto> fails,String[] filedMatchs,
			Map<String, String> map) {
		HSSFWorkbook workBook = new HSSFWorkbook();// 创建 一个excel文档对象
		HSSFSheet sheet = workBook.createSheet("导入详情");// 创建一个工作薄对象
		HSSFRow row = null;
		HSSFCell cell = null;
		CellStyle cellStyle = null;
		String rtnMsg = null;

		Font font = ExcelUtils.getDelFont(workBook);
		// 错误情况的样式：
		CellStyle errorCellStyle = ExcelUtils.getCellStyle(workBook,
				HSSFColor.RED.index, CellStyle.ALIGN_LEFT);
		HSSFDataFormat format = workBook.createDataFormat();
		cellStyle = workBook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        cellStyle.setDataFormat(format.getFormat("@")); // 设置单位格式为文本
		for (int i = 0; i <= fails.size(); i++) {
			String[] valueList = new String[]{};
			row = sheet.createRow(i); // 创建行对象
			if (i != 0) {
				rtnMsg = fails.get(i - 1).getErrorMsg();
				valueList = JsonUtil.getStringArrayJson(fails.get(i-1).getJsonData());
			} else {
				row.setHeight((short) 400);
			}
			int c = 0;	
			for (int j = 0; j <= filedMatchs.length; j++) {						
				if(j != 0 && StringUtils.isNotBlank(filedMatchs[j-1])){
					c++;
				}
					if (i == 0) {
						font.setFontHeightInPoints((short) 12); // 字体大小
						font.setColor(HSSFColor.BLACK.index); // 字体颜色
						font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 是否粗体
						cellStyle.setFont(font);
						if (j == 0) {
							cell = row.createCell(j); // 创建单元格
							sheet.setColumnWidth(j, 6000);
							cell.setCellValue("验证结果");
							cell.setCellStyle(cellStyle);
						} else {
							if(StringUtils.isNotBlank(filedMatchs[j-1])){
								cell = row.createCell(c); // 创建单元格
								sheet.setColumnWidth(c, 4000);
								// 企业版中 客户名称与联系人姓名code相同，所以需要特殊判断
								if("name".equals(filedMatchs[j - 1].split("_")[0]) && "1".equals(filedMatchs[j - 1].split("_")[1])){
									cell.setCellValue("客户名称");
								}else{
									cell.setCellValue(map.get(filedMatchs[j - 1].split("_")[0]));
								}
								cell.setCellStyle(cellStyle);
							}
						}
					
					} else {
						if (j == 0) {
							cell = row.createCell(j); // 创建单元格
							cell.setCellValue(rtnMsg);
							ExcelUtils.setCellVal(sheet, rtnMsg, errorCellStyle,cell);
						} else {
							if(StringUtils.isNotBlank(filedMatchs[j-1])){
								cell = row.createCell(c); // 创建单元格
								if (StringUtils.isNotBlank(filedMatchs[j-1])&&StringUtils.isNotBlank(valueList[j-1])) {
									cell.setCellValue(valueList[j-1]);
								}
							}
							
						}
					}		
			}
		}
		return workBook;
	}
    
    /** 生成待导入excel模板 */
    public static HSSFWorkbook writeTempToExcel(List<CustFieldSet>filedMatchs,String mark) {
		HSSFWorkbook workBook = new HSSFWorkbook();// 创建 一个excel文档对象
		HSSFSheet sheet = workBook.createSheet("导入模板");// 创建一个工作薄对象
		HSSFRow row = sheet.createRow(0); // 创建行对象;
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
			// 企业版中 客户名称与联系人姓名code相同，所以需要特殊判断
			if("name".equals(filedMatchs.get(j).getFieldCode()) && "1".equals(filedMatchs.get(j).getState())){
				cell.setCellValue("客户名称");
			}else{
				cell.setCellValue(filedMatchs.get(j).getFieldName());
			}
			cell.setCellStyle(cellStyle);				
		}
		if("2".equals(mark)){ // 意向客户导入
			cell = row.createCell(filedMatchs.size()); // 创建单元格	
			sheet.setColumnWidth(filedMatchs.size(), 4000);
			cell.setCellValue("销售进程");
			cell.setCellStyle(cellStyle);	
			cell = row.createCell(filedMatchs.size()+1); // 创建单元格	
			sheet.setColumnWidth(filedMatchs.size()+1, 4000);
			cell.setCellValue("淘到客户时间");
			cell.setCellStyle(cellStyle);	
		}else if("3".equals(mark)){ // 签约客户导入
			cell = row.createCell(filedMatchs.size()); // 创建单元格	
			sheet.setColumnWidth(filedMatchs.size(), 4000);
			cell.setCellValue("签约时间");
			cell.setCellStyle(cellStyle);	
		}
		return workBook;
	}
    
    
    /** 错误的导入资源 返回 HSSFWorkbook
     * 	@param fails 错误的资源集合
     * @param filedMatchs 匹配字段
     * @param map<filedCode,filedName>
     * */
    public static HSSFWorkbook writeAllErrorInfoToExcel(List<ImportLeadInfoDto> fails,String[] filedMatchs,
			Map<String, String> map) {
		HSSFWorkbook workBook = new HSSFWorkbook();// 创建 一个excel文档对象
		HSSFSheet sheet = workBook.createSheet("导入详情");// 创建一个工作薄对象
		HSSFRow row = null;
		HSSFCell cell = null;
		CellStyle cellStyle = null;
		String rtnMsg = null;

		Font font = ExcelUtils.getDelFont(workBook);
		// 错误情况的样式：
		CellStyle errorCellStyle = ExcelUtils.getCellStyle(workBook,
				HSSFColor.RED.index, CellStyle.ALIGN_LEFT);
		HSSFDataFormat format = workBook.createDataFormat();
		cellStyle = workBook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); 
        cellStyle.setDataFormat(format.getFormat("@")); // 设置单位格式为文本
		for (int i = 0; i <= fails.size(); i++) {
			String[] valueList = new String[]{};
			row = sheet.createRow(i); // 创建行对象
			if (i != 0) {
				rtnMsg = fails.get(i - 1).getErrorMsg();
				valueList = JsonUtil.getStringArrayJson(fails.get(i-1).getJsonData());
			} else {
				row.setHeight((short) 400);
			}
			int c = 0;	
			for (int j = 0; j <= filedMatchs.length; j++) {						
				if(j != 0 && StringUtils.isNotBlank(filedMatchs[j-1])){
					c++;
				}
					if (i == 0) {
						font.setFontHeightInPoints((short) 12); // 字体大小
						font.setColor(HSSFColor.BLACK.index); // 字体颜色
						font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 是否粗体
						cellStyle.setFont(font);
						if (j == 0) {
							cell = row.createCell(j); // 创建单元格
							sheet.setColumnWidth(j, 6000);
							cell.setCellValue("验证结果");
							cell.setCellStyle(cellStyle);
						} else {
							if(StringUtils.isNotBlank(filedMatchs[j-1])){
								cell = row.createCell(c); // 创建单元格
								sheet.setColumnWidth(c, 4000);
								// 企业版中 客户名称与联系人姓名code相同，所以需要特殊判断
								if("name".equals(filedMatchs[j - 1].split("_")[0]) && "1".equals(filedMatchs[j - 1].split("_")[1])){
									cell.setCellValue("客户名称");
								}else{
									cell.setCellValue(map.get(filedMatchs[j - 1].split("_")[0]));
								}
								cell.setCellStyle(cellStyle);
							}
						}
					
					} else {
						if (j == 0) {
							cell = row.createCell(j); // 创建单元格
							cell.setCellValue(rtnMsg);
							ExcelUtils.setCellVal(sheet, rtnMsg, errorCellStyle,cell);
						} else {
							if(StringUtils.isNotBlank(filedMatchs[j-1])){
								cell = row.createCell(c); // 创建单元格
								if (StringUtils.isNotBlank(filedMatchs[j-1])&&StringUtils.isNotBlank(valueList[j-1])) {
									cell.setCellValue(valueList[j-1]);
								}
							}
							
						}
					}		
			}
		}
		return workBook;
	}
    
    public static void main(String[] args) {
        ExcelReader eh = new ExcelReader("D:\\test\\test.xlsx");
    }
}