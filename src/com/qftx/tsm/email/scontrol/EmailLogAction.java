package com.qftx.tsm.email.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.HttpUtil;
import com.qftx.tsm.email.bean.TsmEmailSendLog;
import com.qftx.tsm.email.service.TsmEmailSendLogService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 鍚堝悓绠＄悊
 */
@Controller
@RequestMapping("/email/log")
public class EmailLogAction {
    private static final Logger logger = Logger.getLogger(EmailLogAction.class.getName());
    @Autowired
    private TsmEmailSendLogService tsmEmailSendLogService;

    @RequestMapping()
    public String nullIndex(HttpServletRequest request, TsmEmailSendLog item) {
        return index(request, item);
    }

    @RequestMapping("/list")
    public String index(HttpServletRequest request, TsmEmailSendLog item) {
        ShiroUser user = ShiroUtil.getShiroUser();
        item.setOrgId(user.getOrgId());
        List<TsmEmailSendLog> list = tsmEmailSendLogService.getListPage(item);
        request.setAttribute("list", list);
        request.setAttribute("item", item);
        TsmEmailSendLog newBean = new TsmEmailSendLog();
        if (list.size() > 0) {
            newBean = list.get(0);
        }
        request.setAttribute("info", newBean);
        logger.debug("list" + list.size());
        return "email/log_list";
    }

    @ResponseBody
    @RequestMapping("/get")
    public TsmEmailSendLog get(HttpServletRequest request, TsmEmailSendLog item) {
        ShiroUser user = ShiroUtil.getShiroUser();
        item.setOrgId(user.getOrgId());
        List<TsmEmailSendLog> list = tsmEmailSendLogService.getListByCondtion(item);
        if (list.size() > 0) {
            return list.get(0);
        }
        return new TsmEmailSendLog();
    }

    @RequestMapping("/del")
    public void del(HttpServletRequest request, HttpServletResponse response, String ids) {
        try {
            ShiroUser user = ShiroUtil.getShiroUser();
            //  tsmEmailSendLogService.removeBatch(user.getOrgId(), Arrays.asList(ids.split(",")));
            HttpUtil.writeTextResponse(response, "1");
        } catch (Exception e) {
            try {
                HttpUtil.writeTextResponse(response, "-1");
            } catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
            }
        }
    }
}
