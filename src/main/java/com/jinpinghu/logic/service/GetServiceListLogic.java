package com.jinpinghu.logic.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbServiceDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.service.param.GetServiceListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetServiceListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetServiceListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetServiceListParam myParam=(GetServiceListParam)GetServiceListParam;
		
		Integer type=StringUtil.isNullOrEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		
		
		TbServiceDao tbServiceDao=new TbServiceDao(em);
		Integer count=tbServiceDao.findServiceListCountByType(type);
		if(count!=null){
			List<Map<String,Object>> list = tbServiceDao.findServiceListByType(type);

			res.add("status", 1)
			.add("result", list)
			.add("msg", "操作成功");
			return true;
		}
		
		res.add("status", 1)
		.add("result", "")
		.add("msg", "无数据");
		return true;
	}
	 // 身份认证
		@Override
		protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
			return true;
		}
}
