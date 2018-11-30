package com.qftx.sys.dao;

import java.util.List;

import com.qftx.base.shiro.ShiroUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;


import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.option.dao.OptionMapper;
import com.qftx.tsm.option.dto.OptionDto;
import com.qftx.tsm.sys.bean.TsmCustReview;
import com.qftx.tsm.sys.dao.TsmCustReviewMapper;
import com.qftx.tsm.sys.dto.TsmCustReviewDto;
import com.qftx.tsm.sys.service.TsmCustReviewService;

public class TestRecord extends BaseUtest{
	@Before
	public void before(){
		System.out.println("开始----");
	}
	@Test
	public void queryRecord(){
		TsmCustReviewDto reviewDto = new TsmCustReviewDto();
		TsmCustReviewMapper tsmCustReviewMapper =(TsmCustReviewMapper) context.getBean("tsmCustReviewMapper");
		OptionMapper optionMapper = (OptionMapper) context.getBean("optionMapper");
		reviewDto.setOrgId(ShiroUtil.getUser().getOrgId());
        reviewDto.setOwnerAccs(null);
		List<OptionDto> oplist  = optionMapper.findRecordReviewTypeByOrgId(reviewDto);
		System.out.println("录音范例左侧分组 数量="+oplist.size());
		System.out.println("录音范例左侧分组 列表="+JsonUtil.getJsonString(oplist));
		TsmCustReviewDto tsmCustReviewDto = new TsmCustReviewDto();
		tsmCustReviewDto.setOrgId(ShiroUtil.getShiroUser().getOrgId());
		List<TsmCustReviewDto> list = tsmCustReviewMapper.findRecordReviewListPage(tsmCustReviewDto);
		System.out.println("录音范例数量="+list.size());
		System.out.println("录音范例 列表="+JsonUtil.getJsonString(list));
	}
//	@Ignore
	@Test
	public void saveRecord(){
		TsmCustReviewMapper tsmCustReviewMapper =(TsmCustReviewMapper) context.getBean("tsmCustReviewMapper");
		TsmCustReview tsmCustReview = new TsmCustReview();
		tsmCustReview.setReviewId(GuidUtil.getId());
		tsmCustReview.setOrgId(ShiroUtil.getShiroUser().getOrgId());
		tsmCustReview.setOwnerAcc("happy001");
		tsmCustReviewMapper.insert(tsmCustReview);
		System.out.println("录音范例插入id="+tsmCustReview.getReviewId());
	}
	
//	@Ignore
	@Test
	public void updateRecord(){
		TsmCustReviewService tsmCustReviewMapper =(TsmCustReviewService) context.getBean("tsmCustReviewService");
		TsmCustReview tsmCustReview = new TsmCustReview();
//		tsmCustReview.setReviewId(GuidUtil.getId());
		tsmCustReview.setOrgId(ShiroUtil.getShiroUser().getOrgId());
		tsmCustReview.setOwnerAcc("happy001");
		tsmCustReview.setRecordExampId("11");
		tsmCustReviewMapper.modify(tsmCustReview);
		System.out.println("录音范例修改---");
	}
	
	@After
    public void after(){
		System.out.println("结束-----");
	}
}
