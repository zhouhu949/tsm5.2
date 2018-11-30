package com.qftx.tsm.email;

import com.qftx.base.auth.service.OrgService;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.credit.service.CustFieldSetQupaiService;
import com.qftx.tsm.email.bean.TsmEmailSendLog;
import com.qftx.tsm.email.service.TsmEmailSendLogService;
import com.qftx.tsm.imp.bean.ImportResFileBean;
import com.qftx.tsm.imp.service.ImportResFileService;
import com.qftx.tsm.plan.user.day.service.TestValue;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User�� bxl
 * Date�� 2015/12/25
 * Time�� 15:53
 */
public class TestEmailService extends BaseUtest {

    @Autowired
    private TsmEmailSendLogService tsmEmailSendLogService;
	@Autowired
	private OrgService  orgService;
	@Autowired
	private CustFieldSetQupaiService  custFieldSetQupaiService;

    private Logger logger = Logger.getLogger(TestEmailService.class);
    
	@Test
	public void queryLogResOperateList(){
//		List<String> orgIds=orgService.getAllOrgIds();
		try{
		List<String> orgIds=new ArrayList<String>();
//		orgIds.add("newboss");
//		orgIds.add("newboss3");
//		orgIds.add("qp3");
		orgIds.add("qp5");
		custFieldSetQupaiService.toData(orgIds);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

//    @Test
//    public void queryTest() {
//        TsmEmailSendLog item=new TsmEmailSendLog();
//        item.setOrgId("2");
//        List<TsmEmailSendLog> list = tsmEmailSendLogService.getListPage(item);
//        logger.info(JsonUtil.getJsonString(list));
//
//    }
//    @Test
//    public void testInsertData() {
//        TsmEmailSendLog item=new TsmEmailSendLog();
//        item.setOrgId(shiroUser.getOrgId());
//        item.setId(GuidUtil.getId());
//        item.setInputTime(new Date());
//        item.setUpdateTime(new Date());
//        item.setStatus(1);
//        item.setEmailSendId("1");
//        item.setEmailReviceUser("1");
//        item.setIsDel(0);
//       for(int i=0;i<100;i++){
//           item.setId(GuidUtil.getId());
//           tsmEmailSendLogService.insert(item);
//           logger.info(JsonUtil.getJsonString(item));
//       }
//
//    }
}
