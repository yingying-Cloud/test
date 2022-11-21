package com.jinpinghu.logic.enterprise;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;

import com.jinpinghu.db.bean.TbFile;
import com.jinpinghu.db.bean.TbResEnterpriseFile;
import com.jinpinghu.db.dao.TbFileDao;
import com.jinpinghu.db.dao.TbResToolFileDao;
import com.jinpinghu.logic.BaseZLogic;
import com.jinpinghu.logic.enterprise.param.DelEnterpriseFileParam;

import fw.jbiz.ext.json.ZSimpleJsonObject;
import fw.jbiz.logic.ZLogicParam;

public class DelEnterpriseFileLogic extends BaseZLogic{

	@Override
	protected boolean execute(ZLogicParam DelEnterpriseParam, ZSimpleJsonObject res, EntityManager em) throws Exception {
		DelEnterpriseFileParam myParam=(DelEnterpriseFileParam)DelEnterpriseParam;
		List<String> ids=Arrays.asList(myParam.getId().split(","));
		for(String id:ids){
			TbFileDao tfDao = new TbFileDao(em);
			TbResToolFileDao trfgDao =new TbResToolFileDao(em);
			List<TbResEnterpriseFile> trgfs =trfgDao.findByEnterpriseFileId(Integer.valueOf(id));
			if(trgfs!=null){
				for(TbResEnterpriseFile trgf:trgfs){
					trfgDao.delete(trgf);
					TbFile tfs =tfDao.findById(trgf.getFileId());
					tfDao.delete(tfs);
				}
			}
		}
		res.add("status", 1).add("msg","操作成功");
		return true;
	}

}
