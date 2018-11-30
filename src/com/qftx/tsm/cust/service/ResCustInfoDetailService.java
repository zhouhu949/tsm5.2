package com.qftx.tsm.cust.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.CustDefinedDataBean;
import com.qftx.tsm.cust.bean.LinkmanDefinedDataBean;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResCustInfoDetailBean;
import com.qftx.tsm.cust.dao.LinkmanDefinedDataMapper;
import com.qftx.tsm.cust.dao.ResCustInfoDetailMapper;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.dto.ResCustDto;
import com.qftx.tsm.imp.dto.ImportRepeatDto;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.sys.bean.CustFieldSet;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ResCustInfoDetailService {
	@Autowired
	private ResCustInfoDetailMapper resCustInfoDetailMapper;
	@Autowired
	private ResCustInfoMapper resCustInfoMapper;
	@Autowired
	private LinkmanDefinedDataMapper linkmanDefinedDataMapper;
	@Autowired
	private CachedService cachedService;
	public List<ResCustInfoDetailBean> getCustDetailListPage(ResCustDto resCustDto){
		return resCustInfoDetailMapper.findCustDetailListPage( resCustDto);
	}
	
	public List<ResCustInfoDetailBean> getCustsInfoDetails(String orgId,String resCustId){
		return resCustInfoDetailMapper.findCustsInfoDetails(orgId, resCustId);
	}
	
	public ResCustInfoDetailBean getByPrimaryKeyAndOrgId(@Param("orgId")String orgId,@Param("linkmanId")String linkmanId,String rciId) throws Exception{
		ResCustInfoDetailBean bean = resCustInfoDetailMapper.getByPrimaryKeyAndOrgId(orgId, linkmanId);
		if(bean != null && hasMultiDef(orgId)) {
			Map<String, Object> map = new HashMap<String, Object>();
	        map.put("orgId", orgId);
	        map.put("custId", rciId);
	        map.put("linkmanId",linkmanId);
	        List<LinkmanDefinedDataBean> datas = linkmanDefinedDataMapper.findLinkmanDefinedShowDatas(map);
	        Map<String, String> definedMap = new HashMap<String, String>();
	        for(LinkmanDefinedDataBean cdb : datas){
	        	if(definedMap.containsKey(cdb.getFieldCode())){
	        		definedMap.put(cdb.getFieldCode(),definedMap.get(cdb.getFieldCode())+","+cdb.getFieldData());
	        	}else{
	        		definedMap.put(cdb.getFieldCode(), cdb.getFieldData());
	        	}
	        }
	        if(definedMap.size() > 0){
	        	Class<?> clazz = bean.getClass();
	        	Method setMethod;
	        	String setName;
	        	for (String key : definedMap.keySet()) {
	        		setName = "set" + key.substring(0,1).toUpperCase()+key.substring(1);
	        		setMethod = clazz.getDeclaredMethod(setName, String.class);
	        		setMethod.invoke(bean, definedMap.get(key));
				}
	        }
		}
		return bean;
	}
	
	 
    public boolean hasMultiDef(String orgId){
    	boolean flg = false;
    	List<CustFieldSet> concatFieldSets = cachedService.getContactsFiledSets(ShiroUtil.getShiroUser().getOrgId());
		for(CustFieldSet field : concatFieldSets){
			if(field.getDataType() == 4){
				flg = true;
				break;
			}
		}
		return flg;
    }
	
	public void modify(ResCustInfoDetailBean detailBean){
		resCustInfoDetailMapper.update(detailBean);
	}
	
	public void createOrEditDetail(ResCustInfoDetailBean detailBean,ShiroUser user) throws Exception{
		detailBean.setOrgId(user.getOrgId());
		if(StringUtils.isNotBlank(detailBean.getTscidId())){//编辑
			if(detailBean.getIsDefault() == 1){
				resCustInfoDetailMapper.deleteDefault(detailBean);
				ResCustInfoBean custInfo = new ResCustInfoBean();
				custInfo.setResCustId(detailBean.getRciId());
				custInfo.setOrgId(user.getOrgId());
				// 常用电话
				custInfo.setMobilephone(formatPhone(detailBean.getTelphone(),user)); // 判断是否是固话，是，再判断是否系统设置要添加区号。
				// 备用电话
				custInfo.setTelphone(formatPhone(detailBean.getTelphonebak(),user)); //
				
//				custInfo.setTelphone(detailBean.getTelphone());
//				custInfo.setMobilephone(detailBean.getTelphonebak());
				custInfo.setMainLinkman(detailBean.getName());
				resCustInfoMapper.update(custInfo);
			}
			detailBean.setUpdatetime(new Date());
			Map<String, String[]> multiDefMap1 = getInsertMultiDefined1(detailBean);
			if(multiDefMap1.size() > 0) defWrite1(detailBean.getRciId(), user.getOrgId(),0,detailBean.getTscidId(), multiDefMap1);
			resCustInfoDetailMapper.update(detailBean);
		}else{
			detailBean.setTscidId(SysBaseModelUtil.getModelId());
			if(detailBean.getIsDefault() == 1){
				resCustInfoDetailMapper.deleteDefault(detailBean);
				ResCustInfoBean custInfo = new ResCustInfoBean();
				custInfo.setResCustId(detailBean.getRciId());
				custInfo.setOrgId(user.getOrgId());
				// 常用电话
				custInfo.setMobilephone(formatPhone(detailBean.getTelphone(),user)); // 判断是否是固话，是，再判断是否系统设置要添加区号。
				// 备用电话
				custInfo.setTelphone(formatPhone(detailBean.getTelphonebak(),user)); //
				custInfo.setMainLinkman(detailBean.getName());
				resCustInfoMapper.update(custInfo);
			}
			detailBean.setInputtime(new Date());
			Map<String, String[]> multiDefMap1 = getInsertMultiDefined1(detailBean);
			if(multiDefMap1.size() > 0) defWrite1(detailBean.getRciId(), user.getOrgId(),0,detailBean.getTscidId(), multiDefMap1);
			resCustInfoDetailMapper.insert(detailBean);
		}
	}
	
	 public void defWrite1(String custId,String orgId,int type,String linkmanId,Map<String, String[]> multiDefMap){
	    	if(type == 1) linkmanDefinedDataMapper.deleteBylinkmanId(orgId, custId,linkmanId);
	    	List<LinkmanDefinedDataBean> list = new ArrayList<LinkmanDefinedDataBean>();
	    	LinkmanDefinedDataBean dataBean;
	    	for(String key : multiDefMap.keySet()){
	    		for(String code : multiDefMap.get(key)){
	    			dataBean = new LinkmanDefinedDataBean();
	    			dataBean.setId(SysBaseModelUtil.getModelId());
	    			dataBean.setOrgId(orgId);
	    			dataBean.setCustId(custId);
	    			dataBean.setLinkmanId(linkmanId);
	    			dataBean.setFieldCode(key);
	    			dataBean.setFieldData(code);
	    			list.add(dataBean);
	    		}
	    	}
	    	linkmanDefinedDataMapper.insertBatch(list);
	    }
	
	 public Map<String, String[]> getInsertMultiDefined1(ResCustInfoDetailBean detailBean) throws Exception{
	    	Map<String, String[]> map = new HashMap<String, String[]>();
	    	List<CustFieldSet> concatFieldSets = null;
			concatFieldSets = cachedService.getContactsFiledSets(detailBean.getOrgId());
			Method getMethod;
			Method setMethod;
			String getName;
	    	String setName;
	    	Class<?> clazz = detailBean.getClass();
			for(CustFieldSet field : concatFieldSets){
				if(field.getDataType() == 4){
					getName = "get" + field.getFieldCode().substring(0, 1).toUpperCase() + field.getFieldCode().substring(1);
					getMethod = clazz.getDeclaredMethod(getName);
					Object val = getMethod.invoke(detailBean);
					if(val != null){
						map.put(field.getFieldCode(), val.toString().split(","));
						setName =  "set" + field.getFieldCode().substring(0, 1).toUpperCase() + field.getFieldCode().substring(1);
		    			setMethod = clazz.getDeclaredMethod(setName, String.class);
		    			setMethod.invoke(detailBean, "");
					}
				}
			}
			return map;
	    }
	
	/**
	 * 格式化固话号码 说明：首位加0及去除区号后的“-”字符
	 * 
	 * @param phone
	 * @param request
	 * @return
	 * @create 2015年11月17日 下午3:14:52 lixing
	 * @history
	 */
	public String formatPhone(String phone,ShiroUser user) {
		if (phone == null) {
			return "";
		} else {
			phone = phone.trim();
		}
		String rstPhnoe = phone;
		if (StringUtils.isBlank(phone)) {
			return "";
		}
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
				List<DataDictionaryBean> openList = cachedService.getDirList(AppConstant.DATA18, user.getOrgId());
				String isOpen = openList.get(0).getDictionaryValue();
				isOpen = isOpen == null ? "0" : isOpen;
				if (isOpen.equals("1")) {
					List<DataDictionaryBean> itemValList = cachedService.getDirList(AppConstant.DATA19, user.getOrgId());
					String px = itemValList.get(0).getDictionaryValue();
					px = px == null ? "0" : px;
					rstPhnoe = px + rstPhnoe;
				}
			}
		}
		return rstPhnoe;
	}
	
	/** 号码去重 */
	public Integer getRepeatByPhone(ImportRepeatDto entity){
		return resCustInfoDetailMapper.getRepeatByPhone(entity);
	}
	
	public void insertBatch(List<ResCustInfoDetailBean>list){
		resCustInfoDetailMapper.insertBatch(list);
	}
	
	public String getCustDetailId(String orgId,String phone,String custId){
		return resCustInfoDetailMapper.findCustDetailId(orgId,phone,custId);
	}
	
	public Map<String, String> getCustDetailName(String orgId,String phone,String custId){
		return resCustInfoDetailMapper.findCustDetailName(orgId,phone,custId);
	}	
	
	public void updateCallNum(ResCustInfoDetailBean bean){
		resCustInfoDetailMapper.updateCallNum(bean);
	}

	public void removeComResLinkBatch(Map<String, Object> map) {
		resCustInfoDetailMapper.removeComResLinkBatch(map);
	} 
}
