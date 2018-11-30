package com.qftx.tsm.callrecord.scontrol;

import com.qftx.base.auth.bean.User;
import com.qftx.base.auth.service.UserService;
import com.qftx.base.util.HttpUtil;
import com.qftx.common.exception.SysRunException;
import com.qftx.common.util.CodeUtils;
import com.qftx.common.util.DateUtil;
import com.qftx.common.util.IContants;
import com.qftx.common.util.SysBaseModelUtil;
import com.qftx.tsm.cust.bean.ResCustInfoBean;
import com.qftx.tsm.cust.dto.CustDto;
import com.qftx.tsm.cust.service.ResCustInfoService;
import com.qftx.tsm.sms.bean.TsmSendSmsDetail;
import com.qftx.tsm.sms.bean.TsmSmsReceive;
import com.qftx.tsm.sms.service.TsmSendSmsDetailService;
import com.qftx.tsm.sms.service.TsmSmsReceiveService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 获取短信回执，接收短信, 漏接记录
 * @author: zwj
 * @since: 2016-1-26  上午9:03:53
 * @history: 4.x
 */
@Controller
@RequestMapping("/webClient/getSms")
public class GetSmsController {
	private static Logger logger = Logger.getLogger(GetSmsController.class);
	@Autowired
	private UserService userService;	
	@Autowired
	private ResCustInfoService resCustInfoService;
	@Autowired
	private TsmSmsReceiveService tsmSmsReceiveService;	
	@Autowired
	private TsmSendSmsDetailService tsmSendSmsDetailService;
	/** 
	 * 接收短信
	 * @return 
	 * @create  2014-10-29 下午04:34:18 wuwei
	 * @history  
	 */
	@RequestMapping("/tsmGetSms")
	@ResponseBody
	public void saveGetSms(HttpServletRequest request,HttpServletResponse response) {
		try {
			ServletInputStream inStream = request.getInputStream(); // 取HTTP请求流
			int size = request.getContentLength(); // 取HTTP请求流长度
			byte[] buffer = new byte[size]; // 用于缓存每次读取的数据
			byte[] in_b = new byte[size]; // 用于存放结果的数组
			int count = 0;
			int rbyte = 0;
			// 循环读取
			while (count < size) {
				rbyte = inStream.read(buffer); // 每次实际读取长度存于rbyte中 sflj
				for (int i = 0; i < rbyte; i++) {
					in_b[count + i] = buffer[i];
				}
				count += rbyte;
			}
			inStream.close();
			String protocolXML = new String(CodeUtils.base64Decode(in_b), IContants.CHAR_ENCODING);
			logger.info("******** 【接收短信】 回复返回原始内容：" +  protocolXML + "**********");
			// 解析xml
			Document doc = (Document) DocumentHelper.parseText(protocolXML);
			Element books = doc.getRootElement();
			Iterator Elements = books.elementIterator();

			List<TsmSmsReceive> tsmSmss = new ArrayList<TsmSmsReceive>();
			while (Elements.hasNext()) {
				Element user = (Element) Elements.next();
				List subElements = user.elements();
				String sub_num = "";
				String send_number = "";
				String reply_time = "";
				String reply_content = "";
				for (int i = 0; i < subElements.size(); i++) {
					Element ele = (Element) subElements.get(i);
					// 子码（sub_num）
					if ("sub_num".equals(ele.getName())) {
						sub_num = ele.getText();
					}
					// 发送号码 （send_number）
					else if ("send_number".equals(ele.getName())) {
						send_number = ele.getText();
					}
					// 发送时间 （reply_time）
					else if ("reply_time".equals(ele.getName())) {
						reply_time = ele.getText();
					}
					// 发送内容 （reply_content）
					else if ("reply_content".equals(ele.getName())) {
						reply_content = ele.getText();
					}
				}
					logger.info("**********【接收短信】解析后的 信息 ：子号码:" + sub_num+",发送号码:" + send_number +",回复时间:"+ reply_time+" 回复内容"+ reply_content);
				// 保证接口传过来的子号码和发送号码不为空
				if (StringUtils.isNotEmpty(sub_num) ) {
					User query = new User();
					query.setEnabled(1);
					query.setSubNum(sub_num);
					// 1.通过子码去查询机构
					List<User> users = userService.getListByCondtion(query);
					if (users.size() > 0) {
						User u = users.get(0);
						String orgId = u.getOrgId();
						// 2.通过机构和电话去查找客户信息
						CustDto custDto = new CustDto();
						custDto.setOrgId(orgId);
						custDto.setOwnerAcc(u.getUserAccount());
						custDto.setPhone(send_number);
						List<ResCustInfoBean> res = resCustInfoService.getInterfaceCust(custDto);

						TsmSmsReceive entity = new TsmSmsReceive();
						entity.setSmsId(SysBaseModelUtil.getModelId());
						entity.setOrgId(u.getOrgId()); // 机构ID
						entity.setName(res.size() > 0 ? res.get(0).getName() : "");// 客户姓名
						entity.setAccount(u.getUserAccount()); // 用户帐号
						entity.setSmsComment(reply_content); // 短信内容
						entity.setIsDel(new Short("0"));
						entity.setMobilePhone(send_number);
						entity.setReceiveDate(DateUtil.parse(reply_time, DateUtil.hour24Pattern));
						tsmSmss.add(entity);
					}
				}
			}
			if (tsmSmss.size() > 0) {
				tsmSmsReceiveService.createBatch(tsmSmss);
			}
		} catch (Exception e) {
			HttpUtil.writeTextResponse(response, CodeUtils.base64Encode("<root><code>1</code></root>"));
			throw new SysRunException(e);
		}
		HttpUtil.writeTextResponse(response, CodeUtils.base64Encode("<root><code>1</code></root>"));
	}
	
