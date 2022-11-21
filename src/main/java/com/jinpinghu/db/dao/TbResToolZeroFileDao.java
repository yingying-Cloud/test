package com.jinpinghu.db.dao;

import com.jinpinghu.db.bean.TbResEnterpriseFile;
import com.jinpinghu.db.bean.TbResToolFile;
import com.jinpinghu.db.bean.TbResToolZeroFile;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


public class TbResToolZeroFileDao extends BaseZDao{

	public TbResToolZeroFileDao(EntityManager _em) {
		super(_em);
	}
	public List<TbResToolZeroFile> findByToolZeroId(Integer toolId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResToolZeroFile where toolZeroId =:toolId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("toolId", toolId);
			List<TbResToolZeroFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
}
