package com.yy.cloud.common.data.dto.sysbase;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class UserProfile implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5357717149981670086L;

	private String id;

	@ApiModelProperty(notes = "账号")
	private String loginName;

	private String password;

	@ApiModelProperty(notes = "姓名")
	private String userName;

	private String email;

	private String phone;

	/**
	 * 0:男 1:女
	 */
	private byte gender;

	/**
	 * 生日
	 */
	private Date birthday;

	/**
	 * 岗位系列
	 */
	@ApiModelProperty(notes = "  岗位系列")
	private byte occupationType;

	/**
	 * 职称
	 */
	@ApiModelProperty(notes = " 职称")
	private byte professionalTitle;

	/**
	 * 行政职务
	 */
	@ApiModelProperty(notes = " 行政职务")
	private byte administrativePost;

	/**
	 * 行政级别
	 */
	@ApiModelProperty(notes = " 行政级别")
	private byte administrativeRank;
	
	/**
	 * 部门
	 */
	@ApiModelProperty(notes = " 部门Id")
	private String orgId;
	

	@ApiModelProperty(notes = "绑定角色")
	private List<RoleProfile> roles;

	@ApiModelProperty(notes = "描述")
	private String description;


}
