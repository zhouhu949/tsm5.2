package com.qftx.tsm.rest.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.tsm.rest.dto.HtmlBean;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by bxl on 2015/11/12.
 */
@Controller
@RequestMapping(value = "/jsonp")
public class JsonpAction {
    private static final Logger logger = Logger.getLogger(JsonpAction.class.getName());
    @RequestMapping("/error")
    public void error(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("测试异常");
        throw new Exception("测试异常");
    }
    @RequestMapping("/login")
    public void jsonp(HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
        ShiroUser user = ShiroUtil.getShiroUser();
        String str = name + "(" + JsonUtil.getJsonString(user) + ");";
        logger.debug("返回数据:" + str);
        HttpUtil.writeJsonResponse(response, str);
    }
    @RequestMapping("/loginout")
    public void loginout(HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
        SecurityUtils.getSubject().logout();
        HtmlBean bean=new HtmlBean();
        bean.setUrl(request.getRequestURI());
        bean.setInputtime(new Date());
        bean.setCode("1");
        String str = name + "(" + JsonUtil.getJsonString(bean) + ");";
        logger.debug("返回数据:" + str);
        HttpUtil.writeJsonResponse(response, str);
    }
}
