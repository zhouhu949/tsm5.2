package com.qftx.tsm.credit.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.base.util.GuidUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.credit.bean.ImportLeadInfoBean;
import com.qftx.tsm.credit.bean.ImportLeadResultBean;
import com.qftx.tsm.credit.bean.ImportLeadResultDetailsBean;
import com.qftx.tsm.credit.dao.ImportLeadInfoMapper;
import com.qftx.tsm.credit.dto.ImportLeadInfoDto;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.ResCustInfoDetailBean;
import com.qftx.tsm.cust.service.ResCustInfoDetailService;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.imp.dto.ImportRepeatDto;
import com.qftx.tsm.imp.enums.ImportResultEnum;


@Service
public class ImportLeadInfoService {
	@Autowired ImportLeadInfoMapper mapper;
	@Autowired ImportLeadFileService importLeadFileService;
	@Autowired ResCustInfoService resService;
	@Autowired ImportLeadResultService resultService;
	@Autowired ImportLeadResultDetailsService leadResultDetailsService;
	@Autowired private ResCustInfoService resCustInfoService;
	@Autowired private ResCustInfoDetailService resCustInfoDetailService;
	Logger logger =Logger.getLogger(ImportLeadInfoService.class);

	/**
	 * 导入需要排除的字段
	 */
	public static List<Object> IMPORT_EXCLUDE_FIELDCODES = new ArrayList<Object>();
	static {
		IMPORT_EXCLUDE_FIELDCODES.add("batch");
		IMPORT_EXCLUDE_FIELDCODES.add("status");
		IMPORT_EXCLUDE_FIELDCODES.add("ownerAcc");
		IMPORT_EXCLUDE_FIELDCODES.add("createDate");
		IMPORT_EXCLUDE_FIELDCODES.add("importDeptId");
		IMPORT_EXCLUDE_FIELDCODES.add("importDeptName");
		IMPORT_EXCLUDE_FIELDCODES.add("auditStatus");
		IMPORT_EXCLUDE_FIELDCODES.add("submitTime");
		IMPORT_EXCLUDE_FIELDCODES.add("auditTime");
		IMPORT_EXCLUDE_FIELDCODES.add("isDel");
		IMPORT_EXCLUDE_FIELDCODES.add("rciId");
		IMPORT_EXCLUDE_FIELDCODES.add("auditAcc");
		IMPORT_EXCLUDE_FIELDCODES.add("auditNode");
		IMPORT_EXCLUDE_FIELDCODES.add("allNode");
	}
	
	/**
	 *  查询 import_res_info
	 *  */
	public List<ImportLeadInfoBean> query(String orgId){
		Map<String , String> params = new HashMap<String, String>();
		params.put("orgId", orgId);
		return mapper.query(params);
	}
	
	/**
	 *  查询 import_res_info
	 *  @param fileId ：import_res_file 导入批次ID
	 *  */
	public List<ImportLeadInfoBean> queryByFileId(String orgId,String fileId){
		ImportLeadInfoBean entity= new ImportLeadInfoBean();
		entity.setOrgId(orgId);
		entity.setFileId(fileId);
		entity.setIsDel(0);
		return mapper.findByCondtion(entity);
	}
	
