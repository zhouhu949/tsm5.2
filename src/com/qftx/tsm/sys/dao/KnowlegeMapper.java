package com.qftx.tsm.sys.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.Knowlege;
import com.qftx.tsm.sys.dto.KnowlegeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


/**
 * 问答知识库表数据访问接口
 * 
 * @author: 徐承恩
 * @since: 2013-10-29 上午10:15:48
 * @history:
 */
public interface KnowlegeMapper extends BaseDao<Knowlege> {

	/**
	 * 批量伪删除(单条记录也可以删除)
	 * 
	 * @param map
	 *            ids列表,IS_DEL参数
	 */
	public void deleteKnowlegeBatch(Map<String, Object> map);
	
	
	/** 
	 * 通过知识id获取知识
	 * @param knowlegeId
	 * @return 
	 * @create  2013-11-7 上午10:07:51 wuwei
	 * @history  
	 */
	public Knowlege findKnowlegeById(@Param("orgId")String orgId,@Param("knowlegeId")String knowlegeId );
	
	public void update_new(Knowlege knowlege);
	
	public List<Knowlege> findListPage_new(Knowlege knowlege);
	
	public List<KnowlegeDto> getListPageDto(KnowlegeDto entity);

}