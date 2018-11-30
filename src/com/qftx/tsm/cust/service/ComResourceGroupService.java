package com.qftx.tsm.cust.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.dao.ResourceGroupMapper;
import com.qftx.tsm.cust.dto.ResourceGroupDto;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("comResourceGroupService")
public class ComResourceGroupService {
	transient Logger logger = Logger.getLogger(ComResourceGroupService.class);
	@Autowired
	transient private ResourceGroupMapper resourceGroupMapper;

	public List<ResourceGroupBean> getList() {
		return resourceGroupMapper.find();
	}

	public List<ResourceGroupBean> getListByCondtion(ResourceGroupBean entity) {
		return resourceGroupMapper.findByCondtion(entity);
	}

	public List<ResourceGroupBean> getListPage(ResourceGroupBean entity) {
		return resourceGroupMapper.findListPage(entity);
	}

	public ResourceGroupBean getByPrimaryKey(String id) {
		return resourceGroupMapper.getByPrimaryKey(id);
	}

	public void create(ResourceGroupBean entity) {
		/*String resGroupId = StringUtils.getRandomUUIDStr();
		entity.setResGroupId(resGroupId);*/
		resourceGroupMapper.insert(entity);

	}

	public void createBatch(List<ResourceGroupBean> entitys) {
		resourceGroupMapper.insertBatch(entitys);
	}

	public void modify(ResourceGroupBean entity) {
		resourceGroupMapper.update(entity);

	}

	public void modifyTrends(ResourceGroupBean entity) {
		resourceGroupMapper.updateTrends(entity);

	}

	public void modifyBatch(List<ResourceGroupBean> entitys) {
		for (ResourceGroupBean entity : entitys) {
			resourceGroupMapper.update(entity);
		}

	}

	public void modifyTrendsBatch(List<ResourceGroupBean> entitys) {
		for (ResourceGroupBean entity : entitys) {
			resourceGroupMapper.updateTrends(entity);
		}
	}

	public void remove(String id) {
		resourceGroupMapper.delete(id);

	}

	public void removeBatch(List<String> ids) {
		resourceGroupMapper.deleteBatch(ids);

	}

	public List<ResourceGroupDto> getResSumList(Map<String, Object> map) {
		return resourceGroupMapper.getResSumList(map);
	}

	/**
	 * 获取单位下的资源总数
	 */

	public int getComResSumByOrgID(Map<String, Object> map) {
		return resourceGroupMapper.getComResSumByOrgID(map);
	}

	/**
	 * 获取单位下的未分组的资源总数
	 */

	public int getComResNOTGroupSumByOrgID(Map<String, String> map) {
		return resourceGroupMapper.getComResNOTGroupSumByOrgID(map);
	}

	/**
	 * 判断是否存在同名的资源组名称
	 */

	public List<ResourceGroupBean> getGrpListByGrpName(ResourceGroupBean ResourceGroupBean) {
		return resourceGroupMapper.findGrpListByGrpName(ResourceGroupBean);
	}

	public void deleteResourceGroupByGroupId(String level,String groupId,Map<String, Object> map) {
		resourceGroupMapper.updateResGroupByResId(map);
		if ("1".equals(level)) {
			resourceGroupMapper.updateResourceByGroupId(map);
		}

	}

	public ResourceGroupMapper getResourceGroupMapper() {
		return resourceGroupMapper;
	}

	public void setResourceGroupMapper(ResourceGroupMapper resourceGroupMapper) {
		this.resourceGroupMapper = resourceGroupMapper;
	}

	public List<ResourceGroupBean> queryResGroup(String orgId) {
		return resourceGroupMapper.findResGroup(orgId);
	}

