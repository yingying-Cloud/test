package com.jinpinghu.logic.order.param;

import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.logic.BaseZLogicParam;

import javax.servlet.http.HttpServletRequest;

public class AddLinkOrderInfoParam extends BaseZLogicParam {
    public AddLinkOrderInfoParam(String _userId, String _apiKey, HttpServletRequest request) {
        super(_userId, _apiKey, request);
    }

    private TbLinkOrderInfo tbLinkOrderInfo;
    private String file;
    public TbLinkOrderInfo getTbLinkOrderInfo() {
        return tbLinkOrderInfo;
    }

    public void setTbLinkOrderInfo(TbLinkOrderInfo tbLinkOrderInfo) {
        this.tbLinkOrderInfo = tbLinkOrderInfo;
    }

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

}
