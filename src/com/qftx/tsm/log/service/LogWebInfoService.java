package com.qftx.tsm.log.service;

import com.qftx.base.auth.bean.AuthProductResource;
import com.qftx.base.auth.service.AuthProductReousrceService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.LogUtil;
import com.qftx.tsm.log.bean.LogWebInfoBean;
import com.qftx.tsm.log.dao.LogWebInfoMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User�� bxl
 * Date�� 2016/1/15
 * Time�� 10:58
 */
@Service
public class LogWebInfoService {
    protected static Log logger = LogFactory.getLog(LogWebInfoService.class);
    @Autowired
    private LogWebInfoMapper logWebInfoMapper;
    @Autowired
    private AuthProductReousrceService productReousrceService;
    public static List<LogWebInfoBean> list = new ArrayList<LogWebInfoBean>();
    public static List<AuthProductResource> listResource = new ArrayList<AuthProductResource>();

    public void save(Long timeLength, HttpServletRequest request) {
        String url = request.getRequestURI();
        String ip = request.getHeader("X-Real-IP");
        if (ip == null) ip = request.getRemoteAddr();
        ShiroUser user = ShiroUtil.getUser();
        if (user != null) {
            LogWebInfoBean bean = new LogWebInfoBean();
            bean.setId(GuidUtil.getId());
            bean.setTimeLength(timeLength);
            bean.setIp(ip);
            bean.setWebUrl(url);
            bean.setCode(GuidUtil.getId());
            bean.setUserId(user.getId());
            bean.setAccount(user.getName());
            bean.setName(getName(url,user.getProCode()));
            bean.setOrgId(user.getOrgId());
            bean.setUpdatetime(new Date());
            bean.setInputtime(new Date());
            bean.setIsDel(0);
            bean.setData(LogUtil.getRequestLog(request));
            list.add(bean);
            logger.debug("webLogSize="+list.size());
            if (list.size() >= 20) {
                logWebInfoMapper.insertBatch(list);
                list.clear();
             }
        }
    }

    public String getName(String name,String proCode) {
        if (listResource == null||listResource.size()==0) {
        	AuthProductResource entity = new AuthProductResource();
        	entity.setProCode(proCode);
            listResource = productReousrceService.getListByCondtion(entity);
        }
    //    logger.debug("listResource="+listResource.size());
        for (AuthProductResource resource : listResource) {
           // logger.debug("["+resource.getResourceString()+"]===["+name+"]"+(name.trim().equals(resource.getResourceString().trim())));
            if (name.trim().equals(resource.getResourceString().trim())) {
                return resource.getResourceName();
            }
        }
        return "";
    }
}
