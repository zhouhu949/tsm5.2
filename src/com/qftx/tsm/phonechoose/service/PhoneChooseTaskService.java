
package com.qftx.tsm.phonechoose.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.cust.dao.ResourceGroupMapper;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.phonechoose.bean.PhoneChooseTaskBean;
import com.qftx.tsm.phonechoose.dao.PhoneChooseFailDetailBeanMapper;
import com.qftx.tsm.phonechoose.dao.PhoneChooseTaskBeanMapper;
import com.qftx.tsm.phonechoose.dto.PhoneChooseTaskBeanDto;

@Service
public class PhoneChooseTaskService {
	private static final Logger logger = Logger.getLogger(PhoneChooseTaskService.class);
	
	@Autowired PhoneChooseTaskBeanMapper mapper;
	@Autowired ResCustInfoService resService;
	@Autowired ResourceGroupMapper resourceGroupMapper;
	@Autowired PhoneChooseFailDetailBeanMapper phoneChooseFailDetailBeanMapper;
	
	/**
	 * @param resGroupId
	 * @param psCount
	 * @return			主键
	 */
	public String createPhoneChooseTask(ShiroUser user, String resGroupId, Integer psCount) {
		PhoneChooseTaskBean bean = new PhoneChooseTaskBean();
		bean.setTaskId(SysBaseModelUtil.getModelId());
		bean.setResGroupId(resGroupId);
		bean.setTotalCount(psCount);
		bean.setStatus(PhoneChooseTaskBean.STATUS_BEGIN);
		bean.setProcessedCount(0);
		bean.setOrgId(user.getOrgId());
		bean.setInputAcc(user.getAccount());
		bean.setInputDate(new Date());
		mapper.insert(bean);
		return bean.getTaskId();
	}
	
	/**
	 * 更新任务信息
	 * 
	 * @param account
	 * @param taskId
	 * @param processedCount
	 * @param status
	 * @return
	 */
	public String updateTask(String account, String taskId, Integer processedCount, Byte status) {
		PhoneChooseTaskBean bean = new PhoneChooseTaskBean();
		bean.setTaskId(taskId);
		bean.setProcessedCount(processedCount);
		bean.setStatus(status);
		bean.setUpdateAcc(account);
		bean.setUpdateDate(new Date());
		mapper.updateByPrimaryKeySelective(bean);
		return bean.getTaskId();
	}
	
	
	/**
	 * 查询未完成的任务数量
	 * 
	 * @param orgId
	 * @param resGroupId
	 * @return
	 */
	public int findUnFinishTaskCount(String orgId, String resGroupId) {
		return mapper.findUnFinishTaskCount(orgId, resGroupId);
	}
	
	/**
	 * 筛查结果
	 * @param orgId
	 * @param resGroupId
	 * @return
	 */
	public List<PhoneChooseTaskBeanDto> findScreenResult(String orgId, PhoneChooseTaskBeanDto dto){
		Map<String , Object> params = new HashMap<String, Object>();
		params.put("orgId", orgId);
		params.put("moduleId", dto.getModuleId());
		if("first".equals(dto.getType())){
			params.put("symbol", ">");
			params.put("orderKey", " INPUT_DATE asc ");
		}else{
			params.put("symbol", "< ");
			params.put("orderKey", " INPUT_DATE desc ");
		}
		if(StringUtils.isNotBlank(dto.getImpTime())){
			params.put("inputDate", dto.getImpTime());
		}
		if(StringUtils.isNotBlank(dto.getInputAcc())){
			params.put("inputAcc", dto.getInputAcc());
		}else if(StringUtils.isNotBlank(dto.getOwnerAcc())){
			params.put("inputAcc", dto.getOwnerAcc());
		}
		
		List<PhoneChooseTaskBeanDto> list = mapper.findScreenResult(params);
		for (PhoneChooseTaskBeanDto dto2 : list) {
			String taskId = dto2.getTaskId();
			List<Map<String, Integer>> listmap = (List<Map<String, Integer>>) phoneChooseFailDetailBeanMapper.countScreenFailNum(orgId, taskId);
			if(listmap!=null&&listmap.size()>0){
				Map<String, String> resMap = new HashMap<>();
				if(StringUtils.isNotBlank(dto2.getUndisposedReson())){
					resMap.put("unDisReson", dto2.getUndisposedReson());
				}
				for (Map<String, Integer> map2 : listmap) {
					if(map2.get("screenStatus")==6){
						resMap.put("redoScreen", "忽略"+map2.get("counts")+"条重复筛查");
					}else if(map2.get("screenStatus")==5){
						resMap.put("libWithOutNum", map2.get("counts")+"条库无号码");
					}else if(map2.get("screenStatus")==9){
						resMap.put("otherErr", map2.get("counts")+"条格式不支持");
					}
				}
				for(String key:resMap.keySet()){
					String value = resMap.get(key);
					if(StringUtils.isNotBlank(dto2.getScreenReson())){
						dto2.setScreenReson(dto2.getScreenReson()+","+value);
					}else {
						dto2.setScreenReson(value);
					}
				}
			}else {
				dto2.setScreenReson(dto2.getUndisposedReson());
			}
		}
		return list;
	}
}

