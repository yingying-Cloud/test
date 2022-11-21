package com.jinpinghu.logic.enterprise;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbEnterpriseZero;
import com.jinpinghu.db.bean.TbResEnterpriseZone;
import com.jinpinghu.db.dao.TbEnterpriseZeroDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.AddOrRemoveEnterpriseZeroParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class AddOrRemoveEnterpriseZeroLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam DelEnterpriseParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		AddOrRemoveEnterpriseZeroParam myParam=(AddOrRemoveEnterpriseZeroParam)DelEnterpriseParam;
		List<String> ids=Arrays.asList(myParam.getId().split(","));
		String type = myParam.getType();
		TbEnterpriseZeroDao enterpriseDao = new TbEnterpriseZeroDao(em);
		if("2".equals(type)) {
			if(ids != null) {
				for(String id:ids){
					TbEnterpriseZero zero = enterpriseDao.findByEnterpriseId(Integer.valueOf(id));
					if(zero!=null) {
						zero.setDelFlag(1);
						enterpriseDao.update(zero);
					}
				}
			}
		}else if("1".equals(type)) {
			if(ids != null) {
				for(String id:ids){
					TbEnterpriseZero zero = enterpriseDao.findByEnterpriseId(Integer.valueOf(id));
					if(zero==null) {
						TbEnterpriseZero tbEnterprise=new TbEnterpriseZero();
						tbEnterprise.setDelFlag(0);
						tbEnterprise.setFlag(0);
						tbEnterprise.setEnterpriseId(Integer.valueOf(id));
						enterpriseDao.save(tbEnterprise);
					}
				}
			}
		}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}

}
