package com.jinpinghu.logic.userProduction;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbEnterpriseUserProductionInfoDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetUserProductionEnterpriseListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetEnterpriseListParam myParam = (GetEnterpriseListParam)logicParam;
		String name=myParam.getName();
		String dscd = myParam.getDscd();
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String userName = myParam.getUserName();
		String userIdCard = myParam.getUserIdCard();
		String enterpriseType = myParam.getEnterpriseType();
		
		dscd = StringUtil.handleArea(dscd);
		String permissionDscd = StringUtil.handlePermissionDscd(loginUser, myParam.getUserId(), em);
		
		
		TbEnterpriseUserProductionInfoDao tfDao = new TbEnterpriseUserProductionInfoDao(em);
		
		Integer count = tfDao.getEnterpiseListCount(name, dscd,permissionDscd,userName,userIdCard,enterpriseType);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String,Object>> enterpiseList = tfDao.getEnterpiseList(name, dscd, nowPage, pageCount,permissionDscd,userName,userIdCard,enterpriseType);
		
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", enterpiseList).add("allCounts", count);
		return true;
	}
}
