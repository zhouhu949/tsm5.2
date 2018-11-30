package com.qftx.tsm.contract.service;

import com.alibaba.fastjson.JSONObject;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.SysRunException;
import com.qftx.tsm.contract.bean.ContractOrderBean;
import com.qftx.tsm.contract.bean.ContractOrderExpressInfoBean;
import com.qftx.tsm.contract.dao.ContractOrderExpressInfoMapper;
import com.qftx.tsm.contract.dao.ContractOrderMapper;
import com.qftx.tsm.contract.util.ExpHttpUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ContractOrderExpressInfoService {
	
	private Log log = LogFactory.getLog(ContractOrderExpressInfoService.class);
	
	@Autowired
	private ContractOrderExpressInfoMapper contractOrderExpressInfoMapper;
	@Autowired
	private ContractOrderMapper contractOrderMapper;
	
	public Map<String, Object> searchExpressInfo(String orgId,String orderId,String courierNumber) throws Exception{
		Map<String, Object> data_map = new HashMap<String, Object>();
		List<Map<String, Object>> express_info = new ArrayList<Map<String,Object>>();
		ContractOrderExpressInfoBean bean = new ContractOrderExpressInfoBean();
		bean.setOrgId(orgId);
		bean.setCourierNumber(courierNumber);
		ContractOrderExpressInfoBean express = contractOrderExpressInfoMapper.getByCondtion(bean);
		String expTextName=null,expSpellName=null;
		if(express == null || (express.getCourierStatus() != 4 && express.getInvalidTime().compareTo(new Date()) == -1)){
			String expressSpellName="auto";

			if(express != null && StringUtils.isNotBlank(express.getExpressSpellName())) expressSpellName=express.getExpressSpellName();
			
			String resultJson = ExpHttpUtils.getExpInfo(courierNumber,expressSpellName);
			
			Map<String, Object> result_map = JSONObject.parseObject(resultJson, Map.class);
			
			if((int)result_map.get("showapi_res_code") != 0){
				throw new SysRunException(result_map.get("showapi_res_error").toString());
			}
			
			Map<String, Object> body_map = (Map<String, Object>)result_map.get("showapi_res_body");
			
			if(!(boolean)body_map.get("flag")){
				log.error("express api return:\n"+resultJson);
				throw new SysRunException("物流信息获取失败！");
			}
			
			if(body_map.get("expTextName") != null) expTextName = body_map.get("expTextName").toString();
			
			if(body_map.get("expSpellName") != null) expSpellName = body_map.get("expSpellName").toString();
			
			if(body_map.get("data") != null) express_info = (List<Map<String, Object>>) body_map.get("data");
			
			int status = (int)body_map.get("status");
			Date invalidTime = null;
			if(status != 4){
				invalidTime = DateUtil.addHours(new Date(),2);
			}
			if(express == null){
				bean.setContext(JSONObject.toJSONString(express_info));
				bean.setInvalidTime(invalidTime);
				bean.setCourierStatus(status);
				bean.setId(SysBaseModelUtil.getModelId());
				bean.setExpressName(expTextName);
				bean.setExpressSpellName(expSpellName);
				contractOrderExpressInfoMapper.insert(bean);
			}else{
				express.setCourierStatus(status);
				express.setContext(JSONObject.toJSONString(express_info));
				express.setInvalidTime(invalidTime);
				express.setExpressName(expTextName);
				express.setExpressSpellName(expSpellName);
				contractOrderExpressInfoMapper.update(express);
			}
			//修改订单
			ContractOrderBean order = new ContractOrderBean();
			order.setOrgId(orgId);
			order.setId(orderId);
			order.setCourierStatus(status);
			contractOrderMapper.update(order);
		}else{
			express_info = JsonUtil.getListJson(express.getContext(), Map.class);
			expTextName = express.getExpressName();
		}
		data_map.put("data", express_info);
		data_map.put("expTextName", expTextName);
		return data_map;
	}
}
