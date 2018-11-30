package com.qftx.tsm.callrecord.service;

import com.qftx.tsm.callrecord.dao.CallMapper;
import com.qftx.tsm.callrecord.dto.CustResInfoDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 通信管理 短信发送
 */
@Service
public class CallService{
	Logger logger = Logger.getLogger(CallService.class);
	
	@Autowired
	private CallMapper callMapper;	
	
	/** 邮件发送异步查询 资源列表  */
	public List<CustResInfoDto> getCustByCallListPage(CustResInfoDto entity){
		if(entity.getState() != null && entity.getState() == 1){
			List<CustResInfoDto>list = callMapper.findComCustListPage(entity);
			if(list!=null && list.size()>0){
				Map<String,Object>map = new HashMap<String, Object>();
				Map<String,CustResInfoDto>map1 = new HashMap<String, CustResInfoDto>();
				map.put("orgId", entity.getOrgId());
				List<String>ids = new ArrayList<String>();
				for(CustResInfoDto dto : list){
					ids.add(dto.getCustId());
					map1.put(dto.getCustId(), dto);
				}
				map.put("ids", ids);
				list.clear();
				List<Map<String,Object>>maps = callMapper.findComCustDetails(map);				
				if(maps != null && maps.size()>0){
					CustResInfoDto dto =null;
					Map<String,String>map2 = new HashMap<String, String>(); // 存储资源ID，避免重复
					for(Map<String,Object> frmap : maps){
						if(map2.get(frmap.get("custId").toString())==null
								&& map1.get(frmap.get("custId").toString()) != null){
							map2.put(frmap.get("custId").toString(), "1");
							dto = new CustResInfoDto();
							dto.setCustId(frmap.get("custId").toString());
							dto.setCompany(map1.get(frmap.get("custId")).getCompany());
							dto.setCustName(map1.get(frmap.get("custId")).getCustName());
							dto.setEmail(frmap.get("email").toString());
							list.add(dto);
						}
					}
				}
			}
			return list;
		}
		return callMapper.findPerSonCustListPage(entity);
	}
	
	/**短信发送异步查询 资源列表  */
	public List<CustResInfoDto> getCustByCallSmsListPage(CustResInfoDto entity){
		return callMapper.findCustByCallSmsListPage(entity);
	}
}