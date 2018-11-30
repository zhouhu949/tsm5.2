package com.qftx.tsm.sys.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;

/**
 * 通知公告实体类
 * @author 徐承恩
 */
public class AnnouncementBean extends BaseObject {
	private static final long serialVersionUID = 1L;

	private String announceId;

    private String title;

    private String content;

    private Short type;

    private String inputerAcc;

    private Date inputdate;

    private String modifierAcc;

    private Date modifydate;

    private Short isDel;

    private String orgId;
    
    private String keyword;
    
    private String sum; //已阅人数

    public String getSum() {
		return sum;
	}

	public void setSum(String sum) {
		this.sum = sum;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword == null ? null : keyword.trim();
	}

	public String getAnnounceId() {
        return announceId;
    }

    public void setAnnounceId(String announceId) {
        this.announceId = announceId == null ? null : announceId.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
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

    public Short getIsDel() {
        return isDel;
    }

    public void setIsDel(Short isDel) {
        this.isDel = isDel;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }
}