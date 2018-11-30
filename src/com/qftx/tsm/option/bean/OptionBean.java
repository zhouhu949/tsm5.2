package com.qftx.tsm.option.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

 /** 
 * 数据选项表实体
 * @author: 徐承恩
 * @since: 2013-10-29  上午10:13:59
 * @history:
 */
public class OptionBean extends BaseObject {
	
	private static final long serialVersionUID = 1L;
	
	// 是否默认值
	public static short IS_DEFAULT_NO = 0;	//否
	public static short IS_DEFAULT_YES = 1;	//是

	private String optionlistId;     //数据选项ID
    private String itemCode;         //数据项CODE
    private String optionName;       //数据项名称
    private Integer sort;            //排序
    private String inputerAcc;       //录入人帐号
    private Date inputdate;          //录入时间
    private String modifierAcc;      //修改者帐号
    private Date modifydate;         //修改时间
    private String orgId;            //机构
    private String units;            //计量单位
    private String type;             //类型:1:单位佣金；2：佣金比例
    private String optionValue;      //数据项值域
    private Short isDefault;		 //是否默认值0-否；1-是
    private String pid;				//父id
    private String groupId;		// 分组Id
    
    private String isDefaultValue;		 //是否默认值0-否；1-是
    
    /** 用于查询 显示 */
    private String subNameList;		//子项名称链
    private String prefixOptioinName; //  数据项名称前缀
    private String groupName; // 分组名称
    
    public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getSubNameList() {
		return subNameList;
	}

	public void setSubNameList(String subNameList) {
		this.subNameList = subNameList;
	}

	public Short getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Short isDefault) {
		this.isDefault = isDefault == null ? null : isDefault;
	}
	

	public String getOptionlistId() {
        return optionlistId;
    }

    public void setOptionlistId(String optionlistId) {
        this.optionlistId = optionlistId == null ? null : optionlistId.trim();
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode == null ? null : itemCode.trim();
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName == null ? null : optionName.trim();
    }

    public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort == null ? null : sort;
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

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units == null ? null : units.trim();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public String getOptionValue() {
		return optionValue;
	}

	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue == null ? null : optionValue.trim();
	}
    	
    public String getPrefixOptioinName() {
		return prefixOptioinName;
	}

	public void setPrefixOptioinName(String prefixOptioinName) {
		this.prefixOptioinName = prefixOptioinName;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}	
	
	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
    public boolean equals(Object obj) {
    	 if(this == obj) { 
             return true;  
    	 }
         if(obj == null) {
        	 return false; 
         } 
    	if(obj instanceof OptionBean){
    		OptionBean option = (OptionBean)obj;
    		if(this.optionlistId.equals(option.optionlistId)){
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override
    public int hashCode() {
    	  int result = 17;
          result = 31 * result + this.optionlistId.hashCode();
          return result;
    }

	public String getIsDefaultValue() {
		return isDefaultValue;
	}

	public void setIsDefaultValue(String isDefaultValue) {
		this.isDefaultValue = isDefaultValue;
	}
    
    
}