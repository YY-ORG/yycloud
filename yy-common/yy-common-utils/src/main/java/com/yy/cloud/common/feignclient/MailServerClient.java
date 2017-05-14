/**
 * Project Name:liz-common-utils
 * File Name:MailServerClient.java
 * Package Name:com.yy.cloud.common.feignclient.mailmgmt
 * Date:Apr 25, 201610:50:33 AM
 * Copyright (c) 2016, chenxj All Rights Reserved.
 *
*/

package com.yy.cloud.common.feignclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yy.cloud.common.data.mailmgmt.MailSendResult;
import com.yy.cloud.common.data.mailmgmt.MimeMailInfo;
import com.yy.cloud.common.data.mailmgmt.SimpleMailInfo;

/**
 * ClassName:MailServerClient <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: Apr 25, 2016 10:50:33 AM <br/>
 * 
 * @author chenxj
 * @version
 * @since JDK 1.8
 * @see
 */
@FeignClient("mailmgmt")
public interface MailServerClient {
	/**
	 * sendMimeMail: Going to send Mime Mail. <br/>
	 *
	 * @param _mailInfo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/mailmgmt/authsec/sender/mimemail",
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	MailSendResult sendMimeMail(@RequestBody MimeMailInfo _mailInfo);

	/**
	 * sendSimpleMail: Going to send Simple Mail. <br/>
	 *
	 * @param _mailInfo
	 * @return
	 * @since JDK 1.8
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/mailmgmt/authsec/sender/simplemail",
			consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	MailSendResult sendSimpleMail(@RequestBody SimpleMailInfo _mailInfo);
}
