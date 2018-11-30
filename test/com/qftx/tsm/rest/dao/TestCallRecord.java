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
        "            <h2 class=\"fl_l\">��Դ��Ϣ��ʾ</h2>\n" +
        "            <a href=\"javascript:void(0)\" id=\"close1\" class=\"closeBtn fl_r\" style=\"margin-right:20px;\">�ر�</a>\n" +
        "        </div>\n" +
        "        <div class=\"reous-infor-prom\">\n" +
        "            <div class=\"reous-infor-deco\">\n" +
        "                <span>�ף����ݿͻ�����վ��ַ�жϣ��ÿͻ��Ѿ�¼�빫˾�Ŀͻ��أ������Ϣ���£�</span>\n" +
        "            </div>\n" +
        "            <div class=\"coms-infor\">\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">�ͻ����ƣ�</label><span class=\"overflow_hidden fl_l\" title=\"\">���������ε�������޹�˾</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">��ϵ�ˣ�</label><span class=\"overflow_hidden fl_l\" title=\"\">�µ�</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">��ϵ�绰��</label><span class=\"overflow_hidden fl_l\" title=\"\">18023569567</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">���õ绰��</label><span class=\"overflow_hidden fl_l\" title=\"\">18023569567</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">�ͻ�״̬��</label><span class=\"overflow_hidden fl_l\" title=\"\">����ͻ�</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">���۽��̣�</label><span class=\"overflow_hidden fl_l\" title=\"\">��ϸ�˽��Ʒ</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">�����ߣ�</label><span class=\"overflow_hidden fl_l\" title=\"\">����</span>\n" +
        "                </p>\n" +
        "\n" +
        "                <p class=\"clearfix\"><label class=\"fl_l\" for=\"\">�״θ���ʱ�䣺</label><span class=\"overflow_hidden fl_l\"\n" +
        "                                                                                    title=\"\">2015��8��12��</span></p>\n" +
        "            </div>\n" +
        "        </div>\n" +
        "    </div>\n" +
        "    <div class=\"foot\">\n" +
        "        <div class=\"foot-right\"></div>\n" +
        "    </div>";
    }
}