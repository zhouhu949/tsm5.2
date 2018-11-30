package com.qftx.tsm.tao.service;

import com.qftx.common.cached.CachedService;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResCustInfoDetailBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.tao.dto.CustDetailDto;
import com.qftx.tsm.tao.dto.CustDto;
import com.qftx.tsm.tao.dto.FieldDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustInfoService {
	transient Logger logger = Logger.getLogger(CustInfoService.class);
	@Autowired
	private CachedService cachedService;

	public CustDto getCustDto(ResCustInfoBean custInfo, int state, List<ResCustInfoDetailBean> detailList) {
		CustDto custDto = new CustDto();
		if (custInfo == null) {
			return custDto;
		}
		List<CustDetailDto> list = new ArrayList<CustDetailDto>();
		if (state == 1) {
			if (detailList != null && detailList.size() > 0) {
				for (ResCustInfoDetailBean bean : detailList) {
					CustDetailDto custDetailDto = new CustDetailDto();
					custDetailDto.setDetailId(bean.getTscidId());
					custDetailDto.setIsDefault(bean.getIsDefault() + "");
					custDetailDto.setName(bean.getName());
					custDetailDto.setTelphone(bean.getTelphone());
					custDetailDto.setTelphonebak(bean.getTelphonebak());
					custDetailDto.setDuty(bean.getWork());
					custDetailDto.setSex(bean.getSex());
					custDetailDto.setQq(bean.getQq());
					custDetailDto.setEmail(bean.getEmail());
					custDetailDto.setWangwang(bean.getWangwang());
					custDetailDto.setCallNum(bean.getCallNum());
					list.add(custDetailDto);
				}
			}
		}
		custDto.setAlternatePhone2(custInfo.getAlternatePhone2());
		custDto.setCustId(custInfo.getResCustId());
		custDto.setIsConcat(custInfo.getIsConcat() == null ? "0" : custInfo.getIsConcat() + "");
		custDto.setEmail(custInfo.getEmail());
		custDto.setMobilephone(custInfo.getMobilephone());
		custDto.setQq(custInfo.getQq());
		custDto.setSex(custInfo.getSex());
		custDto.setTelphone(custInfo.getTelphone());
		custDto.setWangWang(custInfo.getWangwang());
		custDto.setList(list);
		return custDto;
	}

	public List<FieldDto> getCustData(List<CustFieldSet> fieldSets, int state, String pname, String cname, String oname, ResCustInfoBean custInfo) throws Exception {
		List<FieldDto> fieldDtos = new ArrayList<FieldDto>();
		if (custInfo == null) {
			return fieldDtos;
		}
		for (CustFieldSet fieldSet : fieldSets) {
			FieldDto fieldDto = new FieldDto();
			if (state == 1) {
				if ("comArea".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(pname)) {
					fieldDto.setFieldValue(pname + "&nbsp;&nbsp;" + cname + "&nbsp;&nbsp;" + oname);
				} else if ("isMajor".equals(fieldSet.getFieldCode()) || "name".equals(fieldSet.getFieldCode())) {

				} else if ("companyTrade".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getCompanyTrade())) {
					fieldDto.setFieldValue(getCompanyTrade(custInfo).get(custInfo.getCompanyTrade()));
				} else if ("companyMoney".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getCompanyMoney())) {
					fieldDto.setFieldValue(custInfo.getCompanyMoney());
				} else if ("companyUser".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getCompanyUser())) {
					fieldDto.setFieldValue(custInfo.getCompanyUser());
				} else if ("companyFax".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getCompanyFax())) {
					fieldDto.setFieldValue(custInfo.getCompanyFax());
				} else if ("keyWord".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getKeyWord())) {
					fieldDto.setFieldValue(custInfo.getKeyWord());
				} else if ("unithome".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getUnithome())) {
					fieldDto.setFieldValue(custInfo.getUnithome());
				} else if ("address".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getAddress())) {
					fieldDto.setFieldValue(custInfo.getAddress());
				} else if ("scope".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getScope())) {
					fieldDto.setFieldValue(custInfo.getScope());
				} else if ("remark".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getRemark())) {
					fieldDto.setFieldValue(custInfo.getRemark());
				}

			} else {
				if ("sex".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getSex())) {
					fieldDto.setFieldValue(custInfo.getSex());
				} else if ("area".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(pname)) {
					fieldDto.setFieldValue(pname);
				} else if ("birthday".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && custInfo.getBirthday() != null) {
					fieldDto.setFieldValue(DateUtil.format(custInfo.getBirthday(), DateUtil.defaultPattern));
				} else if ("companyTrade".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getCompanyTrade())) {
					fieldDto.setFieldValue(getCompanyTrade(custInfo).get(custInfo.getCompanyTrade()));
				} else if ("mobilephone".equals(fieldSet.getFieldCode()) || "telphone".equals(fieldSet.getFieldCode())
						|| "isMajor".equals(fieldSet.getFieldCode()) || "name".equals(fieldSet.getFieldCode())) {

				} else if ("fax".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getFax())) {
					fieldDto.setFieldValue(custInfo.getFax());
				} else if ("company".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getCompany())) {
					fieldDto.setFieldValue(custInfo.getCompany());
				} else if ("keyWord".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getKeyWord())) {
					fieldDto.setFieldValue(custInfo.getKeyWord());
				} else if ("email".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getEmail())) {
					fieldDto.setFieldValue(custInfo.getEmail());
				} else if ("qq".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getQq())) {
					fieldDto.setFieldValue(custInfo.getQq());
				} else if ("ww".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getWangwang())) {
					fieldDto.setFieldValue(custInfo.getWangwang());
				} else if ("unithome".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getUnithome())) {
					fieldDto.setFieldValue(custInfo.getUnithome());
				} else if ("companyOrg".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getCompanyOrg())) {
					fieldDto.setFieldValue(custInfo.getCompanyOrg());
				} else if ("duty".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDuty())) {
					fieldDto.setFieldValue(custInfo.getDuty());
				} else if ("companyAddr".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getCompanyAddr())) {
					fieldDto.setFieldValue(custInfo.getCompanyAddr());
				} else if ("remark".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getRemark())) {
					fieldDto.setFieldValue(custInfo.getRemark());
				} else if ("wangwang".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getWangwang())) {
					fieldDto.setFieldValue(custInfo.getWangwang());
				} else if ("alternatePhone2".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getAlternatePhone2())) {
					fieldDto.setFieldValue(custInfo.getAlternatePhone2());
				}
			}
			if ("defined1".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined1())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined2".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined2())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined3".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined3())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined4".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined4())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined5".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined5())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined6".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined6())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined7".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined7())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined8".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined8())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined9".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined9())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined10".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined10())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined11".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined11())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined12".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined12())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined13".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined13())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined14".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined14())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined15".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && StringUtils.isNotEmpty(custInfo.getDefined15())) {
				setFieldValues(fieldDto, fieldSet, custInfo);
			} else if ("defined16".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && custInfo.getDefined16() != null) {
				fieldDto.setFieldValue(new SimpleDateFormat("yyyy-MM-dd").format(custInfo.getDefined16()));
			} else if ("defined17".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && custInfo.getDefined17() != null) {
				fieldDto.setFieldValue(new SimpleDateFormat("yyyy-MM-dd").format(custInfo.getDefined17()));
			} else if ("defined18".equals(fieldSet.getFieldCode()) && fieldSet.getEnable() == 1 && custInfo.getDefined18() != null) {
				fieldDto.setFieldValue(new SimpleDateFormat("yyyy-MM-dd").format(custInfo.getDefined18()));
			}
			if (StringUtils.isNotEmpty(fieldDto.getFieldValue())) {
				fieldDto.setFieldCode(fieldSet.getFieldCode());
				fieldDto.setFieldName(fieldSet.getFieldName());
				fieldDtos.add(fieldDto);
			}

		}
		return fieldDtos;
	}
	
	public void setFieldValues(FieldDto fieldDto,CustFieldSet fieldSet,ResCustInfoBean custInfo) throws Exception {
		Class<?> classType = custInfo.getClass();
		String fieldCode = fieldSet.getFieldCode();
		// 得到属性名称的第一个字母并转成大写
		String firstLetter = fieldCode.substring(0, 1).toUpperCase();
		// 获得和属性对应的getXXX()方法的名字：get+属性名称的第一个字母并转成大写+属性名去掉第一个字母，
		// 如属性名称为name，则：get+N+ame
		String getMethodName = "get" + firstLetter + fieldCode.substring(1);
		// 获得和属性对应的getXXX()方法
		Method getMethod = classType.getMethod(getMethodName,new Class[] {});
		// 调用原对象的getXXX()方法
		//Object value = getMethod.invoke(custInfo, new Object[] {});
		if (fieldSet.getDataType() == 1) {
			fieldDto.setFieldValue((String)getMethod.invoke(custInfo, new Object[] {}));
		}else if (fieldSet.getDataType() == 3) {
			 List<OptionBean> optionList = fieldSet.getOptionList();
			 String id =(String)getMethod.invoke(custInfo, new Object[] {});
			 if (org.apache.commons.lang3.StringUtils.isNotBlank(id) && optionList != null) {
				 for (OptionBean optionBean : optionList) {
						if (id.equals(optionBean.getOptionlistId())) {
							fieldDto.setFieldValue(optionBean.getOptionName());
						}		 
				 }
			}
		}else if (fieldSet.getDataType() == 4) {
			 List<OptionBean> optionList = fieldSet.getOptionList();
			 String ids = (String)getMethod.invoke(custInfo, new Object[] {});
			 String[] idss = ids.split(",");
			 StringBuffer showview = new StringBuffer();
			 if (org.apache.commons.lang3.StringUtils.isNotBlank(ids) && optionList != null) {
				a:for (String id : idss) {
					b:for (OptionBean optionBean : optionList) {
						if (id.equals(optionBean.getOptionlistId())) {
							if (idss[idss.length-1] == id) {
								showview.append(optionBean.getOptionName());
								break b;
							}else {
								showview.append(optionBean.getOptionName()).append(",");
								break b;
							}
						}		 
					}
				}
			 fieldDto.setFieldValue(showview.toString());
			}
		}
	}

	public Map<String, String> getCompanyTrade(ResCustInfoBean custInfo) {
		List<OptionBean> trades = cachedService.getOptionList(AppConstant.com_trade, custInfo.getOrgId());
		Map<String, String> tradeMap = new HashMap<String, String>();
		tradeMap = cachedService.changeOptionListToMap(trades);
		return tradeMap;
	}
}
