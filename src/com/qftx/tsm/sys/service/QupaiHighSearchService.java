package com.qftx.tsm.sys.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.common.cached.CachedService;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.dto.HighSearchChildDto;
import com.qftx.tsm.sys.dto.HighSearchDto;
import com.qftx.tsm.sys.enums.SysEnum;


@Service
public class QupaiHighSearchService{
	Logger logger=Logger.getLogger(QupaiHighSearchService.class);
	@Autowired
	private CachedService cachedService; 
	
	//  权限判断，（HighSearchService 对应方法）
	static Map<String,String>permissionMap = new HashMap<String,String>();
	static{
		permissionMap.put("ownerAcc", "isShow");
		permissionMap.put("inchargeAcc", "isShow");
		permissionMap.put("opreateAcc", "isShow");
		permissionMap.put("commonAcc", "isShow");
		permissionMap.put("sellAcc", "isShow");
		permissionMap.put("serviceAcc", "isShow");
		permissionMap.put("visitAcc", "isShow");
		permissionMap.put("resAddGroup", "returnGroupJson");
	}
	
	// 多选、单选类型字段DEVELOP_CODE（用于开发代码），以及HighSearchService对应方法 集合
	static Map<String,String>searchMulitMap = new HashMap<String,String>();
	static{
		searchMulitMap.put("resStatus","getResStatus");// 已分配资源-资源状态
		searchMulitMap.put("allResStatus","getAllResStatus"); //  全部资源-资源状态
		searchMulitMap.put("opreateType","getOpreateType");// 放弃类型
		searchMulitMap.put("followType","getFollowType");// 联系方式
		searchMulitMap.put("nextFollowType","getFollowType");// 下次联系方式
		searchMulitMap.put("effectivenes","getEffectivenes");// 有效联系
		//searchMulitMap.put("groupList","getGroupList");// 资源分组
		searchMulitMap.put("custStatus",	"getCustStatusList");// 客户状态
		searchMulitMap.put("custStatus1",	"getCustStatusList1");// 客户状态(我的客户，客户交接)
		searchMulitMap.put("SALES_10002",	"getOptionList"); // 客户类型
		searchMulitMap.put("companyTrade",	"getOptionList"); // 所属行业
		searchMulitMap.put("SALES_10001",	"getOptionList");// 销售进程
		searchMulitMap.put("SALES_10006",	"getOptionList1");// 客户放弃原因
		searchMulitMap.put("SALES_40001",	"getOptionList");// 流失客户原因
		searchMulitMap.put("custSource",	"getCustSource");// 客户来源
		searchMulitMap.put("defined1","getOptionList");// 自定义字段1
		searchMulitMap.put("defined2","getOptionList");// 自定义字段2
		searchMulitMap.put("defined3","getOptionList");// 自定义字段3
		searchMulitMap.put("defined4","getOptionList");// 自定义字段4
		searchMulitMap.put("defined5","getOptionList");// 自定义字段5
		searchMulitMap.put("defined6","getOptionList");// 自定义字段6
		searchMulitMap.put("defined7","getOptionList");// 自定义字段7
		searchMulitMap.put("defined8","getOptionList");// 自定义字段8
		searchMulitMap.put("defined9","getOptionList");// 自定义字段9
		searchMulitMap.put("defined10","getOptionList");// 自定义字段10
		searchMulitMap.put("defined11","getOptionList");// 自定义字段11
		searchMulitMap.put("defined12","getOptionList");// 自定义字段12
		searchMulitMap.put("defined13","getOptionList");// 自定义字段13
		searchMulitMap.put("defined14","getOptionList");// 自定义字段14
		searchMulitMap.put("defined15","getOptionList");// 自定义字段15
	}
	
	
	/**
	 * 执行与系统属性相关字段
	 * @param methodName
	 * @param orgId
	 * @param orgId1
	 * @throws Exception
	 */
	public Object execute(String methodName,Class[] clazz,Object[] objs) throws Exception{
		Method method = null;
		method = this.getClass().getMethod(methodName,clazz);
		Object o1=method.invoke(this,objs); 
		return o1;
	}
	
	public List<HighSearchDto> getHighSearch(String module,String orgId,Integer isSys){
		List<CustSearchSet> dataList = cachedService.getCustSearchSetList(module,orgId);
		List<CustSearchSet> rlist = new ArrayList<CustSearchSet>();
		for (CustSearchSet filed : dataList) {
			if (filed.getDataType() != 1 && filed.getIsQuery() != 3) {
				rlist.add(filed);
			}
		}
		return getHighSearch(rlist,module,orgId,isSys);
	}
	
