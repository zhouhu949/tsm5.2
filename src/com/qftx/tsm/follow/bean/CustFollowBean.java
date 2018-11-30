package com.qftx.tsm.follow.bean;

import com.qftx.common.domain.BaseObject;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


 /** 
 * 跟进记录表 实体类
 * @author: lixing
 * @since: 2015年11月13日  上午11:03:01
 * @history:
 */
public class CustFollowBean extends BaseObject {
	private static final long serialVersionUID = -8729775023378264534L;

	private String custFollowId;      //客户跟进ID
    private String custId;            //客户ID
    private String actionType;        //行动方式：1-电话联系，2-会客联系，3-客户来电,4-短信联系，5-qq联系，6-邮件联系，7-微信联系
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date actionDate;          //行动时间
    
    private String feedbackInfoId;    //反馈信息选项ID
    private String feedbackComment;   //反馈信息内容
    private Integer isFollow;           //状态：1-客户跟进，2-资源备注
    
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date followDate;          // 下次跟进时间
    private String followType;         //下次跟进方式 :1-电话联系，2-会客联系，3-客户来电,4-短信联系，5-qq联系，6-邮件联系，7-微信联系
    private String followAcc;         //跟进者
    private Date inputDate;			  //INPUT_DATE 录入时间
    private String inputAcc;		  //INPUT_ACC  录入人
    private String orgId;		      //机构id
    private String saleProcessId;       //销售进程ID (add by limin)
    private String lastSaleProcessId;       //上次销售进程ID 

    private String labelCode; //跟进标签code
    private String labelName;//跟进标签名称
    
    private String labelNames;// 显示 跟进标签名称（只为了显示,目前在入库标签时 是以#号分割，而显示时又是以，号分割）
    private Integer effectiveness ;//联系有效性；1:有效；0：无效；
    private Integer custStatus; // 客户状态：5-意向客户，6-签约客户，7-沉默客户，8-流失客户,9-公海客户
    private String custDetailName; // 联系人姓名
    private String custDetailMobile; // 联系人常用号码
    private String custTypeId; // 客户类型Id
    private String oldCustName; // 冗余 客户名称
    private String saleChanceId;
	private String defined1;
	private String defined2;
	private String defined3;
	private String defined4;
	private String defined5;
	private String defined6;
	private String defined7;
	private String defined8;
	private String defined9;
	private String defined10;
	private String defined11;
	private String defined12;
	private String defined13;
	private String defined14;
	private String defined15;
	

	private Date defined16;
	private Date defined17;
	private Date defined18;
	
	private String showdefined16;
	private String showdefined17;
	private String showdefined18;
	
	private String startdefined16;
	private String enddefined16;
	private String startdefined17;
	private String enddefined17;
	private String startdefined18;
	private String enddefined18;
	
	private Integer source;
	
	/**
	 * 跟进图片,逗号分割
	 */
	private String feedbackImg;
	
    public String getFeedbackImg() {
		return feedbackImg;
	}

	public void setFeedbackImg(String feedbackImg) {
		this.feedbackImg = feedbackImg;
	}

	public String getLabelCode() {
		return labelCode;
	}

