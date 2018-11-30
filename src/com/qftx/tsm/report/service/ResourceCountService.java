package com.qftx.tsm.report.service;

import com.qftx.common.cached.CachedService;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.dao.ResourceGroupMapper;
import com.qftx.tsm.report.bean.ResourceCountBean;
import com.qftx.tsm.report.dao.ResourceCountMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ResourceCountService {
	private static final Logger logger = Logger.getLogger(ResourceCountService.class);
	@Autowired
	private ResourceCountMapper resourceCountMapper;
    @Autowired
	private ResourceGroupMapper resourceGroupMapper;
    @Autowired
    private CachedService cachedService;
		
	public  List<ResourceCountBean> selectResource(ResourceCountBean bean){
		List<ResourceCountBean> list =new ArrayList<ResourceCountBean>();
			try {
				list=resourceCountMapper.selectResource(bean);
				if(list!=null&&list.size()>0){
					for(ResourceCountBean beans :list){
						beans.setWillCountstr(division(beans.getWillCount() ,beans.getResCount()));//意向数/资源数
						beans.setSignCountstr(division(beans.getSignCount() ,beans.getResCount()));//签约数/资源数
						beans.setCallCountstr(division(beans.getCallCount() ,beans.getResCount()));//接通数/资源数
						beans.setVaildCallCountstr(division(beans.getVaildCallCount() ,beans.getResCount()));//有效通话数/资源数
						beans.setErrorCountstr(division(beans.getErrorCount() ,beans.getResCount()));//错误数/资源数

					}
				}
				
			} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			}
		return	list;
	}
	
	
	public List<Map<String,Object>> getGrouplist(ResourceCountBean bean){
		List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
		List<ResourceCountBean> list=selectResource(bean);
		Map<String,Object> maps=new HashMap<String,Object>();
		maps.put("orgId", bean.getOrgId());
		maps.put("groupIds", bean.getGroupList());
		List<ResourceGroupBean> resList=resourceGroupMapper.findResClassList2(maps);
		
		if(resList!=null&&resList.size()>0){
			for(ResourceGroupBean ResourceGroup :resList){
				Map<String,Object> map=new HashMap<String,Object>();
				List<ResourceCountBean> valus=new ArrayList<ResourceCountBean>();
				if(list!=null&&list.size()>0){
					for(ResourceCountBean ResourceCount :list){
						if(ResourceCount.getPid()==ResourceGroup.getResGroupId()||ResourceGroup.getResGroupId().endsWith(ResourceCount.getPid())){
							valus.add(ResourceCount);
						}
					}
				}
//				if(valus.size()<1){
//			    List<ResourceGroupBean> grouplist=resourceGroupMapper.findGroupList(user.getOrgId(), ResourceGroup.getResGroupId());
//				if(grouplist!=null&&grouplist.size()>0){
//					for(ResourceGroupBean rgbean:grouplist){
//					    ResourceCountBean noValue=new ResourceCountBean();
//					    noValue.setGroupName(rgbean.getGroupName());
//					    noValue.setResGroupId(rgbean.getResGroupId());
//						noValue.setResCount(0);
//						noValue.setWillCount(0);
//						noValue.setSignCount(0);
//						noValue.setCallCount(0);
//						noValue.setVaildCallCount(0);
//						noValue.setErrorCount(0);
//						noValue.setWillCountstr(resourceCountService.division(0 ,0));//意向数/资源数
//						noValue.setSignCountstr("0/0.00%");//签约数/资源数
//						noValue.setCallCountstr("0/0.00%");//接通数/资源数
//						noValue.setVaildCallCountstr("0/0.00%");//有效通话数/资源数
//						noValue.setErrorCountstr("0/0.00%");//错误数/资源数
//						valus.add(noValue);	
//					}
//				}
//
//				}
				if(valus.size()>1){
					int resSum=0;
					int willSum=0;
					int signSum=0;
					int callSum=0;
					int vaildCallSum=0;
					int errorSum=0;
					for(ResourceCountBean rcbean:valus){
						resSum=resSum+rcbean.getResCount();
						willSum=willSum+rcbean.getWillCount();
						signSum=signSum+rcbean.getSignCount();
						callSum=callSum+rcbean.getCallCount();
						vaildCallSum=vaildCallSum+rcbean.getVaildCallCount();
						errorSum=errorSum+rcbean.getErrorCount();
						
						
					}
					ResourceCountBean rcbean=new ResourceCountBean();
					rcbean.setGroupName("合计");
					rcbean.setResCount(resSum);
					rcbean.setWillCountstr(division(willSum ,resSum));
					rcbean.setSignCountstr(division(signSum ,resSum));//签约数/资源数
					rcbean.setCallCountstr(division(callSum ,resSum));//接通数/资源数
					rcbean.setVaildCallCountstr(division(vaildCallSum ,resSum));//有效通话数/资源数
					rcbean.setErrorCountstr(division(errorSum ,resSum));//错误数/资源数
					valus.add(rcbean);
				}
				if(valus!=null&&valus.size()>0){
				map.put("valus", valus);
				map.put("GroupFlId", ResourceGroup.getResGroupId());
				map.put("GroupFlName", ResourceGroup.getGroupName());
				returnList.add(map);
				}
			}
		}
		return returnList;
	}
	
	
	public static String division(int a ,int b){
        String result = "";
        float num =(float)a/b;
        if(a==0&&b==0){
        	result="0/0.00%";
        }else if(num*100<0.01&&a!=0){
        	result =String.valueOf(a)+"/0.01%";	
        }else{
            DecimalFormat df = new DecimalFormat("0.00");
            
            result =String.valueOf(a)+"/"+df.format(num*100)+"%";	
        }


        return result;

    }

}
