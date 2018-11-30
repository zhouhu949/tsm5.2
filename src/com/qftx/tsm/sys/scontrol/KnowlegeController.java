package com.qftx.tsm.sys.scontrol;

import com.qftx.base.shiro.ShiroUser;
import com.qftx.base.shiro.ShiroUtil;
import com.qftx.base.util.GuidUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.StringUtils;
import com.qftx.common.util.constants.AppConstant;
import com.qftx.tsm.log.service.LogUserOperateService;
import com.qftx.tsm.sys.bean.Knowlege;
import com.qftx.tsm.sys.bean.KnowlegeGroup;
import com.qftx.tsm.sys.dto.KnowlegeDto;
import com.qftx.tsm.sys.dto.KnowlegeGroupDto;
import com.qftx.tsm.sys.service.KnowlegeGroupService;
import com.qftx.tsm.sys.service.KnowlegeService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 知识库控制类
 *
 * @author: wuwei
 * @since: 2015年11月11日 下午2:01:48
 * @history:
 */
@Controller
@RequestMapping("/sys/knowlege/")
public class KnowlegeController {
	private Logger logger = Logger.getLogger(KnowlegeController.class);
	@Autowired
	private KnowlegeService knowlegeService;
	@Autowired
	private KnowlegeGroupService knowlegeGroupService;
	@Autowired
	private LogUserOperateService logUserOperateService;

	/**
	 * 前台知识列表
	 * 
	 * @param request
	 * @param knowlegeDto
	 * @param groupId
	 * @return
	 * @create 2015年11月13日 下午5:09:39 wuwei
	 * @history
	 */
	 @RequestMapping(value = "list")
	 public String list(HttpServletRequest request, KnowlegeDto knowlegeDto,String groupId) {
		 getKnowlegeList(request, knowlegeDto, groupId);
		 return "unit/sys/saleTemplate";
	 }

	/**
	 * 后台知识列表
	 * 
	 * @param request
	 * @param knowlegeDto
	 * @param groupId
	 * @return
	 * @create 2015年11月13日 下午5:08:58 wuwei
	 * @history
	 */
	@RequestMapping(value = "knowlegeMgList")
	public String knowlegeMgList(HttpServletRequest request, KnowlegeDto knowlegeDto, String groupId) {
		try {
			getKnowlegeList(request, knowlegeDto, groupId);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return "unit/sys/knowlege_list";
	}

	/**
	 * 设置参数
	 * 
	 * @param request
	 * @param knowlegeDto
	 * @param user
	 * @param groupId
	 * @create 2015年11月13日 下午5:10:08 wuwei
	 * @history
	 */
	public void getKnowlegeList(HttpServletRequest request, KnowlegeDto knowlegeDto, String groupId) {
		Map<String, String> map = new HashMap<String, String>();
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			//校验数据，是否有排序值。没有即赋初始值
			getKnowlegeList();
			
			map.put("orgId", user.getOrgId());
			// 获取知识分组和组员数
			List<KnowlegeGroupDto> knowlegeGroupList = knowlegeGroupService.findKnowlegeGroup_new(map);
			// 未分组数
			int unGroup = knowlegeGroupService.getKnowlegeUnGroup(map);
			// 所有知识总数
			int allKnowlege = knowlegeGroupService.getAllAKnowleges(map);
			// 查询知识列表
			knowlegeDto.setOrgId(user.getOrgId());
			if ("0".equals(groupId)) { // 0未分组
				knowlegeDto.setIsUnGroup("1");
				knowlegeDto.setGroupId("");
			} else
				knowlegeDto.setGroupId(groupId);
			// 查询知识列表
			List<Knowlege> knowlegeList = knowlegeService.getListPage_new(knowlegeDto);
			request.setAttribute("knowlegeDto", knowlegeDto);
			request.setAttribute("knowlegeList", knowlegeList);
			request.setAttribute("knowlegeGroupList", knowlegeGroupList);
			request.setAttribute("unGroup", unGroup);
			request.setAttribute("allKnowlege", allKnowlege);
			request.setAttribute("groupId", groupId);
		} catch (Exception e) {
			throw new SysRunException(e);
		}
	}
	
