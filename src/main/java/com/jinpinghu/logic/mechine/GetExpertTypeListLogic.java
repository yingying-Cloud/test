package com.jinpinghu.logic.mechine;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbTypeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.mechine.param.GetTypeListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetExpertTypeListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetTypeListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetTypeListParam myParam=(GetTypeListParam)GetTypeListParam;
		Integer type=StringUtil.isNullOrEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		
		TbTypeDao tbTypeDao=new TbTypeDao(em);
		
		Integer count=tbTypeDao.getListCountByType(type);
		if(count!=null&&count>0){
			List<Map<String, Object>> list=tbTypeDao.getExpertTypeList(type);
			res.add("status", 1)
			.add("allCounts", count)
			.add("result", list)
			.add("msg", "操作成功！");
			return true;
		}
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}

}
