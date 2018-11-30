package com.qftx.tsm.phonechoose.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.qftx.tsm.phonechoose.bean.PhoneChooseFailDetailBean;

public interface PhoneChooseFailDetailBeanMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int insert(PhoneChooseFailDetailBean record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(PhoneChooseFailDetailBean record);

    /**
     *
     * @mbg.generated
     */
    PhoneChooseFailDetailBean selectByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PhoneChooseFailDetailBean record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PhoneChooseFailDetailBean record);
    
    
    List<Map<String, Integer>> countScreenFailNum(@Param("orgId") String orgId,@Param("taskId") String taskId);
    
    
    
    
    
}