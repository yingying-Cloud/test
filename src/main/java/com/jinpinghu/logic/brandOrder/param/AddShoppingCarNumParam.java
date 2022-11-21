package com.jinpinghu.logic.brandOrder.param;

import com.jinpinghu.logic.BaseZLogicParam;

import javax.servlet.http.HttpServletRequest;

public class AddShoppingCarNumParam extends BaseZLogicParam {
    public AddShoppingCarNumParam(String _userId, String _apiKey, HttpServletRequest request) {
        super(_userId, _apiKey, request);
    }
    private String carId;
    private String numJa;

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getNumJa() {
        return numJa;
    }

    public void setNumJa(String numJa) {
        this.numJa = numJa;
    }
}
