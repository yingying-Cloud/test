package com.jinpinghu.logic.order;

import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.DelLinkOrderInfoParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;

public class DelLinkOrderInfoLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        DelLinkOrderInfoParam myParam=(DelLinkOrderInfoParam)logicParam;
        Integer id=myParam.getId();
        TbLinkOrderInfoDao tbLinkOrderInfoDao=new TbLinkOrderInfoDao(em);
        Integer result=tbLinkOrderInfoDao.delLinkOrderInfo(id);
        res.add("status",1);
        if (result>0){
            res.add("msg","删除成功");
        }else{
            res.add("msg","删除失败");
        }
        return true;
    }
}
