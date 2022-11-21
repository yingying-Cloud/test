package com.jinpinghu.logic.sellOrder;

import com.jinpinghu.db.bean.TbSellOrder;
import com.jinpinghu.db.dao.TbSellOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellOrder.param.DelSellOrderParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

public class DelSellOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DelSellOrderParam myParam = (DelSellOrderParam)logicParam;
		String id=myParam.getId();
	
		TbSellOrderDao toDao = new TbSellOrderDao(em);
		List<String> list = Arrays.asList(id.split(","));
		for(String i:list) {
			TbSellOrder to = toDao.findById(Integer.valueOf(i));
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
