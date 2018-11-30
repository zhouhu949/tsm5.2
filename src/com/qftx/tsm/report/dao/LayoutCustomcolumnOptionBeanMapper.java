package com.qftx.tsm.report.dao;

import com.qftx.tsm.report.bean.LayoutCustomcolumnOptionBean;

public interface LayoutCustomcolumnOptionBeanMapper {
    /**
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int insert(LayoutCustomcolumnOptionBean record);

    /**
     *
     * @mbg.generated
     */
    int insertSelective(LayoutCustomcolumnOptionBean record);

    /**
     *
     * @mbg.generated
     */
    LayoutCustomcolumnOptionBean selectByPrimaryKey(String id);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(LayoutCustomcolumnOptionBean record);

    /**
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(LayoutCustomcolumnOptionBean record);
}