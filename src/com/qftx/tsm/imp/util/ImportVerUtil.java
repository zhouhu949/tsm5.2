package com.qftx.tsm.imp.util;

import com.qftx.common.cached.CachedService;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.common.util.RegexUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.imp.enums.ImportResultEnum;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 导入验证
 * @author zwj
 */
public class ImportVerUtil{
	private static CachedService cachedService = (CachedService)AppContextUtil.getBean("cachedService");
	/**
	 * 获取验证设置 
	 * @param orgId  机构ID
	 * @return String[isOpen,isPhone,isCompanyName,isUnithome]
	 */	 
	@SuppressWarnings("unchecked")
	public static String[] getDataDic(String orgId) {
		String[] dics = null;

		// 资源去重设置是否开启（0:不开启，1：开启）
		String isOpen = "0";

		// 1：电话号码去重:未选中
		// 2：对签约客户电话号码进行去重保护
		// 3：对意向客户电话号码进行去重保护
		// 4：对所有资源进行电话号码去重保护
		String isPhone = "1";

		// 0：单位名称去重：未选中
		// 1：对签约客户单位名称进行去重保护
		// 2：对意向客户单位名称进行去重保护
		// 3：对所有资源单位名称去重保护
		String isCompanyName = "0";

		// 0：单位主页去重：未选中
		// 1：对签约客户单位主页进行去重保护
		// 2：对意向客户单位主页进行去重保护
		// 3：对所有资源单位主页去重保护
		String isUnithome = "0";

		// 0:个人导入限制为：未选中
		// 1:个人导入限制为：选中
		String isImpSet = "0";
		String limits = "0"; // 资源限制大小
		
		// 客户跟进设置开关是否开启（0:不开启，1：开启）
		String isFlowOpen = "0";
		//查询缓存
		Object dataList = null;
		// 取开关值
		List<DataDictionaryBean> dic = cachedService.getDirList(AppConstant.DATA14,orgId);
		if(!dic.isEmpty()){
			isOpen = dic.get(0).getDictionaryValue() == null ? "0" : dic.get(0)
					.getDictionaryValue();
		}

		dic = cachedService.getDirList(AppConstant.DATA11,orgId);
		if (!dic.isEmpty()) {
			isPhone = dic.get(0).getDictionaryValue() == null ? "1" : dic
					.get(0).getDictionaryValue();
		}

		dic = cachedService.getDirList(AppConstant.DATA12,orgId);
		if (!dic.isEmpty()) {
			isCompanyName = dic.get(0).getDictionaryValue() == null ? "0" : dic
					.get(0).getDictionaryValue();
		}
	
		dic = cachedService.getDirList(AppConstant.DATA22,orgId);
		if (!dic.isEmpty()) {
			isUnithome = dic.get(0).getDictionaryValue() == null ? "0" : dic
					.get(0).getDictionaryValue();
		}
		
		dic = cachedService.getDirList(AppConstant.DATA15,orgId);
		if (!dic.isEmpty()) {
			isFlowOpen = dic.get(0).getDictionaryValue() == null ? "0" : dic
					.get(0).getDictionaryValue();
		}
		if(!"0".equals(isFlowOpen)){ // 如果跟进客户设置开启
			dic = cachedService.getDirList(AppConstant.DATA28,orgId);
			if (!dic.isEmpty()) {				
				isImpSet = dic.get(0).getIsOpen() == null ? "0" : dic
						.get(0).getIsOpen();
				limits = dic.get(0).getDictionaryValue() == null ? "0" : dic
						.get(0).getDictionaryValue();
			}
		}
		dics = new String[] { isOpen, isPhone, isCompanyName, isUnithome, isImpSet, limits};
		return dics;
	}
	
