package com.jinpinghu.logic.workTime;

import javax.persistence.EntityManager;

import com.aliyuncs.utils.StringUtils;
import com.jinpinghu.db.bean.TbResUserWorkTime;
import com.jinpinghu.db.dao.TbUserWorkTimeDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.workTime.param.AddOrUpdateWorkTimeParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelWorkTimeLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AddOrUpdateWorkTimeParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrUpdateWorkTimeParam myParam=(AddOrUpdateWorkTimeParam)AddOrUpdateWorkTimeParam;
		Integer id = StringUtils.isEmpty(myParam.getId())?null:Integer.valueOf(myParam.getId());
		
		TbUserWorkTimeDao tbUserWorkTimeDao=new TbUserWorkTimeDao(em);
		TbResUserWorkTime workTime = tbUserWorkTimeDao.findById(id);
		if(workTime!=null) {
			tbUserWorkTimeDao.delete(workTime);
		}
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;

	}

}
