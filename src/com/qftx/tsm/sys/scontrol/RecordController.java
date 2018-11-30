package com.qftx.tsm.sys.scontrol;

import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.action.BaseAction;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.dto.OptionDto;
import com.qftx.tsm.option.service.OptionService;
import com.qftx.tsm.sys.bean.TsmCustReview;
import com.qftx.tsm.sys.dto.TsmCustReviewDto;
import com.qftx.tsm.sys.service.TsmCustReviewService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 录音控制类
 * 
 * @author: wuwei
 * @since: 2015年11月13日 下午1:08:19
 * @history:
 */
@Controller
@RequestMapping("/sys/record/")
public class RecordController extends BaseAction {
	private static Logger logging = Logger.getLogger(RecordController.class);
	@Autowired
	private TsmCustReviewService tsmCustReviewService;
	@Autowired
	private OptionService optionService;
	@Autowired
	transient private OrgGroupService orgGroupService;
	@Autowired
	transient private OrgGroupUserService orgGroupUserService;
	@Resource
	private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Autowired
	private CachedService cachedService;

	@RequestMapping(value = "recordList")
	public String recordList(HttpServletRequest request, TsmCustReviewDto reviewDto) {
		ShiroUser user = ShiroUtil.getUser();
		reviewDto.setOrgId(user.getOrgId());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orgId", user.getOrgId());
		try {
			// 不区分普通和管理员，都可以查看全部成员权限
			reviewDto.setOsType(StringUtils.isBlank(reviewDto.getOsType()) ? "1" : reviewDto.getOsType());
			if ("1".equals(reviewDto.getOsType())) {
				OrgGroupUser entity = new OrgGroupUser();
				entity.setOrgId(user.getOrgId());
				List<OrgGroupUser> list = orgGroupUserService.getListByCondtion(entity);
				tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if (list != null && list.size() > 0) {
					List<String> accountList = new ArrayList<String>();
					for (OrgGroupUser orgGroupUser : list) {
						accountList.add(orgGroupUser.getMemberAcc());
					}
					accountList.add(user.getAccount());
					reviewDto.setOwnerAccs(accountList);
				} else {
					request.setAttribute("project_path", getProgetUtil(request));
					request.setAttribute("relist", null);
					request.setAttribute("reviewDto", reviewDto);
					request.setAttribute("account", user.getAccount());
					return "tsm/sys/record_list";
				}
			} else if ("2".equals(reviewDto.getOsType())) {
				reviewDto.setOwnerAccs(null);
				reviewDto.setOwnerAcc(user.getAccount());
			} else {
				// 处理拥有者
				if (StringUtils.isNotBlank(reviewDto.getOwnerAccsStr())) {
					reviewDto.setOwnerAccs(Arrays.asList(reviewDto.getOwnerAccsStr().split(",")));
				} else {
					reviewDto.setOwnerAccsStr(user.getAccount());
					reviewDto.setOwnerAccs(Arrays.asList(user.getAccount()));
					reviewDto.setOwnerUserIdsStr(user.getId());
				}
			}
			if (reviewDto.getoDateType() != null && !"".equals(reviewDto.getoDateType()) && !"5".equals(reviewDto.getoDateType())) {
				reviewDto.setStartDate(getStartDateStr(new Integer(reviewDto.getoDateType())));
				reviewDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
			List<OptionBean> oplist = cachedService.getOptionList(AppConstant.RECORD_CODE, user.getOrgId());
			request.setAttribute("oplist", oplist);

			// 默认定位第一个
			if (oplist != null && oplist.size() > 0 && reviewDto.getRecordExampId() == null) {
				// reviewDto = new TsmCustReviewDto();
				reviewDto.setRecordExampId(oplist.get(0).getOptionlistId());
			}
			reviewDto.setOrgId(user.getOrgId());
			List<TsmCustReviewDto> relist = tsmCustReviewService.getRecordReviewListPage(reviewDto);
			if (relist != null && relist.size() > 0) {
				for (TsmCustReviewDto resCustReviewDto : relist) {
					resCustReviewDto.setOwnerName(getUserName(user.getOrgId(), resCustReviewDto.getOwnerAcc()));
					resCustReviewDto.setReviewName(getUserName(user.getOrgId(), resCustReviewDto.getReviewAcc()));
				}
			}
			// 服务地址，为了提供给客户端，弹出播放列表
			request.setAttribute("project_path", getProgetUtil(request));
			request.setAttribute("relist", relist);
			request.setAttribute("reviewDto", reviewDto);
			request.setAttribute("account", user.getAccount());
			request.setAttribute("isSys", user.getIssys());
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "tsm/sys/record_list";
	}

	@RequestMapping()
	public String toAddReview(HttpServletRequest request, String id) {
		try {

		} catch (Exception e) {
			logging.error(e.getMessage(), e);
			throw new SysRunException(e);
		}
		return "";
	}

	/**
	 * 删除录音分类
	 * 
	 * @return
	 * @create 2013-11-7 下午02:53:24 徐承恩
	 */
	@ResponseBody
	@RequestMapping("delRecordReview")
	public String deleteRecordReview(HttpServletRequest request) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			String ids = request.getParameter("ids");
			String delParam = request.getParameter("delParam");
			String result = RecordToExampleApi.deleteExample( user.getOrgId(), delParam);
			String[] ids_ = ids.split("\\,");
			List<String> id = Arrays.asList(ids_);
			tsmCustReviewService.removeBatch(id, user.getOrgId());
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			logging.error("loginName =" + user.getName() + "删除录音异常" + e.getMessage(), e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	@RequestMapping("uploadPage")
	public String uploadPage(HttpServletRequest request) {
		request.setAttribute("opid", request.getParameter("opid"));
		request.setAttribute("tsmUpLoadServiceUrl", ConfigInfoUtils.getStringValue("tsm_upload_service_url")); // 上传服务器路径
		return "tsm/sys/uploadRecord";
	}

	@RequestMapping("toaddReview")
	public String toaddReview(HttpServletRequest request, TsmCustReviewDto reviewDto) {
		try {
			reviewDto.setOrgId(ShiroUtil.getUser().getOrgId());
			// 取得左侧列表
			List<OptionDto> oplist = optionService.getRecordReviewTypeByOrgId(reviewDto);
			request.setAttribute("oplist", oplist);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysRunException(e);
		}
		return "tsm/sys/addReview";
	}

	@ResponseBody
	@RequestMapping("addReview")
	public String addReview(HttpServletRequest request, TsmCustReview review) {
		try {
			review.setOrgId(ShiroUtil.getUser().getOrgId());
			// 取得左侧列表
		} catch (Exception e) {
			logging.error(e.getMessage(), e);
		}
		return "";
	}

}
