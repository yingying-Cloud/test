package com.jinpinghu.logic.order.param;

import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.logic.BaseZLogicParam;

import javax.servlet.http.HttpServletRequest;

public class UpdLinkOrderInfoParam extends BaseZLogicParam {
    public UpdLinkOrderInfoParam(String _userId, String _apiKey, HttpServletRequest request) {
        super(_userId, _apiKey, request);
    }
    private Integer id;
	private Integer enterpriseId;
	private String zoneName;
	private String name;
	private String creditCode;
	private String legalPerson;
	private String legalPersonIdcard;
	private String linkPeople;
	private String linkMobile;
	private String address;
    private String file;
    private String area;
    private Integer type;
	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEnterpriseId() {
		return enterpriseId;
	}

	public void setEnterpriseId(Integer enterpriseId) {
		this.enterpriseId = enterpriseId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getLegalPerson() {
		return legalPerson;
	}

	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	public String getLegalPersonIdcard() {
		return legalPersonIdcard;
	}

	public void setLegalPersonIdcard(String legalPersonIdcard) {
		this.legalPersonIdcard = legalPersonIdcard;
	}

	public String getLinkPeople() {
		return linkPeople;
	}

	public void setLinkPeople(String linkPeople) {
		this.linkPeople = linkPeople;
	}

	public String getLinkMobile() {
		return linkMobile;
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
