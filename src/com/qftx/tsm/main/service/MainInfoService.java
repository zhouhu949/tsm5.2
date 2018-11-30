package com.qftx.tsm.main.service;

import com.qftx.base.team.bean.TsmTeamGroupMemberBean;
import com.qftx.base.team.dao.TsmTeamGroupMemberMapper;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.main.bean.MainInfoBean;
import com.qftx.tsm.main.dao.MainInfoMapper;
import com.qftx.tsm.report.dto.TeamTodayContractDto;
import com.qftx.tsm.report.dto.UserContactDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class MainInfoService {
	@Autowired
	private MainInfoMapper mainInfoMapper;
	@Autowired TsmTeamGroupMemberMapper teamGroupMemberMapper;
	
	public List<MainInfoBean> getReportData(String orgId,List<String> accs){
		return mainInfoMapper.findReportData(orgId, accs);
	}
	
	public List<TsmTeamGroupMemberBean> getUserManage(String orgId,String adminAcc,String groupId,List<String> groupIds){
		return teamGroupMemberMapper.findManageUserAcc(orgId,adminAcc, groupId,groupIds);
	}
	
	
	/** 
	 * 修改首页今日关注信息
	 * @param mb  <font color="red"><b>orgId、type(0-资源;1-意向)、account</b></font>三个属性必须赋值
	 * @param addType 1-转签约 2-转意向 3-公海池 4-无进展
	 * @param addNum 增量
	 * @create  2016年1月12日 上午9:14:36 lixing
	 * @history  
	 */
	public void updateMainInfo(MainInfoBean mb,Integer addType,Integer addNum){
		Calendar cal = Calendar.getInstance();
		if(mb.getInputtime() == null){
			mb.setInputtime(cal.getTime());
		}
		MainInfoBean infoBean = mainInfoMapper.getByCondtion(mb);
		if(infoBean == null){//如果没有数据，插入
			mb.setId(SysBaseModelUtil.getModelId());
			mb.setTotalNum(addNum);
			mb.setIsSet(0);
			if(addType == 1){
				mb.setSignNum(addNum);
			}else if(addType == 2){
				mb.setCustNum(addNum);
			}else if(addType == 3){
				mb.setPoolNum(addNum);
			}else{
				mb.setNoNum(addNum);
			}
			mainInfoMapper.insert(mb);
		}else{
			Integer totalNum = infoBean.getTotalNum() == null  ? 0 : infoBean.getTotalNum();
			infoBean.setTotalNum(totalNum+addNum);
			if(addType == 1){
				Integer signNum = infoBean.getSignNum() == null ? 0 : infoBean.getSignNum();
				infoBean.setSignNum(signNum+addNum);
			}else if(addType == 2){
				Integer custNum = infoBean.getCustNum() == null ? 0 : infoBean.getCustNum();
				infoBean.setCustNum(custNum+addNum);
			}else if(addType == 3){
				Integer poolNum = infoBean.getPoolNum() == null ? 0 : infoBean.getPoolNum();
				infoBean.setPoolNum(poolNum+addNum);
			}else{
				Integer noNum = infoBean.getNoNum() == null ? 0 : infoBean.getNoNum();
				infoBean.setNoNum(noNum+addNum);
			}
			infoBean.setUpdateDate(cal.getTime());
			mainInfoMapper.update(infoBean);
		}
	}
	
	/** 
	 * 团队今日数据 联系结果报表
	 * @param userId 登录人帐号
	 * @param orgId 单位ID
	 * @param type 类型 0-资源 1-意向客户
	 * @param groupId 部门ID
	 * @return 
	 * @create  2016年1月21日 上午10:09:03 lixing
	 * @history  
	 */
	public List<TeamTodayContractDto> findTeamTodayContractReport(String userId,String orgId,String type,List<String> groupIds){
		return mainInfoMapper.findTeamTodayContractReport(userId, orgId, type,groupIds);
	}
	
	/** 
	 * 团队今日数据 联系结果报表分页
	 * @param userId 登录人帐号
	 * @param orgId 单位ID
	 * @param type 类型 0-资源 1-意向客户
	 * @param groupId 部门ID
	 * @return 
	 * @create  2016年1月21日 上午10:09:03 lixing
	 * @history  
	 */
	public List<TeamTodayContractDto> findTeamTodayContractReportListPage(TeamTodayContractDto contractDto){
		return mainInfoMapper.findTeamTodayContractReportListPage(contractDto);
	}
	
	
	/*个人统计分析销售结果统计*/
	public List<UserContactDTO> findUserContactDTOByUser(String orgId,String userId,String account,Date fromDate,Date toDate){
		UserContactDTO entity = new UserContactDTO();
		entity.setOrgId(orgId);
		entity.setUserId(userId);
		entity.setAccount(account);
		entity.setFromDate(fromDate);
		entity.setToDate(toDate);
		return mainInfoMapper.findUserContactDTO(entity);
	}
	
	/*个人统计分析销售结果统计*/
	public List<UserContactDTO> findUserContactDTOByGroup(String orgId,String[] groupIds,Date fromDate,Date toDate){
		UserContactDTO entity = new UserContactDTO();
		entity.setOrgId(orgId);
		entity.setFromDate(fromDate);
		entity.setToDate(toDate);
		entity.setGroupIds(groupIds);
		
		return mainInfoMapper.findUserContactDTO(entity);
	}
	
	/*个人统计分析销售结果统计*/
	public List<UserContactDTO> findTeamContactDTO(String orgId,String[] groupIds,Date fromDate,Date toDate){
		UserContactDTO entity = new UserContactDTO();
		entity.setOrgId(orgId);
		entity.setFromDate(fromDate);
		entity.setToDate(toDate);
		entity.setGroupIds(groupIds);
		
		return mainInfoMapper.findTeamContactDTO(entity);
	}
	
	public List<UserContactDTO> findUserWillSignCustByDay(String orgId,String account,Date fromDate,Date toDate,String sort){
		UserContactDTO entity = new UserContactDTO();
		entity.setOrgId(orgId);
		entity.setAccount(account);
		entity.setFromDate(fromDate);
		entity.setToDate(toDate);
		entity.setGroupKey("%Y-%m-%d");
		entity.setOrderKey("dateFmt "+sort);
		
		return mainInfoMapper.findUserWillSignCust(entity);
	}
	
	public List<UserContactDTO> findUserWillSignCustByMonth(String orgId,String account,Date fromDate,Date toDate,String sort){
		UserContactDTO entity = new UserContactDTO();
		entity.setOrgId(orgId);
		entity.setAccount(account);
		entity.setFromDate(fromDate);
		entity.setToDate(toDate);
		entity.setGroupKey("%Y-%m");
		entity.setOrderKey("dateFmt "+sort);
		return mainInfoMapper.findUserWillSignCust(entity);
	}
}
