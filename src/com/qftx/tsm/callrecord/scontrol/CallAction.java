package com.qftx.tsm.callrecord.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.IContants;
import com.qftx.common.util.MathUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.callrecord.bean.DtoRestStateBean;
import com.qftx.tsm.callrecord.dto.CallPlayDto;
import com.qftx.tsm.callrecord.dto.CustResInfoDto;
import com.qftx.tsm.callrecord.service.CallService;
import com.qftx.tsm.callrecord.service.QueryCallService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/call")
public class CallAction {
    private Logger logger = Logger.getLogger(CallAction.class);

    @Autowired
    private CallService callService;
    
    @Autowired
    private QueryCallService queryCallService;
    
    @Autowired
    private CachedService cachedService;
    @Autowired private TsmGroupShareinfoService tsmGroupShareinfoService;
    
    /** 邮件发送异步查询 资源列表  */
    @RequestMapping("/custInfos")
    public String custInfos(HttpServletRequest request, HttpServletResponse response,CustResInfoDto entity) throws Exception {
	      try{
	    	  if(StringUtils.isNotBlank(entity.getQueryText())){
	    		  ShiroUser user = ShiroUtil.getShiroUser();
		    	  entity.setOrgId(user.getOrgId());
		    	  entity.setOwnerAcc(user.getAccount());
		    	  entity.setState(user.getIsState());
		    	  // 管理者查询
					if(user.getIssys() != null && user.getIssys()==1){
						// 所有者查询方式 1-全部 2-只看自己 3-选中查询
						List<String>list = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(),user.getAccount());
						if(list!=null && list.size()>0){
							StringBuffer sb = new StringBuffer();
							for(String str: list){
								sb.append(str);
								sb.append(",");
							}
							if (!list.contains(user.getAccount())) {
								list.add(user.getAccount());
							}
							if(sb.length()>0){
								sb = sb.deleteCharAt(sb.length() - 1);
							}
							entity.setOwnerAccs(list);
						}else {
							list.add(user.getAccount());
							entity.setOwnerAccs(list);
						}									
					}	
		    	  List<CustResInfoDto> list = callService.getCustByCallListPage(entity);
		    	  request.setAttribute("list", list);
		    	  request.setAttribute("item", entity);
		    	  setIsReplaceWord(request,user);
		    	  setCustomFiled(user,request);  
	    	  }
	    	
	      }catch(Exception e){
	    	  throw new SysRunException(e);
	      }
	      return "/call/idialog/email_send_search";
    }

    /** 短信发送异步查询 资源列表  */
    @RequestMapping("/phone/custInfos")
    public String phoneCustInfos(HttpServletRequest request, HttpServletResponse response,CustResInfoDto entity) throws Exception {
	      try{
	    	  ShiroUser user = ShiroUtil.getShiroUser();
	    	  entity.setOrgId(user.getOrgId());
	    	  entity.setOwnerAcc(user.getAccount());
	    	  entity.setState(user.getIsState());
	    	  if(StringUtils.isNotBlank(entity.getQueryText())){
	    		  List<CustResInfoDto> list = callService.getCustByCallSmsListPage(entity);
		    	  request.setAttribute("list", list);
	    	  } 	 
	    	  request.setAttribute("item", entity);
	    	  setIsReplaceWord(request,user);
	      }catch(Exception e){
	    	  throw new SysRunException(e);
	      }
	      return "/call/idialog/sms_send_search";
    }

    /** 播放列表 */
	@RequestMapping("/callPlay")
	public String callPlay(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			setIsReplaceWord(request,user);
			List<CallPlayDto> callList = null;
			String timeElngth = request.getParameter("timeElngth");
			String recordUrl = request.getParameter("recordUrl");	
			String callId = request.getParameter("callId");
			String callName = request.getParameter("callName");
			String callNum = request.getParameter("callNum");
			if(StringUtils.isNotBlank(callId)&&StringUtils.isNotBlank(timeElngth)&&StringUtils.isNotBlank(recordUrl)){
				callName = URLDecoder.decode(callName, IContants.CHAR_ENCODING);
				recordUrl = URLDecoder.decode(recordUrl, IContants.CHAR_ENCODING);
				/**  
				 * 播放器列表 缓存设置/获取
				 * */
				try{
					callList = (List<CallPlayDto>)cachedService.getCallPlay(user.getOrgId(), user.getAccount());
					if(callList==null){
						callList=new LinkedList<CallPlayDto>();
					}else{
						int i = 0;
						for(CallPlayDto r : callList){
							// 如果播放列表中已存在，则直接跳转
							if(r.getCallId().equals(callId)){
								request.setAttribute("callList", callList);							
								request.setAttribute("listSize", i);// 第几条
								return "/call/callPlay";
							}
							i++;
						}
					}	
				}catch(Exception e){
					callList=new LinkedList<CallPlayDto>();
				}
				CallPlayDto rec = new CallPlayDto();
				rec.setCallId(callId);
				rec.setCustName(callName);	
				rec.setCalledNum(callNum);
				rec.setCallTimeShow(MathUtil.secondFormat1(Integer.parseInt(timeElngth)));
				rec.setRecordUrl(recordUrl);
				callList.add(0,rec);
				cachedService.setCallPlay(user.getOrgId(), user.getAccount(), callList);			
			}
			request.setAttribute("callList", callList);
			request.setAttribute("listSize", "0");
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/call/callPlay";
	}	
	
	/** 重载 播放列表 */
	@RequestMapping("/reloadCallPlay")
	public String reloadCallPlay(HttpServletRequest request) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			setIsReplaceWord(request,user);
			List<CallPlayDto> callList = (List<CallPlayDto>)cachedService.getCallPlay(user.getOrgId(), user.getAccount());
			if(callList!=null){						
						request.setAttribute("callList", callList);
						request.setAttribute("listSize", callList.size()-1);
				}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "/call/callPlay";
	}	
	
	/** 删除 选中播放列表  */
	@RequestMapping("/delPlay")
	@ResponseBody
	public String delPlay(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			List<CallPlayDto> callList = (List<CallPlayDto>)cachedService.getCallPlay(user.getOrgId(), user.getAccount());
			if(callList != null){
				String ids=request.getParameter("ids");
				String [] idArr=ids.split(",");
				Map<String,String>map = new HashMap<String, String>();
				for(String id:idArr){
					 map.put(id, id);
				}
				for(int i=0;i<callList.size();i++){
					if(map.get(callList.get(i).getCallId())!= null){
						callList.remove(callList.get(i));			
					}
				}
				cachedService.setCallPlay(user.getOrgId(), user.getAccount(),callList);
				return AppConstant.RESULT_SUCCESS;
			}
			return AppConstant.RESULT_FAILURE;
		}catch(Exception e){
			throw new SysRunException(e);
		}
	}
	
	/** 清空播放列表 */
	@RequestMapping("/clearPlay")
	@ResponseBody
	public String clearPlay(HttpServletRequest request){
		try{
			ShiroUser user = ShiroUtil.getShiroUser();
			cachedService.removeCallPlay(user.getOrgId(),user.getAccount());
			return AppConstant.RESULT_SUCCESS;		
		}catch(Exception e){
			throw new SysRunException(e);
		}
	}
	
	
	/** 校验共有客户和公海客户通话次数*/
	@RequestMapping("/queryCall")
	@ResponseBody
	public DtoRestStateBean queryCall(HttpServletRequest request,String telPhone){
		try{
			logger.debug("进入queryCall，telPhone="+telPhone);
			ShiroUser user = ShiroUtil.getShiroUser();
			DtoRestStateBean bean=queryCallService.queryAnnoy(user.getOrgId(), user.getAccount(), telPhone);
			logger.debug("查询完毕queryCall，DtoRestStateBean="+bean.getCode());
			return bean;		
		}catch(Exception e){
			throw new SysRunException(e);
		}
	}
	
	
	/**
	 * 设置是否开启用*替换电话号码中间四位
	 */
	private void setIsReplaceWord(HttpServletRequest request,ShiroUser user) {
		// 查询缓存
		List<DataDictionaryBean> dataMap=cachedService.getDirList(AppConstant.DATA24, user.getOrgId());
		Integer idReplaceWord = 0;
		if (!dataMap.isEmpty() && dataMap.get(0) != null && !cachedService.judgeHideWhiteList(user.getOrgId(), user.getAccount())) {
			idReplaceWord = new Integer(dataMap.get(0).getDictionaryValue());
		}
		request.setAttribute("idReplaceWord", idReplaceWord);
	}
	
	private void setCustomFiled(ShiroUser user,HttpServletRequest request){
		List<CustFieldSet> fieldSets = null;
		if (user.getIsState() == 1) {// 企业资源
			fieldSets = cachedService.getComFiledSet(user.getOrgId());
		}else{
			fieldSets = cachedService.getPersonFiledSet(user.getOrgId());
		}
		Map<String,String> filedMap = new HashMap<String, String>();
		for(CustFieldSet filed : fieldSets){
			filedMap.put(filed.getFieldCode(), filed.getFieldName());
		}
		request.setAttribute("filedMap", filedMap);
	}
}
