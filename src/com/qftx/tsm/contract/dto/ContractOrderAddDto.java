package com.qftx.tsm.contract.dto;

import com.qftx.tsm.contract.bean.ContractOrderBean;
import com.qftx.tsm.contract.bean.ContractOrderDetailBean;

import java.io.Serializable;
import java.util.List;

public class ContractOrderAddDto implements Serializable {
	private static final long serialVersionUID = 2007228890456052871L;
	private ContractOrderBean orderBean;
	private List<ContractOrderDetailBean> orderDetailBeans;
	public ContractOrderBean getOrderBean() {
		return orderBean;
	}
	public void setOrderBean(ContractOrderBean orderBean) {
		this.orderBean = orderBean;
	}
	public List<ContractOrderDetailBean> getOrderDetailBeans() {
		return orderDetailBeans;
	}
	public void setOrderDetailBeans(List<ContractOrderDetailBean> orderDetailBeans) {
		this.orderDetailBeans = orderDetailBeans;
	}
}
