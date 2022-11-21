package com.jinpinghu.logic.zone;

import com.jinpinghu.db.bean.TbZone;
import com.jinpinghu.db.dao.TbZoneDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.zone.param.AddOrUpdateZoneParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;

public class AddOrUpdateZoneLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        AddOrUpdateZoneParam myParam=(AddOrUpdateZoneParam)logicParam;
        TbZone tbZone=myParam.getTbZone();
        if (tbZone!=null){
            Integer id=tbZone.getId();
            TbZoneDao tbZoneDao=new TbZoneDao(em);
            TbZone tz=tbZoneDao.findById(id);
            Integer result=null;
            if (tz!=null&&id!=null){
                result=tbZoneDao.update(tbZone);
                if (result>0){
                    res.add("msg","更新成功");
                }else{
                    res.add("msg","更新失败");
                }
            }else{
                result=tbZoneDao.save(tbZone);
                if (result>0){
                    res.add("msg","添加成功");
                }else{
                    res.add("msg","添加失败");
                }
            }
            res.add("status",result);
        }
        return true;
    }
}
