package com.jinpinghu.logic.order;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbToolOrder;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.order.param.DelOrderParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DelOrderParam myParam = (DelOrderParam)logicParam;
		String id=myParam.getId();
	
		TbToolOrderDao toDao = new TbToolOrderDao(em);
		List<String> list = Arrays.asList(id.split(","));
		for(String i:list) {
			TbToolOrder to = toDao.findById(Integer.valueOf(i));
			if(to!=null){
				to.setDelFlag(1);
				toDao.update(to);
			}
		}
		res.add("msg", "删除成功");
		res.add("status", 1);
		return true;
	}

}
