package com.jinpinghu.logic.enterprise;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetToEnterpriseListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetToEnterpriseListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetToEnterpriseListParam myParam = (GetToEnterpriseListParam)logicParam;
		Integer enterpriseId =StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String name=myParam.getName();
		Integer enterpriseType = StringUtils.isNullOrEmpty(myParam.getEnterpriseType())?null:Integer.valueOf(myParam.getEnterpriseType());
		String dscd = myParam.getDscd();
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		TbEnterpriseDao enterpriseDao = new TbEnterpriseDao(em);
		List<Map<String,Object>> list = enterpriseDao.getSelfEnterpriseList(enterpriseId);
		List<Map<String,Object>> result = enterpriseDao.getToEnterpriseList(enterpriseId, name, enterpriseType, dscd, pageCount, nowPage);
		list.addAll(result);
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", list);
		return true;
	}

}
