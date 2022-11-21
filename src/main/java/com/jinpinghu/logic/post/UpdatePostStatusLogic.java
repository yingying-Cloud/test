package com.jinpinghu.logic.post;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbPost;
import com.jinpinghu.db.dao.TbPostDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.post.param.UpdatePostStatusParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class UpdatePostStatusLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		// TODO Auto-generated method stub
		UpdatePostStatusParam myParam = (UpdatePostStatusParam)logicParam;
		Integer id = StringUtils.isEmpty(myParam.getId()) ? null : Integer.valueOf(myParam.getId());
		Integer status = StringUtils.isEmpty(myParam.getStatus()) ? null : Integer.valueOf(myParam.getStatus());
		
		TbPostDao postDao = new TbPostDao(em);
		TbPost post = postDao.findById(id);
		
		if(post != null) {
			if(status != null) {
				post.setStatus(status);
				postDao.update(post);
			}
			
		}
		
		res.add("status", 1).add("msg", "操作成功");
		return true;
	}

}
