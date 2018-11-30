package com.qftx.export.dao;

import java.util.List;

import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dto.ResCustInfoDto;

public interface CustDataExportMapper {
	
	public List<ResCustInfoBean> findResCustList(ResCustInfoDto dto);
	
	public Integer findResCustNum(ResCustInfoDto dto);
	
	public List<ResCustInfoBean> findIntCustList(ResCustInfoDto dto);
	
	public Integer findIntCustNum(ResCustInfoDto dto);
	
	public List<ResCustInfoBean> findSignCustList(ResCustInfoDto dto);
	
	public Integer findSignCustNum(ResCustInfoDto dto);
	
	public List<ResCustInfoBean> findSilentCustList(ResCustInfoDto dto);
	
	public Integer findSilentCustNum(ResCustInfoDto dto);
	
	public List<ResCustInfoBean> findLosingCustList(ResCustInfoDto dto);
	
	public Integer findLosingCustNum(ResCustInfoDto dto);
}
