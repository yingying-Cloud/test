package com.jinpinghu.logic.plant;

import javax.persistence.EntityManager;
import com.jinpinghu.db.dao.TbWorkCheckDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelCheckLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)SimpleParam;
		String[] ids=myParam.getId().split(",");
		
		TbWorkCheckDao tbWorkCheckDao=new TbWorkCheckDao(em);
		
		for(String id:ids){
			tbWorkCheckDao.delCheckById(Integer.valueOf(id));
		}

		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}
}