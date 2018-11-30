package com.qftx.tsm.log.dto;

import java.util.Comparator;

/**
 * User�� bxl
 * Date�� 2016/3/30
 * Time�� 10:52
 */
public class DtoLogSqlList  implements Comparator {
    public int compare(Object arg0, Object arg1) {
        int flag = -1;
        if (arg0 instanceof DtoLogSqlBean && arg1 instanceof DtoLogSqlBean) {
            DtoLogSqlBean img0=(DtoLogSqlBean)arg0;
            DtoLogSqlBean img1=(DtoLogSqlBean)arg1;
            flag=img0.getId().compareTo(img1.getId());
        }
        return flag;
    }

}
