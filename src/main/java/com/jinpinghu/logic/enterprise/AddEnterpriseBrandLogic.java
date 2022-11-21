package com.jinpinghu.logic.enterprise;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbResEnterpriseBrand;
import com.jinpinghu.db.dao.TbResEnterpriseBrandDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.AddEnterpriseBrandParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddEnterpriseBrandLogic extends BaseZLogic {

	@Override
	protected boolean execute(ZLogicParam logicParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddEnterpriseBrandParam myParam = (AddEnterpriseBrandParam)logicParam;
		Integer brandId = myParam.getBrandId();
		Integer enterpriseId = myParam.getEnterpriseId();
		
		TbResEnterpriseBrandDao tbResEnterpriseBrandDao = new TbResEnterpriseBrandDao(em);
		
		TbResEnterpriseBrand tbResEnterpriseBrand = tbResEnterpriseBrandDao.findByBrandIdEnterpriseId(enterpriseId, brandId);
		if(tbResEnterpriseBrand!=null) {
			if(tbResEnterpriseBrand.getDelFlag()==0) {
				tbResEnterpriseBrand.setDelFlag(1);
			} else {
				tbResEnterpriseBrand.setDelFlag(0);
			}
			tbResEnterpriseBrandDao.update(tbResEnterpriseBrand);
		}else {
			tbResEnterpriseBrand = new TbResEnterpriseBrand();
			tbResEnterpriseBrand.setBrandId(brandId);
			tbResEnterpriseBrand.setEnterpriseId(enterpriseId);
			tbResEnterpriseBrand.setDelFlag(0);
			tbResEnterpriseBrandDao.save(tbResEnterpriseBrand);
		}
		res.add("status", 1);
		res.add("msg", "操作成功");
		return true;
	}

}
