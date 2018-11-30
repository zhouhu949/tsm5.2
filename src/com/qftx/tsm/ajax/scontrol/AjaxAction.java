package com.qftx.tsm.ajax.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.service.ComResourceService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.service.OptionService;
import com.qftx.tsm.sys.bean.CustFieldSet;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/ajax")
public class AjaxAction extends BaseAction {

	private static final long serialVersionUID = -7585597863755809943L;
	private static Logger logger = Logger.getLogger(AjaxAction.class);
	@Resource
	private OptionService optionService;
	@Resource
	private ComResourceService resCustService;
	@Resource
	private CachedService cachedService;

	/**
	 * Ajax公共验证方法
	 * 
	 * @作者 徐承恩
	 * @创建时间 2014年3月27日 下午3:34:41
	 */
	@ResponseBody
	@RequestMapping("/commonCheck")
	public String commonCheck(HttpServletRequest request) {
		String result = AppConstant.RESULT_FAILURE;
		try {
			// 待验证元素值
			String chkVal = request.getParameter("chkVal") == null ? "" : request.getParameter("chkVal");
			// 1-单位名称;2-单位简称;3-用户帐号;4-单位成员账号;5-通信号码;6-通信记录中登录号码
			String chkType = request.getParameter("chkType") == null ? "" : request.getParameter("chkType");
			String resCustId = request.getParameter("resCustId") == null ? null : request.getParameter("resCustId");
			String isTp = request.getParameter("tanpin") == null ? "" : request.getParameter("tanpin");
			int chkCount = 0;
			ShiroUser user = ShiroUtil.getShiroUser();
			String orgId = user.getOrgId();
			Integer isState = user.getIsState();
			String jsonStr = "";
			if ("1".equals(chkType)) {
				chkCount = checkOptionNameOne(chkVal, AppConstant.RECORD_CODE);// 验证录音示范分类
			} else if ("2".equals(chkType)) {
				chkCount = checkOptionNameOne(chkVal, AppConstant.SALES_TYPE_FIVE);// 销售产品维护
			} else if ("3".equals(chkType)) {
				chkCount = checkOptionNameOne(chkVal, AppConstant.SALES_TYPE_ONE);// 销售进程设计
			} else if ("4".equals(chkType)) {
				chkCount = checkOptionNameOne(chkVal, AppConstant.SALES_TYPE_TWO);// 目标客户分类
			} else if ("5".equals(chkType)) {
				// chkCount = checkOptionNameOne(chkVal,
				// AppConstant.SALES_TYPE_FOUR);// 反馈信息维护
			} else if ("6".equals(chkType)) {
				//手机号抹零处理
				chkVal=StringUtils.toCheckPhone(chkVal);
				String validatetype = request.getParameter("validatetype") == null ? "1" : request.getParameter("validatetype");
				Map<String, String> map = new HashMap<String, String>();
				map.put("code", "");
				map.put("orgId", ShiroUtil.getShiroUser().getOrgId());
				// chkVal = formatPhone(chkVal, request, bean);
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("orgId", orgId);
				param.put("validateType", validatetype);
				param.put("phone", chkVal);
				param.put("resCustId", resCustId);
				param.put("isState", isState);
				if ("2".equals(validatetype) || "3".equals(validatetype) || "4".equals(validatetype)) {
					List<ResCustInfoBean> lists = resCustService.getDPPhone(param);
					if (lists.size() > 0) {
						chkCount = 1;
						ResCustInfoBean cust = getResCust(lists);
						String ownerAcc = org.apache.commons.lang3.StringUtils.substringBeforeLast(cust.getOwnerAcc(), "_F"); 
						String ownerName = getUserName(orgId, ownerAcc);
						cust.setOwnerName(ownerName);
						jsonStr = getJsonStr(cust, "号码--" + ("1".equals(getReplaceWord(user.getOrgId(),user.getAccount())) ? replaceWord(chkVal) : chkVal), chkVal, request, lists.size(), chkType,isTp);// 返回去重详情信息
					}
				} else {
					chkCount = 0;
				}
			} else if ("7".equals(chkType)) { // 单位名称去重
				String validatetype = request.getParameter("validatetype") == null ? "0" : request.getParameter("validatetype");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("orgId", orgId);
				param.put("validateType", validatetype);
				param.put("company", chkVal);
				param.put("resCustId", resCustId);
				param.put("isState", isState);
				if ("1".equals(validatetype) || "2".equals(validatetype) || "3".equals(validatetype)) {
					List<ResCustInfoBean> lists1 = resCustService.getDPUnit(param);
					if (lists1.size() > 0) {

						chkCount = 1;
						ResCustInfoBean cust = getResCust(lists1);
						String ownerAcc = org.apache.commons.lang3.StringUtils.substringBeforeLast(cust.getOwnerAcc(), "_F"); 
						String ownerName = getUserName(orgId, ownerAcc);
						cust.setOwnerName(ownerName);
						jsonStr = getJsonStr(cust, getFieldVal(isState == 1 ? "name" : "company") + chkVal, chkVal, request, lists1.size(), chkType,isTp);// 返回去重详情信息
					}
				} else {
					chkCount = 0;
				}
			} else if ("8".equals(chkType)) { // 单位主页去重
				String validatetype = request.getParameter("validatetype") == null ? "0" : request.getParameter("validatetype");
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("orgId", orgId);
				param.put("validateType", validatetype);
				param.put("unitHome", chkVal);
				param.put("resCustId", resCustId);
				if ("1".equals(validatetype) || "2".equals(validatetype) || "3".equals(validatetype)) {
					List<ResCustInfoBean> lists2 = resCustService.getDPUnitHome(param);
					if (lists2.size() > 0) {
						chkCount = 1;
						ResCustInfoBean cust = getResCust(lists2);
						String ownerAcc = org.apache.commons.lang3.StringUtils.substringBeforeLast(cust.getOwnerAcc(), "_F"); 
						String ownerName = getUserName(orgId, ownerAcc);
						cust.setOwnerName(ownerName);
						jsonStr = getJsonStr(cust, getFieldVal("unithome") + chkVal, chkVal, request, lists2.size(), chkType,isTp);// 返回去重详情信息
					}
				} else {
					chkCount = 0;
				}
			} else {
				chkCount = 1;
			}

			if (chkCount == 0) {
				result = AppConstant.RESULT_SUCCESS;
			} else {
				result = jsonStr;
			}
			return result;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	/**
	 * 按 签约，意向，资源，公海 先后返回
	 * 
	 * @return
	 * @create 2014-10-15 下午01:25:39 WUWEI
	 * @history
	 */
	private ResCustInfoBean getResCust(List<ResCustInfoBean> list) {
		ResCustInfoBean cust = null;
		for (ResCustInfoBean resCust : list) {
			if (resCust.getStatus() == 6) {
				cust = resCust;
				break;
			}
		}
		if (cust == null) {
			for (ResCustInfoBean resCust : list) {
				if (resCust.getStatus() == 3) {
					cust = resCust;
					break;
				}
			}
		}
		if (cust == null) {
			for (ResCustInfoBean resCust : list) {
				if (resCust.getStatus() == 2) {
					cust = resCust;
					break;
				}
			}
		}

		if (cust == null) {
			for (ResCustInfoBean resCust : list) {
				if (resCust.getStatus() == 1) {
					cust = resCust;
					break;
				}
			}
		}
		if (cust == null) {
			for (ResCustInfoBean resCust : list) {
				if (resCust.getStatus() == 4 || resCust.getStatus() == 5) {
					cust = resCust;
					break;
				}
			}
		}
		if (cust == null) {
			for (ResCustInfoBean resCust : list) {
				if (resCust.getStatus() == 7 || resCust.getStatus() == 8) {
					cust = resCust;
					break;
				}
			}
		}
		return cust;
	}

	/**
	 * 验证数据项名称
	 * 
	 * @作者 徐承恩
	 * @创建时间 2014年3月27日 下午3:40:51
	 * @return
	 */
	private int checkOptionNameOne(String val, String code) {
		String orgId = ShiroUtil.getShiroUser().getOrgId();
		OptionBean temp_option = new OptionBean();
		temp_option.setOrgId(orgId);
		temp_option.setItemCode(code);
		temp_option.setOptionName(val);
		List<OptionBean> temp_options = optionService.getListByCondtion(temp_option);
		return temp_options.size();
	}

	/**
	 * 返回去重详情信息
	 * 
	 * @param resCust
	 * @param chkVal
	 * @return
	 */
	private String getJsonStr(ResCustInfoBean resCust, String chkVal, String orgValue, HttpServletRequest request, int num, String type,String isTp) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("orgId", ShiroUtil.getShiroUser().getOrgId());
		map.put("custId", resCust.getResCustId());
		String resultStr = "";
		String optionName = resCustService.getOptionName(map);
		if (StringUtils.isEmpty(optionName)) {
			resultStr = "。";
		} else {
			resultStr = "，进程为：" + optionName + ";";
		}
		if (resCust != null) {
			if (resCust.getType() == 1 && resCust.getStatus() == 1) {
			  	String huiqu ="<a id='addCustQuHui' href='#' style='text-indent:0;color:blue;' onclick=\"addCustQuHui( '" + num + "', '"
						+ type + "', '" + orgValue + "', '" + chkVal + "', '" + resCust.getStatus() + "')\">取回资源</a>";
			    
				if("1".equals(isTp)){
					   huiqu ="<a id='addCustQuHuiYiXiang' href='#' style='text-indent:0;color:blue;' onclick=\"addCustQuHuiYiXiang( '" + num + "', '"
							+ type + "', '" + orgValue + "', '" + chkVal + "')\">取回意向</a>";
				}
				return " 该" + chkVal + "，已在待分配资源中;</label>"+huiqu;
			}
			if (resCust.getType() == 1 && (resCust.getStatus() == 2 || resCust.getStatus() == 3)) {
				return " 该" + chkVal + "，已是 " + resCust.getOwnerName() +"的资源;";
			}
			if (resCust.getStatus() == 4 || resCust.getStatus() == 5) {
				String huiqu ="<a id='addCustQuHui' href='#' style='text-indent:0;color:blue;' onclick=\"addCustQuHui( '" + num + "', '"
						+ type + "', '" + orgValue + "', '" + chkVal + "', '" + resCust.getStatus() + "')\">取回资源</a>";
				if("1".equals(isTp)){
					   huiqu ="<a id='addCustQuHuiYiXiang' href='#' style='text-indent:0;color:blue;' onclick=\"addCustQuHuiYiXiang( '" + num + "', '"
							+ type + "', '" + orgValue + "', '" + chkVal + "')\">取回意向</a>";
				}
				return "<label> 该" + chkVal+ "，已在公海客户池中;</label>"+huiqu;
			}
			if (resCust.getType() == 2 && resCust.getStatus() == 6) {
				return " 该" + chkVal + "，已是 " + resCust.getOwnerName() +"的签约客户" + resultStr;
			}
			if (resCust.getType() == 2) {
				return " 该" + chkVal + "，已是 " + resCust.getOwnerName() + "的意向客户" + resultStr;
			}
//			if (resCust.getType() == 3) {
//				return " 该" + chkVal + "，已是 " + resCust.getOwnerName() + "（" + resCust.getOwnerAcc() + "）的再淘客户" + resultStr;
//			}
		}
		return "";
	}

	/**
	 * 格式化固话号码 说明：首位加0及去除区号后的“-”字符
	 * 
	 * @param phone
	 * @return
	 * @create 2013-12-19 下午03:50:53 haoqj
	 * @history
	 */
	public String formatPhone(String phone, HttpServletRequest request, DataDictionaryBean bean) {
		if (phone == null) {
			return "";
		} else {
			phone = phone.trim();
		}
		String rstPhnoe = phone;
		if (StringUtils.isBlank(phone))
			return "";
		String tel1 = "^\\d{3,4}-\\d{3,4}-\\d{3,4}$";
		Pattern p1 = Pattern.compile(tel1);
		Matcher m1 = p1.matcher(rstPhnoe);
		if (m1.matches()) {
			rstPhnoe = rstPhnoe.replaceAll("-", "");
		} else {
			if (rstPhnoe.length() >= 5 && rstPhnoe.substring(3, 5).contains("-")) {
				rstPhnoe = rstPhnoe.replaceFirst("-", "");
			}
			if (phone.length() == 7 || phone.length() == 8) {
				List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA18, ShiroUtil.getShiroUser().getOrgId());
				if (list != null && "1".equals(list.get(0).getDictionaryValue())) {
					list = cachedService.getDirList(AppConstant.DATA19, ShiroUtil.getShiroUser().getOrgId());
					rstPhnoe = list.get(0).getDictionaryValue() + rstPhnoe;
				}
			}
		}
		return rstPhnoe;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFieldVal(String fieldCode) {
		ShiroUser user = ShiroUtil.getUser();
		List<CustFieldSet> fieldSets = new ArrayList<CustFieldSet>();
		String result = "";

		if (user.getIsState() == 1) {
			fieldSets = cachedService.getComFiledSet(user.getOrgId());
		} else {
			fieldSets = cachedService.getPersonFiledSet(user.getOrgId());
		}
		if (fieldSets != null && fieldSets.size() > 0) {
			for (CustFieldSet fieldSet : fieldSets) {
				if (fieldCode.equals(fieldSet.getFieldCode())) {
					result = fieldSet.getFieldName();
					break;
				}
			}
		}
		return result;
	}

	public String replaceWord(String phone) {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isNotEmpty(phone) && phone.length() > 3) {
			String[] b = phone.split("");
			int length = b.length;
			for (int i = 0; i < length; i++) {
				if (length == 8) {
					if (i > 2 && i < 7) {
						sb.append("*");
					} else {
						sb.append(b[i]);
					}
				}
				if (length == 9) {
					if (i > 2 && i < 7) {
						sb.append("*");
					} else {
						sb.append(b[i]);
					}
				}
				if (length == 10) {
					if (i > 3 && i < 8) {
						sb.append("*");
					} else {
						sb.append(b[i]);
					}
				}
				if (length == 11) {
					if (i > 3 && i < 8) {
						sb.append("*");
					} else {
						sb.append(b[i]);
					}
				}
				if (length == 12) {
					if (i > 3 && i < 8) {
						sb.append("*");
					} else {
						sb.append(b[i]);
					}
				}
				if (length > 12) {
					if (i > 4 && i < 9) {
						sb.append("*");
					} else {
						sb.append(b[i]);
					}
				}
			}
		} else {
			return phone;
		}
		return sb.toString();
	}

}
