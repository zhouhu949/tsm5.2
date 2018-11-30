package com.qftx.tsm.call;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.HttpUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * 模拟通话数据提交
 * User： bxl
 * Date： 2015/12/2
 * Time： 13:39
 */
public class TestInsertCallData extends BaseUtest {

    @Autowired(required = false)
    private UserService userService;
    @Autowired(required = false)
    private ResCustInfoMapper resCustInfoMapper;

    @Test
    public void TestInsert() throws Exception {
        User user = userService.getByAccount("hn001");
        ResCustInfoBean bean = new ResCustInfoBean();
        bean.setOrgId(user.getOrgId());
        bean.setInputAcc(user.getUserAccount());

        List<ResCustInfoBean> list = resCustInfoMapper.findByCondtion(bean);
        System.out.println("list=" + list.size());
        for (ResCustInfoBean item : list) {
            System.out.println(item.getName());
            save( item,user);
        }
        //  save("orgId", "orgName", "inputAcc", "custId", "1", "custName", "followId", "saleProcessId");
    }

    private int getNum(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max) % (max - min + 1) + min;
        return randomNumber;
    }

    public void save(ResCustInfoBean item,User user) throws Exception {
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("orgId", item.getOrgId());
        map.put("code", GuidUtil.getId());
        map.put("orgName", user.getOrgName());
        map.put("startTime", DateUtil.getDataAll());
        map.put("inputAcc", user.getUserAccount());
        map.put("saleProcessId", item.getResCustId());
        map.put("followId", "");
        map.put("timeLength", getNum(3, 20) + "");
        map.put("custId", item.getResCustId());
        map.put("custType", item.getType()+"");
        map.put("custName", item.getName());
        map.put("callerNum", "133123456789");
        map.put("callerCommNum", "130123456789");
        //String str = TestHttpCallData.doPostJSON("http://192.168.1.210:8080/callrecordinfo/save", map);
        String str = HttpUtil.doPostJSON("http://121.196.225.128/callrecordinfo/save", map);
        System.out.println("返回数据:" + str);
    }
}
