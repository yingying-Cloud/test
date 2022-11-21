package com.jinpinghu.logic.plant;

import java.util.Date;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbResCropFarmingTool;
import com.jinpinghu.db.dao.TbResCropFarmingToolDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.AddOrUpdateFarmingToolParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddOrUpdateFarmingToolLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateFarminToolParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateFarmingToolParam myParam=(AddOrUpdateFarmingToolParam)AddOrUpdateFarminToolParam;
		Integer id=StringUtil.isNullOrEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		Integer enterpriseId=StringUtil.isNullOrEmpty(myParam.getEnterpriseId())?null:Integer.valueOf(myParam.getEnterpriseId());
		String addPeople=myParam.getAddPeople();
		Integer workId=myParam.getWorkId();
		Integer toolId=myParam.getToolId();
		String specification=myParam.getSpecification();
		String unit=myParam.getUnit();
		String num=myParam.getNum();
		
		TbResCropFarmingTool tbResCropFarmingTool=new TbResCropFarmingTool();
		TbResCropFarmingToolDao tbResCropFarmingToolDao=new TbResCropFarmingToolDao(em);
		TbToolDao tbToolDao=new TbToolDao(em);

		String toolName=tbToolDao.findById(toolId).getName();
		if(id==null){
			tbResCropFarmingTool.setAddPeople(addPeople);
			tbResCropFarmingTool.setDelFlag(0);
			tbResCropFarmingTool.setEnterpriseId(enterpriseId);
			tbResCropFarmingTool.setNum(num);
			tbResCropFarmingTool.setInputTime(new Date());
			tbResCropFarmingTool.setUnit(unit);
			tbResCropFarmingTool.setSpecification(specification);
			tbResCropFarmingTool.setToolName(toolName);
			tbResCropFarmingTool.setWorkId(workId);
			tbResCropFarmingTool.setToolId(toolId);
			tbResCropFarmingToolDao.save(tbResCropFarmingTool);
			
		}else{
			tbResCropFarmingTool=tbResCropFarmingToolDao.findById(id);
			tbResCropFarmingTool.setAddPeople(addPeople);
			tbResCropFarmingTool.setEnterpriseId(enterpriseId);
			tbResCropFarmingTool.setNum(num);
			tbResCropFarmingTool.setUnit(unit);
			tbResCropFarmingTool.setSpecification(specification);
			tbResCropFarmingTool.setToolName(toolName);
			tbResCropFarmingTool.setWorkId(workId);
			tbResCropFarmingTool.setToolId(toolId);
			tbResCropFarmingToolDao.update(tbResCropFarmingTool);
		}
		res.add("status", 1)
		.add("msg", "�����ɹ���");
		return true;
	}

}
