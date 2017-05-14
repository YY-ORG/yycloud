package com.yy.cloud.api.admin.data.otd;

import java.io.Serializable;

import lombok.Data;

/**
 * ClassName:ServiceProfileResponse <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     Sep 18, 2016 2:24:57 PM <br/>
 * @author   chenxj
 * @version  
 * @since    JDK 1.8
 * @see 	 
 */
@Data
public class ServiceProfileResponse implements Serializable {/**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = -3932112382012890451L;

	private Long serviceId;
	private String serviceName;
}
