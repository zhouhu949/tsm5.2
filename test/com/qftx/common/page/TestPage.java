package com.qftx.common.page;

import com.alibaba.fastjson.JSON;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * Created by bxl on 2017/6/27.
 */
public class TestPage extends BaseUtest{
    @Autowired(required = false)
    private ResCustInfoMapper dao;
    @Test
    public void queryPage() throws InterruptedException {
        ResCustInfoDto item=new ResCustInfoDto ();
        item.setOrgId("fhtx");
        item.setOwnerAccs(Arrays.asList((new String[]{"fhtx001"})));
        item.setOwnerAcc("fhtx001");
        item.setOrderKey("res_cust_id desc");
        item.getPage().setShowCount(50);
        List<ResCustInfoDto> list = dao.findMyResCustListPage(item);
        System.out.println(JSON.toJSONString(item.getPage(),true));
        System.out.println(JSON.toJSONString(list));
        int pagecount = item.getPage().getTotalPage();
        for (int i = 1; i <= pagecount; i++) {
            item.getPage().setCurrentPage(i);
            list = dao.findMyResCustListPage(item);
            System.out.println(JSON.toJSONString(item.getPage(),true));
            System.out.println("list=" + JSON.toJSONString(list));

            Thread.sleep(1000);
        }

    }
}
