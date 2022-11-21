package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResBrandSaleFile;

public class TbResBrandSaleFileDao extends BaseZDao{

	public TbResBrandSaleFileDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public List<TbResBrandSaleFile> findByBrandSaleId(Integer brandSaleId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResBrandSaleFile where brandSaleId =:brandSaleId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("brandSaleId", brandSaleId);
			List<TbResBrandSaleFile> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
}
