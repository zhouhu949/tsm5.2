package com.qftx.tsm.sys.bean;

import com.qftx.common.domain.BaseObject;

import java.util.Date;



 /** 
 * 短信模板
 */
public class SmsTemplate extends BaseObject{

	private static final long serialVersionUID = 7944826511126948410L;

	private String id;

    private String content;

    private String inputerAcc;

    private Date inputdate;

    private String modifierAcc;

    private Date modifydate;

    private String orgId;

    private String tsgId; // 模板分类ID（4.0新增）
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

	public String getTsgId() {
		return tsgId;
	}

	public void setTsgId(String tsgId) {
		this.tsgId = tsgId;
	}
    
}