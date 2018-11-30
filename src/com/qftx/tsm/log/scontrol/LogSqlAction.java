package com.qftx.tsm.log.scontrol;

import com.qftx.tsm.log.dto.DtoLogSqlBean;
import com.qftx.tsm.log.service.LogSqlService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hh on 14-12-19.
 */
@Controller
@RequestMapping("/sql")
public class LogSqlAction {
    private static final Logger logger = Logger.getLogger(LogSqlAction.class.getName());

    @RequestMapping()
    public String nulllist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取当前登录用户
        return "rest/sql";
    }

    @ResponseBody
    @RequestMapping(value = "/list")
    public HashMap<String, Object> list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", request.getParameter("name"));
        params.put("time", request.getParameter("time"));
        params.put("page", request.getParameter("page"));
        params.put("pageSize", request.getParameter("rows"));
        params.put("startDate", request.getParameter("startDate"));
        params.put("endDate", request.getParameter("endDate"));
        return LogSqlService.getInstance().queryPageToJson(params);
    }

    @ResponseBody
    @RequestMapping(value = "/jsontest")
    public HashMap<String, Object> jsontest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.debug("查询SQL日志");
        return list(request, response);
    }

    @ResponseBody
    @RequestMapping("/clear")
    public HashMap<String, DtoLogSqlBean> clear(HttpServletRequest request, HttpServletResponse response) throws Exception {
        LogSqlService.getInstance().clear();
        return LogSqlService.getInstance().getClsMap();
    }

    @ResponseBody
    @RequestMapping("/rowSize")
    public HashMap<String, DtoLogSqlBean> time(Integer rowSize) throws Exception {
        LogSqlService.getInstance().rowSize = rowSize;
        return LogSqlService.getInstance().getClsMap();
    }

    @ResponseBody
    @RequestMapping("/test")
    public HashMap<String, DtoLogSqlBean> test() throws Exception {
        return LogSqlService.getInstance().getClsMap();
    }

    @ResponseBody
    @RequestMapping("/set")
    public int set(int startSql) throws Exception {
        LogSqlService.getInstance().startSql = startSql;
        return LogSqlService.getInstance().startSql;
    }
    @ResponseBody
    @RequestMapping("/get")
    public int get() throws Exception {
        return LogSqlService.getInstance().startSql;
    }


    @ResponseBody
    @RequestMapping("/json")
    public List<Map<String, String>> json() throws Exception {
        HashMap<String, DtoLogSqlBean> map = LogSqlService.getInstance().getClsMap();
        List<Map<String, String>> list = new ArrayList<>();
        for (String s : map.keySet()) {
            Map<String, String> tt = new HashMap<String, String>();
            DtoLogSqlBean bean = map.get(s);
            tt.put("id", bean.getId() + "");
            tt.put("name", bean.getName());
            tt.put("execTime", bean.getExecTime() + "");
            tt.put("sql", bean.getSql() + "");
            list.add(tt);
        }
        return list;
    }
}
