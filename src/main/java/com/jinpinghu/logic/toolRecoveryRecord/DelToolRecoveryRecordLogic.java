package com.jinpinghu.logic.toolRecoveryRecord;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbToolRecoveryRecord;
import com.jinpinghu.db.dao.TbToolRecoveryRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecoveryRecord.param.DelToolRecoveryRecordParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelToolRecoveryRecordLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelToolRecoveryRecordParam myParam =(DelToolRecoveryRecordParam)logicParam;
		String ids = myParam.getIds();
		TbToolRecoveryRecordDao toolRecoveryDao2 = new TbToolRecoveryRecordDao(em);
		Integer count =0;
		if(!StringUtils.isEmpty(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				TbToolRecoveryRecord toolRecovery = toolRecoveryDao2.findById(Integer.valueOf(id));
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
