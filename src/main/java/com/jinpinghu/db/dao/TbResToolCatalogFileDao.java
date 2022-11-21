package com.jinpinghu.db.dao;

import com.jinpinghu.db.bean.TbResEnterpriseFile;
import com.jinpinghu.db.bean.TbResToolCatalogFile;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


public class TbResToolCatalogFileDao extends BaseZDao{

	public TbResToolCatalogFileDao(EntityManager _em) {
		super(_em);
	}
	public List<TbResToolCatalogFile> findByToolCatalogId(Integer toolId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResToolCatalogFile where toolCatalogId =:toolId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("toolId", toolId);
			List<TbResToolCatalogFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

}
