package com.qftx.tsm.report.dto;

import java.util.Map;

public class GroupAndUserPlanDTO {
	private Map<String,PlanUserDto> planUserDtoMap;
	private PlanGroupDto planGroupDto; 
	public PlanGroupDto getPlanGroupDto() {
		return planGroupDto;
	}
	public void setPlanGroupDto(PlanGroupDto planGroupDto) {
		this.planGroupDto = planGroupDto;
	}
	public Map<String, PlanUserDto> getPlanUserDtoMap() {
		return planUserDtoMap;
	}
	public void setPlanUserDtoMap(Map<String, PlanUserDto> planUserDtoMap) {
		this.planUserDtoMap = planUserDtoMap;
	}
	
}
