package com.qftx.tsm.phonechoose.scontrol;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.qftx.base.auth.service.OrgGroupUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.HttpUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.ResultObject;
import com.qftx.tsm.bill.util.HDUtils;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.phonechoose.bean.PhoneChooseDetailBean;
import com.qftx.tsm.phonechoose.dto.PhoneChooseTaskBeanDto;
import com.qftx.tsm.phonechoose.dto.PhoneInfoRequest;
import com.qftx.tsm.phonechoose.dto.PhoneInfoResponse;
import com.qftx.tsm.phonechoose.dto.QueryPhoneStatusRequest;
import com.qftx.tsm.phonechoose.dto.QueryPhoneStatusResponse;
import com.qftx.tsm.phonechoose.service.PhoneChooseDetailService;
import com.qftx.tsm.phonechoose.service.PhoneChooseTaskService;

/**
 * 号码筛查逻辑
 * 
 * @author chenhm
 *
 */
@Controller
public class PhoneChooseAction {
	private static final Logger logger = Logger.getLogger(PhoneChooseAction.class);
	
    private static final String SUCCESS_CODE = "_SUCCESS";
    @Autowired
    private UserService userService;
    @Autowired
    private ResCustInfoService resCustInfoService;
    @Autowired
	private OrgGroupUserService orgGroupUserService;
	
	// 资源管理 - 待分配资源
	public static final String MODULE_ID_106 = "106";
	// 我的客户 - 资源
	public static final String MODULE_ID_1001 = "1001";
	
	public static final Map<String, String> VALIDATE_MODULE_MAP = new HashMap<String, String>();
	static {
		VALIDATE_MODULE_MAP.put(MODULE_ID_106, "资源管理 - 待分配资源");
		VALIDATE_MODULE_MAP.put(MODULE_ID_1001, "我的客户 - 资源");
	}

	@Autowired
	PhoneChooseTaskService phoneChooseTaskService;

	@Autowired
	PhoneChooseDetailService phoneChooseDetailService;

	@Autowired
	ResCustInfoService custInfoService;

	@Autowired
	private CachedService cachedService;
	
	@Value("#{config['query_phone_status_url']}")
	private String queryPhoneStatusUrl;

