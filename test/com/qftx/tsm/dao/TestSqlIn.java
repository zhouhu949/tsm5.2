package com.qftx.tsm.dao;

import java.text.DecimalFormat;

/**
 * User£º bxl
 * Date£º 2016/7/9
 * Time£º 15:53
 */
public class TestSqlIn {
    public static void main(String[] args) {
        String str="select count(1) from tsm_res_cust_info where org_id='zszsrh' and owner_acc in(select user_account from auth_user where org_id='zszsrh') and  name like '%234234%';";
     String newSql="select count(1) from tsm_res_cust_info where org_id='zszsrh' and owner_acc in(";
        int count=770;
        for (int i = 401; i <count ; i++) {
            newSql+="'zszsrh"+ new DecimalFormat("000").format(i)+"'";
            newSql+=",";
        }
        newSql+="'zszsrh"+count+"'";
        newSql+=") ";
       // newSql+="and  name like '%234234%';";
        System.out.println(newSql);
    }
}
