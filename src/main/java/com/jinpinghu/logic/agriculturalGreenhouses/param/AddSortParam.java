package com.jinpinghu.logic.agriculturalGreenhouses.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddSortParam extends BaseZLogicParam{

	public AddSortParam(String _userId, String _apiKey, HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO Auto-generated constructor stub
	}
	
	public String getSortArray() {
		return sortArray;
	}

	public void setSortArray(String sortArray) {
		this.sortArray = sortArray;
	}

	private String sortArray;

}