	/**
	 * @param response
	 * @param request
	 * @param orgId
	 *            机构ID
	 * @param isState
	 *            1企业客户, 0个人客户
	 * @param account
	 *            账号
	 * @param groupId
	 *            部门ID
	 * @param groupName
	 *            部门名称
	 * @param module
	 *            模块名称 106资源管理-待分配资源	1001我的客户-资源
	 * @param resGroupId
	 *            资源分组ID
	 * @param psCount
	 *            筛查数量
	 */
//	@RequestMapping("/phone/choose")
//	@ResponseBody
//	public ResultObject phoneChoose(String orgId, String isState, String account, String groupId, String groupName,
//			String module, String resGroupId, Integer psCount) {
//		ResultObject result = new ResultObject(ResultCode.SUCCESS.getStatus(), null, null);
//		
//		try {
//			if (!VALIDATE_MODULE_MAP.containsKey(module)) {
//				result.setResultCode(ResultCode.FAILURE.getStatus());
//				result.setResultDesc("参数module值不合法!");
//				return result;
//			}
//			
//			// 通过缓存控制并发
//			String cacheKey = String.format(PhoneChooseTaskBean.TASK_CACHE_KEY, orgId, resGroupId);
//			
//			if (null != CachedUtil.getInstance().get(cacheKey)) {
//				result.setResultCode(ResultCode.FAILURE.getStatus());
//				result.setResultDesc("还有任务在进行号码筛查, 请稍后再试!");
//				return result;
//			} else {
//				CachedUtil.getInstance().setnx(cacheKey, cacheKey, 60);
//			}
//			
//			ShiroUser user = new ShiroUser();
//			user.setOrgId(orgId);
//			user.setIsState(Integer.parseInt(isState));
//			user.setAccount(account);
//			user.setGroupId(groupId);
//			user.setGroupName(groupName);
//
//			logger.info(
//					String.format("开始进行号码筛查：机构[%s]，账号[%s]，资源分组[%s], 筛查数量[%s]", orgId, account, resGroupId, psCount));
//			int unFinishTaskCount = phoneChooseTaskService.findUnFinishTaskCount(orgId, resGroupId);
//			if (unFinishTaskCount > 0) {
//				result.setResultCode(ResultCode.FAILURE.getStatus());
//				result.setResultDesc("还有任务在进行号码筛查, 请稍后再试!");
//				return result;
//			}
//
//			String noPhoneMsg = String.format("没有可筛查的号码：机构[%s]，账号[%s]，资源分组[%s], 筛查数量[%s]", orgId, account, resGroupId,
//					psCount);
//			
//			String taskId = phoneChooseTaskService.createPhoneChooseTask(user, resGroupId, psCount);
//
//			int tempCount = 0;
//			while (true) {
//				List<ResCustInfoBean> custInfos = custInfoService.findCustsByRand(module, account, orgId, resGroupId);
//				if (null == custInfos || custInfos.isEmpty()) {
//					logger.error(noPhoneMsg);
//					break;
//				}
//
//				List<PhoneChooseDetailBean> detailInfos = new ArrayList<PhoneChooseDetailBean>();
//				for (ResCustInfoBean custInfo : custInfos) {
//					if (null != custInfo) {
//						// 判断资源是否已经筛查
//						Integer cn = phoneChooseDetailService.findCountByResCustId(orgId, custInfo.getResCustId());
//						if (cn == 0) {
//							PhoneChooseDetailBean bean = phoneChooseDetailService.insert(taskId, user, custInfo);
//							detailInfos.add(bean);
//
//							
//							tempCount = tempCount + 1;
//							if (tempCount >= psCount) {
//								break;
//							}
//						}
//					}
//				}
//				
//				// TODO 需要调用接口，查询号码状态
//				QueryPhoneStatusRequest request = toQueryPhoneStatusRequest(taskId, detailInfos);
//				if (null != request) {
//					String resp = HttpUtil.doPostJSON(queryPhoneStatusUrl, JSON.toJSONString(request));
//				}
//
//				if (tempCount >= psCount) {
//					break;
//				}
//			}
//			
//			if (0 == tempCount) {
//				phoneChooseTaskService.updateTask(account, taskId, tempCount, PhoneChooseTaskBean.STATUS_END);
//				logger.info(noPhoneMsg);
//			}
//			
//			CachedUtil.getInstance().delete(cacheKey);
//
//		} catch (Exception e) {
//			logger.error("任务创建发生异常", e);
//		}
//
//		return result;
//	}
	
	
	/**
	 *
	 * @param request
	 */
	@RequestMapping("/phone/toChoose")
	public String toChoose(HttpServletRequest request) {
		//keyWord:1:资源;2：待分配
		ShiroUser user = ShiroUtil.getShiroUser();
		String keyWord=request.getParameter("keyWord");
		
//        if (StringUtils.isBlank(flag)) {//打开菜单时同步一次
            Map<String, Object> requestMap = HDUtils.splitRequestParamOfUnitLens(user.getOrgId());
            String modelName = "【查单位用户余额请求】";
            String responeStr = HDUtils.sendMsgToHD(requestMap, modelName);
            if (!responeStr.equals("9008") && !responeStr.equals("9999")) {
                Map<String, Object> return_map = JSONObject.parseObject(responeStr, Map.class);
                if (return_map.get("code").equals(SUCCESS_CODE)) {
                    List<Map<String, Object>> balances = (List<Map<String, Object>>) return_map.get("acctList");
                    userService.updateUserLensByAcc(balances);
                } else {
                    logger.error("*********" + modelName + "处理失败，返回结果（code:" + return_map.get("code") + "，desc:" + return_map.get("desc") + "）");
                }
            }
//        }
        User tmpUser = userService.getByAccount(user.getAccount());
		//蜂豆
        request.setAttribute("fd", tmpUser.getCommunicationsTimes() == null ? "0" : tmpUser.getCommunicationsTimes());

        Double fd=tmpUser.getCommunicationsTimes() == null ?(double)0 : tmpUser.getCommunicationsTimes();

		String fdChoosePhone=(ConfigInfoUtils.getStringValue("fd_choosePhone") ==null? "2" :ConfigInfoUtils.getStringValue("fd_choosePhone"));
		double zhi=(double) (fd/Integer.valueOf(fdChoosePhone ==""?"2" :fdChoosePhone));
		int allChooseCount = (int) Math.floor(zhi);
		//最大可筛查数
		request.setAttribute("allChooseCount", allChooseCount);
		request.setAttribute("keyWord", keyWord);
		return "phonechoose/toPhoneChoose";
	}
	
