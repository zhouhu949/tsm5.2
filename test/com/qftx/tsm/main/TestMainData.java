package com.qftx.tsm.main;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.util.DateUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.BaseUtest;
import com.qftx.tsm.main.bean.MainInfoBean;
import com.qftx.tsm.main.dao.MainInfoMapper;
import com.qftx.tsm.main.service.MainInfoService;
import com.qftx.tsm.plan.user.day.service.TestValue;

/**
 * User�� bxl
 * Date�� 2016/1/11
 * Time�� 9:49
 */
public class TestMainData extends BaseUtest {
    @Autowired(required = false)
    private MainInfoMapper mainInfoMapper;
    @Autowired(required = false)
    private UserService userService;
    @Autowired
    private MainInfoService mainInfoService;
    

    @Test
    public void insertTest() {
        User user = userService.getByAccount("hn001");
        List<MainInfoBean> list = mainInfoMapper.find();
        for (MainInfoBean infoBean : list) {
            mainInfoMapper.delete(infoBean.getId());
        }
        insert(user, "1");
        insert(user, "0");
    }

    private  int getNum(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max) % (max - min + 1) + min;
        return randomNumber;
    }

    @Test
    public  void findUserContactDTO(){
    	Date date = new Date();
    	Date fromDate = DateUtil.addDate(date, 0, -3, 0);
    	mainInfoService.findUserContactDTOByUser(TestValue.orgId, TestValue.userId, TestValue.userAccount, fromDate, date);
    }
    
    @Test
    public  void findTeamContactDTO(){
    	Date date = new Date();
    	Date fromDate = DateUtil.addDate(date, 0, -3, 0);try {
    		mainInfoService.findTeamContactDTO(TestValue.orgId, new String[]{TestValue.groupId}, fromDate, date);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    @Test
    public void findUserWillSignCustByDay(){
    	Date date = new Date();
    	Date fromDate = DateUtil.addDate(date, 0, -3, 0);
    	mainInfoService.findUserWillSignCustByDay(TestValue.orgId, TestValue.userAccount, fromDate, date,"asc");
    }
    
    @Test
    public void findUserWillSignCustByMonth(){
    	Date date = new Date();
    	Date fromDate = DateUtil.addDate(date, 0, -3, 0);
    	mainInfoService.findUserWillSignCustByMonth(TestValue.orgId, TestValue.userAccount, fromDate, date,"asc");
    }
    
    private void insert(User user, String type) {
        MainInfoBean infoBean = new MainInfoBean();
        infoBean.setOrgId(user.getOrgId());
        infoBean.setAccount(user.getUserAccount());
        infoBean.setId(GuidUtil.getId());
        infoBean.setInputtime(new Date());
        infoBean.setUpdateDate(new Date());
        infoBean.setType(type);
        infoBean.setCallNum(getNum(50,500));
        infoBean.setCustNum(getNum(50,500));
        infoBean.setNoNum(getNum(50,500));
        infoBean.setPoolNum(getNum(50,500));
        infoBean.setSignNum(getNum(50,500));
        infoBean.setTotalNum(infoBean.getCustNum()+infoBean.getPoolNum()+infoBean.getNoNum()+infoBean.getSignNum());
        infoBean.setIsSet(getNum(0,1));
        mainInfoMapper.insert(infoBean);
    }
}
