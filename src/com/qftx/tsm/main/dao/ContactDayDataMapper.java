package com.qftx.tsm.main.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.main.bean.ContactDayDataBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ContactDayDataMapper extends BaseDao<ContactDayDataBean> {

	Map<String, Integer> findContactReport(@Param("orgId")String orgId,@Param("accounts")List<String> accounts,@Param("curDate")String curDate,@Param("type")Integer type);

}
