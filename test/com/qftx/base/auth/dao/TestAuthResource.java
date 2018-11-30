package com.qftx.base.auth.dao;

import java.util.List;

import com.qftx.base.auth.bean.AuthProductResource;
import com.qftx.base.auth.bean.Resource;
import com.qftx.base.auth.dto.Menu;
import com.qftx.base.auth.service.AuthProductReousrceService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.common.BaseUtest;
import org.junit.Test;

/**
 * Created by bxl on 2015/11/2.
 */
public class TestAuthResource extends BaseUtest{
    @Test
   public void test1() {
    	AuthProductReousrceService service  = (AuthProductReousrceService)context.getBean("reousrceService");
		List<AuthProductResource>list = service.getButtonsListByUserId("d73d4d42a3d343e799bbe3963045d323","orgId");
		for(AuthProductResource res : list){
			System.out.println(res.getResourceName()+"---"+res.getResourceString());
		}
	}
	@Test
	public void queryAll() {
		AuthProductReousrceService service  = (AuthProductReousrceService)context.getBean("reousrceService");
		List<AuthProductResource>list = service.getList();
		for(AuthProductResource res : list){
			System.out.println(res.getResourceName()+"---"+res.getResourceString());
		}
	}
}
