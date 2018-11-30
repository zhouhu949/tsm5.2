package com.qftx.tsm.option.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.OptionGroupBean;
import com.qftx.tsm.option.service.OptionGroupService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 系统属性分组设置
 */
@Controller
@RequestMapping("/opt/set/group")
public class OptionSetGroupControl {
	@Autowired
	private OptionGroupService optionGroupService;
	@Autowired
	private CachedService cachedService;
	
	Logger logger=Logger.getLogger(OptionSetGroupControl.class);		
	
	/** 系统属性 分组编辑页面 */
	@RequestMapping("/modfiyPage")
	public String modfiyPage(HttpServletRequest request){
		try{
			String groupId = request.getParameter("groupId");
			String itemCode = request.getParameter("code");
			if(StringUtils.isNotBlank(groupId)){
				ShiroUser shiroUser = ShiroUtil.getShiroUser();
				OptionGroupBean entity = new OptionGroupBean();
				entity.setOrgId(shiroUser.getOrgId());
				entity.setGroupId(groupId);
				entity = optionGroupService.getByCondtion(entity);
				request.setAttribute("item", entity);
			}		
			request.setAttribute("itemCode", itemCode);
			return "/tsm/option/idialog/property_set_modfiy_group";
		}catch(Exception e){
			logger.error("系统属性 分组编辑页面 异常！", e);
			return null;
		}
		
	}

	
	/** 系统属性 分组删除  */
	@RequestMapping("/remove")
	@ResponseBody
	public String remove(HttpServletRequest request){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			String groupId = request.getParameter("groupId");
			String itemCode = request.getParameter("itemCode");
			if(StringUtils.isNotBlank(groupId)){
				optionGroupService.deleteByGroupId(shiroUser.getOrgId(), itemCode,groupId,shiroUser.getAccount());
				//修改缓存
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.OPTION);
				CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.OPTION_GROUP);
			}
			return AppConstant.RESULT_SUCCESS;
		}catch (Exception e) {
			logger.error("系统属性 分组删除异常！！", e);
			return AppConstant.RESULT_FAILURE;
		}
	}
	
	/** 系统属性 分组编辑操作 */
	@RequestMapping("/modfiy")
	@ResponseBody
	public String modfiy(HttpServletRequest request,OptionGroupBean entity){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			if(StringUtils.isNotBlank(entity.getGroupId())){
				// 修改数据项
				entity.setModifierAcc(shiroUser.getAccount());
				entity.setModifydate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));	
				entity.setOrgId(shiroUser.getOrgId());
				optionGroupService.modifyTrends(entity);
			}else{
				// 新增数据项
				entity.setGroupId(SysBaseModelUtil.getModelId());
				entity.setInputerAcc(shiroUser.getAccount());
				entity.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
				entity.setIsDefault(1);
				entity.setOrgId(shiroUser.getOrgId());
				optionGroupService.create(entity);
			}	
			CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.OPTION_GROUP);
			return AppConstant.RESULT_SUCCESS;
		}catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/** 查询系统属性分组 名称and排序是否唯一 */
	@RequestMapping("/judgeOnlySort")
	@ResponseBody
	public String judgeOnlySort(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			String itemCode = request.getParameter("itemCode");
			String sort = request.getParameter("sort");
			String groupId = request.getParameter("groupId");
			String groupName = request.getParameter("groupName");
			OptionGroupBean entity = new OptionGroupBean();
			entity.setOrgId(user.getOrgId());
			entity.setItemCode(itemCode);
			entity.setSort(Integer.parseInt(sort));	
			if(StringUtils.isNotBlank(groupId)){ // 修改
				entity.setGroupId(groupId);
			}
			List<Map<String,Object>>maps = optionGroupService.getByExists(entity);
			Integer returnResult = 0;
			if(maps != null && maps.size() > 0){
				for(Map<String,Object>map : maps){
					if(groupName.equals(map.get("groupName").toString())){					
						returnResult = returnResult+1;						
					}
					if(sort.equals(map.get("sort").toString())){
						returnResult = returnResult+2;
					}
				}
			}
			return returnResult.toString();
		}catch(Exception e){
			logger.error("查询系统属性分组 排序是否唯一 异常！", e);
			return AppConstant.RESULT_FAILURE;
		}
	}

    /**  跳转变更标签分组页面 */
	@RequestMapping("/toChangeGroup")
	public String toChangeGroup(HttpServletRequest request){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids"); // 数据项IDs
			String itemCode = request.getParameter("itemCode"); 
			request.setAttribute("ids", ids);
			request.setAttribute("itemCode", itemCode);
			List<OptionGroupBean> list =  cachedService.getOptionGroupList(itemCode, user.getOrgId());
			request.setAttribute("items", list);
		} catch (Exception e) {
			logger.error("跳转变更标签分组页面 异常！", e);
			return AppConstant.RESULT_FAILURE;
		}
		return "/tsm/option/idialog/property_set_modfiy_group_change";
	}
	
	
	/** 系统属性--变更分组 */
	@ResponseBody
	@RequestMapping("/changeGroup")
	public String changeGroup(HttpServletRequest request){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			String groupId = request.getParameter("groupId");
			String itemCode = request.getParameter("itemCode");
			if(StringUtils.isNotBlank(ids) && StringUtils.isNotBlank(groupId)){
				Map<String,Object>map = new HashMap<String, Object>();
				String[] idss = ids.split(",");
				map.put("orgId", user.getOrgId());
				map.put("list", Arrays.asList(idss));
				map.put("groupId", groupId);
				map.put("itemCode",itemCode);
				optionGroupService.updateByIds(map);		
				//修改缓存
				CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.OPTION);
			}			
			return AppConstant.RESULT_SUCCESS;				
		} catch (Exception e) {
			logger.error("系统属性--变更分组 异常！", e);
			return AppConstant.RESULT_FAILURE;	
		}		
	}
	
}
