package com.jinpinghu.logic.farmingWater;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResCropFarmingWaterDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetFarmingWaterInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)SimpleParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());

		TbResCropFarmingWaterDao tbResCropFarmingWaterDao=new TbResCropFarmingWaterDao(em);
		Object[] o=tbResCropFarmingWaterDao.getFarminWaterInfo(id);
		
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("id", o[0]);
		map.put("workSn", o[1]);
		map.put("enterpriseName", o[2]);
		map.put("addPeople", o[3]);
		map.put("traffic", o[4]);
		map.put("startIrrigationTime", DateTimeUtil.formatTime2((Date)o[5]));
		map.put("endIrrigationTime", DateTimeUtil.formatTime2((Date)o[6]));
		map.put("waterAmount", o[7]);
		map.put("describe", o[9]);
		map.put("inputTime", DateTimeUtil.formatTime2((Date)o[8]));
		map.put("workId", o[10]);
		TbFileDao tfDao = new TbFileDao(em);
		List<Map<String, Object>> tfs =tfDao.findByResCropFarmingWaterIdMap(Integer.valueOf(o[0].toString()));
		map.put("file", tfs);
		
		res.add("status", 1)
		.add("result", map)
		.add("msg", "操作成功！");
		return true;
	}

}
