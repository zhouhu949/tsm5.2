package com.qftx.tsm.execl;

import com.alibaba.fastjson.JSON;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.Page;
import com.qftx.tsm.cust.bean.ResCustInfoDetailBean;
import com.qftx.tsm.cust.dao.ResCustInfoDetailMapper;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * User： bxl
 * Date： 2016/8/15
 * Time： 9:25
 */
public class TestExportData extends BaseUtest {
    @Autowired(required = false)
    private ResCustInfoMapper service;
    @Autowired(required = false)
    private ResCustInfoDetailMapper serviceDetail;

//    @Test
//    public void export() throws IOException {
//        ResCustInfoDto bean = new ResCustInfoDto();
//        bean.setOrgId("hbwd");
//        List<ResCustInfoDto> list = service.findAllTypeCustListPage(bean);
//        System.out.println(JSON.toJSONString(bean.getPage(), true));
//        Page page = new Page();
//        page.setShowCount(bean.getPage().getTotalResult());
//        bean.setPage(page);
//        list = service.findAllTypeCustListPage(bean);
//        System.out.println(list.size());
//        OutputStream out = new FileOutputStream("d://hbwd.xlsx");
//        String[] headers = {"姓名", "公司名称", "常用电话", "备用电话"};
//        String[] keyValue = {"name", "company", "mobilephone", "telphone"};
//        //  tt.exportExcel(headers, keyValue, dataset, out, "yyyy-MM-dd HH:mm:ss");
//        TestDemoExeclData.exportExcel(headers, keyValue, list, out, "yyyy-MM-dd");
//        out.close();
//    }

    @Test
    public void company() throws IOException {
        ResCustInfoDto bean = new ResCustInfoDto();
        bean.setOrgId("hbwd");
        List<ResCustInfoDto> list = service.findAllTypeCustListPage(bean);
        System.out.println(JSON.toJSONString(bean.getPage(), true));
        Page page = new Page();
        page.setShowCount(bean.getPage().getTotalResult());
        bean.setPage(page);
        list = service.findAllTypeCustListPage(bean);
        ArrayList<ResCustInfoDto> data = new ArrayList<ResCustInfoDto>();
        for (ResCustInfoDto resCustInfoDto : list) {
            ResCustInfoDto dto = new ResCustInfoDto();
            BeanUtils.copyProperties(resCustInfoDto, dto);
            List<ResCustInfoDetailBean> rdcs = serviceDetail.findCustsInfoDetails(bean.getOrgId(), resCustInfoDto.getResCustId());
            if (rdcs.size()>0) {
                dto.setCompany(rdcs.get(0).getName());
                dto.setMobilephone(rdcs.get(0).getTelphone());
                dto.setTelphone(rdcs.get(0).getTelphonebak());
            }
            data.add(dto);
            System.out.println(">>>>>>>>>>>" + data.size());
        }
        System.out.println(list.size());
        OutputStream out = new FileOutputStream("d://hbwd.xlsx");
        String[] headers = {"单位名称", "客户姓名", "常用电话", "备用电话"};
        String[] keyValue = {"name", "company", "mobilephone", "telphone"};
        TestDemoExeclData.exportExcel(headers, keyValue, data, out, "yyyy-MM-dd");
        out.close();
    }
}
