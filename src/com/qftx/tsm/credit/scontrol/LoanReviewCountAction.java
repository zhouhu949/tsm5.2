package com.qftx.tsm.credit.scontrol;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.tsm.credit.dto.StatisticInfoDto;
import com.qftx.tsm.credit.service.LoanReviewService;
import com.qftx.tsm.sys.dto.SearchListShowCodeDto;
import com.qftx.tsm.sys.enums.SysEnum;

@Controller
@RequestMapping("/credit/count")
public class LoanReviewCountAction extends BaseAction{
	
	private static final Logger logger = Logger.getLogger(LoanReviewCountAction.class);
	@Autowired
	private LoanReviewService loanReviewService;
	@Autowired
	private CachedService cachedService;

	@RequestMapping("/reviewCountList")
	public String toMyCusts(HttpServletRequest request, StatisticInfoDto statisticInfoDto) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			Map<String,String> definedNameMap = getQupaiIsDefinedNameList(user.getOrgId());
			request.setAttribute("definedNameMap", definedNameMap);
			// 用于筛选项排序
			Map<String,Integer> mutilSearchCodeSortMap = cachedService.getQupaiUnTestSearchSort(user.getOrgId(), SysEnum.QIPAI_SEARCH_SET_MODULE_2.getState());
			request.setAttribute("mutilSearchCodeSortMap", mutilSearchCodeSortMap);
			// 需隐藏列的序号
			List<Integer> sorts = cachedService.getQupaiMoneySortListCode(SysEnum.QIPAI_SEARCH_SET_MODULE_2.getState(),user.getOrgId(),user.getIsState().toString(),SearchListShowCodeDto.modult_19_List, 3);
			request.setAttribute("sorts", sorts);
			
			request.setAttribute("groupList", cachedService.getResGroupList(user.getOrgId()));
			request.setAttribute("shiroUser", user);
			statisticInfoDto.setOrgId(user.getOrgId());
			loanReviewService.findStatistics(request, statisticInfoDto);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			request.setAttribute("result", "-1");
			request.setAttribute("message", "统计接口异常");
			logger.error(e.getMessage(), e);
		}
		return "/qupai/review/count/reviewCountList";
	}

}
