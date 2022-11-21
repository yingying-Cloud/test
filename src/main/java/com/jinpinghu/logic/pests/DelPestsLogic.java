package com.jinpinghu.logic.pests;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbPests;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbPestsDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.pests.param.GetPestsInfoParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelPestsLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		GetPestsInfoParam myParam = (GetPestsInfoParam)logicParam;
		Integer id = myParam.getId();
		TbPestsDao tbPestsDao = new TbPestsDao(em);
		TbPests tbPests = tbPestsDao.findById(id);
		if(tbPests!=null) {
			tbPestsDao.delete(tbPests);
			tbPestsDao.DelFileByPestsId(tbPests.getId());
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
