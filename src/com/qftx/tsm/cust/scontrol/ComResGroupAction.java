package com.qftx.tsm.cust.scontrol;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alicloud.openservices.tablestore.core.utils.HttpUtil;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonAnyFormatVisitor;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dao.UserMapper;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.cust.service.ComResourceService;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.*;

@Controller
@RequestMapping(value = "/res/group")
public class ComResGroupAction {
	private static Logger logger = Logger.getLogger(ComResGroupAction.class);
	@Autowired
	private ComResourceGroupService comResourceGroupService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private LogUserOperateService logUserOperateService;
	@Autowired
	private ComResourceService comResourceService;
	@Autowired
	private LogCustInfoService logCustInfoService;
	@Autowired
	private UserMapper userMapper;
	
	  
    /**
     * 所有资源组 结构树
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/get_res_group_json")
    public List<Map<String, Object>> get_res_group_json() {
        try {
        	ShiroUser user = ShiroUtil.getShiroUser();
        	List<Map<String, Object>> list = cachedService.getResGroupList1(user.getOrgId());
        	logger.debug("获取所有资源组 结构树>>>" + JSON.toJSONString(list));
            return list;         
        } catch (Exception e) {
            throw new SysRunException(e);
        }      
    }
	
	/**
	 * 添加资源分组
	 * 
	 * @return
	 * @create 2013-10-30 上午10:02:37 wuwei
	 * @history
	 */
	@RequestMapping(value = "toResGroup")
	public String toResGroup(HttpServletRequest request, String groupId, String result,String pid) {
		Map<String, String> groupMap = new HashMap<String, String>();
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			List<ResourceGroupBean> resClassList = cachedService.getResClassList(user.getOrgId());
			request.setAttribute("resClassList", resClassList);
			if (null != groupId && !StringUtils.isBlank(groupId)) {
				groupMap.put("groupId", groupId);
				groupMap.put("orgId", user.getOrgId());
				ResourceGroupBean entity = comResourceGroupService.getByPrimaryKey(groupMap);
				request.setAttribute("pid", entity.getPid());
				request.setAttribute("resGroup", entity);
			} else {
				request.setAttribute("pid", pid);
				result = "1"; // 添加按钮标志位
				request.setAttribute("result", "1");
			}
		} catch (Exception e) {
			logger.error("添加资源分组.groupId=" + groupId + "result=" + result);
			logger.error(e.getMessage(), e);
			throw new SysRunException(e);
		}

