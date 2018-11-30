package com.qftx.tsm.option.dao;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.auth.service.OrgService;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.CodeUtils;
import com.qftx.common.util.IContants;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.service.DataDictionaryService;

/**
 * 字典测试类
 * 
 * @author: wuwei
 * @since: 2015年11月17日 下午1:25:32
 * @history:
 */
public class TestDataDictionnary1 extends BaseUtest {
	@Autowired
	private DataDictionaryService dataDictionaryService;
	@Autowired
	private OrgService orgService;
	/**
	 * 获取字典value
	 * 
	 * @create 2015年11月17日 下午1:26:10 wuwei
	 * @history
	 */
	@Test
	public void setDicTionnary() {
         System.out.println("开始----");
         // 查询所有机构ID
		List<String>orgIds = new ArrayList<String>();
		orgIds = orgService.getAllOrgIdsByProductType("6001");
		if(orgIds != null && orgIds.size() >0){
			for(int i = 0; i<orgIds.size(); i++){
				   Map<String, String> param = new HashMap<String, String>();
			         param.put("orgId", orgIds.get(i));
			         param.put("code", AppConstant.DATA15);
			         if(!"8decbe1278b646b5a462bbd4bc80bd58".equals(orgIds.get(i))){
			        	  Integer value =dataDictionaryService.getDataValueByOrgIdAndCode(param);
					         if(value != null){
					        	saveBatchDataDictionnary(orgIds.get(i));
					           //updateDataDictionnary(orgIds.get(i));
					         }      
			         }
			          
			}
		}

        //saveBatchDataDictionnary(null);
    
         System.out.println("结束----");
	}
	
	/** 
	 * 批量保存字典
	 * @create  2015年11月17日 下午1:42:01 wuwei
	 * @history  
	 */
	private void saveBatchDataDictionnary(String orgId) {
         System.out.println("批量保存字典内容开始----");
         List<DataDictionaryBean>list = new ArrayList<DataDictionaryBean>();
         DataDictionaryBean dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("销售进程回收设置");
//         dataDictionaryBean.setDictionaryValue("");
//         dataDictionaryBean.setDictionaryValueNotes("销售进程回收设置");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50001);
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("签约客户回收设置");
//         dataDictionaryBean.setDictionaryValue("");
//         dataDictionaryBean.setDictionaryValueNotes("is_open 0：关闭，1：开启");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50002);
//         dataDictionaryBean.setIsOpen("0");
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("自动连拨设置 ");
//         dataDictionaryBean.setDictionaryValue("0");
//         dataDictionaryBean.setDictionaryValueNotes(" dictionary_value 0：关闭，1：开启");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50003);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("启用指标系数占比 ");
//         dataDictionaryBean.setDictionaryValue("1");
//         dataDictionaryBean.setDictionaryValueNotes("dictionary_value 0：未选中，1：选中");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50004);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("每日的通话时长达标值");
//         dataDictionaryBean.setDictionaryValue("220");
//         dataDictionaryBean.setDictionaryValueNotes("根据勾选的对象确定通话时长的值 默认值为220");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50005);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("行动完成率基数设置 呼入时长");
//         dataDictionaryBean.setDictionaryValue("1");
//         dataDictionaryBean.setDictionaryValueNotes("dictionary_value 0： 未选中，1：选中");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50006);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("行动完成率基数设置 呼出时长 ");
//         dataDictionaryBean.setDictionaryValue("1");
//         dataDictionaryBean.setDictionaryValueNotes("dictionary_value 0： 未选中，1：选中");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50007);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("通话次数，呼出电话且对方接通的次数");
//         dataDictionaryBean.setDictionaryValue("440");
//         dataDictionaryBean.setDictionaryValueNotes(" 默认值为440");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50008);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("行动完成率基数设置 通话时长指标系数");
//         dataDictionaryBean.setDictionaryValue("0.5");
//         dataDictionaryBean.setDictionaryValueNotes(" 默认值为0.5");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50009);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("行动完成率基数设置 接通次数指标系数 ");
//         dataDictionaryBean.setDictionaryValue("0.5");
//         dataDictionaryBean.setDictionaryValueNotes("默认值为0.5");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50010);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("共有客户设置 ");
//         dataDictionaryBean.setDictionaryValue("0");
//         dataDictionaryBean.setDictionaryValueNotes("dictionary_value 0：关闭，1：开启");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50011);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("允许共有者放弃被共有的客户 ");
//         dataDictionaryBean.setDictionaryValue("0");
//         dataDictionaryBean.setDictionaryValueNotes("dictionary_value 0：关闭，1：开启");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50012);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("允许共有者修改被共有的客户信息 ");
//         dataDictionaryBean.setDictionaryValue("0");
//         dataDictionaryBean.setDictionaryValueNotes("dictionary_value 0：关闭，1：开启");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50013);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("允许共有者对共有客户进行签约......");
//         dataDictionaryBean.setDictionaryValue("");
//         dataDictionaryBean.setDictionaryValueNotes("dictionary_value 0：关闭，1：开启");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50014);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("APP查看附近的客户");
//         dataDictionaryBean.setDictionaryValue("0");
//         dataDictionaryBean.setDictionaryValueNotes("dictionary_value 0：关闭，1：开启");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50015);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("公海客户防骚扰设置 ");
//         dataDictionaryBean.setDictionaryValue("2");
//         dataDictionaryBean.setDictionaryValueNotes("is_open 0：关闭，1：开启");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50016);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         dataDictionaryBean.setIsOpen("0");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("生日弹幕弹出时间为每日的 小时");
//         dataDictionaryBean.setDictionaryValue("13");
//         dataDictionaryBean.setDictionaryValueNotes("");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50017);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("生日弹幕弹出时间为每日的 分钟");
//         dataDictionaryBean.setDictionaryValue("45");
//         dataDictionaryBean.setDictionaryValueNotes("");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50018);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("荣誉榜弹幕弹出世间为每月的第一个 星期值(1到7)");
//         dataDictionaryBean.setDictionaryValue("1");
//         dataDictionaryBean.setDictionaryValueNotes("");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50019);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("荣誉弹幕显示规则：新增签约");
//         dataDictionaryBean.setDictionaryValue("1");
//         dataDictionaryBean.setDictionaryValueNotes("dictionary_value 勾选表示启用 0：未勾选，1：勾选");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50020);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("荣誉弹幕显示规则：呼出次数");
//         dataDictionaryBean.setDictionaryValue("1");
//         dataDictionaryBean.setDictionaryValueNotes("dictionary_value 勾选表示启用 0：未勾选，1：勾选");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50021);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("荣誉弹幕显示规则： 新增意向");
//         dataDictionaryBean.setDictionaryValue("1");
//         dataDictionaryBean.setDictionaryValueNotes("dictionary_value 勾选表示启用 0：未勾选，1：勾选");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50022);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
//         dataDictionaryBean = new DataDictionaryBean();
//         dataDictionaryBean.setOrgId(orgId);
//         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
//         dataDictionaryBean.setDictionaryName("荣誉弹幕显示规则： 呼出时长");
//         dataDictionaryBean.setDictionaryValue("1");
//         dataDictionaryBean.setDictionaryValueNotes("dictionary_value 勾选表示启用 0：未勾选，1：勾选");
//         dataDictionaryBean.setIsDel(new Short("0"));
//         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50023);
//         dataDictionaryBean.setInputdate(new Date());
//         dataDictionaryBean.setInputerAcc("admin");
//         list.add(dataDictionaryBean);
//         
         dataDictionaryBean = new DataDictionaryBean();
         dataDictionaryBean.setOrgId(orgId);
         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
         dataDictionaryBean.setDictionaryName("资源回收设置单选按钮选择项");
         dataDictionaryBean.setDictionaryValue("1");
         dataDictionaryBean.setDictionaryValueNotes("dictionary_value 1:选择第一条，2:选择第二条");
         dataDictionaryBean.setIsDel(new Short("0"));
         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50024);
         dataDictionaryBean.setInputdate(new Date());
         dataDictionaryBean.setInputerAcc("admin");
         list.add(dataDictionaryBean);
         