	/**
	 * 获取格式验证设置 
	 * @param orgId  机构ID
	 * @return String[isOpen,isPhone,isCompanyName,isUnithome]
	 */	 
	@SuppressWarnings("unchecked")
	public static String[] getDataDic2(String orgId) {
		String[] dics = null;
		Set<String> dicSet = new HashSet<String>();
		String commonTelFlag = "0";//常用电话判断标志
		String altTelFlag = "0";//备用电话判断标志
		String altTel2Flag = "0";//备用电话2判断标志
		String foxFlag = "0";//传真判断标志
		String mailFlag = "0";//邮箱判断标志
		//查询缓存
		Object obj = null;
		
		List<DataDictionaryBean> dirlist = cachedService.getDirList(AppConstant.DATA34,orgId);
		if(!dirlist.isEmpty() && "1".equals(dirlist.get(0).getDictionaryValue())){//判断是否开启设置
			//判断常用电话
			List<DataDictionaryBean> commonTelList = cachedService.getDirList(AppConstant.DATA35,orgId);
			if(!commonTelList.isEmpty() && "1".equals(commonTelList.get(0).getDictionaryValue())){
				commonTelFlag = "1";
			}
			
			//判断备用电话
			List<DataDictionaryBean> altTelList = cachedService.getDirList(AppConstant.DATA36,orgId);
			if(!altTelList.isEmpty() && "1".equals(altTelList.get(0).getDictionaryValue())){
				altTelFlag = "1";
			}
			
			//判断备用电话2
			List<DataDictionaryBean> altTel2FlagList = cachedService.getDirList(AppConstant.DATA37,orgId);
			if(!altTel2FlagList.isEmpty() && "1".equals(altTel2FlagList.get(0).getDictionaryValue())){
				altTel2Flag = "1";
			}
			
			//判断传真
			List<DataDictionaryBean> foxFlagList = cachedService.getDirList(AppConstant.DATA38,orgId);
			if(!foxFlagList.isEmpty() && "1".equals(foxFlagList.get(0).getDictionaryValue())){
				foxFlag = "1";
			}
			
			//判断邮箱
			List<DataDictionaryBean> mailFlagList = cachedService.getDirList(AppConstant.DATA39,orgId);
			if(!mailFlagList.isEmpty() && "1".equals(mailFlagList.get(0).getDictionaryValue())){
				mailFlag = "1";
			}
		}
		dics = new String[] { commonTelFlag, altTelFlag, altTel2Flag, foxFlag, mailFlag};
		return dics;
	}
	
	
	/***
	 * 返回【个人资源】 验证后的资源数据
	 * @param field_state	匹配字段_资源类型
	 * @param value 要导入的值
	 * @param map1 <fieldCode,fieldName>
	 * @param dics 资源去重验证
	 * @param dics2 资源格式验证
	 * @param map2 列表字段 主要验证必填
	 * @return
	 */
	public static String[] getPersonResult(String field,String value,Map<String,String>map1,
			String[] dics,String[] dics2,Map<String, Object> map2){
		String resultEnum = null;
		String rtnMsg = null;
			if (AppConstant.name.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
							false, null, 50, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.sex.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					if(!"男".equals(value) && !"女".equals(value) && !"1".equals(value) && !"2".equals(value)){						
						rtnMsg = "性别 请填写：男 或 女 ！";
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}else if(StringUtils.isBlank(value)){
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.birthday.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
							false, null, 19, RegexUtil.BIRTHDAY);
				
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}					
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.mobilephone.equals(field) || AppConstant.telphone.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					if("1".equals(dics2[0])){
						// 电话号码格式验证
						rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
								false, null, 50, RegexUtil.newPhone);
						
						if (rtnMsg != null) {
							resultEnum= ImportResultEnum.FORMART_ERROR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}					
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			}else if (AppConstant.fax.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					if("1".equals(dics2[3])){ // 传真格式验证
						rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
								false, null, 30, RegexUtil.tel_ext);
						
						if (rtnMsg != null) {
							resultEnum= ImportResultEnum.FORMART_ERROR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}					
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.company.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					// 单位名称验证
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field), false,
							null, 200, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.qq.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					// QQ格式验证
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
							false, null, 50, null);
										
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.email.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					if("1".equals(dics2[4])){
						// 邮箱格式验证
						rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
								false, null, 100, RegexUtil.eMail);
						
						if (rtnMsg != null) {
							resultEnum= ImportResultEnum.FORMART_ERROR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}					
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.unithome.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					// 公司主页不能重复
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field), false,
							null, 200, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}		
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.companyOrg.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value,
							map1.get(field), false, null, 64, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.duty.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
							false, null, 100, null);					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.companyAddr.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value,
							map1.get(field), false, null, 200, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.remark.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
							false, null, 400, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.keyWord.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
							false, null, 200, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (field.contains("defined")) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value,
							map1.get(field), false, null, 100, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			}			
			resultEnum= ImportResultEnum.SUCCESS.getState();
			return new String[]{resultEnum,rtnMsg};
	}
	
	
	/***
	 * 返回【企业资源】 验证后的资源数据
	 * @param field_state	匹配字段_资源类型
	 * @param value 要导入的值
	 * @param map1 <fieldCode,fieldName>
	 * @param dics 资源去重验证
	 * @param dics2 资源格式验证
	 * @param map2 列表字段 主要验证必填
	 * @return
	 */
	public static String[] getComResult(String field,String value,Map<String,String>map1,
			String[] dics2,Map<String, Object> map2){
		String resultEnum = null;
		String rtnMsg = null;
			if (AppConstant.com_name.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
							false, null, 50, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.com_capital.equals(field) || AppConstant.com_trade.equals(field)
					|| AppConstant.com_user.equals(field) || AppConstant.com_address.equals(field) ) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
							false, null, 200, null);					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			}else if (AppConstant.com_fax.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					if("1".equals(dics2[3])){ // 传真格式验证
						rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
								false, null, 30, RegexUtil.tel_ext);
						
						if (rtnMsg != null) {
							resultEnum= ImportResultEnum.FORMART_ERROR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}					
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			}else if (AppConstant.com_unithome.equals(field) || AppConstant.com_keyWord.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					// 公司主页不能重复
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field), false,
							null, 200, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}		
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.com_remark.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
							false, null, 400, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (field.contains("defined") || AppConstant.com_scope.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value,
							map1.get(field), false, null, 100, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			}			
			resultEnum= ImportResultEnum.SUCCESS.getState();
			return new String[]{resultEnum,rtnMsg};
	}
	
	/***
	 * 返回【联系人】 验证后的资源数据
	 * @param field_state	匹配字段_资源类型
	 * @param value 要导入的值
	 * @param map1 <fieldCode,fieldName>
	 * @param dics 资源去重验证
	 * @param dics2 资源格式验证
	 * @param map2 列表字段 主要验证必填
	 * @return
	 */
	public static String[] getContactResult(String field,String value,Map<String,String>map1,
			String[] dics,String[] dics2,Map<String, Object> map2){
		String resultEnum = null;
		String rtnMsg = null;
			if (AppConstant.contacts_name.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
							false, null, 50, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.contacts_sex.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					if(!"男".equals(value) && !"女".equals(value) && !"1".equals(value) && !"2".equals(value)){						
						rtnMsg = "性别 请填写：男 或 女 ！";
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}else if(StringUtils.isBlank(value)){
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.contacts_birthday.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
							false, null, 19, RegexUtil.BIRTHDAY);
				
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}					
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.contacts_telphone.equals(field) || AppConstant.contacts_telphonebak.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					if("1".equals(dics2[0])){
						// 电话号码格式验证
						rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
								false, null, 50, RegexUtil.newPhone);
						
						if (rtnMsg != null) {
							resultEnum= ImportResultEnum.PHONE_FORMART.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}					
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			}else if (AppConstant.contacts_fax.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					if("1".equals(dics2[3])){ // 传真格式验证
						rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
								false, null, 30, RegexUtil.tel_ext);
						
						if (rtnMsg != null) {
							resultEnum= ImportResultEnum.FORMART_ERROR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}					
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			}else if (AppConstant.contacts_qq.equals(field) || AppConstant.contacts_groupname.equals(field)
					|| AppConstant.contacts_ww.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					// QQ格式验证
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
							false, null, 50, null);
										
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			} else if (AppConstant.contacts_email.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					if("1".equals(dics2[4])){
						// 邮箱格式验证
						rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
								false, null, 100, RegexUtil.eMail);
						
						if (rtnMsg != null) {
							resultEnum= ImportResultEnum.FORMART_ERROR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}					
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			}else if (AppConstant.contacts_work.equals(field)) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value, map1.get(field),
							false, null, 100, null);					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			}else if (field.contains("defined")) {
				if (StringUtils.isNotBlank(value)) {
					rtnMsg = RegexUtil.checkStrVarMsg(value,
							map1.get(field), false, null, 100, null);
					
					if (rtnMsg != null) {
						resultEnum= ImportResultEnum.FORMART_ERROR.getState();
						return new String[]{resultEnum,rtnMsg};
					}else{
						if(RegexUtil.findRegex(RegexUtil.chkChar, value)){
							rtnMsg = map1.get(field)+"拥有非法字符：`'<>\"”“换行或回车";
							resultEnum= ImportResultEnum.OWN_ILLEGAL_CHAR.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}else{
					if(map2.get(field)!=null && "1".equals(String.valueOf(map2.get(field)))){
						rtnMsg = map1.get(field)+"：为必填项！";
						resultEnum= ImportResultEnum.DEFECT_REQUIRED.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			}			
			resultEnum= ImportResultEnum.SUCCESS.getState();
			return new String[]{resultEnum,rtnMsg};
	}
	
	/**
	 * 截取
	 * @param cust
	 * @return
	 */
	public static ResCustInfoBean subCusts(ResCustInfoBean resCust){
		if(resCust.getName()!=null && resCust.getName().replaceAll(RegexUtil.byteDobule, "xx").length() > 50){
			resCust.setName(resCust.getName().replaceAll(RegexUtil.byteDobule, "xx").substring(0, 50));
		}
		
		if(resCust.getMobilephone()!=null && resCust.getMobilephone().replaceAll(RegexUtil.byteDobule, "xx").length() > 50){
			resCust.setMobilephone(resCust.getMobilephone().replaceAll(RegexUtil.byteDobule, "xx").substring(0,50));
		}
		
		if(resCust.getTelphone()!=null && resCust.getTelphone().replaceAll(RegexUtil.byteDobule, "xx").length() > 50){
			resCust.setTelphone(resCust.getTelphone().replaceAll(RegexUtil.byteDobule, "xx").substring(0,50));
		}
		
		if(resCust.getAlternatePhone2()!= null && resCust.getAlternatePhone2().replaceAll(RegexUtil.byteDobule, "xx").length() > 50){
			resCust.setAlternatePhone2(resCust.getAlternatePhone2().replaceAll(RegexUtil.byteDobule, "xx").substring(0,50));
		}
		
		if(resCust.getFax()!=null && resCust.getFax().replaceAll(RegexUtil.byteDobule, "xx").length() > 30){
			resCust.setFax(resCust.getFax().replaceAll(RegexUtil.byteDobule, "xx").substring(0,30));
		}
		
		if(resCust.getCompany()!=null && resCust.getCompany().replaceAll(RegexUtil.byteDobule, "xx").length() > 100){
			resCust.setCompany(resCust.getCompany().replaceAll(RegexUtil.byteDobule, "xx").substring(0,100));
		}
		
		if(resCust.getQq()!=null && resCust.getQq().replaceAll(RegexUtil.byteDobule, "xx").length() > 50){
			resCust.setQq(resCust.getQq().replaceAll(RegexUtil.byteDobule, "xx").substring(0,50));
		}	
		
		if(resCust.getEmail()!=null && resCust.getEmail().replaceAll(RegexUtil.byteDobule, "xx").length() > 100){
			resCust.setEmail(resCust.getEmail().replaceAll(RegexUtil.byteDobule, "xx").substring(0,100));
		}
		
		if(resCust.getUnithome()!=null && resCust.getUnithome().replaceAll(RegexUtil.byteDobule, "xx").length() > 200){
			resCust.setUnithome(resCust.getUnithome().replaceAll(RegexUtil.byteDobule, "xx").substring(0,200));
		}
		
		if(resCust.getCompanyOrg()!=null && resCust.getCompanyOrg().replaceAll(RegexUtil.byteDobule, "xx").length() > 64){
			resCust.setCompanyOrg(resCust.getCompanyOrg().replaceAll(RegexUtil.byteDobule, "xx").substring(0,64));
		}
		
		if(resCust.getDuty()!=null && resCust.getDuty().replaceAll(RegexUtil.byteDobule, "xx").length() > 100){
			resCust.setDuty(resCust.getDuty().replaceAll(RegexUtil.byteDobule, "xx").substring(0,100));
		}
		
		if(resCust.getCompanyAddr()!=null && resCust.getCompanyAddr().replaceAll(RegexUtil.byteDobule, "xx").length() > 200){
			resCust.setCompanyAddr(resCust.getCompanyAddr().replaceAll(RegexUtil.byteDobule, "xx").substring(0,200));
		}
		
		if(resCust.getRemark()!=null && resCust.getRemark().replaceAll(RegexUtil.byteDobule, "xx").length() > 400){
			resCust.setRemark(resCust.getRemark().replaceAll(RegexUtil.byteDobule, "xx").substring(0,400));
		}
		
		if(resCust.getKeyWord()!=null && resCust.getKeyWord().replaceAll(RegexUtil.byteDobule, "xx").length() > 200){
			resCust.setKeyWord(resCust.getKeyWord().replaceAll(RegexUtil.byteDobule, "xx").substring(0,200));
		}
		
		if(resCust.getDefined1()!=null && resCust.getDefined1().replaceAll(RegexUtil.byteDobule, "xx").length() > 100){
			resCust.setDefined1(resCust.getDefined1().replaceAll(RegexUtil.byteDobule, "xx").substring(0,100));
		}
		
		if(resCust.getDefined2()!=null && resCust.getDefined2().replaceAll(RegexUtil.byteDobule, "xx").length() > 100){
			resCust.setDefined2(resCust.getDefined2().replaceAll(RegexUtil.byteDobule, "xx").substring(0,100));
		}
		
		if(resCust.getDefined3()!=null && resCust.getDefined3().replaceAll(RegexUtil.byteDobule, "xx").length() > 100){
			resCust.setDefined3(resCust.getDefined3().replaceAll(RegexUtil.byteDobule, "xx").substring(0,100));
		}
		
		if(resCust.getDefined4()!=null && resCust.getDefined4().replaceAll(RegexUtil.byteDobule, "xx").length() > 100){
			resCust.setDefined4(resCust.getDefined4().replaceAll(RegexUtil.byteDobule, "xx").substring(0,100));
		}
		
		if(resCust.getDefined5()!=null && resCust.getDefined5().replaceAll(RegexUtil.byteDobule, "xx").length() > 100){
			resCust.setDefined5(resCust.getDefined5().replaceAll(RegexUtil.byteDobule, "xx").substring(0,100));
		}	
		return resCust;
	}
	// -------------------------------------------------------------------------
	// Private Methods：
	// -------------------------------------------------------------------------
	
	/** 号码格式匹配 */
	public static String formatPhone(String phone,String orgId) {
		if (phone == null) {
			return "";
		} else {
			phone = phone.trim();
		}
		String rstPhnoe = phone;
		if (StringUtils.isBlank(phone))
			return "";
		String tel1 = "^\\d{3,4}-\\d{3,4}-\\d{3,4}$"; // 400号码
		Pattern p1 = Pattern.compile(tel1);
		Matcher m1 = p1.matcher(rstPhnoe);
		if (m1.matches()) {
			rstPhnoe = rstPhnoe.replaceAll("-", "");
		} else {
			if (rstPhnoe.length() >= 5
					&& rstPhnoe.substring(3, 5).contains("-")) {
				rstPhnoe = rstPhnoe.replaceFirst("-", "");
			}
			if (phone.length() == 7 || phone.length() == 8) {
				// 去掉横线 判断是否添加区号
				List<DataDictionaryBean> dirlist = cachedService.getDirList(AppConstant.DATA18,orgId);
				if(!dirlist.isEmpty() && "1".equals(dirlist.get(0).getDictionaryValue())){
					List<DataDictionaryBean> dirlist1 = cachedService.getDirList(AppConstant.DATA19,orgId);
					if(!dirlist1.isEmpty()){
						rstPhnoe =  dirlist1.get(0).getDictionaryValue()==null?"0": dirlist1.get(0).getDictionaryValue() + rstPhnoe;
					}
				}
			}
		}
		return rstPhnoe;
	}
}
