package com.jinpinghu.logic.sc;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;

import com.jinpinghu.db.bean.TbBuyRelease;
import com.jinpinghu.db.dao.TbBuyReleaseDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sc.param.DelBuyReleaseParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelBuyReleaseLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		DelBuyReleaseParam myParam = (DelBuyReleaseParam)logicParam;
		String ids = myParam.getIds();
		
		if (!StringUtils.isEmpty(ids)) {
			TbBuyReleaseDao buyReleaseDao = new TbBuyReleaseDao(em);
			String[] idArray = ids.split(",");
			TbBuyRelease buyRelease;
			for (int i = 0; i < idArray.length; i++) {
				buyRelease = buyReleaseDao.findById(Integer.valueOf(idArray[i]));
				if (buyRelease != null) {
					buyRelease.setDelFlag(1);
					buyReleaseDao.update(buyRelease);
				}
			}
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
