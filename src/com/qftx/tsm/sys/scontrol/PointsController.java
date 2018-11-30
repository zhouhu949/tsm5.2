package com.qftx.tsm.sys.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.dto.DataDictionaryModel;
import com.qftx.tsm.option.service.DataDictionaryService;
import com.qftx.tsm.sys.bean.Points;
import com.qftx.tsm.sys.dto.PointsModel;
import com.qftx.tsm.sys.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 等级成长设置
 * @author: zwj
 * @since: 2015-12-4  上午10:21:32
 * @history: 4.x
 */
@Controller
@RequestMapping("/sys/points")
public class PointsController {
	@Autowired
	private PointsService pointsService;
	@Autowired
	private DataDictionaryService dataDictionaryService;
	@Autowired
	private CachedService cachedService;
	
	/** 等级成长设置页面  */
	 @RequestMapping("/pointsPage")
	public String pointsPage(HttpServletRequest request){
		try{
			ShiroUser user= ShiroUtil.getShiroUser();
			//  查询单位下等级成长设置
			Points entity = new Points();
			entity.setOrgId(user.getOrgId());
			List<Points> points = pointsService.getListByCondtion(entity);	
			if(points != null && points.size() >0){
				Map<Long,Points> map=new HashMap<Long,Points>();
				for(Points point : points){		
					if(point.getLevel()!=null){
						map.put(Long.parseLong(point.getLevel().toString()), point);
					}				
				}
				request.setAttribute("map",map);
			}
			
			
			// 查询积分规则			
			DataDictionaryBean dictionary = new DataDictionaryBean();
			dictionary.setOrgId(user.getOrgId());
			
			List<DataDictionaryBean>dictionaryList = dataDictionaryService.getListByCondtion(dictionary);
			Map<String, DataDictionaryBean> dictionaryMap = new HashMap<String, DataDictionaryBean>();
			for (DataDictionaryBean obj : dictionaryList) {
				if(null != obj.getDictionaryCode()){
					dictionaryMap.put(obj.getDictionaryCode(), obj);
				}
			}
			dictionaryList = new ArrayList<DataDictionaryBean>();
			// 销售每签单xx 元获得1积分奖励
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40021));//下标[0]
			// 每月单项任务完成获得 xx 积分奖励
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40022));//下标[1]
			 // 完成月度计划，获得xx积分奖励
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40023));//下标[2]	
			 // 连续3个月完成月度计划，获得xx积分奖励
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40024));//下标[3]			
			request.setAttribute("dictionaryList", dictionaryList);
		}catch(Exception e){
			throw new SysRunException(e);
		}		
		return "/tsm/sys/points";
	}
	
	 
	 
	 /** 设置等级成长 */
	 @Transactional(propagation = Propagation.REQUIRED)
	@ResponseBody
	@RequestMapping("/setPoints")
	public String setPoints(HttpServletRequest request,PointsModel pointsModel,DataDictionaryModel dataDictionaryModel){
		try{
			ShiroUser user= ShiroUtil.getShiroUser();
			List<Points>points = pointsModel.getPoints();
			Date dbtime = DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern);
			
			for (Points obj : points) {
				obj.setModifierAcc(user.getAccount());
				obj.setModifydate(dbtime);
				obj.setOrgId(user.getOrgId());
			}
			if(points != null && points.size() >0){
				pointsService.modifyTrendsBatch(points);
			}			
			List<DataDictionaryBean>dictionaryList = dataDictionaryModel.getDictionaryList();
			
			// 销售每签单xx 元获得1积分奖励
			dictionaryList.get(0).setDictionaryValue(
					dictionaryList.get(0).getDictionaryValue() == null ? "10" : 
						dictionaryList.get(0).getDictionaryValue());
			// 每月单项任务完成获得 xx 积分奖励
			dictionaryList.get(1).setDictionaryValue(
					dictionaryList.get(1).getDictionaryValue() == null ? "100" : 
						dictionaryList.get(1).getDictionaryValue());
			// 完成月度计划，获得xx积分奖励
			dictionaryList.get(2).setDictionaryValue(
					dictionaryList.get(2).getDictionaryValue() == null ? "500" : 
						dictionaryList.get(2).getDictionaryValue());
			// 连续3个月完成月度计划，获得xx积分奖励
			dictionaryList.get(3).setDictionaryValue(
					dictionaryList.get(3).getDictionaryValue() == null ? "2000" : 
						dictionaryList.get(3).getDictionaryValue());
	
			List<DataDictionaryBean>datas = cachedService.getDictionary(user.getOrgId());
			Date time = DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern);
			for (DataDictionaryBean obj : dictionaryList) {
				obj.setModifierAcc(user.getAccount());
				obj.setModifydate(time);
				obj.setOrgId(user.getOrgId());
				for(DataDictionaryBean dic:datas){
					if(dic.getDictionaryId().equals(obj.getDictionaryId())){
						dic.setDictionaryValue(obj.getDictionaryValue());
						dic.setModifierAcc(user.getAccount());
						dic.setModifydate(time);
						dic.setIsOpen(obj.getIsOpen());
						break;
					}
				}
			}
			// 修改数据库
			dataDictionaryService.modifyTrendsBatch(dictionaryList);
			//修改缓存
			cachedService.setDictionary(user.getOrgId(), datas);
			return AppConstant.RESULT_SUCCESS;
		}catch(Exception e){
			throw new SysRunException(e);
		}
	}
	
}
