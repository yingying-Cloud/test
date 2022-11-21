package com.jinpinghu.logic.farmingWater;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbResCropFarmingWaterDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.farmingWater.param.GetFarmingWaterListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetFarmingWaterListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetFarminWaterListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetFarmingWaterListParam myParam=(GetFarmingWaterListParam)GetFarminWaterListParam;
		String addPeople=myParam.getAddPeople();
		String workSn=myParam.getWorkSn();
		String enterpriseId=myParam.getEnterpriseId();
		String endTime=myParam.getEndTime();
		String startTime=myParam.getStartTime();
		Integer nowPage=StringUtil.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtil.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		
		TbResCropFarmingWaterDao tbResCropFarmingWaterDao=new TbResCropFarmingWaterDao(em);
		
		Integer count=tbResCropFarmingWaterDao.getFarminWaterListCount(workSn, addPeople, enterpriseId, startTime, endTime);
		List<Object[]> list=tbResCropFarmingWaterDao.getFarminWaterList(workSn, addPeople, enterpriseId, startTime, endTime, nowPage, pageCount);
		
		if(list!=null){
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			Map<String,Object> map;
			for(Object[] o:list){
				map=new HashMap<String,Object>();
				map.put("id", o[0]);
				map.put("workSn", o[1]);
				map.put("enterpriseName", o[2]);
				map.put("addPeople", o[3]);
				map.put("traffic", o[4]);
				map.put("startIrrigationTime", DateTimeUtil.formatTime2((Date)o[5]));
				map.put("endIrrigationTime", DateTimeUtil.formatTime2((Date)o[6]));
				map.put("waterAmount", o[7]);
				map.put("describe", o[9]);
				map.put("inputTime", DateTimeUtil.formatTime2((Date)o[8]));
				map.put("workId", o[10]);
				map.put("hour", o[11]);
				result.add(map);
			}

			res.add("status", 1)
			.add("allCounts", count)
			.add("result", result)
			.add("msg", "操作成功！");
			return true;
		}
		

		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}
}
