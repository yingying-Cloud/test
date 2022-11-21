package com.jinpinghu.logic.tool;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbTool;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.tool.param.DelToolParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelToolLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelToolParam myParam =(DelToolParam)logicParam;
		String ids = myParam.getIds();
		TbToolDao toolDao2 = new TbToolDao(em);
		Integer count =0;
		if(!StringUtils.isEmpty(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				TbTool tool = toolDao2.findById(Integer.valueOf(id));
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
