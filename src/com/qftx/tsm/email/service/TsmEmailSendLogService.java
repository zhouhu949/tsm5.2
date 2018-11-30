package com.qftx.tsm.email.service;

import com.qftx.tsm.email.bean.TsmEmailSendLog;
import com.qftx.tsm.email.dao.TsmEmailSendLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TsmEmailSendLogService {

    @Autowired
    private TsmEmailSendLogMapper tsmEmailSendLogMapper;


    public List<TsmEmailSendLog> getList() {
        return tsmEmailSendLogMapper.find();
    }

    public List<TsmEmailSendLog> getListByCondtion(TsmEmailSendLog entity) {
        return tsmEmailSendLogMapper.findByCondtion(entity);
    }
    public TsmEmailSendLog getListByCondtion(String id) {
        return tsmEmailSendLogMapper.getByPrimaryKey(id);
    }
    public void create(TsmEmailSendLog entity) {
        tsmEmailSendLogMapper.insert(entity);
    }

    public void createBatch(List<TsmEmailSendLog> entitys) {
        tsmEmailSendLogMapper.insertBatch(entitys);
    }

    public void modify(TsmEmailSendLog entity) {
        tsmEmailSendLogMapper.update(entity);
    }

    public void modifyTrends(TsmEmailSendLog entity) {
        tsmEmailSendLogMapper.updateTrends(entity);
    }

    public void remove(String id) {
        tsmEmailSendLogMapper.delete(id);
    }

    public void removeBatch(List<String> ids, String orgId) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orgId", orgId);
        map.put("list", ids);
        tsmEmailSendLogMapper.deleteBatchBy(map);
    }

    public List<TsmEmailSendLog> getListPage(TsmEmailSendLog entitys) {
        return tsmEmailSendLogMapper.findListPage(entitys);
    }
    public void insert(TsmEmailSendLog entitys) {
         tsmEmailSendLogMapper.insert(entitys);
    }
}
