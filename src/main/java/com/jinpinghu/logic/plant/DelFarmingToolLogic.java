package com.jinpinghu.logic.plant;

import javax.persistence.EntityManager;
import com.jinpinghu.db.bean.TbResCropFarmingTool;
import com.jinpinghu.db.dao.TbResCropFarmingToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelFarmingToolLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)SimpleParam;
		String[] ids=myParam.getId().split(",");
		
		TbResCropFarmingToolDao tbResCropFarmingToolDao=new TbResCropFarmingToolDao(em);
		TbResCropFarmingTool TbResCropFarmingTool=null;
		
		for(String id:ids){
			TbResCropFarmingTool=tbResCropFarmingToolDao.findById(Integer.valueOf(id));
			TbResCropFarmingTool.setDelFlag(1);
			tbResCropFarmingToolDao.update(TbResCropFarmingTool);
		}

		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}

}
