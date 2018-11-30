package com.qftx.tsm.callrecord.util;

import com.alibaba.fastjson.JSON;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.tsm.callrecord.dto.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/***
 * 获取通话记录
 * @author: zwj
 * @since: 2015-12-12  下午2:11:42
 * @history: 3.x
 */
public class CallRecordGetUtil {
	protected static Log logger = LogFactory.getLog(CallRecordGetUtil.class);
	public static final String CALL_FOLLOW_RECORD_QUERY = "call_follow_record_query"; // 获取跟进信息中通话记录地址
	public static final String CALL_NALYSIS="call_nalysis"; // 团队通话效率分析
	public static final String CALL_NALYSIS_MAIN="call_nalysis_main"; // 首页通话统计
	public static final String CALL_HISRECORD_QUERY = "call_hisrecord_query"; // 获取历史通话记录地址
	public static final String CALL_NEXT_HISRECORD_QUERY = "call_next_hisrecord_query"; // 获取历史通话记录地址
	public static final String CALL_CUST_RECORD_QUERY = "call_cust_record_query"; // 跟进客户ID 获取通话记录
	
	// 获取历史通话记录 ************* 5.0新增 **********************
		public static TsGetRangeScrollResp getHisRecordeList(HistoryCallQueryDto dto) {
			try {	
				String rest = doPost(ConfigInfoUtils.getStringValue(CALL_HISRECORD_QUERY), JSON.toJSONString(dto),7000);
				return JSON.parseObject(rest, TsGetRangeScrollResp.class);
			} catch (Exception e) {
				logger.error("获取历史通话记录异常！"+e.getMessage());
			}
			return null;
		} 
		
		// 提交滚动加载请求参数
		public static TsGetRangeScrollResp getHisRecordeScrollList(String jsonScroll) {
			try {	
				String rest = doPost(ConfigInfoUtils.getStringValue(CALL_NEXT_HISRECORD_QUERY),jsonScroll,7000);
				TsGetRangeScrollResp g = JSON.parseObject(rest, TsGetRangeScrollResp.class);
				return g;
			} catch (Exception e) {
				logger.error("获取 滚动加载通话记录异常！"+e.getMessage());
			}
			return null;
		} 
		
		// 根据跟进IDS,获取通话记录
		public static List<TsmRecordCallBean> getRecordeCallFollowList(FollowCallQueryDto dto) {
			try {	
				String url = ConfigInfoUtils.getStringValue(CALL_FOLLOW_RECORD_QUERY);
				String rest = doPost(url, JSON.toJSONString(dto),7000);
				return JSON.parseArray(rest, TsmRecordCallBean.class);
			} catch (Exception e) {
				logger.error("获取通话记录异常！"+e.getMessage());
			}
			return null;
		} 
		
		// 根据客户ID,获取通话记录
		public static List<TsmRecordCallBean> getRecordeCallList(CustCallQueryDto dto) {
			try {	
				String url = ConfigInfoUtils.getStringValue(CALL_CUST_RECORD_QUERY);
				String rest = doPost(url, JSON.toJSONString(dto),7000);
				return JSON.parseArray(rest, TsmRecordCallBean.class);
			} catch (Exception e) {
				logger.error("获取通话记录异常！"+e.getMessage());
			}
			return null;
		} 
		
		// ************* 5.0新增 ***********************
	
	
	// 获取团队通话效率分析 统计
	public static List<CallNalysisDto> getRecordeCallNalysis(Map<String,Object>map) {
		List<CallNalysisDto> list=null;
		try {	
			String url = ConfigInfoUtils.getStringValue(CALL_NALYSIS);
			String sendpar = JSON.toJSONString(map, true);
			String rest = HttpUtil.doPostJSON(url, sendpar);
			list= JsonUtil.getListJson(rest, CallNalysisDto.class);
		} catch (Exception e) {
			logger.error("获取通话记录异常！"+ e.getMessage());
		}
		return list==null?new ArrayList<CallNalysisDto>():list;
	} 

	public static List<CallNalysisDto> getRecordeCallNalysisMain(Map<String,Object>map) {
		List<CallNalysisDto> list=null;
		try {	
			String url = ConfigInfoUtils.getStringValue(CALL_NALYSIS_MAIN);
			String sendpar = JSON.toJSONString(map, true);
			String rest = HttpUtil.doPostJSON(url, sendpar);
			list= JsonUtil.getListJson(rest, CallNalysisDto.class);
		} catch (Exception e) {
			logger.error("获取通话记录异常！"+ e.getMessage());
		}
		return list==null?new ArrayList<CallNalysisDto>():list;
	} 
	  
