package com.qftx.tsm.credit.service;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.DateUtil;
import com.qftx.common.cached.CachedService;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.export.util.ExportExcelUtil;
import com.qftx.export.util.IExportFormatter;
import com.qftx.tsm.credit.bean.CreditLeadInfoBean;
import com.qftx.tsm.credit.bean.TsmLeadDefinedData;
import com.qftx.tsm.credit.bean.TsmLeadReviewInfo;
import com.qftx.tsm.credit.dao.CreditLeadInfoMapper;
import com.qftx.tsm.credit.dao.TsmCustWorkflowSetMapper;
import com.qftx.tsm.credit.dao.TsmLeadDefinedDataMapper;
import com.qftx.tsm.credit.dao.TsmLeadReviewInfoMapper;
import com.qftx.tsm.credit.dto.StatisticInfoDto;
import com.qftx.tsm.credit.dto.TsmLoanReviewInfoDto;
import com.qftx.tsm.credit.scontrol.LoanReviewAction;
import com.qftx.tsm.message.service.TsmMessageService;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.credit.bean.TsmCustWorkflowSetBean;
import com.qftx.tsm.sys.enums.SysEnum;

@Service
public class LoanReviewService {
	private static final Logger logger = Logger.getLogger(LoanReviewAction.class);
	@Autowired
	private CreditLeadInfoMapper creditLeadInfoMapper;
	@Autowired
	private CachedService cachedService;
	@Autowired
	private TsmCustWorkflowSetMapper tsmCustWorkflowSetMapper;
	@Autowired
	private TsmLeadDefinedDataMapper tsmLeadDefinedDataMapper;
	@Autowired
	private TsmMessageService tsmMessageService;
	@Autowired
	private OrgGroupUserService orgGroupUserService;
	@Autowired
	private TsmLeadReviewInfoMapper tsmLeadReviewInfoMapper;
	@Autowired
	private UserService userService;

	/**
	 * 审核详情，确认
	 * 
	 * @param orgId
	 * @param leadId
	 * @return
	 * @throws Exception
	 */
	public TsmLoanReviewInfoDto getReviewInfo(String leadId) throws Exception {
		ShiroUser user = ShiroUtil.getShiroUser();
		TsmLoanReviewInfoDto tsmLoanReviewInfoDto = creditLeadInfoMapper.getReviewDetail(user.getOrgId(), leadId);
		if (tsmLoanReviewInfoDto != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orgId", user.getOrgId());
			map.put("leadIds", Arrays.asList(leadId));
			List<TsmLeadDefinedData> datas = tsmLeadDefinedDataMapper.findCustDefinedShowDatas(map);
			Map<String, String> definedMap = new HashMap<String, String>();
			for (TsmLeadDefinedData cdb : datas) {
				if (definedMap.containsKey(cdb.getFieldCode())) {
					definedMap.put(cdb.getFieldCode(), definedMap.get(cdb.getFieldCode()) + "," + cdb.getFieldData());
				} else {
					definedMap.put(cdb.getFieldCode(), cdb.getFieldData());
				}
			}

			if (definedMap.size() > 0) {
				BeanUtils.copyProperties(tsmLoanReviewInfoDto, definedMap);
			}
			List<TsmLeadReviewInfo> list = tsmLeadReviewInfoMapper.findReviewsByLeadId(user.getOrgId(),
					tsmLoanReviewInfoDto.getLeadId());
			// 控制审核部分操作
			TsmCustWorkflowSetBean tsmCustWorkflowSet = tsmCustWorkflowSetMapper.getByOrgId(user.getOrgId());
			if (tsmCustWorkflowSet.getType() == 0) {
				tsmLoanReviewInfoDto.setReviewAuth("3");
			} else {
				tsmLoanReviewInfoDto.setReviewAuth("2");
				if(tsmLoanReviewInfoDto.getStatus() == 1) {
					if (tsmLoanReviewInfoDto.getAllNode() == 1) {
						if (list.size() == 0 && tsmLoanReviewInfoDto.getStatus() == 1) {
							addListData(list, 1, tsmCustWorkflowSet.getAuditorAcc1(), user.getOrgId(),
									tsmLoanReviewInfoDto.getStatus());
						}
					} else if (tsmLoanReviewInfoDto.getAllNode() == 2) {
						if (list.size() == 0 ) {
							addListData(list, 1, tsmCustWorkflowSet.getAuditorAcc1(), user.getOrgId(),
									tsmLoanReviewInfoDto.getStatus());
							addListData(list, 0, tsmCustWorkflowSet.getAuditorAcc2(), user.getOrgId(),
									tsmLoanReviewInfoDto.getStatus());
						}
						if (list.size() == 1) {
							addListData(list, 1, tsmCustWorkflowSet.getAuditorAcc2(), user.getOrgId(),
									tsmLoanReviewInfoDto.getStatus());
						}
					} else if (tsmLoanReviewInfoDto.getAllNode() == 3) {
						if (list.size() == 0) {
							addListData(list, 1, tsmCustWorkflowSet.getAuditorAcc1(), user.getOrgId(),
									tsmLoanReviewInfoDto.getStatus());
							addListData(list, 0, tsmCustWorkflowSet.getAuditorAcc2(), user.getOrgId(),
									tsmLoanReviewInfoDto.getStatus());
							addListData(list, 0, tsmCustWorkflowSet.getAuditorAcc3(), user.getOrgId(),
									tsmLoanReviewInfoDto.getStatus());
						}
						if (list.size() == 1) {
							addListData(list, 1, tsmCustWorkflowSet.getAuditorAcc2(), user.getOrgId(),
									tsmLoanReviewInfoDto.getStatus());
							addListData(list, 0, tsmCustWorkflowSet.getAuditorAcc3(), user.getOrgId(),
									tsmLoanReviewInfoDto.getStatus());
						}
						if (list.size() == 2) {
							addListData(list, 1, tsmCustWorkflowSet.getAuditorAcc3(), user.getOrgId(),
									tsmLoanReviewInfoDto.getStatus());
						}
					}
				}
			}
			if (tsmLoanReviewInfoDto.getStatus() == 1) {
				if (user.getAccount().equals(tsmLoanReviewInfoDto.getAuditAcc())) {
					tsmLoanReviewInfoDto.setReviewAuth("1");
				}
			}
			Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
			for (TsmLeadReviewInfo tsmLeadReviewInfo : list) {
				String userName = tsmLeadReviewInfo.getReviewAcc();
				if (StringUtils.isNotBlank(userName)) {
					String name = nameMap.get(tsmLeadReviewInfo.getReviewAcc());
					if(StringUtils.isNotBlank(name)) {
						userName = name;
					}
				}
				tsmLeadReviewInfo.setReviewAcc(userName);
			}
			tsmLoanReviewInfoDto.setReviewList(list);
		}
		return tsmLoanReviewInfoDto;
	}

