package com.jinpinghu.logic.agriculturalAdvice.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class GetAgriculturalAdviceInfoParam extends BaseZLogicParam {

	public GetAgriculturalAdviceInfoParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	private Integer id;

}
