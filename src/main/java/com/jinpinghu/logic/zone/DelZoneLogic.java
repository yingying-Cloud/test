package com.jinpinghu.logic.zone;

import com.jinpinghu.db.dao.TbZoneDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.zone.param.DelZoneParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;

public class DelZoneLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        DelZoneParam myParam=(DelZoneParam)logicParam;
        Integer id=myParam.getId();
        TbZoneDao tbZoneDao=new TbZoneDao(em);
        Integer result=tbZoneDao.delete(id);
        if (result>0){
            res.add("msg","删除成功");
        }else{
            res.add("msg","删除失败");
        }
        res.add("status",result);
        return true;
    }
}
