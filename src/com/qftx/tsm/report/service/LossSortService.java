package com.qftx.tsm.report.service;

import com.qftx.common.exception.SysRunException;
import com.qftx.tsm.option.dao.OptionMapper;
import com.qftx.tsm.report.dao.LossSortMapper;
import com.qftx.tsm.report.dto.GroupDto;
import com.qftx.tsm.report.dto.LossSortDetial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LossSortService {

	@Autowired
	private LossSortMapper lossSortMapper;
	@Autowired
	private OptionMapper optionMapper;

	public List<List<Object>> getLossSortList(Map<String, String> map) {
		List<List<Object>> resultList = new ArrayList<List<Object>>();
		try {
			List<GroupDto> gList = lossSortMapper.findGroupList(map);
			if (gList != null && gList.size() > 0) {
				for (int i = 0; i < gList.size(); i++) {
					List<Object> itemList = new ArrayList<Object>();
					GroupDto dto = new GroupDto();
					dto.setDeptId(gList.get(i).getDeptId());
					dto.setDeptName(gList.get(i).getDeptName());
					itemList.add(dto);// 添加部门名称
					map.put("deptId", gList.get(i).getDeptId());
					List<GroupDto> optionNumList = lossSortMapper.findOptionByGroupId(map);
					for (int j = 0; j < optionNumList.size(); j++) {
						GroupDto gDto = new GroupDto();
						gDto.setOptionId(optionNumList.get(j).getOptionId());
						gDto.setTotal(optionNumList.get(j).getTotal());
						itemList.add(gDto); // 添加流失分类统计
					}
					resultList.add(itemList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysRunException(e);
		}
		return resultList;
	}

	public List<Object> getLossSortTotal(Map<String, String> map) {
		List<GroupDto> optionNumList = new ArrayList<GroupDto>();
		List<Object> itemList = new ArrayList<Object>();
		try {
			optionNumList = lossSortMapper.findOptionTotal(map);
			for (int j = 0; j < optionNumList.size(); j++) {
				GroupDto dto = new GroupDto();
				dto.setOptionId(optionNumList.get(j).getOptionId());
				dto.setTotal(optionNumList.get(j).getTotal());
				itemList.add(dto); // 添加流失分类统计
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysRunException(e);
		}
		return itemList;
	}

	public List<LossSortDetial> getlossSortDetailList(Map<String, String> map) {
		List<LossSortDetial> list = new ArrayList<LossSortDetial>();
		try {
			list= lossSortMapper.findDetailList(map);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return list;
	}
}
