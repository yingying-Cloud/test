package com.jinpinghu.logic.index;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.dao.IndexDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.index.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticAllCompanyOrderMonthLogic extends  BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam = (SimpleParam)logicParam;
		String year = myParam.getYear();
		IndexDao indexDao = new IndexDao(em);
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		for(int i=1;i<13;i++) {
			String month = year+"-"+String.format("%02d", i);
			Integer nowMonth = DateTimeUtil.getNowMonth();
			Integer nowYear = DateTimeUtil.getNowYear();
			if(i<=nowMonth||Integer.valueOf(year)<nowYear) {
				Map<String, Object> map = indexDao.statisticAllCompanyOrderMonth(month);
				map.put("time", month);
				list.add(map);
			}
		}
//		List<Map<String, Object>> list = indexDao.statisticAllCompanyOrderMonth(month);
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
