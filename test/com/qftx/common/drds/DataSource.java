package com.qftx.common.drds;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by bxl on 2015/10/12.
 */
public class DataSource {
    private static Log logger = LogFactory.getLog(DataSource.class);

    public static void main(String[] args) throws SQLException {
        DruidDataSource druidDataSource = getDruidDataSource("dataSource5");
        System.out.println(druidDataSource.getConnection());
    }

    public static DruidDataSource getDruidDataSource(String datasorce) {
        try {
            Properties prop = new Properties();
            prop.load(DataSource.class.getResourceAsStream("/properties/db.properties"));
            String driverClass = prop.getProperty(datasorce + ".driver");
            String url = prop.getProperty(datasorce + ".url");
            String user = prop.getProperty(datasorce + ".username");
            String password = prop.getProperty(datasorce + ".password");
            System.out.println(url);
            Class.forName(driverClass);
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(driverClass);
            dataSource.setUsername(user);
            dataSource.setPassword(password);
            dataSource.setUrl(url);
            dataSource.setInitialSize(5);
            dataSource.setMinIdle(1);
            dataSource.setMaxActive(10); // 启用监控统计功能  dataSource.setFilters("stat");// for mysql  dataSource.setPoolPreparedStatements(false);
            return dataSource;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("找不到数据库连接的相关驱动类！", e);
        } catch (Exception e) {
            throw new RuntimeException("异常！", e);
        }
    }
    public static boolean execSql(String sql) throws SQLException {
        System.out.println(sql);
        Connection conn = getDruidDataSource("dataSource5").getConnection();
        Statement st = conn.createStatement();
        boolean rec = st.execute(sql);

        DataSource.close(conn);
        return rec;
    }
    public static void querySql(String sql) throws SQLException {
        System.out.println(sql);
        Connection conn = getDruidDataSource("dataSource5").getConnection();
        Statement st = conn.createStatement();
        ResultSet rec = st.executeQuery(sql);
        ResultSetMetaData resultSetMetaData = rec.getMetaData();
        ArrayList<String> list = new ArrayList<String>();
        int len = resultSetMetaData.getColumnCount();
        String header="";
        for (int i = 1; i <= len; i++) {
            String colname = resultSetMetaData.getColumnName(i);
            //  System.out.println(resultSetMetaData.getColumnDisplaySize(i));
            list.add(colname);
            // System.out.print(colname);
            //  System.out.print("");
            header+=colname+"\t\t";
        }
        System.out.println(header);
        for(int i=0;i<(header.length()*1.36);i++){
            System.out.print("-");
        }
        System.out.println();
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
    /**
     * 关闭：
     */
    public static void close(ResultSet rs, PreparedStatement ps, Connection conn) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            logger.error("ResultSet关闭失败！", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                logger.error("PreparedStatement关闭失败！", e);
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException e) {
                    logger.error("Connection关闭失败！", e);
                }
            }
        }
    }

    /**
     * 关闭：
     */
    public static void close(Object conn) {
        if (conn != null && conn instanceof Connection) {
            try {
                ((Connection) conn).close();
            } catch (SQLException e) {
                logger.error("Connection关闭失败！", e);
            }
        } else if (conn != null && conn instanceof PreparedStatement) {
            try {
                ((PreparedStatement) conn).close();
            } catch (SQLException e) {
                logger.error("Connection关闭失败！", e);
            }
        } else if (conn != null && conn instanceof ResultSet) {
            try {
                ((ResultSet) conn).close();
            } catch (SQLException e) {
                logger.error("ResultSet关闭失败！", e);
            }
        }

    }
}
