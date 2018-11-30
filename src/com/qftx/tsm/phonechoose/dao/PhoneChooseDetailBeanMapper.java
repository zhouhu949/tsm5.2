package com.qftx.tsm.phonechoose.dao;

import org.apache.ibatis.annotations.Param;

import com.qftx.tsm.phonechoose.bean.PhoneChooseDetailBean;

public interface PhoneChooseDetailBeanMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int insert(PhoneChooseDetailBean record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(PhoneChooseDetailBean record);

    /**
     *
     * @mbg.generated
     */
    PhoneChooseDetailBean selectByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(PhoneChooseDetailBean record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(PhoneChooseDetailBean record);
    
    int findCountByResCustId(@Param("orgId")String orgId, @Param("rciId")String rciId);
}