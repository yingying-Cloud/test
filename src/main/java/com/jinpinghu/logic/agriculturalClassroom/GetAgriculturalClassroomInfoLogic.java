package com.jinpinghu.logic.agriculturalClassroom;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.db.dao.TbAgriculturalClassroomDao;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalClassroom.param.GetAgriculturalClassroomInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class GetAgriculturalClassroomInfoLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetAgriculturalClassroomInfoParam myParam = (GetAgriculturalClassroomInfoParam)logicParam;
		Integer id = myParam.getId();
		TbAgriculturalClassroomDao tbagriculturalClassroomDao = new TbAgriculturalClassroomDao(em);
		TbFileDao tfDao = new TbFileDao(em);
		Map<String, Object> result = tbagriculturalClassroomDao.findMapById(id);
		List<Map<String, Object>> tfs =tfDao.getListByAgriculturalClassroomId(id);
		result.put("file", tfs);
		res.add("status", 1).add("msg", "操作成功").add("result", result);
		return true;
	}

}
