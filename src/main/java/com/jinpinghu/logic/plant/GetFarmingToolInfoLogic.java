package com.jinpinghu.logic.plant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbResCropFarmingToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetFarmingToolInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)SimpleParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());

		TbResCropFarmingToolDao tbResCropFarmingToolDao=new TbResCropFarmingToolDao(em);
		
		Object[] o=tbResCropFarmingToolDao.getFarminToolInfo(id);
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", o[0]);
		map.put("workSn", o[1]);
		map.put("enterpriseName", o[2]);
		map.put("addPeople", o[3]);
		map.put("toolId", o[4]);
		map.put("toolName", o[5]);
		map.put("specification", o[6]);
		map.put("unit", o[7]);
		map.put("num", o[8]);
		map.put("inputTime", DateTimeUtil.formatTime((Date)o[9]));
		map.put("type", o[10]);
		map.put("workId", o[11]);
	
		
		res.add("status", 1)
		.add("result", map)
		.add("msg", "操作成功！");
		return true;
	}

}