	public List<Map<String, Object>> queryResGroupForShare(String orgId) {
		List<ResourceGroupBean> groupList = new ArrayList<ResourceGroupBean>();
		List<ResourceGroupBean> list = resourceGroupMapper.findResGroupForShare(orgId);
		Map<String,String> pidMap = new HashMap<String, String>();
		Map<String,List<Map<String, Object>>> dataMap = new HashMap<String,List<Map<String, Object>>>();
		List<Map<String, Object>> treeList = new ArrayList<Map<String,Object>>();
		if(list!=null && list.size()>0){
			List<Map<String, Object>> dlist;
			Map<String, Object> map;
			for(ResourceGroupBean bean:list){
				if("未分组".equals(bean.getGroupName())){
					groupList.add(0,bean);
				}else{
					groupList.add(bean);
				}
				if(StringUtils.isNotBlank(bean.getPid())){
					pidMap.put(bean.getPid(), bean.getPid());
				}
			}
			for(ResourceGroupBean rgb : groupList){
				if(StringUtils.isBlank(rgb.getPid())){
					if(dataMap.containsKey("no_pid")){
						dlist = dataMap.get("no_pid");
					}else{
						dlist = new ArrayList<Map<String,Object>>();
					}
					map = new HashMap<String, Object>();
					map.put("text", rgb.getGroupName());
					map.put("id", rgb.getResGroupId());
					dlist.add(map);
					dataMap.put("no_pid", dlist);
				}else{
					if(dataMap.containsKey(rgb.getPid())){
						dlist = dataMap.get(rgb.getPid());
					}else{
						dlist = new ArrayList<Map<String,Object>>();
					}
					map = new HashMap<String, Object>();
					map.put("text", rgb.getGroupName());
					map.put("id", rgb.getResGroupId());
					map.put("level", 1);
					dlist.add(map);
					dataMap.put(rgb.getPid(), dlist);
				}
			}
			
			List<String> pids = new ArrayList<String>(pidMap.keySet());
			Map<String, ResourceGroupBean> pGroupMap = new HashMap<String, ResourceGroupBean>();
			if(pids.size() > 0){
				List<ResourceGroupBean> pGroups = resourceGroupMapper.getByIds(orgId, pids);
				for(ResourceGroupBean rgb : pGroups){
					pGroupMap.put(rgb.getResGroupId(), rgb);
				}
			}
			
			for(String key : dataMap.keySet()){
				map = new HashMap<String, Object>();
				if(key.equals("no_pid")){
					map.put("id", key);
					map.put("text", "未分类");
					map.put("children", dataMap.get(key));
					map.put("groupIndex", 1);
					map.put("level", 0);
				}else{
					map.put("id", pGroupMap.get(key).getResGroupId());
					map.put("text", pGroupMap.get(key).getGroupName());
					map.put("children", dataMap.get(key));
					map.put("groupIndex", pGroupMap.get(key).getGroupIndex() == null ? 0 : pGroupMap.get(key).getGroupIndex());
					map.put("level", 0);
				}
				treeList.add(map);
			}
		}
		Collections.sort(treeList, new Comparator<Map<String, Object>>() {
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2) {
				return ((Integer)o1.get("groupIndex")).compareTo((Integer)o2.get("groupIndex"));
			}
		});
		return treeList;
	}
	
	public List<ResourceGroupDto> getMyResGroupAndNum(Map<String, String> map) {
		return resourceGroupMapper.getMyResGroupAndNum(map);
	}

	public HashMap<String, Integer> getMyResNums(String param) {
		return resourceGroupMapper.getMyResNums(param);
	}

	public String saveUnGroup(String orgId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		String groupId = resourceGroupMapper.findUnGroup(orgId);
		if ("".equals(groupId) || groupId == null) {
			ResourceGroupBean resGroup = new ResourceGroupBean();
			groupId = GuidUtil.getId();
			resGroup.setOrgId(user.getOrgId());
			resGroup.setGroupIndex(0);
			resGroup.setIsDel(new Short("0"));
			resGroup.setResGroupId(groupId);
			resGroup.setGroupIndex(0);
			resGroup.setIsDel(new Short("0"));
			resGroup.setType("1");
			resGroup.setInputAcc(user.getAccount());
			resGroup.setInputdate(new Date());
			resGroup.setGroupName("未分组");
			resourceGroupMapper.insert(resGroup);
		}
		return groupId;
	}

	public List<ResourceGroupBean> getShareGroupList(Map<String, String> map) {
		return resourceGroupMapper.findShareGroupList(map);
	}
	
	public ResourceGroupBean getByPrimaryKey(Map<String, String> map){
		return resourceGroupMapper.getByPrimaryKey(map);
	}
	
	public String getUnGroup(String orgId){
		return resourceGroupMapper.findUnGroup(orgId);
	}
	
	public List<ResourceGroupBean> findByGroupIds(Map<String, Object> map) {
		return resourceGroupMapper.findByGroupIds(map);
	}
	
	public void initResGroup(String orgId){
		ResourceGroupBean bean = new ResourceGroupBean();
		String groupId = GuidUtil.getId();
		bean.setResGroupId(groupId);
		bean.setType("1");
		bean.setGroupName("未分类");
		bean.setGroupIndex(0);
		bean.setInputAcc("system");
		bean.setInputdate(new Date());
		bean.setOrgId(orgId);
		bean.setLevel(0);
		bean.setIsDel((short)0);
		ResourceGroupBean bean1 = new ResourceGroupBean();
		bean1.setOrgId(orgId);
		bean1.setPid(groupId);
		bean1.setModifierAcc("system");
		bean1.setModifydate(new Date());
		bean1.setLevel(1);
		
		ResourceGroupBean bean2 = new ResourceGroupBean();
		//bean2.setResGroupId(groupId);
		bean2.setGroupName("未分类");
		bean2.setOrgId(orgId);
		bean2.setLevel(0);
		ResourceGroupBean bean3 = resourceGroupMapper.findUnClassById(bean2);
		if (bean3 != null) {
			bean1.setPid(bean3.getResGroupId());
			resourceGroupMapper.initResGroup(bean1);
			//resourceGroupMapper.insert(bean);
		}else {
			resourceGroupMapper.initResGroup(bean1);
			resourceGroupMapper.insert(bean);
		}
	}

	public String getUnClass(String orgId) {
		return resourceGroupMapper.findUnClass(orgId);
	}

	public void updateGroupPid(Map<String, Object> map) {
		resourceGroupMapper.updateGroupPid(map);
	}

}
