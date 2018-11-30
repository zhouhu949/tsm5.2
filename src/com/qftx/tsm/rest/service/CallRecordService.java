package com.qftx.tsm.rest.service;

import com.qftx.tsm.rest.bean.CallRecordBean;
import com.qftx.tsm.rest.dao.CallRecordMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by bxl on 2015/9/18.
 */
@Service
public class CallRecordService {
    private static final Logger logger = Logger.getLogger(CallRecordService.class.getName());
    @Autowired
    private CallRecordMapper flowMapper;
    /**
     * @param id
     * @return
     */
    public List<CallRecordBean> query(CallRecordBean id) {
        return flowMapper.query(id);
    }

    public CallRecordBean get(String id) {
        return flowMapper.get(id);
    }

    /**
     * @return
     */
    public List<CallRecordBean> findAll() {
        return flowMapper.findAll();
    }

    public List<CallRecordBean> queryAll(Map<String, Object> map) {
        return flowMapper.queryAll(map);
    }

    public HashMap<String, Object> queryPageToJson(Map<String, String> params) {
        HashMap<String, Object> json = new HashMap<String, Object>();
        int ipage = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
        int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));
        CallRecordBean itemBean = new CallRecordBean();
        itemBean.setCallId( params.get("ids"));
        itemBean.setOrgId( params.get("orgId"));
        logger.debug("ipage=" + ipage);
        logger.debug("pageSize=" + pageSize);
        if (pageSize == 0) {
            List<CallRecordBean> list = flowMapper.findListPage(itemBean);
            json.put("total", itemBean.getPage().getTotalResult());
            json.put("rows", list);
            logger.debug("JSON数据:" + json.toString());
            return json;
        } else {
            itemBean.getPage().setCurrentPage(ipage);
            itemBean.getPage().setShowCount(pageSize);
            List<CallRecordBean> list = flowMapper.findListPage(itemBean);
            json.put("total", itemBean.getPage().getTotalResult());
            json.put("rows", list);
            logger.debug("JSON数据:" + json.toString());
            return json;
        }
    }

    /**
     * @param user
     * @throws Exception
     */
    public void insert(CallRecordBean user) throws Exception {
        flowMapper.insert(user);
    }

}
