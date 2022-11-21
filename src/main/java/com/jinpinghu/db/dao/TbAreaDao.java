package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbArea;

public class TbAreaDao extends BaseZDao{

	public TbAreaDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}
	public TbArea findById(String id) {
		try {
			String queryString = "from TbArea where id = :id ";
			Query query = getEntityManager().createQuery(queryString);
			query.setParameter("id",id);
			List<TbArea> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list.get(0);
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	public List<Map<String, Object>> findByiv() {
		try {
			String queryString = "SELECT id,legal_person_idcard legalPersonIdcard FROM `tb_link_order_info` where type=2  and legal_person_idcard is not null";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String,Object>> list = query.getResultList();
			if (null != list && list.size() > 0) {
				return list;
			}
			return null;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	
	
}
