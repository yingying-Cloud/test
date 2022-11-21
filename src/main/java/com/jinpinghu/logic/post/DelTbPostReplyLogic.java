package com.jinpinghu.logic.post;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbPost;
import com.jinpinghu.db.bean.TbPostReply;
import com.jinpinghu.db.dao.TbPostDao;
import com.jinpinghu.db.dao.TbPostReplyDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.post.param.DelTbPostReplyParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelTbPostReplyLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DelTbPostReplyParam myParam=(DelTbPostReplyParam)logicParam;
		String idStr =myParam.getId();
		String postId =myParam.getPostId();
		TbPostReplyDao tprDao =new TbPostReplyDao(em);
		String[] ids =idStr.split(",");
		if(ids!=null){
			for(String id:ids)
			{
				TbPostReply tpr=tprDao.findById(Integer.valueOf(id));
				if(tpr!=null){
					tpr.setDelFlag(1);
					tprDao.update(tpr);
				}else{
					res.add("msg", "对象不存在 ");
					res.add("status", 1);
					return true;
				}
			}
		}else{
			res.add("msg", "请选择删除的对象");
			res.add("status", 1);
			return true;
		}
		if(!StringUtils.isEmpty(postId)){
			TbPostDao tpDao =new TbPostDao(em);
			TbPost tp= tpDao.findById(Integer.valueOf(postId));
			if(tp==null){
				res.add("msg", "帖子不存在");
				res.add("status", 1);
				return true;
			}
			TbPostReply tpr = tprDao.findLast(Integer.valueOf(postId));//改成单独一条
			Integer count =tprDao.findByPostIdCount(Integer.valueOf(postId));
			count = count==null?0:count;
			if(tp!=null){
				if(tpr!=null){
					tp.setLastReplyId(tpr.getId());
					tp.setReplyCount(count);
					tp.setLastReplyTime(tpr.getInputTime());
					tpDao.update(tp);
				}else{
					tp.setLastReplyId(null);
					tp.setReplyCount(0);
					tp.setLastReplyTime(null);
					tpDao.update(tp);
				}
			}
		}
		res.add("msg", "删除成功");
		res.add("status", 1);
		return true;
	}
}
