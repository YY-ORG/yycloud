/**
 * Project Name:liz-admin
 * File Name:SysBaseClient.java
 * Package Name:com.gemii.lizcloud.api.admin.clients
 * Date:Sep 28, 20164:14:33 PM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.gemii.lizcloud.api.group.admin.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.gemii.lizcloud.common.data.otd.sysbase.Region;
import com.gemii.lizcloud.common.data.otd.sysbase.AdminRegion;
import com.gemii.lizcloud.common.data.system.SystemDictionary;

/**
 * ClassName:SysBaseClient <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 28, 2016 4:14:33 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@FeignClient("sysbase")
public interface SysBaseClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/region/search/findByStatus?_status={_status}")
	Resources<Region> findByStatus(@PathVariable("_status") Byte _status);
	
	@RequestMapping(method = RequestMethod.GET, value = "/sysdic")
	Resources<SystemDictionary> findSysDicAll();
	
	@RequestMapping(method = RequestMethod.GET, value = "/sysdic/search/findByOwner?_owner={_owner}")
	Resources<SystemDictionary> findByOwner(@PathVariable("_owner") String _owner);
	
	@RequestMapping(method = RequestMethod.GET,
			value = "/sysdic/search/findByOwnerAndField?_owner={_owner}&_field={_field}")
	Resources<SystemDictionary> findByOwnerAndField(@PathVariable("_owner") String _owner,
			@PathVariable("_field") String _field);
	
	@RequestMapping(method = RequestMethod.GET,
			value = "/sysdic/search/findByOwnerAndFieldAndCode?_owner={_owner}&_field={_field}&_code={_code}")
	Resources<SystemDictionary> findByOwnerAndFieldAndCode(@PathVariable("_owner") String _owner,
			@PathVariable("_field") String _field,@PathVariable("_code") String _code);
	
	
}

