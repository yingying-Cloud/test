package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResEnterpriseFile;
import com.jinpinghu.db.bean.TbResToolFile;
import com.jinpinghu.db.bean.TbResToolRecoveryFile;


public class TbResToolRecoveryFileDao extends BaseZDao{

	public TbResToolRecoveryFileDao(EntityManager _em) {
		super(_em);
	}
	public List<TbResToolRecoveryFile> findByToolRecoveryId(Integer toolRecoveryId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResToolRecoveryFile where toolRecoveryId =:toolRecoveryId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("toolRecoveryId", toolRecoveryId);
			List<TbResToolRecoveryFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

}