	private void addListData(List<TsmLeadReviewInfo> list, int result, String acc, String orgId, int status) {
		TsmLeadReviewInfo tsmLeadReviewInfo1 = new TsmLeadReviewInfo();
		if (result == 1) {
			if (status == 3) {
				result = 0;
			}
		}
		tsmLeadReviewInfo1.setReviewResult(result);
		tsmLeadReviewInfo1.setReviewAcc(acc);
		list.add(tsmLeadReviewInfo1);
	}

	/**
	 * 修改审核节点
	 * 
	 * @param reviewInfoDto
	 * @return
	 */
	public Map<String, Object> updateReviewInfo(TsmLoanReviewInfoDto reviewInfoDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		ShiroUser user = ShiroUtil.getShiroUser();
		if (StringUtils.isBlank(reviewInfoDto.getLeadId())) {
			map.put("result", "-1");
			map.put("message", "缺少参数");
		}
		TsmLoanReviewInfoDto dto = creditLeadInfoMapper.getReviewDetail(user.getOrgId(), reviewInfoDto.getLeadId());
		if (dto == null) {
			map.put("result", "-1");
			map.put("message", "找不到记录");
			return map;
		}
		if (dto.getStatus() != 1) {
			map.put("result", "-1");
			map.put("message", "目标无法审核");
			return map;
		}
		dto.setOrgId(user.getOrgId());
		dto.setFundAccount(reviewInfoDto.getFundAccount());
		Date date = new Date();
		TsmCustWorkflowSetBean tsmCustWorkflowSet = tsmCustWorkflowSetMapper.getByOrgId(user.getOrgId());
		if (dto.getAuditNode() == 1) {// auditNode是预设定的节点
			insertReviewInfo(reviewInfoDto, date, user, dto.getAuditNode());
			if (dto.getAuditNode() < dto.getAllNode() && reviewInfoDto.getResult() == 2) {
				dto.setAuditAcc(tsmCustWorkflowSet.getAuditorAcc2());
				dto.setAuditNode(dto.getAuditNode() + 1); // 节点+1
				dto.setAuditTime(date);
				sendMessage(user, dto, tsmCustWorkflowSet.getAuditorAcc2());
			} else {
				if (reviewInfoDto.getResult() == 2) {
					sendMessageSuccess(dto, dto.getOwnerAcc());
				}
				dto.setStatus(reviewInfoDto.getResult());
				dto.setAuditAcc("0");
				dto.setAuditTime(date);
			}
			creditLeadInfoMapper.updateReviewStatus(dto);
		} else if (dto.getAuditNode() == 2) {
			insertReviewInfo(reviewInfoDto, date, user, dto.getAuditNode());
			if (dto.getAuditNode() < dto.getAllNode() && reviewInfoDto.getResult() == 2) {
				dto.setAuditAcc(tsmCustWorkflowSet.getAuditorAcc3());
				dto.setAuditNode(dto.getAuditNode() + 1); // 节点+1
				dto.setAuditTime(date);
				sendMessage(user, dto, tsmCustWorkflowSet.getAuditorAcc3());
			} else {
				if (reviewInfoDto.getResult() == 2) {
					sendMessageSuccess(dto, dto.getOwnerAcc());
				}
				dto.setAuditAcc("0");
				dto.setStatus(reviewInfoDto.getResult());
				dto.setAuditTime(date);
			}
			creditLeadInfoMapper.updateReviewStatus(dto);
		} else if (dto.getAuditNode() == 3) { // 目前节点只有3级，所以到3级无论如何都结束节点
			if (reviewInfoDto.getResult() == 2) {
				sendMessageSuccess(dto, dto.getOwnerAcc());
			}
			insertReviewInfo(reviewInfoDto, date, user, dto.getAuditNode());
			dto.setStatus(reviewInfoDto.getResult());
			dto.setAuditTime(date);
			dto.setAuditAcc("0");
			creditLeadInfoMapper.updateReviewStatus(dto);
		}
		map.put("result", "1");
		return map;
	}