	//  组装资源表BEAN
	public ResCustInfoBean getNewCustBean(Class<ResCustInfoBean> clazz,String orgId,String resGroupId,Integer resStatus,
			String account,int state,String resCustId,String mark,String processId){
		ResCustInfoBean resInfo;
		try {
			resInfo = clazz.newInstance();
			resInfo.setResCustId(resCustId);
			//resInfo.setFax(fax.replace(",", ""));
			resInfo.setResGroupId(resGroupId);
			resInfo.setOrgId(orgId);
			
			resInfo.setOpreateType(AppConstant.OPREATE_TYPE2.intValue());
			resInfo.setStatus(resStatus);
			resInfo.setType(AppConstant.CUST_TYPE1.intValue());
			resInfo.setIsDel(0);
			resInfo.setInputStatus(0);
			resInfo.setFilterType(AppConstant.FILTER_TYPE0.intValue());
			resInfo.setInputAcc(account);
			resInfo.setState(state);
			resInfo.setLifeCode(SysBaseModelUtil.getModelId());
			if(resStatus!=null && resStatus==2){
				resInfo.setOwnerAcc(account);
				resInfo.setOwnerStartDate(new Date());
				if(StringUtils.isNotBlank(mark) && !"1".equals(mark)){
					resInfo.setType(AppConstant.CUST_TYPE2.intValue());
					if("3".equals(mark)){ // 入库签约库
						resInfo.setStatus(6);			
					}else if("2".equals(mark)){
						resInfo.setStatus(3);	
					}
					if(StringUtils.isNotBlank(processId) && "2".equals(mark)){ // 设置销售进程ID
						resInfo.setLastOptionId(processId);
					}
				}
			}
			resInfo.setInputDate(new Date());
			return resInfo;
		} catch (Exception e) {
			logger.error(orgId+"忽略并导入异常！！", e);
		} 
		return null;
	}
	
