package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResToolRecoveryRecordFile;

public class TbResToolRecoveryRecordFileDao extends BaseZDao {

	public TbResToolRecoveryRecordFileDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	
	@SuppressWarnings("unchecked")
	public List<TbResToolRecoveryRecordFile> findByToolRecordId(Integer toolRecoveryRecordId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResToolRecoveryRecordFile where toolRecoveryRecordId =:toolRecoveryRecordId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("toolRecoveryRecordId", toolRecoveryRecordId);
			List<TbResToolRecoveryRecordFile	> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
}
