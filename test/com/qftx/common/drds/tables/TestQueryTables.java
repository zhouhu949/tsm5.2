package com.qftx.common.drds.tables;

import com.qftx.common.drds.DataSource;

import java.sql.SQLException;

/**
 * User： bxl
 * Date： 2015/12/11
 * Time： 11:35
 */
public class TestQueryTables {
    public static void main(String[] args) throws SQLException {
        showTables();
        querySequences();
        showTableTopology("call_record_info");
        queryTablesChilds("auth_user");
    }

    /**
     * 查询所有表
     *
     * @throws SQLException
     */
    public static void showTables() throws SQLException {
        String str = "show tables";
        DataSource.querySql(str);
    }

    /**
     * 查询表的分布情况，物理库、表等
     * @param table
     * @throws SQLException
     */
    public static void showTableTopology(String table) throws SQLException {
        String str = "show topology from " + table;
        DataSource.querySql(str);
    }

    /**
     * 查询广播表
     *
     * @throws SQLException
     */
    public static void showBroadcasts() throws SQLException {
        String str = "show broadcasts";
        DataSource.querySql(str);
    }

    /**
     * 查询表的分区键
     *
     * @param table 指定表
     * @throws SQLException
     */
    public static void queryPartitionsColumn(String table) throws SQLException {
        String str = "show partitions from " + table;
        DataSource.querySql(str);
    }

    /**
     * 查询慢的SQL
     *
     * @throws SQLException
     */
    public static void querySlowSql() throws SQLException {
        String str = "show slow";
        DataSource.querySql(str);
    }

    /**
     * 查询物理慢的SQL
     *
     * @throws SQLException
     */
    public static void queryPhysicalSlowSql() throws SQLException {
        String str = "show physical_slow ";
        DataSource.querySql(str);
    }

    /**
     * 查询表下分库分表信息
     *
     * @param table 指定表
     * @throws SQLException
     */
    public static void queryTablesChilds(String table) throws SQLException {
        String str = " show topology from " + table;
        DataSource.querySql(str);
    }

    public static void querySequences() throws SQLException {
        String str = "show sequences";
        DataSource.querySql(str);
    }

    public static void createSequences(String seq_name, int startindex) throws SQLException {
        String str = "create sequence " + seq_name + " start with " + startindex;
        DataSource.querySql(str);
    }

    public static void getSequences(String seq_name) throws SQLException {
        String str = "select " + seq_name + ".nextVal from dual";
        DataSource.querySql(str);
    }

    public static void removeSequences(String seq_name) throws SQLException {
        String str = "drop sequence  " + seq_name;
        DataSource.querySql(str);
    }

    public static void alertSequences(String seq_name, int startindex) throws SQLException {
        String str = "alert sequence " + seq_name + " start with " + startindex;
        DataSource.querySql(str);
    }

}
