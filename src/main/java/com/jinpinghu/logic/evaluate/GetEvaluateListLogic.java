package com.jinpinghu.logic.evaluate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.dao.TbEvaluateDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.evaluate.param.GetEvaluateListParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetEvaluateListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetEvaluateListParam myParam = (GetEvaluateListParam)logicParam;
		String type = myParam.getType();
		String searchId = myParam.getSearchId();
		String startTime = myParam.getStartTime();
		String endTime = myParam.getEndTime();
		String resId = myParam.getResId();
		String level = myParam.getLevel();
		Integer nowPage = StringUtils.isEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
	
		TbEvaluateDao tbEvaluateDao = new TbEvaluateDao(em);
		Integer maxCount = tbEvaluateDao.findByAllCount(resId, startTime, endTime, searchId, type,level);
		
		List<Map<String, Object>> resultList = new ArrayList<>();
		
		if(maxCount==null||maxCount==0){
			res.add("allCounts",0);
			res.add("maxPage",0);
			res.add("result", resultList);
			res.add("status", 1);
			res.add("msg", "无记录");
			return true;
		}
		int maxPage = 1;
		if(pageCount != null) {
			maxPage = maxCount/pageCount;
			if(maxCount%pageCount!=0){
				maxPage++;
			}
			if (pageCount * nowPage >= (maxCount + pageCount) && maxPage != 0) {
				nowPage = maxPage;
				res.add("allCounts",maxCount);
				res.add("maxPage",maxPage);
				res.add("result", resultList);
				res.add("status", 1);
				res.add("msg", "该页无记录");
				return true;
			}else if(maxPage == 0){
				nowPage = 1;
			}
		}
		TbFileDao tbFileDao = new TbFileDao(em);
		resultList = tbEvaluateDao.findByAll(resId, startTime, endTime, searchId, type, level, nowPage, pageCount);
		if(resultList!=null) {
			for(Map<String,Object> evaluate:resultList) {
				List<Map<String, Object>> file = tbFileDao.findByEvaluateMap(Integer.valueOf(evaluate.get("id").toString()));
				evaluate.put("file", file);
			}
		}
		
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