	private void sendMessage(ShiroUser user, TsmLoanReviewInfoDto dto, String nextAcc) {
		Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
		String msg = String.format("您收到来自%s的放款审核申请，放款编号：%s", nameMap.get(dto.getOwnerAcc()), dto.getLeadCode());
		List<String> accList = new ArrayList<String>();
		accList.add(nextAcc);
		tsmMessageService.createQupaiMessage(dto.getLeadId(), dto.getOrgId(), dto.getOwnerAcc(), accList, msg);
	}
	
	private void sendMessageSuccess(TsmLoanReviewInfoDto dto, String nextAcc) {
		String msg = "您的放款信息已经确认放款";
		List<String> accList = new ArrayList<String>();
		accList.add(nextAcc);
		tsmMessageService.createQupaiQtMessage(dto.getLeadId(), dto.getOrgId(), dto.getOwnerAcc(), accList, msg);
	}

	private void insertReviewInfo(TsmLoanReviewInfoDto reviewInfoDto, Date date, ShiroUser user, int node) {
		TsmLeadReviewInfo tsmLeadReviewInfo = tsmLeadReviewInfoMapper.getAleadyInfo(reviewInfoDto.getLeadId(),
				user.getOrgId(), node);
		if (tsmLeadReviewInfo == null) {
			tsmLeadReviewInfo = new TsmLeadReviewInfo();
			tsmLeadReviewInfo.setLeadId(reviewInfoDto.getLeadId());
			tsmLeadReviewInfo.setOrgId(user.getOrgId());
			tsmLeadReviewInfo.setReviewId(SysBaseModelUtil.getModelId());
			tsmLeadReviewInfo.setReviewNode(node);
			tsmLeadReviewInfo.setReviewAcc(user.getAccount());
			tsmLeadReviewInfo.setReviewDate(date);
			tsmLeadReviewInfo.setReviewRemark(reviewInfoDto.getRemark());
			tsmLeadReviewInfo.setReviewResult(reviewInfoDto.getResult());
			tsmLeadReviewInfoMapper.insertSelective(tsmLeadReviewInfo);
		} else {
			tsmLeadReviewInfo.setReviewAcc(user.getAccount());
			tsmLeadReviewInfo.setReviewDate(date);
			tsmLeadReviewInfo.setReviewRemark(reviewInfoDto.getRemark());
			tsmLeadReviewInfo.setReviewResult(reviewInfoDto.getResult());
			tsmLeadReviewInfoMapper.updateByPrimaryKeySelective(tsmLeadReviewInfo);
		}
	}

