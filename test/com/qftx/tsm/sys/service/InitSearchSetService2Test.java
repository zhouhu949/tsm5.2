package com.qftx.tsm.sys.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qftx.base.auth.bean.Org;
import com.qftx.tsm.credit.service.CustFieldSetQupaiService;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.dao.CustSearchSetMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.auth.service.OrgService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.sys.dao.CustSearchSetMapper1;
import com.qftx.tsm.plan.user.day.service.TestValue;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.bean.SysFileBean;
import com.qftx.tsm.sys.dto.HighSearchChildDto;
import com.qftx.tsm.sys.dto.HighSearchDto;
import com.qftx.tsm.sys.enums.SysEnum;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 我的客户（资源），待分配资源  新增 筛选结果字段。
 * @author Administrator
 *
 */
public class InitSearchSetService2Test extends BaseUtest{
	@Autowired 
	private OrgService orgService;
	@Autowired
	private CustSearchSetMapper1 custSearchSetMapper1;
	@Autowired 
	private UserService userService;
	@Autowired
	private ResCustInfoMapper resCustInfoMapper;
	@Autowired
	private CustFieldSetQupaiService custFieldSetQupaiService;
	@Autowired
	private CustSearchSetMapper custSearchSetMapper;
	@Autowired
    private CustFieldSetService custFieldSetService;
///*
//*
//*订正曲牌
//* */
//	@Test
//	public void field() {
//		try{
//				List<String> orgIds=new ArrayList<String>();
////				orgIds.add("hyx42");
//				orgIds.add("jhqphy");
////				orgIds.add("qpcs");
//				custFieldSetQupaiService.toData(orgIds);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}

