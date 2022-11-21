package com.jinpinghu.logic.post;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbPostReply;
import com.jinpinghu.db.dao.TbPostReplyDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.post.param.SaveOrUpdateTbPostReplyParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class UpdateTbPostReplyStarLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		SaveOrUpdateTbPostReplyParam myParam = (SaveOrUpdateTbPostReplyParam) logicParam;
		String id = myParam.getId();
		String star = myParam.getStar();
		TbPostReplyDao tprDao = new TbPostReplyDao(em);
		TbPostReply tpr = tprDao.findById(Integer.valueOf(id));
		if (tpr != null) {
			tpr.setStar(star);
			tprDao.update(tpr);
		}
		res.add("status", 1);
		res.add("msg", "更新成功");
		return true;
	}

	@Override
	protected boolean validate(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) {
		SaveOrUpdateTbPostReplyParam myParam = (SaveOrUpdateTbPostReplyParam) logicParam;

		String id = myParam.getId();

		if (StringUtils.isEmpty(id) ) {
			res.add("status", -2);
			res.add("msg", "必填参数不能为空！");
			return false;
		}
		return true;
	}

}