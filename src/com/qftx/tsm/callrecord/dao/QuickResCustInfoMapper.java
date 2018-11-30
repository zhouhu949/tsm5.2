package com.qftx.tsm.callrecord.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author: Administrator
 * @since: 2015锟斤拷12锟斤拷21锟斤拷 锟斤拷锟斤拷11:05:30
 * @history:
 */
public interface QuickResCustInfoMapper extends BaseDao<ResCustInfoBean> {

	ResCustInfoBean getByPrimaryKey(@Param("orgId") String orgId, @Param("resId") String resId);
	ResCustInfoDto getByResId(@Param("orgId") String orgId, @Param("resId") String resId);

	/**
	 * 锟斤拷源锟斤拷页锟叫憋拷
	 * 
	 * @param custInfoDto
	 * @return
	 * @create 2015锟斤拷11锟斤拷13锟斤拷 锟斤拷锟斤拷5:27:17 lixing
	 * @history
	 */
	List<ResCustInfoDto>  findMyResCustListPage(ResCustInfoDto custInfoDto);

	/**
	 * 锟斤拷锟斤拷突锟斤拷锟揭筹拷斜锟�
	 * 
	 * @param custInfoDto
	 * @return
	 * @create 2015锟斤拷11锟斤拷13锟斤拷 锟斤拷锟斤拷5:27:33 lixing
	 * @history
	 */
	List<ResCustInfoDto> findMyCustListPage(ResCustInfoDto custInfoDto);

	/**
	 * 签约锟酵伙拷锟斤拷页锟叫憋拷
	 * 
	 * @param custInfoDto
	 * @return
	 * @create 2015锟斤拷11锟斤拷16锟斤拷 锟斤拷锟斤拷11:41:28 lixing
	 * @history
	 */
	List<ResCustInfoDto> findSignCustListPage(ResCustInfoDto custInfoDto);

	/**
	 * 锟斤拷失锟酵伙拷锟斤拷页锟叫憋拷
	 * 
	 * @param custInfoDto
	 * @return
	 * @create 2015锟斤拷11锟斤拷17锟斤拷 锟斤拷锟斤拷6:43:42 lixing
	 * @history
	 */
	List<ResCustInfoDto> findLosingCustListPage(ResCustInfoDto custInfoDto);

	/**
	 * 锟斤拷默锟酵伙拷锟斤拷页锟叫憋拷
	 * 
	 * @param custInfoDto
	 * @return
	 * @create 2015锟斤拷11锟斤拷16锟斤拷 锟斤拷锟斤拷6:07:55 lixing
	 * @history
	 */
	List<ResCustInfoDto> findSilentCustListPage(ResCustInfoDto custInfoDto);

	/**
	 * 全锟斤拷锟酵伙拷锟斤拷页锟叫憋拷
	 * 
	 * @param custInfoDto
	 * @return
	 * @create 2015锟斤拷11锟斤拷17锟斤拷 锟斤拷锟斤拷6:42:19 lixing
	 * @history
	 */
	List<ResCustInfoDto> findAllTypeCustListPage(ResCustInfoDto custInfoDto);

	/**
	 * 锟斤拷莸缁帮拷锟斤拷锟斤拷锟斤拷源
	 * 
	 * @param bean
	 * @return
	 * @create 2016锟斤拷1锟斤拷27锟斤拷 锟斤拷锟斤拷3:21:34 wuwei
	 * @history
	 */
	List<ResCustInfoDto> findCustByTel(ResCustInfoDto bean);
	List<ResCustInfoDto> findCustByTelPhone(ResCustInfoDto bean);
	List<ResCustInfoDto> findCustDetailByTelPhone(ResCustInfoDto bean);
	List<ResCustInfoDto> findCustDetailIDByTelPhone(ResCustInfoDto bean);
	/**
	 * 锟斤拷锟斤拷锟酵伙拷锟斤拷 锟斤拷页锟叫憋拷
	 * 
	 * @param custInfoDto
	 * @return
	 * @create 2015锟斤拷11锟斤拷17锟斤拷 锟斤拷锟斤拷7:36:11 lixing
	 * @history
	 */
	List<ResCustInfoDto> findCustPoolListPage(ResCustInfoDto custInfoDto);

