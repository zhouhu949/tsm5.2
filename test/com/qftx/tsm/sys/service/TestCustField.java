//package com.qftx.tsm.sys.service;
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import com.qftx.base.auth.service.OrgService;
//import com.qftx.common.BaseUtest;
//import com.qftx.common.util.constants.AppConstant;
//import com.qftx.tsm.sys.bean.CustFieldSet;
//import com.qftx.tsm.sys.dao.TestCustFieldSetMapper;
//import com.qftx.tsm.sys.service.CustFieldSetService;
//
///**
// * 所属行业订正 字段
// * 
// */
//public class TestCustField extends BaseUtest {
//	@Autowired
//	private TestCustFieldSetMapper testCustFieldSetMapper;
//	@Autowired
//	private OrgService orgService;
//	@Test
//	public void main(){
//		  System.out.println("开始----");
//	         // 查询所有机构ID
//			List<String>orgIds = new ArrayList<String>();
//			orgIds = orgService.getAllOrgIdsByProductType("6001");
//			if(orgIds != null && orgIds.size() >0){
//				for(int i = 0; i<orgIds.size(); i++){	   
//					updateField(orgIds.get(i));
//				  }	          
//			}
//	        //updateField(null);    
//	         System.out.println("结束----");
//	}
//	
//	
//	public void updateField(String orgId){
//		// 修改所属行业 dataType = 3
//		Map<String,Object>map = new HashMap<String, Object>();
//		map.put("orgId", orgId);
//		map.put("fieldCode", "companyTrade");
//		testCustFieldSetMapper.updateByFieldCode(map);
//	}
//	
//	
//}
