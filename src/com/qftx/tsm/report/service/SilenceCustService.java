package com.qftx.tsm.report.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.DateUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.report.dao.SilenceCustMapper;
import com.qftx.tsm.report.dto.SilenceCustDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * User�� bxl Date�� 2015/12/18 Time�� 15:30
 */
@Service
public class SilenceCustService {
	@Autowired
	private SilenceCustMapper silenceCustMapper;

	public List<SilenceCustDto> getSilentList(Map<String, String> map) {
		List<SilenceCustDto> list = new ArrayList<SilenceCustDto>();
		list = silenceCustMapper.findSilentCustList(map);
		List<SilenceCustDto> newList = new ArrayList<SilenceCustDto>();
		List<String> strList = getDateList(map);
		if (list != null && list.size() > 0) {
			int mark = 0;
			for (int i = 0; i < strList.size(); i++) {
				String tempDate = strList.get(i);
				for (SilenceCustDto dto : list) {
					if (tempDate.equals(dto.getCurrDate())) {
						newList.add(dto);
						mark = mark + 1;
					}
				}
				if (mark > 0) {
					mark = 0;
					continue;
				}
				if (mark == 0) {
					SilenceCustDto sbeanCustDto = getSilentCustDto(tempDate);
					newList.add(sbeanCustDto);
				}
			}

		} else {
			for (int i = 0; i < strList.size(); i++) {
				String tempDate = strList.get(i);
				SilenceCustDto sbeanCustDto = getSilentCustDto(tempDate);
				newList.add(sbeanCustDto);
			}
		}
		return newList;
	}

	public SilenceCustDto getSilentCustDto(String tempDate) {
		SilenceCustDto sbeanCustDto = new SilenceCustDto();
		sbeanCustDto.setAddLossTotal(0);
		sbeanCustDto.setLossTotal(0);
		sbeanCustDto.setLossRate(new Double(0));
		sbeanCustDto.setAddSilentTotal(0);
		sbeanCustDto.setSilentTotal(0);
		sbeanCustDto.setSilentRate(new Double(0));
		sbeanCustDto.setExpireCustTotal(0);
		sbeanCustDto.setWakeRate(new Double(0));
		sbeanCustDto.setAddWakeTotal(0);
		sbeanCustDto.setWakeTotal(0);
		sbeanCustDto.setCurrDate(tempDate);
		sbeanCustDto.setSignTotal(0);
		return sbeanCustDto;
	}

