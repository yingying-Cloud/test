package com.jinpinghu.logic.mechine;

import javax.persistence.EntityManager;
import com.jinpinghu.db.dao.TbResMechineFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.mechine.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelMechineLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)SimpleParam;
		String[] ids=myParam.getId().split(",");
		
		TbResMechineFileDao tbResMechineFileDao=new TbResMechineFileDao(em);
		
		for(String id:ids){
			tbResMechineFileDao.DelResByMechineId(Integer.valueOf(id));
		}

		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}

}
