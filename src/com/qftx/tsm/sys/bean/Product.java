package com.qftx.tsm.sys.bean;

import com.qftx.common.domain.BaseObject;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 系统 产品列表
 * */
public class Product extends BaseObject{

	private static final long serialVersionUID = -1946603393759273177L;
	
	private String id;
	private String orgId;				// 机构ID
	private Integer sort;				// 排序
	private Integer isDefault;		// 是否默认值：0-否；1-是
	private String name;				// 产品名称
	private String model;			// 产品型号
	private String type;				// 产品规格
	private String units;				// 计量单位
	private BigDecimal price;		// 产品价格
	private String context;			// 订单说明
	private String inputAcc;		// 录入人
  	private Date inputdate;		// 录入时间
  	private String modifierAcc;
  	private Date modifydate;
  	private Integer isDel;			// 册除状态1-删除，0-未删除
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getInputAcc() {
		return inputAcc;
	}
	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}
	public Date getInputdate() {
		return inputdate;
	}
	public void setInputdate(Date inputdate) {
		this.inputdate = inputdate;
	}
	public String getModifierAcc() {
		return modifierAcc;
	}
	public void setModifierAcc(String modifierAcc) {
		this.modifierAcc = modifierAcc;
	}
	public Date getModifydate() {
		return modifydate;
	}
	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
	
	 @Override
	    public boolean equals(Object obj) {
	    	 if(this == obj) { 
	             return true;  
	    	 }
	         if(obj == null) {
	        	 return false; 
	         } 
	    	if(obj instanceof Product){
	    		Product product = (Product)obj;
	    		if(this.id.equals(product.id)){
	    			return true;
	    		}
	    	}
	    	return false;
	    }
	    
	    @Override
	    public int hashCode() {
	    	  int result = 17;
	          result = 31 * result + this.id.hashCode();
	          return result;
	    }
}
