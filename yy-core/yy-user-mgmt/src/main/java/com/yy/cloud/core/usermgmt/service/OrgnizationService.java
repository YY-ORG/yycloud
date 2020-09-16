package com.yy.cloud.core.usermgmt.service;

import java.util.List;

import com.yy.cloud.common.data.GeneralContentResult;
import com.yy.cloud.common.data.GeneralResult;
import com.yy.cloud.common.data.PageInfo;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationItem;
import com.yy.cloud.common.data.otd.usermgmt.OrganizationProfile;

/**
 * @author chenxj
 */
public interface OrgnizationService {

	/**
	 * 创建组织
	 *
	 * @param _organizationProfile
	 *            组织信息
	 * @return 组织ID
	 */
	GeneralContentResult<String> createOrgnization(OrganizationProfile _organizationProfile);

	GeneralResult updateOrgnization(String _organizationId, OrganizationProfile _organizationProfile);

	/**
	 * 删除组织
	 *
	 * @param _organizationId
	 *            组织ID
	 */
	GeneralResult deleteOrgnization(String _organizationId);

	/**
	 * 修改组织状态
	 *
	 * @param _organizationId
	 *            组织ID
	 * @param _status
	 *            状态
	 */
	void updateOrgnizationStatus(String _organizationId, Byte _status);

	/**
	 * 分页列出组织
	 *
	 * @param _pageInfo
	 * @param _status
	 * @param _name
	 * @return
	 */
	List<OrganizationItem> listOrganizationsByPage(PageInfo _pageInfo, Byte _status, String _name);

	OrganizationItem findOrganizationItemById(String _organizationId);
}
