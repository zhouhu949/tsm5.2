package com.qftx.tsm.report.service;

import com.qftx.tsm.report.bean.LayoutCustOptionBean;
import com.qftx.tsm.report.bean.LayoutCustProductBean;
import com.qftx.tsm.report.dao.LayoutCustProductMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LayoutCustProductService {
	@Autowired
	private LayoutCustProductMapper layoutCustProductMapper;
	
	public List<LayoutCustProductBean> getCustProductLayoutForGroup(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds){
		List<LayoutCustProductBean> list=layoutCustProductMapper.findCustProductLayoutForGroup(orgId, adminAcc, groupIds);
		List<LayoutCustProductBean> list_new=new ArrayList<LayoutCustProductBean>();
		if(list!=null && list.size()>0){
			for(LayoutCustProductBean bean :list){
				if(bean.getGroupName()!=null && !"".equals(bean.getGroupName())){
					list_new.add(bean);
				}
			}
		}
		return list_new;
	}
	
	public List<LayoutCustProductBean> getCustProductLayoutForPGroup(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds){
		return layoutCustProductMapper.findCustProductLayoutForPGroup(orgId, groupIds);
	}
	
	public List<LayoutCustProductBean> getCustProductLayoutForMember(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds){
		return layoutCustProductMapper.findCustProductLayoutForMember(orgId, groupIds);
	}
	
	public List<LayoutCustProductBean> getCustProductLayoutChart(String orgId,String adminAcc,List<String> groupIds,String account){
		return layoutCustProductMapper.findCustProductLayoutChart(orgId, adminAcc, groupIds,account);
	}
}
