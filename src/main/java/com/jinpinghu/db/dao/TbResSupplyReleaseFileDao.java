package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResSupplyReleaseFile;

public class TbResSupplyReleaseFileDao extends BaseZDao {

	public TbResSupplyReleaseFileDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public List<TbResSupplyReleaseFile> findBySupplyReleaseId(Integer supplyReleaseId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResSupplyReleaseFile where supplyReleaseId =:supplyReleaseId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("supplyReleaseId", supplyReleaseId);
			List<TbResSupplyReleaseFile> list=query.getResultList();
			return list;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	

}
