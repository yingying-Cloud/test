package com.jinpinghu.db.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.jinpinghu.db.bean.TbResEnterpriseBrand;
import com.jinpinghu.db.bean.TbResTrademarkFile;


public class TbResEnterpriseBrandDao extends BaseZDao{

	public TbResEnterpriseBrandDao(EntityManager _em) {
		super(_em);
	}
	public List<TbResEnterpriseBrand> findByEnterpriseId(Integer enterpriseId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResEnterpriseBrand where enterpriseId =:enterpriseId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
			List<TbResEnterpriseBrand> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	
	public TbResEnterpriseBrand findByBrandIdEnterpriseId(Integer enterpriseId,Integer brandId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResEnterpriseBrand where enterpriseId =:enterpriseId and brand_id=:brandId    ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
			query.setParameter("brandId", brandId);
			List<TbResEnterpriseBrand> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list.get(0);
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	

	public List<Object[]> findBrandInfo(Integer enterpriseId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select trtb.area,trtb.yield,tb.product_name,tb.id  from tb_res_enterprise_brand trtb left join tb_brand tb on tb.id=trtb.brand_id  where trtb.enterprise_id =:enterpriseId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("enterpriseId", enterpriseId);
			List<Object[]> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}

	
	
}
