package com.qftx.tsm.area.service;

import com.qftx.tsm.area.bean.ChinaCityBean;
import com.qftx.tsm.area.bean.ChinaCountyBean;
import com.qftx.tsm.area.bean.ChinaProvinceBean;
import com.qftx.tsm.area.dao.ChinaCityMapper;
import com.qftx.tsm.area.dao.ChinaCountyMapper;
import com.qftx.tsm.area.dao.ChinaProvinceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaService {
	@Autowired
	private ChinaProvinceMapper chinaProvinceMapper;
	@Autowired
	private ChinaCityMapper chinaCityMapper;
	@Autowired
	private ChinaCountyMapper chinaCountyMapper;
	
	public List<ChinaProvinceBean> getProvinceBeans(){
		return chinaProvinceMapper.find();
	}
	
	public List<ChinaCityBean> getChinaCityBeans(Integer pid){
		ChinaCityBean cityBean = new ChinaCityBean();
		cityBean.setPid(pid);
		return chinaCityMapper.findByCondtion(cityBean);
	}
	
	public List<ChinaCountyBean> getChinaCountyBeans(Integer cid){
		ChinaCountyBean countyBean = new ChinaCountyBean();
		countyBean.setCid(cid);
		return chinaCountyMapper.findByCondtion(countyBean);
	}
	
	public ChinaProvinceBean getChinaProvinceByPid(Integer pid){
		ChinaProvinceBean cb = new ChinaProvinceBean();
		cb.setPid(pid);
		return chinaProvinceMapper.getByCondtion(cb);
	}
	
	public ChinaCityBean getChinaCityByCid(Integer cid){
		ChinaCityBean ccb = new ChinaCityBean();
		ccb.setCid(cid);
		return chinaCityMapper.getByCondtion(ccb);
	}
	
	public ChinaCountyBean getChinaCountyByOid(Integer oid){
		ChinaCountyBean ccb = new ChinaCountyBean();
		ccb.setOid(oid);
		return chinaCountyMapper.getByCondtion(ccb);
	}
	
	public List<ChinaCityBean> getAllCity(){
		return chinaCityMapper.find();
	}
	
	public List<ChinaCountyBean> getAllCounty(){
		return chinaCountyMapper.find();
	}
}
