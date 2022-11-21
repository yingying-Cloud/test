package com.jinpinghu.logic.statistic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.aliyun.oss.common.utils.StringUtils;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.EnterpriseDataPermissionUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.StatisticDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.statistic.param.RecStatByTimeParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class RecStatByTimeLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam RecStatByTimeParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		RecStatByTimeParam myParam=(RecStatByTimeParam)RecStatByTimeParam;
		
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String startTime=myParam.getStartTime();
		String endTime=myParam.getEndTime();
		String year= StringUtils.isNullOrEmpty(myParam.getYear())?DateTimeUtil.getYear(new Date())+"":myParam.getYear();
		String selectAll = myParam.getSelectAll();
		List<Integer> permissionEnterpriseIdList = EnterpriseDataPermissionUtil.getPermissionEnterpriseIdList(loginUser.getId(), em);
		
		StatisticDao statisticDao=new StatisticDao(em);
		
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = statisticDao.recStatByTime(enterpriseId, year+"-01",year+"-03",selectAll,permissionEnterpriseIdList);
		map.put("startTime","1月");
		map.put("endTime","3月");
		Map<String, Object> map1 = statisticDao.recStatByTime(enterpriseId, year+"-04",year+"-06",selectAll,permissionEnterpriseIdList);
		map1.put("startTime","4月");
		map1.put("endTime","6月");
		Map<String, Object> map2 = statisticDao.recStatByTime(enterpriseId,year+"-07",year+"-09",selectAll,permissionEnterpriseIdList);
		map2.put("startTime","7月");
		map2.put("endTime","9月");
		Map<String, Object> map3 = statisticDao.recStatByTime(enterpriseId, year+"-10",year+"-12",selectAll,permissionEnterpriseIdList);
		map3.put("startTime","10月");
		map3.put("endTime","12月");
		mapList.add(map);
		mapList.add(map1);
		mapList.add(map2);
		mapList.add(map3);
		res.add("result", mapList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		
//		List<Object[]> obList=statisticDao.recStatByTime(enterpriseId, startTime, endTime);
//		
//		List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
//		Map<String,Object> map;
//		Double h9=0.0,h12=0.0,h15=0.0,h18=0.0;
//		if(obList!=null){
//			
//			for(Object[] ob: obList){
//				Double number=Double.valueOf(ob[0].toString());
//				Double hour=Double.valueOf(ob[1].toString());
//				if(hour<12){
//					h9+=number;
//				}else if(12<=hour&&hour<15){
//					h12+=number;
//				}else if(15<=hour&&hour<18){
//					h15+=number;
//				}else if(18<=hour){
//					h18+=number;
//				}
//			}
//			Double[] hour={h9,h12,h15,h18};
//			int startT=9;
//			for(Double h:hour){
//				map=new HashMap<String,Object>();
//				map.put("number", h);
//				if(startT!=9){
//					map.put("hour", startT+":00-"+(startT+=3)+":00");
//				}else{
//					map.put("hour", "09:00-"+(startT+=3)+":00");
//				}
//				result.add(map);
//			}
//			res.add("status", 1).add("result", result).add("msg", "操作成功");
//		}else{
//			Double[] hour={h9,h12,h15,h18};
//			int startT=9;
//			for(Double h:hour){
//				map=new HashMap<String,Object>();
//				map.put("number", h);
//				if(startT!=9){
//					map.put("hour", startT+":00-"+(startT+=3)+":00");
//				}else{
//					map.put("hour", "09:00-"+(startT+=3)+":00");
//				}
//				result.add(map);
//			}
//			res.add("status", 1).add("result", result).add("msg", "操作成功");
//		}
		return true;
	}

}