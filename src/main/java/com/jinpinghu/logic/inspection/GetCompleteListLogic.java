package com.jinpinghu.logic.inspection;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.dao.TbInspectionCompleteDao;
import com.jinpinghu.db.dao.TbResInspectionCompleteFileDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetCompleteListLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;
		Integer type = param.getType();
		Integer inspectionId = param.getInspectionId();
		Integer nowPage = StringUtil.isNullOrEmpty(param.getNowPage()) ? null : Integer.valueOf(param.getNowPage());
		Integer pageCount = StringUtil.isNullOrEmpty(param.getPageCount()) ? null : Integer.valueOf(param.getPageCount());
		
		TbInspectionCompleteDao dao = new TbInspectionCompleteDao(em);
		Integer count = dao.getCompleteListCount(inspectionId, type);
		List<Map<String, Object>> result = dao.getCompleteList(inspectionId,type, nowPage, pageCount);
		
		TbResInspectionCompleteFileDao fileDao = new TbResInspectionCompleteFileDao(em);
		List<Map<String, Object>> file = null;
		if(result != null){
			for(Map<String, Object> map : result){
				file = fileDao.getFileList(Integer.valueOf(map.get("id").toString()));
				map.put("file", file);
			}
		}
		res.add("result", result);
		res.add("allCounts", count);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
