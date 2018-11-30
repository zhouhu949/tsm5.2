package com.qftx.base.auth.ca;

import com.alibaba.fastjson.JSON;
import com.qftx.base.ca.bean.CaDataBean;
import com.qftx.base.util.JsonUtil;

/**
 * User： bxl
 * Date： 2015/12/31
 * Time： 11:24
 */
public class TestParserJson {
    public static void main(String[] args) {
       String str="{\"code\":\"_SUCCESS\",\"descr\":\"成功\",\"basicInfo\":{\"time\":\"2015-12-31 11:22:47\",\"key\":\"20151231112247080c5aaf894baccbed\",\"mescUrl\":\"192.168.1.13:9978\",\"isAdmin\":false,\"bizType\":\"1\",\"ipChange\":false,\"isPwdUpd\":false,\"pwdUpdTime\":\"2015-09-14 14:39:30\"},\"params\":[{\"mP\":\"CALL_TRANS\",\"cP\":\"OPEN\",\"val\":\"1\"},{\"mP\":\"HYX_CONF\",\"cP\":\"FindPwdUrl\",\"val\":\"http://www.qftx.com/view/service.jsp\"},{\"mP\":\"HYX_CONF\",\"cP\":\"OnlineHelp\",\"val\":\"http://www.qftx.com\"},{\"mP\":\"HYX_CONF\",\"cP\":\"TEMIF\",\"val\":\"http://192.168.1.11:8065/tsmif/ContactsServlet,http://192.168.1.177:8065/tsmif/ContactsServlet,http://192.168.1.13:8065/tsmif/ContactsServlet\"},{\"mP\":\"HYX_CONF\",\"cP\":\"TSM_INDEX\",\"val\":\"http://192.168.1.11:8081/tsm4/avoid/externalAvoidLanding.do,http://192.168.1.177:8081/tsm4/avoid/externalAvoidLanding.do,http://192.168.1.13:8081/tsm4/avoid/externalAvoidLanding.do\"},{\"mP\":\"HYX_CONF\",\"cP\":\"TSM_LOGIN\",\"val\":\"http://183.129.206.158:8180/\"},{\"mP\":\"PUB_CONF\",\"cP\":\"BILL_QUERY\",\"val\":\"http://192.168.10.111:9951/pc,http://192.168.1.11:9951/pc,http://192.168.10.11:9951/pc\"},{\"mP\":\"PUB_CONF\",\"cP\":\"BILL_SERVER\",\"val\":\"http://192.168.1.177:9952/pc,http://192.168.1.11:9952/pc,http://192.168.10.11:9952/pc\"},{\"mP\":\"PUB_CONF\",\"cP\":\"CONF_SERVER\",\"val\":\"http://192.168.1.11:8055/resif/ContactsServlet\"},{\"mP\":\"PUB_CONF\",\"cP\":\"LOG_SERVER\",\"val\":\"http://192.168.1.13:9982/logpc/pc\"},{\"mP\":\"PUB_CONF\",\"cP\":\"RECORD_PHONE\",\"val\":\"http://192.168.1.11:8060/us,http://183.129.160.249:8060/us,http://192.168.1.13:8060/us\"},{\"mP\":\"PUB_CONF\",\"cP\":\"RECORD_PUBLIC\",\"val\":\"http://192.168.1.11:8050/us\"},{\"mP\":\"PUB_CONF\",\"cP\":\"UPDATE_SERVER\",\"val\":\"http://183.129.206.158:8097/AutoUpdate/VerUpdateItf\"},{\"mP\":\"PUB_CONF\",\"cP\":\"WORDSSET\",\"val\":\"1\"}]}";
        CaDataBean bean=  JSON.parseObject(str, CaDataBean.class);
        System.out.println(bean.getBasicInfo().getTime());

        System.out.println(JSON.toJSONString(bean, true));
        System.out.println(JsonUtil.getJsonString(bean));
    }
}
