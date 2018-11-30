package com.qftx.tsm.credit.service;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.export.util.ExportExcelUtil;
import com.qftx.export.util.IExportFormatter;
import com.qftx.tsm.credit.bean.CreditLeadInfoBean;
import com.qftx.tsm.credit.bean.TsmLeadDefinedData;
import com.qftx.tsm.credit.dao.CreditLeadInfoMapper;
import com.qftx.tsm.credit.dao.TsmLeadDefinedDataMapper;
import com.qftx.tsm.credit.dto.CreditLeadInfoDto;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.util.CollectionUtil;

/**
 * 催放管理-放款管理 服务类
 *
 * @author: chenhm
 * @since: 2018年07月12日 上午9:25:17
 * @history: 4.0
 */
@Service
public class CreditLeadInfoService {
    private static final Logger logger = Logger.getLogger(CreditLeadInfoService.class);

    @Autowired
    private CreditLeadInfoMapper creditLeadInfoMapper;
    @Autowired
    private CachedService cachedService;
    @Autowired
	private TsmLeadDefinedDataMapper tsmLeadDefinedDataMapper;
    
    /**
     * 跟进身份证号查询贷款次数
     * 
     * @param cardId
     * @return
     */
    public int queryLoanCount(String orgId, String cardId) {
    	return creditLeadInfoMapper.queryLoanCount(orgId, cardId);
    }
    
    /**
     * 根据身份证号查询放款信息（待放款状态、驳回状态）
     * 
     * @param orgId
     * @param cardId
     * @return
     */
    public List<CreditLeadInfoBean> findNotProcessedLeadInfoList(String orgId, String leadId, String cardId){
    	return creditLeadInfoMapper.findNotProcessedLeadInfoList(orgId, leadId, cardId);
    }
    
    /**
     * 根据放款编号查询记录数
     * 
     * @param cardId
     * @return
     */
    public int countByLeadCode(String orgId, String leadId, String leadCode) {
    	return creditLeadInfoMapper.countByLeadCode(orgId, leadId, leadCode);
    }
    
    /**
     * 根据身份证号查询其他负责人下所拥有的放款信息
     * 
     * @param orgId
     * @param cardId
     * @param ownerAcc
     * @return
     */
	public CreditLeadInfoBean findNotCurrentAccLeadInfo(String orgId, String leadId, String cardId, String ownerAcc) {
		List<CreditLeadInfoBean> leadInfoList = creditLeadInfoMapper.findNotCurrentAccLeadInfoList(orgId, leadId, cardId,
				ownerAcc);

		if (null != leadInfoList && !leadInfoList.isEmpty()) {
			return leadInfoList.get(0);
		}

		return null;
	}
    

    public CreditLeadInfoBean queryLeadInfoByOrgIdAndPk(String orgId, String leadId) throws Exception {
        CreditLeadInfoBean bean = creditLeadInfoMapper.getByPrimaryKey(orgId, leadId);
//        if(bean != null && hasMultiDef(bean.getState(), orgId)){
	        Map<String, Object> map = new HashMap<String, Object>();
	        map.put("orgId", orgId);
	        map.put("leadIds", Arrays.asList(leadId));
	        List<TsmLeadDefinedData> datas = tsmLeadDefinedDataMapper.findCustDefinedShowDatas(map);
	        Map<String, String> definedMap = new HashMap<String, String>();
	        for(TsmLeadDefinedData cdb : datas){
	        	if(definedMap.containsKey(cdb.getFieldCode())){
	        		definedMap.put(cdb.getFieldCode(),definedMap.get(cdb.getFieldCode())+","+cdb.getFieldData());
	        	}else{
	        		definedMap.put(cdb.getFieldCode(), cdb.getFieldData());
	        	}
	        }
	        
	        if(definedMap.size() > 0){
	        	BeanUtils.copyProperties(bean, definedMap);
	        }
//        }
        return bean;
    }

    public void create(CreditLeadInfoBean leadInfoBean, Integer state) throws Exception {
    	Map<String, String[]> multiDefMap = getInsertMultiDefined(leadInfoBean, state);
    	if(multiDefMap.size() > 0) defWrite(leadInfoBean.getLeadId(), leadInfoBean.getOrgId(),0, multiDefMap);
        creditLeadInfoMapper.insert(leadInfoBean);
    }

