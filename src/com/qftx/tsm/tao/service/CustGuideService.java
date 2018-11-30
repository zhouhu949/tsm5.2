package com.qftx.tsm.tao.service;

import com.qftx.common.cached.CachedService;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.bean.OptionGroupBean;
import com.qftx.tsm.sys.bean.Product;
import com.qftx.tsm.tao.dto.OptionDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustGuideService {
	private static Logger logger = Logger.getLogger(CustGuideService.class);
	@Autowired
	transient private CachedService cachedService;

	public Map<String, Object> getOptionMap(HttpServletRequest request, String orgId) {
		List<OptionDto> opList = new ArrayList<OptionDto>();
		// 取得单位下各选项列表(销售进程、客户类型、适用产品、反馈信息)
		Map<String, Object> map = new HashMap<String, Object>();
		List<OptionBean> saleProcessList = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, orgId);
		if (saleProcessList != null && saleProcessList.size() > 0) {
			for (OptionBean optionBean : saleProcessList) {
				OptionDto optionDto = new OptionDto();
				optionDto.setIsDefault(optionBean.getIsDefault() + "");
				optionDto.setOptionId(optionBean.getOptionlistId());
				optionDto.setOptionName(optionBean.getOptionName());
				opList.add(optionDto);
			}
		}
		map.put("saleProcessList", opList);
		opList = new ArrayList<OptionDto>();
		List<OptionBean> custTypeList = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, orgId);
		if (custTypeList != null && custTypeList.size() > 0) {
			for (OptionBean optionBean : custTypeList) {
				OptionDto optionDto = new OptionDto();
				optionDto.setIsDefault(optionBean.getIsDefault() + "");
				optionDto.setOptionId(optionBean.getOptionlistId());
				optionDto.setOptionName(optionBean.getOptionName());
				opList.add(optionDto);
			}
		}
		map.put("custTypeList", opList);
		opList = new ArrayList<OptionDto>();
		List<Product> suitProcList = cachedService.getOpionProduct(orgId);
		if (suitProcList != null && suitProcList.size() > 0) {
			for (Product optionBean : suitProcList) {
				OptionDto optionDto = new OptionDto();
				optionDto.setIsDefault(optionBean.getIsDefault() + "");
				optionDto.setOptionId(optionBean.getId());
				optionDto.setOptionName(optionBean.getName());
				opList.add(optionDto);
			}
		}
		map.put("suitProcList", opList);
		opList = new ArrayList<OptionDto>();
//		List<OptionBean> labelList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN, orgId);
//		if (labelList != null && labelList.size() > 0) {
//			for (OptionBean optionBean : labelList) {
//				OptionDto optionDto = new OptionDto();
//				optionDto.setIsDefault(optionBean.getIsDefault() + "");
//				optionDto.setOptionId(optionBean.getOptionlistId());
//				optionDto.setOptionName(optionBean.getOptionName());
//				opList.add(optionDto);
//			}
//		}
		Map<String,Object> maps=getLableList(orgId);
		Integer isSelect=(Integer) maps.get("isSelect");
		opList=(List<OptionDto>) maps.get("opList");
		if(isSelect==1){//有多个分组
		map.put("labelList", "");	
		}else if(isSelect==0){//只有未分组
		map.put("labelList", opList);			
		}
		map.put("isSelect", isSelect);
		opList = new ArrayList<OptionDto>();
		// 信息错误、沟通失败、非目标客户
		List<OptionBean> commFailureList = cachedService.getOptionList(AppConstant.ITEMCODES[0], orgId);
		if (commFailureList != null && commFailureList.size() > 0) {
			for (OptionBean optionBean : commFailureList) {
				OptionDto optionDto = new OptionDto();
				optionDto.setIsDefault(optionBean.getIsDefault() + "");
				optionDto.setOptionId(optionBean.getOptionlistId());
				optionDto.setOptionName(optionBean.getOptionName());
				opList.add(optionDto);
			}
		}
		map.put("commFailureList", opList);
		opList = new ArrayList<OptionDto>();
		List<OptionBean> messageFailureList = cachedService.getOptionList(AppConstant.ITEMCODES[1], orgId);
		if (messageFailureList != null && messageFailureList.size() > 0) {
			for (OptionBean optionBean : messageFailureList) {
				OptionDto optionDto = new OptionDto();
				optionDto.setIsDefault(optionBean.getIsDefault() + "");
				optionDto.setOptionId(optionBean.getOptionlistId());
				optionDto.setOptionName(optionBean.getOptionName());
				opList.add(optionDto);
			}
		}
		map.put("messageFailureList", opList);
		opList = new ArrayList<OptionDto>();
		List<DataDictionaryBean> disList = cachedService.getDirList(AppConstant.DATA17, orgId);
		// 判断 信息错误、沟通失败、非目标客户 是否开启
		String isopen = "";
		if (disList.size() > 0) {
			isopen = disList.get(0).getDictionaryValue();
		}

		String isEffect = cachedService.getDirList(AppConstant.DATA16, orgId).get(0).getDictionaryValue();
		String timeElngth = "0";
		if ("1".equals(isEffect)) {
			timeElngth = cachedService.getDirList(AppConstant.DATA04, orgId).get(0).getDictionaryValue();
		}

		map.put("isopen", isopen);
		map.put("timeElngth", timeElngth);
		return map;
	}
	
	public Map<String,Object> getLableList(String orgId){
		Map<String,Object> map=new HashMap<String,Object>();
		Integer isSelect=0;//是否需要选择层级，0为不需要，按照默认的来，1为需要
		List<OptionDto> opList = new ArrayList<OptionDto>();
		try {

		List<OptionGroupBean> getOptionGroupList=cachedService.getOptionGroupList(AppConstant.SALES_TYPE_TEN,orgId);
		List<OptionBean> labelList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN, orgId);
		if(getOptionGroupList!=null&&getOptionGroupList.size()>0){
			if(getOptionGroupList.size()==1){
				 isSelect=0;
			}else if(getOptionGroupList.size()>1){
				 int sum=0;
				for(OptionGroupBean optionGroupBean :getOptionGroupList){
					if(optionGroupBean.getIsDefault()==1){
						if (labelList != null && labelList.size() > 0) {
							for (OptionBean optionBean : labelList) {
                                if(optionGroupBean.getGroupId()==optionBean.getGroupId()||optionGroupBean.getGroupId().endsWith(optionBean.getGroupId())){
                                	sum=sum+1;
                                	if(sum>1||sum==1){
                                		isSelect=1;
                                		break;
                                	}
                                }
								
							}
						}
					}
				}
			}
		}
		
        if(isSelect==0){
    		if (labelList != null && labelList.size() > 0) {
    			for (OptionBean optionBean : labelList) {
    				OptionDto optionDto = new OptionDto();
    				optionDto.setIsDefault(optionBean.getIsDefault() + "");
    				optionDto.setOptionId(optionBean.getOptionlistId());
    				optionDto.setOptionName(optionBean.getOptionName());
    				opList.add(optionDto);
    			}
    		}	
        }else{
        	opList = cachedService.getLableList(orgId);
        }
		
	   } catch (Exception e) {
		e.printStackTrace();
	   }
        map.put("isSelect", isSelect);
        map.put("opList", opList);
        return map;
	}
}
