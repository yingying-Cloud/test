package com.jinpinghu.logic.mechine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbType;
import com.jinpinghu.db.dao.TbTypeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.mechine.param.GetTypeListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetTypeListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetTypeListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetTypeListParam myParam=(GetTypeListParam)GetTypeListParam;
		Integer type=StringUtil.isNullOrEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		
		TbTypeDao tbTypeDao=new TbTypeDao(em);
		
		Integer count=tbTypeDao.getListCountByType(type);
		if(count!=null&&count>0){
			List<TbType> list=tbTypeDao.getListByType(type);
			
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			Map<String,Object> map;
			for(TbType tbType:list){
				map=new HashMap<String,Object>();
				map.put("id", tbType.getId());
				map.put("name", tbType.getName());
				result.add(map);
			}
			res.add("status", 1)
			.add("allCounts", count)
			.add("result", result)
			.add("msg", "操作成功！");
			return true;
		}
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}
	 // 身份认证
	@Override
   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
		return true;
	}
	
	// 参数检查
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em)  throws Exception {
		return true;
	}
}
