package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public class TbResApplicantCompanyTrademarkFileDao extends BaseZDao {

	public TbResApplicantCompanyTrademarkFileDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public void delApplicantCompanyTrademarkFile(Integer applicantCompanyId) {
		try {
			String queryString = " update tb_res_applicant_company_trademark_file tractf left join tb_applicant_company_trademark tact on tact.id = tractf.applicant_company_trademark_id set tractf.del_flag = 1 where tact.applicant_company_id = :applicantCompanyId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("applicantCompanyId", applicantCompanyId);
			query.executeUpdate();
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}
	
	public List<Map<String, Object>> getApplicantCompanyTrademarkFileList(Integer applicantCompanyTrademarkId){
		try {
			String queryString = " SELECT tracf.type type,tf.id id,tf.file_type fileType,tf.file_url fileUrl,tf.file_name fileName,tf.file_size fileSize ";
			queryString += " FROM tb_res_applicant_company_trademark_file tracf left join tb_file tf on tracf.file_id = tf.id where tracf.del_flag = 0 and tracf.applicant_company_trademark_id = :applicantCompanyTrademarkId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("applicantCompanyTrademarkId", applicantCompanyTrademarkId);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if(list != null && list.size()>0) 
				return list;
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}

}
