package com.qftx.tsm.rest.scontrol;

import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DbUtil;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.tsm.rest.bean.CallRecordBean;
import com.qftx.tsm.rest.service.CallRecordService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value = "/query")
public class QueryAction {
    private static final Logger logger = Logger.getLogger(QueryAction.class.getName());
    @Autowired
    private UserService userService;
    @Autowired
    private CallRecordService callRecordService;

    @RequestMapping(value = "/list")
    public String query(HttpServletRequest request, HttpServletResponse response) throws Exception {
        long start = System.currentTimeMillis();
        logger.debug("查询通话记录");
        System.out.println("callRecordService=" + callRecordService);
        Map<String, Object> params = new HashMap<String, Object>(2);
        String orgid = request.getParameter("orgid");
        String idString = request.getParameter("ids");
        if (orgid != null && !orgid.equals("")) params.put("orgId", orgid);
        if (idString != null && !idString.equals("")) params.put("ids", idString.split(","));
        List<CallRecordBean> list = callRecordService.queryAll(params);
        //  HttpUtil.writeJsonResponse(response, JsonUtil.getJsonString(list));
        request.setAttribute("list", list);
        System.out.println("查询通话记录时间=" + (System.currentTimeMillis() - start));
        return "rest/list";
    }

    @RequestMapping(value = "/json")
    public void json(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        System.out.println("查询通话记录");
        System.out.println("callRecordService=" + callRecordService);
        Map<String, Object> params = new HashMap<String, Object>(2);
        String orgid = request.getParameter("orgid");
        String idString = request.getParameter("ids");
        if (orgid != null && !orgid.equals("")) params.put("orgId", orgid);
        if (idString != null && !idString.equals("")) params.put("ids", idString.split(","));
      /*  params.put("page", request.getParameter("page"));
        params.put("pageSize", request.getParameter("rows"));
        params.put("sort", request.getParameter("sort"));
        params.put("order", request.getParameter("order"));*/
        List<CallRecordBean> list = callRecordService.queryAll(params);
        System.out.println("list=" + list);
        HttpUtil.writeJsonResponse(response, JsonUtil.getJsonString(list));
    }

    @ResponseBody
    @RequestMapping(value = "/jsontest")
    public HashMap<String, Object> jsontest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("orgId", request.getParameter("orgId"));
        params.put("ids", request.getParameter("ids"));
        params.put("page", request.getParameter("page"));
        params.put("pageSize", request.getParameter("rows"));
        params.put("startDate", request.getParameter("startDate"));
        params.put("endDate", request.getParameter("endDate"));
        params.put("sort", request.getParameter("sort"));
        params.put("order", request.getParameter("order"));
        return callRecordService.queryPageToJson(params);
    }


    @RequestMapping("resource_list")
    public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 获取当前登录用户
        return "auth/resource_list";
    }

    @ResponseBody
    @RequestMapping("/logininfo")
    public ShiroUser logininfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //  System.out.println( SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal());
        //   System.out.println( SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal().getClass());
        ShiroUser loginUser = ShiroUtil.getShiroUser();
        return ShiroUtil.getShiroUser();
    }

    @ResponseBody
    @RequestMapping("/setShiro")
    public ShiroUser setlogininfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //  System.out.println( SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal());
        //   System.out.println( SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal().getClass());
        ShiroUser loginUser = ShiroUtil.getShiroUser();
        loginUser.setMobile(System.currentTimeMillis() + "test");
        return ShiroUtil.getShiroUser();
    }

    @RequestMapping(value = "/user")
    public String user(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return "auth/user_list";
    }

    @ResponseBody
    @RequestMapping(value = "/userlist")
    public HashMap<String, Object> userlist(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        params.put("name", request.getParameter("name"));
        params.put("loginname", request.getParameter("loginname"));
        params.put("page", request.getParameter("page"));
        params.put("pageSize", request.getParameter("rows"));
        params.put("startDate", request.getParameter("startDate"));
        params.put("endDate", request.getParameter("endDate"));
        params.put("sort", request.getParameter("sort"));
        params.put("order", request.getParameter("order"));
        return userService.queryPageToJson(params);
    }

    @ResponseBody
    @RequestMapping(value = "/jsonlist", method = RequestMethod.GET)
    public List<CallRecordBean> list(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType("application/json; charset=UTF-8");
        System.out.println("查询通话记录");
        String sql = "select * from tsm_record_call where org_id=\"d9fd7d961f90404387a2910372ece37d\" limit 0,30";
        // List<CallRecordBean> list = callRecordService.findAll();
        List<CallRecordBean> list = querySql(sql);
        System.out.println("list=" + list.size());
        return list;
    }

    private List<CallRecordBean> querySql(String sql) throws SQLException {
        System.out.println(sql);
        Connection conn = DbUtil.getDruidDataSource("dataSource7").getConnection();
        Statement st = conn.createStatement();
        ResultSet rec = st.executeQuery(sql);
        List<CallRecordBean> list = new ArrayList<CallRecordBean>();
        while (rec.next()) {
            CallRecordBean bean = new CallRecordBean();
            bean.setCallId(rec.getString("call_id"));
            bean.setOrgId(rec.getString("org_id"));
            // bean.setTableName("tsm_record_card");
            // bean.setDbName("test_db");
            list.add(bean);
        }
        //   conn.commit();
        DbUtil.close(conn);
        return list;
    }
    @ResponseBody
    @RequestMapping(value = "/testspringcached")
    public List<CallRecordBean> testspringcached(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        List<CallRecordBean> list = new ArrayList<>();
        if (id == null) {
            list = callRecordService.findAll();
        } else {
            list.add(callRecordService.get(id));
        }
        return list;
    }

}
