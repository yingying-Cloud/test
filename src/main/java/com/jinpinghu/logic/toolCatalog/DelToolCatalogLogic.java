package com.jinpinghu.logic.toolCatalog;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.db.dao.TbToolDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolCatalog.param.DelToolCatalogParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelToolCatalogLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelToolCatalogParam myParam =(DelToolCatalogParam)logicParam;
		String ids = myParam.getIds();
		TbToolCatalogDao toolCatelogDao2 = new TbToolCatalogDao(em);
		Integer count =0;
		if(!StringUtils.isEmpty(ids)) {
			String[] split = ids.split(",");
			for(String id:split) {
				TbToolCatalog toolCatelog = toolCatelogDao2.findById(Integer.valueOf(id));
				if(toolCatelog!=null) {
					toolCatelog.setDelFlag(1);
					toolCatelogDao2.update(toolCatelog);
					count++;
				}
				
				if(!StringUtils.isEmpty(toolCatelog.getUniformPrice())&&toolCatelog.getUniformPrice().equals("1")) {
					TbToolDao tbToolDao = new TbToolDao(em);
					tbToolDao.delUniforTool(toolCatelog.getCode());
				}
				
				
			}
		}
		res.add("status", 1);
		res.add("msg", "成功删除"+count+"个");
		return true;
	}
}
