package com.jinpinghu.logic.warehouse;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPlantWarehouseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.warehouse.param.GetPlantWarehouseInfoParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetPlantWarehouseInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetPlantWarehouseListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetPlantWarehouseInfoParam myParam=(GetPlantWarehouseInfoParam)GetPlantWarehouseListParam;
		Integer id=StringUtils.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		TbPlantWarehouseDao rdao=new TbPlantWarehouseDao(em);
		TbFileDao tfdao=new TbFileDao(em);
		Map<String, Object> warehouseInfo = rdao.getWarehouseInfo(id);
		List<Map<String, Object>> file = tfdao.findByPlantWarehouseIdMap(id);
		warehouseInfo.put("file", file);
		res.add("status", 1)
		.add("msg", "操作成功！")
		.add("result", warehouseInfo);
		return true;
	}
}
