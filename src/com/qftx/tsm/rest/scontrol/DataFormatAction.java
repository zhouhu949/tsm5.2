package com.qftx.tsm.rest.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.tsm.worklog.bean.WorkLogInfoBean;
import com.qftx.tsm.worklog.dao.WorkLogInfoMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by bxl on 2015/11/12.
 */
@Controller
@RequestMapping(value = "/df")
public class DataFormatAction {
    private static final Logger logger = Logger.getLogger(DataFormatAction.class.getName());
    @Autowired
    private WorkLogInfoMapper workInfoMapper;

    @ResponseBody
    @RequestMapping("/test")
    public WorkLogInfoBean jsontest(String id) throws Exception {
       return null;
    }
    @RequestMapping("/list")
    public void list(HttpServletRequest request, HttpServletResponse response,String id) throws Exception {
        
    }
    @RequestMapping("/error")
    public void error(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("测试异常");
        logger.debug("AppContextUtil="+ AppContextUtil.getBean("userService"));
       throw new Exception("测试异常");
    }
    @RequestMapping("/jsonp")
    public void jsonp(HttpServletRequest request, HttpServletResponse response,String name) throws Exception {
        ShiroUser user= ShiroUtil.getShiroUser();
       HttpUtil.writeJsonResponse(response,name+"("+JsonUtil.getJsonString(user)+");");
    }
}
