package com.qftx.tsm.call;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.qftx.base.util.HttpUtil;

//补充通话记录
public class TestInsertCall {
	private static Logger logger = Logger.getLogger(TestInsertCall.class);

	public static BufferedReader getFile(String path) {
		File file = null;
		InputStreamReader isr = null;
		BufferedReader bf = null;
		try {
			file = new File(path);
			isr = new InputStreamReader(new FileInputStream(file), "GBK");
			bf = new BufferedReader(isr);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("读取文件流异常！");
			logger.error("读取文件流异常！");
			return null;
		}
		return bf;
	}

	// 解析录音状态
	public static List<String> parseRecord(BufferedReader bf) throws IOException {
		String lineString = "";
		List<String> list = new ArrayList<String>();
		while ((lineString = bf.readLine()) != null) {
			if (lineString.contains("updateJsonRecordStateList")) {
				lineString = lineString.split("updateJsonRecordStateList ")[1];
				if (lineString.length() > 70) { // 去掉请求返回的内容
					System.out.println(lineString);
					logger.debug("valueString=" + lineString);
					list.add(lineString);
				}
			}
		}
		System.out.println("size = " + list.size());
		logger.debug("size = " + list.size());
		return list;
	}

	// 解析通话
	public static List<String> parseCall(BufferedReader bf) throws IOException {
		String lineString = "";
		List<String> list = new ArrayList<String>();
		while ((lineString = bf.readLine()) != null) {
			if (lineString.contains("undefined")) {
				lineString = lineString.split("saveJsonList ")[1].replace("undefined,", "");
				System.out.println(lineString);
				logger.debug("valueString=" + lineString);
				list.add(lineString);
			}
		}
		System.out.println("size = " + list.size());
		logger.debug("size = " + list.size());
		return list;
	}

	public static void main(String args[]) {
		String path = "E:\\ccall\\appCall.log.3";
		// String url = "http://call.qftx.net/service/saveJsonList";
		String recordUrl = "http://call.qftx.net/service/updateJsonRecordStateList";
		String str = "";
		int num = 0;
		try {
			BufferedReader br = getFile(path);
			List<String> list = new ArrayList<String>();
			if (br != null) {
				list = parseRecord(br);
				if (list != null && list.size() > 0) {
					for (String json : list) {
						str = HttpUtil.doPostJSON(recordUrl, json);
						ResultBean bean = JSON.parseObject(str, ResultBean.class);
						if ("1".equals(bean.getCode())) {
							num++;
						} else {
							System.out.println("请求返回失败 str=" + str + ",num=" + num);
						}
						System.out.println("str=" + str + ",num=" + num);
						Thread.sleep(50);
					}
				}
			}
			System.out.println("list.size=" + list == null ? 0 : list.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
