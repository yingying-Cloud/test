package com.jinpinghu.logic.user.param;

import com.jinpinghu.logic.BaseZLogicParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: yanchengjie
 * @Date: 2019/8/28 11:45
 */
public class FindUserListParam extends BaseZLogicParam {
    public FindUserListParam(String _userId, String _apiKey, HttpServletRequest request) {
        super(_userId, _apiKey, request);
    }
    private String name;
    private String roleId;
    private String mobile;
    private Integer nowPage;
    private Integer pageCount;
    private String dscd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNowPage() {
        return nowPage;
    }

    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getDscd() {
		return dscd;
	}

	public void setDscd(String dscd) {
		this.dscd = dscd;
	}


}
