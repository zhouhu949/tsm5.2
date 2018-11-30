package com.qftx.tsm.cust.service;


import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.cust.bean.TsmCustSynConfigBean;
import com.qftx.tsm.cust.bean.TsmCustSynTempDataBean;
import com.qftx.tsm.cust.dao.TsmCustSynConfigMapper;
import com.qftx.tsm.cust.dao.TsmCustSynTempDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class TsmCustSynConfigService {
	@Autowired
	private TsmCustSynConfigMapper tsmCustSynConfigMapper;
	@Autowired
	private TsmCustSynTempDataMapper tsmCustSynTempDataMapper;
	
	/***
	 * 该单位是否配置了资源同步接口
	 * @param orgId
	 * @param type 1：跟进信息，2：签约客户，3：订单信息
	 * @return 
	 */
	public boolean isConfigExist(String orgId,Integer type)throws Exception{
		return isConfigExist(orgId,type,null);
	}

	/***
	 * 该单位是否配置了资源同步接口
	 * @param orgId
	 * @param type 1：跟进信息，2：签约客户，3：订单信息
	 * @param orderAuth 订单条件(1：未审核订单，2：审核通过的订单)
	 * @return 
	 */
	public boolean isConfigExist(String orgId,Integer type,Integer orderAuth)throws Exception{
		TsmCustSynConfigBean bean = new TsmCustSynConfigBean();
		bean.setOrgId(orgId);
		bean.setSynType(type);
		bean.setOrderAuth(orderAuth);
		List<TsmCustSynConfigBean> list =  tsmCustSynConfigMapper.findByCondtion(bean);
		if(list!=null && list.size() > 0){
			return true;
		}
		return false;
	}
	
	
	/***
	 *  入库 资源同步临时表
	 * @param orgId
	 * @param type 1：跟进信息，2：签约客户，3：订单信息
	 * @param jsonData
	 */
	public void insetCustSynTempData(String orgId,Integer type,String jsonData)throws Exception{
		TsmCustSynTempDataBean bean = new TsmCustSynTempDataBean();
		bean.setId(SysBaseModelUtil.getModelId());
		bean.setOrgId(orgId);
		bean.setJsonData(jsonData);
		bean.setInputtime(new Date());
		bean.setType(type);
		tsmCustSynTempDataMapper.insert(bean);
	}
	
}
