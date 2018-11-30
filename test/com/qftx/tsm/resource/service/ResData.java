package com.qftx.tsm.resource.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.qftx.base.auth.service.OrgService;
import com.qftx.common.BaseUtest;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.bean.TsmCustGuide;
import com.qftx.tsm.cust.bean.TsmCustGuideProc;
import com.qftx.tsm.cust.dao.ComResourceMapper;
import com.qftx.tsm.cust.dao.ResCustInfoMapper;
import com.qftx.tsm.cust.dao.TsmCustGuideMapper;
import com.qftx.tsm.cust.dao.TsmCustGuideProcMapper;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.report.dao.CustInfoNalysisMapper;
import com.qftx.tsm.sys.bean.Product;

public class ResData extends BaseUtest {

	@Autowired
	private CachedService cachedService;
	@Autowired(required = false)
	private ResCustInfoMapper resCustInfoMapper;
	@Autowired(required = false)
	private TsmCustGuideMapper tsmCustGuideMapper;
	@Autowired(required = false)
	private TsmCustGuideProcMapper tsmCustGuideProcMapper;
	@Autowired(required = false)
	private CustInfoNalysisMapper custInfoNalysisMapper;

	// 根据单位来修改客户类型id
	public void setCustTypeId() {

		List<String> orgList = new ArrayList<String>();//
		// orgList.add("zsrhwt");
		orgList.add("zsrhyz");
		String sfOrgId = "zszsrh";
		Map<String, String> map = new HashMap<String, String>();
		// 银座

		// SALES_10001 -- 销售进程
		map.put("zsrhyz_SALES_10001_963ce064f546437c989f99ff62d76db6", "182503387f494d0f8bb5c3bb6b45bba6");
		map.put("zsrhyz_SALES_10001_90aaf26e926542dd981c476ae11cfc7d", "5e60177dafb14ac2b8899e32e959eca1");
		map.put("zsrhyz_SALES_10001_5e903207240743f3949e1153971c09c8", "f3aa6b189f0049ceb77a71bcae92d91e");
		map.put("zsrhyz_SALES_10001_11390c36163346698deb1131edb3838b", "f416d7b8371c4d11a4ed7ad56244b375");

		// SALES_10002-- 目标客户分类
		map.put("zsrhyz_SALES_10002_cbc560479b4d434ba16bb1756cb3ecf0", "878b33687f6c474990de7e6f97aba4cc");
		map.put("zsrhyz_SALES_10002_6c5f8f62737c4753ab6dbdbf2d74089d", "346547a95f8b4f82b5f395f9dde25a4f");
		map.put("zsrhyz_SALES_10002_45ba0b9e06204229aea93f26101dfa49", "f0f89b28275c4251bb425784527be3a6");

		// 标签
		map.put("zsrhyz_SALES_10010_98d9d8f75aff4d5e9deaf6727bf830a5", "1e5ea24c7d7248efa4e4da21969d06ce");
		map.put("zsrhyz_SALES_10010_6a2e5f8eca20461d810751fbe332cdd4", "f2f70b53f8e943fbb4d719e24a01bcec");
		map.put("zsrhyz_SALES_10010_376e3827a9f84163bb7da5d7aa84a410", "6aba287fea75458aa1edfb685f5b64e0");
		map.put("zsrhyz_SALES_10010_e36758ff7e64480fa9cc8cdb3eed22b1", "3e01232449db434ea643103236ef6ffb");
		map.put("zsrhyz_SALES_10010_507dd407acef4c5cb0153c9951e23b68", "02882239d49a460bbdc65c6057db3594");
		map.put("zsrhyz_SALES_10010_714616a546ae462b8b9b620c44da6880", "eece0aa69f9941d29fe77b3bf793cc81");

		// 产品
		map.put("zsrhyz_SALES_10005_6abe18a66a914798bba9e91dc1a025b3", "2d3cc96b76e64bd197a80a1540ca4a70");

		// 王田
		// SALES_10001 -- 销售进程
		map.put("zsrhwt_SALES_10001_963ce064f546437c989f99ff62d76db6", "31a7bd232428461dbd68bfb3d83d79f7");
		map.put("zsrhwt_SALES_10001_90aaf26e926542dd981c476ae11cfc7d", "d4b41b0792ef4fdabe84a77c672dc33d");
		map.put("zsrhwt_SALES_10001_5e903207240743f3949e1153971c09c8", "af44bd0a37d647c59ddd1a316d0eb91d");
		map.put("zsrhwt_SALES_10001_11390c36163346698deb1131edb3838b", "592ab9def4fc4f8db362f70ca8cba9fd");

		// SALES_10002-- 目标客户分类
		map.put("zsrhwt_SALES_10002_cbc560479b4d434ba16bb1756cb3ecf0", "e15fd5d03bdf4644be9a47230765fd85");
		map.put("zsrhwt_SALES_10002_6c5f8f62737c4753ab6dbdbf2d74089d", "a51ce37b9a0e481d8b4405cd2311e3f2");
		map.put("zsrhwt_SALES_10002_45ba0b9e06204229aea93f26101dfa49", "8f46c471eca04e47b13dbfd9b70a53cc");

		// 标签
		map.put("zsrhwt_SALES_10010_98d9d8f75aff4d5e9deaf6727bf830a5", "a00378f10c41474ebd03f68e6b253814");
		map.put("zsrhwt_SALES_10010_6a2e5f8eca20461d810751fbe332cdd4", "f6fe2c1b777f4221bdc0c1cc6154f817");
		map.put("zsrhwt_SALES_10010_376e3827a9f84163bb7da5d7aa84a410", "069797269a9a4b25b4bd94d60f8b02a9");
		map.put("zsrhwt_SALES_10010_e36758ff7e64480fa9cc8cdb3eed22b1", "15fd1bc0d4b94966a34a0849aaa8dc35");
		map.put("zsrhwt_SALES_10010_507dd407acef4c5cb0153c9951e23b68", "236e042e2c324cdda6bba7f1dda52025");
		map.put("zsrhwt_SALES_10010_714616a546ae462b8b9b620c44da6880", "0da09ffe5fb8427299617572ce3b44e9");

		// 产品
		map.put("zsrhwt_SALES_10005_6abe18a66a914798bba9e91dc1a025b3", "b421b61ff13344dc9e6b77cd6d834ed5");

		String proName = "润弘大宗商品";
		String custId = "";
		String companyId = "";
		int i = 0;
		Date updateDate = com.qftx.common.util.DateUtil.parse("2017-07-13 04:08:00", DateUtil.hour24HMSPattern);
		try {

			for (String orgId : orgList) {
				companyId = orgId;
				// 取得单位下各选项列表(销售进程、客户类型、适用产品、反馈信息)
				// List<OptionBean> sfsaleProcessList =
				// cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,
				// sfOrgId);
				// List<OptionBean> sfcustTypeList =
				// cachedService.getOptionList(AppConstant.SALES_TYPE_TWO,
				// sfOrgId);
				// List<Product> sfsuitProcList =
				// cachedService.getOpionProduct(sfOrgId);
				// List<OptionBean> sflabelList =
				// cachedService.getOptionList(AppConstant.SALES_TYPE_TEN,
				// sfOrgId);
				//
				// List<OptionBean> saleProcessList =
				// cachedService.getOptionList(AppConstant.SALES_TYPE_ONE,
				// orgId);
				// List<OptionBean> custTypeList =
				// cachedService.getOptionList(AppConstant.SALES_TYPE_TWO,
				// orgId);
				// List<Product> suitProcList =
				// cachedService.getOpionProduct(orgId);
				// List<OptionBean> labelList =
				// cachedService.getOptionList(AppConstant.SALES_TYPE_TEN,
				// orgId);
				String type = "3";// 1-销售不空，2-客户类型不空，3，标签不空
				List<ResCustInfoBean> resList = resCustInfoMapper.findResByOrgId(orgId, type);

				System.out.println("resList size =" + resList.size());
				String key = "";
				System.out.println("开始处理资源 客户类型、标签、销售进程id。。。。。");
				long startTime = System.currentTimeMillis();
				for (ResCustInfoBean bean : resList) { // 是否为老的销售进程
					custId = bean.getResCustId();
					if (StringUtils.isNotEmpty(bean.getLastOptionId())) {
						key = orgId + "_" + AppConstant.SALES_TYPE_ONE + "_" + bean.getLastOptionId();
						bean.setLastOptionId(map.get(key));
					}
					if (StringUtils.isNotEmpty(bean.getLastCustTypeId())) {
						key = orgId + "_" + AppConstant.SALES_TYPE_TWO + "_" + bean.getLastCustTypeId();
						bean.setLastCustTypeId(map.get(key));
					}
					if (StringUtils.isNotEmpty(bean.getLabelCode())) {
						List<OptionBean> labelList = cachedService.getOptionList(AppConstant.SALES_TYPE_TEN, sfOrgId);
						String labelCode = "";
						for (OptionBean optionBean : labelList) {
							if (bean.getLabelCode().contains(optionBean.getOptionlistId())) { // 跟老的数据
								key = orgId + "_" + AppConstant.SALES_TYPE_TEN + "_" + optionBean.getOptionlistId();
								labelCode = labelCode + map.get(key) + "#";
							}
						}
					}
					bean.setUpdateAcc("system");
					bean.setUpdateDate(updateDate);
					//resCustInfoMapper.update(bean);
					i++;
				}
				long time2 = System.currentTimeMillis();
				System.out.println("i=" + i + ".结束处理资源 客户类型、标签、销售进程id。。。。。。耗时=" + (time2 - startTime) + " 毫秒");
			}
		} catch (Exception e) {
			System.out.println("custId=" + custId + ",orgId=" + companyId);
			e.printStackTrace();
		}
	}

