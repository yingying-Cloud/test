package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public class TbResInspectionCompleteFileDao extends BaseZDao{

	public TbResInspectionCompleteFileDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	public List<Map<String, Object>> getFileList(Integer id) {
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("select tf.file_url fileUrl, tf.file_type fileType, tf.file_name fileName, tf.file_size fileSize "
					+ "from tb_file tf right join tb_res_inspection_complete_file trif on tf.id = trif.file_id "
					+ "where trif.inspection_complete_id = :id ");
		
			Query query = getEntityManager().createNativeQuery(sb.toString());
			query.setParameter("id", id);
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
