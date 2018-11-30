package com.qftx.tsm.cust.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.cust.bean.TaoTagBean;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 *
 * @author: wuwei
 * @since: 2015-2-9 下午04:51:46
 * @history:
 */
public interface TaoTagMapper extends BaseDao<TaoTagBean> {

	/**
	 * 修改筛选条件
	 * 
	 * @param map
	 * @create 2015年12月30日 上午9:20:38 wuwei
	 * @history
	 */
	void updateTagByAcc(TaoTagBean tagBean);

	/**
	 * 修改资源为空
	 * 
	 * @param map
	 * @create 2015年12月30日 上午9:20:27 wuwei
	 * @history
	 */
	void updateResIdByAcc(Map<String, String> map);

	public TaoTagBean getByPrimaryKey(@Param("account")String account,@Param("orgId")String orgId);
}
