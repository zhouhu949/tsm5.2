package com.qftx.common.cached;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.http.client.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.qftx.base.auth.bean.Org;
import com.qftx.base.auth.bean.OrgGroup;
import com.qftx.base.auth.bean.OrgGroupUser;
import com.qftx.base.auth.bean.Role;
import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.AuthProductReousrceService;
import com.qftx.base.auth.service.OrgGroupService;
import com.qftx.base.auth.service.OrgGroupUserService;
import com.qftx.base.auth.service.OrgService;
import com.qftx.base.auth.service.RoleService;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.team.service.TsmGroupShareinfoService;
import com.qftx.base.util.JsonUtil;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.area.bean.ChinaCityBean;
import com.qftx.tsm.area.bean.ChinaCountyBean;
import com.qftx.tsm.area.bean.ChinaProvinceBean;
import com.qftx.tsm.area.service.AreaService;
import com.qftx.tsm.callrecord.dto.CallPlayDto;
import com.qftx.tsm.credit.service.CustFieldSetQupaiService;
import com.qftx.tsm.credit.service.CustSearchSetQupaiService;
import com.qftx.tsm.credit.service.QupaiOptionService;
import com.qftx.tsm.cust.bean.BlackListBean;
import com.qftx.tsm.cust.bean.ResourceGroupBean;
import com.qftx.tsm.cust.bean.TaoTagBean;
import com.qftx.tsm.cust.dao.ResourceGroupMapper;
import com.qftx.tsm.cust.dto.ResOptDetailDto;
import com.qftx.tsm.cust.dto.ResOptDto;
import com.qftx.tsm.cust.dto.TaoDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.log.service.LogStaffInfoService;
import com.qftx.tsm.main.bean.BarrageQueue;
import com.qftx.tsm.main.bean.CardQueue;
import com.qftx.tsm.option.bean.DataDictionaryBean;
import com.qftx.tsm.option.bean.OptionBean;
import com.qftx.tsm.option.bean.OptionGroupBean;
import com.qftx.tsm.option.dao.DataDictionaryMapper;
import com.qftx.tsm.option.dao.OptionGroupMapper;
import com.qftx.tsm.option.dao.OptionMapper;
import com.qftx.tsm.option.dto.DictionProcessJsonDto;
import com.qftx.tsm.option.dto.DictionaryWatersDto;
import com.qftx.tsm.option.service.OptionService;
import com.qftx.tsm.progress.dto.ProgressBarDTO;
import com.qftx.tsm.sms.dto.TsmMessageSendDto;
import com.qftx.tsm.sys.bean.CustFieldSet;
import com.qftx.tsm.sys.bean.CustSearchSet;
import com.qftx.tsm.sys.bean.Product;
import com.qftx.tsm.sys.bean.SysDataHelpBean;
import com.qftx.tsm.sys.dto.HighSearchDto;
import com.qftx.tsm.sys.enums.SysEnum;
import com.qftx.tsm.sys.service.CustFieldSetService;
import com.qftx.tsm.sys.service.CustSearchSetService;
import com.qftx.tsm.sys.service.HighSearchService;
import com.qftx.tsm.sys.service.ProductService;
import com.qftx.tsm.sys.service.QupaiHighSearchService;
import com.qftx.tsm.sys.service.SysDataHelpService;
import com.qftx.tsm.tao.dto.OptionDto;

import freemarker.template.utility.StringUtil;

/**
 * 公共缓存使用类，可以直接引入使用 User： bxl Date： 2015/11/19 Time： 16:39
 */
@Service
public class CachedService {
	private Logger logger = Logger.getLogger(CachedService.class);
	public static Map<String, String> LOCK_MAP = new HashMap<String, String>();
	private Lock lock = new ReentrantLock();// 锁
	// 锁
	private ReadWriteLock rwlock = new ReentrantReadWriteLock();
	@Autowired
	private OrgService orgService;
	@Autowired
	public DataDictionaryMapper dataDictionaryMapper;
	@Autowired
	public ProductService productService;
	@Autowired
	public OptionMapper optionMapper;
	@Autowired
	private CustFieldSetService custFieldSetService;
	@Autowired
	private CustFieldSetQupaiService custFieldSetQupaiService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrgGroupService orgGroupService;
	@Autowired
	private OrgGroupUserService orgGroupUserService;
	@Autowired
	private ResourceGroupMapper resourceGroupMapper;
	@Autowired
	private LogStaffInfoService logStaffInfoService;
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private CustSearchSetService custSearchSetService;
	@Autowired
	private CustSearchSetQupaiService custSearchSetQupaiService;
	@Autowired
	private HighSearchService highSearchService;
	@Autowired
	private QupaiHighSearchService qupaiHighSearchService;
	@Autowired
	private OptionService optionService;
	@Autowired
	private QupaiOptionService qupaiOptionService;
	@Autowired
	private OptionGroupMapper optionGroupMapper;
	@Autowired
	private AuthProductReousrceService reousrceService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private AreaService areaService;
	@Autowired 
	private TsmGroupShareinfoService tsmGroupShareinfoService;
	@Autowired 
	private SysDataHelpService sysDataHelpService;
	
	/**
	 * 设置定位
	 *
	 * @param orgId
	 * @param account
	 * @param
	 * @create 2015年12月31日 上午10:59:23 wuwei
	 * @history
	 */
	public void setLocation(String orgId, String account, TaoTagBean taoTagBean) {
		try {
			long domain = (DateUtil.parse(DateUtil.getOneDay(1, DateUtil.defaultPattern), DateUtil.defaultPattern).getTime() - new Date().getTime()) / 1000;
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.TAO_TAG, taoTagBean, (int) domain);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取用户组数据
	 *
	 * @param orgId
	 * @history
	 */
	public List<OrgGroup> getOrgGroup(String orgId) {
		List<OrgGroup> list = (List<OrgGroup>) CachedUtil.getInstance().get(CachedNames.ORG_GROUP + CachedNames.SEPARATOR + orgId);
		if (list == null || list.size() == 0) {
			OrgGroup item = new OrgGroup();
			item.setOrgId(orgId);
			list = orgGroupService.getListByCondtion(item);
			CachedUtil.getInstance().set(CachedNames.ORG_GROUP + CachedNames.SEPARATOR + orgId, list);
		}
		return list;
	}

	/**
	 * 获取用户组数据
	 *
	 * @param orgId
	 * @history
	 */
	public Map<String, String> getOrgGroupNameMap(String orgId) {
		Map<String, String> map = new HashMap<String, String>();
		List<OrgGroup> depts = getOrgGroup(orgId);
		if(depts != null && depts.size() > 0){
			for(OrgGroup dept : depts){
				map.put(dept.getGroupId(), dept.getGroupName());
			}
		}
		return map;
	}
	
	/**
	 * 获取用户组数据
	 *
	 * @param orgId
	 * @history
	 */
	public void delOrgGroup(String orgId) {
		CachedUtil.getInstance().delete(CachedNames.ORG_GROUP + CachedNames.SEPARATOR + orgId);

	}

	/**
	 * 获取用户组数据
	 *
	 * @param orgId
	 * @history
	 */
	public Map<String, OrgGroup> getOrgGroupMap(String orgId) {
		Map<String, OrgGroup> map = (Map<String, OrgGroup>) CachedUtil.getInstance().get(CachedNames.ORG_GROUP_MAP + CachedNames.SEPARATOR + orgId);
		if (map == null || map.size() == 0 || map.isEmpty()) {
			OrgGroup item = new OrgGroup();
			item.setOrgId(orgId);
			List<OrgGroup> list = orgGroupService.getListByCondtion(item);

			map = new HashMap<String, OrgGroup>();
			for (OrgGroup orgGroup : list) {
				map.put(orgGroup.getGroupId(), orgGroup);
			}

			CachedUtil.getInstance().set(CachedNames.ORG_GROUP_MAP + CachedNames.SEPARATOR + orgId, map);
		}
		return map;
	}

	/**
	 * 获取用户组数据
	 *
	 * @param orgId
	 * @history
	 */
	public void delOrgGroupMap(String orgId) {
		CachedUtil.getInstance().delete(CachedNames.ORG_GROUP_MAP + CachedNames.SEPARATOR + orgId);

	}

	/**
	 * 获取用户组数据
	 *
	 * @param orgId
	 * @history
	 */
	public Map<String, List<OrgGroup>> getOrgGroupPidMap(String orgId) {
		Map<String, List<OrgGroup>> map = (Map<String, List<OrgGroup>>) CachedUtil.getInstance().get(
				CachedNames.ORG_GROUP_PID_MAP + CachedNames.SEPARATOR + orgId);
		if (map == null || map.size() == 0 || map.isEmpty()) {
			OrgGroup item = new OrgGroup();
			item.setOrgId(orgId);
			List<OrgGroup> list = orgGroupService.getListByCondtion(item);
			map = new HashMap<String, List<OrgGroup>>();
			for (OrgGroup orgGroup : list) {
				String pid = orgGroup.getpId();
				if (pid != null) {
					if (map.containsKey(pid)) {
						map.get(pid).add(orgGroup);
					} else {
						List<OrgGroup> temp = new ArrayList<OrgGroup>();
						temp.add(orgGroup);
						map.put(pid, temp);
					}
				}
			}

			CachedUtil.getInstance().set(CachedNames.ORG_GROUP_PID_MAP + CachedNames.SEPARATOR + orgId, map);
		}
		return map;
	}

	/**
	 * 获取用户组数据
	 *
	 * @param orgId
	 * @history
	 */
	public void delOrgGroupPidMap(String orgId) {
		CachedUtil.getInstance().delete(CachedNames.ORG_GROUP_PID_MAP + CachedNames.SEPARATOR + orgId);

	}

	/**
	 * 获取用户组与用户关系数据
	 *
	 * @param orgId
	 * @history
	 */
	public List<OrgGroupUser> getOrgGroupMember(String orgId) {
		List<OrgGroupUser> list = (List<OrgGroupUser>) CachedUtil.getInstance().get(CachedNames.ORG_GROUP_MEMBER + CachedNames.SEPARATOR + orgId);
		if (list == null || list.size() == 0) {
			OrgGroupUser item = new OrgGroupUser();
			item.setOrgId(orgId);
			list = orgGroupUserService.getListByCondtion(item);
			CachedUtil.getInstance().set(CachedNames.ORG_GROUP_MEMBER + CachedNames.SEPARATOR + orgId, list);
		}
		return list;
	}

	/**
	 * 读取用户对应部门
	 * 
	 * @param orgId
	 * @return
	 * @create 2016年7月27日 下午2:45:45 lixing
	 * @history
	 */
	public Map<String, String> getUserGroupMap(String orgId) {
		List<OrgGroupUser> list = getOrgGroupMember(orgId);
		Map<String, String> map = new HashMap<String, String>();
		for (OrgGroupUser ogu : list) {
			map.put(ogu.getMemberAcc(), ogu.getGroupId());
		}
		return map;
	}

	/**
	 * 获取用户组与用户关系数据
	 *
	 * @param orgId
	 * @history
	 */
	public void delOrgGroupMember(String orgId) {
		CachedUtil.getInstance().delete(CachedNames.ORG_GROUP_MEMBER + CachedNames.SEPARATOR + orgId);
	}

	/**
	 * 获取缓存资源
	 *
	 * @param orgId
	 * @param account
	 * @return
	 * @create 2015年12月31日 上午10:59:06 wuwei
	 * @history
	 */
	public TaoTagBean getLoction(String orgId, String account) {
		TaoTagBean tagBean = (TaoTagBean) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.TAO_TAG);
		return tagBean;
	}

	// /**
	// * 设置缓存资源
	// *
	// * @param orgId
	// * @param account
	// * @param
	// * @create 2015年12月31日 上午10:59:23 wuwei
	// * @history
	// */
	// public void setTaoDto(String orgId, String account, TaoDto taoDto) {
	//
	// try {
	// long domain = (DateUtil.parse(DateUtil.getOneDay(1,
	// DateUtil.defaultPattern), DateUtil.defaultPattern).getTime() - new
	// Date().getTime()) / 1000;
	// CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + account +
	// CachedNames.SEPARATOR + CachedNames.TAO_LIST, taoDto, (int) domain);
	// } catch (Exception e) {
	// logger.error(e.getMessage(), e);
	// }
	// }
	//
	// /**
	// * 获取缓存资源
	// *
	// * @param orgId
	// * @param account
	// * @return
	// * @create 2015年12月31日 上午10:59:06 wuwei
	// * @history
	// */
	// public TaoDto getTaoDto(String orgId, String account) {
	// TaoDto taoDto = (TaoDto) CachedUtil.getInstance().get(orgId +
	// CachedNames.SEPARATOR + account + CachedNames.SEPARATOR +
	// CachedNames.TAO_LIST);
	// return taoDto;
	// }
	//
	// /**
	// * 清空
	// *
	// * @create 2016年1月4日 下午9:08:40 wuwei
	// * @history
	// */
	// public void removeTaoDto(String orgId, String account) {
	//
	// CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + account +
	// CachedNames.SEPARATOR + CachedNames.TAO_LIST);
	// }

	/**
	 * 设置缓存资源
	 *
	 * @param orgId
	 * @param account
	 * @param
	 * @create 2015年12月31日 上午10:59:23 wuwei
	 * @history
	 */
	public void setTaoDto(String orgId, String account, TaoDto taoDto, String pool) {
		String name = "";
		if ("1".equals(pool)) {
			name = CachedNames.TAO_LIST;
		} else {
			name = CachedNames.DELAY_POOL;
		}
		try {
			long domain = (DateUtil.parse(DateUtil.getOneDay(1, DateUtil.defaultPattern), DateUtil.defaultPattern).getTime() - new Date().getTime()) / 1000;
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + name, taoDto, (int) domain);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取缓存资源
	 *
	 * @param orgId
	 * @param account
	 * @return
	 * @create 2015年12月31日 上午10:59:06 wuwei
	 * @history
	 */
	public TaoDto getTaoDto(String orgId, String account, String pool) {
		String name = "";
		if ("1".equals(pool)) {
			name = CachedNames.TAO_LIST;
		} else {
			name = CachedNames.DELAY_POOL;
		}
		TaoDto taoDto = (TaoDto) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + name);
		return taoDto;
	}

