package com.qftx.tsm.callrecord.util;

import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.excel.ExcelUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;




/**
 * 导入号码 【验证】
 * @author: zwj
 * @since: 2016-1-12  上午11:15:48
 * @history: 4.x
 */
public class SmsImportCheckUtil {
	protected static Log logger = LogFactory.getLog(SmsImportCheckUtil.class);

	/** 导入条件验证：验证格式 
	 * @param validValMap 待验证数据
	 * @param failureRowMap  存储错误数据
	 * @param fieldList 模板中 列头
	 * @param mark 1: 邮件,默认 短信
	 * */
	public static Map<Integer, String[]> checkFormat(Map<Integer, String[]> validValMap,
			Map<Integer, String> failureRowMap, List<String> fieldList, int maxSize,int mark) {
		
		if(validValMap.size()> maxSize){
			throw new SysRunException("导入短信号码数大于最大值！");
		}
		
		Map<Integer, String[]> rightValMap = new LinkedHashMap<Integer, String[]>();
		int len = fieldList.size();

		for (int rowIndex : validValMap.keySet()) {
			String[] arr = validValMap.get(rowIndex);
			
			// "姓名"要将所有","去掉，否则会造成后面逻辑异常：
			arr[0] = arr[0] != null ? arr[0].replaceAll(",", "") : null;
			
			if (StringUtils.isBlank(arr[0])) {
				failureRowMap.put(rowIndex, "【姓名】为空");
				continue;
			} else if(arr[0].length() > 1000){
				failureRowMap.put(rowIndex, "【姓名】格式错误");
				continue;
			} else if (StringUtils.isBlank(arr[1])) {
				if(mark == 1){
					failureRowMap.put(rowIndex, "【邮箱地址】为空");
				}else{
					failureRowMap.put(rowIndex, "【接收号码】为空");
				}				
				continue;
			} else if (checkParren(arr[1],mark)) {
				if(mark == 1){
					failureRowMap.put(rowIndex, "【邮箱地址】格式错误");
				}else{
					failureRowMap.put(rowIndex, "【接收号码】格式错误");
				}			
				continue;
			}
			
			boolean flag = false;
			for(int i=2; i<len; i++){
				if (StringUtils.isBlank(arr[i])) {
					failureRowMap.put(rowIndex, "【" + fieldList.get(i) +"】为空");
					flag = true;
					break;
				}
			}
			
			if(flag){
				continue;
			}
			rightValMap.put(rowIndex, arr);			
		}
		return rightValMap;
	}
	
	
	
	/** 
	 * 得到返回消息 ：
	 */
	public static <T> String getRetnMsg(String account, Workbook workbook,
			int msgColIndex, String linkPath, Map<Integer, T> validValMap,
			Map<Integer, String> failureRowMap, boolean flag) {
		
		// 写数据：
		SmsImportIoUtil.wirteData(workbook, validValMap, failureRowMap, msgColIndex);
		
		int succNum = validValMap.size();
		int failNum = failureRowMap.size();
		
		String fileName = fileSaveName(account) + ".xls";
		ExcelUtils.wirteWorkbookFile(workbook, "d:\\"+ fileName);		
		
		// 返回信息：
		String str = null;
		if(flag){
			if(failNum==0){
				str = "<label for=''>提示：共导入</label><span class='font-color'>"+ succNum +"</span><label for=''>条数据</label>";
			}else{
				str = "<label for=''>提示：共导入</label><span class='font-color'>" + (succNum + failNum) + "</span>" +
						"<label for=''>条数据，失败</label><span class='font-color'>" + failNum + "</span><label>条。" +
								"</label><a href='${ctx }/call/sms/downloadTemp.do?path=d:\\smsSend\\"+fileName+"&fielName=详情.xls' style='font-size:12px;text-decoration:underline;'>查看导入结果</a>";
			}
		}else{
			if(succNum==0 && failNum==0){
				str = "";
			}else{
				str = "" +
						 "<label for=''>提示：共导入</label><span class='font-color'>" + (succNum + failNum) + "</span>" +
							"<label for=''>条数据，失败</label><span class='font-color'>" + failNum + "</span><label>条。" +
									"</label><a href='${ctx }/call/sms/downloadTemp.do?path=d:\\smsSend\\"+fileName+"&fielName=详情.xls' style='font-size:12px;text-decoration:underline;'>查看导入结果</a>";
			}
		}
		return str;
	}
	
	/**
	 * 是否为空
	 * @param map	: map
	 */
	public static <T1, T2> void checkEmpty(Map<T1, T2> map) {
		if (map==null || map.isEmpty()) {
			throw new SysRunException("导入数据为空！");
		}
	}

	/**
	 * 文件名
	 * @param account			帐号
	 * @return
	 */
	private static String fileSaveName(String account) {
		return DateUtil.format(new Date(), "yyyyMMddHHmmssSSS") + "_" + account ;
	}
	
	/** 【电话号码，邮箱地址】做验证  */
	private static boolean checkParren(String arr,int mark){
		if(mark == 1){
			String REGEX_MAIL = "^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$";
			return !Pattern.matches(REGEX_MAIL, arr);
		}else{
			
			return !Pattern.matches("^\\d{11}$", arr);
		}
	}
}
