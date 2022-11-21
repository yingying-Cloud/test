package com.jinpinghu.logic.index;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.IndexDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.index.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticAllCompanyOrderLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam = (SimpleParam)logicParam;
		IndexDao indexDao=new IndexDao(em);
		String name = myParam.getName();
		Integer nowPage=StringUtil.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtil.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		Integer count = indexDao.statisticAllCompanyOrderCount(name);
		List<Map<String, Object>> list=indexDao.statisticAllCompanyOrder(nowPage,pageCount,name);
		if(list!=null){
			res.add("status", 1).add("result", list).add("msg", "操作成功").add("allCounts", count);
		}else{
			res.add("status", 1).add("msg", "无数据").add("allCounts", count);
		}
		return true;
	}
	@Override
	   protected boolean auth(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em)  throws Exception{
				return true;
			}
}

	