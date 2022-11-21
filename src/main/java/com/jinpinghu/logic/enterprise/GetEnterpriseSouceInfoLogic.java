package com.jinpinghu.logic.enterprise;

import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseSouceInfoParam;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseSouceParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class GetEnterpriseSouceInfoLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        GetEnterpriseSouceInfoParam myParam = (GetEnterpriseSouceInfoParam)logicParam;
        String enterpriseCreditCode = myParam.getEnterpriseCreditCode();
        TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
        Map<String, Object> map = enterpriseDao.findSourceInfoByCode(enterpriseCreditCode);
        res.add("result",map).add("msg","操作成功").add("status",1);
        return true;
    }
}
