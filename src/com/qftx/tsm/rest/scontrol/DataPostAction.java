package com.qftx.tsm.rest.scontrol;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.spring.RequestBean;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.rest.bean.CallRecordBean;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bxl on 2015/11/12.
 */
@Controller
@RequestMapping(value = "/dp")
public class DataPostAction {
    private static final Logger logger = Logger.getLogger(DataPostAction.class.getName());

    @RequestMapping("/list")
    public ModelAndView setForm(Model model) {
        model.addAttribute("resItem", new ResCustInfoBean());
        model.addAttribute("crItem", new CallRecordBean());
        return new ModelAndView("rest/dp_test");
    }

    @ResponseBody
    @RequestMapping("/test")
    public Map<String, Object> jsontest(@RequestBean("resItem") ResCustInfoBean resItem, @RequestBean("crItem") CallRecordBean crItem) throws Exception {
        logger.debug("-----测试多对象----");
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("resItem", resItem);
        params.put("crItem", crItem);
        logger.debug(JsonUtil.getJsonString(params));
        //用户法参照  http://localhost:8080/dp/test?resItem.sysType=333&crItem.sysType=111111&resItem.createTime=2015-12-14%2022:33:44
        return params;
    }


}
