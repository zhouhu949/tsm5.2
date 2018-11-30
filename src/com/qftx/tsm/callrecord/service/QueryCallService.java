package com.qftx.tsm.callrecord.service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.dao.OrgMapper;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.callrecord.bean.DtoRestStateBean;
import com.qftx.tsm.callrecord.dao.QuickResCustInfoMapper;
import com.qftx.tsm.callrecord.dto.CallRecordListDto;
import com.qftx.tsm.callrecord.dto.ConditionDto;
import com.qftx.tsm.callrecord.dto.CustCallQueryDto;
import com.qftx.tsm.callrecord.dto.DtoCallRecordInfoBean;
import com.qftx.tsm.callrecord.dto.TsmRecordCallBean;
import com.qftx.tsm.callrecord.dto.TsmRecordCallDto;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.option.bean.DataDictionaryBean;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class QueryCallService {

    private static final Logger logger = Logger.getLogger(QueryCallService.class.getName());
    @Autowired(required = false)
    private OrgMapper orgMapper;

    @Autowired(required = false)
    private CachedService cachedService;

    @Autowired(required = false)
    private OrgGroupUserService orgGroupUserService;
    @Autowired(required = false)
    private OrgGroupService orgGroupService;
    @Autowired(required = false)
    private CallRecordInfoService callRecordInfoService;
    @Autowired(required = false)
    private QuickResCustInfoMapper resCustInfoMapper;

    /**
     * 防骚扰电话查询
     *
     * @param orgId
     * @param account
     * @param telPhone
     * @return
     * @throws Exception
     */
    public DtoRestStateBean queryAnnoy(String orgId, String account, String telPhone) throws Exception {
        DtoRestStateBean restStateBean = new DtoRestStateBean();
        ResCustInfoDto bean = new ResCustInfoDto();
        bean.setOrgId(orgId);
        // bean.setOwnerAcc(account);
        bean.setTelphone(telPhone);
        //todo 1开启 0未开启
        restStateBean.setCode("0");
        restStateBean.setDesc("可以打");
        String str = getUnHarass(orgId);
        logger.debug("是否开启防骚扰状态>>=" + str);
        OrgGroupUser usermeber = new OrgGroupUser();
        usermeber.setOrgId(orgId);
        usermeber.setMemberAcc(account);
        OrgGroupUser newmember = orgGroupUserService.getByCondtion(usermeber);
        logger.debug("是否有组织结构=" + JSON.toJSONString(newmember));
        if (newmember != null && newmember.getGroupId() != null) {
            OrgGroup orgGroup = orgGroupService.getByGroupId(orgId, newmember.getGroupId());
            // ************      服务用户直接返回      *********************
            if (orgGroup != null && orgGroup.getGroupType() == 2) {
                logger.debug("是否服务模块");
                restStateBean.setInputtime(new Date());
                return restStateBean;
            }
        }
        if (str != null && "1".equals(str)) {
            //todo 开启
            List<ResCustInfoDto> list = null;
            Org org = orgMapper.getByPrimaryKey(orgId);
            if (org != null && org.getState() == 1) {
                list = resCustInfoMapper.findCustDetailByTelPhone(bean);
            } else {
                list = resCustInfoMapper.findCustByTelPhone(bean);
            }
            logger.debug("list=" + list.size());
            //1-设置为签约客户不能打电话；2-设置为意向客户不能打电话；3-公海客户当天联系次数不能超过上限；11-共有客户当天是否已联系;0-可以打
            List<DataDictionaryBean> data1 = cachedService.getDirList(AppConstant.DATA42, orgId);
            List<DataDictionaryBean> data2 = cachedService.getDirList(AppConstant.DATA43, orgId);
            String state = "";
            if(data1 !=null && (data1.get(0).getDictionaryValue()=="1"||"1".equals(data1.get(0).getDictionaryValue()))){
            if (data2 != null && data2.size() > 0) {
                state = data2.get(0).getDictionaryValue();
            }
            }
            logger.debug("state=" + state);
            //state 1为意向客户 0是签约客户，4是公海
            List<String> accountList = null;
            for (ResCustInfoDto custbean : list) {
                int custStatus = custbean.getStatus();
                //类型:1-资源，2客户,3-再淘资源
                int custType = custbean.getType();
                logger.debug(">>>>custStatus=" + custStatus + "|custType=" + custType + "|state=" + state);
                
                /**
                 * 开启共有客户，今天如果已联系（有效通话）
                 * 共有客户必须签约客户或者意向客户
                 */
                int isopen=getCommonOwnerOpenState(orgId);
                if(isopen==1&& (custStatus == 2 || custStatus == 3 || custStatus == 6)&& custType == 2 
                	&& custbean.getCommonAcc()!=null && !"".equals(custbean.getCommonAcc())){
                	CustCallQueryDto tsmRecordCallBean=new CustCallQueryDto();
                	tsmRecordCallBean.setCustId(custbean.getResCustId());
                	tsmRecordCallBean.setOrgId(orgId);
//                	tsmRecordCallBean.setCallState("2");
                	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
                	java.util.Date date=new java.util.Date();  
                	String strDate=sdf.format(date);  
                	tsmRecordCallBean.setStartTimeBegin(strDate + " 00:00:00");
                	tsmRecordCallBean.setStartTimeEnd(strDate + " 23:59:59");
                	List<TsmRecordCallBean> reslist= callRecordInfoService.getRecordeCallList(tsmRecordCallBean);

                	int callNum=0;
                	if(list!=null&&list.size()>0){
                		for(TsmRecordCallBean beans : reslist){
                			if("2".endsWith(beans.getCallState())){
                			if(beans.getTimeLength()>0){
                				callNum=callNum+1;
                			}
                		}
                		}
                	}
                	if(callNum>0){
                   	 restStateBean.setCode("11");
                        restStateBean.setDesc("共有客户今天已联系");
                   	}
//                	}

                }else{
                /**
                 * 开启公海客户防骚扰
                 */
                int isCommonSeaopen=getCommonSeaOpenState(orgId);
                if(isCommonSeaopen ==1&& custStatus == 4){
                	//通过接口查询当前资源当日有效通话
                	CustCallQueryDto tsmRecordCallBean=new CustCallQueryDto();
                	tsmRecordCallBean.setCustId(custbean.getResCustId());
                	tsmRecordCallBean.setOrgId(orgId);
//                	tsmRecordCallBean.setCallState("2");
                	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
                	java.util.Date date=new java.util.Date();  
                	String strDate=sdf.format(date);  
                	tsmRecordCallBean.setStartTimeBegin(strDate + " 00:00:00");
                	tsmRecordCallBean.setStartTimeEnd(strDate + " 23:59:59");
                	List<TsmRecordCallBean> reslist= callRecordInfoService.getRecordeCallList(tsmRecordCallBean);
                	int callNum=0;
                	if(list!=null&&list.size()>0){
                		for(TsmRecordCallBean beans : reslist){
                			if("2".endsWith(beans.getCallState())){
                			if(beans.getTimeLength()>0){
                				callNum=callNum+1;
                			}
                		}
                		}
                	}
                	List<DataDictionaryBean> dlist2 = cachedService.getDirList(AppConstant.DATA_50016, orgId);
                	if(!dlist2.isEmpty() && dlist2.get(0) != null){
					String	comSeaValue = dlist2.get(0).getDictionaryValue();
					if(!"".equals(comSeaValue)&&comSeaValue.length()>0){
					if(callNum>=Integer.valueOf(comSeaValue)){
	                restStateBean.setCode("3");
	                restStateBean.setDesc("该公海客户今日已联系"+comSeaValue+"次，已达到该公海客户当日允许联系上限");
					}
					}
                	}
                	}
                }
//            }
            }
        }
        restStateBean.setInputtime(new Date());
        return restStateBean;
    }
    
    // 返回客户防骚扰相关设置值

    private String getUnHarass(String orgId) {
        String flg = "0";
        try {

            		
                    // 查询 跟进警报提醒是否开启
                    List<DataDictionaryBean> data3 = cachedService.getDirList(AppConstant.DATA_50011, orgId);
                    List<DataDictionaryBean> data4 = cachedService.getDirList(AppConstant.DATA_50016, orgId);
                    List<DataDictionaryBean> data1 = cachedService.getDirList(AppConstant.DATA42, orgId);
                    if ((!data3.isEmpty() && "1".equals(data3.get(0).getDictionaryValue())) ||
                    (!data1.isEmpty() && "1".equals(data1.get(0).getDictionaryValue())&&!data4.isEmpty() && "1".equals(data4.get(0).getIsOpen()))) {
                        flg = "1";
                    }     
        } catch (Exception e) {
            //
        }
        return flg;
    }
    
	/**共有者开关  0-关闭 1-开启*/
	private int getCommonOwnerOpenState(String orgId){
		int open = 0;
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50011, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getDictionaryValue();
			open = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		return open;
	}
	
	/**公海客户开关  0-关闭 1-开启*/
	private int getCommonSeaOpenState(String orgId){
		int open = 0;
		List<DataDictionaryBean> data1 = cachedService.getDirList(AppConstant.DATA42, orgId);
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50016, orgId);
		 if(data1 !=null && (data1.get(0).getDictionaryValue()=="1"||"1".equals(data1.get(0).getDictionaryValue()))){
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getIsOpen();
			open = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		 }
		return open;
	}

}
