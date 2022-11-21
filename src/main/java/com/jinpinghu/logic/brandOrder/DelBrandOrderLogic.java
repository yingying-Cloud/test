package com.jinpinghu.logic.brandOrder;

import com.jinpinghu.db.bean.TbBrandOrder;
import com.jinpinghu.db.dao.TbBrandOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.brandOrder.param.DelBrandOrderParam;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.List;

public class DelBrandOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DelBrandOrderParam myParam = (DelBrandOrderParam)logicParam;
		String id=myParam.getId();
	
		TbBrandOrderDao toDao = new TbBrandOrderDao(em);
		List<String> list = Arrays.asList(id.split(","));
		for(String i:list) {
			TbBrandOrder to = toDao.findById(Integer.valueOf(i));
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
