package com.jinpinghu.logic.index;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.IndexDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticAllEnterpriseLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		IndexDao indexDao=new IndexDao(em);
		
		List<Map<String, Object>> list=indexDao.statisticAllEnterprise();
		if(list!=null){
			res.add("status", 1).add("result", list).add("msg", "操作成功");
		}else{
			res.add("status", 1).add("msg", "无数据");
		}
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}

	