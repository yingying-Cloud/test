package com.jinpinghu.logic.sc.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.db.bean.TbBuyRelease;
import com.jinpinghu.logic.BaseZLogicParam;

public class AddBuyReleaseParam extends BaseZLogicParam {

	public AddBuyReleaseParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	
	public TbBuyRelease getBuyRelease() {
		return buyRelease;
	}

	public void setBuyRelease(TbBuyRelease buyRelease) {
		this.buyRelease = buyRelease;
	}

	public String getFileArray() {
		return fileArray;
	}

	public void setFileArray(String fileArray) {
		this.fileArray = fileArray;
	}

	private TbBuyRelease buyRelease;
	private String fileArray;

}
