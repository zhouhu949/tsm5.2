package com.qftx.tsm.report;

import com.qftx.common.BaseUtest;
import com.qftx.tsm.callrecord.dto.CallNalysisDto;
import com.qftx.tsm.callrecord.util.CallRecordGetUtil;
import junit.framework.TestResult;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User�� bxl
 * Date�� 2016/3/10
 * Time�� 13:10
 */
public class TestCallSort extends BaseUtest {
    @Test
    public void testQuery() {
        Map<String,String> map = new HashMap<String, String>();
        map.put("orgId","8decbe1278b646b5a462bbd4bc80bd88");
        map.put("inputAcc","1,2");
       // List<CallNalysisDto>  list=CallRecordGetUtil.getRecordeCallNalysis(map);
       // System.out.println(list.size());
    }
}
