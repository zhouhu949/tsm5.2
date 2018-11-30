package com.qftx.tsm.res;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.cust.dto.LogBatchInfoDto;
import com.qftx.tsm.log.bean.LogBatchInfoBean;
import com.qftx.tsm.log.service.LogBatchInfoService;

public class LogResOperateActionTest  extends BaseUtest{
	@Autowired
	private LogBatchInfoService logBatchInfoService;
	@Test
	public void queryLogResOperateList(){
		try {
			int currentPage = 1;
			LogBatchInfoDto logBatchInfoDto = new LogBatchInfoDto();
			logBatchInfoDto.setOrgId("fhtx");
			logBatchInfoDto.getPage().setCurrentPage(currentPage);
			logBatchInfoDto.getPage().setShowCount(10);
			List<LogBatchInfoDto> logBatchInfoDtos =logBatchInfoService.getLogResOperateListPage(logBatchInfoDto);
			for (LogBatchInfoDto logBatchInfoDtoo : logBatchInfoDtos) {
				String data = logBatchInfoDtoo.getData();
				if (data.contains("ownerAcc")) {
					int index = data.lastIndexOf("ownerAcc");
					String ownerAccss = data.substring(index);
					int index1 = ownerAccss.indexOf("\"");
					String ownerAcc = ownerAccss.substring(9,index1);
					int index2 = ownerAccss.lastIndexOf("=");
					int size = Integer.valueOf(ownerAccss.substring(index2+1,ownerAccss.length()-2)) - 1;
					data = data.substring(0,index-2)+"]";
					System.out.println(ownerAcc + "==" + size + "==" + data);
				}else {
					String[] arr = data.split(",");
					int size = arr.length;
					System.out.println(size + "==" + data);
				}
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	/**
	 * 获取第一天
	 * 
	 * @param type
	 *            1-当天 2-本周 3-本月 4-半年
	 * @return
	 * @create 2015年12月14日 下午3:48:05 lixing
	 * @history
	 */
	public String getStartDateStr(Integer type) {
		String str = "";
		if (type == 1) {
			str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
		} else if (type == 2) {
			str = DateUtil.getWeekFirstDay(new Date());
		} else if (type == 3) {
			str = DateUtil.getMonthFirstDay(new Date());
		} else if (type == 4) {
			str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), -180), DateUtil.DATE_DAY);
		}
		return str;
	}

}
