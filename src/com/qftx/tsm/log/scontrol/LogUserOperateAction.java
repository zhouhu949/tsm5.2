package com.qftx.tsm.log.scontrol;

import com.alibaba.fastjson.JSON;
import com.alicloud.openservices.tablestore.model.ColumnType;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.CodeUtils;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.query.bean.LogUserOperateQueryDto;
import com.qftx.log.tablestore.bean.TsGetRangePage;
import com.qftx.log.tablestore.bean.TsGetRangePageResp;
import com.qftx.tsm.log.bean.LogSysOperateBean;
import com.qftx.tsm.log.bean.LogUserOperateBean;
import com.qftx.tsm.log.bean.ModuleResourceBean;
import com.qftx.tsm.log.bean.RetDictionaryBean;
import com.qftx.tsm.log.dto.DtoLogUserOperateBean;
import com.qftx.tsm.log.service.LogSysOperateService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dto.CustFollowProcessJsonDto;
import com.qftx.tsm.option.dto.DictionProcessJsonDto;
import com.qftx.tsm.option.dto.DictionaryWatersDto;
import com.qftx.tsm.option.dto.MoneyJsonDto;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/log/loguser")
public class LogUserOperateAction extends BaseAction{
	private static final Logger logger = Logger.getLogger(LogUserOperateAction.class);
	@Autowired
	private CachedService cachedService;
	@Autowired
	private LogUserOperateService logUserOperateService;
	@Autowired
	private LogSysOperateService logSysOperateService;
	
