package com.qftx.tsm.report.service;

import com.qftx.tsm.cust.dao.ResourceGroupMapper;
import com.qftx.tsm.report.dao.CustInfoNalysisMapper;
import com.qftx.tsm.report.dto.CustInfoNalysisDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Service
public class CustInfoNalysisService {
	private static Logger logger = Logger.getLogger(CustInfoNalysisService.class);
	@Autowired
	private ResourceGroupMapper resourceGroupMapper;
	@Autowired
	private CustInfoNalysisMapper custInfoNalysisMapper;
	private static final String lock = "lock";

	/**
	 *
	 * @param custId
	 * @create 2016年3月7日 下午4:38:08 Administrator
	 * @history
	 */
	public void saveOrUpdate(String custId, int type, String orgId) {
		CustInfoNalysisDto newDto = new CustInfoNalysisDto();
		newDto.setOrgId(orgId);
		newDto.setResId(custId);
		try {
			logger.debug("custId=" + custId + "type=" + type + "orgId=" + orgId);
			CustInfoNalysisDto dto = custInfoNalysisMapper.findLastRecord(orgId, custId);
			synchronized (lock) {
				if (dto != null) {
					if (type == 1) {
						if (dto.getWillTotal() == null || dto.getWillTotal() == 0) {
							dto.setWillTotal((dto.getWillTotal() == null ? 0 : dto.getWillTotal()) + 1);
						}
					} else {
						if (dto.getSignTotal() == null || dto.getSignTotal() == 0) {
							dto.setSignTotal((dto.getSignTotal() == null ? 0 : dto.getSignTotal()) + 1);
						}
					}
					custInfoNalysisMapper.update(dto);
				} else {
					if (type == 1) {
						newDto.setWillTotal(1);
					} else {
						newDto.setSignTotal(1);
					}
					custInfoNalysisMapper.insert(newDto);
				}
			}
		} catch (Exception e) {
			logger.error("custId=" + custId + "type=" + type + "orgId=" + orgId + "|" + e.getMessage(), e);
		}
	}

	public void list(Map<String, String> map, HttpServletRequest request) {
		List<CustInfoNalysisDto> list = custInfoNalysisMapper.findCustInfoNalysisList(map);
		List<CustInfoNalysisDto> totalCust = custInfoNalysisMapper.findTotalCustInfoNalysis(map);
		request.setAttribute("list", list);
		request.setAttribute("totalCust", totalCust == null ? null : totalCust.get(0));
	}

	public List<CustInfoNalysisDto> getTotalCustInfoNalysis(Map<String, String> map) {
		return custInfoNalysisMapper.findTotalCustInfoNalysis(map);
	}

	public List<CustInfoNalysisDto> getCustInfoNalysisList(Map<String, String> map) {
		return custInfoNalysisMapper.findCustInfoNalysisList(map);
	}

}
