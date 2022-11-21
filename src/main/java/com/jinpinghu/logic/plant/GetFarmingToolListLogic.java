package com.jinpinghu.logic.plant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbResCropFarmingToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.GetFarmingToolListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetFarmingToolListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam GetFarminToolListParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		GetFarmingToolListParam myParam=(GetFarmingToolListParam)GetFarminToolListParam;
		String toolName=myParam.getToolName();
		String workSn=myParam.getWorkSn();
		String enterpriseId=myParam.getEnterpriseId();
		String type=myParam.getType();
		String endTime=myParam.getEndTime();
		String startTime=myParam.getStartTime();
		Integer nowPage=StringUtil.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount=StringUtil.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
		String code = myParam.getCode();
		String isEffective = myParam.getIsEffective();
		
		TbResCropFarmingToolDao tbResCropFarmingToolDao=new TbResCropFarmingToolDao(em);
		
		Integer count=tbResCropFarmingToolDao.getFarminToolListCount(workSn, toolName, enterpriseId, type, startTime, endTime,code,isEffective);
		List<Object[]> list=tbResCropFarmingToolDao.getFarminToolList(workSn, toolName, enterpriseId, type, startTime, endTime, nowPage, pageCount,code,isEffective);
		
		if(list!=null){
			List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
			Map<String,Object> map;
			for(Object[] o:list){
				map=new HashMap<String,Object>();
				map.put("id", o[0]);
				map.put("workSn", o[1]);
				map.put("enterpriseName", o[2]);
				map.put("addPeople", o[3]);
				map.put("toolId", o[4]);
				map.put("toolName", o[5]);
				map.put("specification", o[6]);
				map.put("unit", o[7]);
				map.put("num", o[8]);
				map.put("inputTime", DateTimeUtil.formatTime((Date)o[9]));
				map.put("type", o[10]);
				map.put("workId", o[11]);
				map.put("typeName", o[12]);
				map.put("productionUnits", o[13]);
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
	
	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetFarmingToolListParam myParam=(GetFarmingToolListParam)logicParam;
		String enterpriseId=myParam.getEnterpriseId();
		if(StringUtils.isEmpty(enterpriseId)) {
			res.add("status", 1)
			.add("allCounts", 0)
			.add("result", new ArrayList<>(0))
			.add("msg", "操作成功！");
			return false;
		}
		
		return true;
	}

}
