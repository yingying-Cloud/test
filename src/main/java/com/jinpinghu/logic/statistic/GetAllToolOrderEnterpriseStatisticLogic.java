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

public class GetAllToolOrderEnterpriseStatisticLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetAllToolOrderStatisticParam myParam = (GetAllToolOrderStatisticParam)logicParam;
		String endTime = myParam.getEndTime();
		String startTime = myParam.getStartTime();
		Integer enterpriseId = StringUtils.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String name = myParam.getName();
		String selectAll = myParam.getSelectAll();
		String dscd = myParam.getDscd();
		
		if (!StringUtils.isNullOrEmpty(dscd)) {
			if ("0000000000".equals(dscd.substring(2, 12))) {
				dscd = dscd.substring(0,2)+"%";
			}else if ("00000000".equals(dscd.substring(4, 12))) {
				dscd = dscd.substring(0,4)+"%";
			}else if ("000000".equals(dscd.substring(6, 12))) {
				dscd = dscd.substring(0,6)+"%";
			}else if ("000".equals(dscd.substring(9, 12))) {
				dscd = dscd.substring(0,9)+"%";
			}
		}
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		StatisticDao statisticDao = new StatisticDao(em);
		Integer count = statisticDao.getAllOrderEnterpriseCountCount(enterpriseId, startTime, endTime,name,selectAll,permissionEnterpriseIdList,dscd);
		if(count==null||count==0) {
			res.add("status", 1);
			res.add("msg", "无数据");
			return true;
		}
		List<Map<String, Object>> map = statisticDao.getAllOrderEnterpriseCount(enterpriseId, startTime, endTime,nowPage,pageCount,name,selectAll,permissionEnterpriseIdList,dscd);
		res.add("result", map);
		res.add("allCounts", count);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
