package com.jinpinghu.logic.arableLand;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbArableLand;
import com.jinpinghu.db.dao.TbArableLandDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.arableLand.param.AddOrUpdateArableLandParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelArableLandLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		AddOrUpdateArableLandParam myParam = (AddOrUpdateArableLandParam)logicParam;
		String id = myParam.getId();
		TbArableLandDao tbArableaLandDao = new TbArableLandDao(em);
		if(!StringUtils.isEmpty(id)) {
			TbArableLand tbArableaLand = tbArableaLandDao.findById(id);
			if(tbArableaLand != null)tbArableaLandDao.delete(tbArableaLand);
		}
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
