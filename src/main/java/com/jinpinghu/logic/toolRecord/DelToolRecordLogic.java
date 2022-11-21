package com.jinpinghu.logic.toolRecord;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbToolRecord;
import com.jinpinghu.db.dao.TbToolRecordDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolRecord.param.DelToolRecordParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelToolRecordLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelToolRecordParam myParam =(DelToolRecordParam)logicParam;
		String ids = myParam.getIds();
		TbToolRecordDao toolDao2 = new TbToolRecordDao(em);
		Integer count =0;
		if(!StringUtils.isEmpty(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				TbToolRecord tool = toolDao2.findById(Integer.valueOf(id));
				if(tool!=null) {
					tool.setDelFlag(1);
					toolDao2.update(tool);
					count++;
				}
			}
		}
		res.add("status", 1);
		res.add("msg", "成功删除"+count+"个");
		return true;
	}
}
