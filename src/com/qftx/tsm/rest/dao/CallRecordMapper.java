package com.qftx.tsm.rest.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.rest.bean.CallRecordBean;

import java.util.List;
import java.util.Map;
public interface CallRecordMapper extends BaseDao<CallRecordBean> {

    public CallRecordBean get(String id);

    public List<CallRecordBean> query(CallRecordBean callRecordBean);

    /**
     * @return
     */
    public List<CallRecordBean> findAll();

    public List<CallRecordBean> queryAll(Map<String, Object> map);

    /**
     * @param user
     */
    public void insert(CallRecordBean user);
}
