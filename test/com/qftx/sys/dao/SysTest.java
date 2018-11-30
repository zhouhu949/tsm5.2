package com.qftx.sys.dao;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.auth.bean.AuthProductResource;
import com.qftx.base.auth.bean.Resource;
import com.qftx.base.auth.bean.Role;
import com.qftx.base.auth.bean.RoleResource;
import com.qftx.base.auth.bean.UserRole;
import com.qftx.base.auth.dao.AuthProductResourceMapper;
import com.qftx.base.auth.dao.RoleMapper;
import com.qftx.base.auth.dao.RoleResourceMapper;
import com.qftx.base.auth.dao.UserRoleMapper;
import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.common.util.constants.SysConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dao.DataDictionaryMapper;
import com.qftx.tsm.option.dao.OptionMapper;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.Points;
import com.qftx.tsm.sys.dao.CustFieldSetMapper;
import com.qftx.tsm.sys.dao.PointsMapper;


public class SysTest extends BaseUtest{
	private static final Logger logger = Logger.getLogger(SysTest.class.getName());
	@Autowired(required=false)
	public CustFieldSetMapper custFieldSetMapper;
	@Autowired(required=false)
	public OptionMapper optionMapper;
	@Autowired(required=false)
	public DataDictionaryMapper dataDictionaryMapper;
	@Autowired(required=false)
	public PointsMapper pointsMapper;
	@Autowired
	public RoleMapper roleMapper;
	@Autowired
	public RoleResourceMapper roleResourceMapper;	
	@Autowired
	public AuthProductResourceMapper productResourceMapper;
	@Autowired
	public UserRoleMapper userRoleMapper;
	@Autowired
	public CachedService cachedService;
	
	@Before
	public void before(){
		System.out.println("开始----");
	}
	
	
	public void insertCustFieldBatch(){
		Map<String,Object>map = new HashMap<String, Object>();
    	map.put("state",0); // 1: 企业客户，0：个人客户
		List<CustFieldSet> oldCustFieldSet = custFieldSetMapper.findOldCustFieldSetList(map);//原始自定义字段数据
		for (CustFieldSet field : oldCustFieldSet) {
			field.setFieldId(SysBaseModelUtil.getModelId());
			field.setOrgId("8decbe1278b646b5a462bbd4bc80bd88");
			field.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
			field.setInputerAcc("hn002");
		}
		
		//批量创建自定义字段
		if(oldCustFieldSet != null && !oldCustFieldSet.isEmpty()){
			custFieldSetMapper.insertBatch(oldCustFieldSet);
		}
	}
	
	@Test
	public void insertOptionBatch(){
		String orgId = "fhtx2";
		try{   		
			// 设值缓存
    		OptionBean ob = new OptionBean();
    		ob.setOrgId(orgId);
    		List<OptionBean>obs = optionMapper.findByCondtion(ob);
    		if(obs !=null && obs.size() >0){ // 如果已存在属性设置，则直接设置缓存（有数据表示，老单位迁移）
    			cachedService.setOpion(orgId, obs);
    		}else{
    			List<OptionBean> oldOptionList = optionMapper.getOldOptionList();//选项数据
        		Map<String, String> giveUpMap = new HashMap<String, String>();	//提取出客户放弃原因的主键ID
        		for (OptionBean opt : oldOptionList) {
        			String optid = SysBaseModelUtil.getModelId();
        			if(AppConstant.SALES_TYPE_SIX.equals(opt.getItemCode()) && "F10001".equals(opt.getOptionlistId())){
        				giveUpMap.put(AppConstant.ITEMCODES[0], optid);
        			}else if(AppConstant.SALES_TYPE_SIX.equals(opt.getItemCode()) && "F10002".equals(opt.getOptionlistId())){
        				giveUpMap.put(AppConstant.ITEMCODES[1], optid);
        			}else if(AppConstant.SALES_TYPE_SIX.equals(opt.getItemCode()) && "F10003".equals(opt.getOptionlistId())){
        				giveUpMap.put(AppConstant.ITEMCODES[2], optid);
        			}
        			opt.setOptionlistId(optid);
        			opt.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
        			opt.setModifierAcc("zwjcs");
        			opt.setOrgId(orgId);
        			//处理客户放弃原因的父ID
        			if(AppConstant.ITEMCODES[0].equals(opt.getItemCode())){
        				opt.setPid(giveUpMap.get(opt.getItemCode()));
        			}else if(AppConstant.ITEMCODES[1].equals(opt.getItemCode())){
        				opt.setPid(giveUpMap.get(opt.getItemCode()));
        			}else if(AppConstant.ITEMCODES[2].equals(opt.getItemCode())){
        				opt.setPid(giveUpMap.get(opt.getItemCode()));
        			}
        		}
        		
        		// 新增之前先删除,确保不会重复
        		optionMapper.deleteByOrgId(orgId);
        		
        		//批量创建选项数据
        		if(oldOptionList != null && !oldOptionList.isEmpty()){
        			optionMapper.insertBatch(oldOptionList);
        		}
        		OptionBean ob1 = new OptionBean();
        		ob1.setOrgId(orgId);
        		List<OptionBean>obs1 = optionMapper.findByCondtion(ob1);
        		System.out.println("123");
        		//cachedService.setOpion(orgId, obs1);
    		}
	
    	}catch(Exception e){
    		logger.error(orgId+"@@@【 初始化系统属性】资源异常", e);
    	}		
	}
	
	
	public void insertDictionaryBatch(){
		List<DataDictionaryBean> oldDataList = dataDictionaryMapper.getOldDataList();//字典数据
		for (DataDictionaryBean data : oldDataList) {
			data.setDictionaryId(SysBaseModelUtil.getModelId());
			data.setOrgId("8decbe1278b646b5a462bbd4bc80bd88");
			data.setInputerAcc("hn002");
			data.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
		}
		//批量创建字典数据
		if(oldDataList != null && !oldDataList.isEmpty()){
			dataDictionaryMapper.insertBatch(oldDataList);
		}
	}
	
	
	public void insertPointsBatch(){
		List<Points>points = pointsMapper.getOldPointsList();
		for (Points data : points) {
			data.setPointsId(SysBaseModelUtil.getModelId());
			data.setOrgId("8decbe1278b646b5a462bbd4bc80bd88");
			data.setInputAcc("hn002");
			data.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
		}
		pointsMapper.insertBatch(points);
	}
	
