package com.jinpinghu.logic.mechine;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbMechineTypeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.mechine.param.GetMechineTypeListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetMechineTypeListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetTypeListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetMechineTypeListParam myParam=(GetMechineTypeListParam)GetTypeListParam;
		Integer parentId=StringUtil.isNullOrEmpty(myParam.getParentId())?null:Integer.valueOf(myParam.getParentId());
		
		TbMechineTypeDao tbMechineTypeDao=new TbMechineTypeDao(em);
		
		List<Map<String, Object>> list=tbMechineTypeDao.getListByParentId(parentId);
		
		res.add("status", 1)
		.add("result", list)
		.add("msg", "操作成功！");
		return true;
	}

}
