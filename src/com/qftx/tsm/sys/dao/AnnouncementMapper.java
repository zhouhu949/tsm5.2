package com.qftx.tsm.sys.dao;

import com.qftx.common.dao.BaseDao;
import com.qftx.tsm.sys.bean.AnnouncementBean;
import com.qftx.tsm.sys.dto.AnnouncementDto;

import java.util.List;
import java.util.Map;

/**
 * 通知公告数据访问接口
 * @author 徐承恩
 */
public interface AnnouncementMapper extends BaseDao<AnnouncementBean>{
	
	/**
	 * 批量假删除通知公告
	 * @作者 徐承恩
	 * @创建时间 2014年3月20日 上午10:58:37
	 * @param param
	 */
    void deleteFakeBatch(Map<String, Object> param);
    
    /**
     * 根据ID查询单条通知公告
     */
    AnnouncementDto findNoticeInfoById(Map<String,String>map);
    
    /**
     * 
     * 根据实体条件分页查询通知公告
     * @作者 徐承恩
     * @创建时间 2014年3月26日 上午10:58:36
     * @param orgid
     * @return
     */
    List<AnnouncementDto> findNoticeListPage(AnnouncementDto dto);
    
    
    /**
     * 根据ID查询单条通知公告已阅读人
     */
    List<String> findNoticeReaderUserById(Map<String,String>map);
    
    
    /**
     * 根据实体查询单条通知公告已阅读总数
     */   
    List<AnnouncementDto> findNoticeReadersum(AnnouncementDto dto);
    
}