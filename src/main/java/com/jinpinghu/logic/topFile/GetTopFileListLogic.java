package com.jinpinghu.logic.topFile;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbTopFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.topFile.param.GetTopFileListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetTopFileListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetTopFileListParam myParam = (GetTopFileListParam)logicParam;
		String type = myParam.getType();
		TbTopFileDao tbMechineTypeDao=new TbTopFileDao(em);
		List<Map<String, Object>> list=tbMechineTypeDao.getListByType(type);
		
		res.add("status", 1)
		.add("result", list)
		.add("msg", "操作成功！");
		return true;
	}

}
