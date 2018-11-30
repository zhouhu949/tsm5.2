package com.qftx.tsm.report.scontrol;

import com.qftx.base.auth.service.UserService;
import com.qftx.tsm.rest.service.CallRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 沉默客户统计
 * User： bxl
 * Date： 2015/12/18
 * Time： 15:29
 */
@Controller
@RequestMapping(value = "/report/lossInfo")
public class LossInfoAction {
    private static final Logger logger = Logger.getLogger(LossCustAction.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private CallRecordService callRecordService;

    @RequestMapping()
    public String list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取当前登录用户
        return "report/lossInfo";
    }

}
