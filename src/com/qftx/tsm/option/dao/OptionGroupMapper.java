package com.qftx.tsm.option.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.option.bean.OptionGroupBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OptionGroupMapper extends BaseDao<OptionGroupBean>{
	
	//判断排序是否唯一
	public Integer findSortByExists(OptionGroupBean entity);
	
	public OptionGroupBean getByPrimaryKeyAndOrgId(@Param("groupId") String groupId,@Param("orgId") String orgId);
		
	public List<String>findOptionGroupNames(Map<String,Object>map);	
	
	public void deleteByGroupId(Map<String,String>map);
	
	public void deleteByOrgId(String orgId);
	
	public List<Map<String,Object>> findByExists(OptionGroupBean entity);
}
