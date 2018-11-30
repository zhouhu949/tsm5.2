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
@RequestMapping("/email/send")
public class EmailSendAction {
    private static final Logger logger = Logger.getLogger(EmailSendAction.class.getName());
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
        logger.debug("list" + list.size());
        return "email/send_list";
    }

    @ResponseBody
    @RequestMapping("/get")
    public TsmEmailSendLog get(HttpServletRequest request, TsmEmailSendLog item) {
        ShiroUser user = ShiroUtil.getShiroUser();
        item.setOrgId(user.getOrgId());
        List<TsmEmailSendLog> list = tsmEmailSendLogService.getListByCondtion(item);
        return item;
    }

    @RequestMapping("/del")
    public void del(HttpServletRequest request, HttpServletResponse response, String ids) {
        try {
            ShiroUser user = ShiroUtil.getShiroUser();
            //   contractService.deleteContractBatch(user.getOrgId(), Arrays.asList(ids.split(",")));
            HttpUtil.writeTextResponse(response, "1");
        } catch (Exception e) {
            try {
                HttpUtil.writeTextResponse(response, "-1");
            } catch (Exception e1) {
                logger.error(e1.getMessage(), e1);
            }
            //  throw new SysRunException(e);
        }
    }


}
