package com.jinpinghu.logic.inspection;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbInspectionTypeDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelTypeLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;
		List<String> ids = Arrays.asList(param.getIds().split(","));

		TbInspectionTypeDao dao = new TbInspectionTypeDao(em);
		dao.delType(ids);
		
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
