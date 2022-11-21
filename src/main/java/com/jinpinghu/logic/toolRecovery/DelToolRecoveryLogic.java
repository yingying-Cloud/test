package com.jinpinghu.logic.toolRecovery;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbToolRecovery;
import com.jinpinghu.db.dao.TbToolRecoveryDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecovery.param.DelToolRecoveryParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelToolRecoveryLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelToolRecoveryParam myParam =(DelToolRecoveryParam)logicParam;
		String ids = myParam.getIds();
		TbToolRecoveryDao toolRecoveryDao2 = new TbToolRecoveryDao(em);
		Integer count =0;
		if(!StringUtils.isEmpty(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				TbToolRecovery toolRecovery = toolRecoveryDao2.findById(Integer.valueOf(id));
				if(toolRecovery!=null) {
					toolRecovery.setDelFlag(1);
					toolRecoveryDao2.update(toolRecovery);
					count++;
				}
			}
		}
		res.add("status", 1);
		res.add("msg", "成功删除"+count+"个");
		return true;
	}
}
