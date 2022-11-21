package com.jinpinghu.logic.inspection;

import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import com.jinpinghu.db.dao.TbInspectionDao;
import com.jinpinghu.logic.BaseZLogic;
import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelInspectionLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;
		List<String> ids = Arrays.asList(param.getIds().split(","));

		TbInspectionDao dao = new TbInspectionDao(em);
		dao.delInspections(ids);
		//删除这条监管记录的扣分记录
		dao.delInspectionPoint(ids);
		
		
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
