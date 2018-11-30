package com.qftx.tsm.credit.scontrol;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.credit.dto.TsmLoanReviewInfoDto;
import com.qftx.tsm.credit.service.LoanReviewService;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.dto.HighSearchDto;
import com.qftx.tsm.sys.dto.SearchListShowCodeDto;
import com.qftx.tsm.sys.enums.SysEnum;
import com.qftx.tsm.util.CollectionUtil;
import com.qftx.tsm.util.ExtColumnUtil;

@Controller
@RequestMapping("/credit/review")
public class LoanReviewAction extends BaseAction {
	
	private static final Logger logger = Logger.getLogger(LoanReviewAction.class);
	@Autowired
	private LoanReviewService loanReviewService;
	@Autowired
	private CachedService cachedService;

	/**
	 * 获取审核信息详情
	 * 
	 * @param orgId
	 *            机构Id
	 * @param loanId
	 *            放款信息id
	 * @return
	 */
	@RequestMapping("/getReviewInfo")
	public String getReviewInfo(HttpServletRequest request, String leadId, String auditStatus) {
		try {
			request.setAttribute("leadId", leadId);
			if(StringUtils.isBlank(leadId)){
				request.setAttribute("result", "-1");
				request.setAttribute("message", "缺少参数");
				return "/qupai/review/info/reviewInfo";
			}
			ShiroUser user = ShiroUtil.getShiroUser();
			List<CustFieldSet> fieldSets = cachedService.getQupaiFieldSet(user.getOrgId());
			request.setAttribute("fieldSets", fieldSets);
			TsmLoanReviewInfoDto tsmLoanReviewInfoDto = loanReviewService.getReviewInfo(leadId);
			request.setAttribute("reviewAuth", tsmLoanReviewInfoDto.getReviewAuth()); //审核已完成
			request.setAttribute("item", tsmLoanReviewInfoDto);
			request.setAttribute("list", tsmLoanReviewInfoDto.getReviewList());
			request.setAttribute("auditStatus", auditStatus);
			tsmLoanReviewInfoDto = ExtColumnUtil.getExtData(fieldSets, tsmLoanReviewInfoDto);
			this.setAllFieldShowValue(tsmLoanReviewInfoDto, fieldSets);
			setIsReplaceWord(request);
		} catch (Exception e) {
			request.setAttribute("result", "-1");
			request.setAttribute("message", "审核详情异常");
			logger.error(e.getMessage(), e);
		}
		return "/qupai/review/info/reviewInfo";
	}
	
	public void setAllFieldShowValue(TsmLoanReviewInfoDto leadInfo, List<CustFieldSet> fieldSets) throws Exception {
		if (leadInfo != null) {
			for (CustFieldSet custFieldSet : fieldSets) {
				String fieldCode = custFieldSet.getFieldCode();

				// 没有对应的属性
				if (null == PropertyUtils.getPropertyDescriptor(leadInfo, fieldCode)) {
					continue;
				}

				Object srcValue = PropertyUtils.getProperty(leadInfo, fieldCode);
				if (null != srcValue && srcValue instanceof Date) {
					String value = new SimpleDateFormat("yyyy-MM-dd").format(srcValue);
					custFieldSet.setShowValue(value);
				} else if (null != srcValue) {
					custFieldSet.setShowValue(srcValue.toString());
				} else {
					custFieldSet.setShowValue("");
				}
			}
		}
	}
	