	/** 初始化 角色资源  */
    public void initRoleResource(){
		String orgId = "8decbe1278b646b5a462bbd4bc80bd88";
		String userId = "ccd6a28f29c241aeb4921ad7c77d2adf";
		String proCode = "hyxzy";
		AuthProductResource resource = new AuthProductResource();
    	resource.setEnabled(1);
    	resource.setProCode(proCode);
    	List<AuthProductResource> resources = productResourceMapper.findByCondtion(resource);
    	if(resources != null && resources.size() >0){
    		String roleId = addRole(orgId);
    		addRoleResource(resources,roleId);
    		addUserRole(orgId,userId,roleId);
    	}
    }
	   // 角色与资源关联数据
    private void addRoleResource(List<AuthProductResource>ids,String roleId){
    	List<RoleResource>lists = new ArrayList<RoleResource>();
    	if(ids != null && ids.size() >0){
    		for(AuthProductResource resource :ids){
    			RoleResource rrs = new RoleResource();
    	    	rrs.setId(SysBaseModelUtil.getModelId());
    	    	rrs.setRoleId(roleId);
    	    	rrs.setResourceId(resource.getResourceId());
    	    	lists.add(rrs);
    		}
    		roleResourceMapper.insertBatch(lists);
    	}    	
    }
    
    // 新增角色
    private String addRole(String orgId){
    	String id = SysBaseModelUtil.getModelId();
    	Role role = new Role();
    	role.setRoleId(id);
    	role.setRoleCode("ROLE_"+id);
    	role.setRoleName("初始化角色");
    	role.setEnabled(1);
    	role.setSysType("5");
    	role.setIsbackground(0);
    	role.setIsPhones(0);
    	role.setCreateTime(new Date());
    	role.setRoleType(1);
    	role.setOrgId(orgId);
    	roleMapper.insert(role);
    	return id;
    }
    
    /** 新增角色用户关联数据  */
    private void addUserRole(String orgId,String userId,String roleId){
    	// 新增之前先删除,确保不会重复
    	userRoleMapper.deleteByUserId(userId,orgId);  	
    	String id = SysBaseModelUtil.getModelId();
    	UserRole ur = new UserRole();
    	ur.setId(id);
    	ur.setOrgId(orgId);
    	ur.setUserId(userId);
    	ur.setRoleId(roleId);
    	userRoleMapper.insert(ur);
	}
	
    // 新增初始化角色
    