    public void insertBatch(List<CreditLeadInfoBean> list) {
        creditLeadInfoMapper.insertBatch(list);
    }
    
    /**
     * 根据主键查询放款信息列表
     * 
     * @param leadIds 放款信息ID
     * @return
     * @throws Exception
     */
    public List<CreditLeadInfoDto> findLeadInfoList(String orgId, List<String> leadIds) throws Exception {
    	return creditLeadInfoMapper.findLeadInfoList(orgId, leadIds);
    }
    
    /**
     * 放款信息列表
     *
     * @param leadInfoDto
     * @return
     * @throws Exception 
     * @create 2015年11月13日 下午5:28:29 lixing
     * @history
     */
    public List<CreditLeadInfoDto> getMyLeadListPage(CreditLeadInfoDto leadInfoDto,List<String> multiList, Integer state) throws Exception {
    	List<CreditLeadInfoDto> leads = new ArrayList<CreditLeadInfoDto>();
    	List<String> cids = new ArrayList<String>();
//    	if(state != null && 
//    			state == 1 && 
//    			StringUtils.isNotBlank(leadInfoDto.getQueryText()) && 
//    			(leadInfoDto.getQueryType().equals("mobilephone") || leadInfoDto.getQueryType().equals("mainLinkman"))){
//    		if(leadInfoDto.getQueryType().equals("mobilephone")){
//    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(leadInfoDto.getOrgId(), leadInfoDto.getQueryText());
//    		}else{
//    			cids = resCustInfoDetailMapper.findLinkmanIds(leadInfoDto.getOrgId(), leadInfoDto.getQueryText());
//    		}
//    		if(cids.size() == 0) return rests;
//    		leadInfoDto.setLeadIds(cids);
//    		leadInfoDto.setQueryText(null);
//    	}
    	
    	if(multiList != null && multiList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(leadInfoDto, multiList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("restIds", cids);
				List<String> leadIds = tsmLeadDefinedDataMapper.findLeadIdsByDefinedData(paramMap);
				if(leadIds.size() > 0){
					leadInfoDto.setLeadIds(leadIds);
				}else{
					return leads;
				}
			}
		}
//		if (StringUtils.isNotBlank(leadInfoDto.getQueryText()) && leadInfoDto.getQueryType().equals("mobilephone") && (state == null || state == 0)) {
//			rests = creditLeadInfoMapper.findMyResCustPhoneListPage(leadInfoDto);
//		} else {
			leads = creditLeadInfoMapper.findMyLeadInfoListPage(leadInfoDto);
//		}
		return leads;
    }

    public void modify(CreditLeadInfoBean custInfoBean, Integer state) throws Exception {
    	Map<String, String[]> multiDefMap = getInsertMultiDefined(custInfoBean, state);
    	if(multiDefMap.size() > 0) defWrite(custInfoBean.getLeadId(), custInfoBean.getOrgId(),1, multiDefMap);
    	creditLeadInfoMapper.update(custInfoBean);
    }
    
    public void modify2(CreditLeadInfoBean custInfoBean, Integer state) throws Exception {
    	Map<String, String[]> multiDefMap = getInsertMultiDefined(custInfoBean, state);
    	if(multiDefMap.size() > 0) defWrite(custInfoBean.getLeadId(), custInfoBean.getOrgId(),1, multiDefMap);
    	creditLeadInfoMapper.update(custInfoBean);
    }

    public void defWrite(String custId,String orgId,int type,Map<String, String[]> multiDefMap){
    	if(type == 1) tsmLeadDefinedDataMapper.deleteByCustId(orgId, custId);
    	List<TsmLeadDefinedData> list = new ArrayList<TsmLeadDefinedData>();
    	TsmLeadDefinedData dataBean;
    	for(String key : multiDefMap.keySet()){
    		for(String code : multiDefMap.get(key)){
    			dataBean = new TsmLeadDefinedData();
    			dataBean.setId(SysBaseModelUtil.getModelId());
    			dataBean.setOrgId(orgId);
    			dataBean.setLeadId(custId);
    			dataBean.setFieldCode(key);
    			dataBean.setFieldData(code);
    			list.add(dataBean);
    		}
    	}
    	tsmLeadDefinedDataMapper.insertBatch(list);
    }
    
