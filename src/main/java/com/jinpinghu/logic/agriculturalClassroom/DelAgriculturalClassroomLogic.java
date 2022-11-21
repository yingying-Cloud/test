package com.jinpinghu.logic.agriculturalClassroom;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbAgriculturalClassroom;
import com.jinpinghu.db.dao.TbAgriculturalClassroomDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.agriculturalClassroom.param.GetAgriculturalClassroomInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelAgriculturalClassroomLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetAgriculturalClassroomInfoParam myParam = (GetAgriculturalClassroomInfoParam)logicParam;
		Integer id = myParam.getId();
		TbAgriculturalClassroomDao tbagriculturalClassroomDao = new TbAgriculturalClassroomDao(em);
		TbAgriculturalClassroom agriculturalClassroom = tbagriculturalClassroomDao.findById(id);
		if(agriculturalClassroom!=null) {
			tbagriculturalClassroomDao.delete(agriculturalClassroom);
			tbagriculturalClassroomDao.DelFileByAgriculturalId(agriculturalClassroom.getId());
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
