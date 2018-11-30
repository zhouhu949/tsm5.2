package com.qftx.tsm.callrecord.util;

import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.IOUtils;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.excel.ExcelUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * 导入短信号码
 * @author: zwj
 * @since: 2016-1-12  上午11:15:48
 * @history: 4.x
 */
public class SmsImportIoUtil {
	protected static Log logger = LogFactory.getLog(SmsImportIoUtil.class);

	 /** 
		 * 下载：根据原始文件的路径名来下载内容(错误时将抛出SysRunException异常)
		 * @param filePath			绝对路径
		 * @param fileName			文件名
		 */
		public static String downloadFile(String filePath, String fileName,HttpServletResponse response) {
			try {
				byte[] content = getBytesByFile(filePath);
				return downloadFile(content, fileName, response);
				
			} catch (IOException e) {
				throw new SysRunException("文件不存在或已被删除");
			}
		}
		
		public static byte[] getBytesByFile(String filePath) throws IOException  {
			return IOUtils.getData(new FileInputStream(filePath));
		}
		
		/** 
		 * 下载：根据字节数组来下载内容(错误时将抛出SysRunException异常)
		 * @param content			要下载的文件的字节数组
		 * @param fileName			文件名
		 */
		protected static String downloadFile(byte[] content, String fileName,HttpServletResponse response) {
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
	
		/** 取workbook数据 
		 *  @param mark 1: 邮件, 默认短信
		 * */
		public static Object[] getExcelContentMap(String filePath,int mark) {					
			// 取得一个Workbook并得到其内容的二维数组：
			Workbook workbook = ExcelUtils.getWorkbookByFilePath(filePath);
			Sheet sheet = workbook.getSheetAt(0);
			
			// 整个的内容集：
			Object[] indexArr = getIndex(sheet,mark);
			Map<Integer, String[]> excelContentMap = ExcelUtils.getContentMap(sheet, (Integer)indexArr[0] + 1, 0, 
					((List<String>)indexArr[1]).size(), null); 
			
			return new Object[] { workbook, setData(excelContentMap), (List<String>)indexArr[1] };
		}
		
		private static Map<Integer, String[]> setData(Map<Integer, String[]> excelContentMap) {
			return excelContentMap;
		}
		
		/**
		 * 把正确和错误信息写回到Excel表格中
		 * <br> 说明：默认写到第一个"工作薄"中
		 * <br> 说明：正确的直接写"√"，错误的写failureRowMap里的值
		 * @param workbook			Workbook
		 * @param validValMap		正确的数据
		 * @param failureRowMap		错误的数据
		 * @param msgColIndex		信息所在的列的下标(从0开始)
		 */
		public static <T> void wirteData(Workbook workbook, Map<Integer, T> validValMap,
				Map<Integer, String> failureRowMap, int msgColIndex) {
			
			// 默认为第一个"工作薄"
			Sheet sheet = workbook.getSheetAt(0);
			
			// 正确情况的样式、错误情况的样式：
			CellStyle succCellStyle = ExcelUtils.getCellStyle(workbook, HSSFColor.GREEN.index,
					CellStyle.ALIGN_CENTER);
			CellStyle errorCellStyle = ExcelUtils.getCellStyle(workbook, HSSFColor.RED.index,
					CellStyle.ALIGN_LEFT);
			
			// 成功的项：
			for (int rowIndex : validValMap.keySet()) {
				ExcelUtils.setCellVal(sheet, "√", succCellStyle, rowIndex, msgColIndex);
			}
			
			// 失败的项：
			for (int rowIndex : failureRowMap.keySet()) {
				ExcelUtils.setCellVal(sheet, failureRowMap.get(rowIndex), errorCellStyle, rowIndex,
						msgColIndex);
			}
		}
		
		/**
		 * 获取行下表、字段列表
		 * @param sheet
		 * @param mark 1: 邮件, 默认 短信
		 * @return
		 */
		public static Object[] getIndex(Sheet sheet,int mark){
			String checkName = "接收号码";
			if(mark == 1){
				checkName = "邮箱地址";
			}
			for(int i=0; i<3; i++){
				List<String> list = ExcelUtils.getRowVals(sheet, i);
				if(!(list.size()<2 || !"姓名".equals(list.get(0)) || !checkName.equals(list.get(1)))){
					List<String> fieldList = new ArrayList<String>();
					
					int j=0, len=list.size();
					if(len>20){
						len = 20;
					}
					boolean flag = false; //标志字段是否含有 "[" 或  "]"
					for(j=0; j<len; j++){
						String field = list.get(j);
						if(StringUtils.isNotBlank(field)){
							if(field.indexOf("[")>-1 || field.indexOf("]")>-1){
								flag = true;
								break;
							}
							fieldList.add(field);
						}else{
							break;
						}
					}
					if(flag){
						continue;
					}					
					//begin 验证字段是否有重复
					Set<String> set = new HashSet<String>();
					for(String f : fieldList){
						set.add(f);
					}
					if(set.size()<fieldList.size()){ //字段 有重复
						continue;
					}
					//end...
					return new Object[]{i, fieldList};
				}
			}
			throw new SysRunException("请使用模板！");
		}

		
}
