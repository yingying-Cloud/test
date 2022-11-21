package com.jinpinghu.logic.plant;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbBrand;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.db.dao.TbResBrandFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelBrandLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)SimpleParam;
		String[] ids=myParam.getId().split(",");
		
		TbResBrandFileDao tbResBrandFileDao=new TbResBrandFileDao(em);
		
		for(String id:ids){
			tbResBrandFileDao.DelResByBrandId(Integer.valueOf(id));
		}

		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}

}
