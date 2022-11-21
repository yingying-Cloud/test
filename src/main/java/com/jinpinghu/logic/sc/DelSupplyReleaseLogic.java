package com.jinpinghu.logic.sc;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.db.bean.TbSupplyRelease;
import com.jinpinghu.db.dao.TbSupplyReleaseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.DelSupplyReleaseParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelSupplyReleaseLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		DelSupplyReleaseParam myParam = (DelSupplyReleaseParam)logicParam;
		String ids = myParam.getIds();
		
		if (!StringUtils.isEmpty(ids)) {
			TbSupplyReleaseDao supplyReleaseDao = new TbSupplyReleaseDao(em);
			String[] idArray = ids.split(",");
			TbSupplyRelease supplyRelease;
			for (int i = 0; i < idArray.length; i++) {
				supplyRelease = supplyReleaseDao.findById(Integer.valueOf(idArray[i]));
				if (supplyRelease != null) {
					supplyRelease.setDelFlag(1);
					supplyReleaseDao.update(supplyRelease);
				}
			}
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