	public List<HighSearchDto> getQupaiHighSearch(String module,String orgId,Integer isSys){
		List<CustSearchSet> dataList = cachedService.getQupaiCustSearchSetList(orgId);
		dataList = CachedService.filterByModule(dataList, module);
		
		List<CustSearchSet> rlist = new ArrayList<CustSearchSet>();
		for (CustSearchSet filed : dataList) {
			if (filed.getDataType() != 1 && filed.getIsQuery() != 3) {
				rlist.add(filed);
			}
		}
		return getHighSearch(rlist,module,orgId,isSys);
	}
	
	public List<HighSearchDto> getHighSearch(List<CustSearchSet> dataList,String module,String orgId,Integer isSys){
		List<HighSearchDto> list = new ArrayList<HighSearchDto>();
		try{
			
			if(dataList != null && dataList.size() > 0){
				HighSearchDto dto = null;
				String methodName = null;
				for(CustSearchSet searchSet : dataList){
					dto = new HighSearchDto();
					dto.setDataType(searchSet.getDataType());
					dto.setIsFiledSet(searchSet.getIsFiledSet());
					dto.setSearchCode(searchSet.getSearchCode());
					dto.setSearchName(searchSet.getSearchName());
					dto.setSort(searchSet.getSort());
					dto.setIsQuery(searchSet.getIsQuery());
					dto.setDevelopCode(searchSet.getDevelopCode());
					if(Integer.parseInt(SysEnum.FIELD_DATATYPE_RADIO.getState()) == searchSet.getDataType() ||
							Integer.parseInt(SysEnum.FIELD_DATATYPE_CHECK.getState()) == searchSet.getDataType()){
						if(searchMulitMap.get(searchSet.getDevelopCode()) != null){
							methodName = searchMulitMap.get(searchSet.getDevelopCode());
							if("getOptionList".equals(methodName) || "getOptionList1".equals(methodName)){
								dto.setChildList((List<HighSearchChildDto>) execute(methodName,new Class[]{String.class,String.class},new Object[]{orgId,searchSet.getDevelopCode()}));
								if(!(dto.getChildList().size()>0)){ // 权限范围外
									continue;
								}
							}else{
								dto.setChildList((List<HighSearchChildDto>) execute(methodName,new Class[]{},new Object[]{}));
							}
						}					
					}else if(Integer.parseInt(SysEnum.FIELD_DATATYPE_TREE.getState()) == searchSet.getDataType()){
						if(permissionMap.get(searchSet.getDevelopCode()) != null){
							methodName = permissionMap.get(searchSet.getDevelopCode());
							dto.setParamValue((String) execute(methodName,new Class[]{Integer.class,String.class},new Object[]{isSys,searchSet.getDevelopCode()}));			
							if("-1".equals(dto.getParamValue())){ // 权限范围外
								continue;
							}
						}
					}else if(Integer.parseInt(SysEnum.FIELD_DATATYPE_DATA.getState())== searchSet.getDataType()){
						dto.setChildList(getTimeChild(searchSet.getSearchCode()));
					}
					
					list.add(dto);
				}
			}
		}catch(Exception e){
			logger.error("HighSearchService.getHighSearch异常！", e);
		}
		
		return list;
	}
	
	
	// 已分配资源-资源状态
	public List<HighSearchChildDto>getResStatus(){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		HighSearchChildDto dto = new HighSearchChildDto();
		dto.setName("资源状态");
		dto.setValue("2");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("已分配未联系资源");
		dto.setValue("3");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("已联系资源");
		dto.setValue("4");
		dto.setIsCheck(0);
		list.add(dto);
		return list;
	}
	
	// 全部资源-资源状态
	public List<HighSearchChildDto>getAllResStatus(){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		HighSearchChildDto dto = new HighSearchChildDto();
		dto.setName("待分配资源");
		dto.setValue("1");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("已分配资源");
		dto.setValue("2");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("已分配未联系");
		dto.setValue("3");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("已分配已联系");
		dto.setValue("4");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("意向客户");
		dto.setValue("5");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("签约客户");
		dto.setValue("6");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("沉默客户");
		dto.setValue("7");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("流失客户");
		dto.setValue("8");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("公海客户");
		dto.setValue("9");
		dto.setIsCheck(0);
		list.add(dto);
		return list;
	}
	
