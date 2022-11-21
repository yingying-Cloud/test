package com.jinpinghu.logic.order.param;

import javax.servlet.http.HttpServletRequest;

import com.jinpinghu.logic.BaseZLogicParam;

public class AddOrderParam extends BaseZLogicParam {

	public AddOrderParam(String _userId, String _apiKey,
			HttpServletRequest request) {
		super(_userId, _apiKey, request);
		// TODO 自动生成的构造函数存根
	}

	private String carId;
	private Integer plantEnterpriseId;

	public Integer getPlantEnterpriseId() {
		return plantEnterpriseId;
	}

	public void setPlantEnterpriseId(Integer plantEnterpriseId) {
		this.plantEnterpriseId = plantEnterpriseId;
	}

	public String getCarId() {
		return carId;
	}

	public void setCarId(String carId) {
		this.carId = carId;
	}


}
