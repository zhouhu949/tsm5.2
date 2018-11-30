package com.qftx.common.drds.query;

import java.sql.SQLException;

import com.qftx.common.drds.DataSource;

public class TestDrdsStaticQuery {
	  public static void main(String[] args) throws SQLException {
	        String sql = "select * from call_record_info where org_id=\"1\" limit 0,10";
	         sql = "" +
	        		 "SELECT\n" +
	        		 "\t	d.rci_id\n" +
	        		 "\tFROM\n" +
	        		 "\t	tsm_res_cust_info_detail d,\n" +
	        		 "\t	tsm_res_cust_info t\n" +
	        		 "\t	WHERE\n" +
	        		 "\t	t.ORG_ID = 'hyx41'\n" +
	        		 "\t	AND T.STATUS  IN (2,3,6,7,8)\n" +
	        		 "\t	AND T.IS_DEL=0\n" +
	        		 "\tAND t.OWNER_ACC = 'hyx41001'\n" +
	        		 "\tAND t.RES_CUST_ID = d.rci_id\n" +
	        		 "\t	AND d.org_id = 'hyx41001'\n" +
	        		 "\tAND (\n" +
	        		 "\t	d.telphone = '13735493215'\n" +
	        		 "\t	OR d.telphonebak ='13735493215'\n" +
	        		 "\t	)\n" +
	        		 "\t limit 0,1	";
	        long start = System.currentTimeMillis();
	        DataSource.querySql(sql);
	        System.out.println("time=" + (System.currentTimeMillis() - start));
	    }

}
