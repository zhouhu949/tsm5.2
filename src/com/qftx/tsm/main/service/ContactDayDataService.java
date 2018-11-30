package com.qftx.tsm.main.service;

import com.qftx.base.util.DateUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.main.bean.ContactDayDataBean;
import com.qftx.tsm.main.dao.ContactDayDataMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class ContactDayDataService {
	private static Logger logger = Logger.getLogger(ContactDayDataService.class);
	@Autowired
	private ContactDayDataMapper contactDayDataMapper;
	@Autowired
	private ResCustInfoService resCustInfoService;

	public void contactStatusLog(String orgId, String account, String custId, Integer type, Integer status) {
		ContactDayDataBean cddb = new ContactDayDataBean();
		cddb.setAccount(account);
		cddb.setOrgId(orgId);
		cddb.setResId(custId);
		cddb.setCurrDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		cddb.setType(type);
		ContactDayDataBean dataBean = contactDayDataMapper.getByCondtion(cddb);
		if (dataBean == null) {
			if (type == 2) {
				ResCustInfoDto dto = new ResCustInfoDto();
				dto.setItemCode(AppConstant.SALES_TYPE_ONE);
				dto.setResCustId(custId);
				dto.setOrgId(orgId);
				ResCustInfoDto resCustInfoDto = resCustInfoService.getTeamCustByCustId(dto);
				cddb.setInitProcessId(resCustInfoDto.getSaleProcessId());
			}
			cddb.setLastStatus(3);
			cddb.setStatus(status);
			cddb.setId(SysBaseModelUtil.getModelId());
			contactDayDataMapper.insert(cddb);
		} else {
			dataBean.setLastStatus(dataBean.getStatus());
			dataBean.setStatus(status);
			contactDayDataMapper.update(dataBean);
		}
	}

	public void deleteSign(String orgId, String account, String custId, Integer type) {
		ContactDayDataBean cddb = new ContactDayDataBean();
		cddb.setAccount(account);
		cddb.setOrgId(orgId);
		cddb.setResId(custId);
		cddb.setCurrDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		cddb.setType(type);
		ContactDayDataBean dataBean = contactDayDataMapper.getByCondtion(cddb);
		if (dataBean != null) {
			if(type == 1){
				dataBean.setStatus(1);
			}else{
				dataBean.setStatus(dataBean.getLastStatus());
			}
			contactDayDataMapper.update(dataBean);
		}
	}

	// 意向客户首页今日关注
	public void contactCustStatusLog(String orgId, String account, String custId, Integer type, Integer status, String saleProcessId) {
		try{
			ContactDayDataBean cddb = new ContactDayDataBean();
			cddb.setAccount(account);
			cddb.setOrgId(orgId);
			cddb.setResId(custId);
			cddb.setCurrDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			cddb.setType(type);
			ContactDayDataBean dataBean = contactDayDataMapper.getByCondtion(cddb);
			if (dataBean == null) {
				// 查资源的最近销售进程Id
				cddb.setType(1);
				ContactDayDataBean dataBean1 = contactDayDataMapper.getByCondtion(cddb);
				if(dataBean1 != null && !saleProcessId.equals(dataBean1.getCurrProcessId())){
					cddb.setStatus(1); // 意向增进
				}else{
					cddb.setStatus(3); // 无变化
				}
				cddb.setInitProcessId(saleProcessId);
				cddb.setType(type);
				cddb.setId(SysBaseModelUtil.getModelId());
				contactDayDataMapper.insert(cddb);
			} else {
				if (saleProcessId.equals(dataBean.getInitProcessId())) {
					status = 3;
				}
				dataBean.setLastStatus(dataBean.getStatus());
				dataBean.setStatus(status);
				dataBean.setCurrProcessId(saleProcessId);
				contactDayDataMapper.update(dataBean);
			}
		}catch(Exception e){
			logger.error("【"+orgId+"】"+custId+"【意向客户首页今日关注】exception:", e);
		}
	
	}
	
	// type 1:资源 2:意向客户
	public Map<String, Integer> getContactReport(@Param("orgId") String orgId, @Param("accounts") List<String> accounts, @Param("curDate") String curDate,
			@Param("type") Integer type) {
		return contactDayDataMapper.findContactReport(orgId, accounts, curDate, type);
	}
}
