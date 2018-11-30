package com.qftx.tsm.rest.scontrol;

import com.qftx.tsm.cust.dto.TaoResDto;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Created by bxl on 2015/11/12.
 */
@Controller
@RequestMapping(value = "/tt")
public class TestTaoAction {
    private static final Logger logger = Logger.getLogger(TestTaoAction.class.getName());

    @RequestMapping()
    public ModelAndView nullList(Model model) {
        return list(model);
    }

    @RequestMapping("/list")
    public ModelAndView list(Model model) {
        return new ModelAndView("rest/tao_main");
    }

    @ResponseBody
    @RequestMapping("/query")
    public List<TaoResDto> jsontest(int type) throws Exception {
        logger.debug("-----取list----");
        if (type == 1) {
         //   return TaoCacheList.getUpList();
        } else {
          //  return TaoCacheList.getNextList();
        }
        return null;
    }
    @ResponseBody
    @RequestMapping("/init")
    public List<TaoResDto> init() throws Exception {
        logger.debug("-----取list----");
       // TaoCacheListUtil.up=null;
       // TaoCacheListUtil.next=null;
        return null;
    }
}
