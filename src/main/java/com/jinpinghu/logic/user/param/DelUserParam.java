package com.jinpinghu.logic.user.param;

import com.jinpinghu.logic.BaseZLogicParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: yanchengjie
 * @Date: 2019/8/28 10:49
 */
public class DelUserParam extends BaseZLogicParam {
    public DelUserParam(String _userId, String _apiKey, HttpServletRequest request) {
        super(_userId, _apiKey, request);
    }

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
