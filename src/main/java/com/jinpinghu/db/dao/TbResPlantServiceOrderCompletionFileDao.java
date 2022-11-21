package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResPlantServiceOrderCompletionFile;

public class TbResPlantServiceOrderCompletionFileDao extends BaseZDao{

	public TbResPlantServiceOrderCompletionFileDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public List<TbResPlantServiceOrderCompletionFile> findByCompletionId(Integer completionId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResPlantServiceOrderCompletionFile where plantServiceOrderCompletionId =:completionId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("completionId", completionId);
			List<TbResPlantServiceOrderCompletionFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

}
