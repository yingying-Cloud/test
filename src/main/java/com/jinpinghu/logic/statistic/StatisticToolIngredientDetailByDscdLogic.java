package com.jinpinghu.logic.statistic;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.StatisticToolIngredientByDscdParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticToolIngredientDetailByDscdLogic extends BaseZLogic {

	public StatisticToolIngredientDetailByDscdLogic() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StatisticToolIngredientByDscdParam myParam = (StatisticToolIngredientByDscdParam)logicParam;
		String selectAll = myParam.getSelectAll();
		String isExport = myParam.getIsExport();
		String startMonth = myParam.getStartMonth();
		String endMonth = myParam.getEndMonth();
		String dscd = myParam.getDscd();
		String name = myParam.getName();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		StatisticDao statisticDao = new StatisticDao(em);
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		Integer count = statisticDao.statisticToolIngredientsDetailByDscdCount(selectAll,permissionEnterpriseIdList,null,dscd,startMonth,endMonth,name);
		List<Map<String, Object>> resultList = statisticDao.statisticToolIngredientsDetailByDscd(selectAll,permissionEnterpriseIdList,null,dscd,startMonth,endMonth,name,nowPage,pageCount);
		
		res.add("status", 1).add("msg", "操作成功").add("result", resultList).add("allCounts", count);
		return true;
	}
}
