package com.jinpinghu.logic.toolCatalog;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolCatalog.param.AuditToolCatalogStatusParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AuditToolCatalogStatusLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AuditBrandParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AuditToolCatalogStatusParam myParam=(AuditToolCatalogStatusParam)AuditBrandParam;
		String[] ids=myParam.getIds().split(",");
		Integer status=StringUtil.isNullOrEmpty(myParam.getStatus())?null:Integer.valueOf(myParam.getStatus());
		
		TbToolCatalogDao tbToolCatalogDao=new TbToolCatalogDao(em);
		TbToolCatalog tbToolCatalog=null;
		
		for(String id:ids){
			tbToolCatalog=tbToolCatalogDao.findById(Integer.valueOf(id));
			if(tbToolCatalog!=null) {
				tbToolCatalog.setStatus(status);
				tbToolCatalogDao.update(tbToolCatalog);
			}
		}
		
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}
}

