package com.qftx.tsm.rest.servlet;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.filter.AppContextUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Servlet implementation class LoadingServlet
 */
public class LoadingServlet extends HttpServlet {
	private Log logger = LogFactory.getLog(LoadingServlet.class);
	private ResCustInfoService resCustInfoService = (ResCustInfoService)AppContextUtil.getBean("resCustInfoService");
	private CachedService cachedService = (CachedService)AppContextUtil.getBean("cachedService");
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoadingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		long lenTime = System.currentTimeMillis();
        try {
        	ResCustInfoDto resCustInfoDto = new ResCustInfoDto();
            ShiroUser user = ShiroUtil.getShiroUser();
            resCustInfoDto.setOrgId(user.getOrgId());
            if (user.getIssys() != null && user.getIssys() == 1) {
                // 处理拥有者
                if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
                    resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
                } else {
                    resCustInfoDto.setOwnerAccsStr(user.getAccount());
                    resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
                }
            } else {
                resCustInfoDto.setOwnerAcc(user.getAccount());
            }
            // 默认选中全部标签
            if (StringUtils.isBlank(resCustInfoDto.getNoteType())) {
                resCustInfoDto.setNoteType("1");
            }
            // 处理添加/分配时间
//            if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
//                resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
//                resCustInfoDto.setPendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
//            }
            // 处理最近联系时间
//            if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
//                resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
//                resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
//            }

            List<ResCustInfoDto> rests = resCustInfoService.getMyResCustListPage(resCustInfoDto,null);

            Long start = System.currentTimeMillis();
            Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
            logger.debug("[我的客户->资源->读取单位用户名缓存]消耗:" + (System.currentTimeMillis() - start) + "毫秒");

            start = System.currentTimeMillis();
            Map<String, String> groupMap = cachedService.getOrgResGroupNames(user.getOrgId());
            logger.debug("[我的客户->资源->读取资源分组缓存]消耗:" + (System.currentTimeMillis() - start) + "毫秒");

            request.setAttribute("groupList", groupMap);

            start = System.currentTimeMillis();
            for (ResCustInfoDto rcid : rests) {
                if (rcid.getPlanDate() != null) {
                    Date planDate = DateUtil.parseDate(DateUtil.formatDate(rcid.getPlanDate(), DateUtil.DATE_DAY));
                    Date curDate = DateUtil.parseDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
                    if (planDate.compareTo(curDate) == -1) {
                        rcid.setPlanDate(null);
                    }
                }
                rcid.setOwnerName(StringUtils.isNotBlank(rcid.getOwnerAcc()) ? nameMap.get(rcid.getOwnerAcc()) : "");
                rcid.setGroupName(StringUtils.isNotBlank(rcid.getResGroupId()) ? groupMap.get(rcid.getResGroupId()) : "");
            }
            logger.debug("[我的客户->资源->处理查询结果集合数据]消耗:" + (System.currentTimeMillis() - start) + "毫秒");

            request.setAttribute("rests", rests);
            request.setAttribute("resCustInfoDto", resCustInfoDto);
            request.setAttribute("shiroUser", user);

            start = System.currentTimeMillis();
            setCustomFiled(user, request);
            logger.debug("[我的客户->资源->读取字段设置缓存]消耗:" + (System.currentTimeMillis() - start) + "毫秒");

            start = System.currentTimeMillis();
            setIsReplaceWord(request,user);
            logger.debug("[我的客户->资源->读取模糊电话号码开关缓存]消耗:" + (System.currentTimeMillis() - start) + "毫秒");

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.debug("[我的客户->资源->读取处理逻辑总消耗:" + (System.currentTimeMillis() - lenTime) + "毫秒");
        request.getRequestDispatcher("/view/rest/res/load.jsp").forward(request, response);
	}

	public void setCustomFiled(ShiroUser user, HttpServletRequest request) {
        List<CustFieldSet> fieldSets = null;
        if (user.getIsState() == 1) {// 企业资源
            fieldSets = cachedService.getComFiledSet(user.getOrgId());
        } else {
            fieldSets = cachedService.getPersonFiledSet(user.getOrgId());
        }
        Map<String, String> filedMap = new HashMap<String, String>();
        for (CustFieldSet filed : fieldSets) {
            filedMap.put(filed.getFieldCode(), filed.getFieldName());
        }
        request.setAttribute("filedMap", filedMap);
    }
	
	/**
	 * 设置是否开启用*替换电话号码中间四位
	 */
	public void setIsReplaceWord(HttpServletRequest request, ShiroUser user) {
		// 查询缓存
		List<DataDictionaryBean> dataMap = cachedService.getDirList(AppConstant.DATA24, user.getOrgId());
		// 对电话号码中间4位用*号模糊处理设置到session中
		Set<String> dicSet = new HashSet<String>();
		dicSet.add(AppConstant.DATA24 + "_" + user.getOrgId());
		Integer idReplaceWord = 0;
		if (!dataMap.isEmpty() && dataMap.get(0) != null) {
			idReplaceWord = new Integer(dataMap.get(0).getDictionaryValue());
		}
		request.setAttribute("idReplaceWord", idReplaceWord);
	}
}
