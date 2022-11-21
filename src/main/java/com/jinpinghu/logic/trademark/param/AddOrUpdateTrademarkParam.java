package com.jinpinghu.logic.trademark.param;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrUpdateTrademarkParam extends BaseZLogicParam{

	public AddOrUpdateTrademarkParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
	}
	private String id;
	private String brandName;
	private String address;
	private String trademarkName;
	private String productCertification;
	private String source;
	private String contractNumber;
	private String x;
	private String y;
	private String file;
	private String brand;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTrademarkName() {
		return trademarkName;
	}
	public void setTrademarkName(String trademarkName) {
		this.trademarkName = trademarkName;
	}
	public String getProductCertification() {
		return productCertification;
	}
	public void setProductCertification(String productCertification) {
		this.productCertification = productCertification;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	
}
