package com.jinpinghu.logic.inspection;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbInspectionItemDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelItemLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;
		List<String> ids = Arrays.asList(param.getIds().split(","));

		TbInspectionItemDao dao = new TbInspectionItemDao(em);
		dao.delItem(ids);
		
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