	@RequestMapping("/myOperate")
	public String myOperate() {
		return "/worklog/operationLog";
	}
	
	
	/**
	 * 客户操作日志 
	 *
	 * @param request
	 * @param DtoLogUserOperateBean
	 * @return
	 * @create 2017年6月13日 上午10:36:10 xiaoxh
	 * @history
	 */
	@RequestMapping("/myLoguserOperate")
	@ResponseBody
	public Map<String, Object> myLoguserOperate(HttpServletRequest request, DtoLogUserOperateBean dtoLogUserOperateBean,String pageStr) {

			ShiroUser user = ShiroUtil.getShiroUser();
			TsGetRangePage pages=new TsGetRangePage();

			if(pageStr!=null&&!"".endsWith(pageStr)){
			    pages = JSON.parseObject(pageStr, TsGetRangePage.class);
			}
        	LogUserOperateQueryDto dto=new LogUserOperateQueryDto();
        	dto.setOrgId(user.getOrgId());
		    List<LogUserOperateBean> list=new ArrayList<LogUserOperateBean>();
			Map<String, Object> map = new HashMap<String, Object>();
		try {
			String  dDateType = request.getParameter("dDateType");	
			String  startDate = request.getParameter("startDate");	
			String  endDate = request.getParameter("endDate");	
			dtoLogUserOperateBean.setOrgId(user.getOrgId());
        //  发送时间
        	if(StringUtils.isNotBlank(dDateType) && !"0".equals(dDateType) && !"5".equals(dDateType)){
        		dtoLogUserOperateBean.setStartDate(getStartDateStr(Integer.parseInt(dDateType)));
        		dtoLogUserOperateBean.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
        		dto.setInputTimeBegin(getStartDateStr(Integer.parseInt(dDateType))+" 00:00:00");
        		dto.setInputTimeEnd(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY)+" 23:59:59");
        	}else if("5".equals(dDateType)){
        		dto.setInputTimeBegin(startDate+" 00:00:00");
        		dto.setInputTimeEnd(endDate+" 23:59:59");
        	}	
        	if (StringUtils.isNotEmpty(dtoLogUserOperateBean.getAccountsStr())) {
       		    String[] usernames = dtoLogUserOperateBean.getAccountsStr().split(",");
				List<String> owaList = Arrays.asList(usernames);
				dtoLogUserOperateBean.setAccounts(owaList);
				dto.setUserAcc(dtoLogUserOperateBean.getAccountsStr());
        	}
        	
        	if (StringUtils.isNotEmpty(dtoLogUserOperateBean.getModuleStr())) {
       		    String[] moduleStr = dtoLogUserOperateBean.getModuleStr().split(",");
				List<String> modules = Arrays.asList(moduleStr);
				dtoLogUserOperateBean.setModules(modules);
                dto.setModuleId(dtoLogUserOperateBean.getModuleStr());
        	}
//        	int count=logUserOperateService.findlogUserOperateCount(dtoLogUserOperateBean);
//    		dtoLogUserOperateBean.getPage().setTotalResult(count);

//        	List<String> listinit=logUserOperateService.findlogUserOperateIds(dtoLogUserOperateBean);
//        	if(listinit !=null && listinit.size()>0){
//        		dtoLogUserOperateBean.setIds(listinit);
//            	list=logUserOperateService.findlogUserOperateListPage(dtoLogUserOperateBean);
//        	}
        	int pageSize=0;
        	int pageNo=0;
        	if(pages.getShowCount()!=null){
            	 pageSize=pages.getShowCount();
        	}        	
        	if(pages.getShowCount()!=null){
            	 pageNo=pages.getCurrentPage();
        	}

        	TsGetRangePageResp req=logUserOperateService.selectLogTableStore(dto,pages,pageSize,pageNo);
        	TsGetRangePage retpage=(TsGetRangePage) req.getPage();
        	List<Map<String, Object>> lits2=req.getBeans();
        	if(lits2!=null&&lits2.size()>0){
        		for(Map<String, Object> maps:lits2){
        			LogUserOperateBean bean=new LogUserOperateBean();
        			bean.setId((String) maps.get("id"));
        			bean.setOrgId(user.getOrgId());
        			bean.setUserAccount((String) maps.get("userAcc"));
        			bean.setUserName((String) maps.get("userName"));
        			bean.setInputTime(new Date((Long)maps.get("inputTime")));
        			bean.setModuleId((String) maps.get("moduleId"));
        			bean.setModuleName((String) maps.get("moduleName"));
        			bean.setOperateId((String) maps.get("operateId"));
        			bean.setOperateName((String) maps.get("operateName"));
        			bean.setContent((String) maps.get("content"));
        			bean.setSystemOperateId((String) maps.get("systemOperateId"));
        			list.add(bean);
        		}
        	}
        	Map<String,TsGetRangePage> maps=new HashMap<String,TsGetRangePage>();
        	maps.put("page", retpage);
        	map.put("list", list);
        	map.put("item", maps);
        	map.put("status", "true");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			map.put("status", false);
			map.put("errorMsg", e.getMessage());
			return map;
		}
		return map;
	}
	

	/**
	 * 获取模块树 
	 *
	 * @param request
	 * @param DtoLogUserOperateBean
	 * @return
	 * @create 2017年6月13日 上午10:36:10 xiaoxh
	 * @history
	 */
	@RequestMapping("/getModuleList")
	@ResponseBody
	public List<Map<String,Object>> getModuleList(HttpServletRequest request) {
	
			ShiroUser user = ShiroUtil.getShiroUser();
			List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
//			list=cachedService.getModuleList(user.getOrgId());
//			if(list ==null || list.size() <=0){
				list=retModuleList();
//			}
		return list;
	}
	
	public List<Map<String,Object>> retModuleList(){
		ShiroUser user = ShiroUtil.getShiroUser();
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
	try {
	    List<ModuleResourceBean> modulelist1=logUserOperateService.findModuleReslistByPid("1");
	    if(modulelist1 !=null && modulelist1.size()>0){
		List<Map<String,Object>> returnList = new ArrayList<Map<String,Object>>();
		for(ModuleResourceBean mrb:modulelist1){ //第一级
			Map<String,String> attMap = new HashMap<String,String>();
			attMap.put("type", "A");
			Map<String,Object> moduleMap1 = new HashMap<String,Object>();
			moduleMap1.put("id", mrb.getModuleId());
			moduleMap1.put("text", mrb.getModuleName());
			moduleMap1.put("attributes", attMap);
			Map<String,String> resMap = new HashMap<String,String>();
			resMap.put("pid", mrb.getModuleId());
			resMap.put("levelType", "2");
			List<ModuleResourceBean> modulelist2=logUserOperateService.findModuleRes(resMap);
			if(modulelist2 !=null && modulelist2.size()>0){
				List<Map<String,Object>> retList2 = new ArrayList<Map<String,Object>>();
				for(ModuleResourceBean mrb2:modulelist2){
					Map<String,String> attMap2 = new HashMap<String,String>();
					attMap2.put("type", "B");
					Map<String,Object> moduleMap2 = new HashMap<String,Object>();
					moduleMap2.put("id", mrb2.getModuleId());
					moduleMap2.put("text", mrb2.getModuleName());
					moduleMap2.put("attributes", attMap2);
					Map<String,String> resMap2 = new HashMap<String,String>();
					resMap2.put("pid", mrb2.getModuleId());
					resMap2.put("levelType", "3");
					List<ModuleResourceBean> modulelist3=logUserOperateService.findModuleRes(resMap2);
					if(modulelist3 !=null && modulelist3.size()>0){
						List<Map<String,Object>> retList3 = new ArrayList<Map<String,Object>>();
						for(ModuleResourceBean mrb3 :modulelist3){
							Map<String,String> attMap3 = new HashMap<String,String>();
							attMap3.put("type", "C");
							Map<String,Object> moduleMap3 = new HashMap<String,Object>();
							moduleMap3.put("id", mrb3.getModuleId());
							moduleMap3.put("text", mrb3.getModuleName());
							moduleMap3.put("attributes", attMap3);
							retList3.add(moduleMap3);
						}
					 moduleMap2.put("children", retList3);
					}
				  retList2.add(moduleMap2);
				}
			   moduleMap1.put("children", retList2);
			}
			list.add(moduleMap1);
		}

	    }
	    
	    cachedService.setModuleList(user.getOrgId(), list);
	} catch (Exception e) {
		logger.error(e.getMessage(), e);
	}
	return list;
	}
	
	
	/**
	 * 获取销售管理数据 
	 *
	 * @param request
	 * @param DtoLogUserOperateBean
	 * @return
	 * @create 2017年6月14日 上午14:36:10 xiaoxh
	 * @history
	 */
	@RequestMapping("/getSysOPerateDate")
	public String getSysOPerateDate(HttpServletRequest request,String id) {
	
			ShiroUser user = ShiroUtil.getShiroUser();

			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, String> map2 = new HashMap<String, String>();
			LogSysOperateBean logSysOperateBean =new LogSysOperateBean();
			DataDictionaryBean dataDictionaryBean=new DataDictionaryBean();
		    List<DataDictionaryBean> list=new ArrayList<DataDictionaryBean>();
		    List<DictionaryWatersDto> group_list=new ArrayList<DictionaryWatersDto>();
		    List<DictionProcessJsonDto> option_list=new ArrayList<DictionProcessJsonDto>();
			List<DataDictionaryBean> dictionaryList =new ArrayList<DataDictionaryBean>();
		    
		try {
			if(id != null && id != ""){
			map2.put("orgId", user.getOrgId());
			map2.put("id", id);
			LogUserOperateBean logUserOperateBean =new LogUserOperateBean();
			logUserOperateBean.setOrgId(user.getOrgId());
			logUserOperateBean.setId(id);
			LogUserOperateBean logUserOperateBean_new=logUserOperateService.findlogUserOperateData(logUserOperateBean);
//			logSysOperateBean=logSysOperateService.findSysOper(map2);
//			if(logSysOperateBean != null){
//			String	json =logSysOperateBean.getJsonData();
			if(logUserOperateBean_new != null){
			String	json =logUserOperateBean_new.getSystemOperateId();
			RetDictionaryBean retDictionaryBean=new RetDictionaryBean();
//			System.out.println(json);
			Map<String, Class> classMap = new HashMap<String, Class>();  
		
			classMap.put("grouplist", DictionaryWatersDto.class);  			  
			classMap.put("optionist", DictionProcessJsonDto.class);  
			classMap.put("moneys", MoneyJsonDto.class);  
			classMap.put("followProcess", CustFollowProcessJsonDto.class); 
			List<RetDictionaryBean> listtest = JsonUtil.getListJson(json, RetDictionaryBean.class,classMap);
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");  
			if(listtest != null && listtest.size()>0){
				for(RetDictionaryBean rdb:listtest){
					DataDictionaryBean ddb=new DataDictionaryBean();
					ddb.setDictionaryCode(rdb.getCode());
					ddb.setDictionaryValue(rdb.getValue());
					ddb.setIsOpen(rdb.getOpen());
					ddb.setModifierAcc(rdb.getModfiyAcc());
					ddb.setModifydate(sdf.parse(rdb.getModfiyDate()));
					ddb.setDictionaryName(rdb.getName());
					ddb.setDictionaryValueNotes(rdb.getNotes());
					list.add(ddb);
					if(rdb.getGrouplist()!=null && rdb.getGrouplist().size()>0){
						group_list= rdb.getGrouplist();
						
						// 用户分组 缓存
						List<OrgGroup> deptList = cachedService.getOrgGroup(user.getOrgId());
						Map<String, String> deptMap = new HashMap<String, String>();
						if (deptList != null && deptList.size() > 0) {
							for (OrgGroup dept : deptList) {
								deptMap.put(dept.getGroupId(), dept.getGroupName());
							}
						}
						for(DictionaryWatersDto dto : group_list){						
							dto.setGroupName(deptMap.get(dto.getGroupId()));
							StringBuffer sb = new StringBuffer();
							for(String str : dto.getShareGroupIds().split(",")){
								sb.append(deptMap.get(str));
								sb.append(",");
							}
							if(sb.length()>0){
								sb = sb.deleteCharAt(sb.length()-1);
							}
							dto.setShareGroupNames(sb.toString());
						}
						
					}
					if(rdb.getOptionist()!=null && rdb.getOptionist().size()>0){
						option_list= rdb.getOptionist();
					}
				}
			}
			
			Map<String, DataDictionaryBean> dictionaryMap = new HashMap<String, DataDictionaryBean>();
			for (DataDictionaryBean obj : list) {
				if(null != obj.getDictionaryCode()){
					dictionaryMap.put(obj.getDictionaryCode(), obj);
				}
			}

			//取得相关设置的开启关闭按钮
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA13));//资源回收设置开关-下标[0]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA14));//资源去重设置开关-下标[1]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA15));//客户跟进设置开关-下标[2]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA16));//有效呼叫设置开关-下标[3]
			
			
			//相关设置值
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_RECYLE_EXPIRE));//下标[4]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA11));//下标[5]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA12));//下标[6]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_FOLLOW_EXPIRE));//下标[7]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_FOLLOW_REMIND));//下标[8]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA03));//客户跟进设置->意向客户上限 下标[9]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA04));// 有效呼叫设置->通话有效期设置下标[10]
			//需求后续追加的
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA18));//呼叫区号设置开关-下标[11]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA19));//下标[12]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA20));//下标[13]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA21));//下标[14]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA22));//下标[15]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA23));//资源安全设置-下标[16]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA24));//电话号码中间4位用"*"作模糊处理-下标[17]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA28));//增加个人资源上限的限制设置-下标[18]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA34));//资源规则设置(对客户的电话号码、邮箱等字段进行格式校验 )[19]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA35));//常用电话[20]	
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA36));//备用电话[21]			
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA37));//备用电话2[22]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA38));//传真[23]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA39));//邮箱[24]	
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA42));//客户方骚扰开关设置[25]	
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA43));//客户方骚扰开关设置[26]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40016));// 客户跟进设置 当个人资源数少于[27]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40017));// 客户跟进设置 每次分配xx人[28]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40018));// 待分配资源共享到公海池设置[29]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40019));// 数据默认显示设置[30]
			dictionaryList.add(null);// 每日计划审核设置[31]
			
			dictionaryList.add(null);// 定时分配时长设置开关[32]
			dictionaryList.add(null);// 每个帐号时长低于的临界值[33]
			dictionaryList.add(null);// 每个帐号每次定量分配的时长[34]
			dictionaryList.add(null);// 每个帐号每天可分配的时长[35]
			dictionaryList.add(null);// 管理员修改号码权限控制[36]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40031));// 自动审核时间设置 1-- 1号，2-- 5号，3-- 10号 [37]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40032));// 公海资源批量取回开关设置 [38]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40033));// 公海资源批量取回开关设置 [39]
			//data40033
			
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40034));// 公海资源批量取回开关设置 [40]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40035));// 每个帐号时长低于的临界值 [41]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40036));// 每个帐号每次定量分配的时长 [42]
			
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40037));// 号码隐藏白名单 [43]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40038));// 客户信息只读白名单[44]		
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40039));// 客户信息只读白名单[45]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40040));// 共享客户数据查看范围的设置[46]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40041));// 管理员签约权限设置[47]
			
			if(dictionaryMap.get(AppConstant.DATA_40037) != null && "1".equals(dictionaryMap.get(AppConstant.DATA_40037).getIsOpen())){
				Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
				String values = dictionaryMap.get(AppConstant.DATA_40037).getDictionaryValue();
				if(StringUtils.isNotBlank(values)){
					StringBuffer sbf = new StringBuffer();
					String[] strs = values.split(",");
					for(String str : strs){
						sbf.append(nameMap.get(str) == null ? str : nameMap.get(str));
						sbf.append(",");
					}
					request.setAttribute("baiNumval", sbf.toString());
				}
			}
			
			if(dictionaryMap.get(AppConstant.DATA_40038) != null && "1".equals(dictionaryMap.get(AppConstant.DATA_40038).getIsOpen())){
				Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
				String values = dictionaryMap.get(AppConstant.DATA_40038).getDictionaryValue();
				if(StringUtils.isNotBlank(values)){
					StringBuffer sbf = new StringBuffer();
					String[] strs = values.split(",");
					for(String str : strs){
						sbf.append(nameMap.get(str) == null ? str : nameMap.get(str));
						sbf.append(",");
					}
					request.setAttribute("heiNumval", sbf.toString());
				}
			}
			
			// 页面隐藏域存储json数据，会有格式问题，做BASE64编码
			if(dictionaryMap.get(AppConstant.DATA_50001) != null){
				DataDictionaryBean bean = dictionaryMap.get(AppConstant.DATA_50001);
				if(StringUtils.isNotBlank(bean.getDictionaryValue())){
					bean.setDictionaryValue(CodeUtils.base64Encode(bean.getDictionaryValue()).replaceAll("\r\n", ""));
					dictionaryMap.put(AppConstant.DATA_50001, bean);
				}				
			}
			
			
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50001));// 意向客户回收公海设置增加按钮“销售进程回收设置”[48]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50002));// 签约客户回收设置 is_open 0：关闭，1：开启[49]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50003));// 自动连拨设置  dictionary_value 0：关闭，1：开启 [50]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50004));// 启用指标系数占比 dictionary_value 0：未选中，1：选中[51]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50005));// 根据勾选的对象确定通话时长的值 每日的通话时长达标值 [52]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50006));// 行动完成率基数设置 呼入时长  dictionary_value 0： 未选中，1：选中[53]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50007));// 行动完成率基数设置 呼出时长 dictionary_value 0： 未选中，1：选中 [54]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50008));// 行动完成率基数设置 通话次数，呼出电话且对方接通的次数 [55]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50009));// 行动完成率基数设置 通话时长指标系数[56]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50010));// 行动完成率基数设置 接通次数指标系数[57]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50011));// 共有客户设置 dictionary_value 0：关闭，1：开启[58]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50012));// 允许共有者放弃被共有的客户 dictionary_value 0：关闭，1：开启[59]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50013));// 允许共有者修改被共有的客户信息 dictionary_value 0：关闭，1：开启[60]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50014));// 允许共有者对共有客户进行签约...[61]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50015));// APP查看附近的客户 dictionary_value 0：关闭，1：开启[62]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50016));// 公海客户防骚扰设置 is_open 0：关闭，1：开启[63]
			dictionaryList.add(null);// 生日弹幕弹出时间为每日的 小时[64]
			dictionaryList.add(null);// 生日弹幕弹出时间为每日的 分钟[65]
			dictionaryList.add(null);// 荣誉榜弹幕弹出世间为每月的第一个 星期值[66]
			dictionaryList.add(null);// 荣誉弹幕显示规则：勾选表示启用 0：未勾选，1：勾选 新增签约[67]
			dictionaryList.add(null);// 荣誉弹幕显示规则：勾选表示启用 0：未勾选，1：勾选 呼出次数[68]
			dictionaryList.add(null);// 荣誉弹幕显示规则：勾选表示启用 0：未勾选，1：勾选 新增意向[69]
			dictionaryList.add(null);// 荣誉弹幕显示规则：勾选表示启用 0：未勾选，1：勾选 呼出时长[70]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50024));// 资源回收设置单选按钮选择项 1:选择第一条，2:选择第二条[71]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50025));// 资源回收设置 多少天未联系将自动回收[72]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50026));// 资源回收设置 多少天未联系将自动回收到 1：待分配资源，2：公海客户池[73]
			
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50027));// 双卡自动切换规则设置  dictionary_value 0：关闭，1：开启[74]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50028));// 双卡自动切换规则设置单选按钮选择项 1:选择第一条，2:选择第二条[75]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50029));// 双卡自动切换规则设置呼出多少次，自动切换指另一张卡[76]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50030));// 双卡自动切换规则设置自动切换时间为每日的 小时[77]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50031));// 双卡自动切换规则设置自动切换时间为每日的 分钟[78]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50032));// 双卡自动切换规则设置每月呼叫上限  分钟，不再执行自动切换[79]
			
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50033));// 弹幕设置开关 dictionary_value 0：关闭，1：开启 [80]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50034));// 生日弹幕开关 dictionary_value 0：关闭，1：开启 。默认为开启  [81]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50035));// 时间设置点击展开时间控件，时（24小时）、分（60分），默认值为13：45 [82]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50036));// 允许发送生日红包，红包额度设置[83]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50037));// 月度冠军弹幕开关 dictionary_value 0：关闭，1：开启 。默认为开启 [84]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50038)); // 月度冠军默认发送时间下拉选项 默认2[85]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50039));// 月度签约金额冠军展示，公司奖励红包额度设置 [86]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50040));// 月度新增意向冠军展示，公司奖励红包额度设置 [87]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50041));// 月度新增签约冠军展示，公司奖励红包额度设置 [88]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50042));// 月度呼出次数冠军展示，公司奖励红包额度设置 [89]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50043));// 月度呼出时长冠军展示，公司奖励红包额度设置 [90]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50044));// 签约弹幕设置开关  dictionary_value 0：关闭，1：开启 。默认为开启  [91]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50045));// 不显示客户信息复选框 dictionary_value 0：非选中，1：选中 默认非选中 [92]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50046));// 显示签约金额 dictionary_value 0：非选中，1：选中  默认非选中 [93]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50047));// 为签约成功成员送上公司奖励红包：红包额度设置 [94]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50048));// 回款金额弹幕设置开关  dictionary_value 0：关闭，1：开启 。默认为关闭  [95]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50049));// 金额范围 单选按钮设置。dictionary_value 1：订单金额不小于....，2：订单金额范围....... 默认 1  [96]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50050));// 单选按钮选1时  金额范围[97]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50051));// 单选按钮选1时 红包设置值 [98]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50052));// 单选按钮选2时：金额范围，以及红包设置值  [99]
			if(dictionaryMap.get(AppConstant.DATA_50052) != null){
				String jsonStr = dictionaryMap.get(AppConstant.DATA_50052).getDictionaryValue();
				if(StringUtils.isNotBlank(jsonStr)){
					List<MoneyJsonDto> listmoney = JsonUtil.getListJson(jsonStr, MoneyJsonDto.class);
					request.setAttribute("moneys", listmoney);
				}
			}
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50053));// 客户跟进时间段设置 dictionary_value 这里存储的json字符串  [100]
			if(dictionaryMap.get(AppConstant.DATA_50053) != null){
				String jsonStr = dictionaryMap.get(AppConstant.DATA_50053).getDictionaryValue();
				if(StringUtils.isNotBlank(jsonStr)){
					List<CustFollowProcessJsonDto> listfollow = JsonUtil.getListJson(jsonStr, CustFollowProcessJsonDto.class);
					Map<String, String> optionMap = cachedService.getOrgSaleProcess(user.getOrgId());	
					String[] ids = new String[]{};
					StringBuffer sbf = null;
					for(CustFollowProcessJsonDto pdto : listfollow){
						ids = pdto.getOptionId().split(",");
						sbf = new StringBuffer();
						int i = 0;
						for(String str : ids){							
							i++;
							sbf.append(optionMap.get(str));
							if(i!=ids.length){
								sbf.append(",");
							}							
						}
						pdto.setOptionName(sbf.toString());
					}
					request.setAttribute("followProcess", listfollow);
				}
			}
			//从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,user.getOrgId());
			request.setAttribute("process",options);		
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50054));// 允许公海客户批量取回为资源 dictionary_value 0：非选中，1：选中  [101]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50055));// 允许公海客户批量取回为意向  [102]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50056));// 客户信息导出审核设置  号码 [103]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50057));// 客户信息导出审核设置 审核人  [104]
			request.setAttribute("dictionaryList", dictionaryList);
			request.setAttribute("waters", group_list);
			request.setAttribute("dictionaryList", dictionaryList);
			request.setAttribute("systemoperateids", "1");
		}
		}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "/tsm/option/salesmanage";
	}
	
	
	
	
	
