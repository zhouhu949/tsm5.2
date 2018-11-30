package com.qftx.tsm.rest.scontrol;

import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.tsm.rest.service.CallRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hh on 14-12-19.
 */
@Controller
@RequestMapping("/button")
public class ButtonAction {
    private static final Logger logger = Logger.getLogger(ButtonAction.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private CallRecordService callRecordService;

    @RequestMapping()
    public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取当前登录用户
        return "rest/button_list";
    }
    @ResponseBody
    @RequestMapping("/clear")
    public ShiroUser clear(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShiroUtil.clearAuthorizationInfo();
        logger.debug("清除当前用户授权缓存信息");
        return ShiroUtil.getShiroUser();
    }
    @ResponseBody
    @RequestMapping("/cls")
    public ShiroUser cls(HttpServletRequest request, HttpServletResponse response,String name) throws Exception {
        ShiroUtil.clearAuthorizationInfo(name);
        logger.debug("清除"+name+"用户授权缓存信息");
        return ShiroUtil.getShiroUser();
    }
    @ResponseBody
    @RequestMapping("/clsall")
    public ShiroUser clsAll(HttpServletRequest request, HttpServletResponse response,String name) throws Exception {
        ShiroUtil.clearAllAuthorizationInfo();
        logger.debug("清除所有用户授权缓存信息");
        return ShiroUtil.getShiroUser();
    }
}
