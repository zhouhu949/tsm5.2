package com.qftx.tsm.plan.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.dao.UserMapper;
import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedService;
import com.qftx.tsm.plan.data.dao.DataProcessMapper;


public class DataProcess extends BaseUtest{
	@Autowired
	DataProcessMapper dataProcessMapper;
	@Autowired
	UserMapper userMapper;
	@Autowired
	CachedService cachedService;
	
	private boolean select =false;
	
	private Map<String, String> acc2IdMap = new HashMap<String, String>();
	Logger logger = Logger.getLogger(DataProcess.class.getName());
	
	
	@Test
	public void countTable(){
		String[] tables={"plan_groupmonth","plan_groupmonth_analy","plan_groupmonth_commont","plan_log_year","plan_sale_year","plan_sale_year_log","plan_userday","plan_userday_resource","plan_userday_signcust","plan_userday_willcust","plan_usermonth","plan_usermonth_commont","plan_usermonth_cust","tsm_report_plan","tsm_report_call_info","work_log_bbs","work_log_default","work_log_info","work_log_share"};
		
		List<String> list = new ArrayList<String>();
		for (String table : tables) {
			int i = dataProcessMapper.countTableNum("zszsrh", table);
			if(i>0) list.add(table);
		}
		
		for (String string : list) {
			System.out.println(string);
			//System.out.println("mysqldump -t -hdrdsemwz04c34a66.drds.aliyuncs.com -P3306 -utsm_db -ptsm_db123456 tsm_db "+string+" --where=\"org_id='zszsrh'\" > /data/zszsrh/"+string+".sql");
		}
	}
	
	@Test
	public void start() throws Exception{
		getAcc2IdMap();
		String[] orgIds = {"zsrhyz","zsrhwt"};
		for (String orgId : orgIds) {
			System.out.println("\n\n\n/*"+orgId+"*/");
			runWorkLog(orgId);
			runReport(orgId);
			runPlanUserDay(orgId,getSudIds(orgId));
			//runGroup(orgId, getGroupIds(orgId));
		}
	}
	
	public void runReport(String orgId) throws Exception{
		String[] tables = {"tsm_report_plan","tsm_report_call_info"};
		for (String table : tables) {
			System.out.println("\n-------"+table+"------");
			getSqlScriptByUserAccs(orgId, table, getUserAccs(orgId));
		}
	}
	
	public void runWorkLog(String orgId) throws Exception{
		String[] tables = {"work_log_info","work_log_bbs","work_log_default","work_log_share","plan_userday","plan_usermonth"};
		for (String table : tables) {
			System.out.println("\n-------"+table+"------");
			getSqlScriptByUserIds(orgId, table, getUserIds(orgId));
		}
	}
	
	public void runPlanUserDay(String orgId,List<String> sudIds) throws Exception{
		String[] tables = {"plan_userday_resource","plan_userday_willcust"};
		for (String table : tables) {
			System.out.println("\n-------"+table+"------");
			getSqlScriptBySudIds(orgId, table, sudIds);
		}
	}
	
	public void runGroup(String orgId,List<String> groupIds) throws Exception{
		String[] tables = {"plan_groupmonth","plan_groupmonth_analy","plan_sale_year"};
		for (String table : tables) {
			System.out.println("\n-------"+table+"------");
			getSqlScriptByGroupIds(orgId, table, groupIds);
		}
	}
	
	/*public void runPlanUserMonth(String orgId,List<String> sudIds) throws Exception{
		String[] tables = {"plan_usermonth_cust"};
		for (String table : tables) {
			System.out.println("\n-------"+table+"------");
			getSqlScriptByUserPlanMonthIds(orgId, table, sudIds);
		}
	}*/
	
	public void runPlanUserSaleProcessId(String orgId,List<String> sudIds) throws Exception{
		String[] tables = {"plan_userday_resource","plan_userday_willcust"};
		for (String table : tables) {
			System.out.println("\n-------"+table+"------");
			getSqlScriptBySudIds(orgId, table, sudIds);
		}
	}
	
	public void getAcc2IdMap(){
		User entity = new User();
		entity.setOrgId("zszsrh");
		List<User> users = userMapper.findByCondtion(entity );
		for (User user : users) {
			acc2IdMap.put(user.getUserAccount(), user.getUserId());
		}
	}
	
	public void getSqlScriptByUserIds(String newOrgId,String tableName,List<String> userIds){
		String whereSql = getInSql(userIds);
		if(select){
			System.out.println("select count(1) from "+tableName+" where org_id='zszsrh' and user_id in ("+whereSql+");");
		}else{
			System.out.println("update "+tableName+" set org_id='"+newOrgId+"' where org_id='zszsrh' and user_id in ("+whereSql+");");
		}
	}
	
