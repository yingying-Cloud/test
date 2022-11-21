package com.jinpinghu.logic.enterprise;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseBrandListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetEnterpriseBrandListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetEnterpriseBrandListParam myParam = (GetEnterpriseBrandListParam)logicParam;
		String name = myParam.getName();
		String enterpriseId = myParam.getEnterpriseId();
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		TbEnterpriseDao tbEnterpriseDao = new TbEnterpriseDao(em);
		List<Map<String,Object>> enterpriseBrandList = tbEnterpriseDao.getEnterpriseBrandList(name, enterpriseId, nowPage, pageCount);
		Integer count = tbEnterpriseDao.getEnterpriseBrandListCount(name,enterpriseId);
		
		res.add("result", enterpriseBrandList);
		res.add("allCounts", count);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
