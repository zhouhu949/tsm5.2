package com.qftx.tsm.cust.service;

import com.qftx.common.util.StringUtils;
import com.qftx.tsm.cust.dao.CustOptorMapper;
import com.qftx.tsm.cust.dao.ResCustInfoDetailMapper;
import com.qftx.tsm.cust.dto.CustOptorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustOptorService {
	@Autowired
	private CustOptorMapper custOptorMapper;
	@Autowired
	private ResCustInfoDetailMapper resCustInfoDetailMapper;
	/** 
	 * 客户交接记录
	 * @param dto
	 * @return 
	 * @create  2015年11月30日 下午3:54:01 lixing
	 * @history  
	 */
	public List<CustOptorDto> findCustInOutListPage(CustOptorDto dto,int isState){
		List<CustOptorDto> list = new ArrayList<CustOptorDto>();
		List<String> cids = new ArrayList<String>();
    	if(isState == 1 && 
    			StringUtils.isNotBlank(dto.getQueryText()) && 
    			(dto.getQueryType().equals("3") || dto.getQueryType().equals("2"))){
    		if(dto.getQueryType().equals("3")){
    			cids = resCustInfoDetailMapper.findLinkmanIdsByPhone(dto.getOrgId(), dto.getQueryText());
    		}else{
    			cids = resCustInfoDetailMapper.findLinkmanIds(dto.getOrgId(), dto.getQueryText());
    		}
    		if(cids.size() == 0) return list;
    		dto.setCustIds(cids);
    		dto.setQueryText(null);
    	}
		list = custOptorMapper.findCustInOutListPage(dto);
		return list;
	}
	
	/** 
	 * 今日转入数量
	 * @param transferAcc
	 * @return 
	 * @create  2015年11月30日 下午4:51:38 lixing
	 * @history  
	 */
	public Integer findCustIncomeNum(String transferAcc){
		return custOptorMapper.findCustIncomeNum(transferAcc);
	}
	
	
	/** 
	 * 所有转出数量
	 * @param ownerAcc
	 * @return 
	 * @create  2015年11月30日 下午4:52:47 Administrator
	 * @history  
	 */
	public Integer findCustOutNum(String ownerAcc){
		return custOptorMapper.findCustOutNum(ownerAcc);
	}
}