	 public static String doPost(String url, Map<String, String> params,int timeOut) throws Exception{
	        List<NameValuePair> list = new ArrayList<NameValuePair>();
	        for (String s : params.keySet()) {
	            list.add(new BasicNameValuePair(s, params.get(s)));
	        }
	        String result = null;
	        CloseableHttpClient httpclient =null;
	        InputStream instream = null;
	        CloseableHttpResponse response = null;
	        HttpPost httpPost  = null;
	        try {
				httpclient = HttpClients.createDefault();
	        	RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();//设置请求和传输超时时间
	            httpPost = new HttpPost(url);
	            httpPost.setEntity(new UrlEncodedFormEntity(list));	        	
	        	httpPost.setConfig(requestConfig);
	            response = httpclient.execute(httpPost);
	            if(response.getStatusLine().getStatusCode() == 200)  
	            {
	            	HttpEntity entity = response.getEntity();
	 	            if (entity != null) {
	 	                instream = entity.getContent();
	 	                result = IOUtils.toString(instream, "UTF-8");
	 	            }
	            }else{
	            	httpPost.abort();
	            }	           
	        }finally{
		        	if(httpPost != null){
		        		httpPost.releaseConnection();
		        	}
	        		if (instream != null) {
	        			instream.close();
	        		}
	        		if (response != null) {
	        			response.close();
	        		}
	        		if (httpclient != null) {
	        			httpclient.close();
	        		}
	        }
	        return result;
	    }
	 
	 public static String doPost(String url, String json,int timeOut) throws Exception {
	        String result = null;
	        final String APPLICATION_JSON = "application/json";
	        CloseableHttpClient httpClient = HttpClients.createDefault();
	        InputStream instream = null;
	        CloseableHttpResponse response = null;
	        HttpPost httpPost = null;
	        try{
	        	httpPost = new HttpPost(url);
		        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();//设置请求和传输超时时间       	
		    	httpPost.setConfig(requestConfig);
		        StringEntity se = new StringEntity(json, "UTF-8");
		        se.setContentType(APPLICATION_JSON);
		        httpPost.setEntity(se);
		        response = httpClient.execute(httpPost);
		        if(response.getStatusLine().getStatusCode() == 200)  
	            { 
		        	HttpEntity entity = response.getEntity();
			        if (entity != null) {
			        	instream = entity.getContent();
			            result = IOUtils.toString(instream, "UTF-8");
			        }
	            }else{
	            	httpPost.abort();
	            }		        
	        }finally{
	        	if(httpPost != null){
	        		httpPost.releaseConnection();
	        	}
        		if (instream != null) {
        			instream.close();
        		}
        		if (response != null) {
        			response.close();
        		}
        		if (httpClient != null) {
        			httpClient.close();
        		}
	        }	        
	        return result;
	    }
	 /** 将bean 对象转换为map */
	 public static Map<String, String> transBean2Map(Object obj) {  		  
	        if(obj == null){  
	            return null;  
	        }          
	        Map<String, String> map = new HashMap<String, String>();  
	        try {  
	            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());  
	            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();  
	            for (PropertyDescriptor property : propertyDescriptors) {  
	                String key = property.getName();  	  
	                // 过滤class属性  
	                if (!key.equals("page")&&!key.equals("class")) {  
	                    // 得到property对应的getter方法  
	                    Method getter = property.getReadMethod();  
	                    Object value = getter.invoke(obj);  
	                    System.out.println(key+"******"+value.toString());
	                    if(value != null){
	                    	map.put(key, value.toString());  
	                    }	                    
	                }  	  
	            }  
	        } catch (Exception e) {  
	            System.out.println("transBean2Map Error " + e);  
	        }  	  
	        return map;  	  
	    }  
	 
	 /** 将TsmRecordCallBean对象 返回Map  */
	 private static Map<String, String> retMapByCallBean(TsmRecordCallBean call){
		 Map<String, String> map = transBean2Map(call);
		 if(map!=null){
			 // 加分页查询条件
			 map.put("page.totalResult", String.valueOf(call.getPage().getTotalResult()));
			 map.put("page.showCount", String.valueOf(call.getPage().getShowCount()));
			 map.put("page.currentPage", String.valueOf(call.getPage().getCurrentPage()));
		 }
		 return map;
	 }
	 
	 
	 
	 
	 public static void main(String[] args) {
//		 TsmRecordCallBean call = new TsmRecordCallBean();
//		 call.setCalledNum("1");
//		 call.setStartDate(DateUtil.format(new Date()));
//		 Map<String, String> map = transBean2Map(call);
//		 if(map!=null){
//			 map.put("page.totalResult", String.valueOf(call.getPage().getTotalResult()));
//			 map.put("page.showCount", String.valueOf(call.getPage().getShowCount()));
//			 map.put("page.currentPage", String.valueOf(call.getPage().getCurrentPage()));
//		 }
//	     for (String s : map.keySet()) {
//	        System.out.println(s+"----"+map.get(s));
//	      }
	 
	 }
}