         dataDictionaryBean = new DataDictionaryBean();
         dataDictionaryBean.setOrgId(orgId);
         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
         dataDictionaryBean.setDictionaryName("资源回收设置 多少天未联系将自动回收");
         dataDictionaryBean.setDictionaryValue("30");
         dataDictionaryBean.setDictionaryValueNotes("资源回收设置 多少天未联系将自动回收");
         dataDictionaryBean.setIsDel(new Short("0"));
         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50025);
         dataDictionaryBean.setInputdate(new Date());
         dataDictionaryBean.setInputerAcc("admin");
         list.add(dataDictionaryBean);
         
         dataDictionaryBean = new DataDictionaryBean();
         dataDictionaryBean.setOrgId(orgId);
         dataDictionaryBean.setDictionaryId(GuidUtil.getId());
         dataDictionaryBean.setDictionaryName("资源回收设置 多少天未联系将自动回收到 ");
         dataDictionaryBean.setDictionaryValue("1");
         dataDictionaryBean.setDictionaryValueNotes("1：待分配资源，2：公海客户池");
         dataDictionaryBean.setIsDel(new Short("0"));
         dataDictionaryBean.setDictionaryCode(AppConstant.DATA_50026);
         dataDictionaryBean.setInputdate(new Date());
         dataDictionaryBean.setInputerAcc("admin");
         list.add(dataDictionaryBean);
      
         dataDictionaryService.createBatch(list);
         System.out.println("批量返回值："+dataDictionaryBean.getDictionaryId());
         System.out.println("批量查保存字典内容结束----");
	}
	
	/** 
	 * 修改字典
	 * @create  2015年11月17日 下午1:42:01 wuwei
	 * @history  
	 */
	private void updateDataDictionnary(String orgId) {
         System.out.println("修改字典内容开始----");
         DataDictionaryBean bean = new DataDictionaryBean();
         bean.setOrgId(orgId);
         List<DataDictionaryBean> list = dataDictionaryService.getListByCondtion(bean);
         for(DataDictionaryBean ddb : list){
        	 if(AppConstant.DATA20.equals(ddb.getDictionaryCode())){
        		 ddb.setIsOpen("1");
        		 dataDictionaryService.modifyTrends(ddb);
        	 }
         }
         System.out.println("修改字典内容结束----");
	}
	
	
	public static void main(String[] args) throws UnsupportedEncodingException, Exception {
			String msgMain = "eyJtZXNzYWdlaW5mbyI6eyJtc2ciOiI1Ynk1NWJtVlVWRT0iLCJ3YXYiOiIxIiwidGl0bGUiOiI1Ynk1NWJtVjc3eUIifX0=";
			String msgXml = new String(CodeUtils.base64Decode(msgMain), IContants.CHAR_ENCODING);		
			System.out.println(msgXml);
	}
}
