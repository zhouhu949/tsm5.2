package com.qftx.tsm.rest.scontrol;

import com.qftx.base.auth.service.UserService;
import com.qftx.tsm.rest.service.CallRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by hh on 14-12-19.
 */
@Controller
@RequestMapping("/upload")
public class UploadAction {
    private static final Logger logger = Logger.getLogger(UploadAction.class.getName());

    @RequestMapping()
    public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("sessionid="+request.getSession().getId());
        // 获取当前登录用户
        return "rest/upload";
    }

    @RequestMapping("/test")
    public String test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("sessionid="+request.getSession().getId());
        return "rest/upload";
    }


}
