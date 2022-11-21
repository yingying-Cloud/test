package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResTrademarkFile;


public class TbResTrademarkFileDao extends BaseZDao{

	public TbResTrademarkFileDao(EntityManager _em) {
		super(_em);
	}
	public List<TbResTrademarkFile> findByTrademarkId(Integer trademarkId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResTrademarkFile where trademarkId =:trademarkId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("trademarkId", trademarkId);
			List<TbResTrademarkFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

}
