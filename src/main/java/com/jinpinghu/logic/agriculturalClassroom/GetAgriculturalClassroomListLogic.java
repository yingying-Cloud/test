package com.jinpinghu.logic.agriculturalClassroom;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbAgriculturalClassroomDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalClassroom.param.GetAgriculturalClassroomListParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetAgriculturalClassroomListLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetAgriculturalClassroomListParam myParam = (GetAgriculturalClassroomListParam)logicParam;
		String type = myParam.getType();
		String title = myParam.getTitle();
		Integer nowPage = StringUtils.isNullOrEmpty(myParam.getNowPage())?null:Integer.valueOf(myParam.getNowPage());
		Integer pageCount = StringUtils.isNullOrEmpty(myParam.getPageCount())?null:Integer.valueOf(myParam.getPageCount());
	
		TbAgriculturalClassroomDao tbagriculturalClassroomDao = new TbAgriculturalClassroomDao(em);
		Integer maxCount = tbagriculturalClassroomDao.getListMapCount(type,title);
		
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
		
		resultList = tbagriculturalClassroomDao.getListMap(type,title,nowPage, pageCount);
		
		
		res.add("allCounts",maxCount);
		res.add("maxPage",maxPage);
		res.add("result", resultList);
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
