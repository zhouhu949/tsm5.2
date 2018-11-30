package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.WxBindBean;
import com.qftx.tsm.cust.dto.BindWxDto;

import java.util.List;

public interface WxBindMapper extends BaseDao<WxBindBean> {

	WxBindBean findWxBindInfo(WxBindBean bean);

	// List<WxBindBean> findWxBindInfo_p(WxBindBean bean);

	void updateByLinkNameId(WxBindBean bean);

	List<BindWxDto> findWxBindList(WxBindBean bean);

	WxBindBean findBindedByWx(WxBindBean bean);

	void deleteBindedByWxId(WxBindBean bean);

	void deleteBindedByLinkId(WxBindBean bean);

}
