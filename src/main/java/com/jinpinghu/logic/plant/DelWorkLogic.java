package com.jinpinghu.logic.plant;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbWork;
import com.jinpinghu.db.dao.TbWorkChildDao;
import com.jinpinghu.db.dao.TbWorkDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.SimpleParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelWorkLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam SimpleParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		SimpleParam myParam=(SimpleParam)SimpleParam;
		String[] ids=myParam.getId().split(",");
		
		TbWork tbWork=new TbWork();
		TbWorkDao tbWorkDao=new TbWorkDao(em);
		TbWorkChildDao tbWorkChildDao=new TbWorkChildDao(em);
		for(String id:ids){
			tbWorkChildDao.delById(Integer.valueOf(id));
			tbWork=tbWorkDao.findById(Integer.valueOf(id));
			tbWork.setDelFlag(1);
			tbWorkDao.update(tbWork);
		}

		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}
}
