package com.qftx.tsm.report.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysRunException;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.contract.service.ContractService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.report.bean.LayoutCustOptionBean;
import com.qftx.tsm.report.bean.LayoutCustProductBean;
import com.qftx.tsm.report.bean.LayoutCustStateBean;
import com.qftx.tsm.report.service.LayoutCustOptionService;
import com.qftx.tsm.report.service.LayoutCustProductService;
import com.qftx.tsm.report.service.LayoutCustStateService;
import com.qftx.tsm.sys.bean.Product;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
@Controller
@RequestMapping(value="/layout/user")
public class LayoutPlanAction {
	private static final Logger logger = Logger.getLogger(LayoutPlanAction.class);
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private ContractService contractService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private LayoutCustStateService layoutCustStateService;
	@Autowired
	private LayoutCustOptionService layoutCustOptionService;
	@Autowired
	private LayoutCustProductService layoutCustProductService;
	
	@RequestMapping()
	public String report(HttpServletRequest request){
		try {
			
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "report/layoutPlan";
	}
	
	@RequestMapping("/custStateChart")
	@ResponseBody
	public String custState(HttpServletRequest request,String groupIds,String type){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			LayoutCustStateBean bean = layoutCustStateService.getCustStateLayoutChart(user.getOrgId(), user.getAccount(), StringUtils.isNotBlank(groupIds) ? Arrays.asList(groupIds.split(",")) : null,StringUtils.isNotBlank(type) ? null : user.getAccount());
			return JSONObject.fromObject(bean).toString();
		} catch (Exception e) {
			logger.debug("客户状态分布图读取数据失败");
			return "";
		}
	}
	
	@RequestMapping("/custTypeChart")
	@ResponseBody
	public String custTypeChart(HttpServletRequest request,String groupIds,String type){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<LayoutCustOptionBean> beans = layoutCustOptionService.getCustSaleProcLayoutChart(user.getOrgId(), user.getAccount(),StringUtils.isNotBlank(groupIds) ? Arrays.asList(groupIds.split(",")) : null, 2,StringUtils.isNotBlank(type) ? null : user.getAccount());
			Map<String, Integer> map = new HashMap<String, Integer>();
			Integer totalNum = 0;
			for(LayoutCustOptionBean bean : beans){
				totalNum+=bean.getCustNums();
				map.put(bean.getOptionlistId(), bean.getCustNums());
			}
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, user.getOrgId());
			List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> dataMap = new ArrayList<Map<String,Object>>();
			for(OptionBean ob : options){
				Map<String, Object> lmap = new HashMap<String, Object>();
				lmap.put("optionName",ob.getOptionName());
				lmap.put("custNums",map.get(ob.getOptionlistId()) == null ? 0 : map.get(ob.getOptionlistId()));
				lists.add(lmap);
				if(map.get(ob.getOptionlistId()) != null){
					Map<String, Object> dmap = new HashMap<String, Object>();
					dmap.put("name",ob.getOptionName());
					dmap.put("optionlistId", ob.getOptionlistId());
					dmap.put("value", map.get(ob.getOptionlistId()));
					dataMap.add(dmap);
				}
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("data", dataMap);
			data.put("list", lists);
			data.put("totalNum", totalNum);
			return JSONObject.fromObject(data).toString();
		} catch (Exception e) {
			logger.debug("客户类型分布图读取数据失败");
			return "";
		}
	}
	
	@RequestMapping("/saleProcChart")
	@ResponseBody
	public String saleProcChart(HttpServletRequest request,String groupIds,String type){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<LayoutCustOptionBean> beans = layoutCustOptionService.getCustSaleProcLayoutChart(user.getOrgId(), user.getAccount(),StringUtils.isNotBlank(groupIds) ? Arrays.asList(groupIds.split(",")) : null, 1,StringUtils.isNotBlank(type) ? null : user.getAccount());
			Map<String, Integer> map = new HashMap<String, Integer>();
			Integer totalNum = 0;
			for(LayoutCustOptionBean bean : beans){
				totalNum+=bean.getCustNums();
				map.put(bean.getOptionlistId(), bean.getCustNums());
			}
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, user.getOrgId());
			List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> dataMap = new ArrayList<Map<String,Object>>();
			for(OptionBean ob : options){
				Map<String, Object> lmap = new HashMap<String, Object>();
				lmap.put("optionName",ob.getOptionName());
				lmap.put("custNums",map.get(ob.getOptionlistId()) == null ? 0 : map.get(ob.getOptionlistId()));
				lists.add(lmap);
				if(map.get(ob.getOptionlistId()) != null){
					Map<String, Object> dmap = new HashMap<String, Object>();
					dmap.put("name",ob.getOptionName());
					dmap.put("optionlistId", ob.getOptionlistId());
					dmap.put("value", map.get(ob.getOptionlistId()));
					dataMap.add(dmap);
				}
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("data", dataMap);
			data.put("list", lists);
			data.put("totalNum", totalNum);
			return JSONObject.fromObject(data).toString();
		} catch (Exception e) {
			logger.debug("客户销售进程分布图读取数据失败");
			return "";
		}
	}
	
	@RequestMapping("/productChart")
	@ResponseBody
	public String productChart(HttpServletRequest request,String groupIds,String type){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<LayoutCustProductBean> beans = layoutCustProductService.getCustProductLayoutChart(user.getOrgId(), user.getAccount(), StringUtils.isNotBlank(groupIds) ? Arrays.asList(groupIds.split(",")) : null,StringUtils.isNotBlank(type) ? null : user.getAccount());
			Map<String, Integer> map = new HashMap<String, Integer>();
			Integer totalNum = 0;
			for(LayoutCustProductBean bean : beans){
				totalNum+=bean.getCustNums();
				map.put(bean.getProductId(), bean.getCustNums());
			}
			List<Product> products = cachedService.getOpionProduct(user.getOrgId());
			List<Map<String, Object>> lists = new ArrayList<Map<String,Object>>();
			List<Map<String, Object>> dataMap = new ArrayList<Map<String,Object>>();
			for (Product product : products) {
				Map<String, Object> lmap = new HashMap<String, Object>();
				lmap.put("productName",product.getName());
				lmap.put("custNums",map.get(product.getId()) == null ? 0 : map.get(product.getId()));
				lists.add(lmap);
				if(map.get(product.getId()) != null){
					Map<String, Object> dmap = new HashMap<String, Object>();
					dmap.put("name",product.getName());
					dmap.put("productId", product.getId());
					dmap.put("value", map.get(product.getId()));
					dataMap.add(dmap);
				}
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("data", dataMap);
			data.put("list", lists);
			data.put("totalNum", totalNum);
			return JSONObject.fromObject(data).toString();
		} catch (Exception e) {
			logger.debug("产品分布图读取数据失败");
			return "";
		}
	}
}

//class ProductComparator implements Comparator<Map<String,Object>>{
//	public int compare(Map<String, Object> o1, Map<String, Object> o2) {
//		Integer num1 = o1.get("custNums") == null ? 0 : Integer.parseInt(o1.get("custNums").toString());
//		Integer num2 = o2.get("custNums") == null ? 0 : Integer.parseInt(o2.get("custNums").toString());
//		return num1 - num2;
//	}
//	
//}
