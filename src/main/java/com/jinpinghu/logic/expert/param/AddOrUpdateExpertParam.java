package com.jinpinghu.logic.expert.param;
import javax.servlet.http.HttpServletRequest;
import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrUpdateExpertParam extends BaseZLogicParam{

	public AddOrUpdateExpertParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	private String mobile;
	private String name;
	private String id;
	private String address;
	private String goodsField;
	private String idcard;
	private String status;
	private String synopsis;
	private String file;
	private String type;
	private String cost;
	private String adnm;
	private String productTeam;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getGoodsField() {
		return goodsField;
	}
	public void setGoodsField(String goodsField) {
		this.goodsField = goodsField;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getAdnm() {
		return adnm;
	}
	public void setAdnm(String adnm) {
		this.adnm = adnm;
	}
	public String getProductTeam() {
		return productTeam;
	}
	public void setProductTeam(String productTeam) {
		this.productTeam = productTeam;
	}
	
}