	/**
	 * 锟斤拷展锟斤拷锟斤拷突锟斤拷锟�锟斤拷询
	 * 
	 * @param custInfoDto
	 * @return
	 * @create 2015锟斤拷11锟斤拷18锟斤拷 锟斤拷锟斤拷11:58:31 lixing
	 * @history
	 */
	List<ResCustInfoDto> findClearPoolListPage(ResCustInfoDto custInfoDto);

	/**
	 * 锟斤拷锟斤拷锟斤拷锟饺硷拷
	 * 
	 * @param updateAcc
	 * @param isPrecedence
	 * @param ids
	 * @create 2015锟斤拷11锟斤拷13锟斤拷 锟斤拷锟斤拷7:27:41 lixing
	 * @history
	 */
	void setPrecedenceBatch(@Param("updateAcc") String updateAcc, @Param("isPrecedence") Integer isPrecedence, @Param("ids") List<String> ids);

	/**
	 * @param logAct
	 *            锟斤拷录锟斤拷锟绞猴拷
	 * @param ownerAcc
	 *            锟斤拷锟斤拷锟斤拷
	 * @param ids
	 *            锟酵伙拷锟斤拷偶锟斤拷锟�
	 */
	void updateMoveCust(@Param("logAct") String logAct, @Param("ownerAcc") String ownerAcc, @Param("ids") List<String> ids);

	/**
	 * 锟斤拷源锟酵伙拷锟斤拷锟斤拷锟斤拷统锟斤拷 锟斤拷锟�
	 * 
	 * @param custIds
	 * @create 2015-3-16 锟斤拷锟斤拷04:47:19 lixing
	 * @history
	 */
	public void cleanCallCustCountByCustId(@Param("custIds") List<String> custIds);

	/**
	 * 锟斤拷锟角┰硷拷突锟斤拷锟斤拷锟斤拷锟�
	 * 
	 * @param logAct
	 * @param ownerAcc
	 * @param ids
	 * @create 2014-9-12 锟斤拷锟斤拷01:00:27 wuwei
	 * @history
	 */
	void updateMoveSignCust(@Param("logAct") String logAct, @Param("ownerAcc") String ownerAcc, @Param("ids") List<String> ids);

	/**
	 * 锟斤拷锟斤拷突锟�
	 *
	 * @param status
	 *            状态
	 * @param logAct
	 *            锟斤拷录锟斤拷锟绞猴拷
	 * @param ids
	 *            锟酵伙拷锟斤拷偶锟斤拷锟�
	 */
	void updateBatchCust(@Param("status") Short status, @Param("logAct") String logAct, @Param("ids") List<String> ids);

	/**
	 * 锟斤拷锟斤拷锟睫革拷
	 * 
	 * @param resCustInfoDto
	 * @create 2015锟斤拷11锟斤拷18锟斤拷 锟斤拷锟斤拷10:04:40 lixing
	 * @history
	 */
	void updateByIds(ResCustInfoDto resCustInfoDto);

	/**
	 * 取锟斤拷 锟斤拷锟桔斤拷锟斤拷 锟斤拷页 ID
	 * 
	 * @param custInfoDto
	 * @return
	 * @create 2015锟斤拷11锟斤拷24锟斤拷 锟斤拷锟斤拷9:41:27 lixing
	 * @history
	 */
	List<ResCustInfoDto> findDeliveryIdsListPage(ResCustInfoDto custInfoDto);

