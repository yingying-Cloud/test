package com.jinpinghu.logic.plantProtectionOrder;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbPlantProtectionOrder;
import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.DelOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		DelOrderParam myParam = (DelOrderParam)logicParam;
		String id=myParam.getId();
		
		TbPlantProtectionOrderDao toDao = new TbPlantProtectionOrderDao(em);
		List<String> list = Arrays.asList(id.split(","));
		for(String i:list) {
			TbPlantProtectionOrder to = toDao.findById(Integer.valueOf(i));
			if(to!=null){
				to.setDelFlag(1);
				toDao.update(to);
			}
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}