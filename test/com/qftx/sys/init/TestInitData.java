package com.qftx.sys.init;

import com.alibaba.druid.pool.DruidDataSource;
import com.qftx.common.drds.DataSource;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.Points;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * User�� bxl
 * Date�� 2015/12/23
 * Time�� 9:16
 */
public class TestInitData {
    public static void main(String[] args) throws SQLException {
    	//getOptionData();
    	//getDistionaryData();
    	//getCustFieldData();
    	
    	//setDataDictonary();
    	//setPoints();
    	addField();
    }
    
    // 获取 个人字段 初始数据
    private static void getCustFieldData()throws SQLException{
    	String sql = "select t.* from tsm_cust_field_set t where t.org_id is null";
    	DruidDataSource druidDataSource = DataSource.getDruidDataSource("dataSource2");
        Connection conn = druidDataSource.getConnection();
        System.out.println(conn);
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        List<CustFieldSet> cfss = new ArrayList<CustFieldSet>();
        while(rs.next()){
        	CustFieldSet cfs = new CustFieldSet();
        	cfs.setFieldId(StringUtils.isNotBlank(rs.getString(1))?rs.getString(1):null);
        	cfs.setEnable(rs.getShort(2));
        	cfs.setFieldName(StringUtils.isNotBlank(rs.getString(3))?rs.getString(3):null);
        	cfs.setIsQuery(rs.getShort(4));
        	cfs.setSort(rs.getShort(5));
        	cfs.setFieldCode(StringUtils.isNotBlank(rs.getString(11))?rs.getString(11):null);
        	cfs.setFieldLength(StringUtils.isNotBlank(rs.getString(12))?rs.getString(12):null);
        	cfs.setIsRequired(rs.getShort(13));
        	cfss.add(cfs);
        }
        DataSource.close(rs, pst, conn);
        addCustFieldData(cfss,0);
    }
    
    private static void addCustFieldData(List<CustFieldSet> list ,Integer state) throws SQLException{
    	String sql = "insert into tsm_cust_field_set (FIELD_ID, ENABLE, FIELD_NAME, IS_QUERY, " +
    			"SORT, INPUTER_ACC, INPUTDATE," +
    			"ORG_ID,FIELD_CODE,FIELD_LENGTH,FIELD_DATA,IS_REQUIRED,state,is_read)" +
    			" values (?,?,?,?,?,?,now(),?,?,?,?,?,?,0)";
    	DruidDataSource ds_15 = DataSource.getDruidDataSource("dataSource8");
        System.out.println(ds_15.getConnection());
        Connection conn_15 = ds_15.getConnection();
        PreparedStatement pst_15 = conn_15.prepareStatement(sql);
        conn_15.setAutoCommit(false);
        for(CustFieldSet cfs : list){
        	pst_15.setString(1, cfs.getFieldId());
        	pst_15.setShort(2, cfs.getEnable());
        	pst_15.setString(3, cfs.getFieldName());
        	pst_15.setShort(4, cfs.getIsQuery());
        	pst_15.setShort(5, cfs.getSort());
        	pst_15.setString(6, cfs.getInputerAcc());
        	pst_15.setString(7, cfs.getOrgId());
        	pst_15.setString(8, cfs.getFieldCode());
        	pst_15.setString(9, cfs.getFieldLength());
        	pst_15.setString(10, cfs.getFieldData());
        	pst_15.setShort(11, cfs.getIsRequired());
        	pst_15.setInt(12, state);
        	pst_15.addBatch();
        }
        pst_15.executeBatch();
        conn_15.commit();//2,进行手动提交（commit）   
        System.out.println("提交成功!");   
        conn_15.setAutoCommit(true);//3,提交完成后回复现场将Auto commit,还原为true,   
        DataSource.close(null, pst_15, conn_15);
    }
    
    
    // 获取字典初始数据
    private static void getDistionaryData()throws SQLException{
    	String sql = "select t.* from tsm_dt_datadictionary t where t.org_id is null";
    	DruidDataSource druidDataSource = DataSource.getDruidDataSource("dataSource2");
        Connection conn = druidDataSource.getConnection();
        System.out.println(conn);
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        List<DataDictionaryBean> ddbs = new ArrayList<DataDictionaryBean>();
        while(rs.next()){
        	DataDictionaryBean ddb = new DataDictionaryBean();
        	ddb.setDictionaryId(StringUtils.isNotBlank(rs.getString(1))?rs.getString(1):null);
        	ddb.setDictionaryName(StringUtils.isNotBlank(rs.getString(2))?rs.getString(2):null);
        	ddb.setDictionaryValue(StringUtils.isNotBlank(rs.getString(3))?rs.getString(3):null);
        	ddb.setDictionaryValueNotes(StringUtils.isNotBlank(rs.getString(4))?rs.getString(4):null);
        	ddb.setInputerAcc("admin");
        	ddb.setIsDel(rs.getShort(10));
        	ddb.setDictionaryCode(StringUtils.isNotBlank(rs.getString(11))?rs.getString(11):null);
        	ddb.setIsOpen(StringUtils.isNotBlank(rs.getString(12))?rs.getString(12):null);
        	ddbs.add(ddb);
        }
        DataSource.close(rs, pst, conn);
        addDistionaryData(ddbs);
    }
    
