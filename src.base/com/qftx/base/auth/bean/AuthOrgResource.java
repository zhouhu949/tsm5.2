package com.qftx.base.auth.bean;
import com.qftx.common.domain.BaseObject;

/**   
 * 单位对应部分可用菜单 信息
 */
public class AuthOrgResource extends BaseObject {
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String proCode;
	private String resourceId; // 资源ID
	private String orgId;			//单位ID
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProCode() {
		return proCode;
	}
	public void setProCode(String proCode) {
		this.proCode = proCode;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
		
}
