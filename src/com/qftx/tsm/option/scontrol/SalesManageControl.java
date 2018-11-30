package com.qftx.tsm.option.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.AuthOrgMessageBean;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dto.HttpJsonReturn;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.ExecutorUtils;
import com.qftx.base.util.HttpUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.*;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.log.tablestore.bean.LogBean;
import com.qftx.tsm.log.service.LogCustInfoService;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dto.*;
import com.qftx.tsm.option.service.DataDictionaryService;
import com.qftx.tsm.sys.service.CustSearchSetService;
import com.qftx.tsm.sys.service.HookSmsConfigService;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.net.URLDecoder;
import java.util.*;


/***
 * 销售管理设置
 * @author: zwj
 * @since: 2015-12-4  上午9:59:25
 * @history: 4.x
 */
@Controller
@RequestMapping("/sales/manage")
public class SalesManageControl {
	@Autowired
	private DataDictionaryService dataDictionaryService;
	@Autowired
	private LogCustInfoService logCustInfoService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private UserService userService;
	@Autowired
	private HookSmsConfigService hookSmsConfigService;
	@Autowired
	private CustSearchSetService custSearchSetService;
	@Autowired private UserService usersService;
	@Autowired
	private OrgGroupUserService orgGroupUserService;
	
	
	Logger logger=Logger.getLogger(SalesManageControl.class);
	
	/***************************【慧营销4.0 开始】******************************************/
	
	/** 
	 * 销售管理设置【页面】
	 */
	@RequestMapping("/salesmanage")
	public String salesmanagePage(HttpServletRequest request){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			User tmpUser = usersService.getAdmin(shiroUser.getOrgId()); // 查询单位管理员
			if(tmpUser != null && shiroUser.getAccount().equals(tmpUser.getUserAccount())){
				request.setAttribute("isAdmin", "1");
			}
			DataDictionaryBean dictionary = new DataDictionaryBean();
			dictionary.setOrgId(shiroUser.getOrgId());
			
			List<DataDictionaryBean>dictionaryList = dataDictionaryService.getListByCondtion(dictionary);
			Map<String, DataDictionaryBean> dictionaryMap = new HashMap<String, DataDictionaryBean>();
			for (DataDictionaryBean obj : dictionaryList) {
				if(null != obj.getDictionaryCode()){
					dictionaryMap.put(obj.getDictionaryCode(), obj);
				}
			}
			dictionaryList = new ArrayList<DataDictionaryBean>();
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
			if(dictionaryMap.get(AppConstant.DATA_40033) != null && "1".equals(dictionaryMap.get(AppConstant.DATA_40033).getIsOpen())){
				String jsonStr = dictionaryMap.get(AppConstant.DATA_40033).getDictionaryValue();
				if(StringUtils.isNotBlank(jsonStr)){
					List<DictionaryWatersDto> list = JsonUtil.getListJson(jsonStr, DictionaryWatersDto.class);
					if(list != null && list.size() >0){
						// 用户分组 缓存
						List<OrgGroup> deptList = cachedService.getOrgGroup(shiroUser.getOrgId());
						Map<String, String> deptMap = new HashMap<String, String>();
						if (deptList != null && deptList.size() > 0) {
							for (OrgGroup dept : deptList) {
								deptMap.put(dept.getGroupId(), dept.getGroupName());
							}
						}
						for(DictionaryWatersDto dto : list){						
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
					request.setAttribute("waters", list);
				}
			}
			
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40034));// 公海资源批量取回开关设置 [40]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40035));// 每个帐号时长低于的临界值 [41]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40036));// 每个帐号每次定量分配的时长 [42]
			
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40037));// 号码隐藏白名单 [43]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40038));// 客户信息只读白名单[44]		
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40039));// 客户信息只读白名单[45]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40040));// 共享客户数据查看范围的设置[46]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_40041));// 管理员签约权限设置[47]
			if(dictionaryMap.get(AppConstant.DATA_40037) != null && "1".equals(dictionaryMap.get(AppConstant.DATA_40037).getIsOpen())){
				Map<String,String> nameMap = cachedService.getOrgUserNames(shiroUser.getOrgId());
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
				Map<String,String> nameMap = cachedService.getOrgUserNames(shiroUser.getOrgId());
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
					List<MoneyJsonDto> list = JsonUtil.getListJson(jsonStr, MoneyJsonDto.class);
					request.setAttribute("moneys", list);
				}
			}
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50053));// 客户跟进时间段设置 dictionary_value 这里存储的json字符串  [100]
			if(dictionaryMap.get(AppConstant.DATA_50053) != null){
				String jsonStr = dictionaryMap.get(AppConstant.DATA_50053).getDictionaryValue();
				if(StringUtils.isNotBlank(jsonStr)){
					List<CustFollowProcessJsonDto> list = JsonUtil.getListJson(jsonStr, CustFollowProcessJsonDto.class);
					Map<String, String> optionMap = cachedService.getOrgSaleProcess(shiroUser.getOrgId());	
					String[] ids = new String[]{};
					StringBuffer sbf = null;
					for(CustFollowProcessJsonDto pdto : list){
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
					request.setAttribute("followProcess", list);
				}
			}
			//从缓存读取销售进程列表
			List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,shiroUser.getOrgId());
			request.setAttribute("process",options);		
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50054));// 允许公海客户批量取回为资源 dictionary_value 0：非选中，1：选中  [101]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50055));// 允许公海客户批量取回为意向  [102]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50056));// 客户信息导出审核设置  号码 [103]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50057));// 客户信息导出审核设置 审核人  [104]
			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50058));// 资源筛查设置  [105]
