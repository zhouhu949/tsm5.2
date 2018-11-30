package com.qftx.tsm.report.dao;

import com.qftx.tsm.report.bean.LayoutProvinceOptionBean;

public interface LayoutProvinceOptionBeanMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int insert(LayoutProvinceOptionBean record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(LayoutProvinceOptionBean record);

    /**
     *
     * @mbg.generated
     */
    LayoutProvinceOptionBean selectByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LayoutProvinceOptionBean record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LayoutProvinceOptionBean record);
}