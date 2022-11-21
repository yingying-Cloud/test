package com.jinpinghu.db.dao;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public class TbApplicantCompanyTrademarkDao extends BaseZDao {

	public TbApplicantCompanyTrademarkDao(EntityManager _em) {
		super(_em);
		// TODO Auto-generated constructor stub
	}
	
	public List<Map<String, Object>> getApplicantCompanyTrademarkList(Integer applicantCompanyId){
		try {
			String queryString = " SELECT id,name,type,ifnull(date(start_time),'') startTime,ifnull(date(end_time),'') endTime ";
			queryString += " FROM tb_applicant_company_trademark where del_flag = 0 and applicant_company_id = :applicantCompanyId ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("applicantCompanyId", applicantCompanyId);
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
	
	public Map<String, Object> getInfoById(Integer id){
		try {
			String queryString = " SELECT id,name,type,ifnull(date(start_time),'') startTime,ifnull(date(end_time),'') endTime ";
			queryString += " FROM tb_applicant_company_trademark where del_flag = 0 and id = :id ";
			Query query = getEntityManager().createNativeQuery(queryString);
			query.setParameter("id", id);
			query.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List<Map<String, Object>> list = query.getResultList();
			if(list != null && list.size()>0) 
				return list.get(0);
			return null;
		} catch (RuntimeException e) {
			// TODO: handle exception
			throw e;
		}
	}

}
