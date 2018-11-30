package com.qftx.loan;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.common.BaseUtest;
import com.qftx.tsm.credit.dto.TsmLoanReviewInfoDto;
import com.qftx.tsm.credit.service.LoanReviewService;

public class ReviewInfoTest extends BaseUtest{

	@Autowired
	private LoanReviewService loanReviewService;
	
	@Test
	public void updateReviewInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			TsmLoanReviewInfoDto tsmLoanReviewInfoDto = new TsmLoanReviewInfoDto();
			tsmLoanReviewInfoDto.setLeadId("a02ebfe5e4be4a40974db45e04e4c743");
			tsmLoanReviewInfoDto.setOrgId("rd");
			
			tsmLoanReviewInfoDto.setResult(2);
			tsmLoanReviewInfoDto.setRemark("算你通过");
			
			map = loanReviewService.updateReviewInfo(tsmLoanReviewInfoDto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(map);
	}
	
}
