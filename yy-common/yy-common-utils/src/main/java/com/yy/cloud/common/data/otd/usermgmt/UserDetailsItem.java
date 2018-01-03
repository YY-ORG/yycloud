package com.yy.cloud.common.data.otd.usermgmt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.security.Principal;
import java.util.List;


@Data
@ApiModel
public class UserDetailsItem implements Serializable, Principal {

    /**
	 * serialVersionUID:TODO Description.
	 */
	private static final long serialVersionUID = -7387488235467593996L;

	private String userId;

    private String loginName;

    private String email;

    private String phone;

    private String password;

    private String userName;
    
    private String birthday;

    private Byte status;

    private String description;

    private List<OrganizationItem> organizations;

    private List<RoleItem> roles;
    
	private byte gender;

	/**
	 * 岗位类型:参考数据字典
	 */
	private byte occupationType;
	
	
	/**
	 * 职称:参考数据字典
	 */
	private byte professionalTitle;
	
	/**
	 * 行政级别:参考数据字典
	 */
	private byte administrativeRank;
	
	/**
	 * 行政职务: 参考数据字典
	 */
	private byte administrativePost;

	/**
	 * 部门Id
	 */
	private String deptId;

    /**
     * 部门的名字
     */
	private String deptName;

	@Override
	public String getName() {
		return this.loginName;
	}

}
