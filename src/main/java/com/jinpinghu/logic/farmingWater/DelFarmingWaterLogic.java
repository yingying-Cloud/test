package com.jinpinghu.logic.farmingWater;

import javax.persistence.EntityManager;
import com.jinpinghu.db.bean.TbResCropFarmingWater;
import com.jinpinghu.db.dao.TbResCropFarmingWaterDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelFarmingWaterLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)SimpleParam;
		String[] ids=myParam.getId().split(",");
		
		TbResCropFarmingWaterDao tbResCropFarmingWaterDao=new TbResCropFarmingWaterDao(em);
		TbResCropFarmingWater TbResCropFarmingWater=null;
		
		for(String id:ids){
			TbResCropFarmingWater=tbResCropFarmingWaterDao.findById(Integer.valueOf(id));
			TbResCropFarmingWater.setDelFlag(1);
			tbResCropFarmingWaterDao.update(TbResCropFarmingWater);
		}

		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}

}
