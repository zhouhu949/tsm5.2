package com.qftx.tsm.sys.service;

import com.qftx.tsm.sys.bean.AnnouncementBean;
import com.qftx.tsm.sys.dao.AnnouncementMapper;
import com.qftx.tsm.sys.dto.AnnouncementDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AnnouncementService {
	@Autowired
	private AnnouncementMapper announcementMapper;
	
	public List<AnnouncementBean> getList() {
		return announcementMapper.find();
	}

	public List<AnnouncementBean> getListByCondtion(AnnouncementBean entity) {
		return announcementMapper.findByCondtion(entity);
	}

	public List<AnnouncementBean> getListPage(AnnouncementBean entity) {
		return announcementMapper.findListPage(entity);
	}

	public AnnouncementBean getByPrimaryKey(String id) {
		return announcementMapper.getByPrimaryKey(id);
	}

	public void create(AnnouncementBean entity) {
		announcementMapper.insert(entity);
	}

	public void createBatch(List<AnnouncementBean> entitys) {
		announcementMapper.insertBatch(entitys);
	}

	public void modify(AnnouncementBean entity) {
		announcementMapper.update(entity);
	}

	public void modifyTrends(AnnouncementBean entity) {
		announcementMapper.updateTrends(entity);		
	}

	public void modifyBatch(List<AnnouncementBean> entitys) {
		for (AnnouncementBean announcement : entitys) {
			announcementMapper.update(announcement);
		}
	}

	public void modifyTrendsBatch(List<AnnouncementBean> entitys) {
		for (AnnouncementBean announcement : entitys) {
			announcementMapper.updateTrends(announcement);
		}
	}

	public void remove(String id) {
		announcementMapper.delete(id);
	}

	public void removeBatch(List<String> ids) {
		announcementMapper.deleteBatch(ids);
	}

	public void removeFakeBatch(Map<String, Object> param) {
		announcementMapper.deleteFakeBatch(param);
	}

	public AnnouncementDto getNoticeInfoById(Map<String,String>map) {
		return announcementMapper.findNoticeInfoById(map);
	}
	
	public List<String> getNoticeReaderUserById(Map<String,String>map) {
		return announcementMapper.findNoticeReaderUserById(map);
	}

	public List<AnnouncementDto> getNoticeListPage(AnnouncementDto dto) {
		return announcementMapper.findNoticeListPage(dto);
	}

	public AnnouncementBean getByCondtion(AnnouncementBean entity){
		return announcementMapper.getByCondtion(entity);
	}
	
	public List<AnnouncementDto> findNoticeReadersum(AnnouncementDto dto){
		return announcementMapper.findNoticeReadersum(dto);
	}
}
