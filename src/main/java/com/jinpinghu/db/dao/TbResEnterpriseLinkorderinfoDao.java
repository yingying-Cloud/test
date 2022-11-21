package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

import com.jinpinghu.db.bean.TbResEnterpriseCertificateFile;
import com.jinpinghu.db.bean.TbResEnterpriseLinkorderinfo;

public class TbResEnterpriseLinkorderinfoDao extends BaseZDao{

	public TbResEnterpriseLinkorderinfoDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public TbResEnterpriseLinkorderinfo findByEnterpriseIdAndLinkOrderInfoId(Integer enterpriseId,Integer linkOrderInfoId) {
		StringBuilder sb = new StringBuilder();
		sb.append(" from TbResEnterpriseLinkorderinfo where enterpriseId =:enterpriseId and linkOrderInfoId = :linkOrderInfoId ");
		Query query =getEntityManager().createQuery(sb.toString());
		query.setParameter("enterpriseId", enterpriseId);
		query.setParameter("linkOrderInfoId", linkOrderInfoId);
		List<TbResEnterpriseLinkorderinfo> list=query.getResultList();
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}
	
	public Map<String, Object> findMapByEnterpriseIdAndLinkOrderInfoId(Integer linkOrderInfoId) {
		StringBuilder sb = new StringBuilder();
//		sb.append("select group_concat(te.name) enterpriseName,group_concat(ifnull(te.dscd,'')) dscd from Tb_Res_Enterprise_Linkorderinfo tr left join tb_enterprise te on te.id=tr.enterprise_id where link_Order_Info_Id = :linkOrderInfoId group by link_Order_Info_Id ");
		sb.append("select te.name enterpriseName,te.dscd from Tb_Res_Enterprise_Linkorderinfo tr left join tb_enterprise te on te.id=tr.enterprise_id where link_Order_Info_Id = :linkOrderInfoId order by tr.id asc limit 1 ");
		Query query =getEntityManager().createNativeQuery(sb.toString());
		query.setParameter("linkOrderInfoId", linkOrderInfoId);
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list=query.getResultList();
		if(list!=null&&list.size()>0)
			return list.get(0);
		return null;
	}
	

}
