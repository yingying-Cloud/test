package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbResAdvertisementEnterprise;

public class TbResAdvertisementEnterpriseDao extends BaseZDao {

	public TbResAdvertisementEnterpriseDao(EntityManager _em) {
		super(_em);
		// TODO 自动生成的构造函数存根
	}

	public List<TbResAdvertisementEnterprise> findByAdvertisementId(Integer advertisementId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("  from TbResAdvertisementEnterprise where advertisementId =:advertisementId     ");
			Query query =getEntityManager().createQuery(sb.toString());
			query.setParameter("advertisementId", advertisementId);
			List<TbResAdvertisementEnterprise> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
	public List<Map<String, Object>> findEnterpriseByAdvertisementId(Integer advertisementId){
		try {
			StringBuffer sb = new StringBuffer();
			sb.append(" select te.id,te.name  from Tb_Res_Advertisement_Enterprise trae left join tb_enterprise te on te.id=trae.enterprise_id where advertisement_Id =:advertisementId     ");
			Query query =getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("advertisementId", advertisementId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list=query.getResultList();
			if(list!=null&&list.size()>0)
				return list;
			return null;
		}catch(RuntimeException re) {
			throw re;
		}
	}
}
