package com.jinpinghu.logic.inspection;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbInspectionTypeDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetTypeListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {	
		InspectionParam param = (InspectionParam)logicParam;

		String name = param.getName();
		Integer point = param.getPoint();
		Integer nowPage = StringUtil.isNullOrEmpty(param.getNowPage()) ? null : Integer.valueOf(param.getNowPage());
		Integer pageCount = StringUtil.isNullOrEmpty(param.getPageCount()) ? null : Integer.valueOf(param.getPageCount());
		
		TbInspectionTypeDao dao = new TbInspectionTypeDao(em);
		Integer count = dao.getTypeListCount(name, point);
		
		List<Map<String, Object>> result = dao.getTypeList(name, point, nowPage, pageCount);
		
		res.add("result", result);
		res.add("allCounts", count);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
