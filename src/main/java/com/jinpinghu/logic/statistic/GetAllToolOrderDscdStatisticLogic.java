package com.jinpinghu.logic.statistic;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.GetAllToolOrderStatisticParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetAllToolOrderDscdStatisticLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetAllToolOrderStatisticParam myParam = (GetAllToolOrderStatisticParam)logicParam;
		String selectAll = myParam.getSelectAll();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		String endTime = myParam.getEndTime();
		String startTime = myParam.getStartTime();
		String code = myParam.getCode();
		String productAttributes = myParam.getProductAttributes();
		String toolType = myParam.getToolType();
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		StatisticDao statisticDao = new StatisticDao(em);
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String name = myParam.getName();
		
		Integer count = statisticDao.getAllOrderDscdCount(enterpriseId, startTime, endTime,selectAll,permissionEnterpriseIdList,code,
				productAttributes,toolType,name);
		if(count==null||count==0) {
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		
		List<Map<String, Object>> map = statisticDao.getAllOrderDscd(enterpriseId, startTime, endTime,selectAll,permissionEnterpriseIdList,
				code,productAttributes,toolType,nowPage,pageCount,name);
		res.add("result", map);
		res.add("status", 1);
		res.add("msg", "操作成功");
		res.add("allCounts", count);
		return true;
	}

}
