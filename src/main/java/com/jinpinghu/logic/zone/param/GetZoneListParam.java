package com.jinpinghu.logic.zone.param;

import com.jinpinghu.logic.BaseZLogicParam;

import javax.servlet.http.HttpServletRequest;

public class GetZoneListParam extends BaseZLogicParam {
    public GetZoneListParam(String _userId, String _apiKey, HttpServletRequest request) {
        super(_userId, _apiKey, request);
    }

    private String name;
    private Integer enterpriseId;
    private Integer nowPage;
    private Integer pageCount;

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

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}
}
