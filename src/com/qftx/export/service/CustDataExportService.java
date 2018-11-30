package com.qftx.export.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedNames;
import com.qftx.common.cached.CachedService;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.export.dao.CustDataExportMapper;
import com.qftx.tsm.cust.bean.CustDefinedDataBean;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dao.CustDefinedDataMapper;
import com.qftx.tsm.cust.dao.ResCustInfoDetailMapper;
import com.qftx.tsm.cust.dto.ResCustInfoDto;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.enums.SysEnum;

@Service
public class CustDataExportService {
	
	@Autowired
	private CustDataExportMapper custDataExportMapper;
	@Autowired
	private ResCustInfoDetailMapper resCustInfoDetailMapper;
	@Autowired
	private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private CustDefinedDataMapper custDefinedDataMapper;
	
	public List<ResCustInfoBean> getExportResList(ResCustInfoDto resCustInfoDto,ShiroUser user,List<String> exportColumns,boolean isShowArea) throws Exception{
		List<ResCustInfoBean> list = new ArrayList<ResCustInfoBean>();
		resCustInfoDto.setState(user.getIsState());
		resCustInfoDto.setOrgId(user.getOrgId());
		if (user.getIssys() != null && user.getIssys() == 1) {
			resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
			resCustInfoDto.setAdminAcc(user.getAccount());
			if (resCustInfoDto.getOsType().equals("1")  && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())){
					accs.add(user.getAccount());
				}
				resCustInfoDto.setOwnerAccs(accs);
			} else if (resCustInfoDto.getOsType().equals("2") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			} else {
				// 处理拥有者
				if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
					resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
				} else {
					resCustInfoDto.setOwnerAccsStr(user.getAccount());
					resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
				}
			}
		} else {
			resCustInfoDto.setOwnerAcc(user.getAccount());
		}
		// 默认选中全部标签
		if (StringUtils.isBlank(resCustInfoDto.getNoteType())) {
			resCustInfoDto.setNoteType("1");
		}
		// 处理添加/分配时间
		if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
			resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
			resCustInfoDto.setPendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 处理最近联系时间
		if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
			resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
			resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 下次联系时间
		if (resCustInfoDto.getnDateType() != null && resCustInfoDto.getnDateType() != 0 && resCustInfoDto.getnDateType() != 5) {
			resCustInfoDto.setStartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			resCustInfoDto.setEndDate(getEndDateStr(resCustInfoDto.getnDateType()));
		}
		//导入部门
		if(StringUtils.isNotBlank(resCustInfoDto.getImportDeptIdsStr())) resCustInfoDto.setImportDeptIds(Arrays.asList(resCustInfoDto.getImportDeptIdsStr().split(",")));
		
		setSeachParam(resCustInfoDto, SysEnum.SEARCH_SET_MODULE_4.getState());
		
		if(resCustInfoDto.getEmptySearch()){
			return list;
		}
		
    	list = custDataExportMapper.findResCustList(resCustInfoDto);
    	
    	multiDefinedShowChange(list, user.getIsState(), resCustInfoDto.getOrgId(),exportColumns,isShowArea);
    	
		return list;
	}
	
	public Integer getExportResNum(ResCustInfoDto resCustInfoDto,ShiroUser user) throws Exception{
		Integer totalNum = 0;
		resCustInfoDto.setState(user.getIsState());
		resCustInfoDto.setOrgId(user.getOrgId());
		if (user.getIssys() != null && user.getIssys() == 1) {
			resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
			resCustInfoDto.setAdminAcc(user.getAccount());
			if (resCustInfoDto.getOsType().equals("1")  && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())){
					accs.add(user.getAccount());
				}
				resCustInfoDto.setOwnerAccs(accs);
			} else if (resCustInfoDto.getOsType().equals("2") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			} else {
				// 处理拥有者
				if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
					resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
				} else {
					resCustInfoDto.setOwnerAccsStr(user.getAccount());
					resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
				}
			}
		} else {
			resCustInfoDto.setOwnerAcc(user.getAccount());
		}
		// 默认选中全部标签
		if (StringUtils.isBlank(resCustInfoDto.getNoteType())) {
			resCustInfoDto.setNoteType("1");
		}
		// 处理添加/分配时间
		if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
			resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
			resCustInfoDto.setPendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 处理最近联系时间
		if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
			resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
			resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 下次联系时间
		if (resCustInfoDto.getnDateType() != null && resCustInfoDto.getnDateType() != 0 && resCustInfoDto.getnDateType() != 5) {
			resCustInfoDto.setStartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			resCustInfoDto.setEndDate(getEndDateStr(resCustInfoDto.getnDateType()));
		}
		//导入部门
		if(StringUtils.isNotBlank(resCustInfoDto.getImportDeptIdsStr())) resCustInfoDto.setImportDeptIds(Arrays.asList(resCustInfoDto.getImportDeptIdsStr().split(",")));
		
		setSeachParam(resCustInfoDto, SysEnum.SEARCH_SET_MODULE_4.getState());
		
		if(resCustInfoDto.getEmptySearch()){
			return totalNum;
		}
		
		totalNum = custDataExportMapper.findResCustNum(resCustInfoDto);
		
		return totalNum;
	}
	
	public List<ResCustInfoBean> getExportCustList(ResCustInfoDto resCustInfoDto,ShiroUser user,List<String> exportColumns,boolean isShowArea) throws Exception{
		List<ResCustInfoBean> list = new ArrayList<ResCustInfoBean>();
		resCustInfoDto.setState(user.getIsState());
		resCustInfoDto.setOrgId(user.getOrgId());
		if (user.getIssys() != null && user.getIssys() == 1) {
			resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
			resCustInfoDto.setAdminAcc(user.getAccount());
			if (resCustInfoDto.getOsType().equals("1") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())){
					accs.add(user.getAccount());
				}
				resCustInfoDto.setOwnerAccs(accs);
			} else if (resCustInfoDto.getOsType().equals("2") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			} else {
				// 处理拥有者
				if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
					resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
				} else {
					resCustInfoDto.setOwnerAccsStr(user.getAccount());
					resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
				}
			}
		} else {
			resCustInfoDto.setOwnerAcc(user.getAccount());
		}
		if (StringUtils.isBlank(resCustInfoDto.getNoteType())) {
			resCustInfoDto.setNoteType("1");
		}
		// 淘到客户时间
		if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
			resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
			resCustInfoDto.setPendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 处理最近联系时间
		if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5
				&& resCustInfoDto.getlDateType() != 6) {
			resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
			resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		if (resCustInfoDto.getnDateType() != null && resCustInfoDto.getnDateType() != 0 && resCustInfoDto.getnDateType() != 5
				&& resCustInfoDto.getnDateType() != 6) {
			resCustInfoDto.setStartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			resCustInfoDto.setEndDate(getEndDateStr(resCustInfoDto.getnDateType()));
		}

		// 处理标签
		if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
			resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
		}

		if(getCommonOwnerOpenState(user.getOrgId()) == 1){//如果开启共有者  设置查询共有者
			resCustInfoDto.setCommonAcc(user.getAccount());
			if(StringUtils.isNotBlank(resCustInfoDto.getCommonAccsStr())){
				resCustInfoDto.setCommonAccs(Arrays.asList(resCustInfoDto.getCommonAccsStr().split(",")));
			}
		}
		
		setSeachParam(resCustInfoDto, SysEnum.SEARCH_SET_MODULE_5.getState());
		
		if(resCustInfoDto.getEmptySearch()){
			return list;
		}
		
    	list = custDataExportMapper.findIntCustList(resCustInfoDto);
    	
    	multiDefinedShowChange(list, user.getIsState(), resCustInfoDto.getOrgId(),exportColumns,isShowArea);
		
		return list;
	}
	
	public Integer getExportCustNum(ResCustInfoDto resCustInfoDto,ShiroUser user) throws Exception{
		Integer totalNum = 0;
		resCustInfoDto.setState(user.getIsState());
		resCustInfoDto.setOrgId(user.getOrgId());
		if (user.getIssys() != null && user.getIssys() == 1) {
			resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
			resCustInfoDto.setAdminAcc(user.getAccount());
			if (resCustInfoDto.getOsType().equals("1") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())){
					accs.add(user.getAccount());
				}
				resCustInfoDto.setOwnerAccs(accs);
			} else if (resCustInfoDto.getOsType().equals("2") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
				resCustInfoDto.setOwnerAcc(user.getAccount());
			} else {
				// 处理拥有者
				if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
					resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
				} else {
					resCustInfoDto.setOwnerAccsStr(user.getAccount());
					resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
				}
			}
		} else {
			resCustInfoDto.setOwnerAcc(user.getAccount());
		}
		if (StringUtils.isBlank(resCustInfoDto.getNoteType())) {
			resCustInfoDto.setNoteType("1");
		}
		// 淘到客户时间
		if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
			resCustInfoDto.setPstartDate(getStartDateStr(resCustInfoDto.getoDateType()));
			resCustInfoDto.setPendDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 处理最近联系时间
		if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5
				&& resCustInfoDto.getlDateType() != 6) {
			resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
			resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		if (resCustInfoDto.getnDateType() != null && resCustInfoDto.getnDateType() != 0 && resCustInfoDto.getnDateType() != 5
				&& resCustInfoDto.getnDateType() != 6) {
			resCustInfoDto.setStartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			resCustInfoDto.setEndDate(getEndDateStr(resCustInfoDto.getnDateType()));
		}

		// 处理标签
		if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
			resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
		}

		if(getCommonOwnerOpenState(user.getOrgId()) == 1){//如果开启共有者  设置查询共有者
			resCustInfoDto.setCommonAcc(user.getAccount());
			if(StringUtils.isNotBlank(resCustInfoDto.getCommonAccsStr())){
				resCustInfoDto.setCommonAccs(Arrays.asList(resCustInfoDto.getCommonAccsStr().split(",")));
			}
		}
		
		setSeachParam(resCustInfoDto, SysEnum.SEARCH_SET_MODULE_5.getState());
		
		if(resCustInfoDto.getEmptySearch()){
			return totalNum;
		}
		
		totalNum = custDataExportMapper.findIntCustNum(resCustInfoDto);
    	
		return totalNum;
	}
	
	public List<ResCustInfoBean> getExportSignCustList(ResCustInfoDto resCustInfoDto,ShiroUser user,List<String> exportColumns,boolean isShowArea) throws Exception{
		List<ResCustInfoBean> list = new ArrayList<ResCustInfoBean>();
		resCustInfoDto.setState(user.getIsState());
		resCustInfoDto.setOrgId(user.getOrgId());
		if (user.getIssys() != null && user.getIssys() == 1) {
			resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
			resCustInfoDto.setAdminAcc(user.getAccount());
			if (resCustInfoDto.getOsType().equals("1") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
				resCustInfoDto.setOwnerAccs(accs);
			} else {
				// 处理拥有者
				if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
					resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
				} else {
					resCustInfoDto.setOwnerAccsStr(user.getAccount());
					resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
				}
			}
		} else {
			resCustInfoDto.setOwnerAcc(user.getAccount());
		}
		// 处理最近联系时间
		if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5
				&& resCustInfoDto.getlDateType() != 6) {
			resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
			resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 下次联系时间
		if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5
				&& resCustInfoDto.getoDateType() != 6) {
			resCustInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
		}
		// 最近签约时间
		if (resCustInfoDto.getnDateType() != null && resCustInfoDto.getnDateType() != 0 && resCustInfoDto.getnDateType() != 5) {
			resCustInfoDto.setStartDate(getStartDateStr(resCustInfoDto.getnDateType()));
			resCustInfoDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 默认选中全部标签
		if (StringUtils.isBlank(resCustInfoDto.getNoteType())) {
			resCustInfoDto.setNoteType("1");
		}
		// 处理标签
		if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
			resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
		}
		if(getCommonOwnerOpenState(user.getOrgId()) == 1){//如果开启共有者  设置查询共有者
			resCustInfoDto.setCommonAcc(user.getAccount());
			if(StringUtils.isNotBlank(resCustInfoDto.getCommonAccsStr())){
				resCustInfoDto.setCommonAccs(Arrays.asList(resCustInfoDto.getCommonAccsStr().split(",")));
			}
		}
		if(StringUtils.isNotBlank(resCustInfoDto.getServiceAccStr())){
			resCustInfoDto.setServiceAccs(Arrays.asList(resCustInfoDto.getServiceAccStr().split(",")));
		}
		
		setSeachParam(resCustInfoDto, SysEnum.SEARCH_SET_MODULE_6.getState());
		
		if(resCustInfoDto.getEmptySearch()){
			return list;
		}
		
    	list = custDataExportMapper.findSignCustList(resCustInfoDto);
    	
    	multiDefinedShowChange(list, user.getIsState(), resCustInfoDto.getOrgId(),exportColumns,isShowArea);
		
		return list;
	}
	
	public Integer getExportSignCustNum(ResCustInfoDto resCustInfoDto,ShiroUser user) throws Exception{
		Integer totalNum = 0;
		resCustInfoDto.setState(user.getIsState());
		resCustInfoDto.setOrgId(user.getOrgId());
		if (user.getIssys() != null && user.getIssys() == 1) {
			resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
			resCustInfoDto.setAdminAcc(user.getAccount());
			if (resCustInfoDto.getOsType().equals("1") && StringUtils.isBlank(resCustInfoDto.getOwnerAccsStr())) {
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
				resCustInfoDto.setOwnerAccs(accs);
			} else {
				// 处理拥有者
				if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
					resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
				} else {
					resCustInfoDto.setOwnerAccsStr(user.getAccount());
					resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
				}
			}
		} else {
			resCustInfoDto.setOwnerAcc(user.getAccount());
		}
		// 处理最近联系时间
		if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5
				&& resCustInfoDto.getlDateType() != 6) {
			resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
			resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 下次联系时间
		if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5
				&& resCustInfoDto.getoDateType() != 6) {
			resCustInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
		}
		// 最近签约时间
		if (resCustInfoDto.getnDateType() != null && resCustInfoDto.getnDateType() != 0 && resCustInfoDto.getnDateType() != 5) {
			resCustInfoDto.setStartDate(getStartDateStr(resCustInfoDto.getnDateType()));
			resCustInfoDto.setEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 默认选中全部标签
		if (StringUtils.isBlank(resCustInfoDto.getNoteType())) {
			resCustInfoDto.setNoteType("1");
		}
		// 处理标签
		if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
			resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
		}
		if(getCommonOwnerOpenState(user.getOrgId()) == 1){//如果开启共有者  设置查询共有者
			resCustInfoDto.setCommonAcc(user.getAccount());
			if(StringUtils.isNotBlank(resCustInfoDto.getCommonAccsStr())){
				resCustInfoDto.setCommonAccs(Arrays.asList(resCustInfoDto.getCommonAccsStr().split(",")));
			}
		}
		if(StringUtils.isNotBlank(resCustInfoDto.getServiceAccStr())){
			resCustInfoDto.setServiceAccs(Arrays.asList(resCustInfoDto.getServiceAccStr().split(",")));
		}
		
		setSeachParam(resCustInfoDto, SysEnum.SEARCH_SET_MODULE_6.getState());
		
		if(resCustInfoDto.getEmptySearch()){
			return totalNum;
		}
		
		totalNum = custDataExportMapper.findSignCustNum(resCustInfoDto);
		
		return totalNum;
	}
	
	public List<ResCustInfoBean> getExportSilentCustList(ResCustInfoDto resCustInfoDto,ShiroUser user,List<String> exportColumns,boolean isShowArea) throws Exception{
		List<ResCustInfoBean> list = new ArrayList<ResCustInfoBean>();
		resCustInfoDto.setState(user.getIsState());
		resCustInfoDto.setOrgId(user.getOrgId());
		if (user.getIssys() != null && user.getIssys() == 1) {
			resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
			resCustInfoDto.setAdminAcc(user.getAccount());
			if (resCustInfoDto.getOsType().equals("1")) {
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
				resCustInfoDto.setOwnerAccs(accs);
			} else {
				// 处理拥有者
				if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
					resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
				} else {
					resCustInfoDto.setOwnerAccsStr(user.getAccount());
					resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
				}
			}
		} else {
			resCustInfoDto.setOwnerAcc(user.getAccount());
		}
		if ((resCustInfoDto.getNoteType() == null || resCustInfoDto.getNoteType().equals("")) && resCustInfoDto.getDays() == null) {
			resCustInfoDto.setNoteType("1");
		}
		if (resCustInfoDto.getDays() != null) {
			resCustInfoDto.setNoteType("");
		}
		// 处理 合同截止时间
		if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
			resCustInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
		}
		// 处理 最近联系时间
		if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
			resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
			resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 处理标签
		if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
			resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
		}
		
		list = custDataExportMapper.findSilentCustList(resCustInfoDto);
    	
    	multiDefinedShowChange(list, user.getIsState(), resCustInfoDto.getOrgId(),exportColumns,isShowArea);
		
		return list;
	}
	
	public Integer getExportSilentCustNum(ResCustInfoDto resCustInfoDto,ShiroUser user) throws Exception{
		Integer totalNum = 0;
		resCustInfoDto.setState(user.getIsState());
		resCustInfoDto.setOrgId(user.getOrgId());
		if (user.getIssys() != null && user.getIssys() == 1) {
			resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
			resCustInfoDto.setAdminAcc(user.getAccount());
			if (resCustInfoDto.getOsType().equals("1")) {
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
				resCustInfoDto.setOwnerAccs(accs);
			} else {
				// 处理拥有者
				if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
					resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
				} else {
					resCustInfoDto.setOwnerAccsStr(user.getAccount());
					resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
				}
			}
		} else {
			resCustInfoDto.setOwnerAcc(user.getAccount());
		}
		if ((resCustInfoDto.getNoteType() == null || resCustInfoDto.getNoteType().equals("")) && resCustInfoDto.getDays() == null) {
			resCustInfoDto.setNoteType("1");
		}
		if (resCustInfoDto.getDays() != null) {
			resCustInfoDto.setNoteType("");
		}
		// 处理 合同截止时间
		if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
			resCustInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
		}
		// 处理 最近联系时间
		if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
			resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
			resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 处理标签
		if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
			resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
		}
		
		totalNum = custDataExportMapper.findSilentCustNum(resCustInfoDto);
    	
		return totalNum;
	}
	
	public List<ResCustInfoBean> getExportLosingCustList(ResCustInfoDto resCustInfoDto,ShiroUser user,List<String> exportColumns,boolean isShowArea) throws Exception{
		List<ResCustInfoBean> list = new ArrayList<ResCustInfoBean>();
		resCustInfoDto.setState(user.getIsState());
		resCustInfoDto.setOrgId(user.getOrgId());
		if (user.getIssys() != null && user.getIssys() == 1) {
			// resCustInfoDto.setAdminAcc(user.getAccount());
			resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
			resCustInfoDto.setAdminAcc(user.getAccount());
			if (resCustInfoDto.getOsType().equals("1")) {
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
				resCustInfoDto.setOwnerAccs(accs);
			} else {
				// 处理拥有者
				if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
					resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
				} else {
					resCustInfoDto.setOwnerAccsStr(user.getAccount());
					resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
				}
			}
		} else {
			resCustInfoDto.setOwnerAcc(user.getAccount());
		}

		if ((resCustInfoDto.getNoteType() == null || resCustInfoDto.getNoteType().equals("")) && resCustInfoDto.getDays() == null) {
			resCustInfoDto.setNoteType("1");
		}
		if (resCustInfoDto.getDays() != null) {
			resCustInfoDto.setNoteType("");
		}
		// 处理 合同截止时间
		if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
			resCustInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
		}
		// 处理 最近联系时间
		if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
			resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
			resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 处理标签
		if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
			resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
		}
		
		list = custDataExportMapper.findLosingCustList(resCustInfoDto);
    	
    	multiDefinedShowChange(list, user.getIsState(), resCustInfoDto.getOrgId(),exportColumns,isShowArea);
		
		return list;
	}
	
	public Integer getExportLosingCustNum(ResCustInfoDto resCustInfoDto,ShiroUser user) throws Exception{
		Integer totalNum = 0;
		resCustInfoDto.setState(user.getIsState());
		resCustInfoDto.setOrgId(user.getOrgId());
		if (user.getIssys() != null && user.getIssys() == 1) {
			// resCustInfoDto.setAdminAcc(user.getAccount());
			resCustInfoDto.setOsType(StringUtils.isBlank(resCustInfoDto.getOsType()) ? "1" : resCustInfoDto.getOsType());
			resCustInfoDto.setAdminAcc(user.getAccount());
			if (resCustInfoDto.getOsType().equals("1")) {
				List<String> accs = tsmGroupShareinfoService.getMemberAccs(user.getOrgId(), user.getAccount());
				if(!accs.contains(user.getAccount())) accs.add(user.getAccount());
				resCustInfoDto.setOwnerAccs(accs);
			} else {
				// 处理拥有者
				if (StringUtils.isNotBlank(resCustInfoDto.getOwnerAccsStr())) {
					resCustInfoDto.setOwnerAccs(Arrays.asList(resCustInfoDto.getOwnerAccsStr().split(",")));
				} else {
					resCustInfoDto.setOwnerAccsStr(user.getAccount());
					resCustInfoDto.setOwnerAccs(Arrays.asList(user.getAccount()));
				}
			}
		} else {
			resCustInfoDto.setOwnerAcc(user.getAccount());
		}

		if ((resCustInfoDto.getNoteType() == null || resCustInfoDto.getNoteType().equals("")) && resCustInfoDto.getDays() == null) {
			resCustInfoDto.setNoteType("1");
		}
		if (resCustInfoDto.getDays() != null) {
			resCustInfoDto.setNoteType("");
		}
		// 处理 合同截止时间
		if (resCustInfoDto.getoDateType() != null && resCustInfoDto.getoDateType() != 0 && resCustInfoDto.getoDateType() != 5) {
			resCustInfoDto.setPstartDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
			resCustInfoDto.setPendDate(getEndDateStr(resCustInfoDto.getoDateType()));
		}
		// 处理 最近联系时间
		if (resCustInfoDto.getlDateType() != null && resCustInfoDto.getlDateType() != 0 && resCustInfoDto.getlDateType() != 5) {
			resCustInfoDto.setStartActionDate(getStartDateStr(resCustInfoDto.getlDateType()));
			resCustInfoDto.setEndActionDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 处理标签
		if (StringUtils.isNotBlank(resCustInfoDto.getAllLabels())) {
			resCustInfoDto.setLabels(Arrays.asList(resCustInfoDto.getAllLabels().split(",")));
		}
		
		totalNum = custDataExportMapper.findLosingCustNum(resCustInfoDto);
		
		return totalNum;
	}
	
	public List<String> getShowDefineds(String orgId,Integer isState,Integer type,Map<String, String> columnMap){
		List<String> fields = new ArrayList<String>();
		List<CustFieldSet> fieldSets = null;
		if (isState == 1) {// 企业资源
			fieldSets = cachedService.getComFiledSets(orgId);
		} else {
			fieldSets = cachedService.getPersonFiledSets(orgId);
		}
		for(CustFieldSet fieldSet : fieldSets){
			if(type == 1 && fieldSet.getDataType() == 3 && fieldSet.getEnable() == 1 && columnMap.containsKey(fieldSet.getFieldCode())){
				fields.add(fieldSet.getFieldCode());
			}else if(type == 2 && fieldSet.getDataType() == 4 && fieldSet.getEnable() == 1 && columnMap.containsKey(fieldSet.getFieldCode())){
				fields.add(fieldSet.getFieldCode());
			}
		}
		return fields;
	}
	
	public void multiDefinedShowChange(List<ResCustInfoBean> list,Integer isState,String orgId,List<String> exportColumns,boolean isShowArea) throws Exception{
    	if(list.size() > 0){
    		Map<String, String> columnMap = new HashMap<String, String>();
    		for(String cl : exportColumns) columnMap.put(cl, cl);
    		List<String> multiList = getShowDefineds(orgId, isState, 2,columnMap);
			List<String> singleList = getShowDefineds(orgId, isState, 1,columnMap);
    		Map<String, Object> map = new HashMap<String, Object>();
	    	Map<String, Map<String, String>> custDataMap = new HashMap<String, Map<String,String>>();
	    	Map<String, Map<String, String>> codeNameMap = new HashMap<String, Map<String,String>>();
	    	List<CustDefinedDataBean> definedDatas = new ArrayList<CustDefinedDataBean>();
	    	Map<String, String> dataMap;
			List<OptionBean> option;
	    	if(multiList.size() > 0){
	    		List<List<String>> subCustIds = getShowCustIds(list,1000);
	    		
	    		for(List<String> custIds : subCustIds){
	    			map.put("orgId", orgId);
		        	map.put("fieldCodes", multiList);
		        	map.put("custIds", custIds);
		        	definedDatas.addAll(custDefinedDataMapper.findCustDefinedShowDatas(map));
	    		}
	    		
	    		String val;
	    		String oldVal;
	    		for(CustDefinedDataBean definedData : definedDatas){
	    			if(!codeNameMap.containsKey(definedData.getFieldCode())){
	    				option = cachedService.getOptionList(definedData.getFieldCode(), orgId);
	    				codeNameMap.put(definedData.getFieldCode(), cachedService.changeOptionListToMap(option));
	    			}
	    			val = codeNameMap.get(definedData.getFieldCode()).get(definedData.getFieldData());
	    			if(val != null){
	    				if(custDataMap.containsKey(definedData.getCustId())){
	    					if(custDataMap.get(definedData.getCustId()).containsKey(definedData.getFieldCode())){
	    						oldVal = custDataMap.get(definedData.getCustId()).get(definedData.getFieldCode());
	    						custDataMap.get(definedData.getCustId()).put(definedData.getFieldCode(), oldVal+"，"+val);
	    					}else{
	    						custDataMap.get(definedData.getCustId()).put(definedData.getFieldCode(), val);
	    					}
	    				}else{
	    					dataMap = new HashMap<String, String>();
	    					dataMap.put(definedData.getFieldCode(), val);
	    					custDataMap.put(definedData.getCustId(), dataMap);
	    				}
	    			}
	    		}
	    	}
	    	
	    	//组装
	    	Map<Integer, String> provinceMap = cachedService.getAreaMap(CachedNames.PROVINCE);
			Map<Integer, String> cityMap = cachedService.getAreaMap(CachedNames.CITY);
			Map<Integer, String> countyMap = cachedService.getAreaMap(CachedNames.COUNTY);
			
	    	Map<String,String> valueMap;
			Class tCls;
			String setName;
			Method setMethod;
			String getName;
			Method getMethod;
			for(ResCustInfoBean cust : list){
				tCls = cust.getClass();
				//处理多选
				if(definedDatas.size() > 0){
					if(custDataMap.containsKey(cust.getResCustId())){
						valueMap = custDataMap.get(cust.getResCustId());
						for(String key : valueMap.keySet()){
							setName =  "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
							setMethod = tCls.getDeclaredMethod(setName, String.class);
							setMethod.invoke(cust, valueMap.get(key));
						}
					}
				}
				//处理性别
				if(StringUtils.isNotBlank(cust.getSex())){
					if("1".equals(cust.getSex())){
						cust.setSex("男");
					}else if("2".equals(cust.getSex())){
						cust.setSex("女");
					}else{
						cust.setSex("");
					}
				}else{
					cust.setSex("");
				}
				//处理地区
				if(isShowArea){
					String area = provinceMap.get(cust.getProvinceId());
					if(cust.getCityId() != null) area+="-"+cityMap.get(cust.getCityId());
					if(cust.getCountyId() != null) area+="-"+countyMap.get(cust.getCountyId());
					cust.setLosingRemark(area);
				}
				//处理单选
				for(String singleDefined : singleList){
					if(!codeNameMap.containsKey(singleDefined)){
	    				option = cachedService.getOptionList(singleDefined, orgId);
	    				codeNameMap.put(singleDefined, cachedService.changeOptionListToMap(option));
	    			}
					getName = "get" + singleDefined.substring(0, 1).toUpperCase() + singleDefined.substring(1);
					getMethod = tCls.getDeclaredMethod(getName);
					Object definedVal = getMethod.invoke(cust);
					if(definedVal != null){
						String definedValueName = codeNameMap.get(singleDefined).get(definedVal.toString());
						definedValueName = definedValueName == null ? "" : definedValueName;
						setName =  "set" + singleDefined.substring(0, 1).toUpperCase() + singleDefined.substring(1);
						setMethod = tCls.getDeclaredMethod(setName, String.class);
						setMethod.invoke(cust, definedValueName);
					}
				}
			}
    	}
    }
	
	public List<List<String>> getShowCustIds(List<ResCustInfoBean> list,int spSize){
    	List<String> custIds = new ArrayList<String>();
    	List<List<String>> rtnList = new ArrayList<List<String>>();
    	for(ResCustInfoBean dto : list) custIds.add(dto.getResCustId());
    	int i = 0, start = 0,end = 0;
		int size = custIds.size();
		while(size > end){
			start = (i++) * spSize;
			end = (size > start + spSize) ? start + spSize : size;
			rtnList.add(custIds.subList(start, end));
		}
    	return rtnList;
    }
	
	public void setSeachParam(ResCustInfoDto resCustInfoDto,String module) throws Exception{
		//查出多选项查询字段
		List<String> multiSearchList = cachedService.getMultiSelectDefinedSearchFiled(resCustInfoDto.getOrgId(),module);
		List<String> cids = new ArrayList<String>();
    	if(resCustInfoDto.getState() != null && 
    			resCustInfoDto.getState() == 1 && 
    			StringUtils.isNotBlank(resCustInfoDto.getQueryText()) && 
    			(resCustInfoDto.getQueryType().equals("mobilephone") || resCustInfoDto.getQueryType().equals("mainLinkman"))){
    		if(resCustInfoDto.getQueryType().equals("mobilephone")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(resCustInfoDto.getOrgId(), resCustInfoDto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(resCustInfoDto.getOrgId(), resCustInfoDto.getQueryText());
    		}
    		if(cids.size() == 0){
    			resCustInfoDto.setEmptySearch(true);
    			return;
    		}
    		resCustInfoDto.setCustIds(cids);
    		resCustInfoDto.setQueryText(null);
    	}
    	
    	if(multiSearchList != null && multiSearchList.size() > 0){
			Map<String, Object> paramMap = getMultiDefinedSearchParam(resCustInfoDto, multiSearchList);
			if(paramMap.size() > 0){
				if(cids.size() > 0) paramMap.put("custIds", cids);
				List<String> custIds = custDefinedDataMapper.findCustIdsByDefinedData(paramMap);
				if(custIds.size() > 0){
					resCustInfoDto.setCustIds(custIds);
				}else{
					resCustInfoDto.setEmptySearch(true);
				}
			}
		}
	}
	
	 /**设置扩展表查询条件*/
    public Map<String, Object> getMultiDefinedSearchParam(ResCustInfoDto custInfoDto,List<String> multiList) throws Exception{
    	Map<String, Object> map = new HashMap<String, Object>();
    	Class<?> clazz = custInfoDto.getClass().getSuperclass();
    	List<String> fieldCodes = new ArrayList<String>();
    	List<String> fieldDatas = new ArrayList<String>();
    	String getName;
    	String setName;
    	Method getMethod;
    	Method setMethod;
    	for(String fieldCode : multiList){
    		if(fieldCode.equals(AppConstant.SEARCH_LABEL) && custInfoDto.getLabels() != null && custInfoDto.getLabels().size() > 0){
				fieldCodes.add(AppConstant.SEARCH_LABEL);
				fieldDatas.addAll(custInfoDto.getLabels());
				custInfoDto.setLabels(null);
				continue;
    		}
    		getName =  "get" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
    		getMethod = clazz.getDeclaredMethod(getName);
    		Object val = getMethod.invoke(custInfoDto);
    		if(val != null && StringUtils.isNotBlank(val.toString())){
    			fieldCodes.add(fieldCode);
    			fieldDatas.addAll(Arrays.asList(val.toString().split(",")));
    			setName =  "set" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
    			setMethod = clazz.getDeclaredMethod(setName, String.class);
    			setMethod.invoke(custInfoDto, "");
    		}
    	}
    	if(fieldCodes.size() > 0){
    		map.put("fieldCodes", fieldCodes);
    		map.put("fieldDatas", fieldDatas);
    		map.put("orgId", custInfoDto.getOrgId());
    	}
    	return map;
    }
	
	/**
	 * 获取第一天
	 * 
	 * @param type
	 *            1-当天 2-本周 3-本月 4-半年
	 * @return
	 * @create 2015年12月14日 下午3:48:05 lixing
	 * @history
	 */
	public String getStartDateStr(Integer type) {
		String str = "";
		if (type == 1) {
			str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
		} else if (type == 2) {
			str = DateUtil.getWeekFirstDay(new Date());
		} else if (type == 3) {
			str = DateUtil.getMonthFirstDay(new Date());
		} else if (type == 4) {
			str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), -180), DateUtil.DATE_DAY);
		}
		return str;
	}

	/**
	 * 获取最后一天
	 *
	 * @param type
	 *            1-当天 2-本周 3-本月 4-半年
	 * @return
	 * @create 2015年12月14日 下午3:48:05 lixing
	 * @history
	 */
	public static String getEndDateStr(Integer type) {
		String str = "";
		if (type == 1) {
			str = DateUtil.formatDate(new Date(), DateUtil.DATE_DAY);
		} else if (type == 2) {
			str = DateUtil.getWeekLastDay(new Date());
		} else if (type == 3) {
			str = DateUtil.getMonthLastDay(new Date());
		} else if (type == 4) {
			str = DateUtil.formatDate(DateUtil.getAddDate(new Date(), 180), DateUtil.DATE_DAY);
		}
		return str;
	}
	
	/**共有者开关  0-关闭 1-开启*/
	public int getCommonOwnerOpenState(String orgId){
		int open = 0;
		List<DataDictionaryBean> list = cachedService.getDirList(AppConstant.DATA_50011, orgId);
		if(!list.isEmpty() && list.get(0) != null){
			String dicValue = list.get(0).getDictionaryValue();
			open = Integer.valueOf(StringUtils.isNotBlank(dicValue) ? dicValue : "0");
		}
		return open;
	}
}
