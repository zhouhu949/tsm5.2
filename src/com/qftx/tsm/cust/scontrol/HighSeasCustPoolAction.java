package com.qftx.tsm.cust.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.Constants;
import com.qftx.base.util.DateUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.OperateEnum;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.service.ComResourceGroupService;
import com.qftx.tsm.cust.service.HighSeasCustPoolService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.log.service.LogBatchInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.dto.HighSearchDto;
import com.qftx.tsm.sys.dto.SearchListShowCodeDto;
import com.qftx.tsm.sys.enums.SysEnum;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/res/sea")
public class HighSeasCustPoolAction extends BaseAction {
	
	private static final Logger logger = Logger.getLogger(HighSeasCustPoolAction.class);
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private ComResourceGroupService comReesourceGroupService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private HighSeasCustPoolService highSeasCustPoolService;
	@Autowired
	private LogUserOperateService logUserOperateService;
	@Autowired
	private LogBatchInfoService logBatchInfoService;
	
	/** 
	 * 公海客户池（管理者）
	 * @param request
	 * @param resCustInfoDto
	 * @return 
	 * @create  2015年12月21日 下午5:21:04 lixing
	 * @history  
	 */
	@RequestMapping("/highSeasList")
	public String highSeasList(HttpServletRequest request){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			//是否开启放弃原因
			String isOpenReason = getSettingState(AppConstant.DATA17, user.getOrgId());
			request.setAttribute("isOpenReason", isOpenReason);
//			List<String> reasons = resCustInfoService.custGiveUpReasonList(user);
			List<OptionBean> commFailureList = cachedService.getOptionList(AppConstant.ITEMCODES[0], user.getOrgId());
			List<OptionBean> messageFailureList = cachedService.getOptionList(AppConstant.ITEMCODES[1], user.getOrgId());
			List<OptionBean> losingReasonList = cachedService.getOptionList(AppConstant.SALES_TYPE_WAST, user.getOrgId());
			List<String> reasons = new ArrayList<String>();
			if(isOpenReason.equals("1")){
//				reasons.add("沟通失败");
				for(OptionBean ob : commFailureList) reasons.add(ob.getOptionName());
//				reasons.add("信息错误");
				for(OptionBean ob : messageFailureList) reasons.add(ob.getOptionName());
			}
			for(OptionBean ob : losingReasonList) reasons.add("流失-"+ob.getOptionName());
			request.setAttribute("reasons", reasons);
			//从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
			request.setAttribute("options", options);
			request.setAttribute("shiroUser", ShiroUtil.getShiroUser());
			//是否开启共享待分配资源到公海
			String isShare = getSettingState(AppConstant.DATA_40018, user.getOrgId());
			request.setAttribute("isShare", isShare);
			//是否开启员工批量回收
			String isBatchRecyle = getBatchRecyleSetting(AppConstant.DATA_50054,user.getOrgId());
			request.setAttribute("isBatchRecyle", isBatchRecyle);
			//是否开启员工批量回收
			String isBatchRecyleToCust = getBatchRecyleSetting(AppConstant.DATA_50055,user.getOrgId());
			request.setAttribute("isBatchRecyleToCust", isBatchRecyleToCust);
			
			setIsReplaceWord(request);
			
			List<CustSearchSet> tlist = getIsTextQueryList(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_8.getState());
			request.setAttribute("tList", tlist); // 文本类型查询字段列表
			Map<String,String>map = getIsDefinedNameList(user.getOrgId(),user.getIsState());
			request.setAttribute("definedNameMap", map);
			// 用于筛选项排序
			Map<String,Integer> mutilSearchCodeSortMap = getUnTestSearchSort(SysEnum.SEARCH_SET_MODULE_8.getState(),user.getOrgId(),user.getIssys());
			request.setAttribute("mutilSearchCodeSortMap",mutilSearchCodeSortMap);
			List<HighSearchDto> dtos = cachedService.getHighSearch(SysEnum.SEARCH_SET_MODULE_8.getState(), user.getOrgId(), user.getIssys());
			List<HighSearchDto> definedDtos = new ArrayList<HighSearchDto>();
			for(HighSearchDto dto : dtos){
				if(dto.getIsFiledSet() ==1 && dto.getIsQuery() == 1 ){
					definedDtos.add(dto);
				}
			}			
			request.setAttribute("definedSearchCodes",definedDtos); // 自定义非文本项查询集合
			// 需隐藏列的序号
			List<Integer> sorts = getHideSortListCode(SysEnum.SEARCH_SET_MODULE_8.getState(),user.getOrgId(),user.getIsState().toString(),SearchListShowCodeDto.modult_8_List,2);
			request.setAttribute("sorts", sorts);
			List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
			request.setAttribute("companyTrades", trades);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/high_seas_list";
	}
	
