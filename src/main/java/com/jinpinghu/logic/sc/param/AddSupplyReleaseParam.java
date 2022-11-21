package com.jinpinghu.logic.sc.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.db.bean.TbSupplyRelease;
import com.jinpinghu.logic.BaseZLogicParam;

public class AddSupplyReleaseParam extends BaseZLogicParam {

	public AddSupplyReleaseParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	
	public TbSupplyRelease getSupplyRelease() {
		return supplyRelease;
	}

	public void setSupplyRelease(TbSupplyRelease supplyRelease) {
		this.supplyRelease = supplyRelease;
	}

	public String getFileArray() {
		return fileArray;
	}

	public void setFileArray(String fileArray) {
		this.fileArray = fileArray;
	}

	private TbSupplyRelease supplyRelease;
	private String fileArray;

}
