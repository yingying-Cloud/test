package com.jinpinghu.logic.index;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.IndexDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticAllBrandTypeCountLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		IndexDao indexDao = new IndexDao(em);
		List<Map<String,Object>> list = indexDao.statisticAllBrandTypeCount();
		if(list!=null) {
			for(Map<String,Object> map:list) {
				String type =map.containsKey("type")?map.get("type").toString():"";
				if( type.equals("1") ) {
					map.put("type", "种植");
				} else if(type.equals("2")){
					map.put("type", "养殖");
				} else {
					map.put("type", "其他");
				}
			}
		}
		res.add("result", list);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}
