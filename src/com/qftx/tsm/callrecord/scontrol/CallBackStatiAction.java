package com.qftx.tsm.callrecord.scontrol;

import com.qftx.common.exception.SysRunException;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 回拨统计
 * Date： 2016/1/12
 * Time： 19:33
 */
@Controller
@RequestMapping("/callBack/stati")
public class CallBackStatiAction {
    private Logger logger = Logger.getLogger(CallBackStatiAction.class);

    /** 回拨日统计 */
    @RequestMapping("/callBackStatiDay")
    public String callBackStatiDay(HttpServletRequest request){
    	try{
    		
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}
    	return "/call/frame/callBackStatiDay";
    }
   
    /** 回拨月统计 */
    @RequestMapping("/callBackStatiMonth")
    public String callBackStatiMonth(HttpServletRequest request){
    	try{
    		
    	}catch(Exception e){
    		throw new SysRunException(e);
    	}
    	return "/call/frame/callBackStatiMonth";
    }
}