	/**
	 * 清空
	 *
	 * @create 2016年1月4日 下午9:08:40 wuwei
	 * @history
	 */
	public void removeTaoDto(String orgId, String account, String pool) {
		String name = "";
		if ("1".equals(pool)) {
			name = CachedNames.TAO_LIST;
		} else {
			name = CachedNames.DELAY_POOL;
		}
		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + name);
	}

	/**
	 * 设置 个人资源字段 缓存
	 */
	public void setPersonFiledSet(String orgId, List<CustFieldSet> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSET, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void setPersonFiledSets(String orgId, List<CustFieldSet> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSETS, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取 个人资源字段 缓存
	 */
	public List<CustFieldSet> getPersonFiledSet(String orgId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<CustFieldSet> list = (List<CustFieldSet>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSET);
		logger.debug("获取 个人资源字段 缓存 ==" + JsonUtil.getJsonString(list));
		if (list == null || !(list.size() > 0)) {
			logger.debug("获取 个人资源字段 缓存 list == null");
			// todo 二级缓存
			CustFieldSet set = new CustFieldSet();
			set.setOrgId(orgId);
			set.setState(0);
			list = custFieldSetService.getListByCondtion(set);
			logger.debug("获取 个人资源字段 缓存 list ==" + JsonUtil.getJsonString(list));
			// 排序(升序)
			Collections.sort(list, new Comparator<CustFieldSet>() {
				public int compare(CustFieldSet pFirst, CustFieldSet pSecond) {
					Integer diff = 0;
					Integer aFirst = (int) (pFirst.getSort() == null ? -1 : pFirst.getSort());
					Integer aSecond = (int) (pSecond.getSort() == null ? -1 : pSecond.getSort());
					diff = aFirst - aSecond;
					if (diff > 0) {
						return 1;
					} else if (diff < 0) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			// 修改缓存
			setPersonFiledSet(orgId, list);
		}
		return list;
	}
	/**
	 * 获取 个人资源字段 缓存
	 */
	public List<CustFieldSet> getPersonFiledSets(String orgId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<CustFieldSet> list = (List<CustFieldSet>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.PERSON_FILEDSETS);
		logger.debug("获取 个人资源字段 缓存 ==" + JsonUtil.getJsonString(list));
		if (list == null || !(list.size() > 0)) {
			logger.debug("获取 个人资源字段 缓存 list == null");
			// todo 二级缓存
			CustFieldSet set = new CustFieldSet();
			set.setOrgId(orgId);
			set.setState(0);
			list = custFieldSetService.getListByCondtion(set);
			for(CustFieldSet custFieldSet : list) {
				if (custFieldSet.getDataType() == 4 || custFieldSet.getDataType() == 3) {
					OptionBean optionBean = new OptionBean();
					optionBean.setOrgId(user.getOrgId());		
					optionBean.setItemCode(custFieldSet.getFieldCode());
					optionBean.setOrderKey("sort asc");
					//List<OptionBean>optionList = optionService.getListPage(optionBean);
					List<OptionBean>optionList = optionService.getAllOption(optionBean);
					custFieldSet.setOptionList(optionList);
				}
			}
			logger.debug("获取 个人资源字段 缓存 list ==" + JsonUtil.getJsonString(list));
			// 排序(升序)
			Collections.sort(list, new Comparator<CustFieldSet>() {
				public int compare(CustFieldSet pFirst, CustFieldSet pSecond) {
					Integer diff = 0;
					Integer aFirst = (int) (pFirst.getSort() == null ? -1 : pFirst.getSort());
					Integer aSecond = (int) (pSecond.getSort() == null ? -1 : pSecond.getSort());
					diff = aFirst - aSecond;
					if (diff > 0) {
						return 1;
					} else if (diff < 0) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			// 修改缓存
			setPersonFiledSets(orgId, list);
		}
		return list;
	}

	/**
	 * 设置 企业资源字段 缓存
	 */
	public void setComFiledSet(String orgId, List<CustFieldSet> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.COM_FILEDSET, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void setComFiledSets(String orgId, List<CustFieldSet> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.COM_FILEDSETS, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取 企业资源字段 缓存
	 */
	public List<CustFieldSet> getComFiledSet(String orgId) {

		long start=System.currentTimeMillis();
		List<CustFieldSet> list = (List<CustFieldSet>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.COM_FILEDSET);
		logger.debug("获取 企业资源字段 缓存 ==" + JsonUtil.getJsonString(list));
		if (list == null || !(list.size() > 0)) {
			logger.debug("获取 企业资源字段 缓存 list == null");
			// todo 二级缓存
			CustFieldSet set = new CustFieldSet();
			set.setOrgId(orgId);
			set.setState(1);
			list = custFieldSetService.getListByCondtion(set);
			logger.debug("获取 企业资源字段 db list ==" + JsonUtil.getJsonString(list));
			// 修改缓存
			Collections.sort(list, new Comparator<CustFieldSet>() {
				public int compare(CustFieldSet pFirst, CustFieldSet pSecond) {
					Integer diff = 0;
					Integer aFirst = (int) (pFirst.getSort() == null ? -1 : pFirst.getSort());
					Integer aSecond = (int) (pSecond.getSort() == null ? -1 : pSecond.getSort());
					diff = aFirst - aSecond;
					if (diff > 0) {
						return 1;
					} else if (diff < 0) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			setComFiledSet(orgId, list);
		}
		logger.debug("获取 企业资源字段time="+(System.currentTimeMillis()-start));
		return list;
	}
	/**
	 * 获取 企业资源字段 缓存
	 */
	public List<CustFieldSet> getComFiledSets(String orgId) {
		ShiroUser user = ShiroUtil.getShiroUser();
		List<CustFieldSet> list = (List<CustFieldSet>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.COM_FILEDSETS);
		logger.debug("获取 企业资源字段 缓存 ==" + JsonUtil.getJsonString(list));
		if (list == null || !(list.size() > 0)) {
			logger.debug("获取 企业资源字段 缓存 list == null");
			// todo 二级缓存
			CustFieldSet set = new CustFieldSet();
			set.setOrgId(orgId);
			set.setState(1);
			list = custFieldSetService.getListByCondtion(set);
			for(CustFieldSet custFieldSet : list) {
				if (custFieldSet.getDataType() == 4 || custFieldSet.getDataType() == 3) {
					OptionBean optionBean = new OptionBean();
					optionBean.setOrgId(user.getOrgId());		
					optionBean.setItemCode(custFieldSet.getFieldCode());
					optionBean.setOrderKey("sort asc");
					//List<OptionBean>optionList = optionService.getListPage(optionBean);
					List<OptionBean>optionList = optionService.getAllOption(optionBean);
					custFieldSet.setOptionList(optionList);
				}
			}
			logger.debug("获取 企业资源字段 缓存 list ==" + JsonUtil.getJsonString(list));
			// 修改缓存
			Collections.sort(list, new Comparator<CustFieldSet>() {
				public int compare(CustFieldSet pFirst, CustFieldSet pSecond) {
					Integer diff = 0;
					Integer aFirst = (int) (pFirst.getSort() == null ? -1 : pFirst.getSort());
					Integer aSecond = (int) (pSecond.getSort() == null ? -1 : pSecond.getSort());
					diff = aFirst - aSecond;
					if (diff > 0) {
						return 1;
					} else if (diff < 0) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			setComFiledSets(orgId, list);
		}
		return list;
	}
	/**
	 * 获取 权限部门 缓存
	 */
	public List<String> getShareGroupId(Map<String,String>map) {
		String orgId= map.get("orgId");
		String account = map.get("account");
		List<String> list = (List<String>) CachedUtil.getInstance().get(orgId + account + CachedNames.SHARE_GROUP_ID);
		logger.debug("获取 权限部门 缓存 ==" + JsonUtil.getJsonString(list));
		if (list == null || !(list.size() > 0)) {
			logger.debug("获取 权限部门 缓存 list == null");
			// todo 二级缓存
			list = orgGroupUserService.getShareGroupId(map);
			logger.debug("获取 权限部门 缓存 list ==" + JsonUtil.getJsonString(list));
			// 修改缓存
			SetShareGroupId(orgId, account,list);
		}
		return list;
	}


	private void SetShareGroupId(String orgId, String account, List<String> list) {
		try {
			CachedUtil.getInstance().set(orgId + account +  CachedNames.SHARE_GROUP_ID, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 设置 联系人资源字段 缓存
	 */
	public void setContactsFiledSet(String orgId, List<CustFieldSet> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.CONTACTS_FILEDSET, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取 联系人资源字段 缓存
	 */
	public List<CustFieldSet> getContactsFiledSet(String orgId) {
		List<CustFieldSet> list = (List<CustFieldSet>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.CONTACTS_FILEDSET);
		if (list == null) {
			// todo 二级缓存
			CustFieldSet set = new CustFieldSet();
			set.setOrgId(orgId);
			set.setState(2);
			set.setOrderKey(" sort asc");
			list = custFieldSetService.getListByCondtion(set);	
			// 修改缓存
			setContactsFiledSet(orgId, list);
		}
		return list;
	}
	
	/**
	 * 设置 联系人资源字段 缓存
	 */
	public void setContactsFiledSets(String orgId, List<CustFieldSet> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.CONTACTS_FILEDSETS, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取 联系人资源字段 缓存
	 */
	public List<CustFieldSet> getContactsFiledSets(String orgId) {
		List<CustFieldSet> list = (List<CustFieldSet>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.CONTACTS_FILEDSETS);
		if (list == null) {
			// todo 二级缓存
			CustFieldSet set = new CustFieldSet();
			set.setOrgId(orgId);
			set.setState(2);
			set.setOrderKey(" sort asc");
			list = custFieldSetService.getListByCondtion(set);	
			for(CustFieldSet custFieldSet : list) {
				if (custFieldSet.getFieldCode().contains("defined")) {
					custFieldSet.setFieldCode("con"+custFieldSet.getFieldCode().substring(0,1).toUpperCase() + custFieldSet.getFieldCode().substring(1));
				}
				if (custFieldSet.getDataType() == 4 || custFieldSet.getDataType() == 3) {
					OptionBean optionBean = new OptionBean();
					optionBean.setOrgId(orgId);		
					optionBean.setItemCode("condefined" + custFieldSet.getFieldCode().substring(custFieldSet.getFieldCode().length() - 1));
					optionBean.setOrderKey("sort asc");
					//List<OptionBean>optionList = optionService.getListPage(optionBean);
					List<OptionBean>optionList = optionService.getAllOption(optionBean);
					custFieldSet.setOptionList(optionList);
				}
			}
			// 修改缓存
			setContactsFiledSets(orgId, list);
		}
		return list;
	}

	public void setOpion(String orgId, List<OptionBean> list) {
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.OPTION, list);
	}

	public List<OptionBean> getOpion(String orgId) {
		List<OptionBean> list = (List<OptionBean>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.OPTION);
		if (list == null) {
			OptionBean ob = new OptionBean();
			ob.setOrgId(orgId);
			list = optionMapper.findByCondtion(ob);
			setOpion(orgId, list);
		}
		return list;
	}

	/**
	 * 系统属性设置 销售产品维护
	 */
	public void setOpionProduct(String orgId, List<Product> list) {
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.OPTIONPRODUCT, list);
	}

	public List<Product> getOpionProduct(String orgId) {
		List<Product> list = (List<Product>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.OPTIONPRODUCT);
		if (list == null) {
			Product ob = new Product();
			ob.setOrgId(orgId);
			ob.setIsDel(0);
			list = productService.getListByCondtion(ob);
			Collections.sort(list, new Comparator<Product>() {
				public int compare(Product pFirst, Product pSecond) {
					Integer diff = 0;
					Integer aFirst = pFirst.getSort() == null ? -1 : pFirst.getSort();
					Integer aSecond = pSecond.getSort() == null ? -1 : pSecond.getSort();
					diff = aFirst - aSecond;
					if (diff > 0) {
						return 1;
					} else if (diff < 0) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			setOpionProduct(orgId, list);
		}
		return list;
	}

	/**
	 * 根据类型从缓存取得单位下拉列表
	 *
	 * @param key
	 * @param orgId
	 * @return
	 * @create 2015年11月25日 下午4:37:41 lixing
	 * @history
	 */
	public List<OptionBean> getOptionList(String key, String orgId) {
		List<OptionBean> dataList = getOpion(orgId);
		List<OptionBean> list = new ArrayList<OptionBean>();
		if (dataList != null) {
			for (OptionBean ob : dataList) {
				if (ob.getItemCode() != null && ob.getItemCode().equals(key)) {
					list.add(ob);
				}
			}
			Collections.sort(list, new Comparator<OptionBean>() {
				public int compare(OptionBean pFirst, OptionBean pSecond) {
					Integer diff = 0;
					Integer aFirst = pFirst.getSort() == null ? -1 : pFirst.getSort();
					Integer aSecond = pSecond.getSort() == null ? -1 : pSecond.getSort();
					diff = aFirst - aSecond;
					if (diff > 0) {
						return 1;
					} else if (diff < 0) {
						return -1;
					} else {
						return 0;
					}
				}
			});
		}
		return list;
	}

	/**
	 * 销售管理设置 缓存
	 */
	public void setDictionary(String orgId, List<DataDictionaryBean> dataMap) {
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.DATADICTIONARY, dataMap);
	}

	public List<DataDictionaryBean> getDictionary(String orgId) {
		List<DataDictionaryBean> list = (List<DataDictionaryBean>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.DATADICTIONARY);
		if (list == null || list.size() <= 0) {
			DataDictionaryBean ddb = new DataDictionaryBean();
			ddb.setOrgId(orgId);
			list = dataDictionaryMapper.findByCondtion(ddb);
			setDictionary(orgId, list);
		}
		return list;
	}

	/***
	 * 消息设置 缓存
	 */
	public void setMsgDictionary(String orgId, Object dataMap) {
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.OPEN_MSG_MAP, dataMap);
	}

	public Map<String, DataDictionaryBean> getMsgDictionary(String orgId) {
		return (Map<String, DataDictionaryBean>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.OPEN_MSG_MAP);
	}

	/**
	 * 根据字典编码获取单位下字典缓存
	 *
	 * @param code
	 * @param orgId
	 * @return
	 * @create 2015年11月25日 下午3:43:30 lixing
	 * @history
	 */
	public List<DataDictionaryBean> getDirList(String code, String orgId) {
		List<DataDictionaryBean> list = new ArrayList<DataDictionaryBean>();
		List<DataDictionaryBean> dataList = getDictionary(orgId);
		if (dataList != null) {
			for (DataDictionaryBean ddb : dataList) {
				if (ddb.getDictionaryCode().equals(code)) {
					list.add(ddb);
				}
			}
		}
		return list;
	}

	// 判断号码隐藏白名单
	public boolean judgeHideWhiteList(String orgId, String account) {
		try {
			List<DataDictionaryBean> list = getDirList(AppConstant.DATA_40037, orgId);
			if (!list.isEmpty() && list.get(0) != null && "1".equals(list.get(0).getIsOpen()) && StringUtils.isNotBlank(list.get(0).getDictionaryValue())) {
				String accouts = list.get(0).getDictionaryValue();
				if (accouts.contains(account + ",")) {
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("判断号码隐藏白名单 异常! 账号：" + account, e);
		}
		return false;
	}

	// 判断 只读 白名单
	public boolean judgeReadWhiteList(String orgId, String account) {
		try {
			List<DataDictionaryBean> list = getDirList(AppConstant.DATA_40038, orgId);
			if (!list.isEmpty() && list.get(0) != null && "1".equals(list.get(0).getIsOpen()) && StringUtils.isNotBlank(list.get(0).getDictionaryValue())) {
				String accouts = list.get(0).getDictionaryValue();
				if (accouts.contains(account + ",")) {
					return true;
				}
			}
		} catch (Exception e) {
			logger.error("判断 只读 白名单 异常! 账号：" + account, e);
		}
		return false;
	}

	/**
	 * 数据选项修改
	 *
	 * @param optList
	 * @param orgId
	 * @create 2015-4-16 上午10:48:39 wangchao
	 * @update zwj
	 * @history
	 */
	@SuppressWarnings("unchecked")
	public void updateOption(List<OptionBean> optList, String orgId) throws Exception {
		List<OptionBean> options = getOpion(orgId);
		options.removeAll(optList); // 先删除 选项id 相同的老数据
		options.addAll(optList);
		setOpion(orgId, options);
	}

	/**
	 * 数据选项 删除
	 *
	 * @param optList
	 * @param orgId
	 * @return
	 * @throws Exception
	 * @create 2015-12-19 下午4:55:16 zwj
	 * @history t.x
	 */
	@SuppressWarnings("unchecked")
	public List<OptionBean> deleteOption(List<OptionBean> optList, String orgId) throws Exception {
		List<OptionBean> options = getOpion(orgId);
		options.removeAll(optList); // 先删除 选项id 相同的老数据
		return options;
	}

	/**
	 * 设置缓存资源 存储 消息公告列表 用于显示 公告详情以及上下条数查询
	 */
	public void setMessageNotice(String orgId, String account, List<TsmMessageSendDto> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.USER_MESSAGE_NOTICE, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取缓存资源
	 */
	public List<TsmMessageSendDto> getMessageNotice(String orgId, String account) {
		List<TsmMessageSendDto> messageSends = (List<TsmMessageSendDto>) CachedUtil.getInstance().get(
				orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.USER_MESSAGE_NOTICE);
		return messageSends;
	}

	public void removeMessageNotice(String orgId, String account) {
		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.USER_MESSAGE_NOTICE);
	}

	/**
	 * 设置播放器列表 缓存
	 */
	public void setCallPlay(String orgId, String account, List<CallPlayDto> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.CALL_PLAY, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取 播放器列表 缓存资源
	 */
	public List<CallPlayDto> getCallPlay(String orgId, String account) {
		List<CallPlayDto> list = (List<CallPlayDto>) CachedUtil.getInstance().get(
				orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.CALL_PLAY);
		return list;
	}

	/**
	 * 清除 播放器列表 缓存资源
	 */
	public void removeCallPlay(String orgId, String account) {
		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.CALL_PLAY);
	}

	/***
	 * 设置单位帐号缓存
	 *
	 * @param orgId
	 * @param map
	 * @create 2016年3月28日 上午11:00:02 lixing
	 * @history
	 */
	public void setOrgUserNames(String orgId, Map<String, String> map) {
		try {
			Map<String, String> smap = (Map<String, String>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_NAME);
			if (smap != null) {
				map.putAll(smap);
			}
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_NAME, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/***
	 * 读取单位帐号缓存
	 *
	 * @param orgId
	 * @return
	 * @create 2016年3月28日 上午11:12:55 lixing
	 * @history
	 */
	public Map<String, String> getOrgUserNames(String orgId) {
		Map<String, String> map = (Map<String, String>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_NAME);
		if (map == null) {
			User user = new User();
			user.setOrgId(orgId);
			List<User> users = userService.getListByCondtion(user);
			map = new HashMap<String, String>();
			for (User u : users) {
				map.put(u.getUserAccount(), u.getUserName());
			}
			setOrgUserNames(orgId, map);
		}
		return map;
	}
	
	/***
	 * 设置单位帐号缓存
	 *
	 * @param orgId
	 * @param mapgetOrgUserMapByAccount
	 * @create 2016年3月28日 上午11:00:02 lixing
	 * @history
	 */
	public void setOrgUserMapById(String orgId, Map<String, User> map) {
		try {
			Map<String, User> smap = (Map<String, User>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_MAP_BY_ID);
			if (smap != null) {
				map.putAll(smap);
			}
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_MAP_BY_ID, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/***
	 * 读取单位帐号缓存
	 *
	 * @param orgId
	 * @return
	 * @create 2016年3月28日 上午11:12:55 lixing
	 * @history
	 */
	public Map<String, User> getOrgUserMapById(String orgId) {
		Map<String, User> map = (Map<String, User>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_MAP_BY_ID);
		if (map == null) {
			User user = new User();
			user.setOrgId(orgId);
			List<User> users = userService.getListByCondtion(user);
			map = new HashMap<String, User>();
			for (User u : users) {
				map.put(u.getUserId(), u);
			}
			setOrgUserMapById(orgId, map);
		}
		return map;
	}
	
	/***
	 * 设置单位帐号缓存
	 *
	 * @param orgId
	 * @param map
	 * @create 2016年3月28日 上午11:00:02 lixing
	 * @history
	 */
	public void setOrgUserMapByAccount(String orgId, Map<String, User> map) {
		try {
			Map<String, User> smap = (Map<String, User>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_MAP_BY_ACCOUNT);
			if (smap != null) {
				map.putAll(smap);
			}
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_MAP_BY_ACCOUNT, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/***
	 * 读取单位帐号缓存
	 *
	 * @param orgId
	 * @return
	 * @create 2016年3月28日 上午11:12:55 lixing
	 * @history
	 */
	public Map<String, User> getOrgUserMapByAccount(String orgId) {
		Map<String, User> map = (Map<String, User>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_MAP_BY_ACCOUNT);
		if (map == null) {
			User user = new User();
			user.setOrgId(orgId);
			List<User> users = userService.getListByCondtion(user);
			map = new HashMap<String, User>();
			for (User u : users) {
				map.put(u.getUserAccount(), u);
			}
			setOrgUserMapByUserAcount(orgId, map);
		}
		return map;
	}
	
	/***
	 * 设置单位帐号缓存
	 *
	 * @param orgId
	 * @param map
	 * @create 2016年3月28日 上午11:00:02 xiaoxh
	 * @history
	 */
	public void setOrgUserMapByUserAcount(String orgId, Map<String, User> map) {
		try {
			Map<String, User> smap = (Map<String, User>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_MAP_BY_ACCOUNT);
			if (smap != null) {
				map.putAll(smap);
			}
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_MAP_BY_ACCOUNT, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	/***
	 * 读取权限按钮缓存
	 *
	 * @param orgId
	 * @return
	 * @create 2017年7月17日 上午11:12:55 zjh
	 * @history
	 */
	public Map<String, String> getAuthUrlButton(ShiroUser user) {
		List<Role> roles = roleService.getRoleByUserIdList(user.getOrgId(),user.getId());
		Map<String, String> resultMap = (Map<String, String>) CachedUtil.getInstance().get(roles.get(0).getRoleId()+ CachedNames.AUTH_URL + CachedNames.ORG_USER_URL_BUTTON_SHIRO);
		if (resultMap == null) {
			Map<String,Object>map = new HashMap<String, Object>();
	        map.put("userId", user.getId());
	        map.put("isbackground", 0);
	        map.put("orgId", user.getOrgId());
	        map.put("proCode", user.getProCode());
	        //map.put("resourceType", "02");
	        logger.debug("query auth url button******************************");
	        List<String>ids = reousrceService.getButtRealms(map);
	        Map<String, String> shiroIdsMap = new HashMap<>();
	        for (String id : ids) {
	        	shiroIdsMap.put(id,id);
			}
	        Long date = Calendar.getInstance().getTimeInMillis();
	        String dateStamp = date.toString();
	        shiroIdsMap.put(dateStamp,dateStamp);
			setOrgUserUrlButtonShiro(roles.get(0).getRoleId(), shiroIdsMap);
			return shiroIdsMap;
		}
		return resultMap;
	}

	private void setOrgUserUrlButtonShiro(String roleId, Map<String, String> shiroIdsMap) {
		try{
			CachedUtil.getInstance().set(roleId + CachedNames.AUTH_URL + CachedNames.ORG_USER_URL_BUTTON_SHIRO, shiroIdsMap);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public List<String> getAuthUrl(ShiroUser user) {
		List<Role> roles = roleService.getRoleByUserIdList(user.getOrgId(),user.getId());
		List<String> ids = (List<String>) CachedUtil.getInstance().get(roles.get(0).getRoleId() + CachedNames.AUTH_URL + CachedNames.ORG_USER_URL_SHIRO);
		if (ids == null) {
			Map<String,Object>map = new HashMap<String, Object>();
	        map.put("userId", user.getId());
	        map.put("isbackground", 0);
	        map.put("orgId", user.getOrgId());
	        map.put("proCode", user.getProCode());
	        //map.put("resourceType", "02");
	        logger.debug("query auth url******************************");
	        ids = reousrceService.getButtRealms(map);
			setOrgUserUrlShiro(roles.get(0).getRoleId(), ids);
		}
		return ids;
	}
	
	private void setOrgUserUrlShiro(String roleId, List<String> ids) {
		try{
			CachedUtil.getInstance().set(roleId + CachedNames.AUTH_URL + CachedNames.ORG_USER_URL_SHIRO, ids);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public List<String> getAuthUrlMenu(ShiroUser user) {
		List<Role> roles = roleService.getRoleByUserIdList(user.getOrgId(),user.getId());
		List<String> ids = (List<String>) CachedUtil.getInstance().get(roles.get(0).getRoleId() + CachedNames.AUTH_URL + CachedNames.ORG_USER_URL_SHIRO_MENU);
		if (ids == null) {
			Map<String,Object>map = new HashMap<String, Object>();
	        map.put("userId", user.getId());
	        map.put("isbackground", 0);
	        map.put("orgId", user.getOrgId());
	        map.put("proCode", user.getProCode());
	        map.put("resourceType", "02");
	        logger.debug("query auth url menu******************************");
	        ids = reousrceService.getButtRealms(map);
			setOrgUserUrlShiroMenu(roles.get(0).getRoleId(), ids);
		}
		return ids;
	}
	
	private void setOrgUserUrlShiroMenu(String roleId, List<String> ids) {
		try{
			CachedUtil.getInstance().set(roleId + CachedNames.AUTH_URL + CachedNames.ORG_USER_URL_SHIRO_MENU, ids);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}


	/**
	 * 设置资源分组
	 *
	 * @param orgId
	 * @param map
	 * @create 2016年3月28日 下午4:22:31 wuwei
	 * @history
	 */
	public void setOrgResGroupNames(String orgId, Map<String, String> map) {
		try {
			Map<String, String> smap = (Map<String, String>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_NAME);
			if (smap != null) {
				for (String key : map.keySet()) {
					smap.put(key, map.get(key));
				}
			} else {
				smap = map;
			}
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_NAME, smap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void delOrgResGroupName(String groupId, String orgId) {
		Map<String, String> map = getOrgResGroupNames(orgId);
		if (map != null) {
			map.remove(groupId);
		}
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_NAME, map);
	}

	public Map<String, String> getOrgResGroupNames(String orgId) {
		Map<String, String> map = (Map<String, String>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_NAME);
		if (map == null) {
			ResourceGroupBean rgbean = new ResourceGroupBean();
			rgbean.setOrgId(orgId);
			List<ResourceGroupBean> resourceGroupBeans = resourceGroupMapper.findResGroup(orgId);
			map = new HashMap<String, String>();
			for (ResourceGroupBean u : resourceGroupBeans) {
				map.put(u.getResGroupId(), u.getGroupName());
			}
			setOrgResGroupNames(orgId, map);
		}
		return map;
	}

	/***
	 * 设置单位用户ID缓存
	 *
	 * @param orgId
	 * @param map
	 * @create 2016年3月28日 上午11:00:02 lixing
	 * @history
	 */
	public void setOrgUserNamesByID(String orgId, Map<String, String> map) {
		try {
			Map<String, String> smap = (Map<String, String>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_NAME_BY_ID);
			if (smap != null) {
				map.putAll(smap);
			}
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_NAME_BY_ID, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/***
	 * 读取单位用户ID缓存
	 *
	 * @param orgId
	 * @return
	 * @create 2016年3月28日 上午11:12:55 lixing
	 * @history
	 */
	public Map<String, String> getOrgUserNamesByID(String orgId) {
		Map<String, String> map = (Map<String, String>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.ORG_USER_NAME_BY_ID);
		if (map == null) {
			User user = new User();
			user.setOrgId(orgId);
			List<User> users = userService.getListByCondtion(user);
			map = new HashMap<String, String>();
			for (User u : users) {
				map.put(u.getUserId(), u.getUserName());
			}
			setOrgUserNamesByID(orgId, map);
		}
		return map;
	}

	/***
	 * 设置进度条缓存
	 */
	public void setProgessBar(ProgressBarDTO dto) {
		try {
			if (!CachedUtil.getInstance().orgMapLock.containsKey(dto.getOrgId())) {
				CachedUtil.getInstance().orgMapLock.put(dto.getOrgId(), dto.getOrgId());
			}

			synchronized (CachedUtil.getInstance().orgMapLock.get(dto.getOrgId())) {
				Map<String, ProgressBarDTO> smap = getProgessBar(dto.getOrgId(), dto.getAccount(), String.valueOf(dto.getType()));
				if (smap == null) {
					smap = new HashMap<String, ProgressBarDTO>();
				}

				if (smap.containsKey(dto.getId())) {
					ProgressBarDTO temp = smap.get(dto.getId());
					temp.setCurrent(temp.getCurrent() + dto.getCurrent());
					dto = temp;
				}

				smap.put(dto.getId(), dto);
				logger.info(CachedNames.PROGESS_BAR + CachedNames.SEPARATOR + dto.getOrgId() + CachedNames.SEPARATOR + dto.getAccount() + CachedNames.SEPARATOR
						+ dto.getType());
				CachedUtil.getInstance().set(
						CachedNames.PROGESS_BAR + CachedNames.SEPARATOR + dto.getOrgId() + CachedNames.SEPARATOR + dto.getAccount() + CachedNames.SEPARATOR
								+ dto.getType(), smap, 60 * 30);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/***
	 * 读取进度条缓存
	 */
	public Map<String, ProgressBarDTO> getProgessBar(String orgId, String account, String type) {
		Map<String, ProgressBarDTO> map = (Map<String, ProgressBarDTO>) CachedUtil.getInstance().get(
				CachedNames.PROGESS_BAR + CachedNames.SEPARATOR + orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + type);
		return map;

	}

	/***
	 * 设置首页今日关注缓存
	 */
	public void setTodayView(String orgId, String account, String groupIds, Map<String, Object> reMap) {
		try {
			CachedUtil.getInstance().set(
					CachedNames.TODAY_VIEW + CachedNames.SEPARATOR + orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + groupIds, reMap, 30);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/***
	 * 读取首页今日关注缓存
	 */
	public Map<String, Object> getTodayView(String orgId, String account, String groupIds) {
		Map<String, Object> map = (Map<String, Object>) CachedUtil.getInstance().get(
				CachedNames.TODAY_VIEW + CachedNames.SEPARATOR + orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + groupIds);
		return map;

	}

	public void setTomorrowPlan(String orgId, String account, Map<String, Object> reMap) {
		try {
			CachedUtil.getInstance().set(CachedNames.TOMORROW_PLAN + CachedNames.SEPARATOR + orgId + CachedNames.SEPARATOR + account, reMap, 30);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Map<String, Object> getTomorrowPlan(String orgId, String account) {
		Map<String, Object> map = (Map<String, Object>) CachedUtil.getInstance().get(
				CachedNames.TOMORROW_PLAN + CachedNames.SEPARATOR + orgId + CachedNames.SEPARATOR + account);
		return map;
	}

	public void setRanking(String orgId, String groupId, Map<String, Object> reMap) {
		try {
			CachedUtil.getInstance().set(CachedNames.MAIN_RANK + CachedNames.SEPARATOR + orgId + CachedNames.SEPARATOR + groupId, reMap, 30);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Map<String, Object> getRanking(String orgId, String groupId) {
		Map<String, Object> map = (Map<String, Object>) CachedUtil.getInstance().get(
				CachedNames.MAIN_RANK + CachedNames.SEPARATOR + orgId + CachedNames.SEPARATOR + groupId);
		return map;
	}

	public void setPlanNums(String orgId, String account, Map<String, Object> reMap) {
		try {
			CachedUtil.getInstance().set(CachedNames.MAIN_PLAN_NUMS + CachedNames.SEPARATOR + orgId + CachedNames.SEPARATOR + account, reMap, 30);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Map<String, Object> getPlanNums(String orgId, String account) {
		Map<String, Object> map = (Map<String, Object>) CachedUtil.getInstance().get(
				CachedNames.MAIN_PLAN_NUMS + CachedNames.SEPARATOR + orgId + CachedNames.SEPARATOR + account);
		return map;
	}

	/**
	 * 设置缓存资源 存储  未读消息公告列表 用于显示 公告详情以及上下条数查询
	 */
	public void setNoReadMessageNotice(String orgId, String account, List<TsmMessageSendDto> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.USER_NOREAD_MESSAGE_NOTICE, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取缓存资源，未读消息公告
	 */
	public List<TsmMessageSendDto> getNoReadMessageNotice(String orgId, String account) {
		List<TsmMessageSendDto> messageSends = (List<TsmMessageSendDto>) CachedUtil.getInstance().get(
				orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.USER_NOREAD_MESSAGE_NOTICE);
		return messageSends;
	}

	public void removeNoReadMessageNotice(String orgId, String account) {
		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.USER_NOREAD_MESSAGE_NOTICE);
	}
	
	
	
	
	
	/***
	 * 设置单位销售进程缓存
	 *
	 * @param orgId
	 * @param map
	 * @create 2016年3月28日 上午11:00:02 lixing
	 * @history
	 */
	public void setOrgSaleProcess(String orgId, Map<String, String> map) {
		try {
			Map<String, String> smap = (Map<String, String>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.ORG_SALE_PROCESS);
			if (smap != null) {
				map.putAll(smap);
			}
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.ORG_SALE_PROCESS, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/***
	 * 读取单位销售进程缓存
	 *
	 * @param orgId
	 * @return
	 * @create 2016年3月28日 上午11:12:55 lixing
	 * @history
	 */
	public Map<String, String> getOrgSaleProcess(String orgId) {
		Map<String, String> map = (Map<String, String>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.ORG_SALE_PROCESS);
		if (map == null) {
			List<OptionBean> salProgress = getOptionList(AppConstant.SALES_TYPE_ONE, orgId);
			map = new HashMap<String, String>();
			for (OptionBean sal : salProgress) {
				map.put(sal.getOptionlistId(), sal.getOptionName());
			}
			setOrgSaleProcess(orgId, map);
		}
		return map;
	}

	/***
	 * 设置单位客户类型缓存
	 *
	 * @param orgId
	 * @param map
	 * @create 2016年3月28日 上午11:00:02 lixing
	 * @history
	 */
	public void setOrgCustTypes(String orgId, Map<String, String> map) {
		try {
			Map<String, String> smap = (Map<String, String>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.ORG_CUST_TYPE);
			if (smap != null) {
				map.putAll(smap);
			}
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.ORG_CUST_TYPE, map);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/***
	 * 读取单位客户类型缓存
	 *
	 * @param orgId
	 * @return
	 * @create 2016年3月28日 上午11:12:55 lixing
	 * @history
	 */
	public Map<String, String> getOrgCustTypes(String orgId) {
		Map<String, String> map = (Map<String, String>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.ORG_CUST_TYPE);
		if (map == null) {
			List<OptionBean> custTypes = getOptionList(AppConstant.SALES_TYPE_TWO, orgId);
			map = new HashMap<String, String>();
			for (OptionBean type : custTypes) {
				map.put(type.getOptionlistId(), type.getOptionName());
			}
			setOrgCustTypes(orgId, map);
		}
		return map;
	}

	public String getName(String orgId, Map<String, String> map, String id) {
		String name = "";
		if (map != null) {
			name = map.get(id);
		}
		return name;
	}

	public Map<String, String> changeOptionListToMap(List<OptionBean> list) {
		Map<String, String> map = new HashMap<String, String>();
		for (OptionBean ob : list) {
			map.put(ob.getOptionlistId(), ob.getOptionName());
		}
		return map;
	}

	public Map<String, String> changeOptionProductListToMap(List<Product> list) {
		Map<String, String> map = new HashMap<String, String>();
		for (Product ob : list) {
			map.put(ob.getId(), ob.getName());
		}
		return map;
	}

	// 获取分配单位
	public synchronized boolean isExist(String orgId, String cachedName) {
		Map<String, ResOptDto> resOptMap = getOptMap(orgId, cachedName);
		if (resOptMap != null) {
			return true;
		} else {
			List<ResOptDetailDto> list = new ArrayList<ResOptDetailDto>();
			ResOptDto resOptDto = null;
			resOptMap = new HashMap<String, ResOptDto>();
			resOptDto = new ResOptDto();
			resOptDto.setOrgId(orgId);
			resOptDto.setIsFinished(false);
			resOptDto.setList(list);
			resOptDto.setType(1);
			resOptDto.setTotalLength(0);
			resOptDto.setOptedNum(0);
			resOptDto.setTotal(0);
			resOptMap.put(orgId, resOptDto);
			setOptMap(resOptMap, orgId, cachedName);
			return false;
		}
	}

	public Map<String, ResOptDto> getOptMap(String orgId, String cachedName) {
		Map<String, ResOptDto> map = null;
		try {
			map = (Map<String, ResOptDto>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + cachedName);
		} catch (Exception e) {
			logger.error("getOptMap 异常.orgId = " + orgId + "|cachedName=" + cachedName, e);
		}
		return map;
	}

	// 设置分配单位
	public synchronized void setOptMap(Map<String, ResOptDto> map, String orgId, String cachedName) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + cachedName, map, 60);
		} catch (Exception e) {
			logger.error("setAssignMap 异常。map =" + JSON.toJSONString(map));
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 删除
	 *
	 * @param orgId
	 * @param cacheName
	 * @history
	 */
	public synchronized void delOptMap(String reqId, String orgId, String cacheName, String account) {
		try {
			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + cacheName);
			logger.debug("reqId +" + reqId + "。orgId =" + orgId + "|cacheName=" + cacheName + "成功删除！");
			String context = "reqId +" + reqId + "。orgId =" + orgId + "|cacheName=" + cacheName + "成功删除！";
			logStaffInfoService.saveLogInfo(orgId, account, cacheName + "成功删除！", context, reqId);
		} catch (Exception e) {
			logger.error("reqId +" + reqId + "。delOptMap 异常.orgId = " + orgId + "|cacheName=" + cacheName + e.getMessage(), e);
			String context = "reqId +" + reqId + "。delOptMap 异常.orgId = " + orgId + "|cacheName=" + cacheName + e.getMessage();
			logStaffInfoService.saveLogInfo(orgId, account, cacheName + "删除异常！", context, reqId);

		}
	}

	public List<String> getPoolAuthGroupIds(String orgId, String userGroupId) {
		List<String> authGroupIds = new ArrayList<String>();
		List<DataDictionaryBean> poolDb = getDirList(AppConstant.DATA_40033, orgId);
		if (poolDb != null && poolDb.size() > 0) {
			DataDictionaryBean ddb = poolDb.get(0);
			if (ddb.getIsOpen().equals("1")) {
				String paramStr = ddb.getDictionaryValue();
				if (StringUtils.isNotBlank(paramStr)) {
					List<DictionaryWatersDto> dwdList = JsonUtil.getListJson(paramStr, DictionaryWatersDto.class);
					Map<String, List<String>> authMap = new HashMap<String, List<String>>();
					for (DictionaryWatersDto dwd : dwdList) {
						authMap.put(dwd.getGroupId(), Arrays.asList(dwd.getShareGroupIds().split(",")));
					}
					authGroupIds = authMap.get(userGroupId);
				}
			}
		}
		return authGroupIds;
	}

	/**
	 * 设置资源分组
	 *
	 * @param orgId
	 * @param map
	 * @create 2016年3月28日 下午4:22:31 wuwei
	 * @history
	 */
	public void setOrgResGroupBean(String orgId, Map<String, ResourceGroupBean> map) {
		try {
			Map<String, ResourceGroupBean> smap = (Map<String, ResourceGroupBean>) CachedUtil.getInstance().get(
					orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_BEAN);
			if (smap != null) {
				for (String key : map.keySet()) {
					smap.put(key, map.get(key));
				}
			} else {
				smap = map;
			}
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_BEAN, smap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void delOrgResGroupBean(String groupId, String orgId) {
		Map<String, ResourceGroupBean> map = getOrgResGroupBean(orgId);
		if (map != null) {
			map.remove(groupId);
		}
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_BEAN, map);
	}

	public Map<String, ResourceGroupBean> getOrgResGroupBean(String orgId) {
		Map<String, ResourceGroupBean> map = (Map<String, ResourceGroupBean>) CachedUtil.getInstance().get(
				orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_BEAN);
		if (map == null) {
			ResourceGroupBean rgbean = new ResourceGroupBean();
			rgbean.setOrgId(orgId);
			List<ResourceGroupBean> resourceGroupBeans = resourceGroupMapper.findResGroup(orgId);
			map = new HashMap<String, ResourceGroupBean>();
			for (ResourceGroupBean u : resourceGroupBeans) {
				map.put(u.getResGroupId(), u);
			}
			setOrgResGroupBean(orgId, map);
		}
		return map;
	}
	
	public List<Map<String, Object>> getResGroupList1(String orgId) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST1);
		if (list == null) {
			List<Map<String, Object>> groupList = new ArrayList<>();
			List<ResourceGroupBean> list1 = new ArrayList<>();
			Map<String, Object> map = new HashMap<>();
			list1 = resourceGroupMapper.findResGroup(orgId);
			if (list1 != null && list1.size() > 0) {
				for (ResourceGroupBean bean : list1) {
					if ("未分类".equals(bean.getGroupName()) && bean.getLevel() == 0) {
						Map<String, Object> map1 = new HashMap<>();
						map1.put("id", bean.getResGroupId());
						map1.put("text", bean.getGroupName());
						map1.put("isSharePool",bean.getIsSharePool());
						map1.put("inputAcc", bean.getInputAcc());
						map1.put("level", "0");
						map1.put("type", "1");
						map1.put("sort", 0);
						groupList.add(map1);
						break;
					}
				}
				for (ResourceGroupBean bean : list1) {
					if (!"未分类".equals(bean.getGroupName()) && bean.getLevel() == 0) {
						Map<String, Object> map1 = new HashMap<>();
						map1.put("id", bean.getResGroupId());
						map1.put("text", bean.getGroupName());
						map1.put("isSharePool",bean.getIsSharePool());
						map1.put("inputAcc", bean.getInputAcc());
						map1.put("level", "0");
						map1.put("type", "0");
						map1.put("sort", bean.getGroupIndex());
						groupList.add(map1);
					}
				}
				for(Map<String, Object> map2 : groupList){
					//List<ResourceGroupBean> childrenList = new ArrayList<>();
					List<Map<String, Object>> childrenList = new ArrayList<>();
					for (ResourceGroupBean bean : list1) {
						if (bean.getLevel() == 1 && map2.get("id").equals(bean.getPid()) && "未分组".equals(bean.getGroupName())) {
							//childrenList.add(bean);
							Map<String, Object> map1 = new HashMap<>();
							map1.put("id", bean.getResGroupId());
							map1.put("text", bean.getGroupName());
							map1.put("isSharePool",bean.getIsSharePool());
							map1.put("inputAcc", bean.getInputAcc());
							map1.put("level", "1");
							map1.put("type", "1");
							childrenList.add(map1);
						}
					}
					for (ResourceGroupBean bean : list1) {
						if (bean.getLevel() == 1 && map2.get("id").equals(bean.getPid()) && !"未分组".equals(bean.getGroupName())) {
							//childrenList.add(bean);
							Map<String, Object> map1 = new HashMap<>();
							map1.put("id", bean.getResGroupId());
							map1.put("text", bean.getGroupName());
							map1.put("isSharePool",bean.getIsSharePool());
							map1.put("inputAcc", bean.getInputAcc());
							map1.put("level", "1");
							map1.put("type", "0");
							childrenList.add(map1);
						}
					}
					//if (childrenList.size() > 0) {
						map2.put("children", childrenList);
					//}
				}
			}
			list = groupList;
		}
		setResGroupList2(orgId,list);
		return list;
	}
	
	private void setResGroupList2(String orgId, List<Map<String, Object>> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST1, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public List<ResourceGroupBean> getResGroupList(String orgId) {
		List<ResourceGroupBean> list = (List<ResourceGroupBean>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST);
		if (list == null) {
			list = resourceGroupMapper.findOnlyResGroup(orgId);
		}
		List<ResourceGroupBean> groupList = new ArrayList<ResourceGroupBean>();
			if (list != null && list.size() > 0) {
				
				for (ResourceGroupBean bean : list) {
			
					if ("未分组".equals(bean.getGroupName())&&bean.getLevel()==1) {
						groupList.add(bean);
						break;
					}
				}
				for (ResourceGroupBean bean : list) {
					if (!"未分组".equals(bean.getGroupName())&&bean.getLevel()==1) {
						groupList.add(bean);
					}
				}
			}

		setResGroupList1(orgId,groupList);
		return groupList;
	}
	
	private void setResGroupList1(String orgId, List<ResourceGroupBean> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public List<ResourceGroupBean> setResGroupList(String orgId) {
		List<ResourceGroupBean> groupList = new ArrayList<ResourceGroupBean>();
		List<ResourceGroupBean> list = resourceGroupMapper.findOnlyResGroup(orgId);
		if (list != null && list.size() > 0) {
			for (ResourceGroupBean bean : list) {
				if ("未分组".equals(bean.getGroupName())) {
					groupList.add(bean);
					break;
				}
			}
			for (ResourceGroupBean bean : list) {
				if (!"未分组".equals(bean.getGroupName())) {
					groupList.add(bean);
				}
			}
			list = groupList;
		}
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.RES_GROUP_LIST, list);
		return list;
	}

	public void setContractCode(String orgId, Map<String, Integer> map) {
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.HYX_CONTRACT_CODE, map);
	}

	/**
	 * 生成合同编号
	 * 
	 * @param orgId
	 * @return
	 * @create 2016年8月10日 上午10:01:29 lixing
	 * @history
	 */
	public String getContractCode(String orgId) {
		synchronized (orgId) {
			Object codeObj = CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.HYX_CONTRACT_CODE);
			String date = DateUtil.format(new Date(), "yyyyMMdd");
			if (codeObj == null) {
				Map<String, Integer> map = new HashMap<String, Integer>();
				map.put(date, 2);
				setContractCode(orgId, map);
				return date + "-0001";
			} else {
				Map<String, Integer> map = (Map<String, Integer>) codeObj;
				if (!map.containsKey(date)) {
					map.clear();
					map.put(date, 2);
					setContractCode(orgId, map);
					return date + "-0001";
				} else {
					Integer code = map.get(date);
					String rtn = "";
					if (code < 10) {
						rtn = date + "-000" + code;
					} else if (code >= 10 && code < 100) {
						rtn = date + "-00" + code;
					} else if (code >= 100 && code < 1000) {
						rtn = date + "-0" + code;
					} else {
						rtn = date + "-" + code;
					}
					map.put(date, code + 1);
					setContractCode(orgId, map);
					return rtn;
				}
			}
		}
	}

	public String getGroupName(String groupId, String orgId) {
		String groupName = "";
		Map<String, String> map = getOrgResGroupNames(orgId);
		if (map != null) {
			groupName = map.get(groupId);
		}
		return groupName;
	}
	
    /***
     * 获取单位缓存信息
     * @param orgId
     * @return
     */
    public Org getAuthOrgCatch(String orgId) {
    	Org bean = (Org)CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.AUTH_ORG_CAHCE);
        if (bean == null) {
        	Org entity = new Org();
        	entity.setOrgId(orgId);
        	bean = orgService.getByCondtion(entity);       
        	CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.AUTH_ORG_CAHCE, bean);
        }
        return bean;
    }
    
    // 销售管理设置获取客户销售进程
    public List<DictionProcessJsonDto>getDicProcessList(String orgId,String optionValue){
    	try{
        	List<DataDictionaryBean> dicList = getDirList(AppConstant.DATA_50001,orgId);
        	String jsonStr = "";
        	if(dicList!=null && dicList.size() >0){
        		jsonStr = dicList.get(0).getDictionaryValue();
        	}
        	if(StringUtils.isBlank(jsonStr)){
        		List<OptionBean> salProgress = getOptionList(AppConstant.SALES_TYPE_ONE, orgId);       	
            	if(salProgress!=null && salProgress.size() >0){
            		List<DictionProcessJsonDto> progress = new ArrayList<DictionProcessJsonDto>();
            		DictionProcessJsonDto dto = null;
            		for(OptionBean bean : salProgress){
            			dto = new DictionProcessJsonDto();
            			dto.setOptionId(bean.getOptionlistId());
            			dto.setOptionName(bean.getOptionName());
            			dto.setOptionValue(optionValue);
            			progress.add(dto);
            		}
            		return progress;
            	}  
            	return null;
        	} 
        	return JsonUtil.getListJson(jsonStr, DictionProcessJsonDto.class);
    	}catch(Exception e){
    		logger.error("getDicProcessList 方法异常！", e);
    		return null;
    	}   	
    }
    
    // 系统属性销售进程修改后，需要同步修改销售管理设置中DATA_50001值
    public String setDicProcessList(String orgId){
    	String jsonStr = "";
    	try{   		
        	List<DataDictionaryBean> dicList = getDirList(AppConstant.DATA_50001,orgId);        	
        	Map<String,String>map1 = new HashMap<String, String>();
        	if(dicList!=null && dicList.size() >0){
        		jsonStr = dicList.get(0).getDictionaryValue();
        		if(StringUtils.isNotBlank(jsonStr)){
        			List<DictionProcessJsonDto> jsonList = JsonUtil.getListJson(jsonStr, DictionProcessJsonDto.class);
            		if(jsonList!=null && jsonList.size() >0){       			
            			for(DictionProcessJsonDto dto : jsonList){
            				map1.put(dto.getOptionId(), dto.getOptionValue());
            			}
            		}
        		}       		
        	}
        	List<OptionBean> salProgress = getOptionList(AppConstant.SALES_TYPE_ONE, orgId);       	
        	if(salProgress!=null && salProgress.size() >0){
        		List<DictionProcessJsonDto> progress = new ArrayList<DictionProcessJsonDto>();
        		DictionProcessJsonDto dto = null;
        		for(OptionBean bean : salProgress){
        			dto = new DictionProcessJsonDto();
        			dto.setOptionId(bean.getOptionlistId());
        			if(map1.get(dto.getOptionId()) != null){
        				dto.setOptionValue(map1.get(dto.getOptionId()));
        			}
        			dto.setOptionName(bean.getOptionName());
        			progress.add(dto);
        		}
        		jsonStr = JsonUtil.getJsonString(progress);
        	}             
    	}catch(Exception e){
    		logger.error("setDicProcessList 方法异常！", e);
    	}   	
    	return jsonStr;
    }
    
    
	/**
	 * 设置投诉人缓存   
	 */
	public void setBlackCustList(String orgId, List<String> list) {
		try {
	        CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_CUST, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取投诉人缓存
	 */
	public List<String> getBlackCustList(String orgId) {
		List<String> list =  (List<String>) CachedUtil.getInstance().get(
				orgId + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_CUST);
		return list;
	}

	/**
	 * 删除投诉人缓存
	 */
	public void removeBlackCustList(String orgId) {
		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_CUST);
	}
	
	
	/**
	 * 设置企业黑名单缓存   
	 */
	public void setBlackComList(String orgId, List<String> list) {
		try {
	        CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_COMP, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取企业黑名单缓存 
	 */
	public List<String> getBlackComList(String orgId) {
		List<String> list =  (List<String>) CachedUtil.getInstance().get(
				orgId + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_COMP);
		return list;
	}

	/**
	 * 删除企业黑名单缓存 
	 */
	public void removeBlackComList(String orgId) {
		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_COMP);
	}
    
	 /***
     * 获取投诉客户名单，电话缓存
     * @param orgId
     * @return
     */
    public List<String> getBlackCustPhoneCatch(String orgId) {
    	List<String> list =new ArrayList<String>();
	    List<String> blacklist=new ArrayList<String>();
    	list =(List<String>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_CUST);
        if (list == null || list.size() <= 0) {
        	BlackListBean black=new BlackListBean();
        	black.setOrgId(orgId);
//        	black.setType("0");
        	List<String> typeList=new ArrayList<String>();
			typeList.add("0");
			typeList.add("2");
            black.setTypeList(typeList);
        	blacklist=resCustInfoService.findBlackList(black);
        	CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_CUST, blacklist);
        	
            return blacklist;
        	}else{
             return list;	
        	}
    }
    
   
    /***
     * 获取企业黑名单，电话缓存
     * @param orgId
     * @return
     */
    public  List<String> getBlackComPhoneCatch(String orgId) {
    	List<String> list =new ArrayList<String>();
	    List<String> blacklist=new ArrayList<String>();
    	list =(List<String>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_COMP);
        if (list == null || list.size() <= 0) {
        	BlackListBean black=new BlackListBean();
        	black.setOrgId(orgId);
//        	black.setType("1");
			List<String> typeList=new ArrayList<String>();
			typeList.add("1");
			typeList.add("2");
            black.setTypeList(typeList);
        	blacklist=resCustInfoService.findBlackList(black);
        	CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_COMP, blacklist);
        	return blacklist;
        	}else{
        	return list;	
        	}
        
    }
    
    
    
    /**
	 * 设置弹幕类型 、内容   
	 */
	public void setCardQueue(String orgId, List<CardQueue> list) {
		try {
	        CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_CARDQUEUE, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取弹幕类型 、内容  
	 */
	public List<CardQueue> getCardQueue(String orgId) {
		List<CardQueue> list =   (List<CardQueue>) CachedUtil.getInstance().get(
				orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_CARDQUEUE);
		return list;
	}

	/**
	 * 删除弹幕类型 、内容  
	 */
	public void removeCardQueue(String orgId) {
		CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_CARDQUEUE);
	}
	
	
	 /**
		 * 设置弹幕消息
		 */
		public void setBarrageQueue(String orgId, List<BarrageQueue> list) {
			try {
		        CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_BARRAGEQUE, list);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		/**
		 * 获取弹幕消息 
		 */
		public List<BarrageQueue> getBarrageQueue(String orgId) {
			List<BarrageQueue> list =   (List<BarrageQueue>) CachedUtil.getInstance().get(
					orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_BARRAGEQUE);
			return list;
		}

		/**
		 * 删除弹幕消息
		 */
		public void removeBarrageQueue(String orgId) {
			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_BARRAGEQUE);
		}
		
		
		 /**
			 * 设置弹幕普通消息
			 */
			public void setPtBarrageQueue(String orgId, List<BarrageQueue> list) {
				try {
			        CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_BARRAGEQUE_PT, list);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}

			/**
			 * 获取弹幕普通消息
			 */
			public List<BarrageQueue> getPtBarrageQueue(String orgId) {
				List<BarrageQueue> list =   (List<BarrageQueue>) CachedUtil.getInstance().get(
						orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_BARRAGEQUE_PT);
				return list;
			}

			/**
			 * 删除弹幕普通消息
			 */
			public void removePtBarrageQueue(String orgId) {
				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_BARRAGEQUE_PT);
			}
			
		
		
		 /**
		 * 设置签约消息
		 */
		public void setBarrageSign(String orgId, List<CardQueue> list) {
			try {
		        CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_SIGN, list);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		/**
		 * 获取签约消息 
		 */
		public List<CardQueue> getBarrageSign(String orgId) {
			List<CardQueue> list =   (List<CardQueue>) CachedUtil.getInstance().get(
					orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_SIGN);
			return list;
		}

		/**
		 * 删除签约消息
		 */
		public void removeBarrageSign(String orgId) {
			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_SIGN);
		}
		
		
		 /**
		 * 设置回款消息
		 */
		public void setBarrageSale(String orgId, List<CardQueue> list) {
			try {
		        CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_SALE, list);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		/**
		 * 获取回款消息 
		 */
		public List<CardQueue> getBarrageSale(String orgId) {
			List<CardQueue> list =   (List<CardQueue>) CachedUtil.getInstance().get(
					orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_SALE);
			return list;
		}

		/**
		 * 删除回款消息
		 */
		public void removeBarrageSale(String orgId) {
			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_SALE);
		}
		
		

		 /**
			 * 设置打赏弹幕
			 */
			public void setPayBarrage(String orgId, List<BarrageQueue> list) {
				try {
			        CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_PAYBARRAGE, list);
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}

			/**
			 * 获取打赏弹幕 
			 */
			public List<BarrageQueue> getPayBarrage(String orgId) {
				List<BarrageQueue> list =   (List<BarrageQueue>) CachedUtil.getInstance().get(
						orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_PAYBARRAGE);
				return list;
			}

			/**
			 * 删除打赏弹幕
			 */
			public void removePayBarrage(String orgId) {
				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_PAYBARRAGE);
			}
			
			/**
			 * 设置弹幕生日缓存 
			 */
			public List<CardQueue> setBirthDayQueue(String orgId, List<CardQueue> list) {
				 CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_BIRTHDAYQUEUE,list);
				return list;
			}
			
			/**
			 * 获取弹幕生日缓存 
			 */
			public List<CardQueue> getBirthDayQueue(String orgId) {
				List<CardQueue> list =   (List<CardQueue>) CachedUtil.getInstance().get(
						orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_BIRTHDAYQUEUE);
				return list;
			}

			/**
			 * 删除弹幕生日缓存 
			 *
			 */
			public void removeBirthDayQueue(String orgId) {
				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_BIRTHDAYQUEUE);
			}
			
			/**
			 * 设置排行榜回款
			 */
			public CardQueue setRangSaleCardQueue(String orgId,CardQueue cardQueue) {
				 CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_SALE,cardQueue);
				return cardQueue;
			}
			
			/**
			 * 获取排行榜回款
			 */
			public CardQueue getRangSaleCardQueue(String orgId) {
				CardQueue cardQueue =   (CardQueue) CachedUtil.getInstance().get(
						orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_SALE);
				return cardQueue;
			}

			/**
			 * 删除排行榜回款
			 *
			 */
			public void removeRangSaleCardQueue(String orgId) {
				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_SALE);
			}
			
			/**
			 * 设置排行榜签约 
			 */
			public CardQueue setRangSignCardQueue(String orgId,CardQueue cardQueue) {
				 CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_SIGN,cardQueue);
				return cardQueue;
			}
			
			/**
			 * 获取排行榜签约 
			 */
			public CardQueue getRangSignCardQueue(String orgId) {
				CardQueue cardQueue =   (CardQueue) CachedUtil.getInstance().get(
						orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_SIGN);
				return cardQueue;
			}

			/**
			 * 删除排行榜签约 
			 *
			 */
			public void removeRangSignCardQueue(String orgId) {
				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_SIGN);
			}
			
			/**
			 * 设置排行榜意向 
			 */
			public CardQueue setRangWillCardQueue(String orgId,CardQueue cardQueue) {
				 CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_will,cardQueue);
				return cardQueue;
			}
			
			/**
			 * 获取排行榜意向 
			 */
			public CardQueue getRangWillCardQueue(String orgId) {
				CardQueue cardQueue =   (CardQueue) CachedUtil.getInstance().get(
						orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_will);
				return cardQueue;
			}

			/**
			 * 删除排行榜意向  
			 *
			 */
			public void removeRangWillCardQueue(String orgId) {
				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_will);
			}
			
			/**
			 * 设置排行榜通话，呼出时长
			 */
			public CardQueue setRangCallCardQueue(String orgId,CardQueue cardQueue) {
				 CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_callTop,cardQueue);
				return cardQueue;
			}
			
			/**
			 * 获取排行榜通话
			 */
			public CardQueue getRangCallCardQueue(String orgId) {
				CardQueue cardQueue =   (CardQueue) CachedUtil.getInstance().get(
						orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_callTop);
				return cardQueue;
			}

			/**
			 * 删除排行榜通话
			 *
			 */
			public void removeRangCallCardQueue(String orgId) {
				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_callTop);
			}
			
			
			/**
			 * 设置排行榜呼出
			 */
			public CardQueue setRangCallOutCardQueue(String orgId,CardQueue cardQueue) {
				 CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_callOut,cardQueue);
				return cardQueue;
			}
			
			/**
			 * 获取排行榜呼出
			 */
			public CardQueue getRangCallOutCardQueue(String orgId) {
				CardQueue cardQueue =   (CardQueue) CachedUtil.getInstance().get(
						orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_callOut);
				return cardQueue;
			}

			/**
			 * 删除排行榜呼出
			 *
			 */
			public void removeRangCallOutCardQueue(String orgId) {
				CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_callOut);
			}
		
		
		
		/**
		 * 设置缓存资源      系统消息列表    用于显示 系统消息详情以及上下条数查询
		 */
		public void setSysNotice(String orgId, String account, List<TsmMessageSendDto> list) {
			try {
				CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.USER_MESSAGE_SYS_NOTICE, list);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		/**
		 * 获取缓存资源
		 */
		public List<TsmMessageSendDto> getSysNotice(String orgId, String account) {
			List<TsmMessageSendDto> messageSends = (List<TsmMessageSendDto>) CachedUtil.getInstance().get(
					orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.USER_MESSAGE_SYS_NOTICE);
			return messageSends;
		}

		public void removeSysNotice(String orgId, String account) {
			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.USER_MESSAGE_SYS_NOTICE);
		}

		
		/**
		 * 设置缓存资源 存储     其他第三方消息列表 用于显示 其他消息详情以及上下条数查询
		 */
		public void setOtherNotice(String orgId, String account, List<TsmMessageSendDto> list) {
			try {
				CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.USER_MESSAGE_OTHER_NOTICE, list);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		/**
		 * 获取缓存资源
		 */
		public List<TsmMessageSendDto> getOtherNotice(String orgId, String account) {
			List<TsmMessageSendDto> messageSends = (List<TsmMessageSendDto>) CachedUtil.getInstance().get(
					orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.USER_MESSAGE_OTHER_NOTICE);
			return messageSends;
		}

		public void removeOtherNotice(String orgId, String account) {
			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + account + CachedNames.SEPARATOR + CachedNames.USER_MESSAGE_OTHER_NOTICE);
		}
		

		/**
		 * 设置模块缓存
		 */
		public void setModuleList(String orgId, List<Map<String,Object>> list) {
			try {
				CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR  + CachedNames.MODULE_RESOURCE, list);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}

		/**
		 * 获取模块缓存
		 */
		public List<Map<String,Object>> getModuleList(String orgId) {
			List<Map<String,Object>> list = (List<Map<String, Object>>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.MODULE_RESOURCE);
			return list;
		}

		public void removeModuleList(String orgId, String account) {
			CachedUtil.getInstance().delete(orgId + CachedNames.SEPARATOR + CachedNames.MODULE_RESOURCE);
		}


	
   
    
	/**
	 * 设置 多选、单选类型资源字段 缓存
	 */
	public void setMultiFiledSet(String orgId, List<CustFieldSet> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.MULTI_FILEDSET, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取 多选、单选类型资源字段 缓存
	 */
	public List<CustFieldSet> getMultiFiledSet(String orgId,int state) {
		List<CustFieldSet> list = (List<CustFieldSet>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.MULTI_FILEDSET);
		if (list == null) {
			// todo 二级缓存
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", orgId);
			map.put("state", state);
			List<String>list1 = new ArrayList<String>(); // 3:单选类型，4：多选类型
			list1.add("3");
			list1.add("4");
			map.put("list", list1);
			list = custFieldSetService.getAllByDataType(map);
			for(CustFieldSet custFieldSet : list){
				if(custFieldSet.getState() == 2){
					custFieldSet.setFieldCode("con"+custFieldSet.getFieldCode());
				}
			}
			// 修改缓存
			setMultiFiledSet(orgId, list);
		}
		return list;
	}
    
	/**
	 * 设置 查询项管理
	 */
	public void setCustSearchSetList(String orgId, List<CustSearchSet> list) {
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.SEARCH_SET, list);
	}

	/**
	 * 获取启用的所有查询项字段
	 * @param orgId
	 * @return
	 */
	public List<CustSearchSet> getCustSearchSetList(String orgId) {
		List<CustSearchSet> list = (List<CustSearchSet>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.SEARCH_SET);
		if (list == null || list.size() <= 0) {
			CustSearchSet searchSet = new CustSearchSet();
			searchSet.setOrgId(orgId);
			searchSet.setEnable(1);	
			searchSet.setOrderKey("sort asc");
			list = custSearchSetService.getListByCondtion(searchSet);
			setCustSearchSetList(orgId, list);
		}
		return list;
	}

	/**
	 * 根据模块类型，获取该模块下查询项字段
	 * @param module
	 * @param orgId
	 * @return
	 */
	public List<CustSearchSet> getCustSearchSetList(String module, String orgId) {
		List<CustSearchSet> list = new ArrayList<CustSearchSet>();
		List<CustSearchSet> dataList = getCustSearchSetList(orgId);
		if (dataList != null) {
			for (CustSearchSet ddb : dataList) {
				if (ddb.getSearchModule().equals(module)) {
					list.add(ddb);
				}
			}
		}
		return list;
	}
	
	/** 获取自定义多选、单选类型字段 */
	public Map<String, String> getDefinedSearchField(String orgId,String module) {
		Map<String,String>map = new HashMap<String, String>();
		List<CustSearchSet> dataList = getCustSearchSetList(orgId);
		if (dataList != null) {
			for (CustSearchSet ddb : dataList) {
				if (ddb.getSearchModule().equals(module) 
						&& ddb.getIsFiledSet() == 1
						&& (ddb.getDataType() == 3 || ddb.getDataType() == 4)) {
					map.put(ddb.getDevelopCode(), ddb.getDevelopCode());
				}
			}
		}
		return map;
	}
	
	
	public List<String> getSingleSelectDefinedShowFiled(String orgId,String module){
		List<String> list = new ArrayList<String>();
		List<CustSearchSet> dataList = getCustSearchSetList(orgId);
		if (dataList != null) {
			for (CustSearchSet ddb : dataList) {
				if (ddb.getSearchModule().equals(module) 
						&& ddb.getIsFiledSet() == 1 
						&& ddb.getIsShow() == 1 
						&&  ddb.getDataType() == 3) {
					list.add(ddb.getDevelopCode());
				}
			}
		}
		return list;
	}
	
	/**自定义多选项查询*/
	public List<String> getMultiSelectDefinedSearchFiled(String orgId,String module){
		List<String> list = new ArrayList<String>();
		List<CustSearchSet> dataList = getCustSearchSetList(orgId);
		if (dataList != null) {
			for (CustSearchSet ddb : dataList) {
				if (ddb.getSearchModule().equals(module) 
						&& ddb.getIsFiledSet() == 1 
//						&& ddb.getIsQuery() == 1 
						&&  ddb.getDataType() == 4) {
					list.add(ddb.getDevelopCode());
				}
			}
		}
		return list;
	}
	
	/**自定义多选项展示*/
	public List<String> getMultiSelectDefinedShowFiled(String orgId,String module){
		List<String> list = new ArrayList<String>();
		List<CustSearchSet> dataList = getCustSearchSetList(orgId);
		if (dataList != null) {
			for (CustSearchSet ddb : dataList) {
				if (ddb.getSearchModule().equals(module) 
						&& ddb.getIsFiledSet() == 1 
						&& ddb.getIsShow() == 1 
						&&  ddb.getDataType() == 4) {
					list.add(ddb.getDevelopCode());
				}
			}
		}
		return list;
	}
	
	/** 获取自定义多选、单选类型对应的系统属性值 */
	public String getSearchOptionField(String orgId,String itemCode,String optionId) {
		List<OptionBean> list = getOptionList(itemCode, orgId);
		for (OptionBean sal : list) {
			if(optionId.equals(sal.getOptionlistId())){
				return sal.getOptionName();
			}		
		}
		return "";
	}
	
	// 设置高级查询
	public void setHighSearchByAccount(String module,String orgId,List<HighSearchDto>dtos,String userAccount){
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR +userAccount+ CachedNames.HIGH_SEARCH_+module, dtos);
	}
	
	// 获取高级查询缓存
	public List<HighSearchDto> getHighSearchAccount(String module,String orgId,Integer isSys,String userAccount){
		List<HighSearchDto> list = (List<HighSearchDto>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR +userAccount+ CachedNames.HIGH_SEARCH_+module);
		if(list == null || list.size() <0){
			list = highSearchService.getHighSearch(module, orgId, isSys);
			setHighSearchByAccount(module,orgId,list,userAccount);
		}
		return list;
	}
	
	public void setHighSearch(String module,String orgId,List<HighSearchDto>dtos,String issys){
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR +issys+CachedNames.HIGH_SEARCH_+module, dtos);
	}
	
	// 获取高级查询缓存
	public List<HighSearchDto> getHighSearch(String module,String orgId,Integer isSys){
		List<HighSearchDto> list = (List<HighSearchDto>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + isSys.toString() + CachedNames.HIGH_SEARCH_+module);
		if(list == null || list.size() <0){
			list = highSearchService.getHighSearch(module, orgId,isSys);
			setHighSearch(module,orgId,list,isSys.toString());
		}
		return list;
	}
	
	// 获取高级查询缓存
	@SuppressWarnings("unchecked")
	public List<HighSearchDto> getQupaiHighSearch(String module,String orgId,Integer isSys){
		List<HighSearchDto> list = (List<HighSearchDto>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + isSys.toString() + CachedNames.QUPAi_HIGH_SEARCH_+module);
		if(list == null || list.size() <0){
			list = qupaiHighSearchService.getQupaiHighSearch(module, orgId, isSys);
			setQupaiHighSearch(module,orgId,list,isSys.toString());
		}
		return list;
	}
	
	public void setQupaiHighSearch(String module,String orgId,List<HighSearchDto>dtos,String issys){
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR +issys+CachedNames.QUPAi_HIGH_SEARCH_+module, dtos);
	}
	
	public Map<Integer, String> getAreaMap(String type){
		Map<Integer, String> map = (Map<Integer, String>)CachedUtil.getInstance().get(CachedNames.AREA+CachedNames.SEPARATOR+type);
		if(map == null){
			map = new HashMap<Integer, String>();
			if(type.equals(CachedNames.PROVINCE)){
				List<ChinaProvinceBean> list = areaService.getProvinceBeans();
				for(ChinaProvinceBean cpb : list){
					map.put(cpb.getPid(), cpb.getPname());
				}
			}else if(type.equals(CachedNames.CITY)){
				List<ChinaCityBean> list = areaService.getAllCity();
				for(ChinaCityBean ccb : list){
					map.put(ccb.getCid(), ccb.getCname());
				}
			}else{
				List<ChinaCountyBean> list = areaService.getAllCounty();
				for (ChinaCountyBean ccb : list) {
					map.put(ccb.getOid(), ccb.getOname());
				}
			}
			CachedUtil.getInstance().add(CachedNames.AREA+CachedNames.SEPARATOR+type,map);
		}
		return map;
	}
	
	public static void main(String[] args) {
//		CachedUtil.getInstance().delete("hnqycw" + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_CUST);
//		CachedUtil.getInstance().delete("hnqycw" + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_COMP);
//		getBlackCustPhoneCatch("hncxh");
//		//投诉客户
//		List<String>     	list_cust =(List<String>) CachedUtil.getInstance().get("hnqycw" + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_CUST);
//		//企业黑名单
//		List<String>     	list2_com =(List<String>) CachedUtil.getInstance().get("hnqycw" + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_COMP);
//		System.out.println("BLACK_ORG_CUST:限制呼出"+list_cust);
//		System.out.println("BLACK_ORG_COMP:限制呼入"+list2_com);


//		CachedUtil.getInstance().delete("fhtx"+ CachedNames.SEPARATOR + CachedNames.SEARCH_SET);
//		CachedUtil.getInstance().delete("fhtx" + CachedNames.SEPARATOR + CachedNames.HIGH_SEARCH_+"2");
//		CachedService cc = new CachedService();
//		List<DataDictionaryBean>list = cc.getDirList(AppConstant.DATA_50019, "fhtx");
//		CachedUtil.getInstance().set("fhtxtest","test124");
//    	CardQueue c=new CardQueue();
//    	c.setContent("2222");
//    	CachedUtil.getInstance().set("fhtx" + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_SIGN,c);
//		CardQueue cardQueue =   (CardQueue) CachedUtil.getInstance().get(
//				"qftx" + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_SIGN);
//		System.out.println("恭喜"+cardQueue.getContent()+"赢得7月签约冠军");
//		CardQueue cardQueue2 =   (CardQueue) CachedUtil.getInstance().get(
//				"qftx" + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_will);
//		System.out.println("恭喜"+cardQueue2.getContent()+"赢得7月新增意向冠军");
//		CardQueue cardQueue3 =   (CardQueue) CachedUtil.getInstance().get(
//				"qftx" + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_callTop);
//		System.out.println("恭喜"+cardQueue3.getContent()+"赢得7月呼出时长冠军");
//		CardQueue cardQueue4 =   (CardQueue) CachedUtil.getInstance().get(
//				"qftx" + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_RANK_callOut);
//		System.out.println("恭喜"+cardQueue4.getContent()+"月呼出次数冠军");
		//投诉客户
//		List<String>     	list_cust =(List<String>) CachedUtil.getInstance().get("ny5" + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_CUST);
//		//企业黑名单
//		List<String>     	list2_com =(List<String>) CachedUtil.getInstance().get("ny5" + CachedNames.SEPARATOR + CachedNames.BLACK_ORG_COMP);
//		System.out.println("BLACK_ORG_CUST:限制呼出"+list_cust);
//		System.out.println("BLACK_ORG_COMP:限制呼入"+list2_com);
//		List<CardQueue> list =   (List<CardQueue>) CachedUtil.getInstance().get(
//				"shyt025" + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_SIGN);
//		System.out.println(list);
//		List<BarrageQueue> list2 =   (List<BarrageQueue>) CachedUtil.getInstance().get(
//				"shyt025" + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_BARRAGEQUE);
//		System.out.println(list2);
//		List<CardQueue> list =   (List<CardQueue>) CachedUtil.getInstance().get(
//				"shyt" + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_SIGN);
//		System.out.println(list);
//		CachedUtil.getInstance().delete("shyt" + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_BARRAGEQUE);
//		CachedUtil.getInstance().delete("shyt" + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_CARDQUEUE);
//		CachedUtil.getInstance().delete("shyt" + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_SIGN);
//		CachedUtil.getInstance().delete("shyt" + CachedNames.SEPARATOR + CachedNames.BARRAGE_ORG_PAYBARRAGE);
	}

	public List<String> getMemberAccs(String orgId, String account) {
		List<String> list = (List<String>) CachedUtil.getInstance().get(orgId + account + CachedNames.SEPARATOR + CachedNames.SHARE_MEMACC_ID);
		logger.debug("获取 权限账号 缓存 ==" + JsonUtil.getJsonString(list));
		if (list == null || !(list.size() > 0)) {
			logger.debug("获取 权限账号 缓存 list == null");
			list = tsmGroupShareinfoService.getMemberAccs(orgId,account);
			logger.debug("获取 权限账号 缓存 list ==" + JsonUtil.getJsonString(list));
			// 修改缓存
			setShareMemAccId(orgId, account,list);
		}
		return list;
	}

	private void setShareMemAccId(String orgId, String account,List<String> list) {
		try {
			CachedUtil.getInstance().set(orgId + account + CachedNames.SEPARATOR +  CachedNames.SHARE_MEMACC_ID, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public List<ResourceGroupBean> getResClassList(String orgId) {
		List<ResourceGroupBean> list = (List<ResourceGroupBean>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.RES_CLASS_LIST);
		logger.debug("获取 资源分类 缓存 ==" + JsonUtil.getJsonString(list));
		if (list == null) {
			list = resourceGroupMapper.findResClassList(orgId);
			setResClassList(orgId,list);
		}
		return list;
	}
	private void setResClassList(String orgId, List<ResourceGroupBean> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR +  CachedNames.RES_CLASS_LIST, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	// 行动标签、服务标签 层级分组
	public void setOpionGroup(String orgId, List<OptionGroupBean> list) {
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.OPTION_GROUP, list);
	}

	// 行动标签、服务标签 层级分组
	public List<OptionGroupBean> getOpionGroup(String orgId) {
		List<OptionGroupBean> list = (List<OptionGroupBean>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.OPTION_GROUP);
		if (list == null) {
			OptionGroupBean ob = new OptionGroupBean();
			ob.setOrgId(orgId);
			list = optionGroupMapper.findByCondtion(ob);
			setOpionGroup(orgId, list);
		}
		return list;
	}
	
	// 行动标签、服务标签 层级分组
	public List<OptionGroupBean> getOptionGroupList(String key, String orgId) {
		List<OptionGroupBean> dataList = getOpionGroup(orgId);
		List<OptionGroupBean> list = new ArrayList<OptionGroupBean>();
		if (dataList != null) {
			for (OptionGroupBean ob : dataList) {
				if (ob.getItemCode() != null && ob.getItemCode().equals(key)) {
					list.add(ob);
				}
			}
			Collections.sort(list, new Comparator<OptionGroupBean>() {
				public int compare(OptionGroupBean pFirst, OptionGroupBean pSecond) {
					Integer diff = 0;
					Integer aFirst = pFirst.getSort() == null ? -1 : pFirst.getSort();
					Integer aSecond = pSecond.getSort() == null ? -1 : pSecond.getSort();
					diff = aFirst - aSecond;
					if (diff > 0) {
						return 1;
					} else if (diff < 0) {
						return -1;
					} else {
						return 0;
					}
				}
			});
		}
		return list;
	}
	
	// 行动标签、服务标签 未分组
	public String getOptionDefaultGroupId(String key, String orgId) {
		List<OptionGroupBean> dataList = getOpionGroup(orgId);
		List<OptionGroupBean> list = new ArrayList<OptionGroupBean>();
		if (dataList != null) {
			for (OptionGroupBean ob : dataList) {
				if (ob.getIsDefault() == 0 && ob.getItemCode() != null && ob.getItemCode().equals(key)) {
					list.add(ob);
				}
			}
		}
		return list.get(0).getGroupId();
	}
	
	/**
	 * 设置 行动标签缓存，分层选中后的缓存
	 */
	public void setLableList(String orgId, List<OptionDto> list) {
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.LABLE_LIST, list);
	}
	
	/**
	 * 获取 行动标签缓存，分层选中后的缓存
	 */
	public List<OptionDto> getLableList(String orgId) {
		@SuppressWarnings("unchecked")
		List<OptionDto> list=(List<OptionDto>)CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.LABLE_LIST);
		return list;
	}

	public Map<String, Object> getProgress(String orgId, String taskId) {
		return (Map<String, Object>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + taskId);
	}
	
	/**
	 * 设置进度条缓存
	 */
	public void setProgress(String orgId,String taskId,Map<String, Object> map) {
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + taskId, map,30*60);
	}
	
	/***
	 * 读取帮助视频地址
	 */
	public Map<String, String> getSysHelpUrl() {
		Map<String, String> map = (Map<String, String>) CachedUtil.getInstance().get(CachedNames.SEPARATOR + CachedNames.SYS_HELP);
		if (map == null) {		
			List<SysDataHelpBean> list = sysDataHelpService.findList();
			map = new HashMap<String, String>();
			for (SysDataHelpBean sal : list) {
				map.put(sal.getDataModule(), sal.getUrl());
			}
			setSysHelpUrl(map);
		}
		return map;
	}
	
	// 设置帮助视频地址
	public void setSysHelpUrl(Map<String,String>map){
		CachedUtil.getInstance().set(CachedNames.SEPARATOR + CachedNames.SYS_HELP,map);
	}
	
	
	//**************qupai************************//
	/**
	 * 设置 qupai资源字段 缓存
	 */
	public void setQupaiFiledSet(String orgId, List<CustFieldSet> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.QUPAI_FILEDSET, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
	
	public void setQupaiFiledSets(String orgId, List<CustFieldSet> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.QUPAI_FILEDSETS, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取 qupai资源字段 缓存
	 */
	public List<CustFieldSet> getQupaiFiledSet(String orgId) {
		List<CustFieldSet> list = (List<CustFieldSet>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.QUPAI_FILEDSET);
		logger.debug("获取 qupai资源字段 缓存 ==" + JsonUtil.getJsonString(list));
		if (list == null || !(list.size() > 0)) {
			logger.debug("获取 qupai资源字段 缓存 list == null");
			// todo 二级缓存
			CustFieldSet set = new CustFieldSet();
			set.setOrgId(orgId);
//			set.setState(0);
			list = custFieldSetQupaiService.getListByCondtion(set);
			logger.debug("获取 qupai资源字段 缓存 list ==" + JsonUtil.getJsonString(list));
			// 排序(升序)
			Collections.sort(list, new Comparator<CustFieldSet>() {
				public int compare(CustFieldSet pFirst, CustFieldSet pSecond) {
					Integer diff = 0;
					Integer aFirst = (int) (pFirst.getSort() == null ? -1 : pFirst.getSort());
					Integer aSecond = (int) (pSecond.getSort() == null ? -1 : pSecond.getSort());
					diff = aFirst - aSecond;
					if (diff > 0) {
						return 1;
					} else if (diff < 0) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			// 修改缓存
			setQupaiFiledSet(orgId, list);
		}
		return list;
	}
	/**
	 * 获取 qupai资源字段 缓存
	 */
	public List<CustFieldSet> getQupaiFiledSets(String orgId) {
		List<CustFieldSet> list = (List<CustFieldSet>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.QUPAI_FILEDSETS);
		logger.debug("获取 qupai资源字段 缓存 ==" + JsonUtil.getJsonString(list));
		if (list == null || !(list.size() > 0)) {
			logger.debug("获取 qupai资源字段 缓存 list == null");
			// todo 二级缓存
			CustFieldSet set = new CustFieldSet();
			set.setOrgId(orgId);
//			set.setState(0);
			list = custFieldSetQupaiService.getListByCondtion(set);
			for(CustFieldSet custFieldSet : list) {
				if (custFieldSet.getDataType() == 4 || custFieldSet.getDataType() == 3) {
					OptionBean optionBean = new OptionBean();
					optionBean.setOrgId(orgId);		
					optionBean.setItemCode(custFieldSet.getFieldCode());
					optionBean.setOrderKey("sort asc");
					//List<OptionBean>optionList = optionService.getListPage(optionBean);
					List<OptionBean>optionList = qupaiOptionService.getAllOption(optionBean);
					custFieldSet.setOptionList(optionList);
				}
			}
			logger.debug("获取 QUpai资源字段 缓存 list ==" + JsonUtil.getJsonString(list));
			// 排序(升序)
			Collections.sort(list, new Comparator<CustFieldSet>() {
				public int compare(CustFieldSet pFirst, CustFieldSet pSecond) {
					Integer diff = 0;
					Integer aFirst = (int) (pFirst.getSort() == null ? -1 : pFirst.getSort());
					Integer aSecond = (int) (pSecond.getSort() == null ? -1 : pSecond.getSort());
					diff = aFirst - aSecond;
					if (diff > 0) {
						return 1;
					} else if (diff < 0) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			// 修改缓存
			setQupaiFiledSets(orgId, list);
		}
		return list;
	}

	
	/**
	 * 设置qupai 多选、单选类型资源字段 缓存
	 */
	public void setQupaiMultiFiledSet(String orgId, List<CustFieldSet> list) {
		try {
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.QUPAI_MULTI_FILEDSET, list);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取 qupai 多选、单选类型资源字段 缓存
	 */
	public List<CustFieldSet> getQupaiMultiFiledSet(String orgId) {
		List<CustFieldSet> list = (List<CustFieldSet>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.QUPAI_MULTI_FILEDSET);
		if (list == null) {
			// todo 二级缓存
			Map<String,Object>map = new HashMap<String, Object>();
			map.put("orgId", orgId);
//			map.put("state", state);
			List<String>list1 = new ArrayList<String>(); // 3:单选类型，4：多选类型
			list1.add("3");
			list1.add("4");
			map.put("list", list1);
			list = custFieldSetQupaiService.getAllByDataType(map);
			for(CustFieldSet custFieldSet : list){
				if(custFieldSet.getState() == 2){
					custFieldSet.setFieldCode("con"+custFieldSet.getFieldCode());
				}
			}
			// 修改缓存
			setQupaiMultiFiledSet(orgId, list);
		}
		return list;
	}
	
	/**
	 * 设置 查询项管理
	 */
	public void setQupaiCustSearchSetList(String orgId, List<CustSearchSet> list) {
		CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.QUPAI_SEARCH_SET, list);
	}

	/**
	 * 获取启用的所有查询项字段
	 * @param orgId
	 * @return
	 */
	public List<CustSearchSet> getQupaiCustSearchSetList(String orgId) {
		List<CustSearchSet> list = (List<CustSearchSet>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.QUPAI_SEARCH_SET);
		if (list == null || list.size() <= 0) {
			CustSearchSet searchSet = new CustSearchSet();
			searchSet.setOrgId(orgId);
			searchSet.setEnable(1);	
			searchSet.setOrderKey("sort asc");
			list = custSearchSetQupaiService.getListByCondtion(searchSet);
			setQupaiCustSearchSetList(orgId, list);
		}
		return list;
	}
	
	public List<String> getLeadSelectDefinedSearchFiled(String orgId,String module){
		List<String> list = new ArrayList<String>();
		List<CustSearchSet> dataList = getQupaiCustSearchSetList(orgId);
		if (dataList != null) {
			for (CustSearchSet ddb : dataList) {
				if (ddb.getSearchModule().equals(module) 
						&& ddb.getIsFiledSet() == 1 
//						&& ddb.getIsQuery() == 1 
						&&  ddb.getDataType() == 4) {
					list.add(ddb.getDevelopCode());
				}
			}
		}
		return list;
	}
	
	/**取派自定义多选项展示*/
	public List<String> getLeadSelectDefinedShowFiled(String orgId,String module){
		List<String> list = new ArrayList<String>();
		List<CustSearchSet> dataList = getQupaiCustSearchSetList(orgId);
		if (dataList != null) {
			for (CustSearchSet ddb : dataList) {
				if (ddb.getSearchModule().equals(module) 
						&& ddb.getIsFiledSet() == 1 
						&& ddb.getIsShow() == 1 
						&&  ddb.getDataType() == 4) {
					list.add(ddb.getDevelopCode());
				}
			}
		}
		return list;
	}
	
	public List<String> getLeadSingleDefinedShowFiled(String orgId,String module){
		List<String> list = new ArrayList<String>();
		List<CustSearchSet> dataList = getQupaiCustSearchSetList(orgId);
		if (dataList != null) {
			for (CustSearchSet ddb : dataList) {
				if (ddb.getSearchModule().equals(module) 
						&& ddb.getIsFiledSet() == 1 
						&& ddb.getIsShow() == 1 
						&&  ddb.getDataType() == 3) {
					list.add(ddb.getDevelopCode());
				}
			}
		}
		return list;
	}

	public List<OptionBean> getQupaiOptionList(String fieldCode, String orgId) {
		List<OptionBean> dataList = getQupaiOpion(orgId);
		List<OptionBean> list = new ArrayList<OptionBean>();
		if (dataList != null) {
			for (OptionBean ob : dataList) {
				if (ob.getItemCode() != null && ob.getItemCode().equals(fieldCode)) {
					list.add(ob);
				}
			}
			Collections.sort(list, new Comparator<OptionBean>() {
				public int compare(OptionBean pFirst, OptionBean pSecond) {
					Integer diff = 0;
					Integer aFirst = pFirst.getSort() == null ? -1 : pFirst.getSort();
					Integer aSecond = pSecond.getSort() == null ? -1 : pSecond.getSort();
					diff = aFirst - aSecond;
					if (diff > 0) {
						return 1;
					} else if (diff < 0) {
						return -1;
					} else {
						return 0;
					}
				}
			});
		}
		return list;
	}
	
	public List<OptionBean> getQupaiOpion(String orgId) {
		List<OptionBean> list = (List<OptionBean>) CachedUtil.getInstance().get(orgId + CachedNames.SEPARATOR + CachedNames.QUPAI_OPTION);
		if (list == null) {
			OptionBean optionBean = new OptionBean();
			optionBean.setOrgId(orgId);		
			list = qupaiOptionService.getListByCondtion(optionBean);
			CachedUtil.getInstance().set(orgId + CachedNames.SEPARATOR + CachedNames.QUPAI_OPTION, list);
		}
		return list;
	}
	
	public Map<String, String> changeQupaiOptionListToMap(List<OptionBean> list) {
		Map<String, String> map = new HashMap<String, String>();
		for (OptionBean ob : list) {
			map.put(ob.getOptionlistId(), ob.getOptionName());
		}
		return map;
	}
	
	
	/**
	 * 放款管理 - 获取启用字段列表
	 * 
	 * @param orgId
	 * @return
	 */
	public List<CustFieldSet> getQupaiFieldSet(String orgId) {
		List<CustFieldSet> enabledFeilds = new ArrayList<CustFieldSet>();
		List<CustFieldSet> fieldSets = getQupaiFiledSets(orgId);	//getQupaiFiledSet(orgId);
		
		for(CustFieldSet fieldSet : fieldSets){
			
			// TODO 为何过滤掉图片类型
			if(fieldSet.getEnable() == CustFieldSet.FIELD_ENABLED /*&& fieldSet.getDataType() != CustFieldSet.DATATYPE_PICTURE*/){
				enabledFeilds.add(fieldSet);
			}
		}
		
		return enabledFeilds;
	}
	
	/**
	 * 根据模块进行过滤
	 * 
	 * @param list
	 * @param searchModule
	 * @return
	 */
	public static List<CustSearchSet> filterByModule(List<CustSearchSet> list, String searchModule) {
		List<CustSearchSet> resultList = new ArrayList<CustSearchSet>();
		for (CustSearchSet set : list) {
			if (null != set && searchModule.equals(set.getSearchModule())) {
				resultList.add(set);
			}
		}
		
		return resultList;
	}
	
	/**自定义多选项查询*/
	public List<String> getQupaiMultiSelectDefinedSearchFiled(String orgId,String module){
		List<String> list = new ArrayList<String>();
		List<CustSearchSet> dataList = getQupaiCustSearchSetList(orgId);
		dataList = filterByModule(dataList, module);
		
		if (dataList != null) {
			for (CustSearchSet ddb : dataList) {
				if (ddb.getSearchModule().equals(module) 
						&& ddb.getIsFiledSet() == 1 
//						&& ddb.getIsQuery() == 1 
						&&  ddb.getDataType() == 4) {
					list.add(ddb.getDevelopCode());
				}
			}
		}
		return list;
	}
	
	/**自定义多选项展示*/
	public List<String> getQupaiMultiSelectDefinedShowFiled(String orgId,String module){
		List<String> list = new ArrayList<String>();
		List<CustSearchSet> dataList = getQupaiCustSearchSetList(orgId);
		dataList = filterByModule(dataList, module);
		if (dataList != null) {
			for (CustSearchSet ddb : dataList) {
				if (ddb.getSearchModule().equals(module) 
						&& ddb.getIsFiledSet() == 1 
						&& ddb.getIsShow() == 1 
						&&  ddb.getDataType() == 4) {
					list.add(ddb.getDevelopCode());
				}
			}
		}
		return list;
	}
	
	/**自定义单选项展示*/
	public List<String> getQupaiSingleSelectDefinedShowFiled(String orgId,String module){
		List<String> list = new ArrayList<String>();
		List<CustSearchSet> dataList = getQupaiCustSearchSetList(orgId);
		if (dataList != null) {
			for (CustSearchSet ddb : dataList) {
				if (ddb.getSearchModule().equals(module) 
						&& ddb.getIsFiledSet() == 1 
						&& ddb.getIsShow() == 1 
						&&  ddb.getDataType() == 3) {
					list.add(ddb.getDevelopCode());
				}
			}
		}
		return list;
	}
	
	/**
	 * 放款管理页面需隐藏字段列表
	 * 
	 * @param orgId
	 * @param isState
	 * @param showModult
	 * @param leftCos
	 * @return
	 */
	public List<Integer> getQupaiMoneySortListCode(String module, String orgId, String isState, List<String[]> showModult, int leftCos) {
		List<CustFieldSet> list = getQupaiFieldSet(orgId);
		
		Map<String, String> map = new HashMap<String, String>();
		for (CustFieldSet filed : list) {
			if (filed.getDataType() == 5 && filed.getFieldCode().contains("defined")) {
				map.put(filed.getFieldCode(), filed.getFieldCode());
			} else if("borrowMoney".equals(filed.getFieldCode())) {
				map.put(filed.getFieldCode(), filed.getFieldCode());
			}else if("accountMoney".equals(filed.getFieldCode())) {
				map.put(filed.getFieldCode(), filed.getFieldCode());
			}else if("serviceMoney".equals(filed.getFieldCode())) {
				map.put(filed.getFieldCode(), filed.getFieldCode());
			}else if("billMoney".equals(filed.getFieldCode())) {
				map.put(filed.getFieldCode(), filed.getFieldCode());
			}else if("serviceMoney2".equals(filed.getFieldCode())) {
				map.put(filed.getFieldCode(), filed.getFieldCode());
			}else if("serviceMoney3".equals(filed.getFieldCode())) {
				map.put(filed.getFieldCode(), filed.getFieldCode());
			}
		}
		List<Integer> rList = new ArrayList<Integer>();
		for (String[] strs : showModult) {
			if (map.get(strs[0]) == null) {
				rList.add(Integer.parseInt(strs[1]) + leftCos); // 存储需要隐藏的排序值 .
																// 因为第一列是复选框、第二列是操作列，所以加了2
			}
		}
		return rList;
	}
	
	/**
	 * 放款管理页面需隐藏字段列表
	 * 
	 * @param orgId
	 * @param isState
	 * @param showModult
	 * @param leftCos
	 * @return
	 */
	public List<Integer> getQupaiHideSortListCode(String module, String orgId, String isState, List<String[]> showModult, int leftCos) {
		List<CustSearchSet> list = getQupaiCustSearchSetList(orgId);
		list = filterByModule(list, module);
		
		Map<String, String> map = new HashMap<String, String>();
		for (CustSearchSet filed : list) {
			if (filed.getIsShow() == CustSearchSet.IS_SHOW_YES) {
				map.put(filed.getDevelopCode(), filed.getDevelopCode());
			}
		}
		List<Integer> rList = new ArrayList<Integer>();
		for (String[] strs : showModult) {
			if (("2".equals(strs[2]) || isState.equals(strs[2])) && map.get(strs[0]) == null) {
				rList.add(Integer.parseInt(strs[1]) + leftCos); // 存储需要隐藏的排序值 .
																// 因为第一列是复选框、第二列是操作列，所以加了2
			}
		}
		return rList;
	}
	
	/**
	 * 放款管理 - 文本查询字段
	 * 
	 * @param orgId
	 * @return
	 */
	public List<CustSearchSet> getQupaiIsTextQueryList(String orgId, String module) {
		List<CustSearchSet> list = getQupaiCustSearchSetList(orgId);
		list = filterByModule(list, module);
		List<CustSearchSet> rlist = new ArrayList<CustSearchSet>();
		for (CustSearchSet filed : list) {
			if (filed.getIsQuery() == CustSearchSet.IS_QUERY_YES && (filed.getDataType() == 1 || "cardId".equals(filed.getDevelopCode()))) {
				rlist.add(filed);
			}
		}
		return rlist;
	}

	/**
	 * 放款管理 - 查询日期类型、单选类型、多选类型作为下拉查询项
	 * 
	 * @param orgId
	 * @return
	 */
	public Map<String, Integer> getQupaiUnTestSearchSort(String orgId, String module) {
		List<CustSearchSet> list = getQupaiCustSearchSetList(orgId);
		list = filterByModule(list, module);
		Map<String, Integer> map = new HashMap<String, Integer>();
		int i = 0;
		for (CustSearchSet filed : list) {
			String dataType = Integer.toString(filed.getDataType());
			if (filed.getIsQuery() == 1 && (SysEnum.FIELD_DATATYPE_DATA.getState().equals(dataType)
					|| SysEnum.FIELD_DATATYPE_RADIO.getState().equals(dataType)
					|| SysEnum.FIELD_DATATYPE_CHECK.getState().equals(dataType) 
					|| "ownerAcc".equals(filed.getDevelopCode())
					|| "inchargeAcc".equals(filed.getDevelopCode() ))) {
				i++;
				map.put(filed.getSearchCode(), i);
			}
		}
		return map;
	}
	
	
	/**
	 * 放款管理 - 查询日期类型、单选类型、多选类型作为下拉查询项
	 * 
	 * @param orgId
	 * @return
	 */
	public Map<String, Integer> getQupaiUnTestSearchSort(String orgId, Integer isSys, String module) {
		List<CustSearchSet> list = getQupaiCustSearchSetList(orgId);
		list = filterByModule(list, module);
		Map<String, Integer> map = new HashMap<String, Integer>();
		int i = 0;
		for (CustSearchSet filed : list) {
			String dataType = Integer.toString(filed.getDataType());
			
			if ( 1==isSys ) {
				// 管理员
				if (filed.getIsQuery() == 1 && (SysEnum.FIELD_DATATYPE_DATA.getState().equals(dataType)
						|| SysEnum.FIELD_DATATYPE_RADIO.getState().equals(dataType)
						|| SysEnum.FIELD_DATATYPE_CHECK.getState().equals(dataType) 
						|| "ownerAcc".equals(filed.getDevelopCode())
						|| "inchargeAcc".equals(filed.getDevelopCode() ))) {
					i++;
					map.put(filed.getSearchCode(), i);
				}
			} else {
				// 普通销售
				if (filed.getIsQuery() == 1 && (SysEnum.FIELD_DATATYPE_DATA.getState().equals(dataType)
						|| SysEnum.FIELD_DATATYPE_RADIO.getState().equals(dataType)
						|| SysEnum.FIELD_DATATYPE_CHECK.getState().equals(dataType) 
						)) {
					i++;
					map.put(filed.getSearchCode(), i);
				}
			}
		}
		return map;
	}
	
	/**
	 * 放款管理页面需显示字段列表
	 * 
	 * @param orgId
	 * @param isState
	 * @param showModult
	 * @param leftCos
	 * @return
	 */
	public List<CustSearchSet> getQupaiShowCustSearchSet(String module, String orgId) {
		List<CustSearchSet> rList = new ArrayList<CustSearchSet>();
		
		List<CustSearchSet> list = getQupaiCustSearchSetList(orgId);
		list = filterByModule(list, module);
		
		for (CustSearchSet filed : list) {
			if (filed.getIsShow() == CustSearchSet.IS_SHOW_YES) {
				rList.add(filed);
			}
		}
		
		return rList;
	}
	
	/**
	 * 获取 机构 默认放款编号
	 */
	public String getLeadCode(String orgId) {
		String dateStr = DateUtils.formatDate(new Date(), "yyyyMMdd");
		String prefixKey = String.format("%s_%s", orgId, dateStr);
		Long index = CachedUtil.getInstance().incr(prefixKey + CachedNames.SEPARATOR + CachedNames.QUPAI_LEADCODE);
		String result = dateStr + StringUtil.leftPad(index.toString(), 4, "0");
		logger.debug("获取 机构 默认放款编号 ==" + result);
		return result;
	}


}
