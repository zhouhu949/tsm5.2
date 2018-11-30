package com.qftx.tsm.rest.dao;

import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.rest.service.CallRecordService;
import org.junit.Test;

import java.util.HashMap;

/**
 * Created by bxl on 2015/10/28.
 */
public class TestCallRecord extends BaseUtest {

    @Test
    public void testQueryPage() {
        CallRecordService userDao = (CallRecordService) context.getBean("callRecordService");
        HashMap<String, String> json = new HashMap<String, String>();
        json.put("page", "3");
        json.put("pageSize", "30");
        HashMap<String, Object> list = userDao.queryPageToJson(json);
        System.out.println(JsonUtil.getJsonString(list));

        // AuthUser newuser=userDao.getByCondtion(user);
String str="    <div class=\"main\" style=\"border:none;\">\n" +
        "        <div class=\"reous-infor-title clearfix\">\n" +
        "            <h2 class=\"fl_l\">资源信息提示</h2>\n" +
        "            <a href=\"javascript:void(0)\" id=\"close1\" class=\"closeBtn fl_r\" style=\"margin-right:20px;\">关闭</a>\n" +
        "        </div>\n" +
        "        <div class=\"reous-infor-prom\">\n" +
        "            <div class=\"reous-infor-deco\">\n" +
        "                <span>亲，根据客户的网站地址判断，该客户已经录入公司的客户池，相关信息如下：</span>\n" +
        "            </div>\n" +
        "            <div class=\"coms-infor\">\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">客户名称：</label><span class=\"overflow_hidden fl_l\" title=\"\">广州市米昕蔚服务有限公司</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">联系人：</label><span class=\"overflow_hidden fl_l\" title=\"\">陈道</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">联系电话：</label><span class=\"overflow_hidden fl_l\" title=\"\">18023569567</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">备用电话：</label><span class=\"overflow_hidden fl_l\" title=\"\">18023569567</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">客户状态：</label><span class=\"overflow_hidden fl_l\" title=\"\">意向客户</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">销售进程：</label><span class=\"overflow_hidden fl_l\" title=\"\">详细了解产品</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">所有者：</label><span class=\"overflow_hidden fl_l\" title=\"\">李理</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">首次跟进时间：</label><span class=\"overflow_hidden fl_l\"\n" +
        "                                                                                    title=\"\">2015年8月12日</span></p>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"foot\">\n" +
        "        <div class=\"foot-right\"></div>\n" +
        "    </div>";
    }
}