	//  组装联系人BEAN
	public ResCustInfoDetailBean getNewCustDetailBean(Class<ResCustInfoDetailBean> clazz,String orgId,String resCustId){
		ResCustInfoDetailBean resInfoDetail;
		try {
			resInfoDetail = clazz.newInstance();
			resInfoDetail.setTscidId(SysBaseModelUtil.getModelId());
			resInfoDetail.setRciId(resCustId);
			resInfoDetail.setOrgId(orgId);
			resInfoDetail.setIsDefault(1);
			resInfoDetail.setInputtime(new Date());
			resInfoDetail.setIsDel(0);
			return resInfoDetail;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/* 插入*/
	// 批量 500条提交一次
	public void insertBatch(String orgId,String fileId,List<String> jsonDatas,Date date){
		try{
			List<ImportLeadInfoBean> beans = new ArrayList<ImportLeadInfoBean>();
			for (String jsonData : jsonDatas) {
				ImportLeadInfoBean bean = createBean(orgId, fileId, jsonData, date);
				beans.add(bean);
				if(beans != null && beans.size() == 500){
					mapper.insertBatch(beans);
					beans = new ArrayList<ImportLeadInfoBean>();
				}
			}
			if(beans != null && beans.size() >0){
				mapper.insertBatch(beans);
			}
		}catch(Exception e){
			logger.error("入库临时表异常！", e);
		}
	}
	
	// 组装 import_res_info BEAN
	public ImportLeadInfoBean createBean(String orgId,String fileId,String jsonData,Date date){
		ImportLeadInfoBean bean = new ImportLeadInfoBean();
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setOrgId(orgId);
		bean.setFileId(fileId);
		bean.setJsonData(jsonData);
		bean.setInputtime(date);
		bean.setUpdatetime(date);
		bean.setStatus("0");
		bean.setIsDel(0);
		return bean;
	}
	
	/* 插入*/
	public ImportLeadInfoBean insert(String orgId,String fileId,String jsonData,Date date){
		ImportLeadInfoBean bean = createBean(orgId, fileId, jsonData, date);
		mapper.insert(bean);
		return bean;
	}
	
	/** 
	 * 设置 【格式验证后】 导入结果信息，以及错误详细信息
	 * @param resId 临时资源ID
	 * @param sysFileId 导入批次ID
	 *  @param bean	导入结果信息
	 *  @param beans 导入错误详细信息
	 *  */
	public void setImpResult(ImportLeadResultBean bean ,List<ImportLeadResultDetailsBean> beans,String[] resultEnums,String orgId,String sysFileId,String resId){
		String rtnMsg = resultEnums[1];
		String resultEnum = resultEnums[0];
		ImportLeadResultDetailsBean detailBean = new ImportLeadResultDetailsBean();
		detailBean.setId(SysBaseModelUtil.getModelId());
		detailBean.setOrgId(orgId);
		detailBean.setResId(resId);
		detailBean.setFileId(sysFileId);
		detailBean.setIsDel(0);
		if(ImportResultEnum.PHONE_FORMART.getState().equals(resultEnum)){ // 号码格式错误
			detailBean.setErroCode(ImportResultEnum.PHONE_FORMART.getState());
			detailBean.setErrorMsg(rtnMsg);
			bean.setPhoneFormart(bean.getPhoneFormart()+1);
		}else if(ImportResultEnum.DEFECT_REQUIRED.getState().equals(resultEnum)){ // 缺失必填项
			detailBean.setErroCode(ImportResultEnum.DEFECT_REQUIRED.getState());
			detailBean.setErrorMsg(rtnMsg);
			bean.setDefectRequired(bean.getDefectRequired()+1);
		}else if(ImportResultEnum.OWN_ILLEGAL_CHAR.getState().equals(resultEnum)){ // 拥有非法字符
			detailBean.setErroCode(ImportResultEnum.OWN_ILLEGAL_CHAR.getState());
			detailBean.setErrorMsg(rtnMsg);
			bean.setOwnIllegalChar(bean.getOwnIllegalChar()+1);
		}else if(ImportResultEnum.FORMART_ERROR.getState().equals(resultEnum)){ // 格式错误
			detailBean.setErroCode(ImportResultEnum.FORMART_ERROR.getState());
			detailBean.setErrorMsg(rtnMsg);
			bean.setFormartError(bean.getFormartError()+1);
		}else if(ImportResultEnum.PHONE_REPEAT.getState().equals(resultEnum)){ // 号码重复
			detailBean.setErroCode(ImportResultEnum.PHONE_REPEAT.getState());
			detailBean.setErrorMsg(rtnMsg);
			bean.setPhoneRepeat(bean.getPhoneRepeat()+1);
//		}else if(ImportResultEnum.CUST_NAME_REPEAT.getState().equals(resultEnum)){ // 单位名称重复
//			detailBean.setErroCode(ImportResultEnum.CUST_NAME_REPEAT.getState());
//			detailBean.setErrorMsg(rtnMsg);
//			bean.setCustNameRepeat(bean.getCustNameRepeat()+1);
//		}else if(ImportResultEnum.COMPANY_URL_REPEAT.getState().equals(resultEnum)){ // 单位主页重复
//			detailBean.setErroCode(ImportResultEnum.COMPANY_URL_REPEAT.getState());
//			detailBean.setErrorMsg(rtnMsg);
//			bean.setCompanyUrlRepeat(bean.getCompanyUrlRepeat()+1);
		}		
		beans.add(detailBean);
	}
	
	/**
	 * 验证资源重复
	 * @param bean 待验证的资源
	 * @param beans 保存好的资源
	 * @param dics 资源去重验证 [isOpen,isPhone,isCompanyName,isUnithome,isImpSet, limits]
	 * @param orgId
	 * @param isSys 0:个人，1:企业
	 * @return [导入结果，如有错误显示错误信息]
	 * @create  2016-2-27 下午5:44:50 zwj
	 * @history  4.x
	 */
	public String[] getRepeat(ResCustInfoBean bean,List<ResCustInfoBean>beans,String[] dics,String orgId,Integer isSys){
		String resultEnum = null;
		String rtnMsg = null;
		try{
			// 1：先与本次导入的数据进行去重
			if(beans != null && beans.size() >0){
				for(int i = 0; i<beans.size(); i++){
					if(isSys == 0){ // 个人资源
						if(!"1".equals(dics[1])){
							if(StringUtils.isNotBlank(bean.getMobilephone())){
								if(bean.getMobilephone().equals(beans.get(i).getMobilephone()) || bean.getMobilephone().equals(beans.get(i).getTelphone())){
									rtnMsg = "常用号码重复";
									resultEnum= ImportResultEnum.PHONE_REPEAT.getState();
									return new String[]{resultEnum,rtnMsg};
								}
							}
							if(StringUtils.isNotBlank(bean.getTelphone())){
								if(bean.getTelphone().equals(beans.get(i).getMobilephone()) || bean.getTelphone().equals(beans.get(i).getTelphone())){
									rtnMsg = "备用号码重复";
									resultEnum= ImportResultEnum.PHONE_REPEAT.getState();
									return new String[]{resultEnum,rtnMsg};
								}
							}
						}
						if(!"0".equals(dics[2]) && StringUtils.isNotBlank(bean.getCompany())){
							if(bean.getCompany().equals(beans.get(i).getCompany())){
								rtnMsg = "单位名称重复";
								resultEnum= ImportResultEnum.CUST_NAME_REPEAT.getState();
								return new String[]{resultEnum,rtnMsg};
							}
						}
					}else{ // 企业资源的单位名称 是资源表中的客户名称
						if(!"0".equals(dics[2]) && StringUtils.isNotBlank(bean.getName())){
							if(bean.getName().equals(beans.get(i).getName())){
								rtnMsg = "客户名称重复";
								resultEnum= ImportResultEnum.CUST_NAME_REPEAT.getState();
								return new String[]{resultEnum,rtnMsg};
							}
						}
					}
					
					
					if(!"0".equals(dics[3]) && StringUtils.isNotBlank(bean.getUnithome())){
						if(bean.getUnithome().equals(beans.get(i).getUnithome())){
							rtnMsg = "公司网站重复";
							resultEnum= ImportResultEnum.COMPANY_URL_REPEAT.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}
			}
			Integer repeatCount = 0;
			// 2：再与数据中存在的数据进行去重
			ImportRepeatDto repeatDto = new ImportRepeatDto();
			repeatDto.setOrgId(orgId);
			repeatDto.setIsOpen(dics[0]);
			repeatDto.setIsPhone(dics[1]);
			repeatDto.setIsCompanyName(dics[2]);
			repeatDto.setIsUnithome(dics[3]);
			repeatDto.setMobelPhone(bean.getMobilephone());
			repeatDto.setTelPhone(bean.getTelphone());
			repeatDto.setComPanyName(bean.getCompany());
			repeatDto.setCustName(bean.getName());
			repeatDto.setUnitHome(bean.getUnithome());
			repeatDto.setIsSys(isSys);
			if(isSys == 0){ // 个人资源
				if(!"1".equals(repeatDto.getIsPhone())&&(StringUtils.isNotBlank(repeatDto.getMobelPhone()) || StringUtils.isNotBlank(repeatDto.getTelPhone()))){ // 号码去重
					repeatCount = resCustInfoService.getRepeatByPhone(repeatDto);
					if(repeatCount >0){
						rtnMsg = "号码重复!";
						resultEnum= ImportResultEnum.PHONE_REPEAT.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
				if(!"0".equals(repeatDto.getIsCompanyName()) && StringUtils.isNotBlank(repeatDto.getComPanyName())){ // 单位名称去重
					repeatCount = resCustInfoService.getRepeatByName(repeatDto);
					if(repeatCount >0){
						rtnMsg = "单位名称重复!";
						resultEnum= ImportResultEnum.CUST_NAME_REPEAT.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			}else{
				if(!"0".equals(repeatDto.getIsCompanyName()) && StringUtils.isNotBlank(repeatDto.getCustName())){ // 企业资源 客户名称去重
					repeatCount = resCustInfoService.getRepeatByName(repeatDto);
					if(repeatCount >0){
						rtnMsg = "客户名称重复!";
						resultEnum= ImportResultEnum.CUST_NAME_REPEAT.getState();
						return new String[]{resultEnum,rtnMsg};
					}
				}
			}
			
			if(!"0".equals(repeatDto.getIsUnithome()) && StringUtils.isNotBlank(repeatDto.getUnitHome())){ // 单位主页去重
				repeatCount = resCustInfoService.getRepeatByUnitHome(repeatDto);
				if(repeatCount >0){
					rtnMsg = "公司网站重复!";
					resultEnum= ImportResultEnum.COMPANY_URL_REPEAT.getState();
					return new String[]{resultEnum,rtnMsg};
				}
			}
		}catch(Exception e){
			logger.error("验证资源重复异常", e);
		}		
		resultEnum= ImportResultEnum.SUCCESS.getState();
		return new String[]{resultEnum,rtnMsg};
	}
	
	
	/**
	 * 验证 联系人资源重复
	 * @param bean 待验证的资源
	 * @param beans 保存好的资源
	 * @param dics 资源去重验证
	 * @param orgId
	 * @return [导入结果，如有错误显示错误信息]
	 * @create  2016-2-27 下午5:44:50 zwj
	 * @history  4.x
	 */
	public String[] getComRepeat(ResCustInfoDetailBean bean,List<ResCustInfoDetailBean>beans,String[] dics,String orgId){
		String resultEnum = null;
		String rtnMsg = null;
		try{
			// 1：先与本次导入的数据进行去重
			for(int i = 0; i<beans.size(); i++){
				if(!"1".equals(dics[1])){
					if(StringUtils.isNotBlank(bean.getTelphone())){
						if(bean.getTelphone().equals(beans.get(i).getTelphone()) || bean.getTelphone().equals(beans.get(i).getTelphonebak())){
							rtnMsg = "常用号码重复";
							resultEnum= ImportResultEnum.PHONE_REPEAT.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
					if(StringUtils.isNotBlank(bean.getTelphonebak())){
						if(bean.getTelphonebak().equals(beans.get(i).getTelphonebak()) || bean.getTelphonebak().equals(beans.get(i).getTelphone())){
							rtnMsg = "备用号码重复";
							resultEnum= ImportResultEnum.PHONE_REPEAT.getState();
							return new String[]{resultEnum,rtnMsg};
						}
					}
				}
			}
			// 2：再与数据中存在的数据进行去重
			Integer repeatCount = 0;
			ImportRepeatDto repeatDto = new ImportRepeatDto();
			repeatDto.setOrgId(orgId);
			repeatDto.setIsOpen(dics[0]);
			repeatDto.setIsPhone(dics[1]);
			repeatDto.setMobelPhone(bean.getTelphone());
			repeatDto.setTelPhone(bean.getTelphonebak());
			if(!"1".equals(repeatDto.getIsPhone())&&(StringUtils.isNotBlank(repeatDto.getMobelPhone()) || StringUtils.isNotBlank(repeatDto.getTelPhone()))){ // 号码去重
				repeatCount = resCustInfoDetailService.getRepeatByPhone(repeatDto);
				if(repeatCount > 0){
					rtnMsg = "号码重复!";
					resultEnum= ImportResultEnum.PHONE_REPEAT.getState();
					return new String[]{resultEnum,rtnMsg};
				}
			}
		}catch(Exception e){
			logger.error("联系人资源重复异常", e);
		}
		resultEnum= ImportResultEnum.SUCCESS.getState();
		return new String[]{resultEnum,rtnMsg};
	}
	
	/** 
	 * 批量入库 资源表&联系人表 
	 * @param rcibs 资源表BEAN集合
	 * @param rcidbs	联系人表BEAN集合
	 * @param isSys 0:个人资源，1：企业资源
	 * @create  2016-2-29 下午1:39:02 zwj
	 * @history  4.x
	 */
	public void resCustInsertBatch(List<ResCustInfoBean>rcibs,List<ResCustInfoDetailBean>rcidbs,Integer isSys){
		if(rcibs !=null && rcibs.size()>0){
			resCustInfoService.insertBatch(rcibs);
			if(isSys == 1){ // 企业资源 需要入库联系人
				if(rcidbs != null && rcidbs.size() >0){
					resCustInfoDetailService.insertBatch(rcidbs);
				}				
			}
		}		
	}
	
	/** 
	 * 批量入库 导入分析结果_详情 表 
	 * 每500条提交一次
	 * */
	public void resultDetailInsertBatch(List<ImportLeadResultDetailsBean>irrdbs){
		if(irrdbs != null && irrdbs.size() >0){
			int size=irrdbs.size();
			int count = size/500;
			int yu = size%500;			
			for(int i=0;i<=count;i++){
				if(i<count){
					logger.debug("batchInsert:["+i*500+","+(i+1)*500+")");
					leadResultDetailsService.insertBatch(irrdbs.subList(i*500, (i+1)*500));
				}else if(yu>0){
					logger.debug("batchInsert:["+i*500+","+size+")");
					leadResultDetailsService.insertBatch(irrdbs.subList(i*500, size));
				}
			}
		}
	}
	/** 查询 导入错误资源 */
	public List<ImportLeadInfoDto> getErrorLeadInfos(Map<String,Object>params){
		return mapper.findErrorLeadInfos(params);
	}
	
	/** 查询 导入错误资源 */
	public List<ImportLeadInfoBean> getErrorLeadInfos1(Map<String,Object>params){
		return mapper.findErrorLeadInfos1(params);
	}
	/** 
	 * 【忽略并导入】
	 * @param fileId 导入批次ID
	 * @param errorCode  错误类型 ImportResultEnum枚举定义
	 *  @param state 0:个人资源，1：企业资源
	 *  @param groupId 部门ID
	 *  @param account 用户账号
	 *  */
//	public void ignoreImp(String orgId,String fileId,String errorCode,Integer state,String groupId,String account){
//		try{
//			// 查询导入出错的临时资源
//			Map<String,Object>map = new HashMap<String, Object>();
//			map.put("orgId", orgId);
//			map.put("fileId", fileId);
//			map.put("errorCode", errorCode);
//			List<ImportLeadInfoBean> list =getErrorLeadInfos1(map);
//			// 查询 资源导入_文件表 import_res_file
//			ImportLeadFileBean importResFileBean = importLeadFileService.queryById(orgId, fileId);
//			// 资源分组ID
//			String resGroupId = importResFileBean.getResGroupId();
//			// 导入类型：1： 未分配   2：已分配且未跟进
//			Integer resStatus  = importResFileBean.getResStatus();
//			String mark = importResFileBean.getMark();
//			String processId = importResFileBean.getProcessId();
//			// 匹配好的字段
//			String mapperJsonData = importResFileBean.getMapper();
//			String[] mapperList = JsonUtil.getStringArrayJson(mapperJsonData);
//			List<ResCustInfoBean> batchList = new ArrayList<ResCustInfoBean>();
//			List<ResCustInfoDetailBean> detailBatchList = new ArrayList<ResCustInfoDetailBean>();
//			Class<ResCustInfoBean> clazz = ResCustInfoBean.class;	
//			List<String> ids = new ArrayList<String>();
//			
//			for(ImportLeadInfoBean importTempResBean:list){
//				ids.add(importTempResBean.getId()); // 存储临时资源ID
//				String[] valueList = JsonUtil.getStringArrayJson(importTempResBean.getJsonData());
//				String resCustId = SysBaseModelUtil.getModelId();
//					//反射生成resbean
//					ResCustInfoBean bean = getNewCustBean(clazz, orgId, resGroupId, resStatus, account,state,resCustId,mark,processId);
//					bean.setImportDeptId(groupId);
//					ResCustInfoDetailBean detailBean = null;
//					if(state == 1){ // 企业资源时，新增一个联系人BEAN
//						Class<ResCustInfoDetailBean> detailClazz = ResCustInfoDetailBean.class;	
//						detailBean= getNewCustDetailBean(detailClazz, orgId,resCustId);
//					}
//					/********************** 1: 组装BEAN ,以及格式验证【开始】*************************** */
//					for (int i=0;i<mapperList.length;i++) {
//						if(StringUtils.isNotBlank(mapperList[i])&&StringUtils.isNotBlank(valueList[i])){
//							try {						
//								String field = mapperList[i].split("_")[0]; // 匹配字段
//								String isSys = mapperList[i].split("_")[1]; // 0:个人,1:企业,2:联系人
//								String valueVal = valueList[i].trim(); // 去掉空格
//								if(AppConstant.sex.equals(field)){
//									if("男".equals(valueVal)){
//										valueVal="1";
//									}else if("女".equals(valueVal)){
//										valueVal="2";
//									}else{
//										valueVal=null;
//									}
//								}else if(AppConstant.birthday.equals(field)){ // 生日
//									valueVal=RegexUtil.repBirthday(valueVal);
//									
//								}else if(AppConstant.telphone.equals(field) 
//										|| AppConstant.mobilephone.equals(field)
//										|| AppConstant.contacts_telphonebak.equals(field)){
//									valueVal=ImportVerUtil.formatPhone(valueVal,orgId);
//								}
//								/** 使用 BeanUtils 时，转换date类型会出错，需要以下这段代码做转换 */
//								ConvertUtils.register(new Converter(){   //注册类型转换器
//									   public Object convert(Class type, Object value) {
//									    String str = (String) value;
//									    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//									    try {
//									     return df.parse(str);
//									    } catch (ParseException e) {     
//									     throw new RuntimeException(e);
//									    }   
//									   }}, Date.class);
//								/********************************************************************/
//								if("0".equals(isSys)){							
//									BeanUtils.setProperty(bean, field, valueVal);
//								}else if("1".equals(isSys)){
//									BeanUtils.setProperty(bean, field, valueVal);					
//								}else if("2".equals(isSys) ){
//									BeanUtils.setProperty(detailBean, field, valueVal);		
//								}			
//							} catch (Exception e) {
//								logger.error("忽略并导入组装BEAN ,格式验证异常！",e);
//								break;
//							} 
//						}
//					}		
//					if(state == 0){
//						batchList.add(bean);
//						if(batchList!=null && batchList.size()==500){ // 每500条入库一次 
//							resCustInsertBatch(batchList,null,0);
//							batchList.clear(); // 提交成功后 重置list
//						}
//					}else{
//						if(StringUtils.isNotBlank(detailBean.getTelphone())){
//							bean.setMobilephone(detailBean.getTelphone());
//						}
//						if(StringUtils.isNotBlank(detailBean.getTelphonebak())){
//							bean.setTelphone(detailBean.getTelphonebak());
//						}
//						bean.setMainLinkman(detailBean.getName()); // 关联主要联系人
//						batchList.add(bean);
//						detailBatchList.add(detailBean);
//						if(batchList!=null && batchList.size()==500){ // 每500条入库一次 
//							resCustInsertBatch(batchList,detailBatchList,1);
//							batchList.clear();
//							detailBatchList.clear();
//						}
//					}
//				}
//					// 不足500条的数据，再循环结束后，入库资源表，联系人表
//					if(batchList!=null && batchList.size() >0){
//						if(state == 1){
//							resCustInsertBatch(batchList,detailBatchList,1);
//						}else{
//							resCustInsertBatch(batchList,null,0);
//						}			
//						batchList.clear();
//						detailBatchList.clear();
//					}
//					// 修改导入结果
//					if(ids !=null && ids.size()>0){	
//						ImportLeadResultBean entity = new ImportLeadResultBean();
//						entity.setOrgId(orgId);
//						entity.setFileId(fileId);
//						entity = resultService.findByCondtion(entity);
//						if(entity != null){
//							entity.setFailNum(entity.getFailNum() - ids.size());
//							entity.setSuccessNum(entity.getSuccessNum()+ids.size());
//							if(ImportResultEnum.PHONE_FORMART.getState().equals(errorCode)){
//								entity.setPhoneFormart(0);
//							}else if(ImportResultEnum.PHONE_REPEAT.getState().equals(errorCode)){
//								entity.setPhoneRepeat(0);
////							}else if(ImportResultEnum.CUST_NAME_REPEAT.getState().equals(errorCode)){
////								entity.setCustNameRepeat(0);
////							}else if(ImportResultEnum.COMPANY_URL_REPEAT.getState().equals(errorCode)){
////								entity.setCompanyUrlRepeat(0);
//							}else if(ImportResultEnum.DEFECT_REQUIRED.getState().equals(errorCode)){
//								entity.setDefectRequired(0);
//							}else if(ImportResultEnum.OWN_ILLEGAL_CHAR.getState().equals(errorCode)){
//								entity.setOwnIllegalChar(0);
//							}else if(ImportResultEnum.FORMART_ERROR.getState().equals(errorCode)){
//								entity.setFormartError(0);
//							}					
//							entity.setStatus("1");
//							resultService.modfiyByFileId(entity);
//							// 删除掉导入错误信息
//							leadResultDetailsService.deleteByBatch(orgId,ids);
//						}						
//					}	
//		}catch(Exception e){
//			logger.error(fileId+"忽略并导入异常！",e);
//		}
//	
//	}
	
}
