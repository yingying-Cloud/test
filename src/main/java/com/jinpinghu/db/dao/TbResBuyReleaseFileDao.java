package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResBuyReleaseFile;

public class TbResBuyReleaseFileDao extends BaseZDao {

	public TbResBuyReleaseFileDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public List<TbResBuyReleaseFile> findByBuyReleaseId(Integer buyReleaseId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResBuyReleaseFile where buyReleaseId =:buyReleaseId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("buyReleaseId", buyReleaseId);
			List<TbResBuyReleaseFile> list=query.getResultList();
			return list;
		}catch(RuntimeException re) {
			throw re;
		}
	}

}
