package com.jinpinghu.logic.expert;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbExpert;
import com.jinpinghu.db.dao.TbExpertDao;
import com.jinpinghu.db.dao.TbResUserExpertDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.expert.param.DelExpertParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelExpertLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam DelExpertParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelExpertParam myParam=(DelExpertParam)DelExpertParam;
		List<String> ids=Arrays.asList(myParam.getId().split(","));
		
		TbExpertDao expertDao = new TbExpertDao(em);
		TbResUserExpertDao tbResUserExpertDao = new TbResUserExpertDao(em);
		
		for(String id:ids){
			TbExpert tbExpert=expertDao.findById(Integer.valueOf(id));
			tbExpert.setDelFlag(1);
			expertDao.update(tbExpert);
			tbResUserExpertDao.delByExpertId(Integer.valueOf(id));
		}
		res.add("status", 1).add("msg", "操作成功！");
		return true;
	}

}
