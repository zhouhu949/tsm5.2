package com.qftx.tsm.report.service;

import com.qftx.tsm.report.dao.LossCustMapper;
import com.qftx.tsm.report.dto.LossCustDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * User�� bxl Date�� 2015/12/18 Time�� 15:30
 */
@Service
public class LossCustService {
	@Autowired
	private LossCustMapper lossCustMapper;

	public List<LossCustDto> getLossList() {
		List<LossCustDto> list = new ArrayList<LossCustDto>();
		return list;
	}

	public List<List<LossCustDto>> getLossDetailList() {
		List<List<LossCustDto>> list = new ArrayList<List<LossCustDto>>();
		return list;
	}
}
