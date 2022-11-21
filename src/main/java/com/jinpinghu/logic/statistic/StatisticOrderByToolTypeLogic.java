package com.jinpinghu.logic.statistic;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.StatisticOrderByToolTypeParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticOrderByToolTypeLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StatisticOrderByToolTypeParam myParam = (StatisticOrderByToolTypeParam)logicParam;
		String dscd = myParam.getDscd();
		String month = myParam.getMonth();
		String selectAll = myParam.getSelectAll();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		
		String firstDayOfMonth = "";
		String lastDayOfMonth = "";
		if (!StringUtils.isEmpty(month)) {
			firstDayOfMonth = DateTimeUtil.firstDayOfMonth(month);
			lastDayOfMonth = DateTimeUtil.lastDayOfMonth(month);
		}
		
		if (!StringUtils.isEmpty(dscd)) {
			if ("0000000000".equals(dscd.substring(2, 12))) {
				dscd = dscd.substring(0, 2)+"%";
			}else if ("00000000".equals(dscd.substring(4, 12))) {
				dscd = dscd.substring(0, 4)+"%";
			}else if ("000000".equals(dscd.substring(6, 12))) {
				dscd = dscd.substring(0, 6)+"%";
			}else if ("000".equals(dscd.substring(9, 12))) {
				dscd = dscd.substring(0, 9)+"%";
			}
		}
		
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		
		StatisticDao statisticDao = new StatisticDao(em);
		
		List<Map<String, Object>> resultList = statisticDao.statisticOrderByToolType(selectAll, permissionEnterpriseIdList, dscd, startTime, endTime,firstDayOfMonth,lastDayOfMonth);
		
		res.add("status", 1).add("msg", "操作成功").add("result", resultList);
		return true;
	}

}
