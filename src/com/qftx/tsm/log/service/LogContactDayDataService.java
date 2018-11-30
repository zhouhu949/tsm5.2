package com.qftx.tsm.log.service;

import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.log.bean.LogContactDayDataBean;
import com.qftx.tsm.log.dao.LogContactDayDataMapper;
import com.qftx.tsm.log.dto.LogContactDayDataDto;
import com.qftx.tsm.log.util.ContactUtil;
import com.qftx.tsm.option.bean.OptionBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.*;

@Service
public class LogContactDayDataService {
	
	@Autowired
	private LogContactDayDataMapper logContactDayDataMapper;
	@Autowired
	private ResCustInfoMapper resCustInfoMapper;
	@Autowired
	private CachedService cachedService;
	
	/** 
	 * 添加联系日志
	 * @param operateType 类型见 ContactUtil
	 * @param orgId 单位ID
	 * @param custId 客户ID
	 * @param ownerAcc 拥有者
	 * @param initProcessId 初始化销售进程
	 * @param currProcessId 当前销售进程
	 * @create  2017年7月31日 上午9:01:39 lixing
	 * @history  
	 */
	public void addLogContactDayData(String operateType,String orgId,String custId,String ownerAcc,
			String initProcessId,String currProcessId){
		Calendar cal = Calendar.getInstance();
		LogContactDayDataBean initBean = ContactUtil.getInstance().getParamBean(operateType);
		LogContactDayDataBean bean = new LogContactDayDataBean();
		BeanUtils.copyProperties(initBean, bean);
		bean.setOrgId(orgId);
		bean.setCustId(custId);
		bean.setOwnerAcc(ownerAcc);
		bean.setCurrDate(new Date(cal.getTime().getTime()));
		List<LogContactDayDataBean> beans = logContactDayDataMapper.findByCondtion(bean);
		if(beans.size() > 0){
			LogContactDayDataBean updateBean = beans.get(0);
			updateBean.setType(bean.getType());
			updateBean.setStatus(bean.getStatus());
			updateBean.setCurrProcessId(currProcessId);
			updateBean.setContactTime(new Timestamp(cal.getTime().getTime()));
			logContactDayDataMapper.update(updateBean);
		}else{
			bean.setInitProcessId(StringUtils.isNotBlank(initProcessId) ? initProcessId : "0");
			bean.setCurrProcessId(currProcessId);
			bean.setId(SysBaseModelUtil.getModelId());
			bean.setContactTime(new Timestamp(cal.getTime().getTime()));
			logContactDayDataMapper.insert(bean);
		}
	}
	
	
	public List<LogContactDayDataDto> getLogDetailInfoListPage(LogContactDayDataDto dto){
		List<LogContactDayDataDto> list = logContactDayDataMapper.findLogDetailInfoListPage(dto);
		if(list!=null && list.size()>0){
			findCustInfo(list, dto);
		}
		return list;
	}
	
	public void findCustInfo(List<LogContactDayDataDto> list,LogContactDayDataDto dto){
		String orgId = dto.getOrgId();
		List<ResCustInfoDto> custs = resCustInfoMapper.findCustPoolList(getCustIds(list), orgId);
		Map<String, ResCustInfoDto> cust_map = new HashMap<String, ResCustInfoDto>();
		for(ResCustInfoDto custDto : custs) cust_map.put(custDto.getResCustId(), custDto);
		Map<String, String> nameMap = cachedService.getOrgUserNames(orgId);
		// 从缓存读取销售进程数据
		List<OptionBean> options = cachedService.getOptionList(AppConstant.SALES_TYPE_ONE, orgId);
		Map<String, String> saleProcMap = cachedService.changeOptionListToMap(options);
		// 从缓存读取客户类型数据
		List<OptionBean> typeOptions = cachedService.getOptionList(AppConstant.SALES_TYPE_TWO, orgId);
		Map<String, String> typeNameMap = cachedService.changeOptionListToMap(typeOptions);
		ResCustInfoDto cust;
		for(LogContactDayDataDto dataDto : list){
			cust = cust_map.get(dataDto.getCustId());
			dataDto.setCustName(cust.getName());
			dataDto.setCompany(cust.getCompany());
			if(cust.getNextFollowDate() != null){
				dataDto.setNextFollowDate(DateUtil.formatDate(cust.getNextFollowDate(),DateUtil.DATE_DAY));
				dataDto.setNextFollowDateAll(DateUtil.formatDate(cust.getNextFollowDate(),DateUtil.Data_ALL));
			}
			
			if(dto.getChangeType() == 2){
				dataDto.setSignDate(DateUtil.formatDate(dataDto.getCurrDate(),DateUtil.DATE_DAY));
			}
			
			if(dto.getCustType() == 1 && dto.getChangeType() == 1 && dataDto.getContactTime() != null){
				dataDto.setTaoDate(DateUtil.formatDate(dataDto.getContactTime(),DateUtil.DATE_DAY));
				dataDto.setTaoDateAll(DateUtil.formatDate(dataDto.getContactTime(),DateUtil.Data_ALL));
			}
			
			if(dto.getCustType() == 2 && dto.getChangeType() == 1){
				if(StringUtils.isNotBlank(cust.getLastOptionId())){
					dataDto.setSaleProcessName(saleProcMap.get(cust.getLastOptionId()));
				}
			}else{
				if(StringUtils.isNotBlank(dataDto.getCurrProcessId())){
					dataDto.setSaleProcessName(saleProcMap.get(dataDto.getCurrProcessId()));
				}
			}
			
			if(StringUtils.isNotBlank(dataDto.getInitProcessId())){
				dataDto.setInitSaleProcessName(saleProcMap.get(dataDto.getInitProcessId()));
			}
			if(StringUtils.isNotBlank(cust.getLastCustTypeId())){
				dataDto.setCustTypeName(typeNameMap.get(cust.getLastCustTypeId()));
			}
			if(StringUtils.isNotBlank(cust.getOwnerAcc())){
				dataDto.setOwnerName(nameMap.get(cust.getOwnerAcc()));
			}
			if(cust.getIsDel() == 0){
				if(cust.getStatus() == 4 || cust.getStatus() == 5){
					dataDto.setShowCard(1);
				}else if(dataDto.getOwnerAcc().equals(cust.getOwnerAcc())){
					dataDto.setShowCard(1);
				}else{
					dataDto.setShowCard(0);
				}
			}else{
				dataDto.setShowCard(0);
			}
		}
	}
	
	public List<String> getCustIds(List<LogContactDayDataDto> list){
		List<String> ids = new ArrayList<String>();
		for(LogContactDayDataDto dto : list) ids.add(dto.getCustId());
		return ids;
	}
}
