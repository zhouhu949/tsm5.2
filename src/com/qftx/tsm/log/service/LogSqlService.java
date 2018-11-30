package com.qftx.tsm.log.service;

import com.qftx.base.util.DateUtil;
import com.qftx.tsm.log.dto.DtoLogSqlBean;
import com.qftx.tsm.log.dto.DtoLogSqlList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * User： bxl
 * Date： 2016/3/30
 * Time： 10:13
 */
public class LogSqlService {
    protected static Log logger = LogFactory.getLog(LogSqlService.class);
    private static LogSqlService ourInstance = new LogSqlService();
    private static HashMap<String, DtoLogSqlBean> clsMap = new HashMap<String, DtoLogSqlBean>();
    private static int execTime = 0;
    public static int rowSize = 1000;
    public static int startSql = 0;
    private static int id = 1;

    public static LogSqlService getInstance() {
        return ourInstance;
    }

    private LogSqlService() {
    }

    public void put(String key, DtoLogSqlBean value) {
        if (value != null && value.getExecTime() >= execTime) {
            if (clsMap.size() > rowSize) {
                clear();
            }
            logger.debug("put:" + clsMap.size());
            value.setId(id);
            clsMap.put(id + "", value);
            ++id;
        }
    }

    public void remove(String key) {
        clsMap.remove(key);
    }

    public void clear() {
        clsMap.clear();
        id = 1;
    }

    public HashMap<String, DtoLogSqlBean> getClsMap() {
        return clsMap;
    }

    public HashMap<String, DtoLogSqlBean> getClsMap(long time) {
        HashMap<String, DtoLogSqlBean> map = new HashMap<String, DtoLogSqlBean>();
        for (String s : clsMap.keySet()) {
            DtoLogSqlBean bean = clsMap.get(s);
            if (bean.getExecTime() >= time) {
                int id = (map.size() == 0 ? 1 : map.size());
                map.put(id + "", bean);
            }
        }
        return map;
    }

    public List<DtoLogSqlBean> getClsMapList(long time) {
        List<DtoLogSqlBean> map = new ArrayList<>();
        for (String s : clsMap.keySet()) {
            DtoLogSqlBean bean = clsMap.get(s);
            if (bean.getExecTime() >= time) {
                map.add(bean);
            }
        }
        Collections.sort(map, new DtoLogSqlList());
        return map;
    }

    public List<DtoLogSqlBean> getClsMapList(DtoLogSqlBean item) {
        List<DtoLogSqlBean> map = new ArrayList<>();
        logger.debug("clsMap:" + clsMap.size());
        for (String s : clsMap.keySet()) {
            DtoLogSqlBean bean = clsMap.get(s);
            boolean isTrue1 = false;
            boolean isTrue2 = false;
            boolean isTrue3 = false;
            boolean isTrue4 = false;
            if (bean.getExecTime() >= item.getExecTime()) {
                isTrue1 = true;
            }

            if (bean.getName() != null && item.getName() != null && bean.getName().indexOf(item.getName()) != -1) {
                isTrue2 = true;
            }

            if (item.getStartDate() != null && !"".equals(item.getStartDate())) {
                Date start = DateUtil.parseDate(item.getStartDate());
                if (start != null && item.getInputTime().getTime() >= start.getTime()) {
                    isTrue3 = true;
                }
            }
            if (item.getEndDate() != null && !"".equals(item.getEndDate())) {
                Date start = DateUtil.parseDate(item.getEndDate());
                if (start != null && item.getInputTime().getTime() <= start.getTime()) {
                    isTrue4 = true;
                }

            }
            if (item.getExecTime() == 0) {
                isTrue1 = true;
            }
            if (item.getName() == null || "".equals(item.getName())) {
                isTrue2 = true;
            }
            if (item.getStartDate() == null || "".equals(item.getStartDate())) {
                isTrue3 = true;
            }
            if (item.getEndDate() == null || "".equals(item.getEndDate())) {
                isTrue4 = true;
            }
            logger.debug("isTrue1:" + isTrue1);
            logger.debug("isTrue2:" + isTrue2);
            logger.debug("isTrue3:" + isTrue3);
            logger.debug("isTrue4:" + isTrue4);
            if (isTrue1 && isTrue2 && isTrue3 && isTrue4) {
                map.add(bean);
            }
        }
        Collections.sort(map, new DtoLogSqlList());
        item.getPage().setTotalResult(map.size());
        int start =item.getPage().getCurrentResult();
        int i=0;
        logger.debug("map="+map.size());
        logger.debug("start="+start);
        List<DtoLogSqlBean> list = new ArrayList<>();
        for (DtoLogSqlBean dtoLogSqlBean : map) {
            if(i>=start){
                list.add(dtoLogSqlBean) ;
                if(list.size()>=item.getPage().getShowCount())break;
            }
            i++;
        }

        return list;
    }

    public HashMap<String, Object> queryPageToJson(Map<String, String> params) {
        HashMap<String, Object> json = new HashMap<String, Object>();
        int ipage = params.get("page") == null ? 0 : Integer.parseInt(params.get("page"));
        int pageSize = params.get("pageSize") == null ? 0 : Integer.parseInt(params.get("pageSize"));
        int time = params.get("time") == null ? 0 : Integer.parseInt(params.get("time"));
        int rowSize = params.get("rowSize") == null ? 0 : Integer.parseInt(params.get("rowSize"));
        DtoLogSqlBean itemBean = new DtoLogSqlBean();
        itemBean.setName(params.get("name"));
        itemBean.setExecTime(time);
        itemBean.setStartDate(params.get("startDate"));
        itemBean.setEndDate(params.get("endDate"));

        logger.debug("ipage=" + ipage);
        logger.debug("pageSize=" + pageSize);
        if (pageSize == 0) {
            List<DtoLogSqlBean> list = getClsMapList(itemBean);
            json.put("total", itemBean.getPage().getTotalResult());
            json.put("rows", list);
            logger.debug("JSON数据:" + json.toString());
            return json;
        } else {
            itemBean.getPage().setCurrentPage(ipage);
            itemBean.getPage().setShowCount(pageSize);
            List<DtoLogSqlBean> list = getClsMapList(itemBean);
            json.put("total", itemBean.getPage().getTotalResult());
            json.put("rows", list);
            logger.debug("JSON数据:" + json.toString());
            return json;
        }
    }

    public void setClsMap(HashMap<String, DtoLogSqlBean> clsMap) {
        this.clsMap = clsMap;
    }
}