	/**
	 *
	 * @param request
	 */
	@RequestMapping("/phone/getNoChooseCount")
	@ResponseBody
	public Map<String,Object> getNoChooseCount(HttpServletRequest request) {
		//keyWord:1:资源;2：待分配
		ShiroUser user = ShiroUtil.getShiroUser();
		Map<String,Object> map=new HashMap<String,Object>();
		try{
		String groupId=request.getParameter("groupId");
		String keyWord=request.getParameter("keyWord");
		ResCustInfoDto custInfoDto=new ResCustInfoDto();
		custInfoDto.setOrgId(user.getOrgId());
		custInfoDto.setGroupId(groupId);
		custInfoDto.setKeyWord(keyWord ==null ? "" : keyWord);
		if(keyWord!=null && "1".equals(keyWord)) 		custInfoDto.setOwnerAcc(user.getAccount());
		if(keyWord!=null && "2".equals(keyWord)){
			Map<String, String> shareMap = new HashMap<String, String>();
			shareMap.put("account", user.getAccount());
			shareMap.put("orgId", user.getOrgId());
			List<String> shareGroupList = cachedService.getShareGroupId(shareMap);
				List<String> groups = new ArrayList<String>();
				if (user.getIssys() != null && user.getIssys() == 1) {
						// 查询 所选部门成员列表
							groups = shareGroupList;
				}
				custInfoDto.setDeptIds(groups);
		}
		Integer noChooseCount= resCustInfoService.findNoChooseCount(custInfoDto);
		map.put("code", "0");
		map.put("noChooseCount", noChooseCount);
		map.put("msg", "查询未筛查数成功");
		//未筛查条数
		}catch(Exception e){
			e.printStackTrace();
    		map.put("code", "-1");
    		map.put("msg", "查询未筛查数失败");
    		return map;

		}
		return map;
	}
	
	/**
	 * @param
	 * @param request
	 */
	@RequestMapping("/phone/saveChoose")
	@ResponseBody
	public Map<String,Object> saveChoose(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		try{
		ShiroUser user = ShiroUtil.getShiroUser();
		String groupId=request.getParameter("groupId");
		String keyWord=request.getParameter("keyWord")==null?"":request.getParameter("keyWord");
		String psCount=request.getParameter("chooseCount")==null ?"0":request.getParameter("chooseCount");
		int chooseCount =Integer.valueOf(ConfigInfoUtils.getStringValue("chooseCount") == null ? "0" :ConfigInfoUtils.getStringValue("chooseCount"));
        int count=Integer.valueOf(psCount =="" ?"0":psCount);
			if(count<1){
				map.put("code", "-1");
				map.put("msg", "筛查条数必填");
				return map;
			}
			if(count>chooseCount){
				map.put("code", "-1");
				map.put("msg", String.format("单次最大可筛查资源数量为%s", chooseCount));
				return map;
			}

		Map<String,Object> sendMap=new HashMap<String,Object>();
		sendMap.put("orgId", user.getOrgId());
		sendMap.put("isState", user.getIsState());
		sendMap.put("account", user.getAccount());
		sendMap.put("userName", user.getName());

		if("1".equals(keyWord)) 	sendMap.put("module", MODULE_ID_1001);//我的资源
		if("2".equals(keyWord))		sendMap.put("module", MODULE_ID_106);//待分配

		sendMap.put("resGroupId", groupId);
		sendMap.put("psCount", psCount);
		
        String url = ConfigInfoUtils.getStringValue("phone_choose_url");
        Map<String,String> maps=new HashMap<String,String>();
        String sendUrl=url+"?orgId="+user.getOrgId()+"&isState="+user.getIsState()+"&account="+user.getAccount()+"&userName="+user.getName()+"&module="+sendMap.get("module")+"&resGroupId="+groupId+"&psCount="+psCount+"&issys="+user.getIssys();
        logger.info("发送筛选参数："+sendUrl);
        String returnMsg=HttpUtil.doGet(sendUrl, maps);
        logger.info("筛选结果："+returnMsg);
        ResultObject res=JSONObject.parseObject(returnMsg, ResultObject.class);
        if(res!=null){
        	if(res.getResultCode()==1){
        		map.put("code", "0");
        		map.put("msg", "提交成功");
        	}else{
        		map.put("code", "-1");
        		map.put("msg", res.getResultDesc());
        	}

        }else{
    		map.put("code", "-1");
    		map.put("msg", "筛查失败");
        }

		}catch(Exception e){
			e.printStackTrace();
			map.put("code", "-1");
			map.put("msg", "筛查失败");
			return map;
		}
		return map;
	}
	
