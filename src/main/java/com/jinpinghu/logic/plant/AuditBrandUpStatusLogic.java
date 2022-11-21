package com.jinpinghu.logic.plant;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.jinpinghu.common.tools.StringUtil;
import com.jinpinghu.db.bean.TbBrand;
import com.jinpinghu.db.dao.TbBrandDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.plant.param.AuditBrandParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AuditBrandUpStatusLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam AuditBrandParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AuditBrandParam myParam=(AuditBrandParam)AuditBrandParam;
		String[] ids=myParam.getIds().split(",");
		Integer upStatus=StringUtil.isNullOrEmpty(myParam.getStatus())?null:Integer.valueOf(myParam.getStatus());
		
		TbBrandDao tbBrandDao=new TbBrandDao(em);
		TbBrand tbBrand=null;
		
		for(String id:ids){
			//tbBrand=new TbBrand();
			tbBrand=tbBrandDao.findById(Integer.valueOf(id));
			if(tbBrand!=null) {
//				tbBrand.setUpStatus(upStatus);
				tbBrandDao.update(tbBrand);
			}
		}
		
		res.add("status", 1)
		.add("msg", "操作成功！");
		return true;
	}
}

