package com.qftx.tsm.credit.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.credit.bean.CreditLeadInfoBean;
import com.qftx.tsm.credit.dto.CreditLeadInfoDto;
import com.qftx.tsm.credit.dto.StatisticInfoDto;
import com.qftx.tsm.credit.dto.TsmLoanReviewInfoDto;
import com.qftx.tsm.imp.dto.ImportRepeatDto;


/** 
 * @author: chenhm
 * @since: 2018年07月12日  下午1:26:28
 * @history:
 */
public interface CreditLeadInfoMapper extends BaseDao<CreditLeadInfoBean> {
	
	CreditLeadInfoBean getByPrimaryKey(@Param("orgId")String orgId,@Param("leadId")String leadId);
	
	/**
	 * 根据主键ID查询放款信息列表
	 * 
	 * @param custInfoDto
	 * @return
	 */
	List<CreditLeadInfoDto> findLeadInfoList(@Param("orgId")String orgId, @Param("leadIds")List<String> leadIds);
	
	/**
	 * 查询放款信息分页列表
	 * 
	 * @param custInfoDto
	 * @return
	 */
	List<CreditLeadInfoDto> findMyLeadInfoListPage(CreditLeadInfoDto custInfoDto);
	
	/**
	 * 查询放款信息记录总数
	 * 
	 * @param custInfoDto
	 * @return
	 */
	Integer findExportCount(CreditLeadInfoDto custInfoDto);
	
	List<CreditLeadInfoDto> findMyResCustPhoneListPage(CreditLeadInfoDto custInfoDto);
	
	/** 
	 * 批量修改
	 * @param CreditLeadInfoDto 
	 * @create  2015年11月18日 上午10:04:40 lixing
	 * @history  
	 */
	void updateByIds(CreditLeadInfoDto CreditLeadInfoDto);
	
	/** 
	 * 批量删除
	 * @param CreditLeadInfoDto 
	 * @create  2015年11月18日 上午10:04:40 lixing
	 * @history  
	 */
	void delBatchLead(CreditLeadInfoDto CreditLeadInfoDto);
	
	// 【导入去重】查询号码是否存在
	public Integer getRepeatByPhone(ImportRepeatDto entity);
	
	// 【导入去重】查询单位名称是否存在
	public Integer getRepeatByName(ImportRepeatDto entity);
		
	// 【导入去重】查询单位主页是否存在
	public Integer getRepeatByUnitHome(ImportRepeatDto entity);

	public void update(CreditLeadInfoBean custInfoBean);
	
	/**
	 * 根据身份证号查询贷款次数
	 * 
	 * @param orgId
	 * @param cardId
	 * @return
	 */
	public int queryLoanCount(@Param("orgId")String orgId,@Param("cardId")String cardId);
	
	/**
	 * 根据身份证号查询放款信息（待放款状态、驳回状态）
	 * 
	 * @param orgId
	 * @param cardId
	 * @return
	 */
	public List<CreditLeadInfoBean> findNotProcessedLeadInfoList(@Param("orgId")String orgId, @Param("leadId")String leadId, @Param("cardId")String cardId);
	
	/**
	 * 根据放款编号查询记录数
	 * 
	 * @param orgId
	 * @param cardId
	 * @return
	 */
	public int countByLeadCode(@Param("orgId")String orgId, @Param("leadId")String leadId, @Param("leadCode")String leadCode);
	
	
	/**
	 * 根据身份证号查询其他负责人下所拥有的放款信息
	 * 
	 * @param orgId
	 * @param cardId
	 * @return
	 */
	public List<CreditLeadInfoBean> findNotCurrentAccLeadInfoList(@Param("orgId")String orgId, @Param("leadId")String leadId, @Param("cardId")String cardId, @Param("ownerAcc")String ownerAcc);
	
	/**
	 * 于小晧
	 * @param tsmLoanReviewInfoDto
	 * @return
	 */
	public List<TsmLoanReviewInfoDto> findReviewInfoList(TsmLoanReviewInfoDto tsmLoanReviewInfoDto);
	
	public List<TsmLoanReviewInfoDto> findReviewInfoListPage(TsmLoanReviewInfoDto tsmLoanReviewInfoDto);

	public TsmLoanReviewInfoDto getReviewDetail(@Param("orgId") String orgId, @Param("leadId") String leadId);

	public List<StatisticInfoDto> findStatisticList(StatisticInfoDto statisticInfoDto);

	public void updateReviewStatus(TsmLoanReviewInfoDto dto);

	List<String> findLeadIdByStatus(@Param("orgId") String orgId, @Param("status") Integer status);

	void updateStatus(@Param("orgId") String orgId, @Param("status") Integer status, @Param("leadIds") List<String> list);

	void updateStatuing(@Param("orgId") String orgId, @Param("status") Integer status, @Param("leadIds") List<String> list,@Param("allNode") Integer auditNum, @Param("auditAcc") String auditAcc);

	void updateInitStatus(CreditLeadInfoBean creditLeadInfoBean);
	
}
