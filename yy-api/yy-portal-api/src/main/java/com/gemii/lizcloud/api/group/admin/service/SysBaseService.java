/**
 * Project Name:liz-admin
 * File Name:SysBaseService.java
 * Package Name:com.gemii.lizcloud.api.admin.service
 * Date:Sep 28, 20164:08:03 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.gemii.lizcloud.api.group.admin.service;

import java.util.List;

import com.gemii.lizcloud.common.data.otd.sysbase.AdminRegion;
import com.gemii.lizcloud.common.data.system.SysDic;

/**
 * ClassName:SysBaseService <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Sep 28, 2016 4:08:03 PM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
public interface SysBaseService {

	/**
	 * getRegions:获取所有地理区域. <br/>
	 *
	 * @return
	 */
	
	public List<AdminRegion> getRegionByStatus(Byte _status);
	
	/**
	 * getAllSysdictionary: Retrieve all of the dictionaries <br/>
	 *
	 * @return
	 */
	public List<SysDic> getAllSysdictionary();

	/**
	 * getSysdictionaryByOwner: Retrieve all of the _owner's discs. <br/>
	 *
	 * @param _owner
	 * @return
	 */
	public List<SysDic> getSysdictionaryByOwner(String _owner);

	/**
	 * getSysdictionaryByOwnerAndField: Retrieve the dics with owner and field.
	 * <br/>
	 *
	 * @param _owner
	 * @param _field
	 * @return
	 */
	public List<SysDic> getSysdictionaryByOwnerAndField(String _owner, String _field);

	/**
	 * getOneSysdictionary: Get someone of the dics.. <br/>
	 *
	 * @param _owner
	 * @param _field
	 * @param _code
	 * @return
	 */
	public SysDic getOneSysdictionary(String _owner, String _field, String _code);

	
}
