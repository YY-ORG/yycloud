package com.yy.cloud.core.usermgmt.utils;

import org.springframework.data.domain.Page;

import com.yy.cloud.common.data.PageInfo;

public class VOUtil {

	public static PageInfo initPageInfo(Page<?> page) {
		PageInfo pi = new PageInfo();
		pi.setCurrentPage(page.getNumber());
		pi.setPageSize(page.getSize());
		pi.setTotalPage(page.getTotalPages());
		pi.setTotalRecords(new Long( page.getTotalElements()));
		return pi;
	}
}
