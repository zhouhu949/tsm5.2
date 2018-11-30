package com.qftx.tsm.cust.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.tsm.cust.bean.WxBindBean;
import com.qftx.tsm.cust.dao.WxBindMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service
public class WxBindService {
	@Resource
	WxBindMapper wxBindMapper;
	/*查询绑定的微信id
	 * index 0 为用户id
	 * >0的为绑定的客户ID
	 * */
	public List<String> findBindId(ShiroUser user,String custId){
		WxBindBean entity = new WxBindBean();
		entity.setOrgId(user.getOrgId());
		entity.setAccount(user.getAccount());
		entity.setCustId(custId);
		List<WxBindBean> list = wxBindMapper.findByCondtion(entity);
		List<String> ids =  new ArrayList<String>();
		for (int i=0;i<list.size();i++) {
			WxBindBean bean = list.get(i);
			if(i==0) {
				ids.add(bean.getWxLoginId());
			}
			ids.add(bean.getWxId());
		}
		return ids;
	}
}
