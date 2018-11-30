package com.qftx.tsm.cust.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ��Դ����dto
 * Created by Administrator on 2016/5/31.
 */

public class ResOptDto implements Serializable {
    public Integer total;//�ܴ���
    public Integer optedNum; //�Ѵ������
    public Integer type;  //��������
    public List<ResOptDetailDto> list = new ArrayList<ResOptDetailDto>();
    public long totalLength; // ������ʱ��
    public Boolean isFinished; // �Ƿ���� 0-δ��ɣ�1-���
    public String orgId;

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public Boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished) {
        this.isFinished = isFinished;
    }

    public void calOptedNum(List<ResOptDetailDto> list) {
        if (list != null && list.size() > 0) {
            for (ResOptDetailDto resOptDetailDto : list) {
                if(resOptDetailDto.getIsFinished()){
                    optedNum = optedNum + 1;
                }
            }
        }
    }

    public void getTotalDiff(List<ResOptDetailDto> list) {
        if (list != null && list.size() > 0) {
            for (ResOptDetailDto resOptDetailDto : list) {
                totalLength = totalLength + resOptDetailDto.getDiff();
            }
        }
    }

    public long getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public List<ResOptDetailDto> getList() {
        return list;
    }

    public void setList(List<ResOptDetailDto> list) {
        this.list = list;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOptedNum() {
        return optedNum;
    }

    public void setOptedNum(Integer optedNum) {
        this.optedNum = optedNum;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
