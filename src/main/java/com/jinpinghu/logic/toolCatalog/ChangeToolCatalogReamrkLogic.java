package com.jinpinghu.logic.toolCatalog;

import javax.persistence.EntityManager;

import org.apache.commons.lang.StringUtils;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbToolCatalog;
import com.jinpinghu.db.dao.TbToolCatalogDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.toolCatalog.param.AuditToolCatalogStatusParam;
import com.jinpinghu.logic.toolCatalog.param.ChangeToolCatalogReamrkParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class ChangeToolCatalogReamrkLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AuditBrandParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		ChangeToolCatalogReamrkParam myParam=(ChangeToolCatalogReamrkParam)AuditBrandParam;
		String id = myParam.getId();
		String remark = myParam.getRemark();
		
		TbToolCatalogDao tbToolCatalogDao=new TbToolCatalogDao(em);
		TbToolCatalog tbToolCatalog=null;
		if(!StringUtils.isEmpty(id)) {
			tbToolCatalog=tbToolCatalogDao.findById(Integer.valueOf(id));
		}
		if(tbToolCatalog!=null) {
			tbToolCatalog.setRemark(remark);
			tbToolCatalogDao.update(tbToolCatalog);
		}
		
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}
}