	public List<String> getDateList(Map<String, String> map) {
		String startIime = map.get("startTime");
		String endTime = map.get("endTime");
		List<String> list = new ArrayList<String>();
		try {
			long diff = DateUtil.calculateMonthIn(DateUtil.parseDate(endTime + "-01"), DateUtil.parseDate(startIime + "-01"));
			if (diff > 0) {
				for (int i = 0; i <= diff; i++) {
					if (i == 0) {
						list.add(startIime);
						continue;
					}
					Date date = DateUtil.nextMonthFirstDate(DateUtil.parseDate(startIime + "-01"));
					startIime = DateUtil.getCurrMonth(date);
					list.add(startIime);
				}
			}else{
				list.add(startIime);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysRunException(e);
		}
		return list;
	}

	public List<SilenceCustDto> getSilentDetailList(Map<String, String> map) {
		List<SilenceCustDto> list = silenceCustMapper.findSilentDetailList(map);
		return list;
	}

	/**
	 * addtype 1-签约；2-沉默；3-唤醒；4-合同到期；5-流失
	 * 
	 * @param addType
	 * @param addNum
	 * @create 2016年1月25日 上午10:42:47 wuwei
	 * @history
	 */
	public void updateSilentLossReport(Date inviladDate, Integer addType, Integer addNum,ShiroUser user) {
		Map<String, String> map = new HashMap<String, String>();
		SilenceCustDto sBean = new SilenceCustDto();
		sBean.setAccount(user.getAccount());
		sBean.setOrgId(user.getOrgId());
		if (addType == 4) {
			sBean.setCurrDate(DateUtil.getDataMonth(inviladDate));
		} else {
			sBean.setCurrDate(DateUtil.getDataMonth(new Date()));
		}
		try {
			SilenceCustDto tempBean = silenceCustMapper.getByCondtion(sBean);
			if (tempBean == null) {// 如果没有数据，插入
				map.put("account", user.getAccount());
				map.put("orgId", user.getOrgId());
				SilenceCustDto lastCustDto = silenceCustMapper.findLastSilentCust(map);
				if (lastCustDto != null) {
					sBean.setId(SysBaseModelUtil.getModelId());
					sBean.setSignTotal(lastCustDto.getSignTotal());
					sBean.setSilentTotal(lastCustDto.getSilentTotal());
					sBean.setWakeTotal(lastCustDto.getWakeTotal());
					sBean.setExpireCustTotal(lastCustDto.getExpireCustTotal());
					sBean.setAddLossTotal(0);
					sBean.setLossTotal(lastCustDto.getLossTotal());
					sBean.setAddSilentTotal(0);
					sBean.setSilentRate(new Double(0));
					sBean.setWakeRate(new Double(0));
					sBean.setLossRate(new Double(0));
					sBean.setAddWakeTotal(0);
				} else {
					sBean.setId(SysBaseModelUtil.getModelId());
					sBean.setSignTotal(0);
					sBean.setSilentTotal(0);
					sBean.setWakeTotal(0);
					sBean.setExpireCustTotal(0);
					sBean.setAddLossTotal(0);
					sBean.setLossTotal(0);
					sBean.setAddSilentTotal(0);
					sBean.setSilentRate(new Double(0));
					sBean.setWakeRate(new Double(0));
					sBean.setLossRate(new Double(0));
					sBean.setAddWakeTotal(0);
				}
				if (addType == 1) {
					sBean.setSignTotal(sBean.getSignTotal() + addNum);
				} else if (addType == 2) {
					sBean.setSilentTotal(sBean.getSilentTotal() + addNum);
					sBean.setAddSilentTotal(addNum);
				} else if (addType == 3) {
					sBean.setWakeTotal(sBean.getWakeTotal() + addNum);
					sBean.setAddWakeTotal(addNum);
				} else if (addType == 4) {
					sBean.setExpireCustTotal(sBean.getExpireCustTotal() + addNum);
				} else if (addType == 5) {
					sBean.setLossTotal(sBean.getLossTotal() + addNum);
					sBean.setAddLossTotal(addNum);
				}
				silenceCustMapper.insert(sBean);
			} else {
				int signTotal = tempBean.getSignTotal() == null ? 0 : tempBean.getSignTotal();
				int silentTotal = tempBean.getSilentTotal() == null ? 0 : tempBean.getSilentTotal();
				int wakeTotal = tempBean.getWakeTotal() == null ? 0 : tempBean.getWakeTotal();
				int expireCustTotal = tempBean.getExpireCustTotal() == null ? 0 : tempBean.getExpireCustTotal();
				int lossTotal = tempBean.getLossTotal() == null ? 0 : tempBean.getLossTotal();
				int addSilentTotal = tempBean.getAddSilentTotal() == null ? 0 : tempBean.getAddSilentTotal();
				int addWakeTotal = tempBean.getAddWakeTotal() == null ? 0 : tempBean.getAddWakeTotal();
				int addLossTotal = tempBean.getAddLossTotal() == null ? 0 : tempBean.getAddLossTotal();
				if (addType == 1) {
					tempBean.setSignTotal(signTotal + addNum);

				} else if (addType == 2) {
					tempBean.setSilentTotal(silentTotal + addNum);
					tempBean.setAddSilentTotal(addSilentTotal + addNum);
				} else if (addType == 3) {
					tempBean.setWakeTotal(wakeTotal + addNum);
					tempBean.setAddWakeTotal(addWakeTotal + addNum);
				} else if (addType == 4) {
					tempBean.setExpireCustTotal(expireCustTotal + addNum);
				} else if (addType == 5) {
					tempBean.setLossTotal(lossTotal + addNum);
					tempBean.setAddLossTotal(addLossTotal + addNum);
				}
				silenceCustMapper.update(tempBean);
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
	}
}