	/**
	 * 新增预初始审核信息
	 * 
	 * @param orgId
	 * @param workflowId
	 * @param leadId
	 */
	public void init(ShiroUser user, CreditLeadInfoBean leadInfo) {
		String orgId = user.getOrgId();
		String leadId = leadInfo.getLeadId();
		if (StringUtils.isNotBlank(orgId) && StringUtils.isNotBlank(leadId)) {
			TsmCustWorkflowSetBean tsmCustWorkflowSet = tsmCustWorkflowSetMapper.getByOrgId(orgId);
			if (null != tsmCustWorkflowSet && tsmCustWorkflowSet.getType() == TsmCustWorkflowSetBean.TYPE_ENABLE) { // 需要审核
				CreditLeadInfoBean creditLeadInfoBean = creditLeadInfoMapper.getByPrimaryKey(orgId, leadId);
				creditLeadInfoBean.setAuditAcc(tsmCustWorkflowSet.getAuditorAcc1());
				creditLeadInfoBean.setAllNode(tsmCustWorkflowSet.getAuditNum());
				creditLeadInfoBean.setAuditNode(1);
				creditLeadInfoBean.setStatus(CreditLeadInfoBean.STATUS_DOING);
				creditLeadInfoMapper.update(creditLeadInfoBean);
				
				// 发送消息
				String msg = String.format("您收到来自%s的放款审核申请，放款编号：%s", user.getName(), leadInfo.getLeadCode());

				List<String> accList = new ArrayList<String>();
				accList.add(tsmCustWorkflowSet.getAuditorAcc1());
				tsmMessageService.createQupaiMessage(leadId, leadInfo.getOrgId(), leadInfo.getOwnerAcc(), accList, msg);

			} else { // 无需审核
				CreditLeadInfoBean creditLeadInfoBean = creditLeadInfoMapper.getByPrimaryKey(orgId, leadId);
				creditLeadInfoBean.setStatus(CreditLeadInfoBean.STATUS_DONE);
				creditLeadInfoBean.setAuditTime(creditLeadInfoBean.getCreateDate());
				creditLeadInfoBean.setAuditAcc("0");
				creditLeadInfoBean.setAllNode(tsmCustWorkflowSet.getAuditNum());
				creditLeadInfoBean.setAuditNode(1);
				creditLeadInfoMapper.update(creditLeadInfoBean);
				
			}
		}
	}
	
	
	public void updateInit(ShiroUser user, CreditLeadInfoBean leadInfo) {
		String orgId = user.getOrgId();
		String leadId = leadInfo.getLeadId();
		if (StringUtils.isNotBlank(orgId) && StringUtils.isNotBlank(leadId)) {
			TsmCustWorkflowSetBean tsmCustWorkflowSet = tsmCustWorkflowSetMapper.getByOrgId(orgId);
			if (null != tsmCustWorkflowSet && tsmCustWorkflowSet.getType() == TsmCustWorkflowSetBean.TYPE_ENABLE) { // 需要审核
				CreditLeadInfoBean creditLeadInfoBean = creditLeadInfoMapper.getByPrimaryKey(orgId, leadId);
				creditLeadInfoBean.setStatus(CreditLeadInfoBean.STATUS_DOING);
				creditLeadInfoBean.setAuditAcc(tsmCustWorkflowSet.getAuditorAcc1());
				creditLeadInfoBean.setAllNode(tsmCustWorkflowSet.getAuditNum());
				creditLeadInfoMapper.updateInitStatus(creditLeadInfoBean);
				tsmLeadReviewInfoMapper.deleteReviewInfoOne(orgId, leadId); // 删除审核记录
				// 发送消息
				String msg = String.format("您收到来自%s的放款审核申请，放款编号：%s", user.getName(), leadInfo.getLeadCode());
				
				List<String> accList = new ArrayList<String>();
				accList.add(tsmCustWorkflowSet.getAuditorAcc1());
				tsmMessageService.createQupaiMessage(leadId, leadInfo.getOrgId(), leadInfo.getOwnerAcc(), accList, msg);
				
			} else { // 无需审核
				CreditLeadInfoBean creditLeadInfoBean = creditLeadInfoMapper.getByPrimaryKey(orgId, leadId);
				creditLeadInfoBean.setStatus(CreditLeadInfoBean.STATUS_DONE);
				creditLeadInfoBean.setAuditTime(creditLeadInfoBean.getCreateDate());
				creditLeadInfoBean.setAuditAcc("0");
				creditLeadInfoBean.setAllNode(tsmCustWorkflowSet.getAuditNum());
				creditLeadInfoBean.setAuditNode(1);
				creditLeadInfoMapper.update(creditLeadInfoBean);
				tsmLeadReviewInfoMapper.deleteReviewInfoOne(orgId, leadId); // 删除审核记录
			}
		}
	}

	/**
	 * 查询字段映射关系
	 * 
	 * @param orgId
	 * @param isState
	 * @return
	 */
	public Map<String, String> getFiledMap(String orgId, Integer isState){
		List<CustSearchSet> fieldSets = new ArrayList<CustSearchSet>();
		Map<String, String> map = new HashMap<String, String>();
		fieldSets = cachedService.getQupaiCustSearchSetList(orgId);
		
		for(CustSearchSet fieldSet : fieldSets){
			if(fieldSet.getEnable() == CustSearchSet.ENABLE_YES){
				map.put(fieldSet.getDevelopCode(), fieldSet.getSearchName());
			}
		}
		
		if (0 == isState) {// 个人客户
			map.put("company", "单位名称");
		}
		return map;
	}
	