	/**
	 * 短信回执
	 * 
	 * @create 2013-11-11 下午05:29:38 wuwei
	 * @history
	 */
	@RequestMapping("/getSmsReceipt")
	@ResponseBody
	public void getSmsReceipt(HttpServletRequest request,HttpServletResponse response) {
		try {
			ServletInputStream inStream = request.getInputStream(); // 取HTTP请求流
			int size = request.getContentLength(); // 取HTTP请求流长度
			byte[] buffer = new byte[size]; // 用于缓存每次读取的数据
			byte[] in_b = new byte[size]; // 用于存放结果的数组
			int count = 0;
			int rbyte = 0;
			// 循环读取
			while (count < size) {
				rbyte = inStream.read(buffer); // 每次实际读取长度存于rbyte中 sflj
				for (int i = 0; i < rbyte; i++) {
					in_b[count + i] = buffer[i];
				}
				count += rbyte;
			}
			inStream.close();
			String protocolXML = new String(CodeUtils.base64Decode(in_b), IContants.CHAR_ENCODING);
			logger.info("******** 【短信回执】 回执返回内容：" +  protocolXML + "**********");

			// 解析xml
			Document doc = (Document) DocumentHelper.parseText(protocolXML);
			Element books = doc.getRootElement();
			Iterator Elements = books.elementIterator();

			List<TsmSendSmsDetail> tsmSmss = new ArrayList<TsmSendSmsDetail>();
			while (Elements.hasNext()) {
				Element noteElement = (Element) Elements.next();
				List<Element> subElements = noteElement.elements();
				String smsDetailId = "";
				String state = "";
				for (Element ele : subElements) {
					if ("note_code".equals(ele.getName())) {
						smsDetailId = ele.getText();
					}
					if ("rece_state".equals(ele.getName())) {
						state = ele.getText();
					}
				}
				TsmSendSmsDetail sms = new TsmSendSmsDetail();
				sms.setSmsDetailId(smsDetailId);
				sms.setReceiptStatus(state);
				tsmSmss.add(sms);
			}
			tsmSendSmsDetailService.modifyBatchSmsDetail(tsmSmss);
			if (tsmSmss.size() > 0) {
				// 正确返回结果
				HttpUtil.writeTextResponse(response, CodeUtils.base64Encode("<root><code>1</code></root>"));
			}
		} catch (Exception e) {
			HttpUtil.writeTextResponse(response, CodeUtils.base64Encode("<root><code>1</code></root>"));
			throw new SysRunException(e);
		}
	}


}
