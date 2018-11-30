package com.qftx.tsm.plan.user.day.dto;

import com.qftx.tsm.plan.user.day.bean.PlanUserDayBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserDayResourceBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserdaySigncustBean;
import com.qftx.tsm.plan.user.day.bean.PlanUserdayWillcustBean;

import java.util.List;

public class PlanDayDTO {
	private PlanUserDayBean planUser;
	private List<PlanUserDayResourceBean> resources;
	private List<PlanUserdaySigncustBean> signcusts;
	private List<PlanUserdayWillcustBean> willcusts;
	
	public PlanUserDayBean getPlanUser() {
		return planUser;
	}
	public void setPlanUser(PlanUserDayBean planUser) {
		this.planUser = planUser;
	}
	public List<PlanUserDayResourceBean> getResources() {
		return resources;
	}
	public void setResources(List<PlanUserDayResourceBean> resources) {
		this.resources = resources;
	}
	public List<PlanUserdaySigncustBean> getSigncusts() {
		return signcusts;
	}
	public void setSigncusts(List<PlanUserdaySigncustBean> signcusts) {
		this.signcusts = signcusts;
	}
	public List<PlanUserdayWillcustBean> getWillcusts() {
		return willcusts;
	}
	public void setWillcusts(List<PlanUserdayWillcustBean> willcusts) {
		this.willcusts = willcusts;
	}
}
