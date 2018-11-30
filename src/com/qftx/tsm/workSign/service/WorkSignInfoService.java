package com.qftx.tsm.workSign.service;

import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.cust.bean.ResCustInfoDetailBean;
import com.qftx.tsm.cust.dao.ResCustInfoDetailMapper;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.workSign.bean.WorkSignInfoBean;
import com.qftx.tsm.workSign.dao.WorkSignInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class WorkSignInfoService {
	@Autowired
	private WorkSignInfoMapper workSignInfoMapper;
	@Autowired
	private ResCustInfoMapper resCustInfoMapper;
	@Autowired
	private ResCustInfoDetailMapper resCustInfoDetailMapper;
	@Autowired
	private CachedService cachedService;
	
	public List<WorkSignInfoBean> getWorkSignInfos(Map<String, Object> map){
		String isState = map.get("isState").toString();
		
		List<WorkSignInfoBean> list = workSignInfoMapper.findWorkSignInfos(map);
		if(list.size() > 0){
			List<String> ids = new ArrayList<String>();
			List<String> tocherIds = new ArrayList<String>();
			
			Map<String, ResCustInfoDto> custsMap = new HashMap<String, ResCustInfoDto>();
			Map<String, ResCustInfoDetailBean> detailsMap = new HashMap<String, ResCustInfoDetailBean>();
			Map<String, String> userNameMaps = cachedService.getOrgUserNames(map.get("orgId").toString());
			for(WorkSignInfoBean wsi : list){
				ids.add(wsi.getCustomerId());
				if(StringUtils.isNotBlank(wsi.getToucherId())){
					tocherIds.add(wsi.getToucherId());
				}
			}
			
			List<ResCustInfoDto> custs = resCustInfoMapper.findCustPoolList(ids, map.get("orgId").toString());
			for(ResCustInfoDto rcid : custs) custsMap.put(rcid.getResCustId(), rcid);
			
			if(tocherIds.size() > 0){
				List<ResCustInfoDetailBean> detailBeans = resCustInfoDetailMapper.findByIds(tocherIds, map.get("orgId").toString());
				for(ResCustInfoDetailBean detail : detailBeans) detailsMap.put(detail.getTscidId(), detail);
			}
			
			
			ResCustInfoDto custDto;
			ResCustInfoDetailBean detailBean;
			
			for(WorkSignInfoBean workBean : list){
				custDto = custsMap.get(workBean.getCustomerId());
				workBean.setUserName(userNameMaps.get(workBean.getUserId()));
				if(isState.equals("1")){
					if(custDto != null){
						workBean.setName(custDto.getName());
					}
					detailBean = detailsMap.get(workBean.getToucherId());
					if(detailBean != null){
						workBean.setToucherName(detailBean.getName());
					}
				}else{
					workBean.setName(custDto.getCompany());
					workBean.setToucherName(custDto.getName());
				}
			}
			
		}
		return list;
	}
	
	public List<WorkSignInfoBean> getWorkSignMapInfos(Map<String, Object> map){
		String isState = map.get("isState").toString();
		
		List<WorkSignInfoBean> list = workSignInfoMapper.findWorkSignInfos(map);
		
		List<WorkSignInfoBean> validList = new ArrayList<WorkSignInfoBean>();
		
		if(list.size() > 0){
			List<String> ids = new ArrayList<String>();
			List<String> tocherIds = new ArrayList<String>();
			
			Map<String, ResCustInfoDto> custsMap = new HashMap<String, ResCustInfoDto>();
			Map<String, ResCustInfoDetailBean> detailsMap = new HashMap<String, ResCustInfoDetailBean>();
			Map<String, String> userNameMaps = cachedService.getOrgUserNames(map.get("orgId").toString());
			Map<String,WorkSignInfoBean> workMap = new HashMap<String, WorkSignInfoBean>();
			for(WorkSignInfoBean wsi : list){
				ids.add(wsi.getCustomerId());
				if(StringUtils.isNotBlank(wsi.getToucherId())){
					tocherIds.add(wsi.getToucherId());
				}
			}
			
			List<ResCustInfoDto> custs = resCustInfoMapper.findCustPoolList(ids, map.get("orgId").toString());
			for(ResCustInfoDto rcid : custs) custsMap.put(rcid.getResCustId(), rcid);
			
			if(tocherIds.size() > 0){
				List<ResCustInfoDetailBean> detailBeans = resCustInfoDetailMapper.findByIds(tocherIds, map.get("orgId").toString());
				for(ResCustInfoDetailBean detail : detailBeans) detailsMap.put(detail.getTscidId(), detail);
			}
			
			
			ResCustInfoDto custDto;
			ResCustInfoDetailBean detailBean;
			WorkSignInfoBean wsi;
			
			for(WorkSignInfoBean workBean : list){
//				double realDis = distance(workBean.getLon(), workBean.getLat(), (double)map.get("lon"), (double)map.get("lat"));
//				if(Double.compare(realDis, 1000*10.0d) != 1){
					custDto = custsMap.get(workBean.getCustomerId());
					workBean.setUserName(userNameMaps.get(workBean.getUserId()));
					if(isState.equals("1")){
						if(custDto != null){
							workBean.setName(custDto.getName());
						}
						detailBean = detailsMap.get(workBean.getToucherId());
						if(detailBean != null){
							workBean.setToucherName(detailBean.getName());
						}
					}else{
						workBean.setName(custDto.getCompany());
						workBean.setToucherName(custDto.getName());
					}
					String key = workBean.getCustomerId()+"_"+workBean.getUserId();
					if(workMap.containsKey(key)){
						wsi = workMap.get(key);
						if(wsi.getSignDate().compareTo(workBean.getSignDate()) != 1) workMap.put(key, workBean);
					}else{
						workMap.put(key, workBean);
					}
				}
//			}
			
			for(String key : workMap.keySet()){
				validList.add(workMap.get(key));
			}
			
		}
		return validList;
	}
	
	 /**  
     * 计算中心经纬度与目标经纬度的距离（米）  
     *   
     * @param centerLon  
     *            中心精度  
     * @param centerLan  
     *            中心纬度  
     * @param targetLon  
     *            需要计算的精度  
     * @param targetLan  
     *            需要计算的纬度  
     * @return 米  
     */  
//    public double distance(double centerLon, double centerLat, double targetLon, double targetLat) {    
//        double jl_jd = 102834.74258026089786013677476285;// 每经度单位米;    
//        double jl_wd = 111712.69150641055729984301412873;// 每纬度单位米;    
//        double b = Math.abs((centerLat - targetLat) * jl_jd);    
//        double a = Math.abs((centerLon - targetLon) * jl_wd);    
//        return Math.sqrt((a * a + b * b));    
//    }
    
}
