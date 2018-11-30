package com.qftx.tsm.dao;

import javax.xml.ws.Response;

/**
 * User£º bxl
 * Date£º 2016/7/12
 * Time£º 21:18
 */
public class TestRemoveSqlOrderBY {
    public static void main(String[] args) {
        String sql = " select `m`.`RES_CUST_ID`,`m`.`NAME`,`m`.`MOBILEPHONE`,`m`.`TELPHONE`,`m`.`INPUT_DATE`,`m`.`ORG_ID`,`m`.`OWNER_START_DATE`,`m`.`import_dept_id`,`m`.`RES_GROUP_ID` from ( select `tsm_res_cust_info_7877`.`RES_CUST_ID`,`tsm_res_cust_info_7877`.`NAME`,`tsm_res_cust_info_7877`.`MOBILEPHONE`,`tsm_res_cust_info_7877`.`TELPHONE`,`tsm_res_cust_info_7877`.`INPUT_DATE`,`tsm_res_cust_info_7877`.`ORG_ID`,`tsm_res_cust_info_7877`.`OWNER_START_DATE`,`tsm_res_cust_info_7877`.`import_dept_id`,`tsm_res_cust_info_7877`.`RES_GROUP_ID` from `tsm_res_cust_info_7877` where ((`tsm_res_cust_info_7877`.`is_del` = 0) AND (`tsm_res_cust_info_7877`.`status` = 1) AND (`tsm_res_cust_info_7877`.`import_dept_id` IN ('af1cef35be894c259658e14265862341','51b03bddc7634f9f9e08a8679705278d','0cb3252b6aa44a7f9209b6ec2c800d58')) AND (`tsm_res_cust_info_7877`.`org_id` = 'hyx42') AND (`tsm_res_cust_info_7877`.`type` = 1)) order by `tsm_res_cust_info_7877`.`INPUT_DATE` desc ) `m` order by `m`.`INPUT_DATE` desc";
        System.out.println(sql.split("order by").length);
        System.out.println(sql.split("order by")[0]+"order by"+sql.split("order by")[1]);
    }

}