	public void exportData(List<TsmLoanReviewInfoDto> list, TsmLoanReviewInfoDto tsmLoanReviewInfoDto, OutputStream out)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ParseException {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<String> columns = Arrays.asList(tsmLoanReviewInfoDto.getExportColumnStr().split(","));
		Map<String, String> fieldMap = getFiledMap(user.getOrgId(), user.getIsState());
		List<String> exportColumns = new ArrayList<String>();
		List<String> headers = new ArrayList<String>();
		final Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
		for (String column : columns) {
			headers.add(fieldMap.get(column));
			exportColumns.add(column);
		}
		try {
			// 格式化导出数据列
			IExportFormatter formatter = new IExportFormatter() {
				@Override
				public Object format(String key, Object value) {
					// 将状态字段转换成中文
					if ("status".equals(key)) {
						return CreditLeadInfoBean.maps.get(key).get(value);
					}
					if ("auditStatus".equals(key)) {
						return CreditLeadInfoBean.auditMaps.get(key).get(value);
					}
					if ("ownerAcc".equals(key)) {
						return nameMap.get(value);
					}
					if ("inchargeAcc".equals(key)) {
						return nameMap.get(value);
					}
					return value;
				}
			};
			ExportExcelUtil.exportExcel(headers, columns, list, out, "yyyy-MM-dd", formatter);
		} catch (Exception e) {
			logger.error("导出数据异常！", e);
			throw new SysRunException("导出数据异常！");
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					logger.error("输出流关闭异常！", e);
				}
			}
		}
	}

	public List<TsmLoanReviewInfoDto> getReviewList(ShiroUser user, TsmLoanReviewInfoDto tsmLoanReviewInfoDto)
			throws Exception {
		// initAdmin(user, tsmLoanReviewInfoDto);
		
		if (StringUtils.isNotBlank(tsmLoanReviewInfoDto.getOwnerAccsStr())) {
			tsmLoanReviewInfoDto.setOwnerAccs(Arrays.asList(tsmLoanReviewInfoDto.getOwnerAccsStr().split(",")));
		}
		// 处理借款日
		if (tsmLoanReviewInfoDto.getLoanDateType() != null && tsmLoanReviewInfoDto.getLoanDateType() != 0
				&& tsmLoanReviewInfoDto.getLoanDateType() != 5) {
			tsmLoanReviewInfoDto.setLoanStartDate(getStartDateStr(tsmLoanReviewInfoDto.getLoanDateType()));
			tsmLoanReviewInfoDto.setLoanEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 处理到期日
		if (tsmLoanReviewInfoDto.getDueDateType() != null && tsmLoanReviewInfoDto.getDueDateType() != 0
				&& tsmLoanReviewInfoDto.getDueDateType() != 5) {
			tsmLoanReviewInfoDto.setDueStartDate(getStartDateStr(tsmLoanReviewInfoDto.getDueDateType()));
			tsmLoanReviewInfoDto.setDueEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 提交时间
		if (tsmLoanReviewInfoDto.getSubmitDateType() != null && tsmLoanReviewInfoDto.getSubmitDateType() != 0
				&& tsmLoanReviewInfoDto.getSubmitDateType() != 5) {
			tsmLoanReviewInfoDto.setSubmitStartDate(getStartDateStr(tsmLoanReviewInfoDto.getSubmitDateType()));
			tsmLoanReviewInfoDto.setSubmitEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 审核时间
		if (tsmLoanReviewInfoDto.getAuditDateType() != null && tsmLoanReviewInfoDto.getAuditDateType() != 0
				&& tsmLoanReviewInfoDto.getAuditDateType() != 5) {
			tsmLoanReviewInfoDto.setAuditStartDate(getStartDateStr(tsmLoanReviewInfoDto.getAuditDateType()));
			tsmLoanReviewInfoDto.setAuditEndDate(DateUtil.formatDate(new Date(), DateUtil.DATE_DAY));
		}
		// 查出多选项查询字段
		List<String> multiSearchList = cachedService.getLeadSelectDefinedSearchFiled(user.getOrgId(),
				SysEnum.QIPAI_SEARCH_SET_MODULE_2.getState());
		List<TsmLoanReviewInfoDto> rests = getMyResCustListPage(tsmLoanReviewInfoDto, multiSearchList, user);
		// 多选项显示
		if (rests.size() > 0) {
			List<String> multiShowList = cachedService.getLeadSelectDefinedShowFiled(user.getOrgId(),
					SysEnum.QIPAI_SEARCH_SET_MODULE_2.getState());
			List<String> singleShowList = cachedService.getLeadSingleDefinedShowFiled(user.getOrgId(),
					SysEnum.QIPAI_SEARCH_SET_MODULE_2.getState());
			multiDefinedShowChange(rests, multiShowList, singleShowList, user.getOrgId());
		}
		Map<String, String> nameMap = cachedService.getOrgUserNames(user.getOrgId());
		Map<String, String> deptName = cachedService.getOrgGroupNameMap(user.getOrgId());
		List<CustFieldSet> fieldList = cachedService.getQupaiFieldSet(user.getOrgId());
		for (TsmLoanReviewInfoDto rcid : rests) {
			if (rcid.getAuditNode() != null) {
				if (rcid.getAuditNode() == 1) {
					rcid.setAuditStatus(TsmLoanReviewInfoDto.REVIEW_LOAN_DOING); // 待确认
					rcid.setAuditStatusName("待确认");
				} else if (rcid.getAuditNode() == 2) {
					if (user.getAccount().equals(rcid.getAuditAcc())) {
						rcid.setAuditStatus(TsmLoanReviewInfoDto.REVIEW_LOAN_DOING);
						rcid.setAuditStatusName("待确认");
					} else {
						rcid.setAuditStatus(TsmLoanReviewInfoDto.REVIEW_LOAN_DONE); // 已确认
						rcid.setAuditStatusName("已确认");
					}
				} else if (rcid.getAuditNode() == 3) {
					if (user.getAccount().equals(rcid.getAuditAcc())) {
						rcid.setAuditStatus(TsmLoanReviewInfoDto.REVIEW_LOAN_DOING);
						rcid.setAuditStatusName("待确认");
					} else {
						rcid.setAuditStatus(TsmLoanReviewInfoDto.REVIEW_LOAN_DONE); // 已确认
						rcid.setAuditStatusName("已确认");
					}
				}
			}
			if (rcid.getStatus() == TsmLoanReviewInfoDto.STATUS_LOAN_DONE) {
				rcid.setAuditStatus(TsmLoanReviewInfoDto.REVIEW_LOAN_DONE); // 已确认
				rcid.setAuditStatusName("已确认");
			} else if (rcid.getStatus() == TsmLoanReviewInfoDto.STATUS_LOAN_REJECT) {
				rcid.setAuditStatus(TsmLoanReviewInfoDto.REVIEW_LOAN_REJECT); // 驳回
				rcid.setAuditStatusName("驳回");
			}
			if(rcid.getAuditStatus() != 1) {
				List<TsmLeadReviewInfo> list = tsmLeadReviewInfoMapper.findReviewsByLeadId(rcid.getOrgId(), rcid.getLeadId());
				for (TsmLeadReviewInfo tsmLeadReviewInfo : list) {
					if(user.getAccount().equals(tsmLeadReviewInfo.getReviewAcc())) {
						rcid.setAuditTime(tsmLeadReviewInfo.getReviewDate());
					}
				}
			} else {
				rcid.setAuditTime(null);
			}
			rcid.setOwnerName(StringUtils.isNotBlank(rcid.getOwnerAcc()) ? nameMap.get(rcid.getOwnerAcc()) : "");
			rcid.setInchargeName(StringUtils.isNotBlank(rcid.getInchargeAcc()) ? nameMap.get(rcid.getInchargeAcc()) : "");
			rcid.setImportDeptName(StringUtils.isNotBlank(rcid.getImportDeptId()) ? deptName.get(rcid.getImportDeptId()) : "");
			rcid.setShowdefined16(
					rcid.getDefined16() != null ? DateUtil.formatDate(rcid.getDefined16(), DateUtil.DATE_DAY) : "");
			rcid.setShowdefined17(
					rcid.getDefined17() != null ? DateUtil.formatDate(rcid.getDefined17(), DateUtil.DATE_DAY) : "");
			rcid.setShowdefined18(
					rcid.getDefined18() != null ? DateUtil.formatDate(rcid.getDefined18(), DateUtil.DATE_DAY) : "");
			for (CustFieldSet filed : fieldList) {
				if (filed.getDataType() == 5 && filed.getFieldCode().contains("defined")) {
					String value = BeanUtils.getProperty(rcid, filed.getFieldCode());
					BeanUtils.setProperty(rcid, filed.getFieldCode(), fmtMicrometer(value));
				}
			}
		}
		return rests;
	}
	
	public static String fmtMicrometer(String text) {
		DecimalFormat df = null;
		if(StringUtils.isBlank(text)){
			return text;
		}
		if (text.indexOf(".") > 0) {
			if (text.length() - text.indexOf(".") - 1 == 0) {
				df = new DecimalFormat("###,##0.");
			} else if (text.length() - text.indexOf(".") - 1 == 1) {
				df = new DecimalFormat("###,##0.0");
			} else {
				df = new DecimalFormat("###,##0.00");
			}
		} else {
			df = new DecimalFormat("###,##0");
		}
		double number = 0.0;
		try {
			number = Double.parseDouble(text);
		} catch (Exception e) {
			number = 0.0;
		}
		return df.format(number);
	}

	public Map<String, Object> findReviewInfos(TsmLoanReviewInfoDto tsmLoanReviewInfoDto) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		ShiroUser user = ShiroUtil.getShiroUser();
		tsmLoanReviewInfoDto.setOrgId(user.getOrgId());
		tsmLoanReviewInfoDto.setIsPage(1);
		List<TsmLoanReviewInfoDto> list = getReviewList(user, tsmLoanReviewInfoDto);
		map.put("item", tsmLoanReviewInfoDto);
		map.put("list", list);
		map.put("serverDay", user.getServerDay());
		map.put("isState", user.getIsState());
		map.put("loginAcc", user.getAccount());
		return map;
	}

	private List<TsmLoanReviewInfoDto> getMyResCustListPage(TsmLoanReviewInfoDto tsmLoanReviewInfoDto,
			List<String> multiList, ShiroUser user) throws Exception {
		Set<String> set = new HashSet<>();
		List<TsmLoanReviewInfoDto> rests = new ArrayList<TsmLoanReviewInfoDto>();
		if (multiList != null && multiList.size() > 0) {
			Map<String, Object> paramMap = getMultiDefinedSearchParam(tsmLoanReviewInfoDto, multiList);
			if (paramMap.size() > 0) { // 自定义多选项单独一张表
				List<String> custIds = tsmLeadDefinedDataMapper.findLeadIdsByDefinedData(paramMap);
				if (custIds.size() > 0) {
					set.addAll(custIds);
					tsmLoanReviewInfoDto.setCustIds(set);
				} else {
					return rests;
				}
			}
		}
		tsmLoanReviewInfoDto.setOwnerAcc(user.getAccount());
		if (tsmLoanReviewInfoDto.getAuditStatus() == null) {
			List<String> list = tsmLeadReviewInfoMapper.findLeadIdList(tsmLoanReviewInfoDto);
			Set<String> leadIdsList = new HashSet<>();
			leadIdsList.addAll(list);
			tsmLoanReviewInfoDto.setLeadIds(leadIdsList);
		} else if (tsmLoanReviewInfoDto.getAuditStatus().intValue() != 1) {
			List<String> list = tsmLeadReviewInfoMapper.findLeadIdList(tsmLoanReviewInfoDto);
			Set<String> leadIdsList = new HashSet<>();
			leadIdsList.addAll(list);
			tsmLoanReviewInfoDto.setLeadIds(leadIdsList);
		}
		if(tsmLoanReviewInfoDto.getAuditDateType() != null && tsmLoanReviewInfoDto.getAuditDateType() != 0) {
			if(tsmLoanReviewInfoDto.getAuditStatus() != null 
					&& tsmLoanReviewInfoDto.getAuditStatus() == 1) {
				rests = new ArrayList<>();
				return rests;
			}
		}
		if (tsmLoanReviewInfoDto.getIsPage() == 1) {
			rests = creditLeadInfoMapper.findReviewInfoListPage(tsmLoanReviewInfoDto);
		} else { // 特殊要求，不分页，例如导出
			rests = creditLeadInfoMapper.findReviewInfoList(tsmLoanReviewInfoDto);
		}
		return rests;
	}

	public List<String> getShowCustIds(List<TsmLoanReviewInfoDto> list) {
		List<String> custIds = new ArrayList<String>();
		for (TsmLoanReviewInfoDto dto : list) {
			custIds.add(dto.getLeadId());
		}
		return custIds;
	}

	public void multiDefinedShowChange(List<TsmLoanReviewInfoDto> list, List<String> multiList, List<String> singleList,
			String orgId) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Map<String, String>> custDataMap = new HashMap<String, Map<String, String>>();
		Map<String, Map<String, String>> codeNameMap = new HashMap<String, Map<String, String>>();
		List<TsmLeadDefinedData> definedDatas = new ArrayList<TsmLeadDefinedData>();
		Map<String, String> dataMap;
		List<OptionBean> option;
		if (multiList.size() > 0) {
			List<String> custIds = getShowCustIds(list);
			map.put("orgId", orgId);
			map.put("fieldCodes", multiList);
			map.put("leadIds", custIds);
			definedDatas = tsmLeadDefinedDataMapper.findCustDefinedShowDatas(map);
			String val;
			String oldVal;
			for (TsmLeadDefinedData definedData : definedDatas) {
				if (!codeNameMap.containsKey(definedData.getFieldCode())) {
					option = cachedService.getQupaiOptionList(definedData.getFieldCode(), orgId);
					codeNameMap.put(definedData.getFieldCode(), cachedService.changeQupaiOptionListToMap(option));
				}
				val = codeNameMap.get(definedData.getFieldCode()).get(definedData.getFieldData());
				if (val != null) {
					if (custDataMap.containsKey(definedData.getLeadId())) {
						if (custDataMap.get(definedData.getLeadId()).containsKey(definedData.getFieldCode())) {
							oldVal = custDataMap.get(definedData.getLeadId()).get(definedData.getFieldCode());
							custDataMap.get(definedData.getLeadId()).put(definedData.getFieldCode(),
									oldVal + "，" + val);
						} else {
							custDataMap.get(definedData.getLeadId()).put(definedData.getFieldCode(), val);
						}
					} else {
						dataMap = new HashMap<String, String>();
						dataMap.put(definedData.getFieldCode(), val);
						custDataMap.put(definedData.getLeadId(), dataMap);
					}
				}
			}
		}

		// 组装
		Map<String, String> valueMap;
		Class tCls;
		String setName;
		Method setMethod;
		String getName;
		Method getMethod;
		for (TsmLoanReviewInfoDto cust : list) {
			tCls = cust.getClass().getSuperclass();
			if (definedDatas.size() > 0) {
				if (custDataMap.containsKey(cust.getLeadId())) {
					valueMap = custDataMap.get(cust.getLeadId());
					for (String key : valueMap.keySet()) {
						setName = "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
						setMethod = tCls.getDeclaredMethod(setName, String.class);
						setMethod.invoke(cust, valueMap.get(key));
					}
				}
			}

			for (String singleDefined : singleList) {
				if (!codeNameMap.containsKey(singleDefined)) {
					option = cachedService.getQupaiOptionList(singleDefined, orgId);
					codeNameMap.put(singleDefined, cachedService.changeQupaiOptionListToMap(option));
				}
				getName = "get" + singleDefined.substring(0, 1).toUpperCase() + singleDefined.substring(1);
				getMethod = tCls.getDeclaredMethod(getName);
				Object definedVal = getMethod.invoke(cust);
				if (definedVal != null) {
					String definedValueName = codeNameMap.get(singleDefined).get(definedVal.toString());
					definedValueName = definedValueName == null ? "" : definedValueName;
					setName = "set" + singleDefined.substring(0, 1).toUpperCase() + singleDefined.substring(1);
					setMethod = tCls.getDeclaredMethod(setName, String.class);
					setMethod.invoke(cust, definedValueName);
				}
			}
		}
	}

	public Map<String, Object> getMultiDefinedSearchParam(TsmLoanReviewInfoDto custInfoDto, List<String> multiList)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		Class<?> clazz = custInfoDto.getClass().getSuperclass();
		List<String> fieldCodes = new ArrayList<String>();
		List<String> fieldDatas = new ArrayList<String>();
		String getName;
		String setName;
		Method getMethod;
		Method setMethod;
		for (String fieldCode : multiList) {
			getName = "get" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
			getMethod = clazz.getDeclaredMethod(getName);
			Object val = getMethod.invoke(custInfoDto);
			if (val != null && StringUtils.isNotBlank(val.toString())) {
				fieldCodes.add(fieldCode);
				fieldDatas.addAll(Arrays.asList(val.toString().split(",")));
				setName = "set" + fieldCode.substring(0, 1).toUpperCase() + fieldCode.substring(1);
				setMethod = clazz.getDeclaredMethod(setName, String.class);
				setMethod.invoke(custInfoDto, "");
			}
		}
		if (fieldCodes.size() > 0) {
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
	 * 统计
	 * 
	 * @param statisticInfoDto
	 * @return
	 */
	public void findStatistics(HttpServletRequest request, StatisticInfoDto statisticInfoDto) {
		ShiroUser user = ShiroUtil.getShiroUser(); // 当前用户
		Map<String, Object> userMap = new HashMap<>();
		userMap.put("orgId", user.getOrgId());
		userMap.put("account", user.getAccount());
		if (StringUtils.isNotBlank(statisticInfoDto.getGroupIds())) {
			if (statisticInfoDto.getGroupIds().contains(",")) {
				userMap.put("groupIds", Arrays.asList(statisticInfoDto.getGroupIds().split(",")));
			} else {
				userMap.put("groupId", statisticInfoDto.getGroupIds());
			}
		} // 根据分组id，查询所有的用户
		List<String> users = orgGroupUserService.getGroupUser(userMap);
		if (users.size() > 0) {
			statisticInfoDto.setOwnerAccs(users);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isBlank(statisticInfoDto.getNoteType())) { // 如果没传参数
			statisticInfoDto.setStartDate(sdf.format(getPastDate(7))); // 默认本周的第一天
			statisticInfoDto.setEndDate(sdf.format(new Date())); // 到当前时间
		} else if ("1".equals(statisticInfoDto.getNoteType())) {
			statisticInfoDto.setStartDate(sdf.format(getTimesWeekmorning())); // 默认本周的第一天
			statisticInfoDto.setEndDate(sdf.format(new Date())); // 到当前时间
		} else if ("2".equals(statisticInfoDto.getNoteType())) {
			statisticInfoDto.setStartDate(sdf.format(getTimesMonthmorning())); // 默认本周的第一天
			statisticInfoDto.setEndDate(sdf.format(new Date())); // 到当前时间
		}
		// 数据查询
		statisticInfoDto.setOwnerAcc(user.getAccount());
		List<StatisticInfoDto> list = creditLeadInfoMapper.findStatisticList(statisticInfoDto);
		request.setAttribute("list", list);
		request.setAttribute("item", statisticInfoDto);
		request.setAttribute("user", user);
	}

	// 获取本周的开始时间
	public static Date getTimesWeekmorning() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 0);
		int day = cal.get(Calendar.DAY_OF_WEEK);
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		return cal.getTime();
	}
	
	public static Date getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		return today;
	}

	// 获取本月的开始时间
	public static Date getTimesMonthmorning() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}

	/**
	 * 修改流程的时候，初始化审核信息
	 * 
	 * @param workflowId
	 * @param orgId
	 */
	public void initReviewInfo(String orgId) {
		if (StringUtils.isNotBlank(orgId)) {
			TsmCustWorkflowSetBean tsmCustWorkflowSet = tsmCustWorkflowSetMapper.getByOrgId(orgId);
			List<String> list = creditLeadInfoMapper.findLeadIdByStatus(orgId, 1); // 待审核
			if (tsmCustWorkflowSet.getType() == 0) { // 不需要审核
				if (list.size() > 0) {
					creditLeadInfoMapper.updateStatus(orgId, 2, list); // 直接改为通过
					tsmLeadReviewInfoMapper.deleteReviewInfo(orgId, list); // 删除审核记录
				}
			} else {
				if (list.size() > 0) {
					creditLeadInfoMapper.updateStatuing(orgId, 1, list, tsmCustWorkflowSet.getAuditNum(), tsmCustWorkflowSet.getAuditorAcc1());
					tsmLeadReviewInfoMapper.deleteReviewInfo(orgId, list); // 删除审核记录
				}
			}
		}
	}

}
