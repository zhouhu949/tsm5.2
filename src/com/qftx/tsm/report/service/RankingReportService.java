package com.qftx.tsm.report.service;

import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.main.dto.RankReportDto;
import com.qftx.tsm.report.bean.RankingReportBean;
import com.qftx.tsm.report.dao.RankingReportMapper;
import com.qftx.tsm.report.dto.RankingReportDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RankingReportService {
	
	@Autowired
	private RankingReportMapper rankingReportMapper;
	
	
	/** 
	 * 修改当月排行榜数据
	 * @param orgId 单位ID
	 * @param account 帐号
	 * @param saleAmounts 销售金额
	 * @param signNums 新增签约客户数
	 * @param intNums  新增意向客户数
	 * @create  2016年2月19日 上午10:50:26 lixing
	 * @history  
	 */
	public void updateRankingData(String orgId,String account,BigDecimal saleAmounts,Integer signNums,Integer intNums){
		Calendar cal = Calendar.getInstance();
		String year = cal.get(Calendar.YEAR)+"";
		String month = (cal.get(Calendar.MONTH)+1)+"";
		RankingReportBean rrb = new RankingReportBean(orgId,account,year,month);
		List<RankingReportBean> beans = rankingReportMapper.findByCondtion(rrb);
		if(beans.size() > 0){
			rrb = beans.get(0);
			BigDecimal saleAmountsTotal = rrb.getSaleAmounts().add(saleAmounts);
			rrb.setSaleAmounts(saleAmountsTotal);
			rrb.setSignNums(rrb.getSignNums()+signNums);
			rrb.setIntNums(rrb.getIntNums()+intNums);
			rrb.setUpdateTime(cal.getTime());
			rankingReportMapper.update(rrb);
		}else{
			rrb.setId(SysBaseModelUtil.getModelId());
			rrb.setSaleAmounts(saleAmounts);
			rrb.setSignNums(signNums);
			rrb.setIntNums(intNums);
			rrb.setInputTime(cal.getTime());
			rankingReportMapper.insert(rrb);
		}
	}
	
	public void updateRankingData(String orgId,String account,BigDecimal saleAmounts,Integer signNums,Integer intNums,Date updateTime){
		Calendar cal = Calendar.getInstance();
		cal.setTime(updateTime);
		String year = cal.get(Calendar.YEAR)+"";
		String month = (cal.get(Calendar.MONTH)+1)+"";
		RankingReportBean rrb = new RankingReportBean(orgId,account,year,month);
		List<RankingReportBean> beans = rankingReportMapper.findByCondtion(rrb);
		if(beans.size() > 0){
			rrb = beans.get(0);
			BigDecimal saleAmountsTotal = rrb.getSaleAmounts().add(saleAmounts);
			rrb.setSaleAmounts(saleAmountsTotal);
			rrb.setSignNums(rrb.getSignNums()+signNums);
			rrb.setIntNums(rrb.getIntNums()+intNums);
			rrb.setUpdateTime(new Date());
			rankingReportMapper.update(rrb);
		}
	}
	
	public List<RankingReportDto> getRankingList(String orgId,List<String> groupIds,String year,String month,String adminAcc) {
		return rankingReportMapper.findRankingList(orgId, groupIds, year, month,adminAcc);
	}
	
	public List<RankingReportDto> getCalloutRankingList(String orgId,List<String> groupIds,String startDate,String endDate){
		return rankingReportMapper.findCalloutRankingList(orgId, groupIds, startDate, endDate);
	}
	
	public List<RankReportDto> getCallRankingList(Map<String, Object> map){
		return rankingReportMapper.findCallRankingList(map);
	}
}
