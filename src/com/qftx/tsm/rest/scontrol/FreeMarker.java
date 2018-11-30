package com.qftx.tsm.rest.scontrol;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by bxl on 2015/10/27.
 */
@Controller
public class FreeMarker  {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/freemarker")
    public String index(Model model, HttpServletRequest request) {
        List<User> list = userService.getList();
        model.addAttribute("dataList", list);
        return "ftl/index";
    }


}
