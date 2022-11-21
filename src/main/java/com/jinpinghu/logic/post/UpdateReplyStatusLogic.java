package com.jinpinghu.logic.post;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbPostReply;
import com.jinpinghu.db.dao.TbPostReplyDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.post.param.UpdateReplyStatusParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class UpdateReplyStatusLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		UpdateReplyStatusParam myParam = (UpdateReplyStatusParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		Integer status = StringUtils.isEmpty(myParam.getStatus()) ? null : Integer.valueOf(myParam.getStatus());
		
		TbPostReplyDao replyDao = new TbPostReplyDao(em);
		TbPostReply reply = replyDao.findById(id);
		
		if(reply != null) {
			if(status != null) {
				reply.setStatus(status);
				replyDao.update(reply);
			}
			
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
