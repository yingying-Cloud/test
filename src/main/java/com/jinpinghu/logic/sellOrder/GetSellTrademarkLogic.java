package com.jinpinghu.logic.sellOrder;

import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbTrademarkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellOrder.param.GetSellTrademarkParam;
import com.mysql.cj.util.StringUtils;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.persistence.EntityManager;
import java.util.List;

public class GetSellTrademarkLogic extends BaseZLogic {
    @Override
    protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
        GetSellTrademarkParam myParam = (GetSellTrademarkParam)logicParam;
        Integer sellId= StringUtils.isNullOrEmpty(myParam.getSellId())?null:Integer.valueOf(myParam.getSellId());
        Integer carId= StringUtils.isNullOrEmpty(myParam.getCarId())?null:Integer.valueOf(myParam.getCarId());
        TbBrandDao tbSellDao = new TbBrandDao(em);
        TbTrademarkDao trademarkDao = new TbTrademarkDao(em);
        JSONArray ja = new JSONArray();
        if(sellId!=null) {
            List<Object[]> trademark = tbSellDao.findTrademarkByBrandId(sellId,carId);
            if (trademark != null) {
                for (int i = 0; i < trademark.size(); i++) {
                    Object[] o = trademark.get(i);
                    JSONObject jo = new JSONObject();
                    jo.put("trademarkId", o[0]);
                    jo.put("trademarkName", o[1]);
                    jo.put("num", o[2]);
                    jo.put("carNum", o[3]);
                    ja.add(jo);
                }
            }
        }
        res.add("status",1).add("msg","操作成功").add("result",ja);
        return true;
    }
}
