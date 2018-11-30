package com.qftx.tsm.imp.util;

import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.excel.ExcelUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.Map;

public class TempletExcelUtil {
	private static final Log logger = LogFactory.getLog(TempletExcelUtil.class);
	
	/** 
	 * 读取excel的前两行数据
	 * @param file
	 * @param rowTotal  表示excel多少行的数据
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] readFromExcel(Sheet sheet,int rowTotal) {
		Map<Integer, String[]> dataMap = null;
		String[] st1 = null;
		Object[] returnObjs = null;
		try {				
			Object[] indexArr = getIndex(sheet); // 判断从第几列，几行开始读取
			dataMap = ExcelUtils.getContentMap2(sheet, (Integer)indexArr[0], (Integer)indexArr[1],rowTotal,
						(Integer)indexArr[2],(Map<Integer, String>)indexArr[4],null);
			st1 = (String[])indexArr[5]; // 获取每列首条数据（正常情况下就是标题字段）
			int rowNum = ExcelUtils.getRowNum(sheet, (Integer)indexArr[0], (Integer)indexArr[1],(Integer)indexArr[3],
						(Integer)indexArr[2],null);
			
			returnObjs = new Object[]{(Integer)indexArr[0],dataMap,st1,rowNum,true};			
		} catch (Exception e) {
			throw new SysRunException(e);
		}		
		return returnObjs;
	}
	
	/** 
	 * 读取excel的所有数据
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	public static Object[] readAllExcel(Sheet sheet) {
		Map<Integer, String[]> dataMap = null;
		Object[] returnObjs = null;
		try {				
			Object[] indexArr = getIndex(sheet); // 判断从第几列，几行开始读取
			dataMap = ExcelUtils.getContentMap2(sheet, (Integer)indexArr[0], (Integer)indexArr[1],(Integer)indexArr[3],
						(Integer)indexArr[2],(Map<Integer, String>)indexArr[4],null);		
			returnObjs = new Object[]{(Integer)indexArr[0],dataMap,(String[])indexArr[5]};			
		} catch (Exception e) {
			throw new SysRunException(e);
		}		
		return returnObjs;
	}
	
	/**
	 * 返回[行开始index,列开始index,字段标题列数]
	 * @param sheet
	 * @return object[第几行，第几列，共多少列，共多少行，每列第一条数据(MAP[列数，数据]),
	 *                每列第一条数据(String[数据])]
	 */
	@SuppressWarnings("unchecked")
	public static Object[] getIndex(Sheet sheet){
		    int beginRow = 0; // 第几行
			int beginCol = 0; // 第几列		    
			Object[] list = ExcelUtils.getRowVals2(sheet, 0);		
			if((Integer)list[0] == 0){
				return new Object[]{beginRow,beginCol,0,0,null,null,true};
			}
			return new Object[]{beginRow,beginCol,(Integer)list[0],(Integer)list[1],
					(Map<Integer, String>)list[2],(String[])list[3],true};
	}
}
