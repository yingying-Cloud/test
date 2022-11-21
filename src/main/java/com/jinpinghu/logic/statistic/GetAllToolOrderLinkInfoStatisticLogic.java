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

public class GetAllToolOrderLinkInfoStatisticLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetAllToolOrderStatisticParam myParam = (GetAllToolOrderStatisticParam)logicParam;
		String endTime = myParam.getEndTime();
		String startTime = myParam.getStartTime();
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer type = StringUtils.isNullOrEmpty(myParam.getType())?null:Integer.valueOf(myParam.getType());
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String name = myParam.getName();
		String selectAll = myParam.getSelectAll();
		String toolId = myParam.getToolId();
		String code = myParam.getCode();
		String productAttributes = myParam.getProductAttributes();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		StatisticDao statisticDao = new StatisticDao(em);
		
		Integer count = statisticDao.getAllOrderLinkInfoCountCount(enterpriseId, startTime, endTime,type,name,selectAll,
				permissionEnterpriseIdList,toolId,code,productAttributes);
		if(count==null||count==0) {
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String, Object>> map = statisticDao.getAllOrderLinkInfoCount(enterpriseId, startTime, endTime,type,nowPage,
				pageCount,name,selectAll,permissionEnterpriseIdList,toolId,code,productAttributes);
		res.add("result", map);
		res.add("status", 1);
		res.add("allCounts", count);
		res.add("msg", "操作成功");
		return true;
	}

}
