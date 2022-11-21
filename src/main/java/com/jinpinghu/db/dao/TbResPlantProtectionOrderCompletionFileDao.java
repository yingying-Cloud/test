package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResPlantProtectionOrderCompletionFile;

public class TbResPlantProtectionOrderCompletionFileDao extends BaseZDao{

	public TbResPlantProtectionOrderCompletionFileDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	@SuppressWarnings("unchecked")
	public List<TbResPlantProtectionOrderCompletionFile> findByCompletionId(Integer completionId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResPlantProtectionOrderCompletionFile where plantProtectionOrderCompletionId =:completionId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("completionId", completionId);
			List<TbResPlantProtectionOrderCompletionFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

}
