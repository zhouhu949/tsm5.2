package com.qftx.tsm.rest.scontrol;

import com.qftx.base.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by hh on 14-12-19.
 */
@Controller
@RequestMapping("/hyx4")
public class DemoAction {
    private static final Logger logger = Logger.getLogger(DemoAction.class.getName());

    @RequestMapping()
    public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("-----------------免登系统---------------------");
        logger.debug("index=" + request.getRequestURI());
        Cookie cookie = new Cookie("jsessionid", "2jcligmgi6fh");
        cookie.setMaxAge(Integer.MAX_VALUE);
        response.addCookie(cookie);
        return "index";
    }
    @ResponseBody
    @RequestMapping("/getCode")
    public String setlogininfo(HttpSession session,HttpServletRequest request, HttpServletResponse response) throws Exception {
        String veCode = (String) session.getAttribute(Constants.DEFAULT_CAPTCHA_PARAM);
        return veCode;
    }
}
