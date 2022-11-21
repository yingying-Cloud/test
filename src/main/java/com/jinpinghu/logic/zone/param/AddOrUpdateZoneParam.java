package com.jinpinghu.logic.zone.param;

import com.jinpinghu.db.bean.TbZone;
import com.jinpinghu.logic.BaseZLogicParam;

import javax.servlet.http.HttpServletRequest;

public class AddOrUpdateZoneParam extends BaseZLogicParam {
    public AddOrUpdateZoneParam(String _userId, String _apiKey, HttpServletRequest request) {
        super(_userId, _apiKey, request);
    }
    private TbZone tbZone;

    public TbZone getTbZone() {
        return tbZone;
    }

    public void setTbZone(TbZone tbZone) {
        this.tbZone = tbZone;
    }


}
