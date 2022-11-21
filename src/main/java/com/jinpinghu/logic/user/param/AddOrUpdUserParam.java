package com.jinpinghu.logic.user.param;

import com.jinpinghu.logic.BaseZLogicParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: yanchengjie
 * @Date: 2019/8/28 10:05
 */
public class AddOrUpdUserParam extends BaseZLogicParam {
    public AddOrUpdUserParam(String _userId, String _apiKey, HttpServletRequest request) {
        super(_userId, _apiKey, request);
    }

    private String mobile;
    private String password;
    private String name;
    private Integer id;
    private Integer roleId;
    private Integer enterpriseId;
    private String dscd;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

	public String getDscd() {
		return dscd;
	}

	public void setDscd(String dscd) {
		this.dscd = dscd;
	}


}
