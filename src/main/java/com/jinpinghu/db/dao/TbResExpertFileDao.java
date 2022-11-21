package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResExpertFile;

public class TbResExpertFileDao extends BaseZDao{

	public TbResExpertFileDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public List<TbResExpertFile> findByExpertId(Integer expertId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResExpertFile where expertId =:expertId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("expertId", expertId);
			List<TbResExpertFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
}
