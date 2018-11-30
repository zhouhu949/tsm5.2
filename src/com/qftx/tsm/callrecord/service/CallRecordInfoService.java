package com.qftx.tsm.callrecord.service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.RoundUtil;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.tsm.callrecord.dto.CallRecordListDto;
import com.qftx.tsm.callrecord.dto.CallRecordPageDto;
import com.qftx.tsm.callrecord.dto.ConditionDto;
import com.qftx.tsm.callrecord.dto.CustCallQueryDto;
import com.qftx.tsm.callrecord.dto.DtoCallRecordInfoBean;
import com.qftx.tsm.callrecord.dto.TsmRecordCallBean;
import com.qftx.tsm.callrecord.dto.TsmRecordCallDto;
import com.qftx.tsm.callrecord.util.CallRecordGetUtil;
import com.qftx.tsm.cust.bean.ResCustInfoBean;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

/**
 * 查询通话数据
 * * User： bxl
 * Date： 2015/12/2
 * Time： 13:39
 */
@Service
public class CallRecordInfoService {
    private Logger logger = Logger.getLogger(CallRecordInfoService.class);

    /**
     * 查询通话记录(5.1新增)
     */
    public CallRecordListDto queryCallRecord(ConditionDto bean) throws Exception {
        String jsondata = query(bean);
        CallRecordListDto queryCallRecordBean = JSON.parseObject(jsondata, CallRecordListDto.class);
        logger.debug("call list=" + queryCallRecordBean.getBeans().size());
        return queryCallRecordBean;
    }

    public String query(ConditionDto bean) throws Exception {
        String sendpar = JSON.toJSONString(bean, true);
        String url = ConfigInfoUtils.getStringValue("call_record_query");
        logger.debug("查询URl:" + url);
        logger.debug("查询参数:\n" + sendpar);
        String str = HttpUtil.doPostJSON(url, sendpar);
        logger.debug("返回数据:\n" + str);
        return str;
    }

	// 根据客户ID,获取通话记录
	public  List<TsmRecordCallBean> getRecordeCallList(CustCallQueryDto dto) {
		try {	
			String url = ConfigInfoUtils.getStringValue("call_cust_record_query");
			String rest = doPost(url, JSON.toJSONString(dto),7000);
			return JSON.parseArray(rest, TsmRecordCallBean.class);
		} catch (Exception e) {
			logger.error("获取通话记录异常！"+e.getMessage());
		}
		return null;
	} 
    
	
    public static String doPost(String url, String json,int timeOut) throws Exception {
        String result = null;
        final String APPLICATION_JSON = "application/json";
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeOut).setConnectTimeout(timeOut).build();//设置请求和传输超时时间       	
    	httpPost.setConfig(requestConfig);
        StringEntity se = new StringEntity(json, "UTF-8");
        se.setContentType(APPLICATION_JSON);
        httpPost.setEntity(se);
        HttpResponse response = httpClient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            result = IOUtils.toString(instream, "UTF-8");
        }
        return result;
    }
    /**
     * 保存通话记录
     */
    public String saveCallRecordInfo(ResCustInfoBean item, ShiroUser user, String saleProcessId, String saleProcessName, String followId) throws Exception {

        HashMap<String, String> map = new HashMap<String, String>();

/**
 * code	varchar(32)	必须填
 orgId	varchar(32)	必须填
 callType	int(1)	必须填
 callState	varchar(2)	必须填
 callerNum	varchar(30)	必须填
 callerComNum	varchar(30)	必须填
 calledNum	varchar(30)	必须填
 transNum	varchar(30)	必须填
 startTime	datetime	必须填
 timeElngth	int(11)	必须填
 recordUrl	text	必须填
 dataresource	varchar(2)	必须填
 communication_no	varchar(20)	必须填
 inputAcc	varchar(30)	必须填
 data	longtext	可为空
 */
        String code = GuidUtil.getId();
        map.put("code", code);
        map.put("orgId", item.getOrgId());
        map.put("custType", item.getType() + "");
        map.put("callState", RoundUtil.getRandom(1, 2) + "");
        map.put("callerNum", item.getTelphone());
        map.put("calledNum", item.getTelphone());
        map.put("callerCommNum", item.getFax());
        map.put("startTime", DateUtil.getDataAll());
        map.put("timeLength", RoundUtil.getRandom(0, 20) + "");
        map.put("dataresource", "1");
        map.put("communication_no", "1");
        map.put("recordUrl", code);
        map.put("inputAcc", user.getAccount());
        map.put("saleProcessId", saleProcessId);
        map.put("saleProcessName", saleProcessName);
        map.put("followId", followId);


        map.put("custName", item.getName());

        map.put("custId", item.getResCustId());
        map.put("inputName", user.getName());
        map.put("orgName", user.getOrgName());
        //String str = TestHttpCallData.doPostJSON("http://192.168.1.210:8080/callrecordinfo/save", map);
        String url = ConfigInfoUtils.getStringValue("call_record_save");
        String str = HttpUtil.doPostJSON(url, map);
        logger.debug("返回数据:" + str);
        return str;
    }
}