    private static void addDistionaryData(List<DataDictionaryBean> list) throws SQLException{
    	String sql = "insert into tsm_dt_datadictionary ( DICTIONARY_ID, DICTIONARY_NAME, " +
    			"DICTIONARY_VALUE, DICTIONARY_VALUE_NOTES, INPUTER_ACC, INPUTDATE, " +
    			"ORG_ID, IS_DEL, DICTIONARY_CODE,IS_OPEN)" +
    			" values (?,?,?,?,?,now(),?,?,?,?)";
    	DruidDataSource ds_15 = DataSource.getDruidDataSource("dataSource8");
        System.out.println(ds_15.getConnection());
        Connection conn_15 = ds_15.getConnection();
        PreparedStatement pst_15 = conn_15.prepareStatement(sql);
        conn_15.setAutoCommit(false);
        for(DataDictionaryBean ddb : list){
        	pst_15.setString(1, ddb.getDictionaryId());
        	pst_15.setString(2, ddb.getDictionaryName());
        	pst_15.setString(3, ddb.getDictionaryValue());
        	pst_15.setString(4, ddb.getDictionaryValueNotes());
        	pst_15.setString(5, ddb.getInputerAcc());
        	pst_15.setString(6, ddb.getOrgId());
        	pst_15.setShort(7, ddb.getIsDel());
        	pst_15.setString(8, ddb.getDictionaryCode());
        	pst_15.setString(9, ddb.getIsOpen());
        	pst_15.addBatch();
        }
        pst_15.executeBatch();
        conn_15.commit();//2,进行手动提交（commit）   
        System.out.println("提交成功!");   
        conn_15.setAutoCommit(true);//3,提交完成后回复现场将Auto commit,还原为true,   
        DataSource.close(null, pst_15, conn_15);
    }
    