    public Map<String, String[]> getInsertMultiDefined(CreditLeadInfoBean custInfoBean, Integer state) throws Exception{
    	Map<String, String[]> map = new HashMap<String, String[]>();
    	List<CustFieldSet> fieldSets = null;
//		if (state == 1) {// 企业资源
//			fieldSets = cachedService.getComFiledSet(custInfoBean.getOrgId());
//		} else {
//			fieldSets = cachedService.getPersonFiledSet(custInfoBean.getOrgId());
//		}
    	
		fieldSets = cachedService.getQupaiFiledSet(custInfoBean.getOrgId());
		Method setMethod;
    	String setName;
    	Class<?> clazz = custInfoBean.getClass();
		for(CustFieldSet field : fieldSets){
			if(field.getDataType() == 4){
				Object val = PropertyUtils.getProperty(custInfoBean, field.getFieldCode());
				
				if(val != null){
					map.put(field.getFieldCode(), val.toString().split(","));
					setName =  "set" + field.getFieldCode().substring(0, 1).toUpperCase() + field.getFieldCode().substring(1);
	    			setMethod = clazz.getDeclaredMethod(setName, String.class);
	    			setMethod.invoke(custInfoBean, "");
				}
			}
		}
		return map;
    }
    
    public void multiDefinedShowChange(List<CreditLeadInfoDto> list, List<String> multiList, List<String> singleList,
			String orgId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Map<String, String>> custDataMap = new HashMap<String, Map<String, String>>();
		Map<String, Map<String, String>> codeNameMap = new HashMap<String, Map<String, String>>();
		List<TsmLeadDefinedData> definedDatas = new ArrayList<TsmLeadDefinedData>();
		Map<String, String> dataMap;
		List<OptionBean> option;
		if (multiList.size() > 0) {
			List<String> custIds = CollectionUtil.getPropertyValueList(list, "leadId");
			map.put("orgId", orgId);
			map.put("fieldCodes", multiList);
			map.put("leadIds", custIds);
			definedDatas = tsmLeadDefinedDataMapper.findCustDefinedShowDatas(map);
			String val;
			String oldVal;
			for (TsmLeadDefinedData definedData : definedDatas) {
				if (!codeNameMap.containsKey(definedData.getFieldCode())) {
					option = cachedService.getQupaiOptionList(definedData.getFieldCode(), orgId);
					codeNameMap.put(definedData.getFieldCode(), cachedService.changeQupaiOptionListToMap(option));
				}
				val = codeNameMap.get(definedData.getFieldCode()).get(definedData.getFieldData());
				if (val != null) {
					if (custDataMap.containsKey(definedData.getLeadId())) {
						if (custDataMap.get(definedData.getLeadId()).containsKey(definedData.getFieldCode())) {
							oldVal = custDataMap.get(definedData.getLeadId()).get(definedData.getFieldCode());
							custDataMap.get(definedData.getLeadId()).put(definedData.getFieldCode(),
									oldVal + "，" + val);
						} else {
							custDataMap.get(definedData.getLeadId()).put(definedData.getFieldCode(), val);
						}
					} else {
						dataMap = new HashMap<String, String>();
						dataMap.put(definedData.getFieldCode(), val);
						custDataMap.put(definedData.getLeadId(), dataMap);
					}
				}
			}
		}

		// 组装
		Map<String, String> valueMap;
		for (CreditLeadInfoDto bean : list) {
			if (definedDatas.size() > 0) {
				if (custDataMap.containsKey(bean.getLeadId())) {
					valueMap = custDataMap.get(bean.getLeadId());
					for (String key : valueMap.keySet()) {
						BeanUtils.setProperty(bean, key, valueMap.get(key));
					}
				}
			}

			for (String singleDefined : singleList) {
				if (!codeNameMap.containsKey(singleDefined)) {
					option = cachedService.getQupaiOptionList(singleDefined, orgId);
					codeNameMap.put(singleDefined, cachedService.changeQupaiOptionListToMap(option));
				}
				
				Object definedVal = PropertyUtils.getProperty(bean, singleDefined);
				
				if (definedVal != null) {
					String definedValueName = codeNameMap.get(singleDefined).get(definedVal.toString());
					definedValueName = definedValueName == null ? "" : definedValueName;
					BeanUtils.setProperty(bean, singleDefined, definedValueName);
				}
			}
		}
	}

