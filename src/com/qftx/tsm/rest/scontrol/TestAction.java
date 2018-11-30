package com.qftx.tsm.rest.scontrol;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.tsm.rest.bean.CallRecordBean;
import com.qftx.tsm.rest.service.CallRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hh on 14-12-19.
 */
@Controller
@RequestMapping("/test")
public class TestAction {
    private static final Logger logger = Logger.getLogger(TestAction.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private CallRecordService callRecordService;

    @RequestMapping()
    public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取当前登录用户
        return "rest/test";
    }
    @RequestMapping("/upload")
    public String test() throws Exception {
        return "rest/upload";
    }
    @ResponseBody
    @RequestMapping("/list")
    public List<CallRecordBean> query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        logger.debug("查询通话记录");
        logger.debug("callRecordService=" + callRecordService);
        Map<String, Object> params = new HashMap<String, Object>(2);
        String orgid = request.getParameter("orgid");
        String idString = request.getParameter("ids");
        if (orgid != null && !orgid.equals("")) params.put("orgId", orgid);
        if (idString != null && !idString.equals("")) params.put("ids", idString.split(","));
        List<CallRecordBean> list = callRecordService.queryAll(params);
        logger.debug("list=" + list);
        return list;
    }

    @ResponseBody
    @RequestMapping("/user")
    public List<User> user(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("查询用户");
        List<User> list = userService.getListPage(new User());
        logger.debug("list=" + list.size());
        return list;
    }

    @ResponseBody
    @RequestMapping("/logininfo")
    public ShiroUser logininfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
      //  System.out.println( SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal());
     //   System.out.println( SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal().getClass());
        ShiroUser loginUser = ShiroUtil.getShiroUser();
        return ShiroUtil.getShiroUser();
    }
    @ResponseBody
    @RequestMapping("/getShiro")
    public ShiroUser getShiro(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ShiroUtil.getShiroUser();
    }
    @ResponseBody
    @RequestMapping("/setShiro")
    public ShiroUser setShiro(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //  System.out.println( SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal());
        //   System.out.println( SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal().getClass());
        ShiroUser loginUser = ShiroUtil.getShiroUser();
        loginUser.setMobile(System.currentTimeMillis()+"test");
        return ShiroUtil.getShiroUser();
    }
    @ResponseBody
    @RequestMapping("/clear")
    public ShiroUser clear(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShiroUtil.clearAuthorizationInfo();
        logger.debug("清除用户授权缓存信息");
        return ShiroUtil.getShiroUser();
    }
    @ResponseBody
    @RequestMapping("/cls")
    public ShiroUser clear2(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ShiroUser loginUser = ShiroUtil.getShiroUser();
        ShiroUtil.clearAuthorizationInfo(loginUser.getAccount());
        logger.debug("清除用户授权缓存信息");
        return ShiroUtil.getShiroUser();
    }




}
