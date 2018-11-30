package com.qftx.tsm.email.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.email.bean.TsmEmailSign;
import com.qftx.tsm.email.service.TsmEmailSignService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 邮箱签名
 */
@Controller
@RequestMapping("/email/sign")
public class EmailSignAction {
    private static final Logger logger = Logger.getLogger(EmailSignAction.class.getName());
    @Autowired
    private TsmEmailSignService tsmEmailSignService;

    /** 跳转至 签名操作页面 */
    @RequestMapping("/toOperaSign")
    public String toOperaSign(HttpServletRequest request){
    	try{
    		String id = request.getParameter("id");
    		if(StringUtils.isNotBlank(id)){
    			ShiroUser user = ShiroUtil.getShiroUser();
    			TsmEmailSign entity = new TsmEmailSign();
    			entity.setOrgId(user.getOrgId());
    			entity.setId(id);
    			entity.setIsDel(0);
    			entity = tsmEmailSignService.getByCondtion(entity);
    			request.setAttribute("item", entity);
    		}
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}
    	return "/call/idialog/email_sign";
    }
    
    /** 签名操作 */
    @RequestMapping("/operaSign")
    @ResponseBody
    public String operaSign(HttpServletRequest request,TsmEmailSign entity){
    	try{
    		ShiroUser user = ShiroUtil.getShiroUser();
    		entity.setOrgId(user.getOrgId());
    		entity.setIsDel(0);
    		if(StringUtils.isNotBlank(entity.getId())){
    			tsmEmailSignService.modifyTrends(entity);
    		}else{
    			entity.setId(SysBaseModelUtil.getModelId());
    			tsmEmailSignService.create(entity);
    		}
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}    
    	return AppConstant.RESULT_SUCCESS;
    }
    
    /** 签名删除 */
    @RequestMapping("/deleteSign")
    @ResponseBody
    public String deleteSign(HttpServletRequest request){
    	try{
    		String id = request.getParameter("id");
    		ShiroUser user = ShiroUtil.getShiroUser();
    		Map<String,Object>map = new HashMap<String,Object>();
    		map.put("orgId", user.getOrgId());
    		map.put("id", id);
    		tsmEmailSignService.deleteBatchById(map);   		
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}
    	return AppConstant.RESULT_SUCCESS;
    }
}
