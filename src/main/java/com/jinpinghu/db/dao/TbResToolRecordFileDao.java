package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResToolRecordFile;

public class TbResToolRecordFileDao extends BaseZDao {

	public TbResToolRecordFileDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	
	@SuppressWarnings("unchecked")
	public List<TbResToolRecordFile> findByToolRecordId(Integer toolRecordId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResToolRecordFile where toolRecordId =:toolRecordId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("toolRecordId", toolRecordId);
			List<TbResToolRecordFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
}
