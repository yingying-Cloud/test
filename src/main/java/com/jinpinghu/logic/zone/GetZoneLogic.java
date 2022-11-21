package com.jinpinghu.logic.zone;

import com.jinpinghu.db.bean.TbZone;
import com.jinpinghu.db.dao.TbZoneDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.zone.param.GetZoneParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;

public class GetZoneLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        GetZoneParam myParam=(GetZoneParam)logicParam;
        Integer id=myParam.getId();
        TbZoneDao tbZoneDao=new TbZoneDao(em);
        TbZone tbZone=tbZoneDao.findById(id);
        res.add("result",tbZone);
        if (tbZone!=null){
            res.add("msg","查找成功");
            res.add("status",1);
        }else{
            res.add("msg","查找失败");
            res.add("status",0);
        }
        return true;
    }
}
