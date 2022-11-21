package com.jinpinghu.logic.order;

import com.jinpinghu.db.bean.TbLinkOrderInfo;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbLinkOrderInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.GetTbLinkOrderInfoParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetTbLinkOrderInfoLogic  extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        GetTbLinkOrderInfoParam myParam=(GetTbLinkOrderInfoParam)logicParam;
        Integer id=myParam.getId();
        TbFileDao tfDao = new TbFileDao(em);
        TbLinkOrderInfoDao tbLinkOrderInfoDao=new TbLinkOrderInfoDao(em);
        Object[] tbLinkOrderInfo=tbLinkOrderInfoDao.findInfoById(id);
        Map<String,Object> map=null;
        if (tbLinkOrderInfo!=null){
            map=new HashMap<>();
            map.put("id",tbLinkOrderInfo[0]);
            map.put("enterpriseId",tbLinkOrderInfo[1]);
            map.put("zoneName",tbLinkOrderInfo[2]);
            map.put("name",tbLinkOrderInfo[3]);
            map.put("creditCode",tbLinkOrderInfo[4]);
            map.put("legalPerson",tbLinkOrderInfo[5]);
            map.put("legalPersonIdcard",tbLinkOrderInfo[6]);
            map.put("linkPeople",tbLinkOrderInfo[7]);
            map.put("linkMobile",tbLinkOrderInfo[8]);
            map.put("address",tbLinkOrderInfo[9]);
            map.put("inputTime",tbLinkOrderInfo[10]);
            map.put("area",tbLinkOrderInfo[11]);
            map.put("type",tbLinkOrderInfo[12]);
            List<Map<String, Object>> tfs =tfDao.getMapByOrderInfoId(Integer.valueOf(tbLinkOrderInfo[0].toString()));
            map.put("file", tfs);
            
            res.add("result",map);
            res.add("msg","成功");
            res.add("status",1);
        }else {
            res.add("msg","失败");
            res.add("status",1);
        }
        return true;
    }
}