	public void setLabelCode(String labelCode) {
		this.labelCode = labelCode;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public Date getInputDate() {

		return inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getInputAcc() {
		return inputAcc;
	}

	public void setInputAcc(String inputAcc) {
		this.inputAcc = inputAcc;
	}

	public String getCustFollowId() {
        return custFollowId;
    }

    public void setCustFollowId(String custFollowId) {
        this.custFollowId = custFollowId == null ? null : custFollowId.trim();
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId == null ? null : custId.trim();
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType == null ? null : actionType.trim();
    }

    public Date getActionDate() {
        return actionDate;
    }

    public void setActionDate(Date actionDate) {
        this.actionDate = actionDate;
    }

    public String getFeedbackInfoId() {
        return feedbackInfoId;
    }

    public void setFeedbackInfoId(String feedbackInfoId) {
        this.feedbackInfoId = feedbackInfoId == null ? null : feedbackInfoId.trim();
    }

    public String getFeedbackComment() {
        return feedbackComment;
    }

    public void setFeedbackComment(String feedbackComment) {
        this.feedbackComment = feedbackComment == null ? null : feedbackComment.trim();
    }

    public Integer getIsFollow() {
        return isFollow;
    }

    public void setIsFollow(Integer isFollow) {
        this.isFollow = isFollow;
    }
    
    public Date getFollowDate() {
		return followDate;
	}

	public void setFollowDate(Date followDate) {
		this.followDate = followDate;
	}

	public String getFollowType() {
		return followType;
	}

	public void setFollowType(String followType) {
		this.followType = followType;
	}

	public String getFollowAcc() {
        return followAcc;
    }

    public void setFollowAcc(String followAcc) {
        this.followAcc = followAcc == null ? null : followAcc.trim();
    }

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getSaleProcessId() {
		return saleProcessId;
	}

	public void setSaleProcessId(String saleProcessId) {
		this.saleProcessId = saleProcessId;
	}

	public void setLastSaleProcessId(String lastSaleProcessId) {
		this.lastSaleProcessId = lastSaleProcessId;
	}

	public String getLastSaleProcessId() {
		return lastSaleProcessId;
	}

	public void setEffectiveness(Integer effectiveness) {
		this.effectiveness = effectiveness;
	}

	public Integer getEffectiveness() {
		return effectiveness;
	}

	public String getLabelNames() {
		return labelNames;
	}

	public void setLabelNames(String labelNames) {
		this.labelNames = labelNames;
	}
	
	public Integer getCustStatus() {
		return custStatus;
	}

	public void setCustStatus(Integer custStatus) {
		this.custStatus = custStatus;
	}

	public String getCustDetailName() {
		return custDetailName;
	}

	public void setCustDetailName(String custDetailName) {
		this.custDetailName = custDetailName;
	}

	public String getCustDetailMobile() {
		return custDetailMobile;
	}

	public void setCustDetailMobile(String custDetailMobile) {
		this.custDetailMobile = custDetailMobile;
	}

	public String getCustTypeId() {
		return custTypeId;
	}

	public void setCustTypeId(String custTypeId) {
		this.custTypeId = custTypeId;
	}

	public String getOldCustName() {
		return oldCustName;
	}

	public void setOldCustName(String oldCustName) {
		this.oldCustName = oldCustName;
	}

	public String getDefined1() {
		return defined1;
	}

	public void setDefined1(String defined1) {
		this.defined1 = defined1;
	}

	public String getDefined2() {
		return defined2;
	}

	public void setDefined2(String defined2) {
		this.defined2 = defined2;
	}

	public String getDefined3() {
		return defined3;
	}

	public void setDefined3(String defined3) {
		this.defined3 = defined3;
	}

	public String getDefined4() {
		return defined4;
	}

	public void setDefined4(String defined4) {
		this.defined4 = defined4;
	}

	public String getDefined5() {
		return defined5;
	}

	public void setDefined5(String defined5) {
		this.defined5 = defined5;
	}

	public String getDefined6() {
		return defined6;
	}

	public void setDefined6(String defined6) {
		this.defined6 = defined6;
	}

	public String getDefined7() {
		return defined7;
	}

	public void setDefined7(String defined7) {
		this.defined7 = defined7;
	}

	public String getDefined8() {
		return defined8;
	}

	public void setDefined8(String defined8) {
		this.defined8 = defined8;
	}

	public String getDefined9() {
		return defined9;
	}

	public void setDefined9(String defined9) {
		this.defined9 = defined9;
	}

	public String getDefined10() {
		return defined10;
	}

	public void setDefined10(String defined10) {
		this.defined10 = defined10;
	}

	public String getDefined11() {
		return defined11;
	}

	public void setDefined11(String defined11) {
		this.defined11 = defined11;
	}

	public String getDefined12() {
		return defined12;
	}

	public void setDefined12(String defined12) {
		this.defined12 = defined12;
	}

	public String getDefined13() {
		return defined13;
	}

	public void setDefined13(String defined13) {
		this.defined13 = defined13;
	}

	public String getDefined14() {
		return defined14;
	}

	public void setDefined14(String defined14) {
		this.defined14 = defined14;
	}

	public String getDefined15() {
		return defined15;
	}

	public void setDefined15(String defined15) {
		this.defined15 = defined15;
	}

	public Date getDefined16() {
		return defined16;
	}

	public void setDefined16(Date defined16) {
		this.defined16 = defined16;
	}

	public Date getDefined17() {
		return defined17;
	}

	public void setDefined17(Date defined17) {
		this.defined17 = defined17;
	}

	public Date getDefined18() {
		return defined18;
	}

	public void setDefined18(Date defined18) {
		this.defined18 = defined18;
	}

	public String getShowdefined16() {
		return showdefined16;
	}

	public void setShowdefined16(String showdefined16) {
		this.showdefined16 = showdefined16;
	}

	public String getShowdefined17() {
		return showdefined17;
	}

	public void setShowdefined17(String showdefined17) {
		this.showdefined17 = showdefined17;
	}

	public String getShowdefined18() {
		return showdefined18;
	}

	public void setShowdefined18(String showdefined18) {
		this.showdefined18 = showdefined18;
	}

	public String getStartdefined16() {
		return startdefined16;
	}

	public void setStartdefined16(String startdefined16) {
		this.startdefined16 = startdefined16;
	}

	public String getEnddefined16() {
		return enddefined16;
	}

	public void setEnddefined16(String enddefined16) {
		this.enddefined16 = enddefined16;
	}

	public String getStartdefined17() {
		return startdefined17;
	}

	public void setStartdefined17(String startdefined17) {
		this.startdefined17 = startdefined17;
	}

	public String getEnddefined17() {
		return enddefined17;
	}

	public void setEnddefined17(String enddefined17) {
		this.enddefined17 = enddefined17;
	}

	public String getStartdefined18() {
		return startdefined18;
	}

	public void setStartdefined18(String startdefined18) {
		this.startdefined18 = startdefined18;
	}

	public String getEnddefined18() {
		return enddefined18;
	}

	public void setEnddefined18(String enddefined18) {
		this.enddefined18 = enddefined18;
	}

	public Integer getSource() {
		return source;
	}

	public void setSource(Integer source) {
		this.source = source;
	}
	public String getSaleChanceId() {
		return saleChanceId;
	}

	public void setSaleChanceId(String saleChanceId) {
		this.saleChanceId = saleChanceId;
	}
	
	
}
