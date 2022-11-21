package com.jinpinghu.logic.inspection;

import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbInspectionItemDao;
import com.jinpinghu.db.dao.TbInspectionTypeDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetTypeInfo extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;
		Integer id = param.getId();
		

		TbInspectionTypeDao dao = new TbInspectionTypeDao(em);
		Map<String, Object> result = dao.getTypeInfo(id);
		
		res.add("result", result);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
