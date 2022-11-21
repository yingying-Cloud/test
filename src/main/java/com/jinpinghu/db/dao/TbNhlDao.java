package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public class TbNhlDao extends BaseZDao{

	public TbNhlDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public List<Map<String, Object>> listNhl(){
		StringBuilder queryString = new StringBuilder();
		queryString.append(" SELECT tn.id,tn.name,ifnull(tn.describe,'') 'describe',tn.status, ");
		queryString.append(" tn.member_count memberCount,IFNULL(tf.file_url,'') fileUrl ");
		queryString.append(" FROM tb_nhl tn left join tb_res_nhl_file trnf on trnf.nhl_id = tn.id ");
		queryString.append(" left join tb_file tf on tf.id = trnf.file_id  where tn.del_flag = 0 group by tn.id ");
		Query query = getEntityManager().createNativeQuery(queryString.toString());
		query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List<Map<String, Object>> list = query.getResultList();
		return list;
	}

}
