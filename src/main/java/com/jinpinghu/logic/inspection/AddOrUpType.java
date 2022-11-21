package com.jinpinghu.logic.inspection;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import com.jinpinghu.db.dao.TbInspectionType;
import com.jinpinghu.db.dao.TbInspectionTypeDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddOrUpType extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;
		Integer id = param.getId();
		String name = param.getName();
		Integer point = param.getPoint();
		
		TbInspectionTypeDao dao = new TbInspectionTypeDao(em);
		TbInspectionType tbInspectionType = null;
		if(id == null){//新增
			tbInspectionType = new TbInspectionType( name, new BigDecimal(point), 0);
			dao.save(tbInspectionType);
		}else{//更新
			tbInspectionType = dao.getTypeById(id);
			tbInspectionType.setPoint(new BigDecimal(point));
			tbInspectionType.setName(name);
			dao.update(tbInspectionType);		}
		
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
