package com.qftx.common.drds.query;

import com.qftx.common.drds.DataSource;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by bxl on 2015/10/13.
 */
public class TestDrdsQuerySql {
    public static void main(String[] args) throws SQLException {
        String sql = "select * from call_record_info where org_id=\"1\" limit 0,10";
         sql = "" +
               //  "/*TDDL:tsm_team_group.org_id='8decbe1278b646b5a462bbd4bc80bd58' and xxx.org_id='1' and xxx2.org_id='1' */\n"+
                 "SELECT\n" +
                 "\tt.group_id,\n" +
                 "\tttg.group_name,\n" +
                 "\tSUM((CASE t.status WHEN 0 THEN 1 ELSE 0 END)) not_report,\n" +
                 "\tSUM((CASE WHEN t.status = 1 AND t.auth_state = 1 THEN 1 ELSE 0 END)) checked_plan,\n" +
                 "\tSUM((CASE WHEN T.status = 1 AND t.auth_state = 0 THEN 1 ELSE 0 END)) unchecked_plan\n" +
                 "FROM\n" +
                 "\tplan_userday t LEFT JOIN tsm_team_group ttg \n" +
                 "    ON   ttg.org_id = '8decbe1278b646b5a462bbd4bc80bd58' AND t.group_id = ttg.GROUP_ID \n" +
                 "WHERE t.org_id = '8decbe1278b646b5a462bbd4bc80bd58' AND\n" +
                 "\tt.group_id IN (\n" +
                 "\t\tSELECT\n" +
                 "\t\t\ttgs.GROUP_ID\n" +
                 "\t\tFROM\n" +
                 "\t\t\ttsm_group_shareinfo tgs\n" +
                 "\t\tWHERE tgs.org_id='8decbe1278b646b5a462bbd4bc80bd58' AND\n" +
                 "\t\t\ttgs.share_acc = 'admin'\n" +
                 "\t) GROUP BY t.group_id ";
        long start = System.currentTimeMillis();
        DataSource.querySql(sql);
        System.out.println("time=" + (System.currentTimeMillis() - start));
    }



}
