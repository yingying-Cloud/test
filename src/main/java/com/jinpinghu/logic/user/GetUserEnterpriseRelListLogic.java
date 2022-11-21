package com.jinpinghu.logic.user;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbEnterpriseDataPermissionDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.user.param.GetUserEnterpriseRelListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetUserEnterpriseRelListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetUserEnterpriseRelListParam myParam = (GetUserEnterpriseRelListParam)logicParam;
		Integer enterpriseId = StringUtils.isEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer userTabId = StringUtils.isEmpty(myParam.getUserTabId())?null:Integer.valueOf(myParam.getUserTabId());
		String name = myParam.getName();
		String isIn = myParam.getIsIn();
		String dscd = myParam.getDscd();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		TbEnterpriseDataPermissionDao tbResUserEnterpriseRelDao = new TbEnterpriseDataPermissionDao(em);
		List<Map<String,Object>> list = tbResUserEnterpriseRelDao.findInEnterprise(userTabId,name,isIn,dscd,nowPage,pageCount);
		Integer count = tbResUserEnterpriseRelDao.findInEnterpriseCount(userTabId,name,isIn,dscd);
		res.add("status", 1).add("msg", "操作成功").add("result", list).add("allCounts", count);
		return true;
	}

}
