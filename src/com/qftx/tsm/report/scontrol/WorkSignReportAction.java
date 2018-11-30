package com.qftx.tsm.report.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.workSign.bean.WorkSignInfoBean;
import com.qftx.tsm.workSign.service.WorkSignInfoService;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.*;

@Controller
@RequestMapping("/report/workSign")
public class WorkSignReportAction {
	
	private Log log = LogFactory.getLog(WorkSignReportAction.class);
	
	@Resource
	private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Resource
	private WorkSignInfoService workSignInfoService;
	
	@RequestMapping()
	public String index(){
		return "report/signInTrack";
	}
	
	@RequestMapping("/listData")
	@ResponseBody
	public Map<String, Object> listData(HttpServletRequest request,String startDate,String endDate,String userAccsStr){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			Map<String,Object> searchMap = new HashMap<String, Object>();
			searchMap.put("orgId", user.getOrgId());
			searchMap.put("isState", user.getIsState());
			if(StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)){
				searchMap.put("startDate", DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				searchMap.put("endDate", DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			if(StringUtils.isNotBlank(startDate)) searchMap.put("startDate", startDate);
			if(StringUtils.isNotBlank(endDate)) searchMap.put("endDate", endDate);
			if(user.getIssys() == 1 && StringUtils.isBlank(userAccsStr)){
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())){
					accs.add(user.getAccount());
				}
				searchMap.put("userAccs", accs);
			}else if(StringUtils.isNotBlank(userAccsStr)){
				searchMap.put("userAccs", Arrays.asList(userAccsStr.split(",")));
			}else{
				searchMap.put("userAcc", user.getAccount());
			}
			
			List<WorkSignInfoBean> list = workSignInfoService.getWorkSignInfos(searchMap);
			map.put("success", 1);
			map.put("list", list);
			map.put("isState", user.getIsState());
		} catch (Exception e) {
			map.put("success", 0);
			map.put("msg", "签到轨迹加载列表数据异常！msg="+e.getMessage());
			log.error("签到轨迹加载列表数据异常！",e);
		}
		return map;
	}
	
	@RequestMapping("/mapData")
	@ResponseBody
	public Map<String, Object> mapData(HttpServletRequest request,String startDate,String endDate,String userAccsStr/*,double lon,double lat*/){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			Map<String,Object> searchMap = new HashMap<String, Object>();
			searchMap.put("orgId", user.getOrgId());
			searchMap.put("isState", user.getIsState());
			if(StringUtils.isBlank(startDate) && StringUtils.isBlank(endDate)){
				searchMap.put("startDate", DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
				searchMap.put("endDate", DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			if(StringUtils.isNotBlank(startDate)) searchMap.put("startDate", startDate);
			if(StringUtils.isNotBlank(endDate)) searchMap.put("endDate", endDate);
			if(user.getIssys() == 1 && StringUtils.isBlank(userAccsStr)){
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())){
					accs.add(user.getAccount());
				}
				searchMap.put("userAccs", accs);
			}else if(StringUtils.isNotBlank(userAccsStr)){
				searchMap.put("userAccs", Arrays.asList(userAccsStr.split(",")));
			}else{
				searchMap.put("userAcc", user.getAccount());
			}
//			getAround(searchMap, lat, lon, 1000*10);
			
			List<WorkSignInfoBean> list = workSignInfoService.getWorkSignMapInfos(searchMap);
			Collections.sort(list, new Comparator<WorkSignInfoBean>() {
				@Override
				public int compare(WorkSignInfoBean o1, WorkSignInfoBean o2) {
					return o1.getSignDate().compareTo(o2.getSignDate());
				}
			});
			Collections.reverse(list);
			map.put("success", 1);
			map.put("list", list);
			map.put("isState", user.getIsState());
			
		} catch (Exception e) {
			map.put("success", 0);
			map.put("msg", "签到轨迹加载地图数据异常！msg="+e.getMessage());
			log.error("签到轨迹加载地图数据异常！",e);
		}
		return map;
	}
	
	@RequestMapping("/imgView")
	public String imgView(HttpServletRequest request,String params){
		request.setAttribute("params", params);
		return "report/idialog/signInTrackImgIdialog";
	}
	
	/**  
     * 生成以中心点为中心的四方形经纬度  
     *   
     * @param lat 纬度  
     * @param lon 精度  
     * @param raidus 半径（以米为单位）  
     * @return  
     */    
    public void getAround(Map<String, Object> map,double lat, double lon, int raidus) {    
        Double latitude = lat;    
        Double longitude = lon;    
    
        Double degree = (24901 * 1609) / 360.0;    
        double raidusMile = raidus;    
    
        Double dpmLat = 1 / degree;    
        Double radiusLat = dpmLat * raidusMile;    
        Double minLat = latitude - radiusLat;    
        Double maxLat = latitude + radiusLat;    
    
        Double mpdLng = degree * Math.cos(latitude * (Math.PI / 180));    
        Double dpmLng = 1 / mpdLng;                 
        Double radiusLng = dpmLng * raidusMile;     
        Double minLng = longitude - radiusLng;      
        Double maxLng = longitude + radiusLng;     
        map.put("minLat", minLat);
        map.put("minLng", minLng);
        map.put("maxLat", maxLat);
        map.put("maxLng", maxLng);
        map.put("lat", lat);
        map.put("lon", lon);
    }
	
}