	// 放弃类型
	public List<HighSearchChildDto>getOpreateType(){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		HighSearchChildDto dto = new HighSearchChildDto();
		dto.setName("客户-到期未签约");
		dto.setValue("11");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("客户-到期未跟进");
		dto.setValue("16");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("客户-主动放弃");
		dto.setValue("12");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("签约客户-流失");
		dto.setValue("21");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("资源-沟通失败");
		dto.setValue("4");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("资源-信息错误");
		dto.setValue("5");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("签约-到期未续签");
		dto.setValue("23");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("资源-到期未联系");
		dto.setValue("24");
		dto.setIsCheck(0);
		list.add(dto);
		return list;
	}
	
	// 联系方式
	public List<HighSearchChildDto>getFollowType(){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		HighSearchChildDto dto = new HighSearchChildDto();
		dto.setName("电话联系");
		dto.setValue("1");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("会客联系");
		dto.setValue("2");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("客户来电");
		dto.setValue("3");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("短信联系");
		dto.setValue("4");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("QQ联系");
		dto.setValue("5");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("邮件联系");
		dto.setValue("6");
		dto.setIsCheck(0);
		list.add(dto);
		return list;
	}
	
	// 有效联系 是/否
	public List<HighSearchChildDto>getEffectivenes(){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		HighSearchChildDto dto = new HighSearchChildDto();
		dto.setName("是");
		dto.setValue("1");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("否");
		dto.setValue("0");
		dto.setIsCheck(0);
		list.add(dto);
		return list;
	}
	
	//  资源分组
	public List<HighSearchChildDto>getGroupList(String orgId){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		List<ResourceGroupBean> groups = cachedService.getResGroupList(orgId);
		if(groups != null){
			HighSearchChildDto dto = null;
			for(ResourceGroupBean bean : groups){
				dto = new HighSearchChildDto();
				dto.setName(bean.getGroupName());
				dto.setValue(bean.getResGroupId());
				list.add(dto);
			}
		}
		return list;
	}
	
	// 客户状态
	public List<HighSearchChildDto>getCustStatusList(){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		HighSearchChildDto dto = new HighSearchChildDto();
		dto.setName("全部");
		dto.setValue("1");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("意向客户");
		dto.setValue("2");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("签约客户");
		dto.setValue("3");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("沉默客户");
		dto.setValue("4");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("流失客户");
		dto.setValue("5");
		dto.setIsCheck(0);
		list.add(dto);
		return list;
	}
	
	
	// 客户来源
	public List<HighSearchChildDto>getCustSource(){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		HighSearchChildDto dto = new HighSearchChildDto();
		dto.setName("全部");
		dto.setValue("");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("自有导入");
		dto.setValue("1");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("分配交接");
		dto.setValue("2");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("公海取回");
		dto.setValue("3");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("AI初筛");
		dto.setValue("4");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("在线表单");
		dto.setValue("5");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("数据合作");
		dto.setValue("6");
		dto.setIsCheck(0);
		list.add(dto);
		return list;
	}
	
	// 客户状态（我的客户、客户交接）
	public List<HighSearchChildDto>getCustStatusList1(){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		HighSearchChildDto dto = new HighSearchChildDto();
		dto.setName("全部");
		dto.setValue("0");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("资源");
		dto.setValue("1");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("意向客户");
		dto.setValue("2");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("签约客户");
		dto.setValue("3");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("沉默客户");
		dto.setValue("4");
		dto.setIsCheck(0);
		list.add(dto);
		dto = new HighSearchChildDto();
		dto.setName("流失客户");
		dto.setValue("5");
		dto.setIsCheck(0);
		list.add(dto);
		return list;
	}
	
	// 系统属性字段
	public List<HighSearchChildDto>getOptionList(String orgId,String itemCode){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		try{
			//从缓存读取
			List<OptionBean> custTypeList = cachedService.getQupaiOptionList(itemCode, orgId); //cachedService.getOptionList(itemCode,orgId);
			if(custTypeList!=null && custTypeList.size() >0){
				HighSearchChildDto dto = null;
				for(OptionBean bean:custTypeList){
					dto = new HighSearchChildDto();
					dto.setName(bean.getOptionName());
					dto.setValue(bean.getOptionlistId());
					dto.setIsCheck(0);
					list.add(dto);
				}
			}
		}catch(Exception e){
			logger.error("HighSearchService.getOptionList异常！", e);
		}		
		return list;
	}
	
