package com.qftx.common.cached;

import com.mysql.jdbc.TimeUtil;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.BaseUtest;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.dto.TaoDto;
import com.qftx.tsm.cust.dto.TaoResDto;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustFieldSet;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * User�� bxl
 * Date�� 2015/11/19
 * Time�� 17:32
 */
public class TestCached extends BaseUtest {
	@Autowired
	private CachedService service;
	
//	@Test
	public void testTimeLength(){
		System.out.println("=================================");
		String isEffect =  service.getDirList(AppConstant.DATA04, shiroUser.getOrgId()).get(0).getDictionaryValue();
		System.out.println("isEffect="+isEffect);
		System.out.println("=================================");
	}
	
//	@Test
	public void getFollowDefaultTime(){
		List<DataDictionaryBean> disList = service.getDirList(AppConstant.DATA15, shiroUser.getOrgId());
		List<DataDictionaryBean> disList2 = service.getDirList(AppConstant.DATA20, shiroUser.getOrgId());
		List<DataDictionaryBean> disList3 = service.getDirList(AppConstant.DATA21, shiroUser.getOrgId());
		System.out.println(disList.get(0).getDictionaryName()+":"+disList.get(0).getDictionaryValue());
		System.out.println(disList2.get(0).getDictionaryName()+":"+disList2.get(0).getDictionaryValue());
		System.out.println(disList2.get(0).getDictionaryName()+":"+disList2.get(0).getIsOpen());
		System.out.println(disList3.get(0).getDictionaryName()+":"+disList3.get(0).getDictionaryValue());
	}
//	@Test
	public void getLabelList(){
		String key = AppConstant.SALES_TYPE_TEN;
		List<OptionBean> list = service.getOptionList(key, shiroUser.getOrgId());
		System.out.println(list.size());
	}
	@Test
	public void clearCached(){
		   CachedUtil.getInstance().delete("hzhh5" + CachedNames.SEPARATOR + CachedNames.RES_GROUP_NAME);
	}
	
//	@Test
	public void setTaoCached(){
		TaoDto taoDto = new TaoDto();
		ShiroUser user = ShiroUtil.getShiroUser();
		List<TaoResDto> taoResDtos = new ArrayList<TaoResDto>();
		TaoResDto taoResDto = new TaoResDto();
		taoResDto.setPhone("123456789");
		taoResDtos.add(taoResDto);
		taoDto.setTaoResDtos(taoResDtos);
		service.setTaoDto(user.getOrgId() , user.getAccount(),taoDto,null );
		System.out.println("设置缓存="+JsonUtil.getJsonString(taoDto));
	}
	
//	@Test
	public void getTaoCached() throws Exception {
		ShiroUser user = ShiroUtil.getShiroUser();
		TaoDto taoDto =service.getTaoDto(user.getOrgId() , user.getAccount(),null );
		System.out.println("获取缓存="+JsonUtil.getJsonString(taoDto.getTaoResDtos()));
	}
	
//    @Test
    public void testCachedLock() throws InterruptedException {
       final ArrayList<String> list=new ArrayList<String>();
      final   CachedService service = (CachedService) context.getBean("cachedService");
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                  //  CachedService service = (CachedService) context.getBean("cachedService");
                    String key="1_"+GuidUtil.getId();
                    list.add(key);
                    List<CustFieldSet> arrayList=  new ArrayList<CustFieldSet>();
                    arrayList.add(new CustFieldSet());
                    System.out.println(key + "=" + arrayList.size());

                }
            }.start();
        }
        for (int i = 0; i < 10; i++) {
            new Thread() {
                public void run() {
                    String key="2_"+GuidUtil.getId();
                    list.add(key);

                    List<CustFieldSet> arrayList=     new ArrayList<CustFieldSet>();
                    arrayList.add(new CustFieldSet());
                    arrayList.add(new CustFieldSet());
                    System.out.println(key + "=" + arrayList.size());
                }
            }.start();
        }
        Thread.sleep(5000);
        for (String s : list) {
           // System.out.println(s+"="+service.getFiledSet(s).size());
        }
    }
}
