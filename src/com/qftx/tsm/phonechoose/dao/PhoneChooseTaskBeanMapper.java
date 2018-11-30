package com.qftx.tsm.phonechoose.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qftx.tsm.phonechoose.bean.PhoneChooseTaskBean;
import com.qftx.tsm.phonechoose.dto.PhoneChooseTaskBeanDto;

public interface PhoneChooseTaskBeanMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String taskId);

    /**
     *
     * @mbg.generated
     */
    int insert(PhoneChooseTaskBean record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(PhoneChooseTaskBean record);

    /**
     *
     * @mbg.generated
     */
    PhoneChooseTaskBean selectByPrimaryKey(String taskId);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PhoneChooseTaskBean record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PhoneChooseTaskBean record);
    
    /**
     * 根据状态查询任务数
     * 
     * @param orgId
     * @param resGroupId
     * @param status
     * @return
     */
    int findCountByStatus(@Param("orgId")String orgId, @Param("resGroupId")String resGroupId, @Param("status")byte status);
    
    
    /**
     * 查询未完成的任务数量
     * 
     * @param orgId
     * @param resGroupId
     * @param status
     * @return
     */
    int findUnFinishTaskCount(@Param("orgId")String orgId, @Param("resGroupId")String resGroupId);
    
    /**
     * 查询筛查结果
     * 
     * @param orgId
     * @param resGroupId
     * @return
     */
    public List<PhoneChooseTaskBeanDto> findScreenResult(Map<String , Object> params);
    
}