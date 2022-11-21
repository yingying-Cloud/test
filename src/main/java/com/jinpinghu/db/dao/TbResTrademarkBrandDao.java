package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResTrademarkBrand;
import com.jinpinghu.db.bean.TbResTrademarkFile;


public class TbResTrademarkBrandDao extends BaseZDao{

	public TbResTrademarkBrandDao(EntityManager _em) {
		super(_em);
	}
	public List<TbResTrademarkBrand> findByTrademarkId(Integer trademarkId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResTrademarkBrand where trademarkId =:trademarkId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("trademarkId", trademarkId);
			List<TbResTrademarkBrand> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

	public List<Object[]> findBrandInfo(Integer trademarkId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select trtb.area,trtb.yield,tb.product_name,tb.id  from tb_res_trademark_brand trtb left join tb_brand tb on tb.id=trtb.brand_id  where trtb.trademark_id =:trademarkId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("trademarkId", trademarkId);
			List<Object[]> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

	
	
}
