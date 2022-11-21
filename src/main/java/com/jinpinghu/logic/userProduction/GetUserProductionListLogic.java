package com.jinpinghu.logic.userProduction;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.bean.TbEnterpriseZero;
import com.jinpinghu.db.dao.TbEnterpriseDao;
import com.jinpinghu.db.dao.TbEnterpriseUserProductionInfoDao;
import com.jinpinghu.db.dao.TbEnterpriseZeroDao;
import com.jinpinghu.db.dao.TbPlantProtectionOrderDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.db.dao.TbToolOrderDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.GetEnterpriseListParam;
import com.jinpinghu.logic.userProduction.param.GetUserProductionListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetUserProductionListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetUserProductionListParam myParam = (GetUserProductionListParam)logicParam;
		String name=myParam.getName();
		String dscd = myParam.getDscd();
		Integer enterpriseId = myParam.getEnterpriseId();
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		
		TbEnterpriseUserProductionInfoDao tfDao = new TbEnterpriseUserProductionInfoDao(em);
		
		Integer count = tfDao.getUserProductionListCount(enterpriseId,name, dscd);
		if(count==0||count==null){
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String,Object>> enterpiseList = tfDao.getUserProductionList(enterpriseId, name, dscd, nowPage, pageCount);
		
		res.add("status", 1);
		res.add("msg", "操作成功").add("result", enterpiseList).add("allCounts", count);
		return true;
	}
}
