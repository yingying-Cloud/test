package com.jinpinghu.logic.post;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbPost;
import com.jinpinghu.db.dao.TbPostDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.post.param.DelTbPostParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelTbPostLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res,
			EntityManager em) throws Exception {
		DelTbPostParam myParam =(DelTbPostParam)logicParam;
		String idStr=myParam.getId();
		TbPostDao tpDao =new TbPostDao(em);
		String[] ids =idStr.split(",");
		if(ids!=null){
			for(String id:ids)
			{
				TbPost tp=tpDao.findById(Integer.valueOf(id));
				if(tp!=null){
					tp.setDelFlag(1);
					tpDao.update(tp);
				}
			}
		}else{
			res.add("msg", "请选择删除的对象");
			res.add("status", 1);
			return true;
		}
		res.add("msg", "删除成功");
		res.add("status", 1);
		return true;
	}

}