    /**设置扩展表查询条件*/
    public Map<String, Object> getMultiDefinedSearchParam(CreditLeadInfoDto leadInfoDto,List<String> multiList) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	Class<?> clazz = leadInfoDto.getClass().getSuperclass();
    	List<String> fieldCodes = new ArrayList<String>();
    	List<String> fieldDatas = new ArrayList<String>();
    	String getName;
    	String setName;
    	Method getMethod;
    	Method setMethod;
    	for(String fieldCode : multiList){
    		if(fieldCode.equals(AppConstant.SEARCH_LABEL) && leadInfoDto.getLabels() != null && leadInfoDto.getLabels().size() > 0){
				fieldCodes.add(AppConstant.SEARCH_LABEL);
				fieldDatas.addAll(leadInfoDto.getLabels());
				leadInfoDto.setLabels(null);
				continue;
    		}
    		getName =  "get" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
    		getMethod = clazz.getDeclaredMethod(getName);
    		Object val = getMethod.invoke(leadInfoDto);
    		if(val != null && StringUtils.isNotBlank(val.toString())){
    			fieldCodes.add(fieldCode);
    			fieldDatas.addAll(Arrays.asList(val.toString().split(",")));
    			setName =  "set" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
    			setMethod = clazz.getDeclaredMethod(setName, String.class);
    			setMethod.invoke(leadInfoDto, "");
    		}
    	}
    	if(fieldCodes.size() > 0){
    		map.put("fieldCodes", fieldCodes);
    		map.put("fieldDatas", fieldDatas);
    		map.put("orgId", leadInfoDto.getOrgId());
    	}
    	return map;
    }
    
    /**
     * 删除
     * 
     * @param leadInfoDto
     */
    public void delBatchLead(CreditLeadInfoDto leadInfoDto) {
    	creditLeadInfoMapper.delBatchLead(leadInfoDto);
    }
    
    /**
     * 查询放款信息记录总数
     * 
     * @param leadInfoDto
     */
    public Integer findExportCount(CreditLeadInfoDto leadInfoDto) {
    	return creditLeadInfoMapper.findExportCount(leadInfoDto);
    }
    
    /**
     * 放款信息导出
     * 
     * @param list
     * @param columns
     * @param headers
     * @param orgId
     * @param account
     * @return
     */
	public String export(OutputStream out, final List<CreditLeadInfoDto> list, final List<String> columns, final List<String> headers, final String orgId,
			String account) {
//		String exportId = SysBaseModelUtil.getModelId();
//		String filePath = System.currentTimeMillis() + ".xlsx";// export_excel_path+exportId+".xlsx";
//		OutputStream out = null;
		try {
//			out = new FileOutputStream(filePath);
			
			final Map<String, String> nameMap = cachedService.getOrgUserNames(orgId);
			
			// 格式化导出数据列
			IExportFormatter formatter = new IExportFormatter() {

				@Override
				public Object format(String key, Object value) {
					
					// 将状态字段转换成中文
					if ("status".equals(key)) {
						return CreditLeadInfoBean.maps.get(key).get(value);
					}
					
					if ("inchargeAcc".equals(key)) {
						if (null == value) {
							return "";
						}
						return StringUtils.isNotBlank(value.toString()) ? nameMap.get(value.toString()) : "";
					}
					
					if ("ownerAcc".equals(key)) {
						return StringUtils.isNotBlank(value.toString()) ? nameMap.get(value.toString()) : "";
					}
					
					return value;
				}
				
			};
			
			ExportExcelUtil.exportExcel(headers, columns, list, out, "yyyy-MM-dd", formatter);
		} catch (Exception e) {
			logger.error("导出数据异常！", e);
			throw new SysRunException("导出数据异常！");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("输出流关闭异常！", e);
				}
			}
		}
		// LogExportInfoBean bean = new LogExportInfoBean();
		// bean.setExportId(exportId);
		// bean.setAccount(account);
		// bean.setOrgId(orgId);
		// bean.setExportCustType(exportType);
		// bean.setExportSearchContext(searchContext);
		// bean.setExportNum(list.size());
		// bean.setDeletedFile(0);
		// bean.setExportFilePath(filePath);
		// bean.setExportDate(new Date());
		// insert(bean);
//		return exportId;
		return "";
	}
	
}
