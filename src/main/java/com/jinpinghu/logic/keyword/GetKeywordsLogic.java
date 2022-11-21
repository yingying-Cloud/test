package com.jinpinghu.logic.keyword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbKeyword;
import com.jinpinghu.db.dao.TbKeywordDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetKeywordsLogic extends BaseZLogic {
	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		
		TbKeywordDao tbKeywordDao = new TbKeywordDao(em);
		
		List<TbKeyword> list = tbKeywordDao.findAll();
		if(list==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		Map<String, Object> tMap;
		for (int i = 0; i < list.size(); i++) {
			TbKeyword keyword = list.get(i);
			tMap = new HashMap<>();
			tMap.put("id", keyword.getId());
			tMap.put("name", keyword.getName());
			result.add(tMap);
		}
		res.add("result", result);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}
}