	/**
	 * 同步加载筛查结果
	 */
	@RequestMapping("/phone/chooseResult")
	public String chooseResult(ModelMap model, PhoneChooseTaskBeanDto dto,Integer moduleId) {
		ShiroUser user = ShiroUtil.getUser();
		if(moduleId!=null){
			dto.setModuleId(moduleId);
		}
		List<PhoneChooseTaskBeanDto> beanlist = phoneChooseTaskService.findScreenResult(user.getOrgId(), dto);
		Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
		for(PhoneChooseTaskBeanDto resultDto : beanlist){
			resultDto.setImpTime(DateUtil.formatDate(resultDto.getInputDate(), DateUtil.Data_ALL));
			resultDto.setInputAcc(nameMap.get(resultDto.getInputAcc()) == null ? resultDto.getInputAcc() : nameMap.get(resultDto.getInputAcc()));
		}
		model.put("results", beanlist);
		model.put("moduleId",moduleId);
 		return "phonechoose/importPhoneChooseResult";
	}
	/**
	 * 异步加载筛查结果
	 * @param request
	 * @param dto
	 * @return
	 */
	@RequestMapping("/phone/chooseResult/Json")
	@ResponseBody
	public Map<String, Object> chooseResultJson(HttpServletRequest request, PhoneChooseTaskBeanDto dto,Integer moduleId) {
		Map<String, Object> map= new HashMap<String, Object>();
		try{
			ShiroUser user = ShiroUtil.getUser();		
			dto.setInputAcc(user.getAccount());
			if(moduleId!=null){
				dto.setModuleId(moduleId);
			}
			List<PhoneChooseTaskBeanDto> beanlist = phoneChooseTaskService.findScreenResult(user.getOrgId(), dto);
			Map<String,String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			if(beanlist != null && beanlist.size() >0){
				for(PhoneChooseTaskBeanDto resultDto : beanlist){
					resultDto.setImpTime(DateUtil.formatDate(resultDto.getInputDate(), DateUtil.Data_ALL));
					resultDto.setInputAcc(nameMap.get(resultDto.getInputAcc()) == null ? resultDto.getInputAcc() : nameMap.get(resultDto.getInputAcc()));
				}
			}			
			map.put("item", dto);
			map.put("results", beanlist);
		}catch(Exception e){
			logger.error("异步加载筛查结果异常！", e);
		}	
		return map;
	}
	
	
	/**
	 * 构造请求对象
	 * 
	 * @param taskId
	 * @param custInfos
	 * @return
	 */
	public static QueryPhoneStatusRequest toQueryPhoneStatusRequest(String taskId, List<PhoneChooseDetailBean> detailInfos) {
		QueryPhoneStatusRequest request = null;
		if (null != detailInfos && !detailInfos.isEmpty()) {

			List<PhoneInfoRequest> phones = new ArrayList<PhoneInfoRequest>();
			for (PhoneChooseDetailBean bean : detailInfos) {
				if (null != bean && StringUtils.isNotEmpty(bean.getMobilephone())) {
					PhoneInfoRequest phoneInfo = new PhoneInfoRequest();
					phoneInfo.setDetailId(bean.getId());
					phoneInfo.setPhone(bean.getMobilephone());

					phones.add(phoneInfo);
				}
			}

			if (!phones.isEmpty()) {
				request = new QueryPhoneStatusRequest();
				request.setTaskId(taskId);
				request.setPhones(phones);
			}
		}

		return request;
	}
	
	/**
	 * 处理响应结果,将接口中的号码状态更新到数据库中
	 * 
	 * @param account
	 * @param respJson
	 */
	private void processResponse(String account, String respJson) {
		QueryPhoneStatusResponse response = JSON.parseObject(respJson, QueryPhoneStatusResponse.class);
		if (null != response) {
			List<PhoneInfoResponse> phones = response.getPhones();
			if (null != phones && !phones.isEmpty()) {
				for (PhoneInfoResponse phone : phones) {
					if (null != phone) {
						byte status = Byte.parseByte(phone.getStatus());
						phoneChooseDetailService.updateDetail(account, phone.getDetailId(), status);
					}
				}
			}
		}
	}
	
	/**
	 * 查询出 相关机构 销售管理 - 特殊设置 - 资源筛查设置
	 * 
//	 * @param orgId
	 * @return
	 */
//	private String getPhoneChooseDictionary(String orgId) {
//		List<DataDictionaryBean> dicList = cachedService.getDictionary(orgId);
//		DataDictionaryBean dic = CollectionUtil.get(dicList, "dictionaryCode", AppConstant.DATA_50058);
//		if (null != dic) {
//			return dic.getDictionaryValue();
//		}
//		
//		return null;
//	}
	
	public static void main(String[] args) {
		double countsize = 10086.16;
		double zhi=(double) (countsize/4);
		int ye = (int) Math.floor(zhi);
		System.out.print(ye);
	}
	
}