	// 系统属性字段(客户放弃原因)
	public List<HighSearchChildDto>getOptionList1(String orgId,String itemCode){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		//是否开启放弃原因
		String isOpenReason = getSettingState(AppConstant.DATA17, orgId);
		try{
			//从缓存读取			
			List<OptionBean> commFailureList = cachedService.getOptionList(AppConstant.ITEMCODES[0], orgId);
			List<OptionBean> messageFailureList = cachedService.getOptionList(AppConstant.ITEMCODES[1], orgId);
			List<OptionBean> losingReasonList = cachedService.getOptionList(AppConstant.SALES_TYPE_WAST, orgId);
			List<OptionBean> reasons = new ArrayList<OptionBean>();
			if(isOpenReason.equals("1")){
//				reasons.add("沟通失败");
				for(OptionBean ob : commFailureList) {
					reasons.add(ob);
				}
//				reasons.add("信息错误");
				for(OptionBean ob : messageFailureList) {
					reasons.add(ob);
				}
			}
			for(OptionBean ob : losingReasonList) {
				ob.setOptionName("流失-"+ob.getOptionName());
				reasons.add(ob);
			}				
			if(reasons!=null && reasons.size() >0){
				HighSearchChildDto dto = null;
				for(OptionBean bean:reasons){
					dto = new HighSearchChildDto();
					dto.setName(bean.getOptionName());
					dto.setValue(bean.getOptionlistId());
					dto.setIsCheck(0);
					list.add(dto);
				}
			}
		}catch(Exception e){
			logger.error("HighSearchService.getOptionList异常！", e);
		}		
		return list;
	}
	
	// 返回有权限树路径
	public String isShow(Integer isSys,String developCode){
			if("commonAcc".equals(developCode)){
				return "/orgGroup/get_all_sale_user_json";
			}else if("sellAcc".equals(developCode)){
				return "/orgGroup/get_group_user_json";
			}else if("followAcc".equals(developCode)){
				return "/orgGroup/get_all_group_user_json";
			}else if("opreateAcc".equals(developCode)){
				return "/orgGroup/get_group_user_json";
			}else if("groupList".equals(developCode)){
				return "/res/group/get_res_group_json";
			}else if("visitAcc".equals(developCode)){
				return "";
			}else if("serviceAcc".equals(developCode)){
				return "/orgGroup/get_all_service_user_json";
			}
		if(isSys!=null && isSys == 1){ // 管理者返回树路径
			if("ownerAcc".equals(developCode)){
				return "/orgGroup/get_group_user_json";
			}else if("serviceAcc".equals(developCode)){
				return "/orgGroup/get_all_service_user_json";
			}
		}
		return "-1";
	}
	
	// 查询资源录入部门
	public String returnGroupJson(Integer isSys,String developCode){
		if(isSys!=null && isSys == 1){ // 管理者返回树路径
			return "/orgGroup/get_all_group_json";
		}
		return "-1";
	}
	
	// startTime  查询项配置中，时间类型的searchCode 为开始时间的name
	public List<HighSearchChildDto> getTimeChild(String startTime){
		List<HighSearchChildDto> list = new ArrayList<HighSearchChildDto>();
		try{
			HighSearchChildDto dto = null;
			if(StringUtils.isNotBlank(startTime)){
				if(!(startTime.contains("start") || startTime.contains("Start"))){
					StringBuffer sbf = new StringBuffer();
					sbf.append("start");
					sbf.append(startTime);
					startTime = sbf.toString();
				}
				dto = new HighSearchChildDto();
				dto.setName(startTime);
				dto.setOrder(0);
				dto.setIsCheck(0);
				list.add(dto);
				// 大小写需要注意
				String endTime = startTime.replaceFirst("start", "end");
				endTime = endTime.replaceFirst("Start", "End");
				dto = new HighSearchChildDto();
				dto.setName(endTime);
				dto.setOrder(1);
				dto.setIsCheck(0);
				list.add(dto);
			}
		}catch(Exception e){
			logger.error("HighSearchService.getTimeChild 异常！", e);
		}		
		return list;
	}
	
	public String getSettingState(String code,String orgId){
		List<DataDictionaryBean> dbs = cachedService.getDirList(code,orgId);
		String isOpen = "0";
		if(dbs.size() > 0){
			DataDictionaryBean db = dbs.get(0);
			if(db.getDictionaryValue() != null){
				isOpen = db.getDictionaryValue();
			}
		}
		return isOpen;
	}
	
	public static void main(String[] args) {
		String s1 = "121StartTime";
		String endTime = s1.replace("start", "end");
		endTime = endTime.replace("Start", "End");
		System.out.println(endTime);
	}
}