	@Test
	public void main(){
		List<String>orgIds = new ArrayList<String>();
		orgIds = orgService.getAllOrgIdsByProductType("6000");//6001专业版，6000标准版
//		orgIds.add("hyxbzb3");
		if(orgIds != null && orgIds.size() >0 ){
			for(int i = 0; i<orgIds.size(); i++){
				 try {
				 	if(!"hyxbzb3".equals(orgIds.get(i))){
						execute(orgIds.get(i),"9"); // 4,9
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

/*
*订正单位数据
* */
	public void execute(String orgId,String searchModule) throws Exception{
		// 查询是否启用
		CustSearchSet entity = new CustSearchSet();
		entity.setOrgId(orgId);
		entity.setDevelopCode("inputStatus");//INPUT_STATUS
		entity.setSearchModule(searchModule);
		List<CustSearchSet> list = custSearchSetMapper1.findByCondtion(entity);
		if(!(list!=null && list.size()>0)){
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("searchModule", searchModule);
			if("4".equals(searchModule)) map.put("developCode", "custSource");
			if("9".equals(searchModule)) map.put("developCode", "resAddGroup");

			Integer sort = custSearchSetMapper1.findSortByDevelopCode(map);
			updateIntervalSort(sort+1,orgId,searchModule);
			insertSource(sort+1,orgId,searchModule);
			// 删除高级查询缓存
			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"0"+CachedNames.HIGH_SEARCH_+searchModule);
			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"1"+CachedNames.HIGH_SEARCH_+searchModule);
			List<String> accounts = userService.getUserAccounts(orgId);
			for(String account : accounts){
				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+searchModule);
			}
			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.SEARCH_SET);
		}

		// 删除高级查询缓存
		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"0"+CachedNames.HIGH_SEARCH_+"4");
		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"1"+CachedNames.HIGH_SEARCH_+"4");
		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"0"+CachedNames.HIGH_SEARCH_+"9");
		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"1"+CachedNames.HIGH_SEARCH_+"9");
		List<String> accounts = userService.getUserAccounts(orgId);
		for(String account : accounts){
			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+"4");
			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+"9");
		}
		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.SEARCH_SET);

	}

	// 区间排序做修改
	private void updateIntervalSort(Integer sort,String orgId,String searchModule)throws Exception{

			Map<String,Object>map1 = new HashMap<String, Object>();
			map1.put("orgId", orgId);
			map1.put("minSort", sort);
			map1.put("searchModule", searchModule);
			List<CustSearchSet> list1 = custSearchSetMapper1.findAllBySortInterval(map1);

			if(list1!=null && list1.size()>0){
				List<CustSearchSet>newList = new ArrayList<CustSearchSet>();
				for(CustSearchSet searchSet : list1){
					CustSearchSet newSearchSet = new CustSearchSet();
					newSearchSet.setSort((short) (searchSet.getSort() + 1));
					newSearchSet.setOrgId(searchSet.getOrgId());
					newSearchSet.setId(searchSet.getId());
					newSearchSet.setModifydate(new Date());
					newSearchSet.setModifierAcc("system");
					newList.add(newSearchSet);
				}
				custSearchSetMapper1.batchUpdate(newList);
			}
		}

	private void insertSource(Integer sort,String orgId,String searchModule){
		CustSearchSet newSearchSet = new CustSearchSet();
		newSearchSet.setSort(Short.valueOf(sort.toString()));
		newSearchSet.setOrgId(orgId);
		newSearchSet.setId(SysBaseModelUtil.getModelId());
		newSearchSet.setModifydate(new Date());
		newSearchSet.setModifierAcc("system");
		newSearchSet.setInputdate(new Date());
		newSearchSet.setInputerAcc("system");
		newSearchSet.setEnable(1);
		newSearchSet.setSearchModule(searchModule);
		newSearchSet.setSearchName("筛查结果");
		newSearchSet.setIsQuery((short)1);
		newSearchSet.setSearchCode("inputStatus");
		newSearchSet.setDevelopCode("inputStatus");
		newSearchSet.setIsShow((short)1);
		newSearchSet.setIsDisabled(1);
		newSearchSet.setState(2);
		newSearchSet.setDataType(4);
		newSearchSet.setIsFiledSet(0);
		custSearchSetMapper1.insert(newSearchSet);
	}
	/*
	* 订正历史数据
	* */
	@Test
	public void choose(){
		try {
			List<String>orgIds = new ArrayList<String>();
			orgIds = orgService.getAllOrgIdsByProductType("6000");
//		orgIds.add("rd");
			for(String orgId :orgIds){
//			if(!orgId.equals("wytt")||!orgId.equals("hyx48")||!orgId.equals("peixun4")){
				//查询count
				int count=resCustInfoMapper.findChooseCount(orgId);
				if(count>0){
					//update
					resCustInfoMapper.updateChooseByOrgId(orgId);
					for(int i=0;i<100;i++){
						//再查count
						int newCount=resCustInfoMapper.findChooseCount(orgId);
						if(newCount>0){
							resCustInfoMapper.updateChooseByOrgId(orgId);
						}else{
							break;
						}
					}
				}
//			}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	//订正查询项历史数据，20181128
	//update 所有source IS_QUERY=1,IS_SHOW=1
	@Test
	public void processSource(){
		try {
			List<String> mode_list=new ArrayList<String>();
			mode_list.add("1");
			mode_list.add("4");
			mode_list.add("5");
			mode_list.add("6");
			mode_list.add("7");

//		List<String> orgIds = orgService.getAllOrgIds();
			List<String> orgIds=new ArrayList<String>();
			orgIds.add("ny5");
			if(orgIds!=null){
				for(String orgId : orgIds){
					//source search_mode 1,4,5,6,7
					for(String mode:mode_list){
						CustSearchSet newSearchSet = new CustSearchSet();
						newSearchSet.setOrgId(orgId);
						newSearchSet.setSearchCode("source");
						newSearchSet.setSearchModule(mode);
						CustSearchSet searchSet=custSearchSetMapper.getByCondtion(newSearchSet);
						if(searchSet!=null){
							if(searchSet.getIsQuery()==1&&searchSet.getIsShow()==1){
								System.out.println("2个参数都已经勾选,不需要跟新");
							}else{
								short s1=1;
								searchSet.setIsShow(s1);
								searchSet.setIsQuery(s1);
								custSearchSetMapper.updateTrends(searchSet);

								// 删除高级查询缓存
								CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"0"+CachedNames.HIGH_SEARCH_+mode);
								CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"1"+CachedNames.HIGH_SEARCH_+mode);
								List<String> accounts = userService.getUserAccounts(orgId);
								for(String account : accounts){
									CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+mode);
								}
							}
						}
					}
						CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.SEARCH_SET);
			}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	//订正系统字段，将相关字段全部设为启用
    //企业客户：【企业字段】客户名称、【企业字段】公司网站、【联系人字段】姓名、【联系人字段】常用电话、【联系人字段】备用电话：“启用”按钮禁用，数据订正全部为启用状态。
    @Test
    public void setEnable(){
	    //state:1 ;FIELD_CODE='name' or FIELD_CODE='unithome'
        //state:2 ;FIELD_CODE='name' or FIELD_CODE='telphone' or FIELD_CODE='telphonebak'
        try {
//            List<Org> orgIds =  orgService.getAllOrgs();
            Org o=new Org();
            o.setOrgId("qpkhcs");
            o.setState(1);
            List<Org> orgIds=new ArrayList<Org>();
            orgIds.add(o);
            if(orgIds!=null&&orgIds.size()>0){
                for(Org org:orgIds){
                    String orgId=org.getOrgId();
                    int state=org.getState();
                    if(state==1){//企业
                        CustFieldSet fieldSet=new CustFieldSet();
                        fieldSet.setOrgId(orgId);
                        List<CustFieldSet> list =custFieldSetService.getListByCondtion(fieldSet);
						List<String> accounts = userService.getUserAccounts(orgId);
                        if(list!=null && list.size()>0){
                            for(CustFieldSet set:list ){
                                short s1=1;
                                if(set.getState()==1){//企业
                                    if(("name".equals(set.getFieldCode()) || "unithome".equals(set.getFieldCode())) && set.getEnable()==0){
                                        set.setEnable(s1);
                                        custFieldSetService.modifyTrends(set);

                                        // 删除缓存
                                        CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.COM_FILEDSET);
                                        CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.COM_FILEDSETS);
                                        CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.MULTI_FILEDSET);
                                        // 删除高级查询缓存
                                        for(int i = 1;i<16;i++){
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"0"+CachedNames.HIGH_SEARCH_+i);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"1"+CachedNames.HIGH_SEARCH_+i);
                                        }

                                        for(String account : accounts){
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+1);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+2);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+3);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+4);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+5);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+6);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+7);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+8);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+9);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+10);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+11);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+12);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+13);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+14);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+15);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+16);
                                        }
                                        CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.SEARCH_SET);

                                    }

                                }

                                if(set.getState()==2){//联系人
                                    if(("name".equals(set.getFieldCode()) || "telphone".equals(set.getFieldCode()) || "telphonebak".equals(set.getFieldCode())) && set.getEnable()==0){
                                        set.setEnable(s1);
                                        custFieldSetService.modifyTrends(set);

                                        // 删除缓存
                                        CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.COM_FILEDSET);
                                        CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.COM_FILEDSETS);
                                        CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.MULTI_FILEDSET);
                                        // 删除高级查询缓存
                                        for(int i = 1;i<16;i++){
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"0"+CachedNames.HIGH_SEARCH_+i);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +"1"+CachedNames.HIGH_SEARCH_+i);
                                        }
                                        for(String account : accounts){
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+1);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+2);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+3);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+4);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+5);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+6);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+7);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+8);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+9);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+10);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+11);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+12);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+13);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+14);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+15);
                                            CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+16);
                                        }
                                        CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.SEARCH_SET);
                                    }
                                }
                            }
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
