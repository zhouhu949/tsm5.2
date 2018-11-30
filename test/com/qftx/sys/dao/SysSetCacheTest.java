package com.qftx.sys.dao;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dao.DataDictionaryMapper;
import com.qftx.tsm.option.dao.OptionMapper;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.Points;
import com.qftx.tsm.sys.bean.Product;
import com.qftx.tsm.sys.dao.CustFieldSetMapper;
import com.qftx.tsm.sys.dao.PointsMapper;
import com.qftx.tsm.sys.service.ProductService;

public class SysSetCacheTest extends BaseUtest{
	@Autowired(required=false)
	public CustFieldSetMapper custFieldSetMapper;
	@Autowired(required=false)
	public OptionMapper optionMapper;
	@Autowired(required=false)
	public DataDictionaryMapper dataDictionaryMapper;
	@Autowired(required=false)
	public PointsMapper pointsMapper;
	@Autowired(required=false)
	public CachedService cachedService;
	@Autowired(required=false)
	public ProductService productService;
	@Before
	public void before(){
		System.out.println("开始----");
	}
	
	
	public void setDictionaryCache(){
		DataDictionaryBean ddb = new DataDictionaryBean();
		String orgId = "8decbe1278b646b5a462bbd4bc80bd88";
		ddb.setOrgId(orgId);
		List<DataDictionaryBean>ddbs = dataDictionaryMapper.findByCondtion(ddb);
		cachedService.setDictionary(orgId, ddbs);
	}
	
	@Test
	public void setOptionCache(){
		OptionBean ob = new OptionBean();
		String orgId = "8decbe1278b646b5a462bbd4bc80bd88";
		ob.setOrgId("8decbe1278b646b5a462bbd4bc80bd88");
		List<OptionBean>obs = optionMapper.findByCondtion(ob);
		cachedService.setOpion(orgId, obs);
	}
	
	
	public void setProductCache(){
		Product ob = new Product();
		String orgId = "8decbe1278b646b5a462bbd4bc80bd88";
		ob.setOrgId("8decbe1278b646b5a462bbd4bc80bd88");
		List<Product>obs = productService.getListByCondtion(ob);
		cachedService.setOpionProduct(orgId, obs);
	}
	
	
	@After
    public void after(){
		System.out.println("结束-----");
	}
}
