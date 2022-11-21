package com.jinpinghu.logic.inspection;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbInspectionItem;
import com.jinpinghu.db.dao.TbInspectionItemDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddOrUpItem extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {

		InspectionParam param = (InspectionParam)logicParam;
		Integer id = param.getId();
		String name = param.getName();
		Integer type = param.getType();
		
		TbInspectionItemDao dao = new TbInspectionItemDao(em);
		TbInspectionItem tbInspectionItem = null;
		if(id == null){//新增
			tbInspectionItem = new TbInspectionItem( name, type, 0);
			dao.save(tbInspectionItem);
		}else{//更新
			tbInspectionItem = dao.getItemById(id);
			tbInspectionItem.setInspectionType(type);
			tbInspectionItem.setName(name);
			dao.update(tbInspectionItem);
		}
		
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
