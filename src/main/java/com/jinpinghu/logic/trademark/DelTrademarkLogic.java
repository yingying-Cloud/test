package com.jinpinghu.logic.trademark;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbTrademark;
import com.jinpinghu.db.dao.TbTrademarkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.trademark.param.DelTrademarkParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelTrademarkLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelTrademarkParam myParam =(DelTrademarkParam)logicParam;
		String ids = myParam.getIds();
		TbTrademarkDao trademarkDao = new TbTrademarkDao(em);
		Integer count =0;
		if(!StringUtils.isEmpty(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				TbTrademark trademark = trademarkDao.findById(Integer.valueOf(id));
				if(trademark!=null) {
					trademark.setDelFlag(1);
					trademarkDao.update(trademark);
					count++;
				}
			}
		}
		res.add("status", 1);
		res.add("msg", "成功删除"+count+"个");
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
