package com.jinpinghu.logic.rotation;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbRotationTime;
import com.jinpinghu.db.dao.TbRotationTimeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.rotation.param.DelRotationParam;
import com.mysql.cj.util.StringUtils;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelRotationLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelRotationParam myParam = (DelRotationParam)logicParam;
		String rotationIds = myParam.getRotationIds();
		TbRotationTimeDao tbRotationTimeDao = new TbRotationTimeDao(em);
		if(!StringUtils.isNullOrEmpty(rotationIds)) {
			List<String> list = Arrays.asList(rotationIds.split(","));
			for(String id:list) {
				TbRotationTime tbRotationTime = tbRotationTimeDao.findById(Integer.valueOf(id));
				tbRotationTime.setDelFlag(1);
				tbRotationTimeDao.update(tbRotationTime);
			}
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
