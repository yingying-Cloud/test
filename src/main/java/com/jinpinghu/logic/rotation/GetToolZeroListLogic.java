package com.jinpinghu.logic.rotation;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbToolZeroDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.rotation.param.GetToolZeroListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetToolZeroListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolZeroListParam myParam = (GetToolZeroListParam)logicParam;
		String ids = myParam.getIds();
		List<String> id =null;
		if(!StringUtils.isNullOrEmpty(ids)) {
			id = Arrays.asList(ids.split(","));
		}
		TbToolZeroDao tbToolZeroDao = new TbToolZeroDao(em);
		List<Map<String,Object>> list = tbToolZeroDao.findAll(id);
		res.add("result", list);
		res.add("status", 1);
		res.add("msg", "操作成功");
		
		return true;
	}

}
