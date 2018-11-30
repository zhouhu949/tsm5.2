package com.qftx.tsm.main.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.main.bean.MainInfoBean;
import com.qftx.tsm.report.dto.TeamTodayContractDto;
import com.qftx.tsm.report.dto.UserContactDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MainInfoMapper extends BaseDao<MainInfoBean> {
	public List<MainInfoBean> findReportData(@Param("orgId")String orgId,@Param("accs")List<String> accs);
	
	
	/** 
	 * 团队今日数据 联系结果报表
	 * @param userId 登录人帐号
	 * @param orgId 单位ID
	 * @param type 类型 0-资源 1-意向客户
	 * @param groupId 部门ID
	 * @return 
	 * @create  2016年1月21日 上午10:09:03 Administrator
	 * @history  
	 */
	public List<TeamTodayContractDto> findTeamTodayContractReport(@Param("userId")String userId,@Param("orgId")String orgId,@Param("type")String type,@Param("groupIds")List<String> groupIds);
	
	public List<TeamTodayContractDto> findTeamTodayContractReportListPage(TeamTodayContractDto contractDto);

	public List<UserContactDTO> findUserWillSignCust(UserContactDTO entity);
	
	public List<UserContactDTO> findUserContactDTO(UserContactDTO entity);
	
	public List<UserContactDTO> findTeamContactDTO(UserContactDTO entity);
	
	
	
}
