package com.jinpinghu.logic.enterprise;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseSouceParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetEnterpriseSouceTopLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        GetEnterpriseSouceParam myParam = (GetEnterpriseSouceParam)logicParam;
        TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
        List<Map<String, Object>> list = enterpriseDao.findSourceTopByAll();
        res.add("result",list).add("msg","操作成功").add("status",1);
        return true;
    }
}