    public void addRoleResource(){
    	List<RoleResource>lists = new ArrayList<RoleResource>();
    	RoleResource rrs = new RoleResource();
    	rrs.setId(SysBaseModelUtil.getModelId());
    	rrs.setRoleId("ROLE_A");
    	rrs.setResourceId("3bff096fe4ea4b3aa714a849f4291dc3");
    	RoleResource rrs1 = new RoleResource();
    	rrs1.setId(SysBaseModelUtil.getModelId());
    	rrs1.setRoleId("ROLE_A");
    	rrs1.setResourceId("268f287a4f2e4f1c817952e59a3dea01");
    	RoleResource rrs2 = new RoleResource();
    	rrs2.setId(SysBaseModelUtil.getModelId());
    	rrs2.setRoleId("ROLE_A");
    	rrs2.setResourceId("b9e9b02b89d84261b89645a842b6a7ec");
    	RoleResource rrs3 = new RoleResource();
    	rrs3.setId(SysBaseModelUtil.getModelId());
    	rrs3.setRoleId("ROLE_A");
    	rrs3.setResourceId("93b7b6d5c2c44d028263ba3b5612769c");
    	RoleResource rrs4 = new RoleResource();
    	rrs4.setId(SysBaseModelUtil.getModelId());
    	rrs4.setRoleId("ROLE_A");
    	rrs4.setResourceId("4bf71729bbbd4a7882f7a41aa7323e66");
    	RoleResource rrs5 = new RoleResource();
    	rrs5.setId(SysBaseModelUtil.getModelId());
    	rrs5.setRoleId("ROLE_A");
    	rrs5.setResourceId("9f1843179d974433a3c7a5902c4d7c49");
    	RoleResource rrs6 = new RoleResource();
    	rrs6.setId(SysBaseModelUtil.getModelId());
    	rrs6.setRoleId("ROLE_A");
    	rrs6.setResourceId("8ba953a9d6b54030925cde207a149ee7");
    	RoleResource rrs7 = new RoleResource();
    	rrs7.setId(SysBaseModelUtil.getModelId());
    	rrs7.setRoleId("ROLE_A");
    	rrs7.setResourceId("e2d4a39cccb84676a508bbe88ce98bd9");
    	RoleResource rrs8 = new RoleResource();
    	rrs8.setId(SysBaseModelUtil.getModelId());
    	rrs8.setRoleId("ROLE_A");
    	rrs8.setResourceId("81c8c78c683342ed9f44198c59f579ac");
    	RoleResource rrs9 = new RoleResource();
    	rrs9.setId(SysBaseModelUtil.getModelId());
    	rrs9.setRoleId("ROLE_A");
    	rrs9.setResourceId("7cfff55f586d4b169376c566865005f2");
    	RoleResource rrs10 = new RoleResource();
    	rrs10.setId(SysBaseModelUtil.getModelId());
    	rrs10.setRoleId("ROLE_A");
    	rrs10.setResourceId("1db8879317014764ad9c8e9ad1bb6495");
    	RoleResource rrs11 = new RoleResource();
    	rrs11.setId(SysBaseModelUtil.getModelId());
    	rrs11.setRoleId("ROLE_A");
    	rrs11.setResourceId("b285b4ad7e904fae85ef1f1d407cdc8f");
    	RoleResource rrs12 = new RoleResource();
    	rrs12.setId(SysBaseModelUtil.getModelId());
    	rrs12.setRoleId("ROLE_A");
    	rrs12.setResourceId("25fbb017d15045c4a0b7a620c73fef6c");
    	RoleResource rrs13 = new RoleResource();
    	rrs13.setId(SysBaseModelUtil.getModelId());
    	rrs13.setRoleId("ROLE_A");
    	rrs13.setResourceId("0ec9cf3852934a4da4a7678afe999601");
    	RoleResource rrs14 = new RoleResource();
    	rrs14.setId(SysBaseModelUtil.getModelId());
    	rrs14.setRoleId("ROLE_A");
    	rrs14.setResourceId("600d311cf49745f9b033574887c7f695");
    	RoleResource rrs15 = new RoleResource();
    	rrs15.setId(SysBaseModelUtil.getModelId());
    	rrs15.setRoleId("ROLE_A");
    	rrs15.setResourceId("b1c7f4c2df8311e597151c872c6365c2");
    	lists.add(rrs);
    	lists.add(rrs1);
    	lists.add(rrs2);lists.add(rrs3);
    	lists.add(rrs4);lists.add(rrs5);lists.add(rrs6);lists.add(rrs7);lists.add(rrs8);
    	lists.add(rrs9);lists.add(rrs10);lists.add(rrs11);lists.add(rrs12);lists.add(rrs13);
    	lists.add(rrs14);lists.add(rrs15);
    	roleResourceMapper.insertBatch(lists);   	
    }
    
	@After
    public void after(){
		System.out.println("结束-----");
	}
}
