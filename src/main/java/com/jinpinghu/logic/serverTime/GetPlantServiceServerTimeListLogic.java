package com.jinpinghu.logic.serverTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.db.dao.TbPlantServiceServerTimeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.serverTime.param.GetPlantServiceListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetPlantServiceServerTimeListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetPlantServiceListParam myParam = (GetPlantServiceListParam)logicParam;
		Integer protectionId = StringUtils.isEmpty(myParam.getServiceId()) ? null : Integer.valueOf(myParam.getServiceId());
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage()) ? null : Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount()) ? null : Integer.valueOf(myParam.getPageCount());
		TbPlantServiceServerTimeDao serverTimeDao = new TbPlantServiceServerTimeDao(em);
		Integer maxCount = 0;
		
		maxCount = serverTimeDao.findPlantServiceServerTimeListCount(protectionId);
		
		if(maxCount == null || maxCount == 0) {
			res.add("status", 1);
			res.add("msg", "无数据");
			res.add("result", new ArrayList<>());
			res.add("allCounts",maxCount);
			res.add("maxPage",0);
			return true;
		}
		
		Integer maxPage = null;
		if(pageCount != null) {
			maxPage = 1;
			maxPage = (int) (maxCount/pageCount);
			if(maxCount%pageCount!=0){
				maxPage++;
			}
		}
		
		
		List<Map<String, Object>> dataList = serverTimeDao.findPlantServiceServerTimeList(protectionId, nowPage, pageCount);
		
		res.add("status", 1).add("msg", "操作成功").add("result", dataList == null ? new ArrayList<>() : dataList).add("allCount", maxCount).add("maxPage", maxPage);
		return true;
	}

}
