package com.jinpinghu.logic.sellBrandRecord;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbSellBrandRecord;
import com.jinpinghu.db.dao.TbSellBrandRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellBrandRecord.param.DelSellBrandRecordParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelSellBrandRecordLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelSellBrandRecordParam myParam =(DelSellBrandRecordParam)logicParam;
		String ids = myParam.getIds();
		TbSellBrandRecordDao sellBrandDao = new TbSellBrandRecordDao(em);
		Integer count =0;
		if(!StringUtils.isEmpty(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				TbSellBrandRecord sellBrand = sellBrandDao.findById(Integer.valueOf(id));
				if(sellBrand!=null) {
					sellBrand.setDelFlag(1);
					sellBrandDao.update(sellBrand);
					count++;
				}
			}
		}
		res.add("status", 1);
		res.add("msg", "成功删除"+count+"个");
		return true;
	}
}
