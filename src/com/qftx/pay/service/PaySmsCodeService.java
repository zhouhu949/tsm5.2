package com.qftx.pay.service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.domain.BaseJsonResult;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.pay.bean.PaySmsCodeBean;
import com.qftx.pay.dao.PaySmsCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PaySmsCodeService {
	@Autowired PaySmsCodeMapper paySmsCodeMapper;
	/* 查询*/
	public BaseJsonResult check(ShiroUser user,PaySmsCodeBean smsCode){
		String code = smsCode.getCode();
		if(StringUtils.isBlank(code)){
			return BaseJsonResult.error("验证码为空！");
		}
		
		smsCode.setCode(null);
		if(StringUtils.isBlank(smsCode.getId())){
			smsCode.setOrgId(user.getOrgId());
			smsCode.setAccount(user.getAccount());
		}
		
		PaySmsCodeBean dbSmsCode = getLastSmsCode(smsCode);
		if(dbSmsCode!=null && valid(dbSmsCode)){
			if(code.trim().equals(dbSmsCode.getCode())){
				
				
				PaySmsCodeBean update = new PaySmsCodeBean();
				update.setId(smsCode.getId());
				update.setStatus(1);
				updateTrends(update);
				return BaseJsonResult.success();
			}else{
				return BaseJsonResult.error("验证码无效，请重新输入验证码!");
			}
		}else{
			return BaseJsonResult.error("验证码过期，请重新获取验证码！");
		}
	}
	
	/* 查询*/
	public PaySmsCodeBean getByCondtion(PaySmsCodeBean entity){
		entity.setIsDel(0);		
		return paySmsCodeMapper.getByCondtion(entity);
	}
	
	/* 查询*/
	public PaySmsCodeBean getLastSmsCode(PaySmsCodeBean entity){
		entity.setStatus(0);
		entity.setOrderKey("inputtime desc");
		entity.setLimitKey("1");
		return getByCondtion(entity);
	}
	
	/* 查询*/
	public void updateTrends(PaySmsCodeBean entity){
		entity.setUpdatetime(new Date());
		paySmsCodeMapper.updateTrends(entity);
	}
	
	/* 分页查询*/
	public List<PaySmsCodeBean> findListPage(String orgId,PaySmsCodeBean entity){
		entity.setOrgId(orgId);
		
		return paySmsCodeMapper.findListPage(entity);
	}
	/* 插入*/
	public PaySmsCodeBean insert(PaySmsCodeBean bean){
		String id=GuidUtil.getGuid();
		bean.setId(id);
		bean.setStatus(0);
		bean.setIsDel(0);
		bean.setInputtime(new Date());
		paySmsCodeMapper.insert(bean);
		return bean;
	}
	
	public boolean valid(PaySmsCodeBean smsCodeBean){
		if(smsCodeBean ==null){
			return false;
		}else if(smsCodeBean.getInputtime()==null ||
				(System.currentTimeMillis()-smsCodeBean.getInputtime().getTime())/1000/60 > AppConstant.SMS_CODE_VALID_TIME){
			return false;
		}else{
			return true;
		}
	}
}
