package com.qftx.tsm.follow;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.tsm.follow.bean.CustSaleChanceBean;
import com.qftx.tsm.follow.dto.CustSaleChanceDto;
import com.qftx.tsm.follow.scontrol.CustSaleChanceControl;
import com.qftx.tsm.follow.service.CustSaleChanceService;

public class CustSaleChanceTest  extends BaseUtest{
	@Autowired
	private CustSaleChanceControl custSaleChanceControl;
	@Autowired
	private CustSaleChanceService custSaleChanceService;
	@Autowired
	private CachedService cachedService;
	
	@Test
	public void addSaleChance() {
		CustSaleChanceBean bean = new CustSaleChanceBean();
		bean.setOrgId("fhtx");
		bean.setSaleChanceName("销售机会测试3");
		bean.setCustId("1512438bb7ec41de9b44452a952d06ff");
		bean.setTheorySignMoney(new BigDecimal(100));
		bean.setTheorySignDate(new Date());
		bean.setTheorySuccessRate(2);
		bean.setSignPlan("没有对策1");
		bean.setRival("张强1");
		bean.setRemark("没有备注1");
		custSaleChanceService.addSaleChance(bean);
	}
	@Test
	public void toEditSaleChance() {
		CustSaleChanceBean custSaleChanceBean = new CustSaleChanceBean();
		custSaleChanceBean.setOrgId("fhtx");
		custSaleChanceBean.setSaleChanceId("ebc89bf1809546d2a09922cb0c3b87d9");
		CustSaleChanceBean bean = custSaleChanceService.getByCondition(custSaleChanceBean);
		List<String> recustIds = new ArrayList<>();
		recustIds.add(bean.getCustId());
		CustSaleChanceDto dto = new CustSaleChanceDto();
		dto.setOrgId("fhtx");
		dto.setResCustIds(recustIds);
		List<CustSaleChanceDto> tsmCustList = custSaleChanceService.getResCustsByCustIds(dto);
		if (tsmCustList != null && tsmCustList.size() > 0) {
			bean.setCustName(tsmCustList.get(0).getCustName());
			bean.setCompany(tsmCustList.get(0).getCompany());
		}
		System.out.println(bean.toString());
	}
	@Test
	public void editSaleChance() {
		CustSaleChanceBean bean = new CustSaleChanceBean();
		bean.setSaleChanceId("cbe3091bc5b1430391d6361098e02f3c");
		bean.setOrgId("fhtx");
		bean.setSaleChanceName("销售机会测试2");
		bean.setCustId("1512438bb7ec41de9b44452a952d06ff");
		bean.setTheorySignMoney(new BigDecimal(10000));
		bean.setTheorySignDate(new Date());
		bean.setTheorySuccessRate(2);
		bean.setSignPlan("有对策");
		bean.setRival("张jin");
		bean.setRemark("有备注");
		custSaleChanceService.editSaleChance(bean);
	}
	@Test
	public void delSaleChance() {
		CustSaleChanceDto dto= new CustSaleChanceDto();
		dto.setOrgId("fhtx");
		List<String> list = new ArrayList<>();
		list.add("cbe3091bc5b1430391d6361098e02f3c");
		dto.setSaleChanceIds(list);
		custSaleChanceService.delSaleChance(dto);
	}
	@Test
	public void querySaleChanceListJson() {
		Map<String,Object>map = new HashMap<String, Object>();
		String key = null;
		try{
			CustSaleChanceDto custSaleChanceDto = new CustSaleChanceDto();
			custSaleChanceDto.setQueryText("销售");
			custSaleChanceDto.setQueryType("1");			
			String tDateType = "1";
			String orgId="fhtx";
			 //模糊查询处理
            if(StringUtils.isNotBlank(custSaleChanceDto.getQueryText())){
            	String queryText = custSaleChanceDto.getQueryText().trim();
            	custSaleChanceDto.setQueryText(queryText);
            }
            //  预计签单时间
			if(StringUtils.isNotBlank(tDateType) && !"0".equals(tDateType) && !"5".equals(tDateType)){
				custSaleChanceDto.setTheoryStartSignDate(getStartDateStr(Integer.parseInt(tDateType)));
				custSaleChanceDto.setTheoryEndSignDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			}
            custSaleChanceDto.setRoleType(1); // 角色类别：0--销售，1--管理者
            // 管理者查询
 			if(custSaleChanceDto.getRoleType() != null && custSaleChanceDto.getRoleType()==1){
 				if(StringUtils.isNotEmpty(custSaleChanceDto.getAccs())){
 					String[] ownerAccs = custSaleChanceDto.getAccs().split(",");
 					List<String> owaList = Arrays.asList(ownerAccs);
 					custSaleChanceDto.setOwnerAccs(owaList);
 				}else{
 					// 所有者查询方式 1-全部 2-只看自己 3-选中查询
 					if (StringUtils.isNotEmpty(custSaleChanceDto.getAccs())) {
 						custSaleChanceDto.setOsType("3");
 					}else if (StringUtils.isEmpty(custSaleChanceDto.getAccs()) && StringUtils.isBlank(custSaleChanceDto.getOsType())){
 						custSaleChanceDto.setOsType("1");
 					}
 					//custSaleChanceDto.setOsType(StringUtils.isBlank(custSaleChanceDto.getOsType()) ? "1" : custSaleChanceDto.getOsType());
 					if("1".equals(custSaleChanceDto.getOsType())){
 						List<String>list = cachedService.getMemberAccs("fhtx","fhtx001");
 						if(list!=null && list.size()>0){
 							StringBuffer sb = new StringBuffer();
 							for(String str: list){
 								sb.append(str);
 								sb.append(",");
 							}
 							if (!list.contains("fhtx001")) {
 								list.add("fhtx001");
 							}
 							if(sb.length()>0){
 								sb = sb.deleteCharAt(sb.length() - 1);
 							}
 							custSaleChanceDto.setAccs(sb.toString());
 							custSaleChanceDto.setOwnerAccs(list);
 							}else {
 							list.add("fhtx001");
 							custSaleChanceDto.setOwnerAccs(list);
 						}					
 					}	
 					 
 				}
 				
 			}		
 			custSaleChanceDto.setIsState(1);
		    custSaleChanceDto.setOrgId(orgId);
		    custSaleChanceDto.setOwnerAcc("fhtx001");
		    custSaleChanceDto.setOrderKey("THEORY_SIGN_DATE desc ");
		   // List<CustSaleChanceDto> tsmcustSaleChanceDtoList = new ArrayList<>();
			List<CustSaleChanceDto> tsmcustSaleChanceDtoList = custSaleChanceService.queryCustSaleChanceListPage(custSaleChanceDto);
		    Map<String,String> nameMap = cachedService.getOrgUserNames(orgId);
		    if (tsmcustSaleChanceDtoList != null && tsmcustSaleChanceDtoList.size() > 0) {
				List<String> recustIds = new ArrayList<>();
				for(CustSaleChanceDto csd : tsmcustSaleChanceDtoList) {
					csd.setShowTheorySignDate(csd.getTheorySignDate() !=null?DateUtil.formatDate(csd.getTheorySignDate(), DateUtil.Data_ALL): "");
					recustIds.add(csd.getCustId());
				}
				if(recustIds != null && recustIds.size() >0){
					Map<String,CustSaleChanceDto>csdMap = new HashMap<String, CustSaleChanceDto>();
					custSaleChanceDto.setResCustIds(recustIds);
					List<CustSaleChanceDto> tsmCustList = custSaleChanceService.getResCustsByCustIds(custSaleChanceDto);
					for(CustSaleChanceDto fcd : tsmCustList){
						csdMap.put(fcd.getResCustId(), fcd);
					}
					for(CustSaleChanceDto csd : tsmcustSaleChanceDtoList) {
						if(csdMap.get(csd.getCustId()) != null){
							CustSaleChanceDto cfd1 = csdMap.get(csd.getCustId());
							csd.setOwnerName(nameMap.get(cfd1.getOwnerName()) == null ? cfd1.getOwnerName() : nameMap.get(cfd1.getOwnerName()));
							csd.setCompany(cfd1.getCompany());
							csd.setCustName(cfd1.getCustName());
						}
						
					}
				}
			}
		    System.out.println(tsmcustSaleChanceDtoList.toString());
		}catch(Exception e) {
			map.put("status", "error");	
		}
		
	}
		    
		    
		    
		/**
		 * 获取第一天
		 * 
		 * @param type
		 *            1-当天 2-本周 3-本月 4-半年
		 * @return
		 * @create 2015年12月14日 下午3:48:05 lixing
		 * @history
		 */
		public String getStartDateStr(Integer type) {
			String str = "";
			if (type == 1) {
				str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
			} else if (type == 2) {
				str = DateUtil.getWeekFirstDay(new Date());
			} else if (type == 3) {
				str = DateUtil.getMonthFirstDay(new Date());
			} else if (type == 4) {
				str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), -180), DateUtil.DATE_DAY);
			}
			return str;
		}
}
