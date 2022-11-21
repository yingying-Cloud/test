package com.jinpinghu.logic.statistic;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.GetToolEnterpriseListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetToolEnterpriseListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToolEnterpriseListParam myParam = (GetToolEnterpriseListParam)logicParam;
		String type = myParam.getType();
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		Integer count = enterpriseDao.findToolByAllCount(type);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String, Object>> list = enterpriseDao.findToolByAll(type,nowPage,pageCount);
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", list).add("allCounts", count);
		return true;
	}
}
