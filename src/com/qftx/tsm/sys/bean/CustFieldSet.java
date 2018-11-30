package com.qftx.tsm.sys.bean;

import com.qftx.common.domain.BaseObject;
import com.qftx.tsm.option.bean.OptionBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 客户基本信息字段设置
 */
public class CustFieldSet extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	// 是否启用
	public static final int FIELD_ENABLED = 1;		//启用
	public static final int FIELD_NOT_ENABLE = 0;	//不启动
	
	// 字段类型
	public static final int DATATYPE_TEXT = 1;	//文本类型
	public static final int DATATYPE_DATE = 2;	//日期类型
	public static final int DATATYPE_RADIO = 3;	//单选类型
	public static final int DATATYPE_CHECK = 4;	//多选类型
	public static final int DATATYPE_MONEY = 5;	//多选类型
	public static final int DATATYPE_REFER = 6;	//引用字段
	
	public static final int DATATYPE_PICTURE = 10;	//图片
	
	private String fieldId;

	private Short enable; //是否启用：0-否；1-是；

	private String fieldName;

	private Short isQuery; // 是否查询条件：0-否；1-是；

	private Short sort;

	private Short oldSort; // 用于修改字段时,用于修改排序做标记。
	
	private String inputerAcc;

	private Date inputdate;

	private String modifierAcc;

	private Date modifydate;

	private String orgId;

	private String fieldCode;

    private String fieldLength;
    
    private String fieldData;	// 导入匹配
    
    private String fieldValue;	// 导入匹配
    
    private Short isRequired; // 是否必填：0-否；1-是；
    
    private Integer state; // 默认0，0是个人资源，1企业资源，2 联系人 企业的多些字段(4.0新增，其余是3.1版相同)
    
    private Integer isRead; // 是否只读，0-否；1-是； 默认0
    
    //hyx:  字段类型--1:文本类型，2：日期类型，3：单选类型，4：多选类型
    //qupai  字段类型 --1:文本类型，2：日期类型，3：单选类型，4：多选类型 , 5:金额 ，6：引用字段 ,7 : 计算类型(借款金额*系数) ，8：计算类型（借款金额-服务费），9： 计算类型（借款日+天数）， 10：图片 ，11：树'
    private Integer dataType; 
    
    private List<OptionBean> optionList;//当字段类型为3或者4的时候,下拉菜单
    private Object showValue;
    
    private ArrayList<Map>  dataJson;
    


	public ArrayList<Map> getDataJson() {
		return dataJson;
	}

	public void setDataJson(ArrayList<Map> dataJson) {
		this.dataJson = dataJson;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId == null ? null : fieldId.trim();
	}

	public Short getEnable() {
		return enable;
	}

	public void setEnable(Short enable) {
		this.enable = enable;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName == null ? null : fieldName.trim();
	}

	public Short getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(Short isQuery) {
		this.isQuery = isQuery;
	}

	public Short getSort() {
		return sort;
	}

	public void setSort(Short sort) {
		this.sort = sort;
	}

	public String getInputerAcc() {
		return inputerAcc;
	}

	public void setInputerAcc(String inputerAcc) {
		this.inputerAcc = inputerAcc == null ? null : inputerAcc.trim();
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
		this.modifierAcc = modifierAcc == null ? null : modifierAcc.trim();
	}

	public Date getModifydate() {
		return modifydate;
	}

	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId == null ? null : orgId.trim();
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldLength(String fieldLength) {
		this.fieldLength = fieldLength;
	}

	public String getFieldLength() {
		return fieldLength;
	}

	public Short getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Short isRequired) {
		this.isRequired = isRequired;
	}

	public String getFieldData() {
		return fieldData;
	}

	public void setFieldData(String fieldData) {
		this.fieldData = fieldData;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getIsRead() {
		return isRead;
	}

	public void setIsRead(Integer isRead) {
		this.isRead = isRead;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Short getOldSort() {
		return oldSort;
	}

	public void setOldSort(Short oldSort) {
		this.oldSort = oldSort;
	}

	public List<OptionBean> getOptionList() {
		return optionList;
	}

	public void setOptionList(List<OptionBean> optionList) {
		this.optionList = optionList;
	}

	public Object getShowValue() {
		return showValue;
	}

	public void setShowValue(Object showValue) {
		this.showValue = showValue;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	

	

}