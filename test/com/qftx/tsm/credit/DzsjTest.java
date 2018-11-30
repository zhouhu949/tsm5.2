package com.qftx.tsm.credit;

import static com.qftx.common.util.constants.SMSInitCode.map;
import static org.junit.Assert.*;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedUtil;
import com.qftx.tsm.cust.dao.BlackListBeanMapper;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import org.apache.poi.util.SystemOutLogger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qftx.base.auth.service.OrgService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.credit.service.CustFieldSetQupaiService;
import com.qftx.tsm.cust.dto.LogBatchInfoDto;
import com.qftx.tsm.log.bean.LogBatchInfoBean;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.sys.bean.CustSearchSet;

public class DzsjTest  extends BaseUtest{
	@Autowired
	private OrgService  orgService;
	@Autowired
	private CustFieldSetQupaiService  custFieldSetQupaiService;
	@Autowired
	private ResCustInfoMapper resCustInfoMapper;
	@Autowired
	private BlackListBeanMapper blackListBeanMapper;


//	//清除缓存
//		@Test
//	public void deleteIsDel(){
//			List<String> orgIds=orgService.getAllOrgIds();
//			for(String orgId:orgIds){
//				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_CUST);
//				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_COMP);
//			}
//	}

//	@Test
//	public void queryLogResOperateList(){
////		List<String> orgIds=orgService.getAllOrgIds();
//		
//		List<String> orgIds=new ArrayList<String>();
//		orgIds.add("newboss3");
//		custFieldSetQupaiService.ToData(orgIds);
//	}

	//黄山删除数据
//		@Test
//	public void deleteIsDel(){
//			for(int i=1;i<=50;i++){
//				resCustInfoMapper.deleteIsDel();
//				System.out.println("删除数据量为："+(i*5000));
//			}
//	}
	//黄山删除资源明细数据
			@Test
	public void deleteCustDetail(){

				try{
					for(int i=0;i<190;i++){
						System.out.println(i+"__ing");
                        List<String> list=new ArrayList<String>();
                        Integer start=i*1000;
                        Integer end=1000;
                        System.out.println("start_"+(start+1000));
					    list=resCustInfoMapper.selectDttxDetailIds(start,end);
						if(list!=null && list.size()>0){
							Map<String,Object> map=new HashMap<String,Object>();
							map.put("custIdList",list);
							List<String> custList=resCustInfoMapper.selectDttxCustIds(map);
							if(custList!=null && custList.size()>0){
								list.removeAll(custList);
								Map<String,Object> map_del=new HashMap<String,Object>();
								map_del.put("idList",list);
								if(list!=null &&list.size()>0){
								resCustInfoMapper.deleteDetail(map_del);
								System.out.println("删除1count:"+list.size());
                                }else{
								    System.out.println("没有数据可以删了");
                                }

							}else{
								Map<String,Object> map_del=new HashMap<String,Object>();
								map_del.put("idList",list);
								resCustInfoMapper.deleteDetail(map_del);
								System.out.println("删除2count:"+list.size());
							}
						}
					}
				}catch (Exception e){
					e.printStackTrace();
				}
	}

//	//黑名单
//	@Test
//	public void black(){
//		List<String> orgIds=orgService.getAllOrgIds();
//
//		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
//		Map<String,String> map=new HashMap<String,String>();
//		for(String orgId : orgIds){
//			String balck_Count=blackListBeanMapper.selectCountByOgrId(orgId);
//			map.put(orgId,balck_Count);
//	    }
//	    System.out.println("查询完毕。开始打印");
//		for(String key:map.keySet()){//keySet获取map集合key的集合  然后在遍历key即可
//		 String value = map.get(key).toString();//
//			if(!value.equals("0")){
//		 		System.out.println("key:"+key+" ——————总数为:"+value);
//			}
//		 }
//
//	}
}
