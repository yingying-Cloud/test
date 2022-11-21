package com.jinpinghu.logic.sellBrand;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbSellBrand;
import com.jinpinghu.db.dao.TbSellBrandDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.sellBrand.param.AuditSellBrandParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AuditSellBrandUpStatusLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AuditBrandParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AuditSellBrandParam myParam=(AuditSellBrandParam)AuditBrandParam;
		String[] ids=myParam.getIds().split(",");
		Integer upStatus=StringUtil.isNullOrEmpty(myParam.getStatus())?null:Integer.valueOf(myParam.getStatus());
		
		TbSellBrandDao tbBrandDao=new TbSellBrandDao(em);
		TbSellBrand tbBrand=null;
		
		for(String id:ids){
			tbBrand=tbBrandDao.findById(Integer.valueOf(id));
			if(tbBrand!=null) {
				tbBrand.setStatus(upStatus);
				tbBrandDao.update(tbBrand);
			}
		}
		
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}
}