//			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50059));// 经营报告设置  [106]
//			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50060));// 经营报告设置》次日第二次8：00推送完整数据开关  [107]
//			if (dictionaryMap.get(AppConstant.DATA_50059)!=null) {
//				List<Map<String,Object>> listMaps = new ArrayList<Map<String,Object>>();
//				listMaps = orgGroupUserService.getTreeGroupUser(shiroUser.getId(), shiroUser.getOrgId());
//				request.setAttribute("manageReport", listMaps);
//			}
//			dictionaryList.add(dictionaryMap.get(AppConstant.DATA_50061));// 经营报告设置》推送对象多选值  [108]
			request.setAttribute("dictionaryList", dictionaryList);
		}catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/tsm/option/salesmanage";
	}
	
	/** 
	 *  销售管理设置【操作】
	 */
	@RequestMapping("/updateSalesNorms")
	@ResponseBody
	public String updateSalesNorms(HttpServletRequest request,DataDictionaryModel dataDictionaryModel){
		try{
			String comAccVal = request.getParameter("comAccVal"); // 共有者设置是否改变
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			List<DataDictionaryBean>dictionaryList = dataDictionaryModel.getDictionaryList();
			List<DictionaryWatersDto> waters = dataDictionaryModel.getDictionaryWaterList();
			List<MoneyJsonDto> moneys = dataDictionaryModel.getDictionaryMoneyList();
			List<CustFollowProcessJsonDto> follows = dataDictionaryModel.getDictionaryFollowList();
			String waterJson = "";
			String moneyJson = "";
			String followJson = "";
			if(waters !=null && waters.size()>0){
				Iterator<DictionaryWatersDto> iterator = waters.iterator();
				while(iterator.hasNext()){
					DictionaryWatersDto dtos = iterator.next();
					if(StringUtils.isBlank(dtos.getGroupId()) || StringUtils.isBlank(dtos.getShareGroupIds())){
						iterator.remove();
					}
				}
				if(waters !=null && waters.size()>0){
					waterJson = JsonUtil.getJsonString(waters);	
				}				
			}
			dictionaryList.get(39).setDictionaryValue(
					!dictionaryList.get(39).getIsOpen().equals("0") ? waterJson : 
						"");
			// 回款金额弹幕 金额范围 单选按钮设置。dictionary_value 1：订单金额不小于....，2：订单金额范围....... 默认 1  [96]
			if(dictionaryList.get(96).getDictionaryValue().equals("2") && moneys!=null && moneys.size() > 0){
				Iterator<MoneyJsonDto> iterator = moneys.iterator();
				while(iterator.hasNext()){
					MoneyJsonDto dtos = iterator.next();
					if(StringUtils.isBlank(dtos.getRedPacket()) || "0".equals(dtos.getRedPacket())){
						iterator.remove();
					}
				}
				if(moneys !=null && moneys.size()>0){
					moneyJson = JsonUtil.getJsonString(moneys);	
				}					
			}
			dictionaryList.get(99).setDictionaryValue(moneyJson);
			
			// 客户跟进时间段设置 dictionary_value 这里存储的json字符串 参照CustFollowProcessJsonDto类
			if("1".equals(dictionaryList.get(100).getIsOpen()) && follows !=null && follows.size()>0){
				Iterator<CustFollowProcessJsonDto> iterator = follows.iterator();
				while(iterator.hasNext()){
					CustFollowProcessJsonDto dtos = iterator.next();
					if(StringUtils.isBlank(dtos.getIsAlert()) || "0".equals(dtos.getIsAlert()) || StringUtils.isBlank(dtos.getOptionId()) || StringUtils.isBlank(dtos.getStartTime())){
						iterator.remove();
					}
				}
				if(follows !=null && follows.size()>0){
					followJson = JsonUtil.getJsonString(follows);	
				}				
			}
			dictionaryList.get(100).setDictionaryValue(followJson);
			// 电话号码去重设置(如果为null,表示不去重)
			dictionaryList.get(5).setDictionaryValue(
					dictionaryList.get(5).getDictionaryValue() == null ? "1" : 
						dictionaryList.get(5).getDictionaryValue());
			// 单位名称去重设置(如果为null,表示不去重)
			dictionaryList.get(6).setDictionaryValue(
					dictionaryList.get(6).getDictionaryValue() == null ? "0" : 
						dictionaryList.get(6).getDictionaryValue());
			// 单位主页去重设置(如果为null,表示不去重)
			dictionaryList.get(15).setDictionaryValue(
					dictionaryList.get(15).getDictionaryValue() == null ? "0" : 
						dictionaryList.get(15).getDictionaryValue());
			//处理复选框
			String isOpen_7 = dictionaryList.get(7).getIsOpen();
			if((isOpen_7 !=null) && (!isOpen_7.equals(""))){
				dictionaryList.get(7).setIsOpen("1");
			}else{
				dictionaryList.get(7).setIsOpen("0");
			}
			String isOpen_9 = dictionaryList.get(9).getIsOpen();
			if((isOpen_9 !=null) && (!isOpen_9.equals(""))){
				dictionaryList.get(9).setIsOpen("1");
			}else{
				dictionaryList.get(9).setIsOpen("0");
			}
			String isOpen_13 = dictionaryList.get(13).getIsOpen();
			if((isOpen_13 !=null) && (!isOpen_13.equals(""))){
				dictionaryList.get(13).setIsOpen("1");
				dictionaryList.get(14).setIsOpen("1");
			}else{
				dictionaryList.get(13).setIsOpen("0");
				dictionaryList.get(14).setIsOpen("0");
			}
			String isOpen_16 = dictionaryList.get(16).getIsOpen();
			if((isOpen_16 !=null) && (!isOpen_16.equals(""))){
				dictionaryList.get(16).setIsOpen("1");
			}else{
				dictionaryList.get(16).setIsOpen("0");
			}
		
			String isOpen_18 = dictionaryList.get(18).getIsOpen();
			if((isOpen_18 !=null) && (!isOpen_18.equals(""))){
				dictionaryList.get(18).setIsOpen("1");
			}else{
				dictionaryList.get(18).setIsOpen("0");
			}
			String isOpen_27 = dictionaryList.get(27).getIsOpen();
			if((isOpen_27 !=null) && (!isOpen_27.equals(""))){
				dictionaryList.get(27).setIsOpen("1");
			}else{
				dictionaryList.get(27).setIsOpen("0");
			}
			// 获取数据默认显示设置值
			String pageSize = dictionaryList.get(30).getDictionaryValue();	
			if(StringUtils.isNotBlank(pageSize)){
				shiroUser.setPageSize(Integer.parseInt(pageSize));
			}
			// 页面数据进行了BASE64编码，入库时做解码
			if(StringUtils.isNotBlank(dictionaryList.get(48).getDictionaryValue())){
				dictionaryList.get(48).setDictionaryValue(new String(CodeUtils.base64Decode(dictionaryList.get(48).getDictionaryValue()),IContants.CHAR_ENCODING));
			}
			
			// 公海客户批量取回设置总开关是否开启
			if("0".equals(dictionaryList.get(101).getDictionaryValue()) && "0".equals(dictionaryList.get(102).getIsOpen())){
				dictionaryList.get(38).setDictionaryValue("0");
			}
			
			Date time = DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern);
			for (DataDictionaryBean obj : dictionaryList) {
				obj.setModifierAcc(shiroUser.getAccount());
				obj.setModifydate(time);
				obj.setOrgId(shiroUser.getOrgId());
			}		
			dataDictionaryService.modifyTrendsBatch(dictionaryList);
			//修改缓存
			CachedUtil.getInstance().delete(shiroUser.getOrgId() + CachedNames.SEPARATOR + CachedNames.DATADICTIONARY);
			
			final String userOrgId = shiroUser.getOrgId();
			final String userAccount = shiroUser.getAccount();
			if(!comAccVal.equals(dictionaryList.get(58).getDictionaryValue())){
				ExecutorUtils.THREAD_POOL.submit(new Runnable() {
					public void run() {	
						// 查询项管理 共有者 字段启用或停用
						try {
							custSearchSetService.updateByFileCode("commonAcc",userOrgId,userAccount);
						} catch (Exception e) {						
							logger.error("查询项管理 共有者 字段启用或停用 异常！", e);
						}
						// 删除高级查询缓存
						for(int i = 1;i<16;i++){
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +"0"+CachedNames.HIGH_SEARCH_+i);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +"1"+CachedNames.HIGH_SEARCH_+i);
						}
						List<String> accounts = userService.getUserAccounts(userOrgId);
						for(String account : accounts){
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+1);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+2);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+3);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+4);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+5);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+6);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+7);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+8);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+9);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+10);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+11);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+12);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+13);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+14);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+15);
							CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR +account+CachedNames.HIGH_SEARCH_+16);
						}
						CachedUtil.getInstance().delete(userOrgId + CachedNames.SEPARATOR + CachedNames.SEARCH_SET);
					}
				});
			}
			
			
			/**
			 * 隐藏号码状态推送 调用接口
			 */
			ExecutorUtils.THREAD_POOL.submit(new Runnable() {
					public void run() {	
						try{
							logger.debug(userOrgId+"隐藏号码状态推送开始！");
							Map<String,String>params = new HashMap<String, String>();
							params.put("orgId", userOrgId);
							String url = ConfigInfoUtils.getStringValue("set_telphone_display");
							String jsonStr = HttpUtil.doPost(url, params);
							HttpJsonReturn returnBean = JSON.parseObject(jsonStr,HttpJsonReturn.class);
							if(!"1".equals(returnBean.getCode())){ // 如果返回失败 则再发送一次
								HttpUtil.doPost(url, params);
							}
						}catch(Exception e){
							logger.error(userOrgId+"隐藏号码状态推送异常！", e);
						}					
					}
				});
			
			String isLocalWeb = ConfigInfoUtils.getStringValue("is_local_web"); // #是否本地部署 0:否，1:是
			if(StringUtils.isNotBlank(isLocalWeb) && "1".equals(isLocalWeb)){
				ExecutorUtils.THREAD_POOL.submit(new Runnable() {
					public void run() {	
						try{
							int isHookSet = 0;
							if(hookSmsConfigService.findExistByOrgId(userOrgId) > 0){ 
								 isHookSet = 1;
							 }	 
							String isEffect = "0";  // 有效呼叫设置是否开启 ：0 关闭，1 开启
							String effectVal = "0"; // 有效呼叫设置值
							isEffect = cachedService.getDirList(AppConstant.DATA16, userOrgId).get(0).getDictionaryValue();
							if ("1".equals(isEffect)) {
								List<DataDictionaryBean> list1 = cachedService.getDirList(AppConstant.DATA04, userOrgId);
								if (list1 != null && list1.size() > 0) {
									effectVal = list1.get(0).getDictionaryValue();
								}
							}
							AuthOrgMessageBean bean = new AuthOrgMessageBean();
							bean.setId(SysBaseModelUtil.getModelId());
							bean.setOrgId(userOrgId);
							bean.setType("4");
							bean.setIsHookSet(isHookSet);
							bean.setIsUnCall(1);
							bean.setIsEffect(isEffect);
							bean.setEffectVal(effectVal);
							bean.setMachineCode(ConfigInfoUtils.getStringValue("auth_code"));
							Map<String,String>params = new HashMap<String, String>();
							params.put("data", JsonUtil.getJsonString(bean));
							//
							String url = ConfigInfoUtils.getStringValue("total_jms_send_message_url");
							String jsonStr = HttpUtil.doPost(url, params);
							System.out.println(jsonStr);
						}catch(Exception e){
							logger.error(userOrgId+"修改total_jms_send_message_url异常！", e);
						}					
					}
				});
			}
			List<DataDictionaryBean>dictionaryList1 = cachedService.getDictionary(shiroUser.getOrgId());
			
			List<Map<String,Object>>maps = new ArrayList<Map<String,Object>>();
			for(DataDictionaryBean dt : dictionaryList1){
				Map<String,Object>map = new HashMap<String, Object>();
				map.put("code", dt.getDictionaryCode());
				if(AppConstant.DATA_40033.equals(dt.getDictionaryCode())){
					map.put("grouplist", dt.getDictionaryValue());
				}else if(AppConstant.DATA_50001.equals(dt.getDictionaryCode())){
					map.put("optionist", StringUtils.isNotBlank(dt.getDictionaryValue())?dt.getDictionaryValue():null);
				}else if(AppConstant.DATA_50052.equals(dt.getDictionaryCode())){					
					map.put("moneys", StringUtils.isNotBlank(dt.getDictionaryValue())?dt.getDictionaryValue():null);
				}else if(AppConstant.DATA_50053.equals(dt.getDictionaryCode())){			
					map.put("followProcess", StringUtils.isNotBlank(dt.getDictionaryValue())?dt.getDictionaryValue():null);
				}else{
					map.put("value", dt.getDictionaryValue());
				}				
				map.put("open",dt.getIsOpen());
				map.put("modfiyAcc",dt.getModifierAcc());
				map.put("modfiyDate", DateUtil.format(new Date(),"yyyy-MM-dd hh:mm:ss"));
				map.put("name",dt.getDictionaryName());
				map.put("notes",dt.getDictionaryValueNotes());
				maps.add(map);
			}
			// 新增用户操作日志
			LogBean logBean = new LogBean();
			logBean.setOrgId(shiroUser.getOrgId());
			logBean.setUserAcc(shiroUser.getAccount());
			logBean.setUserName(shiroUser.getName());
			logBean.setModuleId(AppConstant.Module_id13);
			logBean.setModuleName(AppConstant.Module_Name13);
			logBean.setOperateId(AppConstant.Operate_id76);
			logBean.setOperateName(AppConstant.Operate_Name76);
			logBean.setData(JsonUtil.getJsonString(maps));
			logCustInfoService.addTableStoreLog(logBean, null);
			return AppConstant.RESULT_SUCCESS;
		}catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	  /***************************【慧营销4.0 结束】******************************************/
	
	// 跟进设置，销售进程列表
	@RequestMapping("/getDicProcessList")
	public String getDicProcessList(HttpServletRequest request){
		try{
			ShiroUser shiroUser = ShiroUtil.getShiroUser();
			String jsonStr = request.getParameter("jsonStr");
			String optionValue = request.getParameter("optionValue");
			if(StringUtils.isNotBlank(jsonStr)){				
				jsonStr= URLDecoder.decode(jsonStr, IContants.CHAR_ENCODING);
				jsonStr = new String(CodeUtils.base64Decode(jsonStr), IContants.CHAR_ENCODING);
				request.setAttribute("dicProcessList", JsonUtil.getListJson(jsonStr, DictionProcessJsonDto.class));
			}else{
				request.setAttribute("dicProcessList", cachedService.getDicProcessList(shiroUser.getOrgId(),optionValue));
			}		
			request.setAttribute("optionValue", optionValue);
			return "/tsm/option/salesmanage_idialog";
		}catch(Exception e){
			logger.error("getDicProcessList 异常！", e);
			return null;
		}
		
	}
	
	public static void main(String[] args) {
		String str = "[{\"optionId\":\"f5860f3ae9bd42ceb619a3bc3afc87aa\",\"optionName\":\"Fc测试1ssss毒贩夫妇付付付付付顶顶\",\"optionValue\":\"\"},{\"optionId\":\"adf2a746310d429ebeed82444f4572ba\",\"optionName\":\"E深入沟通需求清晰\",\"optionValue\":\"\"},{\"optionId\":\"46f4b73f56f745afa5de8eb92c653b9b\",\"optionName\":\"D全面了解产品\",\"optionValue\":\"\"},{\"optionId\":\"75853e56b7524668a65129cf50e20369\",\"optionName\":\"C会客面谈\",\"optionValue\":\"\"},{\"optionId\":\"e20263ba30824474acd118925d3693da\",\"optionName\":\"B无疑义准备签约\",\"optionValue\":\"\"},{\"optionId\":\"a5a20023ad2644369be7bde644373e44\",\"optionName\":\"A成功签单\",\"optionValue\":\"\"},{\"optionId\":\"137c400e6f2c4e5096168c39eeb91a6a\",\"optionName\":\"测试测试\",\"optionValue\":\"\"}]";
		System.out.println(CodeUtils.base64Encode(str).replaceAll("\r\n", ""));
	}
	 

	
}
