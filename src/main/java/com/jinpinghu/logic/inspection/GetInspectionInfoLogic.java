package com.jinpinghu.logic.inspection;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbInspectionDao;
import com.jinpinghu.db.dao.TbResInspectionFileDao;
import com.jinpinghu.logic.BaseZLogic;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetInspectionInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		InspectionParam param = (InspectionParam)logicParam;
		Integer id = param.getId();
		
		TbInspectionDao dao = new TbInspectionDao(em);

		Map<String, Object> result = dao.getInspectionInfo(id);
		TbResInspectionFileDao filedao = new TbResInspectionFileDao(em);
		List<Map<String, Object>> file = filedao.getFileList(id);
		result.put("file", file);
		
		res.add("result", result);
		res.add("status", 1);
		res.add("msg", "成功");
		return true;
	}

}
