package com.jinpinghu.logic.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbWorkChild;
import com.jinpinghu.db.dao.TbCropFarmingDao;
import com.jinpinghu.db.dao.TbWorkChildDao;
import com.jinpinghu.db.dao.TbWorkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.home.param.StatisticalFarmingByWorkParam;
import com.jinpinghu.common.tools.DateTimeUtil;
import com.jinpinghu.db.bean.TbWork;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class StatisticalFarmingByWorkLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		StatisticalFarmingByWorkParam myParam = (StatisticalFarmingByWorkParam)logicParam;
		Integer workId = StringUtils.isEmpty(myParam.getWorkId()) ? null : Integer.valueOf(myParam.getWorkId());
		
		TbWorkDao workDao = new TbWorkDao(em);
		
		TbWork work = workDao.findById(workId);
		
		if(work == null) {
			res.add("status", -1).add("msg", "该种植计划不存在");
			return false;
		}
		
		TbWorkChildDao workChildDao = new TbWorkChildDao(em);
		TbCropFarmingDao cropFarmingDao = new TbCropFarmingDao(em);
		
		List<TbWorkChild> workChilds = workChildDao.findByWorkId(workId);
		
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		Map<String, Object> resultMap;
		List<Map<String, Object>> commonResultList;;
		Map<String, Object> commonMap;
		
		if(workChilds != null) {
			for (int i = 0; i < workChilds.size(); i++) {
				TbWorkChild workChild = workChilds.get(i);
				resultMap = new HashMap<String, Object>();
				resultMap.put("growthName", workChild.getName());
				resultMap.put("startTime", DateTimeUtil.formatSelf(workChild.getStartTime(),"yyyy-MM-dd"));
				resultMap.put("endTime", DateTimeUtil.formatSelf(workChild.getEndTime(),"yyyy-MM-dd"));
				resultMap.put("day", DateTimeUtil.getDateIffDay(workChild.getStartTime(), workChild.getEndTime()));
				List<Object[]> dataList = cropFarmingDao.statisticsFarmingByGrowth(workId, DateTimeUtil.formatSelf(workChild.getStartTime(),"yyyy-MM-dd"),  DateTimeUtil.formatSelf(workChild.getEndTime(),"yyyy-MM-dd"));
				commonResultList = new ArrayList<Map<String,Object>>();
				if (dataList != null) {
					for(Object[] data :dataList) {
						commonMap = new HashMap<>();
						commonMap.put("count", data[0] == null ? "" : data[0].toString());
						commonMap.put("time", data[1] == null ? "" : data[1].toString());
						commonMap.put("fileUrl", data[2] == null ? "" : data[2].toString());
						commonResultList.add(commonMap);
					}
				}
				resultMap.put("id", workId);
				resultMap.put("num", dataList == null ? 0 : dataList.size());
				resultMap.put("data", commonResultList);
				resultList.add(resultMap);
				
			}
		}
		
		res.add("status", 1).add("msg", "操作成功").add("result", resultList);
		return true;
	}

}