	public void getSqlScriptByUserAccs(String newOrgId,String tableName,List<String> userAccs){
		String whereSql = getInSql(userAccs);
		if(select){
			System.out.println("select count(1) from "+tableName+" where org_id='zszsrh' and account in ("+whereSql+");");
		}else{
			System.out.println("update "+tableName+" set org_id='"+newOrgId+"' where org_id='zszsrh' and account in ("+whereSql+");");
		}
	}
	
	public void getSqlScriptBySudIds(String newOrgId,String tableName,List<String> sudIds){
		String whereSql = getInSql(sudIds);
		if(select){
			System.out.println("select count(1) from "+tableName+" where org_id='zszsrh' and sud_id in ("+whereSql+");");
		}else{
			System.out.println("update "+tableName+" set org_id='"+newOrgId+"' where org_id='zszsrh' and sud_id in ("+whereSql+");");
		}
	}
	
	public void getSqlScriptByGroupIds(String newOrgId,String tableName,List<String> groupIds){
		String whereSql = getInSql(groupIds);
		if(select){
			System.out.println("select count(1) from "+tableName+" where org_id='zszsrh' and group_id in ("+whereSql+");");
		}else{
			System.out.println("update "+tableName+" set org_id='"+newOrgId+"' where org_id='zszsrh' and group_id in ("+whereSql+");");
		}
	}
	
	/*public void getSqlScriptByUserPlanMonthIds(String newOrgId,String tableName,List<String> sudIds){
		String whereSql = getInSql(sudIds);
		if(select){
			System.out.println("select count(1) from "+tableName+" where org_id='zszsrh' and plan_id in ("+whereSql+");");
		}else{
			System.out.println("update "+tableName+" set org_id='"+newOrgId+"' where org_id='zszsrh' and plan_id in ("+whereSql+");");
		}
	}*/
	
	public String getInSql(List<String> ids){
		StringBuilder whereSql = new StringBuilder();
		for (String userId : ids) {
			if(whereSql.length()!=0) whereSql.append(",");
			whereSql.append("'"+userId+"'");
		}
		return whereSql.toString();
	}
	
	public List<String> getUserAccs(String orgId) throws Exception{
		//zszsrh    十方       1-120
		//zsrhwt    旺田       121-400
		//zsrhyz    银座       401-770
		List<String> userAccs = new ArrayList<String>();
		int begin =0;
		int end =0;
		if("zszsrh".equals(orgId)){
			begin =1;
			end=120;
		}else if("zsrhwt".equals(orgId)){
			begin =121;
			end=400;
		}else if("zsrhyz".equals(orgId)){
			begin = 401;
			end = 770;
		}else{
			throw new Exception("orgId 不识别");
		}
		for(;begin<=end;begin++){
			String account = "zszsrh";
			if(begin<10) {
				account+="00";
			}else if(begin<100){
				account+="0";
			}
			account+=begin;
			userAccs.add(account);
		}
		return userAccs;
	}
	
	public List<String> getUserIds(String orgId) throws Exception{
		List<String> userIds = new ArrayList<String>();
		
		List<String> userAccs = getUserAccs(orgId);
		for (String userAcc : userAccs) {
			if(acc2IdMap.containsKey(userAcc)){
				userIds.add(acc2IdMap.get(userAcc));
			}else{
				throw new Exception("userid 未找到\t"+userAcc);
			}
		}
		return userIds;
	}
	
	public List<String> getSudIds(String orgId)throws Exception{
		List<String> userIds = getUserIds(orgId);
		String whereSql = getInSql(userIds);
		List<String> sudIds = dataProcessMapper.getSudIds("zszsrh", whereSql);
		
		if(sudIds==null ||sudIds.size()==0) throw new Exception("未查询到日计划！");
		return sudIds;
	}
	
	public List<String> getPlanUserMonthIds(String orgId)throws Exception{
		List<String> userIds = getUserIds(orgId);
		String whereSql = getInSql(userIds);
		List<String> sudIds = dataProcessMapper.getPlanUserMonthIds("zszsrh", whereSql);
		if(sudIds==null ||sudIds.size()==0) throw new Exception("未查询到月计划！");
		return sudIds;
	}
	
	public List<String> getGroupIds(String orgId)throws Exception{
		List<String> groupIds = dataProcessMapper.getGroupIds(orgId);
		if(groupIds==null ||groupIds.size()==0) throw new Exception("未查询到部门！");
		return groupIds;
	}
}
