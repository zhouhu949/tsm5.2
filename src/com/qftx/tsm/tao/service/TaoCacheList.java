package com.qftx.tsm.tao.service;

import com.qftx.common.util.ConfigInfoUtils;
import com.qftx.tsm.cust.dto.TaoResDto;

import java.util.ArrayList;
import java.util.List;

/**
 * User： bxl Date： 2016/1/8 Time： 16:06
 */

public class TaoCacheList {
	public final static Integer pageNum = new Integer((ConfigInfoUtils.getStringValue("tao_num_page")));

	public static List<TaoResDto> getList(List<TaoResDto> datalist, String up) {
		List<TaoResDto> list = new ArrayList<TaoResDto>();
		boolean isTrue = false;
		for (TaoResDto taoResDto : datalist) {
			if (taoResDto.getResId().equals(up)) {
				isTrue = true;
			}

			if (isTrue) {
				list.add(taoResDto);
			}
			if (list.size() >= pageNum) {
				break;
			}
		}
		return list;
	}

	public static List<TaoResDto> getUpList(List<TaoResDto> datalist, String up) {
		List<TaoResDto> list = new ArrayList<TaoResDto>();
		boolean isTrue = false;
		for (TaoResDto taoResDto : datalist) {
			if (taoResDto.getResId().equals(up)) {
				isTrue = true;
			}
			if (list.size() > pageNum) {
				list.remove(list.get(0));
			}
			if (isTrue && list.size() >= pageNum) {
				break;
			}
			list.add(taoResDto);
		}
		return list;
	}

	public static List<TaoResDto> getNextList(List<TaoResDto> datalist, String up) {
		List<TaoResDto> list = new ArrayList<TaoResDto>();
		boolean isTrue = false;
		for (TaoResDto taoResDto : datalist) {
			if (taoResDto.getResId().equals(up)) {
				isTrue = true;
				continue;
			}

			if (isTrue) {
				list.add(taoResDto);
			}
			if (list.size() >= pageNum) {
				break;
			}
		}
		return list;
	}

	public static void remove(List<TaoResDto> datalist, String up) {
		for (TaoResDto taoResDto : datalist) {
			if (taoResDto.getResId().equals(up)) {
				datalist.remove(taoResDto);
				break;
			}
		}
	}

	public static String removeUp(List<TaoResDto> datalist, String up) {
		List<TaoResDto> list = new ArrayList<TaoResDto>();
		String upString = up;
		boolean isTrue = false;
		for (TaoResDto taoResDto : datalist) {
			// 定位ID
			if (taoResDto.getResId().equals(up)) {
				isTrue = true;
			}
			// 查询保持1条在list
			if (list.size() > 1) {
				list.remove(list.get(0));
			}
			// 中断条件，定位，并不够向下
			if (isTrue && list.size() >= 1) {
				upString = list.get(0).getResId();
				break;
			}
			if (!taoResDto.getResId().equals(up)) {
				list.add(taoResDto);
			}
		}

		return upString;
	}

	public static String removeNext(List<TaoResDto> datalist, String up) {
		if(datalist!=null && datalist.size()<=1){
			return up;
		}
		List<TaoResDto> list = new ArrayList<TaoResDto>();
		String upString = up;
		boolean isTrue = false;
		for (TaoResDto taoResDto : datalist) {
			// 定位ID
			if (taoResDto.getResId().equals(up)) {
				isTrue = true;
				continue;
			}

			// 中断条件，定位，并不够向下
			if (list.size() >= 1) {
				upString = list.get(0).getResId();
				break;
			}
			if (isTrue) {
				list.add(taoResDto);
			}
		}
		if (upString.equals(up)) {
			upString = datalist.get(datalist.size() - 2).getResId();
		}

		return upString;
	}

	public static void main(String[] args) {
		List<TaoResDto> list = new ArrayList<TaoResDto>();
		for (int i = 1; i < 500; i++) {
			TaoResDto bean = new TaoResDto();
			bean.setResId("id" + i);
			list.add(bean);
		}
		int ct = 1;
		List<TaoResDto> newList = getUpList(list, "id5");
		for (TaoResDto taoResDto : newList) {
			System.out.println("up=" + taoResDto.getResId() + "\t" + ct);
			++ct;
		}
		System.out.println("---------------------------------");
		newList = getNextList(list, "id5");
		ct = 1;
		for (TaoResDto taoResDto : newList) {
			System.out.println("next=" + taoResDto.getResId() + "\t" + ct);
			++ct;
		}

		System.out.println("--------------------------------");

		remove(list, "id1");
		remove(list, "id2");
		remove(list, "id3");
		remove(list, "id4");
		remove(list, "id5");
		System.out.println("up=" + removeUp(list, "id6"));

		System.out.println("--------------------------------");
		remove(list, "id7");
		remove(list, "id499");
		System.out.println("next=" + removeNext(list, "id498"));

	}

}