		return "tsm/resource/add_group";
	}
	
	/**
	 * 添加或者修改资源分组
	 * 
	 * @create 2013-10-30 上午10:02:52 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping("saveOrUpdateGroup")
	public BaseJsonResult saveOrUpdateGroup(HttpServletRequest request, ResourceGroupBean resGroup, String groupId, String result,String pid) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			resGroup.setOrgId(user.getOrgId());
			Date sysdate = new Date();
			// 1. 验证名称是否重复
			resGroup.setLevel(1);
			List<ResourceGroupBean> tmpList = comResourceGroupService.getGrpListByGrpName(resGroup);
			if (tmpList.size() > 0) {
				String tmpGroupId = tmpList.get(0).getResGroupId();
				if (StringUtils.isBlank(groupId)) {// 新增时重复
					return BaseJsonResult.error("名称已存在,请更换分组名称");
				} else if (!tmpGroupId.equals(groupId)) {// 编辑时重复
					return BaseJsonResult.error("名称已存在,请更换分组名称");
				}
			}

			// 2.新增或编辑操作
			resGroup.setResGroupId(groupId);
			String resGroupId = groupId;
			if (StringUtils.isBlank(groupId) || groupId.equals("")) {
				resGroupId = StringUtils.getRandomUUIDStr();
				resGroup.setResGroupId(resGroupId);
				resGroup.setOrgId(user.getOrgId());
				resGroup.setGroupIndex(0);
				resGroup.setIsDel(new Short("0"));
				resGroup.setInputAcc(user.getAccount());
				resGroup.setInputdate(sysdate);
				resGroup.setLevel(1);
				resGroup.setPid(pid);
				request.setAttribute("loactionGroupId", resGroupId);
				comResourceGroupService.create(resGroup);
				logUserOperateService.setUserOperateLog( AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id39, AppConstant.Operate_Name39,resGroup.getGroupName(),"");
			} else {
				request.setAttribute("loactionGroupId", groupId);
				resGroup.setModifierAcc(user.getAccount());
				resGroup.setModifydate(sysdate);
				comResourceGroupService.modify(resGroup);
				logUserOperateService.setUserOperateLog( AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id40, AppConstant.Operate_Name40,resGroup.getGroupName(),"");
				request.setAttribute("result", result);
			}
			/*Map<String, String> map = cachedService.getOrgResGroupNames(user.getOrgId());
			if (map != null) {
				map.put(resGroup.getResGroupId(), resGroup.getGroupName());
				cachedService.setOrgResGroupNames(user.getOrgId(), map);
			}

			Map<String, ResourceGroupBean> groupMap = cachedService.getOrgResGroupBean(user.getOrgId());
			if (groupMap != null) {
				groupMap.put(resGroup.getResGroupId(), resGroup);
				cachedService.setOrgResGroupBean(user.getOrgId(), groupMap);
			}*/
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST1);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_BEAN);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_NAME);
			return BaseJsonResult.success(resGroupId).put("level", 1);

		} catch (Exception e) {
			logger.error("添加或者修改资源分组.groupId=" + groupId + "result=" + result);
			return BaseJsonResult.error("系统异常,请重新操作");
		}
	}
	
	/**
	 * 添加资源分类
	 * 
	 * @return
	 * @history
	 */
	@RequestMapping(value = "toAddResClass")
	public String toAddResClass(HttpServletRequest request, String groupId, String result) {
		Map<String, String> groupMap = new HashMap<String, String>();
		try {
			if (null != groupId && !StringUtils.isBlank(groupId)) {
				groupMap.put("groupId", groupId);
				groupMap.put("orgId", ShiroUtil.getShiroUser().getOrgId());
				ResourceGroupBean entity = comResourceGroupService.getByPrimaryKey(groupMap);
				request.setAttribute("resGroup", entity);
			} else {
				result = "1"; // 添加按钮标志位
				request.setAttribute("result", "1");
			}
		} catch (Exception e) {
			logger.error("添加资源分类.groupId=" + groupId + "result=" + result);
			logger.error(e.getMessage(), e);
			throw new SysRunException(e);
		}

		return "tsm/resource/add_res_class";
	}
	
	/**
	 * 添加或者修改资源分类
	 * 
	 * @create 2013-10-30 上午10:02:52 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping("saveOrUpdateResClass")
	public BaseJsonResult saveOrUpdateResClass(HttpServletRequest request, ResourceGroupBean resGroup, String groupId, String result) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			resGroup.setOrgId(user.getOrgId());
			Date sysdate = new Date();
			// 1. 验证名称是否重复
			resGroup.setLevel(0);
			List<ResourceGroupBean> tmpList = comResourceGroupService.getGrpListByGrpName(resGroup);
			if (tmpList.size() > 0) {
				String tmpGroupId = tmpList.get(0).getResGroupId();
				if (StringUtils.isBlank(groupId)) {// 新增时重复
					return BaseJsonResult.error("名称已存在,请更换分类名称");
				} else if (!tmpGroupId.equals(groupId)) {// 编辑时重复
					return BaseJsonResult.error("名称已存在,请更换分类名称");
				}
			}

			// 2.新增或编辑操作
			resGroup.setResGroupId(groupId);
			String resGroupId = groupId;
			if (StringUtils.isBlank(groupId) || groupId.equals("")) {
				resGroupId = StringUtils.getRandomUUIDStr();
				resGroup.setResGroupId(resGroupId);
				resGroup.setOrgId(user.getOrgId());
				resGroup.setIsDel(new Short("0"));
				resGroup.setInputAcc(user.getAccount());
				resGroup.setInputdate(sysdate);
				resGroup.setLevel(0);
				request.setAttribute("loactionGroupId", resGroupId);
				comResourceGroupService.create(resGroup);
				logUserOperateService.setUserOperateLog( AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id49, AppConstant.Operate_Name49,resGroup.getGroupName(),"");
			} else {
				request.setAttribute("loactionGroupId", groupId);
				resGroup.setModifierAcc(user.getAccount());
				resGroup.setModifydate(sysdate);
				comResourceGroupService.modify(resGroup);
				logUserOperateService.setUserOperateLog( AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id50, AppConstant.Operate_Name50,resGroup.getGroupName(),"");
				request.setAttribute("result", result);
			}
			/*Map<String, String> map = cachedService.getOrgResGroupNames(user.getOrgId());
			if (map != null) {
				map.put(resGroup.getResGroupId(), resGroup.getGroupName());
				cachedService.setOrgResGroupNames(user.getOrgId(), map);
			}*/

			/*Map<String, ResourceGroupBean> groupMap = cachedService.getOrgResGroupBean(user.getOrgId());
			if (groupMap != null) {
				groupMap.put(resGroup.getResGroupId(), resGroup);
				cachedService.setOrgResGroupBean(user.getOrgId(), groupMap);
			}*/
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST1);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_CLASS_LIST);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_BEAN);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_NAME);
			return BaseJsonResult.success(resGroupId).put("level", 0);

		} catch (Exception e) {
			logger.error("添加或者修改资源分类.groupId=" + groupId + "result=" + result);
			return BaseJsonResult.error("系统异常,请重新操作");
		}
	}

	/**
	 * 删除资源分组
	 * 
	 * @create 2013-10-30 上午10:03:09 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping("delResourceGroup")
	public String delResourceGroup(HttpServletRequest request, String groupId,String level) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String orgId = user.getOrgId();
			Map<String, Object> map = new HashMap<String, Object>();
			String unGroupId = comResourceGroupService.getUnGroup(ShiroUtil.getShiroUser().getOrgId());
			map.put("orgId", user.getOrgId());
			map.put("unGroupId", unGroupId);
			map.put("groupId", groupId);
			List<ResCustInfoBean> list = new ArrayList<>();
			List<Map<String, Object>> resGroupList = cachedService.getResGroupList1(orgId);
			List<Map<String, Object>> childrenList = new ArrayList<>();
			List<String> resGroupIds = new ArrayList<>();
			if ("0".equals(level)) {
				for (Map<String, Object> map2 : resGroupList) {
					if (groupId.equals(map2.get("id"))) {
						childrenList = (List<Map<String, Object>>) map2.get("children");
						for (Map<String, Object> map3 : childrenList) {
							resGroupIds.add((String) map3.get("id"));
						}
					}
				}
				map.put("groupIds", resGroupIds);
				if (resGroupIds.size() > 0) {
					String unClassId = comResourceGroupService.getUnClass(ShiroUtil.getShiroUser().getOrgId());
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("orgId", user.getOrgId());
					map1.put("pid", unClassId);
					map1.put("groupIds", resGroupIds);
					comResourceGroupService.updateGroupPid(map1);
				}
			}else {
				resGroupIds.add(groupId);
				map.put("groupIds", resGroupIds);
			}
			comResourceGroupService.deleteResourceGroupByGroupId(level,groupId,map);
			User user2 = userMapper.findUserRobotAccounts(orgId, user.getAccount());
			if (user2.getRobotStatus() !=null && user2.getRobotStatus()==1) {//判断单位下该账号是否开通机器人
				Map<String,String>params = new HashMap<String,String>();
				params.put("orgId", orgId);  
				params.put("oldGroupId", groupId);
				params.put("newGroupId", unGroupId);
				String json = JSONObject.toJSONString(params);
				String result = com.qftx.base.util.HttpUtil.doPostJSON(ConfigInfoUtils.getStringValue("robot_deleteGroup_url"), json);//用于小蜂机器人删除资源分
			}
			if ("0".equals(level)) {
				logUserOperateService.setUserOperateLog( AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id51, AppConstant.Operate_Name51, cachedService.getGroupName(groupId, user.getOrgId()) ,"");
			}else {
				logUserOperateService.setUserOperateLog( AppConstant.Module_id106,AppConstant.Module_Name106, AppConstant.Operate_id41, AppConstant.Operate_Name41, cachedService.getGroupName(groupId, user.getOrgId()) ,"");
			}
			
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST1);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_CLASS_LIST);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_BEAN);
			CachedUtil.getInstance().delete(user.getOrgId() + CachedNames.SEPARATOR + CachedNames.RES_GROUP_NAME);
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			logger.error("删除资源分组异常.groupId=" + groupId ,e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}
}
