package com.jinpinghu.logic.agriculturalGreenhouses;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbAgriculturalGreenhousesDao2;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalGreenhouses.param.GetGreenhouseGeomListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetGreenhouseGeomListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetGreenhouseGeomListParam myParam = (GetGreenhouseGeomListParam)logicParam;
		Integer enterpriseId = myParam.getEnterpriseId();
		
		TbAgriculturalGreenhousesDao2 greenhousesDao = new TbAgriculturalGreenhousesDao2(em);
		
		List<Map<String,Object>> resultList = greenhousesDao.getGreenhouseGeomList(enterpriseId);
		
		res.add("status", 1).add("msg", "操作成功").add("result", resultList);
		return true;
	}

}
