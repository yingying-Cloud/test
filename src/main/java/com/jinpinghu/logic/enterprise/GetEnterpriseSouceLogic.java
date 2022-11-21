package com.jinpinghu.logic.enterprise;

import com.jinpinghu.db.bean.TbEnterprise;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseSouceParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class GetEnterpriseSouceLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        GetEnterpriseSouceParam myParam = (GetEnterpriseSouceParam)logicParam;
        String name=myParam.getName();
        String enterpriseCreditCode = myParam.getEnterpriseCreditCode();
        Integer enterpriseType = StringUtils.isNullOrEmpty(myParam.getEnterpriseType())?null:Integer.valueOf(myParam.getEnterpriseType());
        Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
        Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
        TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
        Integer count = enterpriseDao.findSourceByAllCount(name, enterpriseCreditCode, enterpriseType);
        List<Map<String, Object>> list = enterpriseDao.findSourceByAll(name, enterpriseCreditCode, enterpriseType, pageCount, nowPage);
        res.add("result",list).add("msg","操作成功").add("status",1).add("allCounts",count);
        return true;
    }
}
