package com.qftx.common.drds.tables;

import com.qftx.common.drds.DataSource;

import java.sql.SQLException;

/**
 * User�� bxl
 * Date�� 2015/12/11
 * Time�� 11:35
 */
public class TestQueryTables {
    public static void main(String[] args) throws SQLException {
        showTables();
        querySequences();
        showTableTopology("call_record_info");
        queryTablesChilds("auth_user");
    }

    /**
     * ��ѯ���б�
     *
     * @throws SQLException
     */
    public static void showTables() throws SQLException {
        String str = "show tables";
        DataSource.querySql(str);
    }

    /**
     * ��ѯ���ķֲ�����������⡢����
     * @param table
     * @throws SQLException
     */
    public static void showTableTopology(String table) throws SQLException {
        String str = "show topology from " + table;
        DataSource.querySql(str);
    }

    /**
     * ��ѯ�㲥��
     *
     * @throws SQLException
     */
    public static void showBroadcasts() throws SQLException {
        String str = "show broadcasts";
        DataSource.querySql(str);
    }

    /**
     * ��ѯ���ķ�����
     *
     * @param table ָ����
     * @throws SQLException
     */
    public static void queryPartitionsColumn(String table) throws SQLException {
        String str = "show partitions from " + table;
        DataSource.querySql(str);
    }

    /**
     * ��ѯ����SQL
     *
     * @throws SQLException
     */
    public static void querySlowSql() throws SQLException {
        String str = "show slow";
        DataSource.querySql(str);
    }

    /**
     * ��ѯ��������SQL
     *
     * @throws SQLException
     */
    public static void queryPhysicalSlowSql() throws SQLException {
        String str = "show physical_slow ";
        DataSource.querySql(str);
    }

    /**
     * ��ѯ���·ֿ�ֱ���Ϣ
     *
     * @param table ָ����
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