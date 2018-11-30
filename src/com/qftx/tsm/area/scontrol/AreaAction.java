package com.qftx.tsm.area.scontrol;

import com.qftx.tsm.area.bean.ChinaCityBean;
import com.qftx.tsm.area.bean.ChinaCountyBean;
import com.qftx.tsm.area.bean.ChinaProvinceBean;
import com.qftx.tsm.area.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/common/area")
public class AreaAction {
	@Autowired
	private AreaService areaService;
	
	@RequestMapping("/province")
	@ResponseBody
	public List<ChinaProvinceBean> province(){
		return areaService.getProvinceBeans();
	}
	
	@RequestMapping("/city")
	@ResponseBody
	public List<ChinaCityBean> city(Integer pid){
		return areaService.getChinaCityBeans(pid);
	}
	
	@RequestMapping("/county")
	@ResponseBody
	public List<ChinaCountyBean> county(Integer cid){
		return areaService.getChinaCountyBeans(cid);
	}
}