	@RequestMapping("/highSeasListData")
	@ResponseBody
	public Map<String, Object> highSeasListData(HttpServletRequest request,ResCustInfoDto resCustInfoDto){
		Map<String, Object> remap = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			resCustInfoDto.setState(user.getIsState());
			resCustInfoDto.setOrgId(user.getOrgId());
			resCustInfoDto.setReason(StringUtils.toLikeStr(resCustInfoDto.getReason()));
			//权限判断
			List<String> authGroupIds = cachedService.getPoolAuthGroupIds(user.getOrgId(),user.getGroupId());
			if(authGroupIds != null && authGroupIds.size() > 0){
				resCustInfoDto.setAuthGroupIds(authGroupIds);
			}
			if(StringUtils.isBlank(resCustInfoDto.getIsShareRes())){
				resCustInfoDto.setIsShareRes("0");
			}
			//处理拥有者
			resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
        	if(resCustInfoDto.getOsType().equals("1") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())){
        	}else if(resCustInfoDto.getOsType().equals("2") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())){
        		resCustInfoDto.setOwnerAcc(user.getAccount());
        	}else if(StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())){
				resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
			}
			
        	//处理放弃人
        	resCustInfoDto.setUsType(StringUtils.isBlank(resCustInfoDto.getUsType()) ? "1" : resCustInfoDto.getUsType());
        	if(resCustInfoDto.getUsType().equals("1") && StringUtils.isBlank(resCustInfoDto.getUpdateAccsStr())){
        	}else if(resCustInfoDto.getUsType().equals("2") && StringUtils.isBlank(resCustInfoDto.getUpdateAccsStr())){
        		resCustInfoDto.setUpdateAcc(user.getAccount());
        	}else if(StringUtils.isNotBlank(resCustInfoDto.getUpdateAccsStr())){
				resCustInfoDto.setUpdateAccs(Arrays.asList(resCustInfoDto.getUpdateAccsStr().split(",")));
			}
        	
			//处理时间查询
			if(resCustInfoDto.getnDateType() != null && resCustInfoDto.getnDateType() != 0 && resCustInfoDto.getnDateType() != 5){
				resCustInfoDto.setStartDate(getStartDateStr(resCustInfoDto.getnDateType()));
				resCustInfoDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			
			//处理时间查询
			if(resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5){
				resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
				resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			
			//是否开启放弃原因
			String isOpenReason = getSettingState(AppConstant.DATA17, user.getOrgId());
			resCustInfoDto.setIsOpenReason(isOpenReason);
			
//			//模糊查询处理
            if(StringUtils.isNotBlank(resCustInfoDto.getQueryText())){
            	String queryText = resCustInfoDto.getQueryText().trim();
            	resCustInfoDto.setQueryText(queryText);
            	if(queryText.matches("[0-9]+")){
            		resCustInfoDto.setQueryPhone(true);
            	}else{
            		resCustInfoDto.setQueryPhone(false);
            	}
            }
            //处理标签
            if(StringUtils.isNotBlank(resCustInfoDto.getAllLabels())){
            	resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
            }
            if(StringUtils.isNotBlank(resCustInfoDto.getResGroupIdsStr())){
            	resCustInfoDto.setGroupIds(Arrays.asList(resCustInfoDto.getResGroupIdsStr().split(",")));
            }
            List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_8.getState());	
			multiSearchList.add(AppConstant.SEARCH_LABEL);
            List<ResCustInfoDto> custs = resCustInfoService.getCustPoolListPageCache(resCustInfoDto,multiSearchList);
			//多选项显示
			if(custs.size() > 0){
				List<String> multiShowList = cachedService.getMultiSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_8.getState());
				List<String> singleShowList = cachedService.getSingleSelectDefinedShowFiled(user.getOrgId(),SysEnum.SEARCH_SET_MODULE_8.getState());
				resCustInfoService.multiDefinedShowChange(custs,multiShowList,singleShowList,user.getOrgId());
			}
			//从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
			Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			Map<Integer, String> provinceMap = new HashMap<Integer, String>();
			Map<Integer, String> cityMap = new HashMap<Integer, String>();
			Map<Integer, String> countyMap = new HashMap<Integer, String>();
			Map<String, String> tradeMap = new HashMap<String, String>();
			if(user.getIsState() == 1){
				provinceMap = cachedService.getAreaMap(CachedNames.PROVINCE);
				cityMap = cachedService.getAreaMap(CachedNames.CITY);
				countyMap = cachedService.getAreaMap(CachedNames.COUNTY);
				List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, user.getOrgId());
				tradeMap = cachedService.changeOptionListToMap(trades);
			}
			for(ResCustInfoDto rcid : custs){
				rcid.setSaleProcessName(StringUtils.isNotBlank(rcid.getLastOptionId()) ? saleProcMap.get(rcid.getLastOptionId()) : "");
				rcid.setOwnerName(StringUtils.isNotBlank(rcid.getOwnerAcc()) ? nameMap.get(rcid.getOwnerAcc()) : "");
				rcid.setGiveUpName(StringUtils.isNotBlank(rcid.getUpdateAcc()) ? nameMap.get(rcid.getUpdateAcc()) : "");
				rcid.setStartDate(rcid.getUpdateDate() != null ? DateUtil.formatDate(rcid.getUpdateDate(),DateUtil.Data_ALL) : "");
				rcid.setEndDate(rcid.getUpdateDate() != null ? DateUtil.formatDate(rcid.getUpdateDate(),DateUtil.DATE_DAY) : "");
				rcid.setStartActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(),DateUtil.Data_ALL) : "");
				rcid.setEndActionDate(rcid.getActionDate() != null ? DateUtil.formatDate(rcid.getActionDate(),DateUtil.DATE_DAY) : "");
				rcid.setShowdefined16(rcid.getDefined16() !=null?DateUtil.formatDate(rcid.getDefined16(), DateUtil.DATE_DAY): "");
				rcid.setShowdefined17(rcid.getDefined17() !=null?DateUtil.formatDate(rcid.getDefined17(), DateUtil.DATE_DAY): "");
				rcid.setShowdefined18(rcid.getDefined18() !=null?DateUtil.formatDate(rcid.getDefined18(), DateUtil.DATE_DAY): "");
