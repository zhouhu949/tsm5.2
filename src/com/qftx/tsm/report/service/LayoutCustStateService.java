package com.qftx.tsm.report.service;

import com.qftx.tsm.report.bean.LayoutCustStateBean;
import com.qftx.tsm.report.dao.LayoutCustStateMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LayoutCustStateService {
	@Autowired
	private LayoutCustStateMapper layoutCustStateMapper;
	
	public List<LayoutCustStateBean> getCustStateLayoutForGroup(@Param("orgId")String orgId,@Param("adminAcc")String adminAcc,@Param("groupIds")List<String> groupIds){
		List<LayoutCustStateBean> list=layoutCustStateMapper.findCustStateLayoutForGroup(orgId, adminAcc, groupIds);
		List<LayoutCustStateBean> list_new=new ArrayList<LayoutCustStateBean>();
		if(list!=null && list.size()>0){
			for(LayoutCustStateBean bean :list){
				if(bean.getGroupName()!=null && !"".equals(bean.getGroupName())){
					list_new.add(bean);
				}
			}
		}
		return list_new;
	}

	public LayoutCustStateBean getCustStateLayoutForPGroup(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds){
		return layoutCustStateMapper.findCustStateLayoutForPGroup(orgId, groupIds);
	}
	
	public List<LayoutCustStateBean> getCustStateLayoutForMember(@Param("orgId")String orgId,@Param("groupIds")List<String> groupIds){
		return layoutCustStateMapper.findCustStateLayoutForMember(orgId, groupIds);
	}
	
	public LayoutCustStateBean getCustStateLayoutChart(String orgId,String adminAcc,List<String> groupIds,String account){
		return layoutCustStateMapper.findCustStateLayoutChart(orgId, adminAcc, groupIds,account);
	}
}
