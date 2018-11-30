package com.qftx.common.drds.tables;

import com.qftx.common.drds.DataSource;

import java.sql.SQLException;

/**
 * User： bxl
 * Date： 2015/12/11
 * Time： 11:10
 */
public class TestCreateTable {
    public static void main(String[] args) throws SQLException {
        String str = "CREATE TABLE `auth_org` (\n" +
                "  `ORG_ID` varchar(32) DEFAULT NULL COMMENT '机构ID',\n" +
                "  `ORG_CODE` varchar(20) DEFAULT NULL COMMENT '机构编码',\n" +
                "  `ORG_NAME` varchar(100) DEFAULT NULL COMMENT '机构名称',\n" +
                "  `ORG_SHORT_NAME` varchar(100) DEFAULT NULL COMMENT '机构简称',\n" +
                "  `LINK_NAME` varchar(20) DEFAULT NULL COMMENT '联系人',\n" +
                "  `LINK_POSITION` varchar(20) DEFAULT NULL COMMENT '联系人职务',\n" +
                "  `LINK_TEL` varchar(20) DEFAULT NULL COMMENT '联系电话',\n" +
                "  `LINK_MOBILE` varchar(20) DEFAULT NULL COMMENT '联系人手机',\n" +
                "  `ORG_EMAIL` varchar(50) DEFAULT NULL COMMENT '机构邮箱',\n" +
                "  `ADDRESS` varchar(100) DEFAULT NULL COMMENT '联系地址',\n" +
                "  `ORG_TEL` varchar(20) DEFAULT NULL COMMENT '机构座机',\n" +
                "  `ORG_FAX` varchar(20) DEFAULT NULL COMMENT '传真',\n" +
                "  `POST_CODE` varchar(10) DEFAULT NULL COMMENT '邮政编码',\n" +
                "  `SYS_TYPE` varchar(2) DEFAULT NULL COMMENT '所属系统：0--资源平台eab_sys，1--单位平台eab，2--受理平台bap，3--代理商平台agp，4--智慧来显cid，5--电销管家',\n" +
                "  `RECORD_HOST` varchar(200) DEFAULT NULL COMMENT '录音主机',\n" +
                "  `SIGNATURE` varchar(100) DEFAULT NULL COMMENT '签名',\n" +
                "  `IS_SHOW_RETURN` int(1) DEFAULT NULL COMMENT '是否显示回执：0-否，1-是',\n" +
                "  `INPUTTIME` datetime DEFAULT NULL COMMENT '录入时间',\n" +
                "  `UPDATE_DATE` datetime DEFAULT NULL COMMENT '更新时间',\n" +
                "  `IS_SET` int(1) DEFAULT NULL COMMENT '录音主机设置：0-未设置，1-设置',\n" +
                "  `STATE` int(2) DEFAULT NULL COMMENT '0-个人资源，1-企业资源',\n" +
                "  `IS_INIT` int(2) DEFAULT NULL COMMENT '是否初始化（0：否，1：是）',\n" +
                "  UNIQUE KEY `SYS_C00253883` (`ORG_ID`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='权限管理_机构组织';\n\n";
        createTablePartition(str, "org_id", 100);
     //   createTableBroadcasts(str);
    }

    /**
     * 创建分库分表
     *
     * @param sql    原始SQL
     * @param column 分库分表字段
     * @param len    分表数量
     * @return boolean
     * @throws SQLException
     */
    public static boolean createTablePartition(String sql, String column, int len) throws SQLException {
        //   Assert.isNull(sql);
        //  Assert.isNull(column);
        StringBuffer buff = new StringBuffer();
        // if (sql.endsWith(";")) {
        sql = sql.replaceAll(";", "");
        //  }
        buff.append(sql);
        if (sql.toLowerCase().indexOf(column.toLowerCase()) == -1) {
            throw new SQLException("not " + column + " Partition");
        }
        buff.append("dbpartition by hash(" + column + ") tbpartition by hash(" + column + ") tbpartitions " + len);
        buff.append(";");
        boolean ret = false;
        try {
            ret = DataSource.execSql(buff.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("执行:" + (ret ? "成功" : "失败"));
        }
        return ret;
    }

    /**
     * 创建广播表
     *
     * @param sql 原始sql
     * @return boolean
     * @throws SQLException
     */
    public static boolean createTableBroadcasts(String sql) throws SQLException {
        StringBuffer buff = new StringBuffer();
        if (sql.endsWith(";")) {
            sql = sql.replaceAll(";", "");
        }
        buff.append(sql);
        buff.append("BROADCAST;");
        boolean ret = false;
        try {
            ret = DataSource.execSql(buff.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("执行:" + (ret ? "成功" : "失败"));
        }
        return ret;
    }
}