	/**
	 * 初始化模板数据，未添加排序的按照原来顺序添加排序
	 * 
	 * @param request
	 * @param knowlegeDto
	 * @param user
	 * @param groupId
	 * @create 2017年6月8日 下午15:10:08 xiaoxh
	 * @history
	 */
	public void getKnowlegeList() {
		Map<String, String> map = new HashMap<String, String>();
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			map.put("orgId", user.getOrgId());
	        map.put("groupId", "");
			List<KnowlegeGroup> knowlegeGrouplist =new ArrayList<KnowlegeGroup>();
			// 获取知识分组和组员数"findKnowlegeByGroupId"
			List<KnowlegeGroupDto> knowlegeGroupList = knowlegeGroupService.queryKnowlegeGroup(map);
			if(knowlegeGroupList !=null && knowlegeGroupList.size()>0){
				if(knowlegeGroupList.get(0).getGroupSort() ==null){
				for(int i=0;i<knowlegeGroupList.size();i++){
					KnowlegeGroup kg=new KnowlegeGroup();
					kg.setGroupId(knowlegeGroupList.get(i).getGroupId());
					kg.setOrgId(user.getOrgId());
					kg.setGroupSort(i+1);
					knowlegeGrouplist.add(kg);
				}
                //将分组groupsort赋值跟新
				knowlegeGroupService.modifyBatch_new(knowlegeGrouplist);;
			}
			}
			
			KnowlegeDto	 knowlegeDto =new KnowlegeDto();
			// 查询知识列表,全部
			knowlegeDto.setOrgId(user.getOrgId());
			knowlegeDto.setIsUnGroup("");
			// 查询知识列表
			List<Knowlege> knowlegeList = knowlegeService.getListPage(knowlegeDto);
			if(knowlegeList !=null && knowlegeList.size()>0){
				if(knowlegeList.get(0).getSort() ==null){
				for(int j=0;j<knowlegeList.size();j++){
					knowlegeList.get(j).setSort(j+1);
				}

				//给sort初始值
				knowlegeService.modifyBatch_new(knowlegeList);
			}
			}

		} catch (Exception e) {
			throw new SysRunException(e);
		}
	}

	/**
	 * 跳转到添加或修改页面
	 * 
	 * @return
	 * @create 2013-11-7 上午10:49:31 wuwei
	 * @history
	 */
	@RequestMapping("toSaveOrUpdate")
	public String toSaveOrUpdate(HttpServletRequest request, String knowlegeId, String groupId) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			KnowlegeGroup knowlegeGroup = new KnowlegeGroup();
			knowlegeGroup.setOrgId(user.getOrgId());
			List<KnowlegeGroup> knowlegeGroups = knowlegeGroupService.getGrpListByGrpName(knowlegeGroup);
			request.setAttribute("knowlegeGroups", knowlegeGroups);
			knowlegeGroup = knowlegeGroupService.getByPrimaryKey(groupId);
			request.setAttribute("knowlegeGroup", knowlegeGroup);
			request.setAttribute("groupId", groupId);
			if(knowlegeGroup!=null){
			request.setAttribute("groupName", knowlegeGroup.getGroupName());
			}else{
				request.setAttribute("groupName", "请选择");	
			}
			Knowlege knowlege = new Knowlege();
			if (StringUtils.isNotEmpty(knowlegeId)) {
				knowlege = knowlegeService.getKnowlegeById(user.getOrgId(),knowlegeId);
				request.setAttribute("knowlege", knowlege);
				return "unit/sys/knowlege_update";
			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "unit/sys/knowlege_add";
	}

	/**
	 * 保存或更新知识
	 * 
	 * @param request
	 * @param knowlegeId
	 * @return
	 * @create 2015年11月13日 下午7:21:12 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "saveOrUpdate")
	public String saveOrUpdate(HttpServletRequest request, Knowlege knowlege) {
		try {
			ShiroUser user = ShiroUtil.getShiroUser();
			// 2.新增或编辑操作
			if (StringUtils.isBlank(knowlege.getKnowlegeId())) {// ID为空时新增记录，否则为编辑记录
				String id = GuidUtil.getId();
				knowlege.setOrgId(user.getOrgId());
				knowlege.setInputerAcc(user.getAccount());
				knowlege.setInputdate(new Date());
				knowlege.setIsDel(new Short("0"));
				knowlege.setKnowlegeId(id);
				knowlegeService.create(knowlege);
				Map<String, String> map=new HashMap<String, String>();
				map.put("orgId", user.getOrgId());
				map.put("groupId", knowlege.getGroupId());
				KnowlegeGroup knowlegeGroup=knowlegeGroupService.getByPrimaryKey(knowlege.getGroupId());
				logUserOperateService.setUserOperateLog(AppConstant.Module_id110 ,AppConstant.Module_Name110 ,AppConstant.Operate_id2 ,AppConstant.Operate_Name2 ,"新增  "+knowlege.getQuestion()+"  至  "+knowlegeGroup.getGroupName(),"" );
			} else {
				knowlege.setOrgId(user.getOrgId());
				knowlege.setModifydate(new Date());
				knowlege.setModifierAcc(user.getAccount());
				knowlege.setIsDel(new Short("0"));
				knowlegeService.modify(knowlege);
				logUserOperateService.setUserOperateLog(AppConstant.Module_id110 ,AppConstant.Module_Name110 ,AppConstant.Operate_id67 ,AppConstant.Operate_Name67 ,knowlege.getQuestion(),"" );
			}
		} catch (Exception e) {
			logger.error("loginName=" + ShiroUtil.getUser().getName() + "保存或修改异常!"+e.getMessage(),e);
			return AppConstant.RESULT_EXCEPTION;
		}
		return AppConstant.RESULT_SUCCESS;
	}

	/**
	 * 删除知识
	 * 
	 * @param request
	 * @param ids
	 * @return
	 * @create 2015年11月13日 下午8:18:06 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping(value = "knowlege_del")
	public String knowlege_del(HttpServletRequest request, String ids, RedirectAttributes attr) {
		ShiroUser user = ShiroUtil.getShiroUser();
		try {
			String[] idsArray = ids.split("\\,");
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("ids", idsArray);
			map.put("updateacc", user.getAccount());
			KnowlegeDto knowleges=new KnowlegeDto();
			knowleges.setOrgId(user.getOrgId());
			Knowlege knowlege =new Knowlege();
			List<Knowlege> knowlegeList = knowlegeService.getListPage_new(knowleges);
			knowlegeService.removeKnowlegeBatch(map);
			String knowlegeNames="";
			if(knowlegeList !=null &&knowlegeList.size()>0){
			for(int i=0;i<idsArray.length;i++){
		    for(Knowlege know:knowlegeList ){
				if(know.getKnowlegeId()==idsArray[i] || idsArray[i].equals(know.getKnowlegeId())){
					if(i==0){
					knowlegeNames=know.getQuestion();	
					}else{
					knowlegeNames=knowlegeNames+","+know.getQuestion();	
					}
					}
		    
			}	
			}
			logUserOperateService.setUserOperateLog(AppConstant.Module_id110 ,AppConstant.Module_Name110 ,AppConstant.Operate_id5 ,AppConstant.Operate_Name5 ,knowlegeNames,"" );
			}
		} catch (Exception e) {
			logger.debug("loginName=" + ShiroUtil.getUser().getName() + "删除知识异常!"+e.getMessage(),e);
			return AppConstant.RESULT_EXCEPTION;
		}
		return AppConstant.RESULT_SUCCESS;
	}

	/**
	 * 跳转到添加或修改知识组页面
	 * 
	 * @return
	 * @create 2013-11-6 下午05:07:00 wuwei
	 * @history
	 */
	@RequestMapping("toGroup")
	public String toGroup(HttpServletRequest request, String groupId) {

		try {
			Map<String, String> map = new HashMap<String, String>();
			map.put("orgId", ShiroUtil.getShiroUser().getOrgId());
			map.put("groupId", groupId);
			if (null != groupId && !StringUtils.isBlank(groupId)) {
				KnowlegeGroup knowlegeGroup = knowlegeGroupService.getKnowlegeByGroupId(map);
				request.setAttribute("knowlegeGroup", knowlegeGroup);
			} else {

			}
		} catch (Exception e) {
			throw new SysRunException(e);
		}
		return "unit/sys/knowlege_group_add";
	}

	/**
	 * 保存知识组
	 * 
	 * @create 2015年11月14日 下午2:03:50 wuwei
	 * @history
	 */
	@ResponseBody
	@RequestMapping("saveOrUpdateKnowlegeGroup")
	public String saveOrUpdateKnowlegeGroup(HttpServletRequest request, KnowlegeGroup knowlegeGroup) {
		try {
			knowlegeGroup.setOrgId(ShiroUtil.getUser().getOrgId());
			// 1. 验证名称是否重复
			List<KnowlegeGroup> tmpList = knowlegeGroupService.getGrpListByGrpName(knowlegeGroup);
			if (tmpList.size() > 0) {
				String tmpGroupId = tmpList.get(0).getGroupId();
				if (StringUtils.isBlank(knowlegeGroup.getGroupId())) {// 新增时重复
					return AppConstant.RESULT_FAILURE;
				} else if (!tmpGroupId.equals(knowlegeGroup.getGroupId())) {// 编辑时重复
					return AppConstant.RESULT_FAILURE;
				}
			}

			// 2.新增或编辑操作

			if (StringUtils.isBlank(knowlegeGroup.getGroupId()) || "".equals(knowlegeGroup.getGroupId())) {
				String id = GuidUtil.getId();
				knowlegeGroup.setGroupId(id);
				knowlegeGroup.setInputerAcc(ShiroUtil.getUser().getAccount());
				knowlegeGroup.setGroupIndex(0);
				knowlegeGroup.setIsDel(new Short("0"));
				knowlegeGroup.setOrgId(ShiroUtil.getUser().getOrgId());
				knowlegeGroup.setInputdate(new Date());
				knowlegeGroupService.create(knowlegeGroup);
				logUserOperateService.setUserOperateLog(AppConstant.Module_id110 ,AppConstant.Module_Name110 ,AppConstant.Operate_id49 ,AppConstant.Operate_Name49 ,knowlegeGroup.getGroupName(),"" );

			} else {
				knowlegeGroup.setModifierAcc(ShiroUtil.getUser().getAccount());
				knowlegeGroup.setModifydate(new Date());
				knowlegeGroupService.modify(knowlegeGroup);
				logUserOperateService.setUserOperateLog(AppConstant.Module_id110 ,AppConstant.Module_Name110 ,AppConstant.Operate_id50 ,AppConstant.Operate_Name50 ,knowlegeGroup.getGroupName(),"" );
			}
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			throw new SysRunException(e);
		}
	}

	@ResponseBody
	@RequestMapping("knowlegeGroup_del")
	public String knowlegeGroup_del(HttpServletRequest request, String groupId) {
		try {
			// 1,删除分组
			Map<String, String> map = new HashMap<String, String>();
			map.put("groupId", groupId);
			map.put("inputAcc", ShiroUtil.getUser().getAccount());
			map.put("orgId", ShiroUtil.getShiroUser().getOrgId());
			KnowlegeGroup knowlegeGroup = knowlegeGroupService.getKnowlegeByGroupId(map);
			knowlegeGroupService.updateKnowlegeGroup(map);
			logUserOperateService.setUserOperateLog(AppConstant.Module_id110 ,AppConstant.Module_Name110 ,AppConstant.Operate_id51 ,AppConstant.Operate_Name51 ,knowlegeGroup.getGroupName(),"" );
			return AppConstant.RESULT_SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			return AppConstant.RESULT_EXCEPTION;
		}
	}

	/**
	 * 查看知识
	 * 
	 * @return
	 * @create 2015年11月14日 下午4:47:02 wuwei
	 * @history
	 */
	@RequestMapping(value = "knowlege_search")
	public String knowlege_search(HttpServletRequest request, String knowlegeId) {
		try {
			Knowlege knowlege = knowlegeService.getKnowlegeById(ShiroUtil.getUser().getOrgId(),knowlegeId);
			request.setAttribute("knowlege", knowlege);
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("查看知识异常 id=" + knowlegeId);
		}
		return "/unit/sys/knowlege_search";
	}
	
	/**
	 * 校验排序码是否有效
	 * 
	 * @return
	 * @create 2017-6-15 上午10:49:31 xiaoxh
	 * @history
	 */
	@RequestMapping("/sortIsOK")
	@ResponseBody
	public Map<String,Object> sortIsOK(HttpServletRequest request, String groupSort, String sort,String id) {
		    Map<String,Object> map=new HashMap<String,Object>();
		try {
			if((groupSort ==null || "".equals(groupSort))&&(sort ==null || "".equals(sort))){
			map.put("megType", "3");//排序值不能为空
			map.put("msg", "排序值不能为空");//排序值不能为空
			}else{
			map.put("megType", "0");//默认不存在此排序码
			ShiroUser user = ShiroUtil.getShiroUser();
			if(groupSort !=null && groupSort !=""){
			KnowlegeGroup knowlegeGroup = new KnowlegeGroup();
			knowlegeGroup.setOrgId(user.getOrgId());
			List<KnowlegeGroup> knowlegeGroups = knowlegeGroupService.getGrpListByGrpName(knowlegeGroup);
            if(knowlegeGroups !=null && knowlegeGroups.size()>0){
            	for(KnowlegeGroup kg:knowlegeGroups){
            		if(String.valueOf(kg.getGroupSort()) == groupSort || String.valueOf(kg.getGroupSort()).equals(groupSort)){
            			if(kg.getGroupId()!=id && !kg.getGroupId().equals(id)){
            			map.put("megType", "1"); //msgType  ： 此排序码已存在
            		 }
            		}
            	}
            }
			}
			if(sort !=null && sort !=""){
				KnowlegeDto	 knowlegeDto =new KnowlegeDto();
				// 查询知识列表,全部
				knowlegeDto.setOrgId(user.getOrgId());
				knowlegeDto.setIsUnGroup("");
				// 查询知识列表
				List<Knowlege> knowlegeList = knowlegeService.getListPage(knowlegeDto);
				if(knowlegeList !=null && knowlegeList.size()>0){
					for(Knowlege kl : knowlegeList){
						if((String.valueOf(kl.getSort()) == sort || String.valueOf(kl.getSort()).equals(sort))){
					    if(kl.getKnowlegeId() !=id && !kl.getKnowlegeId().equals(id)){
						map.put("megType", "1"); //msgType  ： 此排序码已存在	
					    }
						}
					}
				}	
			}
			}
			map.put("status", "true");
		} catch (Exception e) {
			map.put("status", "false");
			map.put("errorMsg", "校验出错");
		}
		return map;
	}

}
