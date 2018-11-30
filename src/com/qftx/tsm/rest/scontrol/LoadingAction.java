package com.qftx.tsm.rest.scontrol;

import com.alibaba.fastjson.JSON;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.cached.CachedUtil;
import com.qftx.common.util.MD5Utils;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by hh on 14-12-19.
 */
@Controller
@RequestMapping("/load")
public class LoadingAction {
    private static final Logger logger = Logger.getLogger(LoadingAction.class.getName());
    @Autowired
    private ResCustInfoService resCustInfoService;
    @Autowired
    private CachedService cachedService;

    @RequestMapping("/demo")
    public String list(HttpServletRequest request, ResCustInfoDto resCustInfoDto) throws Exception {
        logger.debug("-----------------免登系统---------------------");
        myRests(request, resCustInfoDto);
        return "rest/res/demo";
    }
    @RequestMapping("/test")
    public String test(HttpServletRequest request, ResCustInfoDto resCustInfoDto) throws Exception {
        logger.debug("-----------------免登系统---------------------");
        myRests(request, resCustInfoDto);
        return "rest/res/test";
    }

    @RequestMapping("/fmt")
    public String fmt(HttpServletRequest request, ResCustInfoDto resCustInfoDto) throws Exception {
        logger.debug("-----------------fmt---------------------");
        long lenTime = System.currentTimeMillis();
        List<ResCustInfoDto> list = new ArrayList<ResCustInfoDto>();
        ResCustInfoDto item=null;
        for (int i = 0; i < 100; i++) {
            item = new ResCustInfoDto();
            item.setPlanDate(new Date());
            item.setName("test" + i);
            item.setResCustId(GuidUtil.getId());
            item.setOwnerStartDate(new Date());
            item.setActionDate(new Date());
            list.add(item);
        }
        request.setAttribute("list", list);
        request.setAttribute("list1", new ArrayList<ResCustInfoDto>());
        logger.debug("list>>" + (System.currentTimeMillis() - lenTime));
        long lenTime1 = System.currentTimeMillis();
        request.setAttribute("user", new ShiroUser());
        logger.debug("user>>" + (System.currentTimeMillis() - lenTime1));
        logger.debug("sumtime>>" + (System.currentTimeMillis() - lenTime));

        return "rest/res/fmt";
    }
    @RequestMapping()
    public String myRests(HttpServletRequest request, ResCustInfoDto resCustInfoDto) {
        long lenTime = System.currentTimeMillis();
        String key=MD5Utils.getMD5String(JSON.toJSONString(resCustInfoDto));
        Map mapData =(Map)  CachedUtil.getInstance().get(key);
        logger.debug("[我的客户->资源->读取资源]key="+key);
        if( mapData!=null){
            request.setAttribute("rests",mapData.get("rests"));
            request.setAttribute("resCustInfoDto",mapData.get("resCustInfoDto"));
            request.setAttribute("shiroUser",mapData.get("shiroUser"));
            request.setAttribute("filedMap",mapData.get("filedMap"));
            request.setAttribute("idReplaceWord",mapData.get("idReplaceWord"));
            logger.debug("[我的客户->资源->读取资源]取缓存...."+(System.currentTimeMillis() - lenTime) + "毫秒");
            return "rest/res/load";
        }
        try {
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
                resCustInfoDto.setNoteType("4");
            }
            resCustInfoDto.setNoteType("1");//全部
            // 处理添加/分配时间
            if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
                resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
                resCustInfoDto.setPendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
            }
            // 处理最近联系时间
            if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
                resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
                resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
            }

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
            setIsReplaceWord(request);
            logger.debug("[我的客户->资源->读取模糊电话号码开关缓存]消耗:" + (System.currentTimeMillis() - start) + "毫秒");
            Map map = new HashMap();
            map.put("rests", request.getAttribute("rests"));
            map.put("resCustInfoDto", request.getAttribute("resCustInfoDto"));
            map.put("shiroUser", request.getAttribute("shiroUser"));
            map.put("filedMap", request.getAttribute("filedMap"));
            map.put("idReplaceWord", request.getAttribute("idReplaceWord"));
            CachedUtil.getInstance().set(key,  map,30);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        logger.debug("[我的客户->资源->读取处理逻辑总消耗:" + (System.currentTimeMillis() - lenTime) + "毫秒");

        return "rest/res/load";
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
     * 获取第一天
     *
     * @param type 1-当天 2-本周 3-本月 4-半年
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

    /**
     * 设置是否需要模糊电话号码
     *
     * @param request
     * @create 2015年11月25日 下午2:45:20 lixing
     * @history
     */
    public void setIsReplaceWord(HttpServletRequest request) {
        ShiroUser user = ShiroUtil.getShiroUser();
        List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA24, user.getOrgId());
        Integer idReplaceWord = 0;
        if (list.size() > 0) {
            idReplaceWord = new Integer(list.get(0).getDictionaryValue());
        }
        request.setAttribute("idReplaceWord", idReplaceWord);
    }

    /**
     * 设置是否需要模糊电话号码
     *
     * @param
     * @create 2015年11月25日 下午2:45:20 lixing
     * @history
     */
    public Integer getReplaceWord() {
        ShiroUser user = ShiroUtil.getShiroUser();
        List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA24, user.getOrgId());
        Integer idReplaceWord = 0;
        if (list.size() > 0) {
            idReplaceWord = new Integer(list.get(0).getDictionaryValue());
        }
        return idReplaceWord;
    }

}