	// @Test
	public void setGuide() {
		List<String> orgList = new ArrayList<String>();
		orgList.add("zsrhwt");
		orgList.add("zsrhyz");
		String custId = "";
		String companyId = "";
		int i = 0;
		Date updateDate = com.qftx.common.util.DateUtil.parse("2017-07-13 04:08:00", DateUtil.hour24HMSPattern);
		try {
			List<OptionBean> saleList = new ArrayList<OptionBean>();
			// List<OptionBean> custTypeList = new ArrayList<OptionBean>();
			String key = "";
			String type = "2";
			System.out.println("开始处理资源 客户类型、标签、销售进程id。。。。。");
			long startTime = System.currentTimeMillis();
			for (String orgId : orgList) {
				companyId = orgId;
				List<ResCustInfoBean> resList = resCustInfoMapper.findResByOrgId(orgId, type);
				System.out.println("resList size =" + resList.size());
				for (ResCustInfoBean bean : resList) {
					TsmCustGuide guide = new TsmCustGuide();
					guide.setCustId(bean.getResCustId());
					guide.setOrgId(bean.getOrgId());
					guide.setCustTypeId(bean.getLastCustTypeId());
					guide.setSaleProcessId(bean.getLastOptionId());
					guide.setModifydate(updateDate);
					guide.setModifierAcc("system");
					//tsmCustGuideMapper.updateByCustId(guide);
					// TsmCustGuideProc pro = new TsmCustGuideProc();
					// pro.setOrgId(bean.getOrgId());
					// String procId = "zsrhwt".equals(orgId) ?
					// "b421b61ff13344dc9e6b77cd6d834ed5" :
					// "2d3cc96b76e64bd197a80a1540ca4a70";
					// pro.setProcId(procId);
					// pro.setGuideId(guideId);
					i++;
				}

			}
			long time2 = System.currentTimeMillis();
			System.out.println("i=" + i + ".结束处理资源 客户类型、标签、销售进程id。。。。。。耗时=" + (time2 - startTime) + " 毫秒");
		} catch (Exception e) {
			System.out.println("custId=" + custId + ",orgId=" + companyId);
			e.printStackTrace();
		}
	}

}
