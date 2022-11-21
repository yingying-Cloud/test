package com.jinpinghu.logic.plantService;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbPlantService;
import com.jinpinghu.db.dao.TbPlantServiceDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plantService.param.ChangePlantServiceStatusParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ChangePlantServiceStatusLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		ChangePlantServiceStatusParam myParam = (ChangePlantServiceStatusParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		String status = myParam.getStatus();
		
		TbPlantServiceDao plantServiceDao = new TbPlantServiceDao(em);
		TbPlantService plantService = plantServiceDao.findById(id);
		plantService.setStatus(status);
		plantServiceDao.update(plantService);
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
