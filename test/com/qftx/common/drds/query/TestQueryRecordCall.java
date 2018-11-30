package com.qftx.common.drds.query;

import com.qftx.common.drds.DataSource;

import java.sql.*;
import java.util.ArrayList;
/**
 * Created by bxl on 2015/10/13.
 */
public class TestQueryRecordCall {
    public static void main(String[] args) throws SQLException {
        String sql = "select call_id from tsm_record_call where org_id=\"d9fd7d961f90404387a2910372ece37d\" limit 0,10";
        long start = System.currentTimeMillis();
        querySql(sql);
        System.out.println("time="+(System.currentTimeMillis() - start));
    }
    private static void querySql(String sql) throws SQLException {
        System.out.println(sql);
        Connection conn = DataSource.getDruidDataSource("dataSource7").getConnection();
        Statement st = conn.createStatement();
        ResultSet rec = st.executeQuery(sql);
        ResultSetMetaData resultSetMetaData = rec.getMetaData();
        ArrayList<String> list = new ArrayList<String>();
        int len = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= len; i++) {
            String colname = resultSetMetaData.getColumnName(i);
            list.add(colname);
            System.out.print(colname);
            System.out.print("\t\t");
        }
        System.out.println();
        System.out.println("----------------------------------------------");
        while (rec.next()) {
            for (String s : list) {

                System.out.print(rec.getString(s));
                System.out.print("\t");
            }
            System.out.println();
        }
        //   conn.commit();
        DataSource.close(conn);
    }


}
