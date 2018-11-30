package com.qftx.tsm.report.service;

import com.qftx.tsm.report.bean.LayoutCustOptionBean;
import com.qftx.tsm.report.bean.LayoutCustStateBean;
import com.qftx.tsm.report.dao.LayoutCustOptionMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LayoutCustOptionService {
	@Autowired
	private LayoutCustOptionMapper layoutCustOptionMapper;
	
	public List<LayoutCustOptionBean> getCustOptionLayoutForGroup(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds,@Param("type")Integer type){
		List<LayoutCustOptionBean> list=layoutCustOptionMapper.findCustOptionLayoutForGroup(orgId, adminAcc, groupIds, type);
		List<LayoutCustOptionBean> list_new=new ArrayList<LayoutCustOptionBean>();
		if(list!=null && list.size()>0){
			for(LayoutCustOptionBean bean :list){
				if(bean.getGroupName()!=null && !"".equals(bean.getGroupName())){
					list_new.add(bean);
				}
			}
		}
		return list_new;
	}
	
	public List<LayoutCustOptionBean> getCustOptionLayoutForPGroup(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds,@Param("type")Integer type){
		return layoutCustOptionMapper.findCustOptionLayoutForPGroup(orgId, groupIds, type);
	}
	
	public List<LayoutCustOptionBean> getCustSaleProcLayoutForMember(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds,@Param("type")Integer type){
		return layoutCustOptionMapper.findCustSaleProcLayoutForMember(orgId, groupIds, type);
	}
	
	public List<LayoutCustOptionBean> getCustSaleProcLayoutChart(String orgId,String adminAcc,List<String> groupIds,Integer type,String account){
		return layoutCustOptionMapper.findCustSaleProcLayoutChart(orgId, adminAcc, groupIds, type,account);
	}
}