//				if(definedMap != null && definedMap.size() > 0) resCustInfoService.setCustHighSearchDefined(rcid, definedMap, user.getOrgId());

				if(rcid.getProvinceId() != null && user.getIsState() == 1){
					String area = provinceMap.get(rcid.getProvinceId());
					if(rcid.getCityId() != null) area+="-"+cityMap.get(rcid.getCityId());
					if(rcid.getCountyId() != null) area+="-"+countyMap.get(rcid.getCountyId());
					rcid.setArea(area);
				}
				rcid.setCompanyTrade(rcid.getCompanyTrade() != null ? tradeMap.get(rcid.getCompanyTrade()) : "");
			}
			remap.put("list", custs);
			remap.put("item", resCustInfoDto);
			remap.put("serverDay", user.getServerDay());
			remap.put("loginAcc", user.getAccount());
			remap.put("fields", getIsQueryList(user.getOrgId(), user.getIsState()));
			remap.put("isState", user.getIsState());
			remap.put("isOpenReason", isOpenReason);
			remap.put("isShareRes", resCustInfoDto.getIsShareRes());
		} catch (Exception e) {
			logger.error("公海客户异步加载数据异常！",e);
		}
		return remap;
	}
	
	
	@ResponseBody
	@RequestMapping("/highSeasLeftData")
	public List<Map<String,Object>> getLeftGroupData(String isShareRes){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
			map.put("resType", "4");
			if(StringUtils.isBlank(isShareRes)){
				map.put("isShareRes", "0");
			}else{
				map.put("isShareRes", isShareRes);
			}
			//权限判断
			List<String> authGroupIds = cachedService.getPoolAuthGroupIds(user.getOrgId(),user.getGroupId());
			if(authGroupIds != null && authGroupIds.size() > 0){
				map.put("authGroupIds", authGroupIds);
			}
			list = getLeftResourceList(map);
		} catch (Exception e) {
			logger.error("***公海客户右侧组织结构加载失败！");
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
	
	public String getBatchRecyleSetting(String code,String orgId){
		String isOpen = "0";
		List<DataDictionaryBean> dbs = cachedService.getDirList(AppConstant.DATA_40032,orgId);
		if(dbs.size() > 0 && dbs.get(0).getDictionaryValue() != null && "1".equals(dbs.get(0).getDictionaryValue())){
			List<DataDictionaryBean> dbs1 = cachedService.getDirList(code,orgId);
			if(dbs1.size() > 0){
				DataDictionaryBean db = dbs1.get(0);
				if(code.equals(AppConstant.DATA_50054)){
					isOpen = db.getDictionaryValue();
				}else{
					isOpen = db.getIsOpen();
				}
			}
		}
		return isOpen;
	}
	
	public List<Map<String, Object>> getLeftResourceList(Map<String, Object> map){
		List<Map<String, Object>>groupList = new ArrayList<Map<String, Object>>();
		if(map.get("isShareRes").equals("1")){
			groupList = comReesourceGroupService.queryResGroupForShare(ShiroUtil.getUser().getOrgId());
		}else{
			groupList = cachedService.getResGroupList1(ShiroUtil.getUser().getOrgId());
		}
		return groupList;
	}
	
	@RequestMapping("/toDistribute")
	public String toDistribute(HttpServletRequest request,String ids,String module,String poolType){
		try {
			request.setAttribute("ids", ids);
			request.setAttribute("module", module);
			request.setAttribute("poolType", poolType);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/to_distribute";
	}
	
	/** 
	 * 客户再分配
	 * @create  2015年11月18日 上午9:42:44 lixing
	 * @history  
	 */
	@RequestMapping("/getAgainAssginCust")
	@ResponseBody
	public String getAgainAssginCust(HttpServletRequest request,HttpServletResponse response){
		Map<String, Object> reMap = new HashMap<String, Object>();
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			String ownerAcc = request.getParameter("ownerAcc");
			String ownerName = request.getParameter("ownerName");
			String isClean = request.getParameter("isClean");
			String module = request.getParameter("module");
			String poolType = request.getParameter("poolType");
			//判断是否超过最大资源数
			Map<String, Integer> checkMap = isResBeyondMax(ownerAcc,user.getOrgId(),getCustIds(ids).size());
			if(checkMap.get("flag") == 1){
				reMap.put("flag", false);
				reMap.put("msg", "个人资源最大数为"+checkMap.get("maxNum")+",该帐号库中已有"+checkMap.get("resNum")+",分配数为"+checkMap.get("bSize")+",超出最大资源数！");
				return JSONObject.fromObject(reMap).toString();
			}
			resCustInfoService.againAssginCust(getCustIds(ids), ownerAcc, ownerName, user,isClean,module,poolType);
			reMap.put("flag", true);
			reMap.put("msg", "操作成功!");
			return JSONObject.fromObject(reMap).toString();
		} catch (Exception e) {
			logger.error("客户再分配失败",e);
			reMap.put("flag", false);
			reMap.put("msg", "操作失败!");
			return JSONObject.fromObject(reMap).toString();
		}
	}
	
	/** 
	 * 批量删除
	 * @param request
	 * @param response 
	 * @create  2015年11月18日 上午10:38:12 lixing
	 * @history  
	 */
	@RequestMapping("/delBatchCust")
	@ResponseBody
	public String delBatchCust(HttpServletRequest request,HttpServletResponse response){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			List<String> custIds = getCustIds(ids);
			ResCustInfoDto custInfoDto = new ResCustInfoDto();
			custInfoDto.setIds(custIds);
			custInfoDto.setIsDel(1);
			custInfoDto.setUpdateAcc(user.getAccount());
			custInfoDto.setUpdateDate(new Date());
			custInfoDto.setOrgId(user.getOrgId());
			resCustInfoService.delBatchCust(custInfoDto);
			String module = request.getParameter("module");
			String poolType = request.getParameter("poolType");
			if(StringUtils.isNotBlank(module)){
				StringBuffer context = new StringBuffer("");
				if(custIds.size() > 1){
					context.append(custIds.size()).append("条");
				}else{
					ResCustInfoBean bean = resCustInfoService.getCustInfoByOrgIdAndPk(ShiroUtil.getShiroUser().getOrgId(), custIds.get(0));
					if(bean.getState() == 1 || StringUtils.isEmpty(bean.getCompany())){
						context.append(StringUtils.isEmpty(bean.getName()) ? "" : bean.getName());
					}else{
						context.append(StringUtils.isEmpty(bean.getCompany()) ? "" : bean.getCompany());
					}
//					if(StringUtils.isNotBlank(bean.getMobilephone())) context.append("(").append(bean.getMobilephone()).append(")");
				}
				Map<String, Object> custIdContextMap = new HashMap<String, Object>();
				for(String id : custIds) {
					custIdContextMap.put(id, "");
				}
				if(module.equals("5")){
					logBatchInfoService.saveLog(custInfoDto.getOrgId(), custInfoDto.getUpdateAcc(),user.getName(),OperateEnum.LOG_DELETE, AppConstant.Module_id101,AppConstant.Module_Name101, AppConstant.Operate_id5, AppConstant.Operate_Name5, context.toString(), "", custIds, "", SysBaseModelUtil.getModelId(), custIdContextMap);
				}else if(module.equals("6")){
					if(poolType.equals("0")){
						logBatchInfoService.saveLog(custInfoDto.getOrgId(), custInfoDto.getUpdateAcc(),user.getName(),OperateEnum.LOG_DELETE, AppConstant.Module_id1004,AppConstant.Module_Name1004, AppConstant.Operate_id5, AppConstant.Operate_Name5, context.toString(), "", custIds, "", SysBaseModelUtil.getModelId(), custIdContextMap);
					}else{
						logBatchInfoService.saveLog(custInfoDto.getOrgId(), custInfoDto.getUpdateAcc(),user.getName(),OperateEnum.LOG_DELETE, AppConstant.Module_id1005,AppConstant.Module_Name1005, AppConstant.Operate_id5, AppConstant.Operate_Name5, context.toString(), "", custIds, "", SysBaseModelUtil.getModelId(), custIdContextMap);
					}
				}
			}
			return "1";
		} catch (Exception e) {
			logger.error("批量删除失败",e);
			return "-1";
		}
	}

	@RequestMapping("/toClean")
	public String toClean(){
		return "res/to_clean";
	}
	
	@RequestMapping("/toRecyle")
	public String toRecyle(HttpServletRequest request){
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			List<ResourceGroupBean> groupList = cachedService.getResGroupList(user.getOrgId());
			request.setAttribute("groupList", groupList);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "res/to_recyle";
	}
	
	/** 
	 * 公海客户池 清空
	 * @param request
	 * @param response
	 * @param custInfoDto 
	 * @create  2015年11月18日 下午2:20:35 lixing
	 * @history  
	 */
	@RequestMapping("/custClean")
	@ResponseBody
	public String clearBatch(HttpServletRequest request,HttpServletResponse response,ResCustInfoDto custInfoDto,String taskId){
		try {
			String sessionCode = ShiroUtil.getSession(Constants.DEFAULT_CAPTCHA_PARAM).toString();
			return highSeasCustPoolService.clearHighSeas(custInfoDto, request,ShiroUtil.getShiroUser(),sessionCode,taskId);
		} catch (Exception e) {
			logger.error("****【清空公海资源】  发生异常，exception:"+e.getMessage(),e);
			return "-1";
		}
	}
	
	
	/** 
	 * 流失客户回收到待分配资源
	 * @param request
	 * @param response
	 * @param custInfoDto 
	 * @create  2015年12月22日 下午3:46:21 lixing
	 * @history  
	 */
	@RequestMapping("/recyleLosingCustNum")
	@ResponseBody
	public Integer recyleLosingCustNum(HttpServletRequest request,HttpServletResponse response,ResCustInfoDto custInfoDto){
		try {
			return highSeasCustPoolService.losingCustRecyleNum(custInfoDto, request,ShiroUtil.getShiroUser());
		} catch (Exception e) {
			logger.error("****【流失客户回收到待分配资源】  发生异常，exception:"+e.getMessage(),e);
			return 0;
		}
	}
	
	/** 
	 * 流失客户回收到待分配资源
	 * @param request
	 * @param response
	 * @param custInfoDto 
	 * @create  2015年12月22日 下午3:46:21 lixing
	 * @history  
	 */
	@RequestMapping("/recyleLosingCust")
	@ResponseBody
	public String recyleLosingCust(HttpServletRequest request,HttpServletResponse response,ResCustInfoDto custInfoDto){
		try {
			return highSeasCustPoolService.losingCustRecyle(custInfoDto, request,ShiroUtil.getShiroUser());
		} catch (Exception e) {
			logger.error("****【流失客户回收到待分配资源】  发生异常，exception:"+e.getMessage(),e);
			return "-1";
		}
	}
	
	/** 
	 * 公海客户池 回收到待分配资源数量
	 * @create  2015年11月18日 下午2:21:00 lixing
	 * @history  
	 */
	@RequestMapping("/recyleSourceNum")
	@ResponseBody
	public Integer recyleSourceNum(HttpServletRequest request,HttpServletResponse response,ResCustInfoDto custInfoDto){
		try {
			return highSeasCustPoolService.highSeasRecyleNum(custInfoDto, request,ShiroUtil.getShiroUser());
		} catch (Exception e) {
			logger.error("****【回收到待分配资源】  发生异常，exception:"+e.getMessage(),e);
			return 0;
		}
	}
	
	/** 
	 * 公海客户池 回收到待分配资源
	 * @create  2015年11月18日 下午2:21:00 lixing
	 * @history  
	 */
	@RequestMapping("/recyleSource")
	@ResponseBody
	public String recyleSource(HttpServletRequest request,HttpServletResponse response,ResCustInfoDto custInfoDto,String taskId){
		try {
			return highSeasCustPoolService.highSeasRecyle(custInfoDto, request,ShiroUtil.getShiroUser(),taskId);
		} catch (Exception e) {
			logger.error("****【回收到待分配资源】  发生异常，exception:"+e.getMessage(),e);
			return "-1";
		}
	}
	
	
	/** 
	 * 取回
	 * @param request
	 * @param response 
	 * @create  2015年11月18日 下午3:06:42 lixing
	 * @history  
	 */
	@RequestMapping("/updateBatchResourceOrCust")
	@ResponseBody
	public String updateBatchResourceOrCust(HttpServletRequest request,HttpServletResponse response) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			String module = request.getParameter("module");
			Map<String, Integer> map = isResBeyondMax(user.getAccount(), user.getOrgId(), getCustIds(ids).size());
			if(map.get("flag") == 1){
				return "个人资源最大数为"+map.get("maxNum")+",库中已有"+map.get("resNum")+",取回数为"+map.get("bSize")+",超出最大资源数！";
			}
			String isShare = getSettingState(AppConstant.DATA_40018, user.getOrgId());
			List<String> custIds = getCustIds(ids);
			resCustInfoService.modifyBatchCustByQuHui(user.getAccount(), custIds,user.getOrgId(),isShare,user.getName(),module,user);
			return "1";
		} catch (Exception e) {
			logger.error("公海客户池取回失败",e);
			return "-1";
		}
	}
	
	
	/** 
	 * 取回意向
	 * @param request
	 * @param response 
	 * @create  2017年11月1日 下午3:06:42 xiaoxh
	 * @history  
	 */
	@RequestMapping("/updateBatchResourceToCust")
	@ResponseBody
	public String updateBatchResourceToCust(HttpServletRequest request,HttpServletResponse response) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			String ids = request.getParameter("ids");
			String module = request.getParameter("module");
			Map<String, Integer> map = isCustBeyondMax(user.getAccount(), user.getOrgId(), getCustIds(ids).size());
			if(map.get("flag") == 1){
				return "个人意向最大数为"+map.get("maxNum")+",库中已有"+map.get("resNum")+",取回数为"+map.get("bSize")+",超出最大意向数！";
			}
			String isShare = getSettingState(AppConstant.DATA_40018, user.getOrgId());
			List<String> custIds = getCustIds(ids);
			resCustInfoService.modifyBatchCustByQuHuiYx(user.getAccount(), custIds,user.getOrgId(),isShare,user.getName(),module,user);
//			if(custIds.size() > 1){
//				logUserOperateService.setUserOperateLog( AppConstant.Module_id1004,AppConstant.Module_Name1004, AppConstant.Operate_id20, AppConstant.Operate_Name20, custIds.size()+"条", "");
//			}
			return "1";
		} catch (Exception e) {
			logger.error("公海客户池取回失败",e);
			return "-1";
		}
	}
	
	
	public List<String> getCustIds(String custIds) {
		List<String> ids = new ArrayList<String>();
		for (String custId : custIds.split(",")) {
			if (StringUtils.isNotBlank(custId)) {
				ids.add(custId);
			}
		}
		return ids;
	}
	
	@InitBinder
    public void initBinder(HttpServletRequest request, ServletRequestDataBinder binder) throws Exception {
        binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
    }
}