//	/**
//	 * test,测试操作日志
//	 *
//	 * @param request
//	 * @param 
//	 * @return
//	 * @create 2017年6月13日 上午10:36:10 xiaoxh
//	 * @history
//	 */
//	@RequestMapping("/gettestModuleList")
//	@ResponseBody
//	public void gettestModuleList(HttpServletRequest request) {
//		try {
//			ShiroUser user = ShiroUtil.getShiroUser();
//			String userAccount=user.getAccount();
//			String userName=user.getName();
//			String moduleId=AppConstant.Module_id13;
//			String moduleName=AppConstant.Module_Name13;
//			String operateId =AppConstant.Operate_id76;
//			String operateName=AppConstant.Operate_Name76;
//			String content="我的客户测试";
//			String systemOperateId=request.getParameter("systemOperateId");
//			getUserOperate(userAccount, userName,moduleId ,moduleName , operateId , operateName , content, systemOperateId );
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//	}
	
	
	public static void main(String[] args) {
		String text="{\"showCount\":20,\"totalPage\":11,\"totalResult\":211,\"currentPage\":1,\"pageSize\":20,\"pageNo\":1,\"pageCount\":11,\"recordCount\":211,\"curPageSize\":20,\"operation\":\"3\",\"startPk\":{\"id\":\"z\",\"orgId\":\"fhtx\",\"userAcc\":\"z\",\"inputTime\":\"1511507899000\"},\"endPk\":{\"id\":\"0\",\"orgId\":\"fhtx\",\"userAcc\":\"0\",\"inputTime\":\"1479971899000\"},\"previousStartPk\":null,\"nextStartPk\":{\"id\":\"a729b16ab90b416fa1d68d7e3e260f94\",\"orgId\":\"fhtx\",\"userAcc\":\"fhtx001\",\"inputTime\":\"1509033600000\"},\"preparePreviousStartPk\":{\"id\":\"c30b5a0db3f345e3ab18a9306124903b\",\"orgId\":\"fhtx\",\"userAcc\":\"fhtx001\",\"inputTime\":\"1509033600000\"},\"prepareNextStartPk\":null,\"compFilter\":null,\"columns\":{\"tableName\":\"log_user_operate\",\"pkSectionId\":\"orgId\",\"pkSectionIdType\":\"STRING\",\"pkSecondId\":\"userAcc\",\"pkSecondIdType\":\"STRING\",\"pkThirdId\":\"inputTime\",\"pkThirdIdType\":\"INTEGER\",\"pkId\":\"id\",\"pkIdType\":\"STRING\",\"allColumnMap\":{\"content\":\"STRING\",\"ownerAcc\":\"STRING\",\"operateNum\":\"INTEGER\",\"moduleId\":\"STRING\",\"operateName\":\"STRING\",\"resOperateName\":\"STRING\",\"moduleName\":\"STRING\",\"data\":\"STRING\",\"userName\":\"STRING\",\"batchId\":\"STRING\",\"resOperateId\":\"STRING\",\"operateId\":\"STRING\"}},\"hasPrevious\":false,\"hasNext\":true}";
		TsGetRangePage data = JSON.parseObject(text, TsGetRangePage.class);
		System.out.println(JSON.toJSONString(data));
	}


}
