package com.qftx.tsm.plan.year.service;

import com.qftx.tsm.plan.year.bean.PlanSaleYearBean;
import com.qftx.tsm.plan.year.dao.PlanSaleYearMapper;
import com.qftx.tsm.plan.year.dto.ProgressDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PlanProgressService {
	@Autowired
	private PlanSaleYearService service;
	@Autowired
	PlanSaleYearMapper mapper;
	private Logger logger = Logger.getLogger(PlanProgressService.class);
	/*获取全年计划*/
	public ProgressDto findFullYearOrderByMonth(String orgId,String planYear){
		PlanSaleYearBean entity = new PlanSaleYearBean();
		entity.setOrgId(orgId);
		entity.setPlanYear(planYear);
		entity.setGroupKey("t.plan_month");
		
		List<PlanSaleYearBean> plans = mapper.findSumByCondtion(entity);
		
		ProgressDto dto = new ProgressDto();
		dto.setMap(list2Map(plans));
		
		return dto;
	}
	
	public Map<String, PlanSaleYearBean> list2Map(List<PlanSaleYearBean> plans){
		Map<String, PlanSaleYearBean> map = new HashMap<String, PlanSaleYearBean>();
		for (PlanSaleYearBean plan : plans) {
			String month = plan.getPlanMonth();
			if(month!=null&&month.length()==1){
				month="0"+month;
			}
			if(!map.containsKey(month)){
				map.put(month, plan);
			}else{
				logger.error("月份重复");
			}
		}
		return map;
	}
}