	/**
	 * 修改审核信息
	 * @param orgId 机构Id
	 * @param loanId 放款信息id
	 * @return
	 */
	@RequestMapping("/updateReviewInfo")
	@ResponseBody
	public Map<String, Object> updateReviewInfo(TsmLoanReviewInfoDto tsmLoanReviewInfoDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = loanReviewService.updateReviewInfo(tsmLoanReviewInfoDto);
		} catch (Exception e) {
			map.put("result", "-1");
			map.put("message", "修改审核状态异常");
			logger.error(e.getMessage(), e);
		}
		return map;
	}

	/**
	 * 获取导出字段
	 * @param orgId
	 * @param loanId
	 * @return
	 */
	@RequestMapping("/getExportField")
	public String getExportField(HttpServletRequest request, TsmLoanReviewInfoDto tsmLoanReviewInfoDto) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			tsmLoanReviewInfoDto.setIsPage(1);
			tsmLoanReviewInfoDto.setOrgId(user.getOrgId());
			List<TsmLoanReviewInfoDto> dtoList = loanReviewService.getReviewList(user, tsmLoanReviewInfoDto);
			if (dtoList.size() < 1) {
				request.setAttribute("count", 0);
			} else {
				request.setAttribute("count", tsmLoanReviewInfoDto.getPage().getTotalResult());
			}
			
			List<CustSearchSet> fieldSets = cachedService.getQupaiShowCustSearchSet(SysEnum.QIPAI_SEARCH_SET_MODULE_2.getState(), user.getOrgId());
			fieldSets = CollectionUtil.remove(fieldSets, "dataType", CustSearchSet.DATATYPE_PICTURE);
			
			if (0 == user.getIsState()) {	// 个人客户需要导入单位名称
				List<CustSearchSet> tmpFieldSets = new ArrayList<CustSearchSet>();
				CustSearchSet companySearchSet = new CustSearchSet();
				companySearchSet.setDevelopCode("company");
				companySearchSet.setSearchCode("company");
				companySearchSet.setSearchName("单位名称");
				companySearchSet.setDataType(1);
				companySearchSet.setEnable(CustSearchSet.ENABLE_YES);
				companySearchSet.setIsShow(CustSearchSet.IS_SHOW_YES);
				
				boolean custNameSearchSetExists = false;
				for (CustSearchSet searchSet : fieldSets) {
					if ("custName".equals(searchSet.getDevelopCode())) {
						tmpFieldSets.add(companySearchSet);
						custNameSearchSetExists = true;
					}
					
					tmpFieldSets.add(searchSet);
				}
				if (!custNameSearchSetExists) {
					tmpFieldSets.add(0, companySearchSet);
				}
				fieldSets = tmpFieldSets;
			}
			request.setAttribute("list", fieldSets); // 文本类型查询字段列表
		} catch (Exception e) {
			request.setAttribute("result", "-1");
			request.setAttribute("message", "获取导出字段异常");
			logger.error(e.getMessage(), e);
		}
		return "/qupai/review/info/exportField";
	}

	/**
	 * 导出excel
	 * @param response
	 * @param tsmLoanReviewInfoDto
	 */
	@RequestMapping("/exportData")
	@ResponseBody
	public void exportData(HttpServletResponse response, TsmLoanReviewInfoDto tsmLoanReviewInfoDto) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			tsmLoanReviewInfoDto.setIsPage(2);
			tsmLoanReviewInfoDto.setOrgId(user.getOrgId());
			List<TsmLoanReviewInfoDto> list = loanReviewService.getReviewList(user, tsmLoanReviewInfoDto);
			OutputStream ouputStream = response.getOutputStream();
			String downloadFileName = URLEncoder.encode("审核信息列表.xlsx","UTF-8");
			response.setContentType("application/vnd.ms-excel");    
			response.setHeader("Content-disposition", String.format("attachment;filename=%s", downloadFileName));
			loanReviewService.exportData(list, tsmLoanReviewInfoDto,ouputStream);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 列表查询
	 * @param request
	 * @param tsmLoanReviewInfoDto
	 * @return
	 */
	@RequestMapping("/reviewList")
	public String findReviewInfos(HttpServletRequest request,  TsmLoanReviewInfoDto tsmLoanReviewInfoDto) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			request.setAttribute("groupList", cachedService.getResGroupList(user.getOrgId()));
			request.setAttribute("shiroUser", user);
			List<CustSearchSet> tlist = cachedService.getQupaiIsTextQueryList(user.getOrgId(),SysEnum.QIPAI_SEARCH_SET_MODULE_2.getState());
			request.setAttribute("tList", tlist); // 文本类型查询字段列表
			Map<String,String> definedNameMap = getQupaiIsDefinedNameList(user.getOrgId());
			request.setAttribute("definedNameMap", definedNameMap);
			// 用于筛选项排序
			Map<String,Integer> mutilSearchCodeSortMap = cachedService.getQupaiUnTestSearchSort(user.getOrgId(), SysEnum.QIPAI_SEARCH_SET_MODULE_2.getState());
			request.setAttribute("mutilSearchCodeSortMap", mutilSearchCodeSortMap);
			request.setAttribute("mutilSearchCodeSortMapJson",JsonUtil.getJsonString(mutilSearchCodeSortMap));
			// 需隐藏列的序号
			List<String[]> modultList = new ArrayList<String[]>();
			if(user.getIsState() == 0){
				modultList = SearchListShowCodeDto.modult_18_List;
			}else {
				modultList = SearchListShowCodeDto.modult_20_List;
			}
			List<Integer> sorts = cachedService.getQupaiHideSortListCode(SysEnum.QIPAI_SEARCH_SET_MODULE_2.getState(),user.getOrgId(),user.getIsState().toString(),modultList,1);
			request.setAttribute("sorts", sorts);

			// 高级查询项目
			List<HighSearchDto> dtos = cachedService.getQupaiHighSearch(SysEnum.QIPAI_SEARCH_SET_MODULE_2.getState(), user.getOrgId(), user.getIssys());
			List<HighSearchDto> definedDtos = new ArrayList<HighSearchDto>();
			for (HighSearchDto dto : dtos) {
				if (dto.getIsFiledSet() == 1 && dto.getIsQuery() == 1) {
					definedDtos.add(dto);
				}
			}
			request.setAttribute("item", tsmLoanReviewInfoDto);
			request.setAttribute("definedSearchCodes", definedDtos); // 自定义非文本项查询集合
			request.setAttribute("definedSearchCodesJson", JsonUtil.getJsonString(definedDtos));
			setIsReplaceWord(request);
		} catch (Exception e) {
			request.setAttribute("result", "-1");
			request.setAttribute("message", "打开列表页面异常");
			logger.error(e.getMessage(), e);
		}
		return "/qupai/review/info/reviewList";
	}
	
	/**
	 * 列表查询
	 * @param request
	 * @param tsmLoanReviewInfoDto
	 * @return
	 */
	@RequestMapping("/findReviewInfoData")
	@ResponseBody
	public Map<String, Object> findReviewInfoData(TsmLoanReviewInfoDto tsmLoanReviewInfoDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			map = loanReviewService.findReviewInfos(tsmLoanReviewInfoDto);
		} catch (Exception e) {
			map.put("result", "-1");
			map.put("message", "列表查询异常");
			logger.error(e.getMessage(), e);
		}
		return map;
	}
	
	
	/**
	 * 列表查询
	 * @param request
	 * @param tsmLoanReviewInfoDto
	 * @return
	 */
	@RequestMapping("/findReviewJsp")
	public String findReviewJsp(HttpServletRequest request,TsmLoanReviewInfoDto tsmLoanReviewInfoDto) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			tsmLoanReviewInfoDto.setIsPage(1);
			request.setAttribute("list", loanReviewService.getReviewList(user, tsmLoanReviewInfoDto));
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return "/qupai/review/info/reviewList";
	}
	
}