	/**
	 * 取锟矫革拷锟斤拷剩锟斤拷锟斤拷源锟斤拷
	 * 
	 * @param ownerAcc
	 * @return
	 * @create 2015锟斤拷11锟斤拷26锟斤拷 锟斤拷锟斤拷9:37:15 lixing
	 * @history
	 */
	Integer findMyResSum(String ownerAcc);

	ResCustInfoDto findTeamCustByCustId(ResCustInfoDto dto);

	/**
	 * 锟睫改计伙拷锟斤拷锟斤拷
	 * 
	 * @param planDate
	 * @param orgId
	 * @param custId
	 * @create 2015锟斤拷12锟斤拷9锟斤拷 锟斤拷锟斤拷1:54:05 lixing
	 * @history
	 */
	void updatePlanDate(@Param("planDate") Date planDate, @Param("orgId") String orgId, @Param("custId") String custId);

	/**
	 * 锟接猴拷锟斤拷锟斤拷锟斤拷锟�
	 */
	public List<ResCustInfoBean> findDelayCallAlert(Map<String, Object> map);

	/**
	 * 锟斤拷默锟酵伙拷筛选
	 * 
	 * @param dto
	 * @return
	 * @create 2015锟斤拷12锟斤拷16锟斤拷 锟斤拷锟斤拷6:04:52 lixing
	 * @history
	 */
	public List<ResCustInfoDto> silentCustsSxListPage(ResCustInfoDto dto);

	/**
	 * 锟揭的客伙拷 页签 锟斤拷锟斤拷
	 * 
	 * @param dto
	 * @return
	 * @create 2015锟斤拷12锟斤拷21锟斤拷 锟斤拷锟斤拷11:05:34 lixing
	 * @history
	 */
	public Integer findCustNums(ResCustInfoDto dto);

	/**
	 * 锟介看锟斤拷源锟斤拷锟斤拷锟饺硷拷
	 * 
	 * @param
	 * @return
	 * @create 2015锟斤拷12锟斤拷21锟斤拷 锟斤拷锟斤拷11:05:34 lizhihui
	 * @history
	 */
	public Integer findIsPrecedence(Map<String, Object> map);

	/**
	 * 锟斤拷锟角┰硷拷锟斤拷锟�
	 * 
	 * @param dto
	 * @return
	 * @create 2015锟斤拷12锟斤拷23锟斤拷 锟斤拷锟斤拷4:32:44 lixing
	 * @history
	 */
	public Map<String, Integer> getSignNum(ResCustInfoDto dto);

	/**
	 * 锟斤拷锟剿碉拷签约锟酵伙拷之前状态
	 * 
	 * @param custInfoBean
	 * @create 2015锟斤拷12锟斤拷30锟斤拷 锟斤拷锟斤拷5:19:51 lixing
	 * @history
	 */
	public void rebackSignBeforeStatus(ResCustInfoBean custInfoBean);

	/**
	 * 取锟斤拷
	 * 
	 * @param dto
	 * @create 2016锟斤拷1锟斤拷16锟斤拷 锟斤拷锟斤拷1:57:06 lixing
	 * @history
	 */
	public void modifyQuHui(ResCustInfoDto dto);

	public List<ResCustInfoDto> findTenCust(ResCustInfoDto dto);

	/**
	 * 鏄垜鐨勮祫婧�涓旀剰鍚戞垨绛剧害
	 * 
	 * @param dto
	 * @return
	 * @create 2016骞�鏈�1鏃�涓婂崍10:56:07 wuwei
	 * @history
	 */
	public List<ResCustInfoDto> findCustByPhone(ResCustInfoDto dto);

	/**
	 * 鍙栨剰鍚戝鎴峰墠鍗佹潯
	 * 
	 * @param map
	 * @return
	 * @create 2016骞�鏈�4鏃�涓婂崍11:52:12 Administrator
	 * @history
	 */
	public List<ResCustInfoDto> findCustByFollowDate(Map<String, Object> map);

	public List<ResCustInfoDto> findCustByIds(Map<String, Object> map);
}