    // 获取数据选项表
    private static void getOptionData() throws SQLException{
    	 String sql = "select t.* from tsm_data_optionlist t where t.org_id is null";
    	DruidDataSource druidDataSource = DataSource.getDruidDataSource("dataSource2");
        Connection conn = druidDataSource.getConnection();
        System.out.println(conn);
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        List<OptionBean>options = new ArrayList<OptionBean>();
        while(rs.next()){
        	OptionBean ob = new OptionBean();
        	System.out.println(rs.getString(1));
        	ob.setOptionlistId(StringUtils.isNotBlank(rs.getString(1))?rs.getString(1):null);
        	System.out.println(rs.getString(2));
        	ob.setItemCode(StringUtils.isNotBlank(rs.getString(2))?rs.getString(2):null);
        	System.out.println(rs.getString(3));
        	ob.setOptionName(StringUtils.isNotBlank(rs.getString(3))?rs.getString(3):null);
        	System.out.println(rs.getInt(4));
        	ob.setSort(rs.getInt(4));
        	System.out.println(rs.getString(5));
        	ob.setInputerAcc("admin");
        	ob.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));        	
        	System.out.println(rs.getString(10));
        	ob.setUnits(rs.getString(10));
        	System.out.println(StringUtils.isNotBlank(rs.getString(11))?rs.getString(11):null);
        	ob.setType(StringUtils.isNotBlank(rs.getString(11))?rs.getString(11):null);
        	System.out.println(rs.getString(12));
        	ob.setOptionValue(StringUtils.isNotBlank(rs.getString(12))?rs.getString(12):null);
        	ob.setIsDefault(rs.getShort(13));
        	ob.setPid(StringUtils.isNotBlank(rs.getString(14))?rs.getString(14):null);
        	options.add(ob);
        }
        DataSource.close(rs, pst, conn);
        addOptionData(options);
    }
    
    private static void addOptionData(List<OptionBean> list) throws SQLException{
    	String sql = "insert into tsm_data_optionlist (OPTIONLIST_ID, ITEM_CODE, " +
    			"OPTION_NAME, SORT, INPUTER_ACC, INPUTDATE," +
    			" ORG_ID, UNITS, TYPE, OPTION_VALUE, IS_DEFAULT, PID) values (?,?,?,?,?,now(),?,?,?,?,?,?)";
    	DruidDataSource ds_15 = DataSource.getDruidDataSource("dataSource8");
        System.out.println(ds_15.getConnection());
        Connection conn_15 = ds_15.getConnection();
        PreparedStatement pst_15 = conn_15.prepareStatement(sql);
        conn_15.setAutoCommit(false);
        for(OptionBean ob : list){
        	pst_15.setString(1, ob.getOptionlistId());
        	pst_15.setString(2,ob.getItemCode());
        	pst_15.setString(3,ob.getOptionName());
        	pst_15.setInt(4,ob.getSort());
        	pst_15.setString(5, ob.getInputerAcc());
        	pst_15.setString(6, ob.getOrgId());
        	pst_15.setString(7,ob.getUnits());
        	pst_15.setString(8, ob.getType());
        	pst_15.setString(9,ob.getOptionValue());
        	pst_15.setInt(10, ob.getIsDefault());
        	pst_15.setString(11, ob.getPid());
        	pst_15.addBatch();
        }
        pst_15.executeBatch();
        conn_15.commit();//2,进行手动提交（commit）   
        System.out.println("提交成功!");   
        conn_15.setAutoCommit(true);//3,提交完成后回复现场将Auto commit,还原为true,   
        DataSource.close(null, pst_15, conn_15);
    }
    
    private static void setDataDictonary() throws SQLException{
    	List<DataDictionaryBean>list = new ArrayList<DataDictionaryBean>();
//    	DataDictionaryBean ddb = new DataDictionaryBean();
//    	ddb.setDictionaryId("FOUR40015");
//    	ddb.setDictionaryName("月计划提前时间");
//    	ddb.setDictionaryValue("3");
//    	ddb.setDictionaryValueNotes("天");
//    	ddb.setInputerAcc("admin");
//    	ddb.setIsDel((short)0);
//    	ddb.setDictionaryCode("DATA_40015");
//    	list.add(ddb);
//    	
//    	DataDictionaryBean ddb1 = new DataDictionaryBean();
//    	ddb1.setDictionaryId("FOUR40016");
//    	ddb1.setDictionaryName("客户跟进设置，当个人资源少于xx人");
//    	ddb1.setDictionaryValueNotes("人");
//    	ddb1.setInputerAcc("admin");
//    	ddb1.setIsDel((short)0);
//    	ddb1.setDictionaryCode("DATA_40016");
//    	list.add(ddb1);
//    	
//    	DataDictionaryBean ddb2 = new DataDictionaryBean();
//    	ddb2.setDictionaryId("FOUR40017");
//    	ddb2.setDictionaryName("客户跟进设置，系统自动分配资源，每次分配xx人");
//    	ddb2.setDictionaryValueNotes("人");
//    	ddb2.setInputerAcc("admin");
//    	ddb2.setIsDel((short)0);
//    	ddb2.setDictionaryCode("DATA_40017");
//    	list.add(ddb2);
//    	
//    	DataDictionaryBean ddb3 = new DataDictionaryBean();
//    	ddb3.setDictionaryId("FOUR40018");
//    	ddb3.setDictionaryName("待分配资源共享到公海池设置");
//    	ddb3.setDictionaryValue("0");
//    	ddb3.setDictionaryValueNotes("0关闭1开启");
//    	ddb3.setInputerAcc("admin");
//    	ddb3.setIsDel((short)0);
//    	ddb3.setDictionaryCode("DATA_40018");
//    	list.add(ddb3);
//    	
//    	DataDictionaryBean ddb4 = new DataDictionaryBean();
//    	ddb4.setDictionaryId("FOUR40019");
//    	ddb4.setDictionaryName("数据默认显示设置");
//    	ddb4.setDictionaryValue("10");
//    	ddb4.setDictionaryValueNotes("条");
//    	ddb4.setInputerAcc("admin");
//    	ddb4.setIsDel((short)0);
//    	ddb4.setDictionaryCode("DATA_40019");
//    	list.add(ddb4);
//    	
//    	DataDictionaryBean ddb5 = new DataDictionaryBean();
//    	ddb5.setDictionaryId("FOUR40020");
//    	ddb5.setDictionaryName("每日计划审核设置");
//    	ddb5.setDictionaryValue("1");
//    	ddb5.setDictionaryValueNotes("0关闭1开启");
//    	ddb5.setInputerAcc("admin");
//    	ddb5.setIsDel((short)0);
//    	ddb5.setDictionaryCode("DATA_40020");
//    	list.add(ddb5);    	
    	
    	DataDictionaryBean ddb6 = new DataDictionaryBean();
    	ddb6.setDictionaryId("FOUR40021");
    	ddb6.setDictionaryName("销售每签单xx 元获得1积分奖励");
    	ddb6.setDictionaryValue("10");
    	ddb6.setDictionaryValueNotes("元");
    	ddb6.setInputerAcc("admin");
    	ddb6.setIsDel((short)0);
    	ddb6.setDictionaryCode("DATA_40021");
    	list.add(ddb6);    	
    	
    	DataDictionaryBean ddb7 = new DataDictionaryBean();
    	ddb7.setDictionaryId("FOUR40022");
    	ddb7.setDictionaryName("每月单项任务完成获得 xx 积分奖励");
    	ddb7.setDictionaryValue("100");
    	ddb7.setDictionaryValueNotes("积分");
    	ddb7.setInputerAcc("admin");
    	ddb7.setIsDel((short)0);
    	ddb7.setDictionaryCode("DATA_40022");
    	list.add(ddb7);    	
    	
    	DataDictionaryBean ddb8 = new DataDictionaryBean();
    	ddb8.setDictionaryId("FOUR40023");
    	ddb8.setDictionaryName("完成月度计划，获得xx积分奖励");
    	ddb8.setDictionaryValue("500");
    	ddb8.setDictionaryValueNotes("积分");
    	ddb8.setInputerAcc("admin");
    	ddb8.setIsDel((short)0);
    	ddb8.setDictionaryCode("DATA_40023");
    	list.add(ddb8);    	
    	
    	DataDictionaryBean ddb9 = new DataDictionaryBean();
    	ddb9.setDictionaryId("FOUR40024");
    	ddb9.setDictionaryName("连续3个月完成月度计划，获得xx积分奖励	");
    	ddb9.setDictionaryValue("2000");
    	ddb9.setDictionaryValueNotes("积分");
    	ddb9.setInputerAcc("admin");
    	ddb9.setIsDel((short)0);
    	ddb9.setDictionaryCode("DATA_40024");
    	list.add(ddb9);    	
    	addDistionaryData(list);
    }
    
    private static void setPoints() throws SQLException{
    	List<Points>points = new ArrayList<Points>();		
		Points point = new Points();
		point.setPointsId("P40001");
		point.setLevel(1);
		point.setSort(1);
		point.setLevelName("新手上路");
		point.setStartNumber(0);
		point.setEndNumber(10000);
		point.setIsDel(0);
		point.setInputAcc("admin");
		point.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
		points.add(point);
		
		Points point1 = new Points();
		point1.setPointsId("P40002");
		point1.setLevel(2);
		point1.setSort(2);
		point1.setLevelName("销售能手");
		point1.setStartNumber(10001);
		point1.setEndNumber(100000);
		point1.setIsDel(0);
		point1.setInputAcc("admin");
		point1.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
		points.add(point1);
		
		Points point2 = new Points();
		point2.setPointsId("P40003");
		point2.setLevel(3);
		point2.setSort(3);
		point2.setLevelName("销售高手");
		point2.setStartNumber(100001);
		point2.setEndNumber(500000);
		point2.setIsDel(0);
		point2.setInputAcc("admin");
		point2.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
		points.add(point2);
		
		Points point3 = new Points();
		point3.setPointsId("P40004");
		point3.setLevel(4);
		point3.setSort(4);
		point3.setLevelName("销售圣手");
		point3.setStartNumber(500001);
		point3.setEndNumber(10000000);
		point3.setIsDel(0);
		point3.setInputAcc("admin");
		point3.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
		points.add(point3);
		
		Points point4= new Points();
		point4.setPointsId("P40005");
		point4.setLevel(5);
		point4.setSort(5);
		point4.setLevelName("销售神手");
		point4.setStartNumber(10000001);
		point4.setEndNumber(100000002);
		point4.setIsDel(0);
		point4.setInputAcc("admin");
		point4.setInputdate(DateUtil.getDateCurrentDate(DateUtil.hour24HMSPattern));
		points.add(point4);
		addPointsData(points);
    }
    
    private static void addPointsData(List<Points> list) throws SQLException{
    	String sql = "insert into sys_points ( points_id,level,level_name,sort," +
    			"start_number,end_number, INPUTER_ACC, inputtime, " +
    			"IS_DEL)" +
    			" values (?,?,?,?,?,?,'admin',now(),0)";
    	DruidDataSource ds_15 = DataSource.getDruidDataSource("dataSource8");
        System.out.println(ds_15.getConnection());
        Connection conn_15 = ds_15.getConnection();
        PreparedStatement pst_15 = conn_15.prepareStatement(sql);
        conn_15.setAutoCommit(false);
        for(Points point : list){
        	pst_15.setString(1, point.getPointsId());
        	pst_15.setInt(2, point.getLevel());
        	pst_15.setString(3, point.getLevelName());
        	pst_15.setInt(4, point.getSort());
        	pst_15.setInt(5, point.getStartNumber());
        	System.out.println(point.getEndNumber());
        	pst_15.setInt(6, point.getEndNumber());
        	pst_15.addBatch();
        }
        pst_15.executeBatch();
        conn_15.commit();//2,进行手动提交（commit）   
        System.out.println("提交成功!");   
        conn_15.setAutoCommit(true);//3,提交完成后回复现场将Auto commit,还原为true,   
        DataSource.close(null, pst_15, conn_15);
    }
    
    // 增加 系统字段
    public static void addField() throws SQLException{
    	//{id,是否启用,字段名称，是否查询条件，排序，录入人，字段CODE，字段长度，是否必填，资源类型(个人，企业，联系人)，是否只读，字段类型}
    	Object[][]persons = {
    			{"field020",1,"所在地区",1,19,"admin",AppConstant.area,"100",0,0,0,1}
    	};
    	
    	// 企业字段
    	Object[][] coms = {
    			{"comfield001",1,"客户名称",1,1,"admin",AppConstant.com_name,"100",0,1,0,0},
    			{"comfield002",1,"注册资本",1,2,"admin",AppConstant.com_capital,"100",0,1,0,0},
    			{"comfield003",1,"所属行业",1,3,"admin",AppConstant.com_trade,"100",0,1,0,0},
    			{"comfield004",1,"法人代表",1,4,"admin",AppConstant.com_user,"100",0,1,0,0},
    			{"comfield005",1,"公司传真",1,5,"admin",AppConstant.com_fax,"100",0,1,0,0},
    			{"comfield006",1,"关键字",1,6,"admin",AppConstant.com_keyWord,"100",0,1,0,0},
    			{"comfield007",1,"公司网站",1,7,"admin",AppConstant.com_unithome,"100",0,1,0,0},
    			{"comfield008",1,"所在地区",1,8,"admin",AppConstant.com_area,"100",0,1,0,0},
    			{"comfield009",1,"联系地址",1,9,"admin",AppConstant.com_address,"100",0,1,0,0},
    			{"comfield010",1,"经营范围",1,10,"admin",AppConstant.com_scope,"100",0,1,0,0},
    			{"comfield011",1,"重点关注",1,11,"admin",AppConstant.com_isMajor,"100",0,1,0,0},
    			{"comfield012",1,"备注",1,12,"admin",AppConstant.com_remark,"100",0,1,0,0},
    			{"comfield013",1,"自定义",1,13,"admin",AppConstant.com_defined1,"100",0,1,0,1}
    	};
    	
    	Object[][]contacts = {
    			{"contacts001",1,"姓名",1,1,"admin",AppConstant.contacts_name,"100",0,2,0,0},
    			{"contacts002",1,"常用电话",1,2,"admin",AppConstant.contacts_telphone,"100",0,2,0,0},
    			{"contacts003",1,"备用电话",1,3,"admin",AppConstant.contacts_telphonebak,"100",0,2,0,0},
    			{"contacts004",1,"性别",1,4,"admin",AppConstant.contacts_sex,"100",0,2,0,0},
    			{"contacts005",1,"生日",1,5,"admin",AppConstant.contacts_birthday,"100",0,2,0,0},
    			{"contacts006",1,"邮箱",1,6,"admin",AppConstant.contacts_email,"100",0,2,0,0},
    			{"contacts007",1,"个人传真",1,7,"admin",AppConstant.contacts_fax,"100",0,2,0,0},
    			{"contacts008",1,"QQ",1,8,"admin",AppConstant.contacts_qq,"100",0,2,0,0},
    			{"contacts009",1,"旺旺",1,9,"admin",AppConstant.contacts_ww,"100",0,2,0,0},
    			{"contacts010",1,"职务",1,10,"admin",AppConstant.contacts_work,"100",0,2,0,0},
    			{"contacts011",1,"所在部门",1,11,"admin",AppConstant.contacts_groupname,"100",0,2,0,0},
    			{"contacts012",1,"自定义",1,12,"admin",AppConstant.contacts_defined1,"100",0,2,0,1}
    	};
    	
    	String sql = "insert into tsm_cust_field_set ( FIELD_ID, ENABLE, FIELD_NAME, IS_QUERY, SORT, INPUTER_ACC," +
    			" INPUTDATE, FIELD_CODE,FIELD_LENGTH,IS_REQUIRED,state,is_read," +
    			"DATA_TYPE) values (?,?,?,?,?,?,now(),?,?,?,?,?,?)";
    	DruidDataSource ds_15 = DataSource.getDruidDataSource("dataSource8");
        Connection conn_15 = ds_15.getConnection();
        PreparedStatement pst_15 = conn_15.prepareStatement(sql);
        conn_15.setAutoCommit(false);
        for(int i = 0;i<persons.length;i++){
        	pst_15.setString(1, persons[i][0].toString());
        	pst_15.setString(2, persons[i][1].toString());
        	pst_15.setString(3, persons[i][2].toString());
        	pst_15.setString(4, persons[i][3].toString());
        	pst_15.setString(5, persons[i][4].toString());
        	pst_15.setString(6, persons[i][5].toString());
        	pst_15.setString(7, persons[i][6].toString());
        	pst_15.setString(8, persons[i][7].toString());
        	pst_15.setString(9, persons[i][8].toString());
        	pst_15.setString(10, persons[i][9].toString());
        	pst_15.setString(11, persons[i][10].toString());
        	pst_15.setString(12, persons[i][11].toString());
        	pst_15.addBatch();
        }
      
        pst_15.executeBatch();
        conn_15.commit();//2,进行手动提交（commit）   
        System.out.println("提交成功!");   
        conn_15.setAutoCommit(true);//3,提交完成后回复现场将Auto commit,还原为true,   
        DataSource.close(null, pst_15, conn_15);
    	
    }
   
    
    
}
