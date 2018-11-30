package com.qftx.tsm.rest.scontrol;


import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dao.UserMapper;
import com.qftx.base.auth.service.OrgService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.tsm.callrecord.dto.CallPlayDto;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by hh on 14-12-19.
 */
@Controller
@RequestMapping("/cached")
public class CachedAction {
    private static final Logger logger = Logger.getLogger(CachedAction.class.getName());

    @Autowired
    private CachedService cachedService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
	@RequestMapping("/list/{orgId}/{name}")
    public void list(HttpServletRequest request, HttpServletResponse response,@PathVariable String name, @PathVariable String orgId) {
        Map<String, Object> map = new HashMap<String, Object>();
        try{
        	 if (orgId != null) {
                 if(name!=null&&name.equals("option")){
                     map.put("option", cachedService.getOpion(orgId));
                 }else if(name!=null&&name.equals("optionGroup")){
                  	map.put("optionGroup", cachedService.getOpionGroup(orgId));
                  }else if(name!=null&&name.equals("dictionary")){
                 	map.put("dictionary", cachedService.getDictionary(orgId));
                 }else if(name!=null&&name.equals("orgGroup")){
                 	map.put("tsm_team_group", cachedService.getOrgGroup(orgId));
                 }else if(name!=null&&name.equals("orgGroupMember")){
                 	map.put("tsm_team_group_member", cachedService.getOrgGroupMember(orgId));
                 }else if(name!=null&&name.equals("optionProduct")){
                 	map.put("option_product", cachedService.getOpionProduct(orgId));
                 }else if(name!=null&&name.equals("orgUserName")){
                 	map.put("org_user_name", cachedService.getOrgUserNames(orgId));
                 }else if(name!=null&&name.equals("resGroupName")){
                 	map.put("res_group_name", cachedService.getOrgResGroupNames(orgId));
                 }else if(name!=null&&name.equals("orgUserNameId")){
                 	map.put("org_user_name_id", cachedService.getOrgUserNamesByID(orgId));
                 }else if(name!=null&&name.equals("comFiled")){
                  	map.put("contact_filed", cachedService.getContactsFiledSets(orgId));
                  	map.put("com_filed", cachedService.getComFiledSets(orgId));
                  }else if(name!=null&&name.equals("personFiled")){
                    	map.put("person_filed", cachedService.getPersonFiledSets(orgId));
                  }else if(name!=null&&name.equals(CachedNames.ORG_GROUP_MAP)){
                    	map.put("ORG_GROUP_MAP", cachedService.getOrgGroupMap(orgId));
                  }else if(name!=null&&name.equals(CachedNames.ORG_GROUP_PID_MAP)){
                        map.put("ORG_GROUP_PID_MAP", cachedService.getOrgGroupPidMap(orgId));
                  }else if(name!=null&&name.equals("jms_org_total")){
                      map.put("jms_org_total", CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.AUTH_ORG_MESSAGE));
                  }else if(name!=null&&name.equals("sys_help")){
                      map.put("sys_help", CachedUtil.getInstance().get(CachedNames.SEPARATOR + CachedNames.SYS_HELP));
                  }
                 
             }
        	  HttpUtil.writeJsonResponse(response, JsonUtil.getJsonString(map));
        }catch(Exception e){
        	logger.error(orgId+"---"+name+"获取缓存异常!", e);
        }
      
    }

    @RequestMapping("/remove/{orgId}/{name}")
    public void remove(HttpServletRequest request, HttpServletResponse response,@PathVariable String name, @PathVariable String orgId) {
    	try{   		
    		setCache(orgId,name);  
    	}catch(Exception e){
    		logger.error(orgId+"---"+name+"删除获取缓存异常!", e);
    	}  	
    }
    
    @RequestMapping("/removeAll/{name}")
    public void removeAll(HttpServletRequest request, HttpServletResponse response,@PathVariable String name) {
    	try{   	
    		// 查询所有机构ID
			List<String>orgIds = new ArrayList<String>();
			orgIds = orgService.getAllOrgIds();
			if(orgIds != null && orgIds.size() >0){
				for(int i = 0; i<orgIds.size(); i++){
					setCache(orgIds.get(i),name);
				}
			}    		
    	}catch(Exception e){
    		logger.error(name+"批量删除获取缓存异常!", e);
    	}  	
    }
    
    @RequestMapping("/deleteAll/{orgId}")
    public void deleteAll(HttpServletRequest request, HttpServletResponse response,@PathVariable String orgId) {
    	try{   	
    		if(StringUtils.isNotBlank(orgId)){
    			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.OPTION);
       			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.ORG_SALE_PROCESS);
       			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.DATADICTIONARY);
       			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.OPEN_MSG_MAP);
       			CachedUtil.getInstance().delete(CachedNames.ORG_GROUP + CachedNames.SEPARATOR + orgId);
            	 CachedUtil.getInstance().delete(CachedNames.ORG_GROUP_MEMBER + CachedNames.SEPARATOR + orgId);
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.OPTIONPRODUCT);
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_NAME);
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_NAME);
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_NAME_BY_ID);
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.CONTACTS_FILEDSET);
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.COM_FILEDSET);
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSET);
            	 CachedUtil.getInstance().delete(CachedNames.ORG_GROUP_MAP + CachedNames.SEPARATOR +orgId );
            	 CachedUtil.getInstance().delete(CachedNames.ORG_GROUP_PID_MAP + CachedNames.SEPARATOR +orgId );
	       		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER);
	       		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_COM_NO);
	            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.GC_CALL_SET);	        
    		}
    	}catch(Exception e){
    		logger.error(orgId+"根据orgId 批量删除缓存异常!", e);
    	}  	
    }
    
    @RequestMapping("/callPlay")
    public void callPlay(HttpServletRequest request, HttpServletResponse response){
        String orgId = request.getParameter("orgId");
        String account = request.getParameter("account");
        try {
            String remove = request.getParameter("remove");
            if (StringUtils.isNotBlank(orgId) && StringUtils.isNotBlank(account)) {
                if (StringUtils.isNotBlank(remove)) {
                    CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.CALL_PLAY);
                }
                List<CallPlayDto> list = cachedService.getCallPlay(orgId,account);
                HttpUtil.writeJsonResponse(response, JsonUtil.getJsonString(list));
            }
            HttpUtil.writeXmlResponse(response, "请加上orgId 参数！");
        } catch (Exception e) {
            logger.error(orgId +"--"+account+ "获取播放列表缓存异常!", e);
        }
    }
    
    /** 获取缓存key集合 */
    @RequestMapping("/listKeys")
    public void listKeys(HttpServletRequest request, HttpServletResponse response){
        try {               
                Map<String,String>map = new HashMap<String, String>();
                map.put("option","系统属性");
                map.put("dictionary", "销售管理");
                map.put("orgGroup", "用户组数据");
                map.put("orgGroupMember", "用户组与用户关系数据");
                map.put("optionProduct", "系统产品");
                map.put("resGroupName", "资源分组");
                map.put("orgUserNameId", "用户ID对应用户缓存");
                map.put("comFiled", "企业字段缓存");
                map.put("personFiled", "个人字段缓存");
                map.put("ORG_GROUP_MAP", "缓存用户组键为父部门ID");
                map.put("ORG_GROUP_PID_MAP", "缓存用户组 键为父ID");
                map.put("orgUser", "用户缓存");
                map.put("orgUserComNo", "用户通信号码缓存");
                map.put("gcCallSet", "群呼策略设置");         

                HttpUtil.writeJsonResponse(response, JsonUtil.getJsonString(map));
        } catch (Exception e) {
            logger.error("获取缓存key集合!", e);
        }
    }
    
    @RequestMapping("/deleteKey/{key}")
    public void deleteKey(HttpServletRequest request, HttpServletResponse response,@PathVariable String key){
    	try{
    		CachedUtil.getInstance().delete(key);
    	}catch(Exception e){
    		 logger.error(key+"  deleteKey 异常!", e);
    	}    	
    }
    
    @RequestMapping("/getKey/{key}")
    public void getKey(HttpServletRequest request, HttpServletResponse response,@PathVariable String key){
    	try{
    		Object get = CachedUtil.getInstance().get(key);
    		JsonConfig jsonConfig = new JsonConfig();
    		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
    		JSONArray json = JSONArray.fromObject(get, jsonConfig);
    		HttpUtil.writeJsonResponse(response,  json.toString());
    	}catch(Exception e){
    		 logger.error(key+"  getKey 异常!", e);
    	}    	
    }
    
    private void setCache(String orgId,String name){
    	 if(StringUtils.isNotBlank(orgId)){
       		 if(name!=null&&name.equals("option")){
       			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.OPTION);
       			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.ORG_SALE_PROCESS);
       			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.ORG_CUST_TYPE);	       			
       		 }else if(name!=null&&name.equals("optionGroup")){
        		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.OPTION_GROUP);
           	}else if(name!=null&&name.equals("dictionary")){
       			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.DATADICTIONARY);
       			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.OPEN_MSG_MAP);
       		 }else if(name!=null&&name.equals("orgGroup")){
       			CachedUtil.getInstance().delete(CachedNames.ORG_GROUP + CachedNames.SEPARATOR + orgId);
             }else if(name!=null&&name.equals("orgGroupMember")){
            	 CachedUtil.getInstance().delete(CachedNames.ORG_GROUP_MEMBER + CachedNames.SEPARATOR + orgId);
             }else if(name!=null&&name.equals("optionProduct")){
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.OPTIONPRODUCT);
             }else if(name!=null&&name.equals("orgUserName")){
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_NAME);
             }else if(name!=null&&name.equals("resGroupName")){
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_NAME);
             }else if(name!=null&&name.equals("orgUserNameId")){
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_NAME_BY_ID);
             }else if(name!=null&&name.equals("comFiled")){
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.CONTACTS_FILEDSET);
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.CONTACTS_FILEDSETS);
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.COM_FILEDSET);
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.COM_FILEDSETS);
     			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.MULTI_FILEDSET); 
             }else if(name!=null&&name.equals("personFiled")){
            	 CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSET);
     			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSETS);
    			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.MULTI_FILEDSET);
             }  else if(name!=null&&name.equals(CachedNames.ORG_GROUP_MAP)){
            	 CachedUtil.getInstance().delete(CachedNames.ORG_GROUP_MAP + CachedNames.SEPARATOR +orgId );
             }else if(name!=null&&name.equals(CachedNames.ORG_GROUP_PID_MAP)){
            	 CachedUtil.getInstance().delete(CachedNames.ORG_GROUP_PID_MAP + CachedNames.SEPARATOR +orgId );
             }else if(name!=null&&name.equals("orgUser")){
	       		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER);
	       	}else if(name!=null&&name.equals("orgUserComNo")){
	       		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_COM_NO);
	        }else if(name!=null&&name.equals("gcCallSet")){
	            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.GC_CALL_SET);
	        }else if(name!=null&&name.equals("jms_org_total")){
                CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.AUTH_ORG_MESSAGE);
            }else if(name!=null&&name.equals("sys_help")){
                 CachedUtil.getInstance().delete(CachedNames.SEPARATOR + CachedNames.SYS_HELP);
            }else if(StringUtils.isNotBlank(name) && "searchSet".equals(name)){
            	// 删除高级查询缓存
            	for(int i = 1;i<16;i++){
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"0"+CachedNames.HIGH_SEARCH_+i);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"1"+CachedNames.HIGH_SEARCH_+i);
    			}
    			List<String> accounts = userService.getUserAccounts(orgId);
    			for(String account : accounts){
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+1);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+2);
    				CachedUtil.getInstance().delete(orgId+ CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+3);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+4);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+5);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+6);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+7);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+8);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+9);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+10);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+11);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+12);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+13);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+14);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+15);
    				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+16);
    			}
    			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.SEARCH_SET);
            }
       	 } 
    }
    

	@RequestMapping("/removeTaoDto/{orgId}")
	public void removePool(HttpServletRequest request, HttpServletResponse response,@PathVariable String orgId) {
		try {
			String pool = "";
			if (com.qftx.common.util.StringUtils.isNotEmpty(orgId)) {
				String[] array = orgId.split(",");
				for (String tempOrgId : Arrays.asList(array)) {
					List<User> userlist = userMapper.findAllUserByUnitId(orgId);
					if (userlist != null && userlist.size() > 0) {
						for (User user : userlist) {
							pool = "1";
							cachedService.removeTaoDto(tempOrgId, user.getUserAccount(), pool);
							pool = "2";
							cachedService.removeTaoDto(tempOrgId, user.getUserAccount(), pool);
							CachedUtil.getInstance().delete(
									tempOrgId + CachedNames.SEPARATOR + user.getUserAccount() + CachedNames.SEPARATOR + CachedNames.TAO_TAG);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	@RequestMapping("/removeTaoTag/{orgId}")
	public void removeTaoTag(HttpServletRequest request, HttpServletResponse response,@PathVariable String orgId) {
		try {
			if (com.qftx.common.util.StringUtils.isNotEmpty(orgId)) {
				String[] array = orgId.split(",");
				for (String tempOrgId : Arrays.asList(array)) {
					List<User> userlist = userMapper.findAllUserByUnitId(orgId);
					if (userlist != null && userlist.size() > 0) {
						for (User user : userlist) {
							CachedUtil.getInstance().delete(
									tempOrgId + CachedNames.SEPARATOR + user.getUserAccount() + CachedNames.SEPARATOR + CachedNames.TAO_TAG);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
