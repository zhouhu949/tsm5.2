package com.qftx.tsm.main.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.main.bean.AuthUserResourceQuickBean;
import com.qftx.tsm.main.dao.AuthUserResourceQuickMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class AuthUserResourceQuickService {
	@Autowired
	private AuthUserResourceQuickMapper authUserResourceQuickMapper;
	
	public List<AuthUserResourceQuickBean> getByCondition(AuthUserResourceQuickBean arqb){
		return authUserResourceQuickMapper.findByCondtion(arqb);
	}
	
	/** 根据单位ID，用户ID 批量删除  */
	public void removeByUserIdBatch(Map<String,Object>map){
		authUserResourceQuickMapper.deleteByUserIdBatch(map);
	}
	
	/** 根据单位ID，用户ID 删除  */
	public void removeByUserIdOrgId(Map<String,Object>map){
		authUserResourceQuickMapper.deleteByUserIdOrgId(map);
	}
	
	/** 
	 * 设置快捷菜单
	 * @param ids
	 * @param user 
	 * @create  2015年12月10日 下午7:41:29 lixing
	 * @history  
	 */
	public void setUserQuickMenu(String ids,ShiroUser user){
		authUserResourceQuickMapper.deleteByUserId(user.getId());
		if(ids.length() > 0){
			String[] idArr = ids.split(",");
			for(String id : idArr){
				AuthUserResourceQuickBean arqb = new AuthUserResourceQuickBean();
				arqb.setId(SysBaseModelUtil.getModelId());
				arqb.setUserId(user.getId());
				arqb.setOrgId(user.getOrgId());
				arqb.setResourceId(id);
				authUserResourceQuickMapper.insert(arqb);
			}
		}
	}
	
	/** 
	 * 角色编辑模块中 修改权限配置，快捷菜单时 调用 
	 * @param resourceIds 新资源ID 集合
	 * @create  zwj 
	 * */
	public void modfiyAuthUserRes(String resourceIds,String orgId,String userId){
		authUserResourceQuickMapper.deleteByUserId(userId);
		if(resourceIds.length() > 0){
			String[] idArr = resourceIds.split(",");
			List<AuthUserResourceQuickBean> authUserResourceQuickList = new ArrayList<AuthUserResourceQuickBean>();
			for(String id : idArr){				
				AuthUserResourceQuickBean arqb = new AuthUserResourceQuickBean();
				arqb.setId(SysBaseModelUtil.getModelId());
				arqb.setUserId(userId);
				arqb.setOrgId(orgId);
				arqb.setResourceId(id);
				authUserResourceQuickList.add(arqb);				
			}
			authUserResourceQuickMapper.insertBatch(authUserResourceQuickList);
		}
	}

	
	/** 根据单位ID，资源ID 批量删除  */
	public void deleteByBatch(Map<String,Object>map){
		authUserResourceQuickMapper.deleteByBatch(map);
	}
	
	public void insertBatch(List<AuthUserResourceQuickBean> authUserResourceQuickList){
		authUserResourceQuickMapper.insertBatch(authUserResourceQuickList);
	}
}
